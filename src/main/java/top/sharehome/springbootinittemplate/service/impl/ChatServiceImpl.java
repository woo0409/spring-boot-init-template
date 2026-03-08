package top.sharehome.springbootinittemplate.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.sharehome.springbootinittemplate.mapper.ChatConversationMapper;
import top.sharehome.springbootinittemplate.mapper.ChatMessageMapper;
import top.sharehome.springbootinittemplate.model.dto.ChatHistoryDTO;
import top.sharehome.springbootinittemplate.model.dto.ChatMarkReadDTO;
import top.sharehome.springbootinittemplate.model.dto.ChatPullDTO;
import top.sharehome.springbootinittemplate.model.dto.ChatSendDTO;
import top.sharehome.springbootinittemplate.model.entity.ChatConversation;
import top.sharehome.springbootinittemplate.model.entity.ChatMessage;
import top.sharehome.springbootinittemplate.model.vo.*;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements top.sharehome.springbootinittemplate.service.ChatService {

    private final ChatConversationMapper conversationMapper;
    private final ChatMessageMapper messageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChatSendVO send(ChatSendDTO dto) {
        Long me = LoginUtils.getLoginUserId();
        if (dto == null || dto.getPeerId() == null) {
            throw new IllegalArgumentException("peerId 不能为空");
        }
        if (me.equals(dto.getPeerId())) {
            throw new IllegalArgumentException("不能给自己发消息");
        }
        if (StrUtil.isBlank(dto.getContent())) {
            throw new IllegalArgumentException("content 不能为空");
        }

        Long convId = getOrCreateConversationId(me, dto.getPeerId());

        ChatMessage msg = new ChatMessage()
                .setConversationId(convId)
                .setSenderId(me)
                .setReceiverId(dto.getPeerId())
                .setContent(dto.getContent().trim());

        messageMapper.insert(msg);

        ChatSendVO vo = new ChatSendVO();
        vo.setMessageId(msg.getId());
        vo.setSendTime(msg.getSendTime() == null ? LocalDateTime.now() : msg.getSendTime());
        return vo;
    }

    @Override
    public ChatPullVO pull(ChatPullDTO dto) {
        Long me = LoginUtils.getLoginUserId();
        if (dto == null || dto.getPeerId() == null) {
            throw new IllegalArgumentException("peerId 不能为空");
        }
        long afterId = dto.getAfterId() == null ? 0L : dto.getAfterId();
        int limit = dto.getLimit() == null ? 50 : Math.min(dto.getLimit(), 200);

        Long convId = getOrCreateConversationId(me, dto.getPeerId());

        List<ChatMessage> list = messageMapper.selectList(
                Wrappers.<ChatMessage>lambdaQuery()
                        .eq(ChatMessage::getConversationId, convId)
                        .gt(ChatMessage::getId, afterId)
                        .orderByAsc(ChatMessage::getId)
                        .last("limit " + limit)
        );

        List<ChatMessageVO> records = listToVo(list);

        ChatPullVO vo = new ChatPullVO();
        vo.setRecords(records);
        vo.setNextAfterId(CollUtil.isEmpty(records) ? afterId : records.get(records.size() - 1).getId());
        return vo;
    }

    @Override
    public List<ChatMessageVO> history(ChatHistoryDTO dto) {
        Long me = LoginUtils.getLoginUserId();
        if (dto == null || dto.getPeerId() == null) {
            throw new IllegalArgumentException("peerId 不能为空");
        }
        long beforeId = dto.getBeforeId() == null ? Long.MAX_VALUE : dto.getBeforeId();
        int limit = dto.getLimit() == null ? 20 : Math.min(dto.getLimit(), 200);

        Long convId = getOrCreateConversationId(me, dto.getPeerId());

        // 取 message_id < beforeId 的最近 N 条（倒序取，再正序返回，方便前端直接展示）
        List<ChatMessage> list = messageMapper.selectList(
                Wrappers.<ChatMessage>lambdaQuery()
                        .eq(ChatMessage::getConversationId, convId)
                        .lt(ChatMessage::getId, beforeId)
                        .orderByDesc(ChatMessage::getId)
                        .last("limit " + limit)
        );

        list.sort(Comparator.comparing(ChatMessage::getId));
        return listToVo(list);
    }

    /**
     * 获取或创建两人会话ID
     * 约定：userA 永远是 min(userId)，userB 永远是 max(userId)
     */
    private Long getOrCreateConversationId(Long me, Long peer) {
        if (me == null) {
            throw new IllegalStateException("用户未登录");
        }
        if (peer == null) {
            throw new IllegalArgumentException("peerId 不能为空");
        }
        long a = Math.min(me, peer);
        long b = Math.max(me, peer);

        ChatConversation conv = conversationMapper.selectOne(
                Wrappers.<ChatConversation>lambdaQuery()
                        .eq(ChatConversation::getUserA, a)
                        .eq(ChatConversation::getUserB, b)
                        .last("limit 1")
        );

        if (conv != null) {
            return conv.getId();
        }

        // 并发情况下可能同时插入，依赖数据库 unique key 兜底
        try {
            ChatConversation insert = new ChatConversation()
                    .setUserA(a)
                    .setUserB(b);
            conversationMapper.insert(insert);
            return insert.getId();
        } catch (DuplicateKeyException ignore) {
            // 插入冲突：再查一次
            ChatConversation again = conversationMapper.selectOne(
                    Wrappers.<ChatConversation>lambdaQuery()
                            .eq(ChatConversation::getUserA, a)
                            .eq(ChatConversation::getUserB, b)
                            .last("limit 1")
            );
            if (again == null) {
                throw new IllegalStateException("创建会话失败，请重试");
            }
            return again.getId();
        }
    }

    private List<ChatMessageVO> listToVo(List<ChatMessage> list) {
        if (CollUtil.isEmpty(list)) {
            return List.of();
        }
        return list.stream().map(this::toVo).toList();
    }

    private ChatMessageVO toVo(ChatMessage m) {
        ChatMessageVO vo = new ChatMessageVO();
        vo.setId(m.getId());
        vo.setConversationId(m.getConversationId());
        vo.setSenderId(m.getSenderId());
        vo.setReceiverId(m.getReceiverId());
        vo.setContent(m.getContent());
        vo.setSendTime(m.getSendTime());
        return vo;
    }

    @Override
    public List<ChatConversationVO> conversations() {
        Long me = LoginUtils.getLoginUserId();

        // 查出所有包含我的会话
        List<ChatConversation> convs = conversationMapper.selectList(
                Wrappers.<ChatConversation>lambdaQuery()
                        .and(w -> w.eq(ChatConversation::getUserA, me).or().eq(ChatConversation::getUserB, me))
                        .orderByDesc(ChatConversation::getCreateTime)
        );

        if (CollUtil.isEmpty(convs)) {
            return List.of();
        }

        // 先拿 peerId 列表，批量取名字（你项目已有此工具）
        Set<Long> peerIds = new HashSet<>();
        for (ChatConversation c : convs) {
            long peerId = Objects.equals(c.getUserA(), me) ? c.getUserB() : c.getUserA();
            peerIds.add(peerId);
        }
        Map<Long, String> nameMap = CollUtil.isEmpty(peerIds)
                ? Collections.emptyMap()
                : LoginUtils.getUserNameMap(new ArrayList<>(peerIds));

        // 逐个会话补 lastMessage + unreadCount（演示级 N+1 可接受）
        List<ChatConversationVO> result = new ArrayList<>();
        for (ChatConversation c : convs) {
            long peerId = Objects.equals(c.getUserA(), me) ? c.getUserB() : c.getUserA();

            // 最新一条消息
            ChatMessage last = messageMapper.selectOne(
                    Wrappers.<ChatMessage>lambdaQuery()
                            .eq(ChatMessage::getConversationId, c.getId())
                            .orderByDesc(ChatMessage::getId)
                            .last("limit 1")
            );

            // 未读数：receiver = me 且 read_time is null
            Long unread = messageMapper.selectCount(
                    Wrappers.<ChatMessage>lambdaQuery()
                            .eq(ChatMessage::getConversationId, c.getId())
                            .eq(ChatMessage::getReceiverId, me)
                            .isNull(ChatMessage::getReadTime)
            );

            ChatConversationVO vo = new ChatConversationVO();
            vo.setPeerId(peerId);
            vo.setPeerName(nameMap.getOrDefault(peerId, ""));
            vo.setPeerAvatar(""); // 你后续接入用户头像字段再补

            if (last != null) {
                vo.setLastMessageId(last.getId());
                vo.setLastContent(last.getContent());
                vo.setLastSendTime(last.getSendTime());
            } else {
                vo.setLastMessageId(null);
                vo.setLastContent("");
                vo.setLastSendTime(null);
            }

            vo.setUnreadCount(unread == null ? 0 : unread.intValue());
            result.add(vo);
        }

        // 前端一般希望：按最后消息时间倒序
        result.sort((a, b) -> {
            LocalDateTime ta = a.getLastSendTime();
            LocalDateTime tb = b.getLastSendTime();
            if (ta == null && tb == null) return 0;
            if (ta == null) return 1;
            if (tb == null) return -1;
            return tb.compareTo(ta);
        });

        return result;
    }

    // =========================
    // 新增接口 2：总未读数
    // =========================
    @Override
    public ChatUnreadCountVO unreadCount() {
        Long me = LoginUtils.getLoginUserId();

        Long cnt = messageMapper.selectCount(
                Wrappers.<ChatMessage>lambdaQuery()
                        .eq(ChatMessage::getReceiverId, me)
                        .isNull(ChatMessage::getReadTime)
        );

        ChatUnreadCountVO vo = new ChatUnreadCountVO();
        vo.setTotalUnread(cnt == null ? 0 : cnt.intValue());
        return vo;
    }

    // =========================
    // 新增接口 3：标记已读
    // =========================
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChatMarkReadVO markRead(ChatMarkReadDTO dto) {
        Long me = LoginUtils.getLoginUserId();
        if (dto == null || dto.getPeerId() == null) {
            throw new IllegalArgumentException("peerId 不能为空");
        }
        if (me.equals(dto.getPeerId())) {
            throw new IllegalArgumentException("peerId 不能是自己");
        }

        Long convId = getOrCreateConversationId(me, dto.getPeerId());

        LambdaUpdateWrapper<ChatMessage> uw = Wrappers.<ChatMessage>lambdaUpdate()
                .eq(ChatMessage::getConversationId, convId)
                .eq(ChatMessage::getReceiverId, me)
                .isNull(ChatMessage::getReadTime)
                .set(ChatMessage::getReadTime, LocalDateTime.now());

        if (dto.getUpToMessageId() != null) {
            uw.le(ChatMessage::getId, dto.getUpToMessageId());
        }

        int updated = messageMapper.update(null, uw);

        ChatMarkReadVO vo = new ChatMarkReadVO();
        vo.setUpdated(updated);
        return vo;
    }
}

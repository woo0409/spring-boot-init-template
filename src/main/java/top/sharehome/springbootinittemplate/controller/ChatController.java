package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.model.dto.ChatHistoryDTO;
import top.sharehome.springbootinittemplate.model.dto.ChatMarkReadDTO;
import top.sharehome.springbootinittemplate.model.dto.ChatPullDTO;
import top.sharehome.springbootinittemplate.model.dto.ChatSendDTO;
import top.sharehome.springbootinittemplate.model.vo.*;
import top.sharehome.springbootinittemplate.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@SaCheckLogin
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 发送消息
     */
    @PostMapping("/send")
    public R<ChatSendVO> send(@RequestBody ChatSendDTO dto) {
        return R.ok(chatService.send(dto));
    }

    /**
     * 轮询拉取新消息（增量）
     * afterId：客户端最后一条消息ID，没有就传0
     */
    @PostMapping("/pull")
    public R<ChatPullVO> pull(@RequestBody ChatPullDTO dto) {
        return R.ok(chatService.pull(dto));
    }

    /**
     * 拉取历史消息（向上翻页）
     * beforeId：取 message_id < beforeId 的最近N条；首次传 Long.MAX_VALUE 或不传
     */
    @PostMapping("/history")
    public R<List<ChatMessageVO>> history(@RequestBody ChatHistoryDTO dto) {
        return R.ok(chatService.history(dto));
    }

    /**
     * 会话列表（左侧列表用）
     */
    @PostMapping("/conversations")
    public R<List<ChatConversationVO>> conversations() {
        return R.ok(chatService.conversations());
    }

    /**
     * 总未读数（顶部 badge 用）
     */
    @PostMapping("/unreadCount")
    public R<ChatUnreadCountVO> unreadCount() {
        return R.ok(chatService.unreadCount());
    }

    /**
     * 标记已读（进入会话/切换会话时调用，可选但推荐）
     */
    @PostMapping("/markRead")
    public R<ChatMarkReadVO> markRead(@RequestBody ChatMarkReadDTO dto) {
        return R.ok(chatService.markRead(dto));

    }
}

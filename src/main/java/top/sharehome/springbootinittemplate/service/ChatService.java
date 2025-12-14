package top.sharehome.springbootinittemplate.service;

import top.sharehome.springbootinittemplate.model.dto.ChatHistoryDTO;
import top.sharehome.springbootinittemplate.model.dto.ChatMarkReadDTO;
import top.sharehome.springbootinittemplate.model.dto.ChatPullDTO;
import top.sharehome.springbootinittemplate.model.dto.ChatSendDTO;
import top.sharehome.springbootinittemplate.model.vo.*;

import java.util.List;

public interface ChatService {

    ChatSendVO send(ChatSendDTO dto);

    ChatPullVO pull(ChatPullDTO dto);

    List<ChatMessageVO> history(ChatHistoryDTO dto);

    /**
     * 会话列表（左侧列表用）
     */
    List<ChatConversationVO> conversations();

    /**
     * 总未读数（顶部 badge 用）
     */
    ChatUnreadCountVO unreadCount();

    /**
     * 标记已读（可选，但推荐）
     */
    ChatMarkReadVO markRead(ChatMarkReadDTO dto);
}

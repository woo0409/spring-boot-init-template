package top.sharehome.springbootinittemplate.model.dto;

import lombok.Data;

@Data
public class ChatMarkReadDTO {

    /**
     * 对方用户ID
     */
    private Long peerId;

    /**
     * 将 <= 该消息ID 的消息标记已读（可不传，不传则标记该会话全部未读）
     */
    private Long upToMessageId;
}

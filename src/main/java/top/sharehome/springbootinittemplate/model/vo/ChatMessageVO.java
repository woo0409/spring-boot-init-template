package top.sharehome.springbootinittemplate.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageVO {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime sendTime;
}

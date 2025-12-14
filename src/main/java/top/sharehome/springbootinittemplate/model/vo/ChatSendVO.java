package top.sharehome.springbootinittemplate.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatSendVO {
    private Long messageId;
    private LocalDateTime sendTime;
}

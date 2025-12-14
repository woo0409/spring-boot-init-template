package top.sharehome.springbootinittemplate.model.dto;

import lombok.Data;

@Data
public class ChatSendDTO {
    private Long peerId;
    private String content;
}

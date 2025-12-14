package top.sharehome.springbootinittemplate.model.dto;

import lombok.Data;

@Data
public class ChatPullDTO {
    private Long peerId;
    private Long afterId;   // 客户端最后一条消息ID，没有就传 0
    private Integer limit;  // 默认 50
}

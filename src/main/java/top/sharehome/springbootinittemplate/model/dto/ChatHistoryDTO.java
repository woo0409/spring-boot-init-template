package top.sharehome.springbootinittemplate.model.dto;

import lombok.Data;

@Data
public class ChatHistoryDTO {
    private Long peerId;
    private Long beforeId;  // 向上翻页：取 message_id < beforeId；首次可传 Long.MAX_VALUE
    private Integer limit;  // 默认 20
}

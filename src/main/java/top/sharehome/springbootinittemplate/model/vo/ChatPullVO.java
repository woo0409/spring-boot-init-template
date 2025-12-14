package top.sharehome.springbootinittemplate.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChatPullVO {
    private List<ChatMessageVO> records;
    private Long nextAfterId;
}

package top.sharehome.springbootinittemplate.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatConversationVO {

    private Long peerId;
    private String peerName;
    private String peerAvatar;

    private Long lastMessageId;
    private String lastContent;
    private LocalDateTime lastSendTime;

    private Integer unreadCount;
}

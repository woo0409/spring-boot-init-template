package top.sharehome.springbootinittemplate.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天消息实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "chat_message")
@Accessors(chain = true)
public class ChatMessage implements Serializable {

    /**
     * 消息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会话ID
     */
    private Long conversationId;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime sendTime;

    /**
     * 已读时间（null 表示未读）
     */
    private LocalDateTime readTime;

    @Serial
    private static final long serialVersionUID = 1L;
}

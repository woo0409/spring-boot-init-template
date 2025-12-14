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
 * 聊天会话实体
 *
 * 一条记录表示两个用户之间的一个会话
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "chat_conversation")
@Accessors(chain = true)
public class ChatConversation implements Serializable {

    /**
     * 会话ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户A（较小的 userId）
     */
    private Long userA;

    /**
     * 用户B（较大的 userId）
     */
    private Long userB;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Serial
    private static final long serialVersionUID = 1L;
}

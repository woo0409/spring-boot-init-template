package top.sharehome.springbootinittemplate.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 积分兑换服务记录
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Getter
@Setter
@TableName("integral_services")
public class IntegralServicesDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 兑换人
     */
    private Long userId;

    /**
     * 兑换服务
     */
    private Long serviceId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 兑换状态 1-审核中 2-审核通过 3-审核失败
     */
    private Integer status;

    /**
     * 审核人
     */
    private Long auditId;
}

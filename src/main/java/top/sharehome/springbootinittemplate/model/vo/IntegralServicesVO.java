package top.sharehome.springbootinittemplate.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class IntegralServicesVO {
    private Long id;

    /**
     * 兑换人
     */
    private Long userId;

    /**
     * 兑换人积分
     */
    private Double userIntegral;

    /**
     * 兑换服务
     */
    private Long serviceId;

    /**
     * 兑换服务名称
     */
    private String serviceName;

    /**
     * 兑换状态 1-审核中 2-审核通过 3-审核失败
     */
    private Integer status;

    /**
     * 审核人
     */
    private Long auditId;

    /**
     * 花费积分
     */
    private Double integral;

    /**
     * 评论
     */
    private String comment;
}

package top.sharehome.springbootinittemplate.model.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import top.sharehome.springbootinittemplate.model.common.PageModel;

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
public class IntegralServicesDTO extends PageModel {
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
     * 兑换名称
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

    /**
     * 是否仅自己可见
     */
    private Boolean selfOnly;
}

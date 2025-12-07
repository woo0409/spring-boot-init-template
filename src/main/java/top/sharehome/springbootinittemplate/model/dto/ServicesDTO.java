package top.sharehome.springbootinittemplate.model.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import top.sharehome.springbootinittemplate.model.common.PageModel;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 可兑换的服务
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Getter
@Setter
public class ServicesDTO extends PageModel {
    private Long id;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务描述
     */
    private String serviceDetail;

    /**
     * 所需积分
     */
    private Double serviceIntegral;

    /**
     * 服务地区
     */
    private String serviceRegion;

    /**
     * 总量
     */
    private Integer total;

    /**
     * 剩余
     */
    private Integer remaining;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

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
     * 服务开始时间
     */
    private LocalDateTime startTime;

    /**
     * 服务结束时间
     */
    private LocalDateTime endTime;

    /**
     * 是否仅自己可见
     */
    private Boolean selfOnly;
}

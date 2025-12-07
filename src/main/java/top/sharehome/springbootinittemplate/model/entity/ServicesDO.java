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
 * 可兑换的服务
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Getter
@Setter
@TableName("services")
public class ServicesDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
     * 文件id
     */
    private Long fileId;

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
}

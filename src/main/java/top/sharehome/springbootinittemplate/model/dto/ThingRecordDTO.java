package top.sharehome.springbootinittemplate.model.dto;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 好事记录表
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Getter
@Setter
public class ThingRecordDTO {
    private Long id;

    /**
     * 好事名称
     */
    private String thingName;

    /**
     * 好事类型
     */
    private String thingType;

    /**
     * 好事类型Id
     */
    private Long thingTypeId;


    /**
     * 好事详情
     */
    private String thingDetail;

    /**
     * 状态
     */
    private Long status;

    /**
     * 做好事用户
     */
    private Long userId;

    /**
     * 审核员
     */
    private Long aduitUser;

    /**
     * 佐证材料
     */
    private String supportingMaterials;

    /**
     * 所获得积分
     */
    private Double earnIntegra;

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
     * 发生时间
     */
    private LocalDateTime happenTime;
}

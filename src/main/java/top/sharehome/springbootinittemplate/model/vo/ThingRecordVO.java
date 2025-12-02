package top.sharehome.springbootinittemplate.model.vo;

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
public class ThingRecordVO {
    private Long id;

    /**
     * 好事名称
     */
    private String thingName;

    /**
     * 好事类型
     */
    private Long thingType;

    /**
     * 好事详情
     */
    private String thingDetail;

    /**
     * 状态
     */
    private Integer status;

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
    private Long supportingMaterials;

    /**
     * 材料图片url
     */
    private String thumb;

    /**
     * 所获得积分
     */
    private Double earnIntegra;

    /**
     * 发生时间
     */
    private LocalDateTime happenTime;
}

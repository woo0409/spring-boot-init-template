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
 * 好事记录表
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Getter
@Setter
@TableName("thing_record")
public class ThingRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
    private Object earnIntegra;

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

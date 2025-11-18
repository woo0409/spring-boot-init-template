package top.sharehome.springbootinittemplate.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import top.sharehome.springbootinittemplate.model.entity.ThingTypeDO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 好事类型表
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Getter
@Setter
public class ThingTypeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 好事类型名
     */
    private String typeName;

    /**
     * 对应积分
     */
    private Double integral;

    /**
     * 子类型
     */
    private List<ThingTypeVO> childrenList;
}

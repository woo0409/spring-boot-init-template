package top.sharehome.springbootinittemplate.model.vo.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 类型分布Vo类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TypeDistributionVo implements Serializable {

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 好事次数
     */
    private Long count;

    /**
     * 占比
     */
    private Double percentage;

    @Serial
    private static final long serialVersionUID = 1L;
}

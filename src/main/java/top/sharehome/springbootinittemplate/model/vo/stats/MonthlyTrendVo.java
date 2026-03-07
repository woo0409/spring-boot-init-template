package top.sharehome.springbootinittemplate.model.vo.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 月度趋势Vo类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MonthlyTrendVo implements Serializable {

    /**
     * 月份（YYYY-MM）
     */
    private String month;

    /**
     * 好事次数
     */
    private Long count;

    /**
     * 获得积分
     */
    private Double integral;

    @Serial
    private static final long serialVersionUID = 1L;
}

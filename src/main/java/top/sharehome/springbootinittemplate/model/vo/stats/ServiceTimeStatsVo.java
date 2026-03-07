package top.sharehome.springbootinittemplate.model.vo.stats;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户服务统计Vo类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ServiceTimeStatsVo implements Serializable {

    /**
     * 总好事次数
     */
    private Long totalThings;

    /**
     * 总获得积分
     */
    private Double totalIntegral;

    /**
     * 参与月数
     */
    private Integer totalMonths;

    /**
     * 平均每月好事数
     */
    private Double avgPerMonth;

    /**
     * 首次好事时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime firstTime;

    /**
     * 最后一次好事时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTime;

    @Serial
    private static final long serialVersionUID = 1L;
}

package top.sharehome.springbootinittemplate.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户积分统计Vo类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserIntegralStatisticsVo implements Serializable {

    /**
     * 总用户数
     */
    private Long totalUsers;

    /**
     * 总积分
     */
    private Double totalIntegral;

    /**
     * 平均积分
     */
    private Double averageIntegral;

    /**
     * 最高积分
     */
    private Double maxIntegral;

    /**
     * 积分排行榜（前10名）
     */
    private java.util.List<IntegralRankItem> rankList;

    /**
     * 积分排行榜项
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public static class IntegralRankItem implements Serializable {

        /**
         * 用户ID
         */
        private Long userId;

        /**
         * 用户账号
         */
        private String account;

        /**
         * 用户昵称
         */
        private String name;

        /**
         * 用户头像
         */
        private String avatar;

        /**
         * 积分
         */
        private Double integral;

        /**
         * 排名
         */
        private Integer rank;

        @Serial
        private static final long serialVersionUID = 1L;
    }

    @Serial
    private static final long serialVersionUID = 1L;
}

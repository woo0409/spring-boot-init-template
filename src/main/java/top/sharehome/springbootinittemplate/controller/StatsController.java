package top.sharehome.springbootinittemplate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.config.log.annotation.ControllerLog;
import top.sharehome.springbootinittemplate.config.log.enums.Operator;
import top.sharehome.springbootinittemplate.model.entity.ThingRecordDO;
import top.sharehome.springbootinittemplate.model.entity.ThingTypeDO;
import top.sharehome.springbootinittemplate.model.vo.stats.MonthlyTrendVo;
import top.sharehome.springbootinittemplate.model.vo.stats.ServiceTimeStatsVo;
import top.sharehome.springbootinittemplate.model.vo.stats.TypeDistributionVo;
import top.sharehome.springbootinittemplate.service.ThingRecordService;
import top.sharehome.springbootinittemplate.service.ThingTypeService;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统计控制器
 *
 * @author AntonyCheng
 */
@RestController
@RequestMapping("/stats")
@SaCheckLogin
public class StatsController {

    @Resource
    private ThingRecordService thingRecordService;

    @Resource
    private ThingTypeService thingTypeService;

    /**
     * 获取用户服务统计
     *
     * @return 服务统计信息
     */
    @GetMapping("/user/service-time")
    @ControllerLog(description = "获取用户服务统计", operator = Operator.QUERY)
    public R<ServiceTimeStatsVo> getUserServiceTimeStats() {
        Long userId = LoginUtils.getLoginUserId();

        // 查询该用户的所有好事记录（已审核通过的）
        LambdaQueryWrapper<ThingRecordDO> queryWrapper = Wrappers.lambdaQuery(ThingRecordDO.class)
                .eq(ThingRecordDO::getUserId, userId)
                .eq(ThingRecordDO::getStatus, 3)
                .orderByAsc(ThingRecordDO::getHappenTime);

        List<ThingRecordDO> records = thingRecordService.list(queryWrapper);

        if (records == null || records.isEmpty()) {
            ServiceTimeStatsVo stats = new ServiceTimeStatsVo();
            stats.setTotalThings(0L);
            stats.setTotalIntegral(0.0);
            stats.setTotalMonths(0);
            stats.setAvgPerMonth(0.0);
            return R.ok(stats);
        }

        // 计算统计信息
        Long totalThings = (long) records.size();
        Double totalIntegral = records.stream()
                .mapToDouble(record -> record.getEarnIntegra() != null ? record.getEarnIntegra() : 0.0)
                .sum();

        LocalDateTime firstTime = records.get(0).getHappenTime();
        LocalDateTime lastTime = records.get(records.size() - 1).getHappenTime();

        // 计算参与月数
        Set<String> months = records.stream()
                .map(record -> {
                    LocalDateTime happenTime = record.getHappenTime();
                    if (happenTime == null) return null;
                    return happenTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Integer totalMonths = months.size();

        // 计算平均每月好事数
        Double avgPerMonth = totalMonths > 0 ? (double) totalThings / totalMonths : 0.0;

        ServiceTimeStatsVo stats = new ServiceTimeStatsVo();
        stats.setTotalThings(totalThings);
        stats.setTotalIntegral(totalIntegral);
        stats.setTotalMonths(totalMonths);
        stats.setAvgPerMonth(avgPerMonth);
        stats.setFirstTime(firstTime);
        stats.setLastTime(lastTime);

        return R.ok(stats);
    }

    /**
     * 获取月度趋势数据
     *
     * @return 月度趋势列表
     */
    @GetMapping("/user/monthly-trend")
    @ControllerLog(description = "获取用户月度趋势", operator = Operator.QUERY)
    public R<List<MonthlyTrendVo>> getMonthlyTrend() {
        Long userId = LoginUtils.getLoginUserId();

        // 查询该用户的所有好事记录（已审核通过的）
        LambdaQueryWrapper<ThingRecordDO> queryWrapper = Wrappers.lambdaQuery(ThingRecordDO.class)
                .eq(ThingRecordDO::getUserId, userId)
                .eq(ThingRecordDO::getStatus, 3)
                .orderByAsc(ThingRecordDO::getHappenTime);

        List<ThingRecordDO> records = thingRecordService.list(queryWrapper);

        if (records == null || records.isEmpty()) {
            return R.ok(new ArrayList<>());
        }

        // 按月分组统计
        Map<String, List<ThingRecordDO>> groupedByMonth = records.stream()
                .filter(record -> record.getHappenTime() != null)
                .collect(Collectors.groupingBy(
                        record -> record.getHappenTime().format(DateTimeFormatter.ofPattern("yyyy-MM"))
                ));

        // 转换为Vo列表
        List<MonthlyTrendVo> trendList = new ArrayList<>();
        for (Map.Entry<String, List<ThingRecordDO>> entry : groupedByMonth.entrySet()) {
            String month = entry.getKey();
            List<ThingRecordDO> monthRecords = entry.getValue();

            Long count = (long) monthRecords.size();
            Double integral = monthRecords.stream()
                    .mapToDouble(record -> record.getEarnIntegra() != null ? record.getEarnIntegra() : 0.0)
                    .sum();

            MonthlyTrendVo vo = new MonthlyTrendVo();
            vo.setMonth(month);
            vo.setCount(count);
            vo.setIntegral(integral);

            trendList.add(vo);
        }

        return R.ok(trendList);
    }

    /**
     * 获取类型分布数据
     *
     * @return 类型分布列表
     */
    @GetMapping("/user/type-distribution")
    @ControllerLog(description = "获取用户类型分布", operator = Operator.QUERY)
    public R<List<TypeDistributionVo>> getTypeDistribution() {
        Long userId = LoginUtils.getLoginUserId();

        // 查询该用户的所有好事记录（已审核通过的）
        LambdaQueryWrapper<ThingRecordDO> queryWrapper = Wrappers.lambdaQuery(ThingRecordDO.class)
                .eq(ThingRecordDO::getUserId, userId)
                .eq(ThingRecordDO::getStatus, 3)
                .orderByDesc(ThingRecordDO::getHappenTime);

        List<ThingRecordDO> records = thingRecordService.list(queryWrapper);

        if (records == null || records.isEmpty()) {
            return R.ok(new ArrayList<>());
        }

        // 查询所有类型信息
        List<ThingTypeDO> allTypes = thingTypeService.list();
        Map<Long, String> typeMap = allTypes.stream()
                .collect(Collectors.toMap(ThingTypeDO::getId, ThingTypeDO::getTypeName));

        // 按类型分组统计
        Map<Long, List<ThingRecordDO>> groupedByType = records.stream()
                .filter(record -> record.getThingType() != null)
                .collect(Collectors.groupingBy(ThingRecordDO::getThingType));

        Long totalCount = (long) records.size();

        // 转换为Vo列表
        List<TypeDistributionVo> distributionList = new ArrayList<>();
        for (Map.Entry<Long, List<ThingRecordDO>> entry : groupedByType.entrySet()) {
            Long typeId = entry.getKey();
            List<ThingRecordDO> typeRecords = entry.getValue();

            Long count = (long) typeRecords.size();
            Double percentage = totalCount > 0 ? (count * 100.0 / totalCount) : 0.0;

            String typeName = typeMap.getOrDefault(typeId, "未知类型");

            TypeDistributionVo vo = new TypeDistributionVo();
            vo.setTypeName(typeName);
            vo.setCount(count);
            vo.setPercentage(percentage);

            distributionList.add(vo);
        }

        return R.ok(distributionList);
    }
}

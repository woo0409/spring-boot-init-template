package top.sharehome.springbootinittemplate.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;
import top.sharehome.springbootinittemplate.model.dto.ThingDTO;
import top.sharehome.springbootinittemplate.model.entity.ThingRecordDO;
import top.sharehome.springbootinittemplate.mapper.ThingRecordMapper;
import top.sharehome.springbootinittemplate.model.entity.ThingTypeDO;
import top.sharehome.springbootinittemplate.model.entity.User;
import top.sharehome.springbootinittemplate.service.ThingRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.sharehome.springbootinittemplate.service.ThingTypeService;
import top.sharehome.springbootinittemplate.service.UserService;

import java.util.Arrays;
import java.util.Optional;

/**
 * <p>
 * 好事记录表 服务实现类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Service
public class ThingRecordServiceImpl extends ServiceImpl<ThingRecordMapper, ThingRecordDO> implements ThingRecordService {
    @Autowired
    private UserService userService;
    @Autowired
    private ThingTypeService thingTypeService;


    @Override
    public Boolean addRecord(ThingRecordDO recordDO) {
        Long currentUserId = getCurrentUserId();
        recordDO.setUserId(currentUserId);
        ThingTypeDO thingTypeDO = Optional.ofNullable(thingTypeService.getById(recordDO.getThingType()))
                .orElse(new ThingTypeDO());
        recordDO.setEarnIntegra(thingTypeDO.getIntegral());
        return this.save(recordDO);
    }

    @Override
    public Page<ThingRecordDO> page(ThingDTO thingDTO) {
        // 创建分页对象，从thingDTO获取分页参数
        Page<ThingRecordDO> page = new Page<>(thingDTO.getPage(), thingDTO.getSize());
        // 执行分页查询
        return this.page(page, new LambdaQueryWrapper<>(ThingRecordDO.class)
                .like(StrUtil.isNotBlank(thingDTO.getThingName()), ThingRecordDO::getThingName, thingDTO.getThingName())
                .eq(ObjUtil.isNotEmpty(thingDTO.getTypeId()), ThingRecordDO::getThingType, thingDTO.getTypeId())
                .eq(ObjUtil.isNotEmpty(thingDTO.getStatus()), ThingRecordDO::getStatus, thingDTO.getStatus())
                .eq(ObjUtil.isNotEmpty(thingDTO.getUserId()), ThingRecordDO::getUserId, thingDTO.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean thingAudit(Integer status, Long thingId) {
        // 1. 使用更具体的异常和校验
        ThingRecordDO record = this.getById(thingId);
        if (record == null) {
            throw new RuntimeException("记录不存在，thingId: " + thingId);
        }

        // 2. 状态校验
        if (!isValidStatus(status)) {
            throw new RuntimeException("无效的状态值: " + status);
        }

        record.setStatus(status);

        // 3. 状态为3时的积分处理抽离为独立方法
        if (status == 3) { // 使用常量代替魔法数字
            addIntegralToUser(record);
        }

        return this.updateById(record);
    }

    /**
     * 给用户增加积分
     */
    private void addIntegralToUser(ThingRecordDO record) {
        try {
            User user = userService.getById(record.getUserId());

            if (user == null) {
                throw new RuntimeException("用户不存在");
            }

            // 4. 使用更安全的数据更新方式
            boolean success = userService.lambdaUpdate()
                    .set(User::getIntegral, user.getIntegral() + record.getEarnIntegra())
                    .eq(User::getId, record.getUserId())
                    .update();

            if (!success) {
                throw new RuntimeException("用户积分更新失败");
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("积分处理失败", e);
        }
    }

    /**
     * 获取当前用户ID - 统一处理类型转换
     */
    private Long getCurrentUserId() {
        Object loginId = StpUtil.getLoginId();

        if (loginId instanceof Long) {
            return (Long) loginId;
        } else if (loginId instanceof String) {
            try {
                return Long.valueOf((String) loginId);
            } catch (NumberFormatException e) {
                throw new RuntimeException("用户ID格式错误: " + loginId);
            }
        } else if (loginId instanceof Integer) {
            return ((Integer) loginId).longValue();
        } else {
            throw new RuntimeException("不支持的登录ID类型: " + loginId.getClass().getSimpleName());
        }
    }

    /**
     * 状态校验
     */
    private boolean isValidStatus(Integer status) {
        return status != null && Arrays.asList(1, 2, 3).contains(status);
    }


}

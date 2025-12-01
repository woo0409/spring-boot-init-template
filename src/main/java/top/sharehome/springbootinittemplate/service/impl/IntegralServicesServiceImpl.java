package top.sharehome.springbootinittemplate.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.sharehome.springbootinittemplate.model.entity.IntegralServicesDO;
import top.sharehome.springbootinittemplate.mapper.IntegralServicesMapper;
import top.sharehome.springbootinittemplate.model.entity.ServicesDO;
import top.sharehome.springbootinittemplate.model.entity.User;
import top.sharehome.springbootinittemplate.model.enums.StatusEnum;
import top.sharehome.springbootinittemplate.model.vo.auth.AuthLoginVo;
import top.sharehome.springbootinittemplate.service.IntegralServicesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.sharehome.springbootinittemplate.service.ServicesService;
import top.sharehome.springbootinittemplate.service.UserService;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import java.util.Optional;

/**
 * <p>
 * 积分兑换服务记录 服务实现类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Service
public class IntegralServicesServiceImpl extends ServiceImpl<IntegralServicesMapper, IntegralServicesDO> implements IntegralServicesService {
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean exchange(Long serviceId) {
        ServicesDO servicesDO = Optional.ofNullable(servicesService.getById(serviceId))
                .orElseThrow(() -> new RuntimeException("服务不存在"));

        AuthLoginVo user = LoginUtils.getLoginUserOrThrow();
        if (user.getIntegral() < servicesDO.getServiceIntegral()) {
            throw new RuntimeException("无法兑换该服务");
        }

        IntegralServicesDO integralServicesDO = new IntegralServicesDO();
        integralServicesDO.setServiceId(serviceId);
        integralServicesDO.setUserId(user.getId());
        integralServicesDO.setIntegral(servicesDO.getServiceIntegral());

        return this.save(integralServicesDO) & userService.update(Wrappers.<User>lambdaUpdate()
                .set(User::getIntegral, user.getIntegral() - servicesDO.getServiceIntegral())
                .eq(User::getId, user.getId()));
    }

    @Override
    public Boolean serviceAudit(Integer status, Long serviceId) {
        Long userId = LoginUtils.getLoginUserId();
        User user = userService.getById(userId);

        IntegralServicesDO integralServicesDO = this.getById(serviceId);
        integralServicesDO.setAuditId(userId);
        integralServicesDO.setStatus(status);

        if (status.equals(StatusEnum.AUDIT_FAIL.getCode())) {
            userService.update(Wrappers.<User>lambdaUpdate()
                    .set(User::getIntegral, user.getIntegral() + integralServicesDO.getIntegral())
                    .eq(User::getId, user.getId()));
        }
        return this.updateById(integralServicesDO);
    }
}

package top.sharehome.springbootinittemplate.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.sharehome.springbootinittemplate.convert.ServiceConvert;
import top.sharehome.springbootinittemplate.model.dto.CommentDTO;
import top.sharehome.springbootinittemplate.model.dto.IntegralServicesDTO;
import top.sharehome.springbootinittemplate.model.entity.IntegralServicesDO;
import top.sharehome.springbootinittemplate.mapper.IntegralServicesMapper;
import top.sharehome.springbootinittemplate.model.entity.ServicesDO;
import top.sharehome.springbootinittemplate.model.entity.User;
import top.sharehome.springbootinittemplate.model.enums.StatusEnum;
import top.sharehome.springbootinittemplate.model.vo.IntegralServicesVO;
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
            throw new RuntimeException("积分不足，无法兑换该服务");
        }

        IntegralServicesDO integralServicesDO = new IntegralServicesDO();
        integralServicesDO.setServiceId(serviceId);
        integralServicesDO.setServiceName(servicesDO.getServiceName());
        integralServicesDO.setUserId(user.getId());
        integralServicesDO.setIntegral(servicesDO.getServiceIntegral());
        integralServicesDO.setStatus(StatusEnum.AUDIT_SUCCESS.getCode());

        servicesDO.setRemaining(servicesDO.getRemaining() - 1);

        return servicesService.updateById(servicesDO) & this.save(integralServicesDO) & userService.update(Wrappers.<User>lambdaUpdate()
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

    @Override
    public Page<IntegralServicesDO> getRecordPage(IntegralServicesDTO integralServicesDTO) {
        // 创建分页对象，从thingDTO获取分页参数
        Page<IntegralServicesDO> page = new Page<>(integralServicesDTO.getPage(), integralServicesDTO.getSize());

        // 执行分页查询
        Page<IntegralServicesDO> pageVo = this.page(page, new LambdaQueryWrapper<>(IntegralServicesDO.class)
                .like(ObjUtil.isNotEmpty(integralServicesDTO.getServiceName()), IntegralServicesDO::getServiceName, integralServicesDTO.getServiceName())
                .eq(ObjectUtil.isNotEmpty(integralServicesDTO.getStatus()), IntegralServicesDO::getStatus, integralServicesDTO.getStatus())
                .eq(ObjectUtil.isNotEmpty(integralServicesDTO.getSelfOnly()) && integralServicesDTO.getSelfOnly(), IntegralServicesDO::getUserId, LoginUtils.getLoginUserId()));

        return pageVo;
    }

    @Override
    public IntegralServicesVO record(Long id) {
        IntegralServicesDO integralServicesDO = this.getById(id);
        IntegralServicesVO integralServicesVO = ServiceConvert.INSTANCE.integralServicesDOToVO(integralServicesDO);
        Double integral = userService.getById(integralServicesDO.getUserId()).getIntegral();
        integralServicesVO.setUserIntegral(integral);
        return integralServicesVO;
    }

    @Override
    public Boolean comment(CommentDTO commentDTO) {
        IntegralServicesDO integralServicesDO = Optional.ofNullable(this.getById(commentDTO.getId()))
                .orElseThrow(() -> new RuntimeException("服务不存在"));

        integralServicesDO.setComment(commentDTO.getComment());
        integralServicesDO.setStatus(StatusEnum.COMPLETE.getCode());

        return this.updateById(integralServicesDO);
    }
}

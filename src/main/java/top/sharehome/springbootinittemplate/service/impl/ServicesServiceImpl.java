package top.sharehome.springbootinittemplate.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import top.sharehome.springbootinittemplate.convert.ServiceConvert;
import top.sharehome.springbootinittemplate.model.dto.ServicesDTO;
import top.sharehome.springbootinittemplate.model.entity.ServicesDO;
import top.sharehome.springbootinittemplate.mapper.ServicesMapper;
import top.sharehome.springbootinittemplate.model.entity.ThingRecordDO;
import top.sharehome.springbootinittemplate.model.entity.User;
import top.sharehome.springbootinittemplate.model.vo.auth.AuthLoginVo;
import top.sharehome.springbootinittemplate.service.ServicesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.sharehome.springbootinittemplate.service.UserService;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import java.util.Optional;

/**
 * <p>
 * 可兑换的服务 服务实现类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Service
public class ServicesServiceImpl extends ServiceImpl<ServicesMapper, ServicesDO> implements ServicesService {

    @Override
    public Boolean addOrUpdate(ServicesDTO servicesDTO) {
        return this.saveOrUpdate(ServiceConvert.INSTANCE.dtoToDO(servicesDTO));
    }

    @Override
    public Page<ServicesDO> getPage(ServicesDTO servicesDTO) {
        // 创建分页对象，从thingDTO获取分页参数
        Page<ServicesDO> page = new Page<>(servicesDTO.getPage(), servicesDTO.getSize());
        // 执行分页查询
        return this.page(page, new LambdaQueryWrapper<>(ServicesDO.class)
                .like(ObjUtil.isNotEmpty(servicesDTO.getServiceName()), ServicesDO::getServiceName, servicesDTO.getServiceName()));
    }
}

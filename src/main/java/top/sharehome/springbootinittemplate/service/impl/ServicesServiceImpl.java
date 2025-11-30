package top.sharehome.springbootinittemplate.service.impl;

import top.sharehome.springbootinittemplate.model.dto.ServicesDTO;
import top.sharehome.springbootinittemplate.model.entity.ServicesDO;
import top.sharehome.springbootinittemplate.mapper.ServicesMapper;
import top.sharehome.springbootinittemplate.service.ServicesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    public Boolean addOrUpdate(ServicesDTO servicesDO) {

        return null;
    }
}

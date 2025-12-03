package top.sharehome.springbootinittemplate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.sharehome.springbootinittemplate.model.dto.IntegralServicesDTO;
import top.sharehome.springbootinittemplate.model.dto.ServicesDTO;
import top.sharehome.springbootinittemplate.model.entity.IntegralServicesDO;
import top.sharehome.springbootinittemplate.model.entity.ServicesDO;
import top.sharehome.springbootinittemplate.model.vo.ServicesVO;

/**
 * <p>
 * 可兑换的服务 服务类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
public interface ServicesService extends IService<ServicesDO> {

    Boolean addOrUpdate(ServicesDTO servicesDO);

    Page<ServicesVO> getPage(ServicesDTO servicesDTO);
}

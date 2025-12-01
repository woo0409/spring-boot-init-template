package top.sharehome.springbootinittemplate.service;

import top.sharehome.springbootinittemplate.model.entity.IntegralServicesDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 积分兑换服务记录 服务类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
public interface IntegralServicesService extends IService<IntegralServicesDO> {

    Boolean exchange(Long serviceId);

    Boolean serviceAudit(Integer status, Long serviceId);
}

package top.sharehome.springbootinittemplate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.sharehome.springbootinittemplate.model.dto.CommentDTO;
import top.sharehome.springbootinittemplate.model.dto.IntegralServicesDTO;
import top.sharehome.springbootinittemplate.model.entity.IntegralServicesDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.sharehome.springbootinittemplate.model.vo.IntegralServicesVO;

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

    Page<IntegralServicesDO> getRecordPage(IntegralServicesDTO integralServicesDTO);

    IntegralServicesVO record(Long id);

    Boolean comment(CommentDTO commentDTO);
}

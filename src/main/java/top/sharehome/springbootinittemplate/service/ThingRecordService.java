package top.sharehome.springbootinittemplate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.sharehome.springbootinittemplate.model.dto.ThingDTO;
import top.sharehome.springbootinittemplate.model.entity.ThingRecordDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.sharehome.springbootinittemplate.model.vo.ThingRecordVO;

/**
 * <p>
 * 好事记录表 服务类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
public interface ThingRecordService extends IService<ThingRecordDO> {
    Boolean addRecord(ThingRecordDO recordDO);

    Page<ThingRecordVO> page(ThingDTO thingDTO);

    Boolean thingAudit(Integer status, Long thingId);

    Page<ThingRecordVO> getMyRecord(ThingDTO thingDTO);
}

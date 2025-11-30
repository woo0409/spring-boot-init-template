package top.sharehome.springbootinittemplate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.sharehome.springbootinittemplate.model.dto.ThingDTO;
import top.sharehome.springbootinittemplate.model.entity.ThingRecordDO;
import com.baomidou.mybatisplus.extension.service.IService;

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

    Page<ThingRecordDO> page(ThingDTO thingDTO);

    Boolean audit(Integer status, Long thingId);
}

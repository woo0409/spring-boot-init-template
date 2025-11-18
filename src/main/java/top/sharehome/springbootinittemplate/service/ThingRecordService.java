package top.sharehome.springbootinittemplate.service;

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
    String addRecord(ThingRecordDO recordDO);
}

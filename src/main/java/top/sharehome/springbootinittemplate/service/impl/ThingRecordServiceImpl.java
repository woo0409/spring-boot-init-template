package top.sharehome.springbootinittemplate.service.impl;

import org.springframework.web.bind.annotation.RequestBody;
import top.sharehome.springbootinittemplate.model.entity.ThingRecordDO;
import top.sharehome.springbootinittemplate.mapper.ThingRecordMapper;
import top.sharehome.springbootinittemplate.service.ThingRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 好事记录表 服务实现类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Service
public class ThingRecordServiceImpl extends ServiceImpl<ThingRecordMapper, ThingRecordDO> implements ThingRecordService {

    @Override
    public String addRecord(ThingRecordDO recordDO) {
        return "yes";
    }
}

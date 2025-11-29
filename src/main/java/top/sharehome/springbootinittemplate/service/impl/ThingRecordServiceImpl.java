package top.sharehome.springbootinittemplate.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.sharehome.springbootinittemplate.model.dto.ThingDTO;
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
    public Boolean addRecord(ThingRecordDO recordDO) {
        return this.save(recordDO);
    }

    @Override
    public Page<ThingRecordDO> page(ThingDTO thingDTO) {
        // 创建分页对象，从thingDTO获取分页参数
        Page<ThingRecordDO> page = new Page<>(thingDTO.getPage(), thingDTO.getSize());
        // 执行分页查询
        return this.page(page, new LambdaQueryWrapper<>(ThingRecordDO.class)
                .like(StrUtil.isNotBlank(thingDTO.getThingName()), ThingRecordDO::getThingName, thingDTO.getThingName())
                .eq(ObjUtil.isNotEmpty(thingDTO.getTypeId()), ThingRecordDO::getThingType, thingDTO.getTypeId())
                .eq(ObjUtil.isNotEmpty(thingDTO.getStatus()), ThingRecordDO::getStatus, thingDTO.getStatus()));
    }

}

package top.sharehome.springbootinittemplate.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.sharehome.springbootinittemplate.model.dto.ServicesDTO;
import top.sharehome.springbootinittemplate.model.dto.ThingRecordDTO;
import top.sharehome.springbootinittemplate.model.entity.ServicesDO;
import top.sharehome.springbootinittemplate.model.entity.ThingRecordDO;
import top.sharehome.springbootinittemplate.model.vo.ServicesVO;
import top.sharehome.springbootinittemplate.model.vo.ThingRecordVO;

@Mapper
public interface ThingConvert {
    ThingConvert INSTANCE = Mappers.getMapper(ThingConvert.class);

    ThingRecordDO dtoToDO(ThingRecordDTO servicesDTO);

    Page<ThingRecordVO> doToVo(Page<ThingRecordDO> pageDO);
}

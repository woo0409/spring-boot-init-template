package top.sharehome.springbootinittemplate.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.sharehome.springbootinittemplate.model.entity.ThingTypeDO;
import top.sharehome.springbootinittemplate.model.vo.ThingTypeVO;

import java.util.List;
import java.util.Set;

@Mapper
public interface TypeConvert {
    TypeConvert INSTANCE = Mappers.getMapper(TypeConvert.class);

    ThingTypeVO toVO(ThingTypeDO thing);

    List<ThingTypeVO> toVOList(Set<ThingTypeDO> thing);

    List<ThingTypeVO> toVOList(List<ThingTypeDO> thing);

}

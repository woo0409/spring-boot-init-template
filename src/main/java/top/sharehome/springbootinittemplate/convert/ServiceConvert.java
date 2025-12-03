package top.sharehome.springbootinittemplate.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.sharehome.springbootinittemplate.model.dto.ServicesDTO;
import top.sharehome.springbootinittemplate.model.entity.IntegralServicesDO;
import top.sharehome.springbootinittemplate.model.entity.ServicesDO;
import top.sharehome.springbootinittemplate.model.entity.ThingTypeDO;
import top.sharehome.springbootinittemplate.model.vo.IntegralServicesVO;
import top.sharehome.springbootinittemplate.model.vo.ServicesVO;
import top.sharehome.springbootinittemplate.model.vo.ThingTypeVO;

import java.util.List;
import java.util.Set;

@Mapper
public interface ServiceConvert {
    ServiceConvert INSTANCE = Mappers.getMapper(ServiceConvert.class);

    ServicesDO dtoToDO(ServicesDTO servicesDTO);

    Page<ServicesVO> doToVo(Page<ServicesDO> pageDO);

    IntegralServicesVO integralServicesDOToVO(IntegralServicesDO servicesDO);
}

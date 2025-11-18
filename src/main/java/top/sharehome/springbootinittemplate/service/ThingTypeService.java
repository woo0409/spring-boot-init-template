package top.sharehome.springbootinittemplate.service;

import top.sharehome.springbootinittemplate.model.entity.ThingTypeDO;
import com.baomidou.mybatisplus.extension.service.IService;
import top.sharehome.springbootinittemplate.model.vo.ThingTypeVO;

import java.util.List;

/**
 * <p>
 * 好事类型表 服务类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
public interface ThingTypeService extends IService<ThingTypeDO> {

    List<ThingTypeVO> typeList(String typeName);

    String deleteType(Long id);
}

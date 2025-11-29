package top.sharehome.springbootinittemplate.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.elasticsearch.action.search.SearchTask;
import top.sharehome.springbootinittemplate.convert.TypeConvert;
import top.sharehome.springbootinittemplate.model.entity.ThingTypeDO;
import top.sharehome.springbootinittemplate.mapper.ThingTypeMapper;
import top.sharehome.springbootinittemplate.model.vo.ThingTypeVO;
import top.sharehome.springbootinittemplate.service.ThingTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 好事类型表 服务实现类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Service
public class ThingTypeServiceImpl extends ServiceImpl<ThingTypeMapper, ThingTypeDO> implements ThingTypeService {

    @Override
    public List<ThingTypeVO> typeList(String typeName) {
        // 查询所有符合条件的类型数据
        List<ThingTypeDO> typeList = list(Wrappers.<ThingTypeDO>lambdaQuery()
                .like(ObjectUtil.isNotEmpty(typeName), ThingTypeDO::getTypeName, typeName));

        // 筛选出顶级节点（parent_id为空的节点）
        Set<ThingTypeDO> headList = typeList.stream().map(this::findHead).collect(Collectors.toSet());

        // 转换顶级节点为VO
        List<ThingTypeVO> voList = TypeConvert.INSTANCE.toVOList(headList);

        // 递归构建树形结构
        voList.forEach(type -> buildTree(type, typeList));

        return voList;
    }

    @Override
    public String deleteType(Long id) {
        boolean exists = exists(Wrappers.<ThingTypeDO>lambdaQuery()
                .eq(ThingTypeDO::getParentId, id));

        if (exists) {
            throw new RuntimeException("存在子节点，请先删除子节点");
        }
        removeById(id);
        return "删除成功";
    }

    private ThingTypeDO findHead(ThingTypeDO type) {
        if (ObjectUtil.isEmpty(type.getParentId())) return type;

        type = getOne(Wrappers.<ThingTypeDO>lambdaQuery()
                .eq(ThingTypeDO::getId, type.getParentId()));
        return findHead(type);
    }


    /**
     * 递归构建树形结构
     * @param parent 父节点
     * @param allTypes 所有类型数据
     */
    private void buildTree(ThingTypeVO parent, List<ThingTypeDO> allTypes) {
        // 查找当前节点的子节点
        List<ThingTypeDO> children = allTypes.stream()
                .filter(type -> ObjectUtil.equal(type.getParentId(), parent.getId()))
                .collect(Collectors.toList());

        // 转换子节点为VO
        List<ThingTypeVO> childrenVOList = TypeConvert.INSTANCE.toVOList(children);

        // 设置子节点列表
        parent.setChildrenList(childrenVOList);

        // 递归构建子节点的树形结构
        childrenVOList.forEach(child -> buildTree(child, allTypes));
    }
}

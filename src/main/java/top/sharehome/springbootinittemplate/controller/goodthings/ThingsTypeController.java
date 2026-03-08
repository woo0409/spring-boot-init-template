package top.sharehome.springbootinittemplate.controller.goodthings;

import cn.hutool.core.util.ObjUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.model.entity.ThingTypeDO;
import top.sharehome.springbootinittemplate.model.vo.ThingTypeVO;
import top.sharehome.springbootinittemplate.service.ThingTypeService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/things/type")
@Tag(name = "好事记录模块", description = "好事记录模块")
public class ThingsTypeController {
    @Autowired
    private ThingTypeService thingTypeService;

    @PostMapping("/addOrUpdate")
    @Operation(summary = "添加/更新类型")
    public R<String> addOrUpdateType(@RequestBody ThingTypeDO thingTypeDO) {
        if (ObjUtil.isNotEmpty(thingTypeDO.getId()) && Objects.equals(thingTypeDO.getId(), thingTypeDO.getParentId())) {
            return R.ok("添加成功");
        }
        return thingTypeService.saveOrUpdate(thingTypeDO) ? R.ok("添加成功") : R.fail("添加失败");
    }

    @GetMapping("/list")
    @Operation(summary = "获取所有类型")
    public R<List<ThingTypeVO>> list(@RequestParam(required = false) String typeName) {
        return R.ok(thingTypeService.typeList(typeName));
    }

    @GetMapping("/delete")
    @Operation(summary = "删除类型")
    public R<String> deleteType(@RequestParam Long id) {
        return R.ok(thingTypeService.deleteType(id));
    }
}

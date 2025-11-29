package top.sharehome.springbootinittemplate.controller.goodthings;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.model.dto.ThingDTO;
import top.sharehome.springbootinittemplate.model.entity.ThingRecordDO;
import top.sharehome.springbootinittemplate.service.ThingRecordService;

@RestController
@RequestMapping("/things")
@Tag(name = "好事记录模块", description = "好事记录模块")
public class ThingsController {
    @Autowired
    private ThingRecordService thingRecordService;

    @PostMapping("/add")
    public R<Boolean> addRecord(@RequestBody ThingRecordDO recordDO) {
        return R.ok(thingRecordService.addRecord(recordDO));
    }

    @PostMapping("/page")
    public R<Page<ThingRecordDO>> page(@RequestBody ThingDTO thingDTO) {
        return R.ok(thingRecordService.page(thingDTO));
    }
}

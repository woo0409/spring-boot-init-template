package top.sharehome.springbootinittemplate.controller.auditer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.service.ThingRecordService;

@RestController
@RequestMapping("/auditer")
public class AuditerController {
    @Autowired
    private ThingRecordService thingRecordService;

    @GetMapping("/audit/{status}/{thingId}")
    public R<Boolean> audit(@PathVariable Integer status, @PathVariable Long thingId) {
        return R.ok(thingRecordService.audit(status, thingId));
    }
}

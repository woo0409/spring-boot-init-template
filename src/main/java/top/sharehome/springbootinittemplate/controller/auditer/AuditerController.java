package top.sharehome.springbootinittemplate.controller.auditer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.service.IntegralServicesService;
import top.sharehome.springbootinittemplate.service.ThingRecordService;

@RestController
@RequestMapping("/auditer")
public class AuditerController {
    @Autowired
    private ThingRecordService thingRecordService;
    @Autowired
    private IntegralServicesService integralServicesService;

    @GetMapping("/thingAudit/{status}/{thingId}")
    public R<Boolean> ThingAudit(@PathVariable Integer status, @PathVariable Long thingId) {
        return R.ok(thingRecordService.thingAudit(status, thingId));
    }

    @GetMapping("/serviceAudit/{status}/{serviceId}")
    public R<Boolean> serviceAudit(@PathVariable("status") Integer status,
                                   @PathVariable("serviceId") Long serviceId) {
        return R.ok(integralServicesService.serviceAudit(status, serviceId));
    }
}

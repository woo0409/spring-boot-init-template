package top.sharehome.springbootinittemplate.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.model.dto.ServicesDTO;
import top.sharehome.springbootinittemplate.service.ServicesService;

@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServicesService servicesService;

    @PostMapping("/addOrUpdate")
    public R<Boolean> addOrUpdate(@RequestBody ServicesDTO servicesDO) {
        return R.ok(servicesService.addOrUpdate(servicesDO));
    }
}

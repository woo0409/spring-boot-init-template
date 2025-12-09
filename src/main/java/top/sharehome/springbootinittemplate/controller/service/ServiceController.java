package top.sharehome.springbootinittemplate.controller.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sharehome.springbootinittemplate.common.base.R;
import top.sharehome.springbootinittemplate.model.dto.CommentDTO;
import top.sharehome.springbootinittemplate.model.dto.IntegralServicesDTO;
import top.sharehome.springbootinittemplate.model.dto.ServicesDTO;
import top.sharehome.springbootinittemplate.model.entity.IntegralServicesDO;
import top.sharehome.springbootinittemplate.model.entity.ServicesDO;
import top.sharehome.springbootinittemplate.model.vo.IntegralServicesVO;
import top.sharehome.springbootinittemplate.model.vo.ServicesVO;
import top.sharehome.springbootinittemplate.service.IntegralServicesService;
import top.sharehome.springbootinittemplate.service.ServicesService;

@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private IntegralServicesService integralServicesService;

    @PostMapping("/addOrUpdate")
    public R<Boolean> addOrUpdate(@RequestBody ServicesDTO servicesDTO) {
        return R.ok(servicesService.addOrUpdate(servicesDTO));
    }

    @PostMapping("/page")
    public R<Page<ServicesVO>> page(@RequestBody ServicesDTO servicesDTO) {
        return R.ok(servicesService.getPage(servicesDTO));
    }

    @GetMapping("/exchange/{serviceId}")
    public R<Boolean> exchange(@PathVariable("serviceId") Long serviceId) {
        return R.ok(integralServicesService.exchange(serviceId));
    }

    @PostMapping("/recordPage")
    public R<Page<IntegralServicesDO>> recordPage(@RequestBody IntegralServicesDTO integralServicesDTO) {
        return R.ok(integralServicesService.getRecordPage(integralServicesDTO));
    }

    @GetMapping("/record/{id}")
    public R<IntegralServicesVO> record(@PathVariable("id") Long id) {
        return R.ok(integralServicesService.record(id));
    }

    @PostMapping("/comment")
    public R<Boolean> comment(@RequestBody CommentDTO commentDTO) {
        return R.ok(integralServicesService.comment(commentDTO));
    }
}

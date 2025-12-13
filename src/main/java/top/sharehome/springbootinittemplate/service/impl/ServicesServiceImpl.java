package top.sharehome.springbootinittemplate.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.sharehome.springbootinittemplate.convert.ServiceConvert;
import top.sharehome.springbootinittemplate.mapper.ServicesMapper;
import top.sharehome.springbootinittemplate.model.dto.ServicesDTO;
import top.sharehome.springbootinittemplate.model.entity.File;
import top.sharehome.springbootinittemplate.model.entity.ServicesDO;
import top.sharehome.springbootinittemplate.model.vo.ServicesVO;
import top.sharehome.springbootinittemplate.service.FileService;
import top.sharehome.springbootinittemplate.service.IntegralServicesService;
import top.sharehome.springbootinittemplate.service.ServicesService;
import top.sharehome.springbootinittemplate.utils.satoken.LoginUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 可兑换的服务 服务实现类
 * </p>
 *
 * @author base group
 * @since 2025-10-13
 */
@Service
public class ServicesServiceImpl extends ServiceImpl<ServicesMapper, ServicesDO> implements ServicesService {
    @Autowired
    private FileService fileService;

    @Override
    public Boolean addOrUpdate(ServicesDTO servicesDTO) {
        servicesDTO.setRemaining(servicesDTO.getTotal());
        servicesDTO.setCreateBy(LoginUtils.getLoginUserId());
        return this.saveOrUpdate(ServiceConvert.INSTANCE.dtoToDO(servicesDTO));
    }

    @Override
    public Page<ServicesVO> getPage(ServicesDTO servicesDTO) {
        // 创建分页对象，从thingDTO获取分页参数
        Page<ServicesDO> page = new Page<>(servicesDTO.getPage(), servicesDTO.getSize());

        // 执行分页查询
        Page<ServicesVO> pageVo = ServiceConvert.INSTANCE.doToVo(this.page(page, new LambdaQueryWrapper<>(ServicesDO.class)
                .like(ObjUtil.isNotEmpty(servicesDTO.getServiceName()), ServicesDO::getServiceName, servicesDTO.getServiceName())
                .like(ObjUtil.isNotEmpty(servicesDTO.getServiceRegion()), ServicesDO::getServiceRegion, servicesDTO.getServiceRegion())
                .eq(ObjectUtil.isNotEmpty(servicesDTO.getSelfOnly()) && servicesDTO.getSelfOnly(), ServicesDO::getCreateBy, LoginUtils.getLoginUserId())));

        List<Long> fileIds = pageVo.getRecords().stream().map(ServicesVO::getFileId).toList();
        List<Long> userId = pageVo.getRecords().stream().map(ServicesVO::getCreateBy).toList();
        Map<Long, String> userNameMap = LoginUtils.getUserNameMap(userId);

        Map<Long, String> urlMap = new HashMap<>();
        if (CollUtil.isNotEmpty(fileIds)) {
            urlMap = fileService.listByIds(fileIds).stream()
                    .collect(Collectors.toMap(File::getId, File::getUrl));
        }

        Map<Long, String> finalUrlMap = urlMap;
        pageVo.getRecords().forEach(item -> {
            item.setThumb(finalUrlMap.getOrDefault(item.getFileId(), ""));
            item.setCreateByName(userNameMap.getOrDefault(item.getCreateBy(), ""));
        });

        return pageVo;
    }
}

package top.sharehome.springbootinittemplate.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.sharehome.springbootinittemplate.model.common.PageModel;

@Data
public class ThingDTO extends PageModel {
    @Schema(description = "事件名称")
    private String thingName;

    @Schema(description = "类型id")
    private Long typeId;

    @Schema(description = "状态")
    private Long status;

    @Schema(description = "用户Id")
    private Long userId;

    private Boolean selfOnly;
}

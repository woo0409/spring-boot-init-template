package top.sharehome.springbootinittemplate.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.sharehome.springbootinittemplate.common.validate.PutGroup;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新邮箱Dto类
 *
 * @author AntonyCheng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserUpdatePhoneDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5897238396872573519L;
    /**
     * 新名称
     */
    @NotBlank(message = "新手机号不能为空", groups = {PutGroup.class})
    private String newPhone;

}

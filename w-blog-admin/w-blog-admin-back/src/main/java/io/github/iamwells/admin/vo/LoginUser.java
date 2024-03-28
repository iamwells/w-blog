package io.github.iamwells.admin.vo;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginUser {
    /**
     * 用户账号
     */
    @NotBlank(message = "用户名不能为空！")
    @Size(min = 5, max = 32, message = "用户名长度为5-32位！")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空！")
    @Size(min = 5, max = 32, message = "密码长度为5-32！")
    private String password;
}

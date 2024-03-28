package io.github.iamwells.admin.vo;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterUser {
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

    /**
     * 真实姓名
     */
    @Max(value = 32, message = "真实姓名最长32位！")
    private String name;

    /**
     * 昵称
     */
    @Max(value = 32, message = "昵称最长64位！")
    private String nickname;

    /**
     * 性别
     */
    @PositiveOrZero(message = "性别使用非负整型数字表示！")
    private Integer gender;
    /**
     * 手机号
     */
    @Pattern(
            regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",
            message = "手机号码格式错误"
    )
    private String phone;
    /**
     * 用户邮箱
     */
    @Email(message = "邮箱格式不合法！")
    private String email;
}

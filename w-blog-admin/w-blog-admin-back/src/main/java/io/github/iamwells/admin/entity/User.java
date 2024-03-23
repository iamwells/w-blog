package io.github.iamwells.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * @TableName blog_user
 */
@TableName(value = "blog_user")
@Data
public class User implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户账号
     */
    @TableField(value = "user_username")
    private String username;
    /**
     * 用户密码
     */
    @TableField(value = "user_password")
    private String password;
    /**
     * 真实姓名
     */
    @TableField(value = "user_name")
    private String name;
    /**
     * 昵称
     */
    @TableField(value = "user_nickname")
    private String nickname;
    /**
     * 性别
     */
    @TableField(value = "user_gender")
    private Integer gender;
    /**
     * 手机号
     */
    @TableField(value = "user_phone")
    private String phone;
    /**
     * 用户邮箱
     */
    @TableField(value = "user_email")
    private String email;
    /**
     * 上次登录时间
     */
    @TableField(value = "login_time")
    private OffsetDateTime loginTime;
    /**
     * 登录地址
     */
    @TableField(value = "login_location")
    private String loginLocation;
    /**
     * 登录IP
     */
    @TableField(value = "login_ip")
    private String loginIp;
    /**
     * 用户状态 0禁用 1启用
     */
    @TableField(value = "user_status")
    private Integer status;
    /**
     * 逻辑删除标识 1表示删除
     */
    @TableField(value = "is_deleted")
    @JsonIgnore
    private Integer deleted;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private OffsetDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private OffsetDateTime updateTime;
    @TableField(value = "version")
    @JsonIgnore
    private Integer version;
}
package io.github.iamwells.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * 角色表
 *
 * @TableName blog_role
 */
@TableName(value = "blog_role")
@Data
public class Role implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;
    /**
     * 角色代码
     */
    @TableField(value = "role_code")
    private String roleCode;
    /**
     * 角色名
     */
    @TableField(value = "role_name")
    private String roleName;
    /**
     * 角色状态
     */
    @TableField(value = "role_status")
    private Integer roleStatus;
    /**
     * 逻辑删除
     */
    @TableField(value = "is_deleted")
    private Integer deleted;
    /**
     *
     */
    @TableField(value = "create_time")
    private OffsetDateTime createTime;
    /**
     *
     */
    @TableField(value = "update_time")
    private OffsetDateTime updateTime;
    /**
     * 版本
     */
    @TableField(value = "version")
    @JsonIgnore
    private Integer version;
}
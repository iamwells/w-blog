package io.github.iamwells.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 功能权限表
 * @TableName permission_fun
 */
@TableName(value ="permission_fun")
@Data
public class PermissionFun implements Serializable {
    /**
     * 功能ID
     */
    @TableId(value = "fun_id", type = IdType.AUTO)
    private Integer funId;

    /**
     * 功能代码
     */
    @TableField(value = "fun_code")
    private String funCode;

    /**
     * 功能名
     */
    @TableField(value = "fun_name")
    private String funName;

    /**
     * 功能状态
     */
    @TableField(value = "fun_status")
    private Integer funStatus;

    /**
     * 从属路由
     */
    @TableField(value = "route_id")
    private Integer routeId;

    /**
     * 逻辑删除
     */
    @TableField(value = "is_deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    @TableField(value = "version")
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

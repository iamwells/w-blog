package io.github.iamwells.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.iamwells.admin.entity.PermissionFun;
import io.github.iamwells.admin.entity.Role;

import java.util.List;

/**
 * @author iamwu
 * @description 针对表【permission_fun(功能权限表)】的数据库操作Service
 * @createDate 2024-02-20 15:59:27
 */
public interface PermissionFunService extends IService<PermissionFun> {
    List<PermissionFun> getPermissionFunListByRoles(List<Role> roles);
}

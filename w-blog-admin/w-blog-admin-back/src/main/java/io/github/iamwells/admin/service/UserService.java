package io.github.iamwells.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.iamwells.admin.entity.User;
import io.github.iamwells.admin.vo.UserWithRolesAndPermissions;

/**
 * @author iamwu
 * @description 针对表【blog_user】的数据库操作Service
 * @createDate 2024-02-04 11:04:44
 */
public interface UserService extends IService<User> {
    UserWithRolesAndPermissions getUserWithRolesByUsername(String username);
}

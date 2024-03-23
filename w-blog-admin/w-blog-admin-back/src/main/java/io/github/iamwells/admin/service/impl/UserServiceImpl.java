package io.github.iamwells.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.iamwells.admin.entity.User;
import io.github.iamwells.admin.mapper.UserMapper;
import io.github.iamwells.admin.service.UserService;
import io.github.iamwells.admin.vo.UserWithRolesAndPermissions;
import org.springframework.stereotype.Service;

/**
 * @author iamwu
 * @description 针对表【blog_user】的数据库操作Service实现
 * @createDate 2024-02-04 11:04:44
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public UserWithRolesAndPermissions getUserWithRolesByUsername(String username) {
        return userMapper.selectUserWithRolesByUsername(username);
    }

}





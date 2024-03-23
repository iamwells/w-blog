package io.github.iamwells.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.iamwells.admin.entity.User;
import io.github.iamwells.admin.vo.UserWithRolesAndPermissions;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author iamwu
 * @description 针对表【blog_user】的数据库操作Mapper
 * @createDate 2024-02-04 11:04:44
 * @Entity io.github.iamwells.admin.entity.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    UserWithRolesAndPermissions selectUserWithRolesByUsername(String username);
}





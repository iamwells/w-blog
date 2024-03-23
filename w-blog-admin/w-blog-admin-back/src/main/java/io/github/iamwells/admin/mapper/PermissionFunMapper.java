package io.github.iamwells.admin.mapper;

import io.github.iamwells.admin.entity.PermissionFun;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.iamwells.admin.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author iamwu
* @description 针对表【permission_fun(功能权限表)】的数据库操作Mapper
* @createDate 2024-02-20 15:59:27
* @Entity io.github.iamwells.admin.entity.PermissionFun
*/
@Mapper
public interface PermissionFunMapper extends BaseMapper<PermissionFun> {
    List<PermissionFun> selectPermissionFunListByRoles(List<Role> roles);
}





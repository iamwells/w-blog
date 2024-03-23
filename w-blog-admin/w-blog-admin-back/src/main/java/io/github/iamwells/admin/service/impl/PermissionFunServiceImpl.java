package io.github.iamwells.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.iamwells.admin.entity.PermissionFun;
import io.github.iamwells.admin.entity.Role;
import io.github.iamwells.admin.service.PermissionFunService;
import io.github.iamwells.admin.mapper.PermissionFunMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author iamwu
* @description 针对表【permission_fun(功能权限表)】的数据库操作Service实现
* @createDate 2024-02-20 15:59:27
*/
@Service
public class PermissionFunServiceImpl extends ServiceImpl<PermissionFunMapper, PermissionFun>
    implements PermissionFunService{

    private final PermissionFunMapper permissionFunMapper;

    public PermissionFunServiceImpl(PermissionFunMapper permissionFunMapper) {
        this.permissionFunMapper = permissionFunMapper;
    }

    @Override
    public List<PermissionFun> getPermissionFunListByRoles(List<Role> roles) {
        return permissionFunMapper.selectPermissionFunListByRoles(roles);
    }

}





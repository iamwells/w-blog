package io.github.iamwells.admin.config.security;


import io.github.iamwells.admin.entity.PermissionFun;
import io.github.iamwells.admin.service.PermissionFunService;
import io.github.iamwells.admin.service.UserService;
import io.github.iamwells.admin.util.GrantedAuthorityUtil;
import io.github.iamwells.admin.vo.UserWithRolesAndPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final PermissionFunService permissionFunService;

    @Autowired
    public CustomUserDetailsService(UserService userService, PermissionFunService permissionFunService) {
        this.userService = userService;
        this.permissionFunService = permissionFunService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserWithRolesAndPermissions user = userService.getUserWithRolesByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        List<PermissionFun> permissionFunList = permissionFunService.getPermissionFunListByRoles(user.getRoles());
        user.setFunPermissions(permissionFunList);
        GrantedAuthorityUtil.fillAuthorities(user);
        return new CustomUserDetails(user);
    }


}

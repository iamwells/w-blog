package io.github.iamwells.admin.util;

import io.github.iamwells.admin.entity.PermissionFun;
import io.github.iamwells.admin.entity.Role;
import io.github.iamwells.admin.vo.UserWithRolesAndPermissions;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class GrantedAuthorityUtil {
    public static void fillAuthorities(UserWithRolesAndPermissions user) {
        if (user != null) {
            List<Role> roles = user.getRoles();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (roles != null && !roles.isEmpty()) {
                List<SimpleGrantedAuthority> roleAuthorities = user.getRoles()
                        .stream().map(role -> new SimpleGrantedAuthority(role.getRoleCode())).toList();
                authorities.addAll(roleAuthorities);

            }
            List<PermissionFun> funPermissions = user.getFunPermissions();
            if (funPermissions != null && !funPermissions.isEmpty()) {
                List<SimpleGrantedAuthority> funAuthorities = funPermissions.stream()
                        .map(permissionFun -> new SimpleGrantedAuthority(permissionFun.getFunCode()))
                        .toList();
                authorities.addAll(funAuthorities);
            }
            user.setAuthorities(authorities);
        }
    }

}

package io.github.iamwells.admin.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.iamwells.admin.entity.PermissionFun;
import io.github.iamwells.admin.entity.Role;
import io.github.iamwells.admin.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UserWithRolesAndPermissions extends User {

    private List<Role> roles;
    private List<PermissionFun> funPermissions;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;
}

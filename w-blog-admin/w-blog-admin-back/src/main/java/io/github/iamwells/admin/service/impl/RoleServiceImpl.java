package io.github.iamwells.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.iamwells.admin.entity.Role;
import io.github.iamwells.admin.mapper.RoleMapper;
import io.github.iamwells.admin.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author iamwu
 * @description 针对表【blog_role(角色表)】的数据库操作Service实现
 * @createDate 2024-02-05 17:02:56
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

}





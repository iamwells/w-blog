package io.github.iamwells.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.iamwells.admin.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author iamwu
 * @description 针对表【blog_role(角色表)】的数据库操作Mapper
 * @createDate 2024-02-05 17:02:56
 * @Entity io.github.iamwells.admin.entity.Role
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> selectByUserId(Serializable id);
}





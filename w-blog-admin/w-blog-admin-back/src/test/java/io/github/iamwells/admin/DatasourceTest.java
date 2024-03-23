package io.github.iamwells.admin;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.iamwells.admin.entity.User;
import io.github.iamwells.admin.mapper.UserMapper;
import io.github.iamwells.admin.properties.jwt.JwtHeader;
import io.github.iamwells.admin.properties.jwt.JwtPayload;
import io.github.iamwells.admin.service.UserService;
import io.github.iamwells.admin.vo.UserWithRolesAndPermissions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;

@SpringBootTest
public class DatasourceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtPayload jwtPayload;

    @Autowired
    private JwtHeader jwtHeader;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSelectUser() throws JsonProcessingException {
        System.out.println();
        UserWithRolesAndPermissions admin = userService.getUserWithRolesByUsername("admin");
        System.out.println(admin);
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("test4");
        user.setPassword("123456");
        user.setDeleted(0);
        System.out.println(OffsetDateTime.now());
        boolean save = userService.save(user);
        System.out.println(user);
    }

    @Test
    public void testJwt() {
        JwtHeader jwtHeader1 = jwtHeader;
        System.out.println();
        User user = new User();
        user.setUsername("admin");
        user.setId(1L);
    }

    @Test
    public void testPasswordEncoder() {
        String encode = passwordEncoder.encode("123456");
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>()
                .eq("user_username", "admin").set("user_password", encode);
        userMapper.update(updateWrapper);
    }
}

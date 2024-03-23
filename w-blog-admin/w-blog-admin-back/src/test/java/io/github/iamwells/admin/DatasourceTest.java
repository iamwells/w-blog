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


}

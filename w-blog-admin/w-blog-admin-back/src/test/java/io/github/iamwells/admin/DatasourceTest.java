package io.github.iamwells.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.iamwells.admin.mapper.UserMapper;
import io.github.iamwells.admin.properties.jwt.JwtHeader;
import io.github.iamwells.admin.properties.jwt.JwtPayload;
import io.github.iamwells.admin.service.MailService;
import io.github.iamwells.admin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    private MailService mailService;


    @Test
    public void test() {
        mailService.sendCaptcha("iamwells@qq.com", "123456");
    }


}

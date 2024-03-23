package io.github.iamwells.admin.controller;


import io.github.iamwells.admin.entity.User;
import io.github.iamwells.admin.properties.jwt.JwtProperties;
import io.github.iamwells.admin.util.JwtThreadLocal;
import io.github.iamwells.admin.util.JwtUtil;
import io.github.iamwells.admin.vo.LoginUser;
import io.github.iamwells.admin.vo.UserWithRolesAndPermissions;
import io.github.iamwells.commons.web.ResponseCommonEntity;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("sign")
public class SignController {


    public final AuthenticationManager authenticationManager;

    public final JwtProperties jwtProperties;

    private final RedissonClient redissonClient;

    @Value("${auth.remember-days}")
    private Integer rememberDays;

    public SignController(AuthenticationManager authenticationManager, JwtProperties jwtProperties, RedissonClient redissonClient) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        this.redissonClient = redissonClient;
    }

    @PostMapping("in")
    public ResponseCommonEntity<Object> on(@RequestBody LoginUser user, @RequestParam(required = false) Boolean rememberMe) {
        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        authenticationToken = authenticationManager.authenticate(authenticationToken);

        RBucket<String> bucket = null;
        String jwt = null;
        if (rememberMe != null && rememberMe) {
            bucket = redissonClient.getBucket("online:" + user.getUsername(), new StringCodec());
            if (bucket.isExists()) {
                jwt = bucket.get();
            } else {
                jwt = JwtUtil.generate((User) authenticationToken.getDetails(), jwtProperties);
                bucket.set(jwt);
            }
            bucket.expire(Duration.ofDays(rememberDays));
        } else {
            jwt = JwtUtil.generate((User) authenticationToken.getDetails(), jwtProperties);
        }
        JwtThreadLocal.set(jwt);
        return ResponseCommonEntity.OK(null);
    }

    @PostMapping("up")
    public String up(@RequestBody User user) {
        return null;
    }

    @GetMapping("out")
    public ResponseCommonEntity<String> out() {
        return ResponseCommonEntity.OK("haha");
    }

    @GetMapping("details")
    public ResponseCommonEntity<Object> getUserDetails() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserWithRolesAndPermissions details = null;
        if (authentication != null && authentication.isAuthenticated()) {
            details = (UserWithRolesAndPermissions) authentication.getDetails();
        }
        return ResponseCommonEntity.OK(details);
    }
}

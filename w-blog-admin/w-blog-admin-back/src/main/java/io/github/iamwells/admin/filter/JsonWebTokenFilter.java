package io.github.iamwells.admin.filter;

import io.github.iamwells.admin.properties.AuthProperties;
import io.github.iamwells.admin.properties.jwt.JwtProperties;
import io.github.iamwells.admin.util.GrantedAuthorityUtil;
import io.github.iamwells.admin.util.JwtThreadLocal;
import io.github.iamwells.admin.util.JwtUtil;
import io.github.iamwells.admin.vo.UserWithRolesAndPermissions;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.ErrorCodeValidator;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtContext;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class JsonWebTokenFilter extends OncePerRequestFilter {


    private String headerName;

    private List<String> whiteList = new ArrayList<>(List.of("/"));

    private JwtProperties jwtProperties;

    private RedissonClient redissonClient;

    public JsonWebTokenFilter(AuthProperties auth, JwtProperties jwtProperties, RedissonClient redissonClient) {
        this.headerName = auth.getTokenHeaderName();
        this.whiteList.addAll(auth.getTokenWhiteList());
        this.jwtProperties = jwtProperties;
        this.redissonClient = redissonClient;
        if (headerName == null || headerName.isEmpty()) {
            headerName = "token";
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        boolean isWhiteUri = whiteList.contains(uri);
        if (!isWhiteUri) {
            String jwt = request.getHeader(headerName);
            if (StringUtils.hasLength(jwt)) {
                UserWithRolesAndPermissions user = null;
                try {
                    user = JwtUtil.verify(jwt, jwtProperties.getSignature(), jwtProperties.getPayload(), UserWithRolesAndPermissions.class);
                    doLogin(user);
                } catch (InvalidJwtException e) {
                    List<ErrorCodeValidator.Error> errorDetails = e.getErrorDetails();
                    if (errorDetails != null && errorDetails.size() == 1 && e.hasExpired()) {
                        JwtContext jwtContext = e.getJwtContext();
                        JwtClaims jwtClaims = jwtContext.getJwtClaims();
                        user = (UserWithRolesAndPermissions) jwtClaims.getClaimValue("user");
                        RBucket<Object> bucket = redissonClient.getBucket("online:" + user.getUsername());
                        if (bucket.isExists()) {
                            doLogin(user);
                            String newJwt = JwtUtil.generate(user, jwtProperties);
                            JwtThreadLocal.set(newJwt);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    public void doLogin(UserWithRolesAndPermissions user) {
        GrantedAuthorityUtil.fillAuthorities(user);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
        authenticationToken.setDetails(user);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
    }
}

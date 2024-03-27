package io.github.iamwells.admin.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.iamwells.commons.web.ResponseCommonEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ResponseCommonEntity<Object> commonEntity = ResponseCommonEntity.CLIENT_ERROR(HttpStatus.UNAUTHORIZED, null, authException.getMessage());
        String result = objectMapper.writeValueAsString(commonEntity);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
        response.flushBuffer();
    }
}

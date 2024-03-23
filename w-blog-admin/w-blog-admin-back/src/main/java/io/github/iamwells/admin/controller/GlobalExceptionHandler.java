package io.github.iamwells.admin.controller;


import io.github.iamwells.commons.web.ResponseCommonEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseCommonEntity<Object> exceptionHandler(Exception e, HttpServletRequest request) {
        return ResponseCommonEntity.SERVER_ERROR(null, e.getMessage());
    }
}

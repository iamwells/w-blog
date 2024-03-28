package io.github.iamwells.admin.controller;


import io.github.iamwells.commons.web.ResponseCommonEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseCommonEntity<Object> exceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errList = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        StringBuilder builder = new StringBuilder();
        int errListSize = errList.size();
        for (int i = 0; i < errListSize; i++) {
            builder.append(errList.get(i));
            if (i < errListSize - 1) {
                builder.append("==>");
            }
        }
        return ResponseCommonEntity.SERVER_ERROR(null, builder.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseCommonEntity<Object> exceptionHandler(Exception e, HttpServletRequest request) {
        return ResponseCommonEntity.SERVER_ERROR(null, e.getMessage());
    }
}

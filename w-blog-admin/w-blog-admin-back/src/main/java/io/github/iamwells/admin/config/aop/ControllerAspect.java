package io.github.iamwells.admin.config.aop;


import io.github.iamwells.admin.util.JwtThreadLocal;
import io.github.iamwells.commons.web.ResponseCommonEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class ControllerAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public void pointcut(){}

    @Around(value = "pointcut()")
    public Object after(ProceedingJoinPoint joinPoint) throws Throwable {
        //noinspection unchecked
        ResponseCommonEntity<Object> result = (ResponseCommonEntity<Object>) joinPoint.proceed(joinPoint.getArgs());
        if (StringUtils.hasLength(JwtThreadLocal.get())) {
            result.setToken(JwtThreadLocal.get());
            JwtThreadLocal.remove();
        }
        return result;
    }
}

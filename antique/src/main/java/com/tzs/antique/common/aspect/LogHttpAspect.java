package com.tzs.antique.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Aspect
@Component
public class LogHttpAspect {
    //execution  后面填写拦截的controller的路径
    @Pointcut("execution(public * com.tzs.antique.system.controller.*.*(..))")
    public void log() {
    }

    private long start;

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("------------------------华丽的分割线-----------------------");
        start = System.currentTimeMillis();//当前时间
        log.info("api request start " + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())));
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("url={}", request.getRequestURI());
        log.info("method={}", request.getMethod());
        log.info("ip={}", request.getRemoteAddr());
        log.info("get_params={}", request.getQueryString());
        if (joinPoint != null) {
            log.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            Object[] args = joinPoint.getArgs();
            //  Object[] arguments  = new Object[args.length];
            List<Object> arguments = new ArrayList<>();
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                    continue;
                }
                arguments.add(args[i]);
            }
            log.info("post_params={}", arguments.toString());
        }
        System.out.println("------------------------华丽的结束线-----------------------");
    }


}


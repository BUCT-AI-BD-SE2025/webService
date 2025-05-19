package com.tzs.antique.common.aspect;

import com.tzs.antique.common.annotation.Limit;
import com.tzs.antique.common.enums.LimitType;
import com.tzs.antique.common.exception.LimitAccessException;
import com.tzs.antique.common.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;


/**
 * 接口限流
 */
@Slf4j
@Aspect
@Component
public class LimitAspect {

    @Pointcut("@annotation(com.tzs.antique.common.annotation.Limit)")
    public void pointcut() {
        // do nothing
    }
    static HashMap<String,Integer> LIMIT_MAP;

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Limit limitAnnotation = method.getAnnotation(Limit.class);
        LimitType limitType = limitAnnotation.limitType();
        String name = limitAnnotation.name();
        String key;
        String ip = IPUtil.getIpAddr(request);
        int limitCount = limitAnnotation.count();
        switch (limitType) {
            case IP:
                key = ip;
                break;
            case CUSTOMER:
                key = limitAnnotation.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        String keys = StringUtils.join(limitAnnotation.prefix() + "_", key, ip);
        Integer count = LIMIT_MAP.get(keys);
        if(count == null)
            count = 1;
        else
            count ++;
        LIMIT_MAP.put(keys,count);
        log.info("IP:{} 第 {} 次访问key为 {}，描述为 [{}] 的接口", ip, count, keys, name);
        if (count.intValue() <= limitCount) {
            return point.proceed();
        } else {
            throw new LimitAccessException("接口访问超出频率限制");
        }
    }

}

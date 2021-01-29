package com.example.demo.config.datasource.dynamic.aspect;

import com.example.demo.config.datasource.dynamic.DataSourceKeyHolder;
import com.example.demo.config.datasource.dynamic.annotation.DynamicDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 动态切换数据源的切面
 */
@Component
@Aspect
@Order(1)
public class DataSourceAspect {

    @Pointcut("execution(* com..*Dao.*(..))")
    public void aspect() {

    }

    @Before("aspect()")
    public void before(JoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DynamicDataSource annotation = method.getAnnotation(DynamicDataSource.class);
        if (null == annotation) {
            annotation = joinPoint.getTarget().getClass().getAnnotation(DynamicDataSource.class);
        }
        if (null != annotation) {
            DataSourceKeyHolder.set(annotation.name());
        }

    }

    @After("aspect()")
    public void after() {
        DataSourceKeyHolder.clear();
    }

}

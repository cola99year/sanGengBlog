package com.cola.aspect;

import com.alibaba.fastjson.JSON;
import com.cola.annotation.AopLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author: cola99year
 * @Date: 2022/10/23 0:33
 */
@Aspect  //标识这是一个切面类
@Component
@Slf4j
public class LogAspect {
    //确定切点：括号内的注解来定位，这个注解在哪使用，我就在那里切入
    @Pointcut("@annotation(com.cola.annotation.AopLog)")
    public void pt(){
    }

    /**
     * 环绕通知
     * @param joinPoint 目标方法的信息封装成的一个对象
     * @return
     * @throws Throwable  目标方法执行会出现的异常
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            handleBefore(joinPoint);
            //让目标方法执行，并获取其返回的结果
            result = joinPoint.proceed();
            handleAfter(result);
        } finally {
            // 结束后换行，log方法来自lombok的slf4j
            log.info("=======End=======" + System.lineSeparator());
            //System.lineSeparator()换行的意思，为了适配Linux和Windows换行符不同的问题
        }
        return result;
    }



    private void handleBefore(ProceedingJoinPoint joinPoint) {
        //Spring提供的，内部使用了ThreadLocal，每个线程只会获取自己的变量对象，线程隔离
        //需要注意返回的对象类型必须是ServletRequestAttributes，才有getRequest方法获取request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取request对象
        HttpServletRequest request = requestAttributes.getRequest();
        //获取注解对象
        AopLog aopLog = getAopLog(joinPoint);
        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息,aop自定义注解里的那个属性
        log.info("BusinessName   : {}", aopLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用controller的全路径以及执行方法:com.cola.controller.UserController.saveUserInfo
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),
                ((MethodSignature)joinPoint.getSignature()).getMethod());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参,注意参数类型很多，把他们转为String型
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }
    private void handleAfter(Object result) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(result));
    }

    /**
     * 获取指定方法的aop注解对象
     * @param joinPoint 目标方法的信息封装成的一个对象
     * @return
     */
    private AopLog getAopLog(ProceedingJoinPoint joinPoint) {
        //获取方法的签名
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        //获取方法的AopLog.class注解
        AopLog aopLog = methodSignature.getMethod().getAnnotation(AopLog.class);
        return aopLog;
    }

}

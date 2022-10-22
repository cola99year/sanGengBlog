package com.cola.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AopLog {
    //在方法添加这个注解时，可以顺便写上注释在这个属性上
    String businessName();
}

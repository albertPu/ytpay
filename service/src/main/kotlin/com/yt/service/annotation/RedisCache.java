package com.yt.service.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {


    Operate operate() default Operate.QUERY;

    String nameSpace() default "";

    long cacheTimeSecond() default 5 * 60L;
}


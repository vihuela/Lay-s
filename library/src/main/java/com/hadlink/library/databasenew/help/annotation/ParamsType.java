package com.hadlink.library.databasenew.help.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2015/4/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ParamsType {
    /** 默认值为{@link Type#NORMAL} */
    Type value() default Type.NORMAL;

    enum Type {KEY, NORMAL, NO_USE, FOREIGN_KEY}
}

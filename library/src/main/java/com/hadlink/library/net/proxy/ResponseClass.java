package com.hadlink.library.net.proxy;

/**
 * Created by zhouml on 2015/12/3.
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Make a GET request to a REST path relative to base URL. */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface ResponseClass {
    Class value();
}

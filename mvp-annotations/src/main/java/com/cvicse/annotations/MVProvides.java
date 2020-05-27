package com.cvicse.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liu_zlu on 2017/2/24 10:03
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface MVProvides {
    Class value();
}

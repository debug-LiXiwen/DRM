/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * To mark a distributed resource attribute.
 *
 * @author xichen.lxw
 * @version $Id: DAttribute.java, v 0.1 2020-10-06 11:09 上午 xichen.lxw Exp $$
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DAttribute {

    /**
     * description of this attribute, not requisite
     */
    String description() default "";

    /**
     * name of this attribute, no longer used
     */
    String name() default "";

}
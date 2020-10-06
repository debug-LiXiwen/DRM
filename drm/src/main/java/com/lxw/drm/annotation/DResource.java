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
 * To mark a distributed resource object.
 *
 * @author xichen.lxw
 * @version $Id: DResource.java, v 0.1 2020-10-06 11:08 上午 xichen.lxw Exp $$
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DResource {
    /**
     * resource identify, should be unique
     */
    String id();

    /**
     * description of this resource, not requisite
     */
    String description() default "";
}
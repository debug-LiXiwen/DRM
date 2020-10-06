/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.update;

import java.lang.reflect.Method;
import java.util.Map;

import com.lxw.drm.DRMClient;
import com.lxw.drm.model.DistributedResource;
import com.lxw.drm.model.DistributedResourceAttribute;
import com.lxw.drm.util.StringConverter;

/**
 * 分布式资源属性更新器。无状态。
 *
 * @author xichen.lxw
 * @version $Id: ResourceAttributeUpdater.java, v 0.1 2020-10-06 11:33 上午 xichen.lxw Exp $$
 */
public class ResourceAttributeUpdater {
    /**
     * 更新属性。
     *
     * @param id 分布式资源id
     * @param key 分布式资源属性对象，包含所需的一切信息。
     * @param appName 应用名字
     * @param stringValue 字符串描述的分布式资源属性值。
     * @throws Exception 处理异常
     */
    public static void updateResource(String id, String key, String stringValue, String appName)
        throws Exception {

        // 找到这个应用下的某个分布式资源
        DistributedResource distributedResource = DRMClient.getInstance().findResourceByAppAndId(appName, id);
        Map<String, DistributedResourceAttribute> attributes = distributedResource.getAttributes();

        // 获取到要更新的属性资源
        DistributedResourceAttribute attribute = attributes.get(key);

        // 获取属性的set方法。
        Method setter = attribute.getWriterMethod();
        if (setter == null) {
            throw new RuntimeException("Attribute " + attribute.getAttrName() + " has no setter");
        }
        //目标对象
        Object target = attribute.getResource().getImplementation();
        //set方法的参数类型，也就是属性字段的类型。
        Class<?> paramType = setter.getParameterTypes()[0];
        ClassLoader tcl = null;
        Object param = null;
        tcl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(
                attribute.getResource().getImplementation().getClass().getClassLoader());
            // 将字符串描述的属性值转换为真正的类型。
            param = StringConverter.convertObjectFromString(paramType, stringValue);

            //提供给回调方法的参数，第一个是属性名，第二个是更新的值
            Object[] callbackParams = { attribute.getAttrName(), param };

            //先执行前置回调。
            Method beforeUpdate = attribute.getResource().getBeforeUpdateMethod();
            if (beforeUpdate != null) {
                //确保不会因为非重要的回调方法影响重要的更新方法。
                try {
                    beforeUpdate.invoke(target, callbackParams);
                } catch (Exception e) {

                    System.err.println(
                        "Execute before-update method [" + beforeUpdate.getName()
                            + "] for attribute [" + attribute.getAttrName() + "] exception" + e.getMessage());
                }
            }
            //执行真正的更新方法
            setter.invoke(target, param);
            //再执行后置回调。
            Method afterUpdate = attribute.getResource().getAfterUpdateMethod();
            if (afterUpdate != null) {
                //确保不会因为非重要的回调方法影响重要的更新方法。
                try {
                    afterUpdate.invoke(target, callbackParams);
                } catch (Exception e) {
                    System.err.println(
                        "Execute after-update method [" + afterUpdate.getName()
                            + "] for attribute [" + attribute.getAttrName() + "] exception" + e.getMessage());
                }
            }
        } finally {
            Thread.currentThread().setContextClassLoader(tcl);
        }
    }

}
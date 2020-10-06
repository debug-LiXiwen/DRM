/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.builder;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMException;
import javax.management.MalformedObjectNameException;

import com.lxw.drm.annotation.AfterUpdate;
import com.lxw.drm.annotation.BeforeUpdate;
import com.lxw.drm.annotation.DAttribute;
import com.lxw.drm.annotation.DResource;
import com.lxw.drm.model.DistributedResource;
import com.lxw.drm.model.DistributedResourceAttribute;
import com.lxw.drm.model.DistributedResourceConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.jmx.export.metadata.InvalidMetadataException;
import org.springframework.util.StringUtils;

/**
 * 分布式资源构建器，从组件中分析需要构建成分布式资源对象的信息。
 *
 * <p>需要分析资源的那些属性，操作需要管理，根据的资源类中的标注信息进行分析，可分析的标注如下：
 * <li>DResource : 标识资源整体特性；
 * <li>DAttribute : 标识资源属性特性，如果出现在属性的Getter方法上表示可读，出现在属性的Setter方法上标识可写；
 * <li>DOperation : 标识资源的操作特性；
 *
 * @author xichen.lxw
 * @version $Id: DistributedResourceBuilder.java, v 0.1 2020-10-06 11:58 上午 xichen.lxw Exp $$
 */
public class DistributedResourceBuilder {

    /**
     * 根据分布式资源的配置信息，构建分布式资源对象。
     * @param resource
     * @param app
     * @return
     */
    public DistributedResource build(Object resource, String app) {

        DistributedResource dResource;

        try {
            // 设置属性基本信息
            dResource = getResourceInfo(resource, app);
            // 设置属性信息
            dResource.setAttributes(this.getAttributeInfo(dResource, resource));
            // 设置功能方法信息
            this.setFunctionMethods(dResource, resource);
            dResource.setImplementation(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dResource;
    }

    /**
     * 取得资源的属性信息，主要根据JavaBean的特性进行解析。
     * @param distributedResource
     * @param resource
     * @return
     * @throws JMException
     */
    protected Map<String, DistributedResourceAttribute> getAttributeInfo(DistributedResource distributedResource,
        Object resource)
        throws JMException {
        Class<?> targetClass = resource.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        Map<String, DistributedResourceAttribute> attributes = new HashMap<String, DistributedResourceAttribute>();

        // 遍历所有字段(包括私有的)
        for (Field field : fields) {
            String attrName = field.getName();
            DAttribute ann = field.getAnnotation(DAttribute.class);
            if (ann == null) {
                continue;
            }
            //PropertyDescriptor propertyDescriptor = new PropertyDescriptor(attrName, targetClass);
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(targetClass,
                attrName);
            if (propertyDescriptor == null) {
                throw new RuntimeException("DRM attribute " + attrName
                    + " must have a pair of getter and setter method!");
            }
            Method getter = propertyDescriptor.getReadMethod();
            Method setter = propertyDescriptor.getWriteMethod();
            if (setter == null || getter == null) {
                throw new RuntimeException("DRM attribute " + attrName
                    + " must have a getter/setter method!");
            }
            DistributedResourceAttribute attribute = new DistributedResourceAttribute(attrName);

            attribute.setResource(distributedResource);
            attributes.put(attribute.getAttrName(), attribute);

            attribute.setDesc(ann.description());
            attribute.setReaderMethod(getter);
            attribute.setWriterMethod(setter);

            try {
                //尝试调用get方法
                getter.invoke(resource);
            } catch (Exception e) {
                throw new RuntimeException("Call getter [" + targetClass.getName() + "].["
                    + attrName + "] exception", e);
            }
        }
        return attributes;
    }

    /**
     * 取得资源功能方法信息
     * 设置到distributedResource中
     */
    protected void setFunctionMethods(DistributedResource distributedResource, Object resource) {
        Method[] methods = resource.getClass().getDeclaredMethods();
        for (Method method : methods) {
            BeforeUpdate beforeAnn = method.getAnnotation(BeforeUpdate.class);
            if (beforeAnn != null) {
                // check参数
                validateCallbackMethodParams(method);
                distributedResource.setBeforeUpdateMethod(method);
            }
            AfterUpdate afterAnn = method.getAnnotation(AfterUpdate.class);
            if (afterAnn != null) {
                // check参数
                validateCallbackMethodParams(method);
                distributedResource.setAfterUpdateMethod(method);
            }
        }
    }

    /**
     * 检查回调方法的参数类型。
     *
     * @param callbackMethod
     */
    void validateCallbackMethodParams(Method callbackMethod) {
        Class<?>[] paramClazzs = callbackMethod.getParameterTypes();
        if (!(paramClazzs.length == 2 && paramClazzs[0].equals(String.class) && paramClazzs[1]
            .equals(Object.class))) {
            throw new RuntimeException("The signature of callback method ["
                + callbackMethod.getName()
                + "] must be (String key,Object value)");
        }
    }

    /**
     * 取得资源的全局配置。
     *
     * @param resource
     * @param app
     * @return
     * @throws InvalidMetadataException
     * @throws MalformedObjectNameException
     */
    protected DistributedResource getResourceInfo(Object resource, String app)
        throws InvalidMetadataException,
        MalformedObjectNameException {
        Class<?> clazz = resource.getClass();
        DResource ann = clazz.getAnnotation(DResource.class);
        if (ann == null) {
            throw new RuntimeException("DRM class [" + clazz.getName()
                + "] must have @DResource annotation");
        }

        String id = ann.id();
        String description = ann.description();

        if (!StringUtils.hasLength(id)) {
            throw new RuntimeException("DRM class [" + clazz.getName()
                + "]'s @DResource annotation must have 'id' property");
        }

        //domain使用"Lxw.app"的形式。
        String domain = DistributedResourceConstant.PREFIX + app;
        String version = DistributedResourceConstant.DEFAULT_VERSION;
        //用当前jar包中定义的版本
        DistributedResource distributedResource = new DistributedResource(id, domain, version, app);
        distributedResource.setDesc(description);

        return distributedResource;
    }

    protected String getAttributeDescription(PropertyDescriptor propertyDescriptor) {
        return propertyDescriptor.getDisplayName();
    }

    private static Method getGetMethod(Object ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i];
            }
        }
        return null;
    }

    private static Method getSetMethod(Object ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("set" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i];
            }
        }
        return null;
    }



}
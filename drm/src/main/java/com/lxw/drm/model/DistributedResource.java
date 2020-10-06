/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.model;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * 分布式资源对象。
 *
 * <p>为了保证分布式资源对象的完整和唯一性，所以使用JMX的ObjectName对象来唯一标识一个分布式资源对象。
 *
 * <p>ObjectName的格式如下：
 *
 * <p>比如帐务系统有一个分布式资源：提供WS的Http Transport能力的JettyHttpTransportComponent，改资源有以下属性需要监控和配置
 * <ul>
 * <li> maxThreads :设置Jetty容器处理请求的最大线程数
 * <li> maxIdeleTimeMs :设置Jetty容器处理请求的线程闲置时间
 * <li> minThreads :设置Jetty容器处理请求的最小线程数
 * </ul>
 *
 * <p>该资源对象的ObjectName为：
 * <p>Alipay.SOFA.Service:name=com.alipay.sofa.service.transport.http.impl.JettyHttpTransportComponent,version=1.0
 * <ul>
 * <li>Alipay.SOFA.Service :代表资源的分组类型，可以通过ObjectName.getDomain()方法获得；
 * <li>key1=value1,... :代表该资源的属性类型，可以为任意值，可以通过ObjectName.getKeyProperty()方法获得具体的属性值；
 * <ul>
 * 这里的属性含义如下，用来唯一标识一个资源：
 * <li>name :代表资源的名称，默认为资源类的full name；
 * <li>version :代表资源的版本；
 * </ul>
 * </ul>
 *
 * <p>该ObjectName，用于JMX的MBean注册，也用于配置中心的订阅时的DataId设置。
 * <ul>
 * <li>JMX ObjectName ： Alipay.SOFA.Service:name=com.alipay.sofa.service.transport.http.impl.JettyHttpTransportComponent,version=1.0
 * <li>Confreg 比较特殊，为了保证资源属性配置的安全性，故针对资源的每一个可以写属性设置一个订阅端，所以针对JettyHttpTransportComponent资源的配置如下：
 * <ul>
 * <li>maxThreads :Alipay.SOFA.Service:name=com.alipay.sofa.service.transport.http.impl.JettyHttpTransportComponent@maxThreads,version=1.0
 * <li>maxIdeleTimeMs :Alipay.SOFA.Service:name=com.alipay.sofa.service.transport.http.impl.JettyHttpTransportComponent@maxIdeleTimeMs,version=1.0
 * <li>minThreads :Alipay.SOFA.Service:name=com.alipay.sofa.service.transport.http.impl.JettyHttpTransportComponent@minThreads,version=1.0
 * </ul>
 * </ul>
 *
 * @author xichen.lxw
 * @version $Id: DistributedResource.java, v 0.1 2020-10-06 11:25 上午 xichen.lxw Exp $$
 */
public class DistributedResource {
    // 基本属性
    private String                                    id;
    private ObjectName objectName;
    // 域。固定前缀.app，例如Lxw.demo
    private String                                    domain;
    private String                                    app;
    private String                                    version;
    private Map<String, String> extensionProperties;
    private String                                    desc;

    /**
     * 属性和方法的元数据定义
     */
    private Map<String, DistributedResourceAttribute> attributes;

    /**
     * 属性更新之前的回调方法
     */
    private Method beforeUpdateMethod;

    /**
     * 属性更新之后的回调方法
     */
    private Method                                    afterUpdateMethod;

    // 实际实现资源的对象
    private Object                                    implementation;

    /**
     * 分布式资源对象构造方法
     *
     * @param id
     * @param domain
     * @param version
     * @param app
     * @throws MalformedObjectNameException
     */
    public DistributedResource(String id, String domain, String version, String app)
        throws MalformedObjectNameException {
        this.id = id;
        this.app = app;
        this.domain = domain;
        this.version = version;

        extensionProperties = new ConcurrentHashMap<String, String>(2);
        attributes = new ConcurrentHashMap<String, DistributedResourceAttribute>(8);
    }

    /**
     * 新增分布式资源中的具体的属性信息
     *
     * @param attribute
     */
    public void addAttribute(DistributedResourceAttribute attribute) {

        if (attribute == null) {
            return;
        }

        if (!attributes.containsKey(attribute.getAttrName())) {
            attributes.put(attribute.getAttrName(), attribute);
        } else {
            throw new RuntimeException("分布式资源添加属性的时已经存在注册的属性[" + attribute.getAttrName() + "]");
        }
    }

    /**
     * 根据分布式资源对象，构建与之对应的ObjectName
     *
     * @return
     * @throws MalformedObjectNameException
     */
    public ObjectName buildObejctName() throws MalformedObjectNameException {
        return ObjectNameBuilder.build(this);
    }

    /**
     * 判断该资源是否有读属性。
     *
     * @param attrName 属性名称。
     * @return
     */
    public boolean includeReadAttribute(String attrName) {
        return attributes.get(attrName) != null && attributes.get(attrName).isRead();
    }

    /**
     * 判断该资源是否有写属性。
     *
     * @param attrName 属性名称。
     * @return
     */
    public boolean includeWriteAttribute(String attrName) {
        return attributes.get(attrName) != null && attributes.get(attrName).isWrite();
    }

    /**
     * 取得资源的属性对象。
     *
     * @param attrName 属性名称。
     * @return
     */
    public DistributedResourceAttribute getAttribute(String attrName) {
        return this.attributes.get(attrName);
    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>objectName</tt>.
     *
     * @return property value of objectName
     */
    public ObjectName getObjectName() {
        return objectName;
    }

    /**
     * Setter method for property <tt>objectName</tt>.
     *
     * @param objectName value to be assigned to property objectName
     */
    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    /**
     * Getter method for property <tt>domain</tt>.
     *
     * @return property value of domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Setter method for property <tt>domain</tt>.
     *
     * @param domain value to be assigned to property domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Getter method for property <tt>version</tt>.
     *
     * @return property value of version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Setter method for property <tt>version</tt>.
     *
     * @param version value to be assigned to property version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Getter method for property <tt>extensionProperties</tt>.
     *
     * @return property value of extensionProperties
     */
    public Map<String, String> getExtensionProperties() {
        return extensionProperties;
    }

    /**
     * Setter method for property <tt>extensionProperties</tt>.
     *
     * @param extensionProperties value to be assigned to property extensionProperties
     */
    public void setExtensionProperties(Map<String, String> extensionProperties) {
        this.extensionProperties = extensionProperties;
    }

    /**
     * Getter method for property <tt>attributes</tt>.
     *
     * @return property value of attributes
     */
    public Map<String, DistributedResourceAttribute> getAttributes() {
        return attributes;
    }

    /**
     * Setter method for property <tt>attributes</tt>.
     *
     * @param attributes value to be assigned to property attributes
     */
    public void setAttributes(Map<String, DistributedResourceAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc</tt>.
     *
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ObjectNameBuilder.buildString(this);
    }

    /**
     * Getter method for property <tt>implementation</tt>.
     *
     * @return property value of implementation
     */
    public Object getImplementation() {
        return implementation;
    }

    /**
     * Setter method for property <tt>implementation</tt>.
     *
     * @param implementation value to be assigned to property implementation
     */
    public void setImplementation(Object implementation) {
        this.implementation = implementation;
    }

    /**
     * Getter method for property <tt>app</tt>.
     *
     * @return property value of app
     */
    public String getApp() {
        return app;
    }

    /**
     * Setter method for property <tt>app</tt>.
     *
     * @param app value to be assigned to property app
     */
    public void setApp(String app) {
        this.app = app;
    }

    /**
     * Getter method for property <tt>beforeUpdateMethod</tt>.
     *
     * @return property value of beforeUpdateMethod
     */
    public Method getBeforeUpdateMethod() {
        return beforeUpdateMethod;
    }

    /**
     * Setter method for property <tt>beforeUpdateMethod</tt>.
     *
     * @param beforeUpdateMethod value to be assigned to property beforeUpdateMethod
     */
    public void setBeforeUpdateMethod(Method beforeUpdateMethod) {
        this.beforeUpdateMethod = beforeUpdateMethod;
    }

    /**
     * Getter method for property <tt>afterUpdateMethod</tt>.
     *
     * @return property value of afterUpdateMethod
     */
    public Method getAfterUpdateMethod() {
        return afterUpdateMethod;
    }

    /**
     * Setter method for property <tt>afterUpdateMethod</tt>.
     *
     * @param afterUpdateMethod value to be assigned to property afterUpdateMethod
     */
    public void setAfterUpdateMethod(Method afterUpdateMethod) {
        this.afterUpdateMethod = afterUpdateMethod;
    }
}
/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.model;

import java.lang.reflect.Method;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * 分布式资源中的具体的属性信息。
 *
 * <p>比如帐务系统有一个分布式资源：提供WS的Http Transport能力的JettyHttpTransportComponent，改资源有以下属性需要监控和配置
 * <ul>
 * <li> maxThreads:设置Jetty容器处理请求的最大线程数
 * <li> maxIdeleTimeMs:设置Jetty容器处理请求的线程闲置时间
 * <li> minThreads:设置Jetty容器处理请求的最小线程数
 * </ul>
 *
 * <p>对于上面的“maxThreades”就是分布式资源的属性对象
 * <li>ObjectName为 ：Alipay.SOFA.Service:name=com.alipay.sofa.service.transport.http.impl.JettyHttpTransportComponent@maxThreads,version=1.0
 * <li>ObjectValue为 ：300:update_policy=Lazy,update_policy_lazytime=30,persist_policy=OnUpdate
 *
 * @author xichen.lxw
 * @version $Id: DistributedResourceAttribute.java, v 0.1 2020-10-06 11:24 上午 xichen.lxw Exp $$
 */
public class DistributedResourceAttribute {
    // base
    /**
     * ObjectName中的name属性,全局唯一
     */
    private String id;
    /**
     * ObjectName
     */
    private ObjectName objectName;
    /**
     * 分布式资源对象
     */
    private DistributedResource resource;
    /**
     * 属性名称
     */
    private String attrName;
    /**
     * 属性读方法
     */
    private Method readerMethod;
    /**
     * 属性写方法
     */
    private Method writerMethod;
    /**
     * 读属性描述
     */
    private String readDesc;
    /**
     * 写属性描述
     */
    private String writeDesc;
    private String desc;
    /**
     * 全局管控属性
     */
    private String globalScope;

    /**
     * 分布式资源中的具体的属性信息构造方法
     *
     * @param attrName
     */
    public DistributedResourceAttribute(String attrName) {
        this.attrName = attrName;
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
     * Getter method for property <tt>resource</tt>.
     *
     * @return property value of resource
     */
    public DistributedResource getResource() {
        return resource;
    }

    /**
     * Setter method for property <tt>resource</tt>.
     *
     * @param resource value to be assigned to property resource
     */
    public void setResource(DistributedResource resource) {
        this.resource = resource;
    }

    /**
     * Getter method for property <tt>attrName</tt>.
     *
     * @return property value of attrName
     */
    public String getAttrName() {
        return attrName;
    }

    /**
     * Setter method for property <tt>attrName</tt>.
     *
     * @param attrName value to be assigned to property attrName
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    /**
     * Getter method for property <tt>readerMethod</tt>.
     *
     * @return property value of readerMethod
     */
    public Method getReaderMethod() {
        return readerMethod;
    }

    /**
     * Setter method for property <tt>readerMethod</tt>.
     *
     * @param readerMethod value to be assigned to property readerMethod
     */
    public void setReaderMethod(Method readerMethod) {
        this.readerMethod = readerMethod;
    }

    /**
     * Getter method for property <tt>writerMethod</tt>.
     *
     * @return property value of writerMethod
     */
    public Method getWriterMethod() {
        return writerMethod;
    }

    /**
     * Setter method for property <tt>writerMethod</tt>.
     *
     * @param writerMethod value to be assigned to property writerMethod
     */
    public void setWriterMethod(Method writerMethod) {
        this.writerMethod = writerMethod;
    }

    /**
     * Getter method for property <tt>readDesc</tt>.
     *
     * @return property value of readDesc
     */
    public String getReadDesc() {
        return readDesc;
    }

    /**
     * Setter method for property <tt>readDesc</tt>.
     *
     * @param readDesc value to be assigned to property readDesc
     */
    public void setReadDesc(String readDesc) {
        this.readDesc = readDesc;
    }

    /**
     * Getter method for property <tt>writeDesc</tt>.
     *
     * @return property value of writeDesc
     */
    public String getWriteDesc() {
        return writeDesc;
    }

    /**
     * Setter method for property <tt>writeDesc</tt>.
     *
     * @param writeDesc value to be assigned to property writeDesc
     */
    public void setWriteDesc(String writeDesc) {
        this.writeDesc = writeDesc;
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

    // ~~~ 共用方法

    /**
     * 判断该属性是否可读
     *
     * @return
     */
    public boolean isRead() {
        return this.readerMethod != null;
    }

    /**
     * 判断该属性是否可写
     *
     * @return
     */
    public boolean isWrite() {
        return this.writerMethod != null;
    }

    /**
     * 根据属性对象，构建与之对应的ObjectName。
     *
     * Alipay.SOFA.Arch:name=com.alipay.sofa.service.transport.http.impl.JettyHttpTransportComponent@maxThreads,version=1.0
     *
     * @return
     * @throws NullPointerException
     * @throws MalformedObjectNameException
     */
    public ObjectName buildObjectName() throws MalformedObjectNameException {
        return ObjectNameBuilder.build(this);
    }

    /**
     * Getter method for property <tt>globalScope</tt>.
     *
     * @return property value of globalScope
     */
    public String getGlobalScope() {
        return globalScope;
    }

    /**
     * Setter method for property <tt>globalScope</tt>.
     *
     * @param globalScope value to be assigned to property globalScope
     */
    public void setGlobalScope(String globalScope) {
        this.globalScope = globalScope;
    }

}
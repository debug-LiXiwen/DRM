/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.model;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * ObjectName 构建器，可以根据分布式资源和属性来构建ObjectName。
 *
 * @author xichen.lxw
 * @version $Id: ObjectNameBuilder.java, v 0.1 2020-10-06 11:28 上午 xichen.lxw Exp $$
 */
public class ObjectNameBuilder {
    /**
     * 根据DistributedResource构建ObjectName对象。
     *
     * @param distributedResource
     * @return
     * @throws MalformedObjectNameException
     */
    public static ObjectName build(DistributedResource distributedResource)
        throws MalformedObjectNameException {
        if (distributedResource.getObjectName() != null) {
            return distributedResource.getObjectName();
        }
        ObjectName objectName = new ObjectName(buildString(distributedResource));
        distributedResource.setObjectName(objectName);
        return objectName;
    }

    /**
     * 构建资源ObjectName的字符串。
     *
     * @param distributedResource
     * @return
     */
    public static String buildString(DistributedResource distributedResource) {

        return distributedResource.getDomain() + ":name=" + distributedResource.getId()
            + ",version=" + distributedResource.getVersion();
    }

    /**
     * 根据DistributedResourceAttribute构建ObjectName对象。
     *
     * @param distributedResourceAttribute
     * @return
     * @throws MalformedObjectNameException
     */
    public static ObjectName build(DistributedResourceAttribute distributedResourceAttribute)
        throws MalformedObjectNameException {
        if (distributedResourceAttribute.getObjectName() != null) {
            return distributedResourceAttribute.getObjectName();
        }

        return new ObjectName(buildString(distributedResourceAttribute));
    }

    /**
     * Global情况下根据DistributedResourceAttribute构建ObjectName对象。
     *
     * @param distributedResourceAttribute
     * @return
     * @throws MalformedObjectNameException
     */
    public static ObjectName buildGlobal(DistributedResourceAttribute distributedResourceAttribute)
        throws MalformedObjectNameException {
        if (distributedResourceAttribute.getObjectName() != null) {
            return distributedResourceAttribute.getObjectName();
        }

        return new ObjectName(buildStringGlobal(distributedResourceAttribute));
    }

    /**
     * 根据DistributedResourceAttribute构建ObjectName对象的字符串。
     *
     * @param distributedResourceAttribute
     * @return
     */
    public static String buildString(DistributedResourceAttribute distributedResourceAttribute) {

        DistributedResource distributedResource = distributedResourceAttribute.getResource();

        StringBuilder sb = new StringBuilder(distributedResource.getDomain());
        sb.append(":name=");
        sb.append(distributedResource.getId());
        sb.append(".");
        sb.append(distributedResourceAttribute.getAttrName());
        sb.append(",version=");
        sb.append(distributedResource.getVersion());

        return sb.toString();
    }

    /**
     * 根据DistributedResourceAttribute构建Global范围的ObjectName对象的字符串。
     *
     * @param distributedResourceAttribute
     * @return
     */
    public static String buildStringGlobal(DistributedResourceAttribute distributedResourceAttribute) {

        DistributedResource distributedResource = distributedResourceAttribute.getResource();

        StringBuilder sb = new StringBuilder(DistributedResourceConstant.PREFIX
            + distributedResourceAttribute.getGlobalScope());
        sb.append(":name=");
        sb.append(distributedResource.getId());
        sb.append(".");
        sb.append(distributedResourceAttribute.getAttrName());
        sb.append(",version=");
        sb.append(distributedResource.getVersion());

        return sb.toString();
    }

    /**
     * 根据DistributedResourceAttribute构建Confreg DataId对象。
     *
     * @param distributedResourceAttribute
     * @return
     */
    public static String buildDataId(DistributedResourceAttribute distributedResourceAttribute,
        boolean groupManageable) {
        // 可以进行校验
        ObjectName objectName;
        try {
            objectName = build(distributedResourceAttribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return objectName.toString();
    }

    /**
     * 根据DistributedResource构建Confreg DataId对象。
     *
     * @param distributedResource
     * @return
     * @throws MalformedObjectNameException
     */
    public static String buildDataId(DistributedResource distributedResource)
        throws MalformedObjectNameException {
        // 可以进行校验
        ObjectName objectName = build(distributedResource);

        return objectName.toString();
    }
}
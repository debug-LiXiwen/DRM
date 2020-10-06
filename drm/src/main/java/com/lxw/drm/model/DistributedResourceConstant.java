/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.model;

/**
 * 分布式资源管理常量类。
 *
 * @author xichen.lxw
 * @version $Id: DistributedResourceConstant.java, v 0.1 2020-10-06 11:29 上午 xichen.lxw Exp $$
 */
public interface DistributedResourceConstant {
    // ===========================通用常量================================
    /** 默认版本 */
    String  DEFAULT_VERSION                 = "3.0";
    /** Jar包版本 */
    String  JAR_VERSION                     = "3.5.0";
    /** Jar包版本Key */
    String  JAR_VERSION_KEY                 = "jarVersion";

    String  CMD_PREFIX                      = "CMD:";
    String  DRM_SUFFIX                      = "@DRM";
    String  PREFIX                          = "Lxw.";

    /** 远程数据加载策略 */
    String  REMOTE_STRATEGY                 = "R";
    /** 本地数据加载策略 */
    String  LOCAL_STRATEGY                  = "L";

    /** 代表远程数据加载策略的后缀 */
    String  REMOTE_SUFFIX                   = ":" + REMOTE_STRATEGY + DRM_SUFFIX;
    /** 代表本地数据加载策略的后缀 */
    String  LOCAL_SUFFIX                    = ":" + LOCAL_STRATEGY + DRM_SUFFIX;
    /**  Sofa配置文件中代表DRM Data服务器REST服务地址的Key */
    String  ZDRMDATA_REST_URL_KEY           = "zdrmdata_rest_url";

    /** 全局是否启动DRM */
    String  DRM_ENABLED_SYSTEM_PROPERTY_KEY = "drm_enabled";

    /** 只有显式传false，才禁用DRM */
    boolean DRM_ENABLED                     = !"false".equalsIgnoreCase(System
        .getProperty(DRM_ENABLED_SYSTEM_PROPERTY_KEY));

    /** 默认的依赖级别 */
    String  DRM_DEFAULT_DEPENDENCY_KEY      = "drm_default_dependency";

    /** 异步设值情况下，延迟的时间 */
    String  DRM_LAZY_INIT_TIME_KEY          = "drm_lazy_init_time";

    /** 版本1.0在dataId中的特征,用于区分1.0与3.0版本的客户端 */
    String  VERSION_ONE_TRAIT               = ",version=1.0";

    // =============================ConfigServer常量========================
    /* 默认组，主要用在Confreg */
    String  DEFAULT_CONFREG_GROUP           = "SOFA.DRM";
    // TODO 为了和服务的配置域分开，所有使用SOFA.DRM，但是必须要考虑自定义组的场景

    /* 订阅者名称前缀 */
    String  SUBSCRIBER_PREFIX               = "SofaDRMSubscriber-";

    /* 发布者名称前缀 */
    String  PUBLISHER_PREFIX                = "SofaDRMPublisher-";

    // =============================JMX常量================================
    /* 默认RIM端口*/
    int     DEFAULT_RMI_PORT                = 9875;

    /** 默认的连接器url */
    String  DEFAULT_RMI_SERVICE_URL         = "service:jmx:rmi://localhost/jndi/rmi://localhost:"
        + DEFAULT_RMI_PORT + "/sofaconnector";

    String  DEFAULT_RMI_OBJECTNAME          = "connector:name=rmi";
    // =============================TR常量================================
    /* 默认RIM端口*/
    int     DEFAULT_TR_PORT                 = 9877;
    // TR 端口的配置的系统变量的 KEY
    String  DRM_TR_PORT_SYSTEM_PROPERTY_KEY = "drm_tr_port";

    // ===========================配置中心订阅相关=============================
    //订阅分组
    String  CONFREG_DRM_GROUP               = "DRM";
    //zdrmdata rest接口地址
    String  ZDRMDATA_REST_URL_DATA_ID       = "com.alipay.zdrmdata.rest.url@DRM";
    //zdrmdata心跳检测地址
    String  ZDRMDATA_HEARTBEAT_URL_DATA_ID  = "com.alipay.zdrmdata.heartbeat.url@DRM";
    //客户端所在的zone名
    String  DRM_CLIENT_ZONE                 = "com.alipay.drm.client.zone@DRM";
}
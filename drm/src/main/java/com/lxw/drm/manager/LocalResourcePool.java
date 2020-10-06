/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lxw.drm.model.DistributedResource;

/**
 * 资源组件池
 *
 * @author xichen.lxw
 * @version $Id: LocalResourcePool.java, v 0.1 2020-10-06 11:58 上午 xichen.lxw Exp $$
 */
public class LocalResourcePool {
    /**
     * The Resources.
     * <appName, <id, DistributedResource>>,首先是根据应用名获取这个应用的资源配置信息表
     */
    Map<String, Map<String, DistributedResource>> resources = new ConcurrentHashMap<String, Map<String, DistributedResource>>();

    /**
     * 注册资源到本地池
     * @param id the id
     * @param resource the resource
     */
    public synchronized void registerResource(String id, DistributedResource resource) {
        String appName = resource.getApp();
        Map<String, DistributedResource> appMap = resources.get(appName);
        if (appMap == null) {
            appMap = new ConcurrentHashMap<String, DistributedResource>();
            resources.put(appName, appMap);
        }
        //如果已经有相同的DRM存在，则不覆盖。
        if (!appMap.containsKey(id)) {
            appMap.put(id, resource);
        }
    }

    /**
     * 在本地池中查找所有资源
     * @return
     */
    public Collection<DistributedResource> findAllResources() {
        Collection<DistributedResource> list = new ArrayList<DistributedResource>();
        for (Map<String, DistributedResource> appMap : resources.values()) {
            list.addAll(appMap.values());
        }
        return list;
    }

    /**
     * 按照应用名和资源ID获取本地资源
     *
     * @param appName the app name
     * @param id the id
     * @return distributed resource
     */
    public synchronized DistributedResource findResourceByAppAndId(String appName, String id) {
        Map<String, DistributedResource> appMap = resources.get(appName);
        if (appMap != null) {
            return appMap.get(id);
        } else {
            return null;
        }
    }

    /**
     * 本地库中注销资源
     *
     * @param appName the app name
     * @param id the id
     * @return distributed resource
     */
    public synchronized DistributedResource removeResourceByAppAndId(String appName, String id) {
        Map<String, DistributedResource> appMap = resources.get(appName);
        if (appMap != null) {
            return appMap.remove(id);
        } else {
            return null;
        }
    }

    /**
     * Find resources by id collection.
     *
     * @param id the id
     * @return the collection
     */
    public synchronized Collection<DistributedResource> findResourcesById(String id) {
        Collection<DistributedResource> list = new ArrayList<DistributedResource>();
        for (Map<String, DistributedResource> appMap : resources.values()) {
            DistributedResource resource = appMap.get(id);
            if (resource != null) {
                list.add(resource);
            }
        }
        return list;
    }
}
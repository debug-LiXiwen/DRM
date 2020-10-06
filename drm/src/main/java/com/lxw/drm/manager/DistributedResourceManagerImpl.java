/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.manager;

import java.util.Collection;

import com.lxw.drm.DRMClient;
import com.lxw.drm.DistributedResourceManager;
import com.lxw.drm.builder.DistributedResourceBuilder;
import com.lxw.drm.model.DistributedResource;
import com.lxw.drm.model.DistributedResourceConstant;

/**
 * 分布式资源管理器实现。
 *
 * @author xichen.lxw
 * @version $Id: DistributedResourceManagerImpl.java, v 0.1 2020-10-06 11:56 上午 xichen.lxw Exp $$
 */
public class DistributedResourceManagerImpl implements DistributedResourceManager {
    /**
     * 分布式资源管理器构造方法
     */
    public DistributedResourceManagerImpl() {
        // 设置单例为自己
        DRMClient.setInstance(this);
    }

    private final DistributedResourceBuilder distributedResourceBuilder = new DistributedResourceBuilder();
    private final LocalResourcePool localResourcePool = new LocalResourcePool();

    /**
     * 注册分布式资源
     * @see DistributedResourceManager#register(Object, String)
     */
    @Override
    public DistributedResource register(Object resourceObject, String app) {
        if (!DistributedResourceConstant.DRM_ENABLED) {
            System.out.println("DRM disabled, register failed! ");
            return null;
        }
        DistributedResource distributedResource = buildDistributedResource(resourceObject, app);
        return realRegister(distributedResource);
    }

    /**
     *
     * @see DistributedResourceManager#register(Object, String, String)
     */
    @Override
    public DistributedResource register(Object resourceObject, String app, String id) {
        if (!DistributedResourceConstant.DRM_ENABLED) {
            System.out.println("DRM disabled, register failed! ");
            return null;
        }
        DistributedResource distributedResource = buildDistributedResource(resourceObject, app);
        //覆盖掉ID属性
        assert distributedResource != null;
        // 初始化drm资源
        distributedResource.setId(id);
        return realRegister(distributedResource);
    }

    private DistributedResource buildDistributedResource(Object resourceObject, String app) {
        if (resourceObject == null) {
            System.out.println("Null resource object skipped!");
            return null;
        }

        System.out.println("Start building distributed resource ...");

        return distributedResourceBuilder.build(resourceObject, app);
    }

    private DistributedResource realRegister(DistributedResource distributedResource) {

        System.out.println("Register distributed resource [" + distributedResource + "]");

        try {
            // 注册到本地
            localResourcePool.registerResource(distributedResource.getId(), distributedResource);

            // todo 注册到远程，待实现

        } catch (RuntimeException e) {
            System.err.println("Register distributed resource [" + distributedResource
                + "] exception" + e.getMessage());
            throw e;
        }

        System.out.println("Register distributed resource [" + distributedResource
                + "] completed");

        return distributedResource;
    }

    /**
     * @see DistributedResourceManager#unregister(String, String)
     */
    @Override
    public void unregister(String app, String id) {
        System.out.println("Unregister distributed resource [" + app + "][" + id + "]");
        // 本地库中删除
        DistributedResource distributedResource = localResourcePool.removeResourceByAppAndId(app,
            id);
        // todo 远程库删除

    }

    /**
     * @see DistributedResourceManager#findAllResources()
     */
    @Override
    public Collection<DistributedResource> findAllResources() {
        return localResourcePool.findAllResources();
    }

    /**
     * @see DistributedResourceManager#findResourceByAppAndId(String, String)
     */
    @Override
    public DistributedResource findResourceByAppAndId(String appName, String id) {
        return localResourcePool.findResourceByAppAndId(appName, id);
    }

}
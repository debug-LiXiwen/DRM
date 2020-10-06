/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm;

import java.util.Collection;
import com.lxw.drm.model.DistributedResource;

/**
 * @author xichen.lxw
 * @version $Id: DistributedResourceManager.java, v 0.1 2020-10-06 11:53 上午 xichen.lxw Exp $$
 */
public interface DistributedResourceManager {
    /**
     * Register an original Java object to a distributed resource.
     *
     * @param resourceObject the original Java object with DRM Annotations
     * @param app application name
     */
    DistributedResource register(Object resourceObject, String app);

    /**
     * Register an original Java object to a distributed resource.
     * The parameter <tt>id</tt> will overwrite the id of ,
     * so that you can specify a resource identify dynamically at runtime.
     *
     * @param resourceObject the original Java object with DRM Annotations
     * @param app application name
     * @param id resource identify
     * @return distributed resource model
     */
    DistributedResource register(Object resourceObject, String app, String id);

    /**
     * Find all distributed resource model in this {@link DistributedResourceManager} instance.
     * Usually you needn't to get a distributed resource model.
     * Just use the original Java object you registered.
     *
     * @return all distributed resource models
     */
    Collection<DistributedResource> findAllResources();

    /**
     * Find a distributed resource model by application name and resource identify.
     * Usually you needn't to get a distributed resource model.
     * Just use the original Java object you registered.
     *
     * @param appName application name
     * @param id resource identify
     * @return distributed resource model
     */
    DistributedResource findResourceByAppAndId(String appName, String id);

    /**
     * Unregister a distributed resource.
     * Usually you needn't to unregister it expressly.
     *
     * @param app application name
     * @param id resource identify
     */
    void unregister(String app, String id);

}
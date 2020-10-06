/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm;

import com.lxw.drm.manager.DistributedResourceManagerImpl;

/**
 * @author xichen.lxw
 * @version $Id: DRMClient.java, v 0.1 2020-10-06 11:52 上午 xichen.lxw Exp $$
 */
public class DRMClient {
    /**
     * The singleton
     */
    static DistributedResourceManager instance;

    /**
     * Get the singleton of {@link DistributedResourceManager}
     *
     * @return The {@link DistributedResourceManager} instance
     */
    public synchronized static DistributedResourceManager getInstance() {
        if (instance == null) {
            instance = new DistributedResourceManagerImpl();
        }
        return instance;
    }

    /**
     * Set the singleton.
     * Usually you needn't to call this method directly.
     * Just use <tt>getInstance</tt>.
     *
     * @param ins The instance
     */
    public synchronized static void setInstance(DistributedResourceManager ins) {
        instance = ins;
    }

}
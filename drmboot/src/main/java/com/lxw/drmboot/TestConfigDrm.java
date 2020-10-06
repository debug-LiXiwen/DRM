/**
 * lxw.com. Inc.
 * Copyright (c) 2017-2020 All Rights Reserved.
 */
package com.lxw.drmboot;

import com.lxw.drm.DRMClient;
import com.lxw.drm.annotation.AfterUpdate;
import com.lxw.drm.annotation.BeforeUpdate;
import com.lxw.drm.annotation.DAttribute;
import com.lxw.drm.annotation.DResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author xichen.lxw
 * @version $Id: TestConfigDrm.java, v 0.1 2020-10-06 17:49 xichen.lxw Exp $$
 */
@Service
@DResource(id = "com.lxw.drmboot.TestConfigDrm")
public class TestConfigDrm {

    /**
     * 是否走新链路，默认true
     */
    @DAttribute
    public String enableNewWayStr = "true";
    public static boolean enableNewWay = true;

    @PostConstruct
    public void init() {
        DRMClient.getInstance().register(this, "drmboot");
        System.out.println("register DRM Resource");
    }

    @BeforeUpdate
    public void before(String key, Object value) {
        System.out.println("DRM.beforeUpdate;key=" + key + ",value=" + value);
    }

    @AfterUpdate
    public void after(String key, Object value) {
        try {
            if ("enableNewWayStr".equals(key)) {
                String val = (String) value;
                enableNewWay = Boolean.parseBoolean(val);
                System.out.println("DRM.afterUpdate enableNewWayStr update:" + enableNewWay);
            }
        } catch (Exception e) {
            System.err.println("DRM.afterUpdate Cast Type occurs error, errMsg = " + e.getMessage());
        }

    }

    public String getEnableNewWayStr() {
        return enableNewWayStr;
    }

    public void setEnableNewWayStr(String enableNewWayStr) {
        this.enableNewWayStr = enableNewWayStr;
    }

}

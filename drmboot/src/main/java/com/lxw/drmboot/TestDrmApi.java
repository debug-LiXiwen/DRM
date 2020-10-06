/**
 * lxw.com. Inc.
 * Copyright (c) 2017-2020 All Rights Reserved.
 */
package com.lxw.drmboot;

import com.lxw.drm.update.ResourceAttributeUpdater;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xichen.lxw
 * @version $Id: TestDrmApi.java, v 0.1 2020-10-06 17:48 xichen.lxw Exp $$
 */
@RestController
public class TestDrmApi {

    @GetMapping("/get")
    public String t1() {
        return TestConfigDrm.enableNewWay + "";
    }

    @GetMapping("/put/{id}/{key}/{value}")
    public void t2(@PathVariable("id") String id, @PathVariable("key") String key, @PathVariable("value") String value) throws Exception {

        ResourceAttributeUpdater.updateResource(id, key, value,"drmboot");

    }

}

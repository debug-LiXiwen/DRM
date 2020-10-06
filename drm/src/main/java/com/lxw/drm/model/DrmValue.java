/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.model;

/**
 * @author xichen.lxw
 * @version $Id: DrmValue.java, v 0.1 2020-10-06 11:49 上午 xichen.lxw Exp $$
 */
public class DrmValue {
    /**
     * 用字符串表示的DRM值
     */
    private String stringValue;

    /**
     * DRM值版本。
     * -1代表刚启动，还未拉数据。
     * 0代表拉数据结果为空，即从来没推过该DRM
     */
    private int    version = -1;

    /**
     * Getter method for property <tt>stringValue</tt>.
     *
     * @return property value of stringValue
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Setter method for property <tt>stringValue</tt>.
     *
     * @param stringValue value to be assigned to property stringValue
     */
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * Getter method for property <tt>version</tt>.
     *
     * @return property value of version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Setter method for property <tt>version</tt>.
     *
     * @param version value to be assigned to property version
     */
    public void setVersion(int version) {
        this.version = version;
    }
}
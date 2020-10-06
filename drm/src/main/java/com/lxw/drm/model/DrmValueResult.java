/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lxw.drm.model;

/**
 * 直接拉取DRM值的结果封装类。
 *
 * @author xichen.lxw
 * @version $Id: DrmValueResult.java, v 0.1 2020-10-06 11:54 上午 xichen.lxw Exp $$
 */
public class DrmValueResult {
    /**
     * 是否成功。
     * 如果为true，则resultCode为 "00"，这时通常version应大于等于0，但也有极小概率得到的数据格式不符合预期，version也会置为-1;
     * 如果为false，则resultCode非 "00"，version为-1。
     */
    private boolean success;

    /**
     * 结果码
     * 00 请求成功
     * 01 缺少zone信息
     * 02 缺少zdrmdataRestUrl信息
     * 03 http请求异常
     */
    private String  resultCode;

    /**
     * 提示信息
     */
    private String  resultMessage;

    /**
     * 用字符串表示的DRM值
     */
    private String  stringValue;

    /**
     * DRM值版本。
     * -1说明出现系统错误，或者预期外的响应数据格式错误。
     * 0代表请求成功，但拉数据结果为空，即从来没推过该DRM
     */
    private int     version = -1;

    /**
     * Getter method for property <tt>success</tt>.
     *
     * @return property value of success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Setter method for property <tt>success</tt>.
     *
     * @param success value to be assigned to property success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Getter method for property <tt>resultCode</tt>.
     *
     * @return property value of resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Setter method for property <tt>resultCode</tt>.
     *
     * @param resultCode value to be assigned to property resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

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

    /**
     * Getter method for property <tt>resultMessage</tt>.
     *
     * @return property value of resultMessage
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Setter method for property <tt>resultMessage</tt>.
     *
     * @param resultMessage value to be assigned to property resultMessage
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
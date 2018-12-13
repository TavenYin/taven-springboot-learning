package com.gitee.taven.module.base;

import java.io.Serializable;

public class AjaxResult implements Serializable {

    private static final long serialVersionUID = 61613060714677323L;

    /**
     * ajax成功
     */
    public static final boolean SUC = true;

    /**
     * ajax失败
     */
    public static final boolean FAIL = false;

    /**
     * ajax 是否成功
     */
    private Boolean isSuccess;

    /**
     * ajax 返回消息
     */
    private String message;

    /**
     * ajax 返回的数据
     */
    private Object data;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public AjaxResult() {}

    public AjaxResult(Boolean isSuccess, String message) {
        super();
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public AjaxResult(Boolean isSuccess, Object data) {
        super();
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public AjaxResult(Boolean isSuccess, String message, Object data) {
        super();
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回ajax成功消息 和数据
     *
     * @param message
     * @param data
     * @return
     */
    public static AjaxResult successResponse(String message, Object data) {
        return new AjaxResult(SUC, message, data);
    }

    /**
     * 返回ajax成功消息
     *
     * @param message
     * @return
     */
    public static AjaxResult successResponse(String message) {
        return new AjaxResult(SUC, message);
    }

    /**
     * 返回ajax成功
     *
     * @param data
     * @return
     */
    public static AjaxResult successResponse(Object data) {
        return new AjaxResult(SUC, data);
    }

    /**
     * 返回ajax失败消息 和数据
     *
     * @param message
     * @param data
     * @return
     */
    public static AjaxResult failResponse(String message, Object data) {
        return new AjaxResult(FAIL, message, data);
    }

    /**
     * 返回ajax失败消息
     *
     * @param message
     * @return
     */
    public static AjaxResult failResponse(String message) {
        return new AjaxResult(FAIL, message);
    }

}
package com.project.lx.baseproject.base;

import com.google.gson.annotations.SerializedName;
import com.project.lx.baseproject.util.NetworkException;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/4/7 0007 13:58
 * @company:
 * @email: lx802315@163.com
 */
public class BaseEntity<E> {
    @SerializedName("code")
    private int code;
    @SerializedName("errorMessage")
    private String msg;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @SerializedName("result")
    private boolean result;

    public boolean isSuccess() {
        return result == true;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        //这里可以对返回码进行统一处理
//        NetworkException networkException = new NetworkException();
//        msg = networkException.getNetworkExceptionMessage(code);
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getResponse() {
        return response;
    }

    public void setResponse(E data) {
        this.response = data;
    }

    @SerializedName("response")
    private E response;

}

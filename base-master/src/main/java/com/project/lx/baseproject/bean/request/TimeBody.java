package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/28 11:44
 * @company:
 * @email: lx802315@163.com
 */
public class TimeBody {
    public TimeBody(int code, String targetId) {
        this.code = code;
        this.targetId = targetId;
    }

    /**
     * code : 1
     * targetId : 4028f6815cce4f2d015cce5ebadc0001
     */

    private int code;
    private String targetId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}

package com.project.lx.baseproject.bean.responses;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/28 11:44
 * @company:
 * @email: lx802315@163.com
 */
public class TimeInfo {
    public TimeInfo(long sysTime, long destroyTime) {
        this.sysTime = sysTime;
        this.destroyTime = destroyTime;
    }

    /**
     * sysTime : 1498621860054
     * destroyTime : 1498283733000
     */

    private long sysTime;
    private long destroyTime;

    public long getSysTime() {
        return sysTime;
    }

    public void setSysTime(long sysTime) {
        this.sysTime = sysTime;
    }

    public long getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime(long destroyTime) {
        this.destroyTime = destroyTime;
    }
}

package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/6 14:24
 * @company:
 * @email: lx802315@163.com
 */
public class SysUpdateSwitchBody {
    /**
     * type : 1
     * userToken : a781243d19ce4c0087f79d0c5e2a44db
     */

    private int type;
    private String userToken;

    public SysUpdateSwitchBody(int type, String userToken) {
        this.type = type;
        this.userToken = userToken;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

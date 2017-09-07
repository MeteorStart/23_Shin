package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/15 15:27
 * @company:
 * @email: lx802315@163.com
 */
public class ShowUserInfoBody {
    /**
     * userToken : 2fd8cd7a-309f-4a28-9d41-91dbe67a5621
     */

    private String userToken;

    public ShowUserInfoBody(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

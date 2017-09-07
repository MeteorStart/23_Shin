package com.project.lx.baseproject.bean.responses;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/15 14:07
 * @company:
 * @email: lx802315@163.com
 */
public class ChangePwdForCodeInfo {
    /**
     * isFirstLogin : false
     * userToken : 20aa0868-f29d-4159-8b97-8973ce62673d
     */

    private boolean isFirstLogin;
    private String userToken;

    public boolean isIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

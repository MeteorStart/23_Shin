package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 搜索相关
 * @version: V_1.0.0
 * @date: 2017/6/19 13:50
 * @company:
 * @email: lx802315@163.com
 */
public class SearchBody {
    protected String userToken;

    public SearchBody(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {

        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

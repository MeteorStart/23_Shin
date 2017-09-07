package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/26 10:37
 * @company:
 * @email: lx802315@163.com
 */
public class SubLetterBody {

    public SubLetterBody(String letterId, String userToken) {
        this.letterId = letterId;
        this.userToken = userToken;
    }

    /**
     * letterId : 4028f6815ce1f707015ce21648850000
     * userToken : 96987b0d-d253-4a42-9738-05ecf84dc0d5
     */

    private String letterId;
    private String userToken;

    public String getLetterId() {
        return letterId;
    }

    public void setLetterId(String letterId) {
        this.letterId = letterId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

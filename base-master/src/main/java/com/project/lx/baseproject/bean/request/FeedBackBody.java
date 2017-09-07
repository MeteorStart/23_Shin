package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/28 17:47
 * @company:
 * @email: lx802315@163.com
 */
public class FeedBackBody {
    /**
     * content : string
     * userToken : string
     */

    private String content;
    private String userToken;

    public FeedBackBody(String content, String userToken) {
        this.content = content;
        this.userToken = userToken;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/28 17:13
 * @company:
 * @email: lx802315@163.com
 */
public class AttentionOrcancelAttentionBody {
    public AttentionOrcancelAttentionBody(String otherId, String userToken) {
        this.otherId = otherId;
        this.userToken = userToken;
    }

    /**
     * otherId : string
     * userToken : string
     */

    private String otherId;
    private String userToken;

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

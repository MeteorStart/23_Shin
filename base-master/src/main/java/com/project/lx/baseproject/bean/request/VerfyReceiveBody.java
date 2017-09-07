package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/7 9:56
 * @company:
 * @email: lx802315@163.com
 */
public class VerfyReceiveBody {
    /**
     * letterId : string
     * userId : string
     * userToken : string
     */

    private String letterId;
    private String userId;
    private String userToken;

    public VerfyReceiveBody(String letterId, String userId, String userToken) {
        this.letterId = letterId;
        this.userId = userId;
        this.userToken = userToken;
    }

    public String getLetterId() {
        return letterId;
    }

    public void setLetterId(String letterId) {
        this.letterId = letterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

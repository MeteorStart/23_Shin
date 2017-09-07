package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/14 15:25
 * @company:
 * @email: lx802315@163.com
 */
public class GetScanMessageBody {
    public GetScanMessageBody(String letterId, String userId) {
        this.letterId = letterId;
        this.userId = userId;
    }

    /**
     * letterId : asdf
     * userId : adf
     */

    private String letterId;
    private String userId;

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
}

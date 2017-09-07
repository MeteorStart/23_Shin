package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/27 13:44
 * @company:
 * @email: lx802315@163.com
 */
public class FinishDemandBody {
    public FinishDemandBody(String demandId, String userToken) {
        this.demandId = demandId;
        this.userToken = userToken;
    }

    /**
     * demandId : string
     * userToken : string
     */

    private String demandId;
    private String userToken;

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

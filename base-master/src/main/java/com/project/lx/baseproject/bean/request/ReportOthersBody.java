package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/28 17:26
 * @company:
 * @email: lx802315@163.com
 */
public class ReportOthersBody {

    public ReportOthersBody(String content, String targetId, String userToken) {
        this.content = content;
        this.targetId = targetId;
        this.userToken = userToken;
    }

    /**
     * content : string
     * targetId : string
     * userToken : string
     */

    private String content;
    private String targetId;
    private String userToken;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

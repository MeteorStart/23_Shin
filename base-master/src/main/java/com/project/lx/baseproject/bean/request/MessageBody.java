package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/7 10:12
 * @company:
 * @email: lx802315@163.com
 */
public class MessageBody {
    /**
     * page : 0
     * pize : 0
     * type : 1
     * userToken : ca2669d18eea4f7e9b3b7892f2e4c4e7
     */

    private int page;
    private int pize;
    private int type;
    private String userToken;

    public MessageBody(int page, int pize, int type, String userToken) {
        this.page = page;
        this.pize = pize;
        this.type = type;
        this.userToken = userToken;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPize() {
        return pize;
    }

    public void setPize(int pize) {
        this.pize = pize;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

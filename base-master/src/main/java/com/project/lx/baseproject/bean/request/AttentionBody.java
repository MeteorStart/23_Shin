package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/21 17:17
 * @company:
 * @email: lx802315@163.com
 */
public class AttentionBody {

    public AttentionBody(int page, int pize, String type, String uid, String userToken) {
        this.page = page;
        this.pize = pize;
        this.type = type;
        this.uid = uid;
        this.userToken = userToken;
    }

    /**
     * page : 0
     * pize : 6
     * type : 1
     * uid : null
     * userToken : 4e73a708-c9b0-4b21-959a-6ae8c101906f
     */

    private int page;
    private int pize;
    private String type;
    private String uid;
    private String userToken;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

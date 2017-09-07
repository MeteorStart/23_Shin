package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/28 16:07
 * @company:
 * @email: lx802315@163.com
 */
public class ShowUserInformationBody {
    public ShowUserInformationBody(int page, int pize, int type, String u_id, String userToken) {
        this.page = page;
        this.pize = pize;
        this.type = type;
        this.u_id = u_id;
        this.userToken = userToken;
    }

    /**
     * page : 0
     * pize : 0
     * type : 0
     * u_id :
     * userToken : f1729778-90e6-435d-bc19-365c6f46b03c
     */

    private int page;
    private int pize;
    private int type;
    private String u_id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

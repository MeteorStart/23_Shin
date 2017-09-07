package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/29 10:00
 * @company:
 * @email: lx802315@163.com
 */
public class ShopsLetterBody {
    /**
     * page : 0
     * pize : 0
     * typeCode : 0
     * userToken : string
     */

    private int page;
    private int pize;
    private int typeCode;
    private String userToken;

    public ShopsLetterBody(int page, int pize, int typeCode, String userToken) {
        this.page = page;
        this.pize = pize;
        this.typeCode = typeCode;
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

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

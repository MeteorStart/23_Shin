package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/29 17:23
 * @company:
 * @email: lx802315@163.com
 */
public class SearchResultBody {
    public SearchResultBody(String condition, int page, int pize, int typeCode, String userToken) {
        this.condition = condition;
        this.page = page;
        this.pize = pize;
        this.typeCode = typeCode;
        this.userToken = userToken;
    }

    /**
     * condition : 星
     * page : 0
     * pize : 0
     * typeCode : 0
     * userToken : d69d5fdd-c58f-4430-9918-64d61cf7968f
     */

    private String condition;
    private int page;
    private int pize;
    private int typeCode;
    private String userToken;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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

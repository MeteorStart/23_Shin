package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/26 9:36
 * @company:
 * @email: lx802315@163.com
 */
public class ShowMyLetterBody {

    public ShowMyLetterBody(int code, int page, int pize, String userToken) {
        this.code = code;
        this.page = page;
        this.pize = pize;
        this.userToken = userToken;
    }

    /**
     * code : 1
     * page : 0
     * pize : 0
     * userToken : 96987b0d-d253-4a42-9738-05ecf84dc0d5
     */

    private int code;
    private int page;
    private int pize;
    private String userToken;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

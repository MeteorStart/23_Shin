package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/22 10:12
 * @company:
 * @email: lx802315@163.com
 */
public class ShowShopInfoBody {

    /**
     * code : 0
     * page : 0
     * pize : 0
     * shopId : string
     * userToken : string
     */

    private int code;
    private int page;
    private int pize;
    private String shopId;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

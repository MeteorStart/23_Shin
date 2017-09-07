package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/28 18:11
 * @company:
 * @email: lx802315@163.com
 */
public class ShopsLocatedBody {
    /**
     * shopName : string
     * shopSite : string
     * shopTel : string
     * userToken : string
     */

    private String shopName;
    private String shopSite;
    private String shopTel;
    private String userToken;

    public ShopsLocatedBody(String shopName, String shopSite, String shopTel, String userToken) {
        this.shopName = shopName;
        this.shopSite = shopSite;
        this.shopTel = shopTel;
        this.userToken = userToken;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopSite() {
        return shopSite;
    }

    public void setShopSite(String shopSite) {
        this.shopSite = shopSite;
    }

    public String getShopTel() {
        return shopTel;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

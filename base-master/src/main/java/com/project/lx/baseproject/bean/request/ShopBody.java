package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/20 13:50
 * @company:
 * @email: lx802315@163.com
 */
public class ShopBody {

    public ShopBody(String site, String userToken) {
        this.site = site;
        this.userToken = userToken;
    }

    /**
     * site : 河南省郑州市中原区
     * userToken : 4e73a708-c9b0-4b21-959a-6ae8c101906f
     */


    private String site;
    private String userToken;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

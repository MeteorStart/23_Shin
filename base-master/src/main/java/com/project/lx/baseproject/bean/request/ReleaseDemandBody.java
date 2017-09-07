package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/21 16:14
 * @company:
 * @email: lx802315@163.com
 */
public class ReleaseDemandBody {

    public ReleaseDemandBody(String content, String shopId, String userToken) {
        this.content = content;
        this.shopId = shopId;
        this.userToken = userToken;
    }

    /**
     * content : string
     * shopId : string
     * userToken : string
     */

    private String content;
    private String shopId;
    private String userToken;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

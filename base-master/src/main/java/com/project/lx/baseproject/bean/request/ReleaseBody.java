package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/21 9:38
 * @company:
 * @email: lx802315@163.com
 */
public class ReleaseBody {

    /**
     * addresseeId : string
     * number : string
     * shopId : string
     * title : string
     * topicId : string
     * <p>
     * userToken : string
     */


    public ReleaseBody(String addresseeId, String number, String shopId, String title, String topicId, String userToken) {
        this.addresseeId = addresseeId;
        this.number = number;
        this.shopId = shopId;
        this.title = title;
        this.topicId = topicId;
        this.userToken = userToken;
    }

    private String addresseeId;
    private String number;
    private String shopId;
    private String title;
    private String topicId;
    private String userToken;

    public String getAddresseeId() {
        return addresseeId;
    }

    public void setAddresseeId(String addresseeId) {
        this.addresseeId = addresseeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

package com.project.lx.baseproject.bean.responses;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/22 9:41
 * @company:
 * @email: lx802315@163.com
 */
public class ShopOnMapInfo {

    /**
     * shopId : 001
     * shopName : 星巴克
     * shopPhone : 18703801111
     * shopLongitude : 113.600973
     * shopLatitude : 34.797342
     */

    private String shopId;
    private String shopName;
    private String shopPhone;
    private double shopLongitude;
    private double shopLatitude;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public double getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(double shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public double getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(double shopLatitude) {
        this.shopLatitude = shopLatitude;
    }
}

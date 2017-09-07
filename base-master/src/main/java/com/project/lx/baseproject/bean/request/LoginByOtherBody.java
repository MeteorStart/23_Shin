package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/5 14:44
 * @company:
 * @email: lx802315@163.com
 */
public class LoginByOtherBody {
    /**
     * accountSource : 1
     * deviceType : 2
     * headImg : 1
     * openId : DFEDB32CE718D4DBF5D5955B41B05595
     * username : 1
     */

    private int accountSource;
    private int deviceType;
    private String headImg;
    private String openId;
    private String username;

    public LoginByOtherBody(int accountSource, int deviceType, String headImg, String openId, String username) {
        this.accountSource = accountSource;
        this.deviceType = deviceType;
        this.headImg = headImg;
        this.openId = openId;
        this.username = username;
    }

    public int getAccountSource() {
        return accountSource;
    }

    public void setAccountSource(int accountSource) {
        this.accountSource = accountSource;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

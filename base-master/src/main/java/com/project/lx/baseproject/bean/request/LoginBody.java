package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 登录请求实体类
 * @version: V_1.0.0
 * @date: 2017/6/14 11:37
 * @company:
 * @email: lx802315@163.com
 */
public class LoginBody {

    /**
     * deviceType : 1
     * password : 123456
     * phone : 18703801693
     */

    private int deviceType;
    private String password;
    private String phone;

    public LoginBody(int deviceType, String password, String phone) {
        this.deviceType = deviceType;
        this.password = password;
        this.phone = phone;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

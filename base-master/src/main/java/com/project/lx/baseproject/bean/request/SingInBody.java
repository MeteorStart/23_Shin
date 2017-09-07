package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/15 11:08
 * @company:
 * @email: lx802315@163.com
 */
public class SingInBody {
    /**
     * code : string
     * deviceType : 0
     * password : string
     * phone : string
     */

    private String code;
    private int deviceType;
    private String password;
    private String phone;

    public SingInBody(String code, int deviceType, String password, String phone) {
        this.code = code;
        this.deviceType = deviceType;
        this.password = password;
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/14 17:58
 * @company:
 * @email: lx802315@163.com
 */
public class VerifyPhoneBody {
    /**
     * phone : 12345678900
     */

    private String phone;

    public VerifyPhoneBody(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

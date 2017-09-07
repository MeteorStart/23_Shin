package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/15 10:08
 * @company:
 * @email: lx802315@163.com
 */
public class GetCodeBody {

    public GetCodeBody(String phone, int type) {
        this.phone = phone;
        this.type = type;
    }

    /**
     * phone : string
     * type : 0
     */

    private String phone;
    private int type;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

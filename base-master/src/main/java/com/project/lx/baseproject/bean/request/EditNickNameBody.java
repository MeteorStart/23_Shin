package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/4 13:51
 * @company:
 * @email: lx802315@163.com
 */
public class EditNickNameBody {

    public EditNickNameBody(String headImg, String nickName, int sex, String userToken) {
        this.headImg = headImg;
        this.nickName = nickName;
        this.sex = sex;
        this.userToken = userToken;
    }

    /**
     * headImg : string
     * nickName : string

     * sex : 0
     * userToken : string
     */

    private String headImg;
    private String nickName;
    private int sex;
    private String userToken;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

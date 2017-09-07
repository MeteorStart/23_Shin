package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/16 9:53
 * @company:
 * @email: lx802315@163.com
 */
public class ChangePwForOldPwdBody {
    /**
     * affirmPwd : string
     * newPwd : string
     * password : string
     * userToken : string
     */

    private String newPwd;
    private String password;
    private String userToken;
    private String affirmPwd;

    public ChangePwForOldPwdBody(String affirmPwd, String newPwd, String password, String userToken) {
        this.affirmPwd = affirmPwd;
        this.newPwd = newPwd;
        this.password = password;
        this.userToken = userToken;
    }

    public String getAffirmPwd() {
        return affirmPwd;
    }

    public void setAffirmPwd(String affirmPwd) {
        this.affirmPwd = affirmPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}

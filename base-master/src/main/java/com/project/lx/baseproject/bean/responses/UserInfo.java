package com.project.lx.baseproject.bean.responses;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/14 9:23
 * @company:
 * @email: lx802315@163.com
 */
public class UserInfo {

    /**
     * isFirstLogin : false
     * userToken : 63bc5f89-38b7-4608-82e5-3d8b77d5641f
     * userInfor : {"userId":"4028f6815cce4f2d015cce64acec0007","headImg":null,"nickName":"155****5070","site":null,"attentionCount":1,"fansCount":1,"sex":0,"phone":"15515395070","letterID":"81885167","role":0}
     */

    private boolean isFirstLogin;
    private String userToken;
    private UserInforBean userInfor;

    public boolean isIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public UserInforBean getUserInfor() {
        return userInfor;
    }

    public void setUserInfor(UserInforBean userInfor) {
        this.userInfor = userInfor;
    }

    public static class UserInforBean {
        /**
         * userId : 4028f6815cce4f2d015cce64acec0007
         * headImg : null
         * nickName : 155****5070
         * site : null
         * attentionCount : 1
         * fansCount : 1
         * sex : 0
         * phone : 15515395070
         * letterID : 81885167
         * role : 0
         */

        private String userId;
        private String headImg;
        private String nickName;
        private String site;
        private int attentionCount;
        private int fansCount;
        private int sex;
        private String phone;
        private String letterID;
        private int role;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

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

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public int getAttentionCount() {
            return attentionCount;
        }

        public void setAttentionCount(int attentionCount) {
            this.attentionCount = attentionCount;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLetterID() {
            return letterID;
        }

        public void setLetterID(String letterID) {
            this.letterID = letterID;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }
    }
}

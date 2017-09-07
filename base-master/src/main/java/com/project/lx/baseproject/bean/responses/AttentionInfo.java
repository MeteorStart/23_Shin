package com.project.lx.baseproject.bean.responses;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/21 17:02
 * @company:
 * @email: lx802315@163.com
 */
public class AttentionInfo {


    private List<MyAttentionsBean> myFans;

    private List<MyAttentionsBean> myAttentions;

    public List<MyAttentionsBean> getMyAttentions() {
        return myAttentions;
    }


    public void setMyAttentions(List<MyAttentionsBean> myAttentions) {
        this.myAttentions = myAttentions;
    }

    public List<MyAttentionsBean> getMyFans() {
        return myFans;
    }

    public void setMyFans(List<MyAttentionsBean> myFans) {
        this.myFans = myFans;
    }


    public static class MyAttentionsBean {
        /**
         * userId : 4028f6815cce3e59015cce3eb7b40000
         * headImg : null
         * nickName : 187****1693
         * site : null
         * attentionCount : 0
         * fansCount : 0
         * sex : 0
         * phone : null
         * letterID : null
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

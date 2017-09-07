package com.project.lx.baseproject.bean.responses;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 选择收件人实体类
 * @version: V_1.0.0
 * @date: 2017/6/20 15:27
 * @company:
 * @email: lx802315@163.com
 */
public class ContactsInfo {


    private List<RecentlyFriendBean> recentlyFriend;
    private List<FriendBean> friend;

    public List<RecentlyFriendBean> getRecentlyFriend() {
        return recentlyFriend;
    }

    public void setRecentlyFriend(List<RecentlyFriendBean> recentlyFriend) {
        this.recentlyFriend = recentlyFriend;
    }

    public List<FriendBean> getFriend() {
        return friend;
    }

    public void setFriend(List<FriendBean> friend) {
        this.friend = friend;
    }

    public static class RecentlyFriendBean {
        /**
         * userId : 4028f6815cce4f2d015cce64acec0007
         * headImg : http://23haoxin.oss-cn-shanghai.aliyuncs.com/img/1499310791916.jpg?Expires=1814670790&OSSAccessKeyId=LTAIyvqw3MeP69kt&Signature=2OgYXRYm7FR9XFMk/6psEfU/08M%3D
         * nickName : 依恋
         * site : null
         * attentionCount : 0
         * fansCount : 0
         * sex : 0
         * phone : null
         * letterID : null
         * letterCount : 1
         * needFlag : 0
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
        private String letterCount;
        private String needFlag;
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

        public String getLetterCount() {
            return letterCount;
        }

        public void setLetterCount(String letterCount) {
            this.letterCount = letterCount;
        }

        public String getNeedFlag() {
            return needFlag;
        }

        public void setNeedFlag(String needFlag) {
            this.needFlag = needFlag;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }
    }

    public static class FriendBean {
        /**
         * userId : 4028f6815ce8eac7015ce8f3595c0004
         * headImg : https://wx.qlogo.cn/mmopen/ajNVdqHZLLDKeoibPWR4BuI9vFVFib8L8sYicsqVNh9lPRWacBjQmJKZu3VicsgDtk6EBGLEhsCU23fcCBobpqtPaw/0
         * nickName : 星
         * site : null
         * attentionCount : 0
         * fansCount : 0
         * sex : 0
         * phone : null
         * letterID : null
         * letterCount : null
         * needFlag : null
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
        private String letterCount;
        private String needFlag;
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

        public String getLetterCount() {
            return letterCount;
        }

        public void setLetterCount(String letterCount) {
            this.letterCount = letterCount;
        }

        public String getNeedFlag() {
            return needFlag;
        }

        public void setNeedFlag(String needFlag) {
            this.needFlag = needFlag;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }
    }
}

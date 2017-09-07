package com.project.lx.baseproject.bean.responses;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/29 17:23
 * @company:
 * @email: lx802315@163.com
 */
public class SearchResultInfo {
    private List<UsersBean> users;
    private List<ShopsBean> shops;
    private List<LettersBean> letters;

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public List<ShopsBean> getShops() {
        return shops;
    }

    public void setShops(List<ShopsBean> shops) {
        this.shops = shops;
    }

    public List<LettersBean> getLetters() {
        return letters;
    }

    public void setLetters(List<LettersBean> letters) {
        this.letters = letters;
    }

    public static class UsersBean {
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

    public static class ShopsBean {

        /**
         * shopId : 001
         * shopName : 星巴克
         * ownerId : 4028f6815cce3e59015cce4145df0002
         * headImg : http://cdnq.duitang.com/uploads/item/201504/15/20150415H0546_YGatC.thumb.224_0.jpeg
         * nickName : null
         * site : null
         * onLineCount : 0
         * circulateCount : 0
         * sex : 0
         * phone : null
         * letterID : null
         * role : 0
         */

        private String shopId;
        private String shopName;
        private String ownerId;
        private String headImg;
        private String  nickName;
        private String  site;
        private int onLineCount;
        private int circulateCount;
        private int sex;
        private String  phone;
        private String  letterID;
        private int role;

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
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

        public int getOnLineCount() {
            return onLineCount;
        }

        public void setOnLineCount(int onLineCount) {
            this.onLineCount = onLineCount;
        }

        public int getCirculateCount() {
            return circulateCount;
        }

        public void setCirculateCount(int circulateCount) {
            this.circulateCount = circulateCount;
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

    public static class LettersBean {
        /**
         * letterId : 111
         * letterNumber : 星
         * letterTitle : 星
         * letterState : 0
         * transferShop : null
         * letterFinishTime : null
         * topicName : 音乐&巧克力
         * ownerId : null
         * ownerName : null
         * owenerHeadImg : null
         * shopId : null
         * shopName : null
         * shopCoordinate : null
         * quickMark : null
         */

        private String letterId;
        private String letterNumber;
        private String letterTitle;
        private int letterState;
        private String transferShop;
        private long letterFinishTime;
        private String topicName;
        private String ownerId;
        private String ownerName;
        private String owenerHeadImg;
        private String shopId;
        private String shopName;
        private String shopCoordinate;
        private String quickMark;

        public String getLetterId() {
            return letterId;
        }

        public void setLetterId(String letterId) {
            this.letterId = letterId;
        }

        public String getLetterNumber() {
            return letterNumber;
        }

        public void setLetterNumber(String letterNumber) {
            this.letterNumber = letterNumber;
        }

        public String getLetterTitle() {
            return letterTitle;
        }

        public void setLetterTitle(String letterTitle) {
            this.letterTitle = letterTitle;
        }

        public int getLetterState() {
            return letterState;
        }

        public void setLetterState(int letterState) {
            this.letterState = letterState;
        }

        public String getTransferShop() {
            return transferShop;
        }

        public void setTransferShop(String transferShop) {
            this.transferShop = transferShop;
        }

        public long getLetterFinishTime() {
            return letterFinishTime;
        }

        public void setLetterFinishTime(long letterFinishTime) {
            this.letterFinishTime = letterFinishTime;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getOwenerHeadImg() {
            return owenerHeadImg;
        }

        public void setOwenerHeadImg(String owenerHeadImg) {
            this.owenerHeadImg = owenerHeadImg;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopCoordinate() {
            return shopCoordinate;
        }

        public void setShopCoordinate(String shopCoordinate) {
            this.shopCoordinate = shopCoordinate;
        }

        public String getQuickMark() {
            return quickMark;
        }

        public void setQuickMark(String quickMark) {
            this.quickMark = quickMark;
        }
    }
}

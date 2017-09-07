package com.project.lx.baseproject.bean.responses;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/29 10:02
 * @company:
 * @email: lx802315@163.com
 */
public class ShopLetterInfo {
    private List<LetterBean> letter;
    private List<LetterBean> circulateLetter;

    public List<LetterBean> getLetter() {
        return letter;
    }

    public void setLetter(List<LetterBean> letter) {
        this.letter = letter;
    }

    public List<LetterBean> getCirculateLetter() {
        return circulateLetter;
    }

    public void setCirculateLetter(List<LetterBean> circulateLetter) {
        this.circulateLetter = circulateLetter;
    }

    public static class LetterBean {
        /**
         * letterId : 4028f6815cce4f2d015cce5f2a740004
         * letterNumber : 333333
         * letterTitle : 1111
         * letterState : 0
         * transferShop : null
         * letterFinishTime : null
         * topicName : 红玫瑰与白玫瑰
         * ownerId : 4028f6815cce4f2d015cce52a3e10000
         * ownerName : 周鹏
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

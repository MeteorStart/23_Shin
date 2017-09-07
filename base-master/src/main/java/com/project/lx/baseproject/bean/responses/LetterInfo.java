package com.project.lx.baseproject.bean.responses;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/26 9:45
 * @company:
 * @email: lx802315@163.com
 */
public class LetterInfo {


    private List<LetterBean> letter;

    public List<LetterBean> getLetter() {
        return letter;
    }

    public void setLetter(List<LetterBean> letter) {
        this.letter = letter;
    }

    public static class LetterBean {

        /**
         * letterId : 2c9a2c755d646287015d7261d20a0065
         * letterNumber : cgo0000184
         * letterTitle : 还
         * letterState : 4
         * transferShop : null
         * letterFinishTime : null
         * topicName : 那年的夏天
         * ownerId : null
         * ownerName : null
         * owenerHeadImg : null
         * shopId : 2c9a2c755d646287015d646ab7b30004
         * shopName : 郑州市-中原区-null
         * shopCoordinate : 34.797117,113.6021
         * quickMark : null
         */

        private String letterId;
        private String letterNumber;
        private String letterTitle;
        private int letterState;
        private String transferShop;
        private String letterFinishTime;
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

        public String getLetterFinishTime() {
            return letterFinishTime;
        }

        public void setLetterFinishTime(String letterFinishTime) {
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

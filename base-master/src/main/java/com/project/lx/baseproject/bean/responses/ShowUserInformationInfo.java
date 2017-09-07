package com.project.lx.baseproject.bean.responses;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/28 16:08
 * @company:
 * @email: lx802315@163.com
 */
public class ShowUserInformationInfo {
    /**
     * letter : [{"letterId":"4028f6815ced54f0015ced5c14390000","letterNumber":"12687","letterTitle":null,"letterState":1,"transferShop":null,"letterFinishTime":null,"topicName":"红玫瑰与白玫瑰","ownerId":null,"ownerName":null,"owenerHeadImg":null,"shopId":null,"shopName":null,"shopCoordinate":null,"quickMark":null},{"letterId":"4028f6815ce82e59015ce833133d0003","letterNumber":"6885","letterTitle":null,"letterState":1,"transferShop":null,"letterFinishTime":null,"topicName":null,"ownerId":null,"ownerName":null,"owenerHeadImg":null,"shopId":null,"shopName":null,"shopCoordinate":null,"quickMark":null},{"letterId":"4028f6815ce82e59015ce831ffc80000","letterNumber":"656866","letterTitle":null,"letterState":3,"transferShop":null,"letterFinishTime":null,"topicName":null,"ownerId":null,"ownerName":null,"owenerHeadImg":null,"shopId":null,"shopName":null,"shopCoordinate":null,"quickMark":null}]
     * isFans : false
     * demand : [{"demandId":"4028f6815ccee8e8015cceeb12ff0000","demandContent":"等我","demandState":1,"ownerId":null,"ownerName":null,"owenerHeadImg":null,"shopId":null,"shopName":null,"shopCoordinate":null},{"demandId":"4028f6815ccedcfd015ccee6f4a30012","demandContent":"啊","demandState":0,"ownerId":null,"ownerName":null,"owenerHeadImg":null,"shopId":null,"shopName":null,"shopCoordinate":null}]
     */

    private boolean isFans;
    private List<LetterBean> letter;
    private List<DemandBean> demand;

    public boolean isIsFans() {
        return isFans;
    }

    public void setIsFans(boolean isFans) {
        this.isFans = isFans;
    }

    public List<LetterBean> getLetter() {
        return letter;
    }

    public void setLetter(List<LetterBean> letter) {
        this.letter = letter;
    }

    public List<DemandBean> getDemand() {
        return demand;
    }

    public void setDemand(List<DemandBean> demand) {
        this.demand = demand;
    }

    public static class LetterBean {
        /**
         * letterId : 4028f6815ced54f0015ced5c14390000
         * letterNumber : 12687
         * letterTitle : null
         * letterState : 1
         * transferShop : null
         * letterFinishTime : null
         * topicName : 红玫瑰与白玫瑰
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

    public static class DemandBean {
        /**
         * demandId : 4028f6815ccee8e8015cceeb12ff0000
         * demandContent : 等我
         * demandState : 1
         * ownerId : null
         * ownerName : null
         * owenerHeadImg : null
         * shopId : null
         * shopName : null
         * shopCoordinate : null
         * createTiem:
         */

        private String demandId;
        private String demandContent;
        private int demandState;
        private String ownerId;
        private String ownerName;
        private String owenerHeadImg;
        private String shopId;
        private String shopName;
        private String shopCoordinate;
        private String createTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
        public String getDemandId() {
            return demandId;
        }

        public void setDemandId(String demandId) {
            this.demandId = demandId;
        }

        public String getDemandContent() {
            return demandContent;
        }

        public void setDemandContent(String demandContent) {
            this.demandContent = demandContent;
        }

        public int getDemandState() {
            return demandState;
        }

        public void setDemandState(int demandState) {
            this.demandState = demandState;
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
    }
}

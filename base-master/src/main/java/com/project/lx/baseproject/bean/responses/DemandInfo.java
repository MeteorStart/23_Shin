package com.project.lx.baseproject.bean.responses;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/27 9:39
 * @company:
 * @email: lx802315@163.com
 */
public class DemandInfo {
    private List<MyDemandBean> myDemand;

    private List<MyDemandBean> finishDemand;

    public List<MyDemandBean> getFinishDemand() {
        return finishDemand;
    }

    public void setFinishDemand(List<MyDemandBean> finishDemand) {
        this.finishDemand = finishDemand;
    }

    public List<MyDemandBean> getMyDemand() {
        return myDemand;
    }

    public void setMyDemand(List<MyDemandBean> myDemand) {
        this.myDemand = myDemand;
    }

    public static class MyDemandBean {
        /**
         * demandId : 4028f6815ccee8e8015cceeb12ff0000
         * demandContent : 等我
         * demandState : 1
         * ownerId : null
         * ownerName : null
         * owenerHeadImg : null
         * shopId : 001
         * shopName : null
         * shopCoordinate : 113.611006,34.801862
         * createTiem
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        private String createTime;

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

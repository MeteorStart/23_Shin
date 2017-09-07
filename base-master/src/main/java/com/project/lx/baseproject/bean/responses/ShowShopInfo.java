package com.project.lx.baseproject.bean.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/22 10:13
 * @company:
 * @email: lx802315@163.com
 */
public class ShowShopInfo {
    /**
     * shop : {"shopId":"1","shopName":"陌法咖啡","slideshow":"http://img4.imgtn.bdimg.com/it/u=3071322373,3354763627&fm=26&gp=0.jpg(-_-)http://img1.imgtn.bdimg.com/it/u=1547453816,2687714663&fm=26&gp=0.jpg","address":"大学科技园15E401","vrUrl":"https://www.baidu.com","workTime":"全天 10:30 --21:00 周一至周日","coordinate":"113.609788,34.797935"}
     * letter : [{"letterId":"4028f6815cbedc33015cbf3b5b070005","letterNumber":"666666666666666666666666","letterTitle":"1","letterState":0,"transferShop":null,"letterFinishTime":null,"topicName":"fffff","ownerId":"4028f6815c9b43e9015c9b44a0ef0000","ownerName":"137****0710","owenerHeadImg":"8","shopId":null,"shopCoordinate":null,"quickMark":null},{"letterId":"4028f6815cbedc33015cbf3f0dbc0007","letterNumber":"11","letterTitle":"1的一封信","letterState":0,"transferShop":null,"letterFinishTime":null,"topicName":"1","ownerId":"1","ownerName":"137****0710","owenerHeadImg":"1","shopId":null,"shopCoordinate":null,"quickMark":null},{"letterId":"4028f6815cbedc33015cbf407792000a","letterNumber":"1","letterTitle":"1的二封信","letterState":0,"transferShop":null,"letterFinishTime":null,"topicName":"fffff","ownerId":"1","ownerName":"137****0710","owenerHeadImg":"1","shopId":null,"shopCoordinate":null,"quickMark":null},{"letterId":"4028f6815cc85106015cc85408860000","letterNumber":"1","letterTitle":"一封信","letterState":0,"transferShop":null,"letterFinishTime":null,"topicName":"1","ownerId":"4028f6815caa90ea015caaa0da180001","ownerName":"155****5070","owenerHeadImg":"05","shopId":null,"shopCoordinate":null,"quickMark":null}]
     */

    private ShopBean shop;
    private List<LetterBean> letter;
    private List<DemandBean> demand;

    public List<DemandBean> getDemand() {
        return demand;
    }

    public void setDemand(List<DemandBean> demand) {
        this.demand = demand;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public List<LetterBean> getLetter() {
        return letter;
    }

    public void setLetter(List<LetterBean> letter) {
        this.letter = letter;
    }

    public static class ShopBean {
        /**
         * shopId : 1
         * shopName : 陌法咖啡
         * slideshow : http://img4.imgtn.bdimg.com/it/u=3071322373,3354763627&fm=26&gp=0.jpg(-_-)http://img1.imgtn.bdimg.com/it/u=1547453816,2687714663&fm=26&gp=0.jpg
         * address : 大学科技园15E401
         * vrUrl : https://www.baidu.com
         * workTime : 全天 10:30 --21:00 周一至周日
         * coordinate : 113.609788,34.797935
         */

        private String shopId;
        private String shopName;
        private String slideshow;
        private String address;
        private String vrUrl;
        private String workTime;
        private String coordinate;

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

        public String getSlideshow() {
            return slideshow;
        }

        public void setSlideshow(String slideshow) {
            this.slideshow = slideshow;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getVrUrl() {
            return vrUrl;
        }

        public void setVrUrl(String vrUrl) {
            this.vrUrl = vrUrl;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }
    }

    @Override
    public String toString() {
        return "ShowShopInfo{" +
                "shop=" + shop +
                ", letter=" + letter +
                '}';
    }


    public static class LetterBean {
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

    public static class DemandBean {
        /**
         * demandId : 4028f6815ccee8e8015cceeb12ff0000
         * demandContent : 等我
         * demandState : 0
         * ownerId : 4028f6815cce4f2d015cce64acec0007
         * ownerName : 155****5070
         * owenerHeadImg : null
         * shopId : 001
         * shopName : 郑州市-中原区-星巴克
         * shopCoordinate : 113.611006,34.801862
         * createTime:2017-08-07 14:01:28
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

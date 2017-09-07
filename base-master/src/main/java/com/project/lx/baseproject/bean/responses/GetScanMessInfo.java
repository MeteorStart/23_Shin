package com.project.lx.baseproject.bean.responses;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/14 16:50
 * @company:
 * @email: lx802315@163.com
 */
public class GetScanMessInfo {
    /**
     * letter : {"letterId":"2c9a2c755d3ba178015d400193790002","letterNumber":"hebs0000149","letterTitle":null,"letterState":0,"transferShop":null,"letterFinishTime":null,"topicName":null,"ownerId":"2c9a2c755d2102e5015d2107a5f30002","ownerName":"❦ 她入我心_つ","owenerHeadImg":"http://q.qlogo.cn/qqapp/1105971831/DFEDB32CE718D4DBF5D5955B41B05595/100","shopId":null,"shopName":"郑州市-中原区-星巴克","shopCoordinate":null,"quickMark":null}
     * user : {"userId":"2c9a2c755d2113bf015d212d40e6000b","headImg":null,"nickName":"155****5070","site":null,"attentionCount":0,"fansCount":0,"sex":0,"phone":null,"letterID":null,"letterCount":null,"needFlag":null,"role":0}
     */

    private LetterBean letter;
    private UserBean user;

    public LetterBean getLetter() {
        return letter;
    }

    public void setLetter(LetterBean letter) {
        this.letter = letter;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class LetterBean {
        /**
         * letterId : 2c9a2c755d3ba178015d400193790002
         * letterNumber : hebs0000149
         * letterTitle : null
         * letterState : 0
         * transferShop : null
         * letterFinishTime : null
         * topicName : null
         * ownerId : 2c9a2c755d2102e5015d2107a5f30002
         * ownerName : ❦ 她入我心_つ
         * owenerHeadImg : http://q.qlogo.cn/qqapp/1105971831/DFEDB32CE718D4DBF5D5955B41B05595/100
         * shopId : null
         * shopName : 郑州市-中原区-星巴克
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

    public static class UserBean {
        /**
         * userId : 2c9a2c755d2113bf015d212d40e6000b
         * headImg : null
         * nickName : 155****5070
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

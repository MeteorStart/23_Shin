package com.project.lx.baseproject.bean.responses;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 话题实体类
 * @version: V_1.0.0
 * @date: 2017/6/19 14:03
 * @company:
 * @email: lx802315@163.com
 */
public class TopicInfo {

    private List<TopicBean> topic;
    private List<TopicBean> recentlyTopic;

    public List<TopicBean> getTopic() {
        return topic;
    }

    public void setTopic(List<TopicBean> topic) {
        this.topic = topic;
    }

    public List<TopicBean> getRecentlyTopic() {
        return recentlyTopic;
    }

    public void setRecentlyTopic(List<TopicBean> recentlyTopic) {
        this.recentlyTopic = recentlyTopic;
    }

    public static class TopicBean {
        /**
         * topicId : 1
         * topicName : 1
         * topcContent : 擦擦擦
         * topicImg : null
         */

        private String topicId;
        private String topicName;
        private String topcContent;
        private String topicImg;

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getTopcContent() {
            return topcContent;
        }

        public void setTopcContent(String topcContent) {
            this.topcContent = topcContent;
        }

        public String getTopicImg() {
            return topicImg;
        }

        public void setTopicImg(String topicImg) {
            this.topicImg = topicImg;
        }
    }

}

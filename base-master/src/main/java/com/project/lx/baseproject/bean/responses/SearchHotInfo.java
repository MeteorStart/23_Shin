package com.project.lx.baseproject.bean.responses;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/29 16:00
 * @company:
 * @email: lx802315@163.com
 */
public class SearchHotInfo {
    private List<HotSearchsBean> hotSearchs;

    public List<HotSearchsBean> getHotSearchs() {
        return hotSearchs;
    }

    public void setHotSearchs(List<HotSearchsBean> hotSearchs) {
        this.hotSearchs = hotSearchs;
    }

    public static class HotSearchsBean {
        /**
         * id : 1
         * content : 订单的
         */

        private String id;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

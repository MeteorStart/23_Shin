package com.project.lx.baseproject.bean.request;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/19 13:51
 * @company:
 * @email: lx802315@163.com
 */
public class SearchTopicBody extends SearchBody {

    /**
     * condition : string
     * page : 0
     * pize : 0
     */

    private String condition;
    private int page;
    private int pize;

    public SearchTopicBody(String userToken) {
        super(userToken);
    }

    public SearchTopicBody(String userToken, String condition, int page, int pize) {
        super(userToken);
        this.condition = condition;
        this.page = page;
        this.pize = pize;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPize() {
        return pize;
    }

    public void setPize(int pize) {
        this.pize = pize;
    }
}

package com.project.lx.baseproject.base;

/**
 * @author: Meteor
 * @description: Presenter控制层
 * @version:
 * @date: 2016/12/28 0028 16:40
 * @company:
 * @email: lx802315@163.com
 */
public interface BasePresenter<T extends BaseView> {

    String TAG = "BasePresenter";

    /**
     * 初始化操作
     */
    void initData();
}

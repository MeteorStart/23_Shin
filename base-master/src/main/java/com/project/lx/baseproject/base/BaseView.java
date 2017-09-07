package com.project.lx.baseproject.base;

import android.content.Intent;

/**
 * @author: Meteor
 * @description: View控制层
 * @version:
 * @date: 2016/12/28 0028 16:39
 * @company:
 * @email: lx802315@163.com
 */
public interface BaseView<T extends BasePresenter> {

    /**
     * 为视图设置一个控制层
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 展示一个进度条对话框
     *
     * @param title 标题
     * @param msg   显示的内容
     * @param flag  是否可以取消
     */
    void showLoadingDialog(String title, String msg, boolean flag);

    /**
     * 取消进度条
     */
    void canelLoadingDialog();

    /**
     * 界面的跳转
     *
     * @param intent 需要跳转的页面
     */
    void jumpActivity(Intent intent);
}

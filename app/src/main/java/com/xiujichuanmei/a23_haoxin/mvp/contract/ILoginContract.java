package com.xiujichuanmei.a23_haoxin.mvp.contract;

import android.content.Intent;

import com.project.lx.baseproject.base.BasePresenter;
import com.project.lx.baseproject.base.BaseView;
import com.project.lx.baseproject.widget.PowerfulEditText;

/**
 * @author: X_Meteor
 * @description: 登录相关契约类
 * @version: V_1.0.0
 * @date: 2017/6/14 9:04
 * @company:
 * @email: lx802315@163.com
 */
public class ILoginContract {
    public interface ILoginView extends BaseView<ILoginPresenter> {

        PowerfulEditText getmActLoginEtPhone();

        PowerfulEditText getmActLoginEtPwd();

        /**
         * Toast数据
         *
         * @param msg
         */
        void showMsg(String msg);

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
    public interface ILoginPresenter extends BasePresenter<ILoginView> {

        /**
         * 登录操作
         */
        void login();
    }
}

package com.xiujichuanmei.a23_haoxin.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.bean.request.LoginBody;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.xiujichuanmei.a23_haoxin.mvp.contract.ILoginContract;
import com.xiujichuanmei.a23_haoxin.mvp.model.LoginModelImpl;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.ILoginModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnLoginListener;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.HomeStoreActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.LoginActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.MainActivity;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/14 9:13
 * @company:
 * @email: lx802315@163.com
 */
public class ActLoginPreImpl implements ILoginContract.ILoginPresenter, OnLoginListener {

    ILoginContract.ILoginView mView;
    ILoginModel model;
    Context context;

    String phone;
    String pwd;
    int deviceType;

    public ActLoginPreImpl(ILoginContract.ILoginView mView, Context context) {
        this.mView = mView;
        this.context = context;
        model = new LoginModelImpl(context);
    }

    @Override
    public void initData() {
    }

    @Override
    public void login() {
//        mView.showLoadingDialog("", "登录中...", true);

        phone = mView.getmActLoginEtPhone().getText().toString();
        pwd = mView.getmActLoginEtPwd().getText().toString();
        deviceType = Constants.DEVICE_TYPE;

        if (phone.length() <= 0) {
            mView.showMsg("用户名不能为空！");
            mView.canelLoadingDialog();
            return;
        } else if (pwd.length() <= 0) {
            mView.showMsg("密码不能为空！");
            mView.canelLoadingDialog();
            return;
        } else if (phone.length() != 11) {
            mView.showMsg("用户名格式错误！");
            mView.canelLoadingDialog();
            return;
        } else if (pwd.length() < 6) {
            mView.showMsg("密码格式错误！");
            mView.canelLoadingDialog();
        }

        model.login(new LoginBody(Constants.DEVICE_TYPE, pwd, phone), this);
    }

    @Override
    public void onLoginSuccess(UserInfo userInfo) {
//        mView.canelLoadingDialog();
        SharedPreferencesUtils.putString(MyApplication.getInstance(), Constants.USER_TOKNE, userInfo.getUserToken());

        MyApplication.getInstance().setCurrentUser(userInfo);

        //设置别名
        JPushInterface.setAlias(context, userInfo.getUserToken(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
//                                SPUtils.saveIntToSP("aliasCode", i);
//                                    LogUtils.e("---------------aliasCode", "" + i);

            }
        });

        if (userInfo.getUserInfor().getRole() == 0) {
            mView.jumpActivity(new Intent(context, MainActivity.class));
        } else if (userInfo.getUserInfor().getRole() == 1) {
            //需要传值
            mView.jumpActivity(new Intent(context, HomeStoreActivity.class));
        }
    }

    @Override
    public void onLoginError(String msg) {
        mView.showMsg(msg);
//        mView.canelLoadingDialog();
    }
}

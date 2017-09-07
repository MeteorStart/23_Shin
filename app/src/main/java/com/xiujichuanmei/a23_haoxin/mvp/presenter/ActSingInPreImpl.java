package com.xiujichuanmei.a23_haoxin.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.bean.request.GetCodeBody;
import com.project.lx.baseproject.bean.request.LoginBody;
import com.project.lx.baseproject.bean.request.SingInBody;
import com.project.lx.baseproject.bean.responses.ChangePwdForCodeInfo;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.mvp.contract.ISingInContract;
import com.xiujichuanmei.a23_haoxin.mvp.model.SingInModeImpl;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.ISingInModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnChangePwdForCodeListener;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnGetCodeListener;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnLoginListener;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnSingInListener;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.MainActivity;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/15 9:24
 * @company:
 * @email: lx802315@163.com
 */
public class ActSingInPreImpl implements ISingInContract.ISingInPresenter, OnGetCodeListener, OnSingInListener, OnLoginListener, OnChangePwdForCodeListener {

    ISingInContract.ISingInView mView;
    ISingInModel model;

    Context context;

    String phone;
    String pws;
    String code;

    public ActSingInPreImpl(ISingInContract.ISingInView mView, Context context) {
        this.mView = mView;
        this.context = context;
        model = new SingInModeImpl(context);
    }

    @Override
    public void initData() {
    }

    @Override
    public void getCode(int type) {
//        mView.showLoadingDialog("", "获取中...", true);

        phone = mView.getmActSingInPhoneNumber().getText().toString();
        pws = mView.getmActSingInPhonePassword().getText().toString();
        code = mView.getmActSingInPhoneCode().getText().toString();

        if (phone.length() <= 0) {
            ToastUtils.showToast(context, "用户名不能为空！");
            mView.canelLoadingDialog();
            return;
        }

        if (phone.length() > 11) {
            ToastUtils.showToast(context, "手机号格式错误！");
            mView.canelLoadingDialog();
            return;
        }

        model.getCode(new GetCodeBody(phone, type), this);
    }

    @Override
    public void singIn() {
//        mView.showLoadingDialog("", "注册中...", true);

        phone = mView.getmActSingInPhoneNumber().getText().toString();
        pws = mView.getmActSingInPhonePassword().getText().toString();
        code = mView.getmActSingInPhoneCode().getText().toString();

        model.singIn(new SingInBody(code, Constants.DEVICE_TYPE, pws, phone), this);
    }

    @Override
    public void changePwdForCode() {
//        mView.showLoadingDialog("", "修改中...", true);

        phone = mView.getmActSingInPhoneNumber().getText().toString();
        pws = mView.getmActSingInPhonePassword().getText().toString();
        code = mView.getmActSingInPhoneCode().getText().toString();

        model.changePwdForCode(new SingInBody(code, Constants.DEVICE_TYPE, pws, phone), this);
    }

    @Override
    public void onGetCodeSuccess(BaseEntity baseEntity) {
//        mView.canelLoadingDialog();
//        mView.getmActSingInPhoneCode().setText(baseEntity.getResponse().toString());
    }

    @Override
    public void onGetCodeError(String msg) {
//        ToastUtils.showToast(context, msg);
//        mView.canelLoadingDialog();
    }

    @Override
    public void onSingInSuccess(BaseEntity baseEntity) {
//        mView.canelLoadingDialog();
        model.login(new LoginBody(Constants.DEVICE_TYPE, pws, phone), this);
    }

    @Override
    public void onSingInError(String msg) {
        mView.canelLoadingDialog();
    }

    @Override
    public void onLoginSuccess(UserInfo userInfo) {
//        mView.canelLoadingDialog();
        SharedPreferencesUtils.putString(MyApplication.getInstance(), Constants.USER_TOKNE, userInfo.getUserToken());
        MyApplication.getInstance().setCurrentUser(userInfo);
        mView.jumpActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onLoginError(String msg) {
//        mView.canelLoadingDialog();
    }

    @Override
    public void onChangePwdForCodeSuccess(ChangePwdForCodeInfo changePwdForCodeInfo) {
//        mView.canelLoadingDialog();
//        SharedPreferencesUtils.putString(MyApplication.getInstance(), Constants.USER_TOKNE, changePwdForCodeInfo.getUserToken());
        model.login(new LoginBody(Constants.DEVICE_TYPE, pws, phone), ActSingInPreImpl.this);
//        mView.jumpActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onChangePwdForCodeError(String msg) {
        mView.canelLoadingDialog();
    }
}

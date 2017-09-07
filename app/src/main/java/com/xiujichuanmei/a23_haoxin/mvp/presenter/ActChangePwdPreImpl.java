package com.xiujichuanmei.a23_haoxin.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.bean.request.ChangePwForOldPwdBody;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.widget.PowerfulEditText;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.contract.IChangePwdContract;
import com.xiujichuanmei.a23_haoxin.mvp.model.ChangePwdForOldPwdModelImpl;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.IChangePwdModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnChangePwdForOldPwdListener;

import butterknife.BindView;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/14 9:13
 * @company:
 * @email: lx802315@163.com
 */
public class ActChangePwdPreImpl implements IChangePwdContract.IChangePwdPresenter, OnChangePwdForOldPwdListener {

    IChangePwdContract.IChangePwdView mView;
    IChangePwdModel model;
    Context context;

    String oldPwd;
    String newPwd;
    String newPwdAgain;
    String userToken;

    public ActChangePwdPreImpl(IChangePwdContract.IChangePwdView mView, Context context) {
        this.mView = mView;
        this.context = context;
        model = new ChangePwdForOldPwdModelImpl(context);
    }

    @Override
    public void initData() {
        userToken = SharedPreferencesUtils.getString(context, Constants.USER_TOKNE);
        oldPwd = mView.getmActChangePwdEtOldPwd().getText().toString();
        newPwd = mView.getmActChangePwdEtNewPwd().getText().toString();
        newPwdAgain = mView.getmActChangePwdEtNewPwdAgain().getText().toString();

    }

    @Override
    public void changePwd() {
        initData();
        mView.showLoadingDialog("", "修改中...", true);
        model.changePwd(new ChangePwForOldPwdBody(newPwdAgain, newPwd, oldPwd, userToken), this);
    }

    @Override
    public void onChangePwdForOldPwdSuccess(BaseEntity baseEntity) {
        mView.canelLoadingDialog();
        mView.showMsg("修改成功");
        mView.jumpActivity(null);
    }

    @Override
    public void onChangePwdForOldPwdError(String msg) {
        mView.canelLoadingDialog();
        mView.showMsg("修改失败：" + msg);
    }
}

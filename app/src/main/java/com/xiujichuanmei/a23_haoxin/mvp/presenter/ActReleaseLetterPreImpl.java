package com.xiujichuanmei.a23_haoxin.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.bean.request.ReleaseBody;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.xiujichuanmei.a23_haoxin.mvp.contract.IReleaseLetterContract;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.IReleaseLetterModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnReleaseListener;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.ReleaseLetterModelImpl;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.UserFindLetterActivity;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/21 9:22
 * @company:
 * @email: lx802315@163.com
 */
public class ActReleaseLetterPreImpl implements IReleaseLetterContract.IReleaseLetterPresenter, OnReleaseListener {

    IReleaseLetterContract.IReleaseLetterView mView;
    Context context;
    IReleaseLetterModel model;

    String title;
    String topic;
    String adreess;
    String letter;
    String number;

    ReleaseBody body;

    public ActReleaseLetterPreImpl(IReleaseLetterContract.IReleaseLetterView mView, Context context) {
        this.mView = mView;
        this.context = context;
        model = new ReleaseLetterModelImpl(context);
    }

    @Override
    public void initData() {
        title = mView.getTitles().getText().toString();
        topic = mView.getTopicId();
        adreess = mView.getAdreessId();
        letter = mView.getLetterId();
        number = mView.getNumber().getText().toString();
        body = new ReleaseBody(letter, number, adreess, title, topic, SharedPreferencesUtils.getString(context, Constants.USER_TOKNE));
    }

    @Override
    public void release() {
        mView.showLoadingDialog("", "加载中。。。", true);
        initData();
        model.release(body, this);
    }

    @Override
    public void OnSearchSuccess(BaseEntity request) {
        mView.canelLoadingDialog();
        mView.showMsg("发布成功");
        Intent intent = new Intent(context, UserFindLetterActivity.class);
        intent.putExtra("Type", 1);
        mView.jumpActivity(intent);
    }

    @Override
    public void OnSearchError(String msg) {
        mView.canelLoadingDialog();
        mView.showMsg(msg);
    }
}

package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.contract.IUserFindLetterContract;
import com.xiujichuanmei.a23_haoxin.mvp.presenter.FragUserFindLetterPresenterImpl;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.NotSlipViewPager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @name: UserFindLetterActivity
 * @description: 用户 我找的信页面
 * @version: 1.0
 * @date: 2017/6/15 14:39
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class UserWaitLetterActivity extends BaseActivity implements IUserFindLetterContract.IUserFindLetterView {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.tab_act_user_find_letter)
    TabLayout tabActUserFindLetter;
    @BindView(R.id.vp_act_user_find_letter)
    NotSlipViewPager vpActUserFindLetter;

    public IUserFindLetterContract.IUserFindLetterPresenter getPresenter() {
        return presenter;
    }

    IUserFindLetterContract.IUserFindLetterPresenter presenter;

    LetterInfo request;

    public LetterInfo getRequest() {
        return request;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_user_find_letter);
    }

    @Override
    public void initView() {
        textActTop.setText("我等的信");
        imvActTopRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        presenter = new FragUserFindLetterPresenterImpl(this, this);
        ((FragUserFindLetterPresenterImpl) presenter).initWaitData();
    }

    @Override
    public void initListener() {

    }


    @Override
    public void setPresenter(IUserFindLetterContract.IUserFindLetterPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog() {
        dismissProcessDialog();
    }

    @Override
    public void jumpActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public NotSlipViewPager getmFragUserFindLetterVpContent() {
        return vpActUserFindLetter;
    }

    @Override
    public TabLayout getmTabLayout() {
        return tabActUserFindLetter;
    }

    @Override
    public FragmentManager getManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void setLetterInfo(LetterInfo letterInfo) {
        request = letterInfo;
    }

    @OnClick(R.id.imv_act_top_back)
    public void onViewClicked() {
        finish();
    }
}

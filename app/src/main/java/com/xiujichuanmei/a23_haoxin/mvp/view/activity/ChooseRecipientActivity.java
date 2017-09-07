package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.lx.baseproject.base.BaseActivity;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.contract.ISearchTopicContract;
import com.xiujichuanmei.a23_haoxin.mvp.presenter.ActChooseRecipientPreImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @name: ChooseRecipientActivity
 * @description: 选择收信人
 * @version: 1.0
 * @date: 2017/6/12 16:44
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */

public class ChooseRecipientActivity extends BaseActivity implements ISearchTopicContract.ISearchTopicView {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;

    @BindView(R.id.tv_act_choose_recipient_success)
    TextView tvActChooseRecipientSuccess;

    @BindView(R.id.recy_act_choose_recipient_history)
    RecyclerView recyActChooseRecipientHistory;
    @BindView(R.id.lay_act_choose_recipient_history)
    LinearLayout layActChooseRecipientHistory;

    @BindView(R.id.tv_ac_choose_addressee_hot)
    TextView tvAcChooseAddresseeHot;
    @BindView(R.id.recy_act_choose_recipient_hot)
    RecyclerView recyActChooseRecipientHot;
    @BindView(R.id.lay_choose_recipient_hot)
    LinearLayout layChooseRecipientHot;

    ISearchTopicContract.ISearchTopicPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_choose_addressee);
    }

    @Override
    public void initView() {
        textActTop.setText("选择收信人");
        imvActTopRight.setVisibility(View.GONE);
        tvActChooseRecipientSuccess.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        presenter = new ActChooseRecipientPreImpl(this, this);
        presenter.getSearchDatas();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(ISearchTopicContract.ISearchTopicPresenter presenter) {
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
        if (intent != null){
            startActivity(intent);
        }else {
            finish();
        }
    }

    @Override
    public EditText getSearchEdit() {
        return null;
    }

    @Override
    public TextView getTextEdit() {
        return tvActChooseRecipientSuccess;
    }

    @Override
    public LinearLayout getActSearchTopicHistoryLay() {
        return layActChooseRecipientHistory;
    }

    @Override
    public RecyclerView getSearchTopicHistoryRecy() {
        return recyActChooseRecipientHistory;
    }

    @Override
    public TextView getTextSearchTopicHot() {
        return tvAcChooseAddresseeHot;
    }

    @Override
    public LinearLayout getActSearchTopicHotLay() {
        return layChooseRecipientHot;
    }

    @Override
    public RecyclerView getyActSearchTopicHotLRecy() {
        return recyActChooseRecipientHot;
    }

    @OnClick({R.id.imv_act_top_back, R.id.tv_act_choose_recipient_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.tv_act_choose_recipient_success:
                finish();
                break;
        }
    }
}

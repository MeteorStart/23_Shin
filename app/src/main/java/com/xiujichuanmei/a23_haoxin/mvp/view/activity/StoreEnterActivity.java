package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.lx.baseproject.base.BaseActivity;
import com.xiujichuanmei.a23_haoxin.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @name: StoreEnterActivity
 * @description: 商户入驻页面
 * @version: 1.0
 * @date: 2017/6/13 16:44
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class StoreEnterActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.lay_act_store_enter)
    LinearLayout layActStoreEnter;
    @BindView(R.id.btn_act_store_enter)
    Button btnActStoreEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_store_enter);
    }

    @Override
    public void initView() {
        textActTop.setText("商家入驻");
        imvActTopRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.imv_act_top_back, R.id.btn_act_store_enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.btn_act_store_enter:
                startActivity(new Intent(StoreEnterActivity.this, RecruitmentActivity.class));
                break;
        }
    }
}

package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.FeedBackBody;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: HelpActivity
 * @description: 帮助反馈页面
 * @version: 1.0
 * @date: 2017/6/13 16:35
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class HelpActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.edit_act_help)
    EditText editActHelp;
    @BindView(R.id.btn_act_help)
    Button btnActHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_help);
    }

    @Override
    public void initView() {
        textActTop.setText("帮助与反馈");
        imvActTopRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        editActHelp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    btnActHelp.setEnabled(true);
                } else {
                    btnActHelp.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.imv_act_top_back, R.id.btn_act_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.btn_act_help:
                Observable<BaseEntity> observable = RetrofitFactory.getInstance().feedback(new FeedBackBody(editActHelp.getText().toString(), MyApplication.getInstance().getCurrentUser().getUserToken()));
                observable.compose(RxSchedulers.<BaseEntity>compose())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            public void onNext(Object value) {
                                if (value instanceof BaseEntity) {
                                    if (((BaseEntity) value).isSuccess()) {
                                        onHandleSuccess(value);
                                    } else {
                                        onHandleError(((BaseEntity) value).getMsg());
                                    }
                                }
                            }

                            @Override
                            protected void onHandleSuccess(Object o) {
                                ToastUtils.showToast(HelpActivity.this, "提交成功");
                                finish();
                            }

                            @Override
                            protected void onHandleError(String msg) {
                                ToastUtils.showToast(HelpActivity.this, "提交失败" + msg);
                            }
                        });
                break;
        }
    }
}

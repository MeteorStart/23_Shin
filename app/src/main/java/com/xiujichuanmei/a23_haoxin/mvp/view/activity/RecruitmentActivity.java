package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.B;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.ShopsLocatedBody;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: 商家入驻页面
 * @description: 类描述
 * @version: 1.0
 * @date: 2017/6/28 17:53
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class RecruitmentActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.tv_act_recruitment_ok)
    TextView tvActRecruitmentOk;
    @BindView(R.id.edit_act_recruitment_name)
    EditText editActRecruitmentName;
    @BindView(R.id.edit_act_recruitment_address)
    EditText editActRecruitmentAddress;
    @BindView(R.id.edit_act_recruitment_phone)
    EditText editActRecruitmentPhone;

    boolean nameIsOk;
    boolean addressIsOk;
    boolean phoneIsOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_recruitment);
    }

    @Override
    public void initView() {
        textActTop.setText("申请入驻");
        imvActTopRight.setVisibility(View.GONE);
        tvActRecruitmentOk.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        editActRecruitmentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    nameIsOk = true;
                } else {
                    nameIsOk = false;
                }
                checkBtn();
            }
        });

        editActRecruitmentPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    phoneIsOk = true;
                } else {
                    phoneIsOk = false;
                }
                checkBtn();
            }
        });

        editActRecruitmentAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    addressIsOk = true;
                } else {
                    addressIsOk = false;
                }
                checkBtn();
            }
        });
    }

    @OnClick({R.id.imv_act_top_back, R.id.tv_act_recruitment_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.tv_act_recruitment_ok:
                ShopsLocatedBody body = new ShopsLocatedBody(editActRecruitmentName.getText().toString(), editActRecruitmentAddress.getText().toString(), editActRecruitmentPhone.getText().toString(), MyApplication.getInstance().getCurrentUser().getUserToken());
                Observable<BaseEntity> observable = RetrofitFactory.getInstance().shopsLocated(body);
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
                                ToastUtils.showToast(RecruitmentActivity.this, "提交申请成功");
                                finish();
                            }

                            @Override
                            protected void onHandleError(String msg) {
                                ToastUtils.showToast(RecruitmentActivity.this, "提交申请失败" + msg);
                            }
                        });
                break;
        }
    }

    public void checkBtn() {
        if (nameIsOk && phoneIsOk && addressIsOk) {
            tvActRecruitmentOk.setVisibility(View.VISIBLE);
        } else {
            tvActRecruitmentOk.setVisibility(View.GONE);
        }
    }
}

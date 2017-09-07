package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.EditNickNameBody;
import com.project.lx.baseproject.bean.request.ShowUserInfoBody;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.PowerfulEditText;
import com.xiujichuanmei.a23_haoxin.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class ChangeUserNameActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.edit_act_change_username)
    PowerfulEditText editActChangeUsername;
    @BindView(R.id.act_rite_success)
    TextView actRiteSuccess;

    String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("name"))){
            name = getIntent().getStringExtra("name");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_change_user_name);
    }

    @Override
    public void initView() {
        textActTop.setText("修改昵称");
        editActChangeUsername.setText(name);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        editActChangeUsername.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    actRiteSuccess.setVisibility(View.VISIBLE);
                } else {
                    actRiteSuccess.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.act_rite_success, R.id.imv_act_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.act_rite_success:
                showProcessDialog("", "修改中。。。", true);
                Observable<BaseEntity> observable = RetrofitFactory.getInstance().editNickName(new EditNickNameBody("", editActChangeUsername.getText().toString(), 0, MyApplication.getInstance().getCurrentUser().getUserToken()));
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
                                Observable<BaseEntity<UserInfo>> observable = RetrofitFactory.getInstance().showUserInfo(new ShowUserInfoBody(MyApplication.getInstance().getCurrentUser().getUserToken()));
                                observable.compose(RxSchedulers.<BaseEntity<UserInfo>>compose())
                                        .subscribe(new BaseObserver<UserInfo>(ChangeUserNameActivity.this) {
                                            @Override
                                            protected void onHandleSuccess(UserInfo userInfo) {
                                                ToastUtils.showToast(ChangeUserNameActivity.this, "修改成功");
                                                MyApplication.getInstance().setCurrentUser(userInfo);
                                                dismissProcessDialog();
                                                finish();
                                            }

                                            @Override
                                            protected void onHandleError(String msg) {
                                                LogUtils.print(msg);
                                                dismissProcessDialog();
                                            }
                                        });
                            }

                            @Override
                            protected void onHandleError(String msg) {
                                ToastUtils.showToast(ChangeUserNameActivity.this, "修改失败" + msg);
                            }
                        });
                break;
        }
    }
}

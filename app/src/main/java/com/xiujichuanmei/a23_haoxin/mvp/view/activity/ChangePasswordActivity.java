package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.PowerfulEditText;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.contract.IChangePwdContract;
import com.xiujichuanmei.a23_haoxin.mvp.presenter.ActChangePwdPreImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @name: ChangePasswordActivity
 * @description: 修改密码页面
 * @version: 1.0
 * @date: 2017/6/13 15:40
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class ChangePasswordActivity extends BaseActivity implements IChangePwdContract.IChangePwdView {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;

    @BindView(R.id.edit_act_change_password_old)
    PowerfulEditText editActChangePasswordOld;
    @BindView(R.id.edit_act_change_password_new)
    PowerfulEditText editActChangePasswordNew;
    @BindView(R.id.edit_act_change_password_again)
    PowerfulEditText editActChangePasswordAgain;
    @BindView(R.id.btn_act_change_password)
    Button btnActChangePassword;

    IChangePwdContract.IChangePwdPresenter pwdPresenter;

    boolean oldIsOk, newIsOk, againIsOk;

    String oldPaw, newPaw, againPaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_change_password);
    }

    @Override
    public void initView() {
        textActTop.setText("修改密码");
        imvActTopRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        pwdPresenter = new ActChangePwdPreImpl(this, this);
    }

    @Override
    public void initListener() {
        editActChangePasswordOld.addTextListener(new PowerfulEditText.TextListener() {

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 6) {
                    oldIsOk = true;
                } else {
                    oldIsOk = false;
                }
                changeBtnState();
            }
        });
        editActChangePasswordNew.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 6) {
                    newIsOk = true;
                } else {
                    newIsOk = false;
                }
                changeBtnState();
            }
        });
        editActChangePasswordAgain.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 6) {
                    againIsOk = true;
                } else {
                    againIsOk = false;
                }
                changeBtnState();
            }
        });
    }

    public void changeBtnState() {
        if (oldIsOk && newIsOk && againIsOk) {
            btnActChangePassword.setEnabled(true);
        } else {
            btnActChangePassword.setEnabled(false);
        }
    }

    @Override
    public void setPresenter(IChangePwdContract.IChangePwdPresenter presenter) {
        this.pwdPresenter = presenter;
    }

    @Override
    public PowerfulEditText getmActChangePwdEtOldPwd() {
        return editActChangePasswordOld;
    }

    @Override
    public PowerfulEditText getmActChangePwdEtNewPwd() {
        return editActChangePasswordNew;
    }

    @Override
    public PowerfulEditText getmActChangePwdEtNewPwdAgain() {
        return editActChangePasswordAgain;
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(this, msg);
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
        if (intent != null) {
            startActivity(intent);
        }
        finish();
    }

    @OnClick({R.id.imv_act_top_back, R.id.btn_act_change_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.btn_act_change_password:
                oldPaw = editActChangePasswordOld.getText().toString();
                newPaw = editActChangePasswordNew.getText().toString();
                againPaw = editActChangePasswordAgain.getText().toString();

                if (newPaw.equals(againPaw)) {
                    pwdPresenter.changePwd();
                } else {
                    ToastUtils.showToast(this, "新密码两次输入有误！");
                }
                break;
        }
    }
}

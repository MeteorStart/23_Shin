package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.util.AccountValidatorUtil;
import com.project.lx.baseproject.widget.PowerfulEditText;
import com.project.lx.baseproject.widget.TimeButton;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.contract.ISingInContract;
import com.xiujichuanmei.a23_haoxin.mvp.presenter.ActSingInPreImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @name: ForgetActivity
 * @description: 忘记密码
 * @version: 1.0
 * @date: 2017/6/12 16:49
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class ForgetActivity extends BaseActivity implements ISingInContract.ISingInView, TimeButton.OnTimeOverLisenter {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;

    @BindView(R.id.edit_act_forget_phone_number)
    PowerfulEditText editActForgetPhoneNumber;
    @BindView(R.id.edit_act_forget_password)
    PowerfulEditText editActForgetPassword;

    EditText editActForgetCode;

    @BindView(R.id.btn_act_forget)
    Button btnActForget;
    @BindView(R.id.btn_act_forget_time)
    TimeButton btnActForgetTime;

    ISingInContract.ISingInPresenter presenter;
    boolean phoneIsOk;
    boolean pwdIsOk;
    boolean codeIsOk;

    @BindView(R.id.til_act_forget_phone)
    TextInputLayout tilActForgetPhone;
    @BindView(R.id.til_act_forget_password)
    TextInputLayout tilActForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_forget);
    }

    @Override
    public void initView() {
        textActTop.setText("找回密码");
        imvActTopRight.setVisibility(View.GONE);
        editActForgetCode = (EditText) findViewById(R.id.edit_act_forget_code);
        tilActForgetPhone.setHint("请输入手机号！");
        tilActForgetPassword.setHint("请输入密码！");
    }

    @Override
    public void initData() {
        presenter = new ActSingInPreImpl(this, this);
    }

    @Override
    public void initListener() {
        btnActForgetTime.setOnTimeOverLisenter(this);

        editActForgetCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    codeIsOk = true;
                } else {
                    codeIsOk = false;
                }
                checkBtnState();
            }
        });

        editActForgetPhoneNumber.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                tilActForgetPhone.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilActForgetPhone.setErrorEnabled(true);
                tilActForgetPhone.setErrorTextAppearance(R.style.errorAppearance);

                if (s.length() == 11) {
                    phoneIsOk = true;
                    if (!AccountValidatorUtil.isPhoneNumber(s.toString())) {
                        tilActForgetPhone.setError("手机号码格式不对哦~");
                    }else {
                        //进行用户名可用性判断
                    }
                } else if (s.length() > 11) {
                    tilActForgetPhone.setError("中国有超过11位的手机号吗？");
                } else {
                    phoneIsOk = false;
                }
                checkBtnState();

                if (phoneIsOk) {
                    btnActForgetTime.setEnabled(true);
                    btnActForgetTime.setBackgroundResource(R.drawable.shape_bg_btn_act_login_click);
                    btnActForgetTime.setTextColor(getResources().getColor(R.color.white));
                } else {
                    btnActForgetTime.setEnabled(false);
                    btnActForgetTime.setBackgroundResource(R.drawable.shape_bg_btn_act_login_unclick);
                    btnActForgetTime.setTextColor(getResources().getColor(R.color.act_main_line));
                }

            }
        });

        editActForgetPassword.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                tilActForgetPassword.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilActForgetPassword.setErrorEnabled(true);
                tilActForgetPassword.setErrorTextAppearance(R.style.errorAppearance);

                if (s.length() >= 6 && s.length() <= 16) {
                    pwdIsOk = true;
                    if (!AccountValidatorUtil.isPassword(s.toString())) {
                        tilActForgetPassword.setError("密码格式错误~");
                    }
                } else if (s.length() > 16) {
                    pwdIsOk = false;
                    tilActForgetPassword.setError("密码太长了~");
                } else {
                    pwdIsOk = false;
                }
                checkBtnState();
            }
        });


    }

    public void checkBtnState() {

        if (pwdIsOk && phoneIsOk && codeIsOk) {
            btnActForget.setEnabled(true);
        } else {
            btnActForget.setEnabled(false);
        }

    }

    @OnClick({R.id.imv_act_top_back, R.id.btn_act_forget_time, R.id.btn_act_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.btn_act_forget_time:
                btnActForgetTime.setBackgroundResource(R.drawable.shape_bg_btn_act_login_click);
                btnActForgetTime.setTextColor(getResources().getColor(R.color.white));
                presenter.getCode(2);
                break;
            case R.id.btn_act_forget:
                presenter.changePwdForCode();
                break;
        }
    }

    @Override
    public void setPresenter(ISingInContract.ISingInPresenter presenter) {
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
        clearActicity();
        startActivity(intent);
        finish();
    }

    @Override
    public PowerfulEditText getmActSingInPhoneNumber() {
        return editActForgetPhoneNumber;
    }

    @Override
    public PowerfulEditText getmActSingInPhonePassword() {
        return editActForgetPassword;
    }

    @Override
    public EditText getmActSingInPhoneCode() {
        return editActForgetCode;
    }

    @Override
    public void timeStart() {
    }

    @Override
    public void timeOver() {
    }
}

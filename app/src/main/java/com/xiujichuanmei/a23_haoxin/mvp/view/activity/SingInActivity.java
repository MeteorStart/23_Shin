package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.base.WebViewActivity;
import com.project.lx.baseproject.bean.request.VerfyReceiveBody;
import com.project.lx.baseproject.bean.request.VerifyPhoneBody;
import com.project.lx.baseproject.bean.responses.ProtocolInfo;
import com.project.lx.baseproject.util.AccountValidatorUtil;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.widget.PowerfulEditText;
import com.project.lx.baseproject.widget.TimeButton;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.contract.ISingInContract;
import com.xiujichuanmei.a23_haoxin.mvp.presenter.ActSingInPreImpl;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: SingInActivity
 * @description: 注册页面
 * @version: 1.0
 * @date: 2017/6/8 15:05
 * @company:
 * @email: lx802315@163.com
 */
public class SingInActivity extends BaseActivity implements ISingInContract.ISingInView {

    @BindView(R.id.imv_act_sing_in_avatar)
    ImageView imvActSingInAvatar;
    @BindView(R.id.text_act_sing_in_msg)
    TextView textActSingInMsg;
    @BindView(R.id.edit_act_sing_in_phone_number)
    PowerfulEditText editActSingInPhoneNumber;
    @BindView(R.id.edit_act_sing_in_password)
    PowerfulEditText editActSingInPassword;
    @BindView(R.id.edit_act_sing_in_code)
    EditText editActSingInCode;
    @BindView(R.id.btn_act_sing_in)
    Button btnActSingIn;
    @BindView(R.id.checkbox_act_sing_in)
    CheckBox checkboxActSingIn;
    @BindView(R.id.btn_act_sing_in_down)
    TimeButton btnActSingInDown;

    ISingInContract.ISingInPresenter presenter;

    boolean phoneIsOk;
    boolean pwdIsOk;
    boolean codeIsOk;
    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.tv_act_sing_in_protocol)
    TextView tvActSingInProtocol;
    @BindView(R.id.til_act_sing_in_phone)
    TextInputLayout tilActSingInPhone;
    @BindView(R.id.til_act_sing_in_password)
    TextInputLayout tilActSingInPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_sing_in);
    }

    @Override
    public void initView() {
        textActTop.setText("注册");
        checkboxActSingIn.setChecked(true);
        imvActTopRight.setVisibility(View.GONE);

        tilActSingInPhone.setHint("请输入手机号！");
        tilActSingInPassword.setHint("密码6-16位字符,可包含数字，字母！");
    }

    @Override
    public void initData() {
        presenter = new ActSingInPreImpl(this, this);
        presenter.initData();
    }

    @Override
    public void initListener() {
        editActSingInCode.addTextChangedListener(new TextWatcher() {
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

        editActSingInPhoneNumber.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                tilActSingInPhone.setErrorEnabled(false);
                tilActSingInPhone.setHint("请输入手机号！");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilActSingInPhone.setErrorEnabled(true);
                tilActSingInPhone.setErrorTextAppearance(R.style.errorAppearance);

                if (s.length() == 11) {
                    phoneIsOk = true;

                    if (!AccountValidatorUtil.isPhoneNumber(s.toString())) {
                        tilActSingInPhone.setError("手机号码格式不对哦~");
                    } else {
                        //进行用户名可用性判断
                        Observable<BaseEntity> observable = RetrofitFactory.getInstance().verifyPhone(new VerifyPhoneBody(s.toString()));
                        observable.compose(RxSchedulers.<BaseEntity>compose())
                                .subscribe(new BaseObserver(SingInActivity.this) {
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
                                        if (o instanceof BaseEntity) {
                                            tilActSingInPhone.setHint("该帐号可以注册！");
                                        }
                                    }

                                    @Override
                                    protected void onHandleError(String msg) {
                                        tilActSingInPhone.setError(msg);
                                    }
                                });
                    }
                } else if (s.length() > 11) {
                    tilActSingInPhone.setError("中国有超过11位的手机号吗？");
                    phoneIsOk = false;
                } else {
                    phoneIsOk = false;
                }

                checkBtnState();

                if (phoneIsOk) {
                    btnActSingInDown.setEnabled(true);
                    btnActSingInDown.setBackgroundResource(R.drawable.shape_bg_btn_act_login_click);
                    btnActSingInDown.setTextColor(getResources().getColor(R.color.white));
                } else {
                    btnActSingInDown.setEnabled(false);
                    btnActSingInDown.setBackgroundResource(R.drawable.shape_bg_btn_act_login_unclick);
                    btnActSingInDown.setTextColor(getResources().getColor(R.color.act_main_line));
                }
            }
        });
        editActSingInPassword.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                tilActSingInPassword.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilActSingInPassword.setErrorEnabled(true);

                if (s.length() >= 6 && s.length() <= 16) {
                    pwdIsOk = true;
                    if (!AccountValidatorUtil.isPassword(s.toString())) {
                        tilActSingInPassword.setError("密码格式错误~");
                    }
                } else if (s.length() > 16) {
                    pwdIsOk = false;
                    tilActSingInPassword.setError("密码太长了~");
                } else {
                    pwdIsOk = false;
                }

                checkBtnState();
            }
        });
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
        return editActSingInPhoneNumber;
    }

    @Override
    public PowerfulEditText getmActSingInPhonePassword() {
        return editActSingInPassword;
    }

    @Override
    public EditText getmActSingInPhoneCode() {
        return editActSingInCode;
    }

    public void checkBtnState() {
        if (pwdIsOk && phoneIsOk && checkboxActSingIn.isChecked() && codeIsOk) {
            btnActSingIn.setEnabled(true);
        } else {
            btnActSingIn.setEnabled(false);
        }
    }

    @OnClick({R.id.tv_act_sing_in_protocol, R.id.checkbox_act_sing_in, R.id.imv_act_top_back, R.id.btn_act_sing_in_down, R.id.btn_act_sing_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_act_sing_in_down:
                presenter.getCode(1);
                break;
            case R.id.btn_act_sing_in:
                presenter.singIn();
                break;
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.checkbox_act_sing_in:
                checkBtnState();
                break;
            case R.id.tv_act_sing_in_protocol:
                Observable<BaseEntity<ProtocolInfo>> observable = RetrofitFactory.getInstance().protocol();
                observable.compose(RxSchedulers.<BaseEntity<ProtocolInfo>>compose())
                        .subscribe(new BaseObserver<ProtocolInfo>(this) {
                            @Override
                            protected void onHandleSuccess(ProtocolInfo protocolInfo) {
                                Intent intent = new Intent(SingInActivity.this, WebViewActivity.class);
                                intent.putExtra("Data", protocolInfo.getContent());
                                startActivity(intent);
                            }

                            @Override
                            protected void onHandleError(String msg) {

                            }
                        });
                break;
        }
    }

}

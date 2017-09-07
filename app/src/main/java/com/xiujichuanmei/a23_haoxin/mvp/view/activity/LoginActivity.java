package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.LoginByOtherBody;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.AccountValidatorUtil;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.PowerfulEditText;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.contract.ILoginContract;
import com.xiujichuanmei.a23_haoxin.mvp.presenter.ActLoginPreImpl;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.Observable;

/**
 * @name: LoginActivity
 * @description: 登录页
 * @version: 1.0
 * @date: 2017/6/8 10:41
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class LoginActivity extends BaseActivity implements ILoginContract.ILoginView {

    @BindView(R.id.imv_act_login_avatar)
    ImageView imvActLoginAvatar;
    @BindView(R.id.text_act_login_msg)
    TextView textActLoginMsg;
    @BindView(R.id.edit_act_login_phone_number)
    PowerfulEditText editActLoginPhoneNumber;
    @BindView(R.id.edit_act_login_password)
    PowerfulEditText editActLoginPassword;
    @BindView(R.id.btn_act_login)
    Button btnActLogin;
    @BindView(R.id.text_act_login_sing_in)
    TextView textActLoginSingIn;
    @BindView(R.id.text_act_login_forget)
    TextView textActLoginForget;
    @BindView(R.id.imv_act_login_other_login_qq)
    ImageView imvActLoginOtherLoginQq;
    @BindView(R.id.imv_act_login_other_login_weibo)
    ImageView imvActLoginOtherLoginWeibo;
    @BindView(R.id.imv_act_login_other_login_weixin)
    ImageView imvActLoginOtherLoginWeixin;

    boolean isPwsOk;
    boolean isPhoneOk;

    ILoginContract.ILoginPresenter presenter;
    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;

    String openId;
    String headImg;
    String userName;
    String uid;

    @BindView(R.id.til_avt_login_phone)
    TextInputLayout tilAvtLoginPhone;
    @BindView(R.id.til_avt_login_password)
    TextInputLayout tilAvtLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        textActTop.setText("登录");
        imvActTopRight.setVisibility(View.GONE);

        tilAvtLoginPhone.setHint("请输入手机号！");
        tilAvtLoginPassword.setHint("请输入密码！");
    }

    @Override
    public void initData() {
        presenter = new ActLoginPreImpl(this, this);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(LoginActivity.this).setShareConfig(config);
    }

    @Override
    public void initListener() {
        editActLoginPassword.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                tilAvtLoginPassword.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilAvtLoginPassword.setErrorEnabled(true);
                if (s.length() >= 6 && s.length() <= 16) {
                    isPwsOk = true;
                    if (!AccountValidatorUtil.isPassword(s.toString())) {
                        tilAvtLoginPassword.setError("密码格式错误~");
                    }
                } else if (s.length() > 16) {
                    isPwsOk = false;
                    tilAvtLoginPassword.setError("密码太长了~");
                } else {
                    isPwsOk = false;
                }

                if (isPwsOk && isPhoneOk) {
                    btnActLogin.setEnabled(true);
                } else {
                    btnActLogin.setEnabled(false);
                }
            }
        });

        editActLoginPhoneNumber.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                tilAvtLoginPhone.setErrorEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilAvtLoginPhone.setErrorEnabled(true);
                tilAvtLoginPhone.setErrorTextAppearance(R.style.errorAppearance);

                if (s.length() == 11) {
                    isPhoneOk = true;
                    if (!AccountValidatorUtil.isPhoneNumber(s.toString())) {
                        tilAvtLoginPhone.setError("手机号码格式不对哦~");
                    }
                } else if (s.length() > 11) {
                    tilAvtLoginPhone.setError("中国有超过11位的手机号吗？");
                } else {
                    isPhoneOk = false;
                }

                if (isPwsOk && isPhoneOk) {
                    btnActLogin.setEnabled(true);
                } else {
                    btnActLogin.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void setPresenter(ILoginContract.ILoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public PowerfulEditText getmActLoginEtPhone() {
        return editActLoginPhoneNumber;
    }

    @Override
    public PowerfulEditText getmActLoginEtPwd() {
        return editActLoginPassword;
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(LoginActivity.this, "登录失败" + msg);
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
        finish();
    }

    @OnClick({R.id.imv_act_login_other_login_qq, R.id.imv_act_login_other_login_weibo, R.id.imv_act_login_other_login_weixin, R.id.imv_act_top_back, R.id.btn_act_login, R.id.text_act_login_sing_in, R.id.text_act_login_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_act_login:
                presenter.login();
                break;
            case R.id.text_act_login_sing_in:
                startActivity(new Intent(this, SingInActivity.class));
//                jumpActivity(new Intent(this, SingInActivity.class));
                break;
            case R.id.text_act_login_forget:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.imv_act_top_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

            /**
             * SHARE_MEDIA.QQ   QQ授权/分享到QQ好友
             * SHARE_MEDIA.QZONE  分享到QQ空间
             * SHARE_MEDIA.WEIXIN  微信授权
             * SHARE_MEDIA.WEIXIN_CIRCLE  分享到朋友圈
             * SHARE_MEDIA.SINA 微博授权/分享到微博
             */

            case R.id.imv_act_login_other_login_qq:
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.imv_act_login_other_login_weibo:
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.imv_act_login_other_login_weixin:
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
        }
    }

    /**
     * @name:
     * @description: 第三方登录回调
     * @date: 2017/7/4 15:26
     * @company:
     * @author: Meteor
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            showLoadingDialog("", "登录中。。。", true);
            LogUtils.print("开始回调");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            LogUtils.print(map.toString());
            LoginByOtherBody body = null;
            switch (share_media) {
                case QQ:
                    openId = map.get("openid");
                    headImg = map.get("profile_image_url");
                    userName = map.get("screen_name");
                    body = new LoginByOtherBody(Constants.OTHER_LOGIN_QQ, Constants.DEVICE_TYPE, headImg, openId, userName);
                    break;
                case WEIXIN:
                    openId = map.get("openid");
                    headImg = map.get("iconurl");
                    userName = map.get("screen_name");
                    body = new LoginByOtherBody(Constants.OTHER_LOGIN_WEIXIN, Constants.DEVICE_TYPE, headImg, openId, userName);
                    break;
                case SINA:
                    openId = map.get("id");
                    headImg = map.get("iconurl");
                    userName = map.get("screen_name");
                    body = new LoginByOtherBody(Constants.OTHER_LOGIN_WEIBO, Constants.DEVICE_TYPE, headImg, openId, userName);
                    break;
            }
            //调用第三方登录
            otherLogin(body);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            LogUtils.print("回调错误");
            dismissProcessDialog();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            dismissProcessDialog();
            LogUtils.print("取消回调");
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    //请求第三方登录
    private void otherLogin(LoginByOtherBody byOtherBody) {
        if (byOtherBody != null) {
            Observable<BaseEntity<UserInfo>> observable = RetrofitFactory.getInstance().loginByOther(byOtherBody);
            observable.compose(RxSchedulers.<BaseEntity<UserInfo>>compose())
                    .subscribe(new BaseObserver<UserInfo>(this) {
                        @Override
                        protected void onHandleSuccess(UserInfo userInfo) {
                            dismissProcessDialog();
                            MyApplication.getInstance().setCurrentUser(userInfo);
                            SharedPreferencesUtils.putString(LoginActivity.this, Constants.USER_TOKNE, userInfo.getUserToken());

                            //设置别名
                            JPushInterface.setAlias(LoginActivity.this, userInfo.getUserToken(), new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {
//                                SPUtils.saveIntToSP("aliasCode", i);
//                                    LogUtils.e("---------------aliasCode", "" + i);

                                }
                            });
                            if (userInfo.getUserInfor().getRole() == 0) {
                                jumpActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else if (userInfo.getUserInfor().getRole() == 1) {
                                //需要传值
                                jumpActivity(new Intent(LoginActivity.this, HomeStoreActivity.class));
                            }
                        }

                        @Override
                        protected void onHandleError(String msg) {
                            dismissProcessDialog();
                            ToastUtils.showToast(LoginActivity.this, "登录失败" + msg);
                        }
                    });
        }

    }
}

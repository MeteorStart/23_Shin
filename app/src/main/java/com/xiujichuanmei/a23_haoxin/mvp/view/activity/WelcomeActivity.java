package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.CheckPermissionsActivity;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.ShowUserInfoBody;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.StartAnimationUtils;
import com.xiujichuanmei.a23_haoxin.R;

import io.reactivex.Observable;

/**
 * @name: WelcomeActivity
 * @description: App启动页
 * @version: 1.0
 * @date: 2017/6/8 10:39
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class WelcomeActivity extends CheckPermissionsActivity implements StartAnimationUtils.OnJumeListener {

    /**
     * 用户Token
     */
    String userToken;

    //放置图片资源的集合
    ViewPager actAppStartVp;
    //放置圆点指示器
    LinearLayout actAppStartPoints;
    //首次跳转按钮
    Button actAppStartBtnStart;

    //非首次页面图片
    ImageView imvAppstartTransition;
    //非首次跳转按钮
    TextView viewTbtnAppstartCountdown;

    //是否为第一次打开
    private boolean isFirrst;

    //倒计时
    CountDownTimer countDownTimer;

    Bundle savedInstanceState;

    //开始页面工具类
    StartAnimationUtils startAnimationUtils;

    //倒计时时长(默认为3s) 开始直接会走一秒，这里传入4秒
    private long time = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        isFirrst = SharedPreferencesUtils.getBoolean(this, "isFirst", true);
        //判断是否首次开启，加载不同页面
//        if (isFirrst) {
//            setContentView(R.layout.activity_welcome_first);
//        } else {
//        }
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void initView() {
        //根据不同的加载页面，初始化不同的控件
//        if (isFirrst) {
//            actAppStartVp = (ViewPager) findViewById(R.id.vp_appstart_welcome);
//            actAppStartPoints = (LinearLayout) findViewById(R.id.view_appstart_indicator);
//            actAppStartBtnStart = (Button) findViewById(R.id.btn_appstart_skip);
//        } else {
            imvAppstartTransition = (ImageView) findViewById(R.id.imv_appstart_transition);
            viewTbtnAppstartCountdown = (TextView) findViewById(R.id.view_tbtn_appstart_countdown);
            Glide.with(this).load(R.drawable.start_bg)
                    .into(imvAppstartTransition);
//        }
    }

    @Override
    public void initData() {

        //获取应用存放的usertoken
        userToken = SharedPreferencesUtils.getString(this, Constants.USER_TOKNE, null);
        //通过uoken进行用户查询，成功后赋值给当前用户
        if (userToken != null && userToken.length() > 0) {
            Observable<BaseEntity<UserInfo>> observable = RetrofitFactory.getInstance().showUserInfo(new ShowUserInfoBody(userToken));
            observable.compose(RxSchedulers.<BaseEntity<UserInfo>>compose())
                    .subscribe(new BaseObserver<UserInfo>(this) {
                        @Override
                        protected void onHandleSuccess(UserInfo userInfo) {
                            MyApplication.getInstance().setCurrentUser(userInfo);
                        }

                        @Override
                        protected void onHandleError(String msg) {
                            LogUtils.print(msg);
                        }

                    });
        }

//        if (isFirrst) {
//            startAnimationUtils = new StartAnimationUtils(this, actAppStartVp, actAppStartPoints);
//            startAnimationUtils.setOnJumeListener(this);
//            startAnimationUtils.setImageResources(Constants.app_start_img_res);
//            startAnimationUtils.setIconResources(Constants.app_start_icon);
////            startAnimationUtils.setButton(actAppStartBtnStart);
//            startAnimationUtils.startAnimation();
//        } else {
            initTimer();
//        }

    }

    @Override
    public void initListener() {
//        if (isFirrst) {
//            actAppStartBtnStart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    jumpActicity();
//                }
//            });
//        } else {
            viewTbtnAppstartCountdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpActicity();
                }
            });
//        }
    }

    private void initTimer() {
        countDownTimer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                viewTbtnAppstartCountdown.setText(millisUntilFinished / 1000 + " 秒进入");
            }

            @Override
            public void onFinish() {
                jumpActicity();
            }
        }.start();
    }

    public void jumpActicity() {

        Intent intent = new Intent();
        //根据当前用户信息跳转不同页面，
        if (MyApplication.getInstance().getCurrentUser() != null) {
            if (MyApplication.getInstance().getCurrentUser().getUserInfor().getRole() == 0) {
                if (userToken != null && userToken.length() > 0) {
                    intent.putExtra(Constants.IS_NEED_LOGIN, false);
                } else {
                    intent.putExtra(Constants.IS_NEED_LOGIN, true);
                }
                intent.setClass(WelcomeActivity.this, MainActivity.class);
            } else if (MyApplication.getInstance().getCurrentUser().getUserInfor().getRole() == 1) {
                intent.setClass(WelcomeActivity.this, HomeStoreActivity.class);
            } else {
                intent.setClass(WelcomeActivity.this, MainActivity.class);
                intent.putExtra(Constants.IS_NEED_LOGIN, true);
            }
        } else {
            intent.putExtra(Constants.IS_NEED_LOGIN, true);
            intent.setClass(WelcomeActivity.this, MainActivity.class);
        }
        startActivity(intent);
        if (!isFirrst) {
            //跳转后需要把countDownTimer 清理
            countDownTimer.cancel();
        }
        finish();
    }

    @Override
    public void jump() {
        jumpActicity();
    }
}

package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.SearchBody;
import com.project.lx.baseproject.bean.request.SysUpdateSwitchBody;
import com.project.lx.baseproject.bean.responses.SwitchStateInfo;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: SettingMsgActivity
 * @description: 消息设置页面
 * @version: 1.0
 * @date: 2017/6/13 16:24
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class SettingMsgActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.rel_act_setting_msg_notice)
    RelativeLayout relActSettingMsgNotice;
    @BindView(R.id.switch_act_setting_receive)
    Switch switchActSettingReceive;
    @BindView(R.id.switch_act_setting_send)
    Switch switchActSettingSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_setting_msg);
    }

    @Override
    public void initView() {
        textActTop.setText("消息设置");
        imvActTopRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        Observable<BaseEntity<SwitchStateInfo>> observable = RetrofitFactory.getInstance().getSwitchState(new SearchBody(MyApplication.getInstance().getCurrentUser().getUserToken()));
        observable.compose(RxSchedulers.<BaseEntity<SwitchStateInfo>>compose())
                .subscribe(new BaseObserver<SwitchStateInfo>(this) {
                    @Override
                    protected void onHandleSuccess(SwitchStateInfo switchStateInfo) {
                        if (switchStateInfo.getLetterMesFlag() == 1) {
                            switchActSettingReceive.setChecked(true);
                        }
                        if (switchStateInfo.getSysMesFlag() == 1) {
                            switchActSettingSend.setChecked(true);
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {

                    }
                });
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.imv_act_top_back,R.id.switch_act_setting_receive, R.id.switch_act_setting_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switch_act_setting_receive:
                changeState(2);
                break;
            case R.id.switch_act_setting_send:
                changeState(1);
                break;
            case R.id.imv_act_top_back:
                finish();
                break;
        }
    }

    public void changeState(final int type) {
        Observable<BaseEntity> observable = RetrofitFactory.getInstance().sysUpdateSwitch(new SysUpdateSwitchBody(type, MyApplication.getInstance().getCurrentUser().getUserToken()));
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
                        ToastUtils.showToast(SettingMsgActivity.this, "修改完成");
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.showToast(SettingMsgActivity.this, "修改失败" + msg);
                    }
                });
    }

}

package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.GetScanMessageBody;
import com.project.lx.baseproject.bean.request.VerfyReceiveBody;
import com.project.lx.baseproject.bean.responses.GetScanMessInfo;
import com.project.lx.baseproject.util.Base64Utils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: UserInfoToScanActivity
 * @description: 扫码跳转页面
 * @version: 1.0
 * @date: 2017/6/12 14:00
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class UserInfoToScanActivity extends BaseActivity {

    Intent intent;
    String[] strings;
    @BindView(R.id.imv_act_user_info_to_scan_close)
    ImageView imvActUserInfoToScanClose;
    @BindView(R.id.imv_act_user_info_to_scan_avatar)
    ImageView imvActUserInfoToScanAvatar;
    @BindView(R.id.tv_act_user_info_to_scan_username)
    TextView tvActUserInfoToScanUsername;
    @BindView(R.id.iv_act_user_info_to_scan_line)
    ImageView ivActUserInfoToScanLine;
    @BindView(R.id.tv_act_user_info_to_scan_send_username)
    TextView tvActUserInfoToScanSendUsername;
    @BindView(R.id.tv_act_user_info_to_scan_get_username)
    TextView tvActUserInfoToScanGetUsername;
    @BindView(R.id.tv_act_user_info_to_scan_letter_number)
    TextView tvActUserInfoToScanLetterNumber;
    @BindView(R.id.tv_act_user_info_to_scan_address)
    TextView tvActUserInfoToScanAddress;
    @BindView(R.id.lay_act_user_info_to_scan)
    LinearLayout layActUserInfoToScan;
    @BindView(R.id.btn_act_user_info_to_scan)
    Button btnActUserInfoToScan;

    String letterId;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
//        strings = Base64Utils.getFromBase64(intent.getStringExtra("ScanResult")).split(",");
        strings = intent.getStringExtra("ScanResult").split(",");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_user_info_to_scan);
    }

    @Override
    public void initView() {
        showProcessDialog("", "加载中。。。", true);
//        for (int i = 0; i < length; i++) {
//            switch (i) {
//                case 0:
//                    tvActUserInfoToScanSendUsername.setText(strings[i]);
//                    break;
//                case 1:
//                    tvActUserInfoToScanGetUsername.setText(strings[i]);
//                    tvActUserInfoToScanUsername.setText(strings[i]);
//                    break;
//                case 2:
//                    tvActUserInfoToScanLetterNumber.setText(strings[i]);
//                    break;
//                case 3:
//                    tvActUserInfoToScanAddress.setText(strings[i]);
//                    break;
//                case 4:
//                    letterId = strings[i];
//                    break;
//                case 5:
//                    userId = strings[i];
//                    break;
//                case 6:
//                    Glide.with(this).
//                            load(strings[i])
//                            .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
//                            .error(R.drawable.icon_denglu_touxiang_weidenglu)
//                            .bitmapTransform(new GlideCircleTransform(this))
//                            .into(imvActUserInfoToScanAvatar);
//                    break;
//            }
//        }
    }

    @Override
    public void initData() {
        int length = strings.length;
        if (length > 1) {
            letterId = strings[0];
            userId = strings[1];
            Observable<BaseEntity<GetScanMessInfo>> observable = RetrofitFactory.getInstance().getScanMessage(new GetScanMessageBody(letterId, userId));
            observable.compose(RxSchedulers.<BaseEntity<GetScanMessInfo>>compose())
                    .subscribe(new BaseObserver<GetScanMessInfo>(this) {
                        @Override
                        protected void onHandleSuccess(GetScanMessInfo getScanMessInfo) {
                            if (getScanMessInfo != null) {
                                dismissProcessDialog();
                                tvActUserInfoToScanAddress.setText(getScanMessInfo.getLetter().getShopName() + "");
                                tvActUserInfoToScanUsername.setText(getScanMessInfo.getLetter().getOwnerName() + "");
                                tvActUserInfoToScanLetterNumber.setText(getScanMessInfo.getLetter().getLetterNumber() + "");
                                tvActUserInfoToScanGetUsername.setText(getScanMessInfo.getLetter().getOwnerName() + "");
                                Glide.with(UserInfoToScanActivity.this).
                                        load(getScanMessInfo.getLetter().getOwenerHeadImg() + "")
                                        .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                                        .error(R.drawable.icon_denglu_touxiang_weidenglu)
                                        .bitmapTransform(new GlideCircleTransform(UserInfoToScanActivity.this))
                                        .into(imvActUserInfoToScanAvatar);

                                tvActUserInfoToScanSendUsername.setText(getScanMessInfo.getUser().getNickName() + "");
                            }
                        }

                        @Override
                        protected void onHandleError(String msg) {
                            dismissProcessDialog();

                        }
                    });
        }
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.imv_act_user_info_to_scan_close, R.id.btn_act_user_info_to_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_user_info_to_scan_close:
                finish();
                break;
            case R.id.btn_act_user_info_to_scan:
                showProcessDialog("", "验证中。。。", true);
                if (letterId != null && userId != null) {
                    Observable<BaseEntity> observable = RetrofitFactory.getInstance().verifyReceive(new VerfyReceiveBody(letterId, userId, MyApplication.getInstance().getCurrentUser().getUserToken()));
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
                                               ToastUtils.showToast(UserInfoToScanActivity.this, "验证成功");
                                               finish();
                                               dismissProcessDialog();
                                           }

                                           @Override
                                           protected void onHandleError(String msg) {
                                               ToastUtils.showToast(UserInfoToScanActivity.this, "验证失败" + msg);
                                               dismissProcessDialog();
                                           }
                                       }
                            );
                }

                break;
        }
    }
}

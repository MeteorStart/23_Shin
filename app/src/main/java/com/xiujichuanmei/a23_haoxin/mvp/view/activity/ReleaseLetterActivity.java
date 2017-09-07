package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.orhanobut.logger.Logger;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.base.RxBus;
import com.project.lx.baseproject.bean.request.FinishDemandBody;
import com.project.lx.baseproject.bean.responses.ContactsInfo;
import com.project.lx.baseproject.bean.responses.ShopInfo;
import com.project.lx.baseproject.bean.responses.ShowShopInfo;
import com.project.lx.baseproject.bean.responses.TopicInfo;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.contract.IReleaseLetterContract;
import com.xiujichuanmei.a23_haoxin.mvp.presenter.ActReleaseLetterPreImpl;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @name: ReleaseLetterActivity
 * @description: 信件发布页面
 * @version: 1.0
 * @date: 2017/6/12 16:50
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class ReleaseLetterActivity extends BaseActivity implements IReleaseLetterContract.IReleaseLetterView {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.tv_act_release_letter_title)
    EditText tvActReleaseLetterTitle;
    @BindView(R.id.tv_act_release_letter_topic)
    TextView tvActReleaseLetterTopic;
    @BindView(R.id.tv_act_release_letter_position)
    TextView tvActReleaseLetterPosition;
    @BindView(R.id.tv_act_release_letter_receiver)
    TextView tvActReleaseLetterReceiver;
    @BindView(R.id.tv_act_release_letter_receive_number)
    EditText tvActReleaseLetterReceiveNumber;
    @BindView(R.id.btn_act_release_letter)
    Button btnActReleaseLetter;
    @BindView(R.id.imv_act_choose_recipi_scan)
    ImageView imvActChooseRecipiScan;

    boolean addressIsOk;
    boolean numberIsOk;

    String provice;
    String city;
    String area;

    ShopInfo shopInfo;
    TopicInfo.TopicBean topicBean;
    ContactsInfo.FriendBean friendBean;
    ShowShopInfo.DemandBean letterBean;

    IReleaseLetterContract.IReleaseLetterPresenter presenter;

    Flowable<Object> f = RxBus.getInstance().register(Object.class);

    Intent intent;
    @BindView(R.id.imv_act_choose_recipi_position)
    ImageView imvActChooseRecipiPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_release_letter);
    }

    @Override
    public void initView() {
        textActTop.setText("发布信件");
        imvActTopRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        presenter = new ActReleaseLetterPreImpl(this, this);

        letterBean = new ShowShopInfo.DemandBean();
        letterBean.setOwnerId(intent.getStringExtra("ownerId"));
        letterBean.setOwnerName(intent.getStringExtra("ownerName"));
        letterBean.setOwenerHeadImg(intent.getStringExtra("owenerHeadImg"));
        letterBean.setShopId(intent.getStringExtra("shopId"));
        letterBean.setShopName(intent.getStringExtra("shopName"));
        letterBean.setShopCoordinate(intent.getStringExtra("shopCoordinate"));
        letterBean.setDemandId(intent.getStringExtra("demandId"));

        if (letterBean.getShopName() != null && letterBean.getShopName().length() > 0) {
            tvActReleaseLetterPosition.setText(letterBean.getShopName());
            addressIsOk = true;
        }

        if (letterBean.getOwnerName() != null && letterBean.getOwnerName().length() > 0) {
            tvActReleaseLetterReceiver.setText(letterBean.getOwnerName());
        }
    }

    @Override
    public void initListener() {

        tvActReleaseLetterPosition.addTextChangedListener(new TextWatcher() {
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
                chackBtn();

            }
        });

        tvActReleaseLetterReceiveNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    numberIsOk = true;
                } else {
                    numberIsOk = false;
                }
                chackBtn();
            }
        });

        f.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object obj) throws Exception {

                if (obj instanceof TopicInfo.TopicBean) {
                    topicBean = (TopicInfo.TopicBean) obj;
                    if (topicBean != null) {
                        tvActReleaseLetterTopic.setText(((TopicInfo.TopicBean) obj).getTopicName());
                    }
                }
                if (obj instanceof String) {
                    getArea((String) obj);
                }
                if (obj instanceof ShopInfo) {
                    shopInfo = (ShopInfo) obj;
                    if (shopInfo != null) {
                        tvActReleaseLetterPosition.setText(city + "-" + area + "-" + ((ShopInfo) obj).getShopName());
                    }
                }
                if (obj instanceof ContactsInfo.FriendBean) {
                    friendBean = (ContactsInfo.FriendBean) obj;
                    if (friendBean != null) {
                        tvActReleaseLetterReceiver.setText(((ContactsInfo.FriendBean) obj).getNickName() + "");
                    }
                }
                if (obj instanceof ContactsInfo.RecentlyFriendBean) {
                    tvActReleaseLetterReceiver.setText(((ContactsInfo.RecentlyFriendBean) obj).getNickName() + "");
                }
                if (obj instanceof ShowShopInfo.DemandBean) {
                    letterBean = (ShowShopInfo.DemandBean) obj;

                }
            }
        });
    }

    @OnClick({R.id.imv_act_top_back,R.id.imv_act_choose_recipi_position, R.id.imv_act_choose_recipi_scan, R.id.tv_act_release_letter_title, R.id.tv_act_release_letter_topic, R.id.tv_act_release_letter_position, R.id.tv_act_release_letter_receiver, R.id.tv_act_release_letter_receive_number, R.id.btn_act_release_letter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.tv_act_release_letter_topic:
                startActivity(new Intent(this, SearchTopicActivity.class));
                break;
            case R.id.tv_act_release_letter_position:
                startActivity(new Intent(this, ChooseAddressActivity.class));
                break;
            case R.id.tv_act_release_letter_receiver:
                startActivity(new Intent(this, ChooseRecipientActivity.class));
                break;
            case R.id.btn_act_release_letter:

                if (letterBean.getDemandId() != null && letterBean.getDemandId().length() > 0) {
                    FinishDemandBody finishDemandBody = new FinishDemandBody(letterBean.getDemandId(), MyApplication.getInstance().getCurrentUser().getUserToken());
                    Observable<BaseEntity> observable = RetrofitFactory.getInstance().finishDemand(finishDemandBody);
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
                                    if (o instanceof BaseEntity) {
                                        ToastUtils.showToast(ReleaseLetterActivity.this, "完成需求");
                                    }
                                }

                                @Override
                                protected void onHandleError(String msg) {
                                    ToastUtils.showToast(ReleaseLetterActivity.this, "未完成" + msg);
                                }
                            });
                }

                presenter.release();

                break;
            case R.id.imv_act_choose_recipi_scan:
                customScan();
                break;
            case R.id.imv_act_choose_recipi_position:
                startActivity(new Intent(this, ChooseAddressActivity.class));
                break;
        }
    }

    //扫描二维码
    public void customScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        //integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setPrompt("");
        integrator.setCaptureActivity(CustomScanActivity.class);
        integrator.initiateScan();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void chackBtn() {
        if (addressIsOk && numberIsOk) {
            btnActReleaseLetter.setEnabled(true);
        } else {
            btnActReleaseLetter.setEnabled(false);
        }
    }

    // 通过 onActivityResult的方法获取 扫描回来的 值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描成功", Toast.LENGTH_LONG).show();
//                Toast.makeText(this, "返回数据" + intentResult.getContents().toString(), Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                Logger.i("返回数据：" + ScanResult.toString());
                tvActReleaseLetterReceiveNumber.setText(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getArea(String string) {

        String[] strings = string.split("-");
        if (strings.length >= 3) {
            provice = strings[0];
            city = strings[1];
            area = strings[2];
        }

    }

    @Override
    public void setPresenter(IReleaseLetterContract.IReleaseLetterPresenter presenter) {
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
        if (intent != null) {
            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    public EditText getTitles() {
        return tvActReleaseLetterTitle;
    }

    @Override
    public String getLetterId() {
        if (friendBean != null) {
            return friendBean.getUserId();
        }
        if (letterBean.getOwnerId() != null) {
            return letterBean.getOwnerId();
        }
        return "";
    }

    @Override
    public EditText getNumber() {
        return tvActReleaseLetterReceiveNumber;
    }

    @Override
    public String getAdreessId() {
        if (shopInfo != null) {
            return shopInfo.getShopId();
        }
        if (letterBean.getShopId() != null) {
            return letterBean.getShopId();
        }
        return "";
    }

    @Override
    public String getTopicId() {
        if (topicBean != null) {
            return topicBean.getTopicId();
        }
        return "";
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(this, msg);
    }

}

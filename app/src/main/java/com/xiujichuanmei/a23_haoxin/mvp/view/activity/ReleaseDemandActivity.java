package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.base.RxBus;
import com.project.lx.baseproject.bean.request.ReleaseDemandBody;
import com.project.lx.baseproject.bean.responses.ContactsInfo;
import com.project.lx.baseproject.bean.responses.EmptyInfo;
import com.project.lx.baseproject.bean.responses.ShopInfo;
import com.project.lx.baseproject.bean.responses.TopicInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @name: ReleaseDemandActivity
 * @description: 需求发布页面
 * @version: 1.0
 * @date: 2017/6/12 16:52
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class ReleaseDemandActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.tv_act_release_demand_address)
    TextView tvActReleaseDemandAddress;
    @BindView(R.id.imv_act_release_demand_address)
    ImageView imvActReleaseDemandAddress;
    @BindView(R.id.lay_act_release_demand_address)
    LinearLayout layActReleaseDemandAddress;
    @BindView(R.id.edit_act_release_demand)
    EditText editActReleaseDemand;
    @BindView(R.id.imv_act_release_demand_content)
    ImageView imvActReleaseDemandContent;
    @BindView(R.id.btn_act_release_demand)
    Button btnActReleaseDemand;

    String provice;
    String city;
    String area;

    ShopInfo shopInfo;

    boolean editIsOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_release_demand);
    }

    @Override
    public void initView() {
        textActTop.setText("发布需求");
        imvActTopRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        final Flowable<Object> f = RxBus.getInstance().register(Object.class);
        f.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object obj) throws Exception {
                if (obj instanceof String) {
                    getArea((String) obj);
                }
                if (obj instanceof ShopInfo) {
                    shopInfo = (ShopInfo) obj;
                    if (shopInfo != null) {
                        tvActReleaseDemandAddress.setText(city + "-" + area + "-" + ((ShopInfo) obj).getShopName());
                    }
                }
            }
        });

        editActReleaseDemand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    editIsOk = true;
                } else {
                    editIsOk = false;
                }
                checkBtn();
            }
        });
    }

    @OnClick({R.id.imv_act_top_back, R.id.lay_act_release_demand_address, R.id.btn_act_release_demand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.lay_act_release_demand_address:
                startActivity(new Intent(this, ChooseAddressActivity.class));
                break;
            case R.id.btn_act_release_demand:
                showProcessDialog("", "发布中。。。", true);
                releaseDemand();
                break;
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

    public void checkBtn() {
        if (editIsOk && shopInfo != null) {
            btnActReleaseDemand.setEnabled(true);
        } else {
            btnActReleaseDemand.setEnabled(false);
        }
    }

    public void releaseDemand() {
        ReleaseDemandBody releaseDemandBody = new ReleaseDemandBody(editActReleaseDemand.getText().toString(), shopInfo.getShopId(), SharedPreferencesUtils.getString(this, Constants.USER_TOKNE));

        Observable<BaseEntity> observable = RetrofitFactory.getInstance().releaseDemand(releaseDemandBody);
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
                        dismissProcessDialog();
                        ToastUtils.showToast(ReleaseDemandActivity.this, "发布成功");
                        startActivity(new Intent(ReleaseDemandActivity.this,UserWaitLetterActivity.class));
                        finish();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                        ToastUtils.showToast(ReleaseDemandActivity.this, "发布失败" + msg);
                    }
                });
    }
}

package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.base.RxBus;
import com.project.lx.baseproject.bean.request.ShopBody;
import com.project.lx.baseproject.bean.responses.AreaInfo;
import com.project.lx.baseproject.bean.responses.ShopInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.AreaUtils;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.ChangeAddressPopwindow;
import com.project.lx.baseproject.widget.ChangeShopPopwindow;
import com.xiujichuanmei.a23_haoxin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: ChooseAddressActivity
 * @description: 选择门店页面
 * @version: 1.0
 * @date: 2017/6/12 16:42
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class ChooseAddressActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.tv_act_choose_address_success)
    TextView tvActChooseAddressSuccess;
    @BindView(R.id.tv_act_choose_address_now_position)
    TextView tvActChooseAddressNowPosition;
    @BindView(R.id.imv_act_choose_address_line)
    ImageView imvActChooseAddressLine;

    @BindView(R.id.tv_act_choose_address_address)
    TextView tvActChooseAddressAddress;
    @BindView(R.id.lay_act_choose_address_address)
    LinearLayout layActChooseAddressAddress;

    @BindView(R.id.tv_act_choose_address_store)
    TextView tvActChooseAddressStore;
    @BindView(R.id.lay_act_choose_address_store)
    LinearLayout layActChooseAddressStore;

    String provice = "";
    String city = "";
    String area = "";

    ShopInfo shopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_choose_address);
    }

    @Override
    public void initView() {
        textActTop.setText("选择门店");
        imvActTopRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_act_choose_address_now_position, R.id.imv_act_top_back, R.id.tv_act_choose_address_success, R.id.lay_act_choose_address_address, R.id.lay_act_choose_address_store})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_act_choose_address_now_position:
                provice = SharedPreferencesUtils.getString(this, Constants.ADREESS_PROVINCE);
                city = SharedPreferencesUtils.getString(this, Constants.ADREESS_CITY);
                area = SharedPreferencesUtils.getString(this, Constants.ADREESS_DISTRICT);
                tvActChooseAddressAddress.setText(provice + "-" + city + "-" + area);
                break;
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.tv_act_choose_address_success:
                if (shopInfo != null) {
                    RxBus.getInstance().post(tvActChooseAddressAddress.getText());
                    RxBus.getInstance().post(shopInfo);
                    finish();
                } else {
                    ToastUtils.showToast(this, "请先选择地址");
                }
                break;
            case R.id.lay_act_choose_address_address:
                initPop();
                break;
            case R.id.lay_act_choose_address_store:
                initShop();
                break;
        }
    }

    public void initPop() {
        showProcessDialog("", "加载中。。。", true);
        Observable<BaseEntity<List<AreaInfo>>> observable = RetrofitFactory.getInstance().getArea();
        observable.compose(RxSchedulers.<BaseEntity<List<AreaInfo>>>compose())
                .subscribe(new BaseObserver<List<AreaInfo>>(this) {
                    @Override
                    protected void onHandleSuccess(final List<AreaInfo> areaInfos) {
                        dismissProcessDialog();
                        AreaUtils areaUtils = new AreaUtils(areaInfos);
                        ChangeAddressPopwindow addressPopwindow = new ChangeAddressPopwindow(ChooseAddressActivity.this, areaUtils.getmProvinceDatas(), areaUtils.getmCitisDatasMap(), areaUtils.getmAreaDatasMap());
                        addressPopwindow.showAtLocation(tvActChooseAddressAddress.getRootView(), Gravity.BOTTOM, 0, 0);
                        addressPopwindow
                                .setAddresskListener(new ChangeAddressPopwindow.OnAddressCListener() {

                                    @Override
                                    public void onClick(String province, String city, String area) {
                                        // TODO Auto-generated method stub
//                                        Toast.makeText(ChooseAddressActivity.this,
//                                                province + "-" + city + "-" + area,
//                                                Toast.LENGTH_LONG).show();
                                        ChooseAddressActivity.this.provice = province;
                                        ChooseAddressActivity.this.city = city;
                                        ChooseAddressActivity.this.area = area;
                                        String adreess = province + "-" + city + "-" + area;
                                        tvActChooseAddressAddress.setText(adreess);
//                                        RxBus.getInstance().post(adreess);
                                    }
                                });
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                    }
                });
    }

    public void initShop() {
        if (area.length() < 1) {
            ToastUtils.showToast(this, "请先选择地址");
        } else {
            showProcessDialog("", "加载中。。。", true);
            ShopBody shopBody = new ShopBody(provice + "-" + city + "-" + area, SharedPreferencesUtils.getString(getApplicationContext(), Constants.USER_TOKNE));
            Observable<BaseEntity<List<ShopInfo>>> observable = RetrofitFactory.getInstance().getShop(shopBody);
            observable.compose(RxSchedulers.<BaseEntity<List<ShopInfo>>>compose())
                    .subscribe(new BaseObserver<List<ShopInfo>>(this) {
                        @Override
                        protected void onHandleSuccess(final List<ShopInfo> shopInfos) {
                            dismissProcessDialog();
                            final String[] shop = new String[shopInfos.size()];
                            for (int i = 0; i < shop.length; i++) {
                                shop[i] = shopInfos.get(i).getShopName();
                            }

                            ChangeShopPopwindow changeShopPopwindow = new ChangeShopPopwindow(ChooseAddressActivity.this, shop);
                            changeShopPopwindow.showAtLocation(tvActChooseAddressAddress.getRootView(), Gravity.BOTTOM, 0, 0);
                            changeShopPopwindow
                                    .setAddresskListener(new ChangeShopPopwindow.OnAddressCListener() {
                                        @Override
                                        public void onClick(String province, int position) {
                                            if (provice != null && province.length() > 1) {
                                                tvActChooseAddressStore.setText(province);
                                                tvActChooseAddressSuccess.setEnabled(true);
                                                shopInfo = shopInfos.get(position);
                                            } else {
                                                ToastUtils.showToast(ChooseAddressActivity.this, "此区域没有咖啡店,请重新选择");
                                                tvActChooseAddressStore.setText("");
                                                tvActChooseAddressSuccess.setEnabled(false);
                                            }
//                                            RxBus.getInstance().post(shopInfos.get(position));
                                        }
                                    });
                        }

                        @Override
                        protected void onHandleError(String msg) {
                            dismissProcessDialog();
                        }
                    });
        }

    }

}

package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.map.offline.OfflineMapUtil;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.base.WebViewActivity;
import com.project.lx.baseproject.bean.request.ShowShopInfoBody;
import com.project.lx.baseproject.bean.request.SubLetterBody;
import com.project.lx.baseproject.bean.request.TimeBody;
import com.project.lx.baseproject.bean.responses.ShopInfo;
import com.project.lx.baseproject.bean.responses.ShopLetterInfo;
import com.project.lx.baseproject.bean.responses.ShowShopInfo;
import com.project.lx.baseproject.bean.responses.TimeInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.CustomLoadMoreView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.ShopDetaiDemandlAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.ShopDetailAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.ShopOnLineLetterAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;
import com.xiujichuanmei.a23_haoxin.loader.GlideImageLoader;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.MapPopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.SharePopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowSubPopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowTimePopupWindow;
import com.xiujichuanmei.a23_haoxin.utils.BaiduNaviUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: ShopDetailsActivity
 * @description: 店铺详情页
 * @version: 1.0
 * @date: 2017/6/6 14:29
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class ShopDetailsActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;

    @BindView(R.id.xrecl_act_shop_detail)
    RecyclerView xreclActShopDetail;

    @BindView(R.id.rel_act_shop_details)
    LinearLayout relActShopDetails;

    @BindView(R.id.swip_act_shop_details)
    SwipeRefreshLayout swipActShopDetails;

    View headBannerView;

    View headIntroduceView;

    View emptyView;

    Banner headBanner;

    TextView address, vr;
    TextView textActShopDetailShopName;
    TextView textActShopDetailShopAddress;
    TextView title;

    TextView textActShopDetailShopTime;
    ShopDetailAdapter mShopDetailAdapter;

    ShopDetaiDemandlAdapter mShopDetaiDemandlAdapter;

    //测试List
    List<ShowShopInfo.LetterBean> mList;
    List<ShowShopInfo.DemandBean> mDemandList;

    //轮播图地址列表
    List imagesList = new ArrayList();

    ImageView imvActShopDetailEmpty;
    String shopId;
    String shopName;
    String shopAddress;

    int type;

    ShowShopInfo showShopInfo;
    ShowTimePopupWindow pop;

    //每页加载个数
    private static final int PAGE_SIZE = 6;
    //当前页 起始页page = 0
    private int page = 0;
    //当前加载数据个数
    private int mCurrentCounter;

    LatLng endLatLng;
    LatLng startLatLng;
    String startName;
    String endName;

    String VrUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        shopId = intent.getStringExtra(Constants.SHOP_ID);
        shopName = intent.getStringExtra(Constants.SHOP_NAME);
        type = intent.getIntExtra(Constants.HOME_TYPE, 1);
        startLatLng = BaiduNaviUtils.stringToLatLng(SharedPreferencesUtils.getString(this, "LatLng"));
        startName = SharedPreferencesUtils.getString(this, "StartName");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_shop_details);
    }

    @Override
    public void initView() {
        textActTop.setText(shopName);
//        textActTop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(ShopDetailsActivity.this, Main2Activity.class));
////                startPhotoActivity(this, );
//            }
//        });

        headBannerView = LayoutInflater.from(this).inflate(R.layout.head_banner_act_shop_details, null);
        headBanner = (Banner) headBannerView.findViewById(R.id.banner_act_shop_details);

        headIntroduceView = LayoutInflater.from(this).inflate(R.layout.head_introduce_act_shop_details, null);
        textActShopDetailShopName = (TextView) headIntroduceView.findViewById(R.id.text_act_shop_detail_shop_name);
        textActShopDetailShopAddress = (TextView) headIntroduceView.findViewById(R.id.text_act_shop_detail_shop_address);
        textActShopDetailShopTime = (TextView) headIntroduceView.findViewById(R.id.text_act_shop_detail_shop_time);
        title = (TextView) headIntroduceView.findViewById(R.id.text_act_shop_detail_shop_unclaimed);

        address = (TextView) headIntroduceView.findViewById(R.id.text_act_shop_detail_shop_address_skip);
        vr = (TextView) headIntroduceView.findViewById(R.id.text_act_shop_detail_shop_vr);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        xreclActShopDetail.setLayoutManager(layoutManager);
        xreclActShopDetail.addItemDecoration(new SimpleDicider());

        //添加下拉刷新
        swipActShopDetails.setColorSchemeColors(getResources().getColor(R.color.act_main_line));

        emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty_act_shop_details, null);

        if (type == 1) {
            title.setText("未领取信件");
        } else {
            title.setText("未领取");
        }
//        imvActShopDetailEmpty = (ImageView) emptyView.findViewById(R.id.imv_act_shop_detail_empty);

//        imvActShopDetailEmpty.setOnClickListener(this);
    }

    @Override
    public void initData() {

        showProcessDialog("", "加载中。。。", true);
        if (type == 1) {
            mList = new ArrayList<>();
            mShopDetailAdapter = new ShopDetailAdapter(R.layout.item_act_shop_details, mList);
            mShopDetailAdapter.addHeaderView(headBannerView);
            mShopDetailAdapter.addHeaderView(headIntroduceView);

            //设置加载布局
            mShopDetailAdapter.setLoadMoreView(new CustomLoadMoreView());
            //设置载入动画
            mShopDetailAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
            xreclActShopDetail.setAdapter(mShopDetailAdapter);
            mCurrentCounter = mShopDetailAdapter.getData().size();

            swipActShopDetails.setOnRefreshListener(this);
            mShopDetailAdapter.setOnLoadMoreListener(this, xreclActShopDetail);
            //关闭首次进入默认加载
            mShopDetailAdapter.disableLoadMoreIfNotFullPage();

        } else if (type == 2) {

            mDemandList = new ArrayList<>();
            mShopDetaiDemandlAdapter = new ShopDetaiDemandlAdapter(R.layout.item_act_shop_details_demand, mDemandList);
            mShopDetaiDemandlAdapter.addHeaderView(headBannerView);
            mShopDetaiDemandlAdapter.addHeaderView(headIntroduceView);
            //设置加载布局
            mShopDetaiDemandlAdapter.setLoadMoreView(new CustomLoadMoreView());
            //设置载入动画
            mShopDetaiDemandlAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
            xreclActShopDetail.setAdapter(mShopDetaiDemandlAdapter);
            mCurrentCounter = mShopDetaiDemandlAdapter.getData().size();

            swipActShopDetails.setOnRefreshListener(this);
            mShopDetaiDemandlAdapter.setOnLoadMoreListener(this, xreclActShopDetail);
            //关闭首次进入默认加载
            mShopDetaiDemandlAdapter.disableLoadMoreIfNotFullPage();
        }

        getData(page);

    }

    @Override
    public void initListener() {
        address.setOnClickListener(this);
        vr.setOnClickListener(this);

        if (type == 1) {
            mShopDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(final BaseQuickAdapter adapter, View view, int position) {
                    final ShowShopInfo.LetterBean item = (ShowShopInfo.LetterBean) adapter.getItem(position);
                    switch (view.getId()) {
                        case R.id.text_act_shop_detail_shop_down_time:
                            TimeBody timeBody = new TimeBody(type, item.getLetterId());
                            Observable<BaseEntity<TimeInfo>> observable = RetrofitFactory.getInstance().showCountdown(timeBody);
                            observable.compose(RxSchedulers.<BaseEntity<TimeInfo>>compose())
                                    .subscribe(new BaseObserver<TimeInfo>(ShopDetailsActivity.this) {
                                        @Override
                                        protected void onHandleSuccess(TimeInfo timeInfo) {
                                            pop = new ShowTimePopupWindow(ShopDetailsActivity.this, timeInfo, item.getOwenerHeadImg(), item.getOwnerName(), item.getLetterNumber());
                                            pop.showAtLocation(xreclActShopDetail.getRootView(), Gravity.CENTER, 0, 0);
                                            darkenBackground(0.4f);
                                            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                                @Override
                                                public void onDismiss() {
                                                    darkenBackground(1f);
                                                }
                                            });
                                        }

                                        @Override
                                        protected void onHandleError(String msg) {

                                        }
                                    });
                            break;
                        case R.id.btn_act_shop_detail_shop_order:
                            if (item.getOwnerId().equals(MyApplication.getInstance().getCurrentUser().getUserInfor().getUserId())) {
//                                ToastUtils.showToast(ShopDetailsActivity.this,"自己不能预约自己的信件");
                            } else {
                                showSubPop(item, position);
                            }
                            break;
                        case R.id.imv_act_shop_detail_avatar:
                            if (item.getOwnerId().equals(MyApplication.getInstance().getCurrentUser().getUserInfor().getUserId())) {
//                                ToastUtils.showToast(ShopDetailsActivity.this,"自己不能预约自己的信件");
                            } else {
                                Intent intent = new Intent(ShopDetailsActivity.this, UserInforActivity.class);
                                intent.putExtra("HeadImg", item.getOwenerHeadImg());
                                intent.putExtra("UserId", item.getOwnerId());
                                intent.putExtra("NickName", item.getOwnerName());
                                startActivity(intent);
                            }
                            break;
                    }
                }
            });

            mShopDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ShowShopInfo.LetterBean item = (ShowShopInfo.LetterBean) adapter.getItem(position);
                    if (item.getLetterId() != null && item.getLetterId().length() > 0) {
                        Intent intent = new Intent(ShopDetailsActivity.this, LetterDetailActivity.class);
                        intent.putExtra("topic", item.getTopicName());
                        intent.putExtra("title", item.getLetterTitle());
                        intent.putExtra("letterNumber", item.getLetterNumber());
                        intent.putExtra("letterId", item.getLetterId());
                        intent.putExtra("userName", item.getOwnerName());
                        intent.putExtra("userAvatar", item.getOwenerHeadImg());
                        intent.putExtra("ownerId", item.getOwnerId());
                        intent.putExtra("letterState", item.getLetterState());
                        intent.putExtra("shopName", "1-1-" + shopName);
                        intent.putExtra("position", position);
                        int requestcode = 10;
                        startActivityForResult(intent, requestcode);
                    }
                }
            });
        } else if (type == 2) {
            mShopDetaiDemandlAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(final BaseQuickAdapter adapter, final View view, int position) {
                    final ShowShopInfo.DemandBean item = (ShowShopInfo.DemandBean) adapter.getItem(position);
                    switch (view.getId()) {
                        case R.id.imv_act_shop_detail_demand_time:
                            TimeBody timeBody = new TimeBody(type, item.getDemandId());
                            Observable<BaseEntity<TimeInfo>> observable = RetrofitFactory.getInstance().showCountdown(timeBody);
                            observable.compose(RxSchedulers.<BaseEntity<TimeInfo>>compose())
                                    .subscribe(new BaseObserver<TimeInfo>(ShopDetailsActivity.this) {
                                        @Override
                                        protected void onHandleSuccess(TimeInfo timeInfo) {
                                            pop = new ShowTimePopupWindow(ShopDetailsActivity.this, timeInfo, item.getOwenerHeadImg(), item.getOwnerName(), "");
                                            pop.showAtLocation(xreclActShopDetail.getRootView(), Gravity.CENTER, 0, 0);
                                            darkenBackground(0.4f);
                                            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                                @Override
                                                public void onDismiss() {
                                                    darkenBackground(1f);
                                                }
                                            });
                                        }

                                        @Override
                                        protected void onHandleError(String msg) {

                                        }
                                    });
                            break;
                        case R.id.imv_act_shop_detail_demand_write:
                            Intent intent = new Intent(ShopDetailsActivity.this, ReleaseLetterActivity.class);
                            intent.putExtra("shopName", item.getShopName());
                            intent.putExtra("shopId", item.getShopId());
                            intent.putExtra("ownerId", item.getOwnerId());
                            intent.putExtra("ownerName", item.getOwnerName());
                            intent.putExtra("owenerHeadImg", item.getOwenerHeadImg());
                            intent.putExtra("demandContent", item.getDemandContent());
                            intent.putExtra("shopCoordinate", item.getShopCoordinate());
                            intent.putExtra("demandId", item.getDemandId());
                            startActivity(intent);
                            break;
                        case R.id.imv_act_shop_detail_demand_avatar:
                            if (item.getOwnerId().equals(MyApplication.getInstance().getCurrentUser().getUserInfor().getUserId())) {
//                                ToastUtils.showToast(ShopDetailsActivity.this,"自己不能预约自己的信件");
                            } else {
                                Intent intent2 = new Intent(ShopDetailsActivity.this, UserInforActivity.class);
                                intent2.putExtra("HeadImg", item.getOwenerHeadImg());
                                intent2.putExtra("UserId", item.getOwnerId());
                                intent2.putExtra("NickName", item.getOwnerName());
                                startActivity(intent2);
                            }
                            break;
                    }
                }
            });

            mShopDetaiDemandlAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ShowShopInfo.DemandBean item = (ShowShopInfo.DemandBean) adapter.getItem(position);
                    if (item.getDemandId() != null && item.getDemandId().length() > 0) {
                        Intent intent = new Intent(ShopDetailsActivity.this, DemandDetailsActivity.class);
                        intent.putExtra("shopName", item.getShopName());
                        intent.putExtra("shopId", item.getShopId());
                        intent.putExtra("ownerId", item.getOwnerId());
                        intent.putExtra("ownerName", item.getOwnerName());
                        intent.putExtra("owenerHeadImg", item.getOwenerHeadImg());
                        intent.putExtra("demandContent", item.getDemandContent());
                        intent.putExtra("shopCoordinate", item.getShopCoordinate());
                        intent.putExtra("demandId", item.getDemandId());
                        intent.putExtra("demandState", item.getDemandState());
                        intent.putExtra("createTime",item.getCreateTime());
                        startActivity(intent);
                    }
                }
            });
        }
    }

    public void getData(final int page) {
        final ShowShopInfoBody shopInfoBody = new ShowShopInfoBody();

        shopInfoBody.setCode(type);
        shopInfoBody.setShopId(shopId);
        shopInfoBody.setPage(page);
        shopInfoBody.setPize(PAGE_SIZE);
        if (MyApplication.getInstance().getCurrentUser() != null) {
            shopInfoBody.setUserToken(MyApplication.getInstance().getCurrentUser().getUserToken());
        }

        Observable<BaseEntity<ShowShopInfo>> observable = RetrofitFactory.getInstance().showShopInfo(shopInfoBody);
        observable.compose(RxSchedulers.<BaseEntity<ShowShopInfo>>compose())
                .subscribe(new BaseObserver<ShowShopInfo>(this) {
                    @Override
                    protected void onHandleSuccess(ShowShopInfo showShopInfo) {
                        shopAddress = showShopInfo.getShop().getAddress();
                        endLatLng = BaiduNaviUtils.baiduStringToLatLng(showShopInfo.getShop().getCoordinate());
                        endName = showShopInfo.getShop().getShopName();
                        VrUrl = showShopInfo.getShop().getVrUrl();
                        if (type == 1) {
                            initBanner(headBanner, showShopInfo.getShop().getSlideshow());
                            initheadIntroduceView(showShopInfo);

                            List<ShowShopInfo.LetterBean> letterList = showShopInfo.getLetter();
                            mCurrentCounter = letterList.size();

                            if (mCurrentCounter < PAGE_SIZE) {
                                //如果没有数据
                                if (page == 0 && mCurrentCounter == 0) {
                                    letterList.add(new ShowShopInfo.LetterBean());
//                                    //设置空布局
//                                    mShopDetailAdapter.setEmptyView(emptyView);
                                }

                                if (page > 0) {
                                    mShopDetailAdapter.addData(letterList);
                                } else {
                                    mShopDetailAdapter.setNewData(letterList);
                                }
                                mShopDetailAdapter.loadMoreEnd(false);
                            } else {
                                if (letterList != null) {
                                    ShopDetailsActivity.this.page++;
                                    if (page > 0) {
                                        mShopDetailAdapter.addData(letterList);
                                    } else {
                                        mShopDetailAdapter.setNewData(letterList);
                                    }
                                }
                                mShopDetailAdapter.loadMoreComplete();
                            }
                            dismissProcessDialog();
                            swipActShopDetails.setEnabled(true);

                        } else if (type == 2) {

                            initBanner(headBanner, showShopInfo.getShop().getSlideshow());
                            initheadIntroduceView(showShopInfo);

                            List<ShowShopInfo.DemandBean> letterList = showShopInfo.getDemand();
                            mCurrentCounter = letterList.size();

                            if (mCurrentCounter < PAGE_SIZE) {
                                //如果没有数据
                                if (page == 0 && mCurrentCounter == 0) {
                                    letterList.add(new ShowShopInfo.DemandBean());
//                                    //设置空布局
//                                    mShopDetaiDemandlAdapter.setEmptyView(null);
                                }

                                if (page > 0) {
                                    mShopDetaiDemandlAdapter.addData(letterList);
                                } else {
                                    mShopDetaiDemandlAdapter.setNewData(letterList);
                                }
                                mShopDetaiDemandlAdapter.loadMoreEnd(false);
                            } else {
                                if (letterList != null) {
                                    ShopDetailsActivity.this.page++;
                                    if (page > 0) {
                                        mShopDetaiDemandlAdapter.addData(letterList);
                                    } else {
                                        mShopDetaiDemandlAdapter.setNewData(letterList);
                                    }
                                }
                                mShopDetaiDemandlAdapter.loadMoreComplete();
                            }
                            dismissProcessDialog();
                            swipActShopDetails.setEnabled(true);
                        }

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                    }
                });
    }

    private List getBannerImage(String images) {
        String[] imagesUrls = images.split("\\(-_-\\)");
        if (imagesList != null) {
            imagesList.clear();
        }
        for (int i = 0; i < imagesUrls.length; i++) {
            imagesList.add(imagesUrls[i]);
        }
        return imagesList;
    }

    public void initBanner(final Banner banner, String images) {
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(getBannerImage(images));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                startPhotoActivity(ShopDetailsActivity.this, banner, position);
            }
        });
        banner.start();
    }

    public void initheadIntroduceView(ShowShopInfo showShopInfo) {
        textActShopDetailShopName.setText(showShopInfo.getShop().getShopName());
        textActShopDetailShopAddress.setText(showShopInfo.getShop().getAddress());
        textActShopDetailShopTime.setText(showShopInfo.getShop().getWorkTime());
    }

    /**
     * 打开图片查看活动
     *
     * @param context
     * @param imageView 传入宿主控件
     * @param position  传入当前点击Banner的position
     */
    public void startPhotoActivity(Context context, Banner imageView, int position) {

        Intent intent = new Intent(context, ShowPhotoActivity.class);
        intent.putExtra("position", position);
        intent.putStringArrayListExtra("images", (ArrayList<String>) imagesList);

        int location[] = new int[2];

        imageView.getLocationOnScreen(location);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", imageView.getHeight());
        intent.putExtra("width", imageView.getWidth());

        context.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @OnClick({R.id.imv_act_top_back, R.id.imv_act_top_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.imv_act_top_right:
                showMapChoosePop(this);
                break;
        }
    }

    SharePopupWindow mSharePopupWindow;

    /**
     * 地图弹窗的处理方法
     */
    private void showMapChoosePop(Context context) {
        mSharePopupWindow = new SharePopupWindow(context);
        darkenBackground(0.5f);
        mSharePopupWindow.showAtLocation(relActShopDetails.getRootView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        mSharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

        /**
         * SHARE_MEDIA.QQ   QQ授权/分享到QQ好友
         * SHARE_MEDIA.QZONE  分享到QQ空间
         * SHARE_MEDIA.WEIXIN  微信授权
         * SHARE_MEDIA.WEIXIN_CIRCLE  分享到朋友圈
         * SHARE_MEDIA.SINA 微博授权/分享到微博
         */

        mSharePopupWindow.setOnItemClickListener(new SharePopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                switch (v.getId()) {
                    case R.id.imv_pop_choose_share_weixin:
                        shareUtils(SHARE_MEDIA.WEIXIN);
                        break;
                    case R.id.imv_pop_choose_share_pengyouquan:
                        shareUtils(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case R.id.imv_pop_choose_share_xinlang:
                        shareUtils(SHARE_MEDIA.SINA);
                        break;
                    case R.id.imv_pop_choose_share_qqkongjian:
                        shareUtils(SHARE_MEDIA.QZONE);
                        break;
                    case R.id.imv_pop_choose_share_qq:
                        shareUtils(SHARE_MEDIA.QQ);
                        break;
                    case R.id.imv_pop_choose_share_lianjie:
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(Constants.BASE_URL + "share/store.html?id=" + shopId);
                        ToastUtils.showToast(ShopDetailsActivity.this, "链接已经复制到剪切板");
                        break;
                    case R.id.imv_pop_choose_share_close:
                        mSharePopupWindow.dismiss();
                        break;
                }
                mSharePopupWindow.dismiss();
            }
        });

    }

    private void shareUtils(SHARE_MEDIA share_media) {
        UMImage qq_thumb = new UMImage(ShopDetailsActivity.this, R.mipmap._logo);
        UMWeb qq_web = new UMWeb(Constants.SHARE_URL + shopId);
        qq_web.setThumb(qq_thumb);  //缩略图
        qq_web.setTitle("店铺名称:" + shopName);//标题
        qq_web.setDescription("店铺地址:" + shopAddress);//副标题

        new ShareAction(ShopDetailsActivity.this)
                .withMedia(qq_web)
                .setPlatform(share_media)
                .setCallback(shareListener)
                .share();
    }

    /**
     * @name:
     * @description: 分享回调
     * @date: 2017/7/4 15:25
     * @company:
     * @author: Meteor
     */
    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            LogUtils.print("开始分享回调");
            showProcessDialog("", "分享中。。。", false);
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            LogUtils.print("回调完成");
            dismissProcessDialog();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            LogUtils.print("回调错误");
            dismissProcessDialog();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            LogUtils.print("回调取消");
            dismissProcessDialog();
        }
    };

    private MapPopupWindow mapPopupWindow;
    private ShowSubPopupWindow mShowSubPopupWindow;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_act_shop_detail_shop_address_skip:
//                ToastUtils.showToast(this, "跳转地图");
                List<Integer> mapLists = new ArrayList();

                if (isAvilible(ShopDetailsActivity.this, Constants.BAIDU_MAP_NAME)) {
                    mapLists.add(Constants.BAIDU_MAP_TYPE);
                }

                if (isAvilible(ShopDetailsActivity.this, Constants.GAODE_MAP_NAME)) {
                    mapLists.add(Constants.GAODE_MAP_TYPE);
                }

                if (mapLists.size() > 0) {
                    showMapChoosePop(mapLists);
                } else {
                    BaiduNaviUtils.startWebNavi(startLatLng, endLatLng, startName, endName, this);
                }

                break;
            case R.id.text_act_shop_detail_shop_vr:
//                ToastUtils.showToast(this, "跳转VR");
                if (VrUrl.length() > 0 && VrUrl != null) {
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("Url", VrUrl);
                    startActivity(intent);
                } else {
                    ToastUtils.showToast(this, "该功能暂未开放");
                }

                break;
//            case R.id.imv_act_shop_detail_empty:
//                getData(0);
//                break;
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }

    /**
     * 地图弹窗的处理方法
     *
     * @param mapLists 本机所存在地图类型集合
     */
    private void showMapChoosePop(List mapLists) {
        mapPopupWindow = new MapPopupWindow(this, mapLists);
        darkenBackground(0.2f);
        mapPopupWindow.showAtLocation(ShopDetailsActivity.this.findViewById(R.id.rel_act_shop_details), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        mapPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

        mapPopupWindow.setOnItemClickListener(new MapPopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                switch (v.getId()) {
                    case R.id.lay_pop_map_baidu:
                        mapPopupWindow.dismiss();
                        BaiduNaviUtils.startNavi(startLatLng, endLatLng, startName, endName, ShopDetailsActivity.this);
                        break;
                    case R.id.lay_pop_map_gaode:
                        Intent intent = BaiduNaviUtils.openGaode(endLatLng);
                        if (intent != null) {
                            startActivity(intent);
                        } else {
                            ToastUtils.showToast(ShopDetailsActivity.this, "跳转高德地图失败");
                        }
                        mapPopupWindow.dismiss();
                        break;
                }
            }
        });

    }

    /**
     * 地图弹窗的处理方法
     */
    private void showSubPop(final ShowShopInfo.LetterBean item, final int poison) {
        mShowSubPopupWindow = new ShowSubPopupWindow(this);
        darkenBackground(0.5f);
        mShowSubPopupWindow.showAtLocation(ShopDetailsActivity.this.findViewById(R.id.rel_act_shop_details), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

        mShowSubPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

        mShowSubPopupWindow.setOnItemClickListener(new MapPopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_pop_sub_ok:
                        ToastUtils.showToast(ShopDetailsActivity.this, "确认");

                        SubLetterBody body = new SubLetterBody(item.getLetterId(), SharedPreferencesUtils.getString(ShopDetailsActivity.this, Constants.USER_TOKNE));
                        Observable<BaseEntity> observable1 = RetrofitFactory.getInstance().subscribeLetter(body);
                        observable1.compose(RxSchedulers.<BaseEntity>compose())
                                .subscribe(new BaseObserver(ShopDetailsActivity.this) {
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
                                        ToastUtils.showToast(ShopDetailsActivity.this, "预约成功");
                                        mShopDetailAdapter.remove(poison);
                                        mShowSubPopupWindow.dismiss();
                                    }

                                    @Override
                                    protected void onHandleError(String msg) {
                                        ToastUtils.showToast(ShopDetailsActivity.this, "预约失败" + msg);
                                        mShowSubPopupWindow.dismiss();
                                    }
                                });
                        break;
                    case R.id.btn_pop_sub_clean:
                        mShowSubPopupWindow.dismiss();
                        ToastUtils.showToast(ShopDetailsActivity.this, "取消");
                        break;
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        if (type == 1) {
            mShopDetailAdapter.setEnableLoadMore(false);
            //刷新当前页为0页
            page = 0;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData(page);
                    swipActShopDetails.setRefreshing(false);
                    mShopDetailAdapter.setEnableLoadMore(true);
                }
            }, 1000);
        } else if (type == 2) {
            mShopDetaiDemandlAdapter.setEnableLoadMore(false);
            //刷新当前页为0页
            page = 0;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData(page);
                    swipActShopDetails.setRefreshing(false);
                    mShopDetaiDemandlAdapter.setEnableLoadMore(true);
                }
            }, 1000);
        }

    }

    @Override
    public void onLoadMoreRequested() {
        swipActShopDetails.setEnabled(false);
        if (type == 1) {
            if (mShopDetailAdapter.getData().size() < PAGE_SIZE) {
                mShopDetailAdapter.loadMoreEnd(true);
            } else {
                getData(page);
            }
        } else if (type == 2) {
            if (mShopDetaiDemandlAdapter.getData().size() < PAGE_SIZE) {
                mShopDetaiDemandlAdapter.loadMoreEnd(true);
            } else {
                getData(page);
            }
        }

    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        BaiduNaviUtils.onDestroy(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 10:
                switch (resultCode) {
                    case 0:
                        if (data.getBooleanExtra("isOk", false)) {
                            if (mList != null && mShopDetailAdapter != null) {
                                if (data.getIntExtra("position", -1) > -1) {
                                    mShopDetailAdapter.remove(data.getIntExtra("position", -1));
                                }
                            }
                        }
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}

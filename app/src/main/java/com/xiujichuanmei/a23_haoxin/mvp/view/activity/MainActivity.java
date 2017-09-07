package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Circle;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.base.RxBus;
import com.project.lx.baseproject.bean.request.SearchBody;
import com.project.lx.baseproject.bean.request.ShopOnMapBody;
import com.project.lx.baseproject.bean.responses.AttentionContentInfo;
import com.project.lx.baseproject.bean.responses.AttentionInfo;
import com.project.lx.baseproject.bean.responses.ShopInfo;
import com.project.lx.baseproject.bean.responses.ShopOnMapInfo;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.SlidingMenu;
import com.project.lx.baseproject.widget.UnderLineBtn;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.listener.MyOrientationListener;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;
import com.xiujichuanmei.a23_haoxin.utils.BaiduNaviUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: MainActivity
 * @description: 用户首页
 * @version: 1.0
 * @date: 2017/6/8 10:39
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class MainActivity extends BaseActivity implements MyOrientationListener.OnOrientationListener, BaiduMap.OnMapStatusChangeListener, View.OnClickListener, OnGetGeoCoderResultListener {

    @BindView(R.id.imv_act_main_my)
    ImageView imvActMainMy;
    @BindView(R.id.ulbtn_act_main_letter)
    UnderLineBtn ulbtnActMainLetter;
    @BindView(R.id.ulbtn_act_main_deman)
    UnderLineBtn ulbtnActMainDeman;
    @BindView(R.id.imv_act_main_mes)
    ImageView imvActMainMes;
    @BindView(R.id.rel_act_main_top)
    RelativeLayout relActMainTop;
    @BindView(R.id.sliding_menu)
    SlidingMenu slidingMenu;
    @BindView(R.id.btn_loc)
    ImageButton btnLoc;
    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.btn_fabu)
    ImageButton btnFabu;

    RelativeLayout relActMainLeftNoLogin;
    LinearLayout relActMainLeftLoginUser;

    //未登录页面控件
    Button btnActMainLeftNoLoginLogin;
    Button btnActMainLeftNoLoginSingIn;

    //登录页面控件
    @BindView(R.id.imv_act_main_login_in_user_avatar)
    ImageView imvActMainLoginInUserAvatar;
    @BindView(R.id.text_act_main_login_in_user_username)
    TextView textActMainLoginInUserUsername;
    @BindView(R.id.text_act_main_login_in_user_def_shop)
    TextView textActMainLoginInUserDefShop;
    @BindView(R.id.text_act_main_login_in_user_def_shop_address)
    TextView textActMainLoginInUserDefShopAddress;
    @BindView(R.id.text_act_main_login_in_user_attention_number)
    TextView textActMainLoginInUserAttentionNumber;
    @BindView(R.id.text_act_main_login_in_user_attention)
    TextView textActMainLoginInUserAttention;
    @BindView(R.id.text_act_main_login_in_user_be_attention_number)
    TextView textActMainLoginInUserBeAttentionNumber;
    @BindView(R.id.text_act_main_login_in_user_be_attention)
    TextView textActMainLoginInUserBeAttention;
    @BindView(R.id.text_act_main_login_in_user_search)
    TextView textActMainLoginInUserSearch;
    @BindView(R.id.text_act_main_login_in_user_news)
    TextView textActMainLoginInUserNews;
    @BindView(R.id.text_act_main_login_in_user_find_letters)
    TextView textActMainLoginInUserFindLetters;
    @BindView(R.id.text_act_main_login_in_user_wait_letters)
    TextView textActMainLoginInUserWaitLetters;
    @BindView(R.id.text_act_main_login_in_user_setting)
    TextView textActMainLoginInUserSetting;
    @BindView(R.id.lay_act_main_login_user_attention)
    LinearLayout layActMainLoginUserAttention;
    @BindView(R.id.lay_act_main_login_user_be_attention)
    LinearLayout layActMainLoginUserBeAttention;

    //判断是否需要登录
    private boolean isNeedLogin = true;

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    public LocationClient mlocation;

    UserInfo mUserInfo;

    private boolean isFirstLocate;

    //地图标记物集合
    List<Marker> markers;

    //当前选择的状态（1：找信，2：等信 默认为：1）
    private int type = 1;

    //精度半径
    float mCurrentAccracy;
    //自己当前的经度
    double mCurrentLantitude;
    //自己当前的纬度
    double mCurrentLongitude;

    private int mXDirection;
    private MyLocationData.Builder builder;
    private MyOrientationListener myOrientationListener;

    // 搜索模块，也可去掉地图模块独立使用
    GeoCoder mSearch = null;

    //存放当前位置
    String privence;
    String city;
    String area;

    //需要比较的市区
    String cityC;
    //需要比较的地区
    String district;

    //地图锚点信息
    List<ShopOnMapInfo> shopOnMapInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置个性化地图样式，必须在此处设置（在MapView创建之前设置）
        setMapCustomFile(this, "23_haoxin_map_style.txt");

        super.onCreate(savedInstanceState);

        mlocation = new LocationClient(getApplicationContext());
        mlocation.registerLocationListener(new MyLocationListener());

        isFirstLocate = true;
        requestLocation();

        markers = new ArrayList();

        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {

        relActMainLeftNoLogin = (RelativeLayout) findViewById(R.id.lay_act_main_no_login);
        relActMainLeftLoginUser = (LinearLayout) findViewById(R.id.lay_act_main_login_user);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        //隐藏缩放控件
        mMapView.showZoomControls(false);
        //设置开启个性化地图
        MapView.setMapCustomEnable(true);
        //关闭SlidingMenu的滑动监听
        slidingMenu.setTouch(false);

    }

    /**
     * 初始化未登录页面
     */
    private void initNoLoginView() {
        relActMainLeftNoLogin.setVisibility(View.VISIBLE);
        relActMainLeftLoginUser.setVisibility(View.GONE);

        btnActMainLeftNoLoginLogin = (Button) findViewById(R.id.btn_act_main_left_no_login_login);
        btnActMainLeftNoLoginSingIn = (Button) findViewById(R.id.btn_act_main_left_no_login_sing_in);

        btnActMainLeftNoLoginLogin.setOnClickListener(this);
        btnActMainLeftNoLoginSingIn.setOnClickListener(this);
    }

    /**
     * 初始化用户登录页面
     */
    private void initUserLoginView() {
        relActMainLeftLoginUser.setVisibility(View.VISIBLE);
        relActMainLeftNoLogin.setVisibility(View.GONE);

        mUserInfo = MyApplication.getInstance().getCurrentUser();

        if (mUserInfo != null) {
            textActMainLoginInUserAttentionNumber.setText(mUserInfo.getUserInfor().getAttentionCount() + "");
            textActMainLoginInUserBeAttentionNumber.setText(mUserInfo.getUserInfor().getFansCount() + "");
            textActMainLoginInUserUsername.setText(mUserInfo.getUserInfor().getNickName() + "");
            textActMainLoginInUserDefShopAddress.setText(mUserInfo.getUserInfor().getSite() + "");
            textActMainLoginInUserDefShop.setText(privence + area);

            Glide.with(this).
                    load(mUserInfo.getUserInfor().getHeadImg())
                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
                    .bitmapTransform(new GlideCircleTransform(this))
                    .into(imvActMainLoginInUserAvatar);
        }
    }

    /**
     * 设置个性化地图config文件路径
     *
     * @param context
     * @param PATH    assest/customConfigdir 文件夹下对应的个性化地图的文件名
     */
    private void setMapCustomFile(Context context, String PATH) {
        FileOutputStream out = null;
        InputStream inputStream = null;
        String moduleName = null;
        try {
            inputStream = context.getAssets()
                    .open("customConfigdir/" + PATH);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            moduleName = context.getFilesDir().getAbsolutePath();
            File f = new File(moduleName + "/" + PATH);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            out = new FileOutputStream(f);
            out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //设置个性化地图属性文件
        MapView.setCustomMapStylePath(moduleName + "/" + PATH);
    }

    @Override
    public void initData() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        boolean i = getIntent().getBooleanExtra(Constants.IS_NEED_LOGIN, true);
        isNeedLogin = i;
    }

    /**
     * 获取店铺在地图上的信息
     */
    public void getShopOnMap(LatLng latLng) {
        ShopOnMapBody shopOnMapBody = new ShopOnMapBody(latLng.latitude + "", latLng.longitude + "");
        Observable<BaseEntity<List<ShopOnMapInfo>>> observable = RetrofitFactory.getInstance().shopOnMap(shopOnMapBody);
        observable.compose(RxSchedulers.<BaseEntity<List<ShopOnMapInfo>>>compose())
                .subscribe(new BaseObserver<List<ShopOnMapInfo>>(this) {
                    @Override
                    protected void onHandleSuccess(List<ShopOnMapInfo> shopOnMapInfos) {
                        MainActivity.this.shopOnMapInfos = shopOnMapInfos;

                        ShopOnMapInfo shopOnMapInfo;

                        for (int i = 0; i < shopOnMapInfos.size(); i++) {
                            shopOnMapInfo = shopOnMapInfos.get(i);
                            initMarker(shopOnMapInfo);
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {

                    }
                });
    }

    @Override
    public void initListener() {
        //初始化方向传感器
        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setOnOrientationListener(this);
        //设置地图变化监听
        mBaiduMap.setOnMapStatusChangeListener(this);
        // 设置自定义图标
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_zhaoxin_weizhi_dibiao);

        MyLocationConfiguration configuration = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker, 0, 0);

        mBaiduMap.setMyLocationConfiguration(configuration);

        //为地图添加触摸监听，触摸时隐藏标记物弹窗
        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                mBaiduMap.hideInfoWindow();
            }
        });

        //为地图添加点击监听
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                initMarkerPain(latLng);

                for (Marker marker : markers) {
                    marker.remove();
                }
                markers.clear();

                getShopOnMap(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                initMarkerPain(mapPoi.getPosition());

                for (Marker marker : markers) {
                    marker.remove();
                }
                markers.clear();

                getShopOnMap(mapPoi.getPosition());

                return false;
            }
        });
    }

    //点击地图出现的笔
    Marker paint;
    //笔周围的圆圈
    Circle circle;

    /**
     * @param latLng
     */
    public void initMarkerPain(LatLng latLng) {

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_zhaoxin_weizhi_bi);
//
//        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//
//        giflist.add( BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_denglu_disanfang_weibo));
//        giflist.add( BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_denglu_disanfang_qq));
//        giflist.add( BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_denglu_disanfang_weixin));

        //构建MarkerOption，用于在地图上添加Marker
        MarkerOptions option = new MarkerOptions()
                .position(latLng)
                .perspective(true)
                .animateType(MarkerOptions.MarkerAnimateType.grow)
//                .icons(giflist)
                .icon(bitmap);

        //在地图上添加Marker，并显示
        if (paint != null) {
            paint.remove();
        }
        if (circle != null) {
            circle.remove();
        }

        // 添加圆
        OverlayOptions ooCircle = new CircleOptions().fillColor(getResources().getColor(R.color.ral_act_main_line))
                .center(latLng)
//                .stroke(new Stroke(0, getResources().getColor(R.color.black_text_666)))
                .radius(3000);

        circle = (Circle) mBaiduMap.addOverlay(ooCircle);

        paint = (Marker) mBaiduMap.addOverlay(option);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isLogin()) {
            isNeedLogin = false;
        } else {
            isNeedLogin = true;
        }

        if (isNeedLogin) {
            initNoLoginView();
        } else {
            initUserLoginView();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (isLogin()) {
            isNeedLogin = false;
        }

        if (isNeedLogin) {
            initNoLoginView();
        } else {
            initUserLoginView();
        }
    }

    /**
     * 完成定位功能，并且移动视角到地图中心
     */
    private void requestLocation() {
        initLocation();
        mlocation.start();
    }

    /**
     * 移动视图到定位处
     */
    public void moveCamera() {
        //定义地图状态(完成定位后移动视图到定位处)
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(new LatLng(mCurrentLantitude, mCurrentLongitude))
                .zoom(Constants.MAP_ZOOM)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(1000);
        //设置百度坐标
        option.setCoorType("bd09ll");
        option.setOpenGps(true); // 打开gps
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        mlocation.setLocOption(option);
    }

    @Override
    public void onOrientationChanged(float x) {
        //因为图片原因，这里逆时针转90°处理
        mXDirection = (int) x - 90;
        builder = new MyLocationData.Builder();
        builder.accuracy(mCurrentAccracy);
        builder.direction(mXDirection);
        builder.latitude(mCurrentLantitude);
        builder.longitude(mCurrentLongitude);
        MyLocationData locationData = builder.build();
        mBaiduMap.setMyLocationData(locationData);

    }

    @OnClick({R.id.lay_act_main_login_user_attention, R.id.lay_act_main_login_user_be_attention, R.id.btn_fabu, R.id.btn_loc, R.id.imv_act_main_my, R.id.ulbtn_act_main_letter, R.id.ulbtn_act_main_deman, R.id.imv_act_main_mes,
            R.id.imv_act_main_login_in_user_avatar, R.id.text_act_main_login_in_user_username, R.id.text_act_main_login_in_user_def_shop,
            R.id.text_act_main_login_in_user_def_shop_address, R.id.text_act_main_login_in_user_search, R.id.text_act_main_login_in_user_news,
            R.id.text_act_main_login_in_user_find_letters, R.id.text_act_main_login_in_user_wait_letters,
            R.id.text_act_main_login_in_user_setting})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.imv_act_main_my:
                slidingMenu.openMenu();
                //判断是否为登录状态
                if (isLogin()) {
                    Observable<BaseEntity<AttentionContentInfo>> observable = RetrofitFactory.getInstance().showMyAttentionFansCount(new SearchBody(MyApplication.getInstance().getCurrentUser().getUserToken()));
                    observable.compose(RxSchedulers.<BaseEntity<AttentionContentInfo>>compose())
                            .subscribe(new BaseObserver<AttentionContentInfo>(this) {
                                @Override
                                protected void onHandleSuccess(AttentionContentInfo attentionContentInfo) {
                                    textActMainLoginInUserAttentionNumber.setText(attentionContentInfo.getAttentionCount() + "");
                                    textActMainLoginInUserBeAttentionNumber.setText(attentionContentInfo.getFansCount() + "");
                                }

                                @Override
                                protected void onHandleError(String msg) {

                                }
                            });
                } else {
//                    //如果未登录跳转到登录页面
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                if (textActMainLoginInUserDefShop != null) {
                    textActMainLoginInUserDefShop.setText(privence + city);
                }
                break;
            case R.id.ulbtn_act_main_letter:
                type = 1;
                choseBtn(ulbtnActMainLetter);
                break;
            case R.id.ulbtn_act_main_deman:
                type = 2;
                choseBtn(ulbtnActMainDeman);
                break;
            case R.id.imv_act_main_mes:
                if (isLogin()) {
                    if (type == 1) {
                        startActivity(new Intent(MainActivity.this, UserFindLetterActivity.class));
                    } else if (type == 2) {
                        startActivity(new Intent(MainActivity.this, UserWaitLetterActivity.class));
                    }
                } else {
                    ToastUtils.showToast(MainActivity.this, "您需要先登录");
                    slidingMenu.openMenu();
                }
                break;
            case R.id.btn_fabu:
                if (isLogin()) {
                    if (type == 1) {
                        startActivity(new Intent(MainActivity.this, ReleaseLetterActivity.class));
                    } else if (type == 2) {
                        startActivity(new Intent(MainActivity.this, ReleaseDemandActivity.class));
                    }
                } else {
                    ToastUtils.showToast(MainActivity.this, "您需要先登录");
                    slidingMenu.openMenu();
                }
                break;
            case R.id.btn_loc:
                moveCamera();
                break;

            case R.id.lay_act_main_login_user_attention:
                intent.setClass(this, AttentionListActivity.class);
                intent.putExtra(Constants.ATTENTION, Constants.USER_ATTENTION);
                startActivity(intent);
                break;
            case R.id.lay_act_main_login_user_be_attention:
                intent.setClass(this, AttentionListActivity.class);
                intent.putExtra(Constants.ATTENTION, Constants.USER_BE_ATTENTION);
                startActivity(intent);
                break;

            case R.id.imv_act_main_login_in_user_avatar:
                intent = new Intent(this, PhotoViewActivity.class);
//                intent.putExtra("images", (ArrayList<String>) datas);//非必须
//                intent.putExtra("position", position);
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);//必须
                intent.putExtra("locationY", location[1]);//必须

                intent.putExtra("url", MyApplication.getInstance().getCurrentUser().getUserInfor().getHeadImg());//必须

                intent.putExtra("width", view.getWidth());//必须
                intent.putExtra("height", view.getHeight());//必须
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.text_act_main_login_in_user_username:
                break;

            case R.id.text_act_main_login_in_user_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.text_act_main_login_in_user_news:
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case R.id.text_act_main_login_in_user_find_letters:
                startActivity(new Intent(this, UserFindLetterActivity.class));
                break;
            case R.id.text_act_main_login_in_user_wait_letters:
                startActivity(new Intent(this, UserWaitLetterActivity.class));
                break;
            case R.id.text_act_main_login_in_user_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }

    /**
     * 选择顶部按钮
     *
     * @param btn
     */
    public void choseBtn(UnderLineBtn btn) {
        if (btn == ulbtnActMainLetter) {
            if (!ulbtnActMainLetter.isChecked()) {
                ulbtnActMainLetter.setChecked(true);
                ulbtnActMainDeman.setChecked(false);
            }
        } else {
            if (!ulbtnActMainDeman.isChecked()) {
                ulbtnActMainDeman.setChecked(true);
                ulbtnActMainLetter.setChecked(false);
            }
        }
    }

    /**
     * 地图状态开始改变
     *
     * @param mapStatus
     */
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    /**
     * 地图状态改变中
     *
     * @param mapStatus
     */
    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    /**
     * 地图状态改变结束
     *
     * @param mapStatus 中心点信息
     */
    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        LatLng _latLng = mapStatus.target;
        Logger.t("liuxing");
        Logger.i(_latLng.latitude + "," + _latLng.longitude);

        LogUtils.print("地区编码：" + mapStatus.describeContents());
        LogUtils.print("地区编码：" + mapStatus.toString());

        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(_latLng));

//        initMarker(_latLng);
    }

    /**
     * 初始化标记物，并设置点击状态
     *
     * @param shopOnMapInfo
     */
    public void initMarker(final ShopOnMapInfo shopOnMapInfo) {

        //定义Maker坐标点
        LatLng point = new LatLng(shopOnMapInfo.getShopLatitude(), shopOnMapInfo.getShopLongitude());
//        LatLng point = new LatLng(_latLng.latitude, _latLng.longitude);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_zhaoxin_dibiao);

        //构建MarkerOption，用于在地图上添加Marker
        MarkerOptions option = new MarkerOptions()
                .position(point)
                .title(shopOnMapInfo.getShopName())
                .animateType(MarkerOptions.MarkerAnimateType.grow)
                .perspective(true)
                .icon(bitmap);

        //在地图上添加Marker，并显示
        Marker marker = (Marker) mBaiduMap.addOverlay(option);

        markers.add(marker);

        //为Marker加入点击监听
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                for (int i = 0; i < markers.size(); i++) {
                    if (marker == markers.get(i)) {
                        //自定义的弹框
                        View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.map_info_window, null);
                        TextView name = (TextView) rootView.findViewById(R.id.tv_map_info_window_name);
                        TextView phone = (TextView) rootView.findViewById(R.id.tv_map_info_window_phon);

                        name.setText(shopOnMapInfos.get(i).getShopName());
                        phone.setText(shopOnMapInfos.get(i).getShopPhone());

                        //自定义标记物图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.icon_zhaoxin_dibiao);

                        //给弹框一个偏移量
                        int height = bitmap.getBitmap().getHeight();
                        mInfoWindow = new InfoWindow(rootView, marker.getPosition(), -height);

                        mBaiduMap.showInfoWindow(mInfoWindow);

                        //这里为了防止数据错乱
                        final int finalI = i;
                        rootView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mBaiduMap.hideInfoWindow();
                                Intent intent = new Intent(MainActivity.this, ShopDetailsActivity.class);
                                intent.putExtra(Constants.HOME_TYPE, type);
                                intent.putExtra(Constants.SHOP_ID, shopOnMapInfos.get(finalI).getShopId());
                                intent.putExtra(Constants.SHOP_NAME, shopOnMapInfos.get(finalI).getShopName());
                                if (isLogin()) {
                                    startActivity(intent);
                                } else {
                                    ToastUtils.showToast(MainActivity.this, "您需要先登录！");
                                    slidingMenu.openMenu();
                                }
                            }
                        });
                    }
                }
                return false;
            }
        });
    }

    InfoWindow mInfoWindow;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_act_main_left_no_login_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.btn_act_main_left_no_login_sing_in:
                startActivity(new Intent(this, SingInActivity.class));
                break;
        }
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(MainActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
//        Toast.makeText(MainActivity.this, result.getAddress(),
//                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(MainActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
//        Toast.makeText(MainActivity.this, result.getAddress(),
//                Toast.LENGTH_LONG).show();
        //中心点搜索预留
        LogUtils.print("province" + result.getAddressDetail().province);
        LogUtils.print("city" + result.getAddressDetail().city);
        LogUtils.print("district" + result.getAddressDetail().district);

        String district = result.getAddressDetail().district;
        String city = result.getAddressDetail().city;

        if (TextUtils.isEmpty(cityC)){
            return;
        }else {
            //以市为单位，进行查询
            if (cityC.equals(city)) {
                return;
            } else {
                cityC = city;
                for (Marker marker : markers) {
                    marker.remove();
                }
                markers.clear();
//            getShopOnMap(result.getAddressDetail().province, result.getAddressDetail().city, result.getAddressDetail().district);
            }
        }

//        if (this.district.equals(district)) {
//            return;
//        } else {
//            this.district = district;
//            for (Marker marker : markers) {
//                marker.remove();
//            }
//            markers.clear();
//            getShopOnMap(result.getAddressDetail().province, result.getAddressDetail().city, result.getAddressDetail().district);
//        }
    }

    /**
     * 定位接口
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            Logger.i("listener");
            final StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("经度：").append(bdLocation.getLatitude()).append("\n");
            currentPosition.append("纬度：").append(bdLocation.getLongitude()).append("\n");
            currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
            currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
            currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
            currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
            currentPosition.append("定位方式：");
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("gps");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("net");
            }
            Logger.t("定位测试");

            privence = bdLocation.getProvince();
            city = bdLocation.getCity();
            area = bdLocation.getDistrict();

            SharedPreferencesUtils.putString(getApplicationContext(), Constants.ADREESS_PROVINCE, bdLocation.getProvince());
            SharedPreferencesUtils.putString(getApplicationContext(), Constants.ADREESS_CITY, bdLocation.getCity());
            SharedPreferencesUtils.putString(getApplicationContext(), Constants.ADREESS_DISTRICT, bdLocation.getDistrict());

            Logger.i(currentPosition + "");
            Logger.i("城市编码：" + bdLocation.getCityCode());

            mCurrentAccracy = bdLocation.getRadius();
            mCurrentLantitude = bdLocation.getLatitude();
            mCurrentLongitude = bdLocation.getLongitude();

            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                //设置地图显示
                navigateTo(bdLocation);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            Logger.i(s + "\n" + i);
        }

        //导航定位
        private void navigateTo(BDLocation location) {
            if (isFirstLocate) {
                //定位坐标
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                String s = BaiduNaviUtils.latLngToString(ll);

                SharedPreferencesUtils.putString(MainActivity.this, "LatLng", s);
                SharedPreferencesUtils.putString(MainActivity.this, "StartName", location.getStreet());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(update);
                //设置缩放级别
                update = MapStatusUpdateFactory.zoomTo(Constants.MAP_ZOOM);
                mBaiduMap.animateMapStatus(update);
                getShopOnMap(ll);
                district = area;
                cityC = city;
                initMarkerPain(ll);
                isFirstLocate = false;
                moveCamera();
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        myOrientationListener.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mlocation.stop();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        myOrientationListener.stop();
        mSearch.destroy();
        super.onDestroy();
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }

    }
}

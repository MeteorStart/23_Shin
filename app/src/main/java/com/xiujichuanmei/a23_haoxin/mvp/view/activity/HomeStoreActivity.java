package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.SearchBody;
import com.project.lx.baseproject.bean.request.ShopsLetterBody;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.project.lx.baseproject.bean.responses.ShopInfo;
import com.project.lx.baseproject.bean.responses.ShopLetterContentsInfo;
import com.project.lx.baseproject.bean.responses.ShopLetterInfo;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.CustomLoadMoreView;
import com.project.lx.baseproject.widget.SlidingMenu;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.ShopOnLineLetterAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: HomeStoreActivity
 * @description: 商家首页
 * @version: 1.0
 * @date: 2017/6/13 9:49
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class HomeStoreActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.imv_act_main_store_my)
    ImageView imvActMainStoreMy;
    @BindView(R.id.sliding_menu)
    SlidingMenu slidingMenu;

    RelativeLayout relActMainLeftNoLogin;
    LinearLayout relActMainLeftLoginUser;

    //未登录页面控件
    Button btnActMainLeftNoLoginLogin;
    Button btnActMainLeftNoLoginSingIn;

    UserInfo mUserInfo;

    @BindView(R.id.imv_act_main_login_in_shop_avatar)
    ImageView imvActMainLoginInShopAvatar;
    @BindView(R.id.text_act_main_login_in_shop_username)
    TextView textActMainLoginInShopUsername;
    @BindView(R.id.text_act_main_login_in_shop_online_letters_number)
    TextView textActMainLoginInShopOnlineLettersNumber;
    @BindView(R.id.text_act_main_login_in_shop_online_letters)
    TextView textActMainLoginInShopOnlineLetters;
    @BindView(R.id.text_act_main_login_in_shop_flow_letters_number)
    TextView textActMainLoginInShopFlowLettersNumber;
    @BindView(R.id.text_act_main_login_in_shop_flow_letters)
    TextView textActMainLoginInShopFlowLetters;
    @BindView(R.id.text_act_main_login_in_shop_richscan)
    TextView textActMainLoginInShopRichscan;
    @BindView(R.id.text_act_main_login_in_shop_search)
    TextView textActMainLoginInShopSearch;
    @BindView(R.id.text_act_main_login_in_shop_news)
    TextView textActMainLoginInShopNews;
    @BindView(R.id.text_act_main_login_in_shop_letters)
    TextView textActMainLoginInShopLetters;
    @BindView(R.id.text_act_main_login_in_shop_setting)
    TextView textActMainLoginInShopSetting;
    @BindView(R.id.lay_act_main_login_shop_online_letters)
    LinearLayout layActMainLoginShopOnlineLetters;
    @BindView(R.id.lay_act_main_login_shop_flow_letters)
    LinearLayout layActMainLoginShopFlowLetters;
    //    @BindView(R.id.imv_act_main_store_mes)
//    ImageView imvActMainStoreMes;
    @BindView(R.id.rel_act_main_store_top)
    RelativeLayout relActMainStoreTop;
    @BindView(R.id.recy_act_main_store)
    RecyclerView recyActMainStore;
    @BindView(R.id.imv_act_main_store_scan)
    ImageView imvActMainStoreScan;

    ShopOnLineLetterAdapter adapter;
    @BindView(R.id.swip_act_main_store)
    SwipeRefreshLayout swipActMainStore;

    //每页加载个数
    private static final int PAGE_SIZE = 6;
    //当前页 起始页page = 0
    private int Page = 0;
    //当前加载数据个数
    private int mCurrentCounter;

    private List<ShopLetterInfo.LetterBean> letterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_home_store);
    }

    @Override
    public void initView() {
        //关闭SlidingMenu的滑动监听
        slidingMenu.setTouch(false);

        relActMainLeftNoLogin = (RelativeLayout) findViewById(R.id.lay_act_main_no_login);
        relActMainLeftLoginUser = (LinearLayout) findViewById(R.id.lay_act_main_login_shop);

        if (isLogin()) {
            initShopLoginView();
        } else {
            initNoLoginView();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyActMainStore.setLayoutManager(layoutManager);
        recyActMainStore.addItemDecoration(new SimpleDicider());

        //添加下拉刷新
        swipActMainStore.setColorSchemeColors(getResources().getColor(R.color.act_main_line));

    }

    @Override
    public void initData() {
        showProcessDialog("", "加载中。。。", true);
        letterList = new ArrayList<>();

        adapter = new ShopOnLineLetterAdapter(R.layout.item_act_main_store, letterList);
        //设置加载布局
        adapter.setLoadMoreView(new CustomLoadMoreView());
        //设置载入动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recyActMainStore.setAdapter(adapter);
        mCurrentCounter = adapter.getData().size();

        swipActMainStore.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this, recyActMainStore);
        //关闭首次进入默认加载
        adapter.disableLoadMoreIfNotFullPage();

        getNumberData();
        getListData(Page);
    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopLetterInfo.LetterBean item = (ShopLetterInfo.LetterBean) adapter.getItem(position);
                Intent intent = new Intent(HomeStoreActivity.this, LetterDetailActivity.class);
                intent.putExtra("topic", item.getTopicName());
                intent.putExtra("title", item.getLetterTitle());
                intent.putExtra("letterNumber", item.getLetterNumber());
                intent.putExtra("letterId", item.getLetterId());
                intent.putExtra("userName", item.getOwnerName());
                intent.putExtra("userAvatar", item.getOwenerHeadImg());
                intent.putExtra("ownerId", item.getOwnerId());
                intent.putExtra("letterState", item.getLetterState());
                intent.putExtra("shopName",item.getShopName());
                startActivity(intent);
            }
        });
    }

    /**
     * @name: getNumberData
     * @description: 请求加载关注人数
     * @date: 2017/7/3 9:28
     * @company:
     * @author: Meteor
     */
    private void getNumberData() {
        Observable<BaseEntity<ShopLetterContentsInfo>> observable = RetrofitFactory.getInstance().shopsLetterCount(new SearchBody(mUserInfo.getUserToken()));
        observable.compose(RxSchedulers.<BaseEntity<ShopLetterContentsInfo>>compose())
                .subscribe(new BaseObserver<ShopLetterContentsInfo>(this) {
                    @Override
                    protected void onHandleSuccess(ShopLetterContentsInfo shopLetterContentsInfo) {
                        textActMainLoginInShopOnlineLettersNumber.setText(shopLetterContentsInfo.getOnlineCount() + "");
                        textActMainLoginInShopFlowLettersNumber.setText(shopLetterContentsInfo.getCirculateCount() + "");
                    }

                    @Override
                    protected void onHandleError(String msg) {
                    }
                });
    }

    /**
     * @name: getListDate
     * @description: 请求加载列表数据
     * @date: 2017/7/3 9:27
     * @company:
     * @author: Meteor
     */
    private void getListData(final int page) {
        Observable<BaseEntity<ShopLetterInfo>> observable1 = RetrofitFactory.getInstance().shopsLetter(new ShopsLetterBody(page, PAGE_SIZE, 1, mUserInfo.getUserToken()));
        observable1.compose(RxSchedulers.<BaseEntity<ShopLetterInfo>>compose())
                .subscribe(new BaseObserver<ShopLetterInfo>(this) {
                    @Override
                    protected void onHandleSuccess(ShopLetterInfo shopLetterInfo) {
                        List<ShopLetterInfo.LetterBean> letterList = shopLetterInfo.getLetter();
                        mCurrentCounter = letterList.size();

                        swipActMainStore.setRefreshing(false);
                        adapter.setEnableLoadMore(true);

                        if (mCurrentCounter < PAGE_SIZE) {
                            //如果没有数据
                            if (page == 0 && mCurrentCounter == 0) {
                                //设置空布局
//                                adapter.setEmptyView(null);
                            }

                            if (page > 0) {
                                adapter.addData(letterList);
                            } else {
                                adapter.setNewData(letterList);
                            }
                            adapter.loadMoreEnd(false);
                        } else {
                            if (letterList != null) {
                                Page++;
                                if (page > 0) {
                                    adapter.addData(letterList);
                                } else {
                                    adapter.setNewData(letterList);
                                }
                            }
                            adapter.loadMoreComplete();
                        }
                        dismissProcessDialog();
                        swipActMainStore.setEnabled(true);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                        adapter.loadMoreFail();
                        Toast.makeText(HomeStoreActivity.this, "加载失败" + msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 初始化未登录页面
     */
    private void initNoLoginView() {
        relActMainLeftNoLogin.setVisibility(View.VISIBLE);
        relActMainLeftLoginUser.setVisibility(View.GONE);

        btnActMainLeftNoLoginLogin = (Button) findViewById(R.id.btn_act_main_left_no_login_login);
        btnActMainLeftNoLoginSingIn = (Button) findViewById(R.id.btn_act_main_left_no_login_sing_in);

//        btnActMainLeftNoLoginLogin.setOnClickListener(this);
//        btnActMainLeftNoLoginSingIn.setOnClickListener(this);
    }

    /**
     * 初始化商家登录页面
     */
    private void initShopLoginView() {
        relActMainLeftLoginUser.setVisibility(View.VISIBLE);
        relActMainLeftNoLogin.setVisibility(View.GONE);

        mUserInfo = MyApplication.getInstance().getCurrentUser();

        if (mUserInfo != null) {
            Glide.with(this).
                    load(mUserInfo.getUserInfor().getHeadImg())
                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
                    .bitmapTransform(new GlideCircleTransform(this))
                    .into(imvActMainLoginInShopAvatar);

            textActMainLoginInShopUsername.setText(mUserInfo.getUserInfor().getNickName());
        }
    }


    @OnClick({R.id.imv_act_main_store_scan, R.id.lay_act_main_login_shop_online_letters, R.id.lay_act_main_login_shop_flow_letters,
            R.id.imv_act_main_store_my, R.id.btn_act_main_left_no_login_login, R.id.btn_act_main_left_no_login_sing_in, R.id.imv_act_main_login_in_shop_avatar,
            R.id.text_act_main_login_in_shop_username, R.id.text_act_main_login_in_shop_richscan, R.id.text_act_main_login_in_shop_search,
            R.id.text_act_main_login_in_shop_news, R.id.text_act_main_login_in_shop_letters, R.id.text_act_main_login_in_shop_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_main_store_my:
                slidingMenu.openMenu();
                break;

            case R.id.btn_act_main_left_no_login_login:
                break;
            case R.id.btn_act_main_left_no_login_sing_in:
                break;

            case R.id.imv_act_main_login_in_shop_avatar:
                break;
            case R.id.text_act_main_login_in_shop_username:
                break;
            case R.id.lay_act_main_login_shop_online_letters:
                startActivity(new Intent(this, OnLineLetterActivity.class));
                break;
            case R.id.lay_act_main_login_shop_flow_letters:
                startActivity(new Intent(this, FlowLetterActivity.class));
                break;

            case R.id.text_act_main_login_in_shop_richscan:
                customScan();
                break;
            case R.id.text_act_main_login_in_shop_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.text_act_main_login_in_shop_news:
                startActivity(new Intent(this, MessageActivity.class));
//                ToastUtils.showToast(this, "跳转消息页面");
                break;
            case R.id.text_act_main_login_in_shop_letters:
//                ToastUtils.showToast(this, "跳转待中转信件页面");
                break;
            case R.id.text_act_main_login_in_shop_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.imv_act_main_store_scan:
                customScan();
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
                Intent intent = new Intent(this, UserInfoToScanActivity.class);
                intent.putExtra("ScanResult", ScanResult);
                startActivity(intent);
//                tvActReleaseLetterReceiveNumber.setText(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        //刷新当前页为0页
        Page = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getListData(Page);
            }
        }, 1000);
    }

    @Override
    public void onLoadMoreRequested() {
        swipActMainStore.setEnabled(false);
        if (adapter.getData().size() < PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        } else {
            getListData(Page);
        }
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

package com.xiujichuanmei.a23_haoxin.mvp.view.fragment;

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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseFragment;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.ShowMyLetterBody;
import com.project.lx.baseproject.bean.request.TimeBody;
import com.project.lx.baseproject.bean.responses.AttentionInfo;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.project.lx.baseproject.bean.responses.TimeInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.CustomLoadMoreView;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.UserFindLetterSubAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;
import com.xiujichuanmei.a23_haoxin.mvp.model.UserFindLetterModelImpl;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.IUserFindLetterModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnShowMyLetterListener;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.AttentionListActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.LetterDetailActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.PhotoViewActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.ShopDetailsActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.UserFindLetterActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.UserInforActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.MapPopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowScanPopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowTimePopupWindow;
import com.xiujichuanmei.a23_haoxin.utils.BaiduNaviUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;


/**
 * @author: X_Meteor
 * @description: 用户找信，预约Fragment
 * @version: V_1.0.0
 * @date: 2017/6/16 14:10
 * @company:
 * @email: lx802315@163.com
 */
public class UserFindLetterSubFragment extends BaseFragment implements OnShowMyLetterListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swip_frag_sub)
    SwipeRefreshLayout swipFragSub;

    @BindView(R.id.recy_frag_user_find_sub)
    RecyclerView recyFragUserFindSub;

    UserFindLetterActivity activity;

    UserFindLetterSubAdapter adapter;

    IUserFindLetterModel model;

    //跳转地图相关
    MapPopupWindow mapPopupWindow;
    LatLng endLatLng;
    LatLng startLatLng;
    String startName;
    String endName;

    ShowTimePopupWindow pop;
    View rootView;

    //每页加载个数
    private static final int PAGE_SIZE = 6;
    //当前页 起始页page = 0
    private int Page = 0;
    //当前加载数据个数
    private int mCurrentCounter;

    View emptyView;

    @BindView(R.id.tv_act_shop_detail_empty)
    TextView tvActShopDetailEmpty;

    List<LetterInfo.LetterBean> list;

    @Override
    protected View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        rootView = inflater.inflate(R.layout.frag_user_find_letter_sub, null);
        emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.view_empty_act_shop_details, null);
        return rootView;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        startLatLng = BaiduNaviUtils.stringToLatLng(SharedPreferencesUtils.getString(getActivity(), "LatLng"));
        startName = SharedPreferencesUtils.getString(getActivity(), "StartName");

        activity = (UserFindLetterActivity) getActivity();
        activity.showLoadingDialog("", "加载中。。。", true);
        model = new UserFindLetterModelImpl(activity);

        initView();

        adapter = new UserFindLetterSubAdapter(R.layout.item_frag_user_find_sub, list);
        recyFragUserFindSub.setAdapter(adapter);

        //设置加载布局
        adapter.setLoadMoreView(new CustomLoadMoreView());
        //设置载入动画
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        adapter.setEmptyView(emptyView);

        recyFragUserFindSub.setAdapter(adapter);
        mCurrentCounter = adapter.getData().size();

        swipFragSub.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this, recyFragUserFindSub);
        //关闭首次进入默认加载
        adapter.disableLoadMoreIfNotFullPage();

        getLetters(Page);

        initListener();
    }

    private void initView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyFragUserFindSub.setLayoutManager(layoutManager);
        recyFragUserFindSub.addItemDecoration(new SimpleDicider());

        //添加下拉刷新
        swipFragSub.setColorSchemeColors(getResources().getColor(R.color.act_main_line));

    }

    private void initListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final LetterInfo.LetterBean item = (LetterInfo.LetterBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.imv_frag_user_find_sub_avatar:
                        if (item.getOwnerId() == null) {

                            Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
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
                            getActivity().overridePendingTransition(0, 0);

                        } else {
                            Intent intent = new Intent(getActivity(), UserInforActivity.class);
                            intent.putExtra("HeadImg", item.getOwenerHeadImg());
                            intent.putExtra("UserId", item.getOwnerId());
                            intent.putExtra("NickName", item.getOwnerName());
                            startActivity(intent);
                        }
                        break;
                    case R.id.text_frag_user_find_sub_shop_down_time:
                        TimeBody timeBody = new TimeBody(1, item.getLetterId());
                        Observable<BaseEntity<TimeInfo>> observable = RetrofitFactory.getInstance().showCountdown(timeBody);
                        observable.compose(RxSchedulers.<BaseEntity<TimeInfo>>compose())
                                .subscribe(new BaseObserver<TimeInfo>(getActivity()) {
                                    @Override
                                    protected void onHandleSuccess(TimeInfo timeInfo) {
                                        pop = new ShowTimePopupWindow(getActivity(), timeInfo, item.getOwenerHeadImg(), item.getOwnerName(), item.getLetterNumber());
                                        pop.showAtLocation(rootView, Gravity.CENTER, 0, 0);
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
                    case R.id.imv_frag_user_find_sub_scan:
                        ShowScanPopupWindow popupWindow = new ShowScanPopupWindow(getActivity(), item);
                        popupWindow.showAtLocation(recyFragUserFindSub.getRootView(), Gravity.CENTER, 0, 0);
                        darkenBackground(0.4f);
                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                darkenBackground(1f);
                            }
                        });
                        break;
                    case R.id.tv_frag_user_find_sub_address:
                        List<Integer> mapLists = new ArrayList();
                        endLatLng = BaiduNaviUtils.baiduStringToLatLng(item.getShopCoordinate());
                        endName = item.getShopName();
                        if (isAvilible(getActivity(), Constants.BAIDU_MAP_NAME)) {
                            mapLists.add(Constants.BAIDU_MAP_TYPE);
                        }

                        if (isAvilible(getActivity(), Constants.GAODE_MAP_NAME)) {
                            mapLists.add(Constants.GAODE_MAP_TYPE);
                        }

                        if (mapLists.size() > 0) {
                            showMapChoosePop(mapLists);
                        } else {
                            BaiduNaviUtils.startWebNavi(startLatLng, endLatLng, startName, endName, getActivity());
                        }

                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LetterInfo.LetterBean item = (LetterInfo.LetterBean) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), LetterDetailActivity.class);
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
//                finish();
            }
        });
    }

    @Override
    protected void lazyLoad() {
    }

    public void getLetters(int page) {
        String token = MyApplication.getInstance().getCurrentUser().getUserToken();
        model.getLetterList(new ShowMyLetterBody(Constants.USER_FIND_LETTER_SUB, page, PAGE_SIZE, token), this);
    }

    @Override
    public void OnSearchSuccess(LetterInfo request, int page) {
        List<LetterInfo.LetterBean> letterList = request.getLetter();
        mCurrentCounter = letterList.size();

        swipFragSub.setRefreshing(false);
        adapter.setEnableLoadMore(true);

        if (mCurrentCounter < PAGE_SIZE) {
            //如果没有数据
            if (page == 0 && mCurrentCounter == 0) {
//                if (adapter.getEmptyView() != null){
//                    return;
//                }else {
//                    //设置空布局
//                    adapter.setEmptyView(emptyView);
//                }
                tvActShopDetailEmpty.setVisibility(View.VISIBLE);
            } else {
                tvActShopDetailEmpty.setVisibility(View.GONE);
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
        activity.canelLoadingDialog();

        if (swipFragSub != null) {
            swipFragSub.setEnabled(true);
        }
    }

    @Override
    public void OnSearchError(String msg) {
        adapter.loadMoreFail();
        activity.canelLoadingDialog();
        ToastUtils.showToast(activity, "加载失败" + msg);
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgcolor;

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);

    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        //刷新当前页为0页
        Page = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLetters(Page);

            }
        }, 1000);
    }

    @Override
    public void onLoadMoreRequested() {
        swipFragSub.setEnabled(false);
        if (adapter.getData().size() < PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        } else {
            getLetters(Page);
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
     * 地图弹窗的处理方法
     *
     * @param mapLists 本机所存在地图类型集合
     */
    private void showMapChoosePop(List mapLists) {
        mapPopupWindow = new MapPopupWindow(getActivity(), mapLists);
        darkenBackground(0.2f);
        mapPopupWindow.showAtLocation(recyFragUserFindSub.getRootView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

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
                        BaiduNaviUtils.startNavi(startLatLng, endLatLng, startName, endName, getActivity());
                        break;
                    case R.id.lay_pop_map_gaode:
                        Intent intent = BaiduNaviUtils.openGaode(endLatLng);
                        if (intent != null) {
                            startActivity(intent);
                        } else {
                            ToastUtils.showToast(getActivity(), "跳转高德地图失败");
                        }
                        mapPopupWindow.dismiss();
                        break;
                }
            }
        });

    }
}

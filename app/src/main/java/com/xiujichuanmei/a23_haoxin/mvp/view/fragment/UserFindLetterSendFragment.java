package com.xiujichuanmei.a23_haoxin.mvp.view.fragment;

import android.content.Intent;
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
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseFragment;
import com.project.lx.baseproject.bean.request.ShowMyLetterBody;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.CustomLoadMoreView;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.UserFindLetterSendAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;
import com.xiujichuanmei.a23_haoxin.mvp.model.UserFindLetterModelImpl;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.IUserFindLetterModel;
import com.xiujichuanmei.a23_haoxin.mvp.model.listener.OnShowMyLetterListener;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.LetterDetailActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.PhotoViewActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.UserFindLetterActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.activity.UserInforActivity;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowScanPopupWindow;

import java.util.List;

import butterknife.BindView;

/**
 * @author: X_Meteor
 * @description: 用户找信，发送Fragment
 * @version: V_1.0.0
 * @date: 2017/6/16 14:10
 * @company:
 * @email: lx802315@163.com
 */
public class UserFindLetterSendFragment extends BaseFragment implements OnShowMyLetterListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recy_frag_user_find_sub)
    RecyclerView recyFragUserFindSub;
    @BindView(R.id.swip_frag_send)
    SwipeRefreshLayout swipFragSend;

    UserFindLetterActivity activity;

    UserFindLetterSendAdapter adapter;

    IUserFindLetterModel model;
    //每页加载个数
    private static final int PAGE_SIZE = 6;
    //当前页 起始页page = 0
    private int Page = 0;
    //当前加载数据个数
    private int mCurrentCounter;

    List<LetterInfo.LetterBean> list;

    View emptyView;
    RelativeLayout empty;

    @Override
    protected View initLayout(LayoutInflater inflater, ViewGroup container, boolean b) {
        View rootView = inflater.inflate(R.layout.frag_user_find_letter_send, null);
        empty = (RelativeLayout) rootView.findViewById(R.id.lay_empty);
        emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.view_empty_act_shop_details, null);
        return rootView;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        activity = (UserFindLetterActivity) getActivity();
        activity.showLoadingDialog("", "加载中。。。", true);
        model = new UserFindLetterModelImpl(activity);

        initView();

        adapter = new UserFindLetterSendAdapter(R.layout.item_frag_user_find_send, list);
        recyFragUserFindSub.setAdapter(adapter);

        //设置加载布局
        adapter.setLoadMoreView(new CustomLoadMoreView());
        //设置载入动画
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
//        adapter.setEmptyView(emptyView);

        recyFragUserFindSub.setAdapter(adapter);
        mCurrentCounter = adapter.getData().size();

        swipFragSend.setOnRefreshListener(this);
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
        swipFragSend.setColorSchemeColors(getResources().getColor(R.color.act_main_line));

    }

    private void initListener() {
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
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LetterInfo.LetterBean item = (LetterInfo.LetterBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.imv_frag_user_find_send_avatar:
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
                    case R.id.imv_frag_user_find_send_scan:
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
                }

            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    public void getLetters(int page) {
        model.getLetterList(new ShowMyLetterBody(Constants.USER_FIND_LETTER_SEND, page, PAGE_SIZE, MyApplication.getInstance().getCurrentUser().getUserToken()), this);
    }

    @Override
    public void OnSearchSuccess(LetterInfo request, int page) {
        List<LetterInfo.LetterBean> letterList = request.getLetter();
        mCurrentCounter = letterList.size();

        if (swipFragSend != null){
            swipFragSend.setRefreshing(false);
        }
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
                empty.setVisibility(View.VISIBLE);
            } else {
                empty.setVisibility(View.GONE);
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
        if (swipFragSend != null) {
            swipFragSend.setEnabled(true);
        }
    }

    @Override
    public void OnSearchError(String msg) {
        adapter.loadMoreFail();
        activity.canelLoadingDialog();
        ToastUtils.showToast(activity, "加载失败" + msg);
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
        swipFragSend.setEnabled(false);
        if (adapter.getData().size() < PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        } else {
            getLetters(Page);
        }
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
}

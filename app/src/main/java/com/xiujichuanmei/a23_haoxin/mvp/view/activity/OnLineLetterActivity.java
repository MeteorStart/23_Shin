package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.ShopsLetterBody;
import com.project.lx.baseproject.bean.responses.ShopLetterInfo;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.CustomLoadMoreView;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.ShopOnLineLetterAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: OnLineLetterActivity
 * @description: 在线信件
 * @version: 1.0
 * @date: 2017/6/13 9:53
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class OnLineLetterActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.recy_act_online_letter)
    RecyclerView recyActOnlineLetter;
    @BindView(R.id.swip_act_online_letter)
    SwipeRefreshLayout swipActOnlineLetter;

    ShopOnLineLetterAdapter adapter;

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
        setContentView(R.layout.activity_on_line_letter);
    }

    @Override
    public void initView() {
        textActTop.setText("在线信件");
        imvActTopRight.setVisibility(View.GONE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyActOnlineLetter.setLayoutManager(layoutManager);
        recyActOnlineLetter.addItemDecoration(new SimpleDicider());

        //添加下拉刷新
        swipActOnlineLetter.setColorSchemeColors(getResources().getColor(R.color.act_main_line));

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
        recyActOnlineLetter.setAdapter(adapter);
        mCurrentCounter = adapter.getData().size();

        swipActOnlineLetter.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this, recyActOnlineLetter);
        //关闭首次进入默认加载
        adapter.disableLoadMoreIfNotFullPage();

        getLetterListData(Page);
    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopLetterInfo.LetterBean item = (ShopLetterInfo.LetterBean) adapter.getItem(position);
                Intent intent = new Intent(OnLineLetterActivity.this, LetterDetailActivity.class);
                intent.putExtra("topic", item.getTopicName());
                intent.putExtra("title", item.getLetterTitle());
                intent.putExtra("letterNumber", item.getLetterNumber());
                intent.putExtra("letterId", item.getLetterId());
                intent.putExtra("userName", item.getOwnerName());
                intent.putExtra("userAvatar", item.getOwenerHeadImg());
                intent.putExtra("ownerId", item.getOwnerId());
                intent.putExtra("letterState", item.getLetterState());
                intent.putExtra("userAvatar", item.getOwenerHeadImg());
                intent.putExtra("shopName",item.getShopName());
                startActivity(intent);
            }
        });
    }

    public void getLetterListData(final int page) {
        Observable<BaseEntity<ShopLetterInfo>> observable1 = RetrofitFactory.getInstance().shopsLetter(new ShopsLetterBody(page, PAGE_SIZE, 1, MyApplication.getInstance().getCurrentUser().getUserToken()));
        observable1.compose(RxSchedulers.<BaseEntity<ShopLetterInfo>>compose())
                .subscribe(new BaseObserver<ShopLetterInfo>(this) {
                    @Override
                    protected void onHandleSuccess(ShopLetterInfo shopLetterInfo) {
                        List<ShopLetterInfo.LetterBean> letterList = shopLetterInfo.getLetter();
                        mCurrentCounter = letterList.size();

                        swipActOnlineLetter.setRefreshing(false);
                        adapter.setEnableLoadMore(true);

                        if (mCurrentCounter < PAGE_SIZE) {
                            //如果没有数据
                            if (page == 0 && mCurrentCounter == 0) {
                                //设置空布局
//                                adapter.setEmptyView(null);
                            }

                            if (page > 0){
                                adapter.addData(letterList);
                            }else {
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
                        swipActOnlineLetter.setEnabled(true);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                        adapter.loadMoreFail();
                        Toast.makeText(OnLineLetterActivity.this, "加载失败" + msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.imv_act_top_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        //刷新当前页为0页
        Page = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLetterListData(Page);

            }
        }, 1000);
    }

    @Override
    public void onLoadMoreRequested() {
        swipActOnlineLetter.setEnabled(false);
        if (adapter.getData().size() < PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        }else {
            getLetterListData(Page);
        }
    }
}

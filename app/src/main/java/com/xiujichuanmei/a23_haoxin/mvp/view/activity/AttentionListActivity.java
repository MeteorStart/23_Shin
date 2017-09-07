package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
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
import com.project.lx.baseproject.bean.request.AttentionBody;
import com.project.lx.baseproject.bean.responses.AttentionInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.widget.CustomLoadMoreView;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.AttintionAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class AttentionListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.recy_act_attention_list)
    RecyclerView recyActAttentionList;
    @BindView(R.id.swip_act_main_attention)
    SwipeRefreshLayout swipActMainAttention;

    int type;

    //空布局
    View emptyView;
    ImageView imvActShopDetailEmpty;

    AttintionAdapter attintionAdapter;

    //每页加载个数
    private static final int PAGE_SIZE = 6;
    @BindView(R.id.tv_act_shop_detail_empty)
    TextView tvActShopDetailEmpty;
    //当前页 起始页page = 0
    private int page = 0;
    //当前加载数据个数
    private int mCurrentCounter;

    private List<AttentionInfo.MyAttentionsBean> myFans;

    private List<AttentionInfo.MyAttentionsBean> myAttentions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        type = getIntent().getIntExtra(Constants.ATTENTION, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_attention_list);
    }

    @Override
    public void initView() {
        switch (type) {
            case Constants.USER_ATTENTION:
                textActTop.setText("我关注");
                break;
            case Constants.USER_BE_ATTENTION:
                textActTop.setText("关注我");
                break;
        }
        imvActTopRight.setVisibility(View.GONE);

        emptyView = getLayoutInflater().inflate(R.layout.view_empty_act_shop_details, null);
//        imvActShopDetailEmpty = (ImageView) emptyView.findViewById(R.id.imv_act_shop_detail_empty);
        recyActAttentionList.setLayoutManager(new LinearLayoutManager(AttentionListActivity.this, LinearLayoutManager.VERTICAL, false));
        recyActAttentionList.addItemDecoration(new SimpleDicider());

        //添加下拉刷新
        swipActMainAttention.setColorSchemeColors(getResources().getColor(R.color.act_main_line));
    }

    @Override
    public void initData() {
        showProcessDialog("", "加载中。。。", true);

        myFans = new ArrayList<>();
        myAttentions = new ArrayList<>();

        if (type == 1) {
            attintionAdapter = new AttintionAdapter(R.layout.item_choose_recipi_all, myAttentions);
        } else if (type == 2) {
            attintionAdapter = new AttintionAdapter(R.layout.item_choose_recipi_all, myFans);
        }

        //设置加载布局
        attintionAdapter.setLoadMoreView(new CustomLoadMoreView());
        //设置载入动画
        attintionAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recyActAttentionList.setAdapter(attintionAdapter);
        mCurrentCounter = attintionAdapter.getData().size();

        swipActMainAttention.setOnRefreshListener(this);
        attintionAdapter.setOnLoadMoreListener(this, recyActAttentionList);
        //关闭首次进入默认加载
        attintionAdapter.disableLoadMoreIfNotFullPage();

        getData(page);
    }

    private void getData(final int page) {
        AttentionBody attentionBody = new AttentionBody(page, PAGE_SIZE, type + "", "", MyApplication.getInstance().getCurrentUser().getUserToken());
        Observable<BaseEntity<AttentionInfo>> observable = RetrofitFactory.getInstance().showMyAttentionFans(attentionBody);
        observable.compose(RxSchedulers.<BaseEntity<AttentionInfo>>compose())
                .subscribe(new BaseObserver<AttentionInfo>(this) {
                    @Override
                    protected void onHandleSuccess(final AttentionInfo attentionInfos) {
                        dismissProcessDialog();
                        List<AttentionInfo.MyAttentionsBean> list = new ArrayList<>();
                        if (type == 1) {
                            list = attentionInfos.getMyAttentions();
                        } else if (type == 2) {
                            list = attentionInfos.getMyFans();
                        }
                        mCurrentCounter = list.size();

                        swipActMainAttention.setRefreshing(false);
                        attintionAdapter.setEnableLoadMore(true);

                        if (mCurrentCounter < PAGE_SIZE) {
                            //如果没有数据
                            if (page == 0 && mCurrentCounter == 0) {
                                //设置空布局
//                                attintionAdapter.setEmptyView(null);
                                tvActShopDetailEmpty.setVisibility(View.VISIBLE);
                            }else {
                                tvActShopDetailEmpty.setVisibility(View.GONE);
                            }

                            if (page > 0) {
                                attintionAdapter.addData(list);
                            } else {
                                attintionAdapter.setNewData(list);
                            }

                            attintionAdapter.loadMoreEnd(false);
                        } else {
                            if (list != null) {
                                AttentionListActivity.this.page++;
                                if (page > 0) {
                                    attintionAdapter.addData(list);
                                } else {
                                    attintionAdapter.setNewData(list);
                                }
                            }
                            attintionAdapter.loadMoreComplete();
                        }
                        dismissProcessDialog();
                        swipActMainAttention.setEnabled(true);

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                        attintionAdapter.loadMoreFail();
                        Toast.makeText(AttentionListActivity.this, "加载失败" + msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void initListener() {
        //item点击监听
        attintionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AttentionInfo.MyAttentionsBean item = (AttentionInfo.MyAttentionsBean) adapter.getItem(position);
                Intent intent = new Intent(AttentionListActivity.this, UserInforActivity.class);
                intent.putExtra("HeadImg", item.getHeadImg());
                intent.putExtra("UserId", item.getUserId());
                intent.putExtra("NickName", item.getNickName());
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });

//        //空布局点击图片刷新
//        imvActShopDetailEmpty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                ToastUtils.showToast(AttentionListActivity.this, "刷新一下");
//            }
//        });
    }

    @OnClick(R.id.imv_act_top_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        attintionAdapter.setEnableLoadMore(false);
        //刷新当前页为0页
        page = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(page);

            }
        }, 1000);
    }

    @Override
    public void onLoadMoreRequested() {
        swipActMainAttention.setEnabled(false);
        if (attintionAdapter.getData().size() < PAGE_SIZE) {
            attintionAdapter.loadMoreEnd(true);
        } else {
            getData(page);
        }
    }
}

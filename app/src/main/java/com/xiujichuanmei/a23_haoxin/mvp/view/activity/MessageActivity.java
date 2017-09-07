package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.MessageBody;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.project.lx.baseproject.bean.responses.MessageInfo;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.CustomLoadMoreView;
import com.project.lx.baseproject.widget.UnderLineBtn;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.MessageListAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.UserFindLetterSubAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: MessageActivity
 * @description: 消息通知
 * @version: 1.0
 * @date: 2017/7/6 13:46
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class MessageActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.imv_act_message_back)
    ImageView imvActMessageBack;
    @BindView(R.id.ulbtn_act_message_letter)
    UnderLineBtn ulbtnActMessageLetter;
    @BindView(R.id.ulbtn_act_message_system)
    UnderLineBtn ulbtnActMessageSystem;
    @BindView(R.id.rel_act_main_top)
    RelativeLayout relActMainTop;
    @BindView(R.id.recy_act_message)
    RecyclerView recyActMessage;
    @BindView(R.id.swip_act_message)
    SwipeRefreshLayout swipActMessage;

    MessageListAdapter adapter;

    int type = 2;

    //每页加载个数
    private static final int PAGE_SIZE = 6;
    //当前页 起始页page = 0
    private int Page = 0;
    //当前加载数据个数
    private int mCurrentCounter;

    View emptyView;

    List<MessageInfo> list;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("Type", 2);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_message);
    }

    @Override
    public void initView() {
        emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty_act_shop_details, null);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyActMessage.setLayoutManager(layoutManager);
        recyActMessage.addItemDecoration(new SimpleDicider());

        //添加下拉刷新
        swipActMessage.setColorSchemeColors(getResources().getColor(R.color.act_main_line));
        if (type == 2) {
            choseBtn(ulbtnActMessageLetter);
        } else {
            choseBtn(ulbtnActMessageSystem);
        }
    }

    @Override
    public void initData() {
        list = new ArrayList<>();

        adapter = new MessageListAdapter(R.layout.item_act_message, list);
        recyActMessage.setAdapter(adapter);

        //设置加载布局
        adapter.setLoadMoreView(new CustomLoadMoreView());
        //设置载入动画
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //设置空布局
//        adapter.setEmptyView(emptyView);

        recyActMessage.setAdapter(adapter);
        mCurrentCounter = adapter.getData().size();

        swipActMessage.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this, recyActMessage);
        //关闭首次进入默认加载
        adapter.disableLoadMoreIfNotFullPage();

        getMessage(Page);
    }

    @Override
    public void initListener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (MyApplication.getInstance().getCurrentUser() != null) {
            if (MyApplication.getInstance().getCurrentUser().getUserInfor().getRole() == 1) {
                startActivity(new Intent(this, HomeStoreActivity.class));
            }
        }else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.imv_act_message_back, R.id.ulbtn_act_message_letter, R.id.ulbtn_act_message_system})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_message_back:
                if (MyApplication.getInstance().getCurrentUser() != null) {
                    if (MyApplication.getInstance().getCurrentUser().getUserInfor().getRole() == 1) {
                        startActivity(new Intent(this, HomeStoreActivity.class));
                    }
                }else {
                    startActivity(new Intent(this, MainActivity.class));
                }
                finish();
                break;
            case R.id.ulbtn_act_message_letter:
                type = 2;
                Page = 0;
                getMessage(Page);
                choseBtn(ulbtnActMessageLetter);
                break;
            case R.id.ulbtn_act_message_system:
                type = 1;
                Page = 0;
                getMessage(Page);
                choseBtn(ulbtnActMessageSystem);
                break;
        }
    }

    /**
     * 选择顶部按钮
     *
     * @param btn
     */
    public void choseBtn(UnderLineBtn btn) {
        if (btn == ulbtnActMessageLetter) {
            if (!ulbtnActMessageLetter.isChecked()) {
                ulbtnActMessageLetter.setChecked(true);
                ulbtnActMessageSystem.setChecked(false);
            }
        } else {
            if (!ulbtnActMessageSystem.isChecked()) {
                ulbtnActMessageSystem.setChecked(true);
                ulbtnActMessageLetter.setChecked(false);
            }
        }
    }

    public void getMessage(final int page) {
        Observable<BaseEntity<List<MessageInfo>>> observable = RetrofitFactory.getInstance().getMessage(new MessageBody(page, PAGE_SIZE, type, MyApplication.getInstance().getCurrentUser().getUserToken()));
        observable.compose(RxSchedulers.<BaseEntity<List<MessageInfo>>>compose())
                .subscribe(new BaseObserver<List<MessageInfo>>(this) {
                    @Override
                    protected void onHandleSuccess(List<MessageInfo> messageInfos) {
                        mCurrentCounter = messageInfos.size();

                        swipActMessage.setRefreshing(false);
                        adapter.setEnableLoadMore(true);

                        if (mCurrentCounter < PAGE_SIZE) {
                            //如果没有数据
                            if (page == 0 && mCurrentCounter == 0) {

                            }
                            if (page > 0) {
                                adapter.addData(messageInfos);
                            } else {
                                adapter.setNewData(messageInfos);
                            }
                            adapter.loadMoreEnd(false);
                        } else {
                            if (messageInfos != null) {
                                Page++;
                                if (page > 0) {
                                    adapter.addData(messageInfos);
                                } else {
                                    adapter.setNewData(messageInfos);
                                }
                            }
                            adapter.loadMoreComplete();
                        }
                        if (swipActMessage != null) {
                            swipActMessage.setEnabled(true);
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        adapter.loadMoreFail();
                        ToastUtils.showToast(MessageActivity.this, "加载失败" + msg);
                    }
                });
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        //刷新当前页为0页
        Page = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getMessage(Page);

            }
        }, 1000);
    }

    @Override
    public void onLoadMoreRequested() {
        swipActMessage.setEnabled(false);
        if (adapter.getData().size() < PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        } else {
            getMessage(Page);
        }

    }
}

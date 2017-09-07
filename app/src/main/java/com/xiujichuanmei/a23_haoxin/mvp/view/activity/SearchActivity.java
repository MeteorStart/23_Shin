package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.SearchBody;
import com.project.lx.baseproject.bean.responses.SearchHotInfo;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.SearchHistoryAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.SearchHotAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;
import com.xiujichuanmei.a23_haoxin.utils.HistoryUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.tv_edit_search)
    TextView tvEditSearch;
    @BindView(R.id.recy_act_search_hot)
    RecyclerView recyActSearchHot;
    @BindView(R.id.lay_act_search_topic_history)
    LinearLayout layActSearchTopicHistory;
    @BindView(R.id.tv_act_search_topic_hot)
    TextView tvActSearchTopicHot;
    @BindView(R.id.recy_act_search_history)
    RecyclerView recyActSearchHistory;
    @BindView(R.id.lay_act_search_topic_hot)
    LinearLayout layActSearchTopicHot;

    SearchHotAdapter adapter;
    SearchHistoryAdapter historyAdapter;

    Set<String> history;

    View footView;
    TextView clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_search);
    }

    @Override
    public void initView() {
        recyActSearchHot.setLayoutManager(new GridLayoutManager(this, 4));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyActSearchHistory.setLayoutManager(layoutManager);
        recyActSearchHistory.addItemDecoration(new SimpleDicider());
        footView = LayoutInflater.from(this).inflate(R.layout.foot_clear, null);
        clear = (TextView) footView.findViewById(R.id.tv_foot_clear);
    }

    @Override
    public void initData() {
        showProcessDialog("", "加载中。。。", true);
        Observable<BaseEntity<SearchHotInfo>> observable = RetrofitFactory.getInstance().sySearchPage(new SearchBody(MyApplication.getInstance().getCurrentUser().getUserToken()));
        observable.compose(RxSchedulers.<BaseEntity<SearchHotInfo>>compose())
                .subscribe(new BaseObserver<SearchHotInfo>(this) {
                    @Override
                    protected void onHandleSuccess(SearchHotInfo searchHotInfo) {
                        dismissProcessDialog();
                        adapter = new SearchHotAdapter(R.layout.item_search_hot, searchHotInfo.getHotSearchs());
                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                SearchHotInfo.HotSearchsBean item = (SearchHotInfo.HotSearchsBean) adapter.getItem(position);
                                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                                intent.putExtra("SearchContent", item.getContent());
                                startActivity(intent);
                            }
                        });
                        recyActSearchHot.setAdapter(adapter);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                    }
                });

        history = SharedPreferencesUtils.getArray(this);

        if (history.size() > 0) {
            List<String> list = new ArrayList<>(history);
            historyAdapter = new SearchHistoryAdapter(R.layout.item_search_history, list);
            historyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.imv_act_search_close:
                            String item = (String) adapter.getItem(position);
                            adapter.remove(position);
                            history.remove(item);
                            SharedPreferencesUtils.putArray(SearchActivity.this, history);
                            break;
                    }
                }
            });
            historyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String item = (String) adapter.getItem(position);
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("SearchContent", item);
                    startActivity(intent);
                }
            });
            historyAdapter.addFooterView(footView);
        } else {
            history = new HashSet<>();
            List<String> list = new ArrayList<>(history);
            historyAdapter = new SearchHistoryAdapter(R.layout.item_search_history, list);
//            historyAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty_act_shop_details, null));
        }
        recyActSearchHistory.setAdapter(historyAdapter);
    }

    @Override
    public void initListener() {
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(editSearch.getText())) {
                        Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    } else {
                        HistoryUtils.setHistory(SearchActivity.this, editSearch.getText().toString());
                        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                        intent.putExtra("SearchContent", editSearch.getText().toString());
                        startActivity(intent);

//                        //第一：保存搜索内容到搜索记录中
//                        SearchUtils.getInstance().saveSearch(search_edit.getText().toString());
//                        //第二：结束当前界面，关闭软键盘
//                        KeyBoardUtils.hideSoftInput(search_edit);
//                        //第三：携带参数跳转到搜索结果界面，把值给输入框
//                        Intent intent = new Intent();
//                        intent.putExtra("searchMsg", search_edit.getText().toString().trim());
//                        intent.setClass(SearchActivity.this, SearchResultActivity.class);
//                        startActivity(intent);
//                        finish();//你这里finish 调了把
                    }
                    return true;
                }
                return false;
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List newHistory = new ArrayList();
                historyAdapter.setNewData(newHistory);

                history = SharedPreferencesUtils.getArray(SearchActivity.this);
                history.clear();
                SharedPreferencesUtils.putArray(SearchActivity.this, history);
            }
        });
    }

    @OnClick(R.id.tv_edit_search)
    public void onViewClicked() {
//        HistoryUtils.setHistory(getApplicationContext(), editSearch.getText().toString());
        finish();
    }
}

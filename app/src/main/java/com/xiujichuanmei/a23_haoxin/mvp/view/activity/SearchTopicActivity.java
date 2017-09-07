package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.lx.baseproject.base.BaseActivity;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.contract.ISearchTopicContract;
import com.xiujichuanmei.a23_haoxin.mvp.presenter.ActSearchTopicPreImpl;
import com.xiujichuanmei.a23_haoxin.utils.HistoryUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @name: SearchTopicActivity
 * @description: 选择话题
 * @version: 1.0
 * @date: 2017/6/19 9:55
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class SearchTopicActivity extends BaseActivity implements ISearchTopicContract.ISearchTopicView {

    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.tv_edit_search)
    TextView tvEditSearch;
    @BindView(R.id.recy_act_search_topic_history)
    RecyclerView recyActSearchTopicHistory;
    @BindView(R.id.lay_act_search_topic_history)
    LinearLayout layActSearchTopicHistory;
    @BindView(R.id.recy_act_search_topic_hot)
    RecyclerView recyActSearchTopicHot;
    @BindView(R.id.lay_act_search_topic_hot)
    LinearLayout layActSearchTopicHot;
    @BindView(R.id.tv_act_search_topic_hot)
    TextView tvActSearchTopicHot;

    ISearchTopicContract.ISearchTopicPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_search_topic);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        presenter = new ActSearchTopicPreImpl(this, this);
        //进来调用一次不带参数的搜索，加载最近和全部的话题
        presenter.getSearchDatas();
    }

    @Override
    public void initListener() {

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(editSearch.getText())) {
                        Toast.makeText(SearchTopicActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    } else {
                        presenter.getSearchDatas();
                        HistoryUtils.setHistory(SearchTopicActivity.this, editSearch.getText().toString());

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

//        editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() > 0) {
//                    if (tvEditSearch.getVisibility() == View.GONE) {
//                        tvEditSearch.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    if (tvEditSearch.getVisibility() == View.VISIBLE) {
//                        tvEditSearch.setVisibility(View.GONE);
//                    }
//                }
//            }
//        });
    }

    @OnClick(R.id.tv_edit_search)
    public void onViewClicked() {
       finish();
//        HistoryUtils.setHistory(getApplicationContext(), editSearch.getText().toString());
    }

    @Override
    public void setPresenter(ISearchTopicContract.ISearchTopicPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingDialog(String title, String msg, boolean flag) {
        showProcessDialog(title, msg, flag);
    }

    @Override
    public void canelLoadingDialog() {
        dismissProcessDialog();
    }

    @Override
    public void jumpActivity(Intent intent) {
        if (intent != null) {
            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    public EditText getSearchEdit() {
        return editSearch;
    }

    @Override
    public TextView getTextEdit() {
        return tvEditSearch;
    }

    @Override
    public LinearLayout getActSearchTopicHistoryLay() {
        return layActSearchTopicHistory;
    }

    @Override
    public RecyclerView getSearchTopicHistoryRecy() {
        return recyActSearchTopicHistory;
    }

    @Override
    public TextView getTextSearchTopicHot() {
        return tvActSearchTopicHot;
    }

    @Override
    public LinearLayout getActSearchTopicHotLay() {
        return layActSearchTopicHot;
    }

    @Override
    public RecyclerView getyActSearchTopicHotLRecy() {
        return recyActSearchTopicHot;
    }
}

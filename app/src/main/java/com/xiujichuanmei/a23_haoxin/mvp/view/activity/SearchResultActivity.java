package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.SearchResultBody;
import com.project.lx.baseproject.bean.responses.SearchResultInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.widget.CustomLinearLayoutManager;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.SearchResultLetterAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.SearchResultShopAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.SearchResultUserAdapter;
import com.xiujichuanmei.a23_haoxin.utils.HistoryUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.tv_edit_search)
    TextView tvEditSearch;
    @BindView(R.id.rel_act_search_result_user)
    RelativeLayout relActSearchResultUser;
    @BindView(R.id.recy_act_search_user)
    RecyclerView recyActSearchUser;
    @BindView(R.id.lay_act_search_result_user)
    LinearLayout layActSearchResultUser;
    @BindView(R.id.rel_act_search_result_letter)
    RelativeLayout relActSearchResultLetter;
    @BindView(R.id.recy_act_search_letter)
    RecyclerView recyActSearchLetter;
    @BindView(R.id.lay_act_search_result_letter)
    LinearLayout layActSearchResultLetter;
    @BindView(R.id.rel_act_search_result_shop)
    RelativeLayout relActSearchResultShop;
    @BindView(R.id.recy_act_search_shop)
    RecyclerView recyActSearchShop;
    @BindView(R.id.lay_act_search_result_shop)
    LinearLayout layActSearchResultShop;

    SearchResultUserAdapter userAdapter;
    SearchResultLetterAdapter letterAdapter;
    SearchResultShopAdapter shopAdapter;

    SearchResultBody body;

    int type;
    String content;

    List<SearchResultInfo.UsersBean> usersBeanList;
    List<SearchResultInfo.LettersBean> lettersBeanList;
    List<SearchResultInfo.ShopsBean> shopsBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_search_result);
    }

    @Override
    public void initView() {
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(editSearch.getText())) {
                        Toast.makeText(SearchResultActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    } else {
                        content = editSearch.getText().toString();
                        if (content.equals(body.getCondition()) && type == body.getTypeCode()) {

                        } else {
                            body.setCondition(content);
                            body.setTypeCode(type);
                            getData(body);
                            HistoryUtils.setHistory(SearchResultActivity.this, editSearch.getText().toString());
                        }

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
        editSearch.setText(getIntent().getStringExtra("SearchContent"));
    }

    @Override
    public void initData() {

        type = 0;
        content = getIntent().getStringExtra("SearchContent");

        body = new SearchResultBody(content, 0, 0, type, MyApplication.getInstance().getCurrentUser().getUserToken());
        getData(body);
    }

    @Override
    public void initListener() {

    }

    public void initUserAdapter(List<SearchResultInfo.UsersBean> usersBeanList) {
        CustomLinearLayoutManager linearLayoutManager;
        linearLayoutManager = new CustomLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        recyActSearchUser.setLayoutManager(linearLayoutManager);

        if (usersBeanList != null) {
            userAdapter = new SearchResultUserAdapter(R.layout.item_search_result, usersBeanList);
            userAdapter.setSupperText(editSearch.getText().toString());
            userAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    SearchResultInfo.UsersBean item = (SearchResultInfo.UsersBean) adapter.getItem(position);
                    Intent intent = new Intent(SearchResultActivity.this, UserInforActivity.class);
                    intent.putExtra("HeadImg", item.getHeadImg());
                    intent.putExtra("UserId", item.getUserId());
                    intent.putExtra("NickName", item.getNickName());
                    intent.putExtra("type", type);
                    startActivity(intent);
                }
            });
            recyActSearchUser.setAdapter(userAdapter);
        } else {
            layActSearchResultUser.setVisibility(View.GONE);
        }

    }

    public void initLetterAdapter(List<SearchResultInfo.LettersBean> lettersBeanList) {
        CustomLinearLayoutManager linearLayoutManager;
        linearLayoutManager = new CustomLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        recyActSearchLetter.setLayoutManager(linearLayoutManager);

        if (lettersBeanList != null) {
            letterAdapter = new SearchResultLetterAdapter(R.layout.item_search_result, lettersBeanList);
            letterAdapter.setSupperText(editSearch.getText().toString());
            letterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    SearchResultInfo.LettersBean item = (SearchResultInfo.LettersBean) adapter.getItem(position);
                    Intent intent = new Intent(SearchResultActivity.this, LetterDetailActivity.class);
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
//                finish();
                }
            });
            recyActSearchLetter.setAdapter(letterAdapter);
        } else {
            layActSearchResultLetter.setVisibility(View.GONE);
        }
    }

    public void initShopAdapter(List<SearchResultInfo.ShopsBean> shopsBeanList) {
        CustomLinearLayoutManager linearLayoutManager;
        linearLayoutManager = new CustomLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        recyActSearchShop.setLayoutManager(linearLayoutManager);

        if (shopsBeanList != null) {
            shopAdapter = new SearchResultShopAdapter(R.layout.item_search_result, shopsBeanList);
            shopAdapter.setSupperText(editSearch.getText().toString());
            shopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    SearchResultInfo.ShopsBean item = (SearchResultInfo.ShopsBean) adapter.getItem(position);
                    Intent intent = new Intent(SearchResultActivity.this, ShopDetailsActivity.class);
                    intent.putExtra(Constants.HOME_TYPE, 1);
                    intent.putExtra(Constants.SHOP_ID, item.getShopId());
                    intent.putExtra(Constants.SHOP_NAME, item.getShopName());
                    startActivity(intent);
                }
            });
            recyActSearchShop.setAdapter(shopAdapter);
        } else {
            layActSearchResultShop.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_edit_search, R.id.rel_act_search_result_user, R.id.rel_act_search_result_letter, R.id.rel_act_search_result_shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_act_search_result_user:
                body.setTypeCode(1);
                getData(body);
                break;
            case R.id.rel_act_search_result_letter:
                body.setTypeCode(2);
                getData(body);
                break;
            case R.id.rel_act_search_result_shop:
                body.setTypeCode(3);
                getData(body);
                break;
            case R.id.tv_edit_search:
                finish();
                break;
        }
    }

    public void getData(final SearchResultBody body) {
        showProcessDialog("", "加载中。。。", true);
        Observable<BaseEntity<SearchResultInfo>> observable = RetrofitFactory.getInstance().sySearch(body);
        observable.compose(RxSchedulers.<BaseEntity<SearchResultInfo>>compose())
                .subscribe(new BaseObserver<SearchResultInfo>(this) {
                    @Override
                    protected void onHandleSuccess(SearchResultInfo searchResultInfo) {
                        dismissProcessDialog();
                        switch (body.getTypeCode()) {
                            case 0:
                                layActSearchResultUser.setVisibility(View.VISIBLE);
                                layActSearchResultShop.setVisibility(View.VISIBLE);
                                layActSearchResultLetter.setVisibility(View.VISIBLE);

                                usersBeanList = searchResultInfo.getUsers();
                                lettersBeanList = searchResultInfo.getLetters();
                                shopsBeanList = searchResultInfo.getShops();

                                if (usersBeanList != null && usersBeanList.size() > 0) {
                                    if (userAdapter != null) {
                                        userAdapter.setNewData(usersBeanList);
                                    } else {
                                        initUserAdapter(usersBeanList);
                                    }
                                } else {
                                    layActSearchResultUser.setVisibility(View.GONE);
                                }

                                if (lettersBeanList != null && lettersBeanList.size() > 0) {
                                    if (letterAdapter != null) {
                                        letterAdapter.setNewData(lettersBeanList);
                                    } else {
                                        initLetterAdapter(lettersBeanList);
                                    }
                                } else {
                                    layActSearchResultLetter.setVisibility(View.GONE);
                                }

                                if (shopsBeanList != null && shopsBeanList.size() > 0) {
                                    if (shopAdapter != null) {
                                        shopAdapter.setNewData(shopsBeanList);
                                    } else {
                                        initShopAdapter(shopsBeanList);
                                    }
                                } else {
                                    layActSearchResultShop.setVisibility(View.GONE);
                                }

                                break;
                            case 1:
                                usersBeanList = searchResultInfo.getUsers();
                                userAdapter.setNewData(usersBeanList);
                                layActSearchResultLetter.setVisibility(View.GONE);
                                layActSearchResultShop.setVisibility(View.GONE);
                                break;
                            case 2:
                                lettersBeanList = searchResultInfo.getLetters();
                                letterAdapter.setNewData(lettersBeanList);
                                layActSearchResultUser.setVisibility(View.GONE);
                                layActSearchResultShop.setVisibility(View.GONE);
                                break;
                            case 3:
                                shopsBeanList = searchResultInfo.getShops();
                                shopAdapter.setNewData(shopsBeanList);
                                layActSearchResultLetter.setVisibility(View.GONE);
                                layActSearchResultUser.setVisibility(View.GONE);
                                break;
                        }

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (body.getTypeCode() != 0) {
            body.setTypeCode(type);
            getData(body);
        } else {
            finish();
        }
    }
}

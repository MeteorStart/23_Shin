package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.ShowUserInformationBody;
import com.project.lx.baseproject.bean.request.TimeBody;
import com.project.lx.baseproject.bean.responses.ShowUserInformationInfo;
import com.project.lx.baseproject.bean.responses.TimeInfo;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.widget.CustomLinearLayoutManager;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.UserInfoFindAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.UserInfoWaitAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowScanPopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowTimePopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: UserInfoListActivity
 * @description: 用户详情页  信件信息列表
 * @version: 1.0
 * @date: 2017/7/25 13:59
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class UserInfoListActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.recy_act_user_list)
    RecyclerView recyActUserList;

    String type;
    String u_id;

    private List<ShowUserInformationInfo.LetterBean> letter;
    private List<ShowUserInformationInfo.DemandBean> demand;

    UserInfoFindAdapter findAdapter;
    UserInfoWaitAdapter waitAdapter;

    ShowTimePopupWindow pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        u_id = getIntent().getStringExtra("u_id");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_user_info_list);
    }

    @Override
    public void initView() {

        imvActTopRight.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(type)) {
            switch (type) {
                case "1":
                    textActTop.setText("他找的信");
                    break;
                case "2":
                    textActTop.setText("他等的信");
                    break;
                default:
                    textActTop.setText("他找的信");
                    break;
            }
        }
    }

    @Override
    public void initData() {
        showProcessDialog("", "加载中。。。", true);
        ShowUserInformationBody body = new ShowUserInformationBody(0, 10, Integer.valueOf(type), u_id, MyApplication.getInstance().getCurrentUser().getUserToken());
        Observable<BaseEntity<ShowUserInformationInfo>> observable = RetrofitFactory.getInstance().showUserInformation(body);
        observable.compose(RxSchedulers.<BaseEntity<ShowUserInformationInfo>>compose())
                .subscribe(new BaseObserver<ShowUserInformationInfo>(this) {
                    @Override
                    protected void onHandleSuccess(ShowUserInformationInfo showUserInformationInfo) {
                        dismissProcessDialog();
                       if (showUserInformationInfo != null){
                           letter = showUserInformationInfo.getLetter();
                           demand = showUserInformationInfo.getDemand();
                       }

                       if (letter != null && letter.size() > 0){
                           initFindAdapter(letter);
                       }

                       if (demand != null && demand.size() > 0){
                           initWaitAdapter(demand);
                       }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                    }
                });
    }

    public void initFindAdapter(final List<ShowUserInformationInfo.LetterBean> letter) {

        findAdapter = new UserInfoFindAdapter(R.layout.item_userinfo_find, letter);
        findAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShowUserInformationInfo.LetterBean item = (ShowUserInformationInfo.LetterBean) adapter.getItem(position);
                Intent intent = new Intent(UserInfoListActivity.this, LetterDetailActivity.class);
                intent.putExtra("topic", item.getTopicName());
                intent.putExtra("title", item.getLetterTitle());
                intent.putExtra("letterNumber", item.getLetterNumber());
                intent.putExtra("letterId", item.getLetterId());
                intent.putExtra("userName", getIntent().getStringExtra("username"));
                intent.putExtra("userAvatar",getIntent().getStringExtra("userAvatar") );
                intent.putExtra("ownerId", item.getOwnerId());
                intent.putExtra("letterState", item.getLetterState());
                intent.putExtra("shopName",item.getShopName());
                startActivity(intent);
//                finish();

            }
        });

        findAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final ShowUserInformationInfo.LetterBean item = (ShowUserInformationInfo.LetterBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.text_frag_user_find_sub_avatar_name:

                        TimeBody timeBody = new TimeBody(1, item.getLetterId());
                        Observable<BaseEntity<TimeInfo>> observable = RetrofitFactory.getInstance().showCountdown(timeBody);
                        observable.compose(RxSchedulers.<BaseEntity<TimeInfo>>compose())
                                .subscribe(new BaseObserver<TimeInfo>(UserInfoListActivity.this) {
                                    @Override
                                    protected void onHandleSuccess(TimeInfo timeInfo) {
                                        pop = new ShowTimePopupWindow(UserInfoListActivity.this, timeInfo, item.getOwenerHeadImg(), item.getOwnerName(), item.getLetterNumber());
                                        pop.showAtLocation(recyActUserList.getRootView(), Gravity.CENTER, 0, 0);
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
                        ShowScanPopupWindow popupWindow = new ShowScanPopupWindow(UserInfoListActivity.this, item);
                        popupWindow.showAtLocation(recyActUserList.getRootView(), Gravity.CENTER, 0, 0);
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
        CustomLinearLayoutManager linearLayoutManager;
        linearLayoutManager = new CustomLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyActUserList.setLayoutManager(linearLayoutManager);
        recyActUserList.setAdapter(findAdapter);
        recyActUserList.addItemDecoration(new SimpleDicider());
    }

    public void initWaitAdapter(List<ShowUserInformationInfo.DemandBean> demand) {
        waitAdapter = new UserInfoWaitAdapter(R.layout.item_userinfo_wait, demand);
        waitAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShowUserInformationInfo.DemandBean item = (ShowUserInformationInfo.DemandBean) adapter.getItem(position);
                Intent intent = new Intent(UserInfoListActivity.this, DemandDetailsActivity.class);
                intent.putExtra("shopName", item.getShopName());
                intent.putExtra("shopId", item.getShopId());
                intent.putExtra("ownerId", u_id);
                intent.putExtra("userName", getIntent().getStringExtra("username"));
                intent.putExtra("userAvatar",getIntent().getStringExtra("userAvatar") );
                intent.putExtra("demandContent", item.getDemandContent());
                intent.putExtra("shopCoordinate", item.getShopCoordinate());
                intent.putExtra("demandId", item.getDemandId());
                intent.putExtra("demandState",item.getDemandState());
                intent.putExtra("createTime",item.getCreateTime());
                startActivity(intent);
            }
        });
        waitAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final ShowUserInformationInfo.DemandBean item = (ShowUserInformationInfo.DemandBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.text_frag_user_find_sub_avatar_name:
                        TimeBody timeBody = new TimeBody(2, item.getDemandId());
                        Observable<BaseEntity<TimeInfo>> observable = RetrofitFactory.getInstance().showCountdown(timeBody);
                        observable.compose(RxSchedulers.<BaseEntity<TimeInfo>>compose())
                                .subscribe(new BaseObserver<TimeInfo>(UserInfoListActivity.this) {
                                    @Override
                                    protected void onHandleSuccess(TimeInfo timeInfo) {
                                        pop = new ShowTimePopupWindow(UserInfoListActivity.this, timeInfo, item.getOwenerHeadImg(), item.getOwnerName(), item.getDemandId());
                                        pop.showAtLocation(recyActUserList.getRootView(), Gravity.CENTER, 0, 0);
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
                        Intent intent = new Intent(UserInfoListActivity.this, ReleaseLetterActivity.class);
                        intent.putExtra("shopName", item.getShopName());
                        intent.putExtra("shopId", item.getShopId());
                        intent.putExtra("ownerId", u_id);
                        intent.putExtra("ownerName", getIntent().getStringExtra("username"));
                        intent.putExtra("owenerHeadImg", getIntent().getStringExtra("userAvatar"));
                        intent.putExtra("demandContent", item.getDemandContent());
                        intent.putExtra("shopCoordinate", item.getShopCoordinate());
                        intent.putExtra("demandId", item.getDemandId());
                        startActivity(intent);
                        break;
                }
            }
        });
        CustomLinearLayoutManager linearLayoutManager;
        linearLayoutManager = new CustomLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyActUserList.setLayoutManager(linearLayoutManager);
        recyActUserList.setAdapter(waitAdapter);
        recyActUserList.addItemDecoration(new SimpleDicider());
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.imv_act_top_back)
    public void onViewClicked() {
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgcolor;

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.getWindow().setAttributes(lp);

    }
}

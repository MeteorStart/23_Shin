package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.AttentionOrcancelAttentionBody;
import com.project.lx.baseproject.bean.request.ShowUserInfoBody;
import com.project.lx.baseproject.bean.request.ShowUserInformationBody;
import com.project.lx.baseproject.bean.request.TimeBody;
import com.project.lx.baseproject.bean.responses.ShowUserInformationInfo;
import com.project.lx.baseproject.bean.responses.TimeInfo;
import com.project.lx.baseproject.bean.responses.UserInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.ToastUtils;
import com.project.lx.baseproject.widget.CustomLinearLayoutManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.adapter.UserInfoFindAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.UserInfoWaitAdapter;
import com.xiujichuanmei.a23_haoxin.adapter.divider.SimpleDicider;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.AttentionButton;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.PopActUserInfo;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.SharePopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowScanPopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowTimePopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @name: UserInforActivity
 * @description: 用户详情页
 * @version: 1.0
 * @date: 2017/6/8 16:59
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class UserInforActivity extends BaseActivity {

    @BindView(R.id.btn_act_user_info_attention)
    AttentionButton btnActUserInfoAttention;
    @BindView(R.id.imv_act_user_info_blur_avatar)
    ImageView imvActUserInfoBlurAvatar;
    @BindView(R.id.imv_act_user_info_avatar)
    ImageView imvActUserInfoAvatar;
    @BindView(R.id.text_act_user_info_name)
    TextView textActUserInfoName;
    @BindView(R.id.btn_act_user_info_draw_letter)
    Button btnActUserInfoDrawLetter;
    @BindView(R.id.recy_act_search_topic_history)
    RecyclerView recyActSearchTopicHistory;
    @BindView(R.id.lay_act_search_topic_history)
    LinearLayout layActSearchTopicHistory;
    @BindView(R.id.tv_act_search_topic_hot)
    TextView tvActSearchTopicHot;
    @BindView(R.id.recy_act_search_topic_hot)
    RecyclerView recyActSearchTopicHot;
    @BindView(R.id.lay_act_search_topic_hot)
    LinearLayout layActSearchTopicHot;
    @BindView(R.id.imv_act_user_info_back)
    ImageView imvActUserInfoBack;
    @BindView(R.id.imv_act_user_info_more)
    ImageView imvActUserInfoMore;

    Intent mainIntent;

    UserInfoFindAdapter findAdapter;
    UserInfoWaitAdapter waitAdapter;

    List<ShowUserInformationInfo.LetterBean> letter;
    List<ShowUserInformationInfo.DemandBean> demand;

    ShowTimePopupWindow pop;
    SharePopupWindow mSharePopupWindow;
    @BindView(R.id.tv_act_user_info_more1)
    TextView tvActUserInfoMore1;
    @BindView(R.id.tv_act_user_info_more2)
    TextView tvActUserInfoMore2;

    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainIntent = getIntent();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_user_infor);
    }

    @Override
    public void initView() {
        setSystemBarTint(R.color.transparent);
        setStatusBarLightMode(this);

        Glide.with(this).
                load(mainIntent.getStringExtra("HeadImg"))
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .crossFade(1000)
                .bitmapTransform(new GlideCircleTransform(this, 1, Color.WHITE))
                .into(imvActUserInfoAvatar);

        Glide.with(this).
                load(mainIntent.getStringExtra("HeadImg"))
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new BlurTransformation(this, 2, 2))
                .crossFade(1000)
                .into(imvActUserInfoBlurAvatar);

        if (mainIntent.getStringExtra("NickName") != null) {
            nickname = mainIntent.getStringExtra("NickName");
            textActUserInfoName.setText(nickname);
        }
    }

    @Override
    public void initData() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(UserInforActivity.this).setShareConfig(config);

        showProcessDialog("", "加载中。。。", true);
        ShowUserInformationBody body = new ShowUserInformationBody(0, 0, 0, mainIntent.getStringExtra("UserId"), MyApplication.getInstance().getCurrentUser().getUserToken());
        Observable<BaseEntity<ShowUserInformationInfo>> observable = RetrofitFactory.getInstance().showUserInformation(body);
        observable.compose(RxSchedulers.<BaseEntity<ShowUserInformationInfo>>compose())
                .subscribe(new BaseObserver<ShowUserInformationInfo>(this) {
                    @Override
                    protected void onHandleSuccess(ShowUserInformationInfo showUserInformationInfo) {
                        letter = showUserInformationInfo.getLetter();
                        btnActUserInfoAttention.setAtten(showUserInformationInfo.isIsFans());
                        dismissProcessDialog();
                        initFindAdapter(letter);
                        demand = showUserInformationInfo.getDemand();
                        initWaitAdapter(demand);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dismissProcessDialog();
                    }
                });
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_act_user_info_more1, R.id.tv_act_user_info_more2, R.id.imv_act_user_info_back, R.id.imv_act_user_info_more, R.id.btn_act_user_info_attention, R.id.imv_act_user_info_blur_avatar, R.id.btn_act_user_info_draw_letter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_user_info_blur_avatar:
                Intent intent2 = new Intent(this, PhotoViewActivity.class);
//                intent.putExtra("images", (ArrayList<String>) datas);//非必须
//                intent.putExtra("position", position);
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                intent2.putExtra("locationX", location[0]);//必须
                intent2.putExtra("locationY", location[1]);//必须

                intent2.putExtra("url", mainIntent.getStringExtra("HeadImg"));//必须

                intent2.putExtra("width", view.getWidth());//必须
                intent2.putExtra("height", view.getHeight());//必须
                startActivity(intent2);
                overridePendingTransition(0, 0);
                break;
            case R.id.btn_act_user_info_draw_letter:
                Intent intent = new Intent(UserInforActivity.this, ReleaseLetterActivity.class);
                intent.putExtra("ownerId", mainIntent.getStringExtra("UserId"));
                intent.putExtra("ownerName", mainIntent.getStringExtra("NickName"));
                intent.putExtra("owenerHeadImg", mainIntent.getStringExtra("HeadImg"));
                startActivity(intent);
                break;
            case R.id.btn_act_user_info_attention:
                btnActUserInfoAttention.setAtten(!btnActUserInfoAttention.isAtten());
                AttentionOrcancelAttentionBody bodt = new AttentionOrcancelAttentionBody(mainIntent.getStringExtra("UserId"), MyApplication.getInstance().getCurrentUser().getUserToken());
                Observable<BaseEntity> observable = RetrofitFactory.getInstance().attentionOrcancelAttention(bodt);
                observable.compose(RxSchedulers.<BaseEntity>compose())
                        .subscribe(new BaseObserver(this) {
                            @Override
                            public void onNext(Object value) {
                                if (value instanceof BaseEntity) {
                                    if (((BaseEntity) value).isSuccess()) {
                                        onHandleSuccess(value);
                                    } else {
                                        onHandleError(((BaseEntity) value).getMsg());
                                    }
                                }
                            }

                            @Override
                            protected void onHandleSuccess(Object o) {

                                Observable<BaseEntity<UserInfo>> observable = RetrofitFactory.getInstance().showUserInfo(new ShowUserInfoBody(MyApplication.getInstance().getCurrentUser().getUserToken()));
                                observable.compose(RxSchedulers.<BaseEntity<UserInfo>>compose())
                                        .subscribe(new BaseObserver<UserInfo>(UserInforActivity.this) {
                                            @Override
                                            protected void onHandleSuccess(UserInfo userInfo) {
                                                if (btnActUserInfoAttention.isAtten()) {
                                                    ToastUtils.showToast(UserInforActivity.this, "添加关注成功");
                                                } else {
                                                    ToastUtils.showToast(UserInforActivity.this, "取消关注成功");
                                                }
                                                MyApplication.getInstance().setCurrentUser(userInfo);
                                            }

                                            @Override
                                            protected void onHandleError(String msg) {
                                                LogUtils.print(msg);
                                            }
                                        });
                            }

                            @Override
                            protected void onHandleError(String msg) {

                            }
                        });
                break;
            case R.id.imv_act_user_info_back:
                finish();
                break;
            case R.id.imv_act_user_info_more:
                final PopActUserInfo popActUserInfo = new PopActUserInfo(this);
                popActUserInfo.showAsDropDown(imvActUserInfoMore);
                popActUserInfo.setOnItemClickListener(new PopActUserInfo.OnItemClickListener() {
                    @Override
                    public void setOnItemClick(View v) {
                        switch (v.getId()) {
                            case R.id.tv_pop_user_info_share:
                                showMapChoosePop(UserInforActivity.this);
                                popActUserInfo.dismiss();
                                break;
                            case R.id.tv_pop_user_info_report:
                                Intent intent = new Intent(UserInforActivity.this, ReportActivity.class);
                                intent.putExtra("ownerId", mainIntent.getStringExtra("UserId"));
                                intent.putExtra("ownerName", mainIntent.getStringExtra("NickName"));
                                intent.putExtra("owenerHeadImg", mainIntent.getStringExtra("HeadImg"));
                                startActivity(intent);
                                popActUserInfo.dismiss();
                                break;
                        }
                    }
                });
                break;
            case R.id.tv_act_user_info_more1:
                Intent intent1 = new Intent(this, UserInfoListActivity.class);
                intent1.putExtra("type", "1");
                intent1.putExtra("username", mainIntent.getStringExtra("NickName"));
                intent1.putExtra("userAvatar", mainIntent.getStringExtra("HeadImg"));
                intent1.putExtra("u_id", mainIntent.getStringExtra("UserId"));
                startActivity(intent1);
                break;
            case R.id.tv_act_user_info_more2:
                intent2 = new Intent(this, UserInfoListActivity.class);
                intent2.putExtra("type", "2");
                intent2.putExtra("username", mainIntent.getStringExtra("NickName"));
                intent2.putExtra("userAvatar", mainIntent.getStringExtra("HeadImg"));
                intent2.putExtra("u_id", mainIntent.getStringExtra("UserId"));
                startActivity(intent2);
                break;
        }
    }

    /**
     * 地图弹窗的处理方法
     */
    private void showMapChoosePop(Context context) {
        mSharePopupWindow = new SharePopupWindow(context);
        darkenBackground(0.5f);
        mSharePopupWindow.showAtLocation(recyActSearchTopicHistory.getRootView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        mSharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

        /**
         * SHARE_MEDIA.QQ   QQ授权/分享到QQ好友
         * SHARE_MEDIA.QZONE  分享到QQ空间
         * SHARE_MEDIA.WEIXIN  微信授权
         * SHARE_MEDIA.WEIXIN_CIRCLE  分享到朋友圈
         * SHARE_MEDIA.SINA 微博授权/分享到微博
         */

        mSharePopupWindow.setOnItemClickListener(new SharePopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                switch (v.getId()) {
                    case R.id.imv_pop_choose_share_weixin:
                        shareUtils(SHARE_MEDIA.WEIXIN);
                        break;
                    case R.id.imv_pop_choose_share_pengyouquan:
                        shareUtils(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case R.id.imv_pop_choose_share_xinlang:
                        shareUtils(SHARE_MEDIA.SINA);
                        break;
                    case R.id.imv_pop_choose_share_qqkongjian:
                        shareUtils(SHARE_MEDIA.QZONE);
                        break;
                    case R.id.imv_pop_choose_share_qq:
                        shareUtils(SHARE_MEDIA.QQ);
                        break;
                    case R.id.imv_pop_choose_share_lianjie:
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(Constants.BASE_URL + "share/letters.html?id=" + mainIntent.getStringExtra("UserId"));
                        ToastUtils.showToast(UserInforActivity.this, "链接已经复制到剪切板");
                        break;
                    case R.id.imv_pop_choose_share_close:
                        mSharePopupWindow.dismiss();
                        break;
                }
                mSharePopupWindow.dismiss();
            }
        });

    }

    private void shareUtils(SHARE_MEDIA share_media) {
        UMImage qq_thumb = new UMImage(UserInforActivity.this, R.mipmap._logo);
        UMWeb qq_web = new UMWeb(Constants.SHARE_URL + mainIntent.getStringExtra("UserId"));
        qq_web.setThumb(qq_thumb);  //缩略图
        qq_web.setTitle(nickname + "的个人主页");//标题
        qq_web.setDescription("Ta找的信:" + findAdapter.getData().size() + "封  " + "Ta等的信：" + waitAdapter.getData().size() + "封");

        new ShareAction(UserInforActivity.this)
                .withMedia(qq_web)
                .setPlatform(share_media)
                .setCallback(shareListener)
                .share();
    }

    /**
     * @name:
     * @description: 分享回调
     * @date: 2017/7/4 15:25
     * @company:
     * @author: Meteor
     */
    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            LogUtils.print("开始分享回调");
            showProcessDialog("", "分享中。。。", false);
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            LogUtils.print("回调完成");
            dismissProcessDialog();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            LogUtils.print("回调错误");
            dismissProcessDialog();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            LogUtils.print("回调取消");
            dismissProcessDialog();
        }
    };

    public void initFindAdapter(final List<ShowUserInformationInfo.LetterBean> letter) {

        findAdapter = new UserInfoFindAdapter(R.layout.item_userinfo_find, letter);
        findAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShowUserInformationInfo.LetterBean item = (ShowUserInformationInfo.LetterBean) adapter.getItem(position);
                Intent intent = new Intent(UserInforActivity.this, LetterDetailActivity.class);
                intent.putExtra("topic", item.getTopicName());
                intent.putExtra("title", item.getLetterTitle());
                intent.putExtra("letterNumber", item.getLetterNumber());
                intent.putExtra("letterId", item.getLetterId());
                intent.putExtra("userName", mainIntent.getStringExtra("NickName"));
                intent.putExtra("userAvatar", mainIntent.getStringExtra("HeadImg"));
                intent.putExtra("ownerId", item.getOwnerId());
                intent.putExtra("letterState", item.getLetterState());
                intent.putExtra("shopName", item.getShopName());
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
                                .subscribe(new BaseObserver<TimeInfo>(UserInforActivity.this) {
                                    @Override
                                    protected void onHandleSuccess(TimeInfo timeInfo) {
                                        pop = new ShowTimePopupWindow(UserInforActivity.this, timeInfo, item.getOwenerHeadImg(), item.getOwnerName(), item.getLetterNumber());
                                        pop.showAtLocation(recyActSearchTopicHistory.getRootView(), Gravity.CENTER, 0, 0);
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
                        ShowScanPopupWindow popupWindow = new ShowScanPopupWindow(UserInforActivity.this, item);
                        popupWindow.showAtLocation(recyActSearchTopicHistory.getRootView(), Gravity.CENTER, 0, 0);
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
        linearLayoutManager.setScrollEnabled(false);
        recyActSearchTopicHistory.setLayoutManager(linearLayoutManager);
        recyActSearchTopicHistory.setAdapter(findAdapter);
        recyActSearchTopicHistory.addItemDecoration(new SimpleDicider());
    }

    public void initWaitAdapter(List<ShowUserInformationInfo.DemandBean> demand) {
        waitAdapter = new UserInfoWaitAdapter(R.layout.item_userinfo_wait, demand);
        waitAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShowUserInformationInfo.DemandBean item = (ShowUserInformationInfo.DemandBean) adapter.getItem(position);
                Intent intent = new Intent(UserInforActivity.this, DemandDetailsActivity.class);
                intent.putExtra("shopName", item.getShopName());
                intent.putExtra("shopId", item.getShopId());
                intent.putExtra("ownerId", mainIntent.getStringExtra("UserId"));
                intent.putExtra("ownerName", mainIntent.getStringExtra("NickName"));
                intent.putExtra("owenerHeadImg", mainIntent.getStringExtra("HeadImg"));
                intent.putExtra("demandContent", item.getDemandContent());
                intent.putExtra("shopCoordinate", item.getShopCoordinate());
                intent.putExtra("demandId", item.getDemandId());
                intent.putExtra("demandState", item.getDemandState());
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
                                .subscribe(new BaseObserver<TimeInfo>(UserInforActivity.this) {
                                    @Override
                                    protected void onHandleSuccess(TimeInfo timeInfo) {
                                        pop = new ShowTimePopupWindow(UserInforActivity.this, timeInfo, item.getOwenerHeadImg(), item.getOwnerName(), item.getDemandId());
                                        pop.showAtLocation(recyActSearchTopicHistory.getRootView(), Gravity.CENTER, 0, 0);
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
                        Intent intent = new Intent(UserInforActivity.this, ReleaseLetterActivity.class);
                        intent.putExtra("shopName", item.getShopName());
                        intent.putExtra("shopId", item.getShopId());
                        intent.putExtra("ownerId", mainIntent.getStringExtra("UserId"));
                        intent.putExtra("ownerName", mainIntent.getStringExtra("NickName"));
                        intent.putExtra("owenerHeadImg", mainIntent.getStringExtra("HeadImg"));
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
        linearLayoutManager.setScrollEnabled(false);
        recyActSearchTopicHot.setLayoutManager(linearLayoutManager);
        recyActSearchTopicHot.setAdapter(waitAdapter);
        recyActSearchTopicHot.addItemDecoration(new SimpleDicider());
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}

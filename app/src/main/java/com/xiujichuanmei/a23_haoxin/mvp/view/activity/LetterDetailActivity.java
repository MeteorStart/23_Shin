package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.base.BaseActivity;
import com.project.lx.baseproject.base.BaseEntity;
import com.project.lx.baseproject.base.BaseObserver;
import com.project.lx.baseproject.base.RetrofitFactory;
import com.project.lx.baseproject.bean.request.SubLetterBody;
import com.project.lx.baseproject.bean.responses.ShowShopInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.RxSchedulers;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.MapPopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.SharePopupWindow;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.ShowSubPopupWindow;
import com.xiujichuanmei.a23_haoxin.utils.LetterUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @name: 信件详情页
 * @description: 类描述
 * @version: 1.0
 * @date: 2017/6/27 14:39
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class LetterDetailActivity extends BaseActivity {

    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;
    @BindView(R.id.imv_act_demand_details_avatar)
    ImageView imvActDemandDetailsAvatar;
    @BindView(R.id.tv_act_demand_details_username)
    TextView tvActDemandDetailsUsername;
    @BindView(R.id.tv_act_letter_detail_title)
    TextView tvActLetterDetailTitle;
    @BindView(R.id.tv_act_letter_detail_topic)
    TextView tvActLetterDetailTopic;
    @BindView(R.id.tv_act_letter_detail_number)
    TextView tvActLetterDetailNumber;
    @BindView(R.id.btn_act_demand_details_send)
    Button btnActDemandDetailsSend;

    Intent intent;
    ShowShopInfo.LetterBean item;

    SharePopupWindow mSharePopupWindow;
    @BindView(R.id.tv_act_letter_detail_shope)
    TextView tvActLetterDetailShope;
    @BindView(R.id.lay_act_letter_details)
    LinearLayout layActLetterDetails;

    /**
     * 是否预约成功
     */
    boolean isOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_letter_detail);
    }

    @Override
    public void initView() {
        item = new ShowShopInfo.LetterBean();

        item.setOwenerHeadImg(intent.getStringExtra("userAvatar"));
        if (TextUtils.isEmpty(intent.getStringExtra("userName"))) {
            item.setOwnerName(MyApplication.getInstance().getCurrentUser().getUserInfor().getNickName());
        } else {
            item.setOwnerName(intent.getStringExtra("userName"));
        }
        item.setLetterId(intent.getStringExtra("letterId"));
        if (!TextUtils.isEmpty(intent.getStringExtra("topic")) && !intent.getStringExtra("topic").equals("null")) {
            item.setTopicName("#" + intent.getStringExtra("topic"));
        } else {
            item.setTopicName("该信件没有话题");
        }

        item.setLetterTitle(intent.getStringExtra("title"));
        item.setLetterNumber(intent.getStringExtra("letterNumber"));
        item.setOwnerId(intent.getStringExtra("ownerId"));
        item.setLetterState(intent.getIntExtra("letterState", 0));
        item.setShopName(intent.getStringExtra("shopName"));

        switch (item.getLetterState()) {
            case 0:
                btnActDemandDetailsSend.setText("预约");
                break;
            case 1:
            case 2:
            case 3:
                btnActDemandDetailsSend.setText("已预约");
                break;
        }

        if (MyApplication.getInstance().getCurrentUser().getUserInfor().getRole() == 1) {
            btnActDemandDetailsSend.setText("商家不能有预约哦~");
            btnActDemandDetailsSend.setEnabled(false);
//            btnActDemandDetailsSend.setVisibility(View.GONE);
        } else if (MyApplication.getInstance().getCurrentUser().getUserInfor().getRole() == 0) {
            if (item.getOwnerId() == null || item.getOwnerId().equals(MyApplication.getInstance().getCurrentUser().getUserInfor().getUserId())) {
//                btnActDemandDetailsSend.setVisibility(View.GONE);
                btnActDemandDetailsSend.setText("不能预约自己的信件哦~");
                btnActDemandDetailsSend.setEnabled(false);
            }
        }
        if (item.getLetterState() != 0) {
//            btnActDemandDetailsSend.setVisibility(View.GONE);
            btnActDemandDetailsSend.setEnabled(false);
            btnActDemandDetailsSend.setText("已销毁");
        }

        Glide.with(this).
                load(item.getOwenerHeadImg())
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new GlideCircleTransform(this))
                .into(imvActDemandDetailsAvatar);

        if (!TextUtils.isEmpty((item.getOwnerName()))) {
            tvActDemandDetailsUsername.setText(item.getOwnerName());
        }

        if (!TextUtils.isEmpty(item.getTopicName())) {
            tvActLetterDetailTopic.setText(item.getTopicName());
        }

        if (!TextUtils.isEmpty(item.getLetterTitle())) {
            tvActLetterDetailTitle.setText(item.getLetterTitle());
        }

        if (!TextUtils.isEmpty(item.getLetterNumber())) {
            tvActLetterDetailNumber.setText(item.getLetterNumber());
        }

        if (!TextUtils.isEmpty(item.getShopName())) {
            tvActLetterDetailShope.setText(textUtils(item.getShopName()));
        }
    }

    private String textUtils(String s) {
        String name = "";
        if (!TextUtils.isEmpty(s)) {
            String[] strings = s.split("-");
            if (strings.length > 2) {
                name = strings[2];
            }
        }
        return name;
    }

    private String addressUtils(String s) {
        String name = "";
        if (!TextUtils.isEmpty(s)) {
            String[] strings = s.split("-");
            if (strings.length > 2) {
                name = strings[0] + "-" + strings[1];
            }
        }
        return name;
    }

    @Override
    public void initData() {
        isOk = false;
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.imv_act_top_right, R.id.imv_act_top_back, R.id.btn_act_demand_details_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                Intent intent = new Intent();
                intent.putExtra("isOk", isOk);
                if (getIntent().getIntExtra("position", -1) > -1) {
                    intent.putExtra("position", getIntent().getIntExtra("position", -1));
                }
                this.setResult(0, intent);
                finish();
                break;
            case R.id.btn_act_demand_details_send:
                showSubPop(item);

                break;
            case R.id.imv_act_top_right:
                showMapChoosePop(this);
                break;
        }
    }

    private ShowSubPopupWindow mShowSubPopupWindow;

    /**
     * 地图弹窗的处理方法
     */
    private void showSubPop(final ShowShopInfo.LetterBean item) {
        mShowSubPopupWindow = new ShowSubPopupWindow(this);
        darkenBackground(0.5f);
        mShowSubPopupWindow.showAtLocation(LetterDetailActivity.this.findViewById(R.id.lay_act_letter_details), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

        mShowSubPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

        mShowSubPopupWindow.setOnItemClickListener(new MapPopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_pop_sub_ok:
                        ToastUtils.showToast(LetterDetailActivity.this, "确认");

                        SubLetterBody body = new SubLetterBody(item.getLetterId(), SharedPreferencesUtils.getString(LetterDetailActivity.this, Constants.USER_TOKNE));
                        Observable<BaseEntity> observable1 = RetrofitFactory.getInstance().subscribeLetter(body);
                        observable1.compose(RxSchedulers.<BaseEntity>compose())
                                .subscribe(new BaseObserver(LetterDetailActivity.this) {
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
                                        ToastUtils.showToast(LetterDetailActivity.this, "预约成功");
                                        btnActDemandDetailsSend.setEnabled(false);
                                        btnActDemandDetailsSend.setText("已预约");
                                        isOk = true;
                                        mShowSubPopupWindow.dismiss();
                                    }

                                    @Override
                                    protected void onHandleError(String msg) {
                                        ToastUtils.showToast(LetterDetailActivity.this, "预约失败" + msg);
                                        mShowSubPopupWindow.dismiss();
                                    }
                                });
                        break;
                    case R.id.btn_pop_sub_clean:
                        mShowSubPopupWindow.dismiss();
                        ToastUtils.showToast(LetterDetailActivity.this, "取消");
                        break;
                }
            }
        });

    }

    /**
     * 地图弹窗的处理方法
     */
    private void showMapChoosePop(Context context) {
        mSharePopupWindow = new SharePopupWindow(context);
        darkenBackground(0.5f);
        mSharePopupWindow.showAtLocation(LetterDetailActivity.this.findViewById(R.id.lay_act_letter_details), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

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
                        cm.setText(Constants.BASE_URL + "share/him.html?id=" + item.getLetterId());
                        ToastUtils.showToast(LetterDetailActivity.this, "链接已经复制到剪切板");
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
        UMImage qq_thumb = new UMImage(LetterDetailActivity.this, R.mipmap._logo);
        UMWeb qq_web = new UMWeb(Constants.SHARE_URL + item.getLetterId());
        qq_web.setThumb(qq_thumb);  //缩略图
        qq_web.setTitle(LetterUtils.setTitle(item));//标题
        qq_web.setDescription("所在店铺：" + textUtils(item.getShopName()) + "  店铺地址:" + addressUtils(item.getShopName()));

        new ShareAction(LetterDetailActivity.this)
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

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent();
        intent.putExtra("isOk", isOk);
        if (getIntent().getIntExtra("position", -1) > -1) {
            intent.putExtra("position", getIntent().getIntExtra("position", -1));
        }
        this.setResult(0, intent);
        return super.onKeyDown(keyCode, event);
    }
}

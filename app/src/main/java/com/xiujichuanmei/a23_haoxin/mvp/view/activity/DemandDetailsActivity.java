package com.xiujichuanmei.a23_haoxin.mvp.view.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.project.lx.baseproject.bean.responses.ShowShopInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.LogUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.SharePopupWindow;
import com.xiujichuanmei.a23_haoxin.utils.LetterUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @name: DemandDetailsActivity
 * @description: 需求详情页
 * @version: 1.0
 * @date: 2017/6/12 17:20
 * @company:
 * @author: Meteor
 * @email: lx802315@163.com
 */
public class DemandDetailsActivity extends BaseActivity {

    SharePopupWindow mSharePopupWindow;
    @BindView(R.id.imv_act_top_back)
    ImageView imvActTopBack;
    @BindView(R.id.text_act_top)
    TextView textActTop;
    @BindView(R.id.imv_act_top_right)
    ImageView imvActTopRight;

    Intent intent;
    ShowShopInfo.DemandBean item;
    @BindView(R.id.imv_act_demand_details_avatar)
    ImageView imvActDemandDetailsAvatar;
    @BindView(R.id.tv_act_demand_details_username)
    TextView tvActDemandDetailsUsername;
    @BindView(R.id.tv_act_demand_details_content)
    TextView tvActDemandDetailsContent;
    @BindView(R.id.btn_act_demand_details_send)
    Button btnActDemandDetailsSend;
    @BindView(R.id.lay_act_demand_details)
    LinearLayout layActDemandDetails;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        item = new ShowShopInfo.DemandBean();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initRootView() {
        setContentView(R.layout.activity_demand_details);
    }

    @Override
    public void initView() {
        item.setShopName(intent.getStringExtra("shopName"));
        item.setShopId(intent.getStringExtra("shopId"));
        item.setOwnerId(intent.getStringExtra("ownerId"));
        item.setOwenerHeadImg(intent.getStringExtra("owenerHeadImg"));
        item.setOwnerName(LetterUtils.setNickName(intent.getStringExtra("ownerName")));
        item.setDemandContent(intent.getStringExtra("demandContent"));
        item.setShopCoordinate(intent.getStringExtra("shopCoordinate"));
        item.setDemandId(intent.getStringExtra("demandId"));
        item.setDemandState(intent.getIntExtra("demandState", 0));
        item.setCreateTime(intent.getStringExtra("createTime"));

        if (!TextUtils.isEmpty(item.getCreateTime())) {
            tvCreateTime.setText(item.getCreateTime() + "发布");
        }

        if (item.getOwnerId() == null || item.getDemandState() == 1) {
            btnActDemandDetailsSend.setText("已完成");
            btnActDemandDetailsSend.setEnabled(false);
        }

        if (item.getOwnerId() != null && item.getOwnerId().equals(MyApplication.getInstance().getCurrentUser().getUserInfor().getUserId())) {
            btnActDemandDetailsSend.setText("不能给自己写信~");
            btnActDemandDetailsSend.setEnabled(false);
        }

        if (item.getOwnerName() != null) {
            tvActDemandDetailsUsername.setText(item.getOwnerName());
        }

        if (item.getDemandContent() != null) {
            tvActDemandDetailsContent.setText("     " + item.getDemandContent());
        }

        if (item.getOwenerHeadImg() != null) {
            Glide.with(this).load(item.getOwenerHeadImg())
                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
                    .bitmapTransform(new GlideCircleTransform(this))
                    .into(imvActDemandDetailsAvatar);
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    /**
     * 地图弹窗的处理方法
     */
    private void showMapChoosePop(Context context) {
        mSharePopupWindow = new SharePopupWindow(context);
        darkenBackground(0.5f);
        mSharePopupWindow.showAtLocation(DemandDetailsActivity.this.findViewById(R.id.lay_act_demand_details), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        mSharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

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
                        cm.setText(Constants.BASE_URL + "share/him.html?id=" + item.getDemandId());
                        ToastUtils.showToast(DemandDetailsActivity.this, "链接已经复制到剪切板");
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
        UMImage qq_thumb = new UMImage(DemandDetailsActivity.this, R.mipmap._logo);
        UMWeb qq_web = new UMWeb(Constants.SHARE_URL + item.getDemandId());
        qq_web.setThumb(qq_thumb);  //缩略图
        qq_web.setTitle("期待你的来信");//标题
        qq_web.setDescription("所在店铺：" + textUtils(item.getShopName()) + "  店铺地址:" + addressUtils(item.getShopName()));

        new ShareAction(DemandDetailsActivity.this)
                .withMedia(qq_web)
                .setPlatform(share_media)
                .setCallback(shareListener)
                .share();
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

    @OnClick({R.id.imv_act_top_right, R.id.imv_act_top_back, R.id.btn_act_demand_details_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_act_top_back:
                finish();
                break;
            case R.id.btn_act_demand_details_send:
                Intent intent = new Intent(DemandDetailsActivity.this, ReleaseLetterActivity.class);
                intent.putExtra("shopName", item.getShopName());
                intent.putExtra("shopId", item.getShopId());
                intent.putExtra("ownerId", item.getOwnerId());
                intent.putExtra("ownerName", item.getOwnerName());
                intent.putExtra("owenerHeadImg", item.getOwenerHeadImg());
                intent.putExtra("shopCoordinate", item.getShopCoordinate());
                intent.putExtra("demandId", item.getDemandId());
                startActivity(intent);
                finish();
                break;
            case R.id.imv_act_top_right:
                showMapChoosePop(this);
                break;
        }
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
}

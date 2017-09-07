package com.xiujichuanmei.a23_haoxin.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.AttentionInfo;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;
import com.xiujichuanmei.a23_haoxin.utils.LetterUtils;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/21 17:11
 * @company:
 * @email: lx802315@163.com
 */
public class AttintionAdapter extends BaseQuickAdapter<AttentionInfo.MyAttentionsBean, BaseViewHolder> {

    public AttintionAdapter(@LayoutRes int layoutResId, @Nullable List<AttentionInfo.MyAttentionsBean> data) {
        super(R.layout.item_choose_recipi_all, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttentionInfo.MyAttentionsBean item) {
        helper.setText(R.id.tv_item_choose_recipi_all_name, LetterUtils.setNickName(item.getNickName()));
        Glide.with(mContext).
                load(item.getHeadImg())
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.imv_item_choose_recipi_avatar));
    }
}

package com.xiujichuanmei.a23_haoxin.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.ShopLetterInfo;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.TimeUtils;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;
import com.xiujichuanmei.a23_haoxin.utils.LetterUtils;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/29 10:25
 * @company:
 * @email: lx802315@163.com
 */
public class ShopFlowLetterAdapter extends BaseQuickAdapter<ShopLetterInfo.LetterBean, BaseViewHolder> {

    public ShopFlowLetterAdapter(@LayoutRes int layoutResId, @Nullable List<ShopLetterInfo.LetterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopLetterInfo.LetterBean item) {
        Glide.with(mContext).
                load(item.getOwenerHeadImg())
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.imv_act_flow_letter_avatar));

        helper.setText(R.id.text_act_flow_letter_avatar_name, LetterUtils.setNickName(item.getOwnerName()))
                .setText(R.id.text_act_flow_letter_number, "编号:" + item.getLetterNumber())
                .setText(R.id.tv_act_flow_letter_time, TimeUtils.getStrTime(item.getLetterFinishTime()));

        TextView textView = helper.getView(R.id.text_act_flow_letter_type);
        LetterUtils.setTitle(textView, item);

    }

}

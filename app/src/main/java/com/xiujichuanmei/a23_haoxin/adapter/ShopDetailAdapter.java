package com.xiujichuanmei.a23_haoxin.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.ShowShopInfo;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;
import com.xiujichuanmei.a23_haoxin.utils.LetterUtils;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/7 21:54
 * @company:
 * @email: lx802315@163.com
 */
public class ShopDetailAdapter extends BaseQuickAdapter<ShowShopInfo.LetterBean, BaseViewHolder> {

    public ShopDetailAdapter(@LayoutRes int layoutResId, @Nullable List<ShowShopInfo.LetterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShowShopInfo.LetterBean item) {
        if (item.getLetterId() != null) {
            helper.setText(R.id.text_act_shop_detail_avatar_name, LetterUtils.setNickName(item.getOwnerName()))
                    .setText(R.id.text_act_shop_detail_number, item.getLetterNumber())
                    .addOnClickListener(R.id.imv_act_shop_detail_avatar)
                    .addOnClickListener(R.id.text_act_shop_detail_shop_down_time)
                    .addOnClickListener(R.id.btn_act_shop_detail_shop_order);

            Glide.with(mContext).
                    load(item.getOwenerHeadImg())
                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .into((ImageView) helper.getView(R.id.imv_act_shop_detail_avatar));

            TextView title = helper.getView(R.id.text_act_shop_detail_type);
            LetterUtils.setTitle(title, item);
        } else {
            helper.setVisible(R.id.tv_act_shop_detail_shop_empty, true)
                    .setVisible(R.id.imv_act_shop_detail_avatar, false)
                    .setVisible(R.id.rel_act_shop_details_item, false)
                    .setVisible(R.id.text_act_shop_detail_shop_down_time, false)
                    .setVisible(R.id.btn_act_shop_detail_shop_order, false);
        }
    }

}

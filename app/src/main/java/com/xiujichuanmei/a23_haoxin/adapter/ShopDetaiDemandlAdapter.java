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
public class ShopDetaiDemandlAdapter extends BaseQuickAdapter<ShowShopInfo.DemandBean, BaseViewHolder> {

    public ShopDetaiDemandlAdapter(@LayoutRes int layoutResId, @Nullable List<ShowShopInfo.DemandBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShowShopInfo.DemandBean item) {
        if (item.getDemandId() != null && item.getDemandId().length() > 0) {
            helper.setText(R.id.imv_act_shop_detail_demand_name, LetterUtils.setNickName(item.getOwnerName()))
                    .setText(R.id.text_act_frag_user_wait_start_type, item.getDemandContent())
                    .setText(R.id.tv_create_time, item.getCreateTime() + "发布")
                    .addOnClickListener(R.id.imv_act_shop_detail_demand_avatar)
                    .addOnClickListener(R.id.imv_act_shop_detail_demand_time)
                    .addOnClickListener(R.id.imv_act_shop_detail_demand_write);

            Glide.with(mContext).
                    load(item.getOwenerHeadImg())
                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .into((ImageView) helper.getView(R.id.imv_act_shop_detail_demand_avatar));
        } else {
            helper.setVisible(R.id.imv_act_shop_detail_demand_avatar, false)
                    .setVisible(R.id.lay_act_shop_detail_demand, false)
                    .setVisible(R.id.lay_act_shop_detail_demand_btn, false)
                    .setVisible(R.id.tv_act_shop_detail_shop_empty, true);
        }

    }

}

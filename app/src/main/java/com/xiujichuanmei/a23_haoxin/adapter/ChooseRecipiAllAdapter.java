package com.xiujichuanmei.a23_haoxin.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.ContactsInfo;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;
import com.xiujichuanmei.a23_haoxin.utils.LetterUtils;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 话题搜索Adapter
 * @version: V_1.0.0
 * @date: 2017/6/19 14:33
 * @company:
 * @email: lx802315@163.com
 */
public class ChooseRecipiAllAdapter extends BaseQuickAdapter<ContactsInfo.FriendBean, BaseViewHolder> {

    public ChooseRecipiAllAdapter(@LayoutRes int layoutResId, @Nullable List<ContactsInfo.FriendBean> data) {
        super(R.layout.item_choose_recipi_all, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactsInfo.FriendBean item) {
        helper.setText(R.id.tv_item_choose_recipi_all_name, LetterUtils.setNickName(item.getNickName()));

        Glide.with(mContext).
                load(item.getHeadImg())
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.imv_item_choose_recipi_avatar));
    }
}

package com.xiujichuanmei.a23_haoxin.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.ContactsInfo;
import com.project.lx.baseproject.bean.responses.TopicInfo;
import com.project.lx.baseproject.widget.MyTextView;
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
public class ChooseRecipiHistoryAdapter extends BaseQuickAdapter<ContactsInfo.RecentlyFriendBean, BaseViewHolder> {

    public ChooseRecipiHistoryAdapter(@LayoutRes int layoutResId, @Nullable List<ContactsInfo.RecentlyFriendBean> data) {
        super(R.layout.item_search_topic, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactsInfo.RecentlyFriendBean item) {
        helper.setText(R.id.tv_item_search_topic_name, LetterUtils.setNickName(item.getNickName()))
                .setText(R.id.tv_item_search_topic_content, "来往信件（" + item.getLetterCount() + ")");

        Glide.with(mContext).
                load(item.getHeadImg())
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.imv_item_search_avatar));
    }

}

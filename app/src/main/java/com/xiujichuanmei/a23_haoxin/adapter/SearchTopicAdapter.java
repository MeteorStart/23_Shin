package com.xiujichuanmei.a23_haoxin.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.TopicInfo;
import com.project.lx.baseproject.widget.MyTextView;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author: X_Meteor
 * @description: 话题搜索Adapter
 * @version: V_1.0.0
 * @date: 2017/6/19 14:33
 * @company:
 * @email: lx802315@163.com
 */
public class SearchTopicAdapter extends BaseQuickAdapter<TopicInfo.TopicBean, BaseViewHolder> {

    //关键字
    private String supperText;

    public String getSupperText() {
        return supperText;
    }

    public void setSupperText(String supperText) {
        this.supperText = supperText;
    }

    public SearchTopicAdapter(@LayoutRes int layoutResId, @Nullable List<TopicInfo.TopicBean> data) {
        super(R.layout.item_search_topic, data);
        supperText = "";
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicInfo.TopicBean item) {
        MyTextView name = helper.getView(R.id.tv_item_search_topic_name);
        MyTextView content = helper.getView(R.id.tv_item_search_topic_content);

        if (supperText.length() > 0) {
            name.setSpecifiedTextsColor(item.getTopicName(), supperText, Color.parseColor("#818b56"));
            content.setSpecifiedTextsColor(item.getTopcContent(), supperText, Color.parseColor("#818b56"));
        } else {
            name.setText(item.getTopicName());
            content.setText(item.getTopcContent());
        }

//        helper.setText(R.id.tv_item_search_topic_name, item.getTopicName())
//                .setText(R.id.tv_item_search_topic_content, item.getTopcContent());

        Glide.with(mContext).
                load(item.getTopicImg())
                .placeholder(R.drawable.icon_dianpu_banner)
                .error(R.drawable.icon_dianpu_banner)
                .bitmapTransform(new RoundedCornersTransformation(mContext, 35, 0))
                .into((ImageView) helper.getView(R.id.imv_item_search_avatar));
    }
}

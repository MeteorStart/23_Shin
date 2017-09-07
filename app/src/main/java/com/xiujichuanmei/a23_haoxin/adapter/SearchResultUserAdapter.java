package com.xiujichuanmei.a23_haoxin.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.SearchResultInfo;
import com.project.lx.baseproject.widget.MyTextView;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/29 17:15
 * @company:
 * @email: lx802315@163.com
 */
public class SearchResultUserAdapter extends BaseQuickAdapter<SearchResultInfo.UsersBean, BaseViewHolder> {
    //关键字
    private String supperText;

    public String getSupperText() {
        return supperText;
    }

    public void setSupperText(String supperText) {
        this.supperText = supperText;
    }

    public SearchResultUserAdapter(@LayoutRes int layoutResId, @Nullable List<SearchResultInfo.UsersBean> data) {
        super(layoutResId, data);
        supperText = "";
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultInfo.UsersBean item) {
        MyTextView name = helper.getView(R.id.tv_search_result_name);

        if (supperText.length() > 0) {
            name.setSpecifiedTextsColor(item.getNickName(), supperText, Color.parseColor("#818b56"));
        } else {
            name.setText(item.getNickName());
        }

        if (!TextUtils.isEmpty(item.getHeadImg())) {
            Glide.with(mContext).
                    load(item.getHeadImg())
                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .into((ImageView) helper.getView(R.id.imv_search_result_avatar));
        } else {
            Glide.with(mContext).
                    load(R.drawable.icon_denglu_touxiang_weidenglu)
                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .into((ImageView) helper.getView(R.id.imv_search_result_avatar));
        }
//        if (item.getHeadImg() != null && item.getHeadImg().length() > 0) {
//            Glide.with(mContext).
//                    load(item.getHeadImg())
//                    .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
//                    .error(R.drawable.icon_denglu_touxiang_weidenglu)
//                    .bitmapTransform(new GlideCircleTransform(mContext))
//                    .into((ImageView) helper.getView(R.id.imv_search_result_avatar));
//        } else {
//            helper.setVisible(R.id.imv_search_result_avatar, false);
//        }
    }
}

package com.xiujichuanmei.a23_haoxin.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
public class SearchResultLetterAdapter extends BaseQuickAdapter<SearchResultInfo.LettersBean, BaseViewHolder> {
    //关键字
    private String supperText;

    public String getSupperText() {
        return supperText;
    }

    public void setSupperText(String supperText) {
        this.supperText = supperText;
    }

    public SearchResultLetterAdapter(@LayoutRes int layoutResId, @Nullable List<SearchResultInfo.LettersBean> data) {
        super(layoutResId, data);
        supperText = "";
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultInfo.LettersBean item) {
        MyTextView name = helper.getView(R.id.tv_search_result_name);

        if (supperText.length() > 0) {
            name.setSpecifiedTextsColor(item.getLetterTitle() + "", supperText, Color.parseColor("#818b56"));
        } else {
            name.setText(item.getLetterTitle() + "");
        }

        if (!TextUtils.isEmpty(item.getOwenerHeadImg())) {
            Glide.with(mContext).
                    load(item.getOwenerHeadImg())
                    .placeholder(R.drawable.letter_avatar)
                    .error(R.drawable.letter_avatar)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .into((ImageView) helper.getView(R.id.imv_search_result_avatar));
        } else {
            Glide.with(mContext).
                    load(R.drawable.letter_avatar)
                    .placeholder(R.drawable.letter_avatar)
                    .error(R.drawable.letter_avatar)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .into((ImageView) helper.getView(R.id.imv_search_result_avatar));
        }

    }
}

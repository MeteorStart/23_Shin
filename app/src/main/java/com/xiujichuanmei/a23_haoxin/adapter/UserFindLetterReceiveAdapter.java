package com.xiujichuanmei.a23_haoxin.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.LetterInfo;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.mvp.view.view.GlideCircleTransform;
import com.xiujichuanmei.a23_haoxin.utils.LetterUtils;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/26 10:48
 * @company:
 * @email: lx802315@163.com
 */
public class UserFindLetterReceiveAdapter extends BaseQuickAdapter<LetterInfo.LetterBean, BaseViewHolder> {

    public UserFindLetterReceiveAdapter(@LayoutRes int layoutResId, @Nullable List<LetterInfo.LetterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LetterInfo.LetterBean item) {
        helper.setText(R.id.text_frag_user_find_recieve_avatar_name, LetterUtils.setNickName(item.getOwnerName()))
                .addOnClickListener(R.id.imv_frag_user_find_recieve_avatar)
                .setText(R.id.text_frag_user_find_recieve_number, "编号:" + item.getLetterNumber())
                .addOnClickListener(R.id.btn_act_frag_user_find_recieve_sub);

        Glide.with(mContext).
                load(item.getOwenerHeadImg())
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.imv_frag_user_find_recieve_avatar));

        TextView title = helper.getView(R.id.text_act_frag_user_find_recieve_type);
        LetterUtils.setTitle(title, item);

    }
}

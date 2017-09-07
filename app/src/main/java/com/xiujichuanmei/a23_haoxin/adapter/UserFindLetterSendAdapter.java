package com.xiujichuanmei.a23_haoxin.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.application.MyApplication;
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
public class UserFindLetterSendAdapter extends BaseQuickAdapter<LetterInfo.LetterBean, BaseViewHolder> {

    public UserFindLetterSendAdapter(@LayoutRes int layoutResId, @Nullable List<LetterInfo.LetterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LetterInfo.LetterBean item) {
        helper.setText(R.id.text_frag_user_find_send_number, "编号:" + item.getLetterNumber())
                .addOnClickListener(R.id.imv_frag_user_find_send_avatar)
                .addOnClickListener(R.id.imv_frag_user_find_send_scan);
        switch (item.getLetterState()) {
            case 0:
                helper.setText(R.id.text_frag_user_find_send_avatar_name, MyApplication.getInstance().getCurrentUser().getUserInfor().getNickName())
                        .setText(R.id.text_act_frag_user_find_send_type, "待预约");
                break;
            case 1:
                helper.setText(R.id.text_frag_user_find_send_avatar_name, LetterUtils.setNickName(item.getOwnerName()))
                        .setText(R.id.text_act_frag_user_find_send_type, "已预约");
            case 2:
                helper.setText(R.id.text_frag_user_find_send_avatar_name, LetterUtils.setNickName(item.getOwnerName()))
                        .setText(R.id.text_act_frag_user_find_send_type, "待中转");
                break;
            case 3:
                helper.setText(R.id.text_frag_user_find_send_avatar_name, LetterUtils.setNickName(item.getOwnerName()))
                        .setText(R.id.text_act_frag_user_find_send_type, "已收到");
            case 4:
                helper.setText(R.id.text_frag_user_find_send_avatar_name, LetterUtils.setNickName(item.getOwnerName()))
                        .setText(R.id.text_act_frag_user_find_send_type, "已销毁");
                break;
        }

        if (item.getOwenerHeadImg() == null) {
            item.setOwenerHeadImg(MyApplication.getInstance().getCurrentUser().getUserInfor().getHeadImg());
        }

        Glide.with(mContext).
                load(item.getOwenerHeadImg())
                .placeholder(R.drawable.letter_avatar)
                .error(R.drawable.letter_avatar)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.imv_frag_user_find_send_avatar));

//        TextView title = helper.getView(R.id.text_act_frag_user_find_send_type);
//        LetterUtils.setTitle(title, item);

    }
}

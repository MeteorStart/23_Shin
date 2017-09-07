package com.xiujichuanmei.a23_haoxin.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.application.MyApplication;
import com.project.lx.baseproject.bean.responses.DemandInfo;
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
public class UserWaitLetterStartAdapter extends BaseQuickAdapter<DemandInfo.MyDemandBean, BaseViewHolder> {

    public UserWaitLetterStartAdapter(@LayoutRes int layoutResId, @Nullable List<DemandInfo.MyDemandBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemandInfo.MyDemandBean item) {
        helper.setText(R.id.text_frag_user_wait_start_avatar_name, LetterUtils.setNickName(item.getOwnerName()))
                .addOnClickListener(R.id.imv_frag_user_wait_start_avatar)
                .setText(R.id.text_act_frag_user_wait_start_type, item.getDemandContent());

        TextView time = helper.getView(R.id.text_frag_user_wait_start_time);

        if (item.getDemandState() == 0) {
            time.setCompoundDrawables(null, null, null, null);
            time.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            time.setText(item.getCreateTime() + "发布");
//            helper.addOnClickListener(R.id.text_frag_user_wait_start_time);
        } else if (item.getDemandState() == 1) {
            time.setCompoundDrawables(null, null, null, null);
            time.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            time.setText("已完成");
        }

        if (item.getOwenerHeadImg() == null) {
            item.setOwenerHeadImg(MyApplication.getInstance().getCurrentUser().getUserInfor().getHeadImg());
        }
        Glide.with(mContext).
                load(item.getOwenerHeadImg())
                .placeholder(R.drawable.icon_denglu_touxiang_weidenglu)
                .error(R.drawable.icon_denglu_touxiang_weidenglu)
                .bitmapTransform(new GlideCircleTransform(mContext))
                .into((ImageView) helper.getView(R.id.imv_frag_user_wait_start_avatar));

//        LetterUtils.setTitle(title, item);
    }
}

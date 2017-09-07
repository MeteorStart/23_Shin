package com.xiujichuanmei.a23_haoxin.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.ShowUserInformationInfo;
import com.xiujichuanmei.a23_haoxin.R;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/28 15:58
 * @company:
 * @email: lx802315@163.com
 */
public class UserInfoWaitAdapter extends BaseQuickAdapter<ShowUserInformationInfo.DemandBean, BaseViewHolder> {
    public UserInfoWaitAdapter(@LayoutRes int layoutResId, @Nullable List<ShowUserInformationInfo.DemandBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShowUserInformationInfo.DemandBean item) {
        helper.setText(R.id.text_act_frag_user_find_sub_type, item.getDemandContent())
                .setText(R.id.tv_create_time, item.getCreateTime() + "发布")
                .addOnClickListener(R.id.text_frag_user_find_sub_avatar_name)
                .addOnClickListener(R.id.imv_frag_user_find_sub_scan);
    }
}

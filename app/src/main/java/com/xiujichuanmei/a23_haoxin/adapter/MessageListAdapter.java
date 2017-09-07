package com.xiujichuanmei.a23_haoxin.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.MessageInfo;
import com.project.lx.baseproject.util.TimeUtils;
import com.xiujichuanmei.a23_haoxin.R;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/7/7 10:18
 * @company:
 * @email: lx802315@163.com
 */
public class MessageListAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {

    public MessageListAdapter(@LayoutRes int layoutResId, @Nullable List<MessageInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        helper.setText(R.id.tv_act_message_time, TimeUtils.getStrTime(item.getCtime()))
                .setText(R.id.tv_act_message_content, item.getContent());
    }
}

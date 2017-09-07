package com.xiujichuanmei.a23_haoxin.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.lx.baseproject.bean.responses.SearchHotInfo;
import com.xiujichuanmei.a23_haoxin.R;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/29 15:58
 * @company:
 * @email: lx802315@163.com
 */
public class SearchHotAdapter extends BaseQuickAdapter<SearchHotInfo.HotSearchsBean, BaseViewHolder> {

    public SearchHotAdapter(@LayoutRes int layoutResId, @Nullable List<SearchHotInfo.HotSearchsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHotInfo.HotSearchsBean item) {
        helper.setText(R.id.tv_act_search_hot, item.getContent());
    }
}

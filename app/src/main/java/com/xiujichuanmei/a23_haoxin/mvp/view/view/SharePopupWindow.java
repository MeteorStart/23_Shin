package com.xiujichuanmei.a23_haoxin.mvp.view.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.xiujichuanmei.a23_haoxin.R;

/**
 * @author: X_Meteor
 * @description: 地图选择弹窗
 * @version: V_1.0.0
 * @date: 2017/6/7 9:55
 * @company:
 * @email: lx802315@163.com
 */
public class SharePopupWindow extends PopupWindow implements View.OnClickListener {

    Context context;

    private View mPopView;
    private OnItemClickListener mListener;

    ImageView weixin, pengyouquan, xinlang, qqkongjian, qq, lianjie, close;

    public SharePopupWindow(Context context) {
        super(context);
        this.context = context;

        init(context);
        setPopupWindow();

        weixin.setOnClickListener(this);
        pengyouquan.setOnClickListener(this);
        xinlang.setOnClickListener(this);
        qqkongjian.setOnClickListener(this);
        qq.setOnClickListener(this);
        lianjie.setOnClickListener(this);
        close.setOnClickListener(this);
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.pop_choose_share, null);
        weixin = (ImageView) mPopView.findViewById(R.id.imv_pop_choose_share_weixin);
        pengyouquan = (ImageView) mPopView.findViewById(R.id.imv_pop_choose_share_pengyouquan);
        xinlang = (ImageView) mPopView.findViewById(R.id.imv_pop_choose_share_xinlang);
        qqkongjian = (ImageView) mPopView.findViewById(R.id.imv_pop_choose_share_qqkongjian);
        qq = (ImageView) mPopView.findViewById(R.id.imv_pop_choose_share_qq);
        lianjie = (ImageView) mPopView.findViewById(R.id.imv_pop_choose_share_lianjie);
        close = (ImageView) mPopView.findViewById(R.id.imv_pop_choose_share_close);
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.getRootView().getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }


}

package com.xiujichuanmei.a23_haoxin.mvp.view.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/12 9:57
 * @company:
 * @email: lx802315@163.com
 */
public class PopActUserInfo extends PopupWindow implements View.OnClickListener {

    private View mPopView;
    Context context;
    TextView tvShare, tvReport;

    private OnItemClickListener mListener;


    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
    public PopActUserInfo(Context context) {
        super(context);
        this.context = context;
        init(context);
        setPopupWindow();

        tvShare.setOnClickListener(this);
        tvReport.setOnClickListener(this);
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
        mPopView = inflater.inflate(R.layout.pop_act_user_info, null);

        tvShare = (TextView) mPopView.findViewById(R.id.tv_pop_user_info_share);
        tvReport = (TextView) mPopView.findViewById(R.id.tv_pop_user_info_report);

    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        // 加上下面两行可以用back键关闭popupwindow，否则必须调用dismiss();
        ColorDrawable dw = new ColorDrawable(00000);
        this.setBackgroundDrawable(dw);
        this.update();
        this.setFocusable(true);// 设置弹出窗口可
//        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
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
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
//        switch (v.getId()) {
//
//            case R.id.tv_pop_user_info_share:
//                ToastUtils.showToast(context,"点击的分享");
//                break;
//            case R.id.tv_pop_user_info_report:
//                ToastUtils.showToast(context,"点击的举报");
//                break;
//        }
    }
}

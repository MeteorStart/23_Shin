package com.xiujichuanmei.a23_haoxin.mvp.view.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.baidu.mapapi.model.LatLng;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.util.ToastUtils;
import com.xiujichuanmei.a23_haoxin.R;
import com.xiujichuanmei.a23_haoxin.utils.BaiduNaviUtils;

import java.util.List;

/**
 * @author: X_Meteor
 * @description: 地图选择弹窗
 * @version: V_1.0.0
 * @date: 2017/6/7 9:55
 * @company:
 * @email: lx802315@163.com
 */
public class MapPopupWindow extends PopupWindow implements View.OnClickListener {

    Context context;
    List<Integer> mList;

    private View mPopView;
    private OnItemClickListener mListener;

    LinearLayout layPopMapBaidu;
    LinearLayout layPopMapGaode;
    CheckBox checkboxPopMapBaidu;
    CheckBox checkboxPopMapGaode;

    LatLng latLng;

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public MapPopupWindow(Context context, List<Integer> list) {
        super(context);
        this.context = context;
        this.mList = list;
        init(context, list);
        setPopupWindow();

        layPopMapBaidu.setOnClickListener(this);
        layPopMapGaode.setOnClickListener(this);
        checkboxPopMapBaidu.setOnClickListener(this);
        checkboxPopMapGaode.setOnClickListener(this);
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context, List<Integer> list) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.pop_choose_map, null);

        layPopMapBaidu = (LinearLayout) mPopView.findViewById(R.id.lay_pop_map_baidu);
        layPopMapGaode = (LinearLayout) mPopView.findViewById(R.id.lay_pop_map_gaode);
        checkboxPopMapBaidu = (CheckBox) mPopView.findViewById(R.id.checkbox_pop_map_baidu);
        checkboxPopMapGaode = (CheckBox) mPopView.findViewById(R.id.checkbox_pop_map_gaode);

        //如果本机装有地图，进行弹窗展示
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                switch (list.get(i)) {
                    case Constants.GAODE_MAP_TYPE:
                        layPopMapGaode.setVisibility(View.VISIBLE);
                        break;
                    case Constants.BAIDU_MAP_TYPE:
                        layPopMapBaidu.setVisibility(View.VISIBLE);
                        break;
                }
            }
        } else {//如果没有地图，进行处理
            ToastUtils.showToast(context, "本机安装没有地图，将跳转到百度web");
            if (latLng != null) {
                BaiduNaviUtils.startWebNavi(BaiduNaviUtils.stringToLatLng(SharedPreferencesUtils.getString(context, "LatLng")), latLng, null, null, context);
            }
        }


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
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
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

            switch (v.getId()) {
                case R.id.lay_pop_map_baidu:
                    checkboxPopMapBaidu.setChecked(true);
                    break;
                case R.id.lay_pop_map_gaode:
                    checkboxPopMapGaode.setChecked(true);
                    break;
            }
        }
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}

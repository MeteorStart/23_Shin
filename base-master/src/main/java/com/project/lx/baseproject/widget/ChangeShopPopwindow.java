package com.project.lx.baseproject.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.lx.baseproject.R;
import com.project.lx.baseproject.constants.Constants;
import com.project.lx.baseproject.util.SharedPreferencesUtils;
import com.project.lx.baseproject.widget.wheelview.OnWheelChangedListener;
import com.project.lx.baseproject.widget.wheelview.OnWheelScrollListener;
import com.project.lx.baseproject.widget.wheelview.WheelView;
import com.project.lx.baseproject.widget.wheelview.adapter.AbstractWheelTextAdapter1;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChangeShopPopwindow extends PopupWindow implements View.OnClickListener {

    private WheelView wvProvince;
    private View lyChangeAddress;
    private View lyChangeAddressChild;
    private TextView btnSure;
    private TextView btnCancel;

    private Context context;
    /**
     * 所有省
     */
    private String[] mProvinceDatas;


    public void setmProvinceDatas(String[] mProvinceDatas) {
        this.mProvinceDatas = mProvinceDatas;
    }


    private ArrayList<String> arrProvinces = new ArrayList<String>();
    private AddressTextAdapter provinceAdapter;

    private String strProvince;
    private OnAddressCListener onAddressCListener;

    private int maxsize = 16;
    private int minsize = 12;

    public ChangeShopPopwindow(final Context context, String[] mProvinceDatas) {
        super(context);
        this.context = context;
        View view = View.inflate(context, R.layout.edit_changeshop_pop_layout, null);

        wvProvince = (WheelView) view.findViewById(R.id.wv_shop_province);
        lyChangeAddress = view.findViewById(R.id.ly_myinfo_changeshop);
        lyChangeAddressChild = view.findViewById(R.id.ly_myinfo_changeshop_child);
        btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
        btnCancel = (TextView) view.findViewById(R.id.btn_myinfo_cancel);

        strProvince = "";

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//		this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        lyChangeAddressChild.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        this.mProvinceDatas = mProvinceDatas;
//        initJsonData();
//        initDatas();


        initProvinces();
        provinceAdapter = new AddressTextAdapter(context, arrProvinces, getProvinceItem(strProvince), maxsize, minsize);
        wvProvince.setVisibleItems(5);
        wvProvince.setViewAdapter(provinceAdapter);
//		wvProvince.setCurrentItem(getProvinceItem(strProvince));

        wvProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                strProvince = currentText;
                setTextviewSize(currentText, provinceAdapter);
            }
        });

        wvProvince.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                strProvince = currentText;
                setTextviewSize(currentText, provinceAdapter);
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                strProvince = currentText;
                setTextviewSize(currentText, provinceAdapter);
            }
        });

    }


    private class AddressTextAdapter extends AbstractWheelTextAdapter1 {
        ArrayList<String> list;

        protected AddressTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(14);
            } else {
                textvew.setTextSize(12);
            }
        }
    }

    public void setAddresskListener(OnAddressCListener onAddressCListener) {
        this.onAddressCListener = onAddressCListener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnSure) {
            if (onAddressCListener != null) {
                if (strProvince.length() < 1) {
                    if (mProvinceDatas.length > 0){
                        strProvince = mProvinceDatas[0];
                    }
                }
                onAddressCListener.onClick(strProvince, getProvinceItem(strProvince));
            }
        } else if (v == btnCancel) {

        } else if (v == lyChangeAddressChild) {
            return;
        } else {
//			dismiss();
        }
        dismiss();
    }

    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface OnAddressCListener {
        void onClick(String province, int pointer);
    }

    /**
     //     * 从文件中读取地址数据
     //     */
//    private void initJsonData() {
//        try {
//            StringBuffer sb = new StringBuffer();
//            InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/" + "city.json");
//            int len = -1;
//            byte[] buf = new byte[1024];
//            while ((len = is.read(buf)) != -1) {
//                sb.append(new String(buf, 0, len, "gbk"));
//            }
//            is.close();
//            mJsonObj = new JSONObject(sb.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 解析整个Json对象，完成后释放Json对象的内存
//     */
//    private void initDatas() {
//        try {
//            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
//            mProvinceDatas = new String[jsonArray.length()];
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
//                String province = jsonP.getString("p");// 省名字
//
//                mProvinceDatas[i] = province;
//
//                JSONArray jsonCs = null;
//                try {
//                    /**
//                     * Throws JSONException if the mapping doesn't exist or is
//                     * not a JSONArray.
//                     */
//                    jsonCs = jsonP.getJSONArray("c");
//                } catch (Exception e1) {
//                    continue;
//                }
//                String[] mCitiesDatas = new String[jsonCs.length()];
//                for (int j = 0; j < jsonCs.length(); j++) {
//                    JSONObject jsonCity = jsonCs.getJSONObject(j);
//                    String city = jsonCity.getString("n");// 市名字
//                    mCitiesDatas[j] = city;
//                    JSONArray jsonAreas = null;
//                    try {
//                        /**
//                         * Throws JSONException if the mapping doesn't exist or
//                         * is not a JSONArray.
//                         */
//                        jsonAreas = jsonCity.getJSONArray("a");
//                    } catch (Exception e) {
//                        continue;
//                    }
//
//                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
//                    for (int k = 0; k < jsonAreas.length(); k++) {
//                        String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
//                        mAreasDatas[k] = area;
//                    }
//                    mAreaDatasMap.put(city, mAreasDatas);
//                }
//
//                mCitisDatasMap.put(province, mCitiesDatas);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        mJsonObj = null;
//    }

    /**
     * 初始化省会
     */
    public void initProvinces() {
        int length = mProvinceDatas.length;
        for (int i = 0; i < length; i++) {
            arrProvinces.add(mProvinceDatas[i]);
        }
    }

    /**
     * 初始化地点
     *
     * @param province
     */
    public void setAddress(String province) {
        if (province != null && province.length() > 0) {
            this.strProvince = province;
            wvProvince.setCurrentItem(getProvinceItem(strProvince));
        }

    }

    /**
     * 返回省会索引，没有就返回默认“广东”
     *
     * @param province
     * @return
     */
    public int getProvinceItem(String province) {
        int size = arrProvinces.size();
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(arrProvinces.get(i))) {
                noprovince = false;
                return provinceIndex;
            } else {
                provinceIndex++;
            }
        }
        if (noprovince) {
            strProvince = "";
            return 0;
        }
        return provinceIndex;
    }


}
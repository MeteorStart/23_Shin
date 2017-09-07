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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChangeAddressPopwindow extends PopupWindow implements View.OnClickListener {

    private WheelView wvProvince;
    private WheelView wvCitys;
    private WheelView wvArea;
    private View lyChangeAddress;
    private View lyChangeAddressChild;
    private TextView btnSure;
    private TextView btnCancel;

    private Context context;
    private JSONObject mJsonObj;
    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市s
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区s
     */
    private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();


    public void setmProvinceDatas(String[] mProvinceDatas) {
        this.mProvinceDatas = mProvinceDatas;
    }

    public void setmCitisDatasMap(Map<String, String[]> mCitisDatasMap) {
        this.mCitisDatasMap = mCitisDatasMap;
    }

    public void setmAreaDatasMap(Map<String, String[]> mAreaDatasMap) {
        this.mAreaDatasMap = mAreaDatasMap;
    }

    private ArrayList<String> arrProvinces = new ArrayList<String>();
    private ArrayList<String> arrCitys = new ArrayList<String>();
    private ArrayList<String> arrAreas = new ArrayList<String>();
    private AddressTextAdapter provinceAdapter;
    private AddressTextAdapter cityAdapter;
    private AddressTextAdapter areaAdapter;

    private String strProvince;
    private String strCity;
    private String strArea;
    private OnAddressCListener onAddressCListener;

    private int maxsize = 16;
    private int minsize = 12;

    public ChangeAddressPopwindow(final Context context, String[] mProvinceDatas, final Map<String, String[]> mCitisDatasMap, final Map<String, String[]> mAreaDatasMap) {
        super(context);
        this.context = context;
        View view = View.inflate(context, R.layout.edit_changeaddress_pop_layout, null);

        wvProvince = (WheelView) view.findViewById(R.id.wv_address_province);
        wvCitys = (WheelView) view.findViewById(R.id.wv_address_city);
        wvArea = (WheelView) view.findViewById(R.id.wv_address_area);
        lyChangeAddress = view.findViewById(R.id.ly_myinfo_changeaddress);
        lyChangeAddressChild = view.findViewById(R.id.ly_myinfo_changeaddress_child);
        btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
        btnCancel = (TextView) view.findViewById(R.id.btn_myinfo_cancel);

        strProvince = SharedPreferencesUtils.getString(context, Constants.ADREESS_PROVINCE, "河南省");
        strCity = SharedPreferencesUtils.getString(context, Constants.ADREESS_CITY, "郑州市");
        strArea = SharedPreferencesUtils.getString(context, Constants.ADREESS_DISTRICT, "中原区");

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
        this.mCitisDatasMap = mCitisDatasMap;
        this.mAreaDatasMap = mAreaDatasMap;
//        initJsonData();
//        initDatas();


        initProvinces();
        provinceAdapter = new AddressTextAdapter(context, arrProvinces, getProvinceItem(strProvince), maxsize, minsize);
        wvProvince.setVisibleItems(5);
        wvProvince.setViewAdapter(provinceAdapter);
        wvProvince.setCurrentItem(getProvinceItem(strProvince));

        initCitys(mCitisDatasMap.get(strProvince));
        cityAdapter = new AddressTextAdapter(context, arrCitys, getCityItem(strCity), maxsize, minsize);
        wvCitys.setVisibleItems(5);
        wvCitys.setViewAdapter(cityAdapter);
        wvCitys.setCurrentItem(getCityItem(strCity));

        initAreas(mAreaDatasMap.get(strCity));
        areaAdapter = new AddressTextAdapter(context, arrAreas, getAreaItem(strArea), maxsize, minsize);
        wvArea.setVisibleItems(5);
        wvArea.setViewAdapter(areaAdapter);
        wvArea.setCurrentItem(getAreaItem(strArea));

        wvProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                strProvince = currentText;
                setTextviewSize(currentText, provinceAdapter);

                String[] citys = mCitisDatasMap.get(currentText);
                initCitys(citys);
                cityAdapter = new AddressTextAdapter(context, arrCitys, 0, maxsize, minsize);
                wvCitys.setVisibleItems(5);
                wvCitys.setViewAdapter(cityAdapter);
                wvCitys.setCurrentItem(0);
                setTextviewSize("0", cityAdapter);

                //根据市，地区联动
                String[] areas = mAreaDatasMap.get(citys[0]);
                initAreas(areas);
                areaAdapter = new AddressTextAdapter(context, arrAreas, 0, maxsize, minsize);
                wvArea.setVisibleItems(5);
                wvArea.setViewAdapter(areaAdapter);
                wvArea.setCurrentItem(0);
                setTextviewSize("0", areaAdapter);
            }
        });

        wvProvince.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, provinceAdapter);
            }
        });

        wvCitys.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                strCity = currentText;
                setTextviewSize(currentText, cityAdapter);

                //根据市，地区联动
                String[] areas = mAreaDatasMap.get(currentText);
                initAreas(areas);
                areaAdapter = new AddressTextAdapter(context, arrAreas, 0, maxsize, minsize);
                wvArea.setVisibleItems(5);
                wvArea.setViewAdapter(areaAdapter);
                wvArea.setCurrentItem(0);
                setTextviewSize("0", areaAdapter);


            }
        });

        wvCitys.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, cityAdapter);
            }
        });

        wvArea.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) areaAdapter.getItemText(wheel.getCurrentItem());
                strArea = currentText;
                setTextviewSize(currentText, cityAdapter);
            }
        });

        wvArea.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) areaAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, areaAdapter);
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
                onAddressCListener.onClick(strProvince, strCity, strArea);
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
        void onClick(String province, String city, String area);
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
     * 根据省会，生成该省会的所有城市
     *
     * @param citys
     */
    public void initCitys(String[] citys) {
        if (citys != null) {
            arrCitys.clear();
            int length = citys.length;
            for (int i = 0; i < length; i++) {
                arrCitys.add(citys[i]);
            }
        } else {
            String[] city = mCitisDatasMap.get(strProvince);
            arrCitys.clear();
            int length = city.length;
            for (int i = 0; i < length; i++) {
                arrCitys.add(city[i]);
            }
        }
        if (arrCitys != null && arrCitys.size() > 0
                && !arrCitys.contains(strCity)) {
            strCity = arrCitys.get(0);
        }
    }

    /**
     * 根据城市，生成该城市的所有地区
     *
     * @param areas
     */
    public void initAreas(String[] areas) {
        if (areas != null) {
            arrAreas.clear();
            int length = areas.length;
            for (int i = 0; i < length; i++) {
                arrAreas.add(areas[i]);
            }
        } else {
            String[] area = mAreaDatasMap.get(strCity);
            arrAreas.clear();
            int length = area.length;
            for (int i = 0; i < length; i++) {
                arrAreas.add(area[i]);
            }
        }
        if (arrAreas != null && arrAreas.size() > 0
                && !arrAreas.contains(strArea)) {
            strArea = arrAreas.get(0);
        }
    }

    /**
     * 初始化地点
     *
     * @param province
     * @param city
     */
    public void setAddress(String province, String city, String area) {
        if (province != null && province.length() > 0) {
            this.strProvince = province;
            wvProvince.setCurrentItem(getProvinceItem(strProvince));
        }
        if (city != null && city.length() > 0) {
            this.strCity = city;
            wvCitys.setCurrentItem(getCityItem(strCity));
        }

        if (area != null && area.length() > 0) {
            this.strArea = area;
            wvArea.setCurrentItem(getAreaItem(strArea));
        }


    }

    /**
     * 返回省会索引，没有就返回默认“广东”
     *
     * @param province
     * @return
     */
    public int getProvinceItem(String province) {
        if (province == null || province.length() < 1) {
            return 0;
        }
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
            strProvince = SharedPreferencesUtils.getString(context, Constants.ADREESS_PROVINCE, "河南省");
            return 0;
        }
        return provinceIndex;
    }

    /**
     * 得到城市索引，没有返回默认“深圳”
     *
     * @param city
     * @return
     */
    public int getCityItem(String city) {
        if (city == null || city.length() < 1) {
            return 0;
        }
        int size = arrCitys.size();
        int cityIndex = 0;
        boolean nocity = true;
        for (int i = 0; i < size; i++) {
            System.out.println(arrCitys.get(i));
            if (city.equals(arrCitys.get(i))) {
                nocity = false;
                return cityIndex;
            } else {
                cityIndex++;
            }
        }
        if (nocity) {
            strCity = SharedPreferencesUtils.getString(context, Constants.ADREESS_CITY, "郑州市");
            return 0;
        }
        return cityIndex;
    }

    /**
     * 得到地区索引，没有返回默认“福田区”
     *
     * @param area
     * @return
     */
    public int getAreaItem(String area) {
        if (area == null || area.length() < 1) {
            return 0;
        }
        int size = arrAreas.size();
        int areaIndex = 0;
        boolean noarea = true;
        for (int i = 0; i < size; i++) {
            System.out.println(arrAreas.get(i));
            if (area.equals(arrAreas.get(i))) {
                noarea = false;
                return areaIndex;
            } else {
                areaIndex++;
            }
        }
        if (noarea) {
            strArea = SharedPreferencesUtils.getString(context, Constants.ADREESS_DISTRICT, "中原区");
            return 0;
        }
        return areaIndex;
    }


}
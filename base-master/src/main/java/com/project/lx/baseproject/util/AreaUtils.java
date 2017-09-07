package com.project.lx.baseproject.util;

import com.project.lx.baseproject.bean.responses.AreaInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2017/6/20 10:57
 * @company:
 * @email: lx802315@163.com
 */
public class AreaUtils {

    List<AreaInfo> allList;

    List<AreaInfo> provinceList = new ArrayList<>();
    List<AreaInfo> cityList = new ArrayList<>();
    List<AreaInfo> areaList = new ArrayList<>();

    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市s
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();

    public String[] getmProvinceDatas() {
        return mProvinceDatas;
    }

    public Map<String, String[]> getmCitisDatasMap() {
        return mCitisDatasMap;
    }

    public Map<String, String[]> getmAreaDatasMap() {
        return mAreaDatasMap;
    }

    /**
     * key - 市 values - 区s

     */
    private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();

    public AreaUtils(List<AreaInfo> list) {
        this.allList = list;
        initData();
    }

    public void initData() {
        for (int i = 0; i < allList.size(); i++) {
            if (allList.get(i).getAreaLevel().equals("1")) {
                provinceList.add(allList.get(i));
            } else if (allList.get(i).getAreaLevel().equals("2")) {
                cityList.add(allList.get(i));
            } else if (allList.get(i).getAreaLevel().equals("3")) {
                areaList.add(allList.get(i));
            }
        }

        initCity();
        initArea();
    }

    public void initCity(){
        mProvinceDatas = new String[provinceList.size()];

        List<String> list = new ArrayList<>();

        for (int i = 0; i < provinceList.size(); i++) {
            mProvinceDatas[i] = provinceList.get(i).getAreaName();

            for (int j = 0; j < cityList.size(); j++) {
                if (cityList.get(j).getFatherId().equals(provinceList.get(i).getId())) {
                    list.add(cityList.get(j).getAreaName());
                }
            }
            LogUtils.print(list.toString());
            String[] cityName = new String[list.size()];

            for (int jj = 0; jj < cityName.length; jj++) {
                cityName[jj] = list.get(jj);
            }
            LogUtils.print(cityName.toString());
            mCitisDatasMap.put(mProvinceDatas[i], cityName);
            list.clear();
            LogUtils.print(mCitisDatasMap.toString());
        }

    }

    public void initArea(){
        List<String> list = new ArrayList<>();

        for (int i = 0; i < cityList.size(); i++) {

            for (int j = 0; j < areaList.size(); j++) {
                if (areaList.get(j).getFatherId().equals(cityList.get(i).getId())) {
                    list.add(areaList.get(j).getAreaName());
                }
            }
            LogUtils.print(list.toString());
            String[] areaName = new String[list.size()];

            for (int jj = 0; jj < areaName.length; jj++) {
                areaName[jj] = list.get(jj);
            }
            LogUtils.print(areaName.toString());
            mAreaDatasMap.put(cityList.get(i).getAreaName(), areaName);
            list.clear();
            LogUtils.print(mAreaDatasMap.toString());
        }
    }
}

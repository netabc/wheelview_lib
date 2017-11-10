package com.imgpng.weathorapp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.imgpng.weathorapp.R;
import com.imgpng.weathorapp.bean.ItemBean;
import com.imgpng.weathorapp.model.NetModel;
import com.imgpng.weathorapp.view.Weather;
import com.imgpng.wheelview.LoopView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by imgpng on 2016/6/12.
 * 获得城市列表
 * 更具城市名获取天气预报
 * 更新天气
 * 更新当前城市
 */
public class WeatherPresenter {
    private Weather mWeather;
    private NetModel mModel;
    private Context mContext;
    private LinkedList<ItemBean> mProvinces;
    private LinkedList<ItemBean> mCitys;
    private LinkedList<ItemBean> mCountys;

    public WeatherPresenter(Weather weather, Context context) {
        this.mContext = context;
        this.mWeather = weather;
        mModel = new NetModel(context);
    }

    @NonNull
    private static ArrayList<String> beanToString(LinkedList<ItemBean> beans) {

        ArrayList<String> dataP = new ArrayList<String>();

        for (ItemBean bean : beans) {
            dataP.add(bean.getName());
        }
        return dataP;
    }

    public void searchWeather(String cityId) {
        if (TextUtils.isEmpty(cityId)) {
            Log.e("Presenter", "city不能为空");
            return;
        }
        String weather = mModel.get(cityId);
        showTus(weather);
    }

    private void showTus(String weather) {
        Toast.makeText(mContext, weather, Toast.LENGTH_SHORT).show();
    }

//    public void selected(final LoopView loopView, int index) {
//
//        if (loopView.getId() == R.id.loopview_province) {//省
//            if (index == 0) {
//                mWeather.loadCity(null);
//                if (mCitys != null) {
//                    mCitys.clear();
//                }
//            } else {
//                ItemBean bean = mProvinces.get(index);
//                mCitys = mModel.getCity(bean, "city");
//                ArrayList<String> datas = beanToString(mCitys);
//                mWeather.loadCity(datas);
//            }
//            mWeather.loadCounty(null);
//
//        } else if (loopView.getId() == R.id.loopview_city) {//市
//            if (index == 0) {
//                if (mCountys != null) {
//                    mWeather.loadCounty(null);
//                    mCountys.clear();
//                }
//            } else {
//                ItemBean bean = mCitys.get(index);
//                mCountys = mModel.getCity(bean, "county");
//                ArrayList<String> datas = beanToString(mCountys);
//                mWeather.loadCounty(datas);
//            }
//
//
//        } else if (loopView.getId() == R.id.loopview_county) {//县
//            ItemBean itemBean = mCountys.get(index);
//            final String cityID = mModel.getCityID(itemBean);
//            if (TextUtils.isEmpty(cityID)) {
//                return;
//            }
//            //请求网络 获取天气预报
//            showWeather(loopView, cityID);
//            Toast.makeText(mContext, "" + itemBean.getName(), Toast.LENGTH_SHORT).show();
//        }
//
//    }

    private void showWeather(final LoopView loopView, final String cityID) {
        new Thread() {
            @Override
            public void run() {
                final String weather = mModel.get(cityID);
                JSONObject jsonObject = null;
                String tT = "22℃";
                String tL = "0℃";
                String w = "晴";
                try {
                    jsonObject = new JSONObject(weather);
                    JSONObject weath = jsonObject.optJSONObject("weatherinfo");
                    if (weath != null) {
                        tT = weath.optString("temp2");//℃
                        tL = weath.optString("temp1");
                        w = weath.optString("weather");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String tT1 = tT;
                final String tL1 = tL;
                final String w1 = w;
                loopView.post(new Runnable() {
                    @Override
                    public void run() {
                        mWeather.update("当前天气：" + w1 + "    最高温度：" + tT1 + "最低温度：" + tL1);
                    }
                });
            }
        }.start();
    }

    /**
     * 添加默认的省
     */
    public void loadDef() {
        mProvinces = mModel.getProvince();
        ArrayList<String> dataP = beanToString(mProvinces);
        mWeather.loadProvince(dataP);
    }

}

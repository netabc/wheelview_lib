package com.imgpng.weathorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.imgpng.weathorapp.presenter.WeatherPresenter;
import com.imgpng.weathorapp.view.Weather;
import com.imgpng.wheelview.LoopView;
import com.imgpng.wheelview.OnItemSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Weather, OnItemSelectedListener {

    private TextView showCityName;
    private TextView tv_showWeather;
    private LoopView loopview_city;
    private LoopView loopview_county;
    private LoopView loopview_province;
    private WeatherPresenter weatherPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showCityName = (TextView) findViewById(R.id.tv_showCityName);
        tv_showWeather = (TextView) findViewById(R.id.tv_showWeather);
        loopview_city = (LoopView) findViewById(R.id.loopview_city);
        loopview_county = (LoopView) findViewById(R.id.loopview_county);
        loopview_province = (LoopView) findViewById(R.id.loopview_province);
        weatherPresenter = new WeatherPresenter(this,this);
        initData();
    }

    private void initData() {
        loopview_province.setNotLoop();
        loopview_province.setListener(this);
        loopview_city.setNotLoop();
        loopview_city.setListener(this);
        loopview_county.setNotLoop();
        loopview_county.setListener(this);
        loopview_city.setInitPosition(0);////设置初始位置
        loopview_county.setInitPosition(0);
        loopview_province.setInitPosition(0);
        weatherPresenter.loadDef();
    }

    @Override
    public void update(String newWeather) {
        tv_showWeather.setText(""+newWeather);
    }

    @Override
    public void loadProvince(ArrayList<String> provinces) {
        loopview_province.setItems(provinces);
//        loopview_province.setSelectedItem(0);
    }

    @Override
    public void loadCity(ArrayList<String> citys) {
        loopview_city.setSelectedItem(0);
        loopview_city.setItems(citys);//设置初始位置
        loopview_city.dataChange();

//        loopview_county.dataChange();
    }

    @Override
    public void loadCounty(ArrayList<String> countys) {
        loopview_county.setItems(countys);
        loopview_county.setSelectedItem(0);
        loopview_county.dataChange();
    }

    @Override
    public void onItemSelected(LoopView loopView,int index) {
        weatherPresenter.selected(loopView,index);
    }
}

package com.imgpng.weathorapp.view;

import java.util.ArrayList;

/**
 * Created by imgpng on 2016/6/12.
 */
public interface Weather {
    public abstract void update(String newWeather);
//    public abstract void search(String city)
    public abstract void loadProvince(ArrayList<String> provinces);//添加省
    public abstract void loadCity(ArrayList<String> city);
    public abstract void loadCounty(ArrayList<String> county);
}

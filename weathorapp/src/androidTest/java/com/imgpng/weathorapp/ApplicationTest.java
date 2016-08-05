package com.imgpng.weathorapp;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
    }
    public void test2() throws Exception {
        Context context = getContext();
//        Weather weather = new Weather() {
//            @Override
//            public void update(String newWeather) {
//                Log.e("sssss","ssssssssssssssssssssssss");
//            }
//        };
//        WeatherPresenter presenter =  new WeatherPresenter(weather,context);
//        presenter.searchWeather("101030100");
    }

}
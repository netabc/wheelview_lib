package com.imgpng.weathorapp.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.imgpng.weathorapp.bean.ItemBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by imgpng on 2016/6/12.
 */
public class NetModel {
    private final Context mContext;
    private OkHttpClient mClient;

    public NetModel(Context context) {
        mContext = context;
        mClient = new OkHttpClient();
    }

    public String get(String cityID) {
//        http://www.weather.com.cn/data/cityinfo/101010100.html
        String url = "http://www.weather.com.cn/data/cityinfo/" + cityID + ".html";
        Request request = new Request.Builder().url(url).build();
        Call newCall = mClient.newCall(request);
        try {
            Response response = newCall.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("网络请求", "get请求出现异常");
        return "{}";
    }

    /**
     *
     * @param item 用户选择的城市
     * @return 返回城市的id
     */
    public String getCityID(ItemBean item) {
        String cityID = "";
        XmlPullParser xmlPullParser = Xml.newPullParser();
        InputStream inputStream = null;
        try {
            inputStream = mContext.getResources().getAssets().open("weatherCity.xml");
            xmlPullParser.setInput(inputStream, "utf-8");
            //获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
            int evtType = xmlPullParser.getEventType();
            //一直循环，直到文档结束
            while (evtType != XmlPullParser.END_DOCUMENT) {
                //如果xml没有结束，则导航到下一个river节点
                String nodeName = xmlPullParser.getName();
                switch (evtType) {
                    //文档开始
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    //开始节点
                    case XmlPullParser.START_TAG:
                        if(!"county".equals(nodeName)){
                            break;
                        }
                        if (item.getId().equals(xmlPullParser.getAttributeValue(0))) {
                            cityID = xmlPullParser.getAttributeValue(2);
                        }
                        break;
                    //结束节点
                    case XmlPullParser.END_TAG:
                        break;
                }
                evtType = xmlPullParser.next();

            }
            inputStream.close();
            xmlPullParser = null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return cityID;
    }

    //省
    public LinkedList<ItemBean> getProvince() {
        LinkedList<ItemBean> items = new LinkedList<ItemBean>();
        items.add(new ItemBean("","--"));
        XmlPullParser xmlPullParser = Xml.newPullParser();
        InputStream inputStream = null;
        try {
            inputStream = mContext.getResources().getAssets().open("weatherCity.xml");
            xmlPullParser.setInput(inputStream, "utf-8");
            //获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
            int evtType = xmlPullParser.getEventType();
            //一直循环，直到文档结束
            while (evtType != XmlPullParser.END_DOCUMENT) {
                //如果xml没有结束，则导航到下一个river节点

                String nodeName = xmlPullParser.getName();
                switch (evtType) {
                    //文档开始
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    //开始节点
                    case XmlPullParser.START_TAG:
                        if ("province".equals(nodeName)) {
                            ItemBean bean = new ItemBean();
                            bean.setId(xmlPullParser.getAttributeValue(0));
                            bean.setName(xmlPullParser.getAttributeValue(1));
                            items.add(bean);
                        }
                        break;
                    //结束节点
                    case XmlPullParser.END_TAG:
                        break;
                }
                evtType = xmlPullParser.next();

            }
            inputStream.close();
            xmlPullParser = null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * @param item  省或者县
     * @param type= city 》 市  ；county 县
     * @return 返回城市列表（县和市）
     */
    public LinkedList<ItemBean> getCity(ItemBean item, String type) {
        LinkedList<ItemBean> items = new LinkedList<ItemBean>();
        items.add(new ItemBean("","--"));
        XmlPullParser xmlPullParser = Xml.newPullParser();
        InputStream inputStream = null;
        try {
            inputStream = mContext.getResources().getAssets().open("weatherCity.xml");
            xmlPullParser.setInput(inputStream, "utf-8");
            //获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
            int evtType = xmlPullParser.getEventType();
            //一直循环，直到文档结束
            while (evtType != XmlPullParser.END_DOCUMENT) {
                //如果xml没有结束，则导航到下一个river节点

                String nodeName = xmlPullParser.getName();
                switch (evtType) {
                    //文档开始
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    //开始节点
                    case XmlPullParser.START_TAG:
                        int count = xmlPullParser.getAttributeCount();
                        if(count<=0){
                            break;
                        }
                        if (TextUtils.isEmpty(xmlPullParser.getAttributeValue(0)) || !xmlPullParser.getAttributeValue(0).startsWith(item.getId())) {
                            break;
                        }
                        if (type.equals(nodeName)) { //根据type获得市或者县数据集合
                            ItemBean bean = new ItemBean();
                            bean.setId(xmlPullParser.getAttributeValue(0));
                            bean.setName(xmlPullParser.getAttributeValue(1));
                            items.add(bean);
                        }
                        break;
                    //结束节点
                    case XmlPullParser.END_TAG:
                        break;
                }
                evtType = xmlPullParser.next();

            }
            inputStream.close();
            xmlPullParser = null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return items;
    }

}

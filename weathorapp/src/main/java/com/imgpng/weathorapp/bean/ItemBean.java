package com.imgpng.weathorapp.bean;

/**
 * Created by imgpng on 2016/6/12.
 */
public class ItemBean {
    public String id;
    public String name;

    public ItemBean() {
    }

    public ItemBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
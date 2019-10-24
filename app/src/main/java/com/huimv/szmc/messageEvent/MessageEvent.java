package com.huimv.szmc.messageEvent;

import java.util.ArrayList;
import java.util.List;

public class MessageEvent<T> {
    private int id;
    private List list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String data;
    private T dataT;
    public MessageEvent() {
    }
    public MessageEvent(int id) {
        this.id = id;
    }

    public T getDataT() {
        return dataT;
    }

    public void setDataT(T dataT) {
        this.dataT = dataT;
    }

    public MessageEvent(int id, List list, String data) {
        this.id = id;
        this.list = list;
        this.data = data;
    }
    public MessageEvent(int id,  String data) {
        this.id = id;
        this.data = data;
    }
    public List getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public MessageEvent(int id, T dataT) {
        this.id = id;
        this.dataT = dataT;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getData() {
        return data == null ? "" : data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

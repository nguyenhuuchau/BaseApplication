package com.cnguyen.baseapp;

/**
 * Created by nc on 5/15/2017.
 */

public class MessageEvent {
    private int mKey;
    private Object mObject;

    public MessageEvent(int key, Object o) {
        this.mKey = key;
        this.mObject = o;
    }

    public int getKey() {
        return mKey;
    }

    public void setKey(int id) {
        this.mKey = id;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object o) {
        this.mObject = o;
    }
}

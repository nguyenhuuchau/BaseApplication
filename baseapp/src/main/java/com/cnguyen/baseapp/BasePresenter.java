package com.cnguyen.baseapp;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;

/**
 * Created by nc on 5/15/2017.
 */

public class BasePresenter{

    public BasePresenter() {
    }

    @Produce
    public void sendEvent(String tag, int key, Object s) {
        RxBus.get().post(tag, new MessageEvent(key, s));
    }
}

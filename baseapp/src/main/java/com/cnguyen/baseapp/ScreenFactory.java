package com.cnguyen.baseapp;

import android.util.SparseArray;

import com.cnguyen.baseapp.annotations.RegisterScreens;
import com.cnguyen.baseapp.annotations.ScreenId;

import java.util.HashMap;

/**
 * Created by nc on 5/15/2017.
 */

public class ScreenFactory {

    private static SparseArray<Class> mScreenMap;

    private ScreenFactory() {
    }

    public static BaseFragment getScreenById(int screenId) {
        try {
            return (BaseFragment) mScreenMap.get(screenId).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void init(Class<?> screenRegisterClazz) {
        mScreenMap = new SparseArray<>();
        if (screenRegisterClazz.isAnnotationPresent(RegisterScreens.class)) {
            RegisterScreens registerScreens = screenRegisterClazz
                    .getAnnotation(RegisterScreens.class);
            for (Class<?> clz : registerScreens.screens()) {
                if (clz.isAnnotationPresent(ScreenId.class)) {
                    ScreenId screenId = clz.getAnnotation(ScreenId.class);
                    mScreenMap.put(screenId.id(), clz);
                }
            }
        }
    }

}

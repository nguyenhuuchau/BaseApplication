package com.cnguyen.baseapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnguyen.baseapp.annotations.ScreenId;
import com.hwangjr.rxbus.RxBus;

/**
 * Created by nc on 5/15/2017.
 */

public abstract class BaseFragment extends Fragment {

    public static final int RESULT_OK = 0;

    public static final int RESULT_CANCELED = 1;


    public static final String TAG = BaseFragment.class.getCanonicalName();

    private BasePresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = initView(inflater, container, savedInstanceState);
        registerViewEventListeners();
        return v;
    }

    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState);

    protected abstract void registerViewEventListeners();

    protected abstract void onHandleMessages(MessageEvent message);

    protected void transitionToScreen(int screenId, Bundle bundle) {
        ((BaseActivity) getActivity()).transitionToScreen(screenId, bundle);
    }

    protected void transitionToScreen(int screenId) {
        transitionToScreen(screenId, null);
    }

    protected void transitionToScreenForResult(int screenId, int requestCode, Bundle bundle) {
        ((BaseActivity) getActivity())
                .transitionToScreenForResult(this, screenId, requestCode, bundle);
    }

    protected abstract BasePresenter onCreatePresenter();

    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter = null;
        RxBus.get().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        RxBus.get().register(this);
        mPresenter = onCreatePresenter();
        ScreenResultManager.ScreenResult result = ScreenResultManager
                .getInstance()
                .requestResult(this);
        if (result != null) {
            onScreenResult(result.getRequestCode(),
                    result.getResultCode(),
                    result.getResult());
        }
    }

    protected void setScreenResult(Bundle result) {
        ScreenResultManager.getInstance().setResult((BaseFragment) getTargetFragment(),
                getTargetRequestCode(),
                RESULT_OK,
                result);
        getFragmentManager().popBackStack();
    }

    public void onScreenResult(int requestCode, int resultCode, Bundle data) {
    }

    public int getScreenId() {
        if (this.getClass().isAnnotationPresent(ScreenId.class)) {
            ScreenId screenId = this.getClass().getAnnotation(ScreenId.class);
            return screenId.id();
        }
        return -1;
    }
}
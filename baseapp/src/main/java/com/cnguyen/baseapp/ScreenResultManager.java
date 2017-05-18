package com.cnguyen.baseapp;

import android.os.Bundle;

import java.util.Stack;

/**
 * Created by nc on 5/18/2017.
 */

public class ScreenResultManager {

    private Stack<ScreenResult> mResultStack;

    private static final ScreenResultManager mInstance = new ScreenResultManager();

    private ScreenResultManager() {
    }

    public static ScreenResultManager getInstance() {
        return mInstance;
    }

    public void setResult(BaseFragment mTargetFragment,
                          int mRequestCode,
                          int mResultCode,
                          Bundle mResult) {
        if (mResultStack == null) {
            mResultStack = new Stack<>();
        }
        mResultStack.push(new ScreenResult(mTargetFragment, mRequestCode, mResultCode, mResult));
    }

    public ScreenResult requestResult(BaseFragment fragment) {
        if (mResultStack != null && !mResultStack.isEmpty()) {
            ScreenResult result = mResultStack.peek();
            if (fragment == result.getTargetFragment()) {
                return mResultStack.pop();
            } else {
                return null;
            }
        }
        return null;
    }

    public void clearAllResult() {
        if (mResultStack != null) {
            mResultStack.clear();
            mResultStack = null;
        }
    }

    public static class ScreenResult {
        private BaseFragment mTargetFragment;
        private int mRequestCode;
        private int mResultCode;
        private Bundle mResult;

        public ScreenResult(BaseFragment mTargetFragment,
                            int mRequestCode,
                            int mResultCode, Bundle mResult) {
            this.mTargetFragment = mTargetFragment;
            this.mRequestCode = mRequestCode;
            this.mResultCode = mResultCode;
            this.mResult = mResult;
        }

        public BaseFragment getTargetFragment() {
            return mTargetFragment;
        }

        public int getRequestCode() {
            return mRequestCode;
        }

        public int getResultCode() {
            return mResultCode;
        }

        public Bundle getResult() {
            return mResult;
        }
    }

}

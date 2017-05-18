package com.cnguyen.baseapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nc on 5/17/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayout());
    }


    /**
     * getLayoutId(): get layout for main activity with ViewGroup holder for fragments.
     * @return return layout id.
     */
    protected abstract int getLayout();

    /**
     * getFragmentHolder(): get fragment holder ViewGroup.
     * @return ViewGroup id.
     */
    protected abstract int getFragmentHolder();

    public void transitionToScreen(int screenId, Bundle bundle) {
        BaseFragment fragment = ScreenFactory.getScreenById(screenId);
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment.setArguments(bundle);
            transaction.addToBackStack(fragment.getTag());
            transaction.replace(getFragmentHolder(), fragment);
            transaction.commit();
        }
    }

    public void transitionToScreen(int screenId) {
        transitionToScreen(screenId, null);
    }

    public void transitionToScreenForResult(BaseFragment targetFragment,
                                            int screenId,
                                            int requestCode,
                                            Bundle bundle) {
        BaseFragment fragment = ScreenFactory.getScreenById(screenId);
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment.setArguments(bundle);
            fragment.setTargetFragment(targetFragment, requestCode);
            transaction.addToBackStack(fragment.getTag());
            transaction.replace(getFragmentHolder(), fragment);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenResultManager.getInstance().clearAllResult();
    }
}

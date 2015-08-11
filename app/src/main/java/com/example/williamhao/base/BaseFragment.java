package com.example.williamhao.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity ;
    protected Bundle mBundle ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity)getActivity();
        mBundle = getArguments();
    }

    public void ShowToast(final String text) {
        mActivity.ShowToast(text);
    }

}


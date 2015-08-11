package com.example.williamhao.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.williamhao.opensourceusage.Constant;
import com.example.williamhao.opensourceusage.MyApplication;
import com.example.williamhao.utils.SpUtils;



public class BaseActivity extends FragmentActivity implements OnSharedPreferenceChangeListener {

    protected static String mTAG;

    protected MyApplication mMyApplication;
    protected SpUtils mSpUtils;
    protected Resources mResources;
    protected Context mContext;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mTAG = this.getClass().getSimpleName();
        initConfigure();
    }


    private void initConfigure() {
        mContext = this;
        mMyApplication = MyApplication.getInstance();
        mMyApplication.addActivity(this);
        if (null == mSpUtils) {
            mSpUtils = new SpUtils(this, Constant.Sp.PRE_NAME);
        }
        mSpUtils.getInstance().registerOnSharedPreferenceChangeListener(this);
        mResources = getResources();

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        //可用于监听设置参数，然后作出响应
    }

    /**
     * Activity跳转
     *
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public void redirectToActivity(Context context, Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Activity跳转
     *
     * @param context
     * @param targetActivity
     */
    public void redirectToActivity(Context context, Class<?> targetActivity) {
        redirectToActivity(context, targetActivity, null);
    }

    /**
     * 加载完毕隐藏progressbar
     */
    public void hideLoadingBar() {

    }

    public void showLoadingBar() {

    }

    /**
     * 有结果隐藏空提示
     */
    public void hideEmptyTips() {

    }

    /**
     * 无结果显示空提示
     */
    public void showEmptyTips() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    Toast mToast;

    public void ShowToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text,
                                Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });

        }
    }

    public SpUtils getmSpUtils() {
        if (mSpUtils == null) {
            mSpUtils = new SpUtils(this, Constant.Sp.PRE_NAME);
        }
        return mSpUtils;
    }

}

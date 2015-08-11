package com.example.williamhao.opensourceusage;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.example.williamhao.utils.ActivityManagerUtils;

public class MyApplication extends Application{

    public static String TAG;

    private static MyApplication myApplication = null;

    public static MyApplication getInstance(){
        if(null == myApplication){
            myApplication = new MyApplication();
        }
        return myApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        TAG = this.getClass().getSimpleName();
    }

    /**
     * 获得当前进程号
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    public void addActivity(Activity ac){
        ActivityManagerUtils.getInstance().addActivity(ac);
    }

    public void exit(){
        ActivityManagerUtils.getInstance().removeAllActivity();
    }

    public Activity getTopActivity(){
        return ActivityManagerUtils.getInstance().getTopActivity();
    }


}

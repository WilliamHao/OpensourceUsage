package com.example.williamhao.listener;

/**
 * 用于主页面缓存信息
 * Created by liujie on 15/8/2.
 */
public interface CacheListener {
//    public static final int ERROR_STATE_RESULT_0 = 999;   //返回零个数据
//    public static final int ERROR_STATE_RESULT_ERROR = 888; //请求错误
    public void startCache(OnCacheSuccessfullyListener successListener, OnCacheErrorListener errorListener);

    interface OnCacheSuccessfullyListener{
        public void onCacheSuccessfully();
    }
    interface OnCacheErrorListener{
        public void onCacheError();
    }
}

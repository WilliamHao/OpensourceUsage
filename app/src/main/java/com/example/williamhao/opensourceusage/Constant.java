package com.example.williamhao.opensourceusage;

import android.os.Environment;

import java.io.File;

public class Constant {

    public static class Config {
        public static final boolean ISDEBUG = Boolean.parseBoolean("true");
        public static final boolean ISSHOWTOAST = Boolean.parseBoolean("true");
        public static final String TAG = "tag";

        // 高德地图   以及   友盟appkey 在 mainifest 文件
        // 融云 appkey 在 IMKIT mainifest

        public static final String BMOB_APP_ID = "6b19f618e14989113ec07998a283ac42";

        //新的接口
        public static final String POST_GET_STREAM_URL = "http://112.74.204.210/stream?title=";
        public static final String POST_GET_STREAM_SUFFIX = "&publishKey=publishKey&publishSecurity=dynamic";
        public static final String  POST_GET_RONGTOKEN_URL= "http://112.74.204.210/RCToken?userId=";


        public static final String ZHIBO_URL="http://www.zhiboeasy.com/";
        public static final String QINIU_URL="http://7xjny2.com1.z0.glb.clouddn.com/";
        public static final String QIQIU_TOKEN_URL="http://203.195.221.117:8090/getToken";

        public static final String WX_APP_ID="wx9c50dcea33ae3bfb";

        public static final String WEIBO_REDIRECT_URL ="https://api.weibo.com/oauth2/default.html";
        public static final String WEIBO_APP_KEY="2747468016";

        public static final String STATEMENT_IMAGE_URI="http://7xjny2.com1.z0.glb.clouddn.com/";
    }

    public static class Count {
        public static final int LIST_ITEM_COUNT = 8;
        public static final int IO_BUFFER_SIZE = 4 * 1024;


        public static final int LIST_MAX_MSG_COUNT = 100;

        //封面尺寸
        public static final int UPLOAD_AVATAR_SIZE = 128;

        //表情大小  dp
        public static final int EMOTICONS_SIZE_DP = 27;

        public static final int THUMB_SIZE = 150;

        //第三方应用的包名
        public static final String WEIXIN_PNAME = "com.tencent.mm";
        public static final String WEIBO_PNAME = "com.sina.weibo";
    }

    public static class Data {
        //短信接口地址
        public static final String VERIFY_INTERFACE_URL = "http://120.24.158.159:8090/sendSMS/";
        //再次发送短信间隔
        public static final int VERIFY_INTERVAL = 70;

    }


    public static class Sp {
        //应用总的 SharedPreference key
        public static final String PRE_NAME = "opensos_pre";
    }

    public static class Path {
        public static final String SDCardRoot = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + File.separator;
        //默认的文件夹名称
        public static final String DEFAULT_DIR_NAME = "openSos";
        public static final String DIR_WITH_SEPARATE = DEFAULT_DIR_NAME + File.separator;
        public static final String DIR_WITHOUT_SEPARATE = DEFAULT_DIR_NAME;
        public static final String COMPLETE_PATH = SDCardRoot + DIR_WITH_SEPARATE;


    }


}

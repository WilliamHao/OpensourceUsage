package com.example.williamhao.opensourceusage;

import android.os.Environment;

import java.io.File;

//test branch

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
        //用户默认密码
        public static final String DEFAULT_PASS = "wec";

        public static final String SILENT = "全体禁言";
        public static final String NOT_SILENT = "全体解禁";

    }


    public static class Sp {
        //应用总的 SharedPreference key
        public static final String PRE_NAME = "wecourse_pre";
    }

    public static class Path {
        public static final String SDCardRoot = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + File.separator;
        //默认的文件夹名称
        public static final String DEFAULT_DIR_NAME = "wecourse";
        public static final String DIR_WITH_SEPARATE = DEFAULT_DIR_NAME + File.separator;
        public static final String DIR_WITHOUT_SEPARATE = DEFAULT_DIR_NAME;
        public static final String COMPLETE_PATH = SDCardRoot + DIR_WITH_SEPARATE;


    }

    public static class KeyValue {

        public static final String VIEWPAGER_PHOTOS_POSITION_KEY = "viewpager_photos_position_key";
        public static final String VIEWPAGER_PHOTOS_KEY = "viewpager_photos_key";

        public static final String TYPE_KEY = "type_key";
        public static final String DATA_KEY = "data_key";
        public static final String USER_KEY = "user_key";
        public static final String TITLE_KEY = "title_key";

        public static final String IMAGE_PICKER_SINGLE = "image_picker_single";
        public static final String IMAGE_PICKER_MULTI = "image_picker_multi";

        public static final String SHARE_FLAG = "flag";
        public static final String CHAT_OR_CIRCLE ="chat_or_circle" ;

        public static final String USER_NAME = "user_name";
        public static final String GENDER = "gender";
        public static final String DETAILS = "details";

        public static final String ROOM_MODIFI = "room_modifi";
        public static final String ROOM_NAME = "room_name";
        public static final String ROOM_Detail="room_detail";

        public static final String PAGE_TYPE="page_type";
    }


    public static class CODE {

        //modify_type
        public static final int MODIFY_LOGIN = 0;
        public static final int MODIFY_OTHER = 1;

        public static final int MODIFY_USERNAME = 3;
        public static final int MODIFY_GENDER = 4;
        public static final int MODIFY_DETAILS = 5;

        public static final int MODIFY_ROOMNAME=6;
        public static final int MODIFY_ROOMDETIAL=7;


        //gender type
        public static final int MALE = 0;
        public static final int FEMALE = 1;

        //room type
        public static final int ROOM_PD = 0;
        public static final int ROOM_SXY = 1;


        //intent action
        public static final int PICK_FROM_CAMERA = 0;
        public static final int PICK_FROM_FILE = 1;
        public static final int ACTION_CROP = 2;

        //list type
        public static final int LIST_HOME_ROOM = 0;
        public static final int LIST_SBC_ROOM = 1;      // discard, use LIST_SBC_NORMAL_ROOM and LIST_SBC_SXY
        public static final int LIST_MEMBER = 2;
        public static final int LIST_PROGRAM_PRIVATE = 3;
        public static final int LIST_PROGRAM_PUBLIC = 4;
        public static final int LIST_MSG = 5;
        public static final int LIST_SXY_ROOM = 6;
        public static final int LIST_FORESHOW = 7;
        public static final int LIST_MY_ROOM = 8;
        public static final int LIST_SBC_ROOM_PD = 9;
        public static final int LIST_SBC_SXY = 10;
        public static final int LIST_SEARCH_MY= 11;
        public static final int LIST_BLACKLIST=12;
        public static final int LIST_SERACH_SXY=13;


        //user_type
        public static final int USER_NORMAL = 0;
        public static final int USER_MANAGER = 1;
        public static final int USER_ADMIN = 2;
        public static final int USER_BLACK = 3;

        //group type
        public static final int Group_500 = 2;
        public static final int Group_NO_PERMISSION = 0;

        //member
        public static final int MEMBER_CODE = 0;
        public static final int TO_RPA = 1;
        public static final int GEN_CODE = 3;

        //share
        public static final int WCHAT = 0;
        public static final int WEIBO = 1;
        public static final int NO_SHARE =11 ;


        public static boolean toMarket=false;

        public static boolean SILENT=true;
        public static boolean NOT_SILENT=false;

    }

    /**
     *发送融云的消息类型
     * https://trello.com/c/QOA9YZit/10-ios-android
     */
    public static class RongMessageType{

        public static final int COMMON = 0;//普通消息

        public static final int PPT_ADD=10;//增加幻灯片(注意分隔符)
        public static final int PPT_DEL=11;//删除PPT
        public static final int PPT_PLAY=12;//PPT翻页
        public static final int OPEN_LIVING=20;//开启直播
        public static final int CLOSE_LIVING=21;//关闭直播
        public static final int NON_ALL_SPEAK=40;//开启全体禁言
        public static final int ALLOW_ALL_SPEAK=41;//解除全体禁言

        public static final int ON_LINE_COUNT=30;//在线人数
        public static final int NOT_PERSON_SPEAK=50;//开启个人禁言
        public static final int ALLOW_PERSON_SPEAK=51;//解除个人禁言
        public static final int SET_MANAGER=60;//设置管理员
        public static final int REMOVE_MANAGER=61;//解除管理员
    }
}

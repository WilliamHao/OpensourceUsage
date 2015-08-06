package com.example.williamhao.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 *
 */
public class OtherUtils {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static String getTimeStringByDate(String date) {
        String result;
        Date commentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            commentTime = sdf.parse(date);
        } catch (ParseException e) {
            LogUtils.e("解析日期出错:" + date);
            e.printStackTrace();
        }
        long dayBefore = (System.currentTimeMillis() - commentTime.getTime()) / (1000 * 60 * 60 * 24);
        long hourBefore = (System.currentTimeMillis() - commentTime.getTime()) / (1000 * 60 * 60);
        long minBefore = (System.currentTimeMillis() - commentTime.getTime()) / (1000 * 60) + 4;

        if (minBefore < 60) {
            result = minBefore + "分钟前";
        } else if (hourBefore < 24) {
            result = hourBefore + "小时前";
        } else {
            result = dayBefore + "天前";
        }

        return result;

    }


    /**
     * 产生一个四位随机数
     *
     * @return
     */
    public static int getRandomVerifyCode() {
        return Integer.parseInt(((Math.floor(Math.random() * 10000) + 1000) + "").substring(0, 4));
    }

    /**
     * 获取接下来3天的日期
     *
     * @return
     */
    public static List<String> get5DatesAfterToday() {
        List<String> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        result.add(df.format(calendar.getTime()));

        for (int i = 0; i < 2; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);//让日期加1
            result.add(df.format(calendar.getTime()));
            //LogUtils.i(result.get(i));
        }
        return result;

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static String second2format(int second) {
        String hh = "";
        String mm = "";
        String ss = "";

        int ih = second / 3600;
        if (ih < 10) hh = "0" + ih;
        else hh = ih + "";

        int im = (second / 60) % 60;
        if (im < 10) mm = "0" + im;
        else mm = im + "";


        int is = second % 60;
        if (is < 10) ss = "0" + is;
        else ss = is + "";

        return hh + " : " + mm + " : " + ss;
    }

    public static InputStream getImageFromUrl(String url) {
        InputStream is = null;
        try {
            URL Url = new URL(url);
            is = Url.openStream();
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }
        return is;
    }

    public static Uri getImageUriFromLocal(String path, String fileName) {
        File file = new File(path + "/" + fileName);
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            return uri;
        } else
            return null;
    }

    public static String md5(String string) {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            //throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            //throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * @param context
     * @param packageName 要判断的应用的包名
     * @return
     */
    public static boolean isThirdpartInstalled(Context context, String packageName,boolean toMarket) {
        List<String> pName = getAllPName(context);//用于存储所有已安装程序的包名
        boolean hasPName=pName.contains(packageName);
        if (hasPName) {
            return true;
        }else{
            if(toMarket&&isMarketInstalled(context,openAppMarketIntent(packageName))) {
                context.startActivity(openAppMarketIntent(packageName));
            }
            return false;
        }
    }

    //获取手机上安装的所有包名
    public static List<String> getAllPName(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        //获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名
        //从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName;
    }

    /**
     * 检测所需第三方软件是否安装
     *
     * @param context
     * @param intent
     * @param packageName
     * @return
     */
    public static boolean isIntentCanbeResolved(Context context, Intent intent, String packageName,boolean toMarket) {
        PackageManager pm = context.getPackageManager();
        ComponentName cn = intent.resolveActivity(pm);
        if (cn == null) {
            Intent marketIntent = openAppMarketIntent(packageName);
            if (toMarket&&isMarketInstalled(context,marketIntent)) {
                context.startActivity(marketIntent);
            }
            return false;
        }else{
            return true;
        }
    }

    public static Intent openAppMarketIntent(String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);//id为包名
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        return intent;
    }

    public static boolean isMarketInstalled(Context context,Intent marketIntent){
        PackageManager pm = context.getPackageManager();
        ComponentName cn = marketIntent.resolveActivity(pm);
        if(cn!=null){
            return true;
        }
        return false;
    }
    public static Bitmap convertToBlur(Bitmap bmp) {//高斯模糊（不是很好）

//        int w = bmp.getWidth(); // 得到图片的宽，高
//        int h = bmp.getHeight();
//        bmp= Bitmap.createBitmap(bmp, w/5, w/5, w/5*3, w/5*3, null, false);

        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap newBmp = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixColor = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int delta = 16; // 值越小图片会越亮，越大则越暗
        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);
                        newR = newR + pixR * gauss[idx];
                        newG = newG + pixG * gauss[idx];
                        newB = newB + pixB * gauss[idx];
                        idx++;
                    }
                }
                newR /= delta;
                newG /= delta;
                newB /= delta;
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBmp;
    }

    public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {//毛玻璃效果


        Bitmap bitmap;
        try{
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }catch (Exception e){
            bitmap=sentBitmap;
        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
//        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int temp = 256 * divsum;
        int dv[] = new int[temp];
        for (i = 0; i < temp; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

//        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }


}
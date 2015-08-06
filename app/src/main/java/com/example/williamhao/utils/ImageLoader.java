package com.example.williamhao.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chuxin on 7/1/15.
 */
public class ImageLoader {

    private static HashMap<String, SoftReference<Bitmap>> cache;
    private static ExecutorService es;

    public ImageLoader() {
    }

    static {
        if (cache == null)
            cache = new HashMap<String, SoftReference<Bitmap>>();
        if (es == null || es.isTerminated() || es.isShutdown())
            es = Executors.newFixedThreadPool(5);
    }

    public void getImage(final String uri, final ImageCallBack callBack) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                callBack.loadImage((Bitmap) msg.obj, uri);
            }
        };


        if (cache.get(uri) != null) {
            Bitmap bm = cache.get(uri).get();
            if (bm != null) {
                Message msg = handler.obtainMessage(0, bm);
                handler.sendMessage(msg);
            } else es.submit(new Runnable() {
                @Override
                public void run() {
                    final BitmapFactory.Options option = new BitmapFactory.Options();
                    option.inSampleSize = getImageScale(uri);
                    Bitmap bm = BitmapFactory.decodeFile(uri, option);
                    cache.put(uri, new SoftReference<Bitmap>(bm));
                    Message msg = handler.obtainMessage(0, bm);
                    handler.sendMessage(msg);

                }
            });
        } else es.submit(new Runnable() {
            @Override
            public void run() {
                final BitmapFactory.Options option = new BitmapFactory.Options();
                option.inSampleSize = getImageScale(uri);
                Bitmap bm = BitmapFactory.decodeFile(uri, option);
                cache.put(uri, new SoftReference<Bitmap>(bm));
                Message msg = handler.obtainMessage(0, bm);
                handler.sendMessage(msg);

            }
        });
    }

    private int getImageScale(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, option);

        int scale = 1;
        while (option.outWidth / scale >= 320 || option.outHeight / scale >= 640) {
            scale *= 2;
        }
        return scale;
    }

    public void destroyExecutors() {
        if (es != null) {
            es.shutdown();
        }
    }

    public interface ImageCallBack {
        public void loadImage(Bitmap bm, String uri);
    }
}

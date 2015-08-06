package com.example.williamhao.opensourceusage;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by williamhao on 8/5/15.
 */
public class AudioIconUtil {
    private static final String TAG = "AudioIconUtil";
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    AudioRecord mAudioRecord;
    boolean isGetVoiceRun;
    Object mLock;
    private Handler mHandler;
    private static final int CONSTANT_WHAT = 123;
    private static final String CONSTANT_BUNDLE = "int";
    private ImageView mImageView;
    private boolean imageViewTag;//初始化组件时用于判断传入的ImageView是否正确

    public AudioIconUtil(ImageView imageView) {
        mLock = new Object();
        imageViewTag = false;
        if(imageView instanceof ImageView) {
            mImageView = imageView;
            imageViewTag = true;
        }
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == CONSTANT_WHAT && imageViewTag) {
                    int volume = msg.getData().getInt(CONSTANT_BUNDLE, 0);
                    mImageView.setImageLevel(volume);
                }
            }
        };
    }

    public void begin() {
        if (isGetVoiceRun) {
            Log.e(TAG, "还在录着呢");
            return;
        }
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        if (mAudioRecord == null) {
            Log.e("sound", "mAudioRecord初始化失败");
        }
        isGetVoiceRun = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                short[] buffer = new short[BUFFER_SIZE];
                while (isGetVoiceRun) {
                    //r是实际读取的数据长度，一般而言r会小于buffersize
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (int i = 0; i < buffer.length; i++) {
                        v += buffer[i] * buffer[i];
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    double volume = 10 * Math.log10(mean);
                    // 如果想利用这个数值进行操作，建议用 sendMessage 将其抛出，在 Handler 里进行处理。
                    Log.d(TAG, "分贝值:" + volume);
                    Message msg = Message.obtain();
                    msg.what = CONSTANT_WHAT;
                    Bundle bundle = new Bundle();
                    bundle.putInt(CONSTANT_BUNDLE,(int)volume);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                    // 大概一秒十次
                    synchronized (mLock) {
                        try {
                            mLock.wait(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
        }).start();
    }

    public void stop(){
        isGetVoiceRun = false;
    }
}

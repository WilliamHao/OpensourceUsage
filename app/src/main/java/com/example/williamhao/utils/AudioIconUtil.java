package com.example.williamhao.utils;

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
    static int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    AudioRecord mAudioRecord;
    boolean isGetVoiceRun;
    Object mLock;
    Object mLock2;
    private Handler mHandler;
    private static final int CONSTANT_WHAT = 123;
    private static final String CONSTANT_BUNDLE = "int";
    private ImageView mImageView;
    private boolean imageViewTag;//初始化组件时用于判断传入的ImageView是否正确

    public AudioIconUtil(ImageView imageView) {
        mLock = new Object();
        mLock2 = new Object();
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

    private static int[] mSampleRates = new int[] { 8000, 11025, 22050, 44100 };
    public AudioRecord findAudioRecord() {
        for (int rate : mSampleRates) {
            for (short audioFormat : new short[] { AudioFormat.ENCODING_PCM_16BIT, AudioFormat.ENCODING_PCM_8BIT }) {
                for (short channelConfig : new short[] { AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO }) {
                    try {
                        LogUtils.d(TAG, "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
                                + channelConfig);
                        BUFFER_SIZE = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                        if (BUFFER_SIZE != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, rate, channelConfig, audioFormat, BUFFER_SIZE);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                return recorder;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.e(TAG, rate + "Exception, keep trying.");
                    }
                }
            }
        }
        return null;
    }


    public void begin() {
        if (isGetVoiceRun) {
            LogUtils.e(TAG, "还在录着呢");
            return;
        }

        mAudioRecord = findAudioRecord();
        if (mAudioRecord == null) {
            LogUtils.e("sound", "mAudioRecord初始化失败");
            return;
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
                    if(r == mAudioRecord.ERROR_INVALID_OPERATION){
                        LogUtils.d(TAG, "Audio read error: invalid operation");
                        isGetVoiceRun = false;
                        r = BUFFER_SIZE/2;

                    }else if(r == mAudioRecord.ERROR_BAD_VALUE){
                        LogUtils.d(TAG, "Audio read error: bad value");
                        isGetVoiceRun = false;
                        r = BUFFER_SIZE/2;
                    }

                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (int i = 0; i < buffer.length; i++) {
                        v += buffer[i] * buffer[i];
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    if(mean <= 0.0) mean = 1;
                    double volume = 10 * Math.log10(mean);
                    Log.d(TAG, "分贝值1:" + volume + ">>" + v + ">>" + r + ">>" + Math.log10(mean));
                    Message msg = Message.obtain();
                    msg.what = CONSTANT_WHAT;
                    Bundle bundle = new Bundle();
                    bundle.putInt(CONSTANT_BUNDLE,(int)volume);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                    // 大概一秒五次
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
                //mAudioRecord = null;
                //归位0
                Message msg = Message.obtain();
                msg.what = CONSTANT_WHAT;
                Bundle bundle = new Bundle();
                bundle.putInt(CONSTANT_BUNDLE, 0);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        }).start();

    }

    public void stop(){
        isGetVoiceRun = false;
    }
}

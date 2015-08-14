package com.example.williamhao.opensourceusage;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamhao.utils.AudioIconUtil;
import com.example.williamhao.utils.LogUtils;
import com.example.williamhao.utils.MediaRecordUtil;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

import java.io.File;
import java.io.IOException;

/**
 * Created by williamhao on 8/5/15.
 */
public class AudioIconActivity2 extends KJActivity{

    @Override
    public void setRootView(){
        setContentView(R.layout.audio_icon);
    }
    @BindView(id = R.id.button1, click = true)
    private Button btn1;
    @BindView(id = R.id.button2, click = true)
    private Button btn2;
    @BindView(id = R.id.image)
    private ImageView image;
    @BindView(id = R.id.image2)
    private ImageView image2;
    private TextView text;
    private AudioIconUtil adio;
    private MediaRecordUtil medio;

    @Override
    public void initData(){
        adio = new AudioIconUtil(image);
        try {
            File file = new File(Environment.getExternalStorageDirectory().getCanonicalFile() + "/lulu.amr");
            LogUtils.e("lulu",file.exists()+"");
            medio = new MediaRecordUtil(file,image2);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void widgetClick(View v){
        super.widgetClick(v);
        switch(v.getId()){
            case R.id.button1 :
                open();
                break;
            case R.id.button2 :
                close();
                break;

        }
    }

    private void open(){
        adio.begin();
        medio.startRecord();
    }

    private void close(){
        adio.stop();
        medio.stopRecord();
    }
}

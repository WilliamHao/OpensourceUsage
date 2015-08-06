package com.example.williamhao.opensourceusage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamhao.utils.AudioIconUtil;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

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
    @BindView(id = R.id.text)
    private TextView text;
    private Handler mHandler;
    private Thread mThread;
    private Context mContext;
    private AudioIconUtil adio;

    @Override
    public void initData(){
        mContext = this;
        adio = new AudioIconUtil(image);
        //image.setImageLevel(2);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 123){
                    int audio = msg.getData().getInt("int");
                    text.setText(String.valueOf(audio));
                    if(audio>60){
                        Toast.makeText(mContext, audio + "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
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
    }

    private void close(){
        adio.stop();
    }
}

package com.example.williamhao.opensourceusage;

import org.kymjs.kjframe.KJActivity;


public class MainActivity extends KJActivity {

    @Override
    public void setRootView(){
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData(){
        showActivity(this, AudioIconActivity2.class);
    }

}

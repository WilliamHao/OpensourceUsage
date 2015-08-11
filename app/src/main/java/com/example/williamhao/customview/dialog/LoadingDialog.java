package com.example.williamhao.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;


public class LoadingDialog extends Dialog{
    TextView textView;

    public static LoadingDialog newInstanceShow(Context viewHolderContext,boolean showProgress){

        LoadingDialog dialog = new LoadingDialog(viewHolderContext, 0);
/*        if(!showProgress) dialog.findViewById(R.id.progress_ll).setVisibility(View.GONE);
        dialog.show();*/

        return dialog;
    }

    //默认不显示百分比
    public static LoadingDialog newInstanceShow(Context viewHolderContext){
        return LoadingDialog.newInstanceShow(viewHolderContext,false);
    }


    public LoadingDialog(Context context, int layout_res_id) {
        super(context,layout_res_id);

    }

    public void setProgress(int percent){
        textView.setText(percent+"");
    }

}

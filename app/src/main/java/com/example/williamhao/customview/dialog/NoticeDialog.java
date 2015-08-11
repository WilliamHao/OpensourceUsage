package com.example.williamhao.customview.dialog;

import android.app.Dialog;
import android.content.Context;


public class NoticeDialog extends Dialog{
    OnSureListener onSureListener = null;

    public static NoticeDialog newInstanceShow(Context viewHolderContext,String msg){
        return newInstanceShow(viewHolderContext,msg,false,null);
    }

    public static NoticeDialog newInstanceShow(Context viewHolderContext,String msg,boolean showCancelBtn,OnSureListener onSureListener){

        NoticeDialog dialog = new NoticeDialog(viewHolderContext, 0);
        dialog.setMsg(msg);
        dialog.setOnSureListener(onSureListener);
        if(showCancelBtn) dialog.showCancelBtn();
        dialog.show();

        return dialog;
    }


    public NoticeDialog(Context context, int layout_res_id) {
        super(context, layout_res_id);

    }

    public void setMsg(String msg) {
    }

    private void showCancelBtn(){

    }


    public interface OnSureListener{
        public void onSure();
    }

    public void setOnSureListener(OnSureListener onSureListener) {
        this.onSureListener = onSureListener;
    }
}

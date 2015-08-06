package com.example.williamhao.utils;

import android.util.Log;

import com.bao.wec.app.Constant;
import com.bao.wec.ui.base.BaseActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HTTPUtils {

    public static  final String RestHeader_ContentType_key = "Content-Type";
    public static  final String RestHeader_ContentType_value = "application/json;";
    public static  final String RestHeader_Authorization_key = "Authorization";
    public static  final String RestHeader_Authorization_value_prefix = "Bearer ";

    public static  final String RestOp_GetToken = "token";
    public static  final String RestOp_AddUser = "users";
    public static  final String RestOp_AddChatRoom = "chatrooms";



    public static void postGetJson( JSONObject jsonObject, String op,  boolean needToken, SpUtils spUtils ,final OnPostReturnListener onPostReturnListener ){
        try {
            final DefaultHttpClient httpClient = new DefaultHttpClient();
            final HttpPost httpPost = new HttpPost(Constant.Config.EASE_REST_URL + op);
            httpPost.addHeader(HTTPUtils.RestHeader_ContentType_key, HTTPUtils.RestHeader_ContentType_value);

            if(needToken && spUtils != null){
                String token = spUtils.getValue("token", "");
                httpPost.addHeader(HTTPUtils.RestHeader_Authorization_key, HTTPUtils.RestHeader_Authorization_value_prefix + token);
            }

            StringEntity se = new StringEntity(jsonObject.toString());
            httpPost.setEntity(se);
            LogUtils.i("httpPost.getEntity -- >" + InputStreamUtils.InputStreamTOString(httpPost.getEntity().getContent()));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        httpClient.execute(httpPost,new ResponseHandler<Object>() {
                            @Override
                            public Object handleResponse(HttpResponse httpResponse) throws IOException {
                                try {
                                    String jsonString = InputStreamUtils.InputStreamTOString(httpResponse.getEntity().getContent());
                                    LogUtils.i("httpResponse.getEntity -- >" + jsonString);
                                    final JSONObject jsonObject = new JSONObject(jsonString);
                                    //如果token过期则提示
                                    if(jsonObject.has("error") && (jsonObject.getString("error") == "unauthorized" || jsonObject.getString("error").equals("unauthorized"))){

                                    }else {
                                        if(onPostReturnListener != null) onPostReturnListener.onResponse(jsonObject);
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                    LogUtils.i(e.getLocalizedMessage());
                                }
                                return null;
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                        LogUtils.i(e.getLocalizedMessage());
                    }
                }
            });
            thread.start();

        }catch (Exception e){
            e.printStackTrace();
            LogUtils.i(e.getLocalizedMessage());
        }

    }

    public static void postGetJson(JSONObject jsonObject, String op, final OnPostReturnListener onPostReturnListener){
        postGetJson(jsonObject,op,false,null,onPostReturnListener);
    }

    public interface OnPostReturnListener {
        public void onResponse(JSONObject json) throws Exception;
    }

    public static void checkToken(final BaseActivity baseActivity){
        long expires  = baseActivity.getmSpUtils().getValue("token_expires_in", 0l);
        //3小时后过期则重新申请token
        if(expires == 0 || expires < System.currentTimeMillis() + 180*60*1000){
            LogUtils.i("token_expires_in  -- > " + expires);
            LogUtils.i("System.currentTimeMillis  -- > " + System.currentTimeMillis());
            updateToken(baseActivity);
        }else {
            LogUtils.i("未过期,无需更新token");
            //do nothing
        }
    }

    public static void updateToken(final BaseActivity baseActivity){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("grant_type","client_credentials");
            jsonObject.put("client_id", Constant.Config.EASE_CLIENT_ID);
            jsonObject.put("client_secret", Constant.Config.EASE_CLIENT_SECRET);
            HTTPUtils.postGetJson(jsonObject,HTTPUtils.RestOp_GetToken,new OnPostReturnListener() {
                @Override
                public void onResponse(JSONObject json) throws Exception {
                    if(!json.has("error")){
                        //存储token
                        baseActivity.getmSpUtils().setValue("token_expires_in", json.getLong("expires_in") * 1000 + System.currentTimeMillis());
                        baseActivity.getmSpUtils().setValue("token", json.getString("access_token"));
                        LogUtils.i("更新token成功");
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.i(e.getLocalizedMessage());
        }
    }

    public static void doPost(String url, List<BasicNameValuePair> params,final OnReturnListener listener){
        final HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
        final HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        }
        catch (UnsupportedEncodingException e){

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpResponse reponse = client.execute(post);
                    HttpEntity entity = reponse.getEntity();
                    listener.onReponse(EntityUtils.toString(entity));
                }
                catch (Exception e){
                    e.printStackTrace();
                    Log.d("time out", "连接或读取超时");
                    //listener.onReponse(EntityUtils.toString(entity));
                }
            }
        }).start();
    }

    public static void doPost(String url,final OnReturnListener listener){
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        doPost(url,params,listener);
    }

    public interface OnReturnListener{
        public void onReponse(String responseStr) throws JSONException;
    }

}

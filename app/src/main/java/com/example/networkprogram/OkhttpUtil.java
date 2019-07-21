package com.example.networkprogram;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * class description:
 * author ywk
 * since 2019-07-21
 */
public class OkhttpUtil {
    public static void SendOkhttpClient(String adress, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(adress)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
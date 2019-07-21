package com.example.networkprogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 使用HttpURLconnection发送请求，在子线程中提交请求，会导致返回的数据为空，因为子线程还在执行，数据已经返回了。
 * 解决方法，定义一个接口，在网络请求成功的时候调用接口，然后通过参数，将返回的数据提供给调用者。调用这在发送请求的时候
 * 传入一个接口的实例。重写接口的两个方法，在抽象方法中操作返回的数据。
 *
 * 使用okttp发送请求的优点是，返回的resonse，不是字符流。另外，在传入的接口实例不需要我们自己去定义。
 * 在equeue中已经帮我们开启好了子线程，需要注意的是不能在子线程中更新UI
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        HttpUtil.sendHttpUtil("http://baidu.com", new HttpUtil.HttpCallbakListener() {
//            @Override
//            public void onFinish(String response) {
//                Log.d(TAG, "返回的数据" + response);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Toast.makeText(MainActivity.this, "数据返回错误", Toast.LENGTH_SHORT).show();
//            }
//        });
//        使用okhttp封装方法，进行访问网络
        String adress = "http://10.0.2.2/get_json_data.json";
        OkhttpUtil.SendOkhttpClient(adress, new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                String str = response.body().string();
//                try {
//                    JSONArray jsonArray = new JSONArray(str);
//                    for (int i = 0; i < jsonArray.length(); i++){
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        String name = jsonObject.getString("name");
//                        String age = jsonObject.getString("age");
//                        String sex = jsonObject.getString("sex");
//
//                        Log.d(TAG, "姓名为" + name);
//                        Log.d(TAG, "年龄为" + age);
//                        Log.d(TAG, "性别为" + sex);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                Gson gson = new Gson();
                List<PersonBean> PersonList  = gson.fromJson(response.body().string(), new TypeToken<List<PersonBean>>(){}.getType());
                for (PersonBean person : PersonList){
                    Log.d(TAG, "姓名为" + person.getName());
                    Log.d(TAG, "年龄为" + person.getAge());
                    Log.d(TAG, "性别为" + person.getSex());
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "请求失败");
            }
        });
    }
}

package Service;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKhttpleper {
    private static final String TAG = "OKhttpleper";
    private OkHttpClient okHttpClient= new OkHttpClient.Builder()
            .readTimeout(10000, TimeUnit.MILLISECONDS)//设置读取超时为10秒
            .connectTimeout(10000, TimeUnit.MILLISECONDS)//设置链接超时为10秒
            .build();;
    private void yibupost(String s) {
        FormBody formBody = new FormBody.Builder().add("a", "1").add("c", "3").build();
        Request request = new Request.Builder().url(s).post(formBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i(TAG, "onFailure: 异步post请求失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    Log.i(TAG, "异步请求成功: "+response.body().string());
                }

            }
        });

    }
    private void tongbupost(String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
                Request request = new Request.Builder().url(s).post(formBody).build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response execute = call.execute();
                    Log.i(TAG, "同步post请求成功: "+execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void yibuget() {

        Request build = new Request.Builder().url("http://10.0.2.2:5000/recommend").build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i(TAG, "onFailure: netwokerror:异步请求失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    Log.i(TAG, "异步get请求成功: "+response.body().string());
                }
            }
        });
    }
    private void tongbuget() {//同步请求需要new 线程
        new Thread(){
            @Override
            public void run() {
                Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build();
                //准备好请求的call对象
                Call call = okHttpClient.newCall(request);
                try {
                    Response execute = call.execute();
                    Log.i(TAG, execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}

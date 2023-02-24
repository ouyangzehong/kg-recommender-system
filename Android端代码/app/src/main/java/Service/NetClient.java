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
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetClient {
    private static final String TAG = "NetClient";
    private String baseurl="http://10.0.2.2:5000";
    private static NetClient netClient;
    public final OkHttpClient client;

    private NetClient() {
        client = initOkHttpClient();
    }
    // 创建OkHttpClient对象
    private OkHttpClient initOkHttpClient() {
         //初始化的时候可以自定义一些参数
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)//设置读取超时为10秒
                .connectTimeout(10000, TimeUnit.MILLISECONDS)//设置链接超时为10秒
                .build();
        return okHttpClient;
    }

    public static NetClient getNetClient() {
        //先检查实例是否存在，如果不存在才进入下面的同步块
        if (netClient == null) {
            //同步代码块，线程安全的创建实例
            synchronized (NetClient.class) {
                //再次检查实例是否存在，如果不存在才真正的创建实例
                if(netClient == null){
                    netClient = new NetClient();
                }
            }
        }
        return netClient;
    }

    /**
     * get异步请求
     * @param url
     * @param mCallback
     */
    public void getAsyncRequest(String url, final MyCallBack mCallback){
        url=baseurl+url;
        Request request = new Request.Builder().url(url).build();//构造Request对象
        Call call = getNetClient().initOkHttpClient().newCall(request);//通过OkHttpClient和Request对象来构建Call对象
//        通过Call对象的enqueue(Callback)方法来执行异步请求
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.i(TAG, "onResponse: "+response);
                //请求网络成功说明服务器有响应，但返回的是什么我们无法确定，可以根据响应码判断
//                if (response.code() == 200) {
//                    //如果是200说明正常，调用MyCallBack的成功方法，传入数据
//                    mCallback.onResponse(response.body().toString());
//                }else{
//                    //如果不是200说明异常，调用MyCallBack的失败方法将响应码传入
//                    mCallback.onFailure(response.code());
//                }
                if (response.isSuccessful()){
                    String data=response.body().string();
                    Log.i(TAG, "异步get请求成功: "+data);
                    mCallback.onResponse(data);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //请求网络失败，调用自己的接口，通过传回的-1可以知道错误类型
                mCallback.onFailure(-1);
            }
        });
    }

    /**
     * 异步post请求提交表单
     * @param url 地址
     * @param formBody 自定义的请求体
     * @param mCallback   回调函数
     */
    public void postAsyncRequest(String url, FormBody formBody, final MyCallBack mCallback){
        url=baseurl+url;
        //此处post请求多一个请求体 formBody
        Request request = new Request.Builder().url(url).post(formBody).build();//构造Request对象
        Call call = getNetClient().initOkHttpClient().newCall(request);//通过OkHttpClient和Request对象来构建Call对象
//        通过Call对象的enqueue(Callback)方法来执行异步请求
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //请求网络成功说明服务器有响应，但返回的是什么我们无法确定，可以根据响应码判断
//                if (response.code() == 200) {
//                    //如果是200说明正常，调用MyCallBack的成功方法，传入数据
//                    mCallback.onResponse(response.body().toString());
//                }else{
//                    //如果不是200说明异常，调用MyCallBack的失败方法将响应码传入
//                    mCallback.onFailure(response.code());
//                }
                if (response.isSuccessful()){
                    String data=response.body().string();
                    Log.i(TAG, "异步post请求成功: "+data);
                    mCallback.onResponse(data);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //请求网络失败，调用自己的接口，通过传回的-1可以知道错误类型
                mCallback.onFailure(-1);
            }
        });
    }

}
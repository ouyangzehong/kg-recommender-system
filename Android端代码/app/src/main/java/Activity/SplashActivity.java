package Activity;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.hellofood.R;

import java.util.List;

import Bean.FoodBean;
import Service.MyCallBack;
import Service.NetClient;
import Service.OKhttpleper;
import Util.DataUtil;
import Util.GsonUtil;
import Util.SetStyle;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    Handler mhandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SetStyle.changeStatusBarTextColor(getWindow(),true);
        SetStyle.changeStatusBarbackColor(getWindow());

//        OKhttpleper oKhttpleper = new OKhttpleper();
//        oKhttpleper.yibuget();

        NetClient.getNetClient().getAsyncRequest("/recommend", new MyCallBack() {
            @Override
            public void onFailure(int code) {
                Log.i(TAG, "onFailure: net error"+code);
            }

            @Override
            public void onResponse(String json) {
                Log.i(TAG, "onResponse: "+json);
                List<FoodBean> foodBeans = GsonUtil.GsonToList(json, FoodBean.class);
                DataUtil.setFoodBeans(foodBeans);
                Log.i(TAG, "onResponse: "+foodBeans.get(0).toString());
            }
        });
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },3000);

    }

}
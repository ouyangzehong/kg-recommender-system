package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hellofood.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.DMFruitRVAdapter;
import Bean.DMFoodBeaan;
import Service.MyCallBack;
import Service.NetClient;
import Util.GsonUtil;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DMSuitableFoodActivity extends AppCompatActivity {

    private int flags[]={0,0,0,0,0,0};
    private List<DMFoodBeaan> foodBeaans=new ArrayList<>();
    private DMFruitRVAdapter adapter;
    private DMFruitRVAdapter vegetableadapter;
    private DMFruitRVAdapter grainadapter;
    private DMFruitRVAdapter meatadapter;
    private DMFruitRVAdapter dryFruitadapter;
    private DMFruitRVAdapter condimentadapter;
    @BindViews({R.id.rv_suitable_dm_fruit,R.id.rv_suitable_dm_vegetable})
    List<RecyclerView> recyclerViews;
    @BindViews({R.id.iv_fruit_dm,R.id.iv_vegetable_dm})
    List<ImageView> imageViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suitablefood_dm);
        ButterKnife.bind(this);
        //创建适配器
        adapter=new DMFruitRVAdapter(this,foodBeaans);
        vegetableadapter=new DMFruitRVAdapter(this,foodBeaans);
        grainadapter=new DMFruitRVAdapter(this,foodBeaans);
        meatadapter=new DMFruitRVAdapter(this,foodBeaans);
        dryFruitadapter=new DMFruitRVAdapter(this,foodBeaans);
        condimentadapter=new DMFruitRVAdapter(this,foodBeaans);

        //请求水果
        getdata("/FruitForDM",adapter);
        //请求蔬菜
        getdata("/vegetableForDM",vegetableadapter);
//        //请求谷物豆类
//        getdata("/FruitForDM",adapter);
//        //请求肉类
//        getdata("/FruitForDM",adapter);
//        //请求干果
//        getdata("/FruitForDM",adapter);
//        //请求食用油及调味品
//        getdata("/FruitForDM",adapter);
        //设置recyclerview
        setrv(recyclerViews.get(0),adapter);
        setrv(recyclerViews.get(1),vegetableadapter);


    }

    //设置recyclerview
    private void setrv(RecyclerView v,DMFruitRVAdapter adapter) {
        v.setAdapter(adapter);
        v.setLayoutManager(new LinearLayoutManager(this));
    }

    //网络请求
    private void getdata(String url,DMFruitRVAdapter adapter) {
        NetClient.getNetClient().getAsyncRequest(url, new MyCallBack() {
            @Override
            public void onFailure(int code) {

            }

            @Override
            public void onResponse(String json) {
                List<DMFoodBeaan> dmFoodBeaans = GsonUtil.GsonToList(json, DMFoodBeaan.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updata(dmFoodBeaans);
                    }
                });
            }
        });
    }

    //跳转
    public static void actionStart(Context context){
        Intent intent = new Intent(context, DMSuitableFoodActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.iv_back_dm)
    public void iv_back_dm(View v){
        this.finish();
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ll_fruit_dm,R.id.ll_vegetaable_dm})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_fruit_dm:
                if (flags[0]==0){
                    recyclerViews.get(0).setVisibility(View.VISIBLE);
                    imageViews.get(0).setImageResource(R.mipmap.arrow_down);
                    flags[0]=1;
                }else {
                    recyclerViews.get(0).setVisibility(View.GONE);
                    imageViews.get(0).setImageResource(R.mipmap.arrow_right);
                    flags[0]=0;
                }
                break;
            case R.id.ll_vegetaable_dm:
                if (flags[1]==0){
                    recyclerViews.get(1).setVisibility(View.VISIBLE);
                    imageViews.get(1).setImageResource(R.mipmap.arrow_down);
                    flags[1]=1;
                }else {
                    recyclerViews.get(1).setVisibility(View.GONE);
                    imageViews.get(1).setImageResource(R.mipmap.arrow_right);
                    flags[1]=0;
                }
                break;

        }
    }
}
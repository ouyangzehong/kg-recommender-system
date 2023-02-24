package Fragmentuse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellofood.R;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import Adapter.RecommendRVAdapter;
import Bean.FoodBean;
import Service.MyCallBack;
import Service.NetClient;
import Util.DataUtil;
import Util.GsonUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendFragment extends Fragment {
    private static final String TAG = "RecommendFragment";
//    private RecyclerView recyclerview;
    private RecommendRVAdapter adapter;
    private LoadingDialog ld;
    LoadingDialog.Speed speed = LoadingDialog.Speed.SPEED_TWO;
    private boolean intercept_back_event = false;
    private int repeatTime = 0;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private List<FoodBean> foodBeanList=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend2, container, false);
        ButterKnife.bind(this,view);
        ld = new LoadingDialog(getContext());
        ld.setLoadingText("正在构建您的知识图谱...")
                .setSuccessText("推荐生成成功！")//显示加载成功时的文字
                .setFailedText("知识图谱构建失败！")
                .setInterceptBack(intercept_back_event)
                .setLoadSpeed(speed)
                .setRepeatCount(repeatTime)
//                .setDrawColor(color)
                .setShowTime(10)
                .show();
        initrv();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initrv() {
        //创建适配器
        RecommendRVAdapter adapter = new RecommendRVAdapter(getContext(), foodBeanList);
        NetClient.getNetClient().getAsyncRequest("/recommend", new MyCallBack() {
            @Override
            public void onFailure(int code) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ld.loadFailed();
                    }
                });
                Log.i(TAG, "onFailure: net error"+code);
            }

            @Override
            public void onResponse(String json) {
                Log.i(TAG, "onResponse: "+json);
                List<FoodBean> foodBeans = GsonUtil.GsonToList(json, FoodBean.class);
//                DataUtil.setFoodBeans(foodBeans);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updata(foodBeans);
                        //在你代码中合适的位置调用反馈
                        ld.loadSuccess();
                    }
                });

                Log.i(TAG, "onResponse: "+foodBeans.get(0).toString());
            }
        });

        //设置LayoutManager，以LinearLayoutManager为例子进行线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //获取数据
        foodBeanList=new ArrayList<>();

        //设置适配器
        recyclerView.setAdapter(adapter);


    }
}

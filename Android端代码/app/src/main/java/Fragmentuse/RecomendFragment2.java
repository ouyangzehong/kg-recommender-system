package Fragmentuse;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellofood.R;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import Adapter.RecommendRVAdapter;
import Base.BaseFragment;
import Base.DataManager;
import Bean.FoodBean;
import Contract.RecommendFragmentContract;
import Presenter.RecommendFagmentPresenter;
import Service.MyCallBack;
import Service.NetClient;
import Util.GsonUtil;
import butterknife.BindView;

public class RecomendFragment2 extends BaseFragment<RecommendFagmentPresenter> implements RecommendFragmentContract.RecommendFragmentView {
    private static final String TAG = "RecomendFragment2";
    private RecommendRVAdapter adapter;
    private LoadingDialog ld;
    LoadingDialog.Speed speed = LoadingDialog.Speed.SPEED_TWO;
    private boolean intercept_back_event = false;
    private int repeatTime = 0;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private List<FoodBean> foodBeanList = null;

    //获取布局对象
    @Override
    protected int getLayout() {
        return R.layout.fragment_recommend2;
    }

    //创建Presenter
    @Override
    protected RecommendFagmentPresenter createPresenter() {
        RecommendFagmentPresenter presenter = new RecommendFagmentPresenter(new DataManager());
        return presenter;
    }

    //初始化数据
    @Override
    protected void initEventAndData() {

        //设置等待
        setloading();
        //找Presenter处理数据
        mPresenter.getFooddata();
        //初始化列表 Rcyclerview
        initrv();
    }

    private void setloading(){
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
    }
    private void initrv() {
        //创建适配器
        adapter = new RecommendRVAdapter(getContext(), foodBeanList);
        //设置LayoutManager，以LinearLayoutManager为例子进行线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //获取数据
        foodBeanList=new ArrayList<>();

        //设置适配器
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void setdata( List<FoodBean> foodBeans) {
        ld.loadSuccess();
        adapter.updata(foodBeans);
    }

    @Override
    public void showError() {
        ld.loadFailed();
        super.showError();
    }
}

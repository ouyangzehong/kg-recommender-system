package Presenter;

import android.app.Activity;
import android.os.Handler;

import java.util.List;

import Base.DataManager;
import Base.RxBasePresenter;
import Bean.FoodBean;
import Contract.RecommendFragmentContract;
import Service.MyCallBack;
import Util.GsonUtil;

public class RecommendFagmentPresenter extends RxBasePresenter<RecommendFragmentContract.RecommendFragmentView> implements RecommendFragmentContract.Presenter {
    private Handler handler = new Handler();
    //（Model）
    private DataManager mDataManager;

    public RecommendFagmentPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }


    //调用DataManager获取数据
    public void getFooddata() {
        //监听数据是否返回
        mDataManager.getRecommenddata(new MyCallBack() {
            @Override
            public void onFailure(int code) {
                //ui数据只能在主线程变化
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showError();
                    }
                });

            }

            @Override
            public void onResponse(String json) {
                List<FoodBean> foodBeans = GsonUtil.GsonToList(json, FoodBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.setdata(foodBeans);
                    }
                });
            }
        });
    }
}


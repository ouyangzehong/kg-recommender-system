package Fragmentuse;

import androidx.lifecycle.ViewModelProvider;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.tabs.TabLayout;

import com.example.hellofood.R;

import java.util.ArrayList;
import java.util.List;

import Activity.MainActivity;
import Activity.SplashActivity;
import Adapter.ViewPagerAdapter;
import Service.EChartView;
import Util.EChartOptionUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ThirdFragment extends Fragment {
    private static final String TAG = "ThirdFragment";
    Handler mhandler=new Handler();
    @BindView(R.id.tl_third)
    TabLayout tl_third;
    @BindView(R.id.vp_third)
    ViewPager vp_third;
    //echart
    @BindView(R.id.lineChart)
    EChartView lineChart;
    List<Fragment> mFragments;

    String[] mTitles = new String[]{
            "主页", "饮食记录"
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment, container, false);
        ButterKnife.bind(this, view);

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                initviewpager();
//            }
//        });
        initechart();

        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initviewpager();
            }
        },1);


        return view;

    }

    private void initechart() {
        lineChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                Log.i(TAG, "onPageFinished: 更新折线图表数据");
                refreshLineChart();
            }
        });
        lineChart.setBackgroundColor(0);
    }

    private void refreshLineChart() {
        Object[] x = new Object[]{
                "一", "二", "三", "四", "五", "六", "日"
        };
        Object[] y = new Object[]{
                820, 932, 901, 934, 1290, 1330, 1320
        };
        lineChart.refreshEchartsWithOption(EChartOptionUtil.getLineChartOptions(x, y));

        Log.i(TAG, "onPageFinished: 更新折线图表数据2");
    }

    private void initviewpager() {
        mFragments = new ArrayList<>();
        mFragments.add(new PersonFragment());
        mFragments.add(new WeekNotesFragment2());


        // 第二步：为ViewPager设置适配器
        ViewPagerAdapter adapter =
                new ViewPagerAdapter(getChildFragmentManager(), mFragments, mTitles);
        vp_third.setAdapter(adapter);
        //  第三步：将ViewPager与TableLayout 绑定在一起
        tl_third.setupWithViewPager(vp_third);
    }


}
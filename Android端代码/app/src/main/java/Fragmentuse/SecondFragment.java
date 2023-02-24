package Fragmentuse;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hellofood.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyFragmentPagerAdapter;
import Adapter.SecondFragmentPagerAdapter;
import Adapter.ViewPagerAdapter;
import Fragmentuse.disease.data.CHDFragment;
import Fragmentuse.disease.data.DMFragment;
import Fragmentuse.disease.data.HBPFragment;
import Fragmentuse.disease.data.RAFragment;
import Fragmentuse.disease.data.StrokeFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondFragment extends Fragment {

    private ViewPagerAdapter myFragmentPagerAdapter;
    private String[] Titles = new String[]{"糖尿病", "高血压","脑卒中","冠心病","类风湿性关节炎"};
    @BindView(R.id.view_pager_f2)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout_f2)
    TabLayout mTabLayout;
    private List<Fragment> fragments;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        ButterKnife.bind(this,view);

        //设置fragmentlist
        fragments=new ArrayList<>();
        fragments.add(new DMFragment());//糖尿病
        fragments.add(new HBPFragment());//高血压
        fragments.add(new StrokeFragment());//脑卒中 中风
        fragments.add(new CHDFragment());//冠心病
        fragments.add(new RAFragment());//类风湿性关节炎
        //viewpager适配器
        myFragmentPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments,Titles);
        //firstFragment里面的viewpager
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);

        return view;


    }


}
package Fragmentuse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hellofood.R;
import com.google.android.material.tabs.TabLayout;

import android.widget.TableLayout;

import Adapter.MyFragmentPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstFragment extends Fragment {

    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.first_fragment, container, false);
        ButterKnife.bind(this,inflate);

        //viewpager适配器
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        //firstFragment里面的viewpager
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);
        return inflate;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
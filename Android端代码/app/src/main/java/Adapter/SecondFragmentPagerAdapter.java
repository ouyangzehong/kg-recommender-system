package Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import Fragmentuse.FindFragment;
import Fragmentuse.RecommendFragment;
import Fragmentuse.disease.data.DMFragment;

public class SecondFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mfragments;
    private String[] mTitles;

    public SecondFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments,String[] Titles) {
        super(fm);
        mfragments=fragments;
        mTitles=Titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position-1);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
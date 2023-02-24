package Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import Fragmentuse.FindFragment;
import Fragmentuse.RecomendFragment2;
import Fragmentuse.RecommendFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	
    private String[] mTitles = new String[]{"个性推荐", "安心查询"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new FindFragment();
        }
        return new RecomendFragment2();
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
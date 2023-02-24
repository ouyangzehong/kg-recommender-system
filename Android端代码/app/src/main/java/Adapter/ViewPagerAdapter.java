package Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mList;
    private String[] mTitles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list,String[] Titles) {
        super(fm);
        mList = list;
        this.mTitles=Titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}

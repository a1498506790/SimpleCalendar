package com.mir.simplecalendardemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Airsaid
 * @Date 2017/8/6 17:03
 * @Blog http://blog.csdn.net/airsaid
 * @Desc 懒加载 FragmentPagerAdapter (代码来自 momodae：https://github.com/momodae)
 */
public class LazyLoadFragmentPagerAdapter extends FragmentPagerAdapter {

    public static final String KEY_IS_LOAD = "isLoad";
    private List<Fragment> mFragments;
    private int mLastPosition = -1;

    public LazyLoadFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public LazyLoadFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.mFragments = fragments;
        // 初始化加载标记为 false
        for (Fragment fragment : mFragments) {
            Bundle args = fragment.getArguments();
            if(args == null){
                args = new Bundle();
            }
            args.putBoolean(KEY_IS_LOAD, false);
            fragment.setArguments(args);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if(object instanceof Fragment){
            Fragment f = (Fragment) object;
            if(f instanceof LazyLoadCallback && mLastPosition != position && f.isResumed()){
                mLastPosition = position;
                Bundle args = f.getArguments();
                if(args != null && !args.getBoolean(KEY_IS_LOAD)){
                    args.putBoolean(KEY_IS_LOAD, true);
                    ((LazyLoadCallback) f).onLoad();
                }
            }
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        if(object instanceof Fragment){
            Fragment f = (Fragment) object;
            Bundle args = f.getArguments();
            if(args != null){
                args.putBoolean(KEY_IS_LOAD, false);
            }
        }
    }
}

package com.mir.simplecalendardemo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.mir.simplecalendardemo.calendar.MonthCalendarView;

import java.util.List;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/18
 * @desc
 */
public class MyAdapter extends PagerAdapter{

    private List<MonthCalendarView> mViews;

    public MyAdapter(List<MonthCalendarView> listView) {
        this.mViews = listView;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

}

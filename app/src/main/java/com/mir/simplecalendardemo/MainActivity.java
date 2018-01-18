package com.mir.simplecalendardemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mir.simplecalendardemo.calendar.MyCalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private LazyLoadFragmentPagerAdapter mAdapter;
    private Calendar mInstance;
    private int mYear;
    private int mMonth;
    private TextView mTxtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInstance = Calendar.getInstance();
        mYear = mInstance.get(Calendar.YEAR);
        mMonth = mInstance.get(Calendar.MONTH) + 1;
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTxtData = (TextView) findViewById(R.id.text_data);
        initAdapter();
        updataYearAndMonth(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                updataYearAndMonth(position - 100 - 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        findViewById(R.id.btn_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(100 + mMonth);
            }
        });

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mViewPager.getCurrentItem() + 1;
                mViewPager.setCurrentItem(currentItem);
            }
        });

        findViewById(R.id.btn_pre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mViewPager.getCurrentItem() - 1;
                mViewPager.setCurrentItem(currentItem);
            }
        });
    }

    private void initAdapter() {
        for (int i = -100; i < 100; i++) {
            mFragments.add(LazyLoadFragment.newInstance(i));
        }
        mAdapter = new LazyLoadFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(100 + mMonth);
    }

    public void updataYearAndMonth(int position){
        String formatYearAndMonth = MyCalendarUtils.formatYearAndMonth(mYear, mMonth + position);
        mTxtData.setText(formatYearAndMonth);
    }

}

package com.mir.simplecalendardemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mir.simplecalendardemo.calendar.MonthCalendarView;

import java.util.Calendar;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/18
 * @desc
 */

public class LazyLoadFragment extends Fragment {

    private MonthCalendarView mCalendarView;

    public static LazyLoadFragment newInstance(int month){
        Bundle args = new Bundle();
        args.putInt("month", month);
        LazyLoadFragment fragment = new LazyLoadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragememt_calendar, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCalendarView = (MonthCalendarView) view.findViewById(R.id.calendarView);
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        int month = arguments.getInt("month");
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        mCalendarView.setMonth(year, month);
    }
}

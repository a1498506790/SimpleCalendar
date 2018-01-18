package com.mir.simplecalendardemo.calendar;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/18
 * @desc 日历模型
 */

public class MyCalendarBean {

    private int year;
    private int month;
    private int day;
    private boolean isCurrentMonth = true; //是否是当前月份的日期

    public MyCalendarBean(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public void setCurrentMonth(boolean currentMonth) {
        isCurrentMonth = currentMonth;
    }
}

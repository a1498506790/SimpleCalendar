package com.mir.simplecalendardemo.calendar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/18
 * @desc 日历的工具类
 */

public class MyCalendarUtils {

    private final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};

    /**
     * 构建具体一天的对象
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static MyCalendarBean generateCalendarBean(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        return new MyCalendarBean(year, month, day);
    }

    /**
     * 获得当前月份的日期列表
     * @param year
     * @param month
     * @return
     */
    public static List<MyCalendarBean> getDaysListOfMonth(int year, int month){
        List<MyCalendarBean> list = new ArrayList<>();
        int daysOfMonth = getDaysOfCertainMonth(year, month);

        //找到当前月第一天的星期， 计算出前面空缺的上个月的日期个数， 填充到当月日期列表中
        int weekDayOfFirstDay = getWeekDayOnCertainDate(year, month, 1);
        int weekDayOfLastDay = getWeekDayOnCertainDate(year, month, daysOfMonth);
        int preMonthDays = weekDayOfFirstDay - 1;
        int lastMonthDays = weekDayOfLastDay - 1;
        for (int i = preMonthDays; i > 0; i--) {
            MyCalendarBean bean = generateCalendarBean(year, month, 1 - i);
            bean.setCurrentMonth(false);
            list.add(bean);
        }

        for (int i = 0; i < daysOfMonth; i++) {
            MyCalendarBean bean = new MyCalendarBean(year, month, i + 1);
            bean.setCurrentMonth(true);
            list.add(bean);
        }

        int count = 0;
        for (int i = lastMonthDays; i < 6; i++) {
            MyCalendarBean bean = generateCalendarBean(year, month + 1, count + 1);
            bean.setCurrentMonth(false);
            list.add(bean);
            count ++;
        }

        return list;
    }

    /**
     * 获得具体月份的最大天数
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfCertainMonth(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取具体一天对应的星期
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getWeekDayOnCertainDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 格式化标题展示
     * @param year
     * @param month
     * @return
     */
    public static String formatYearAndMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        return year + "年" + (month > 9 ? month : ("0" + month)) + "月";
    }

    public static boolean isToDay(MyCalendarBean bean){
        int[] nowDay = getNowDayFromSystem();
        return bean.getYear() == nowDay[0] && bean.getMonth() == nowDay[1] && bean.getDay() == nowDay[2];
    }

    public static int[] getNowDayFromSystem(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE)};
    }

    public static String getChinaDay(int year, int month, int day){
        Calendar today = Calendar.getInstance();
        try {
            today.setTime(Lunar.chineseDateFormat.parse(year + "年"+ month +"月" + day + "日"));
            Lunar lunar = new Lunar(today);
            String chinaString = lunar.getChinaString();
            return chinaString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据国历获取假期
     *
     * @return
     */
    public static String getHolidayFromSolar(int year, int month, int day) {
        String message = "";
        if (month == 0 && day == 1) {
            message = "元旦";
        } else if (month == 1 && day == 14) {
            message = "情人节";
        } else if (month == 2 && day == 8) {
            message = "妇女节";
        } else if (month == 2 && day == 12) {
            message = "植树节";
        } else if (month == 3) {
            if (day == 1) {
                message = "愚人节";
            } else if (day >= 4 && day <= 6) {
                if (year <= 1999) {
                    int compare = (int) (((year - 1900) * 0.2422 + 5.59) - ((year - 1900) / 4));
                    if (compare == day) {
                        message = "清明节";
                    }
                } else {
                    int compare = (int) (((year - 2000) * 0.2422 + 4.81) - ((year - 2000) / 4));
                    if (compare == day) {
                        message = "清明节";
                    }
                }
            }
        } else if (month == 4 && day == 1) {
            message = "劳动节";
        } else if (month == 4 && day == 4) {
            message = "青年节";
        } else if (month == 4 && day == 12) {
            message = "护士节";
        } else if (month == 5 && day == 1) {
            message = "儿童节";
        } else if (month == 6 && day == 1) {
            message = "建党节";
        } else if (month == 7 && day == 1) {
            message = "建军节";
        } else if (month == 8 && day == 10) {
            message = "教师节";
        } else if (month == 9 && day == 1) {
            message = "国庆节";
        } else if (month == 10 && day == 11) {
            message = "光棍节";
        } else if (month == 11 && day == 25) {
            message = "圣诞节";
        }
        return message;
    }


}

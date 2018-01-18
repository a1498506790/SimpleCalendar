package com.mir.simplecalendardemo.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mir.simplecalendardemo.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/18
 * @desc
 */

public class MonthCalendarView extends ViewGroup {

    //每行的数量
    private int mColumn = 7;
    private List<MyCalendarBean> mList;
    private int mYear;
    private int mMonth;
    private int pickUpPosition;

    public MonthCalendarView(Context context) {
        this(context, null);
    }

    public MonthCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initYearAndMonth();
    }

    public void initYearAndMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(calendar.MONTH) + 1;
        setMonth(year, month);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));

        //将宽度平均分成7份， 每个item的宽高都等于它
        int itemWidth = parentWidth / mColumn;
        int itemHeight = itemWidth;

        int parentHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));

            //计算控件所需的高度
            if (i % mColumn == 0) {
                parentHeight += childView.getMeasuredHeight();
            }
        }
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View itemView = getChildAt(i);
            int columnCount = i % mColumn;
            int rowCount = i / mColumn;

            int itemWidth = itemView.getMeasuredWidth();
            int itemHeight = itemView.getMeasuredHeight();

            l = columnCount * itemWidth;
            t = rowCount * itemHeight;
            r = l + itemWidth;
            b = t + itemHeight;
            itemView.layout(l, t, r, b);
        }
    }

    public void setMonth(int year, int month){
        mYear = year;
        mMonth = month;
        invalidateMonth();
    }

    private void invalidateMonth() {
        mList = MyCalendarUtils.getDaysListOfMonth(mYear, mMonth);
        Log.e("test", "mList : " + mList.get(0).getMonth());
        removeAllViews();
        addAllItem();
        requestLayout();
    }

    private void addAllItem() {
        for (int i = 0; i < mList.size(); i++) {
            final MyCalendarBean bean = mList.get(i);
            final View itemView = generateDataView(bean);
            addViewInLayout(itemView, i, itemView.getLayoutParams(), true);
            final int position = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pickUpPosition == position) {
                        return;
                    }
                    if (pickUpPosition != -1) {
                        getChildAt(pickUpPosition).setSelected(false);
                    }
                    itemView.setSelected(true);

                    if (mOnDatePickUpListener != null) {
                        mOnDatePickUpListener.onDatePickUp(bean);
                    }

                    pickUpPosition = position;
                }
            });
        }
    }

    private View generateDataView(MyCalendarBean bean){
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_data_view, null);
        LinearLayout lltContent = (LinearLayout) itemView.findViewById(R.id.llt_content);
        TextView data = (TextView) itemView.findViewById(R.id.date);
        TextView mDataChina = (TextView) itemView.findViewById(R.id.text_china);
        data.setText(String.valueOf(bean.getDay()));
        String holidayFromSolar = MyCalendarUtils.getHolidayFromSolar(bean.getYear(), bean.getMonth() - 1, bean.getDay());
        LunarCalendarUtils.Lunar lunar = LunarCalendarUtils.solarToLunar(new LunarCalendarUtils.Solar(bean.getYear(), bean.getMonth(), bean.getDay()));
        String lunarHoliday = LunarCalendarUtils.getLunarHoliday(lunar.lunarYear, lunar.lunarMonth, lunar.lunarDay);

        if (TextUtils.isEmpty(holidayFromSolar) && TextUtils.isEmpty(lunarHoliday)) {
            mDataChina.setText(MyCalendarUtils.getChinaDay(bean.getYear(), bean.getMonth(), bean.getDay()));
            mDataChina.setTextColor(Color.parseColor("#999999"));
        }else{
            mDataChina.setTextColor(Color.parseColor("#89cbc1"));
            if (TextUtils.isEmpty(lunarHoliday)) {
                mDataChina.setText(holidayFromSolar);
            }else{
                mDataChina.setText(lunarHoliday);
            }
        }
        if (bean.isCurrentMonth()) {
            data.setTextColor(Color.parseColor("#333333"));
            if (MyCalendarUtils.isToDay(bean)) {
                lltContent.setBackgroundResource(R.drawable.item_today_bg);
            }else{
                lltContent.setBackgroundResource(R.drawable.item_pick_up);
            }
        }else{
            data.setTextColor(Color.parseColor("#999999"));
        }
        return itemView;
    }

    public void moveToPreMonth(){
        mMonth -= 1;
        invalidateMonth();
    }

    public void moveToNextMonth(){
        mMonth += 1;
        invalidateMonth();
    }

    public String getCurrentYearAndMonth(){
        return MyCalendarUtils.formatYearAndMonth(mYear, mMonth);
    }

    public String getCurrentAnimal(){
        Calendar today = Calendar.getInstance();
        try {
            today.setTime(Lunar.chineseDateFormat.parse(mYear + "年"+ mMonth +"月" + 1 + "日"));
            Lunar lunar = new Lunar(today);
            String animals = lunar.animalsYear();
            return animals;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private OnDatePickUpListener mOnDatePickUpListener;
    public void setOnDatePickUpListener(OnDatePickUpListener onDatePickUpListener){
        this.mOnDatePickUpListener = onDatePickUpListener;
    }
    public interface OnDatePickUpListener{
        void onDatePickUp(MyCalendarBean bean);
    }


}

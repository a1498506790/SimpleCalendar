package com.mir.simplecalendardemo.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.mir.simplecalendardemo.R;

/**
 * @author by lx
 * @github https://github.com/a1498506790
 * @data 2018/1/18
 * @desc
 */

public class SimpleWeekView extends View{

    private String[] mWeeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private int mTextSize;
    private int mTextColor;
    private Typeface mTypeface;
    private Paint mPaint;
    private float mMeasureTextWidth;

    public SimpleWeekView(Context context) {
        this(context, null);
    }

    public SimpleWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WeekView);
        int textColor = a.getColor(R.styleable.WeekView_wv_textColor, Color.BLACK);
        setTextColor(textColor);
        int textSize = a.getDimensionPixelSize(R.styleable.WeekView_wv_textSize, -1);
        setTextSize(textSize);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) ((mMeasureTextWidth * mWeeks.length) + getPaddingLeft() + getPaddingRight());
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) (mMeasureTextWidth + getPaddingTop() + getPaddingBottom());
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mTextSize != -1) {
            mPaint.setTextSize(mTextSize);
        }
        if (mTypeface != null) {
            mPaint.setTypeface(mTypeface);
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
        int columnWidth = (getWidth() - getPaddingLeft() - getPaddingRight()) / 7;
        for (int i = 0; i < mWeeks.length; i++) {
            String text = mWeeks[i];
            int fontWidth = (int) mPaint.measureText(text);
            int startX = columnWidth * i + (columnWidth - fontWidth) / 2 + getPaddingLeft();
            int startY = (int) ((getHeight()) / 2 - (mPaint.ascent() + mPaint.descent()) / 2) + getPaddingTop();
            canvas.drawText(text, startX, startY, mPaint);
        }

    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        mPaint.setColor(mTextColor);
    }

    public void setTextSize(int textSize) {
        mTextSize = textSize;
        mPaint.setTextSize(mTextSize);
        mMeasureTextWidth = mPaint.measureText(mWeeks[0]);
    }

    /**
     * 设置字体
     * @param typeface
     */
    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
        invalidate();
    }
}
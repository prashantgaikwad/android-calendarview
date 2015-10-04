package com.pg.calendarview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarViewGrid extends GridView{

    private MonthDisplayHelper mHelper;
    private Calendar mRightNow = null;
    private Cell[][] mCells = new Cell[6][7];
    private Context context;
    private int todayCellColor = 0xFFFFEB3B;
    private int weekdayCellColor = 0xffeeeeee;
    private int weekendCellColor = 0xffdedede;

    private OnMonthChangeListener monthChangeListener;

    public CalendarViewGrid(Context context) {
        this(context, null);
    }

    public CalendarViewGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        Calendar calender = Calendar.getInstance();
        mRightNow = Calendar.getInstance();
        mHelper = new MonthDisplayHelper(mRightNow.get(Calendar.YEAR), mRightNow.get(Calendar.MONTH));

        initCells();
        initCalenderView();
//		-----------		Swipe detection		----------------
        final GestureDetectorCompat gestureDetector = new GestureDetectorCompat(context,new MyGestureDetector());
        OnTouchListener gestureListener = new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
        this.setOnTouchListener(gestureListener);
        this.setBackgroundColor(Color.WHITE);
        this.setNumColumns(7);
        this.setColumnWidth(GridView.AUTO_FIT);
        this.setVerticalSpacing(2);
        this.setHorizontalSpacing(2);
        this.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        this.setSelector(android.R.color.transparent);
    }

    public void setOnMonthChangedListener(OnMonthChangeListener monthChangedListener) {
        this.monthChangeListener = monthChangedListener;
        monthChangedListener.onMonthChanged(getMonth(),getYear());
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_THRESHOLD_VELOCITY = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                nextMonth();
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                previousMonth();
            }
            return false;
        }

    }
    void initCalenderView(){

        List< Cell> objects = new ArrayList<Cell>();
        for(Cell[] week : mCells) {
            for(Cell day : week) {
                objects.add(day);
            }
        }
        CalenderAdapter adapter = new CalenderAdapter(context, objects);
        this.setAdapter(adapter);
    }

    private void initCells() {
        class _calendar {
            public int day;
            public int month;
            public boolean thisMonth;
            public int year;
            public _calendar(int d, boolean b) {
                day = d;
                thisMonth = b;
            }
            public _calendar(int d) {
                this(d, false);
            }
        };
        _calendar tmp[][] = new _calendar[6][7];

        for(int i = 0; i < tmp.length; i++) {
            int n[] = mHelper.getDigitsForRow(i);
            for(int d = 0; d < n.length; d++) {
                if(mHelper.isWithinCurrentMonth(i, d)){
                    tmp[i][d] = new _calendar(n[d], true);
                }
                else{
                    tmp[i][d] = new _calendar(n[d]);
                }
                tmp[i][d].year = mHelper.getYear();
                tmp[i][d].month = mHelper.getMonth();
            }
        }

        Calendar today = Calendar.getInstance();
        int thisDay = 0;
        if(mHelper.getYear()==today.get(Calendar.YEAR) && mHelper.getMonth()==today.get(Calendar.MONTH)) {
            thisDay = today.get(Calendar.DAY_OF_MONTH);
        }
        // build cells

        for(int week=0; week<mCells.length; week++) {
            for(int day=0; day<mCells[week].length; day++) {
                if(tmp[week][day].thisMonth) {
                    if(day==0 || day==6 ){
                        mCells[week][day] = new WeekendCell(tmp[week][day].day,tmp[week][day].month,tmp[week][day].year);
                    }else {
                        mCells[week][day] = new WeekdayCell(tmp[week][day].day,tmp[week][day].month,tmp[week][day].year);
                    }
                } else {
                    mCells[week][day] = new Cell(tmp[week][day].day,tmp[week][day].month,tmp[week][day].year, false);
                }
                // get today
                if(tmp[week][day].day==thisDay && tmp[week][day].thisMonth) {
                    mCells[week][day].color = todayCellColor;
                }
            }
        }
    }

    public int getYear() {
        return mHelper.getYear();
    }

    public int getMonth() {
        return mHelper.getMonth();
    }

    public void nextMonth() {
        mHelper.nextMonth();
        initCells();
        initCalenderView();
        monthChangeListener.onMonthChanged(getMonth(), getYear());
    }

    public void previousMonth() {
        mHelper.previousMonth();
        initCells();
        initCalenderView();
        monthChangeListener.onMonthChanged(getMonth(), getYear());
    }

    public boolean firstDay(int day) {
        return day==1;
    }

    public boolean lastDay(int day) {
        return mHelper.getNumberOfDaysInMonth()==day;
    }

    public void goToday() {
        Calendar cal = Calendar.getInstance();
        mHelper = new MonthDisplayHelper(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        initCells();
    }

    public Calendar getDate() {
        return mRightNow;
    }

    public class WeekdayCell extends Cell {
        public WeekdayCell(int dayOfMon, int month, int year) {
            super(dayOfMon, month, year, true);
            color = weekdayCellColor;
        }
    }

    private class WeekendCell extends Cell {
        public WeekendCell(int dayOfMon, int month, int year) {
            super(dayOfMon, month, year, true);
            color = weekendCellColor;
        }
    }
}

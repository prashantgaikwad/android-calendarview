package com.pg.calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarView extends LinearLayout {

    private final static int DAYS_LAYOUT_BG = 0xFFFFC107;
    private CalendarViewGrid calendarViewGrid;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TextView monthTextView = new TextView(context);
        monthTextView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        monthTextView.setTextColor(0xff000000);
        monthTextView.setBackgroundColor(0xFFFFA000);
        monthTextView.setPadding(0, 5, 0, 5);
        monthTextView.setGravity(Gravity.CENTER);
        calendarViewGrid = new CalendarViewGrid(context);
        calendarViewGrid.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        calendarViewGrid.setOnMonthChangedListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChanged(int month, int year) {
                monthTextView.setText(new DateFormatSymbols().getMonths()[month] +" "+year);
            }
        });

        LinearLayout weekdaysLayout = new LinearLayout(context);
        weekdaysLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        weekdaysLayout.setWeightSum(7);
        weekdaysLayout.setOrientation(HORIZONTAL);
        weekdaysLayout.setPadding(10,0,10,0);
        weekdaysLayout.setBackgroundColor(DAYS_LAYOUT_BG);
        String[] daysArray = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day:daysArray) {
            TextView dayTextView = new TextView(context);
            dayTextView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            dayTextView.setTextColor(0xff000000);
            dayTextView.setPadding(5,3,3,3);
            dayTextView.setText(day);
            weekdaysLayout.addView(dayTextView);
        }
        this.setOrientation(VERTICAL);
        this.addView(monthTextView);
        this.addView(weekdaysLayout);
        this.addView(calendarViewGrid);
    }

    public void setOnDateClickListener(final OnDateClickListener listener) {
        calendarViewGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cell cell = (Cell) parent.getItemAtPosition(position);
                Log.d("CalendarView", "date = " + cell.getDayOfMonth() + "/" + cell.getMonth() + "/" + cell.getYear());
                if (cell.isThisMonth()){
                    listener.onDateClicked(cell.getDayOfMonth(), cell.getMonth()+1, cell.getYear());
                }
            }
        });
    }
}

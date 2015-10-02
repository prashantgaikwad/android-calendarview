package com.pg.calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CellView extends LinearLayout{

    private TextView dayTextView;

    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dayTextView = new TextView(context);
        dayTextView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dayTextView.setPadding(10, 10, 10, 10);
        dayTextView.setText("sd");
        this.setGravity(Gravity.CENTER);
        this.addView(dayTextView);
    }

    public void setText(String text){
        if (dayTextView != null){
            dayTextView.setText(text);
        }
    }

}

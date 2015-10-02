package com.pg.calendarview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.List;

public class CalenderAdapter extends ArrayAdapter<Cell> {

    public CalenderAdapter(Context context, List<Cell> objects) {
        super(context, 0, objects);
        items = objects;
        this.context = context;
    }
    Context context;
    private List<Cell> items;

    public View getView(int position, View convertView, ViewGroup parent) {
        CellView cellView ;
        if(convertView == null){
            cellView =  new CellView(this.context);
            cellView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            cellView.setText("" + items.get(position).getDayOfMonth());
            convertView = cellView;
        }else {
            cellView = (CellView)convertView;
            cellView.setText(""+items.get(position).getDayOfMonth());
        }
        convertView.setBackgroundColor(items.get(position).getColor());
        return convertView;
    }
}

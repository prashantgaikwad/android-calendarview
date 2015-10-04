package com.pg.calendarview;

public class Cell {
	protected int dayOfMonth = 1;	// from 1 to 31
	Boolean isToday = false;
	protected int color = 0xffffffff;
	private int month = 1
			,year = 2000;
    private boolean thisMonth;
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

    public Cell(int dayOfMon, int month, int year, boolean thisMonth) {
        this.month =month;
		this.year = year;
		this.dayOfMonth = dayOfMon;
        this.thisMonth = thisMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getDayOfMonth() {
		return dayOfMonth;

	}

	public String toString() {
		return String.valueOf(dayOfMonth);
	}

    public boolean isThisMonth() {
        return thisMonth;
    }
}
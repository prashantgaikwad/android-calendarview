/*
 * Copyright (C) 2011 Chris Gao <chris@exina.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
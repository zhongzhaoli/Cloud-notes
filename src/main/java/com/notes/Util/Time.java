package com.notes.Util;

import java.util.Calendar;
import java.util.Date;

public class Time {
	public String gettime(){
		Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=(cal.get(Calendar.MONTH)) + 1;//获取月份
        int day=cal.get(Calendar.DATE);//获取日
        String time = year + "-" + month + "-" + day;
        return time;
	}
}

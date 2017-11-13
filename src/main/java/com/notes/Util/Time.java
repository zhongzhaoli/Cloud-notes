package com.notes.Util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.catalina.startup.HomesUserDatabase;

public class Time {
	
	public static Timestamp timestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public String gettime_more(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = (cal.get(Calendar.MONTH)) + 1;
		String day = add_zero(cal.get(Calendar.DATE));
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		String time = year + "-" + month + "-" + day + " " + hour + ":" + min;
		return time;
	}
	
	public String add_zero(int i){
		String zero = "0";
		String new_i; 
		new_i= Integer.toString(i);
		if(i < 10){
			new_i = zero + new_i;
		}
//		i = Integer.parseInt(new_i);
		return new_i;
	}
}

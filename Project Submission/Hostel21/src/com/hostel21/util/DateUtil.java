package com.hostel21.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {
	public static String sqliteDateFormat="yyyy-MM-dd";
	public static String hostel21DateFormat="MM/dd/yyyy";
	
	public static Date convertToDate(String date,String format) {
		Date formattedDate=null;
		try {
			SimpleDateFormat formatter=new SimpleDateFormat(format);
			formattedDate=formatter.parse(date);
		}
		catch(Exception e) {
    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}				  
		return formattedDate;
	}
	
	public static String convertToString(Date date,String format) {
		String formattedDate=null;
		try {
			SimpleDateFormat formatter=new SimpleDateFormat(format);
			formattedDate=formatter.format(date);
		}
		catch(Exception e) {
    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}				  
		return formattedDate;
	}	
	
	public static String convert(String date,String fromFormat,String toFormat) {
		String formattedDate=null;
		try {
			SimpleDateFormat formatter=new SimpleDateFormat(fromFormat);
			Date fromDate=formatter.parse(date);
			formatter=new SimpleDateFormat(toFormat);
			formattedDate=formatter.format(fromDate);
		}
		catch(Exception e) {
    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}				  
		return formattedDate;
	}		
	
	public static Date addDaysToDate(Date d, int numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);

		calendar.add(Calendar.DATE, numberOfDays);

		d.setTime(calendar.getTime().getTime());
		return d;
	}
	
	public static List<Date> datesBetweenTwoDates(Date start, Date end)
	{
	    List<Date> dates = new ArrayList<Date>();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(start);

	    while (calendar.getTime().before(end))
	    {
	        Date resultado = calendar.getTime();
	        dates.add(resultado);
	        calendar.add(Calendar.DATE, 1);
	    }
	    return dates;
	}	
	
    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
     }	
    
	public static String converToSQLDate(String date) {
		return DateUtil.convert(date, DateUtil.hostel21DateFormat, DateUtil.sqliteDateFormat);
	}
	
	public static String converToHostelDate(String date) {
		return DateUtil.convert(date, DateUtil.sqliteDateFormat,DateUtil.hostel21DateFormat);
	}	    
}

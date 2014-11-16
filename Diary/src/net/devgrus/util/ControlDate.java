package net.devgrus.util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by SeoDong on 2014-10-31.
 */
public class ControlDate {
	
	private static String dateChanger(int date){
		if(date < 10){
			return "0"+date;
		}
		return ""+date;
	}
	
	/**
	 * @return Date by Calendar Class 
	 */
	public static Calendar getdateC(){
		Calendar call = Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);	
		return call;
	}
	
	/**
	 * @return Date by String Class (1960-01-01 00:00:00)
	 */
	public static String getdateS(){
		Calendar call=Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);
		
		int year = call.get(Calendar.YEAR);
		int month = call.get(Calendar.MONTH)+1;
		int day = call.get(Calendar.DATE);
		int hour = call.get(Calendar.HOUR);
		int minute = call.get(Calendar.MINUTE);
		int second = call.get(Calendar.SECOND);
		String date = year+"-"+dateChanger(month)+"-" +dateChanger(day)+" "+ dateChanger(hour)+":"+dateChanger(minute)+":"+dateChanger(second); 		
		return date;
	}
	
	/**
	 * @return Date by String Class (1960-01-01 00:00:00)
	 */
	public static String getdateS(Calendar rCall){
		Calendar call=Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);
		rCall.setTimeZone(tz);
		
		int year = rCall.get(Calendar.YEAR);
		int month = rCall.get(Calendar.MONTH)+1;
		int day = rCall.get(Calendar.DATE);
		int hour = call.get(Calendar.HOUR);
		int minute = call.get(Calendar.MINUTE);
		int second = call.get(Calendar.SECOND);
		
		String date = year+"-"+dateChanger(month)+"-" +dateChanger(day)+" "+ dateChanger(hour)+":"+dateChanger(minute)+":"+dateChanger(second);	
		return date;
	}
	
	/**
	 * @return Date by String <String to String> (1960-01-01)
	 */
	public static String getdateStoS(String rCall){
		String[] call = rCall.split(" ");
		
		String date = call[0];		
		return date;
	}
	
	/**
	 * @return Year by String <String to String> (1960)
	 */
	public static int getyearStoS(String rCall){
		String[] call = rCall.split("-");
		
		String date = call[0];		
		return Integer.parseInt(date);
	}
	
	/**
	 * @return Date by String <String to String> (1960-01-01 000000)
	 */
	public static String convertdateStoS(String rCall){
		return rCall.replaceAll(":", "");
	}
	
	/**
	 * @return Date by String <Class to String> (1960-01-01 00:00:00)
	 */
	public static String getdateCtoS(Calendar rCall){
		Calendar call=Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);
		rCall.setTimeZone(tz);
		
		int year = rCall.get(Calendar.YEAR);
		int month = rCall.get(Calendar.MONTH)+1;
		int day = rCall.get(Calendar.DATE);
		int hour = call.get(Calendar.HOUR);
		int minute = call.get(Calendar.MINUTE);
		int second = call.get(Calendar.SECOND);
		
		String date = year+"-"+dateChanger(month)+"-" +dateChanger(day)+" "+ dateChanger(hour)+":"+dateChanger(minute)+":"+dateChanger(second);	
		return date;
	}
	
	/**
	 * @return Year(int)
	 */
	public static int getYear(){
		Calendar call=Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);
		return call.get(Calendar.YEAR);
	}
	
	/**
	 * @return Month(int)
	 */
	public static int getMonth(){
		Calendar call=Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);
		return call.get(Calendar.MONTH)+1;
	}
	
	/**
	 * @return Day(int)
	 */
	public static int getDay(){
		Calendar call=Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);
		return call.get(Calendar.DATE);
	}
	
	/**
	 * @return Hour(int)
	 */
	public static int getHour(){
		Calendar call=Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);
		return call.get(Calendar.HOUR);
	}
	
	/**
	 * @return Minute(int)
	 */
	public static int getMinute(){
		Calendar call=Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);
		return call.get(Calendar.MINUTE);
	}
	
	/**
	 * @return Second(int)
	 */
	public static int getSecond(){
		Calendar call=Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("GMT+09:00");
		call.setTimeZone(tz);
		return call.get(Calendar.SECOND);
	}
	
	/**
	 * @return Date(int) (0:Year, 1:Month, 2: Date, 3:Hour, 4:Minute, 5:Second)
	 */
	public static int getdateForSettingCalender(String rCall, int index){
		return Integer.parseInt(rCall.replaceAll(":", "-").replaceAll(" ", "-").split("-")[index]);
	}
}
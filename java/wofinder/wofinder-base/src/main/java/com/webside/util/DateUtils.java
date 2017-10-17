package com.webside.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	// allStyle eg:"yyyy年M月d日 HH:mm:ss E a" => 2011年7月25日 11:18:46 星期一 上午
	public static final String _DEFAULT1 = "yyyy-MM-dd HH:mm"; // 2011-07-25
																// 11:18

	public static final String _DEFAULT2 = "yyyy-MM-dd HH:mm:ss"; // 2011-07-25
																	// 11:18:46

	public static final String _DEFAULT3 = "yyyyMMdd"; // 20110725

	public static final String _DEFAULT4 = "yyyyMMddHHmmss"; // 20110725111846

	public static final String _DEFAULT5 = "yyyy-MM-dd";

	public static final String _DEFAULT_CN = "yyyy年M月d日 HH:mm:ss"; // 2011年7月25日
																	// 11:18:46

	public static final String _DEFAULT_CN_WEEK = "yyyy年M月d日 E"; // 2011年7月25日
																	// 11:18:46
																	// 星期一

	public static final String _DEFAULT_CN_YEAR_DAY = "yyyy年M月"; // 2011年7月

	public static final String _DEFAULT_CN_WEEK1 = "E";// 星期一

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 日期时间格式转换
	 * 
	 * @param:String style:_DEFAULT1.2...
	 */
	public static String formatDate(Date date, String style) {
		return new SimpleDateFormat(style).format(date);
	}

	/**
	 * @title : String 转 Date
	 */
	public static Date getStringDate(String date) {
		Date dates = null;
		try {
			dates = new SimpleDateFormat(_DEFAULT2).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dates;
	}

	/**
	 * @title : String 转 Date
	 */
	public static Date getStringDate(String date, String style) {
		Date dates = null;
		try {
			dates = new SimpleDateFormat(style).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dates;
	}

	/**
	 * @title : String 时间格式转换 yyyy-MM-dd HH:mm:ss 转 yyyy-MM-dd
	 */
	public static String getStringToString(String date, String style) {
		String str = null;
		try {
			Date dates = new SimpleDateFormat(_DEFAULT2).parse(date);
			str = formatDate(dates, style);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 将时间戳转换成时间String
	 */
	public static String longToString(long mill) {
		Date date = new Date(mill);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}
	
	/**
	 * 将时间戳转换成时间String
	 */
	public static String longToString(long mill,String style) {
		Date date = new Date(mill);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(style);
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		if (StringUtils.toLong(str) != null && StringUtils.toLong(str) != 0) {
			str = longToString(StringUtils.toLong(str));
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * @title : 计算两个时间差
	 * @param ：
	 *            String startTime
	 * @param :
	 *            String endtime
	 */
	public static String getTimeDifference(long startTime, long endtime) {
		String result = "";
		try {
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// Date d1 = df.parse("2016-05-23 15:00:00");
			//
			// Date d2 = df.parse("2016-06-22 11:00:00");
			long diff = startTime - endtime;// 这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);

			long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
			if (days != 0) {
				result = result.concat(days + "天后");
				return result;
			}
			if (hours != 0) {
				result = result.concat(hours + "小时后");
				return result;
			}
			if (minutes != 0) {
				result = result.concat(minutes + "分钟后");
				return result;
			} else if (minutes == 0) {
				result = result.concat("即将开始");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @title : 计算两个时间差
	 * @param ：
	 *            String startTime
	 * @param :
	 *            String endtime
	 */
	public static String getTimeDifferenceFront(long startTime, long endtime) {
		String result = "";
		try {
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// Date d1 = df.parse("2016-05-23 15:00:00");
			//
			// Date d2 = df.parse("2016-06-22 11:00:00");
			long diff = startTime - endtime;// 这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);

			long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
			if (days != 0) {
				result = result.concat(days + "天前");
				return result;
			}
			if (hours != 0) {
				result = result.concat(hours + "小时前");
				return result;
			}
			if (minutes != 0) {
				result = result.concat(minutes + "分钟前");
				return result;
			} else if (minutes == 0) {
				result = result.concat("刚刚");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 几天前的时间 long
	 */
	public static String getBeforeDay(String style, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		Date date = new Date();
		long time = (date.getTime() / 1000) - 60 * 60 * 24 * day;
		date.setTime(time * 1000);

		Date d = getStringDate(sdf.format(date), style);
		return d.getTime() + "";
	}

	/**
	 * 计算xx时间前(day为负数)或后(day为正数)的long型时间戳
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static String getDayPrevDateLongTime(Date date, int day) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, day);
		return c.getTimeInMillis() + "";
	}

	/**
	 * 计算从long开始到现在过去天数，只计算年月日，忽略时分秒毫秒
	 * 
	 * @param times
	 * @return
	 */
	public static long getDayForLongTime(long times) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(times);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return pastDays(c.getTime());
	}

	/**
	 * 整数(秒数)转换为时分秒格式(xx:xx:xx)
	 * 
	 * @param time
	 * @return
	 */
	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// System.out.println(formatDate(parseDate("2010/3/6")));
		// System.out.println(getDate("yyyy年MM月dd日 E"));
		// long time = new Date().getTime()-parseDate("2012-11-19").getTime();
		// System.out.println(time/(24*60*60*1000));
		Long str = 1479973614475L;
		Date date = new Date(str);
		//System.out.println(date.toString());

		System.err.println(parseDate("1479973614475"));
	}
}
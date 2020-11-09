package com.honestpeak.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: DateTimeUtils
 * @Description: 时间参数工具类，用做日期转换
 * @author Jeabev
 * @date 2016年8月5日 上午11:00:40
 */
public class DateTimeUtils {

	/**
	 * 根据日期时间点 获取对应毫秒数
	 * 
	 * @param date
	 *            eg. 2015-08-01 16:00:00
	 * @return
	 */
	public static long getMillisByString(String date) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(d);
		return c.getTimeInMillis();
	}
	/**
	 * @Title: getSecondByString
	 * @Description:根据时间点获取对应的秒数
	 * @param date
	 * @return
	 */
	public static long getSecondByString(String date) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(d);
		return c.getTimeInMillis();
	}
	/**
	 * 根据日期时间点 获取对应毫秒数
	 * @param date 格式 yyyy-MM-dd
	 * @return
	 */
	public static long getMillisByDateString(String date) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(d);
		return c.getTimeInMillis();
	}

	/**
	 * 根据字符型日期日期 获取对应毫秒数字符串
	 * 
	 * @param date
	 *            eg. 2015-08-01
	 * @return
	 */
	public static String getStringMillisByDateString(String date) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(d);
		return String.valueOf(c.getTimeInMillis());
	}
	
	/**
	 * @Description 根据字符型日期日期 获取对应毫秒数字符串
	 * @author ZongDouDou
	 * @param date eg.2017-06-20 10:20:30
	 * @return
	 */
	public static String getStringMillisByStringDate(String date) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(d);
		return String.valueOf(c.getTimeInMillis());
	}

	/**
	 * @Title: getStringMillisByDateString
	 * @Description: 根据日期date 获取对应毫秒数字符串
	 * @param date
	 * @return
	 */
	public static String getStringMillisByDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return String.valueOf(c.getTimeInMillis());
	}

	/**
	 * 将一个date的类型的日期，转换成 年-月-日 时:分:秒 类型的字符串
	 * 
	 * @param date
	 *            传入日期
	 * @return String 转换后的日期 yyyy-MM-dd HH:mm:ss
	 */
	public static String DateToDateTimeString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(date);
		return dateString;
	}
	/**
	 * @Title: DateToDateTimeString
	 * @Description: 传入指定日期，以及想要转换的时间格式。返回相应的字符串日期
	 * @param date 日期
	 * @param format 格式
	 * @return
	 * @exception NullPointerException 若给定参数为空，如date、format。则会抛出空指针异常
	 * @exception IllegalArgumentException 若给定format不符合规定，则会抛出非法参数异常
	 */
	public static String DateToDateTimeString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dateString = sdf.format(date);
		return dateString;
	}

	/**
	 * 将一个date的类型的日期，转换成 年月日时分秒 类型的字符串
	 * 
	 * @param date
	 *            传入日期
	 * @return String 转换后的日期 yyyyMMddHHmmss
	 */
	public static String DateToDateTimeWithOutDote(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = sdf.format(date);
		return dateString;
	}

	/**
	 * 把一个String类型的日期，转换成date
	 * 
	 * @param dateString
	 * @return Date
	 * @throws ParseException
	 */
	public static Date StringToDate(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateString);
		return date;
	}

	/**
	 * @Title: DateToString
	 * @Description: 把一个Date型日期，转换成"yyyy-MM-dd"字符串
	 * @param date
	 * @throws ParseException
	 */
	public static String DateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * @Title: timeMillisToDateString
	 * @Description: 将毫秒数转换成"yyyy-MM-dd HH:mm:ss"格式日期字符串
	 * @param millis
	 * @return
	 */
	public static String timeMillisToDateString(String millis) {
		Long mills = null;
		try {
			mills = Long.parseLong(millis);
		} catch (Exception e) {
			return "";
		}
		Date date = new Date(mills);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return sdf.format(date);
	}
	/**
	 * @Title: timeMillisToDateStringByFormat
	 * @Description: 指定格式将毫秒数转换成相应格式的时间
	 * @param millis 时间
	 * @param format 格式
	 * @return
	 */
	public static String timeMillisToDateStringByFormat(String millis,String format) {
		Long mills = null;
		try {
			mills = Long.parseLong(millis);
		} catch (Exception e) {
			return "";
		}
		Date date = new Date(mills);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		return sdf.format(date);
	}
	
	/**
	 * @Title: timeMillisToLongByFormat
	 * @Description: 指定格式将毫秒数转换成相应格式的时间
	 * @param millis 
	 * @param format
	 * @return 格式出错会返回0。
	 */
	public static Long timeMillisToLongByFormat(String millis,String format){
		Long mills = null;
		try {
			mills = Long.parseLong(millis);
		} catch (Exception e) {
			return 0l;	//出错返回0
		}
		Date date = new Date(mills);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		try {
			mills = Long.parseLong(sdf.format(date));
		} catch (Exception e) {
			mills = 0l;	//出错返回0
		}
		return mills;
	}
	
	/**
	 * @Title: timeMillisToDate
	 * @Description: 毫秒数转date
	 * @param millis
	 * @return
	 */
	public static Date timeMillisToDate(String millis) {
		return new Date(Long.parseLong(millis));
	}
	
	

	/**
	 * 传入一个日期date,获取其中学年信息
	 * 
	 * @param date
	 *            日期
	 * @return String year 学年
	 */
	public static String getCourseYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int mounth = c.get(Calendar.MONTH);
		String courseYear = "";
		if (mounth >= 0 && mounth <= 6) {
			courseYear = (year - 1) + "-" + year;
		} else if (mounth >= 7 && mounth <= 11) {
			courseYear = (year) + "-" + (year + 1);
		}
		return courseYear;
	}

	/**
	 * 传入一个日期date,获取其中的月份（0-11）
	 * 
	 * @param date
	 *            日期
	 * @return month
	 */
	public static String getTerm(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int mounth = c.get(Calendar.MONTH);

		int term = 1;
		if (mounth >= 1 && mounth <= 6) {
			term = 2;
		} else if ((mounth >= 7 && mounth <= 11) || (mounth == 0)) {
			term = 1;
		}
		return String.valueOf(term);
	}
	
	/**
	 * 获取指定日期前一天
	 * @param date
	 * @return
	 */
	public static Date getPreDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * @Title: compareDate
	 * @Description: 比较两个Date是否在同一天
	 * @param stDate
	 * @param date
	 * @return
	 */
	public static boolean compareDate(Date stDate, Date date) {
		if(stDate !=null ){
			if(date!=null && DateToString(stDate).equals(DateToString(date))){
				return true;
			}
		} else {
			if(date==null){
				return true;
			}
		}
		return false;
	}
	/**
	 * @Title: getCurrentCourseYearStart
	 * @Description: 获取传入日期的学期开始日期
	 * @param date
	 * @return
	 */
	public static String getCurrentCourseYearStart(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int mounth = c.get(Calendar.MONTH);

		StringBuffer start = new StringBuffer();
		if (mounth >= 1 && mounth <= 6) {
			start.append(year+"-02-01");
		} else if ((mounth >= 7 && mounth <= 11) || (mounth == 0)) {
			year = year-1;//注意年份需要减一
			start.append(year+"-08-01");
		}
		return start.toString();
	}

}

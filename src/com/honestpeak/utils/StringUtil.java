package com.honestpeak.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StringUtil {

	public final static String APP_KEY = "25wehl3uwnekw";
	public final static String APP_SECRET = "uofPwtOUKjikR";
	public final static String PASSWORD="123456";
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 99999;

	public static synchronized long getTourist() {
		if (seq > ROTATION)
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);
		return Long.parseLong(str);
	}

	/**
	 * get the datetime string
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getStrDateTime() {
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * @title getStrDate
	 * @description TODO
	 * @return String yyyy-MM-dd
	 * @return
	 * @author JinKai
	 * @date 2017年3月15日  下午3:22:39
	 * @version 1.0
	 */
	public static String getStrDate() {
		Format format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}

	public static String getPreDayDate(int reduceDay) {
		Date dnow = new Date();
		Date dPre = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dnow);
		calendar.add(Calendar.DAY_OF_MONTH, -reduceDay);
		dPre = calendar.getTime();

		Format format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(dPre);
	}

	public static boolean isEmpty(String data) {
		if (null == data || "".equals(data.trim()) || "null".equals(data.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * android : 所有android设备 mac os : iphone ipad windows
	 * phone:Nokia等windows系统的手机
	 */
	public static boolean isMobileDevice(String requestHeader) {
		String[] deviceArray = new String[] { "android", "iphone", "windows phone", "ipad", "mqqbrowser" };
		if (requestHeader == null)
			return false;
		requestHeader = requestHeader.toLowerCase();
		for (int i = 0; i < deviceArray.length; i++) {
			if (requestHeader.indexOf(deviceArray[i]) > -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param code
	 * @return
	 * @author JinKai
	 * @date 2016年8月2日  下午3:20:24
	 * @version 1.0
	 */
	public static String getCode(String code) {
		
		String prefix = code.replaceAll("[0-9]", "");
		String numString = code.replaceAll("[A-Z]", "");
		Integer number = Integer.parseInt(numString) + 1;
		StringBuffer suffix = new StringBuffer();
		for(int i = 0; i<numString.length()-number.toString().length(); i++){
			suffix.append("0");
		}
		suffix.append(number.toString());
		
		return prefix + suffix.toString();
	}

	/**
	 * @return 获取当年年份的字符串
	 * @author JinKai
	 * @date 2016年8月2日  下午3:58:13
	 * @version 1.0
	 */
	public static String getCurrentYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		return year+"";
	}
	/**
	 * @return 获取当月份的字符串
	 * @author JinKai
	 * @date 2016年8月2日  下午3:58:13
	 * @version 1.0
	 */
	public static String getCurrentMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH )+1;
		return month+"";
	}
	
	
	/**
	 * @title isWindows
	 * @description 判断系统是否是windows
	 * @return boolean 返回类型
	 * @return
	 * @author JinKai
	 * @date 2016年10月18日  下午5:36:04
	 * @version 1.0
	 */
	public static boolean isWindows(){
		String property = System.getProperty("os.name");
		return property.toLowerCase().matches("windows[ 0-9a-zA-Z.]+");
	}
	
	public static String getDateTimeToFileName(){
		Format format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		return format.format(new Date());
	}
	
	
	/**
	 * @Title: getYear
	 * @Description: 获取当前学年
	 * @return String 返回类型
	 * @author zzy
	 * @throws
	 * @return
	 */
	public static String getYear(){
		
		return StringUtil.getCurrentYear()+"-"+(Integer.parseInt(StringUtil.getCurrentYear())+1);
	}

	
	/**
	 * @Title: getRootLastString
	 * @Description: 判断字符串最后一位是什么字符
	 * @return Boolean 返回类型
	 * @author zzy
	 * @throws
	 * @param r
	 * @return
	 */
	public static Boolean getRootLastString(String r){
		
		if(r.equals("s")){
			
			return false;
		}
		else if(r.equals("x")){
			return true;
			
		}else{
			
			return null;
		}
		
	}
}

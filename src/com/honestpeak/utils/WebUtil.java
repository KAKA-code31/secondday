package com.honestpeak.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtil {


	/**
	* 删除Html标签
	* @param inputString
	* @return
	*/
	public static String removeHtmlTag(String inputString) {
		if (inputString == null)
			return null;
			String htmlStr = inputString; // 含html标签的字符串
			String textStr = "";
			java.util.regex.Pattern p_script;
			java.util.regex.Matcher m_script;
			java.util.regex.Pattern p_style;
			java.util.regex.Matcher m_style;
			java.util.regex.Pattern p_html;
			java.util.regex.Matcher m_html;
			java.util.regex.Pattern p_special;
			java.util.regex.Matcher m_special;
			try {
				//定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
				String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
				//定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
				String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
				// 定义HTML标签的正则表达式
				String regEx_html = "<[^>]+>";
				// 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				String regEx_special = "\\&[a-zA-Z]{1,10};";
			
				p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
				m_script = p_script.matcher(htmlStr);
				htmlStr = m_script.replaceAll(""); // 过滤script标签
				p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
				m_style = p_style.matcher(htmlStr);
				htmlStr = m_style.replaceAll(""); // 过滤style标签
				p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
				m_html = p_html.matcher(htmlStr);
				htmlStr = m_html.replaceAll(""); // 过滤html标签
				p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
				m_special = p_special.matcher(htmlStr);
				htmlStr = m_special.replaceAll(""); // 过滤特殊标签
				textStr = htmlStr;
			} catch (Exception e) {
				e.printStackTrace();
			}
		return textStr;// 返回文本字符串
	}
	
	/**
	 * 将存入时带有html标签的字符串中的标签去掉
	 * @param htmlStr 时带有html标签的字符串
	 * @return
	 */
	public static String delHtmlTag(String htmlStr) {
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签
		String str = htmlStr.replaceAll("&nbsp;", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				.replaceAll("&amp;", "&").replaceAll("&quot;", "\"").replaceAll("&apos;", "\'")
				.replaceAll("&ldquo;", "“").replaceAll("&rdquo;", "”").replaceAll("&lsquo;", "‘")
				.replaceAll("&rsquo;", "’");
		return str;
	}
	
	/**
	 * 根据日期时间点 获取对应毫秒数
	 * @param date   eg. 2015-08-01 16:00:00
	 * @return
	 */
	public static long getMillisByString(String date){
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
	 * 将一个date的类型的日期，转换成String
	 * @param date 传入日期
	 * @return String 转换后的日期  yyyy-MM-dd
	 */
	public static String DateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(date);
		return dateString;
	}
	/**
	 * 将一个date的类型的日期，转换成  年-月-日 时:分:秒 类型的字符串
	 * @param date 传入日期
	 * @return String 转换后的日期  yyyy-MM-dd HH:mm:ss
	 */
	public static String DateToDateTimeString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(date);
		return dateString;
	}
	/**
	 * 将一个date的类型的日期，转换成 时:分:秒 类型的字符串
	 * @param date 传入日期
	 * @return String 转换后的日期  yyyy-MM-dd HH:mm:ss
	 */
	public static String DateToTimeString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String dateString = sdf.format(date);
		return dateString;
	}
	/**
	 * 将一个date的类型的日期，转换成  年-月-日 时:分:秒 类型的字符串
	 * @param date 传入日期
	 * @return String 转换后的日期  yyyyMMddHHmmss
	 */
	public static String DateToDateTimeWithOutDote(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = sdf.format(date);
		return dateString;
	}
	
	/**
	 * 把一个String类型的日期，转换成date
	 * @param dateString
	 * @return Date 
	 * @throws ParseException
	 */
	public static Date StringToDate(String dateString) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateString);
		return date;
	}
	/**
	 * 传入一个日期date,获取其中的年份
	 * @param date
	 * @return int year
	 */
	public static int getYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		return year;
	}
	/**
	 * 传入一个日期date,获取其中学年信息
	 * @param date 日期
	 * @return String year 学年
	 */
	public static String getCourseYear(Date date){
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
	 * 传入一个日期date,获取其中年份信息
	 * @param date 日期
	 * @return String year 年份
	 */
	public static String getCurrentYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		return year+"";
	}
	/**
	 * 传入一个日期date,获取其中的月份（0-11）
	 * @param date 日期
	 * @return month
	 */
	public static int getMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		return month;
	}
	
	
	/**
	 * 传入一个日期date,获取其中的月份（0-11）
	 * @param date 日期
	 * @return month
	 */
	public static int getTerm(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int mounth = c.get(Calendar.MONTH);
		
		int term = 1;
		if (mounth >= 1 && mounth <= 6) {
			term = 2;
		} else if ((mounth >= 7 && mounth <= 11) || (mounth == 0)) {
			term = 1;
		}
		
		return term;
	}
	

	/**
	 * @return 返回一个随机生成的支出账单编号
	 */
	public static String makeFinanceId(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//hhmmss
		String financeId = sdf.format(new Date());
		long roodom = Math.round(Math.random()*(99-10)+10);
		financeId += roodom;
		return financeId;
	}
	
	/**
	 * 通过毫秒数，获取到该毫秒数对应的时间
	 * @param millis 毫秒数
	 * @return  String "YYYY-MM-dd HH:mm:ss" 字符型时间
	 */
	public static String getDateStringByMillis(Long millis){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		Date date = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 通过毫秒数，获取到该毫秒数对应的时间
	 * @param millis 毫秒数
	 * @return  String "YYYY-MM-dd HH:mm:ss" 字符型时间
	 */
	public static String getTimeStringByMillis(Long millis){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		Date date = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("mm分ss秒");
		return sdf.format(date);
	}
	
	/**
	 * 通过毫秒数，获取到该毫秒数对应的日期
	 * @param millis
	 * @return date 该毫秒数对应的date
	 */
	public static Date getDateByMillis(Long millis){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		return c.getTime();
	}
	
	/**
	 * @param filename 文件名
	 * @param savepath 文件保存目录
	 * @return 已创建文件保存路径
	 */
	public static String makePath(String filename,String savepath){
		int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;//拿到文件的1-4位
		int dir2 = (hashcode&0xf0)>>4;//拿到文件的5-8位
		String dir = savepath+File.separator+dir1+dir2+File.separator;//+"\\"
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}
	/**
	 * @Title: makePath
	 * @Description: 保存文件时生成文件保存路径的hash码
	 * @param filename 文件名
	 * @param systemPath 系统路径
	 * @param savepath 项目内保存路径文件夹 如：/upload/attach
	 * @return String 返回类型
	 */
	public static String makePath(String filename,String systemPath,String savepath){
		int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;//拿到文件的1-4位
		int dir2 = (hashcode&0xf0)>>4;//拿到文件的5-8位
		String dir = savepath+File.separator+dir1+dir2+File.separator;//+"\\"
		File file = new File(systemPath+dir);
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}
	
	/**
	 * @return 唯一的标识
	 */
	public static String makeId(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 由当前日期，判断当前学期开始日期
	 * @param date
	 * @return YYYY-MM-DD
	 */
	public static String getCourseStartDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int mounth = c.get(Calendar.MONTH);
		if (mounth >= 1 && mounth <= 6) {//2-7月 本年的第一学期
			return c.get(Calendar.YEAR)+"-2-01";
		} else if ( mounth >= 7 && mounth <= 11 ) {//8-12月 本年的第二学期
			return c.get(Calendar.YEAR)+"-08-01";
		} else {//mounth==0 一月份属于上一年的第二学期
			return (c.get(Calendar.YEAR)-1)+"-08-01";
		}
	}
	/**
	 * 由当前日期，判断当前学期结束日期
	 * @param date
	 * @return YYYY-MM-DD
	 */
	public static String getCourseEndDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int mounth = c.get(Calendar.MONTH);
		if (mounth >= 1 && mounth <= 6) {//2-7月 本年的第一学期
			return c.get(Calendar.YEAR)+"-07-31";
		} else if ( mounth >= 7 && mounth <= 11 ) {//8-12月 本年的第二学期
			return (c.get(Calendar.YEAR)+1)+"-01-31";
		} else {//mounth==0 一月份属于上一年的第二学期
			return c.get(Calendar.YEAR)+"-08-01";
		}
	}
	
	/**
	 * 通过String类型的date 获取到传入时间的毫秒数
	 * @param date yyyy-MM-dd
	 * @return 毫秒数
	 */
	public static Long getTimeByStringDate(String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateTime = format.parse(date);
			return dateTime.getTime();
		} catch (ParseException e) {
			return null;
		}
	} 
	
	/**
	 * @Title: isWindowsOS
	 * @Description: 返回当前操作系统是否为windows
	 * @return boolean 返回类型
	 */
	public static boolean isWindowsOS(){

		String os = System.getProperties().getProperty("os.name");

		if(os.startsWith("win") || os.startsWith("Win")){
			return true;
		}else {
			return false;
		}
	}
}

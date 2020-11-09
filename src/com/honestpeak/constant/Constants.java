package com.honestpeak.constant;

/**
 * @ClassName: Constants
 * @Description: 系统公共参数设置列表
 * @author Jeabev
 * @date 2017年3月28日 下午1:48:04
 */
public class Constants {
	/**
	 * 系统生成key允许的延迟时间，这里采用 HHmmss -> Long。转换后的long型数字要把前面的0去除,
	 * 那么00:30:00 对应的数值就是 3000。17:25:36对应的数值就是172536。在这个时间之前，系统解密时，将取出前一天作为key。
	 * 并在解密时，优先采用前一天的key
	 */
	public static final long KEY_SYSTEM_MISSTIME = 3000;
	
	/**
	 * 前台列表文本字段最多显示的字符数目
	 */
	public static final int CONTENT_FRON_LENGTH = 65;
	
	/**
	 * 会员注册状态：待审核状态
	 */
	public static final int CHECK_AUDIT=1;
	/**
	 * 会员注册状态：审核通过状态
	 */
	public static final int CHECK_PASS=2;
	/**
	 * 会员注册状态：审核未通过状态
	 */
	public static final int CHECK_FAIL=3;
	/**
	 * 初级会员
	 */
	public static final int CLUB_MEMBER_JUNIOR=1;
	/**
	 * 中级会员
	 */
	public static final int CLUB_MEMBER_MIDDLE=2;
	/**
	 * 高级会员
	 */
	public static final int CLUB_MEMBER_SENIOR=3;
	
	/**
	 * 学生物理卡号长度，字节数（16进制）
	 */
	public static final int CL_STUDENTCARDCD = 8;
	
	/**
	 * 学生学号长度，字节数
	 */
	public static final int CL_USERIDCD = 10;
	
	/**
	 * 学生姓名长度，字节数
	 */
	public static final int CL_NAMECD = 10;
	
	/**
	 * 学生姓名结尾，字符
	 */
	public static final String CL_XMJW = "\n\r";
	
	/**
	 * 数据库读取学生姓名  编码集，在给新版 考勤机提供学生信息数据 接口中使用。<br>
	 * Value = UTF-8
	 */
	public static final String SYSTEM_CHARSET = "UTF-8";
	
	/**
	 * 新版考勤机需要的姓名编码格式<br>
	 * Value = GB2312
	 */
	public static final String MACHINE_CHARSET = "GBK";
	
	/**
	 * mysql保存list时，一次性最大保存数目，10000
	 */
	public static final int MAX_SAVE_LIST_SIZE = 10000;
	
	/**
	 * 考勤机固件信息错误
	 */
	public static final String MACHINE_SYSTEM_NOTFOUND = "systemnotload.so";
	
	
	/**
	 * 课程性质  课程  正常 1
	 */
	public static String COURSE_NORMAL = "1";
	/**
	 * 课程性质  课程  重修 2
	 */
	public static String COURSE_REBUILD = "2";
	
	/**
	 * 俱乐部视频  审核状态   待审0
	 */
	public static String CHECK_PEND = "0";
	
	/**
	 * 俱乐部视频  审核状态  通过1
	 */
	public static String CHECK_PASSD = "1";
	
	/**
	 * 初始化日志  类型  0：系统日志，1：课程安排,2：学生导入
	 */
	public static int INITLOG_SYSTEM = 0;
	public static int INITLOG_SCHEDULE = 1;
	public static int INITLOG_IMPORTSTUDNET = 2;
	public static int INITLOG_SCOPESTUDENT = 3;
	/**
	 * 日志类型
	 */
	public static int INIT_TYPE_SYSTEM = 0; 
	public static int INIT_TYPE_SCHEDULE = 1;
	public static int INIT_TYPE_STUDENT = 2;
	
	public static int INIT_TYPE_SUPERVISOR=4;
	
	public static final String DEFAULT_PASSWORD="111333";
}

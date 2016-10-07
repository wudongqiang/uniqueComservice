
package com.unique.alipay.util;

import java.util.Date;
import java.util.Random;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/* *
 *类名：UtilDate
 *功能：自定义订单类
 *详细：工具类，可以用作获取系统日期、订单编号等
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class UtilDate {
	
    /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
    public static final String dtLong                  = "yyyyMMddHHmmss";
    
    /** 完整时间 yyyy-MM-dd HH:mm:ss */
    public static final String simple                  = "yyyy-MM-dd HH:mm:ss";
    
    /** 年月日格式 yyyy-MM-dd */
    public static final String dtDate                  = "yyyy-MM-dd";
    
    /** 年月日(无下划线) yyyyMMdd */
    public static final String dtShort                 = "yyyyMMdd";
    
    /** 年月 yyyy-MM */
    public static final String dtMonth                 = "yyyy-MM";
    /** 时分的格式 */
    public static final String dtHm                 = "HH:mm";
	
    
    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     *      以yyyyMMddHHmmss为格式的当前系统时间
     */
	public  static String getOrderNum(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtLong);
		return df.format(date);
	}
	
	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public  static String getDateFormatter(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(simple);
		return df.format(date);
	}
	
	/**
	 * 获取系统当前日期(年月日)，格式：yyyy-MM-dd
	 * @return
	 */
	public  static String getCurrentDate(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtDate);
		return df.format(date);
	}
	
	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtShort);
		return df.format(date);
	}
	
	/**
	 * 获取系统当期年月，格式：yyyy-MM
	 * @return
	 */
	public static String getMonth(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtMonth);
		return df.format(date);
	}
	
	/**
	 * 根据日期格式转换字符串为日期对象
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 */
	public static Date getStrToDate(String dateStr,String dateFormat){
		DateFormat df=new SimpleDateFormat(dateFormat);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	/**
	 * 日期对象转换字符串
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 */
	public static String getDateToStr(Date date,String dateFormat){
		DateFormat df=new SimpleDateFormat(dateFormat);
		return df.format(date);
	}
	
	/**
	 * 产生随机的三位数
	 * @return
	 */
	public static String getThree(){
		Random rad=new Random();
		return rad.nextInt(1000)+"";
	}
}

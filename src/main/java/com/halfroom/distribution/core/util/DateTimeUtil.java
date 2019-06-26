package com.halfroom.distribution.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;

public class DateTimeUtil {

	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtil.DEFAULT_FORMAT_DATE);
	private static byte[] lock = new byte[0];
	public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";
	public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";
	public static final String DEFAULT_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	
	
	private final static SimpleDateFormat SDF_YEAR = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat SDF_DAY = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat SDF_DAYS = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat SDF_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private final static SimpleDateFormat SDFMS_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	private final static SimpleDateFormat ALL_TIME = new SimpleDateFormat("yyyyMMddHHmmss");
	/** The int EXPIRE_SECOND */
	public static final int EXPIRE_SECOND = 1;
	/** The int EXPIRE_MINUTE */
	public static final int EXPIRE_MINUTE = EXPIRE_SECOND * 60;
	/** The int EXPIER_HOUR */
	public static final int EXPIER_HOUR = EXPIRE_MINUTE * 60;
	/** The int EXPIRE_DAY */
	public static final int EXPIRE_DAY = EXPIER_HOUR * 24;
	/** The int EXPIRE_WEEK */
	public static final int EXPIRE_WEEK = EXPIRE_DAY * 7;
	/** The int EXPIRE_YEAR */
	public static final int EXPIRE_YEAR = EXPIRE_DAY * 365;
	/** The int EXPIRE_DEFAULT */
	public static final int EXPIRE_DEFAULT = EXPIER_HOUR * 3;

	/**
	 * 格式化时间
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTime(Date time, String pattern) {
		if (time == null) {
			return StringUtils.EMPTY;
		}
		synchronized (lock) {
			if (pattern != null && !DateTimeUtil.DEFAULT_FORMAT_DATE.equals(pattern)) {
				return new SimpleDateFormat(pattern).format(time);
			}
			return dateFormat.format(time);
		}
	}

	/**
	 * 将字符串格式时间戳转换为年月日的格式
	 * @param timestamp
	 * @return
	 */
	public static String getDateStr(String timestamp){
		if (timestamp == null) {
			return StringUtils.EMPTY;
		}
		
		try {
			DateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT_DATE_TIME);
			Date datetime = dateFormat.parse(timestamp);
			synchronized (lock) {
				return new SimpleDateFormat(DEFAULT_FORMAT_DATE).format(datetime);
			}
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}
	
	public static String getDateTimeStr(String timestamp){
		if (timestamp == null) {
			return StringUtils.EMPTY;
		}
		
		try {
			DateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT_DATE_TIME);
			Date datetime = dateFormat.parse(timestamp);
			synchronized (lock) {
				return new SimpleDateFormat(DEFAULT_FORMAT_DATE_TIME).format(datetime);
			}
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static String getCurrentTimestamp(){
		return formatTime(new Date(), DEFAULT_FORMAT_DATE_TIME);
	}
	public static String  getCurrentTimestampForT(){
		String str=System.currentTimeMillis()+"";
		str=str.substring(0, str.length()-3);
		return str;
	}
	/**
	 * 格式化时间 到日
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTimeDay(Date time) {
		if (time == null) {
			return StringUtils.EMPTY;
		}
		synchronized (lock) {
			return dateFormat.format(time);
		}
	}
	/**
	 * 格式化时间 到秒
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTimeScend(Date time) {
		if (time == null) {
			return StringUtils.EMPTY;
		}
		synchronized (lock) {
		
			return new SimpleDateFormat(DateTimeUtil.DEFAULT_FORMAT_DATE_TIME).format(time);
		}
	}

	/**
	 * 解析时间
	 * 
	 * @param time
	 * @return
	 */
	public static Date parseTime(String dateTimeStr, String pattern) {

		if (StringUtils.isEmpty(dateTimeStr)) {
			return null;
		}

		try {
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.parse(dateTimeStr);
		} catch (Exception e) {
			return null;
		}

	}
	/**
	 * 添加指定的小时，返回添加好的日期 如果为负则是减去
	 * 
	 * @param start
	 * @param i
	 * @return
	 */
	public static Date addHour(Date startDate, int hour) {
		synchronized (lock) {
			calendar.setTime(startDate);
			calendar.add(Calendar.HOUR_OF_DAY, hour);
			return calendar.getTime();
		}
	}
	
	/**
	 * 添加指定的小时，返回添加好的日期 如果为负则是减去
	 * 
	 * @param start
	 * @param i
	 * @return
	 */
	public static Date addSecond(Date startDate, int second) {
		synchronized (lock) {
			calendar.setTime(startDate);
			calendar.add(Calendar.SECOND, second);
			return calendar.getTime();
		}
	}

	/**
	 * 添加指定的天数，返回添加好的日期 如果为负则是减去
	 * 
	 * @param start
	 * @param i
	 * @return
	 */
	public static Date addDay(Date startDate, int day) {
		synchronized (lock) {
			calendar.setTime(startDate);
			calendar.add(Calendar.DAY_OF_YEAR, day);
			return calendar.getTime();
		}
	}
	
	/**
	 * 添加指定周数
	 * 
	 * @param start
	 * @param i
	 * @return
	 */
	public static Date addWeek(Date startDate, int week) {
		synchronized (lock) {
			calendar.setTime(startDate);
			calendar.add(Calendar.WEEK_OF_YEAR, week);
			return calendar.getTime();
		}
	}
	
	/**
	 * 添加指定月数
	 * @param startDate
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date startDate, int month){
		synchronized (lock) {
			if(startDate == null){
				calendar.setTime(new Date());
			}
			else{
				calendar.setTime(startDate);
			}
			calendar.add(Calendar.MONTH, month);
			return calendar.getTime();
		}
	}
	
	
	/**
	 * 添加指定年数
	 * @param startDate
	 * @param month
	 * @return
	 */
	public static Date addYear(Date startDate, int year){
		synchronized (lock) {
			if(startDate == null){
				calendar.setTime(new Date());
			}
			else{
				calendar.setTime(startDate);
			}
			calendar.add(Calendar.YEAR, year);
			return calendar.getTime();
		}
	}
	/**
	 * 获取指定日期所在月份的起始时间
	 * @param date
	 * @return
	 */
	public static Date getMonthStart(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01  00:00:00");  
		String gtime = sdf.format(date.getTime());
		return parseTime(gtime, DEFAULT_FORMAT_DATE_TIME);
	}
	
	/**
	 * 获取当前时间的前一个月
	 * @return
	 */
	public static Date getBeforeMonth(){
		return addMonth(new Date(), -1);
	}
	
	/**
	 * 获取上个月的起始时间
	 * @return
	 */
	public static Date getBeforeMonthStart(){
		return getMonthStart(addMonth(new Date(), -1));
	}
	
	/**
	 * 获取本月的起始时间
	 * @return
	 */
	public static Date getThisMonthStart(){
		return getMonthStart(new Date());
	}
	
	/**
	 * 获取当前时间的后一个月
	 * @return
	 */
	public static Date getAfterMonth(){
		return addMonth(new Date(), 1);
	}
	
	
	//得到一天的开始一刻
	public static Date getStartTimeOfDay(Date day) {
		synchronized (lock) {
			calendar.setTime(day);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 000);
			return calendar.getTime();
		}
	}

	
	//得到一天的最后一刻
	public static Date getEndTimeOfDay(Date day) {
		synchronized (lock) {
			calendar.setTime(day);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			return calendar.getTime();
		}
	}
	// 获得当前日期与本周一相差的天数
	public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }
	// 获得当前周--周一的日期
	public static String getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        return SDF_DAY.format(monday);
    }
 
	// 获得当前周--周日的日期
	public static String getPreviousSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date sunday = currentDate.getTime();
        return SDF_DAY.format(sunday);
    }
	// 获得当前月--开始日期
	public static String getMinMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return SDF_DAY.format(calendar.getTime());
    }
 
    // 获得当前月--结束日期
	public  static String getMaxMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return SDF_TIME.format(calendar.getTime());
    }
	/**
	 * 获取当前日期的小时
	 * @param day
	 * @return
	 */
	public static int getHour(Date date) {
		synchronized (lock) {
			calendar.setTime(date);
			return calendar.get(Calendar.HOUR_OF_DAY);
		}
	}
	
	/**
	 * 获取当前的月
	 * @param day
	 * @return
	 */
	public static int getMonth(Date date) {
		synchronized (lock) {
			calendar.setTime(date);
			return calendar.get(Calendar.MONTH) + 1;
		}
	}
	
	public static int getYear(Date date) {
		synchronized (lock) {
			calendar.setTime(date);
			return calendar.get(Calendar.YEAR);
		}
	}
	/**
	 * 获取当前的月的第几天
	 * @param day
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		synchronized (lock) {
			calendar.setTime(date);
			return calendar.get(Calendar.DAY_OF_MONTH);
		}
	}
	/**
	 * 获取当前date的分钟
	 * @param day
	 * @return
	 */
	public static int getMinuteOfDate(Date date) {
		synchronized (lock) {
			calendar.setTime(date);
			return calendar.get(Calendar.MINUTE);
		}
	}
	
	/**
	 *	获取当前时间的 周几
	 * @param day
	 * @return
	 */
	public static int getDay(Date date) {
		synchronized (lock) {
			calendar.setTime(date);
			return calendar.get(Calendar.DAY_OF_WEEK);
		}
	}

	/**
	 * 根据出生日期计算年龄
	 * 
	 * @param birthDay
	 * @return 未来日期返回0
	 * @throws Exception
	 */
	public static int getAge(Date birthDay){
		if (birthDay == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			return 0;
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}
	
	/**
     * date2比date1多的天数
     * @param date1    
     * @param date2
     * @return    
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date parseDate(String date,String FORMAT) {
		try {
		     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT);
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	//获取某段时间内的所有日期
	public static List<Date> findDates(Date dStart, Date dEnd) {
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(dStart);

		List dateList = new ArrayList();
		//别忘了，把起始日期加上
		dateList.add(dStart);
		// 此日期是否在指定日期之后
		while (dEnd.after(cStart.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cStart.add(Calendar.DAY_OF_MONTH, 1);
			dateList.add(cStart.getTime());
		}
		return dateList;
	}
}

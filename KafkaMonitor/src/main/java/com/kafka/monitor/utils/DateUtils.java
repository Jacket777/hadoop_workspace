/*
 * Copyright (C), 2002-2016, 苏宁易购电子商务有限公司
 * FileName: DateUtils.java
 * Author:   14082455
 * Date:     2016年9月10日 上午11:45:14
 * Description: 日期处理utils
 */
package com.kafka.monitor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期处理utils<br>
 *
 *
 */
public class DateUtils {

    public static String formatDateTime(Date dateTime) {
        if (dateTime == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(dateTime);
    }

    public static String formatDateTime(Date dateTime, String format) {
        if (dateTime == null) {
            return "";
        }

        if (format == null) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(dateTime);
    }

    public static Date getDateByString(String dateTime) throws ParseException {
        if (null == dateTime || dateTime.trim().length() == 0) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(dateTime);
    }

    public static Date getDateByString(String dateTime, String format) throws ParseException {
        if (null == dateTime || dateTime.trim().length() == 0) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.parse(dateTime);
    }

    public static int getHours(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutes(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    public static int getSeconds(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    }

    /**
     * 功能描述: <br>
     * 获取与今天相隔interval天日期
     *
     * @param date     当天时间
     * @param interval 相差天数
     * @return 相隔interval天的日期
     */
    public static Date getIntervalDay(Date date, int interval) {
        Calendar cal = Calendar.getInstance();
        if (null != date) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_MONTH, interval);
        resetDay(cal);
        return cal.getTime();
    }

    /**
     * 功能描述: <br>
     * 获取与今天相隔interval天日期
     *
     * @param interval 相差天数
     * @return 相隔interval天的日期
     */
    public static Date getIntervalDay(int interval) {
        return getIntervalDay(null, interval);
    }

    /**
     * 功能描述: <br>
     * 获取与今天相隔interval天日期
     *
     * @param date     当天时间
     * @param interval 相差天数
     * @return 相隔interval天的日期
     */
    public static Date getIntervalDayTime(Date date, int interval) {
        Calendar cal = Calendar.getInstance();
        if (null != date) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_MONTH, interval);
        return cal.getTime();
    }

    public static Date getIntervalMonthTime(Date date, int interval) {
        Calendar cal = Calendar.getInstance();
        if (null != date) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, interval);
        return cal.getTime();
    }

    /**
     * 功能描述: <br>
     * 获取当天0点日期
     *
     * @return 当天时间
     */
    public static Date getTodayDay() {
        Calendar today = Calendar.getInstance();
        resetDay(today);
        return today.getTime();
    }

    /**
     * 功能描述: <br>
     * 计算今天所有分钟
     *
     * @return 日期List
     */
    public static List<Date> getMinuteBatchTime(int frequency) {
        return getMinuteBatchTime(frequency, null);
    }

    /**
     * 功能描述: <br>
     * 计算今天所有分钟
     *
     * @return 日期List
     */
    public static List<Date> getMinuteBatchTime(int frequency, Date batchTime) {
        List<Date> dateList = new ArrayList<>(24);
        if (frequency <= 0 || frequency >= 60) {
            return dateList;
        }

        Calendar startCal = Calendar.getInstance();
        if (batchTime != null)
            startCal.setTime(batchTime);
        DateUtils.resetDay(startCal);

        Calendar endCal = Calendar.getInstance();
        if (batchTime != null)
            endCal.setTime(batchTime);
        endCal.add(Calendar.DAY_OF_MONTH, 1);

        DateUtils.resetDay(endCal);
        Calendar thisDay = null;
        while (startCal.getTimeInMillis() < endCal.getTimeInMillis()) {
            thisDay = Calendar.getInstance();
            thisDay.setTime(startCal.getTime());
            dateList.add(thisDay.getTime());
            startCal.add(Calendar.MINUTE, frequency);
        }
        return dateList;
    }

    public static Date getNextMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        resetDay(calendar);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }


    /**
     * 功能描述: <br>
     * 获取最近n*月的日期
     *
     * @return 日期List
     */
    public static List<Date> getLastNMonth(Date batchTime, int frequency) {
        List<Date> dateList = new ArrayList<Date>(frequency * 31);

        // 获取当月的最后一天
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(batchTime);
        endTime.set(Calendar.DAY_OF_MONTH, endTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        DateUtils.resetDay(endTime);

        // 设置为1号,获取frequency个月的1号
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(endTime.getTime());
        startTime.add(Calendar.MONTH, 1 - frequency);
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        DateUtils.resetDay(startTime);

        while (startTime.getTime().getTime() <= endTime.getTime().getTime()) {
            dateList.add(startTime.getTime());
            startTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateList;
    }

    public static void resetDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date resetDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    public static Date resetHourOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * 功能描述: <br>
     * 开始时间和结束时间的时间差
     *
     * @param timeUnit  单位
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 时间差值
     */
    public static Long timeDiff(TimeUnits timeUnit, Date startDate, Date endDate) {

        // 当时间单位为秒、分、时、天、周
        if (timeUnit.getnCode() <= TimeUnits.WEEK.getnCode()) {
            Long diffTime = endDate.getTime() - startDate.getTime();
            if (timeUnit == TimeUnits.SECOND) {
                return diffTime / 1000;
            } else if (timeUnit == TimeUnits.MINUTE) {
                return diffTime / 1000 / 60;
            } else if (timeUnit == TimeUnits.HOUR) {
                return diffTime / 1000 / 60 / 60;
            } else if (timeUnit == TimeUnits.DAY) {
                return diffTime / 1000 / 60 / 60 / 24;
            } else if (timeUnit == TimeUnits.WEEK) {
                return diffTime / 1000 / 60 / 60 / 24 / 7;
            }
            return diffTime / 1000 / 60 / 60 / 24 / 7;
        } else {
            if (timeUnit == TimeUnits.MONTH) {
                return monthsDiff(startDate, endDate);
            } else if (timeUnit == TimeUnits.QUARTER) {
                return monthsDiff(startDate, endDate) / 3;
            } else if (timeUnit == TimeUnits.YEAR) {
                return monthsDiff(startDate, endDate) / 12;
            } else {
                return 0L;
            }
        }
    }

    public static Long monthsDiff(Date beginDate, Date endDate) {
        // 时间单位为月、季度、年时
        Calendar cs = Calendar.getInstance();
        Calendar ce = Calendar.getInstance();
        cs.setTime(beginDate);
        ce.setTime(endDate);
        // 计算开始时间和结束时间的差值（分别为年月日位上）
        Integer yearDiff = ce.get(Calendar.YEAR) - cs.get(Calendar.YEAR);
        Integer monthDiff = ce.get(Calendar.MONTH) - cs.get(Calendar.MONTH);

        // 先比较时分秒
        if (ce.get(Calendar.DAY_OF_MONTH) >= cs.get(Calendar.DAY_OF_MONTH)
                && ce.get(Calendar.HOUR) >= cs.get(Calendar.HOUR) && ce.get(Calendar.MINUTE) >= cs.get(Calendar.MINUTE)
                && ce.get(Calendar.SECOND) >= cs.get(Calendar.SECOND)) {
            return (long) (yearDiff * 12 + monthDiff);
        } else {
            return (long) (yearDiff * 12 + monthDiff - 1);
        }
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sf.parse("2016-01-01");
        // System.out.println(getLastMonthDay(date));
        // System.out.println(getLast7Day(date));
        // System.out.println(getLastWeekDay(date));
        // System.out.println(getLast24Hour(date));
        System.out.println(getIntervalDay(date, 0));
        System.out.println(getIntervalDay(date, 1));
        System.out.println(getNextMonday(date));
        System.out.println(getIntervalMonthTime(date, -1));
        System.out.println(getMinuteBatchTime(1));
//        System.out.println(test());
    }
}

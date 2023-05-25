/*
 * Copyright (C), 2002-2014, 苏宁易购电子商务有限公司
 * FileName: Units.java
 * Author:   14082455
 * Date:     2014-9-22 下午2:43:04
 * Description: 时间单位枚举
 */
package com.kafka.monitor.utils;

/**
 *  时间单位枚举<br> 
 *  时间单位枚举：年月日时分秒周季度
 *
 * @author 14082455
 */
public enum TimeUnits {
    
    // 利用构造函数传参
    SECOND(2), MINUTE(4), HOUR(8), DAY(16), WEEK(32), MONTH(64), QUARTER(128), YEAR(256);

    // 定义私有变量
    private int nCode;

    // 构造函数，枚举类型只能为私有
    private TimeUnits(int nCode) {
        this.nCode = nCode;
    }
    //返回code
    public int getnCode() {
        return nCode;
    }
}

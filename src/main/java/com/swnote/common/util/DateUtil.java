package com.swnote.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理时间相关的工具类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-09]
 */
public class DateUtil {
    public final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String YEAR_MONTH_DAY_FORMAT = "yyyyMMdd";

    public final static String YEAR_MONTH_FORMAT = "yyyyMM";

    /**
     * 将时间转化成默认的字符串形式
     * 
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        return date2Str(date, DEFAULT_FORMAT);
    }

    /**
     * 将时间转化成指定的字符串形式
     * 
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
package com.swnote.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ID生成器
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-23]
 */
public class IdGenarator {
    private static AtomicLong next = new AtomicLong(1);

    /**
     * 当前时间戳（线程安全），如：1401157280173（13位长）
     * 
     * @return
     */
    public static long longId() {
        return System.currentTimeMillis() + next.getAndIncrement();
    }

    /**
     * 当前时间戳（线程安全），如：1401157280173（13位长）
     * 
     * @return
     */
    public static String longIdStr() {
        return (System.currentTimeMillis() + next.getAndIncrement()) + "";
    }

    /**
     * 当前时间戳（线程安全），如：20140527102120173（17位长）
     * 
     * @return
     */
    public static long currentTimeLong() {
        long time = System.currentTimeMillis() + next.getAndIncrement() - next.getAndIncrement();
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String s = formatter.format(date);

        return Long.parseLong(s);
    }

    private static long preTimeMillis = 0L;

    /**
     * 当前时间戳 ，如：20140527102120173（17位长）
     * 
     * @return
     */
    public static synchronized String currentTimeString() {

        long time = System.currentTimeMillis();
        if (preTimeMillis >= time) {
            time = preTimeMillis + 1;
        }

        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String s = formatter.format(date);

        preTimeMillis = time;
        return s;
    }

    /**
     * GUID编码，32位长度
     * 
     * @return
     */
    public static String guid() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        id = id.replaceAll("-", "");
        return id;
    }
}
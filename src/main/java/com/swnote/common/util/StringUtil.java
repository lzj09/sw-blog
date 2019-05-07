package com.swnote.common.util;

import java.io.StringWriter;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 字符串工具类
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-23]
 */
public class StringUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 将字节码转换成十六进制字符串表示
     * 
     * @param bs 字节码
     * @return
     */
    public static final String bytesToHexStringWithoutHeader(byte[] bs) {
        if (bs == null) {
            return null;
        }
        if (bs.length == 0) {
            return "";
        }

        String hexDigits = "0123456789ABCDEF";
        StringBuilder ret = new StringBuilder(bs.length * 2);
        for (byte b : bs) {
            ret.append(hexDigits.charAt(0x0F & (b >> 4)));
            ret.append(hexDigits.charAt(0x0F & b));
        }

        return ret.toString();
    }

    /**
     * 字符串MD5加密
     * 
     * @param s 需要加密的字符串
     * @return 加密后的长度为固定的32位
     */
    public static String md5(String s) {
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            return bytesToHexStringWithoutHeader(md);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否为空或空串（包括全都是空格的情况）
     * 
     * @param s 字符串
     * @return
     */
    public static boolean isNullOrEmpty(String s) {
        if (s == null || s.equals("") || s.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 转换为int数据类型
     * 
     * @param s 字符串
     * @param defaultValue 如果s为空或不是合法的数字，将返回defaultValue
     * @return
     */
    public static int convertInt(String s, int defaultValue) {
        if (isNullOrEmpty(s)) {
            return defaultValue;
        } else {
            Integer d = null;
            try {
                d = new Integer(s);
            } catch (NumberFormatException e) {
                d = defaultValue;
            }
            return d.intValue();
        }
    }

    /**
     * 转换为long数据类型
     * 
     * @param s 字符串
     * @param defaultValue 如果s为空或不是合法的数字，将返回defaultValue
     * @return
     */
    public static long convertLong(String s, long defaultValue) {
        if (isNullOrEmpty(s)) {
            return defaultValue;
        } else {
            Long d = null;
            try {
                d = new Long(s);
            } catch (NumberFormatException e) {
                d = defaultValue;
            }
            return d.longValue();
        }
    }

    /**
     * 转换为long数据类型
     * 
     * @param s 字符串
     * @param defaultValue 如果s为空或不是合法的数字，将返回defaultValue
     * @return
     */
    public static double convertDouble(String s, double defaultValue) {
        if (isNullOrEmpty(s)) {
            return defaultValue;
        } else {
            Double d = null;
            try {
                d = new Double(s);
            } catch (NumberFormatException e) {
                d = defaultValue;
            }
            return d.doubleValue();
        }
    }

    /**
     * 将字符串数组转换为int类型数组
     * 
     * @param s 字符串数组
     * @param defaultValue 如果s中的值为空或不是合法的数字，将返回defaultValue
     * @return
     */
    public static int[] convertIntArray(String[] s, int defaultValue) {
        if (s == null || s.length < 1) {
            return null;
        } else {
            int[] ret = new int[s.length];
            for (int i = 0; i < s.length; i++) {
                ret[i] = convertInt(s[i], defaultValue);
            }
            return ret;
        }
    }

    /**
     * 将字符串数组转换为long类型数组
     * 
     * @param s 字符串数组
     * @param defaultValue 如果s中的值为空或不是合法的数字，将返回defaultValue
     * @return
     */
    public static long[] convertLongArray(String[] s, long defaultValue) {
        if (s == null || s.length < 1) {
            return null;
        } else {
            long[] ret = new long[s.length];
            for (int i = 0; i < s.length; i++) {
                ret[i] = convertLong(s[i], defaultValue);
            }
            return ret;
        }
    }

    /**
     * 将字符串数组转换为double类型数组
     * 
     * @param s 字符串数组
     * @param defaultValue 如果s中的值为空或不是合法的数字，将返回defaultValue
     * @return
     */
    public static double[] convertDoubleArray(String[] s, double defaultValue) {
        if (s == null || s.length < 1) {
            return null;
        } else {
            double[] ret = new double[s.length];
            for (int i = 0; i < s.length; i++) {
                ret[i] = convertDouble(s[i], defaultValue);
            }
            return ret;
        }
    }

    /**
     * 判断字符串数组中是否包含某字符串，大小写敏感
     * 
     * @param array  字符串数组
     * @param str 字符串
     * @return
     */
    public static boolean isContains(String[] array, String str) {
        if (array == null || array.length < 1 || isNullOrEmpty(str)) {
            return false;
        }

        boolean contains = false;
        for (int i = 0; i < array.length; i++) {
            String item = array[i];
            if (item != null && item.equals(str)) {
                contains = true;
                break;
            }
        }

        return contains;
    }

    /**
     * 用于正则表达匹配
     * 
     * @param regex
     * @param str
     * @return
     */
    public static boolean regexMatcher(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 校验email
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return regexMatcher("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", email);
    }

    /**
     *  校验是否由字母、数字和下划线组成
     *
     * @param str
     * @return
     */
    public static boolean isId(String str) {
        return regexMatcher("^\\w+$", str);
    }
    /**
     * 将对象转成JSON字符串
     * 
     * @param obj
     * @return
     */
    public static String parseObj2Json(Object obj) throws Exception {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createGenerator(sw);
        mapper.writeValue(gen, obj);
        gen.close();
        return sw.toString();
    }

    /**
     * 去除html标签
     * 
     * @param content
     * @return
     */
    public static String replaceHtmlTags(String content) {
        return content.replaceAll("</?[^>]+>", "");
    }

    /**
     * 去除特殊字符
     * 
     * @param content
     * @return
     */
    public static String replaceSpecialStrs(String content) {
        // 去除&nbsp;
        content = content.replaceAll("&nbsp;", "");
        // 去除&quot;
        content = content.replaceAll("&quot;", "");
        // 去除&lt;
        content = content.replaceAll("&lt;", "");
        // 去除&gt;
        content = content.replaceAll("&gt;", "");
        return content;
    }

}
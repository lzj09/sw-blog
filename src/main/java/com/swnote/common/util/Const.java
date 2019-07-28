package com.swnote.common.util;

/**
 * 常量信息
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-18]
 */
public class Const {
    
    /**
     * 前台页面文件夹前缀
     */
    public final static String BASE_INDEX_PAGE = "/index/";

    /**
     * 用户登录成功后放入session中key
     */
    public final static String SESSION_USER = "_SESSION_USER";

    /**
     * 系统临时缓存标识
     */
    public final static String CACHE_SYSTEM_TEMP = "SystemTempCache";

    /**
     * 系统永久缓存标识
     */
    public final static String CACHE_SYSTEM_ETERNAL = "SystemEternalCache";

    /**
     * 私有化构造函数
     */
    private Const() {
    }
}
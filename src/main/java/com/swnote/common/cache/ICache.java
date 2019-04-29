package com.swnote.common.cache;

/**
 * 缓存接口
 *
 * @author lzj
 * @since 1.0
 * @date [2019-04-27]
 */
public interface ICache<T> {
    /**
     * 根据key获取缓存数据
     *
     * @param key
     * @return
     */
    public T get(Object key);

    /**
     * 存放缓存数据
     *
     * @param key
     * @param value
     */
    public void put(Object key, T value);

    /**
     * 根据key移除内容
     *
     * @param key
     */
    public void remove(Object key);
}

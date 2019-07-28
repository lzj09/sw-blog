package com.swnote.common.cache;

import com.swnote.blog.domain.Category;
import com.swnote.blog.service.ICategoryService;
import com.swnote.common.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 缓存分类信息
 * 分类信息放到系统永久缓存中，存放形式为："_CATEGORY" + categoryId为key，value为分类信息对象
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-22]
 */
@Slf4j
@DependsOn("categoryService")
@Component("categoryCache")
public class CategoryCache implements ICache<List<Category>> {

    /**
     * 注入基于Spring提供的Cache接口实例，默认由Ehcache实现
     * TODO 以后也可以是Redis、Memcached提供实现
     */
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 缓存实例
     */
    private Cache cache;

    /**
     * key的前缀
     */
    private String keyPrefix = "_CATEGORY";

    /**
     * 分类信息根节点ID
     */
    public static final String ROOT_CATEGORY_ID = "0";

    @PostConstruct
    public void init() {
        // 获取系统永久缓存实例
        cache = cacheManager.getCache(Const.CACHE_SYSTEM_ETERNAL);
        log.info("获取系统永久缓存实例");

        log.debug("开始加载父分类信息");
        List<Category> categorys = categoryService.getByParentId(ROOT_CATEGORY_ID);
        if (categorys != null && !categorys.isEmpty()) {
            put(keyPrefix + ROOT_CATEGORY_ID, categorys);
        }
        log.debug("加载完毕父分类信息");
    }

    @Override
    public List<Category> get(Object key) {
        Cache.ValueWrapper valueWrapper = cache.get(keyPrefix + key);
        if (valueWrapper == null) {
            // 从数据库重新加载一次
            List<Category> categorys = categoryService.getByParentId((String) key);
            if (categorys == null) {
                return null;
            }

            // 再次放到缓存中
            put(key, categorys);

            return categorys;
        }
        return (List<Category>) valueWrapper.get();
    }

    @Override
    public void put(Object key, List<Category> value) {
        cache.put(keyPrefix + key, value);
    }

    @Override
    public void remove(Object key) {
        cache.evict(keyPrefix + key);
    }
}

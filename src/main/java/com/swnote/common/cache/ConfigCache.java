package com.swnote.common.cache;

import com.swnote.common.domain.Config;
import com.swnote.common.service.IConfigService;
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
 * 缓存配置信息
 * 配置信息放到系统永久缓存中，存放形式为："_CONFIG" + configId为key，value为配置信息对象
 *
 * @author lzj
 * @since 1.0
 * @date [2019-04-27]
 */
@Slf4j
@DependsOn("configService")
@Component("configCache")
public class ConfigCache implements ICache<Config> {

    /**
     * 注入基于Spring提供的Cache接口实例，默认由Ehcache实现
     * TODO 以后也可以是Redis、Memcached提供实现
     */
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private IConfigService configService;

    /**
     * 系统临时缓存实例
     */
    private Cache cache;

    /**
     * key的前缀
     */
    private String keyPrefix = "_CONFIG";

    @PostConstruct
    public void init() {
        // 获取系统永久缓存实例
        cache = cacheManager.getCache(Const.CACHE_SYSTEM_ETERNAL);
        log.info("获取系统永久缓存实例");

        log.info("开始加载所有配置信息");
        List<Config> configs = configService.list();
        if (configs != null && !configs.isEmpty()) {
            configs.stream().forEach(config -> cache.put(keyPrefix + config.getConfigId(), config));
        }
        log.info("加载完毕所有配置信息");
    }

    @Override
    public Config get(Object key) {
        Cache.ValueWrapper valueWrapper = cache.get(keyPrefix + key);
        if (valueWrapper == null) {
            // 此时从数据库重新加载一次
            Config config = configService.getById((String) key);
            if (config == null) {
                return null;
            }

            // 再次放到缓存中
            cache.put(keyPrefix + config.getConfigId(), config);

            return config;
        }
        return (Config) valueWrapper.get();
    }

    @Override
    public void put(Object key, Config value) {
        cache.put(keyPrefix + key, value);
    }

    @Override
    public void remove(Object key) {
        cache.evict(keyPrefix + key);
    }
}
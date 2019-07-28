package com.swnote.common.config;

import com.swnote.common.cache.ICache;
import com.swnote.common.domain.Config;
import com.swnote.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 配置类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-05-07]
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Resource(name = "configCache")
    private ICache<Config> configCache;

    /**
     *  拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/**");
    }

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射头像图片
        String avatarPath = configCache.get(Config.CONFIG_IMG_AVATAR_PATH).getConfigValue();
        if (!(avatarPath.endsWith("/") || avatarPath.endsWith("\\"))) {
            avatarPath += "/";
        }
        registry.addResourceHandler("/img/avatar/**").addResourceLocations("file:" + avatarPath);

        // 映射专栏logo
        String groupLogoPath = configCache.get(Config.CONFIG_IMG_GROUP_LOGO_PATH).getConfigValue();
        if (!(groupLogoPath.endsWith("/") || groupLogoPath.endsWith("\\"))) {
            groupLogoPath += "/";
        }
        registry.addResourceHandler("/img/group/logo/**").addResourceLocations("file:" + groupLogoPath);
    }
}

package com.swnote.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swnote.blog.domain.Article;

/**
 * 文章信息服务接口
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
public interface IArticleService extends IService<Article> {

    /**
     * 新增文章
     *
     * @param article
     * @param tag
     * @return
     */
    public boolean create(Article article, String tag);
}

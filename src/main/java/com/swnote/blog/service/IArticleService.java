package com.swnote.blog.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swnote.blog.domain.Article;

import java.util.List;
import java.util.Map;

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

    /**
     * 更新文章
     *
     * @param article
     * @param tag
     * @return
     */
    public boolean update(Article article, String tag);

    /**
     * 根据条件查询limit条记录
     *
     * @param queryWrapper
     * @param limit
     * @return
     */
    public List<Article> queryForLimit(Wrapper<Article> queryWrapper, int limit);

    /**
     * 根据标签信息获取limit条记录
     *
     * @param params
     * @param tags
     * @param limit
     * @return
     */
    public List<Article> queryForLimitByTags(Map<String, Object> params, List<String> tags, int limit);
}

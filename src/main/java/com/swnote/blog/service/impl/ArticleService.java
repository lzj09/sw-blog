package com.swnote.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.blog.dao.ArticleDao;
import com.swnote.blog.domain.Article;
import com.swnote.blog.domain.ArticleTag;
import com.swnote.blog.domain.Tag;
import com.swnote.blog.service.IArticleService;
import com.swnote.blog.service.IArticleTagService;
import com.swnote.blog.service.ITagService;
import com.swnote.common.cache.ICache;
import com.swnote.common.domain.Config;
import com.swnote.common.util.IdGenarator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章信息服务类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Service
public class ArticleService extends ServiceImpl<ArticleDao, Article> implements IArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ITagService tagService;

    @Autowired
    private IArticleTagService articleTagService;

    @Resource(name = "configCache")
    private ICache<Config> configCache;


    @Override
    public boolean create(Article article, String tag) {
        Date now = new Date();

        // 文章是否需要审核
        Config config = configCache.get(Config.CONFIG_ARTICLE_AUDIT);
        if (config != null && "1".equals(config.getConfigValue())) {
            // 文章需要审核
            article.setStatus(Article.STATUS_NO);
        } else {
            // 不需要审核
            article.setStatus(Article.STATUS_SUCCESS);
            article.setPublishTime(now);
        }

        // 保存文章
        boolean flag = save(article);

        // 封装文章与标签的关系
        wrapArticleTag(article.getArticleId(), tag);
        return flag;
    }

    @Override
    public boolean update(Article article, String tag) {
        // 文章是否需要审核
        Config config = configCache.get(Config.CONFIG_ARTICLE_AUDIT);
        if (config != null && "1".equals(config.getConfigValue())) {
            // 文章需要审核
            article.setStatus(Article.STATUS_NO);
        } else {
            // 不需要审核
            article.setStatus(Article.STATUS_SUCCESS);
        }

        // 更新文章
        boolean flag = updateById(article);

        // 删除文章与标签间的关系
        articleTagService.remove(new QueryWrapper<ArticleTag>().lambda().eq(ArticleTag::getArticleId, article.getArticleId()));

        // 删除文章与标签的关系
        wrapArticleTag(article.getArticleId(), tag);
        return flag;
    }

    @Override
    public List<Article> queryForLimit(Wrapper<Article> queryWrapper, int limit) {
        IPage<Article> page = page(new Page<Article>(1, limit), queryWrapper);
        return page.getRecords();
    }

    @Override
    public List<Article> queryForLimitByTags(Map<String, Object> params, List<String> tags, int limit) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("tags", tags);
        params.put("limit", limit);

        return articleDao.queryByTags(params);
    }

    /**
     * 封装文章与标签的关系
     *
     * @param articleId
     * @param tag
     */
    private void wrapArticleTag(String articleId, String tag) {
        if (!StringUtils.isEmpty(tag)) {
            Date now = new Date();
            String[] tags = tag.split(",");
            // 保存标签信息以及文章与标签的关系
            for (String item : tags) {
                String tagId = "";
                // 首先根据标签名查找标签信息
                List<Tag> tempTags = tagService.list(new QueryWrapper<Tag>().lambda().eq(Tag::getTag, item));
                if (tempTags != null && !tempTags.isEmpty()) {
                    tagId = tempTags.get(0).getTagId();
                } else {
                    // 封装标签信息
                    Tag tagBean = new Tag();
                    tagBean.setTagId(IdGenarator.guid());
                    tagBean.setTag(item);
                    tagBean.setCreateTime(now);
                    // 保存
                    tagService.save(tagBean);

                    tagId = tagBean.getTagId();
                }

                // 封装文章与标签的关系
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tagId);
                articleTag.setCreateTime(now);
                // 保存
                articleTagService.save(articleTag);
            }
        }
    }
}
package com.swnote.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.List;

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

        if (!StringUtils.isEmpty(tag)) {
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
                articleTag.setArticleId(article.getArticleId());
                articleTag.setTagId(tagId);
                articleTag.setCreateTime(now);
                // 保存
                articleTagService.save(articleTag);
            }
        }
        return flag;
    }
}

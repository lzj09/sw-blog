package com.swnote.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.blog.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 文章信息Dao
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Repository
public interface ArticleDao extends BaseMapper<Article> {

    /**
     * 根据标签信息获取文章列表
     *
     * @param params
     * @return
     */
    public List<Article> queryByTags(Map<String, Object> params);
}

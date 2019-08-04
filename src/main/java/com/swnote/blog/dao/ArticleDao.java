package com.swnote.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.blog.domain.Article;
import org.springframework.stereotype.Repository;

/**
 * 文章信息Dao
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Repository
public interface ArticleDao extends BaseMapper<Article> {
}

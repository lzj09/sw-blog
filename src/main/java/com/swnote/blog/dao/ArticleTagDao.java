package com.swnote.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.blog.domain.ArticleTag;
import org.springframework.stereotype.Repository;

/**
 * 文章标签关系Dao
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Repository
public interface ArticleTagDao extends BaseMapper<ArticleTag> {
}

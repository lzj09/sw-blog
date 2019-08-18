package com.swnote.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.blog.domain.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签信息Dao
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Repository
public interface TagDao extends BaseMapper<Tag> {

    /**
     * 根据文章ID获取标签列表
     *
     * @param articleId
     * @return
     */
    public List<Tag> queryByArticleId(@Param("articleId") String articleId);
}

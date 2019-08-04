package com.swnote.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.blog.domain.Tag;
import org.springframework.stereotype.Repository;

/**
 * 标签信息Dao
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Repository
public interface TagDao extends BaseMapper<Tag> {
}

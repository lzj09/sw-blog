package com.swnote.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.blog.domain.Category;
import org.springframework.stereotype.Repository;

/**
 * 分类信息Dao
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-21]
 */
@Repository
public interface CategoryDao extends BaseMapper<Category> {
}

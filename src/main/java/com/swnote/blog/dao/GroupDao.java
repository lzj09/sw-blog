package com.swnote.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.blog.domain.Group;
import org.springframework.stereotype.Repository;

/**
 * 专栏信息Dao
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-14]
 */
@Repository
public interface GroupDao extends BaseMapper<Group> {
}
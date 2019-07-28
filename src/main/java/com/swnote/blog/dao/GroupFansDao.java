package com.swnote.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.blog.domain.GroupFans;
import org.springframework.stereotype.Repository;

/**
 * 专栏关注信息Dao
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-21]
 */
@Repository
public interface GroupFansDao extends BaseMapper<GroupFans> {
}

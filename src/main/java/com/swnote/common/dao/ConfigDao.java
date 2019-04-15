package com.swnote.common.dao;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.common.domain.Config;

/**
 * 站点相关配置信息Dao
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-04]
 */
@Repository
public interface ConfigDao extends BaseMapper<Config> {

}
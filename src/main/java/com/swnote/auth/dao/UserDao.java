package com.swnote.auth.dao;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swnote.auth.domain.User;

/**
 * 用户信息Dao
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-18]
 */
@Repository
public interface UserDao extends BaseMapper<User> {

}
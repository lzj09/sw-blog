package com.swnote.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swnote.auth.domain.User;

/**
 * 用户信息服务接口
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-18]
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名获取用户信息
     * 
     * @param name
     * @return
     */
    public User getByName(String name);

    /**
     * 根据email获取用户信息
     * 
     * @param email
     * @return
     */
    public User getByEmail(String email);
}
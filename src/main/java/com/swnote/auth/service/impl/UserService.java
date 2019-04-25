package com.swnote.auth.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.auth.dao.UserDao;
import com.swnote.auth.domain.User;
import com.swnote.auth.service.IUserService;

/**
 * 用户信息服务类
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-18]
 */
@Service
public class UserService extends ServiceImpl<UserDao, User> implements IUserService {

    @Override
    public User getByName(String name) {
        return getOne(new QueryWrapper<User>().lambda().eq(User::getLoginName, name));
    }

    @Override
    public User getByEmail(String email) {
        return getOne(new QueryWrapper<User>().lambda().eq(User::getEmail, email));
    }
}
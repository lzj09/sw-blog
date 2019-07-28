package com.swnote.auth.service.impl;

import com.swnote.common.cache.ICache;
import com.swnote.common.domain.Config;
import com.swnote.common.util.Const;
import com.swnote.common.util.IdGenarator;
import com.swnote.common.util.StringUtil;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.auth.dao.UserDao;
import com.swnote.auth.domain.User;
import com.swnote.auth.service.IUserService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户信息服务类
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-18]
 */
@Service
public class UserService extends ServiceImpl<UserDao, User> implements IUserService {

    @Resource(name = "configCache")
    private ICache<Config> configCache;

    @Override
    public User getByName(String name) {
        return getOne(new QueryWrapper<User>().lambda().eq(User::getLoginName, name));
    }

    @Override
    public User getByEmail(String email) {
        return getOne(new QueryWrapper<User>().lambda().eq(User::getEmail, email));
    }

    @Override
    public User getByCode(String code) {
        return getOne(new QueryWrapper<User>().lambda().eq(User::getCode, code));
    }

    @Override
    public boolean create(User user) {
        // 获取当前时间
        Date now = new Date();

        // 设置主键
        user.setUserId(IdGenarator.guid());
        // 设置未实名认证
        user.setRealStatus(User.REAL_STATUS_NO);

        // 用户是否需要激活
        Config config = configCache.get(Config.CONFIG_USER_ACTIVE);
        if (config != null && "1".equals(config.getConfigValue())) {
            // TODO 发送激活邮件信息
            // 说明需要激活
            user.setIsActive(User.ACTIVE_NO);
        } else {
            // 说明不需要激活，默认激活
            user.setIsActive(User.ACTIVE_YES);
        }

        // 设置启用账号状态
        user.setStatus(User.STATUS_YES);
        // 设置创建时间
        user.setCreateTime(now);
        // 设置关注数为0
        user.setFollows(0);
        // 设置粉丝数为0
        user.setFans(0);
        return save(user);
    }

    @Override
    public User verifyUser(String name, String password, String ip) {
        // 根据用户名或者邮箱获取用户信息
        User user = getOne(new QueryWrapper<User>().lambda().eq(User::getLoginName, name).or().eq(User::getEmail, name));
        if (user != null) {
            if (user.getPassword().equals(StringUtil.md5(password))) {
                if (user.getIsActive() == User.ACTIVE_NO || user.getStatus() == User.STATUS_NO) {
                    return null;
                }

                // 设置登录时间
                user.setLastLoginTime(new Date());
                // 设置登录ip
                user.setLastLoginIp(ip);
                // 更新信息
                updateById(user);

                return user;
            }
        }
        return null;
    }
}
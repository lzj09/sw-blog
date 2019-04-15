package com.swnote.common.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.common.dao.ConfigDao;
import com.swnote.common.domain.Config;
import com.swnote.common.service.IConfigService;

/**
 * 站点相关配置信息服务类
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-04]
 */
@Service
public class ConfigService extends ServiceImpl<ConfigDao, Config> implements IConfigService {

}
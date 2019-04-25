package com.swnote.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swnote.common.domain.Config;
import com.swnote.common.service.IConfigService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ConfigController {

    @Autowired
    private IConfigService configService;

    @RequestMapping(value = "/config/save", method = RequestMethod.POST)
    public Config save(@RequestBody Config config) throws Exception {
        try {
            configService.save(config);
            return config;
        } catch (Exception e) {
            log.error("新增配置信息错误", e);
            throw e;
        }
    }

    @RequestMapping(value = "/config/list", method = RequestMethod.GET)
    public List<Config> list() throws Exception {
        try {
            return configService.list();
        } catch (Exception e) {
            log.error("查询配置信息错误", e);
            throw e;
        }
    }
}
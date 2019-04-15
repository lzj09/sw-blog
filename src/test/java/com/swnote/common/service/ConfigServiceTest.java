package com.swnote.common.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.swnote.common.domain.Config;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigServiceTest {

    @Autowired
    private IConfigService configService;

    @Test
    public void save() throws Exception {
        Config config = new Config();
        config.setConfigId("test");
        config.setConfigValue("testValue");
        config.setDescription("测试内容");
        configService.save(config);
        
    }
    
    @Test
    public void update() {
        Config config = new Config();
        config.setConfigId("test");
        config.setConfigValue("testValue---g");
        config.setDescription("测试内容---g");
        configService.updateById(config);
    }
    
    @Test
    public void get() {
        Config config = configService.getById("test");
        System.out.println(config);
    }
    
    @After
    public void delete() {
        configService.removeById("test");
    }
}
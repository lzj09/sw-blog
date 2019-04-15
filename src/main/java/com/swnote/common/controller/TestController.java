package com.swnote.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swnote.common.domain.Config;
import com.swnote.common.service.IConfigService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IConfigService configService;
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String test(ModelMap model) {
        Config config = configService.getById("name");
        
        model.addAttribute("config", config);
        
        log.info("config info: " + config);
        return "test";
    }
}
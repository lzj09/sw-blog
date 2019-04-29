package com.swnote.common.controller.index;

import com.swnote.common.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 首页的控制器类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-04-29]
 */
@Slf4j
@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, HttpSession session) {
        return Const.BASE_INDEX_PAGE + "common/index/index";
    }

}

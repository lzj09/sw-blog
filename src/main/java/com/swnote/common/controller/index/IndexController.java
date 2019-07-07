package com.swnote.common.controller.index;

import com.swnote.auth.domain.User;
import com.swnote.auth.service.IUserService;
import com.swnote.common.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private IUserService userService;

    /**
     * 加载出网站首页
     *
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, HttpSession session) {
        return Const.BASE_INDEX_PAGE + "common/index/index";
    }

    /**
     *  加载出个人主页
     *
     * @param code
     * @param  model
     * @return
     */
    @RequestMapping(value = "u/{code}", method = RequestMethod.GET)
    public String userCenter(@PathVariable("code") String code, Model model) {
        // 根据code获取用户信息
        User user = userService.getByCode(code);

        model.addAttribute("user", user);
        return Const.BASE_INDEX_PAGE + "common/index/userCenter";
    }
}

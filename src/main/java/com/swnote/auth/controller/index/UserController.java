package com.swnote.auth.controller.index;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swnote.auth.domain.User;
import com.swnote.auth.service.IUserService;
import com.swnote.common.bean.Result;
import com.swnote.common.exception.TipException;
import com.swnote.common.util.Const;
import com.swnote.common.util.HttpUtil;
import com.swnote.common.util.IdGenarator;
import com.swnote.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户相关信息的前端控制器类
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-18]
 */
@Slf4j
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 加载出注册页面
     * 
     * @return
     */
    @RequestMapping(value = "/auth/signup", method = RequestMethod.GET)
    public String signup() {
        return Const.BASE_INDEX_PAGE + "auth/user/signup";
    }

    /**
     * 保存注册信息
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/auth/signup", method = RequestMethod.POST)
    @ResponseBody
    public Result signup(Model model, HttpServletRequest request) {
        Result result = new Result();
        try {
            // 接收参数
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // 简单校验
            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
                throw new TipException("缺少必要请求参数");
            }

            if (!StringUtil.isEmail(email)) {
                throw new TipException("邮箱不符全规范");
            }
            
            // 校验用户名
            User tempUser = userService.getByName(name);
            if (tempUser != null && !StringUtils.isEmpty(tempUser.getUserId())) {
                throw new TipException("该用户已经注册了");
            }
            
            // 校验邮箱
            tempUser = userService.getByEmail(email);
            if (tempUser != null && !StringUtils.isEmpty(tempUser.getUserId())) {
                throw new TipException("该邮箱已经注册了");
            }

            // 获取用户ip
            String ip = HttpUtil.getIpAddr(request);

            // 构建用户信息
            User user = new User();
            user.setLoginName(name);
            user.setEmail(email);
            user.setPassword(StringUtil.md5(password));
            user.setCreateIp(ip);
            
            // 保存用户信息
            boolean flag = userService.create(user);
            if (!flag) {
                throw new TipException("用户创建失败");
            }

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("用户创建成功");
        } catch (Exception e) {
            log.error("用户创建失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("用户创建失败");
        }
        return result;
    }

    /**
     * 加载出登录页面
     * 
     * @return
     */
    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        if (user != null) {
            String code = user.getCode();
            if (StringUtils.isEmpty(code)) {
                // 说明用户还没有设置个人主页的标识
                return "redirect:/user/code";
            }
            return "redirect:/u/" + code;
        }
        return Const.BASE_INDEX_PAGE + "auth/user/login";
    }

    /**
     * 处理登录信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpServletRequest request, HttpSession session) {
        Result result = new Result();
        try {
            // 接收参数
            String name = request.getParameter("name");
            String password = request.getParameter("password");

            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
                throw new TipException("缺少必要请求参数");
            }

            // 获取用户ip
            String ip = HttpUtil.getIpAddr(request);

            User user = userService.verifyUser(name, password, ip);
            if (user == null) {
                throw new TipException("用户名或密码错误");
            }

            // 放置session信息
            session.setAttribute(Const.SESSION_USER, user);

            // TODO 还有一些相关统计信息，后面再加上

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("登录成功");
            result.setContent(user.getCode());
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("登录失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("登录失败");
        }
        return result;
    }

    /**
     *  退出系统
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/auth/logout", method = RequestMethod.POST)
    @ResponseBody
    public Result logout(HttpSession session) {
        Result result = new Result();
        try {
            User user = (User) session.getAttribute(Const.SESSION_USER);
            if (user == null || StringUtils.isEmpty(user.getUserId())) {
                throw new TipException("用户还没有登录");
            }

            session.removeAttribute(Const.SESSION_USER);

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("退出成功");
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("退出失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("退出失败");
        }
        return result;
    }

    /**
     * 加载出修改个人主页标识页面
     *
     * @return
     */
    @RequestMapping(value = "/user/code", method = RequestMethod.GET)
    public String code(HttpSession session) {
        // session中的信息
        User user = (User) session.getAttribute(Const.SESSION_USER);
        if (!StringUtils.isEmpty(user.getCode())) {
            // 跳转到个人主页
            return "redirect:/u/" + user.getCode();
        }
        return Const.BASE_INDEX_PAGE + "auth/user/code";
    }

    /**
     * 保存主页标识信息
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/code", method = RequestMethod.POST)
    @ResponseBody
    public Result code(HttpServletRequest request, HttpSession session) {
        Result result = new Result();
        try {
            // 接收参数
            String code = request.getParameter("code");

            // 校验参数
            if (StringUtils.isEmpty(code)) {
                throw new TipException("主页标识不能为空");
            }
            if (!StringUtil.isId(code)) {
                throw new TipException("主页标识只能包含字母、数字和下划线");
            }

            // session中的信息
            User user = (User) session.getAttribute(Const.SESSION_USER);
            if (!StringUtils.isEmpty(user.getCode())) {
                throw new TipException("主页标识只能设置一次");
            }

            // 设置主页标识
            user.setCode(code);
            userService.updateById(user);

            // 更新session
            session.removeAttribute(Const.SESSION_USER);
            session.setAttribute(Const.SESSION_USER, user);

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("修改成功");
            result.setContent(code);
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("保存主页标识信息失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("保存主页标识信息失败");
        }
        return result;
    }

    /**
     * 加载出修改个人信息页面
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String edit(HttpSession session, Model model) {
        // session中的信息
        User sessionUser = (User) session.getAttribute(Const.SESSION_USER);

        // 从数据库中获取用户信息
        User user = userService.getById(sessionUser.getUserId());

        model.addAttribute("user", user);
        return Const.BASE_INDEX_PAGE + "auth/user/edit";
    }

    /**
     * 修改个人信息
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result edit(HttpServletRequest request, HttpSession session) {
        Result result = new Result();
        try {
            // 获取登录信息
            User tempUser = (User) session.getAttribute(Const.SESSION_USER);
            String userId = tempUser.getUserId();

            // 接收参数
            String realName = request.getParameter("realName");
            String cellphone = request.getParameter("cellphone");
            String sexStr = request.getParameter("sex");
            String introduce = request.getParameter("introduce");

            if (StringUtils.isEmpty(realName) || StringUtils.isEmpty(cellphone) || StringUtils.isEmpty(sexStr) || StringUtils.isEmpty(introduce)) {
                throw new TipException("缺少必要请求参数");
            }

            // 校验性别
            int sex = User.SEX_SECRET;
            try {
                sex = Integer.parseInt(sexStr);

                if (User.SEX_FEMALE != sex && User.SEX_MALE != sex && User.SEX_SECRET != sex) {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new TipException("性别数据不符合规则");
            }

            // 获取用户的信息
            User user = userService.getById(userId);
            user.setRealName(realName);
            user.setCellphone(cellphone);
            user.setSex(sex);
            user.setIntroduce(introduce);

            // 更新用户的信息
            boolean flag = userService.updateById(user);
            if (!flag) {
                throw new TipException("修改个人信息失败");
            }

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("修改成功");
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("修改个人信息失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("修改个人信息失败");
        }
        return result;
    }

    /**
     * 加载出修改头像页面
     *
     * @return
     */
    @RequestMapping(value = "/user/avatar", method = RequestMethod.GET)
    public String avatar(HttpSession session, Model model) {
        // session中的信息
        User sessionUser = (User) session.getAttribute(Const.SESSION_USER);

        // 从数据库中获取用户信息
        User user = userService.getById(sessionUser.getUserId());

        model.addAttribute("user", user);
        return Const.BASE_INDEX_PAGE + "auth/user/avatar";
    }

    /**
     * 加载出修改密码页面
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/password", method = RequestMethod.GET)
    public String password(HttpSession session, Model model) {
        // session中的信息
        User sessionUser = (User) session.getAttribute(Const.SESSION_USER);

        // 从数据库中获取用户信息
        User user = userService.getById(sessionUser.getUserId());

        model.addAttribute("user", user);
        return Const.BASE_INDEX_PAGE + "auth/user/password";
    }

    /**
     * 修改密码
     *
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/password", method = RequestMethod.POST)
    @ResponseBody
    public Result password(HttpServletRequest request, HttpSession session) {
        Result result = new Result();
        try {
            // 获取登录信息
            User tempUser = (User) session.getAttribute(Const.SESSION_USER);
            String userId = tempUser.getUserId();

            // 接收参数
            String password = request.getParameter("password");
            String newPwd = request.getParameter("newPwd");
            String confirmNewPwd = request.getParameter("confirmNewPwd");

            if (StringUtils.isEmpty(password) || StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(confirmNewPwd)) {
                throw new TipException("缺少必要请求参数");
            }

            if (!newPwd.equals(confirmNewPwd)) {
                throw new TipException("两次输入的新密码不相等");
            }

            // 获取用户信息
            User user = userService.getById(userId);
            if (!user.getPassword().equals(StringUtil.md5(password))) {
                throw new TipException("旧密码输入不正确");
            }

            // 修改密码
            user.setPassword(StringUtil.md5(newPwd));
            boolean flag = userService.updateById(user);

            if (!flag) {
                throw new TipException("修改密码失败");
            }

            result.setCode(Result.CODE_SUCCESS);
            result.setMsg("修改成功");
        } catch (TipException e) {
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("修改密码失败", e);
            result.setCode(Result.CODE_EXCEPTION);
            result.setMsg("修改密码失败");
        }
        return result;
    }
}
package com.swnote.common.interceptor;

import com.swnote.auth.domain.User;
import com.swnote.common.util.Const;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 检查是否登录
 *
 * @author lzj
 * @since 1.0
 * @date [2019-05-07]
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);

        if (user == null) {
            // 说明没有登录，直接跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return false;
        }
        return true;
    }
}

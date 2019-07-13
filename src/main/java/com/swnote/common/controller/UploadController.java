package com.swnote.common.controller;

import com.swnote.auth.domain.User;
import com.swnote.common.handler.IUploadHandler;
import com.swnote.common.util.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 处理文件上传下载控制器类
 *
 * @author lzj
 * @date [2019-07-09]
 * @since 1.0
 */
@Slf4j
@Controller
public class UploadController {

    @Autowired
    private ApplicationContext context;

    // 用于存储处理上传文件对象
    private Map<String, IUploadHandler> uploadHandlers;

    /**
     * 初始化操作
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        uploadHandlers = context.getBeansOfType(IUploadHandler.class);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(@RequestParam(value = "_uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpSession session) {
        Object result = null;
        try {
            // 接收参数
            // 获取上传文件类型，参数名为_fileType
            String _distType = request.getParameter("_distType");

            // 获取用户信息
            User user = (User) session.getAttribute(Const.SESSION_USER);

            result = uploadHandlers.get(_distType).upload(file, _distType, user.getUserId());
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
        return result;
    }
}
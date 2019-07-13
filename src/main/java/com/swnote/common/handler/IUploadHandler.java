package com.swnote.common.handler;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 上传文件处理接口类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-09]
 */
public interface IUploadHandler {

    /**
     * 上传文件处理方法
     * 文件上传成功，返回文件的相关信息
     * 文件上传失败， 返回null
     *
     * @param file
     * @param distType
     * @param userId
     * @return
     * @throws Exception
     */
    public Object upload(MultipartFile file, String distType, String userId) throws Exception;

    /**
     * 下载文件
     *
     * @param fileId
     * @param response
     * @throws Exception
     */
    public void download(String fileId, HttpServletResponse response) throws Exception;

    /**
     * 根据条件列出文件信息
     *
     * @param distType
     * @param userId
     * @return
     * @throws Exception
     */
    public Object list(String distType, String userId) throws Exception;
}
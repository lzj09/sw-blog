package com.swnote.common.handler.impl;

import com.swnote.common.cache.ICache;
import com.swnote.common.domain.Config;
import com.swnote.common.exception.TipException;
import com.swnote.common.handler.IUploadHandler;
import com.swnote.common.util.DateUtil;
import com.swnote.common.util.FileUtil;
import com.swnote.common.util.IdGenarator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传文章图片处理类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-08-01]
 */
@Slf4j
@Component("_articleImg")
public class UploadArticleImgHandler implements IUploadHandler {

    @Resource(name = "configCache")
    private ICache<Config> configCache;

    @Value("${server.context-path:}")
    private String contextPath;

    @Override
    public Object upload(MultipartFile file, String distType, String userId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            // 获取图片的原始名称
            String originalName = file.getOriginalFilename();

            // 判断图片的类型
            if (!(originalName.endsWith(".jpg") || originalName.endsWith(".JPG") || originalName.endsWith(".png") || originalName.endsWith(".PNG") || originalName.endsWith(".gif") || originalName.endsWith(".GIF") || originalName.endsWith(".jpeg") || originalName.endsWith(".JPEG") || originalName.endsWith(".bmp") || originalName.endsWith(".BMP"))) {
                throw new TipException("您上传的图片类型有误，请上传格式为jpg、png、gif或bmp");
            }

            // 获取图片的大小
            long fileSize = file.getSize();

            // 图片大小不能超过5M, 5M = 5 * 1024 * 1024B = 5242800B
            if (fileSize > 5242800L) {
                throw new TipException("您上传的图片超过5M");
            }

            Config config = configCache.get(Config.CONFIG_IMG_ARTICLE_PATH);
            // 保存文章图片的根目录
            String basePath = config.getConfigValue();
            if (!(basePath.endsWith("/") || basePath.endsWith("\\"))) {
                basePath += "/";
            }

            // 根据当前时间构建yyyyMM的文件夹，建立到月的文件夹
            String dateDirName = DateUtil.date2Str(new Date(), DateUtil.YEAR_MONTH_FORMAT);
            basePath += dateDirName;

            File imageDir = new File(basePath);
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }

            String fileNewName = IdGenarator.guid() + originalName.substring(originalName.lastIndexOf("."));
            FileUtil.copy(file.getInputStream(), new FileOutputStream(new File(imageDir, fileNewName)));

            result.put("success", 1);
            result.put("url", "/img/article/" + dateDirName + "/" + fileNewName);
            result.put("message", "上传文章图片成功");
        } catch (TipException e) {
            result.put("success", 0);
            result.put("message", e.getMessage());
        } catch (Exception e) {
            log.error("上传文章图片失败", e);
            result.put("success", 0);
            result.put("message", "上传文章图片失败");
        }
        return result;
    }

    @Override
    public void download(String fileId, HttpServletResponse response) throws Exception {

    }

    @Override
    public Object list(String distType, String userId) throws Exception {
        return null;
    }
}
package com.swnote.common.handler.impl;

import com.swnote.common.cache.ICache;
import com.swnote.common.domain.Config;
import com.swnote.common.exception.TipException;
import com.swnote.common.handler.IUploadHandler;
import com.swnote.common.util.DateUtil;
import com.swnote.common.util.FileUtil;
import com.swnote.common.util.IdGenarator;
import lombok.extern.slf4j.Slf4j;
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
 * 上传专栏Logo处理类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-23]
 */
@Slf4j
@Component("_groupLogo")
public class UploadGroupLogoHandler implements IUploadHandler {

    @Resource(name = "configCache")
    private ICache<Config> configCache;

    @Override
    public Object upload(MultipartFile file, String distType, String userId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            // 获取图片的原始名称
            String originalName = file.getOriginalFilename();

            // 判断图片的类型
            if (!(originalName.endsWith(".jpg") || originalName.endsWith(".JPG") || originalName.endsWith(".png") || originalName.endsWith(".PNG") || originalName.endsWith(".gif") || originalName.endsWith(".GIF") || originalName.endsWith(".jpeg") || originalName.endsWith(".JPEG"))) {
                throw new TipException("您上传的图片类型有误，请上传格式为jpg、png或gif");
            }

            // 获取图片的大小
            long fileSize = file.getSize();

            // 图片大小不能超过2M, 2M = 2 * 1024 * 1024B = 2097152B
            if (fileSize > 2097152L) {
                throw new TipException("您上传的图片超过2M");
            }

            Config config = configCache.get(Config.CONFIG_IMG_GROUP_LOGO_PATH);
            // 保存头像的根目录
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

            result.put("url", dateDirName + "/" + fileNewName);
            result.put("msg", "上传成功");
        } catch (TipException e) {
            result.put("url", "");
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            log.error("上传失败", e);
            result.put("url", "");
            result.put("msg", "上传失败");
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
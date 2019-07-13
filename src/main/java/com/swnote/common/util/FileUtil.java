package com.swnote.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理文件相关的工具类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-09]
 */
public class FileUtil {

    /**
     * 复制文件
     * 
     * @param input
     * @param output
     */
    public static void copy(InputStream input, OutputStream output) throws Exception {
        try (BufferedInputStream in = new BufferedInputStream(input);
             BufferedOutputStream out = new BufferedOutputStream(output);) {

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
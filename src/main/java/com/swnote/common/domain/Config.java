package com.swnote.common.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 站点相关配置信息
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-04]
 */
@Data
@NoArgsConstructor
@TableName("comm_config")
public class Config implements Serializable {
    private static final long serialVersionUID = 2408416388054324323L;

    /**
     * 配置项key
     */
    @TableId(value = "configId", type = IdType.INPUT)
    private String configId;

    /**
     * 配置项value
     */
    @TableField("configValue")
    private String configValue;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 头像图片的保存根路径的key
     */
    public static final String CONFIG_IMG_AVATAR_PATH = "img_avatar_path";
}
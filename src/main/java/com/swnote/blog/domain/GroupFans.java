package com.swnote.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 专栏关注信息
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-21]
 */
@Data
@NoArgsConstructor
@TableName("blog_group_fans")
public class GroupFans implements Serializable {
    private static final long serialVersionUID = 5182891629964648689L;

    /**
     * 专栏id
     */
    @TableId(value = "groupId", type = IdType.INPUT)
    private String groupId;

    /**
     * 用户id
     */
    @TableField("userId")
    private String userId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;
}
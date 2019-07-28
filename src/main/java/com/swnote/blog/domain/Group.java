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
 * 专栏信息
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-14]
 */
@Data
@NoArgsConstructor
@TableName("blog_group")
public class Group implements Serializable {
    private static final long serialVersionUID = 3028840276651248638L;

    /**
     * 主键
     */
    @TableId(value = "groupId", type = IdType.UUID)
    private String groupId;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 图标logo路径
     */
    @TableField("logo")
    private String logo;

    /**
     * 介绍
     */
    @TableField("introduce")
    private String introduce;

    /**
     * 审核状态，0：未审核，1：已审核，-1：审核不通过
     */
    @TableField("status")
    private Integer status;

    /**
     * 所属分类id
     */
    @TableField("categoryId")
    private String categoryId;

    /**
     * 创建者
     */
    @TableField("creator")
    private String creator;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 审核状态 - 未审核
     */
    public static final int STATUS_NO = 0;

    /**
     * 审核状态 - 已审核
     */
    public static final int STATUS_SUCCESS = 1;

    /**
     * 审核状态 - 审核不通过
     */
    public static final int STATUS_REJECT = -1;
}
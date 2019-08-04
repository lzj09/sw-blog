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
 * 文章信息
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Data
@NoArgsConstructor
@TableName("blog_article")
public class Article implements Serializable {
    private static final long serialVersionUID = 8936822135244917946L;

    /**
     * 主键
     */
    @TableId(value = "articleId", type = IdType.UUID)
    private String articleId;

    /**
     * 专栏信息表主键
     */
    @TableField("groupId")
    private String groupId;

    /**
     * 文章类型，1：原创，2：翻译，3：转载
     */
    @TableField("type")
    private Integer type;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 文章状态，0：待审核，1：审核通过，-1：审核不通过
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否置顶，0：不置顶，1置顶
     */
    @TableField("canTop")
    private Integer canTop;

    /**
     * 是否可以评论，0：不可评论，1：可以评论
     */
    @TableField("canComment")
    private Integer canComment;

    /**
     * 浏览次数
     */
    @TableField("viewCount")
    private Long viewCount;

    /**
     * 点赞次数
     */
    @TableField("goodNum")
    private Long goodNum;

    /**
     * 踩的次数
     */
    @TableField("badNum")
    private Long badNum;

    /**
     * 审核管理员id
     */
    @TableField("checkAdmin")
    private String checkAdmin;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 创建时间ip
     */
    @TableField("createIp")
    private String createIp;

    /**
     * 发布时间
     */
    @TableField("publishTime")
    private Date publishTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 用户id
     */
    @TableField("userId")
    private String userId;

    /**
     * 扩展属性- 用户名称
     */
    @TableField(exist = false, select = false)
    private String userName;

    /**
     * 扩展属性 - 专栏名称
     */
    @TableField(exist = false, select = false)
    private String groupName;

    /**
     * 是否置顶 - 置顶
     */
    public final static int TOP_YES = 1;

    /**
     * 是否置顶 - 不置顶
     */
    public final static int TOP_NO = 0;

    /**
     * 是否可以评论 - 可以评论
     */
    public final static int COMMENT_YES = 1;

    /**
     * 是否可以评论 - 不可评论
     */
    public final static int COMMENT_NO = 0;

    /**
     * 文章状态 - 待审核
     */
    public final static int STATUS_NO = 0;

    /**
     * 文章状态 - 审核通过
     */
    public final static int STATUS_SUCCESS = 1;

    /**
     * 文章状态 - 审核不通过
     */
    public final static int STATUS_REJECT = -1;

    /**
     * 文章类型 - 原创
     */
    public final static int TYPE_ORIGINAL = 1;

    /**
     * 文章类型 - 翻译
     */
    public final static int TYPE_TRANSLATION = 2;

    /**
     * 文章类型 - 转载
     */
    public final static int TYPE_REPRINT = 3;
}
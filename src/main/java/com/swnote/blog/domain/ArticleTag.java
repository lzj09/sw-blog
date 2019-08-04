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
 * 文章标签关系
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Data
@NoArgsConstructor
@TableName("blog_article_tag")
public class ArticleTag implements Serializable {
    private static final long serialVersionUID = 5518470644282040067L;

    /**
     * 标签表主键
     */
    @TableId(value = "tagId", type = IdType.INPUT)
    private String tagId;

    /**
     * 文章表主键
     */
    @TableField("articleId")
    private String articleId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;
}

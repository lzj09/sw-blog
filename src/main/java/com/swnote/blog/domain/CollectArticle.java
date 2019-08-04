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
 * 收藏文章信息
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Data
@NoArgsConstructor
@TableName("blog_collect_article")
public class CollectArticle implements Serializable {
    private static final long serialVersionUID = -2079668728606677767L;

    /**
     * 用户表主键
     */
    @TableId(value = "userId", type = IdType.INPUT)
    private String userId;

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

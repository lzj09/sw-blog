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
 * 标签信息
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Data
@NoArgsConstructor
@TableName("blog_tag")
public class Tag implements Serializable {
    private static final long serialVersionUID = -7861296334686648705L;

    /**
     * 主键
     */
    @TableId(value = "tagId", type = IdType.UUID)
    private String tagId;

    /**
     * 标签
     */
    @TableField("tag")
    private String tag;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;
}

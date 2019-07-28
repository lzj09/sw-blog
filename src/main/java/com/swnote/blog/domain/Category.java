package com.swnote.blog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分类信息
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-21]
 */
@Data
@NoArgsConstructor
@TableName("blog_category")
public class Category implements Serializable {
    private static final long serialVersionUID = 6961004843019948492L;

    /**
     * 主键
     */
    @TableId(value = "categoryId", type = IdType.UUID)
    private String categoryId;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;

    /**
     * 说明
     */
    @TableField("introduce")
    private String introduce;

    /**
     * 父节点id
     */
    @TableField("parentId")
    private String parentId;

    /**
     * 排序号
     */
    @TableField("orderId")
    private Integer orderId;
}

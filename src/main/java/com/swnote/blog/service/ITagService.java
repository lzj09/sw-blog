package com.swnote.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swnote.blog.domain.Tag;

import java.util.List;

/**
 * 标签信息服务接口
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
public interface ITagService extends IService<Tag> {

    /**
     * 根据文章ID获取标签列表
     *
     * @param articleId
     * @return
     */
    public List<Tag> queryByArticleId(String articleId);
}

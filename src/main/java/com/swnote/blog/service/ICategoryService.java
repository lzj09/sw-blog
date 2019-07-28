package com.swnote.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swnote.blog.domain.Category;

import java.util.List;

/**
 * 分类信息服务接口
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-21]
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 根据parentId查询分类信息
     *
     * @param parentId
     * @return
     */
    public List<Category> getByParentId(String parentId);
}

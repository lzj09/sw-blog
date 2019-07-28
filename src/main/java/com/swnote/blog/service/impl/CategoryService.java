package com.swnote.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.blog.dao.CategoryDao;
import com.swnote.blog.domain.Category;
import com.swnote.blog.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类信息服务类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-21]
 */
@Service
public class CategoryService extends ServiceImpl<CategoryDao, Category> implements ICategoryService {

    @Override
    public List<Category> getByParentId(String parentId) {
        return list(new QueryWrapper<Category>().lambda().eq(Category::getParentId, parentId).orderByAsc(Category::getOrderId));
    }
}

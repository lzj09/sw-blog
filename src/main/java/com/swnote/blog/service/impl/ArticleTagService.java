package com.swnote.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.blog.dao.ArticleTagDao;
import com.swnote.blog.domain.ArticleTag;
import com.swnote.blog.service.IArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关系服务类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Service
public class ArticleTagService extends ServiceImpl<ArticleTagDao, ArticleTag> implements IArticleTagService {
}

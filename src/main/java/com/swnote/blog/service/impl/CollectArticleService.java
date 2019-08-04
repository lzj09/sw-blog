package com.swnote.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.blog.dao.CollectArticleDao;
import com.swnote.blog.domain.CollectArticle;
import com.swnote.blog.service.ICollectArticleService;
import org.springframework.stereotype.Service;

/**
 * 收藏文章信息服务类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Service
public class CollectArticleService extends ServiceImpl<CollectArticleDao, CollectArticle> implements ICollectArticleService {
}

package com.swnote.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.blog.dao.TagDao;
import com.swnote.blog.domain.Tag;
import com.swnote.blog.service.ITagService;
import org.springframework.stereotype.Service;

/**
 * 标签信息服务类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-31]
 */
@Service
public class TagService extends ServiceImpl<TagDao, Tag> implements ITagService {
}

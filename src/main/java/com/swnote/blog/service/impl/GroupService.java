package com.swnote.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.blog.dao.GroupDao;
import com.swnote.blog.domain.Group;
import com.swnote.blog.service.IGroupService;
import org.springframework.stereotype.Service;

/**
 * 专栏信息服务类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-14]
 */
@Service
public class GroupService extends ServiceImpl<GroupDao, Group> implements IGroupService {
}

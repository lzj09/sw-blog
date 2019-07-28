package com.swnote.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swnote.blog.dao.GroupFansDao;
import com.swnote.blog.domain.GroupFans;
import com.swnote.blog.service.IGroupFansService;
import org.springframework.stereotype.Service;


/**
 * 专栏关注信息服务类
 *
 * @author lzj
 * @since 1.0
 * @date [2019-07-21]
 */
@Service
public class GroupFansService extends ServiceImpl<GroupFansDao, GroupFans> implements IGroupFansService {
}

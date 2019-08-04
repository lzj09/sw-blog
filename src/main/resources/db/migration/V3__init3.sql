CREATE TABLE `blog_article` (
  `articleId` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `groupId` varchar(64) DEFAULT NULL COMMENT '专栏信息表主键',
  `type` int(11) DEFAULT NULL COMMENT '文章类型，1：原创，2：翻译，3：转载',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT '标题',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `content` longtext COMMENT '内容',
  `status` int(11) DEFAULT NULL COMMENT '文章状态，1：待审核，2：审核通过，-1：审核不通过',
  `canTop` int(11) DEFAULT NULL COMMENT '是否置顶，0：不置顶，1置顶',
  `canComment` int(11) DEFAULT NULL COMMENT '是否可以评论，0：不可评论，1：可以评论',
  `viewCount` bigint(20) unsigned DEFAULT NULL COMMENT '浏览次数',
  `goodNum` bigint(20) unsigned DEFAULT NULL COMMENT '点赞次数',
  `badNum` bigint(20) unsigned DEFAULT NULL COMMENT '踩的次数',
  `checkAdmin` varchar(64) DEFAULT NULL COMMENT '审核管理员id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createIp` varchar(64) DEFAULT NULL COMMENT '创建时间ip',
  `publishTime` datetime DEFAULT NULL COMMENT '发布时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `userId` varchar(64) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`articleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章信息';

CREATE TABLE `blog_article_tag` (
  `tagId` varchar(64) NOT NULL DEFAULT '' COMMENT '标签表主键',
  `articleId` varchar(64) NOT NULL COMMENT '文章表主键',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`tagId`,`articleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章标签关系';

CREATE TABLE `blog_tag` (
  `tagId` varchar(64) NOT NULL COMMENT '主键',
  `tag` varchar(200) DEFAULT NULL COMMENT '标签',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`tagId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签信息';

CREATE TABLE `blog_collect_article` (
  `userId` varchar(64) NOT NULL COMMENT '用户表主键',
  `articleId` varchar(64) NOT NULL COMMENT '文章表主键',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`userId`,`articleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收藏文章信息';
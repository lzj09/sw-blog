CREATE TABLE  `blog_group` (
  `groupId` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `logo` varchar(200) DEFAULT NULL COMMENT '图标logo路径',
  `introduce` varchar(2000) DEFAULT NULL COMMENT '介绍',
  `status` int(11) DEFAULT NULL COMMENT '审核状态，0：未审核，1：已审核，-1：审核不通过',
  `categoryId` varchar(64) DEFAULT NULL COMMENT '所属分类id',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专栏信息';

CREATE TABLE `blog_group_fans` (
  `groupId` varchar(64) NOT NULL COMMENT '专栏id',
  `userId` varchar(64) NOT NULL COMMENT '用户id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`groupId`,`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专栏关注信息';

CREATE TABLE `blog_category` (
  `categoryId` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(200) DEFAULT NULL COMMENT '分类名称',
  `introduce` varchar(2000) DEFAULT NULL COMMENT '说明',
  `parentId` varchar(64) DEFAULT NULL COMMENT '父节点id',
  `orderId` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类信息';
CREATE TABLE `comm_config` (
  `configId` varchar(200) NOT NULL COMMENT '主键',
  `configValue` varchar(1024) DEFAULT NULL COMMENT '配置值',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`configId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站点相关配置信息';

CREATE TABLE `auth_user` (
  `userId` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `code` varchar(64) DEFAULT NULL COMMENT '用户代码',
  `loginName` varchar(200) DEFAULT NULL COMMENT '用户名',
  `email` varchar(200) DEFAULT NULL COMMENT '电子邮箱',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `realName` varchar(200) DEFAULT NULL COMMENT '真实姓名',
  `cellphone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `idCard` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `idCardImgPath` varchar(200) DEFAULT NULL COMMENT '身份证照片路径',
  `realStatus` int(11) DEFAULT NULL COMMENT '实名认证状态，0：未实名认证，1：已实名认证，-1：实名认证失败',
  `sex` int(11) DEFAULT NULL COMMENT '性别，0：表示女，1：表示男，-1：表示保密',
  `picture` varchar(200) DEFAULT NULL COMMENT '头像图片路径',
  `introduce` varchar(2000) DEFAULT NULL COMMENT '个人简介',
  `isActive` int(11) DEFAULT NULL COMMENT '是否激活，0：未激活，1：已激活',
  `status` int(11) DEFAULT NULL COMMENT '账号状态，0：禁用，1：启用',
  `follows` int(11) DEFAULT NULL COMMENT '关注用户数量',
  `fans` int(11) DEFAULT NULL COMMENT '粉丝数量',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间，也即注册时间',
  `createIp` varchar(32) DEFAULT NULL COMMENT '创建时的ip地址',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '最近一次登录的时间',
  `lastLoginIp` varchar(32) DEFAULT NULL COMMENT '最近一次登录的ip',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';
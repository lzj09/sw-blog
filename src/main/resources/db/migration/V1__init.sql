CREATE TABLE `comm_config` (
  `configId` varchar(200) NOT NULL COMMENT '主键',
  `configValue` varchar(1024) DEFAULT NULL COMMENT '配置值',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`configId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站点相关配置信息';
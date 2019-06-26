# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.41)
# Database: half_classroom
# Generation Time: 2018-11-19 10:02:35 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table admin_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `admin_user`;

CREATE TABLE `admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `roleid` varchar(255) DEFAULT NULL COMMENT '角色id',
  `branchsalerid` int(11) DEFAULT NULL COMMENT '分会id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  `secret` varchar(4000) DEFAULT NULL COMMENT '分销商key',
  `superaccount` varchar(45) DEFAULT NULL COMMENT '上级key',
  `fullindex` varchar(4000) DEFAULT NULL COMMENT '全路径',
  `level` varchar(200) DEFAULT NULL COMMENT '等级',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

LOCK TABLES `admin_user` WRITE;
/*!40000 ALTER TABLE `admin_user` DISABLE KEYS */;

INSERT INTO `admin_user` (`id`, `avatar`, `account`, `password`, `salt`, `name`, `birthday`, `sex`, `email`, `phone`, `roleid`, `branchsalerid`, `status`, `version`, `secret`, `superaccount`, `fullindex`, `level`, `createtime`)
VALUES
	(1,'girl.gif','admin','57816f6c885d2fdc7e8d53008b58730c','8pgby','阿龙哥','1985-05-05 00:00:00',2,'4326922@qq.com','13701235674','1',24,1,25,'D6Bn7lH2x7g8LkiHHhJb',NULL,'admin','0','2018-11-01 08:49:53'),
	(2,NULL,'dist','e46b7e4742c2ca7efd8ed23117e38daa','bm26x','黑龙江分会管理员','2018-04-02 00:00:00',1,'feng_hrb@msn.com','13701235674','5',32,1,NULL,'FlDvmDNcN47AF40L2tjz','admin','admin.dist','1','2018-04-08 14:25:57');

/*!40000 ALTER TABLE `admin_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table branchsaler
# ------------------------------------------------------------

DROP TABLE IF EXISTS `branchsaler`;

CREATE TABLE `branchsaler` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` int(11) DEFAULT NULL COMMENT '父分会id',
  `pids` varchar(255) DEFAULT NULL COMMENT '父级分会列表ids',
  `simplename` varchar(45) DEFAULT NULL COMMENT '简称',
  `fullname` varchar(255) DEFAULT NULL COMMENT '全称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分会表';

LOCK TABLES `branchsaler` WRITE;
/*!40000 ALTER TABLE `branchsaler` DISABLE KEYS */;

INSERT INTO `branchsaler` (`id`, `num`, `pid`, `pids`, `simplename`, `fullname`, `tips`, `version`)
VALUES
	(24,1,0,'[0],','总会','半间教室总部','',NULL),
	(25,2,24,'[0],[24],','山东','山东分会','',NULL),
	(26,3,24,'[0],[24],','江苏','江苏分会','',NULL),
	(27,4,24,'[0],[24],','上海','上海分会','',NULL),
	(32,1,24,'[0],[24],','黑龙江','黑龙江分会','',NULL),
	(33,NULL,32,'[0],[24],[32],','哈尔滨','哈尔滨分会','半间教室哈尔滨',NULL);

/*!40000 ALTER TABLE `branchsaler` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table dict
# ------------------------------------------------------------

DROP TABLE IF EXISTS `dict`;

CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` int(11) DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

LOCK TABLES `dict` WRITE;
/*!40000 ALTER TABLE `dict` DISABLE KEYS */;

INSERT INTO `dict` (`id`, `num`, `pid`, `name`, `tips`)
VALUES
	(16,0,0,'状态',NULL),
	(17,1,16,'启用',NULL),
	(18,2,16,'禁用',NULL),
	(29,0,0,'性别',NULL),
	(30,1,29,'男',NULL),
	(31,2,29,'女',NULL),
	(35,0,0,'账号状态',NULL),
	(36,1,35,'启用',NULL),
	(37,2,35,'冻结',NULL),
	(38,3,35,'已删除',NULL);

/*!40000 ALTER TABLE `dict` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table login_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `login_log`;

CREATE TABLE `login_log` (
  `id` int(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` int(65) DEFAULT NULL COMMENT '管理员id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否执行成功',
  `message` text COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录记录';



# Dump of table menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` int(65) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `ismenu` int(11) DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;

INSERT INTO `menu` (`id`, `code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `num`, `levels`, `ismenu`, `tips`, `status`, `isopen`)
VALUES
	(105,'system','0','[0],','系统管理','fa-user','',1,1,1,NULL,1,1),
	(106,'mgr','system','[0],[system],','用户管理','','/mgr',1,2,1,NULL,1,0),
	(107,'mgr_add','mgr','[0],[system],[mgr],','添加用户',NULL,'/mgr/add',1,3,0,NULL,1,0),
	(108,'mgr_edit','mgr','[0],[system],[mgr],','修改用户',NULL,'/mgr/edit',2,3,0,NULL,1,0),
	(109,'mgr_delete','mgr','[0],[system],[mgr],','删除用户',NULL,'/mgr/delete',3,3,0,NULL,1,0),
	(110,'mgr_reset','mgr','[0],[system],[mgr],','重置密码',NULL,'/mgr/reset',4,3,0,NULL,1,0),
	(111,'mgr_freeze','mgr','[0],[system],[mgr],','冻结用户',NULL,'/mgr/freeze',5,3,0,NULL,1,0),
	(112,'mgr_unfreeze','mgr','[0],[system],[mgr],','解除冻结用户',NULL,'/mgr/unfreeze',6,3,0,NULL,1,0),
	(113,'mgr_setRole','mgr','[0],[system],[mgr],','分配角色',NULL,'/mgr/setRole',7,3,0,NULL,1,0),
	(114,'role','system','[0],[system],','角色管理',NULL,'/role',2,2,1,NULL,1,0),
	(115,'role_add','role','[0],[system],[role],','添加角色',NULL,'/role/add',1,3,0,NULL,1,0),
	(116,'role_edit','role','[0],[system],[role],','修改角色',NULL,'/role/edit',2,3,0,NULL,1,0),
	(117,'role_remove','role','[0],[system],[role],','删除角色',NULL,'/role/remove',3,3,0,NULL,1,0),
	(118,'role_setAuthority','role','[0],[system],[role],','配置权限',NULL,'/role/setAuthority',4,3,0,NULL,1,0),
	(119,'menu','system','[0],[system],','菜单管理',NULL,'/menu',4,2,1,NULL,1,0),
	(120,'menu_add','menu','[0],[system],[menu],','添加菜单',NULL,'/menu/add',1,3,0,NULL,1,0),
	(121,'menu_edit','menu','[0],[system],[menu],','修改菜单',NULL,'/menu/edit',2,3,0,NULL,1,0),
	(122,'menu_remove','menu','[0],[system],[menu],','删除菜单',NULL,'/menu/remove',3,3,0,NULL,1,0),
	(128,'log','system','[0],[system],','业务日志',NULL,'/log',6,2,1,NULL,1,0),
	(130,'druid','system','[0],[system],','监控管理',NULL,'/druid',7,2,1,NULL,1,NULL),
	(131,'dept','system','[0],[system],','分会管理',NULL,'/dept',3,2,1,NULL,1,NULL),
	(133,'loginLog','system','[0],[system],','登录日志',NULL,'/loginLog',6,2,1,NULL,1,NULL),
	(134,'log_clean','log','[0],[system],[log],','清空日志',NULL,'/log/delLog',3,3,0,NULL,1,NULL),
	(135,'dept_add','dept','[0],[system],[dept],','添加分会',NULL,'/dept/add',1,3,0,NULL,1,NULL),
	(136,'dept_update','dept','[0],[system],[dept],','修改分会',NULL,'/dept/update',1,3,0,NULL,1,NULL),
	(137,'dept_delete','dept','[0],[system],[dept],','删除分会',NULL,'/dept/delete',1,3,0,NULL,1,NULL),
	(141,'notice','system','[0],[system],','通知管理',NULL,'/notice',9,2,1,NULL,1,NULL),
	(142,'notice_add','notice','[0],[system],[notice],','添加通知',NULL,'/notice/add',1,3,0,NULL,1,NULL),
	(143,'notice_update','notice','[0],[system],[notice],','修改通知',NULL,'/notice/update',2,3,0,NULL,1,NULL),
	(144,'notice_delete','notice','[0],[system],[notice],','删除通知',NULL,'/notice/delete',3,3,0,NULL,1,NULL),
	(150,'to_menu_edit','menu','[0],[system],[menu],','菜单编辑跳转','','/menu/menu_edit',4,3,0,NULL,1,NULL),
	(151,'menu_list','menu','[0],[system],[menu],','菜单列表','','/menu/list',5,3,0,NULL,1,NULL),
	(152,'to_dept_update','dept','[0],[system],[dept],','修改分会跳转','','/dept/dept_update',4,3,0,NULL,1,NULL),
	(153,'dept_list','dept','[0],[system],[dept],','分会列表','','/dept/list',5,3,0,NULL,1,NULL),
	(154,'dept_detail','dept','[0],[system],[dept],','分会详情','','/dept/detail',6,3,0,NULL,1,NULL),
	(158,'log_list','log','[0],[system],[log],','日志列表','','/log/list',2,3,0,NULL,1,NULL),
	(159,'log_detail','log','[0],[system],[log],','日志详情','','/log/detail',3,3,0,NULL,1,NULL),
	(160,'del_login_log','loginLog','[0],[system],[loginLog],','清空登录日志','','/loginLog/delLoginLog',1,3,0,NULL,1,NULL),
	(161,'login_log_list','loginLog','[0],[system],[loginLog],','登录日志列表','','/loginLog/list',2,3,0,NULL,1,NULL),
	(162,'to_role_edit','role','[0],[system],[role],','修改角色跳转','','/role/role_edit',5,3,0,NULL,1,NULL),
	(163,'to_role_assign','role','[0],[system],[role],','角色分配跳转','','/role/role_assign',6,3,0,NULL,1,NULL),
	(164,'role_list','role','[0],[system],[role],','角色列表','','/role/list',7,3,0,NULL,1,NULL),
	(165,'to_assign_role','mgr','[0],[system],[mgr],','分配角色跳转','','/mgr/role_assign',8,3,0,NULL,1,NULL),
	(166,'to_user_edit','mgr','[0],[system],[mgr],','编辑用户跳转','','/mgr/user_edit',9,3,0,NULL,1,NULL),
	(167,'mgr_list','mgr','[0],[system],[mgr],','用户列表','','/mgr/list',10,3,0,NULL,1,NULL),
	(168,'memmber_manage','huiyuanxinxi','[0],[huiyuanxinxi],','会员管理','','/disMemberInfo',2,2,1,NULL,1,NULL),
	(169,'profit_param','fenxiaopeizhi','[0],[fenxiaopeizhi],','分润设置','','/disProfiParam',3,2,1,NULL,1,NULL),
	(170,'profit_order','jiaoyizhongxin','[0],[jiaoyizhongxin],','分润信息','','/disProfitRecord',4,2,1,NULL,1,NULL),
	(171,'member_add','memmber_manage','[0],[memmber_manage],','模拟新增会员','','/disMemberInfo/add',1,2,0,NULL,1,NULL),
	(172,'profit_param_add','profit_param','[0],[profit_param],','新增分润类型','','/disProfiParam/add',1,2,0,NULL,1,NULL),
	(173,'profit_param_delete','profit_param','[0],[profit_param],','删除','','/disProfiParam/delete',2,2,0,NULL,1,NULL),
	(174,'profit_order_add','profit_order','[0],[profit_order],','模拟交易','','/disProfitRecord/add',1,2,0,NULL,1,NULL),
	(175,'disMemberInfo_view','memmber_manage','[0],[memmber_manage],','查看关系图','','/disMemberInfo/view',5,2,0,NULL,1,NULL),
	(177,'disProfiParam_menu','profit_param','[0],[profit_param],','分润查询','','/disProfiParam/menu',1,2,0,NULL,1,NULL),
	(178,'dic','fenxiaopeizhi','[0],[fenxiaopeizhi],','分销字典管理','','/dic',9,2,1,NULL,1,NULL),
	(179,'dic_add','dic','[0],[system],[dic],','分销字典增加','','/dic/add',2,3,0,NULL,1,NULL),
	(180,'dic_update','dic','[0],[system],[dic],','分销字典修改','','/dic/update',2,3,0,NULL,1,NULL),
	(181,'dic_delete','dic','[0],[dic],','分销 字典删除','','/dic/delete',3,2,0,NULL,1,NULL),
	(182,'disMemberAmount','huiyuanxinxi','[0],[huiyuanxinxi],','账单查询','','/disMemberAmount',4,2,1,NULL,1,NULL),
	(183,'disMemberAmount_menu','disMemberAmount','[0],[disMemberAmount],','账单查询','','/disMemberAmount/menu',1,2,0,NULL,1,NULL),
	(186,'DistWithdrawParam','fenxiaopeizhi','[0],[fenxiaopeizhi],','提现费率设置','','/DistWithdrawParam',5,2,1,NULL,1,NULL),
	(187,'DistWithdrawParam_menu','DistWithdrawParam','[0],[DistWithdrawParam],','菜单','','/DistWithdrawParam/menu',1,2,0,NULL,1,NULL),
	(188,'DistWithdrawParam_add','DistWithdrawParam','[0],[DistWithdrawParam],','新增','','/DistWithdrawParam/add',3,2,0,NULL,1,NULL),
	(189,'DistWithdrawParam_update','DistWithdrawParam','[0],[DistWithdrawParam],','修改','','/DistWithdrawParam/update',4,2,0,NULL,1,NULL),
	(190,'DistWithdrawParam_delete','DistWithdrawParam','[0],[DistWithdrawParam],','删除','','/DistWithdrawParam/delete',6,2,0,NULL,1,NULL),
	(191,'DisWithdrawRecord','jiaoyizhongxin','[0],[jiaoyizhongxin],','提现信息','','/DisWithdrawRecord',6,2,1,NULL,1,NULL),
	(192,'DisWithdrawRecord_add','DisWithdrawRecord','[0],[DisWithdrawRecord],','菜单','','/DisWithdrawRecord/add',1,2,0,NULL,1,NULL),
	(194,'huiyuanxinxi','0','[0],','会员信息','','/',2,1,1,NULL,1,NULL),
	(195,'fenxiaopeizhi','0','[0],','分销配置','','/',3,1,1,NULL,1,NULL),
	(196,'jiaoyizhongxin','0','[0],','交易中心','','/',4,1,1,NULL,1,NULL),
	(197,'statisticalCenter','0','[0],','统计中心','','/',6,1,1,NULL,1,NULL),
	(198,'dynamic','statisticalCenter','[0],[statisticalCenter],','交易动态','','/dynamic',1,2,1,NULL,1,NULL),
	(199,'dynamic_menu','dynamic','[0],[statisticalCenter],[dynamic],','动态查询','','/dynamic/menu',1,3,0,NULL,1,NULL),
	(200,'dynamic_myaccount','statisticalCenter','[0],[statisticalCenter],','我的账户','','/dynamic/myaccount',2,2,1,NULL,1,NULL),
	(201,'DisRankParam_menu','fenxiaopeizhi','[0],[fenxiaopeizhi],','段位积分设置','','/DisRankParam',4,2,1,NULL,1,NULL),
	(202,'disRankParam_add','DisRankParam_menu','[0],[fenxiaopeizhi],[DisRankParam_menu],','添加','','/DisRankParam/add',1,3,0,NULL,1,NULL),
	(203,'disRankParam_update','DisRankParam_menu','[0],[fenxiaopeizhi],[DisRankParam_menu],','段位积分修改','','/DisRankParam/update',2,3,0,NULL,1,NULL),
	(204,'disRankParam_delete','DisRankParam_menu','[0],[fenxiaopeizhi],[DisRankParam_menu],','段位积分删除','','/DisRankParam/delete',3,3,0,NULL,1,NULL),
	(205,'DisUpgradeParam','fenxiaopeizhi','[0],[fenxiaopeizhi],','垂直升级配置','','/DisUpgradeParam',6,2,1,NULL,1,NULL),
	(206,'DisUpgradeParam_add','DisUpgradeParam','[0],[fenxiaopeizhi],[DisUpgradeParam],','添加','','/DisUpgradeParam/add',1,3,0,NULL,1,NULL),
	(207,'DisUpgradeParam_update','DisUpgradeParam','[0],[fenxiaopeizhi],[DisUpgradeParam],','修改','','/DisUpgradeParam/update',2,3,0,NULL,1,NULL),
	(208,'DisUpgradeParam_delete','DisUpgradeParam','[0],[fenxiaopeizhi],[DisUpgradeParam],','删除','','/DisUpgradeParam/delete',3,3,0,NULL,1,NULL);

/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table notice
# ------------------------------------------------------------

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` text COMMENT '内容',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知表';

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;

INSERT INTO `notice` (`id`, `title`, `type`, `content`, `createtime`, `creater`)
VALUES
	(6,'欢迎界面',10,'欢迎使用半间教室分会管理系统测试','2018-11-11 08:53:20',1);

/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table operation_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `operation_log`;

CREATE TABLE `operation_log` (
  `id` int(65) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `logtype` varchar(255) DEFAULT NULL COMMENT '日志类型',
  `logname` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `userid` int(65) DEFAULT NULL COMMENT '用户id',
  `classname` varchar(255) DEFAULT NULL COMMENT '类名称',
  `method` text COMMENT '方法名称',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(255) DEFAULT NULL COMMENT '是否成功',
  `message` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';



# Dump of table relation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `relation`;

CREATE TABLE `relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuid` int(11) DEFAULT NULL COMMENT '菜单id',
  `roleid` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

LOCK TABLES `relation` WRITE;
/*!40000 ALTER TABLE `relation` DISABLE KEYS */;

INSERT INTO `relation` (`id`, `menuid`, `roleid`)
VALUES
	(5327,105,1),
	(5328,106,1),
	(5329,107,1),
	(5330,108,1),
	(5331,109,1),
	(5332,110,1),
	(5333,111,1),
	(5334,112,1),
	(5335,113,1),
	(5336,165,1),
	(5337,166,1),
	(5338,167,1),
	(5339,114,1),
	(5340,115,1),
	(5341,116,1),
	(5342,117,1),
	(5343,118,1),
	(5344,162,1),
	(5345,163,1),
	(5346,164,1),
	(5347,119,1),
	(5348,120,1),
	(5349,121,1),
	(5350,122,1),
	(5351,150,1),
	(5352,151,1),
	(5353,128,1),
	(5354,134,1),
	(5355,158,1),
	(5356,159,1),
	(5357,130,1),
	(5358,131,1),
	(5359,135,1),
	(5360,136,1),
	(5361,137,1),
	(5362,152,1),
	(5363,153,1),
	(5364,154,1),
	(5365,133,1),
	(5366,160,1),
	(5367,161,1),
	(5368,141,1),
	(5369,142,1),
	(5370,143,1),
	(5371,144,1),
	(5379,194,1),
	(5380,168,1),
	(5381,171,1),
	(5382,175,1),
	(5383,182,1),
	(5384,183,1),
	(5385,195,1),
	(5386,169,1),
	(5387,172,1),
	(5388,173,1),
	(5389,177,1),
	(5390,178,1),
	(5391,179,1),
	(5392,180,1),
	(5393,181,1),
	(5394,186,1),
	(5395,187,1),
	(5396,188,1),
	(5397,189,1),
	(5398,190,1),
	(5399,201,1),
	(5400,202,1),
	(5401,203,1),
	(5402,204,1),
	(5403,205,1),
	(5404,206,1),
	(5405,207,1),
	(5406,208,1),
	(5407,196,1),
	(5408,170,1),
	(5409,174,1),
	(5410,191,1),
	(5411,192,1),
	(5412,197,1),
	(5413,198,1),
	(5414,199,1),
	(5415,200,1),
	(5416,194,5),
	(5417,168,5),
	(5418,171,5),
	(5419,175,5),
	(5420,182,5),
	(5421,183,5),
	(5422,195,5),
	(5423,169,5),
	(5424,172,5),
	(5425,173,5),
	(5426,177,5),
	(5427,178,5),
	(5428,179,5),
	(5429,180,5),
	(5430,181,5),
	(5431,186,5),
	(5432,187,5),
	(5433,188,5),
	(5434,189,5),
	(5435,190,5),
	(5436,201,5),
	(5437,202,5),
	(5438,203,5),
	(5439,204,5),
	(5440,205,5),
	(5441,206,5),
	(5442,207,5),
	(5443,208,5),
	(5444,196,5),
	(5445,170,5),
	(5446,174,5),
	(5447,191,5),
	(5448,192,5),
	(5449,197,5),
	(5450,198,5),
	(5451,199,5),
	(5452,200,5);

/*!40000 ALTER TABLE `relation` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `pid` int(11) DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `branchsalerid` int(11) DEFAULT NULL COMMENT '分会名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `num`, `pid`, `name`, `branchsalerid`, `tips`, `version`)
VALUES
	(1,1,0,'半间教室管理员',24,'总会管理员',1),
	(5,2,1,'黑龙江管理员',24,'黑龙江分会管理员',1),
	(7,3,1,'辽宁管理员',24,'辽宁分会管理员',1),
	(8,4,5,'哈尔滨管理员',32,'哈尔滨分会管理员',1),
	(9,NULL,5,'齐齐哈尔管理员',32,'齐齐哈尔分会管理员',1),
	(10,NULL,5,'大庆管理员',32,'大庆分会管理员',1),
	(11,NULL,1,'上海管理员',24,'上海分会管理员',1);

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT '男' COMMENT '性别',
  `headImage` varchar(255) DEFAULT NULL COMMENT '头像',
  `birthday` varchar(0) DEFAULT NULL COMMENT '出生日期',
  `country` varchar(255) DEFAULT '中国',
  `province` int(11) DEFAULT NULL COMMENT '省',
  `city` int(11) DEFAULT NULL COMMENT '市',
  `county` int(11) DEFAULT NULL COMMENT '县',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址带省市县',
  `registerEntry` int(11) DEFAULT '0' COMMENT '注册来源  0-h5 1安卓 2ios',
  `registerIntroducer` bigint(20) DEFAULT NULL COMMENT '如果是通过推广大使或者阅读大使的二维码注册的，则表示推广大使或阅读大使的会员id',
  `integral` bigint(20) DEFAULT '0' COMMENT '积分 推荐人（推广大使、阅读大使）获得积分，只有通过推荐人体验码注册这儿才有积分',
  `memberStatus` int(11) DEFAULT '0' COMMENT '0.体验中 1.体验过期 2.正式会员 3.会员过期',
  `branchSaler` int(11) DEFAULT NULL COMMENT '分会ID',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT '1' COMMENT '0表示不可用，1表示可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ph` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

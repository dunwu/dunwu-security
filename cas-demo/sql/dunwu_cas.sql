/*
SQLyog Community v12.09 (64 bit)
MySQL - 5.7.31 : Database - dunwu_cas
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
create DATABASE /*!32312 IF NOT EXISTS*/`dunwu_cas` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dunwu_cas`;

/*Table structure for table `dept` */

create TABLE `dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT '0' COMMENT '上级部门',
  `sub_count` int(5) DEFAULT '0' COMMENT '子部门数目',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `weight` int(5) DEFAULT '999' COMMENT '排序',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `key_pid` (`pid`),
  KEY `key_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门';

/*Data for the table `dept` */

insert  into `dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,7,1,'研发部',3,'',NULL,'admin','admin','2019-03-25 09:15:32','2020-08-02 14:48:47');
insert  into `dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,7,0,'运维部',4,'',NULL,'admin','admin','2019-03-25 09:20:44','2020-05-17 14:27:27');
insert  into `dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,8,0,'测试部',6,'',NULL,'admin','admin','2019-03-25 09:52:18','2020-06-08 11:59:21');
insert  into `dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,0,2,'华南分部',0,'',NULL,'admin','admin','2019-03-25 11:04:50','2020-06-08 12:08:56');
insert  into `dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (8,0,2,'华北分部',1,'',NULL,'admin','admin','2019-03-25 11:04:53','2020-05-14 12:54:00');
insert  into `dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (15,8,0,'UI部门',7,'',NULL,'admin','admin','2020-05-13 22:56:53','2020-05-14 12:54:13');
insert  into `dept`(`id`,`pid`,`sub_count`,`name`,`weight`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (17,2,0,'研发一组',999,'',NULL,'admin','admin','2020-08-02 14:49:07','2020-08-02 14:49:07');

/*Table structure for table `dept_role_map` */

create TABLE `dept_role_map` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_role_menu` (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色部门关联';

/*Data for the table `dept_role_map` */

/*Table structure for table `dict` */

create TABLE `dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(255) NOT NULL COMMENT '字典编码',
  `name` varchar(255) NOT NULL COMMENT '字典名称',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典';

/*Data for the table `dict` */

insert  into `dict`(`id`,`code`,`name`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'user_status','用户状态','','用户状态','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `dict`(`id`,`code`,`name`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'dept_status','部门状态','','部门状态','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `dict`(`id`,`code`,`name`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'job_status','岗位状态','','岗位状态','admin',NULL,'2019-10-27 20:31:36',NULL);

/*Table structure for table `dict_option` */

create TABLE `dict_option` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dict_id` bigint(11) DEFAULT NULL COMMENT '字典id',
  `code` varchar(255) NOT NULL COMMENT '字典选项编码',
  `name` varchar(255) NOT NULL COMMENT '字典选项名称',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `key_dict_id` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典详情';

/*Data for the table `dict_option` */

insert  into `dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,1,'true','激活','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,1,'false','禁用','admin',NULL,NULL,NULL);
insert  into `dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,2,'true','启用','admin',NULL,NULL,NULL);
insert  into `dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (4,2,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,3,'true','启用','admin',NULL,NULL,NULL);
insert  into `dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,3,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL);

/*Table structure for table `job` */

create TABLE `job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '岗位名称',
  `weight` int(5) DEFAULT NULL COMMENT '排序',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `enabled` bit(1) NOT NULL COMMENT '岗位状态',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`),
  KEY `key_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='岗位';

/*Data for the table `job` */

insert  into `job`(`id`,`name`,`weight`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'人事专员',3,NULL,'',NULL,'admin','admin','2019-03-29 14:52:28',NULL);
insert  into `job`(`id`,`name`,`weight`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'产品经理',4,NULL,'',NULL,'admin','admin','2019-03-29 14:55:51',NULL);
insert  into `job`(`id`,`name`,`weight`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'全栈开发',2,NULL,'',NULL,'admin','admin','2019-03-31 13:39:30','2020-05-05 11:33:43');
insert  into `job`(`id`,`name`,`weight`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (4,'软件测试',5,NULL,'',NULL,'admin','admin','2019-03-31 13:39:43','2020-05-10 19:56:26');

/*Table structure for table `job_role_map` */

create TABLE `job_role_map` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `job_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_job_role` (`job_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统岗位/角色关系表';

/*Data for the table `job_role_map` */

/*Table structure for table `menu` */

create TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级菜单ID',
  `sub_count` int(5) DEFAULT '0' COMMENT '子菜单数目',
  `type` int(11) DEFAULT NULL COMMENT '菜单类型',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
  `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `weight` int(5) DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
  `cache` bit(1) DEFAULT b'0' COMMENT '缓存',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_title` (`title`),
  UNIQUE KEY `uk_name` (`name`),
  KEY `key_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单';

/*Data for the table `menu` */

insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,NULL,7,0,'权限管理',NULL,NULL,1,'validCode','auth','\0','\0','\0',NULL,NULL,NULL,'2018-12-18 15:11:29','2021-09-26 10:47:01');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,1,3,1,'用户管理','User','user/user/index',2,'peoples','user','\0','\0','\0','user:view',NULL,NULL,'2018-12-18 15:14:44','2021-09-26 10:38:09');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,1,3,1,'角色管理','Role','user/role/index',3,'role','role','\0','\0','\0','roles:view',NULL,NULL,'2018-12-18 15:16:07','2021-09-26 10:38:11');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,1,3,1,'菜单管理','Menu','user/menu/index',5,'menu','menu','\0','\0','\0','menu:view',NULL,NULL,'2018-12-18 15:17:28','2021-09-26 10:38:13');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,NULL,5,0,'系统监控',NULL,NULL,10,'monitor','monitor','\0','\0','\0',NULL,NULL,NULL,'2018-12-18 15:17:48',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,6,0,1,'应用日志','Log','monitor/log/index',11,'log','log','\0','','\0',NULL,NULL,'admin','2018-12-18 15:18:26','2020-06-06 13:11:57');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (9,6,0,1,'SQL监控','Sql','monitor/sql/index',18,'sqlMonitor','druid','\0','\0','\0',NULL,NULL,NULL,'2018-12-18 15:19:34',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (35,1,3,1,'部门管理','Dept','user/dept/index',6,'dept','dept','\0','\0','\0','dept:view',NULL,NULL,'2019-03-25 09:46:00','2021-09-26 10:38:16');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (37,1,3,1,'岗位管理','Job','user/job/index',7,'Steve-Jobs','job','\0','\0','\0','job:view',NULL,NULL,'2019-03-29 13:51:18','2021-09-26 10:38:20');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (39,1,3,1,'字典管理','Dict','user/dict/index',8,'dictionary','dict','\0','\0','\0','dict:view',NULL,NULL,'2019-04-10 11:49:04','2021-09-26 10:38:18');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (41,6,0,1,'在线用户','OnlineUser','monitor/online/index',10,'Steve-Jobs','online','\0','\0','\0',NULL,NULL,NULL,'2019-10-26 22:08:43',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (44,2,0,2,'用户新增',NULL,'',2,'','','\0','\0','\0','user:add',NULL,NULL,'2019-10-29 10:59:46',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (45,2,0,2,'用户编辑',NULL,'',3,'','','\0','\0','\0','user:edit',NULL,NULL,'2019-10-29 11:00:08',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (46,2,0,2,'用户删除',NULL,'',4,'','','\0','\0','\0','user:del',NULL,NULL,'2019-10-29 11:00:23',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (48,3,0,2,'角色创建',NULL,'',2,'','','\0','\0','\0','role:add',NULL,NULL,'2019-10-29 12:45:34','2021-09-26 10:31:55');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (49,3,0,2,'角色修改',NULL,'',3,'','','\0','\0','\0','role:edit',NULL,NULL,'2019-10-29 12:46:16','2021-09-26 10:31:57');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (50,3,0,2,'角色删除',NULL,'',4,'','','\0','\0','\0','role:del',NULL,NULL,'2019-10-29 12:46:51','2021-09-26 10:31:59');
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (52,5,0,2,'菜单新增',NULL,'',2,'','','\0','\0','\0','menu:add',NULL,NULL,'2019-10-29 12:55:07',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (53,5,0,2,'菜单编辑',NULL,'',3,'','','\0','\0','\0','menu:edit',NULL,NULL,'2019-10-29 12:55:40',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (54,5,0,2,'菜单删除',NULL,'',4,'','','\0','\0','\0','menu:del',NULL,NULL,'2019-10-29 12:56:00',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (56,35,0,2,'部门新增',NULL,'',2,'','','\0','\0','\0','dept:add',NULL,NULL,'2019-10-29 12:57:09',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (57,35,0,2,'部门编辑',NULL,'',3,'','','\0','\0','\0','dept:edit',NULL,NULL,'2019-10-29 12:57:27',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (58,35,0,2,'部门删除',NULL,'',4,'','','\0','\0','\0','dept:del',NULL,NULL,'2019-10-29 12:57:41',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (60,37,0,2,'岗位新增',NULL,'',2,'','','\0','\0','\0','job:add',NULL,NULL,'2019-10-29 12:58:27',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (61,37,0,2,'岗位编辑',NULL,'',3,'','','\0','\0','\0','job:edit',NULL,NULL,'2019-10-29 12:58:45',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (62,37,0,2,'岗位删除',NULL,'',4,'','','\0','\0','\0','job:del',NULL,NULL,'2019-10-29 12:59:04',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (64,39,0,2,'字典新增',NULL,'',2,'','','\0','\0','\0','dict:add',NULL,NULL,'2019-10-29 13:00:17',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (65,39,0,2,'字典编辑',NULL,'',3,'','','\0','\0','\0','dict:edit',NULL,NULL,'2019-10-29 13:00:42',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (66,39,0,2,'字典删除',NULL,'',4,'','','\0','\0','\0','dict:del',NULL,NULL,'2019-10-29 13:00:59',NULL);
insert  into `menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`weight`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (80,6,0,1,'服务监控','ServerMonitor','monitor/server/index',14,'codeConsole','server','\0','\0','\0','monitor:view',NULL,'admin','2019-11-07 13:06:39','2020-05-04 18:20:50');

/*Table structure for table `role` */

create TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `level` int(255) DEFAULT NULL COMMENT '角色级别',
  `data_scope` varchar(255) DEFAULT NULL COMMENT '数据权限',
  `enabled` bit(1) NOT NULL COMMENT '岗位状态',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`),
  KEY `role_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`level`,`data_scope`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'超级管理员',1,'全部','','-',NULL,'admin','2018-11-23 11:04:37','2020-08-06 16:10:24');
insert  into `role`(`id`,`name`,`level`,`data_scope`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'普通用户',2,'本级','','-',NULL,'admin','2018-11-23 13:09:06','2020-09-05 10:45:12');

/*Table structure for table `role_menu_map` */

create TABLE `role_menu_map` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_role_menu` (`menu_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联';

/*Data for the table `role_menu_map` */

insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (131,1,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (78,1,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (130,2,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (79,2,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (127,3,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (129,5,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (128,6,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (80,6,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (134,7,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (81,7,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (136,9,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (82,9,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (135,10,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (83,10,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (132,11,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (84,11,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (133,14,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (85,14,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (140,15,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (86,15,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (141,18,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (137,19,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (87,19,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (139,21,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (88,21,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (138,22,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (89,22,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (146,23,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (90,23,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (145,24,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (91,24,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (143,27,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (92,27,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (142,28,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (144,30,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (93,30,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (152,33,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (94,33,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (151,34,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (95,34,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (148,35,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (147,36,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (96,36,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (150,37,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (149,38,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (156,39,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (157,41,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (153,44,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (155,45,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (154,46,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (161,48,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (163,49,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (162,50,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (158,52,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (160,53,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (159,54,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (167,56,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (169,57,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (168,58,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (164,60,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (166,61,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (165,62,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (170,64,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (172,65,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (171,66,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (177,73,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (176,74,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (173,75,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (175,77,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (174,78,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (103,79,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (102,80,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (97,80,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (104,82,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (98,82,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (101,83,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (99,83,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (108,90,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (105,92,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (107,93,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (106,94,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (111,97,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (110,98,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (109,102,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (117,103,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (116,104,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (119,105,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (118,106,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (113,107,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (112,108,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (115,109,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (114,110,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (124,111,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (123,112,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (126,113,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (125,114,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (120,116,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (100,116,2);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (122,117,1);
insert  into `role_menu_map`(`id`,`menu_id`,`role_id`) values (121,118,1);

/*Table structure for table `sys_log` */

create TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `description` varchar(255) DEFAULT NULL COMMENT '日志描述信息',
  `level` varchar(10) DEFAULT NULL COMMENT '日志级别',
  `exception` text COMMENT '异常信息，只有日志级别为ERROR时才有值',
  `method` varchar(255) DEFAULT NULL COMMENT '被调用方法的名称',
  `params` text COMMENT '被调用方法的参数',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `request_ip` varchar(255) DEFAULT NULL COMMENT 'HTTP请求的IP地址',
  `request_location` varchar(255) DEFAULT NULL COMMENT 'HTTP请求的地理地址',
  `request_browser` varchar(255) DEFAULT NULL COMMENT 'HTTP请求的浏览器',
  `request_time` bigint(20) DEFAULT NULL COMMENT 'HTTP请求的耗时',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志记录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志记录';

/*Data for the table `sys_log` */

insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (1,'更新一条 SysUser 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysUserController.edit()','{\"gender\":\"男\",\"roles\":[{\"id\":2}],\"deptId\":2,\"updateTime\":1599273818000,\"avatar\":\"http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png\",\"dept\":{\"hasChildren\":false,\"weight\":3,\"pid\":7,\"updateTime\":1596350927000,\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476532000,\"name\":\"研发部\",\"id\":2},\"isAdmin\":false,\"enabled\":true,\"jobId\":2,\"password\":\"$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK\",\"createBy\":\"admin\",\"phone\":\"15199999999\",\"updateBy\":\"admin\",\"createTime\":1588648549000,\"name\":\"test\",\"nickname\":\"测试\",\"id\":2,\"job\":{\"weight\":4,\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553842551000,\"name\":\"产品经理\",\"id\":2},\"email\":\"231@qq.com\",\"username\":\"test\"}','admin','127.0.0.1','本机地址','Chrome 91',142,'2021-08-05 17:44:29');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (2,'更新一条 SysDept 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysDeptController.edit()','{\"hasChildren\":false,\"weight\":4,\"pid\":7,\"updateTime\":1589696847000,\"label\":\"运维部\",\"enabled\":false,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476844000,\"name\":\"运维部\",\"id\":5}','admin','127.0.0.1','本机地址','Chrome 91',52,'2021-08-05 17:45:07');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (3,'更新一条 SysDept 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysDeptController.edit()','{\"hasChildren\":false,\"weight\":4,\"pid\":7,\"updateTime\":1589696847000,\"label\":\"运维部\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476844000,\"name\":\"运维部\",\"id\":5}','admin','127.0.0.1','本机地址','Chrome 91',49,'2021-08-05 17:45:10');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (4,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":0,\"path\":\"demo/HelloList\",\"component\":\"demo/HelloList\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',252,'2021-09-17 20:54:28');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (5,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":0,\"path\":\"demo/index\",\"component\":\"demo/HelloList\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',67,'2021-09-17 20:55:11');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (6,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":1,\"path\":\"demo/index\",\"component\":\"demo/index\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',48,'2021-09-17 20:57:51');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (7,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":1,\"path\":\"demo/index\",\"component\":\"demo/index\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',8,'2021-09-17 20:59:44');
insert  into `sys_log`(`id`,`description`,`level`,`exception`,`method`,`params`,`username`,`request_ip`,`request_location`,`request_browser`,`request_time`,`create_time`) values (8,'更新一条 SysMenu 记录','INFO',NULL,'io.github.dunwu.module.system.controller.SysMenuController.edit()','{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":1,\"path\":\"demo/index\",\"component\":\"demo/index\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}','admin','127.0.0.1','本机地址','Chrome 91',22,'2021-09-17 21:09:23');

/*Table structure for table `user` */

create TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `job_id` bigint(20) DEFAULT NULL COMMENT '岗位ID',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `is_admin` bit(1) DEFAULT b'0' COMMENT '是否为admin账号',
  `enabled` bigint(20) DEFAULT NULL COMMENT '状态：1启用、0禁用',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新着',
  `pwd_reset_time` datetime DEFAULT NULL COMMENT '修改密码的时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_email` (`email`) USING BTREE,
  UNIQUE KEY `uk_username` (`username`) USING BTREE,
  KEY `key_dept_id` (`dept_id`) USING BTREE,
  KEY `key_avatar` (`avatar`) USING BTREE,
  KEY `key_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统用户';

/*Data for the table `user` */

insert  into `user`(`id`,`dept_id`,`job_id`,`username`,`nickname`,`gender`,`phone`,`email`,`avatar`,`password`,`is_admin`,`enabled`,`create_by`,`update_by`,`pwd_reset_time`,`create_time`,`update_time`) values (1,2,1,'admin','管理员','男','18888888888','201507802@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','V9/eUKpAu2XMWTu9xm5qgfg2vvOoLRpWjmj4Ih1+JRBShxv9EX7Y2o0DYwbuRrVcTfereRy0g2vOo2d67PYBOfl0HOHidRSiw8qFu4pP/MIq2cFxqtE+y07LgnuqfWlrPDKpHvuE2e6ZNmvZWGoXzMKrMpKJgM+J4WuXzrJHuhc=','',1,NULL,'admin','2020-05-03 16:38:31','2018-08-23 09:11:56','2021-09-26 21:05:01');
insert  into `user`(`id`,`dept_id`,`job_id`,`username`,`nickname`,`gender`,`phone`,`email`,`avatar`,`password`,`is_admin`,`enabled`,`create_by`,`update_by`,`pwd_reset_time`,`create_time`,`update_time`) values (2,2,2,'test','测试','男','15199999999','231@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK','\0',1,'admin','admin',NULL,'2020-05-05 11:15:49','2020-09-05 10:43:38');

/*Table structure for table `user_role_map` */

create TABLE `user_role_map` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联';

/*Data for the table `user_role_map` */

insert  into `user_role_map`(`id`,`user_id`,`role_id`) values (1,1,1);
insert  into `user_role_map`(`id`,`user_id`,`role_id`) values (3,2,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

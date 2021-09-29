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
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dunwu_cas` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dunwu_cas`;

/*Table structure for table `cas_dept` */

DROP TABLE IF EXISTS `cas_dept`;

CREATE TABLE `cas_dept` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid` bigint(20) unsigned DEFAULT '0' COMMENT '上级部门',
    `sub_count` int(5) unsigned DEFAULT '0' COMMENT '子部门数目',
    `name` varchar(255) NOT NULL COMMENT '名称',
    `sequence` int(5) unsigned DEFAULT '999' COMMENT '排序',
    `enabled` bit(1) NOT NULL COMMENT '状态',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_pid` (`pid`),
    KEY `key_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门';

/*Data for the table `cas_dept` */

insert  into `cas_dept`(`id`,`pid`,`sub_count`,`name`,`sequence`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,7,1,'研发部',3,'',NULL,'admin','admin','2019-03-25 09:15:32','2020-08-02 14:48:47');
insert  into `cas_dept`(`id`,`pid`,`sub_count`,`name`,`sequence`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,7,0,'运维部',4,'',NULL,'admin','admin','2019-03-25 09:20:44','2020-05-17 14:27:27');
insert  into `cas_dept`(`id`,`pid`,`sub_count`,`name`,`sequence`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,8,0,'测试部',6,'',NULL,'admin','admin','2019-03-25 09:52:18','2020-06-08 11:59:21');
insert  into `cas_dept`(`id`,`pid`,`sub_count`,`name`,`sequence`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,0,2,'北京研发中心',0,'',NULL,'admin','admin','2019-03-25 11:04:50','2020-06-08 12:08:56');
insert  into `cas_dept`(`id`,`pid`,`sub_count`,`name`,`sequence`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (8,0,2,'南京研发中心',1,'',NULL,'admin','admin','2019-03-25 11:04:53','2020-05-14 12:54:00');
insert  into `cas_dept`(`id`,`pid`,`sub_count`,`name`,`sequence`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (15,8,0,'UI部门',7,'',NULL,'admin','admin','2020-05-13 22:56:53','2020-05-14 12:54:13');
insert  into `cas_dept`(`id`,`pid`,`sub_count`,`name`,`sequence`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (17,2,0,'研发一组',999,'',NULL,'admin','admin','2020-08-02 14:49:07','2020-08-02 14:49:07');

/*Table structure for table `cas_dept_role_map` */

DROP TABLE IF EXISTS `cas_dept_role_map`;

CREATE TABLE `cas_dept_role_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` bigint(20) unsigned NOT NULL,
    `dept_id` bigint(20) unsigned NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu` (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门角色关联';

/*Data for the table `cas_dept_role_map` */

/*Table structure for table `cas_job` */

DROP TABLE IF EXISTS `cas_job`;

CREATE TABLE `cas_job` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) NOT NULL COMMENT '岗位名称',
    `sequence` int(5) unsigned DEFAULT NULL COMMENT '排序',
    `dept_id` bigint(20) unsigned DEFAULT NULL COMMENT '部门ID',
    `enabled` bit(1) NOT NULL COMMENT '岗位状态',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name` (`name`),
    KEY `key_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='岗位';

/*Data for the table `cas_job` */

insert  into `cas_job`(`id`,`name`,`sequence`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'人事专员',3,NULL,'',NULL,'admin','admin','2019-03-29 14:52:28',NULL);
insert  into `cas_job`(`id`,`name`,`sequence`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'产品经理',4,NULL,'',NULL,'admin','admin','2019-03-29 14:55:51',NULL);
insert  into `cas_job`(`id`,`name`,`sequence`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'全栈开发',2,NULL,'',NULL,'admin','admin','2019-03-31 13:39:30','2020-05-05 11:33:43');
insert  into `cas_job`(`id`,`name`,`sequence`,`dept_id`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (4,'软件测试',5,NULL,'',NULL,'admin','admin','2019-03-31 13:39:43','2020-05-10 19:56:26');

/*Table structure for table `cas_job_role_map` */

DROP TABLE IF EXISTS `cas_job_role_map`;

CREATE TABLE `cas_job_role_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_id` bigint(20) unsigned NOT NULL COMMENT '岗位ID',
    `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_job_role` (`job_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='岗位角色关联';

/*Data for the table `cas_job_role_map` */

/*Table structure for table `cas_menu` */

DROP TABLE IF EXISTS `cas_menu`;

CREATE TABLE `cas_menu` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid` bigint(20) unsigned DEFAULT NULL COMMENT '上级菜单ID',
    `sub_count` int(5) unsigned DEFAULT '0' COMMENT '子菜单数目',
    `type` int(5) unsigned DEFAULT NULL COMMENT '菜单类型',
    `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
    `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
    `component` varchar(255) DEFAULT NULL COMMENT '组件',
    `sequence` int(5) unsigned DEFAULT NULL COMMENT '排序',
    `icon` varchar(255) DEFAULT NULL COMMENT '图标',
    `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
    `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
    `cache` bit(1) DEFAULT b'0' COMMENT '缓存',
    `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
    `permission` varchar(255) DEFAULT NULL COMMENT '权限',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_title` (`title`),
    UNIQUE KEY `uk_name` (`name`),
    KEY `key_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='菜单';

/*Data for the table `cas_menu` */

insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,NULL,7,0,'权限管理',NULL,NULL,1,'validCode','cas','\0','\0','\0',NULL,NULL,NULL,'2018-12-18 15:11:29','2021-09-27 20:08:13');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,1,3,1,'用户管理','User','cas/user/index',2,'peoples','user','\0','\0','\0','user:view',NULL,NULL,'2018-12-18 15:14:44','2021-09-27 20:04:29');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,1,3,1,'角色管理','Role','cas/role/index',3,'role','role','\0','\0','\0','roles:view',NULL,NULL,'2018-12-18 15:16:07','2021-09-27 20:04:32');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,1,3,1,'菜单管理','Menu','cas/menu/index',5,'menu','menu','\0','\0','\0','menu:view',NULL,NULL,'2018-12-18 15:17:28','2021-09-27 20:04:34');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,NULL,5,0,'系统监控',NULL,NULL,10,'monitor','monitor','\0','\0','\0',NULL,NULL,NULL,'2018-12-18 15:17:48',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (7,6,0,1,'应用日志','Log','monitor/log/index',11,'log','log','\0','','\0',NULL,NULL,'admin','2018-12-18 15:18:26','2020-06-06 13:11:57');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (9,6,0,1,'SQL监控','Sql','monitor/sql/index',18,'sqlMonitor','druid','\0','\0','\0',NULL,NULL,NULL,'2018-12-18 15:19:34',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (35,1,3,1,'部门管理','Dept','cas/dept/index',6,'dept','dept','\0','\0','\0','dept:view',NULL,NULL,'2019-03-25 09:46:00','2021-09-27 20:04:38');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (37,1,3,1,'岗位管理','Job','cas/job/index',7,'Steve-Jobs','job','\0','\0','\0','job:view',NULL,NULL,'2019-03-29 13:51:18','2021-09-27 20:04:40');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (39,1,3,1,'字典管理','Dict','cas/dict/index',8,'dictionary','dict','\0','\0','\0','dict:view',NULL,NULL,'2019-04-10 11:49:04','2021-09-27 20:04:45');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (41,6,0,1,'在线用户','OnlineUser','monitor/online/index',10,'Steve-Jobs','online','\0','\0','\0',NULL,NULL,NULL,'2019-10-26 22:08:43',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (44,2,0,2,'用户新增',NULL,'',2,'','','\0','\0','\0','user:add',NULL,NULL,'2019-10-29 10:59:46',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (45,2,0,2,'用户编辑',NULL,'',3,'','','\0','\0','\0','user:edit',NULL,NULL,'2019-10-29 11:00:08',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (46,2,0,2,'用户删除',NULL,'',4,'','','\0','\0','\0','user:del',NULL,NULL,'2019-10-29 11:00:23',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (48,3,0,2,'角色创建',NULL,'',2,'','','\0','\0','\0','role:add',NULL,NULL,'2019-10-29 12:45:34','2021-09-26 10:31:55');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (49,3,0,2,'角色修改',NULL,'',3,'','','\0','\0','\0','role:edit',NULL,NULL,'2019-10-29 12:46:16','2021-09-26 10:31:57');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (50,3,0,2,'角色删除',NULL,'',4,'','','\0','\0','\0','role:del',NULL,NULL,'2019-10-29 12:46:51','2021-09-26 10:31:59');
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (52,5,0,2,'菜单新增',NULL,'',2,'','','\0','\0','\0','menu:add',NULL,NULL,'2019-10-29 12:55:07',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (53,5,0,2,'菜单编辑',NULL,'',3,'','','\0','\0','\0','menu:edit',NULL,NULL,'2019-10-29 12:55:40',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (54,5,0,2,'菜单删除',NULL,'',4,'','','\0','\0','\0','menu:del',NULL,NULL,'2019-10-29 12:56:00',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (56,35,0,2,'部门新增',NULL,'',2,'','','\0','\0','\0','dept:add',NULL,NULL,'2019-10-29 12:57:09',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (57,35,0,2,'部门编辑',NULL,'',3,'','','\0','\0','\0','dept:edit',NULL,NULL,'2019-10-29 12:57:27',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (58,35,0,2,'部门删除',NULL,'',4,'','','\0','\0','\0','dept:del',NULL,NULL,'2019-10-29 12:57:41',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (60,37,0,2,'岗位新增',NULL,'',2,'','','\0','\0','\0','job:add',NULL,NULL,'2019-10-29 12:58:27',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (61,37,0,2,'岗位编辑',NULL,'',3,'','','\0','\0','\0','job:edit',NULL,NULL,'2019-10-29 12:58:45',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (62,37,0,2,'岗位删除',NULL,'',4,'','','\0','\0','\0','job:del',NULL,NULL,'2019-10-29 12:59:04',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (64,39,0,2,'字典新增',NULL,'',2,'','','\0','\0','\0','dict:add',NULL,NULL,'2019-10-29 13:00:17',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (65,39,0,2,'字典编辑',NULL,'',3,'','','\0','\0','\0','dict:edit',NULL,NULL,'2019-10-29 13:00:42',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (66,39,0,2,'字典删除',NULL,'',4,'','','\0','\0','\0','dict:del',NULL,NULL,'2019-10-29 13:00:59',NULL);
insert  into `cas_menu`(`id`,`pid`,`sub_count`,`type`,`title`,`name`,`component`,`sequence`,`icon`,`path`,`i_frame`,`cache`,`hidden`,`permission`,`create_by`,`update_by`,`create_time`,`update_time`) values (80,6,0,1,'服务监控','ServerMonitor','monitor/server/index',14,'codeConsole','server','\0','\0','\0','monitor:view',NULL,'admin','2019-11-07 13:06:39','2020-05-04 18:20:50');

/*Table structure for table `cas_role` */

DROP TABLE IF EXISTS `cas_role`;

CREATE TABLE `cas_role` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code` varchar(255) NOT NULL COMMENT '编码',
    `name` varchar(255) NOT NULL COMMENT '名称',
    `level` int(255) DEFAULT NULL COMMENT '角色级别',
    `data_scope` varchar(255) DEFAULT NULL COMMENT '数据权限',
    `enabled` bit(1) NOT NULL COMMENT '岗位状态',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_code` (`code`),
    KEY `idx_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色';

/*Data for the table `cas_role` */

insert  into `cas_role`(`id`,`code`,`name`,`level`,`data_scope`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'admin','超级管理员',1,'全部','','-',NULL,'admin','2018-11-23 11:04:37','2020-08-06 16:10:24');
insert  into `cas_role`(`id`,`code`,`name`,`level`,`data_scope`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'user','普通用户',2,'本级','','-',NULL,'admin','2018-11-23 13:09:06','2020-09-05 10:45:12');

/*Table structure for table `cas_role_menu_map` */

DROP TABLE IF EXISTS `cas_role_menu_map`;

CREATE TABLE `cas_role_menu_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单ID',
    `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu` (`menu_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联';

/*Data for the table `cas_role_menu_map` */

insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (131,1,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (78,1,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (130,2,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (79,2,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (127,3,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (129,5,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (128,6,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (80,6,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (134,7,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (81,7,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (136,9,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (82,9,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (135,10,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (83,10,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (132,11,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (84,11,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (133,14,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (85,14,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (140,15,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (86,15,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (141,18,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (137,19,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (87,19,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (139,21,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (88,21,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (138,22,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (89,22,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (146,23,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (90,23,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (145,24,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (91,24,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (143,27,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (92,27,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (142,28,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (144,30,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (93,30,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (152,33,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (94,33,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (151,34,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (95,34,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (148,35,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (147,36,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (96,36,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (150,37,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (149,38,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (156,39,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (157,41,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (153,44,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (155,45,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (154,46,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (161,48,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (163,49,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (162,50,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (158,52,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (160,53,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (159,54,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (167,56,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (169,57,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (168,58,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (164,60,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (166,61,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (165,62,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (170,64,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (172,65,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (171,66,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (177,73,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (176,74,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (173,75,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (175,77,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (174,78,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (103,79,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (102,80,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (97,80,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (104,82,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (98,82,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (101,83,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (99,83,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (108,90,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (105,92,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (107,93,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (106,94,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (111,97,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (110,98,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (109,102,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (117,103,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (116,104,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (119,105,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (118,106,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (113,107,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (112,108,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (115,109,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (114,110,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (124,111,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (123,112,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (126,113,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (125,114,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (120,116,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (100,116,2);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (122,117,1);
insert  into `cas_role_menu_map`(`id`,`menu_id`,`role_id`) values (121,118,1);

/*Table structure for table `cas_user` */

DROP TABLE IF EXISTS `cas_user`;

CREATE TABLE `cas_user` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dept_id` bigint(20) unsigned DEFAULT NULL COMMENT '部门ID',
    `job_id` bigint(20) unsigned DEFAULT NULL COMMENT '岗位ID',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
    `gender` varchar(2) DEFAULT NULL COMMENT '性别',
    `phone` varchar(255) DEFAULT NULL COMMENT '手机号码',
    `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
    `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
    `password` varchar(255) DEFAULT NULL COMMENT '密码',
    `is_admin` bit(1) DEFAULT b'0' COMMENT '是否为admin账号',
    `enabled` bigint(20) unsigned DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新着',
    `pwd_reset_time` datetime DEFAULT NULL COMMENT '修改密码的时间',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_email` (`email`) USING BTREE,
    UNIQUE KEY `uk_username` (`username`) USING BTREE,
    KEY `key_dept_id` (`dept_id`) USING BTREE,
    KEY `key_avatar` (`avatar`) USING BTREE,
    KEY `key_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户';

/*Data for the table `cas_user` */

insert  into `cas_user`(`id`,`dept_id`,`job_id`,`username`,`nickname`,`gender`,`phone`,`email`,`avatar`,`password`,`is_admin`,`enabled`,`create_by`,`update_by`,`pwd_reset_time`,`create_time`,`update_time`) values (1,2,1,'admin','萧峰','男','18888888888','201507802@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','g7uLhgVcnwZB9DiXPe6kWHDCt5ZwVZyFohe5+M3v8cotQHcMd4cFaKxC83TL+HHsYsyK3HhgwWomr4he9uEEG20ArCwZfjkaBxWbD22uDI8btl21g87Qho+oOSHjPlj1tYJsJbdV3aXv7TbwskTjJa2Q6cB67edi9gEeaiPjhhw=','',1,NULL,'admin','2020-05-03 16:38:31','2018-08-23 09:11:56','2021-09-27 20:19:53');
insert  into `cas_user`(`id`,`dept_id`,`job_id`,`username`,`nickname`,`gender`,`phone`,`email`,`avatar`,`password`,`is_admin`,`enabled`,`create_by`,`update_by`,`pwd_reset_time`,`create_time`,`update_time`) values (2,2,2,'test','段誉','男','15199999999','231@qq.com','http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png','$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK','\0',1,'admin','admin',NULL,'2020-05-05 11:15:49','2021-09-27 19:27:11');

/*Table structure for table `cas_user_role_map` */

DROP TABLE IF EXISTS `cas_user_role_map`;

CREATE TABLE `cas_user_role_map` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_user_role` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色关联';

/*Data for the table `cas_user_role_map` */

insert  into `cas_user_role_map`(`id`,`user_id`,`role_id`) values (1,1,1);
insert  into `cas_user_role_map`(`id`,`user_id`,`role_id`) values (3,2,2);

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code` varchar(255) NOT NULL COMMENT '字典编码',
    `name` varchar(255) NOT NULL COMMENT '字典名称',
    `enabled` bit(1) NOT NULL COMMENT '状态',
    `note` varchar(255) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典';

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`id`,`code`,`name`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,'user_status','用户状态','','用户状态','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `sys_dict`(`id`,`code`,`name`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,'dept_status','部门状态','','部门状态','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `sys_dict`(`id`,`code`,`name`,`enabled`,`note`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,'job_status','岗位状态','','岗位状态','admin',NULL,'2019-10-27 20:31:36',NULL);

/*Table structure for table `sys_dict_option` */

DROP TABLE IF EXISTS `sys_dict_option`;

CREATE TABLE `sys_dict_option` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dict_id` bigint(20) unsigned DEFAULT NULL COMMENT '字典id',
    `code` varchar(255) NOT NULL COMMENT '字典选项编码',
    `name` varchar(255) NOT NULL COMMENT '字典选项名称',
    `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_dict_id` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='数据字典详情';

/*Data for the table `sys_dict_option` */

insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (1,1,'true','激活','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (2,1,'false','禁用','admin',NULL,NULL,NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (3,2,'true','启用','admin',NULL,NULL,NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (4,2,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (5,3,'true','启用','admin',NULL,NULL,NULL);
insert  into `sys_dict_option`(`id`,`dict_id`,`code`,`name`,`create_by`,`update_by`,`create_time`,`update_time`) values (6,3,'false','停用','admin',NULL,'2019-10-27 20:31:36',NULL);

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `level` varchar(10) DEFAULT NULL COMMENT '日志级别',
    `biz_type` varchar(100) DEFAULT NULL COMMENT '业务类型',
    `message` text COMMENT '日志消息',
    `exception_message` text COMMENT '异常信息，只有日志级别为ERROR时才有值',
    `class_name` varchar(255) NOT NULL COMMENT '操作的类名',
    `method_name` varchar(255) NOT NULL COMMENT '操作的方法名',
    `params` text COMMENT '被调用方法的参数',
    `operate_type` varchar(100) DEFAULT NULL COMMENT '操作类型',
    `operator_id` bigint(20) unsigned DEFAULT NULL COMMENT '操作者ID',
    `operator_name` varchar(100) DEFAULT NULL COMMENT '操作者用户名',
    `server_ip` varchar(100) DEFAULT NULL COMMENT '服务端IP地址',
    `client_ip` varchar(100) DEFAULT NULL COMMENT '客户端IP地址',
    `client_location` varchar(100) DEFAULT NULL COMMENT '客户端地理位置',
    `client_device` varchar(100) DEFAULT NULL COMMENT '客户端设备',
    `request_time` bigint(20) unsigned DEFAULT NULL COMMENT 'HTTP请求的耗时',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志记录时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_class_method` (`method_name`,`class_name`),
    KEY `idx_level` (`level`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志记录';

/*Data for the table `sys_log` */

insert  into `sys_log`(`id`,`level`,`biz_type`,`message`,`exception_message`,`class_name`,`method_name`,`params`,`operate_type`,`operator_id`,`operator_name`,`server_ip`,`client_ip`,`client_location`,`client_device`,`request_time`,`create_time`) values (2,'INFO','部门','【部门】更新 cas_dept 表中 id = 7 的记录',NULL,'io.github.dunwu.module.cas.controller.DeptController','edit','{\"pid\":0,\"enabled\":true,\"subCount\":2,\"sequence\":0,\"name\":\"北京研发中心\",\"id\":7}','更新操作',1,'admin','172.22.211.75','127.0.0.1','本机地址','Chrome 91',11,'2021-09-29 20:23:14');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

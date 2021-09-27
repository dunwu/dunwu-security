/*
SQLyog Community v12.09 (64 bit)
MySQL - 5.7.31 : Database - dunwu_cas
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE = ''*/;

/*!40014 SET @`old_unique_checks` = @@`unique_checks`, UNIQUE_CHECKS = 0 */;
/*!40101 SET @`old_sql_mode` = @@`sql_mode`, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @`old_sql_notes` = @@`sql_notes`, SQL_NOTES = 0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS */`dunwu_cas` /*!40100 DEFAULT CHARACTER SET `utf8` */;

USE `dunwu_cas`;

/*Table structure for table `cas_dept` */

DROP TABLE IF EXISTS `cas_dept`;

CREATE TABLE `cas_dept` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid`         BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '上级部门',
    `sub_count`   INT(5) UNSIGNED     DEFAULT '0' COMMENT '子部门数目',
    `name`        VARCHAR(255)        NOT NULL COMMENT '名称',
    `sequence`    INT(5) UNSIGNED     DEFAULT '999' COMMENT '排序',
    `enabled`     BIT(1)              NOT NULL COMMENT '状态',
    `note`        VARCHAR(255)        DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_pid`(`pid`),
    KEY `key_enabled`(`enabled`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 18
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='部门';

/*Data for the table `cas_dept` */

INSERT INTO `cas_dept`(`id`, `pid`, `sub_count`, `name`, `sequence`, `enabled`, `note`, `create_by`, `update_by`,
                       `create_time`, `update_time`)
VALUES (2, 7, 1, '研发部', 3, '', NULL, 'admin', 'admin', '2019-03-25 09:15:32', '2020-08-02 14:48:47');
INSERT INTO `cas_dept`(`id`, `pid`, `sub_count`, `name`, `sequence`, `enabled`, `note`, `create_by`, `update_by`,
                       `create_time`, `update_time`)
VALUES (5, 7, 0, '运维部', 4, '', NULL, 'admin', 'admin', '2019-03-25 09:20:44', '2020-05-17 14:27:27');
INSERT INTO `cas_dept`(`id`, `pid`, `sub_count`, `name`, `sequence`, `enabled`, `note`, `create_by`, `update_by`,
                       `create_time`, `update_time`)
VALUES (6, 8, 0, '测试部', 6, '', NULL, 'admin', 'admin', '2019-03-25 09:52:18', '2020-06-08 11:59:21');
INSERT INTO `cas_dept`(`id`, `pid`, `sub_count`, `name`, `sequence`, `enabled`, `note`, `create_by`, `update_by`,
                       `create_time`, `update_time`)
VALUES (7, 0, 2, '华南分部', 0, '', NULL, 'admin', 'admin', '2019-03-25 11:04:50', '2020-06-08 12:08:56');
INSERT INTO `cas_dept`(`id`, `pid`, `sub_count`, `name`, `sequence`, `enabled`, `note`, `create_by`, `update_by`,
                       `create_time`, `update_time`)
VALUES (8, 0, 2, '华北分部', 1, '', NULL, 'admin', 'admin', '2019-03-25 11:04:53', '2020-05-14 12:54:00');
INSERT INTO `cas_dept`(`id`, `pid`, `sub_count`, `name`, `sequence`, `enabled`, `note`, `create_by`, `update_by`,
                       `create_time`, `update_time`)
VALUES (15, 8, 0, 'UI部门', 7, '', NULL, 'admin', 'admin', '2020-05-13 22:56:53', '2020-05-14 12:54:13');
INSERT INTO `cas_dept`(`id`, `pid`, `sub_count`, `name`, `sequence`, `enabled`, `note`, `create_by`, `update_by`,
                       `create_time`, `update_time`)
VALUES (17, 2, 0, '研发一组', 999, '', NULL, 'admin', 'admin', '2020-08-02 14:49:07', '2020-08-02 14:49:07');

/*Table structure for table `cas_dept_role_map` */

DROP TABLE IF EXISTS `cas_dept_role_map`;

CREATE TABLE `cas_dept_role_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` BIGINT(20) UNSIGNED NOT NULL,
    `dept_id` BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu`(`role_id`, `dept_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='部门角色关联';

/*Data for the table `cas_dept_role_map` */

/*Table structure for table `cas_job` */

DROP TABLE IF EXISTS `cas_job`;

CREATE TABLE `cas_job` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(255)        NOT NULL COMMENT '岗位名称',
    `sequence`    INT(5) UNSIGNED     DEFAULT NULL COMMENT '排序',
    `dept_id`     BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '部门ID',
    `enabled`     BIT(1)              NOT NULL COMMENT '岗位状态',
    `note`        VARCHAR(255)        DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name`(`name`),
    KEY `key_enabled`(`enabled`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='岗位';

/*Data for the table `cas_job` */

INSERT INTO `cas_job`(`id`, `name`, `sequence`, `dept_id`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                      `update_time`)
VALUES (1, '人事专员', 3, NULL, '', NULL, 'admin', 'admin', '2019-03-29 14:52:28', NULL);
INSERT INTO `cas_job`(`id`, `name`, `sequence`, `dept_id`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                      `update_time`)
VALUES (2, '产品经理', 4, NULL, '', NULL, 'admin', 'admin', '2019-03-29 14:55:51', NULL);
INSERT INTO `cas_job`(`id`, `name`, `sequence`, `dept_id`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                      `update_time`)
VALUES (3, '全栈开发', 2, NULL, '', NULL, 'admin', 'admin', '2019-03-31 13:39:30', '2020-05-05 11:33:43');
INSERT INTO `cas_job`(`id`, `name`, `sequence`, `dept_id`, `enabled`, `note`, `create_by`, `update_by`, `create_time`,
                      `update_time`)
VALUES (4, '软件测试', 5, NULL, '', NULL, 'admin', 'admin', '2019-03-31 13:39:43', '2020-05-10 19:56:26');

/*Table structure for table `cas_job_role_map` */

DROP TABLE IF EXISTS `cas_job_role_map`;

CREATE TABLE `cas_job_role_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_id`  BIGINT(20) UNSIGNED NOT NULL COMMENT '岗位ID',
    `role_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_job_role`(`job_id`, `role_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='岗位角色关联';

/*Data for the table `cas_job_role_map` */

/*Table structure for table `cas_menu` */

DROP TABLE IF EXISTS `cas_menu`;

CREATE TABLE `cas_menu` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid`         BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '上级菜单ID',
    `sub_count`   INT(5) UNSIGNED     DEFAULT '0' COMMENT '子菜单数目',
    `type`        INT(5) UNSIGNED     DEFAULT NULL COMMENT '菜单类型',
    `title`       VARCHAR(255)        DEFAULT NULL COMMENT '菜单标题',
    `name`        VARCHAR(255)        DEFAULT NULL COMMENT '组件名称',
    `component`   VARCHAR(255)        DEFAULT NULL COMMENT '组件',
    `sequence`    INT(5) UNSIGNED     DEFAULT NULL COMMENT '排序',
    `icon`        VARCHAR(255)        DEFAULT NULL COMMENT '图标',
    `path`        VARCHAR(255)        DEFAULT NULL COMMENT '链接地址',
    `i_frame`     BIT(1)              DEFAULT NULL COMMENT '是否外链',
    `cache`       BIT(1)              DEFAULT b'0' COMMENT '缓存',
    `hidden`      BIT(1)              DEFAULT b'0' COMMENT '隐藏',
    `permission`  VARCHAR(255)        DEFAULT NULL COMMENT '权限',
    `create_by`   VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_title`(`title`),
    UNIQUE KEY `uk_name`(`name`),
    KEY `key_pid`(`pid`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 81
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='菜单';

/*Data for the table `cas_menu` */

INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (1, NULL, 7, 0, '权限管理', NULL, NULL, 1, 'validCode', 'auth', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-18 15:11:29', '2021-09-26 10:47:01');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (2, 1, 3, 1, '用户管理', 'User', 'user/user/index', 2, 'peoples', 'user', '\0', '\0', '\0', 'user:view', NULL, NULL,
        '2018-12-18 15:14:44', '2021-09-26 10:38:09');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (3, 1, 3, 1, '角色管理', 'Role', 'user/role/index', 3, 'role', 'role', '\0', '\0', '\0', 'roles:view', NULL, NULL,
        '2018-12-18 15:16:07', '2021-09-26 10:38:11');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (5, 1, 3, 1, '菜单管理', 'Menu', 'user/menu/index', 5, 'menu', 'menu', '\0', '\0', '\0', 'menu:view', NULL, NULL,
        '2018-12-18 15:17:28', '2021-09-26 10:38:13');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (6, NULL, 5, 0, '系统监控', NULL, NULL, 10, 'monitor', 'monitor', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-18 15:17:48', NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (7, 6, 0, 1, '应用日志', 'Log', 'monitor/log/index', 11, 'log', 'log', '\0', '', '\0', NULL, NULL, 'admin',
        '2018-12-18 15:18:26', '2020-06-06 13:11:57');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (9, 6, 0, 1, 'SQL监控', 'Sql', 'monitor/sql/index', 18, 'sqlMonitor', 'druid', '\0', '\0', '\0', NULL, NULL, NULL,
        '2018-12-18 15:19:34', NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (35, 1, 3, 1, '部门管理', 'Dept', 'user/dept/index', 6, 'dept', 'dept', '\0', '\0', '\0', 'dept:view', NULL, NULL,
        '2019-03-25 09:46:00', '2021-09-26 10:38:16');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (37, 1, 3, 1, '岗位管理', 'Job', 'user/job/index', 7, 'Steve-Jobs', 'job', '\0', '\0', '\0', 'job:view', NULL, NULL,
        '2019-03-29 13:51:18', '2021-09-26 10:38:20');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (39, 1, 3, 1, '字典管理', 'Dict', 'user/dict/index', 8, 'dictionary', 'dict', '\0', '\0', '\0', 'dict:view', NULL,
        NULL, '2019-04-10 11:49:04', '2021-09-26 10:38:18');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (41, 6, 0, 1, '在线用户', 'OnlineUser', 'monitor/online/index', 10, 'Steve-Jobs', 'online', '\0', '\0', '\0', NULL,
        NULL, NULL, '2019-10-26 22:08:43', NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (44, 2, 0, 2, '用户新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'user:add', NULL, NULL, '2019-10-29 10:59:46',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (45, 2, 0, 2, '用户编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'user:edit', NULL, NULL, '2019-10-29 11:00:08',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (46, 2, 0, 2, '用户删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'user:del', NULL, NULL, '2019-10-29 11:00:23',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (48, 3, 0, 2, '角色创建', NULL, '', 2, '', '', '\0', '\0', '\0', 'role:add', NULL, NULL, '2019-10-29 12:45:34',
        '2021-09-26 10:31:55');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (49, 3, 0, 2, '角色修改', NULL, '', 3, '', '', '\0', '\0', '\0', 'role:edit', NULL, NULL, '2019-10-29 12:46:16',
        '2021-09-26 10:31:57');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (50, 3, 0, 2, '角色删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'role:del', NULL, NULL, '2019-10-29 12:46:51',
        '2021-09-26 10:31:59');
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (52, 5, 0, 2, '菜单新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'menu:add', NULL, NULL, '2019-10-29 12:55:07',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (53, 5, 0, 2, '菜单编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'menu:edit', NULL, NULL, '2019-10-29 12:55:40',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (54, 5, 0, 2, '菜单删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'menu:del', NULL, NULL, '2019-10-29 12:56:00',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (56, 35, 0, 2, '部门新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'dept:add', NULL, NULL, '2019-10-29 12:57:09',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (57, 35, 0, 2, '部门编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'dept:edit', NULL, NULL, '2019-10-29 12:57:27',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (58, 35, 0, 2, '部门删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'dept:del', NULL, NULL, '2019-10-29 12:57:41',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (60, 37, 0, 2, '岗位新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'job:add', NULL, NULL, '2019-10-29 12:58:27',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (61, 37, 0, 2, '岗位编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'job:edit', NULL, NULL, '2019-10-29 12:58:45',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (62, 37, 0, 2, '岗位删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'job:del', NULL, NULL, '2019-10-29 12:59:04',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (64, 39, 0, 2, '字典新增', NULL, '', 2, '', '', '\0', '\0', '\0', 'dict:add', NULL, NULL, '2019-10-29 13:00:17',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (65, 39, 0, 2, '字典编辑', NULL, '', 3, '', '', '\0', '\0', '\0', 'dict:edit', NULL, NULL, '2019-10-29 13:00:42',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (66, 39, 0, 2, '字典删除', NULL, '', 4, '', '', '\0', '\0', '\0', 'dict:del', NULL, NULL, '2019-10-29 13:00:59',
        NULL);
INSERT INTO `cas_menu`(`id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `sequence`, `icon`, `path`,
                       `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`,
                       `update_time`)
VALUES (80, 6, 0, 1, '服务监控', 'ServerMonitor', 'monitor/server/index', 14, 'codeConsole', 'server', '\0', '\0', '\0',
        'monitor:view', NULL, 'admin', '2019-11-07 13:06:39', '2020-05-04 18:20:50');

/*Table structure for table `cas_role` */

DROP TABLE IF EXISTS `cas_role`;

CREATE TABLE `cas_role` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code`        VARCHAR(255)        NOT NULL COMMENT '编码',
    `name`        VARCHAR(255)        NOT NULL COMMENT '名称',
    `level`       INT(255)     DEFAULT NULL COMMENT '角色级别',
    `data_scope`  VARCHAR(255) DEFAULT NULL COMMENT '数据权限',
    `enabled`     BIT(1)              NOT NULL COMMENT '岗位状态',
    `note`        VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_code`(`code`),
    KEY `idx_name`(`name`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色';

/*Data for the table `cas_role` */

INSERT INTO `cas_role`(`id`, `code`, `name`, `level`, `data_scope`, `enabled`, `note`, `create_by`, `update_by`,
                       `create_time`,
                       `update_time`)
VALUES (1, 'admin', '超级管理员', 1, '全部', '', '-', NULL, 'admin', '2018-11-23 11:04:37',
        '2020-08-06 16:10:24');
INSERT INTO `cas_role`(`id`, `code`, `name`, `level`, `data_scope`, `enabled`, `note`, `create_by`, `update_by`,
                       `create_time`, `update_time`)
VALUES (2, 'user', '普通用户', 2, '本级', '', '-', NULL, 'admin', '2018-11-23
13:09:06', '2020-09-05 10:45:12');

/*Table structure for table `cas_role_menu_map` */

DROP TABLE IF EXISTS `cas_role_menu_map`;

CREATE TABLE `cas_role_menu_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `menu_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '菜单ID',
    `role_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu`(`menu_id`, `role_id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 178
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色菜单关联';

/*Data for the table `cas_role_menu_map` */

INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (131, 1, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (78, 1, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (130, 2, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (79, 2, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (127, 3, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (129, 5, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (128, 6, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (80, 6, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (134, 7, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (81, 7, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (136, 9, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (82, 9, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (135, 10, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (83, 10, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (132, 11, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (84, 11, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (133, 14, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (85, 14, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (140, 15, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (86, 15, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (141, 18, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (137, 19, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (87, 19, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (139, 21, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (88, 21, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (138, 22, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (89, 22, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (146, 23, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (90, 23, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (145, 24, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (91, 24, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (143, 27, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (92, 27, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (142, 28, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (144, 30, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (93, 30, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (152, 33, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (94, 33, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (151, 34, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (95, 34, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (148, 35, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (147, 36, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (96, 36, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (150, 37, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (149, 38, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (156, 39, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (157, 41, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (153, 44, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (155, 45, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (154, 46, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (161, 48, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (163, 49, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (162, 50, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (158, 52, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (160, 53, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (159, 54, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (167, 56, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (169, 57, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (168, 58, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (164, 60, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (166, 61, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (165, 62, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (170, 64, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (172, 65, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (171, 66, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (177, 73, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (176, 74, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (173, 75, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (175, 77, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (174, 78, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (103, 79, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (102, 80, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (97, 80, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (104, 82, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (98, 82, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (101, 83, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (99, 83, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (108, 90, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (105, 92, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (107, 93, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (106, 94, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (111, 97, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (110, 98, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (109, 102, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (117, 103, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (116, 104, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (119, 105, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (118, 106, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (113, 107, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (112, 108, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (115, 109, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (114, 110, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (124, 111, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (123, 112, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (126, 113, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (125, 114, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (120, 116, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (100, 116, 2);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (122, 117, 1);
INSERT INTO `cas_role_menu_map`(`id`, `menu_id`, `role_id`)
VALUES (121, 118, 1);

/*Table structure for table `cas_user` */

DROP TABLE IF EXISTS `cas_user`;

CREATE TABLE `cas_user` (
    `id`             BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dept_id`        BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '部门ID',
    `job_id`         BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '岗位ID',
    `username`       VARCHAR(255)        DEFAULT NULL COMMENT '用户名',
    `nickname`       VARCHAR(255)        DEFAULT NULL COMMENT '昵称',
    `gender`         VARCHAR(2)          DEFAULT NULL COMMENT '性别',
    `phone`          VARCHAR(255)        DEFAULT NULL COMMENT '手机号码',
    `email`          VARCHAR(255)        DEFAULT NULL COMMENT '邮箱',
    `avatar`         VARCHAR(255)        DEFAULT NULL COMMENT '头像地址',
    `password`       VARCHAR(255)        DEFAULT NULL COMMENT '密码',
    `is_admin`       BIT(1)              DEFAULT b'0' COMMENT '是否为admin账号',
    `enabled`        BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `create_by`      VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`      VARCHAR(255)        DEFAULT NULL COMMENT '更新着',
    `pwd_reset_time` DATETIME            DEFAULT NULL COMMENT '修改密码的时间',
    `create_time`    DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_email`(`email`) USING BTREE,
    UNIQUE KEY `uk_username`(`username`) USING BTREE,
    KEY `key_dept_id`(`dept_id`) USING BTREE,
    KEY `key_avatar`(`avatar`) USING BTREE,
    KEY `key_enabled`(`enabled`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='用户';

/*Data for the table `cas_user` */

INSERT INTO `cas_user`(`id`, `dept_id`, `job_id`, `username`, `nickname`, `gender`, `phone`, `email`, `avatar`,
                       `password`, `is_admin`, `enabled`, `create_by`, `update_by`, `pwd_reset_time`, `create_time`,
                       `update_time`)
VALUES (1, 2, 1, 'admin', '管理员', '男', '18888888888', '201507802@qq.com',
        'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png',
        'aFbik3+CmuiJil6dIAf5W9fgP/3o9AESgNq58alRxH02h+WMktz543lJEGEP1rmdEG6AgaNyeEE0NmKWXNjS/7MJ6B+mAqeqJYlH9QrtDYGgdqEae4tkPXv9j7fv6c9vQKqAJuMRJCr7xHw96zj35pNSLyPe/NmCLwW/JoEb1Ic=',
        '', 1, NULL, 'admin', '2020-05-03 16:38:31', '2018-08-23 09:11:56', '2021-09-27 11:06:58');
INSERT INTO `cas_user`(`id`, `dept_id`, `job_id`, `username`, `nickname`, `gender`, `phone`, `email`, `avatar`,
                       `password`, `is_admin`, `enabled`, `create_by`, `update_by`, `pwd_reset_time`, `create_time`,
                       `update_time`)
VALUES (2, 2, 2, 'test', '测试', '男', '15199999999', '231@qq.com',
        'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png',
        '$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK', '\0', 1, 'admin', 'admin', NULL,
        '2020-05-05 11:15:49', '2020-09-05 10:43:38');

/*Table structure for table `cas_user_role_map` */

DROP TABLE IF EXISTS `cas_user_role_map`;

CREATE TABLE `cas_user_role_map` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_user_role`(`user_id`, `role_id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='用户角色关联';

/*Data for the table `cas_user_role_map` */

INSERT INTO `cas_user_role_map`(`id`, `user_id`, `role_id`)
VALUES (1, 1, 1);
INSERT INTO `cas_user_role_map`(`id`, `user_id`, `role_id`)
VALUES (3, 2, 2);

/*Table structure for table `dict` */

DROP TABLE IF EXISTS `dict`;

CREATE TABLE `dict` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code`        VARCHAR(255)        NOT NULL COMMENT '字典编码',
    `name`        VARCHAR(255)        NOT NULL COMMENT '字典名称',
    `enabled`     BIT(1)              NOT NULL COMMENT '状态',
    `note`        VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='数据字典';

/*Data for the table `dict` */

INSERT INTO `dict`(`id`, `code`, `name`, `enabled`, `note`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES (1, 'user_status', '用户状态', '', '用户状态', 'admin', NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `dict`(`id`, `code`, `name`, `enabled`, `note`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES (2, 'dept_status', '部门状态', '', '部门状态', 'admin', NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `dict`(`id`, `code`, `name`, `enabled`, `note`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES (3, 'job_status', '岗位状态', '', '岗位状态', 'admin', NULL, '2019-10-27 20:31:36', NULL);

/*Table structure for table `dict_option` */

DROP TABLE IF EXISTS `dict_option`;

CREATE TABLE `dict_option` (
    `id`          BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dict_id`     BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '字典id',
    `code`        VARCHAR(255)        NOT NULL COMMENT '字典选项编码',
    `name`        VARCHAR(255)        NOT NULL COMMENT '字典选项名称',
    `create_by`   VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_dict_id`(`dict_id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='数据字典详情';

/*Data for the table `dict_option` */

INSERT INTO `dict_option`(`id`, `dict_id`, `code`, `name`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES (1, 1, 'true', '激活', 'admin', NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `dict_option`(`id`, `dict_id`, `code`, `name`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES (2, 1, 'false', '禁用', 'admin', NULL, NULL, NULL);
INSERT INTO `dict_option`(`id`, `dict_id`, `code`, `name`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES (3, 2, 'true', '启用', 'admin', NULL, NULL, NULL);
INSERT INTO `dict_option`(`id`, `dict_id`, `code`, `name`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES (4, 2, 'false', '停用', 'admin', NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `dict_option`(`id`, `dict_id`, `code`, `name`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES (5, 3, 'true', '启用', 'admin', NULL, NULL, NULL);
INSERT INTO `dict_option`(`id`, `dict_id`, `code`, `name`, `create_by`, `update_by`, `create_time`, `update_time`)
VALUES (6, 3, 'false', '停用', 'admin', NULL, '2019-10-27 20:31:36', NULL);

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
    `id`               BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `description`      VARCHAR(255)                 DEFAULT NULL COMMENT '日志描述信息',
    `level`            VARCHAR(10)                  DEFAULT NULL COMMENT '日志级别',
    `exception`        TEXT COMMENT '异常信息，只有日志级别为ERROR时才有值',
    `method`           VARCHAR(255)                 DEFAULT NULL COMMENT '被调用方法的名称',
    `params`           TEXT COMMENT '被调用方法的参数',
    `username`         VARCHAR(255)                 DEFAULT NULL COMMENT '用户名',
    `request_ip`       VARCHAR(255)                 DEFAULT NULL COMMENT 'HTTP请求的IP地址',
    `request_location` VARCHAR(255)                 DEFAULT NULL COMMENT 'HTTP请求的地理地址',
    `request_browser`  VARCHAR(255)                 DEFAULT NULL COMMENT 'HTTP请求的浏览器',
    `request_time`     BIGINT(20) UNSIGNED          DEFAULT NULL COMMENT 'HTTP请求的耗时',
    `create_time`      DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志记录时间',
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 9
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='系统日志记录';

/*Data for the table `sys_log` */

INSERT INTO `sys_log`(`id`, `description`, `level`, `exception`, `method`, `params`, `username`, `request_ip`,
                      `request_location`, `request_browser`, `request_time`, `create_time`)
VALUES (1, '更新一条 SysUser 记录', 'INFO', NULL, 'io.github.dunwu.module.system.controller.SysUserController.edit()',
        '{\"gender\":\"男\",\"roles\":[{\"id\":2}],\"deptId\":2,\"updateTime\":1599273818000,\"avatar\":\"http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png\",\"dept\":{\"hasChildren\":false,\"weight\":3,\"pid\":7,\"updateTime\":1596350927000,\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476532000,\"name\":\"研发部\",\"id\":2},\"isAdmin\":false,\"enabled\":true,\"jobId\":2,\"password\":\"$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK\",\"createBy\":\"admin\",\"phone\":\"15199999999\",\"updateBy\":\"admin\",\"createTime\":1588648549000,\"name\":\"test\",\"nickname\":\"测试\",\"id\":2,\"job\":{\"weight\":4,\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553842551000,\"name\":\"产品经理\",\"id\":2},\"email\":\"231@qq.com\",\"username\":\"test\"}',
        'admin', '127.0.0.1', '本机地址', 'Chrome 91', 142, '2021-08-05 17:44:29');
INSERT INTO `sys_log`(`id`, `description`, `level`, `exception`, `method`, `params`, `username`, `request_ip`,
                      `request_location`, `request_browser`, `request_time`, `create_time`)
VALUES (2, '更新一条 SysDept 记录', 'INFO', NULL, 'io.github.dunwu.module.system.controller.SysDeptController.edit()',
        '{\"hasChildren\":false,\"weight\":4,\"pid\":7,\"updateTime\":1589696847000,\"label\":\"运维部\",\"enabled\":false,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476844000,\"name\":\"运维部\",\"id\":5}',
        'admin', '127.0.0.1', '本机地址', 'Chrome 91', 52, '2021-08-05 17:45:07');
INSERT INTO `sys_log`(`id`, `description`, `level`, `exception`, `method`, `params`, `username`, `request_ip`,
                      `request_location`, `request_browser`, `request_time`, `create_time`)
VALUES (3, '更新一条 SysDept 记录', 'INFO', NULL, 'io.github.dunwu.module.system.controller.SysDeptController.edit()',
        '{\"hasChildren\":false,\"weight\":4,\"pid\":7,\"updateTime\":1589696847000,\"label\":\"运维部\",\"enabled\":true,\"createBy\":\"admin\",\"updateBy\":\"admin\",\"createTime\":1553476844000,\"name\":\"运维部\",\"id\":5}',
        'admin', '127.0.0.1', '本机地址', 'Chrome 91', 49, '2021-08-05 17:45:10');
INSERT INTO `sys_log`(`id`, `description`, `level`, `exception`, `method`, `params`, `username`, `request_ip`,
                      `request_location`, `request_browser`, `request_time`, `create_time`)
VALUES (4, '更新一条 SysMenu 记录', 'INFO', NULL, 'io.github.dunwu.module.system.controller.SysMenuController.edit()',
        '{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":0,\"path\":\"demo/HelloList\",\"component\":\"demo/HelloList\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}',
        'admin', '127.0.0.1', '本机地址', 'Chrome 91', 252, '2021-09-17 20:54:28');
INSERT INTO `sys_log`(`id`, `description`, `level`, `exception`, `method`, `params`, `username`, `request_ip`,
                      `request_location`, `request_browser`, `request_time`, `create_time`)
VALUES (5, '更新一条 SysMenu 记录', 'INFO', NULL, 'io.github.dunwu.module.system.controller.SysMenuController.edit()',
        '{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":0,\"path\":\"demo/index\",\"component\":\"demo/HelloList\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}',
        'admin', '127.0.0.1', '本机地址', 'Chrome 91', 67, '2021-09-17 20:55:11');
INSERT INTO `sys_log`(`id`, `description`, `level`, `exception`, `method`, `params`, `username`, `request_ip`,
                      `request_location`, `request_browser`, `request_time`, `create_time`)
VALUES (6, '更新一条 SysMenu 记录', 'INFO', NULL, 'io.github.dunwu.module.system.controller.SysMenuController.edit()',
        '{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":1,\"path\":\"demo/index\",\"component\":\"demo/index\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}',
        'admin', '127.0.0.1', '本机地址', 'Chrome 91', 48, '2021-09-17 20:57:51');
INSERT INTO `sys_log`(`id`, `description`, `level`, `exception`, `method`, `params`, `username`, `request_ip`,
                      `request_location`, `request_browser`, `request_time`, `create_time`)
VALUES (7, '更新一条 SysMenu 记录', 'INFO', NULL, 'io.github.dunwu.module.system.controller.SysMenuController.edit()',
        '{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":1,\"path\":\"demo/index\",\"component\":\"demo/index\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}',
        'admin', '127.0.0.1', '本机地址', 'Chrome 91', 8, '2021-09-17 20:59:44');
INSERT INTO `sys_log`(`id`, `description`, `level`, `exception`, `method`, `params`, `username`, `request_ip`,
                      `request_location`, `request_browser`, `request_time`, `create_time`)
VALUES (8, '更新一条 SysMenu 记录', 'INFO', NULL, 'io.github.dunwu.module.system.controller.SysMenuController.edit()',
        '{\"cache\":true,\"hidden\":false,\"icon\":\"demo\",\"weight\":50,\"pid\":117,\"title\":\"Hello\",\"type\":1,\"path\":\"demo/index\",\"component\":\"demo/index\",\"updateBy\":\"admin\",\"name\":\"Hello\",\"iFrame\":false,\"id\":118}',
        'admin', '127.0.0.1', '本机地址', 'Chrome 91', 22, '2021-09-17 21:09:23');

/*!40101 SET SQL_MODE = @`old_sql_mode` */;
/*!40014 SET UNIQUE_CHECKS = @`old_unique_checks` */;
/*!40111 SET SQL_NOTES = @`old_sql_notes` */;

/*
Navicat MySQL Data Transfer

Source Server         : 开发
Source Server Version : 50712
Source Host           : 192.168.10.117:3306
Source Database       : wofinder

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2017-10-18 17:55:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('webside_quartzScheduler', '每6小时重置热门视频列表', 'DEFAULT', '0 0 0/6 * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE,
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('webside_quartzScheduler', '重置热门视频列表', '重置热门视频列表', '每6小时重置热门视频列表', 'com.webside.quartz.job.HotVideoJob', '1', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000B7363686564756C654A6F627372002A636F6D2E776562736964652E71756172747A2E6D6F64656C2E5363686564756C654A6F62456E74697479000000000000000102000E4C0007626173655572697400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C0007656E644461746571007E000A4C000C6A6F62436C6173734E616D6571007E00094C00076A6F624465736371007E00094C00086A6F6247726F757071007E00094C00076A6F624E616D6571007E00094C000C6E6578744669726554696D6571007E000A4C000973746172744461746571007E000A4C000C7472696767657247726F757071007E00094C000B747269676765724E616D6571007E00094C000D7472696767657253746174757371007E00094C000A75706461746554696D6571007E000A78720025636F6D2E776562736964652E626173652E626173656D6F64656C2E42617365456E7469747900000000000000010200014C000269647400104C6A6176612F6C616E672F4C6F6E673B7870707400062F61646D696E7074000D30203020302F36202A202A203F7372000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001655D36D80078740022636F6D2E776562736964652E71756172747A2E6A6F622E486F74566964656F4A6F62740022E6AF8F36E5B08FE697B6E9878DE7BDAEE783ADE997A8E8A786E9A291E58897E8A1A8740018E9878DE7BDAEE783ADE997A8E8A786E9A291E58897E8A1A8740018E9878DE7BDAEE783ADE997A8E8A786E9A291E58897E8A1A8707371007E001077080000015E0585AC007874000744454641554C54740022E6AF8F36E5B08FE697B6E9878DE7BDAEE783ADE997A8E8A786E9A291E58897E8A1A870707800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('webside_quartzScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('webside_quartzScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('webside_quartzScheduler', 'Woda-PC1507706716524', '1508320651926', '20000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE,
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('webside_quartzScheduler', '每6小时重置热门视频列表', 'DEFAULT', '重置热门视频列表', '重置热门视频列表', null, '1503482400000', '1503460800000', '5', 'ERROR', 'CRON', '1503331200000', '1534867200000', null, '0', '');

-- ----------------------------
-- Table structure for tb_dict
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict`;
CREATE TABLE `tb_dict` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `label` varchar(100) NOT NULL COMMENT '名称',
  `value` varchar(100) NOT NULL COMMENT '值',
  `sort` int(2) DEFAULT NULL COMMENT '排序',
  `label_class` varchar(100) DEFAULT NULL COMMENT '文字列表样式',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) NOT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of tb_dict
-- ----------------------------
INSERT INTO `tb_dict` VALUES ('0', 'station_type', '本网站', '0', '0', 'label label-sm label-success arrowed arrowed-righ', '本网站', '1', '1', '1493372270211', '', null);
INSERT INTO `tb_dict` VALUES ('070b9d6e6fa64ddc82e9d2fbd718179b', 'seo_config_type', '通用', '1', '1', 'label label-sm label-success arrowed arrowed-righ', 'SEO类型', '1', '1498545044690', '1', '1', '1498545083361');
INSERT INTO `tb_dict` VALUES ('081d6e33f2394f5ab8f4dc023f1b155e', 'third_party_type', '斗鱼TV', '2', '2', 'label label-sm label-success arrowed arrowed-righ', '所属第三方', '1', '1', '1493362891670', null, null);
INSERT INTO `tb_dict` VALUES ('09c45ba01c8f4098b57803a6dc4d5197', 'Q_now_offset', '网络游戏', '981', null, null, '0', '1', '1', '1501754791493', null, '1502692988546');
INSERT INTO `tb_dict` VALUES ('0cf6214e29ef4adfb74b45a76a652005', 'user_security_question', '您父亲的姓名是？', '6', '6', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071070460', '1', null, null);
INSERT INTO `tb_dict` VALUES ('0ef9a6ff967144f4897f28734bbf9a6e', 'third_party_type', '龙珠TV', '4', '4', 'label label-sm label-success arrowed arrowed-righ', '所属第三方', '1', '1', '1493372246151', null, null);
INSERT INTO `tb_dict` VALUES ('1069341b46fa44b9ace01e5c0b66a3bd', 'third_party_type', '熊猫TV', '3', '3', 'label label-sm label-success arrowed arrowed-righ', '所属第三方', '1', '1', '1493372234314', null, null);
INSERT INTO `tb_dict` VALUES ('17517dd8b97840a4a6fbc03f024eb41c', 'A_default_values', 'Mugen', '72', '7', 'label label-sm label-success arrowed arrowed-righ', '-20', '1', '1498720660400', '1', null, '1502692958658');
INSERT INTO `tb_dict` VALUES ('1d3b7d51331246c189374a551e9e0f6e', 'user_security_question', '您初中班主任的名字是？', '10', '10', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071113093', '1', null, null);
INSERT INTO `tb_dict` VALUES ('1ffd3723bcf64df49e822cee83aac8a1', 'effective_status', '无效', '0', '2', 'label label-sm label-warning arrowed arrowed-righ', '有效状态', '1', '1', '1492410841621', '1', '1492418723845');
INSERT INTO `tb_dict` VALUES ('27d360cece7c40b4a1cea202b89ff474', 'Q_now_offset', '达人解说', '977', null, null, '0', '1', '1', '1501754791384', null, '1502692988553');
INSERT INTO `tb_dict` VALUES ('28a542fd91744fe182e792837ab4eabb', 'up_second_title_type', '个人荣誉', '3', '3', 'label label-sm label-success arrowed arrowed-righ', '人物二级信息的标题', '1', '1496804762459', '1', null, null);
INSERT INTO `tb_dict` VALUES ('2b091f26829d43e588cf1681b757a65a', 'user_security_question', '您配偶的生日是？', '2', '2', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071012415', '1', null, null);
INSERT INTO `tb_dict` VALUES ('2f5d3edf7e8e44d6ba9bcbfa0775aa49', 'Q_now_offset', '游戏周边', '980', null, null, '0', '1', '1', '1501754791476', null, '1502692984243');
INSERT INTO `tb_dict` VALUES ('2fbdafa32ed349339562371163e4f8e2', 'user_security_question', '您最熟悉的童年好友名字是？', '11', '11', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071122920', '1', null, null);
INSERT INTO `tb_dict` VALUES ('302af64f3450464b981fdc6903c2867f', 'Q_now_offset', '主机单机', '974', null, null, '0', '1', '1', '1501754791533', null, '1502692991931');
INSERT INTO `tb_dict` VALUES ('38a04ae146a141fbbc97b00e4add4d83', 'station_type', 'AcFun', '1', '1', 'label label-sm label-success arrowed arrowed-righ', 'icon-wf_Azhan', '1', '1', '1492511553152', '1', '1501837764640');
INSERT INTO `tb_dict` VALUES ('3bc2a75b79c14f8d8efa5fe8a5573aba', 'A_default_values', '主机单机', '84', '1', 'label label-sm label-success arrowed arrowed-righ', '87785', '1', '1498720479911', '1', null, '1502693005871');
INSERT INTO `tb_dict` VALUES ('41c56ecd64a74fcd9ef173fe6b90f2cf', 'A_default_values', '电子竞技', '145', '3', 'label label-sm label-success arrowed arrowed-righ', '8810', '1', '1498720530330', '1', '1', '1502693021625');
INSERT INTO `tb_dict` VALUES ('43ced2c9973f4796964d7d13f0d83b06', 'station_type', '优酷视频', '3', '3', 'label label-sm label-success arrowed arrowed-righ', 'icon-wf_youku', '1', '1', '1492511580841', '1', '1500344714287');
INSERT INTO `tb_dict` VALUES ('4c8b5b14716644eeb57c1827f0366610', 'up_second_title_type', '个人生活', '1', '1', 'label label-sm label-success arrowed arrowed-righ', '人物二级信息的标题', '1', '1496803980697', '1', null, null);
INSERT INTO `tb_dict` VALUES ('51add0bc66f14d658c848a9c61f9db75', 'sys_switch_group', '开启信息弹幕', '1', '1', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1498546313270', '1', null, null);
INSERT INTO `tb_dict` VALUES ('524e447b0eee462a984bc3837a6805ee', 'yes_no', '否', '0', '2', 'label label-sm label-warning arrowed arrowed-righ', '是/否', '1', '1', '1492410890200', '1', '1492418732498');
INSERT INTO `tb_dict` VALUES ('52a18152472745b3850f68317ade5a7c', 'user_security_question', '您配偶的姓名是？', '9', '9', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071102291', '1', null, null);
INSERT INTO `tb_dict` VALUES ('534aeb93d247421b863c9553d5288aff', 'A_default_values', '桌游卡牌', '165', '6', 'label label-sm label-success arrowed arrowed-righ', '3607', '1', '1498720640818', '1', '1', '1502692971560');
INSERT INTO `tb_dict` VALUES ('58d6e6a5ea994ca4af63f54883f59033', 'station_type', '土豆视频', '4', '4', 'label label-sm label-success arrowed arrowed-righ', 'icon-wf_tudou', '1', '1', '1492511598205', '1', '1500344737396');
INSERT INTO `tb_dict` VALUES ('5bd91785ecc34882a9d57fdabce92152', 'Q_now_offset', '电子竞技', '976', null, null, '4770', '1', '1', '1501754791692', null, '1502692993967');
INSERT INTO `tb_dict` VALUES ('5d2f31b8f568421f93494b0e46e484aa', 'up_name_type', '主名', '1', '2', 'label label-sm label-success arrowed arrowed-righ', '作者名称类别', '1', '1', '1492506862212', '1', '1492658430789');
INSERT INTO `tb_dict` VALUES ('5eebfe6421f94497a407ffc8ec530f36', 'third_party_type', '战旗TV', '5', '5', 'label label-sm label-success arrowed arrowed-righ', '所属第三方', '1', '1', '1493372258869', null, null);
INSERT INTO `tb_dict` VALUES ('6d20109b4031406db87dc9e62cc3f0ef', 'user_security_question', '对您影响最大的人名字是？', '13', '13', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071141552', '1', null, null);
INSERT INTO `tb_dict` VALUES ('6f213bd5f9844b10b559057d99b6d41b', 'Q_now_offset', '手机游戏', '975', null, null, '0', '1', '1', '1501754791506', null, '1502692986231');
INSERT INTO `tb_dict` VALUES ('759e235835694a9683dfa52ec47a9b05', 'user_security_question', '您父亲的生日是？', '8', '8', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071090812', '1', null, null);
INSERT INTO `tb_dict` VALUES ('7a93f344524f4202bc1727f7620e0d6b', 'user_security_question', '您小学班主任的名字是？', '7', '7', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071080667', '1', null, null);
INSERT INTO `tb_dict` VALUES ('8c27304a9be043c098d1fff716bc661d', 'user_security_question', '您的学号（或工号）是？', '3', '3', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071024335', '1', null, null);
INSERT INTO `tb_dict` VALUES ('a3eeaf663e2342e282009994c701d560', 'user_security_question', '您高中班主任的名字是？', '5', '5', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071059168', '1', null, null);
INSERT INTO `tb_dict` VALUES ('a40fac2143bd4ebeb48171f0bdbf067c', 'up_second_title_type', '游戏资料', '2', '2', 'label label-sm label-success arrowed arrowed-righ', '人物二级信息的标题', '1', '1496804744728', '1', null, null);
INSERT INTO `tb_dict` VALUES ('a625e74928634ccbb9e189b65841ef4c', 'A_default_values', '游戏集锦', '83', '2', 'label label-sm label-success arrowed arrowed-righ', '122230', '1', '1498720514111', '1', null, '1502693001006');
INSERT INTO `tb_dict` VALUES ('b0eec46c0c084187a1084db1d245d438', 'third_party_type', '微博', '1', '1', 'label label-sm label-success arrowed arrowed-righ', '所属第三方', '1', '1', '1493362868829', null, null);
INSERT INTO `tb_dict` VALUES ('b9db8628abe84a798d70302ff19c267f', 'seo_config_type', '首页', '2', '2', 'label label-sm label-success arrowed arrowed-righ', 'SEO类型', '1', '1498545058442', '1', null, null);
INSERT INTO `tb_dict` VALUES ('bba4363a188e4c8db7d2f83140349b6b', 'user_security_question', '您最熟悉的学校宿舍舍友名字是？', '12', '12', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071132907', '1', null, null);
INSERT INTO `tb_dict` VALUES ('c7b194700cf2461d86670e5943786e83', 'effective_status', '有效', '1', '1', 'label label-sm label-success arrowed arrowed-righ', '有效状态', '1', '1', '1492410827287', '1', '1496830823777');
INSERT INTO `tb_dict` VALUES ('cf3558da0d464ef295ea822c2396c86b', 'third_party_type', '全民TV', '6', '6', 'label label-sm label-success arrowed arrowed-righ', '所属第三方', '1', '1', '1493372270211', null, null);
INSERT INTO `tb_dict` VALUES ('d74d2f9f546c4e81bd6b7db7c8cd4962', 'user_security_question', '您母亲的生日是？', '4', '4', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071041369', '1', null, null);
INSERT INTO `tb_dict` VALUES ('dcc184cd6bbe40209fde4441ddcf10e2', 'up_name_type', '次名', '2', '1', 'label label-sm label-success arrowed arrowed-righ', '作者名称类别', '1', '1', '1492506874808', '1', '1492658435372');
INSERT INTO `tb_dict` VALUES ('de0fce047fee4d5e877ecdf9b5ec6e97', 'station_type', '芒果TV', '7', '7', 'label label-sm label-success arrowed arrowed-righ', 'icon-wf_mangguo', '1', '1496803614979', '1', null, null);
INSERT INTO `tb_dict` VALUES ('e54385ccc0f64f9b8fb1570af83d86c2', 'station_type', 'bilibili', '2', '2', 'label label-sm label-success arrowed arrowed-righ', 'icon-wf_Bzhan', '1', '1', '1492511564859', '1', '1501837774016');
INSERT INTO `tb_dict` VALUES ('e60bf0baa47843258d775110c8d1a4c1', 'A_default_values', '守望先锋', '170', '5', 'label label-sm label-success arrowed arrowed-righ', '-62', '1', '1498720615095', '1', null, '1502692989504');
INSERT INTO `tb_dict` VALUES ('ef885eddace9467c988f184fb226f74f', 'user_security_question', '您目前的姓名是？', '1', '1', 'label label-sm label-success arrowed arrowed-righ', '', '1', '1499071001322', '1', null, null);
INSERT INTO `tb_dict` VALUES ('f0237840bb6348e58a840d5e0238dfd3', 'A_default_values', '英雄联盟', '85', '4', 'label label-sm label-success arrowed arrowed-righ', '47204', '1', '1498720591340', '1', null, '1502692995736');
INSERT INTO `tb_dict` VALUES ('f76530757d7a48adbfff50998c3df273', 'station_type', '腾讯视频', '6', '6', 'label label-sm label-success arrowed arrowed-righ', 'icon-wf_tengxun', '1', '1', '1492511634050', '1', '1500344781220');
INSERT INTO `tb_dict` VALUES ('fcb35ef7e98c4d129897091053406b8b', 'station_type', '爱奇艺视频', '5', '5', 'label label-sm label-success arrowed arrowed-righ', 'icon-wf_aiqiyi', '1', '1', '1492511623943', '1', '1500344762342');
INSERT INTO `tb_dict` VALUES ('fe000aeeacb549af923c3b0a7565267e', 'yes_no', '是', '1', '1', 'label label-sm label-success arrowed arrowed-righ', '是/否', '1', '1', '1492410875819', '1', '1492411049187');

-- ----------------------------
-- Table structure for tb_login_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_info`;
CREATE TABLE `tb_login_info` (
  `l_id` varchar(32) NOT NULL,
  `u_id` varchar(32) DEFAULT NULL,
  `u_account_name` varchar(255) DEFAULT NULL,
  `l_ip` varchar(255) DEFAULT NULL,
  `l_region` varchar(255) DEFAULT NULL,
  `l_province` varchar(255) DEFAULT NULL,
  `l_city` varchar(255) DEFAULT NULL,
  `l_login_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_agent` varchar(500) DEFAULT NULL,
  `device` varchar(50) DEFAULT NULL COMMENT '登录设备',
  PRIMARY KEY (`l_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_login_info
-- ----------------------------
INSERT INTO `tb_login_info` VALUES ('00492cd8c3cb4856b98e165824d8a126', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-24 14:45:21', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('02f259d6b71c425f978da52a92d3782c', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-07-31 16:31:13', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('03d3734aa5734785a2b37495a2d52c63', '06cd6367762049e69338a08ae65a812a', 'v米', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-02 17:39:11', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('040861fd11c245bf84746ca45e4c8101', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '36.102.236.244', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-27 16:49:04', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('086bebea941141b79d047c6ab162caa8', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-14 10:37:17', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('09b23dfc44f74be29b918f803030a179', '12e83fde1ef44aa29609785ec03433b6', '大支', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-01 16:19:05', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3141.7 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('0a8d8e4dcfbd4b629ee32822d6d4dc9f', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 10:57:41', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('0ae5754b0c10482c967476b8e21578f1', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-22 14:41:07', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('0b0b033317854a5398580a4f1b6ec8d4', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-26 13:59:53', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('0cbbda9d4d8f4f3dba414b1dd89ef7c1', '06cd6367762049e69338a08ae65a812a', 'v米', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-14 14:38:38', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('0cce19f844bb4bb98f39619cc2fc0d93', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-29 10:29:51', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('0eacf230c24740498d5f0449acd9309f', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-07 10:09:33', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('0f27a0187d6f4f58ab98b23edd24ecd8', '12e83fde1ef44aa29609785ec03433b6', '大支', '115.239.226.221', '中国|华东|浙江省|绍兴市|电信', '浙江省', '绍兴市', '2017-07-25 16:20:40', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('0f5d9e91407f455cb61019584373d14e', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-21 15:10:35', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('1137dc64efd64b15a4769339ddd6f566', '1', 'superadmin', '59.172.188.94', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-19 12:13:49', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('11bb3d4f20f94d79b842f088fa49e334', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 16:17:44', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('131e6e5efdc945ae8bab29a1fb03c32c', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-14 14:18:36', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('136d4d33a49a441ca4a385e75be7df8a', '1', 'superadmin', '59.172.188.94', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 12:47:33', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('1376b04dd32c427a8f58b2f796defc6a', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '36.102.236.246', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-27 16:13:39', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('13a516a85653467497b25ff51afdcdcb', '1', 'superadmin', '59.172.188.94', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-20 12:51:35', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('14adea33fca04e23a84676ffaa61cc0a', '1', 'superadmin', '59.172.188.94', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-20 11:55:05', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('163cd074a80b4962b4aa4630f949cd04', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '36.102.236.246', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-28 14:26:01', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('16c8b6a8ecdc47bead0b4e992f707333', '2169db33b2f64697a84d4027dbd7bcc3', '真的是鸭蛋', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-07 10:25:25', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11681.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('17084b3ea59d4090934756cf731e2242', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-26 15:10:43', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('1dc6e20aa5314c75869270e0d3b529f7', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-07-31 17:03:38', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('214ad3b95ead4079b62d97eeae4abbdf', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-15 14:31:23', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('22832074af094700ac45ee3171c0b4a2', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-08 15:26:56', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('263e26ea11d048f194791962fb6127ba', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-27 15:31:55', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('272950e114f943b2bb6d4c65fcf0a9a5', '06cd6367762049e69338a08ae65a812a', 'v米', '36.102.236.246', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-27 17:10:24', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('27acc546c09a4105be5e24dd4fe482a5', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-24 18:34:47', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('28b778b5e84c49b684b198180571c6ce', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-31 10:55:11', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('291c04132e4b4fe8b481622efc35058f', '12e83fde1ef44aa29609785ec03433b6', '大支', '171.113.106.0', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-22 22:55:27', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('2a1b972139b942c5b2c5e3ca106b229e', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-18 14:28:39', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('2c0da68c72e64bb1b81edc0f99c09aab', '8e9ba9fdb29c4826a65984820155d282', '丶無趣', '111.37.37.107', '中国|华东|山东省|潍坊市|移动', '山东省', '潍坊市', '2017-08-08 11:50:24', 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('2c16b84fbca34d609fc11535471ea610', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-13 09:39:47', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('2d9914c648a24982839d04b4aaee445d', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-02 16:33:27', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('2dd9cb3ade89425ba64f8b69596c9b24', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-01 10:53:18', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('2de86c717bed479896c96bd9e462aacf', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-12 09:51:30', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('2e585d3f3b47458f98b15a9d15779c55', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-11 18:47:12', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('308d6182b9f448a18cfb865ac27664bb', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '36.102.236.242', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-27 17:13:01', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('319f27bac01a4e559e05a76d36c686ba', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-28 17:25:39', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('31b92cf17da54e3eb1a4fcb659ca0550', 'fb81845169554873a8e809c4610a463e', '少飞', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 10:53:56', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('33eab7bfbce744dfb64d33da888b6f93', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-22 14:33:36', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('34a53cdf44c54d868b4a3cfdb9dd91d4', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-02 10:27:51', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('35bccfc33485416bb067cb78bbf731ce', 'fe367eb73b164e7797ccfe5bab9171a6', 'wf_Lx4K9ewzv', '36.102.236.242', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-27 16:10:31', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('36112e31fc9642438367220ff9d3812f', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-14 14:10:49', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('3756cacf95834b65b9c1b72fe1677ae8', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-20 10:24:01', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('388f64e781d54e25ac2a12675b0df7d9', '12e83fde1ef44aa29609785ec03433b6', '大支', '59.172.188.87', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-25 11:16:43', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('3c542192321d46ba93f8db8d1cdb0783', '1', 'superadmin', '192.168.10.38', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-28 11:52:00', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('3d87a518a4d84618aeaf771cf76dea77', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-25 14:11:13', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('3e2c5e5b0ed84e848dc46725758f655b', '1', 'superadmin', '192.168.10.28', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-25 16:36:29', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('40197df65d0b4b9784061450d7a8f863', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-14 14:37:06', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('41565aa9206742779a82a2aa1e509992', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-28 19:38:17', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('415c38b7d543441c87a0372df212a9e6', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 17:03:12', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('4230114bf93a4702ab437cca9093f869', 'a4237b3b08c84a229fde55509068d639', 'wf_3f3VXQSMm', '59.172.188.90', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-25 11:40:24', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7', 'Windows');
INSERT INTO `tb_login_info` VALUES ('4689079bb565479c81678977f4d51726', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-21 11:47:07', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('46ccb5fdeaa947a7bc2f1fc963bc0f0e', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-25 15:54:13', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('47a05877d03d41ccac742e314ae40252', '2acfe173afc14c2fa1836ebb771d7acb', 'wf_GVkOIcfrp', '59.172.188.86', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-20 11:44:27', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('4a6ac7bf73eb4d228ded02f6bef244eb', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-20 18:02:44', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('4b5ddd1ebd794fed8fc092bc6ba820ce', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-31 20:07:36', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('4c8d1cd3963a43b78bb10411bc7f054e', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-28 10:11:43', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('4cba6f9989924c4ca112c18c99d7be0a', '2169db33b2f64697a84d4027dbd7bcc3', 'wf_iPvUebj2Q', '59.172.188.89', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-24 17:34:25', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11681.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('4e69dc7cf3ff486c93df75573b491028', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-25 17:36:52', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('50250e06264f4d018d7f5ea8f21eafd8', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-25 12:00:26', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('5213de0812134b80957b2bf26ddcd395', '12e83fde1ef44aa29609785ec03433b6', '大支', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-07-31 12:04:05', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('52ebf7f3b84649c7b7dae069e5cab9e5', 'fb81845169554873a8e809c4610a463e', 'wf_DkXhWVFZp', '59.172.188.86', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-20 11:06:00', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('54af1af438d3495680e56f162d0e514f', '31ecce68d6fa4185a3798b9ef7d92cf4', 'wf_DBC90FmtT', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-07 16:00:41', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('5622065c8e5c4f3f814762be25a4385b', '1', 'superadmin', '59.172.188.94', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 11:19:52', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('56a62bd00cec4d8ba503f9ff6b06ea74', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-11 14:45:39', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('597900d636db424cb427c2b4b53721b5', '12e83fde1ef44aa29609785ec03433b6', '大支', '59.172.188.87', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-24 16:23:45', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3141.7 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('5d0b07b37e9b41e7858c5884e41d18f3', '12e83fde1ef44aa29609785ec03433b6', '大支', '171.113.117.183', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-08-13 22:09:09', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('5d817ccf90984684bc2b079355a4cba1', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-05 16:16:03', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('5e125674ace843b6a18aa846c890f10f', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-22 11:08:06', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('5e86a88d0b544a4f95d699a60bfcdcc7', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-24 14:41:47', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('5edc9ec3b08a4239ad413581cd04fcc9', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-23 16:05:46', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('5f54d707edc54d2abb26183f2d9cc645', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-04 15:58:25', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('5f90ce7a0b6546bd8916dfa98dfc7319', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 09:56:15', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('6021c84e5d814f2cbd0ab50f6833228a', '12e83fde1ef44aa29609785ec03433b6', '大支', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-07-31 13:39:57', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('605dadfdaf4e4015968b80a82b633874', '12e83fde1ef44aa29609785ec03433b6', '大支', '59.172.188.87', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-25 11:17:21', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('615ee0d50fc54a42bb19ec900fd54085', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-28 16:41:05', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('6323f6e1ad5e4f738f1ed4ee89844300', '31ecce68d6fa4185a3798b9ef7d92cf4', '阿宁', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-07 16:12:38', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('63c8bf6d78a0476db02e184f6c23786f', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-25 18:17:38', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('64e1c64642154ce48e2b98ed878083ca', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-29 14:46:09', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('666182ce4fba4c9fac4981043b38bcc3', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-26 09:56:37', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('66f600d64ab148059888c6d340fbe621', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-15 15:37:38', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('676fe19ae6d24f64bbd46a393a8a31a4', '02836c03b3bc4f07a697f2d05f70100b', 'wf_L44wWPkSo', '180.173.221.74', '中国|华东|上海市|上海市|电信', '上海市', '上海市', '2017-08-07 18:10:09', 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('68ec865841274d91acd06b979e4169c2', 'fb81845169554873a8e809c4610a463e', '少飞', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-02 17:30:14', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('6d1a106c4c4343d68ee5ed4563626db7', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-02 17:22:26', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('6e0b55ec746c4a699363b0333373700b', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-28 17:57:16', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('6e42565ef4d44eabbe2ba44401a1bc97', '06cd6367762049e69338a08ae65a812a', 'wf_LtbkoMDWv', '59.172.188.94', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 14:52:14', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('7096255016fc465e9d14bd366e1c62ae', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-31 20:13:36', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('71d72133514d4d4c95698c643d7d39ab', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '36.102.236.242', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-27 17:14:01', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('72ed57bb8de540e698cbf1beaca5000f', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-01 10:56:31', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('749a4f9aeb634a9d82256ec30277ae79', '31ecce68d6fa4185a3798b9ef7d92cf4', '阿宁', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-08 17:57:05', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('764d140cc8be44ae93d77b427db7e549', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-31 20:08:12', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('777bb305e29b478f80b66fa4dc5feac8', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-02 17:07:53', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('7782ccf1a5a947f5854ece9e59fb8c5a', '896c70c78ab14afd9594fcb9faf0b52e', 'wf_RIVM61lGA', '59.172.188.92', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-20 11:26:00', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11681.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('77c131c7bdcd48c793f965a0309085f7', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-20 15:50:01', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('7ac61b279a8f4f86b6cdec0374ec4467', '6ab0651ce12b44daafbcee76de129bb4', 'wf_h8k8nhX5U', '183.134.67.194', '中国|华东|浙江省|绍兴市|电信', '浙江省', '绍兴市', '2017-07-25 18:22:35', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('7c2c17d44e554869937d64bd92ce7ad9', '06cd6367762049e69338a08ae65a812a', 'v米', '59.172.188.94', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 18:43:25', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('7c45736327cb488b82c6981d73e1b399', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-22 11:08:48', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('7d8fb9199c624f309f30f87677dd0627', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 18:15:24', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('7e80afa7fae242849cfcd9dd25127dd3', '06cd6367762049e69338a08ae65a812a', 'v米', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-14 10:59:35', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('7f0fbd097639431ab34e92ec518061bd', '06cd6367762049e69338a08ae65a812a', 'v米', '115.239.226.218', '中国|华东|浙江省|绍兴市|电信', '浙江省', '绍兴市', '2017-07-25 15:56:19', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11681.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('80463be386914372aac608fc3aa4096a', '06cd6367762049e69338a08ae65a812a', 'v米', '36.102.236.244', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-27 17:11:09', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('80aca1cf5e9d45d1af5ae29bb5e284c8', '12e83fde1ef44aa29609785ec03433b6', '大支', '59.172.188.87', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-21 18:39:56', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('8210540a9dfe4b52b60446ac7ae96957', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-31 15:32:42', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3357.400 QQBrowser/9.6.11858.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('838cd8a089d84c3a8f26051efb42305c', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-03 18:05:28', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('8535736381444e0cb0aec84881ecf685', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-26 12:15:22', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('866a2a937cc1493fa8eb46041ed7a877', '8e9ba9fdb29c4826a65984820155d282', 'wf_DkRwvukJk', '111.37.37.107', '中国|华东|山东省|潍坊市|移动', '山东省', '潍坊市', '2017-08-08 11:49:22', 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('8748c3b9ef96427aabe8240913afb7ab', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-01 11:07:14', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('895a5a4c4650435e8e4d84a489c8ee7d', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-28 17:26:26', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('89709226abbc4039b547640e0c9a09e7', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-04 17:08:50', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('8cfbbef745b443da94315f0ab428dfe8', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-26 14:52:00', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('8ea0de4bb651402dae8930f8504b3397', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-04 17:01:09', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('91ada9a6c7d14df3906b78533ef6b019', '06cd6367762049e69338a08ae65a812a', 'v米', '192.168.10.46', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-28 18:46:07', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('94be92bd9c4d44e697e68e0e3dcdfe22', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-22 10:45:49', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('9aed076555bb499eb92015f10c0a45e0', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-07 12:00:00', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('9ced2c05968c4c7d9446986032cf657c', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-02 17:58:38', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('9eda9323fceb4f1fba418aeef3816da7', '12e83fde1ef44aa29609785ec03433b6', '大支', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-03 11:18:45', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('9f6263def0274257b9bf31df4d52165c', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-20 15:11:05', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('a17df010e7a947339ebec0188853befd', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-11 15:07:39', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('a1aec4a79160499182dfd3e43bb37700', '2169db33b2f64697a84d4027dbd7bcc3', '真的是鸭蛋', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-04 13:26:47', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11681.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('a2cddc94c1594edb816f821b6f353b9b', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-21 10:29:37', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('a2ce0d089ece49a095b4e8ffbb9287a6', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-22 14:53:02', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('a6b2a2121758412ea8f1d21872267a3b', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-25 09:59:50', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('a85baf83561b40838647e60cd4d08772', '2169db33b2f64697a84d4027dbd7bcc3', '真的是鸭蛋', '59.172.188.89', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-24 17:37:40', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11681.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('a8eb325777dc4656a4b96aace088ef4e', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-24 18:00:59', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('a9bb4bd6afe946b1ad50fdce89b32ab6', '36eedbaea8514cb6a28b6f97f19cd970', 'wf_NFCDWaGV7', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-19 14:58:30', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ab03ea67c010486aaf74dc0b6c20e274', '06cd6367762049e69338a08ae65a812a', 'v米', '192.168.10.46', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-28 12:59:05', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ae08dda6ea314c63b4009d8a572dc9b1', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-07 17:19:44', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('b1df77d4f8ed4f72a0c2d980d8b7d5d0', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-18 15:52:59', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('b24ab5aac6474722924cad338db4e8eb', '1', 'superadmin', '192.168.10.28', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-23 15:32:22', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('b39ec5bb1c8b45acb4efb13e05da65a3', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-11 17:08:57', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('b3d9d6343e134d74a09c1b4be9b1112d', '31ecce68d6fa4185a3798b9ef7d92cf4', '阿宁', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-07 17:16:31', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('b637ba25588742bb835eb6576a0efcc7', '12e83fde1ef44aa29609785ec03433b6', '大支', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-02 17:43:17', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('b89b2898e80f4d88b7af87a74cfaefcb', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-24 18:01:33', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('bd00592af8334c97986f41f6851d826d', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-03 11:00:35', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('bdadaf6dc265403a9e1766978be689f2', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-11 15:56:17', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('be02b9a25cd248a297903edb387b79ce', '7540d5600b4e4236a6586326bbb60418', 'wf_taFNumrUB', '222.82.48.179', '中国|西北|新疆维吾尔自治区|乌鲁木齐市|电信', '新疆维吾尔自治区', '乌鲁木齐市', '2017-08-04 16:34:14', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c035220cfdda4a4081f458f330c72de0', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-28 17:41:55', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c038dcf21c40408fba520366bd9d5678', 'a4237b3b08c84a229fde55509068d639', 'herry', '59.172.188.90', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-25 11:46:57', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c08004dffbac4cffa60d05ce59795ab1', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-25 11:38:12', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c2d72da15b9d41c8a3c8bf4a756cf01f', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '36.102.236.244', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-28 11:30:13', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c386f09b98084e5c9313aaf8aafa7f56', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-31 10:54:04', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c4dbe58c601140f49d7086ea407d68fd', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-31 20:44:08', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c517618a5c004aee81704b0f163e8d97', '06cd6367762049e69338a08ae65a812a', 'v米', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-01 15:55:44', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c6339501f23d47b0901db86093e283a4', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-25 14:03:59', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c6bbedc43b1f493aae6d38e82bafed25', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-28 10:10:21', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c7ccf00995594b219328808f455fd43f', '06cd6367762049e69338a08ae65a812a', 'v米', '192.168.10.46', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-29 18:57:28', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c80f054621634407a19b7e16e6cacc53', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-25 09:59:51', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('c9e7dcca0ab648c1b95e72bd7c318db5', 'd196dddf1d714336ad987a26d544d990', 'wf_QCcXwGFzp', '219.140.224.66', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-24 14:50:15', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ca11b30b6a094935bb89f3a93dc35bd3', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-13 14:59:03', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ca15c0a3232b454b97f61acda546024b', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-25 11:38:11', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('cabf895c403f4da7b01e5fe6365e4e55', '12e83fde1ef44aa29609785ec03433b6', '大支', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-11 12:00:07', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('cbb3bf7743494a38981ea60d24c02c66', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '36.102.236.246', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-27 17:12:32', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('cc75fd3b30ba48c4af0a09d23707dd65', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-01 17:57:18', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('cd04abce35be442bbdfb9dd5079a5526', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-15 10:10:33', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('cfcca2d01d454844a0cd080d3fe0b416', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-22 15:34:08', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('d2441c96bc5e45ee9d9052357cc9b647', 'a4237b3b08c84a229fde55509068d639', 'herry', '59.172.188.90', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-25 11:42:08', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7', 'Windows');
INSERT INTO `tb_login_info` VALUES ('d24f4170094e4e468ca76bd3266c998f', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-20 10:18:01', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('d3f16d425ca74d90b092351d6d0eb897', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-03 19:34:48', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('d418c4f13fa040559cf217f95df2806f', '1', 'superadmin', '192.168.10.28', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-23 17:23:10', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('d87dc30e3dc04a2281296ad9212e9481', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-09 16:00:28', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('d93b77ac1dfa426fb444b7ef1055e312', '1', 'superadmin', '192.168.10.46', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-11 15:04:07', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('da1d5ff488594abb9e9e823d86396acd', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-28 18:54:26', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('db26d97a22884eb59253e0c404cb8136', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-01 18:01:30', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('dc7bc0139c6049729c842b349cb877f0', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-14 14:43:45', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('dcb9e01c0e5b4e13ad37ccf7bc333007', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-07-31 14:21:42', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('dcfbefeae72948e493d56430d0e96224', '06cd6367762049e69338a08ae65a812a', 'v米', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-14 14:19:41', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ddce8e1566814c958262ea898f199a6f', '12e83fde1ef44aa29609785ec03433b6', 'wf_5hw08Jw3t', '59.172.188.87', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-20 11:34:51', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3141.7 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('de64a9834c5d4ad383efe5fe3eec3446', '1', 'superadmin', '59.172.188.88', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-19 14:34:15', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('dfb907d39ec74c7eb3e10136452928d1', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-07 16:00:46', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('dfd981b2c822468e8777573ce610b204', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-03 17:18:55', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('e05c16e2950647e49cb489ef4ade6156', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-25 09:59:50', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('e0a08bb7f8374a06b343053153f6abf6', '12e83fde1ef44aa29609785ec03433b6', '大支', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-11 12:07:42', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('e2b91f75c2524fd0bf9744d4aab8baba', '12e83fde1ef44aa29609785ec03433b6', '大支', '59.172.188.87', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-25 11:16:01', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('e30aeb7d0e0d43d79f347ba561c1e3b1', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-22 11:13:23', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('e4140b3c6eb146af9cf1b611d223a538', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-26 15:09:46', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('e6ba22c364044992b6116b4bbece6adc', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-26 11:28:57', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('e818a068403b49b38b1dbc7ef9db94f7', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-01 16:24:27', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('e9691364889d420ca9759bc8ee4acf53', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-14 14:20:16', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ebcc414bf144401ca64eafe904aebb35', '06cd6367762049e69338a08ae65a812a', 'v米', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-14 14:39:43', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ebf5813980c6425bb10996e6c2153b4f', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-29 15:48:24', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ec02316acfb84b7a99c5757fa44f5369', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-12 15:00:33', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ecb0d75b188c4bef9e3127ffb66701c2', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-13 10:22:11', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('edae7cfd0eab4f15a8ca136f9c7a35ce', '1', 'superadmin', '36.102.236.246', '中国|华东|浙江省|杭州市|电信', '浙江省', '杭州市', '2017-07-27 17:47:01', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('edf6b8643bcb40f994209f62cffcab54', '06cd6367762049e69338a08ae65a812a', 'v米', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-07-31 10:39:23', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ee6d2a2f9b394b2d8970902326441b5c', '1', 'superadmin', '192.168.10.40', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-22 14:51:42', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ef0186ba37fd43978eee83aa4ebdaeb4', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-12 10:37:03', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('ef01a3f48cc34991975862eea68a7346', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-08 15:35:57', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('f2610824026940da910a5ff7f08d435e', '12e83fde1ef44aa29609785ec03433b6', '大支', '59.172.188.87', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-25 10:50:51', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3226.400 QQBrowser/9.6.11682.400', 'Windows');
INSERT INTO `tb_login_info` VALUES ('f302183a2f23426db14aa396ca6faf3c', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-07-28 17:55:24', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('f394721b2c3a4f9f8f8bb1eb2f101222', '06cd6367762049e69338a08ae65a812a', 'v米', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-07-31 14:14:50', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('f39f6252e44a43baa444ede47347c135', '31ecce68d6fa4185a3798b9ef7d92cf4', '阿宁', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-07 16:35:33', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('f3b756da81dd4afdbac1065616e481da', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-13 10:09:17', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('f4a7822da8eb47cabdae44ba00a1d837', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-28 14:06:37', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('f4ad84e0814a4de8a7463aa569a4cc18', 'fe367eb73b164e7797ccfe5bab9171a6', '小黄鸭', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-01 17:04:53', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0', 'Windows');
INSERT INTO `tb_login_info` VALUES ('f6180c11db8c436db6a358298e595c48', '06cd6367762049e69338a08ae65a812a', 'v米', '119.98.79.77', '中国|华中|湖北省|武汉市|电信', '湖北省', '武汉市', '2017-07-31 20:58:49', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('f9f30d7fb9944c3a9a284a109c7dea78', '1', 'superadmin', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-19 17:43:08', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('fac1d1c3e91b41d4a9c231a556acf17d', '36eedbaea8514cb6a28b6f97f19cd970', '素颜', '115.239.226.214', '中国|华东|浙江省|绍兴市|电信', '浙江省', '绍兴市', '2017-07-25 15:03:11', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('fc3d4007e37841ddb05df9a91f89bcab', '1', 'superadmin', '192.168.1.100', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-08-04 16:06:01', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('fd344b5aea2e4290bc0168469c5c1306', '31ecce68d6fa4185a3798b9ef7d92cf4', '阿宁', '0:0:0:0:0:0:0:1', '未分配或者内网IP|0|0|0|0', '0', '0', '2017-09-07 16:20:54', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36', 'Windows');
INSERT INTO `tb_login_info` VALUES ('fed83494fa8d427782fb18c7818ad989', '1', 'superadmin', '220.170.187.179', '中国|华中|湖南省|株洲市|电信', '湖南省', '株洲市', '2017-08-14 14:41:55', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36', 'Windows');

-- ----------------------------
-- Table structure for tb_log_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_log_info`;
CREATE TABLE `tb_log_info` (
  `l_id` varchar(64) NOT NULL,
  `u_id` varchar(32) NOT NULL,
  `l_account_name` varchar(100) DEFAULT NULL,
  `l_operation` varchar(255) DEFAULT NULL COMMENT '用户所做的操作',
  `l_content` text COMMENT '日志内容',
  `l_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`l_id`),
  KEY `IN_LOGINFO_UID` (`u_id`) USING BTREE,
  KEY `IN_LOGINFO_CTIME` (`l_create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_log_info
-- ----------------------------
INSERT INTO `tb_log_info` VALUES ('027ca8206def4fa4a82810fa6f85af14', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 属性值管理)(getId : 00254d3a9ab54a8199a2c147ec0648f2)(getType : 0)(getDescription : )(getIcon : fa  fa-bat)(getSourceUrl : /taskValuetCtrl/listUI.html)(getSourceKey : task:value)(getChildren : [])(getCreateTime : Fri Sep 22 17:14:09 CST 2017)(getParentId : bd27a654ce814223b45045b42dcfb6c4)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-22 17:14:09');
INSERT INTO `tb_log_info` VALUES ('03db7eab665241dea4c90d9253023c17', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@2653ebea)(getStatus : com.webside.dict.model.DictEntity@6c3498e2)(getTask : com.webside.task.model.TaskEntity@68ae132f)(getUpdateTime : 1505472150002)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@5e2f0de1)]', '2017-09-15 18:42:30');
INSERT INTO `tb_log_info` VALUES ('03f52142bf354b9d9f8ec4da0437d072', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : cfcf145424ba4011b5dd2949f863bffe)(getContent : 1)(getStatus : com.webside.dict.model.DictEntity@283a9a82)(getCreateUser : com.webside.user.model.UserEntity@4bff427f)(getTitle : 111)(getCreateTime : 1505814415741)(getViewCount : 1)(getUp : com.webside.up.model.UpEntity@4afbc66)(getLikeCount : 1)]', '2017-09-19 17:46:56');
INSERT INTO `tb_log_info` VALUES ('05ff2d5ec41f464ba5bad0cb7cbac3c6', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 1,1)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@90e7e1a)(getCreateUser : com.webside.user.model.UserEntity@773ac5cf)(getUpdateUser : com.webside.user.model.UserEntity@28d2a12f)(getUpdateTime : 1)(getCreateTime : 1)(getStatus : com.webside.dict.model.DictEntity@74185233)(getParentComment : com.webside.task.model.TaskCommentEntity@5f6376a3)(getLikeNum : 1)]', '2017-09-15 18:01:48');
INSERT INTO `tb_log_info` VALUES ('09cb1730fec64e3ea3ddbbb860a67a97', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : 918c67549ec34fa986386aac9ee3ea7e)(getContent : 1)(getStatus : com.webside.dict.model.DictEntity@3047d684)(getLikeCount : 1)(getUp : com.webside.up.model.UpEntity@4152e64)(getCreateUser : com.webside.user.model.UserEntity@29c7fae4)(getTitle : 1)(getCreateTime : 1505383820490)(getViewCount : 1)]', '2017-09-14 18:10:21');
INSERT INTO `tb_log_info` VALUES ('0af8c30321084429b7512e2dba596905', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 修改人物事件UI)(getId : 8f219d78691741d5bde5b52d34aac922)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCtrl/editUI.html)(getSourceKey : task:editUI)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 17:34:57');
INSERT INTO `tb_log_info` VALUES ('0d0fc99a2e0b4d7d9b14ee4479fb79b4', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 视频标签举报list)(getId : dc87ae762af94c849c7b965184878854)(getType : 1)(getDescription : )(getIcon : )(getChildren : [])(getCreateTime : Tue Aug 22 14:52:36 CST 2017)(getParentId : 3cf54176dd154e97b9f3adc814aac7bd)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getSourceUrl : /videoValueInform/list.html)(getSourceKey : videoValueInform:list)]', '2017-08-22 14:52:37');
INSERT INTO `tb_log_info` VALUES ('0d65731129fa4aefa046090332f39b7f', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@50b6d06e)(getStatus : com.webside.dict.model.DictEntity@4507ba44)(getTask : com.webside.task.model.TaskEntity@1eb27f4c)(getUpdateTime : 1505472013871)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@2d76916a)]', '2017-09-15 18:40:14');
INSERT INTO `tb_log_info` VALUES ('0fb6c413d7f04e1684d67a395af3ec93', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 新增人物事件UI)(getId : f41a5399b6754affa6ecb0c772d49035)(getType : 0)(getDescription : )(getChildren : [])(getCreateTime : Mon Sep 11 15:09:19 CST 2017)(getSourceUrl : /taskCtrl/addUI.html)(getSourceKey : task:addUI)(getIcon : )(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:09:20');
INSERT INTO `tb_log_info` VALUES ('10f411ba14b14435a181ef375611a87a', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoAlbumEntity，值：(getName : 测试)(getId : 96923c9e40ca45eaa733ab5ce980db9f)(getStatus : com.webside.dict.model.DictEntity@2dc82ef6)(getHomeId : )(getCreateUser : com.webside.user.model.UserEntity@4f84dacd)(getCreateTime : 1503479007105)(getIntroduction : 测试测试)(getParentAlbum : com.webside.video.model.VideoAlbumEntity@6e0cc533)(getHomeUrl : )]', '2017-08-23 17:03:27');
INSERT INTO `tb_log_info` VALUES ('11d994de92424d15a4791ff2bd7cd8a9', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 修改属性值)(getId : ebe4b4d7740c4eb1bf2d852c4c6f6179)(getType : 1)(getDescription : )(getIcon : )(getSourceUrl : /taskValueCtrl/edit.html)(getSourceKey : taskValue:edit)(getChildren : [])(getCreateTime : Fri Sep 22 17:20:24 CST 2017)(getParentId : 00254d3a9ab54a8199a2c147ec0648f2)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-22 17:20:24');
INSERT INTO `tb_log_info` VALUES ('13908f38219043998eff8bea57af955c', '1', 'superadmin', 'update', 'update[参数1，类型：String，值：(getBytes : [B@12f343d5)][参数2，类型：String，值：(getBytes : [B@68f0ba47)]', '2017-09-01 11:04:41');
INSERT INTO `tb_log_info` VALUES ('1442c4327caf4eab82ee212cb98fa0a5', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : a6111e60fbe2470babfecb459ca648c1)(getContent : 123)(getLikeCount : 123)(getStatus : com.webside.dict.model.DictEntity@1ef1043)(getUp : com.webside.up.model.UpEntity@768dd640)(getCreateUser : com.webside.user.model.UserEntity@6f45330f)(getTitle : 123)(getCreateTime : 1505187826254)(getViewCount : 132)]', '2017-09-12 11:43:46');
INSERT INTO `tb_log_info` VALUES ('190929a7fc294402849ebb3cca9a147b', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@5dc57149)(getStatus : com.webside.dict.model.DictEntity@1cb049af)(getTask : com.webside.task.model.TaskEntity@50fa99cd)(getUpdateTime : 1505471376300)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@3fd37765)]', '2017-09-15 18:29:36');
INSERT INTO `tb_log_info` VALUES ('191af5aad67346bcb307fdf2085e7eb5', '1', 'superadmin', 'insert', 'add[参数1，类型：UpSecondLevel，值：(getId : a4561eaf120e413aa37d136c7b98a1b4)(getContent : <ul style=\"list-style-type: none;\" class=\" list-paddingleft-2\"><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">ID</span>：Wh1t3zZ</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">战队</span>：皇族战队（3张）</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">参赛ID</span>：Royal丶Wh1t3zZ</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">国服ID</span>：柚柚妹、退役战神卢本伟、别抓我我想静静</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">韩服ID</span>：Ch1nA God55KaI、55kai YYTV、화이트55열다</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">比较常用的英雄</span>：菲兹、奥莉安娜、辛德拉、劫、卡利斯塔、亚索等</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">最擅长的位置</span>：中单</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">经常双排的小伙伴</span>：元宝君、娜美、萝莉控、蜘蛛侠、笑笑、淘宝权、Ssun、大表哥、第一最寂寞、七小银、 PDD、杀神风</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">国服成绩</span>：国服最强王者组</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">韩服成绩</span>：S4 韩服王者组</p></li></ul><p><br/></p>)(getSort : 2)(getStatus : 1)(getCreateTime : 1505962807617)(getTitleType : 2)(getUpId : 00001ce4d0114979bc9f7811e4ad4ffa)(getCreateId : 1)]', '2017-09-21 11:00:08');
INSERT INTO `tb_log_info` VALUES ('1cf16e3d16d1449bb7246e055b8ad388', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 新增人物事件UI)(getId : f41a5399b6754affa6ecb0c772d49035)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCtrl/addUI.html)(getSourceKey : task:addUI)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:11:11');
INSERT INTO `tb_log_info` VALUES ('1d5c4513db69446b8793253f91973525', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoAlbumEntity，值：(getName : 第二季)(getId : a353eef2b88143a5a71b869cb7908923)(getStatus : com.webside.dict.model.DictEntity@4629c706)(getHomeId : )(getCreateUser : com.webside.user.model.UserEntity@3490b907)(getCreateTime : 1503479094162)(getIntroduction : 测试)(getParentAlbum : com.webside.video.model.VideoAlbumEntity@ae38fea)(getHomeUrl : )]', '2017-08-23 17:04:54');
INSERT INTO `tb_log_info` VALUES ('1d89a9e63f0142028a49cd4ed71cdeca', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : c93abb92ac2b4c0bb34e1f72d89abf0d)(getContent : 22)(getTask : com.webside.task.model.TaskEntity@b90a366)(getStatus : com.webside.dict.model.DictEntity@5360ccf1)(getUpdateUser : com.webside.user.model.UserEntity@2defeefd)(getParentComment : com.webside.task.model.TaskCommentEntity@24a7fefd)(getUpdateTime : 1506326939615)(getLikeNum : 2)]', '2017-09-25 16:09:00');
INSERT INTO `tb_log_info` VALUES ('1f0d14c572b442ea9e752e6c38e99e32', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@220df906)(getTask : com.webside.task.model.TaskEntity@75e20257)(getStatus : com.webside.dict.model.DictEntity@174049be)]', '2017-09-26 15:17:02');
INSERT INTO `tb_log_info` VALUES ('1fba62c2946647a2a0e7fbd69e1966ce', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 修改属性值UI)(getId : be6073163807434da294f1d0d3157568)(getType : 1)(getDescription : )(getIcon : )(getSourceUrl : /taskValueCtrl/editUI.html)(getSourceKey : taskValue:editUI)(getChildren : [])(getCreateTime : Fri Sep 22 17:19:23 CST 2017)(getParentId : 00254d3a9ab54a8199a2c147ec0648f2)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-22 17:19:24');
INSERT INTO `tb_log_info` VALUES ('20d3719ef87f4f9d9aad9567d7821f6a', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 新增人物事件列表)(getId : 56a9bcf86edc4398bf8668d2dd6475c0)(getType : 0)(getDescription : )(getChildren : [])(getCreateTime : Mon Sep 11 15:04:50 CST 2017)(getSourceUrl : /taskCtrl/add.html)(getSourceKey : task:add)(getIcon : )(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:04:51');
INSERT INTO `tb_log_info` VALUES ('22994686a0fd4dabaffdd584eecaa993', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 新增评论)(getId : b58ec3c4ba5c471f8f2d6ea4ed0e9a0c)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/add.html)(getSourceKey : taskComment:add)(getParentId : 6fbbbe3bd93b42b6acd0be80a2f0371d)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-12 16:08:31');
INSERT INTO `tb_log_info` VALUES ('24d78e18232a4c25b79e861e29e3935f', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@72a76e52)(getStatus : com.webside.dict.model.DictEntity@eeedf5b)(getTask : com.webside.task.model.TaskEntity@78f90511)(getUpdateTime : 1505471377094)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@7d98469c)]', '2017-09-15 18:29:37');
INSERT INTO `tb_log_info` VALUES ('2517a1d1bdd64ad4a20bdc02ec601d7b', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@55ba451d)(getId : d2d112e989ab41a2b7ace77358c7fc0e)(getStatus : com.webside.dict.model.DictEntity@5ad4ff36)(getTask : com.webside.task.model.TaskEntity@4a80832d)(getCreateUser : com.webside.user.model.UserEntity@32f63931)(getCreateTime : 1506319928231)]', '2017-09-25 14:12:08');
INSERT INTO `tb_log_info` VALUES ('2597d5d4576b413a94598c6d8ac84b2f', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoCommentEntity，值：(getId : af07900ca6ea40e0ae4e3c1218b562ce)(getStatus : com.webside.dict.model.DictEntity@53670235)(getVideo : com.webside.video.model.VideoEntity@47969044)(getCreateUser : com.webside.user.model.UserEntity@63edb2a7)(getCreateTime : 1505201652508)(getStation : com.webside.dict.model.DictEntity@6c4aa31e)(getCommentUserId : 111)(getLikeNum : 0)(getCommentId : 1)(getCommentParent : com.webside.video.model.VideoCommentEntity@757d5f19)(getCommentContent : 123)(getCommentUserName : 宁)(getCommentCreatetime : 1505201580000)]', '2017-09-12 15:34:13');
INSERT INTO `tb_log_info` VALUES ('26b2183082b64c03acd318c0173baf9f', '1', 'superadmin', 'update', 'update[参数1，类型：String，值：(getBytes : [B@329776e3)][参数2，类型：String，值：(getBytes : [B@7c08d18e)]', '2017-08-29 10:46:57');
INSERT INTO `tb_log_info` VALUES ('26eca01599f647218aa4cbbcda2ced65', '1', 'superadmin', 'update', 'update[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@364674d6)(getId : f729561ee1d4402bb08db701d99fdf0e)(getStatus : com.webside.dict.model.DictEntity@36aa8684)(getTask : com.webside.task.model.TaskEntity@e185ab4)(getUpdateTime : 1506332349999)(getUpdateUser : com.webside.user.model.UserEntity@266b9ea3)]', '2017-09-25 17:39:10');
INSERT INTO `tb_log_info` VALUES ('2767f61f1ee14ccba2c54ae4bffb48d2', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:0000038beaaf472cb06572504b7f7db2)', '2017-09-22 16:17:27');
INSERT INTO `tb_log_info` VALUES ('282ff2cb586848a88b9a87a9023637ed', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:8ef22b70d8cf4d52ac8855066622c80a)', '2017-09-11 17:55:56');
INSERT INTO `tb_log_info` VALUES ('28e51b1a70274c2da28f6cc6d673157b', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : c93abb92ac2b4c0bb34e1f72d89abf0d)(getContent : 22)(getTask : com.webside.task.model.TaskEntity@3f0d0e92)(getStatus : com.webside.dict.model.DictEntity@22f46581)(getUpdateUser : com.webside.user.model.UserEntity@c5f16ab)(getParentComment : com.webside.task.model.TaskCommentEntity@1028d3b6)(getUpdateTime : 1506326956684)(getLikeNum : 2)]', '2017-09-25 16:09:17');
INSERT INTO `tb_log_info` VALUES ('28ea2d12dfd84ba493f14d96d95cdcaf', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 评论列表)(getId : cdf07f2b376046afa663ba26e9128dc6)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/list.html)(getSourceKey : taskComment:list)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getCreateTime : Tue Sep 12 16:01:45 CST 2017)(getIcon : )]', '2017-09-12 16:01:45');
INSERT INTO `tb_log_info` VALUES ('2a0ee746113c445b9bca732149a0542c', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoAlbumValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@3fcb3788)(getId : 29822de59c00436790d4aed6b3457a50)(getStatus : com.webside.dict.model.DictEntity@bf9bec9)(getVideoAlbum : com.webside.video.model.VideoAlbumEntity@4a251576)(getCreateUser : com.webside.user.model.UserEntity@3e304f8f)(getCreateTime : 1503909679887)]', '2017-08-28 16:41:20');
INSERT INTO `tb_log_info` VALUES ('2b5256a4574d4ed6ada16bc6ab92de52', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskCommentEntity，值：(getId : e659f1f0f15449ffa37957219442b816)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@56b48e04)(getStatus : com.webside.dict.model.DictEntity@3c9c1668)(getCreateUser : com.webside.user.model.UserEntity@6646ecc6)(getCreateTime : 1505979272760)(getLikeNum : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@48bb68be)]', '2017-09-21 15:34:33');
INSERT INTO `tb_log_info` VALUES ('2eaa35c278d540f89ca546769bb1d2d8', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 2a8b66f90f284a2c84b2a5da9e0edf3d)(getContent : <p>1234</p><p><span style=\"text-decoration: underline;\">asasddsa</span></p><p><span style=\"text-decoration: line-through; font-size: 24px;\"><strong>aaaa</strong></span></p>)(getStatus : com.webside.dict.model.DictEntity@54876353)(getUpdateUser : com.webside.user.model.UserEntity@742277ac)(getTitle : 123)(getUpdateTime : 1506408971211)(getViewCount : 1)(getUp : com.webside.up.model.UpEntity@3fa81102)(getLikeCount : 0)]', '2017-09-26 14:56:11');
INSERT INTO `tb_log_info` VALUES ('2f6d71183be84a91afd0799467931466', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 新增评论)(getId : b58ec3c4ba5c471f8f2d6ea4ed0e9a0c)(getType : 0)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/add.html)(getSourceKey : taskComment:add)(getParentId :  )(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getCreateTime : Tue Sep 12 15:51:47 CST 2017)(getIcon : )]', '2017-09-12 15:51:48');
INSERT INTO `tb_log_info` VALUES ('2fdcdc31894e44daafca39da7e568334', '1', 'superadmin', 'insert', 'add[参数1，类型：UpSecondLevel，值：(getId : a095115e358e4b3ca6fccc4ffd42f9d9)(getContent : <ul style=\"list-style-type: none;\" class=\" list-paddingleft-2\"><li><p>2011年TGA成都区冠军</p></li><li><p>2011年TGA总决赛冠军</p></li><li><p>2011年WCG中国区冠军</p></li><li><p>2013年S3全球总决赛中国区冠军</p></li><li><p>2013年S3全球总决赛亚军</p></li></ul><p><br/></p>)(getSort : 3)(getStatus : 1)(getCreateTime : 1505962817989)(getTitleType : 3)(getUpId : 00001ce4d0114979bc9f7811e4ad4ffa)(getCreateId : 1)]', '2017-09-21 11:00:18');
INSERT INTO `tb_log_info` VALUES ('30618703004f4357b33af7de621cfc6e', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 修改评论)(getId : 4ffdd16ce1544cbebb8188ee460eab7a)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/edit.html)(getSourceKey : taskComment:edit)(getParentId : 6fbbbe3bd93b42b6acd0be80a2f0371d)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-12 16:07:41');
INSERT INTO `tb_log_info` VALUES ('315208f55c694a40a3949664e1be2309', '1', 'superadmin', 'update', 'update[参数1，类型：String，值：(getBytes : [B@71cd8c9a)][参数2，类型：String，值：(getBytes : [B@2dd6acae)]', '2017-09-01 11:07:14');
INSERT INTO `tb_log_info` VALUES ('3221c0e863284d43a42ed24a5879b67c', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@31fcd88d)(getStatus : com.webside.dict.model.DictEntity@d2bde96)(getTask : com.webside.task.model.TaskEntity@3fc43c74)(getUpdateTime : 1505471372884)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@4652e282)]', '2017-09-15 18:29:33');
INSERT INTO `tb_log_info` VALUES ('324f715c477849b7ab1528bb2efc9f12', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@220df906)(getTask : com.webside.task.model.TaskEntity@1d545a12)(getStatus : com.webside.dict.model.DictEntity@402a6fca)]', '2017-09-26 15:16:41');
INSERT INTO `tb_log_info` VALUES ('32cd853df0d74c6783a2d48b6388c618', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 新增评论)(getId : b58ec3c4ba5c471f8f2d6ea4ed0e9a0c)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/add.html)(getSourceKey : taskComment:add)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-12 15:56:03');
INSERT INTO `tb_log_info` VALUES ('33398b73852b4b9f9836a862a4677a52', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:9e4605944c99479d83ed10c5134967dd)', '2017-09-13 17:03:47');
INSERT INTO `tb_log_info` VALUES ('34550c188eed491c890dac1933321663', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : 213213)(getLikeCount : 1)(getUp : com.webside.up.model.UpEntity@1c2d7d27)(getUpdateUser : com.webside.user.model.UserEntity@5b8cf25)(getTitle : 123213)(getUpdateTime : 1505189798753)(getViewCount : 12312)]', '2017-09-12 12:16:39');
INSERT INTO `tb_log_info` VALUES ('3753cb7315dd498180bb228a5bd36759', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@448d1a06)(getId : d8bc9c93d09a4c1984e2e40d044085fb)(getTask : com.webside.task.model.TaskEntity@7edfe760)(getStatus : com.webside.dict.model.DictEntity@1b73c536)(getCreateUser : com.webside.user.model.UserEntity@196b757b)(getCreateTime : 1506321581781)]', '2017-09-25 14:39:42');
INSERT INTO `tb_log_info` VALUES ('381df137575d402db75a9581e17df500', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : 213213)(getLikeCount : 1)(getUp : com.webside.up.model.UpEntity@360b440e)(getUpdateUser : com.webside.user.model.UserEntity@678b5417)(getTitle : 123213)(getUpdateTime : 1505189212010)(getViewCount : 12312)]', '2017-09-12 12:06:52');
INSERT INTO `tb_log_info` VALUES ('3873e08997144175a23e4a9cdfb8580e', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:4c0a0c0997884e67a3e22ea5347cbf30)', '2017-09-20 18:34:42');
INSERT INTO `tb_log_info` VALUES ('3ba19fcfc9b240f0a1f02f123e18a3d5', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:f103894e06eb49ca816731d497e535c7)', '2017-09-28 10:13:00');
INSERT INTO `tb_log_info` VALUES ('3cfe56a9b3764a83b93d577501a4165f', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 删除评论)(getId : e4eb75fce5a642e290e5a6d2dad0c4ec)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/delete.html)(getSourceKey : taskComment:del)(getParentId : 6fbbbe3bd93b42b6acd0be80a2f0371d)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-12 16:09:15');
INSERT INTO `tb_log_info` VALUES ('3d988402532a4c6eb408b3cd498ee19c', '1', 'superadmin', 'update', 'update[参数1，类型：DictEntity，值：(getLabel : bilibili)(getUpdateUser : com.webside.user.model.UserEntity@38ac0e92)(getUpdateTime : 1501837774016)(getLabelClass : label label-sm label-success arrowed arrowed-righ)(getSort : 2)(getDescription : icon-wf_Bzhan)(getStatus : com.webside.dict.model.DictEntity@13a8939d)(getValue : 2)(getId : e54385ccc0f64f9b8fb1570af83d86c2)]', '2017-08-04 17:09:34');
INSERT INTO `tb_log_info` VALUES ('40cdb1a4e0d84ba3bfa4d8609952a88a', '1', 'superadmin', 'update', 'update[参数1，类型：String，值：(getBytes : [B@8988d76)][参数2，类型：String，值：(getBytes : [B@6020615b)]', '2017-08-28 11:02:25');
INSERT INTO `tb_log_info` VALUES ('433b3194cd75447eace9d1fb5627d0dd', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 2a8b66f90f284a2c84b2a5da9e0edf3d)(getContent : <p>1234</p><p><img src=\"/upload/ueditor/20170926/1506408738906082561.jpg\" title=\"1506408738906082561.jpg\" alt=\"Lighthouse.jpg\"/></p>)(getStatus : com.webside.dict.model.DictEntity@36f227e5)(getUpdateUser : com.webside.user.model.UserEntity@108c9b47)(getTitle : 123)(getUpdateTime : 1506408741178)(getViewCount : 1)(getUp : com.webside.up.model.UpEntity@3877c2a0)(getLikeCount : 0)]', '2017-09-26 14:52:21');
INSERT INTO `tb_log_info` VALUES ('439ae877aa0343da831cf59421b0c418', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskCommentEntity，值：(getId : 1)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@541e5731)(getCreateUser : com.webside.user.model.UserEntity@43427b17)(getUpdateTime : 1)(getCreateTime : 1)(getStatus : com.webside.dict.model.DictEntity@469e3b9b)(getParentComment : com.webside.task.model.TaskCommentEntity@1f7e89e7)(getLikeNum : 1)]', '2017-09-15 17:57:44');
INSERT INTO `tb_log_info` VALUES ('44260dd6244d47eb8eeca16df39a5fa5', '1', 'superadmin', 'delete', 'deleteBatch[参数1，类型:String，值:(id:6084331554674e6aa2ef750615d76e0e)', '2017-09-12 15:55:46');
INSERT INTO `tb_log_info` VALUES ('447ae02fbfea457a82f0a230af373ef2', '1', 'superadmin', 'update', 'update[参数1，类型：DictEntity，值：(getLabel : AcFun)(getUpdateUser : com.webside.user.model.UserEntity@2c2d9419)(getUpdateTime : 1501837764640)(getLabelClass : label label-sm label-success arrowed arrowed-righ)(getSort : 1)(getDescription : icon-wf_Azhan)(getStatus : com.webside.dict.model.DictEntity@622667d6)(getValue : 1)(getId : 38a04ae146a141fbbc97b00e4add4d83)]', '2017-08-04 17:09:24');
INSERT INTO `tb_log_info` VALUES ('45e7d7f5f918446eb42fc0f8bfa916ac', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@14bb77a8)(getStatus : com.webside.dict.model.DictEntity@246423eb)(getTask : com.webside.task.model.TaskEntity@73dd8352)(getUpdateTime : 1505472163015)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@4c0b5b8a)]', '2017-09-15 18:42:43');
INSERT INTO `tb_log_info` VALUES ('46f7bbdd305049889241582c384a9c3d', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@5eb34b09)(getId : 344cdaf5fb624e11b82219c5a1e1501d)(getTask : com.webside.task.model.TaskEntity@2a7f5f97)(getStatus : com.webside.dict.model.DictEntity@e7bce3e)(getCreateUser : com.webside.user.model.UserEntity@2768dabb)(getCreateTime : 1506308272191)]', '2017-09-25 10:57:52');
INSERT INTO `tb_log_info` VALUES ('4735494de1f842e1ac5ba30f8a07bf54', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 新增属性值UI)(getId : ab328143ae1c4fefb856eca3380d3f2a)(getType : 1)(getDescription : )(getIcon : )(getSourceUrl : /taskValueCtrl/addUI.html)(getSourceKey : taskValue:addUI)(getChildren : [])(getCreateTime : Fri Sep 22 17:15:58 CST 2017)(getParentId : 00254d3a9ab54a8199a2c147ec0648f2)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-22 17:15:59');
INSERT INTO `tb_log_info` VALUES ('47c1ea5d86444e6c906859c4f5698d35', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 2)(getContent : 1)(getStatus : com.webside.dict.model.DictEntity@672671f4)(getTask : com.webside.task.model.TaskEntity@5e6b48c4)(getUpdateUser : com.webside.user.model.UserEntity@5223b2d7)(getUpdateTime : 1505814220254)(getLikeNum : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@7dc40ebf)]', '2017-09-19 17:43:40');
INSERT INTO `tb_log_info` VALUES ('483596a30c0340dc81c5247008ab5de4', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:7954b36396f54efb98206c7e3f8a58ca)', '2017-09-26 14:00:17');
INSERT INTO `tb_log_info` VALUES ('49e0fea56659446caf460e471d09e31d', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 属性值管理)(getId : 00254d3a9ab54a8199a2c147ec0648f2)(getType : 1)(getDescription : )(getSourceUrl : /taskValuetCtrl/listUI.html)(getSourceKey : task:value)(getChildren : [])(getParentId : bd27a654ce814223b45045b42dcfb6c4)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-22 17:22:19');
INSERT INTO `tb_log_info` VALUES ('4bf2ceb244f7477daca2d133dfc6d7e8', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskCommentEntity，值：(getId : c6cae77c08204c8f885a69671ecda3a2)(getContent : 11)(getTask : com.webside.task.model.TaskEntity@2fadb03b)(getStatus : com.webside.dict.model.DictEntity@1b0af1df)(getCreateUser : com.webside.user.model.UserEntity@6faddfbe)(getParentComment : com.webside.task.model.TaskCommentEntity@4c7f5ff6)(getCreateTime : 1506326670339)(getLikeNum : 0)]', '2017-09-25 16:04:30');
INSERT INTO `tb_log_info` VALUES ('4c18d9af7ed246f480e8b8fa87dfc27c', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@42700abe)(getStatus : com.webside.dict.model.DictEntity@2e3c4bd3)(getTask : com.webside.task.model.TaskEntity@3b096e19)(getUpdateTime : 1505471008927)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@392b1ff8)]', '2017-09-15 18:23:29');
INSERT INTO `tb_log_info` VALUES ('4c795da418d343489431d433551716f1', '1', 'superadmin', 'delete', 'deleteQuartzTrigger[参数1，类型:ScheduleJobEntity，值:(id:com.webside.quartz.model.ScheduleJobEntity@737e977b)', '2017-08-22 15:34:42');
INSERT INTO `tb_log_info` VALUES ('4e8f0dea4daa4879a3ce38436ef1ba72', '1', 'superadmin', 'update', 'update[参数1，类型：String，值：(getBytes : [B@50c5d67e)][参数2，类型：String，值：(getBytes : [B@23189b0)]', '2017-09-01 11:05:52');
INSERT INTO `tb_log_info` VALUES ('4ff38be0fd23454d9daca69b433a6512', '1', 'superadmin', 'update', 'update[参数1，类型：SeoConfigEntity，值：(getUpdateUser : com.webside.user.model.UserEntity@550f8d40)(getUpdateTime : 1501833581751)(getKeywords : 游戏,游戏视频,WoDotA,游戏搜索,游戏门户)(getTitle : 搜索游戏视频，就上WoDotA找一下，看看。)(getDescription : WoDotA是一个特别的游戏门户网站。这里可以搜索、预览各大游戏平台的游戏视频、统一进行评论、点评、回复弹幕直上首页。用WoDotA你也可以是游戏达人！)(getStatus : com.webside.dict.model.DictEntity@28ef2cd4)(getId : 656f44a794bd4a3d976c94644c0d2876)(getType : com.webside.dict.model.DictEntity@2f2234b0)]', '2017-08-04 15:59:41');
INSERT INTO `tb_log_info` VALUES ('5106ed2e7d6a440ab92bbc76b24261a8', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@6bfcd188)(getId : f729561ee1d4402bb08db701d99fdf0e)(getStatus : com.webside.dict.model.DictEntity@31b47295)(getTask : com.webside.task.model.TaskEntity@1e8e51bb)(getCreateTime : 1506332343150)(getCreateUser : com.webside.user.model.UserEntity@384ce995)]', '2017-09-25 17:39:03');
INSERT INTO `tb_log_info` VALUES ('528331a25a664ef3ad31ae18c1786136', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@1b4eab13)(getId : 6e6f838c590049f3a71de0a9a5c959b4)(getTask : com.webside.task.model.TaskEntity@60975397)(getStatus : com.webside.dict.model.DictEntity@4dfe1a0)(getCreateUser : com.webside.user.model.UserEntity@4cde01e1)(getCreateTime : 1506308248047)]', '2017-09-25 10:57:28');
INSERT INTO `tb_log_info` VALUES ('53f57e1d3ff34d4a84a0c57d0c8604f1', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoEntity，值：(getId : d33f2071dc8a4d4b87f71fa0a1b60bda)(getDuration : 1.0)(getStatus : com.webside.dict.model.DictEntity@77f9581f)(getCover : http://www.bilibili.com/video/av14223208/?zw)(getShortId : yNeEJnb6)(getScore : 1.0)(getAlbumIndex : 1)(getAlbum : com.webside.video.model.VideoAlbumEntity@48554ffe)(getCreateUser : com.webside.user.model.UserEntity@42272684)(getTitle : 1)(getCreateTime : 1505125231218)(getGame : com.webside.sys.model.GameEntity@a8ecf34)]', '2017-09-11 18:20:31');
INSERT INTO `tb_log_info` VALUES ('5427c02d4d314003b628474507d1a367', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : d9a73636316444139bbd8762aac0f252)(getContent : 1)(getStatus : com.webside.dict.model.DictEntity@581b1215)(getTask : com.webside.task.model.TaskEntity@ee477b9)(getParentComment : com.webside.task.model.TaskCommentEntity@578c0772)(getUpdateTime : 1506333447355)(getLikeNum : 12)(getUpdateUser : com.webside.user.model.UserEntity@2a96bfd0)]', '2017-09-25 17:57:27');
INSERT INTO `tb_log_info` VALUES ('55e10f0ea4bb47fea1ccc06c34a4df25', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 修改评论UI)(getId : 015af4f4b5f74aee9d913734c4dfa3d4)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/editUI.html)(getSourceKey : taskComment:editUI)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getCreateTime : Tue Sep 12 16:02:33 CST 2017)(getIcon : )]', '2017-09-12 16:02:33');
INSERT INTO `tb_log_info` VALUES ('5b12ecd339854a27a3db7b4efa04a7b5', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : 王企鹅)(getLikeCount : 1111)(getStatus : com.webside.dict.model.DictEntity@6358fec4)(getUp : com.webside.up.model.UpEntity@38cc6a9f)(getUpdateUser : com.webside.user.model.UserEntity@54c1c5a9)(getTitle : 请问)(getUpdateTime : 1505199924731)(getViewCount : 12312)]', '2017-09-12 15:05:25');
INSERT INTO `tb_log_info` VALUES ('5c79eaa7dcfe4b0ba5e02a62b9278417', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:c6cae77c08204c8f885a69671ecda3a2)', '2017-09-25 16:07:05');
INSERT INTO `tb_log_info` VALUES ('5d265c6e82b4493d89216cd9816afc6a', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 新增人物事件)(getId : 56a9bcf86edc4398bf8668d2dd6475c0)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCtrl/add.html)(getSourceKey : task:add)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:10:58');
INSERT INTO `tb_log_info` VALUES ('5d82a54496d742f1bce8d4e79328f736', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoRecommendEntity，值：(getVideo : com.webside.video.model.VideoEntity@5476a6d3)(getCreateTime : 1501148870642)(getCreateUser : com.webside.user.model.UserEntity@151b5ac7)(getUrl : )(getStartTime : 1501148820000)(getStatus : com.webside.dict.model.DictEntity@5c5eff2)(getId : 8aeb5f7cc036471880879f39d7b4c27f)]', '2017-07-27 17:47:50');
INSERT INTO `tb_log_info` VALUES ('5ef652a1584c4f85ab69690b8284f82d', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoAlbumEntity，值：(getName : 测试)(getId : 05169fa5514c48118d435287f406093d)(getStatus : com.webside.dict.model.DictEntity@31e3beb0)(getHomeId : )(getCreateUser : com.webside.user.model.UserEntity@46f61e70)(getCreateTime : 1503475586781)(getIntroduction : 测试测试)(getParentAlbum : com.webside.video.model.VideoAlbumEntity@6a18c5c4)(getHomeUrl : )]', '2017-08-23 16:06:27');
INSERT INTO `tb_log_info` VALUES ('6260e88910a94b05ab3b45af93bad73c', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoRecommendEntity，值：(getVideo : com.webside.video.model.VideoEntity@58226b04)(getCreateTime : 1501148884530)(getCreateUser : com.webside.user.model.UserEntity@69e14cd6)(getUrl : )(getStartTime : 1501148820000)(getStatus : com.webside.dict.model.DictEntity@17c7dcb)(getId : cf95893953514ae19e41fa2379a27d36)]', '2017-07-27 17:48:04');
INSERT INTO `tb_log_info` VALUES ('6264b7fce5f746399569f4190880856c', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoAlbumValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@73b992c3)(getId : 01b1847bf48948789fd40bf73401b091)(getStatus : com.webside.dict.model.DictEntity@be32f08)(getVideoAlbum : com.webside.video.model.VideoAlbumEntity@737145c)(getCreateUser : com.webside.user.model.UserEntity@b4cda4b)(getCreateTime : 1503909690650)]', '2017-08-28 16:41:31');
INSERT INTO `tb_log_info` VALUES ('6296cbe496f34786a86557672ecc108d', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:af07900ca6ea40e0ae4e3c1218b562ce)', '2017-09-12 15:35:21');
INSERT INTO `tb_log_info` VALUES ('63a13697b28d48fd8918bbe282dcb489', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 2a8b66f90f284a2c84b2a5da9e0edf3d)(getContent : <p>1234</p><p><span style=\"text-decoration: underline;\">asasddsa</span></p><p><strong>aaaa</strong></p>)(getStatus : com.webside.dict.model.DictEntity@5cfef66f)(getUpdateUser : com.webside.user.model.UserEntity@1d00e3e6)(getTitle : 123)(getUpdateTime : 1506408953875)(getViewCount : 1)(getUp : com.webside.up.model.UpEntity@71adbb81)(getLikeCount : 0)]', '2017-09-26 14:55:54');
INSERT INTO `tb_log_info` VALUES ('63ffb40d65114993978070c0e8d3c840', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : 2fbe7e89793140fdba13abda0f21a030)(getContent : 白小白的日常)(getLikeCount : 50000)(getStatus : com.webside.dict.model.DictEntity@53f0c04e)(getUp : com.webside.up.model.UpEntity@52407bd)(getCreateUser : com.webside.user.model.UserEntity@3658a53)(getTitle : 白小白)(getCreateTime : 1505293347504)(getViewCount : 100000)]', '2017-09-13 17:02:28');
INSERT INTO `tb_log_info` VALUES ('641c71076478444fa881c941ca849bae', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 1,1)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@69940a48)(getCreateUser : com.webside.user.model.UserEntity@6037776d)(getUpdateUser : com.webside.user.model.UserEntity@7dfd5f5e)(getUpdateTime : 222)(getCreateTime : 1)(getStatus : com.webside.dict.model.DictEntity@79e6f038)(getParentComment : com.webside.task.model.TaskCommentEntity@1b3c270a)(getLikeNum : 1)]', '2017-09-15 17:57:57');
INSERT INTO `tb_log_info` VALUES ('68053caa73244bc8a855233d2ad4d59f', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : 王企鹅)(getLikeCount : 1111)(getStatus : com.webside.dict.model.DictEntity@23987efa)(getUp : com.webside.up.model.UpEntity@32c3bc6)(getUpdateUser : com.webside.user.model.UserEntity@2be8f8f2)(getTitle : 请问)(getUpdateTime : 1505293282408)(getViewCount : 12312)]', '2017-09-13 17:01:22');
INSERT INTO `tb_log_info` VALUES ('6856e3d32c1540c8b7d4684528fe2037', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 2,2)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@192677ce)(getCreateUser : com.webside.user.model.UserEntity@6460aa4c)(getUpdateUser : com.webside.user.model.UserEntity@53186a80)(getUpdateTime : 1500276402243)(getCreateTime : 1500276402243)(getStatus : com.webside.dict.model.DictEntity@77d4bdc5)(getParentComment : com.webside.task.model.TaskCommentEntity@14080232)(getLikeNum : 1)]', '2017-09-15 17:58:20');
INSERT INTO `tb_log_info` VALUES ('6896f1d3d4fc46a7b1643211445123cd', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:1)', '2017-09-14 17:08:17');
INSERT INTO `tb_log_info` VALUES ('69a6f7c0c17f4f1c9b31ee042c4ab297', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : 85e15c33042e4e7dba000c0470c90a7e)(getContent : <p>sdfsdfsfd</p>)(getStatus : com.webside.dict.model.DictEntity@78ffa2d2)(getUp : com.webside.up.model.UpEntity@1efece0c)(getCreateUser : com.webside.user.model.UserEntity@5ad10ab)(getTitle : 4556fdf)(getCreateTime : 1506592677543)(getViewCount : 0)(getLikeCount : 0)]', '2017-09-28 17:57:58');
INSERT INTO `tb_log_info` VALUES ('6ca6ba9d0fb842c68735d12aa233a745', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 人物事件列表)(getId : 4eac4f474f5e4382bdd06dcf4ccf815b)(getType : 1)(getDescription : )(getChildren : [])(getCreateTime : Mon Sep 11 15:02:15 CST 2017)(getSourceUrl : /taskCtrl/list.html)(getSourceKey : task:list)(getIcon : )(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:02:16');
INSERT INTO `tb_log_info` VALUES ('6fca51ea14414de38c9dff742608b6af', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@33c3fc6)(getId : d4c529e6d7cc419db6cc3e6e05ad9e37)(getTask : com.webside.task.model.TaskEntity@3a165f3e)(getStatus : com.webside.dict.model.DictEntity@48b30105)(getCreateUser : com.webside.user.model.UserEntity@5f1ab1da)(getCreateTime : 1506327088424)]', '2017-09-25 16:11:28');
INSERT INTO `tb_log_info` VALUES ('703676e133db456ea5a1fa6a6ecd4a44', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoEntity，值：(getId : 8ef22b70d8cf4d52ac8855066622c80a)(getDuration : 1.0)(getStatus : com.webside.dict.model.DictEntity@311b9da0)(getCover : http://www.bilibili.com/video/av14223208/?zw)(getShortId : MYfiri7Q)(getScore : 6.9)(getAlbumIndex : 1)(getAlbum : com.webside.video.model.VideoAlbumEntity@77a5a232)(getCreateUser : com.webside.user.model.UserEntity@2df34d74)(getTitle : 1)(getCreateTime : 1505123676669)(getGame : com.webside.sys.model.GameEntity@3261d6b4)]', '2017-09-11 17:54:37');
INSERT INTO `tb_log_info` VALUES ('705d63b85cb54648a4cafebd8e8ef48a', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 视频标签举报管理)(getId : 3cf54176dd154e97b9f3adc814aac7bd)(getType : 0)(getDescription : )(getIcon : )(getChildren : [])(getCreateTime : Tue Aug 22 14:51:11 CST 2017)(getParentId : f2fc7fa7af13499ab78926ebda8f5aca)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getSourceUrl : /videoValueInform/listUI.html)(getSourceKey : videoValueInform)]', '2017-08-22 14:51:12');
INSERT INTO `tb_log_info` VALUES ('7196b3bba8e54c1baa2d89a5212f27fb', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : c93abb92ac2b4c0bb34e1f72d89abf0d)(getContent : 22)(getTask : com.webside.task.model.TaskEntity@17c6c041)(getStatus : com.webside.dict.model.DictEntity@1f7deb23)(getUpdateUser : com.webside.user.model.UserEntity@1ae9013e)(getParentComment : com.webside.task.model.TaskCommentEntity@2db64406)(getUpdateTime : 1506326942921)(getLikeNum : 2)]', '2017-09-25 16:09:03');
INSERT INTO `tb_log_info` VALUES ('722e64dd73c543a3b1505e5883804aea', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 2a8b66f90f284a2c84b2a5da9e0edf3d)(getContent : <p>1234</p><p><span style=\"text-decoration: underline;\">asasddsa</span></p><p><span style=\"text-decoration: line-through; font-size: 24px;\"><strong>aaaa</strong></span></p><p><span style=\"text-decoration: line-through; font-size: 24px;\"><strong><img src=\"/upload/ueditor/20170929/1506668140631085330.jpg\" title=\"1506668140631085330.jpg\" alt=\"1.jpg\"/></strong></span></p>)(getStatus : com.webside.dict.model.DictEntity@596aa3a)(getUp : com.webside.up.model.UpEntity@31ff0e38)(getUpdateUser : com.webside.user.model.UserEntity@3886956c)(getTitle : 123)(getUpdateTime : 1506668162214)(getViewCount : 99)(getLikeCount : 7)]', '2017-09-29 14:56:02');
INSERT INTO `tb_log_info` VALUES ('7339eb0fd25d4661b31ce0611470e747', '1', 'superadmin', 'update', 'update[参数1，类型：VideoAlbumEntity，值：(getName : 第一季)(getId : 05169fa5514c48118d435287f406093d)(getStatus : com.webside.dict.model.DictEntity@3946148)(getHomeId : )(getUpdateUser : com.webside.user.model.UserEntity@18164235)(getUpdateTime : 1503479046433)(getIntroduction : )(getParentAlbum : com.webside.video.model.VideoAlbumEntity@45220e66)(getHomeUrl : )]', '2017-08-23 17:04:06');
INSERT INTO `tb_log_info` VALUES ('73721a3e57ff47dba164d4e3da4ea9c8', '1', 'superadmin', 'update', 'update[参数1，类型：VideoAlbumEntity，值：(getName : 阿萨德)(getId : ea344991dacb49148e30c77f12c13914)(getStatus : com.webside.dict.model.DictEntity@306e103c)(getHomeUrl : )(getHomeId : )(getUpdateUser : com.webside.user.model.UserEntity@7d5bb282)(getUpdateTime : 1504599426471)(getIntroduction : )(getCover : )(getAuthor : )(getUpdateRemarks : )(getParentAlbum : com.webside.video.model.VideoAlbumEntity@7efc2d54)]', '2017-09-05 16:17:06');
INSERT INTO `tb_log_info` VALUES ('73e05a8cf854462792b2fdb6fdf69950', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : 471160dde5394a748f498ff1f1f52476)(getContent : 1)(getStatus : com.webside.dict.model.DictEntity@477933b7)(getLikeCount : 1)(getViewCount : 1)(getUp : com.webside.up.model.UpEntity@74d484a2)(getCreateUser : com.webside.user.model.UserEntity@7739bb71)(getTitle : 1)(getCreateTime : 1505903675347)]', '2017-09-20 18:34:35');
INSERT INTO `tb_log_info` VALUES ('74ae14958d6a4cbcac9dfb760da0f375', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : 王企鹅)(getLikeCount : 1111)(getUp : com.webside.up.model.UpEntity@3cb44182)(getUpdateUser : com.webside.user.model.UserEntity@759aba76)(getTitle : 请问)(getUpdateTime : 1505199823306)(getViewCount : 12312)]', '2017-09-12 15:03:43');
INSERT INTO `tb_log_info` VALUES ('75336c04258f4fb39ac8a98794ebf306', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 人物事件管理)(getId : 828f3f0130314d0598ab92c185cd804a)(getType : 0)(getDescription : )(getChildren : [])(getCreateTime : Mon Sep 11 15:00:27 CST 2017)(getSourceUrl : /taskCtrl/listUI.html)(getSourceKey : task:task)(getIcon : )(getParentId : bd27a654ce814223b45045b42dcfb6c4)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:00:28');
INSERT INTO `tb_log_info` VALUES ('756e142e1bde4e16b8d77274a2b8b273', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@2d73b0bf)(getStatus : com.webside.dict.model.DictEntity@6b6defb0)(getTask : com.webside.task.model.TaskEntity@53cc3963)(getUpdateTime : 1505471376491)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@1df75218)]', '2017-09-15 18:29:37');
INSERT INTO `tb_log_info` VALUES ('77079b65682741feb51a8510531f54ed', '1', 'superadmin', 'update', 'update[参数1，类型：VideoAlbumEntity，值：(getName : 第一季)(getId : 05169fa5514c48118d435287f406093d)(getStatus : com.webside.dict.model.DictEntity@733e97e6)(getHomeId : )(getUpdateUser : com.webside.user.model.UserEntity@4070c882)(getUpdateTime : 1503479067228)(getIntroduction : 测试)(getParentAlbum : com.webside.video.model.VideoAlbumEntity@3a3ad0db)(getHomeUrl : )]', '2017-08-23 17:04:27');
INSERT INTO `tb_log_info` VALUES ('7821c2d4f48d4b13b4c5d4708172876f', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@342f63e2)(getId : 4c00e247e9f54f1691667ebcc57fa90b)(getTask : com.webside.task.model.TaskEntity@1c8a8768)(getStatus : com.webside.dict.model.DictEntity@142ec505)(getCreateUser : com.webside.user.model.UserEntity@2b299b5d)(getCreateTime : 1506308235857)]', '2017-09-25 10:57:16');
INSERT INTO `tb_log_info` VALUES ('78bb5698d82f4e99b762789afae33b54', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 2,2)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@273c5d64)(getCreateUser : com.webside.user.model.UserEntity@4874fd81)(getUpdateUser : com.webside.user.model.UserEntity@7e7b1b90)(getUpdateTime : 1)(getCreateTime : 1500276402243)(getStatus : com.webside.dict.model.DictEntity@4a7438aa)(getParentComment : com.webside.task.model.TaskCommentEntity@5b61c0c2)(getLikeNum : 1)]', '2017-09-15 17:58:24');
INSERT INTO `tb_log_info` VALUES ('7dfcea79e3464573937b36d39403ac29', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : 213213)(getLikeCount : 1)(getUp : com.webside.up.model.UpEntity@2c816f45)(getUpdateUser : com.webside.user.model.UserEntity@552a18c1)(getTitle : 123213)(getUpdateTime : 1505189220938)(getViewCount : 12312)]', '2017-09-12 12:07:01');
INSERT INTO `tb_log_info` VALUES ('7ed92c3232e04bebaac257ba43ba1d53', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 2)(getContent : 1)(getStatus : com.webside.dict.model.DictEntity@3603b584)(getParentComment : com.webside.task.model.TaskCommentEntity@6d334b53)(getUpdateUser : com.webside.user.model.UserEntity@35de1ca2)(getUpdateTime : 1505903658604)(getLikeNum : 1)]', '2017-09-20 18:34:19');
INSERT INTO `tb_log_info` VALUES ('7f67cf1a6fe1450cb2cba5dba93d54aa', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:3)', '2017-09-19 17:43:46');
INSERT INTO `tb_log_info` VALUES ('7fa5aaad2075470e92704cd159407009', '1', 'superadmin', 'insert', 'add[参数1，类型：UpValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@3b4a110e)(getId : 0207a8f99d0e4d97afb6f299ab9ad123)(getStatus : com.webside.dict.model.DictEntity@1a9c2e99)(getCreateUser : com.webside.user.model.UserEntity@6459747e)(getCreateTime : 1506409830619)(getUp : com.webside.up.model.UpEntity@7b2cce8e)]', '2017-09-26 15:10:31');
INSERT INTO `tb_log_info` VALUES ('800601676e9f4287b37811b51e6c8386', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:a6111e60fbe2470babfecb459ca648c1)', '2017-09-12 15:05:47');
INSERT INTO `tb_log_info` VALUES ('801fa357d81946f08c77c27a5dbe1af6', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoRecommendEntity，值：(getCreateTime : 1501665080995)(getVideo : com.webside.video.model.VideoEntity@4befc246)(getCreateUser : com.webside.user.model.UserEntity@3506f96a)(getUrl : )(getEndTime : 1501751460000)(getStartTime : 1501578660000)(getStatus : com.webside.dict.model.DictEntity@32a9ea20)(getId : 2a4b74a509004d3d942c8017fd05b622)]', '2017-08-02 17:11:21');
INSERT INTO `tb_log_info` VALUES ('826663308c774288b2324717b32861e4', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 4c0a0c0997884e67a3e22ea5347cbf30)(getContent : 12)(getStatus : com.webside.dict.model.DictEntity@8fce69d)(getLikeCount : 12)(getUp : com.webside.up.model.UpEntity@43c1804c)(getUpdateUser : com.webside.user.model.UserEntity@51b3c825)(getTitle : 1)(getUpdateTime : 1505381701706)(getViewCount : 12)]', '2017-09-14 17:35:02');
INSERT INTO `tb_log_info` VALUES ('830d61e5acfd43908022e0a88e8f2290', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@6077cfab)(getId : a7a1a1c950b24239ab6e13246ffb43f2)(getTask : com.webside.task.model.TaskEntity@419e5dee)(getStatus : com.webside.dict.model.DictEntity@6fc57ff0)(getCreateUser : com.webside.user.model.UserEntity@f23ebf)(getCreateTime : 1506308256221)]', '2017-09-25 10:57:36');
INSERT INTO `tb_log_info` VALUES ('854ee1d7dab04f32844b66406a6842d1', '1', 'superadmin', 'insert', 'add[参数1，类型：GameEntity，值：(getName : 111)(getId : 53a640d740bd4f11af43a7d39ee34425)(getStatus : com.webside.dict.model.DictEntity@7f118724)(getCreateUser : com.webside.user.model.UserEntity@75de882b)(getCreateTime : 1505115759313)]', '2017-09-11 15:42:39');
INSERT INTO `tb_log_info` VALUES ('8574dffb11c74913b398ca601bd917c1', '1', 'superadmin', 'update', 'update[参数1，类型：VideoAlbumEntity，值：(getName : 测试)(getId : 05169fa5514c48118d435287f406093d)(getStatus : com.webside.dict.model.DictEntity@1bdbc5f3)(getHomeId : )(getUpdateUser : com.webside.user.model.UserEntity@1cc63cb0)(getUpdateTime : 1503479033832)(getIntroduction : 测试测试)(getParentAlbum : com.webside.video.model.VideoAlbumEntity@439541a9)(getHomeUrl : )]', '2017-08-23 17:03:54');
INSERT INTO `tb_log_info` VALUES ('8670cc3b01434515bb48837d02d93e0d', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 新增评论UI)(getId : 8359efcf52da48dd8d3b92547681b1dd)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/addUI.html)(getSourceKey : taskComment:addUI)(getParentId : 6fbbbe3bd93b42b6acd0be80a2f0371d)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-12 16:08:05');
INSERT INTO `tb_log_info` VALUES ('88f59c1addd04d0e88e3eb1f109b3335', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoAlbumValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@44a8e857)(getId : 971218addac54968b85fede90cd2097b)(getStatus : com.webside.dict.model.DictEntity@39bc0d78)(getCreateUser : com.webside.user.model.UserEntity@41abd417)(getCreateTime : 1506564822496)(getVideoAlbum : com.webside.video.model.VideoAlbumEntity@19638efe)]', '2017-09-28 10:13:43');
INSERT INTO `tb_log_info` VALUES ('8995c1c351474eec8a2bbcfb268595ae', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 2a8b66f90f284a2c84b2a5da9e0edf3d)(getContent : <p>1234</p><p>asasddsa</p><p><strong>aaaa</strong></p>)(getStatus : com.webside.dict.model.DictEntity@21d05c6a)(getUpdateUser : com.webside.user.model.UserEntity@3a7641f7)(getTitle : 123)(getUpdateTime : 1506408930114)(getViewCount : 1)(getUp : com.webside.up.model.UpEntity@714425a5)(getLikeCount : 0)]', '2017-09-26 14:55:30');
INSERT INTO `tb_log_info` VALUES ('8a67e0126f564ec7a1f980b0e9a90927', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@6744d53f)(getStatus : com.webside.dict.model.DictEntity@2b53bbfa)(getTask : com.webside.task.model.TaskEntity@474a7d0b)(getUpdateTime : 1505472064571)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@9b91ff2)]', '2017-09-15 18:41:05');
INSERT INTO `tb_log_info` VALUES ('8a82758d6fea4860b1e99630bff64fe5', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 2,2)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@459db1cf)(getCreateUser : com.webside.user.model.UserEntity@76238bf)(getUpdateUser : com.webside.user.model.UserEntity@4cb2c9ef)(getUpdateTime : 1)(getCreateTime : 1500276402243)(getStatus : com.webside.dict.model.DictEntity@49e99ae2)(getParentComment : com.webside.task.model.TaskCommentEntity@5fcfac74)(getLikeNum : 1)]', '2017-09-15 17:58:24');
INSERT INTO `tb_log_info` VALUES ('8f2ca38714214e2a8aa1538a754932f8', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : c93abb92ac2b4c0bb34e1f72d89abf0d)(getContent : 22)(getTask : com.webside.task.model.TaskEntity@9029412)(getStatus : com.webside.dict.model.DictEntity@7f7ddcc6)(getUpdateUser : com.webside.user.model.UserEntity@76054869)(getParentComment : com.webside.task.model.TaskCommentEntity@50a809e7)(getUpdateTime : 1506327996970)(getLikeNum : 2)]', '2017-09-25 16:26:37');
INSERT INTO `tb_log_info` VALUES ('917ec7ae07f640e497e5874c6d427472', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 1,2)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@3de5d636)(getCreateUser : com.webside.user.model.UserEntity@7d5c60c0)(getUpdateUser : com.webside.user.model.UserEntity@4f353f0f)(getUpdateTime : 1)(getCreateTime : 1)(getStatus : com.webside.dict.model.DictEntity@1f4213c2)(getParentComment : com.webside.task.model.TaskCommentEntity@c40cc8a)(getLikeNum : 1)]', '2017-09-15 17:58:11');
INSERT INTO `tb_log_info` VALUES ('92751805008a4788a9d303fb986ff4ee', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:918c67549ec34fa986386aac9ee3ea7e)', '2017-09-20 18:34:44');
INSERT INTO `tb_log_info` VALUES ('9301ced8c3c7494f9b3850af16df30d8', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : 2a8b66f90f284a2c84b2a5da9e0edf3d)(getContent : <p>123</p>)(getStatus : com.webside.dict.model.DictEntity@4d24bbf0)(getUp : com.webside.up.model.UpEntity@2c0aae2)(getCreateUser : com.webside.user.model.UserEntity@553cc61f)(getLikeCount : 0)(getTitle : 123)(getCreateTime : 1506326393609)(getViewCount : 0)]', '2017-09-25 15:59:54');
INSERT INTO `tb_log_info` VALUES ('94a8743cefe64c0084e495e4baa43d93', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 评论管理)(getId : 6fbbbe3bd93b42b6acd0be80a2f0371d)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/listUI.html)(getSourceKey : task:comment)(getParentId : bd27a654ce814223b45045b42dcfb6c4)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getCreateTime : Tue Sep 12 16:06:15 CST 2017)(getIcon : )]', '2017-09-12 16:06:16');
INSERT INTO `tb_log_info` VALUES ('951a0411b42b441b936b253707bd2709', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@5384e51b)(getId : 7954b36396f54efb98206c7e3f8a58ca)(getTask : com.webside.task.model.TaskEntity@2db438d1)(getStatus : com.webside.dict.model.DictEntity@54515f52)(getCreateUser : com.webside.user.model.UserEntity@263d349c)(getCreateTime : 1506405610304)]', '2017-09-26 14:00:10');
INSERT INTO `tb_log_info` VALUES ('98398d64aef54587818c61edc5be9e72', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:1)', '2017-09-18 15:53:10');
INSERT INTO `tb_log_info` VALUES ('9a2b067b5629407eb7a04260237f4197', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskCommentEntity，值：(getId : 98f732214b6c49f9a9c3a08fc238c226)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@72dc175a)(getStatus : com.webside.dict.model.DictEntity@6c7b1f36)(getCreateUser : com.webside.user.model.UserEntity@feacd2b)(getCreateTime : 1505979288453)(getLikeNum : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@67d0906e)]', '2017-09-21 15:34:48');
INSERT INTO `tb_log_info` VALUES ('9caa51f9c5374c508aae05ea0c5e0116', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 新增人物事件)(getId : 56a9bcf86edc4398bf8668d2dd6475c0)(getType : 0)(getDescription : )(getChildren : [])(getSourceUrl : /taskCtrl/add.html)(getSourceKey : task:add)(getIcon : )(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:05:19');
INSERT INTO `tb_log_info` VALUES ('9cb7dbbc2e324e09b4d5d4c6f1fcede3', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 1)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@38e82256)(getStatus : com.webside.dict.model.DictEntity@4d4cbff9)(getParentComment : com.webside.task.model.TaskCommentEntity@4f35f128)(getLikeNum : 1)(getUpdateUser : com.webside.user.model.UserEntity@629e2001)(getUpdateTime : 1505472593870)]', '2017-09-15 18:49:54');
INSERT INTO `tb_log_info` VALUES ('9d5445a664c44ec8b51894f9a2c68ff3', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 评论列表)(getId : cdf07f2b376046afa663ba26e9128dc6)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/list.html)(getSourceKey : taskComment:list)(getParentId : 6fbbbe3bd93b42b6acd0be80a2f0371d)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-12 16:08:52');
INSERT INTO `tb_log_info` VALUES ('9d995b72b23940219cd5ce06056a21f3', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@4c3a5d9c)(getId : 68d19a5e9e7c42889c2fe09fb8c14152)(getStatus : com.webside.dict.model.DictEntity@5f0bbff)(getTask : com.webside.task.model.TaskEntity@5616f90e)(getCreateUser : com.webside.user.model.UserEntity@319c62e8)(getCreateTime : 1506320138807)]', '2017-09-25 14:15:39');
INSERT INTO `tb_log_info` VALUES ('9dac5445b2b746eea44093582104f6bf', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskCommentEntity，值：(getId : b190019881fd46c591d925ad3317ae40)(getContent : 白小白的日常)(getParentComment : com.webside.task.model.TaskCommentEntity@29f0f00d)(getStatus : com.webside.dict.model.DictEntity@3d912539)(getTask : com.webside.task.model.TaskEntity@10af4af4)(getCreateUser : com.webside.user.model.UserEntity@2f6fcd08)(getCreateTime : 1506311007853)(getLikeNum : 1)]', '2017-09-25 11:43:28');
INSERT INTO `tb_log_info` VALUES ('9ecdb702dd2e4dd1bc73c9e554d18ac6', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@6c17397b)(getId : d2672e24e8cf4aadb1bc6fd8f71d0b89)(getTask : com.webside.task.model.TaskEntity@72eec189)(getStatus : com.webside.dict.model.DictEntity@75f8dbb)(getCreateUser : com.webside.user.model.UserEntity@6a94df55)(getCreateTime : 1506308261320)]', '2017-09-25 10:57:41');
INSERT INTO `tb_log_info` VALUES ('9ef3607cfa364b4395fd9369b2ca885a', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 修改评论)(getId : 4ffdd16ce1544cbebb8188ee460eab7a)(getType : 0)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/edit.html)(getSourceKey : taskComment:edit)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getIcon : )]', '2017-09-12 15:52:33');
INSERT INTO `tb_log_info` VALUES ('9fffbaa7318a4432900cf1b15c289b70', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 人物事件管理)(getId : bd27a654ce814223b45045b42dcfb6c4)(getType : 0)(getDescription : )(getChildren : [])(getCreateTime : Mon Sep 11 14:59:10 CST 2017)(getSourceUrl : )(getSourceKey : task)(getIcon : fa  fa-certificate)(getParentId :  )(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 14:59:10');
INSERT INTO `tb_log_info` VALUES ('a31bb77a1eb4430f927ef75879c774e5', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@475b966)(getStatus : com.webside.dict.model.DictEntity@51addbaf)(getTask : com.webside.task.model.TaskEntity@79833911)(getUpdateTime : 1505471161030)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@5acabdc)]', '2017-09-15 18:26:01');
INSERT INTO `tb_log_info` VALUES ('a3ec1f7c9bf14f4b924392e3f37339e6', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 修改评论)(getId : 4ffdd16ce1544cbebb8188ee460eab7a)(getType : 0)(getDescription : )(getChildren : [])(getSourceUrl : /videoCommentCtrl/edit.html)(getSourceKey : videoComment:edit)(getParentId : bd27a654ce814223b45045b42dcfb6c4)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getCreateTime : Tue Sep 12 15:51:09 CST 2017)(getIcon : )]', '2017-09-12 15:51:09');
INSERT INTO `tb_log_info` VALUES ('a3f710b3fd8d4c86ab1ebf7c62bf7976', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 1,1)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@222d16cc)(getCreateUser : com.webside.user.model.UserEntity@79f2891d)(getUpdateUser : com.webside.user.model.UserEntity@58e36836)(getUpdateTime : 1)(getCreateTime : 1)(getStatus : com.webside.dict.model.DictEntity@216006eb)(getParentComment : com.webside.task.model.TaskCommentEntity@2bf2e8)(getLikeNum : 1)]', '2017-09-15 17:58:02');
INSERT INTO `tb_log_info` VALUES ('a508377024924f8ea8ffb5584bf9f403', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : c93abb92ac2b4c0bb34e1f72d89abf0d)(getContent : 22)(getStatus : com.webside.dict.model.DictEntity@9971333)(getTask : com.webside.task.model.TaskEntity@3436198a)(getParentComment : com.webside.task.model.TaskCommentEntity@5f4ba5bb)(getUpdateTime : 1506333809661)(getLikeNum : 2)(getUpdateUser : com.webside.user.model.UserEntity@34fb0a36)]', '2017-09-25 18:03:30');
INSERT INTO `tb_log_info` VALUES ('a871fa0f4c0f48eeba31b8b6d55d4444', '1', 'superadmin', 'update', 'update[参数1，类型：VideoAlbumEntity，值：(getName : 测试)(getId : 96923c9e40ca45eaa733ab5ce980db9f)(getStatus : com.webside.dict.model.DictEntity@26eb03d0)(getUpdateTime : 1503901265567)(getIntroduction : 测试测试)(getParentAlbum : com.webside.video.model.VideoAlbumEntity@54bb417e)(getAuthor : 123,每周四晚20:30更新两集)(getHomeUrl : )(getHomeId : )(getUpdateUser : com.webside.user.model.UserEntity@6ba389da)]', '2017-08-28 14:21:06');
INSERT INTO `tb_log_info` VALUES ('a978d326443243979e1d13f39de3ee5c', '1', 'superadmin', 'update', 'update[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@16de8899)(getId : b7c0eb556cdb4da1919a475f327eb3d4)(getStatus : com.webside.dict.model.DictEntity@5ce86058)(getTask : com.webside.task.model.TaskEntity@7be0a609)(getUpdateTime : 1506336841310)(getUpdateUser : com.webside.user.model.UserEntity@399375d5)]', '2017-09-25 18:54:01');
INSERT INTO `tb_log_info` VALUES ('ab1c2bad1de14a3983c098768bf7502e', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 新增评论)(getId : 6084331554674e6aa2ef750615d76e0e)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/add.html)(getSourceKey : taskComment:add)(getParentId : 4eac4f474f5e4382bdd06dcf4ccf815b)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getCreateTime : Tue Sep 12 15:55:24 CST 2017)(getIcon : )]', '2017-09-12 15:55:24');
INSERT INTO `tb_log_info` VALUES ('ab310f36c1614a8d86dea8ffaa921e3e', '1', 'superadmin', 'insert', 'add[参数1，类型：UpSecondLevel，值：(getId : d33861dba50a4923bb985ee8247e4e62)(getContent : <ul style=\"list-style-type: none;\" class=\" list-paddingleft-2\"><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">曾效力战队</span>：IG、SH皇族</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">血型</span>：O型</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">星座</span>：狮子座</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">身高</span>：178cm</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">口头禅</span>：可以，兄弟、芽儿、55开吧，我也经常单杀他的、这波不亏、哎呀我好烦呐、老婆关下门，有点冷啊。。好冷、我只买20层的杀人书、肉松饼、我的天、有点贱啊、哇，有点伤啊、我的车、好卡啊、不会输还能打、好的各位观众，打了一把如此精髓的.......</p></li><li><p><span style=\"box-sizing: border-box; -webkit-tap-highlight-color: transparent; font-weight: 700; margin: 0px; padding: 0px; border: 0px; outline: 0px; font-stretch: normal; line-height: 1.5; color: rgb(65, 65, 65);\">妻子</span>：UU</p></li></ul>)(getSort : 1)(getStatus : 1)(getCreateTime : 1505962787405)(getTitleType : 1)(getUpId : 00001ce4d0114979bc9f7811e4ad4ffa)(getCreateId : 1)]', '2017-09-21 10:59:47');
INSERT INTO `tb_log_info` VALUES ('aba0d0a1d46c4492b636f23fee99b81e', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:971218addac54968b85fede90cd2097b)', '2017-09-28 10:14:03');
INSERT INTO `tb_log_info` VALUES ('ac41857275c04644a0dade08df8759eb', '1', 'superadmin', 'update', 'update[参数1，类型：VideoAlbumEntity，值：(getName : 测试)(getId : 96923c9e40ca45eaa733ab5ce980db9f)(getStatus : com.webside.dict.model.DictEntity@a4790ae)(getUpdateTime : 1503901296275)(getIntroduction : 测试测试)(getParentAlbum : com.webside.video.model.VideoAlbumEntity@2052117)(getAuthor : 123)(getUpdateRemarks : 每周四晚20:30更新两集)(getHomeUrl : )(getHomeId : )(getUpdateUser : com.webside.user.model.UserEntity@4ed982e1)]', '2017-08-28 14:21:36');
INSERT INTO `tb_log_info` VALUES ('ad80888f30a747f6ae9165d1d565345a', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 2fbe7e89793140fdba13abda0f21a030)(getContent : 白小白的日常)(getLikeCount : 50000)(getStatus : com.webside.dict.model.DictEntity@2541c8a0)(getUp : com.webside.up.model.UpEntity@37da80bc)(getUpdateUser : com.webside.user.model.UserEntity@469285ce)(getTitle : 白小白)(getUpdateTime : 1505293956394)(getViewCount : 100000)]', '2017-09-13 17:12:36');
INSERT INTO `tb_log_info` VALUES ('b0aff84e6fa74344b8e3da93bc83de7e', '1', 'superadmin', 'update', 'update[参数1，类型：UpEntity，值：(getName : 墨花染缘)(getId : 00001ce4d0114979bc9f7811e4ad4ffa)(getStatus : com.webside.dict.model.DictEntity@2e64f46d)(getUpdateUser : com.webside.user.model.UserEntity@8e8ea49)(getUpdateTime : 1505961812457)(getGame : com.webside.sys.model.GameEntity@6ce47a03)(getIntroduction : <p>写这神马的最讨厌了</p>)(getAvatar : http://i0.hdslb.com/bfs/face/1dc31cacda51452f3ba310d8bc26f3f432921716.jpg)(getIsSearch : com.webside.dict.model.DictEntity@1251d83)]', '2017-09-21 10:43:32');
INSERT INTO `tb_log_info` VALUES ('b0eb3d7e69b4471b81b354549cfe79e0', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:000003c3ab9f4fdc9bbb8c1ebef8660f)', '2017-09-22 16:22:52');
INSERT INTO `tb_log_info` VALUES ('b146a9f091d24a9d8afd00061b8326ce', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 2a8b66f90f284a2c84b2a5da9e0edf3d)(getContent : <p>1234</p>)(getStatus : com.webside.dict.model.DictEntity@291ecd38)(getUp : com.webside.up.model.UpEntity@52d9e410)(getUpdateUser : com.webside.user.model.UserEntity@362c304c)(getLikeCount : 0)(getTitle : 123)(getUpdateTime : 1506326584446)(getViewCount : 1)]', '2017-09-25 16:03:04');
INSERT INTO `tb_log_info` VALUES ('b210aa6ab34f44d2a6f05e452eda7342', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:2fbe7e89793140fdba13abda0f21a030)', '2017-09-13 17:12:45');
INSERT INTO `tb_log_info` VALUES ('b2bbb82dd0f046ddadc4328b764f512d', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : <p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>王企鹅武器二片区纪委批那是的那是的确问其他区儿童起晚了rhqwlkOK恩情我肯 asd权威qWeqw额</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>权威e权威</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>权威权威q问权威</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>权威</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>权威asd</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>发个df好fg就特价款</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\">躺赢uk</span><span style=\"text-decoration: none;\"></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: none;\">奥术大师多asd 权威权威区位图去<img src=\"/upload/ueditor/20170921/1505981076942030978.jpg\" title=\"1505981076942030978.jpg\" alt=\"Desert.jpg\"/></span></p>)(getUp : com.webside.up.model.UpEntity@681eacb7)(getStatus : com.webside.dict.model.DictEntity@1624c92)(getLikeCount : 1111)(getTitle : 请问)(getUpdateTime : 1505981451019)(getViewCount : 12312)(getUpdateUser : com.webside.user.model.UserEntity@1b242e8)]', '2017-09-21 16:10:51');
INSERT INTO `tb_log_info` VALUES ('b30ad09df7994287bc61fff2b966e414', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 删除评论)(getId : e4eb75fce5a642e290e5a6d2dad0c4ec)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/delete.html)(getSourceKey : taskComment:del)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getCreateTime : Tue Sep 12 15:57:04 CST 2017)(getIcon : )]', '2017-09-12 15:57:04');
INSERT INTO `tb_log_info` VALUES ('b549fd5c2a39460c8640ed5a7d630167', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 视频列表)(getId : b77280c530e145b89b563e271c95a47d)(getType : 1)(getDescription : )(getIcon : )(getChildren : [])(getCreateTime : Wed Aug 23 16:58:17 CST 2017)(getSourceUrl : /videoAlbumCtrl/batchVideoUI.html)(getSourceKey : videoAlbum:batchVideoUI)(getParentId : 2d31d167da6b448d86bc37ba2800069c)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-08-23 16:58:19');
INSERT INTO `tb_log_info` VALUES ('b5cc29346d674968a2f93b2c93e85702', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 2)(getContent : 1)(getStatus : com.webside.dict.model.DictEntity@6f1bf512)(getParentComment : com.webside.task.model.TaskCommentEntity@7e54d496)(getUpdateUser : com.webside.user.model.UserEntity@62b8468c)(getUpdateTime : 1505903662239)(getLikeNum : 1)]', '2017-09-20 18:34:22');
INSERT INTO `tb_log_info` VALUES ('b5e9141b0cc7486db2e694f06a3f1023', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@7be1e7a5)(getId : 4997d49638e5404ea22f06e7da93200c)(getTask : com.webside.task.model.TaskEntity@5d36cb32)(getStatus : com.webside.dict.model.DictEntity@29186059)(getCreateUser : com.webside.user.model.UserEntity@50123a4a)(getCreateTime : 1506321537558)]', '2017-09-25 14:38:58');
INSERT INTO `tb_log_info` VALUES ('b6ae888751bb48b888896f9c51c0ca23', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 1,1)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@32bd70dd)(getStatus : com.webside.dict.model.DictEntity@42cc3c50)(getTask : com.webside.task.model.TaskEntity@74e3213b)(getUpdateTime : 1505471660523)(getLikeNum : 1)(getUpdateUser : com.webside.user.model.UserEntity@8137bb)]', '2017-09-15 18:34:21');
INSERT INTO `tb_log_info` VALUES ('b73a963aae0d4888abd60a194455977a', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoAlbumEntity，值：(getName : 1)(getId : ea344991dacb49148e30c77f12c13914)(getStatus : com.webside.dict.model.DictEntity@12ea17d9)(getHomeUrl : )(getHomeId : )(getCreateUser : com.webside.user.model.UserEntity@5daeb428)(getCreateTime : 1504599381666)(getIntroduction : )(getCover : )(getAuthor : )(getUpdateRemarks : )(getParentAlbum : com.webside.video.model.VideoAlbumEntity@739b9603)]', '2017-09-05 16:16:22');
INSERT INTO `tb_log_info` VALUES ('ba9fb0cd75724e33850ac3351c7e41e4', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 新增人物事件UI)(getId : 740ea2aadf594881a0d222aecd699357)(getType : 1)(getDescription : )(getChildren : [])(getCreateTime : Mon Sep 11 15:13:52 CST 2017)(getSourceUrl : /taskCtrl/addUI.html)(getSourceKey : task:addUI)(getIcon : )(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:13:53');
INSERT INTO `tb_log_info` VALUES ('bf1f0c2506b448c787d48c6037978780', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@4227cbf1)(getStatus : com.webside.dict.model.DictEntity@3e26dfb9)(getTask : com.webside.task.model.TaskEntity@28b629cf)(getUpdateTime : 1505471055707)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@11c33058)]', '2017-09-15 18:24:16');
INSERT INTO `tb_log_info` VALUES ('bfa0e90f91944ba9b544559a30e9ab0d', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : d9a73636316444139bbd8762aac0f252)(getContent : 1)(getStatus : com.webside.dict.model.DictEntity@793b0b8)(getTask : com.webside.task.model.TaskEntity@31390a28)(getParentComment : com.webside.task.model.TaskCommentEntity@5023141)(getUpdateTime : 1506333815638)(getLikeNum : 12)(getUpdateUser : com.webside.user.model.UserEntity@aa925f6)]', '2017-09-25 18:03:36');
INSERT INTO `tb_log_info` VALUES ('bfc102d7657a45fbbcb04954930b8b58', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 删除人物事件)(getId : ec2c4328b78e44b8922ce10845d769c0)(getType : 1)(getDescription : )(getChildren : [])(getCreateTime : Mon Sep 11 15:16:13 CST 2017)(getSourceUrl : /taskCtrl/delete.html)(getSourceKey : task:del)(getIcon : )(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:16:14');
INSERT INTO `tb_log_info` VALUES ('c1262c84387e4d6a96b26bab4f549b47', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : <p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>王企鹅武器二片区纪委批那是的那是的确问其他区儿童起晚了rhqwlkOK恩情我肯 asd权威qWeqw额</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>权威e权威</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>权威权威q问权威</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>权威</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>权威asd</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\"><strong>发个df好fg就特价款</strong></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: underline;\">躺赢uk</span><span style=\"text-decoration: none;\"></span></p><p style=\"text-align: justify;\"><span style=\"text-decoration: none;\">奥术大师多asd 权威权威区位图去<img src=\"/upload/ueditor/20170921/1505981076942030978.jpg\" title=\"1505981076942030978.jpg\" alt=\"Desert.jpg\"/></span></p>)(getUp : com.webside.up.model.UpEntity@128be819)(getStatus : com.webside.dict.model.DictEntity@3338a7e)(getLikeCount : 1111)(getTitle : 请问)(getUpdateTime : 1505981187317)(getViewCount : 12312)(getUpdateUser : com.webside.user.model.UserEntity@3dc5ff18)]', '2017-09-21 16:06:27');
INSERT INTO `tb_log_info` VALUES ('c1acf80d3eac47348437e293ccf1d332', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : 5d68dddf1586487390e3ee1ec03d1431)(getContent : 111)(getLikeCount : 1)(getStatus : com.webside.dict.model.DictEntity@61c28822)(getCreateUser : com.webside.user.model.UserEntity@4b40787f)(getTitle : 1)(getCreateTime : 1505187046129)(getViewCount : 11)]', '2017-09-12 11:30:46');
INSERT INTO `tb_log_info` VALUES ('c35821b22eb04d1b8aad310ceb6fb189', '1', 'superadmin', 'update', 'update[参数1，类型：SeoConfigEntity，值：(getUpdateUser : com.webside.user.model.UserEntity@6f453637)(getUpdateTime : 1501833595691)(getKeywords : -WoDotA)(getTitle : -WoDotA)(getDescription : )(getStatus : com.webside.dict.model.DictEntity@4bdfc6bb)(getId : f730a127e3c541b29155d58928369fcf)(getType : com.webside.dict.model.DictEntity@21188d90)]', '2017-08-04 15:59:55');
INSERT INTO `tb_log_info` VALUES ('c37b85f69f964f0b8aaef8936448326a', '1', 'superadmin', 'delete', 'deleteBatch[参数1，类型:String，值:(id:f41a5399b6754affa6ecb0c772d49035)', '2017-09-11 15:14:59');
INSERT INTO `tb_log_info` VALUES ('c389399121af4e16b4ce0cbd45745807', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 2)(getContent : 1)(getStatus : com.webside.dict.model.DictEntity@37b0c8ed)(getParentComment : com.webside.task.model.TaskCommentEntity@4ca799f5)(getUpdateUser : com.webside.user.model.UserEntity@41aa41ba)(getUpdateTime : 1505896675633)(getLikeNum : 1)]', '2017-09-20 16:37:56');
INSERT INTO `tb_log_info` VALUES ('c45a48b1881b4fd780969c9afca5d102', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 删除属性值)(getId : d19859f2ac1f400498dc4c52419458fe)(getType : 1)(getDescription : )(getIcon : )(getSourceUrl : /taskValueCtrl/delete.html)(getSourceKey : taskValue:del)(getChildren : [])(getCreateTime : Fri Sep 22 17:18:33 CST 2017)(getParentId : 00254d3a9ab54a8199a2c147ec0648f2)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-22 17:18:34');
INSERT INTO `tb_log_info` VALUES ('c492cbd20e174d01af1321c2aa7df625', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoCommentEntity，值：(getId : 9e4605944c99479d83ed10c5134967dd)(getStatus : com.webside.dict.model.DictEntity@d7faaab)(getCommentContent : 熊二哈哈哈)(getCommentUserName : 熊二)(getVideo : com.webside.video.model.VideoEntity@3cbeddbe)(getCreateUser : com.webside.user.model.UserEntity@5f76108b)(getCommentCreatetime : 1505293380000)(getCreateTime : 1505293417445)(getStation : com.webside.dict.model.DictEntity@2573b7a4)(getCommentUserId : 110)(getLikeNum : 0)(getCommentId : 100)(getCommentParent : com.webside.video.model.VideoCommentEntity@6feadd99)]', '2017-09-13 17:03:38');
INSERT INTO `tb_log_info` VALUES ('c64235583bd9497d9caf1995a252893b', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : 王企鹅)(getStatus : com.webside.dict.model.DictEntity@225969da)(getLikeCount : 1111)(getUp : com.webside.up.model.UpEntity@5fca659a)(getUpdateUser : com.webside.user.model.UserEntity@1adb7527)(getTitle : 请问)(getUpdateTime : 1505383814020)(getViewCount : 12312)]', '2017-09-14 18:10:14');
INSERT INTO `tb_log_info` VALUES ('c7dc589497d6459dae0a35898290232c', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : c93abb92ac2b4c0bb34e1f72d89abf0d)(getContent : 22)(getTask : com.webside.task.model.TaskEntity@2c0ae859)(getStatus : com.webside.dict.model.DictEntity@396137a)(getUpdateUser : com.webside.user.model.UserEntity@3ecefb1)(getParentComment : com.webside.task.model.TaskCommentEntity@219ce101)(getUpdateTime : 1506326933592)(getLikeNum : 2)]', '2017-09-25 16:08:54');
INSERT INTO `tb_log_info` VALUES ('c87b7cce54d24d088e9d4be20d6b06d4', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@3434607f)(getId : b7c0eb556cdb4da1919a475f327eb3d4)(getStatus : com.webside.dict.model.DictEntity@1acdcde1)(getTask : com.webside.task.model.TaskEntity@69e3def1)(getCreateTime : 1506336836834)(getCreateUser : com.webside.user.model.UserEntity@62e6ca78)]', '2017-09-25 18:53:57');
INSERT INTO `tb_log_info` VALUES ('c8b9c7df8ad5433da2db452e25be8075', '1', 'superadmin', 'update', 'update[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@20efcb38)(getId : 9774097cc595422b852f041583c68d5f)(getTask : com.webside.task.model.TaskEntity@79306873)(getStatus : com.webside.dict.model.DictEntity@733d191d)(getUpdateUser : com.webside.user.model.UserEntity@4ceec837)(getUpdateTime : 1506327080171)]', '2017-09-25 16:11:20');
INSERT INTO `tb_log_info` VALUES ('c8e30a048c4f49959c77277bc5048926', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:1)', '2017-09-22 17:23:57');
INSERT INTO `tb_log_info` VALUES ('c8efcef6e63b4cc58a66f6ee5815c189', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 修改评论)(getId : 4ffdd16ce1544cbebb8188ee460eab7a)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/edit.html)(getSourceKey : taskComment:edit)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-12 15:53:18');
INSERT INTO `tb_log_info` VALUES ('ca174af17ee44ccab6532bd09740e82b', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@38f6a8d5)(getId : c275720ca2454f40ab693a4471db4f9c)(getTask : com.webside.task.model.TaskEntity@4c7be233)(getStatus : com.webside.dict.model.DictEntity@26e2c027)(getCreateUser : com.webside.user.model.UserEntity@4eb42d8d)(getCreateTime : 1506321842004)]', '2017-09-25 14:44:02');
INSERT INTO `tb_log_info` VALUES ('cb1dd759dad947c5b1167e2c51503a4f', '1', 'superadmin', 'insert', 'add[参数1，类型：UpValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@4bfbfd8c)(getId : 543e781911e34607b8d196bcbc5065d5)(getStatus : com.webside.dict.model.DictEntity@419a2af4)(getUp : com.webside.up.model.UpEntity@278ea9f)(getCreateUser : com.webside.user.model.UserEntity@16462685)(getCreateTime : 1506564741588)]', '2017-09-28 10:12:22');
INSERT INTO `tb_log_info` VALUES ('cbaba0859f9f4e77ad2f73a53cdf134f', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : 王企鹅)(getLikeCount : 1111)(getStatus : com.webside.dict.model.DictEntity@18be3ba0)(getUp : com.webside.up.model.UpEntity@3204cea)(getUpdateUser : com.webside.user.model.UserEntity@478bde9a)(getTitle : 请问)(getUpdateTime : 1505199931517)(getViewCount : 12312)]', '2017-09-12 15:05:32');
INSERT INTO `tb_log_info` VALUES ('cbf4b23076d74d5da576af4f248449d0', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 4)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@3c0fdcc6)(getStatus : com.webside.dict.model.DictEntity@30d48d20)(getParentComment : com.webside.task.model.TaskCommentEntity@24268c9b)(getUpdateTime : 1505981703915)(getLikeNum : 11)(getUpdateUser : com.webside.user.model.UserEntity@9d8a60b)]', '2017-09-21 16:15:04');
INSERT INTO `tb_log_info` VALUES ('cf108900f57047ad8f80097015278d56', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskCommentEntity，值：(getId : 6ed85959f54d40bda5423b4d57704962)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@463cdc04)(getCreateUser : com.webside.user.model.UserEntity@365f5fa8)(getUpdateTime : 1)(getCreateTime : 1)(getStatus : com.webside.dict.model.DictEntity@28655e76)(getParentComment : com.webside.task.model.TaskCommentEntity@18ad731f)(getLikeNum : 1)]', '2017-09-15 17:38:36');
INSERT INTO `tb_log_info` VALUES ('d0efeb84b8b94b839b8b61a80b47c2a2', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 修改人物事件)(getId : d8330ec12bbe47baab75f372bc264f79)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCtrl/edit.html)(getSourceKey : task:edit)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:13:01');
INSERT INTO `tb_log_info` VALUES ('d13fb6fb2b3d451dbabae381ddb61c5b', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 1,1)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@3257dccf)(getCreateUser : com.webside.user.model.UserEntity@16783b0c)(getUpdateUser : com.webside.user.model.UserEntity@62df2ad8)(getUpdateTime : 111)(getCreateTime : 1)(getStatus : com.webside.dict.model.DictEntity@3857ab64)(getParentComment : com.webside.task.model.TaskCommentEntity@407f0156)(getLikeNum : 1)]', '2017-09-15 17:57:59');
INSERT INTO `tb_log_info` VALUES ('d2913b92c289445ca98b6ba94f69e32a', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 新增属性值)(getId : d41dcf6c6bc4416689f7bc67d41e3b51)(getType : 1)(getDescription : )(getIcon : )(getSourceUrl : /taskValueCtrl/add.html)(getSourceKey : task:Value:add)(getChildren : [])(getCreateTime : Fri Sep 22 17:17:23 CST 2017)(getParentId : 00254d3a9ab54a8199a2c147ec0648f2)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-22 17:17:23');
INSERT INTO `tb_log_info` VALUES ('d2b4b34c78244f9683cddf7d51ab72c7', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskCommentEntity，值：(getId : 4)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@4190ad36)(getStatus : com.webside.dict.model.DictEntity@d418454)(getTask : com.webside.task.model.TaskEntity@7254dbe0)(getCreateTime : 1505470998387)(getLikeNum : 11)(getCreateUser : com.webside.user.model.UserEntity@7949da34)]', '2017-09-15 18:23:18');
INSERT INTO `tb_log_info` VALUES ('d51e0d66d0604b489d14229dc4314e5b', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 2a8b66f90f284a2c84b2a5da9e0edf3d)(getContent : <p>1234</p>)(getStatus : com.webside.dict.model.DictEntity@97d9bce)(getUp : com.webside.up.model.UpEntity@5e4c1f4c)(getUpdateUser : com.webside.user.model.UserEntity@7c61b340)(getLikeCount : 0)(getTitle : 123)(getUpdateTime : 1506326600764)(getViewCount : 1)]', '2017-09-25 16:03:21');
INSERT INTO `tb_log_info` VALUES ('d664f4bb9ac246b4a0c36e4155fd73b9', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:471160dde5394a748f498ff1f1f52476)', '2017-09-20 18:34:40');
INSERT INTO `tb_log_info` VALUES ('d7507d58d5f84310be123e5cd7845faf', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : ad739c08ccd243d2b244ab6a8466932a)(getContent : 1)(getLikeCount : 1)(getStatus : com.webside.dict.model.DictEntity@41ed34d6)(getTitle : 1)(getCreateTime : 1505471558239)(getViewCount : 1)(getUp : com.webside.up.model.UpEntity@5b100ec3)(getCreateUser : com.webside.user.model.UserEntity@45c44468)]', '2017-09-15 18:32:38');
INSERT INTO `tb_log_info` VALUES ('d8251d42c7624f53ae7cd3761123865e', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : c93abb92ac2b4c0bb34e1f72d89abf0d)(getContent : 22)(getStatus : com.webside.dict.model.DictEntity@52d792a7)(getTask : com.webside.task.model.TaskEntity@d3eb41b)(getParentComment : com.webside.task.model.TaskCommentEntity@11dc0362)(getUpdateTime : 1506334165710)(getLikeNum : 2)(getUpdateUser : com.webside.user.model.UserEntity@1a2702d5)]', '2017-09-25 18:09:26');
INSERT INTO `tb_log_info` VALUES ('de11b45f171a4a7183010cca4bbad164', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskCommentEntity，值：(getId : c93abb92ac2b4c0bb34e1f72d89abf0d)(getContent : 2)(getTask : com.webside.task.model.TaskEntity@516c04c4)(getStatus : com.webside.dict.model.DictEntity@44004fec)(getCreateUser : com.webside.user.model.UserEntity@55b9f8bc)(getParentComment : com.webside.task.model.TaskCommentEntity@2b2d75fd)(getCreateTime : 1506326851359)(getLikeNum : 2)]', '2017-09-25 16:07:31');
INSERT INTO `tb_log_info` VALUES ('ded2bf18dea94e0ba0edfdbfd882a535', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@20efcb38)(getId : 9774097cc595422b852f041583c68d5f)(getTask : com.webside.task.model.TaskEntity@4e45d9ac)(getStatus : com.webside.dict.model.DictEntity@657037d9)(getCreateUser : com.webside.user.model.UserEntity@6e92d630)(getCreateTime : 1506327029586)]', '2017-09-25 16:10:30');
INSERT INTO `tb_log_info` VALUES ('e0b902a9f5a24c449d5516bdccd8ea82', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 1,1)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@1741afbf)(getCreateUser : com.webside.user.model.UserEntity@dcdfa42)(getUpdateUser : com.webside.user.model.UserEntity@1f2ed946)(getUpdateTime : 1)(getCreateTime : 1500276402243)(getStatus : com.webside.dict.model.DictEntity@21adc499)(getParentComment : com.webside.task.model.TaskCommentEntity@2dfc6238)(getLikeNum : 1)]', '2017-09-15 17:58:28');
INSERT INTO `tb_log_info` VALUES ('e400eaa2d12f45aabeba89cb63edd4b5', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 1)(getContent : 王企鹅)(getStatus : com.webside.dict.model.DictEntity@52ccab49)(getLikeCount : 1111)(getUp : com.webside.up.model.UpEntity@73151516)(getUpdateUser : com.webside.user.model.UserEntity@6ca6d0eb)(getTitle : 请问)(getUpdateTime : 1505273914673)(getViewCount : 12312)]', '2017-09-13 11:38:35');
INSERT INTO `tb_log_info` VALUES ('e5ca00f389024a60ada2de1cca5acf4f', '1', 'superadmin', 'delete', 'delete[参数1，类型:String，值:(id:5d68dddf1586487390e3ee1ec03d1431)', '2017-09-12 15:05:59');
INSERT INTO `tb_log_info` VALUES ('e64a2030047f432f960fdd4ecfec588b', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 修改评论UI)(getId : 015af4f4b5f74aee9d913734c4dfa3d4)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/editUI.html)(getSourceKey : taskComment:editUI)(getParentId : 6fbbbe3bd93b42b6acd0be80a2f0371d)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-12 16:06:48');
INSERT INTO `tb_log_info` VALUES ('e73b9000656a4f1091196ad46030967e', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskCommentEntity，值：(getId : d9a73636316444139bbd8762aac0f252)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@60f3e9eb)(getStatus : com.webside.dict.model.DictEntity@2fb984d1)(getCreateUser : com.webside.user.model.UserEntity@46d8f84a)(getParentComment : com.webside.task.model.TaskCommentEntity@31a1ddb0)(getCreateTime : 1506326835526)(getLikeNum : 1)]', '2017-09-25 16:07:16');
INSERT INTO `tb_log_info` VALUES ('e74458e1244e46648b646bed488c4e96', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@28ce9d79)(getStatus : com.webside.dict.model.DictEntity@645462de)(getTask : com.webside.task.model.TaskEntity@6c825614)(getUpdateTime : 1505471172981)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@5e267a4a)]', '2017-09-15 18:26:13');
INSERT INTO `tb_log_info` VALUES ('e8dae7d06d474cb1a135572c5bbd7538', '1', 'superadmin', 'update', 'update[参数1，类型：UpEntity，值：(getName : 墨花染缘)(getId : 00001ce4d0114979bc9f7811e4ad4ffa)(getStatus : com.webside.dict.model.DictEntity@64eb5b4d)(getUpdateUser : com.webside.user.model.UserEntity@34ec46a5)(getUpdateTime : 1505961793749)(getGame : com.webside.sys.model.GameEntity@7dfa2f97)(getIntroduction : <p>写这神马的最讨厌了</p>)(getAvatar : http://i0.hdslb.com/bfs/face/1dc31cacda51452f3ba310d8bc26f3f432921716.jpg)(getIsSearch : com.webside.dict.model.DictEntity@287e7bc6)]', '2017-09-21 10:43:14');
INSERT INTO `tb_log_info` VALUES ('eb1a475baaaa483e901ea9de9fb3e20d', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoRecommendEntity，值：(getVideo : com.webside.video.model.VideoEntity@6eccd3e3)(getCreateTime : 1501148856866)(getCreateUser : com.webside.user.model.UserEntity@33165002)(getUrl : )(getStartTime : 1501148820000)(getStatus : com.webside.dict.model.DictEntity@ac670ba)(getId : c2c5914b6abf4f7195ac081fae4f968a)]', '2017-07-27 17:47:36');
INSERT INTO `tb_log_info` VALUES ('eba29ac7a1b14eaab210991927b367ed', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 修改人物事件UI)(getId : 8f219d78691741d5bde5b52d34aac922)(getType : 1)(getDescription : )(getChildren : [])(getCreateTime : Mon Sep 11 15:12:31 CST 2017)(getSourceUrl : /taskCtrl/editUI.html)(getSourceKey : taskTask:editUI)(getIcon : )(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:12:31');
INSERT INTO `tb_log_info` VALUES ('ec05fd3290734f7fbe5a4554aa668194', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@1ee35b48)(getStatus : com.webside.dict.model.DictEntity@8d4a418)(getParentComment : com.webside.task.model.TaskCommentEntity@e670d36)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@680c728d)(getUpdateTime : 1505472505173)]', '2017-09-15 18:48:25');
INSERT INTO `tb_log_info` VALUES ('eca4af9b82f0461d81425e12b0aaf600', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 属性值管理)(getId : 00254d3a9ab54a8199a2c147ec0648f2)(getType : 0)(getDescription : )(getIcon : fa  fa-bell)(getSourceUrl : /taskValuetCtrl/listUI.html)(getSourceKey : task:value)(getChildren : [])(getParentId : bd27a654ce814223b45045b42dcfb6c4)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-22 17:14:27');
INSERT INTO `tb_log_info` VALUES ('ecc519cbd42e4a0286e4f1767bcdebaa', '1', 'superadmin', 'update', 'update[参数1，类型：TaskEntity，值：(getId : 2a8b66f90f284a2c84b2a5da9e0edf3d)(getContent : <p>1234</p>)(getStatus : com.webside.dict.model.DictEntity@4f772a5f)(getLikeCount : 0)(getTitle : 123)(getUpdateTime : 1506336701820)(getViewCount : 1)(getUp : com.webside.up.model.UpEntity@42cd2675)(getUpdateUser : com.webside.user.model.UserEntity@380a2c45)]', '2017-09-25 18:51:42');
INSERT INTO `tb_log_info` VALUES ('ecefb715be2447169bf9892335f085a9', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@33b84bab)(getStatus : com.webside.dict.model.DictEntity@4eae533f)(getTask : com.webside.task.model.TaskEntity@241508a9)(getUpdateTime : 1505471362448)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@3f568a03)]', '2017-09-15 18:29:22');
INSERT INTO `tb_log_info` VALUES ('f1bd7336b0a04c2eafb7147339030546', '1', 'superadmin', 'update', 'update[参数1，类型：ResourceEntity，值：(getName : 新增评论)(getId : b58ec3c4ba5c471f8f2d6ea4ed0e9a0c)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/add.html)(getSourceKey : taskComment:add)(getParentId : 4eac4f474f5e4382bdd06dcf4ccf815b)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-12 15:52:54');
INSERT INTO `tb_log_info` VALUES ('f4b46eac90244ece8e7e451b94bd9d09', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 1,1)(getContent : 1)(getTask : com.webside.task.model.TaskEntity@5dfbf3d4)(getUpdateUser : com.webside.user.model.UserEntity@3b395162)(getUpdateTime : 1505469911854)(getStatus : com.webside.dict.model.DictEntity@4aa3be95)(getParentComment : com.webside.task.model.TaskCommentEntity@48c49146)(getLikeNum : 1)]', '2017-09-15 18:05:12');
INSERT INTO `tb_log_info` VALUES ('f4bf682b807d4220998948570c5c219c', '1', 'superadmin', 'insert', 'add[参数1，类型：VideoValueEntity，值：(getValue : com.webside.sys.model.ValueEntity@44a8e857)(getId : f103894e06eb49ca816731d497e535c7)(getStatus : com.webside.dict.model.DictEntity@43c9ae22)(getVideo : com.webside.video.model.VideoEntity@1deb72d8)(getCreateUser : com.webside.user.model.UserEntity@df555b6)(getCreateTime : 1506564773746)]', '2017-09-28 10:12:54');
INSERT INTO `tb_log_info` VALUES ('f4d9240ed0f24b14b7e85378e38f780e', '1', 'superadmin', 'insert', 'add[参数1，类型：TaskEntity，值：(getId : 4c0a0c0997884e67a3e22ea5347cbf30)(getContent : 12)(getLikeCount : 12)(getStatus : com.webside.dict.model.DictEntity@4e82f67b)(getUp : com.webside.up.model.UpEntity@45c89568)(getCreateUser : com.webside.user.model.UserEntity@6a0b81b9)(getTitle : 1)(getCreateTime : 1505187878061)(getViewCount : 12)]', '2017-09-12 11:44:38');
INSERT INTO `tb_log_info` VALUES ('f89c98d472594ca4b303039d0ae54fe8', '1', 'superadmin', 'delete', 'deleteQuartzJob[参数1，类型:ScheduleJobEntity，值:(id:com.webside.quartz.model.ScheduleJobEntity@362ad65)', '2017-08-22 15:36:37');
INSERT INTO `tb_log_info` VALUES ('fa0df69078764175a762fdacb9d14d5a', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 新增评论UI)(getId : 8359efcf52da48dd8d3b92547681b1dd)(getType : 1)(getDescription : )(getChildren : [])(getSourceUrl : /taskCommentCtrl/addUI.html)(getSourceKey : taskComment:addUI)(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)(getCreateTime : Tue Sep 12 16:03:11 CST 2017)(getIcon : )]', '2017-09-12 16:03:12');
INSERT INTO `tb_log_info` VALUES ('fa11eb30e3224b8fa5436ed2bb6599ad', '1', 'superadmin', 'insert', 'add[参数1，类型：ResourceEntity，值：(getName : 修改视频)(getId : d8330ec12bbe47baab75f372bc264f79)(getType : 1)(getDescription : )(getChildren : [])(getCreateTime : Mon Sep 11 15:10:44 CST 2017)(getSourceUrl : /taskCtrl/edit.html)(getSourceKey : task:edit)(getIcon : )(getParentId : 828f3f0130314d0598ab92c185cd804a)(getIsHide : 0)(getIsExpanded : false)(getIsLeaf : false)(getLoaded : true)(getSelected : false)]', '2017-09-11 15:10:45');
INSERT INTO `tb_log_info` VALUES ('fc3d1cf31a724a4f8870d267cbe3eaff', '1', 'superadmin', 'update', 'update[参数1，类型：TaskCommentEntity，值：(getId : 3,3)(getContent : 1)(getParentComment : com.webside.task.model.TaskCommentEntity@56db33d6)(getStatus : com.webside.dict.model.DictEntity@1c7b1b3a)(getTask : com.webside.task.model.TaskEntity@741b87ae)(getUpdateTime : 1505472091277)(getLikeNum : 0)(getUpdateUser : com.webside.user.model.UserEntity@e40f98e)]', '2017-09-15 18:41:31');

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource` (
  `s_id` varchar(32) NOT NULL COMMENT '资源id',
  `s_parent_id` varchar(32) DEFAULT NULL COMMENT '资源父id',
  `s_name` varchar(100) NOT NULL COMMENT '资源名称',
  `s_source_key` varchar(100) NOT NULL COMMENT '资源唯一标识',
  `s_type` int(11) NOT NULL COMMENT '资源类型,0:目录;1:菜单;2:按钮',
  `s_source_url` varchar(500) DEFAULT NULL COMMENT '资源url',
  `s_level` int(11) DEFAULT NULL COMMENT '层级',
  `s_icon` varchar(100) DEFAULT '' COMMENT '图标',
  `s_is_hide` int(11) DEFAULT '0' COMMENT '是否隐藏',
  `s_description` varchar(100) DEFAULT NULL COMMENT '描述',
  `s_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `s_update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES ('00254d3a9ab54a8199a2c147ec0648f2', 'bd27a654ce814223b45045b42dcfb6c4', '属性值管理', 'task:value', '1', '/taskValuetCtrl/listUI.html', null, null, '0', '', '2017-09-22 17:14:09', '2017-09-22 09:22:52');
INSERT INTO `tb_resource` VALUES ('015af4f4b5f74aee9d913734c4dfa3d4', '6fbbbe3bd93b42b6acd0be80a2f0371d', '修改评论UI', 'taskComment:editUI', '1', '/taskCommentCtrl/editUI.html', null, null, '0', '', '2017-09-12 16:02:33', '2017-09-12 08:07:07');
INSERT INTO `tb_resource` VALUES ('024cc92af94e4f9fb65539074df8b01d', '2d31d167da6b448d86bc37ba2800069c', '选择专辑查询列表UI', 'videoAlbum:searchListUI', '1', '/videoAlbumCtrl/searchListUI.html', null, '', '0', '选择专辑查询列表UI', '2017-05-27 11:43:49', '2017-05-27 03:44:45');
INSERT INTO `tb_resource` VALUES ('04e32192369d499a94910f7c39b56f16', '8f3a755650df4b06ac24c57b73cc2594', '新增游戏UI', 'sysGame:addUI', '1', '/sysGameCtrl/addUI.html', null, '', '0', '新增游戏UI', '2017-05-26 17:48:47', '2017-05-26 09:49:43');
INSERT INTO `tb_resource` VALUES ('09275dcc376a4758a1291f7e35a98816', '44b40bba58294327bc23ef72ee3980d2', '作者站点管理', 'up:station', '1', '/upStationCtrl/listUI.html', null, '', '0', '作者站点管理', '2017-05-26 18:06:10', '2017-05-26 10:07:06');
INSERT INTO `tb_resource` VALUES ('093259a287a944bbb70ab800a41cde12', ' ', '业务基础数据管理', 'sys', '0', '', null, 'fa  fa-bars', '0', '业务基础数据管理', '2017-05-26 17:13:08', '2017-05-26 09:14:04');
INSERT INTO `tb_resource` VALUES ('0cb76b2c61a9418ab0188e4e41c6c349', '09275dcc376a4758a1291f7e35a98816', '作者站点列表', 'upStation:list', '1', '/upStationCtrl/list.html', null, '', '0', '作者站点列表', '2017-05-26 18:10:40', '2017-05-26 10:11:36');
INSERT INTO `tb_resource` VALUES ('0f64071a652149dabf7c71e2ef82beb8', '4', '查看角色下的用户列表UI', 'role:roleUserListUI', '1', '/role/roleUserListUI.html', null, '', '0', '查看角色下的用户列表UI', '2017-05-26 17:10:22', '2017-05-26 09:11:18');
INSERT INTO `tb_resource` VALUES ('1', null, '控制台', 'desktop', '0', '/welcome.jsp', '1', 'fa fa-tachometer', '0', '控制台', '2016-01-12 17:08:55', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('10', '9', '用户登录信息', 'logininfo:userLogin', '0', '/loginInfoCtrl/listUI.html', '2', '', '0', '用户登录信息', '2016-01-11 22:47:41', '2017-05-26 09:04:06');
INSERT INTO `tb_resource` VALUES ('100', '2', '字典管理', 'system:dict', '0', '/dictCtrl/listUI.html', null, '', '0', '', '2017-03-13 17:43:41', '2017-05-26 09:02:45');
INSERT INTO `tb_resource` VALUES ('103', '100', '字典列表', 'dict:list', '1', '/dictCtrl/list.html', null, '', '0', '', '2017-03-14 11:40:49', '2017-05-26 09:03:13');
INSERT INTO `tb_resource` VALUES ('104', '100', '新增字典UI', 'dict:addUI', '1', '/dictCtrl/addUI.html', null, '', '0', '', '2017-03-14 11:42:20', '2017-05-26 09:03:22');
INSERT INTO `tb_resource` VALUES ('105', '100', '新增字典', 'dict:add', '1', '/dictCtrl/add.html', null, '', '0', '', '2017-03-14 11:42:38', '2017-05-26 09:03:26');
INSERT INTO `tb_resource` VALUES ('106', '100', '编辑字典UI', 'dict:editUI', '1', '/dictCtrl/editUI.html', null, '', '0', '', '2017-03-14 11:43:01', '2017-05-26 09:03:16');
INSERT INTO `tb_resource` VALUES ('107', '100', '编辑字典', 'dict:edit', '1', '/dictCtrl/edit.html', null, '', '0', '', '2017-03-14 11:43:22', '2017-05-26 09:03:20');
INSERT INTO `tb_resource` VALUES ('11', '3', '添加用户', 'user:add', '1', '/userCtrl/add.html', '3', null, '0', '添加用户', '2016-01-22 00:18:40', '2017-05-26 09:02:16');
INSERT INTO `tb_resource` VALUES ('1163f635849b44b4bc75d6a93b7007fb', '2ca2a216152b43e7bc7d21201504c9f7', '属性值关联列表', 'sysAttributeValue:list', '1', '/sysAttributeValueCtrl/list.html', null, null, '0', '属性值关联列表', '2017-05-26 17:46:40', '2017-05-26 09:55:49');
INSERT INTO `tb_resource` VALUES ('11c39dbd8efa4544915960b7cf757fd6', '97e8437e73d544a0a409eb6b5e998233', '新增游戏属性值关联', 'sysGameValue:add', '1', '/sysGameValueCtrl/add.html', null, '', '0', '新增游戏属性值关联', '2017-05-26 17:52:05', '2017-05-26 09:53:01');
INSERT INTO `tb_resource` VALUES ('12', '3', '编辑用户', 'user:edit', '1', '/userCtrl/edit.html', '3', null, '0', '编辑用户', '2016-01-22 00:18:40', '2017-05-26 09:02:08');
INSERT INTO `tb_resource` VALUES ('13', '3', '删除用户', 'user:deleteBatch', '1', '/userCtrl/deleteBatch.html', null, null, '0', '删除用户', '2016-02-05 15:26:32', '2017-05-26 09:02:10');
INSERT INTO `tb_resource` VALUES ('14', '3', '重置密码', 'user:resetPassword', '1', '/userCtrl/resetPassword.html', null, null, '0', '重置密码', '2016-02-27 23:55:13', '2017-05-26 09:01:42');
INSERT INTO `tb_resource` VALUES ('1435c92cc52c43b0968f6ea45077945b', '44b40bba58294327bc23ef72ee3980d2', '作者属性值关联管理', 'up:value', '1', '/upValueCtrl/listUI.html', null, null, '0', '作者属性值关联管理', '2017-05-26 18:12:04', '2017-05-27 03:04:17');
INSERT INTO `tb_resource` VALUES ('15', '4', '添加角色', 'role:add', '1', '/role/add.html', null, null, '0', '添加', '2016-02-27 23:56:52', '2016-12-29 13:37:34');
INSERT INTO `tb_resource` VALUES ('15529b879a754a23b8313c74108a7414', '8f3a755650df4b06ac24c57b73cc2594', '选择游戏查询列表UI', 'sysGame:searchListUI', '1', '/sysGameCtrl/searchListUI.html', null, '', '0', '选择游戏查询列表UI', '2017-05-26 17:47:43', '2017-05-26 09:48:39');
INSERT INTO `tb_resource` VALUES ('16', '4', '编辑角色', 'role:edit', '1', '/role/edit.html', null, null, '0', '编辑', '2016-02-27 23:57:35', '2016-12-29 13:37:22');
INSERT INTO `tb_resource` VALUES ('17', '4', '删除角色', 'role:deleteBatch', '1', '/role/deleteBatch.html', null, null, '0', '删除角色', '2016-02-27 23:58:02', '2017-01-03 17:25:42');
INSERT INTO `tb_resource` VALUES ('18', '4', '分配权限', 'role:permission', '1', '/role/permission.html', null, null, '0', '分配权限', '2016-02-27 23:59:20', '2016-06-30 20:54:48');
INSERT INTO `tb_resource` VALUES ('1838ab94925345858e28a8c69cc08480', 'f2fc7fa7af13499ab78926ebda8f5aca', '视频属性值关联管理', 'video:value', '1', '/videoValueCtrl/listUI.html', null, '', '0', '视频属性值关联管理', '2017-05-27 11:04:04', '2017-05-27 03:04:59');
INSERT INTO `tb_resource` VALUES ('18a72a015df24943a023dd682445f16c', 'a30f1d8804c240df97bb499d0c8060e6', '删除首页推荐视频', 'videoRecommend:del', '1', '/videoRecommendCtrl/delete.html', null, null, '0', '删除首页推荐视频', '2017-05-27 12:01:42', '2017-05-27 04:03:21');
INSERT INTO `tb_resource` VALUES ('19', '89', '添加资源', 'resource:add', '1', '/resource/add.html', null, null, '0', '添加', '2016-02-28 00:01:15', '2017-03-13 03:02:32');
INSERT INTO `tb_resource` VALUES ('1e3ee592b3e24218b25f75b1e94bd2e4', '2', '帮助中心', 'help', '0', '/helpCtrl/listUI.html', null, 'fa  fa-bars', '0', '', '2017-06-30 15:10:57', '2017-06-30 07:10:57');
INSERT INTO `tb_resource` VALUES ('2', null, '系统基础管理', 'system', '0', '', '1', 'fa fa-list', '0', '系统基础管理', '2016-01-05 12:11:12', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('20', '89', '编辑资源', 'resource:edit', '1', '/resource/edit.html', null, null, '0', '编辑', '2016-02-28 00:02:01', '2017-03-13 03:04:07');
INSERT INTO `tb_resource` VALUES ('206fd20b2f5c4177b3bc34713cf83f07', '2eaa5101ef544796809bd7ebd67eeb9d', '新增视频站点UI', 'videoStation:addUI', '1', '/videoStationCtrl/addUI.html', null, null, '0', '新增视频站点UI', '2017-05-27 11:00:52', '2017-05-27 03:02:01');
INSERT INTO `tb_resource` VALUES ('20d89ddd87d04f2c907da386ed354a17', '8f3a755650df4b06ac24c57b73cc2594', '修改游戏UI', 'sysGame:editUI', '1', '/sysGameCtrl/editUI.html', null, '', '0', '修改游戏UI', '2017-05-26 17:50:01', '2017-05-26 09:50:57');
INSERT INTO `tb_resource` VALUES ('21', '89', '删除资源', 'resource:deleteBatch', '1', '/resource/deleteBatch.html', null, null, '0', '删除', '2016-02-28 00:03:03', '2017-03-13 03:23:31');
INSERT INTO `tb_resource` VALUES ('21e5c18537274e3ba621b3e0d21a4e30', '1838ab94925345858e28a8c69cc08480', '新增视频属性值关联UI', 'videoValue:addUI', '1', '/videoValueCtrl/addUI.html', null, '', '0', '新增视频属性值关联UI', '2017-05-27 11:05:18', '2017-05-27 03:06:14');
INSERT INTO `tb_resource` VALUES ('22', '9', '用户操作信息', 'loginfo:userOperation', '0', '/loginInfoCtrl/listUI.html', null, 'fa  fa-sticky-note-o', '0', '用户操作信息', '2016-03-08 22:00:36', '2017-05-26 09:04:15');
INSERT INTO `tb_resource` VALUES ('23076822f10f4794a26f25b1e37eaa9e', '2eaa5101ef544796809bd7ebd67eeb9d', '新增视频站点', 'videoStation:add', '1', '/videoStationCtrl/add.html', null, '', '0', '新增视频站点', '2017-05-27 11:01:30', '2017-05-27 03:02:26');
INSERT INTO `tb_resource` VALUES ('27b0ce6eea554946b198e97d09ce7c2c', 'b098121732a44149b9cad2865cedeffa', '相关人物-新增', 'upRelation:add', '1', '/upRelation/add.html', null, '', '0', '', '2017-06-06 15:40:29', '2017-06-06 07:41:00');
INSERT INTO `tb_resource` VALUES ('28378e8466c648469ff3ca2bbce15295', '2eaa5101ef544796809bd7ebd67eeb9d', '修改视频站点UI', 'videoStation:editUI', '1', '/videoStationCtrl/editUI.html', null, '', '0', '修改视频站点UI', '2017-05-27 11:00:31', '2017-05-27 03:01:26');
INSERT INTO `tb_resource` VALUES ('2ac0a5277d664ec9a213c88fa63b7ab4', 'c406877b260e4763b34070c89a76fd44', '单视频抓取', 'crawler:onevideo', '0', '/crawlerCtrl/singleVideoUI.html', null, '', '0', '', '2017-06-13 18:25:51', '2017-06-16 07:10:40');
INSERT INTO `tb_resource` VALUES ('2c3951bf9c2a4acca48d5379f0649994', '9c0a0f61428d4570aeba088003907fa0', '选择属性查询列表UI', 'sysAttribute:searchListUI', '1', '/sysAttributeCtrl/searchListUI.html', null, null, '0', '选择属性查询列表UI', '2017-05-26 17:25:00', '2017-05-26 09:39:37');
INSERT INTO `tb_resource` VALUES ('2ca2a216152b43e7bc7d21201504c9f7', '093259a287a944bbb70ab800a41cde12', '属性值关联管理', 'sys:attributeValue', '0', '/sysAttributeValueCtrl/listUI.html', null, '', '0', '属性值关联管理', '2017-05-26 17:42:56', '2017-05-26 09:43:52');
INSERT INTO `tb_resource` VALUES ('2d31d167da6b448d86bc37ba2800069c', 'f2fc7fa7af13499ab78926ebda8f5aca', '专辑管理', 'video:album', '0', '/videoAlbumCtrl/listUI.html', null, 'fa  fa-calendar', '0', '专辑管理', '2017-05-27 11:34:06', '2017-05-27 03:50:26');
INSERT INTO `tb_resource` VALUES ('2e0455b1cbfb44e4a1976407c0a4eee4', '94c6029804d94e09acabd1b052b8aad9', '修改作者UI', 'up:editUI', '1', '/upCtrl/editUI.html', null, null, '0', '修改作者UI', '2017-05-26 18:04:20', '2017-05-27 03:53:50');
INSERT INTO `tb_resource` VALUES ('2e280411d72b400b8a98e06ce5f44db0', 'bbbf9dc00602444fbcfea9760a199add', '合并视频', 'video:merge', '1', '/videoCtrl/merge.html', null, '', '0', '合并视频', '2017-05-27 10:57:06', '2017-05-27 02:58:02');
INSERT INTO `tb_resource` VALUES ('2eaa5101ef544796809bd7ebd67eeb9d', 'f2fc7fa7af13499ab78926ebda8f5aca', '视频站点管理', 'video:station', '1', '/videoStationCtrl/listUI.html', null, '', '0', '视频站点管理UI', '2017-05-27 10:59:31', '2017-05-27 03:00:27');
INSERT INTO `tb_resource` VALUES ('2f2a3ee1c2f347bc9f32d96c953f8a2d', '2d31d167da6b448d86bc37ba2800069c', '新增专辑UI', 'videoAlbum:addUI', '1', '/videoAlbumCtrl/addUI.html', null, '', '0', '新增专辑UI', '2017-05-27 11:43:18', '2017-05-27 03:44:13');
INSERT INTO `tb_resource` VALUES ('2f2c593853c64baaba52b2592b05c991', 'a30f1d8804c240df97bb499d0c8060e6', '新增首页推荐视频UI', 'videoRecommend:addUI', '1', '/videoRecommendCtrl/addUI.html', null, '', '0', '新增首页推荐视频UI', '2017-05-27 12:00:41', '2017-05-27 04:01:36');
INSERT INTO `tb_resource` VALUES ('3', '2', '用户管理', 'system:user', '0', '/userCtrl/listUI.html', '2', '', '0', '用户管理', '2016-01-08 12:37:10', '2017-05-26 09:01:53');
INSERT INTO `tb_resource` VALUES ('30', null, '计划任务管理', 'schedule', '0', '', null, 'fa  fa-list-ol', '0', '计划任务管理', '2016-07-17 01:09:27', '2016-07-20 16:01:08');
INSERT INTO `tb_resource` VALUES ('309a069c329f42e7bc8baf8ddc019642', 'b098121732a44149b9cad2865cedeffa', '相关人物列表', 'upRelation:list', '1', '/upRelation/list.html', null, '', '0', '', '2017-06-06 15:38:50', '2017-06-06 07:39:21');
INSERT INTO `tb_resource` VALUES ('32', '38', '添加jobUI', 'schedule:addJobUI', '1', '/scheduleJob/addJobUI.html', null, null, '0', '添加任务', '2016-07-17 01:15:38', '2016-12-29 13:47:48');
INSERT INTO `tb_resource` VALUES ('32fe615ee0af4d60867f2be6e16aca5d', 'b098121732a44149b9cad2865cedeffa', '相关人物-编辑', 'upRelation:edit', '1', '/upRelation/edit.html', null, '', '0', '', '2017-06-06 15:41:41', '2017-06-06 07:42:13');
INSERT INTO `tb_resource` VALUES ('3341407c42b045a29366c28055accd51', '44b40bba58294327bc23ef72ee3980d2', '合并视频作者', 'up:mergeUI', '0', '/upCtrl/mergeUI.html', null, '', '0', '合并视频作者UI', '2017-05-27 10:57:53', '2017-05-27 02:58:49');
INSERT INTO `tb_resource` VALUES ('34', '38', '删除job', 'schedule:deleteJob', '1', '/scheduleJob/deleteJob.html', null, null, '0', '删除任务', '2016-07-17 01:19:24', '2016-07-26 17:32:05');
INSERT INTO `tb_resource` VALUES ('3437676427844f46b0e9d27ec6f9be89', '94c6029804d94e09acabd1b052b8aad9', '修改作者', 'up:edit', '1', '/upCtrl/edit.html', null, null, '0', '修改作者', '2017-05-26 18:03:54', '2017-05-27 03:54:05');
INSERT INTO `tb_resource` VALUES ('35', '38', '执行job', 'schedule:executeJob', '1', '/scheduleJob/executeJob.html', null, null, '0', '立即执行一次', '2016-07-17 01:22:01', '2016-07-26 17:33:26');
INSERT INTO `tb_resource` VALUES ('36', '39', '暂停job', 'schedule:pauseJob', '1', '/scheduleJob/pauseJob.html', null, null, '0', '暂停任务', '2016-07-17 01:24:13', '2016-07-26 17:32:20');
INSERT INTO `tb_resource` VALUES ('3647fd639bc74a87ad9c3b411d30553e', '09275dcc376a4758a1291f7e35a98816', '修改作者站点UI', 'upStation:editUI', '1', '/upStationCtrl/editUI.html', null, '', '0', '修改作者站点UI', '2017-05-26 18:08:53', '2017-05-26 10:09:49');
INSERT INTO `tb_resource` VALUES ('37', '39', '恢复job', 'schedule:resumeJob', '1', '/scheduleJob/resumeJob.html', null, null, '0', '恢复任务', '2016-07-17 01:25:23', '2016-07-26 17:32:26');
INSERT INTO `tb_resource` VALUES ('38', '30', '计划中任务', 'schedule:planSchedule', '0', '/scheduleJob/planningJobListUI.html', null, 'fa  fa-hourglass', '0', '计划中任务', '2016-07-19 18:51:54', '2016-12-29 10:27:36');
INSERT INTO `tb_resource` VALUES ('39', '30', '运行中任务', 'schedule:runSchedule', '0', '/scheduleJob/runningJobListUI.html', null, 'fa  fa-hourglass-2', '0', '运行中任务', '2016-07-19 18:53:45', '2016-12-29 10:27:55');
INSERT INTO `tb_resource` VALUES ('3c0379d30f874a63aa7008f16305160f', '8f3a755650df4b06ac24c57b73cc2594', '选择游戏查询列表', 'sysGame:searchList', '1', '/sysGameCtrl/searchList.html', null, '', '0', '选择游戏查询列表', '2017-05-26 17:48:22', '2017-05-26 09:49:18');
INSERT INTO `tb_resource` VALUES ('3cdd33d922a543a8ac3a6a732c47f346', '2ca2a216152b43e7bc7d21201504c9f7', '新增属性值关联', 'sysAttributeValue:add', '1', '/sysAttributeValueCtrl/add.html', null, '', '0', '新增属性值关联', '2017-05-26 17:45:04', '2017-05-26 09:46:00');
INSERT INTO `tb_resource` VALUES ('3cf54176dd154e97b9f3adc814aac7bd', 'f2fc7fa7af13499ab78926ebda8f5aca', '视频标签举报管理', 'videoValueInform', '0', '/videoValueInform/listUI.html', null, '', '0', '', '2017-08-22 14:51:12', '2017-08-22 06:52:44');
INSERT INTO `tb_resource` VALUES ('3d6e237eef07470797901fc68bb78462', '1838ab94925345858e28a8c69cc08480', '删除视频属性值关联', 'videoValue:del', '1', '/videoValueCtrl/delete.html', null, '', '0', '删除视频属性值关联', '2017-05-27 11:06:13', '2017-05-27 03:07:09');
INSERT INTO `tb_resource` VALUES ('4', '2', '角色管理', 'system:role', '0', '/role/listUI.html', '2', '', '0', '角色管理', '2016-01-11 22:51:07', '2016-06-30 20:53:38');
INSERT INTO `tb_resource` VALUES ('40', '38', '触发器管理', 'schedule:trigger', '1', '/scheduleJob/jobTriggerListUI.html', null, null, '0', '查看触发器列表', '2016-07-21 21:19:57', '2016-12-29 10:34:05');
INSERT INTO `tb_resource` VALUES ('402f91b425a7476a94fa826d9e7fcaf2', 'a30f1d8804c240df97bb499d0c8060e6', '修改首页推荐视频', 'videoRecommend:edit', '1', '/videoRecommendCtrl/edit.html', null, '', '0', '修改首页推荐视频', '2017-05-27 12:02:14', '2017-05-27 04:03:09');
INSERT INTO `tb_resource` VALUES ('41', '40', '添加triggerUI', 'schedule:addTriggerUI', '1', '/scheduleJob/addTriggerUI.html', null, null, '0', '给当前job添加trigger页面', '2016-07-26 19:31:07', '2016-12-29 13:49:44');
INSERT INTO `tb_resource` VALUES ('411fdce39be046058d5a76a778081ba6', '6b5a075cf27e415e9146e41f65eb1af1', 'SEO配置列表', 'seoConfig:list', '1', '/seoConfigCtrl/list.html', null, '', '0', 'SEO配置列表', '2017-06-27 14:35:44', '2017-06-27 06:35:52');
INSERT INTO `tb_resource` VALUES ('42', '38', '暂停job', 'schedule:pauseJob', '1', '/scheduleJob/pauseJob.html', null, '', '0', '暂停job', '2016-07-26 19:32:15', '2016-07-26 19:32:15');
INSERT INTO `tb_resource` VALUES ('43', '38', '恢复job', 'schedule:resumeJob', '1', '/scheduleJob/resumeJob.html', null, '', '0', '恢复job', '2016-07-26 19:32:52', '2016-07-26 19:32:52');
INSERT INTO `tb_resource` VALUES ('430ff24d33f24b75840b73cf66c4bd54', 'a350ea4b4b7545b180fe28e0248fa256', '人物二级信息-编辑', 'upSecondLevel:edit', '1', '/upSecondLevel/edit.html', null, '', '0', '', '2017-06-06 15:34:42', '2017-06-06 07:35:13');
INSERT INTO `tb_resource` VALUES ('43e89a90ad7c46aa9f32368afedb59e7', '55b1bf2e961948bc96dd8641e13e11c7', '删除视频', 'video:del', '1', '/videoCtrl/delete.html', null, null, '0', '删除视频', '2017-05-27 10:49:20', '2017-05-27 03:47:21');
INSERT INTO `tb_resource` VALUES ('44', '40', '编辑triggerUI', 'schedule:editTriggerUI', '1', '/scheduleJob/editTriggerUI.html', null, null, '0', '编辑trigger页面', '2016-07-26 19:34:44', '2016-12-29 14:04:06');
INSERT INTO `tb_resource` VALUES ('444e9f8c03e4454d9e949371069f32a3', 'b4ac084e4e0f4cdca0bd641728306e4a', '选择属性值查询列表', 'sysValue:searchList', '1', '/sysValueCtrl/searchList.html', null, '', '0', '选择属性值查询列表', '2017-05-26 17:32:06', '2017-05-26 09:41:30');
INSERT INTO `tb_resource` VALUES ('44b40bba58294327bc23ef72ee3980d2', ' ', '视频作者管理', 'up', '0', '', null, 'fa  fa-ellipsis-v', '0', '视频作者管理', '2017-05-26 17:14:04', '2017-05-27 03:55:31');
INSERT INTO `tb_resource` VALUES ('44fa3becedf246d1bd2ce244f9bad2b3', '8f3a755650df4b06ac24c57b73cc2594', '修改游戏', 'sysGame:edit', '1', '/sysGameCtrl/edit.html', null, '', '0', '修改游戏', '2017-05-26 17:49:37', '2017-05-26 09:50:33');
INSERT INTO `tb_resource` VALUES ('45', '40', '删除trigger', 'schedule:deleteTrigger', '1', '/scheduleJob/deleteTrigger.html', null, null, '0', '删除trigger', '2016-07-26 19:35:23', '2016-07-26 19:36:50');
INSERT INTO `tb_resource` VALUES ('46', '40', '暂停trigger', 'schedule:pauseTrigger', '1', '/scheduleJob/pauseTrigger.html', null, null, '0', '暂停trigger', '2016-07-26 19:36:37', '2017-01-03 17:50:15');
INSERT INTO `tb_resource` VALUES ('47', '40', '恢复trigger', 'schedule:resumeTrigger', '1', '/scheduleJob/resumeTrigger.html', null, '', '0', '恢复trigger', '2016-07-26 19:37:22', '2016-07-26 19:37:22');
INSERT INTO `tb_resource` VALUES ('48', '3', '锁定', 'user:lock', '1', '/userCtrl/lock.html', null, null, '0', '锁定用户账户', '2016-12-26 23:35:14', '2017-05-26 09:01:51');
INSERT INTO `tb_resource` VALUES ('48154f70c4dd43859b3cf55dd5f49d16', '8f3a755650df4b06ac24c57b73cc2594', '游戏列表', 'sysGame:list', '1', '/sysGameCtrl/list.html', null, '', '0', '游戏列表', '2017-05-26 17:50:46', '2017-05-26 09:51:42');
INSERT INTO `tb_resource` VALUES ('49', '3', '解锁', 'user:unlock', '1', '/userCtrl/unlock.html', null, null, '0', '解锁账户', '2016-12-26 23:36:12', '2017-05-26 09:01:39');
INSERT INTO `tb_resource` VALUES ('4d1d360f9e7b4e12b37bb8f1185be8cf', '09275dcc376a4758a1291f7e35a98816', '新增作者站点UI', 'upStation:addUI', '1', '/upStationCtrl/addUI.html', null, '', '0', '新增作者站点UI', '2017-05-26 18:06:55', '2017-05-26 10:07:51');
INSERT INTO `tb_resource` VALUES ('4d93285b64044bb3817dbb4c8b5d500f', '55b1bf2e961948bc96dd8641e13e11c7', '选择视频查询列表', 'video:searchList', '1', '/videoCtrl/searchList.html', null, null, '0', '选择视频查询列表', '2017-05-27 10:49:46', '2017-05-27 03:47:53');
INSERT INTO `tb_resource` VALUES ('4eac4f474f5e4382bdd06dcf4ccf815b', '828f3f0130314d0598ab92c185cd804a', '人物事件列表', 'task:list', '1', '/taskCtrl/list.html', null, '', '0', '', '2017-09-11 15:02:16', '2017-09-11 07:02:34');
INSERT INTO `tb_resource` VALUES ('4ffdd16ce1544cbebb8188ee460eab7a', '6fbbbe3bd93b42b6acd0be80a2f0371d', '修改评论', 'taskComment:edit', '1', '/taskCommentCtrl/edit.html', null, null, '0', '', '2017-09-12 15:51:09', '2017-09-12 08:08:00');
INSERT INTO `tb_resource` VALUES ('50', '6', '在线用户', 'session', '0', '/session/listUI.html', null, 'fa  fa-heartbeat', '0', '在线用户', '2016-12-27 00:45:41', '2016-12-29 10:41:11');
INSERT INTO `tb_resource` VALUES ('51', '50', '踢出', 'session:kickout', '1', '/session/kickout.html', null, null, '0', '踢出在线用户', '2016-12-28 11:25:57', '2016-12-29 10:43:33');
INSERT INTO `tb_resource` VALUES ('52', '3', '用户列表', 'user:list', '1', '/userCtrl/list.html', null, null, '0', '用户列表', '2016-12-29 10:05:04', '2017-05-26 09:01:57');
INSERT INTO `tb_resource` VALUES ('52aa1aba21964a37ae412cc8532f57bd', '2d31d167da6b448d86bc37ba2800069c', '删除专辑', 'videoAlbum:del', '1', '/videoAlbumCtrl/delete.html', null, '', '0', '删除专辑', '2017-05-27 11:40:13', '2017-05-27 03:41:08');
INSERT INTO `tb_resource` VALUES ('53', '4', '角色列表', 'role:list', '1', '/role/list.html', null, 'fa  fa-reorder', '0', '角色列表', '2016-12-29 10:09:36', '2016-12-29 10:09:36');
INSERT INTO `tb_resource` VALUES ('55', '10', '登陆日志列表', 'loginInfo:list', '1', '/loginInfoCtrl/list.html', null, null, '0', '用户登陆列表', '2016-12-29 10:12:37', '2017-05-26 09:04:13');
INSERT INTO `tb_resource` VALUES ('55b1bf2e961948bc96dd8641e13e11c7', 'f2fc7fa7af13499ab78926ebda8f5aca', '视频管理', 'video:video', '0', '/videoCtrl/listUI.html', null, '', '0', '视频管理', '2017-05-27 11:46:05', '2017-05-27 03:47:01');
INSERT INTO `tb_resource` VALUES ('56', '22', '操作日志列表', 'logInfo:list', '1', '/logInfoCtrl/list.html', null, 'fa  fa-bars', '0', '操作日志列表', '2016-12-29 10:14:50', '2017-05-26 09:04:31');
INSERT INTO `tb_resource` VALUES ('56a9bcf86edc4398bf8668d2dd6475c0', '828f3f0130314d0598ab92c185cd804a', '新增人物事件', 'task:add', '1', '/taskCtrl/add.html', null, null, '0', '', '2017-09-11 15:04:51', '2017-09-11 07:11:16');
INSERT INTO `tb_resource` VALUES ('57', '38', '计划中任务列表', 'schedule:planScheduleList', '1', '/scheduleJob/planningJobList.html', null, 'fa  fa-bars', '0', '计划中任务列表', '2016-12-29 10:25:51', '2016-12-29 10:25:51');
INSERT INTO `tb_resource` VALUES ('5761a5a70c464ec2988a020aef1aa169', '94c6029804d94e09acabd1b052b8aad9', '删除作者', 'up:del', '1', '/upCtrl/delete.html', null, null, '0', '删除作者', '2017-05-26 18:03:22', '2017-05-27 03:54:23');
INSERT INTO `tb_resource` VALUES ('576ff25e74574d61935b8799c5729def', 'a30f1d8804c240df97bb499d0c8060e6', '新增首页推荐视频', 'videoRecommend:add', '1', '/videoRecommendCtrl/add.html', null, '', '0', '新增首页推荐视频', '2017-05-27 12:01:13', '2017-05-27 04:02:09');
INSERT INTO `tb_resource` VALUES ('58', '39', '运行中任务列表', 'schedule:runScheduleList', '1', '/scheduleJob/runningJobList.html', null, 'fa  fa-bars', '0', '运行中任务列表', '2016-12-29 10:29:46', '2016-12-29 10:29:46');
INSERT INTO `tb_resource` VALUES ('59', '40', '触发器列表', 'schedule:triggerList', '1', '/scheduleJob/jobTriggerList.html', null, 'fa  fa-bars', '0', '触发器列表', '2016-12-29 10:33:32', '2016-12-29 10:33:32');
INSERT INTO `tb_resource` VALUES ('595b19bbc18d4bae9c26af7ea7768551', 'a350ea4b4b7545b180fe28e0248fa256', '人物二级信息-新增UI', 'upSecondLevel:addUI', '1', '/upSecondLevel/addUI.html', null, '', '0', '', '2017-06-06 15:32:40', '2017-06-06 07:33:11');
INSERT INTO `tb_resource` VALUES ('59ca815018aa4faeb0c10b3d5fe97af2', '8be560e65d66441ba6a5cb4add8aa867', '提交搜索关键字查询列表', 'submitKeyword:list', '1', '/submitKeywordCtrl/list.html', null, null, '0', '', '2017-06-20 16:30:25', '2017-06-22 09:43:37');
INSERT INTO `tb_resource` VALUES ('5a41977282074937acd77290569a81f5', 'c835d8e535204a60b57baa0125153c03', '删除视频专辑属性值关联', 'videoAlbumValue:del', '1', '/videoAlbumValueCtrl/delete.html', null, '', '0', '删除视频专辑属性值关联', '2017-06-02 16:30:58', '2017-06-02 08:32:00');
INSERT INTO `tb_resource` VALUES ('5c61b3635abb4992965d5b672b743237', '1435c92cc52c43b0968f6ea45077945b', '修改作者属性值关联', 'upValue:edit', '1', '/upValueCtrl/edit.html', null, '', '0', '修改作者属性值关联', '2017-05-26 18:14:54', '2017-05-26 10:15:50');
INSERT INTO `tb_resource` VALUES ('5df0039f4b634bb2a2cf81b181c346e8', '8be560e65d66441ba6a5cb4add8aa867', '修改提交搜索关键字UI', 'submitKeyword:editUI', '1', '/submitKeywordCtrl/editUI.html', null, null, '0', '', '2017-06-20 16:36:25', '2017-06-22 09:43:49');
INSERT INTO `tb_resource` VALUES ('5e560fca6bcc4608a8cb46cf611c1b97', '2ca2a216152b43e7bc7d21201504c9f7', '新增属性值关联UI', 'sysAttributeValue:addUI', '1', '/sysAttributeValueCtrl/addUI.html', null, '', '0', '新增属性值关联UI', '2017-05-26 17:43:59', '2017-05-26 09:44:55');
INSERT INTO `tb_resource` VALUES ('5f81ff5709b6427aba30048c9d165134', 'c835d8e535204a60b57baa0125153c03', '新增视频专辑属性值关联', 'videoAlbumValue:add', '1', '/videoAlbumValueCtrl/add.html', null, '', '0', '新增视频专辑属性值关联', '2017-06-02 16:32:41', '2017-06-02 08:33:43');
INSERT INTO `tb_resource` VALUES ('5fb415e6c0f545ef87c78af5763882a3', '8be560e65d66441ba6a5cb4add8aa867', '删除提交搜索关键字', 'submitKeyword:del', '1', '/submitKeywordCtrl/delete.html', null, null, '0', '', '2017-06-20 16:48:08', '2017-06-22 09:43:01');
INSERT INTO `tb_resource` VALUES ('6', null, '系统监控管理', 'monitor', '0', '', '1', 'fa fa-pencil-square-o', '0', '系统监控管理', '2016-01-05 12:11:12', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('60', '50', '在线用户列表', 'session:list', '1', '/session/list.html ', null, 'fa  fa-bars', '0', '在线用户列表', '2016-12-29 10:41:49', '2016-12-29 10:41:49');
INSERT INTO `tb_resource` VALUES ('61', '3', '添加用户UI', 'user:addUI', '1', '/userCtrl/addUI.html', null, '', '0', '添加用户页面', '2016-12-29 13:41:07', '2017-05-26 09:02:13');
INSERT INTO `tb_resource` VALUES ('62', '4', '添加角色UI', 'role:addUI', '1', '/role/addUI.html', null, '', '0', '添加角色页面', '2016-12-29 13:44:07', '2016-12-29 13:44:07');
INSERT INTO `tb_resource` VALUES ('620e3ec103b74381aef034008465f301', '1838ab94925345858e28a8c69cc08480', '视频属性值关联列表', 'videoValue:list', '1', '/videoValueCtrl/list.html', null, '', '0', '视频属性值关联列表', '2017-05-27 11:07:07', '2017-05-27 03:08:03');
INSERT INTO `tb_resource` VALUES ('62bdf4abcd9f43b3a9902cbce3b346ce', '6d1b50bed56b4348a32c6c2edc60acae', '删除作者名称', 'upName:del', '1', '/upNameCtrl/delete.html', null, '', '0', '删除作者名称', '2017-05-27 10:31:18', '2017-05-27 02:32:13');
INSERT INTO `tb_resource` VALUES ('63', '89', '添加资源UI', 'resource:addUI', '1', '/resource/addUI.html', null, null, '0', '添加资源页面', '2016-12-29 13:44:50', '2017-03-13 03:23:53');
INSERT INTO `tb_resource` VALUES ('63d25f1dfb864963ad71b28e96e48f98', 'b098121732a44149b9cad2865cedeffa', '相关人物-编辑UI', 'upRelation:editUI', '1', '/upRelation/editUI.html', null, '', '0', '', '2017-06-06 15:41:05', '2017-06-06 07:41:37');
INSERT INTO `tb_resource` VALUES ('64', '38', '添加job', 'schedule:addJob', '1', '/scheduleJob/addJob.html', null, '', '0', '添加job', '2016-12-29 13:48:19', '2016-12-29 13:48:19');
INSERT INTO `tb_resource` VALUES ('65', '40', '添加trigger', 'schedule:addTrigger', '1', '/scheduleJob/addTrigger.html', null, '', '0', '添加trigger', '2016-12-29 13:49:34', '2016-12-29 13:49:34');
INSERT INTO `tb_resource` VALUES ('6527814e06a740559afa9f89758b7945', '9c0a0f61428d4570aeba088003907fa0', '选择属性查询列表', 'sysAttribute:searchList', '1', '/sysAttributeCtrl/searchList.html', null, null, '0', '选择属性查询列表', '2017-05-26 17:25:44', '2017-05-26 09:39:42');
INSERT INTO `tb_resource` VALUES ('66', '4', '分配权限UI', 'role:permissionUI', '1', '/role/permissionUI.html', null, '', '0', '分配权限页面', '2016-12-29 13:54:44', '2016-12-29 13:54:44');
INSERT INTO `tb_resource` VALUES ('67', '3', '编辑用户UI', 'user:editUI', '1', '/userCtrl/editUI.html', null, '', '0', '编辑用户页面', '2016-12-29 13:56:06', '2017-05-26 09:02:05');
INSERT INTO `tb_resource` VALUES ('68', '4', '编辑角色UI', 'role:editUI', '1', '/role/editUI.html', null, '', '0', '编辑角色页面', '2016-12-29 13:57:03', '2016-12-29 13:57:03');
INSERT INTO `tb_resource` VALUES ('69', '89', '编辑资源UI', 'resource:editUI', '1', '/resource/editUI.html', null, null, '0', '编辑资源页面', '2016-12-29 13:57:54', '2017-03-13 03:01:58');
INSERT INTO `tb_resource` VALUES ('6b5a075cf27e415e9146e41f65eb1af1', '2', 'SEO配置', 'system:seoConfig', '0', '/seoConfigCtrl/listUI.html', null, '', '0', '', '2017-06-27 14:34:21', '2017-06-27 06:34:29');
INSERT INTO `tb_resource` VALUES ('6ba6e6074da74081bba3add6f5c1d601', '6d1b50bed56b4348a32c6c2edc60acae', '新增作者名称UI', 'upName:addUI', '1', '/upNameCtrl/addUI.html', null, null, '0', '新增作者名称UI', '2017-05-27 10:27:26', '2017-05-27 02:29:22');
INSERT INTO `tb_resource` VALUES ('6c17ce10f157463db6b7fddccd466a24', '94c6029804d94e09acabd1b052b8aad9', '选择作者查询列表', 'up:searchList', '1', '/upCtrl/searchList.html', null, null, '0', '选择作者查询列表', '2017-05-26 18:05:28', '2017-05-27 03:54:45');
INSERT INTO `tb_resource` VALUES ('6d0eb41b35ca4fffaecf0bb8604a342b', 'b098121732a44149b9cad2865cedeffa', '相关人物-新增UI', 'upRelation:addUI', '1', '/upRelation/addUI.html', null, '', '0', '', '2017-06-06 15:39:43', '2017-06-06 07:40:15');
INSERT INTO `tb_resource` VALUES ('6d1b50bed56b4348a32c6c2edc60acae', '44b40bba58294327bc23ef72ee3980d2', '作者名称管理', 'up:name', '1', '/upNameCtrl/listUI.html', null, '', '0', '作者名称管理', '2017-05-26 18:15:29', '2017-05-26 10:16:25');
INSERT INTO `tb_resource` VALUES ('6ec606e3e574477d8384a61c017807a0', 'a30f1d8804c240df97bb499d0c8060e6', '首页推荐视频列表', 'videoRecommend:list', '1', '/videoRecommendCtrl/list.html', null, '', '0', '首页推荐视频列表', '2017-05-27 11:59:38', '2017-05-27 04:00:34');
INSERT INTO `tb_resource` VALUES ('6fbbbe3bd93b42b6acd0be80a2f0371d', 'bd27a654ce814223b45045b42dcfb6c4', '评论管理', 'task:comment', '1', '/taskCommentCtrl/listUI.html', null, '', '0', '', '2017-09-12 16:06:16', '2017-09-12 08:06:35');
INSERT INTO `tb_resource` VALUES ('7', '6', 'Sirona监控', 'monitor:sirona', '0', '/sirona', '2', '', '0', 'Sirona监控', '2016-01-05 12:11:12', '2016-06-30 20:53:41');
INSERT INTO `tb_resource` VALUES ('70', '40', '编辑trigger', 'schedule:editTrigger', '1', '/scheduleJob/editTrigger.html', null, '', '0', '编辑trigger', '2016-12-29 14:03:47', '2016-12-29 14:03:47');
INSERT INTO `tb_resource` VALUES ('71', '50', '在线用户详情', 'session:info', '1', '/session/info.html', null, '', '0', '在线用户详情', '2016-12-30 17:49:32', '2016-12-30 17:49:32');
INSERT INTO `tb_resource` VALUES ('72', '3', '个人信息', 'user:infoUI', '1', '/userCtrl/infoUI.html', null, '', '0', '个人信息详页', '2017-01-03 18:03:06', '2017-05-26 09:01:59');
INSERT INTO `tb_resource` VALUES ('72e93101701540f4972db99204cfe66e', 'a30f1d8804c240df97bb499d0c8060e6', '修改首页推荐视频UI', 'videoRecommend:editUI', '1', '/videoRecommendCtrl/editUI.html', null, null, '0', '修改首页推荐视频UI', '2017-05-27 12:00:11', '2017-05-27 04:05:04');
INSERT INTO `tb_resource` VALUES ('73', '3', '个人信息编辑', 'user:info', '1', '/userCtrl/info.html', null, '', '0', '个人信息编辑', '2017-01-03 18:03:51', '2017-05-26 09:02:02');
INSERT INTO `tb_resource` VALUES ('73a012d6a09c4054a76a15d49c0326e6', 'a350ea4b4b7545b180fe28e0248fa256', '人物二级信息列表', 'upSecondLevel:list', '1', '/upSecondLevel/list.html', null, '', '0', '', '2017-06-06 15:31:22', '2017-06-06 07:31:54');
INSERT INTO `tb_resource` VALUES ('74', '3', '修改密码UI', 'user:passwork', '1', '/userCtrl/passwordUI.html', null, '', '0', '修改个人密码UI', '2017-01-03 18:05:13', '2017-05-26 09:01:44');
INSERT INTO `tb_resource` VALUES ('740ea2aadf594881a0d222aecd699357', '828f3f0130314d0598ab92c185cd804a', '新增人物事件UI', 'task:addUI', '1', '/taskCtrl/addUI.html', null, '', '0', '', '2017-09-11 15:13:53', '2017-09-11 07:14:11');
INSERT INTO `tb_resource` VALUES ('7424363d11ce4f0084b5480b6c38a2b8', '2ca2a216152b43e7bc7d21201504c9f7', '修改属性值关联UI', 'sysAttributeValue:editUI', '1', '/sysAttributeValueCtrl/editUI.html', null, '', '0', '修改属性值关联UI', '2017-05-26 17:45:54', '2017-05-26 09:46:50');
INSERT INTO `tb_resource` VALUES ('75', '3', '修改个人密码', 'user:password', '1', '/userCtrl/password.html', null, '', '0', '修改个人密码', '2017-01-03 18:05:58', '2017-05-26 09:01:47');
INSERT INTO `tb_resource` VALUES ('76f5294ed2cf42eb9f760c324883c17c', ' ', '忘记密码反馈管理', 'userForgetPassword', '0', '/userForgetPassword/listUI.html', null, 'fa  fa-question-circle', '0', '', '2017-06-19 18:01:42', '2017-06-19 10:11:45');
INSERT INTO `tb_resource` VALUES ('772bd25a45124f7889ba8420fdfe63d3', '09275dcc376a4758a1291f7e35a98816', '新增作者站点', 'upStation:add', '1', '/upStationCtrl/add.html', null, '', '0', '新增作者站点', '2017-05-26 18:07:28', '2017-05-26 10:08:24');
INSERT INTO `tb_resource` VALUES ('7af2c2fce93649f994d59bc0b861a0dc', '94c6029804d94e09acabd1b052b8aad9', '新增作者', 'up:add', '1', '/upCtrl/add.html', null, null, '0', '新增作者', '2017-05-26 18:02:43', '2017-05-27 03:54:56');
INSERT INTO `tb_resource` VALUES ('7c552f6bd948413692439849ce1fc294', 'eb965e126fc64c478e31570f00996942', '修改评论', 'videoComment:edit', '1', '/videoCommentCtrl/edit.html', null, '', '0', '修改评论', '2017-05-27 12:10:10', '2017-05-27 04:11:06');
INSERT INTO `tb_resource` VALUES ('7d3efa5f41c1486db4e8397b428f2def', '1435c92cc52c43b0968f6ea45077945b', '新增作者属性值关联UI', 'upValue:addUI', '1', '/upValueCtrl/addUI.html', null, '', '0', '新增作者属性值关联UI', '2017-05-26 18:13:27', '2017-05-26 10:14:23');
INSERT INTO `tb_resource` VALUES ('7d54e45d1b0844e9bb7eceb3a16f0afa', '2ca2a216152b43e7bc7d21201504c9f7', '删除属性值关联', 'sysAttributeValue:del', '1', '/sysAttributeValueCtrl/delete.html', null, '', '0', '删除属性值关联', '2017-05-26 17:46:17', '2017-05-26 09:47:13');
INSERT INTO `tb_resource` VALUES ('7dd0d01382ef4f26a4893ca8070d69ca', '1838ab94925345858e28a8c69cc08480', '修改视频属性值关联UI', 'videoValue:editUI', '1', '/videoValueCtrl/editUI.html', null, '', '0', '修改视频属性值关联UI', '2017-05-27 11:06:40', '2017-05-27 03:07:36');
INSERT INTO `tb_resource` VALUES ('7fdbd10db0684be8839769308546ff70', '1e3ee592b3e24218b25f75b1e94bd2e4', '添加', 'sysHelp:addUI', '1', '', null, '', '0', '', '2017-06-30 15:25:54', '2017-06-30 07:25:53');
INSERT INTO `tb_resource` VALUES ('8', '6', 'Druid监控', 'monitor:druid', '0', '/druid', '2', '', '0', 'Druid监控', '2016-01-11 22:45:27', '2016-06-30 20:53:44');
INSERT INTO `tb_resource` VALUES ('80663dbe57044beaae3dd6cd21253b97', '55b1bf2e961948bc96dd8641e13e11c7', '视频列表', 'video:list', '1', '/videoCtrl/list.html', null, null, '0', '视频列表', '2017-05-27 10:34:22', '2017-05-27 03:47:40');
INSERT INTO `tb_resource` VALUES ('828f3f0130314d0598ab92c185cd804a', 'bd27a654ce814223b45045b42dcfb6c4', '人物事件管理', 'task:task', '0', '/taskCtrl/listUI.html', null, '', '0', '', '2017-09-11 15:00:28', '2017-09-11 07:00:46');
INSERT INTO `tb_resource` VALUES ('830e0007f1e5415aba6ed53f04d1c228', 'b4ac084e4e0f4cdca0bd641728306e4a', '新增属性值UI', 'sysValue:addUI', '1', '/sysValueCtrl/addUI.html', null, '', '0', '新增属性值UI', '2017-05-26 17:29:24', '2017-05-26 09:30:20');
INSERT INTO `tb_resource` VALUES ('8353d0efb3bf4ba2a640f63490fc0b5a', 'c5f89e919d134649970103b2b369f39c', '相册图片-编辑', 'upPhoto:edit', '1', '/upPhoto/edit.html', null, '', '0', '', '2017-06-06 15:46:01', '2017-06-06 07:46:32');
INSERT INTO `tb_resource` VALUES ('8359efcf52da48dd8d3b92547681b1dd', '6fbbbe3bd93b42b6acd0be80a2f0371d', '新增评论UI', 'taskComment:addUI', '1', '/taskCommentCtrl/addUI.html', null, null, '0', '', '2017-09-12 16:03:12', '2017-09-12 08:08:24');
INSERT INTO `tb_resource` VALUES ('83ceee53cfc74898909e89646ef14c6d', 'c835d8e535204a60b57baa0125153c03', '修改视频专辑属性值关联', 'videoAlbumValue:edit', '1', '/videoAlbumValueCtrl/edit.html', null, '', '0', '修改视频专辑属性值关联', '2017-06-02 16:32:14', '2017-06-02 08:33:16');
INSERT INTO `tb_resource` VALUES ('843e8c6742824711bc3204e635072af3', '2d31d167da6b448d86bc37ba2800069c', '修改专辑', 'videoAlbum:edit', '1', '/videoAlbumCtrl/edit.html', null, '', '0', '修改专辑', '2017-05-27 11:39:34', '2017-05-27 03:40:29');
INSERT INTO `tb_resource` VALUES ('8552703085134681840fe90ecbc4ad55', '4', '查看角色下的用户列表', 'role:roleUserList', '1', '/role/roleUserList.html', null, '', '0', '查看角色下的用户列表', '2017-05-26 17:11:22', '2017-05-26 09:12:18');
INSERT INTO `tb_resource` VALUES ('8824e44dedb9481797d6fef8b460c249', '6b5a075cf27e415e9146e41f65eb1af1', '新增SEO配置', 'seoConfig:add', '1', '/seoConfigCtrl/add.html', null, null, '0', '新增SEO配置', '2017-06-27 14:36:55', '2017-06-27 06:37:55');
INSERT INTO `tb_resource` VALUES ('89', '2', '资源管理', 'system:resource', '0', '/resource/listGridUI.html', null, 'fa  fa-indent', '0', '资源管理', '2017-01-17 15:17:08', '2017-03-13 08:55:39');
INSERT INTO `tb_resource` VALUES ('89d7aba5f1064e88accd26cd9cdfff4b', '2ca2a216152b43e7bc7d21201504c9f7', '修改属性值关联', 'sysAttributeValue:edit', '1', '/sysAttributeValueCtrl/edit.html', null, '', '0', '修改属性值关联', '2017-05-26 17:45:28', '2017-05-26 09:46:24');
INSERT INTO `tb_resource` VALUES ('8ae38d91e1f14b27a9618a351ef75cf6', 'd6ac4a5c243f47d6ae9d87227e392dfc', '提交搜索关键字url管理', 'submit:url', '1', '/submitUrlCtrl/listUI.html', null, '', '0', '', '2017-06-23 11:23:14', '2017-06-23 03:23:16');
INSERT INTO `tb_resource` VALUES ('8ba237c3dbaf483eba4133ac70a0b683', 'b4ac084e4e0f4cdca0bd641728306e4a', '属性值列表', 'sysValue:list', '1', '/sysValueCtrl/list.html', null, '', '0', '属性值列表', '2017-05-26 17:31:12', '2017-05-26 09:41:33');
INSERT INTO `tb_resource` VALUES ('8be560e65d66441ba6a5cb4add8aa867', 'd6ac4a5c243f47d6ae9d87227e392dfc', '提交搜索关键字管理', 'submit:keyword', '1', '', null, '', '0', '', '2017-06-22 17:42:12', '2017-06-22 09:42:15');
INSERT INTO `tb_resource` VALUES ('8be86f4f7f6744e49e2068923a119d20', '9c0a0f61428d4570aeba088003907fa0', '新增属性UI', 'sysAttribute:addUI', '1', '/sysAttributeCtrl/addUI.html', null, null, '0', '新增属性UI', '2017-05-26 17:19:01', '2017-05-26 09:28:52');
INSERT INTO `tb_resource` VALUES ('8c5d32c86ff14237a5343406cc75c881', '2d31d167da6b448d86bc37ba2800069c', '专辑列表', 'videoAlbum:list', '1', '/videoAlbumCtrl/list.html', null, '', '0', '专辑列表', '2017-05-27 11:41:14', '2017-05-27 03:42:09');
INSERT INTO `tb_resource` VALUES ('8c669eefa7ae4504adc98d399d2c4185', '2eaa5101ef544796809bd7ebd67eeb9d', '修改视频站点', 'videoStation:edit', '1', '/videoStationCtrl/edit.html', null, '', '0', '修改视频站点', '2017-05-27 11:02:41', '2017-05-27 03:03:36');
INSERT INTO `tb_resource` VALUES ('8cfb8f0455a64f6bb37c4a7650289684', 'c835d8e535204a60b57baa0125153c03', '修改视频专辑属性值关联UI', 'videoAlbumValue:editUI', '1', '/videoAlbumValueCtrl/editUI.html', null, '', '0', '修改视频专辑属性值关联UI', '2017-06-02 16:31:49', '2017-06-02 08:32:51');
INSERT INTO `tb_resource` VALUES ('8e1df5fe37904ac6babb6de197b4176b', 'eb965e126fc64c478e31570f00996942', '新增评论', 'videoComment:add', '1', '/videoCommentCtrl/add.html', null, '', '0', '新增评论', '2017-05-27 12:09:21', '2017-05-27 04:10:16');
INSERT INTO `tb_resource` VALUES ('8f219d78691741d5bde5b52d34aac922', '828f3f0130314d0598ab92c185cd804a', '修改人物事件UI', 'task:editUI', '1', '/taskCtrl/editUI.html', null, null, '0', '', '2017-09-11 15:12:31', '2017-09-11 09:35:15');
INSERT INTO `tb_resource` VALUES ('8f3a755650df4b06ac24c57b73cc2594', '093259a287a944bbb70ab800a41cde12', '游戏管理', 'sys:game', '0', '/sysGameCtrl/listUI.html', null, '', '0', '游戏管理', '2017-05-26 17:47:13', '2017-05-26 09:48:09');
INSERT INTO `tb_resource` VALUES ('9', null, '日志信息管理', 'logininfo', '0', '', '1', 'fa fa-tag', '0', '日志信息管理', '2016-01-11 22:46:39', '2016-02-25 14:07:48');
INSERT INTO `tb_resource` VALUES ('90', '89', '资源列表', 'resource:listGrid', '1', '/resource/listGrid.html', null, null, '0', '资源列表', '2017-01-17 15:21:21', '2017-03-13 08:55:42');
INSERT INTO `tb_resource` VALUES ('90bcec4b83904a7fba0dfd957c83d74d', '6b5a075cf27e415e9146e41f65eb1af1', '新增字典UI', 'seoConfig:addUI', '1', '/seoConfigCtrl/addUI.html', null, '', '0', '新增字典UI', '2017-06-27 14:36:22', '2017-06-27 06:36:30');
INSERT INTO `tb_resource` VALUES ('94c6029804d94e09acabd1b052b8aad9', '44b40bba58294327bc23ef72ee3980d2', '作者管理', 'up:up', '0', '/upCtrl/listUI.html', null, '', '0', '作者管理', '2017-05-27 11:52:15', '2017-05-27 03:53:10');
INSERT INTO `tb_resource` VALUES ('955a6f0c0f27489f91b82d683bec217a', '6d1b50bed56b4348a32c6c2edc60acae', '新增作者名称', 'upName:add', '1', '/upNameCtrl/add.html', null, '', '0', '新增作者名称', '2017-05-27 10:28:10', '2017-05-27 02:29:06');
INSERT INTO `tb_resource` VALUES ('96f3896fe50a46038c56cea524e2b14d', '1435c92cc52c43b0968f6ea45077945b', '新增作者属性值关联', 'upValue:add', '1', '/upValueCtrl/add.html', null, '', '0', '新增作者属性值关联', '2017-05-26 18:13:55', '2017-05-26 10:14:51');
INSERT INTO `tb_resource` VALUES ('97e8437e73d544a0a409eb6b5e998233', '093259a287a944bbb70ab800a41cde12', '游戏属性值关联管理', 'sys:GameValue', '0', '/sysGameValueCtrl/listUI.html', null, '', '0', '游戏属性值关联管理', '2017-05-26 17:51:29', '2017-05-26 09:52:25');
INSERT INTO `tb_resource` VALUES ('98cca6340e7e458c9117ac7a648a9d0a', '8be560e65d66441ba6a5cb4add8aa867', '新增提交搜索关键字', 'submitKeyword:add', '1', '/submitKeywordCtrl/add.html', null, null, '0', '', '2017-06-20 16:33:20', '2017-06-22 09:42:33');
INSERT INTO `tb_resource` VALUES ('997f0ec412de40bebbe8842e1b4d384f', '55b1bf2e961948bc96dd8641e13e11c7', '新增视频UI', 'video:addUI', '1', '/videoCtrl/addUI.html', null, null, '0', '新增视频UI', '2017-05-27 10:50:39', '2017-05-27 03:48:06');
INSERT INTO `tb_resource` VALUES ('9a7431bfcd684101ad855024fc5cc420', 'b4ac084e4e0f4cdca0bd641728306e4a', '新增属性值', 'sysValue:add', '1', '/sysValueCtrl/add.html', null, '', '0', '新增属性值', '2017-05-26 17:29:59', '2017-05-26 09:41:44');
INSERT INTO `tb_resource` VALUES ('9c0a0f61428d4570aeba088003907fa0', '093259a287a944bbb70ab800a41cde12', '属性管理', 'sys:attribute', '0', '/sysAttributeCtrl/listUI.html', null, '', '0', '属性管理', '2017-05-26 17:20:59', '2017-05-26 09:21:55');
INSERT INTO `tb_resource` VALUES ('9d8002279d2d4aabb3b0ab4b832b3445', '2eaa5101ef544796809bd7ebd67eeb9d', '删除视频站点', 'videoStation:del', '1', '/videoStationCtrl/delete.html', null, '', '0', '删除视频站点', '2017-05-27 11:01:55', '2017-05-27 03:02:50');
INSERT INTO `tb_resource` VALUES ('a0571f0aed8b47b48742c97d9c6176bd', 'a350ea4b4b7545b180fe28e0248fa256', '人物二级信息-编辑UI', 'upSecondLevel:editUI', '1', '/upSecondLevel/editUI.html', null, '', '0', '', '2017-06-06 15:34:12', '2017-06-06 07:34:43');
INSERT INTO `tb_resource` VALUES ('a073bfb000ef4ed6a912925a30ca43f9', 'c835d8e535204a60b57baa0125153c03', '新增视频专辑属性值关联UI', 'videoAlbumValue:addUI', '1', '/videoAlbumValueCtrl/addUI.html', null, null, '0', '新增视频专辑属性值关联UI', '2017-06-02 16:30:12', '2017-06-02 08:31:31');
INSERT INTO `tb_resource` VALUES ('a2ab97c66b9f4f419354466d6c99bebe', '9c0a0f61428d4570aeba088003907fa0', '新增属性', 'sysAttribute:add', '1', '/sysAttributeCtrl/add.html', null, '', '0', '新增属性', '2017-05-26 17:22:54', '2017-05-26 09:28:54');
INSERT INTO `tb_resource` VALUES ('a30f1d8804c240df97bb499d0c8060e6', 'f2fc7fa7af13499ab78926ebda8f5aca', '首页推荐视频管理', 'video:recommend', '0', '/videoRecommendCtrl/listUI.html', null, '', '0', '首页推荐视频管理', '2017-05-27 11:55:31', '2017-05-27 03:56:27');
INSERT INTO `tb_resource` VALUES ('a350ea4b4b7545b180fe28e0248fa256', '44b40bba58294327bc23ef72ee3980d2', '人物二级信息管理', 'up:upSecondLevel', '1', '/upSecondLevel/listUI.html', null, null, '0', '', '2017-06-06 15:09:16', '2017-06-06 07:54:18');
INSERT INTO `tb_resource` VALUES ('a35f564815a6423a8eb8f9728fb88a78', '8be560e65d66441ba6a5cb4add8aa867', '新增提交搜索关键字UI', 'submitKeyword:addUI', '1', '/submitKeywordCtrl/addUI.html', null, null, '0', '', '2017-06-20 16:31:51', '2017-06-22 09:42:46');
INSERT INTO `tb_resource` VALUES ('a858241945824eca8f8346561734fd3f', 'c5f89e919d134649970103b2b369f39c', '相册图片列表', 'upPhoto:list', '1', '/upPhoto/list.html', null, null, '0', '', '2017-06-06 15:42:39', '2017-06-06 08:00:33');
INSERT INTO `tb_resource` VALUES ('a8b9b8f6e73b46c3b3109eb011f1bb6c', '94c6029804d94e09acabd1b052b8aad9', '作者列表', 'up:list', '1', '/upCtrl/list.html', null, null, '0', '作者列表', '2017-05-26 17:59:12', '2017-05-27 03:53:28');
INSERT INTO `tb_resource` VALUES ('aa148ab403fb40ca82ff840d38f1ff1a', '09275dcc376a4758a1291f7e35a98816', '删除作者站点', 'upStation:del', '1', '/upStationCtrl/delete.html', null, '', '0', '删除作者站点', '2017-05-26 18:07:57', '2017-05-26 10:08:53');
INSERT INTO `tb_resource` VALUES ('ab328143ae1c4fefb856eca3380d3f2a', '00254d3a9ab54a8199a2c147ec0648f2', '新增属性值UI', 'taskValue:addUI', '1', '/taskValueCtrl/addUI.html', null, '', '0', '', '2017-09-22 17:15:59', '2017-09-22 09:16:32');
INSERT INTO `tb_resource` VALUES ('ad6c19c0adbd4bbe95f3df5485f1c052', '1435c92cc52c43b0968f6ea45077945b', '删除作者属性值关联', 'upValue:del', '1', '/upValueCtrl/delete.html', null, '', '0', '删除作者属性值关联', '2017-05-26 18:14:25', '2017-05-26 10:15:21');
INSERT INTO `tb_resource` VALUES ('aea669db4d77414dbae79c2c0a58a6d0', '55b1bf2e961948bc96dd8641e13e11c7', '新增视频', 'video:add', '1', '/videoCtrl/add.html', null, null, '0', '新增视频', '2017-05-27 10:50:12', '2017-05-27 03:48:17');
INSERT INTO `tb_resource` VALUES ('b098121732a44149b9cad2865cedeffa', '44b40bba58294327bc23ef72ee3980d2', '相关人物管理', 'up:upRelation', '1', '/upRelation/listUI.html', null, null, '0', '', '2017-06-06 15:11:58', '2017-06-06 07:54:34');
INSERT INTO `tb_resource` VALUES ('b3bb0302d22e401ea97a5c190c6d1b59', '8f3a755650df4b06ac24c57b73cc2594', '新增游戏', 'sysGame:add', '1', '/sysGameCtrl/add.html', null, '', '0', '新增游戏', '2017-05-26 17:49:12', '2017-05-26 09:50:08');
INSERT INTO `tb_resource` VALUES ('b3c822a9b68e4114bbad0e2194197b95', 'eb965e126fc64c478e31570f00996942', '删除评论', 'videoComment:del', '1', '/videoCommentCtrl/delete.html', null, '', '0', '删除评论', '2017-05-27 12:09:46', '2017-05-27 04:10:41');
INSERT INTO `tb_resource` VALUES ('b40165fe92114e48993f75ebcec48e0e', '94c6029804d94e09acabd1b052b8aad9', '新增作者UI', 'up:addUI', '1', '/upCtrl/addUI.html', null, null, '0', '新增作者UI', '2017-05-26 18:01:05', '2017-05-27 03:55:06');
INSERT INTO `tb_resource` VALUES ('b48d724277c14e3fb51c91e5f2355383', '1435c92cc52c43b0968f6ea45077945b', '修改作者属性值关联UI', 'upValue:editUI', '1', '/upValueCtrl/editUI.html', null, '', '0', '修改作者属性值关联UI', '2017-05-26 18:12:59', '2017-05-26 10:13:56');
INSERT INTO `tb_resource` VALUES ('b4ac084e4e0f4cdca0bd641728306e4a', '093259a287a944bbb70ab800a41cde12', '属性值管理', 'sys:value', '0', '/sysValueCtrl/listUI.html', null, '', '0', '属性值管理', '2017-05-26 17:28:46', '2017-05-26 09:29:42');
INSERT INTO `tb_resource` VALUES ('b58ec3c4ba5c471f8f2d6ea4ed0e9a0c', '6fbbbe3bd93b42b6acd0be80a2f0371d', '新增评论', 'taskComment:add', '1', '/taskCommentCtrl/add.html', null, null, '0', '', '2017-09-12 15:51:48', '2017-09-12 08:08:50');
INSERT INTO `tb_resource` VALUES ('b77280c530e145b89b563e271c95a47d', '2d31d167da6b448d86bc37ba2800069c', '视频列表', 'videoAlbum:batchVideoUI', '1', '/videoAlbumCtrl/batchVideoUI.html', null, '', '0', '', '2017-08-23 16:58:17', '2017-08-23 08:58:35');
INSERT INTO `tb_resource` VALUES ('b7b6c381d02e474794b9805b9292164b', '97e8437e73d544a0a409eb6b5e998233', '删除游戏属性值关联', 'sysGameValue:del', '1', '/sysGameValueCtrl/delete.html', null, '', '0', '删除游戏属性值关联', '2017-05-26 17:53:23', '2017-05-26 09:54:19');
INSERT INTO `tb_resource` VALUES ('b949b6ed49ac4f2e96e706bcee0046c2', '8ae38d91e1f14b27a9618a351ef75cf6', '提交搜索关键字url查询列表', 'submitUrl:list', '1', '/submitUrlCtrl/list.html', null, '', '0', '', '2017-06-23 11:47:46', '2017-06-23 03:47:48');
INSERT INTO `tb_resource` VALUES ('ba8885c430b64483baf0f59102fea467', '6d1b50bed56b4348a32c6c2edc60acae', '修改作者名称UI', 'upName:editUI', '1', '/upNameCtrl/editUI.html', null, '', '0', '修改作者名称UI', '2017-05-26 18:16:32', '2017-05-26 10:17:28');
INSERT INTO `tb_resource` VALUES ('ba95e2f953e94030b0cf2f8fa09462fa', '55b1bf2e961948bc96dd8641e13e11c7', '选择视频查询列表UI', 'video:searchListUI', '1', '/videoCtrl/searchListUI.html', null, null, '0', '选择视频查询列表UI', '2017-05-27 10:51:04', '2017-05-27 03:48:29');
INSERT INTO `tb_resource` VALUES ('bbbf9dc00602444fbcfea9760a199add', 'f2fc7fa7af13499ab78926ebda8f5aca', '合并视频', 'video:mergeUI', '0', '/videoCtrl/mergeUI.html', null, '', '0', '合并视频', '2017-05-27 10:56:26', '2017-05-27 02:57:21');
INSERT INTO `tb_resource` VALUES ('bd2460507555487ab15a019a40bf8b83', '9c0a0f61428d4570aeba088003907fa0', '修改属性', 'sysAttribute:edit', '1', '/sysAttributeCtrl/edit.html', null, '', '0', '修改属性', '2017-05-26 17:23:57', '2017-05-26 09:28:45');
INSERT INTO `tb_resource` VALUES ('bd27a654ce814223b45045b42dcfb6c4', ' ', '人物事件管理', 'task', '0', '', null, 'fa  fa-certificate', '0', '', '2017-09-11 14:59:10', '2017-09-11 06:59:28');
INSERT INTO `tb_resource` VALUES ('be1a3f3787584bba8f7f0ab09ab066fb', '97e8437e73d544a0a409eb6b5e998233', '新增游戏属性值关联UI', 'sysGameValue:addUI', '1', '/sysGameValueCtrl/addUI.html', null, '', '0', '新增游戏属性值关联UI', '2017-05-26 17:52:31', '2017-05-26 09:53:28');
INSERT INTO `tb_resource` VALUES ('be6073163807434da294f1d0d3157568', '00254d3a9ab54a8199a2c147ec0648f2', '修改属性值UI', 'taskValue:editUI', '1', '/taskValueCtrl/editUI.html', null, '', '0', '', '2017-09-22 17:19:24', '2017-09-22 09:19:57');
INSERT INTO `tb_resource` VALUES ('bfa0b1f1a04f466393579db482c3889f', '97e8437e73d544a0a409eb6b5e998233', '修改游戏属性值关联', 'sysGameValue:edit', '1', '/sysGameValueCtrl/edit.html', null, '', '0', '修改游戏属性值关联', '2017-05-26 17:53:49', '2017-05-26 09:54:45');
INSERT INTO `tb_resource` VALUES ('c0189e086e55439a98ab26efbd64c9ef', '9c0a0f61428d4570aeba088003907fa0', '修改属性UI', 'sysAttribute:editUI', '1', '/sysAttributeCtrl/editUI.html', null, '', '0', '修改属性UI', '2017-05-26 17:23:27', '2017-05-26 09:28:43');
INSERT INTO `tb_resource` VALUES ('c34b6d723290450ca9b2f60a28afcc9a', '76f5294ed2cf42eb9f760c324883c17c', '忘记密码反馈列表', 'userForgetPassword:list', '1', '/userForgetPassword/list.html', null, null, '0', '', '2017-06-19 18:03:01', '2017-06-19 10:11:59');
INSERT INTO `tb_resource` VALUES ('c406877b260e4763b34070c89a76fd44', ' ', '爬虫管理', 'crawler', '0', '', null, 'fa  fa-exchange', '0', '', '2017-06-13 18:22:56', '2017-06-13 10:25:52');
INSERT INTO `tb_resource` VALUES ('c5f89e919d134649970103b2b369f39c', '44b40bba58294327bc23ef72ee3980d2', '相册图片管理', 'up:upPhoto', '1', '/upPhoto/listUI.html', null, null, '0', '', '2017-06-06 15:12:51', '2017-06-06 07:54:50');
INSERT INTO `tb_resource` VALUES ('c76fa973dd9a48aa84a211351c1311c7', '09275dcc376a4758a1291f7e35a98816', '修改作者站点', 'upStation:edit', '1', '/upStationCtrl/edit.html', null, '', '0', '修改作者站点', '2017-05-26 18:09:52', '2017-05-26 10:10:48');
INSERT INTO `tb_resource` VALUES ('c835d8e535204a60b57baa0125153c03', 'f2fc7fa7af13499ab78926ebda8f5aca', '视频专辑属性值关联管理', 'video:albumValue', '1', '/videoAlbumValueCtrl/listUI.html', null, '', '0', '视频专辑属性值关联管理', '2017-06-02 16:29:45', '2017-06-02 08:30:47');
INSERT INTO `tb_resource` VALUES ('c92971b36c61413ab935ea93a35e819b', '3341407c42b045a29366c28055accd51', '合并视频作者', 'up:merge', '1', '/upCtrl/merge.html', null, '', '0', '合并视频作者', '2017-05-27 10:58:23', '2017-05-27 02:59:19');
INSERT INTO `tb_resource` VALUES ('c9e343bcd24247b7af6162ffe11e2c2c', '8f3a755650df4b06ac24c57b73cc2594', '删除游戏', 'sysGame:del', '1', '/sysGameCtrl/delete.html', null, '', '0', '删除游戏', '2017-05-26 17:50:24', '2017-05-26 09:51:20');
INSERT INTO `tb_resource` VALUES ('ca6c3f9ffaad464782f8a283a7e19a93', 'eb965e126fc64c478e31570f00996942', '评论列表', 'videoComment:list', '1', '/videoCommentCtrl/list.html', null, null, '0', '评论列表', '2017-05-27 12:06:56', '2017-05-27 04:09:48');
INSERT INTO `tb_resource` VALUES ('cb602c63b9de440a80c045bea80ff553', '2d31d167da6b448d86bc37ba2800069c', '修改专辑UI', 'videoAlbum:editUI', '1', '/videoAlbumCtrl/editUI.html', null, '', '0', '修改专辑UI', '2017-05-27 11:39:02', '2017-05-27 03:39:57');
INSERT INTO `tb_resource` VALUES ('cd85ac80a4a04d44a589d49edfc76fca', 'eb965e126fc64c478e31570f00996942', '修改评论UI', 'videoComment:editUI', '1', '/videoCommentCtrl/editUI.html', null, '', '0', '修改评论UI', '2017-05-27 12:07:28', '2017-05-27 04:08:23');
INSERT INTO `tb_resource` VALUES ('cdf07f2b376046afa663ba26e9128dc6', '6fbbbe3bd93b42b6acd0be80a2f0371d', '评论列表', 'taskComment:list', '1', '/taskCommentCtrl/list.html', null, null, '0', '', '2017-09-12 16:01:45', '2017-09-12 08:09:11');
INSERT INTO `tb_resource` VALUES ('cdfb447c50ac48b9b447567db5d6d4d3', '94c6029804d94e09acabd1b052b8aad9', '选择作者查询列表UI', 'up:searchListUI', '1', '/upCtrl/searchListUI.html', null, null, '0', '选择作者查询列表UI', '2017-05-26 18:05:00', '2017-05-27 03:55:17');
INSERT INTO `tb_resource` VALUES ('ce93bbdc91ed4261b693dd7856818f01', 'c5f89e919d134649970103b2b369f39c', '相册图片-新增UI', 'upPhoto:addUI', '1', '/upPhoto/addUI.html', null, '', '0', '', '2017-06-06 15:43:39', '2017-06-06 07:44:11');
INSERT INTO `tb_resource` VALUES ('cec379bd216a4607bfe96f171199290f', '9c0a0f61428d4570aeba088003907fa0', '删除属性', 'sysAttribute:del', '1', '/sysAttributeCtrl/delete.html', null, '', '0', '删除属性', '2017-05-26 17:24:24', '2017-05-26 09:42:58');
INSERT INTO `tb_resource` VALUES ('d19859f2ac1f400498dc4c52419458fe', '00254d3a9ab54a8199a2c147ec0648f2', '删除属性值', 'taskValue:del', '1', '/taskValueCtrl/delete.html', null, '', '0', '', '2017-09-22 17:18:34', '2017-09-22 09:19:07');
INSERT INTO `tb_resource` VALUES ('d2f4197a33384c898a225d347da1e87d', '97e8437e73d544a0a409eb6b5e998233', '修改游戏属性值关联UI', 'sysGameValue:editUI', '1', '/sysGameValueCtrl/editUI.html', null, '', '0', '修改游戏属性值关联UI', '2017-05-26 17:52:55', '2017-05-26 09:53:51');
INSERT INTO `tb_resource` VALUES ('d41dcf6c6bc4416689f7bc67d41e3b51', '00254d3a9ab54a8199a2c147ec0648f2', '新增属性值', 'task:Value:add', '1', '/taskValueCtrl/add.html', null, '', '0', '', '2017-09-22 17:17:23', '2017-09-22 09:17:56');
INSERT INTO `tb_resource` VALUES ('d5ead8511d1b48afa42e7c3fd2a12f9d', '97e8437e73d544a0a409eb6b5e998233', '游戏属性值关联列表', 'sysGameValue:list', '1', '/sysGameValueCtrl/list.html', null, '', '0', '游戏属性值关联列表', '2017-05-26 17:55:37', '2017-05-26 09:56:33');
INSERT INTO `tb_resource` VALUES ('d60bfade7d7646708674bca303936222', 'b4ac084e4e0f4cdca0bd641728306e4a', '删除属性值', 'sysValue:del', '1', '/sysValueCtrl/delete.html', null, '', '0', '删除属性值', '2017-05-26 17:30:56', '2017-05-26 09:43:01');
INSERT INTO `tb_resource` VALUES ('d6ac4a5c243f47d6ae9d87227e392dfc', ' ', '提交搜索关键字管理', 'submit', '0', '/submitKeywordCtrl/listUI.html', null, 'fa  fa-pencil', '0', '', '2017-06-20 16:25:23', '2017-06-22 09:41:39');
INSERT INTO `tb_resource` VALUES ('d8330ec12bbe47baab75f372bc264f79', '828f3f0130314d0598ab92c185cd804a', '修改人物事件', 'task:edit', '1', '/taskCtrl/edit.html', null, null, '0', '', '2017-09-11 15:10:45', '2017-09-11 07:13:19');
INSERT INTO `tb_resource` VALUES ('d93269629ccb41218fab9b0eb9ce409c', '55b1bf2e961948bc96dd8641e13e11c7', '修改视频', 'video:edit', '1', '/videoCtrl/edit.html', null, null, '0', '修改视频', '2017-05-27 10:48:54', '2017-05-27 03:48:40');
INSERT INTO `tb_resource` VALUES ('dba6c414c5ab445794e1c364aae1c124', 'eb965e126fc64c478e31570f00996942', '新增评论UI', 'videoComment:addUI', '1', '/videoCommentCtrl/addUI.html', null, '', '0', '新增评论UI', '2017-05-27 12:08:35', '2017-05-27 04:09:30');
INSERT INTO `tb_resource` VALUES ('dc87ae762af94c849c7b965184878854', '3cf54176dd154e97b9f3adc814aac7bd', '视频标签举报list', 'videoValueInform:list', '1', '/videoValueInform/list.html', null, '', '0', '', '2017-08-22 14:52:37', '2017-08-22 06:54:08');
INSERT INTO `tb_resource` VALUES ('deec8cc689164e51be587cb4de1c1955', 'c835d8e535204a60b57baa0125153c03', '视频专辑属性值关联列表', 'videoAlbumValue:list', '1', '/videoAlbumValueCtrl/list.html', null, null, '0', '视频专辑属性值关联列表', '2017-06-02 16:31:21', '2017-06-02 08:45:05');
INSERT INTO `tb_resource` VALUES ('e0a553683f1a4c31a0e63c61cc66d4ae', 'a350ea4b4b7545b180fe28e0248fa256', '人物二级信息-新增', 'upSecondLevel:add', '1', '/upSecondLevel/add.html', null, '', '0', '', '2017-06-06 15:33:25', '2017-06-06 07:33:56');
INSERT INTO `tb_resource` VALUES ('e14858e1d66a430db205f05618bc4e6c', '6d1b50bed56b4348a32c6c2edc60acae', '作者名称列表', 'upName:list', '1', '/upNameCtrl/list.html', null, '', '0', '作者名称列表', '2017-05-26 18:15:56', '2017-05-26 10:16:52');
INSERT INTO `tb_resource` VALUES ('e1a9992a70fb485caff03514dabb69db', '6b5a075cf27e415e9146e41f65eb1af1', '编辑SEO配置UI', 'seoConfig:editUI', '1', '/seoConfigCtrl/editUI.html', null, null, '0', '编辑SEO配置UI', '2017-06-27 14:37:30', '2017-06-27 06:38:05');
INSERT INTO `tb_resource` VALUES ('e205b882b1d64c618a5ed739cce1d760', '2eaa5101ef544796809bd7ebd67eeb9d', '视频站点列表', 'videoStation:list', '1', '/videoStationCtrl/list.html', null, '', '0', '视频站点列表', '2017-05-27 11:00:00', '2017-05-27 03:00:55');
INSERT INTO `tb_resource` VALUES ('e247cbb5681048a59999709db903d974', 'c5f89e919d134649970103b2b369f39c', '相册图片-新增', 'upPhoto:add', '1', '/upPhoto/add.html', null, '', '0', '', '2017-06-06 15:44:28', '2017-06-06 07:44:59');
INSERT INTO `tb_resource` VALUES ('e41b149d5e134a7c8b0f37b7d7b51197', '55b1bf2e961948bc96dd8641e13e11c7', '修改视频UI', 'videoVideo:editUI', '1', '/videoCtrl/editUI.html', null, null, '0', '修改视频UI', '2017-05-27 10:41:34', '2017-05-27 03:48:51');
INSERT INTO `tb_resource` VALUES ('e4eb75fce5a642e290e5a6d2dad0c4ec', '6fbbbe3bd93b42b6acd0be80a2f0371d', '删除评论', 'taskComment:del', '1', '/taskCommentCtrl/delete.html', null, null, '0', '', '2017-09-12 15:57:04', '2017-09-12 08:09:34');
INSERT INTO `tb_resource` VALUES ('e9a7bc0806a547d7b94e9aaaad78e3eb', 'b4ac084e4e0f4cdca0bd641728306e4a', '选择属性值查询列表UI', 'sysValue:searchListUI', '1', '/sysValueCtrl/searchListUI.html', null, '', '0', '选择属性值查询列表UI', '2017-05-26 17:31:43', '2017-05-26 09:41:26');
INSERT INTO `tb_resource` VALUES ('e9d26726a9c64e1c9898778d3773bed1', 'b4ac084e4e0f4cdca0bd641728306e4a', '修改属性值', 'sysValue:edit', '1', '/sysValueCtrl/edit.html', null, '', '0', '修改属性值', '2017-05-26 17:30:20', '2017-05-26 09:41:38');
INSERT INTO `tb_resource` VALUES ('eb43c46b034a4a3cb68724359f52a949', '6b5a075cf27e415e9146e41f65eb1af1', '编辑SEO配置', 'seoConfig:edit', '1', '/seoConfigCtrl/edit.html', null, '', '0', '编辑SEO配置', '2017-06-27 14:38:49', '2017-06-27 06:38:58');
INSERT INTO `tb_resource` VALUES ('eb965e126fc64c478e31570f00996942', 'f2fc7fa7af13499ab78926ebda8f5aca', '评论管理', 'video:comment', '1', '/videoCommentCtrl/listUI.html', null, '', '0', '评论管理', '2017-05-27 12:06:14', '2017-05-27 04:07:10');
INSERT INTO `tb_resource` VALUES ('ebe4b4d7740c4eb1bf2d852c4c6f6179', '00254d3a9ab54a8199a2c147ec0648f2', '修改属性值', 'taskValue:edit', '1', '/taskValueCtrl/edit.html', null, '', '0', '', '2017-09-22 17:20:24', '2017-09-22 09:20:57');
INSERT INTO `tb_resource` VALUES ('ec2c4328b78e44b8922ce10845d769c0', '828f3f0130314d0598ab92c185cd804a', '删除人物事件', 'task:del', '1', '/taskCtrl/delete.html', null, '', '0', '', '2017-09-11 15:16:14', '2017-09-11 07:16:32');
INSERT INTO `tb_resource` VALUES ('ee352f364bc64756902c903dd61111b2', '9c0a0f61428d4570aeba088003907fa0', '属性列表', 'sysAttribute:list', '1', '/sysAttributeCtrl/list.html', null, null, '0', '属性列表', '2017-05-26 17:19:43', '2017-05-26 09:28:41');
INSERT INTO `tb_resource` VALUES ('ee58288eaaff44e495a34ed3bd0da587', '2d31d167da6b448d86bc37ba2800069c', '选择专辑查询列表', 'videoAlbum:searchList', '1', '/videoAlbumCtrl/searchList.html', null, '', '0', '选择专辑查询列表', '2017-05-27 11:44:31', '2017-05-27 03:45:26');
INSERT INTO `tb_resource` VALUES ('f0fd118a7e6e4b648d8b8f63d75e1e0b', '6d1b50bed56b4348a32c6c2edc60acae', '修改作者名称', 'upName:edit', '1', '/upNameCtrl/edit.html', null, '', '0', '修改作者名称', '2017-05-27 10:26:49', '2017-05-27 02:27:44');
INSERT INTO `tb_resource` VALUES ('f2fc7fa7af13499ab78926ebda8f5aca', ' ', '视频管理', 'video', '0', '', null, 'fa  fa-compass', '0', '视频管理', '2017-05-26 17:13:29', '2017-05-27 03:47:27');
INSERT INTO `tb_resource` VALUES ('f5972ec9b0df41909ad0730b721a776f', 'b4ac084e4e0f4cdca0bd641728306e4a', '修改属性值UI', 'sysValue:editUI', '1', '/sysValueCtrl/editUI.html', null, '', '0', '修改属性值UI', '2017-05-26 17:30:39', '2017-05-26 09:41:35');
INSERT INTO `tb_resource` VALUES ('f5e24c1f16a9454099d0adddecb86e70', 'c5f89e919d134649970103b2b369f39c', '相册图片-编辑UI', 'upPhoto:editUI', '1', '/upPhoto/editUI.html', null, '', '0', '', '2017-06-06 15:45:19', '2017-06-06 07:45:50');
INSERT INTO `tb_resource` VALUES ('f7c0fee7b92645e69935c24ade95285c', '1435c92cc52c43b0968f6ea45077945b', '作者属性值关联列表', 'upValue:list', '1', '/upValueCtrl/list.html', null, '', '0', '作者属性值关联列表', '2017-05-26 18:12:32', '2017-05-26 10:13:28');
INSERT INTO `tb_resource` VALUES ('fc10cc8676414d63807536b1ad7c9227', '1838ab94925345858e28a8c69cc08480', '修改视频属性值关联', 'videoValue:edit', '1', '/videoValueCtrl/edit.html', null, '', '0', '修改视频属性值关联', '2017-05-27 11:04:50', '2017-05-27 03:05:46');
INSERT INTO `tb_resource` VALUES ('fc4b102e5ebc4aea8e6b69046a86454a', '2d31d167da6b448d86bc37ba2800069c', '新增专辑', 'videoAlbum:add', '1', '/videoAlbumCtrl/add.html', null, '', '0', '新增专辑', '2017-05-27 11:40:43', '2017-05-27 03:41:39');
INSERT INTO `tb_resource` VALUES ('feae88aca2ef4e64873ff4ab3eaae850', '1838ab94925345858e28a8c69cc08480', '新增视频属性值关联', 'videoValue:add', '1', '/videoValueCtrl/add.html', null, '', '0', '新增视频属性值关联', '2017-05-27 11:05:48', '2017-05-27 03:06:43');
INSERT INTO `tb_resource` VALUES ('ff5523429bad4cd9b6cc698df2f04f55', '8be560e65d66441ba6a5cb4add8aa867', '修改提交搜索关键字', 'submitKeyword:edit', '1', '/submitKeywordCtrl/deit.html', null, null, '0', '', '2017-06-20 16:35:01', '2017-06-22 09:43:24');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `key` varchar(50) NOT NULL COMMENT 'key',
  `type` int(4) DEFAULT NULL COMMENT '角色类型，0：网站官方组  1：普通用户组',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', '超级管理员', 'administrator', '0', '超级管理员', '1', null, '0', null, null);
INSERT INTO `tb_role` VALUES ('1c2b75b31a04477f93ce60b82abbb80a', '管理员', 'admin', '0', '', '1', null, '0', null, null);

-- ----------------------------
-- Table structure for tb_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_resources`;
CREATE TABLE `tb_role_resources` (
  `id` varchar(32) NOT NULL,
  `s_id` varchar(32) DEFAULT NULL COMMENT '资源id',
  `r_id` varchar(32) DEFAULT NULL COMMENT '角色id',
  `t_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_r_resource_role` (`s_id`) USING BTREE,
  KEY `FK_r_role_resource` (`r_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限映射表';

-- ----------------------------
-- Records of tb_role_resources
-- ----------------------------
INSERT INTO `tb_role_resources` VALUES ('0137a0d538f34aeba858a46c5ba9aeb1', 'c34b6d723290450ca9b2f60a28afcc9a', '1', '2017-06-19 18:03:01');
INSERT INTO `tb_role_resources` VALUES ('02287bd475f24fe9ab7e0a02ee57ebe3', 'fc4b102e5ebc4aea8e6b69046a86454a', '1', '2017-05-27 11:40:43');
INSERT INTO `tb_role_resources` VALUES ('022e4e7d0ffe4cd0a45b1a09e29adf3f', 'cdfb447c50ac48b9b447567db5d6d4d3', '1', '2017-05-26 18:05:00');
INSERT INTO `tb_role_resources` VALUES ('038883624e6c40339753ff982cbee251', '44fa3becedf246d1bd2ce244f9bad2b3', '1', '2017-05-26 17:49:37');
INSERT INTO `tb_role_resources` VALUES ('03fa55a744ae43208249ab73904e1530', '27b0ce6eea554946b198e97d09ce7c2c', '1', '2017-06-06 15:40:29');
INSERT INTO `tb_role_resources` VALUES ('0650429a8fbd4bdd855d9d15ea34c5b3', 'ca6c3f9ffaad464782f8a283a7e19a93', '1', '2017-05-27 12:06:56');
INSERT INTO `tb_role_resources` VALUES ('0a79a7b6530b42de893a6242c4077a5d', '8be86f4f7f6744e49e2068923a119d20', '1', '2017-05-26 17:19:01');
INSERT INTO `tb_role_resources` VALUES ('0b75761085ef4fdba75e75537a8288ef', 'e9a7bc0806a547d7b94e9aaaad78e3eb', '1', '2017-05-26 17:31:43');
INSERT INTO `tb_role_resources` VALUES ('0dccc1cdad0345dabed156262eeab395', '90bcec4b83904a7fba0dfd957c83d74d', '1', '2017-06-27 14:36:22');
INSERT INTO `tb_role_resources` VALUES ('0e980fbbd02b4f45ba329798227c01a5', 'ee58288eaaff44e495a34ed3bd0da587', '1', '2017-05-27 11:44:31');
INSERT INTO `tb_role_resources` VALUES ('112d46dab7f841b7b0b6755887d86bd1', 'd6ac4a5c243f47d6ae9d87227e392dfc', '1', '2017-06-20 16:25:23');
INSERT INTO `tb_role_resources` VALUES ('11787f83993a4354913b1980dd8b726b', 'cd85ac80a4a04d44a589d49edfc76fca', '1', '2017-05-27 12:07:28');
INSERT INTO `tb_role_resources` VALUES ('120ff95cd87343969a7ee8ce49ce3afe', '6527814e06a740559afa9f89758b7945', '1', '2017-05-26 17:25:44');
INSERT INTO `tb_role_resources` VALUES ('14815cc417694977b7b80b4b828b73ab', 'ff5523429bad4cd9b6cc698df2f04f55', '1', '2017-06-20 16:35:01');
INSERT INTO `tb_role_resources` VALUES ('15189a48c537454ab60fee2933fc9588', 'c406877b260e4763b34070c89a76fd44', '1', '2017-06-13 18:22:56');
INSERT INTO `tb_role_resources` VALUES ('1679af39bbf14b27b383847977dbedd3', '89d7aba5f1064e88accd26cd9cdfff4b', '1', '2017-05-26 17:45:28');
INSERT INTO `tb_role_resources` VALUES ('1721374f97084043ab367b1f8ac73ec8', '7c552f6bd948413692439849ce1fc294', '1', '2017-05-27 12:10:10');
INSERT INTO `tb_role_resources` VALUES ('173', '1', '3', '2016-02-28 17:11:47');
INSERT INTO `tb_role_resources` VALUES ('1831fc4379bf48e29e4122dcd29c09af', '6c17ce10f157463db6b7fddccd466a24', '1', '2017-05-26 18:05:28');
INSERT INTO `tb_role_resources` VALUES ('19d20015edd14d43be688a5f3fcb53be', 'e9d26726a9c64e1c9898778d3773bed1', '1', '2017-05-26 17:30:20');
INSERT INTO `tb_role_resources` VALUES ('1a4ec271e76742cd9bda5a8252289cd0', 'e1a9992a70fb485caff03514dabb69db', '1', '2017-06-27 14:37:30');
INSERT INTO `tb_role_resources` VALUES ('1d06684f8ded407692fbaf395e6b048b', '1163f635849b44b4bc75d6a93b7007fb', '1', '2017-05-26 17:46:40');
INSERT INTO `tb_role_resources` VALUES ('1e9ee96dac0b4e8e98ca7f40ba4465e4', '8c5d32c86ff14237a5343406cc75c881', '1', '2017-05-27 11:41:14');
INSERT INTO `tb_role_resources` VALUES ('1ef6f7fa4989443b8c58c2d8c145d03e', '8359efcf52da48dd8d3b92547681b1dd', '1', '2017-09-12 16:03:12');
INSERT INTO `tb_role_resources` VALUES ('1f09558f84d2499d849ba75d31f01761', '5761a5a70c464ec2988a020aef1aa169', '1', '2017-05-26 18:03:22');
INSERT INTO `tb_role_resources` VALUES ('201401c174764ec7b4937dd35fd213f9', 'dba6c414c5ab445794e1c364aae1c124', '1', '2017-05-27 12:08:35');
INSERT INTO `tb_role_resources` VALUES ('223ccd0b869a4047bb2402cf1b826b64', '2ca2a216152b43e7bc7d21201504c9f7', '1', '2017-05-26 17:42:56');
INSERT INTO `tb_role_resources` VALUES ('24852790db1f408db7bb7765147c85e7', 'b4ac084e4e0f4cdca0bd641728306e4a', '1', '2017-05-26 17:28:46');
INSERT INTO `tb_role_resources` VALUES ('259fd64788b84959a3d557ea0063c935', '8e1df5fe37904ac6babb6de197b4176b', '1', '2017-05-27 12:09:21');
INSERT INTO `tb_role_resources` VALUES ('2606c9d35ee34e90a2f010fe5cdfd16a', 'cec379bd216a4607bfe96f171199290f', '1', '2017-05-26 17:24:24');
INSERT INTO `tb_role_resources` VALUES ('263d5a75e247499b9ae337ecb0bb8b8c', '72e93101701540f4972db99204cfe66e', '1', '2017-05-27 12:00:11');
INSERT INTO `tb_role_resources` VALUES ('2694', '3', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2695', '2', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2696', '4', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2697', '89', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2698', '100', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2700', '6', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2701', '50', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2702', '10', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2703', '9', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2704', '22', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2705', '30', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2706', '38', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2707', '40', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2708', '39', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2709', '1', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2710', '11', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2711', '12', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2712', '13', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2713', '14', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2714', '48', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2715', '49', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2716', '52', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2717', '61', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2718', '67', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2719', '72', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2719db1030f14579923f7fab3da04ac7', '4eac4f474f5e4382bdd06dcf4ccf815b', '1', '2017-09-11 15:02:16');
INSERT INTO `tb_role_resources` VALUES ('2720', '73', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2721', '74', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2722', '75', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2723', '15', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2724', '16', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2725', '17', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2726', '18', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2727', '53', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2728', '62', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2729', '66', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('272d28e8c5d24a20b0c496d098e4f5f5', 'e41b149d5e134a7c8b0f37b7d7b51197', '1', '2017-05-27 10:41:34');
INSERT INTO `tb_role_resources` VALUES ('2730', '68', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2731', '19', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2732', '20', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2733', '21', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2734', '63', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2735', '69', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2736', '90', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2738', '103', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2739', '104', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2740', '105', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2741', '106', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2742', '107', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2748', '7', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2749', '8', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2750', '51', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2751', '60', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2752', '71', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2753', '55', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2754', '56', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2755', '32', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2756', '35', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2757', '34', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2758', '41', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2759', '44', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2760', '45', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2761', '46', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2762', '47', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2763', '59', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2764', '65', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2765', '70', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2766', '42', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2767', '43', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2768', '57', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2769', '64', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2770', '36', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2771', '37', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2772', '58', '1', '2017-03-14 08:50:43');
INSERT INTO `tb_role_resources` VALUES ('2798bbad1bf943d49cad3d22fa5910f1', '830e0007f1e5415aba6ed53f04d1c228', '1', '2017-05-26 17:29:24');
INSERT INTO `tb_role_resources` VALUES ('27f528446bb84b39998c95011b2584cd', '5df0039f4b634bb2a2cf81b181c346e8', '1', '2017-06-20 16:36:25');
INSERT INTO `tb_role_resources` VALUES ('28248f9698a8432ab29f7419cde39a2f', 'b098121732a44149b9cad2865cedeffa', '1', '2017-06-06 15:11:58');
INSERT INTO `tb_role_resources` VALUES ('29d2b02136bc487183bafbf17e59ef38', 'd19859f2ac1f400498dc4c52419458fe', '1', '2017-09-22 17:18:34');
INSERT INTO `tb_role_resources` VALUES ('2a52e60835a840ba95df7364c4d190ff', 'e0a553683f1a4c31a0e63c61cc66d4ae', '1', '2017-06-06 15:33:25');
INSERT INTO `tb_role_resources` VALUES ('2a5e56b3790b4d5e9a18737bf3605953', 'c835d8e535204a60b57baa0125153c03', '1', '2017-06-02 16:29:45');
INSERT INTO `tb_role_resources` VALUES ('2b36488fbbf3442eb500c1a9ce49c8ae', 'cb602c63b9de440a80c045bea80ff553', '1', '2017-05-27 11:39:02');
INSERT INTO `tb_role_resources` VALUES ('2c2fff23e9b240eba84202172f861d19', '1e3ee592b3e24218b25f75b1e94bd2e4', '1', '2017-06-30 15:10:57');
INSERT INTO `tb_role_resources` VALUES ('2deec5050ca943dc8203cf81c5645c4e', '4ffdd16ce1544cbebb8188ee460eab7a', '1', '2017-09-12 15:51:09');
INSERT INTO `tb_role_resources` VALUES ('2f7487f38cab4b6ba6faa7c304dd7ff5', '997f0ec412de40bebbe8842e1b4d384f', '1', '2017-05-27 10:50:39');
INSERT INTO `tb_role_resources` VALUES ('304be0ee3b3b42d3a3b86b064ca22a14', '4d93285b64044bb3817dbb4c8b5d500f', '1', '2017-05-27 10:49:46');
INSERT INTO `tb_role_resources` VALUES ('30d15bbc8b8849a38617db9203fbaf48', 'ab328143ae1c4fefb856eca3380d3f2a', '1', '2017-09-22 17:15:59');
INSERT INTO `tb_role_resources` VALUES ('31c05cacdfb94158b3dc658ee6491665', '21e5c18537274e3ba621b3e0d21a4e30', '1', '2017-05-27 11:05:18');
INSERT INTO `tb_role_resources` VALUES ('31c4bbe44f454e94b56f4940d179c0c8', '5e560fca6bcc4608a8cb46cf611c1b97', '1', '2017-05-26 17:43:59');
INSERT INTO `tb_role_resources` VALUES ('3220bb753990460787c29e71b7b96887', '8ba237c3dbaf483eba4133ac70a0b683', '1', '2017-05-26 17:31:12');
INSERT INTO `tb_role_resources` VALUES ('35c8ae5d10f8471788e0d5ac06a86502', 'b3bb0302d22e401ea97a5c190c6d1b59', '1', '2017-05-26 17:49:12');
INSERT INTO `tb_role_resources` VALUES ('3a5a0d543a8e49519b5f6041a01c3703', 'be6073163807434da294f1d0d3157568', '1', '2017-09-22 17:19:24');
INSERT INTO `tb_role_resources` VALUES ('3a62af53b277476d931ce908aef8bb13', '59ca815018aa4faeb0c10b3d5fe97af2', '1', '2017-06-20 16:30:25');
INSERT INTO `tb_role_resources` VALUES ('3b256ad8f3f641439c3f0387ef7a6ae0', 'deec8cc689164e51be587cb4de1c1955', '1', '2017-06-02 16:31:21');
INSERT INTO `tb_role_resources` VALUES ('3df1f7f60f034728a509da2e2daeb1a3', 'aa148ab403fb40ca82ff840d38f1ff1a', '1', '2017-05-26 18:07:57');
INSERT INTO `tb_role_resources` VALUES ('3fcd02ab0b02463ca82b7acbac4fed3c', 'ec2c4328b78e44b8922ce10845d769c0', '1', '2017-09-11 15:16:14');
INSERT INTO `tb_role_resources` VALUES ('44761b7fba0e44e6ad3aef75953b743b', 'c5f89e919d134649970103b2b369f39c', '1', '2017-06-06 15:12:51');
INSERT INTO `tb_role_resources` VALUES ('44c2c066041b4827a310812e5004cc0e', 'a30f1d8804c240df97bb499d0c8060e6', '1', '2017-05-27 11:55:31');
INSERT INTO `tb_role_resources` VALUES ('45972c872a444549b50abbbf92d2702d', 'e14858e1d66a430db205f05618bc4e6c', '1', '2017-05-26 18:15:56');
INSERT INTO `tb_role_resources` VALUES ('4710547d1f424dd8844982c813aa09ac', '23076822f10f4794a26f25b1e37eaa9e', '1', '2017-05-27 11:01:30');
INSERT INTO `tb_role_resources` VALUES ('47d4a58e3da74110b091a66b9d9b2a35', 'ad6c19c0adbd4bbe95f3df5485f1c052', '1', '2017-05-26 18:14:25');
INSERT INTO `tb_role_resources` VALUES ('4908840370214166a2baf178c9ce5646', 'feae88aca2ef4e64873ff4ab3eaae850', '1', '2017-05-27 11:05:48');
INSERT INTO `tb_role_resources` VALUES ('49d947cf473849de9d1fb635f23db31b', 'a8b9b8f6e73b46c3b3109eb011f1bb6c', '1', '2017-05-26 17:59:12');
INSERT INTO `tb_role_resources` VALUES ('4e34e1afff2f46858d936f4dd0db1a1f', 'bfa0b1f1a04f466393579db482c3889f', '1', '2017-05-26 17:53:49');
INSERT INTO `tb_role_resources` VALUES ('4f1657b63ee840a390bcb9c5a27673e5', '6ec606e3e574477d8384a61c017807a0', '1', '2017-05-27 11:59:38');
INSERT INTO `tb_role_resources` VALUES ('4fd43f015dd04104bb776f3115b37c22', '7d54e45d1b0844e9bb7eceb3a16f0afa', '1', '2017-05-26 17:46:17');
INSERT INTO `tb_role_resources` VALUES ('50be6b853dca4fe18d85e99083c64e7d', '6d1b50bed56b4348a32c6c2edc60acae', '1', '2017-05-26 18:15:29');
INSERT INTO `tb_role_resources` VALUES ('51110327bed24e938f4434820bcb7b53', '94c6029804d94e09acabd1b052b8aad9', '1', '2017-05-27 11:52:15');
INSERT INTO `tb_role_resources` VALUES ('51c9dc31103048f3b61656e7dd3f613f', '2e280411d72b400b8a98e06ce5f44db0', '1', '2017-05-27 10:57:06');
INSERT INTO `tb_role_resources` VALUES ('53fec5565d994045b0c2535a39b670ac', '3341407c42b045a29366c28055accd51', '1', '2017-05-27 10:57:53');
INSERT INTO `tb_role_resources` VALUES ('53ff9c99f42647ef9d11b748c0257548', '7af2c2fce93649f994d59bc0b861a0dc', '1', '2017-05-26 18:02:43');
INSERT INTO `tb_role_resources` VALUES ('540bc2b76c2c4fd1aede77ca4917b6ea', 'bd27a654ce814223b45045b42dcfb6c4', '1', '2017-09-11 14:59:10');
INSERT INTO `tb_role_resources` VALUES ('543b63a1e07b49339f3556476ef1fb44', 'd2f4197a33384c898a225d347da1e87d', '1', '2017-05-26 17:52:55');
INSERT INTO `tb_role_resources` VALUES ('577c48d803d643029659766d8a7d0402', 'eb965e126fc64c478e31570f00996942', '1', '2017-05-27 12:06:14');
INSERT INTO `tb_role_resources` VALUES ('585f82213fe444ff82491fff55b37106', '2eaa5101ef544796809bd7ebd67eeb9d', '1', '2017-05-27 10:59:31');
INSERT INTO `tb_role_resources` VALUES ('5a90fbe1c19b4c37abccfb76397b86d5', '8552703085134681840fe90ecbc4ad55', '1', '2017-05-26 17:11:22');
INSERT INTO `tb_role_resources` VALUES ('5d3830cb47904f76a46c74c6e6862791', '18a72a015df24943a023dd682445f16c', '1', '2017-05-27 12:01:42');
INSERT INTO `tb_role_resources` VALUES ('5f0877a6219f4da4b7a970a67d1febbe', '015af4f4b5f74aee9d913734c4dfa3d4', '1', '2017-09-12 16:02:33');
INSERT INTO `tb_role_resources` VALUES ('602e3c7c6cd943f2a1b457f353b58d26', '620e3ec103b74381aef034008465f301', '1', '2017-05-27 11:07:07');
INSERT INTO `tb_role_resources` VALUES ('603563030b2744ed84e24a69ba1c644f', 'a2ab97c66b9f4f419354466d6c99bebe', '1', '2017-05-26 17:22:54');
INSERT INTO `tb_role_resources` VALUES ('6146e39838c447199c21eb37dbe16688', 'a858241945824eca8f8346561734fd3f', '1', '2017-06-06 15:42:39');
INSERT INTO `tb_role_resources` VALUES ('6152371de8b14dd4ac1ef829fdd8e520', '402f91b425a7476a94fa826d9e7fcaf2', '1', '2017-05-27 12:02:14');
INSERT INTO `tb_role_resources` VALUES ('637e56aaaf484ff8b284092cf12ec85b', 'bbbf9dc00602444fbcfea9760a199add', '1', '2017-05-27 10:56:26');
INSERT INTO `tb_role_resources` VALUES ('65c1c73d60ee442595b5732c321899dd', '5c61b3635abb4992965d5b672b743237', '1', '2017-05-26 18:14:54');
INSERT INTO `tb_role_resources` VALUES ('65ce2a5cc2454451bdb97ffadf644407', '206fd20b2f5c4177b3bc34713cf83f07', '1', '2017-05-27 11:00:52');
INSERT INTO `tb_role_resources` VALUES ('6634076f410b4fa9a9cea3c9c1ed7411', '3437676427844f46b0e9d27ec6f9be89', '1', '2017-05-26 18:03:54');
INSERT INTO `tb_role_resources` VALUES ('6688dacef74f4df0a2605a619db5499a', 'b3c822a9b68e4114bbad0e2194197b95', '1', '2017-05-27 12:09:46');
INSERT INTO `tb_role_resources` VALUES ('6adc65c522af4b51bb79a64a21dd39d4', '8be560e65d66441ba6a5cb4add8aa867', '1', '2017-06-22 17:42:12');
INSERT INTO `tb_role_resources` VALUES ('6b87bdb9a0ef4a29ba5d7a4fd8b067e0', '6b5a075cf27e415e9146e41f65eb1af1', '1', '2017-06-27 14:34:21');
INSERT INTO `tb_role_resources` VALUES ('6ef6b9fa29454ea5b35e8a738192ec97', '2f2c593853c64baaba52b2592b05c991', '1', '2017-05-27 12:00:41');
INSERT INTO `tb_role_resources` VALUES ('709959df6b874e84accc7077d6c988c4', '98cca6340e7e458c9117ac7a648a9d0a', '1', '2017-06-20 16:33:20');
INSERT INTO `tb_role_resources` VALUES ('71876ad47e764c8ab64fd22cab360745', '0cb76b2c61a9418ab0188e4e41c6c349', '1', '2017-05-26 18:10:40');
INSERT INTO `tb_role_resources` VALUES ('729fd0b4b5b941c2b7d8927e1cafde40', '76f5294ed2cf42eb9f760c324883c17c', '1', '2017-06-19 18:01:42');
INSERT INTO `tb_role_resources` VALUES ('73f6c357c67c4b54800939d0af3e9902', 'b7b6c381d02e474794b9805b9292164b', '1', '2017-05-26 17:53:23');
INSERT INTO `tb_role_resources` VALUES ('74709a46c8ec4dacacf936419f63fe5f', '11c39dbd8efa4544915960b7cf757fd6', '1', '2017-05-26 17:52:05');
INSERT INTO `tb_role_resources` VALUES ('7483cdd2928a446d85119662a339f264', 'b58ec3c4ba5c471f8f2d6ea4ed0e9a0c', '1', '2017-09-12 15:51:48');
INSERT INTO `tb_role_resources` VALUES ('74b10255de984b4699514d03f27ae481', '83ceee53cfc74898909e89646ef14c6d', '1', '2017-06-02 16:32:14');
INSERT INTO `tb_role_resources` VALUES ('7558c7917e2843acaa476e5a16fb5390', '62bdf4abcd9f43b3a9902cbce3b346ce', '1', '2017-05-27 10:31:18');
INSERT INTO `tb_role_resources` VALUES ('7562969b042e4775a9f4ca7e9712cf32', 'a073bfb000ef4ed6a912925a30ca43f9', '1', '2017-06-02 16:30:12');
INSERT INTO `tb_role_resources` VALUES ('77f3284b5f604194b920c990e1c27182', '52aa1aba21964a37ae412cc8532f57bd', '1', '2017-05-27 11:40:13');
INSERT INTO `tb_role_resources` VALUES ('7980e87caa4942f8a536b1262f56e5f4', 'dc87ae762af94c849c7b965184878854', '1', '2017-08-22 14:52:37');
INSERT INTO `tb_role_resources` VALUES ('7d0ba07fc6194f68a30d72dbfb96ea4e', '5f81ff5709b6427aba30048c9d165134', '1', '2017-06-02 16:32:41');
INSERT INTO `tb_role_resources` VALUES ('7d35c9274bee481e852fa85f2348e134', 'fc10cc8676414d63807536b1ad7c9227', '1', '2017-05-27 11:04:50');
INSERT INTO `tb_role_resources` VALUES ('7e03a7976d164bca92c73c74337222ab', '3c0379d30f874a63aa7008f16305160f', '1', '2017-05-26 17:48:22');
INSERT INTO `tb_role_resources` VALUES ('7f171588c5fc4c43be8b3da6ff29e8b1', '7dd0d01382ef4f26a4893ca8070d69ca', '1', '2017-05-27 11:06:40');
INSERT INTO `tb_role_resources` VALUES ('7f50391de5b9460887a05176905698c1', 'd41dcf6c6bc4416689f7bc67d41e3b51', '1', '2017-09-22 17:17:23');
INSERT INTO `tb_role_resources` VALUES ('8377ad66efa84d4e8d02b18e9307b8a8', '2d31d167da6b448d86bc37ba2800069c', '1', '2017-05-27 11:34:06');
INSERT INTO `tb_role_resources` VALUES ('852a145aba7e47cd9faa43f77e1ed19a', '444e9f8c03e4454d9e949371069f32a3', '1', '2017-05-26 17:32:06');
INSERT INTO `tb_role_resources` VALUES ('86b9ba8898f44fe1a856d87032c556a9', 'ee352f364bc64756902c903dd61111b2', '1', '2017-05-26 17:19:43');
INSERT INTO `tb_role_resources` VALUES ('885', '3', '2', '2016-12-12 13:37:42');
INSERT INTO `tb_role_resources` VALUES ('886', '2', '2', '2016-12-12 13:37:42');
INSERT INTO `tb_role_resources` VALUES ('887', '9', '2', '2016-12-12 13:37:42');
INSERT INTO `tb_role_resources` VALUES ('88dbb16a9ce04912adab0a0a03db0aa8', '04e32192369d499a94910f7c39b56f16', '1', '2017-05-26 17:48:47');
INSERT INTO `tb_role_resources` VALUES ('891', '11', '2', '2016-12-12 13:37:42');
INSERT INTO `tb_role_resources` VALUES ('892', '12', '2', '2016-12-12 13:37:42');
INSERT INTO `tb_role_resources` VALUES ('893', '13', '2', '2016-12-12 13:37:42');
INSERT INTO `tb_role_resources` VALUES ('894', '14', '2', '2016-12-12 13:37:42');
INSERT INTO `tb_role_resources` VALUES ('895', '10', '2', '2016-12-12 13:37:42');
INSERT INTO `tb_role_resources` VALUES ('896', '22', '2', '2016-12-12 13:37:42');
INSERT INTO `tb_role_resources` VALUES ('89c3923e7b204f48bf1e3e7e58210a4e', '7d3efa5f41c1486db4e8397b428f2def', '1', '2017-05-26 18:13:27');
INSERT INTO `tb_role_resources` VALUES ('8a859638f06847788032ac02e57b5698', 'e247cbb5681048a59999709db903d974', '1', '2017-06-06 15:44:28');
INSERT INTO `tb_role_resources` VALUES ('8b41e719d44e43849fa880447b9674e9', 'd8330ec12bbe47baab75f372bc264f79', '1', '2017-09-11 15:10:45');
INSERT INTO `tb_role_resources` VALUES ('8d0d35d42520414183572d9f2302b5aa', '2e0455b1cbfb44e4a1976407c0a4eee4', '1', '2017-05-26 18:04:20');
INSERT INTO `tb_role_resources` VALUES ('8da9f69cb6eb4060a7159cfebf3f8d0d', 'c0189e086e55439a98ab26efbd64c9ef', '1', '2017-05-26 17:23:27');
INSERT INTO `tb_role_resources` VALUES ('8f2a40773aeb4fcd8dc933966c07e941', '8353d0efb3bf4ba2a640f63490fc0b5a', '1', '2017-06-06 15:46:01');
INSERT INTO `tb_role_resources` VALUES ('93a9c272b2e94af1ae237e3b9e0cefb8', '5a41977282074937acd77290569a81f5', '1', '2017-06-02 16:30:58');
INSERT INTO `tb_role_resources` VALUES ('94c04f4aba164227bfe4141fa1ccb4a8', 'f7c0fee7b92645e69935c24ade95285c', '1', '2017-05-26 18:12:32');
INSERT INTO `tb_role_resources` VALUES ('95713350e2e647da98ddab7df7b05091', '430ff24d33f24b75840b73cf66c4bd54', '1', '2017-06-06 15:34:42');
INSERT INTO `tb_role_resources` VALUES ('96e0ffcbd04845638da97a8fb5a400cc', '8c669eefa7ae4504adc98d399d2c4185', '1', '2017-05-27 11:02:41');
INSERT INTO `tb_role_resources` VALUES ('97c438e9ad9b434abc90c520efacc6ca', '8f3a755650df4b06ac24c57b73cc2594', '1', '2017-05-26 17:47:13');
INSERT INTO `tb_role_resources` VALUES ('98b880c54aa54e57a0ec778de9116cc6', 'be1a3f3787584bba8f7f0ab09ab066fb', '1', '2017-05-26 17:52:31');
INSERT INTO `tb_role_resources` VALUES ('98da5c4fb50b4028aaa31a097144ff16', 'ce93bbdc91ed4261b693dd7856818f01', '1', '2017-06-06 15:43:39');
INSERT INTO `tb_role_resources` VALUES ('9906ef43cbf84344a57c14ea501e0450', '4d1d360f9e7b4e12b37bb8f1185be8cf', '1', '2017-05-26 18:06:55');
INSERT INTO `tb_role_resources` VALUES ('9a13b0e626e540d999656ec9af7aed40', '7fdbd10db0684be8839769308546ff70', '1', '2017-06-30 15:25:54');
INSERT INTO `tb_role_resources` VALUES ('9af2be5a00fa488e81159ffcc49b1c9c', '20d89ddd87d04f2c907da386ed354a17', '1', '2017-05-26 17:50:01');
INSERT INTO `tb_role_resources` VALUES ('9cf81de8b72542a7b4f10775a77e3d35', '44b40bba58294327bc23ef72ee3980d2', '1', '2017-05-26 17:14:04');
INSERT INTO `tb_role_resources` VALUES ('9d4bf2a86819484a80659198b84c5c73', '6ba6e6074da74081bba3add6f5c1d601', '1', '2017-05-27 10:27:26');
INSERT INTO `tb_role_resources` VALUES ('9d7f274e98794e578344555b27889b03', 'eb43c46b034a4a3cb68724359f52a949', '1', '2017-06-27 14:38:49');
INSERT INTO `tb_role_resources` VALUES ('9e11f30b065d46e1940093ac4e499d1c', 'd60bfade7d7646708674bca303936222', '1', '2017-05-26 17:30:56');
INSERT INTO `tb_role_resources` VALUES ('a1b34b591646431190bc916a2695f4ac', '7424363d11ce4f0084b5480b6c38a2b8', '1', '2017-05-26 17:45:54');
INSERT INTO `tb_role_resources` VALUES ('a202bd0a5a7b45f58dce3d80ee2b5de4', 'c9e343bcd24247b7af6162ffe11e2c2c', '1', '2017-05-26 17:50:24');
INSERT INTO `tb_role_resources` VALUES ('a53aad966fbe4cf28cf8589c11298054', 'e4eb75fce5a642e290e5a6d2dad0c4ec', '1', '2017-09-12 15:57:04');
INSERT INTO `tb_role_resources` VALUES ('a55aaa023aab4af183b1b2d3b56db769', 'bd2460507555487ab15a019a40bf8b83', '1', '2017-05-26 17:23:57');
INSERT INTO `tb_role_resources` VALUES ('a89661ba297d42c3806f28bc0017cef4', '8ae38d91e1f14b27a9618a351ef75cf6', '1', '2017-06-23 11:23:14');
INSERT INTO `tb_role_resources` VALUES ('a8be8c5c567940c0b236cc1098c40b47', '15529b879a754a23b8313c74108a7414', '1', '2017-05-26 17:47:43');
INSERT INTO `tb_role_resources` VALUES ('a99ba3fa2ce04655bc9e2b2713daae96', '55b1bf2e961948bc96dd8641e13e11c7', '1', '2017-05-27 11:46:05');
INSERT INTO `tb_role_resources` VALUES ('a99e0b8b23d7463584c85191d9f80b29', 'e205b882b1d64c618a5ed739cce1d760', '1', '2017-05-27 11:00:00');
INSERT INTO `tb_role_resources` VALUES ('aba79a3fd9824ad2a887310e2c7217a7', 'ebe4b4d7740c4eb1bf2d852c4c6f6179', '1', '2017-09-22 17:20:24');
INSERT INTO `tb_role_resources` VALUES ('adc6e7d8638a442ea2daac4324a011bd', '955a6f0c0f27489f91b82d683bec217a', '1', '2017-05-27 10:28:10');
INSERT INTO `tb_role_resources` VALUES ('ae072f0a4c84408b81e1c9314bbaa1e9', '6d0eb41b35ca4fffaecf0bb8604a342b', '1', '2017-06-06 15:39:43');
INSERT INTO `tb_role_resources` VALUES ('b04221682daf47608e5b0b78581dd887', 'f5e24c1f16a9454099d0adddecb86e70', '1', '2017-06-06 15:45:19');
INSERT INTO `tb_role_resources` VALUES ('b12dd050f35d40e9b4e4fbaffde92e7f', 'b949b6ed49ac4f2e96e706bcee0046c2', '1', '2017-06-23 11:47:46');
INSERT INTO `tb_role_resources` VALUES ('b1c064aba2054a93995c8bbf51701c14', 'd93269629ccb41218fab9b0eb9ce409c', '1', '2017-05-27 10:48:54');
INSERT INTO `tb_role_resources` VALUES ('b2d85d2182504a25a9b64c61cc0c273e', 'aea669db4d77414dbae79c2c0a58a6d0', '1', '2017-05-27 10:50:12');
INSERT INTO `tb_role_resources` VALUES ('b433caa384f54c478272e987fa518c9b', '0f64071a652149dabf7c71e2ef82beb8', '1', '2017-05-26 17:10:22');
INSERT INTO `tb_role_resources` VALUES ('b4a1877660604c0488be63c921eeed37', '740ea2aadf594881a0d222aecd699357', '1', '2017-09-11 15:13:53');
INSERT INTO `tb_role_resources` VALUES ('b703f51858e044109b331c942ffdb837', 'a0571f0aed8b47b48742c97d9c6176bd', '1', '2017-06-06 15:34:12');
INSERT INTO `tb_role_resources` VALUES ('b9046f90e9e94e9c9e09b45deb01de26', '5fb415e6c0f545ef87c78af5763882a3', '1', '2017-06-20 16:48:08');
INSERT INTO `tb_role_resources` VALUES ('b95351be3aed488b8307afc559de4444', 'a350ea4b4b7545b180fe28e0248fa256', '1', '2017-06-06 15:09:16');
INSERT INTO `tb_role_resources` VALUES ('bb82f1ed94564f8993ee630985177bf7', '024cc92af94e4f9fb65539074df8b01d', '1', '2017-05-27 11:43:49');
INSERT INTO `tb_role_resources` VALUES ('bbab0d2d006741eeb06356d4fad481b3', '772bd25a45124f7889ba8420fdfe63d3', '1', '2017-05-26 18:07:28');
INSERT INTO `tb_role_resources` VALUES ('bbbd3ba8391a401c9b0705e5f7b2ddae', '2f2a3ee1c2f347bc9f32d96c953f8a2d', '1', '2017-05-27 11:43:18');
INSERT INTO `tb_role_resources` VALUES ('bc3df9f0e71d40e39440c9ff34d1f28e', '97e8437e73d544a0a409eb6b5e998233', '1', '2017-05-26 17:51:29');
INSERT INTO `tb_role_resources` VALUES ('bd092e49ae8848f2b5d135fa9dffef7e', 'cdf07f2b376046afa663ba26e9128dc6', '1', '2017-09-12 16:01:45');
INSERT INTO `tb_role_resources` VALUES ('be78ee571ac042a199eb7a7ec9ceb506', '00254d3a9ab54a8199a2c147ec0648f2', '1', '2017-09-22 17:14:09');
INSERT INTO `tb_role_resources` VALUES ('bf03b04c29f346b98704d480fe3853ff', 'b40165fe92114e48993f75ebcec48e0e', '1', '2017-05-26 18:01:05');
INSERT INTO `tb_role_resources` VALUES ('bf90e747de604d4492b9097ae1f7d058', '3cdd33d922a543a8ac3a6a732c47f346', '1', '2017-05-26 17:45:04');
INSERT INTO `tb_role_resources` VALUES ('c2928720ec6345b1933f0d2a031ce27a', '093259a287a944bbb70ab800a41cde12', '1', '2017-05-26 17:13:08');
INSERT INTO `tb_role_resources` VALUES ('c29d6eef27ed4815a28dbf6aa3abef03', '843e8c6742824711bc3204e635072af3', '1', '2017-05-27 11:39:34');
INSERT INTO `tb_role_resources` VALUES ('c3038cbd019f4187b3139f7f26bc3aa6', '828f3f0130314d0598ab92c185cd804a', '1', '2017-09-11 15:00:28');
INSERT INTO `tb_role_resources` VALUES ('c34651336bc94254a5b1b0e4874b176a', 'b48d724277c14e3fb51c91e5f2355383', '1', '2017-05-26 18:12:59');
INSERT INTO `tb_role_resources` VALUES ('c6e4279edd1b47d5b7ed70c6a7d4a24e', '411fdce39be046058d5a76a778081ba6', '1', '2017-06-27 14:35:44');
INSERT INTO `tb_role_resources` VALUES ('c8176eb2e91443aaaa2bd68dc8e4c42a', '56a9bcf86edc4398bf8668d2dd6475c0', '1', '2017-09-11 15:04:51');
INSERT INTO `tb_role_resources` VALUES ('c8927b4760064c689db5572f5d4b0112', 'ba8885c430b64483baf0f59102fea467', '1', '2017-05-26 18:16:32');
INSERT INTO `tb_role_resources` VALUES ('c8c02486ccae4cc5ab2bdbd05490f266', '1435c92cc52c43b0968f6ea45077945b', '1', '2017-05-26 18:12:04');
INSERT INTO `tb_role_resources` VALUES ('c90f73f8188b489abb25c92465893661', 'f5972ec9b0df41909ad0730b721a776f', '1', '2017-05-26 17:30:39');
INSERT INTO `tb_role_resources` VALUES ('ccba883daf554ab4b6a6914831a44518', '32fe615ee0af4d60867f2be6e16aca5d', '1', '2017-06-06 15:41:41');
INSERT INTO `tb_role_resources` VALUES ('cce178de5f684180bc76d6ac563f8a6d', '2c3951bf9c2a4acca48d5379f0649994', '1', '2017-05-26 17:25:00');
INSERT INTO `tb_role_resources` VALUES ('d0696f523687400d9c04adf4f57244e0', 'b77280c530e145b89b563e271c95a47d', '1', '2017-08-23 16:58:19');
INSERT INTO `tb_role_resources` VALUES ('d243e1bd7ed74f43b2689372653df546', '576ff25e74574d61935b8799c5729def', '1', '2017-05-27 12:01:13');
INSERT INTO `tb_role_resources` VALUES ('d2d9236920014ed78b021b05eaffa846', '9c0a0f61428d4570aeba088003907fa0', '1', '2017-05-26 17:20:59');
INSERT INTO `tb_role_resources` VALUES ('d31cfb49687c42cdae3450aefc2a4092', '9d8002279d2d4aabb3b0ab4b832b3445', '1', '2017-05-27 11:01:55');
INSERT INTO `tb_role_resources` VALUES ('d7c8a4df1f684e1cb1f27e48898e01c8', '1838ab94925345858e28a8c69cc08480', '1', '2017-05-27 11:04:04');
INSERT INTO `tb_role_resources` VALUES ('d87f80f28fc941d2a8166fadae8a7b3b', '309a069c329f42e7bc8baf8ddc019642', '1', '2017-06-06 15:38:50');
INSERT INTO `tb_role_resources` VALUES ('d94ec08d2e564455903d8c5b5bd3ec24', '63d25f1dfb864963ad71b28e96e48f98', '1', '2017-06-06 15:41:05');
INSERT INTO `tb_role_resources` VALUES ('da57aa959cdf4ef69c84200dfcdf056d', '96f3896fe50a46038c56cea524e2b14d', '1', '2017-05-26 18:13:55');
INSERT INTO `tb_role_resources` VALUES ('da6e6b8ae8674383abe8bfeffebba564', 'a35f564815a6423a8eb8f9728fb88a78', '1', '2017-06-20 16:31:51');
INSERT INTO `tb_role_resources` VALUES ('dacd016824084226b9441b5997f21672', '6fbbbe3bd93b42b6acd0be80a2f0371d', '1', '2017-09-12 16:06:16');
INSERT INTO `tb_role_resources` VALUES ('db70a481ae5d4a108da23302b353c669', '8824e44dedb9481797d6fef8b460c249', '1', '2017-06-27 14:36:55');
INSERT INTO `tb_role_resources` VALUES ('e3b9b6b956924cd283ecf2f6d9bf549b', '48154f70c4dd43859b3cf55dd5f49d16', '1', '2017-05-26 17:50:46');
INSERT INTO `tb_role_resources` VALUES ('e4a24c0f6d8d4a7b8488a587ab56242d', '80663dbe57044beaae3dd6cd21253b97', '1', '2017-05-27 10:34:22');
INSERT INTO `tb_role_resources` VALUES ('e6edde9aff1c46dda280487c72da6a49', '2ac0a5277d664ec9a213c88fa63b7ab4', '1', '2017-06-13 18:25:51');
INSERT INTO `tb_role_resources` VALUES ('e7d971777e604345bf9e171d289f1ad3', '28378e8466c648469ff3ca2bbce15295', '1', '2017-05-27 11:00:31');
INSERT INTO `tb_role_resources` VALUES ('e847a5d3bbb54fe5a9b99c50990b6940', 'ba95e2f953e94030b0cf2f8fa09462fa', '1', '2017-05-27 10:51:04');
INSERT INTO `tb_role_resources` VALUES ('eb4e6d390076495aaf5ed14bf67e3c26', 'd5ead8511d1b48afa42e7c3fd2a12f9d', '1', '2017-05-26 17:55:37');
INSERT INTO `tb_role_resources` VALUES ('ef144c6d6ac04881910d1d53d2e12e2c', '8f219d78691741d5bde5b52d34aac922', '1', '2017-09-11 15:12:31');
INSERT INTO `tb_role_resources` VALUES ('f0d0cc5f8745408193332991a7cae705', 'c92971b36c61413ab935ea93a35e819b', '1', '2017-05-27 10:58:23');
INSERT INTO `tb_role_resources` VALUES ('f2b0cac6cba94d9085c30659fb6dd3de', 'f0fd118a7e6e4b648d8b8f63d75e1e0b', '1', '2017-05-27 10:26:49');
INSERT INTO `tb_role_resources` VALUES ('f2daacde98c340d6ae2163b4a9ea35c3', '3d6e237eef07470797901fc68bb78462', '1', '2017-05-27 11:06:13');
INSERT INTO `tb_role_resources` VALUES ('f3bf0d4db0924cb2a6762bb71f8372ad', '43e89a90ad7c46aa9f32368afedb59e7', '1', '2017-05-27 10:49:20');
INSERT INTO `tb_role_resources` VALUES ('f49f79db3caf4eb0812a3a8c46cfb992', '3cf54176dd154e97b9f3adc814aac7bd', '1', '2017-08-22 14:51:12');
INSERT INTO `tb_role_resources` VALUES ('f4dca01793b64065a1b479fd6fc04f51', '73a012d6a09c4054a76a15d49c0326e6', '1', '2017-06-06 15:31:22');
INSERT INTO `tb_role_resources` VALUES ('f521a0c116144f18950a656edda36e66', '3647fd639bc74a87ad9c3b411d30553e', '1', '2017-05-26 18:08:53');
INSERT INTO `tb_role_resources` VALUES ('f744ac572c3a495d84f111f9a8ff4cd3', '595b19bbc18d4bae9c26af7ea7768551', '1', '2017-06-06 15:32:40');
INSERT INTO `tb_role_resources` VALUES ('f77d47d590144a78994813c0e6118be9', '8cfb8f0455a64f6bb37c4a7650289684', '1', '2017-06-02 16:31:49');
INSERT INTO `tb_role_resources` VALUES ('f823b29af3cd49809a09ef82ef032cf7', 'f2fc7fa7af13499ab78926ebda8f5aca', '1', '2017-05-26 17:13:29');
INSERT INTO `tb_role_resources` VALUES ('f8bd7d2f54d74246b9b0da6dfee3632f', 'c76fa973dd9a48aa84a211351c1311c7', '1', '2017-05-26 18:09:52');
INSERT INTO `tb_role_resources` VALUES ('fd22bf0fd39a484db3917a0927c1a509', '9a7431bfcd684101ad855024fc5cc420', '1', '2017-05-26 17:29:59');
INSERT INTO `tb_role_resources` VALUES ('ff85ff797f0b44528f9b031579ac91f6', '09275dcc376a4758a1291f7e35a98816', '1', '2017-05-26 18:06:10');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `credentials_salt` varchar(500) NOT NULL COMMENT '密码加密盐',
  `nick_name` varchar(32) NOT NULL COMMENT '昵称',
  `mobile` varchar(20) NOT NULL COMMENT '手机号码',
  `photo` varchar(256) DEFAULT NULL COMMENT '头像',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `sign` varchar(128) DEFAULT NULL COMMENT '签名',
  `steam_key` varchar(64) DEFAULT NULL COMMENT 'steam key',
  `steam_nick` varchar(50) DEFAULT NULL COMMENT 'steam 用户昵称',
  `qq_key` varchar(64) DEFAULT NULL COMMENT 'qq key',
  `qq_nick` varchar(50) DEFAULT NULL COMMENT 'qq 用户昵称',
  `wechat_key` varchar(64) DEFAULT NULL COMMENT '微信 key',
  `wechat_nick` varchar(50) DEFAULT NULL COMMENT '微信 用户昵称',
  `weibo_key` varchar(64) DEFAULT NULL COMMENT '微博 key',
  `weibo_nick` varchar(50) DEFAULT NULL COMMENT '微博 用户昵称',
  `locked` int(2) NOT NULL COMMENT '是否锁定',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` bigint(20) unsigned NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `account_update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IN_USER_MOBILE` (`mobile`) USING BTREE,
  UNIQUE KEY `IN_USER_EMAIL` (`email`) USING BTREE,
  KEY `IN_USER_CTIME` (`create_time`) USING BTREE,
  KEY `IN_USER_UTIME` (`update_time`) USING BTREE,
  KEY `IN_USER_NICK_NAME` (`nick_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('02836c03b3bc4f07a697f2d05f70100b', 'hITwLSZTd8+9rGB1Oi6yow==', '868de0fb1b0effc8c719bae0d4a76fff', '单挑三只狗', '15618090630', '/resources/wofinder/images/default.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1502100609856', null, '1502100621805', '1502100609856');
INSERT INTO `tb_user` VALUES ('06cd6367762049e69338a08ae65a812a', 'NHJJOwQkAtl1wY1FN7ozog==', '1b35748238e76b8dc97be35dcb63e199', 'v米', '13088885427', '/resources/wofinder/images/default.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500619934735', null, '1502692902045', '1502692902045');
INSERT INTO `tb_user` VALUES ('1', 'QGBvXhgG+7ci0kPSt2Qvdw==', 'e1d32926b78e0adb15a43f43c6f0d415', 'superadmin', '18000000050', '/upload/avatar/20170807/110ebd2aa73e4e8da120aa82d30b401f.jpg', 'admin@webside.com', '什么游戏好玩呢规范化富贵花饭店干活服饰公司梵蒂冈山东分公司的对分公司的风格对方告诉对方第三个发生的隧道股份十多个谁的风格谁的风格谁的风格谁的风格但是风格谁的风格第三个', '', '', '', '', '', '', '', '', '0', '1', '', '1471231126132', '', '1503371317868', '1503371317868');
INSERT INTO `tb_user` VALUES ('12e83fde1ef44aa29609785ec03433b6', 'kUp7fs1GuFyaehjE6ySEBg==', 'bbf43688008bec16ce072d4a3fb13775', '大支', '13886086902', '/upload/avatar/20170720/ca432660cd954e6fba13650a3aeccb8c.jpg', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500521691772', null, '1500521904345', '1500521691772');
INSERT INTO `tb_user` VALUES ('2169db33b2f64697a84d4027dbd7bcc3', 'HN5IzY+TP9XiRbclad/Hmw==', 'f18d7f0ae0e57f7f2d0583d8736acd70', '真的是鸭蛋', '13811785512', '/upload/avatar/20170724/e13f22b7bf864ca98ef5695f045bac4e.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500888865829', null, '1500888936326', '1500888865829');
INSERT INTO `tb_user` VALUES ('2acfe173afc14c2fa1836ebb771d7acb', '+4RMLcsEpJXmBpxv5AlWQA==', '9b6906553e88893a18fdb57f74a73fba', '熊二', '15926222267', '/resources/wofinder/images/default.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500522267072', null, '1500536136244', '1500536136244');
INSERT INTO `tb_user` VALUES ('31ecce68d6fa4185a3798b9ef7d92cf4', 'Dv/NIBz9NUy5GCCrkZj0eA==', '5b0c39e3f95ee851e3579c8469ca6c7f', '阿宁', '18573745487', '/upload/avatar/20170907/24c6dfbe4ce74151a0dd60cfcf02e03d.jpg', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1504771240387', null, '1504771367846', '1504771240387');
INSERT INTO `tb_user` VALUES ('36eedbaea8514cb6a28b6f97f19cd970', 'EkA9WoUlo7ow8ZixjO/W3g==', 'de23592b2aa2c156bae5de5207f08133', '素颜', '18062691807', '/upload/avatar/20170719/8eb2a59dce9a4813b44f3ab83f26acc9.jpg', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500447510841', null, '1500539298538', '1500539298538');
INSERT INTO `tb_user` VALUES ('6ab0651ce12b44daafbcee76de129bb4', 'fJvfpt+ByHNj8AcvQZ9klQ==', 'fa7e0546b547bdfc1579a27423ff3033', '怦怦然', '18680463580', '/resources/wofinder/images/default.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500978155188', null, '1500978164006', '1500978155188');
INSERT INTO `tb_user` VALUES ('7540d5600b4e4236a6586326bbb60418', 'lwu5OyQnHV7SstA5RGzRLQ==', 'bde006832cf5fac4f29a2f71242e82ba', '桃子嗨嗨', '18699971456', '/resources/wofinder/images/default.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1501835654575', null, '1501835662139', '1501835654575');
INSERT INTO `tb_user` VALUES ('896c70c78ab14afd9594fcb9faf0b52e', 'V+6qrULpQL6UlqjvqQ4TqA==', 'ebedd8aa177a5e67014a992893302fb3', 'JAYJAY', '18007177170', '/upload/avatar/20170720/9b17d68a895647188bf5f590cf566a2b.jpg', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500521160427', null, '1500521189349', '1500521160427');
INSERT INTO `tb_user` VALUES ('8e9ba9fdb29c4826a65984820155d282', 'e1dyIYMjdt/ppRr/1tTDsw==', '9675e6ba6457ada07cf6f0dd9b62d6e0', '丶無趣', '18366505550', '/resources/wofinder/images/default.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1502164162094', null, '1502164205855', '1502164162094');
INSERT INTO `tb_user` VALUES ('a4237b3b08c84a229fde55509068d639', '+TY5C3eQcE3dqflmFXf8Tg==', '2ebce7b358911fcae74a3cc1dd866e9f', 'herry', '18942918275', '/upload/avatar/20170725/62a125b5a9a14eb5b7f80b5505987158.jpg', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500954024224', null, '1500954082598', '1500954024224');
INSERT INTO `tb_user` VALUES ('d196dddf1d714336ad987a26d544d990', 'Gc+6l9b9zR0BpK/zBXIUcQ==', '7011eb7084b9eb2fbc87c1f2b871aefa', 'axis01', '15392927077', '/upload/avatar/20170724/70334ba7d02e43c781daed847e404984.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500879015227', null, '1500879037390', '1500879015227');
INSERT INTO `tb_user` VALUES ('fb81845169554873a8e809c4610a463e', 'djybmv2v1WsFlSi6KzpCIQ==', '94f2733cee4077205826bfc53f501e72', '少飞', '13824403604', '/resources/wofinder/images/default.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1500519960213', null, '1500519964083', '1500519960213');
INSERT INTO `tb_user` VALUES ('fe367eb73b164e7797ccfe5bab9171a6', 'CtFhXU1RsBXv2+SrzU7lhA==', 'b1d63c72de32b4e60c9a541d4e9e68a0', '小黄鸭', '15623501056', '/upload/avatar/20170801/2fef519614f44df18322324f41869f38.png', null, null, null, null, null, null, null, null, null, null, '0', '1', null, '1501143029261', null, '1501581640783', '1501143029261');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `id` varchar(32) NOT NULL,
  `r_id` varchar(32) NOT NULL COMMENT '角色id',
  `u_id` varchar(32) NOT NULL COMMENT '用户id',
  `t_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `IN_ROLEUSER_UID` (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色映射表';

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES ('1', '1', '1', null);

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `ID` varchar(32) NOT NULL COMMENT '唯一标识',
  `config_group` varchar(100) DEFAULT NULL COMMENT '配置组',
  `config_key` varchar(100) DEFAULT NULL COMMENT '配置键',
  `config_value` varchar(500) DEFAULT NULL COMMENT '配置值',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `create_operator_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `update_operator_id` varchar(32) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置表';

-- ----------------------------
-- Records of t_config
-- ----------------------------
INSERT INTO `t_config` VALUES (' 1', null, 'mobile_authentication', '{\"smsTemplateCode\":\"SMS_78750096\",\"appKey\":\"23436816\",\"secret\":\"9f00fe3dd94869d8a46bdef2c0286974\",\"smsSign\":\"WoDotA游戏网\"}', null, null, null, null);
INSERT INTO `t_config` VALUES ('2', null, 'email_captcha_template', '{\"fromName\":\"G菠菜\",\"title\":\"G菠菜 -- 邮箱身份验证\",\"content\":\"<p>G菠菜 - 邮箱验证</p><p>亲爱的用户：</p><p>您好，%s是您本次的验证码（为了保障您帐号的安全性，请在5分钟内完成验证），感谢您使用G菠菜服务，这是一封验证邮件。</p><p><br/></p><p>G菠菜账户团队</p><p>%s<br/></p><p><br/></p>\"}', null, null, '1486451065986', '1');
INSERT INTO `t_config` VALUES ('3', null, 'jc_share_key', '1', null, null, null, null);
INSERT INTO `t_config` VALUES ('4', null, 'domain_url', '{\"wodota\":\"http://192.168.11.72:8089\"}', null, null, null, null);

-- ----------------------------
-- Table structure for t_entry
-- ----------------------------
DROP TABLE IF EXISTS `t_entry`;
CREATE TABLE `t_entry` (
  `id` varchar(32) NOT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` text,
  `score` int(11) DEFAULT NULL COMMENT '父综合评分',
  `rank` int(11) DEFAULT NULL COMMENT '父排名',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_entry
-- ----------------------------

-- ----------------------------
-- Table structure for t_entry_img
-- ----------------------------
DROP TABLE IF EXISTS `t_entry_img`;
CREATE TABLE `t_entry_img` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `entry_id` varchar(32) NOT NULL COMMENT '词条id',
  `img_url` varchar(32) NOT NULL COMMENT '词条图片url',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_entry_img
-- ----------------------------

-- ----------------------------
-- Table structure for t_entry_value
-- ----------------------------
DROP TABLE IF EXISTS `t_entry_value`;
CREATE TABLE `t_entry_value` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `entry_id` varchar(32) NOT NULL COMMENT '词条id',
  `value_id` varchar(32) NOT NULL COMMENT '属性值id',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_entry_value
-- ----------------------------

-- ----------------------------
-- Table structure for t_seo_config
-- ----------------------------
DROP TABLE IF EXISTS `t_seo_config`;
CREATE TABLE `t_seo_config` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `keywords` varchar(512) DEFAULT NULL COMMENT 'keywords',
  `description` varchar(512) DEFAULT NULL COMMENT 'description',
  `title` varchar(512) DEFAULT NULL COMMENT '标题',
  `type` int(2) DEFAULT NULL COMMENT '类别: 1通用 2首页',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) NOT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SEO配置';

-- ----------------------------
-- Records of t_seo_config
-- ----------------------------
INSERT INTO `t_seo_config` VALUES ('656f44a794bd4a3d976c94644c0d2876', '游戏,游戏视频,WoDotA,游戏搜索,游戏门户', 'WoDotA是一个特别的游戏门户网站。这里可以搜索、预览各大游戏平台的游戏视频、统一进行评论、点评、回复弹幕直上首页。用WoDotA你也可以是游戏达人！', '搜索游戏视频，就上WoDotA找一下，看看。', '2', '1', '1', '1498546062026', '1', '1501833581751');
INSERT INTO `t_seo_config` VALUES ('f730a127e3c541b29155d58928369fcf', '-WoDotA', '', '-WoDotA', '1', '1', '1', '1498546068656', '1', '1501833595691');

-- ----------------------------
-- Table structure for t_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_log`;
CREATE TABLE `t_sms_log` (
  `ID` varchar(32) NOT NULL COMMENT '唯一标识',
  `mobile` varchar(32) NOT NULL,
  `content` varchar(500) NOT NULL,
  `result` varchar(200) DEFAULT NULL,
  `send_time` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IN_SMSLOG_MOBILE` (`mobile`) USING BTREE,
  KEY `IN_SMSLOG_STIME` (`send_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信日志';

-- ----------------------------
-- Records of t_sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_submit_keyword
-- ----------------------------
DROP TABLE IF EXISTS `t_submit_keyword`;
CREATE TABLE `t_submit_keyword` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `keyword` varchar(200) NOT NULL COMMENT '关键字',
  `status` int(2) NOT NULL COMMENT '状态，1有效，0无效',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提交搜索关键字';

-- ----------------------------
-- Records of t_submit_keyword
-- ----------------------------

-- ----------------------------
-- Table structure for t_submit_url
-- ----------------------------
DROP TABLE IF EXISTS `t_submit_url`;
CREATE TABLE `t_submit_url` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `sk_id` varchar(32) NOT NULL COMMENT '提交搜索关键字id',
  `url` varchar(200) NOT NULL COMMENT 'url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提交搜索关键字url';

-- ----------------------------
-- Records of t_submit_url
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_attribute
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_attribute`;
CREATE TABLE `t_sys_attribute` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性';

-- ----------------------------
-- Records of t_sys_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_attribute_value`;
CREATE TABLE `t_sys_attribute_value` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `attribute_id` varchar(32) NOT NULL COMMENT '属性id',
  `value_id` varchar(32) NOT NULL COMMENT '属性值id',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sav_v_a` (`value_id`,`attribute_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性值关联';

-- ----------------------------
-- Records of t_sys_attribute_value
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_game
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_game`;
CREATE TABLE `t_sys_game` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) NOT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏';

-- ----------------------------
-- Records of t_sys_game
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_game_value
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_game_value`;
CREATE TABLE `t_sys_game_value` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `game_id` varchar(32) NOT NULL COMMENT '游戏id',
  `value_id` varchar(32) NOT NULL COMMENT '属性值id',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) NOT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏属性值关联';

-- ----------------------------
-- Records of t_sys_game_value
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_help
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_help`;
CREATE TABLE `t_sys_help` (
  `ID` varchar(32) NOT NULL COMMENT '系统帮助',
  `TITLE` varchar(100) NOT NULL COMMENT '标题',
  `CODE` varchar(20) DEFAULT NULL COMMENT '内码',
  `CONTENT` text NOT NULL COMMENT '通知内容',
  `ADD_TIME` bigint(20) NOT NULL COMMENT '创建时间',
  `SYS_USER_ID` varchar(32) NOT NULL COMMENT '添加人员',
  `TYPE` varchar(10) NOT NULL COMMENT '1.系统帮助；2.用户反馈；3.系统公告',
  `SEQUENCE` int(10) DEFAULT NULL COMMENT '排序',
  `STATUS` varchar(10) NOT NULL COMMENT '状态 1：有效 0 无效 ',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_help
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_value
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_value`;
CREATE TABLE `t_sys_value` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `sv_name_idx` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性值';

-- ----------------------------
-- Records of t_sys_value
-- ----------------------------

-- ----------------------------
-- Table structure for t_task
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `up_id` varchar(32) DEFAULT NULL COMMENT '人物ID',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `view_count` bigint(20) DEFAULT NULL COMMENT '阅读量',
  `like_count` bigint(20) DEFAULT NULL COMMENT '点赞量',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件表';

-- ----------------------------
-- Records of t_task
-- ----------------------------
INSERT INTO `t_task` VALUES ('2a8b66f90f284a2c84b2a5da9e0edf3d', '00001ce4d0114979bc9f7811e4ad4ffa', '123', '<p>1234</p><p><span style=\"text-decoration: underline;\">asasddsa</span></p><p><span style=\"text-decoration: line-through; font-size: 24px;\"><strong>aaaa</strong></span></p><p><span style=\"text-decoration: line-through; font-size: 24px;\"><strong><img src=\"/upload/ueditor/20170929/1506668140631085330.jpg\" title=\"1506668140631085330.jpg\" alt=\"1.jpg\"/></strong></span></p>', '127', '7', '1', '1', '1506326393609', '1', '1506682714047');
INSERT INTO `t_task` VALUES ('85e15c33042e4e7dba000c0470c90a7e', '00001ce4d0114979bc9f7811e4ad4ffa', '4556fdf', '<p>sdfsdfsfd</p>', '37', '5', '1', '1', '1506592677543', null, '1506667592881');

-- ----------------------------
-- Table structure for t_task_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_task_comment`;
CREATE TABLE `t_task_comment` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `task_id` varchar(32) NOT NULL COMMENT '事件id',
  `content` text CHARACTER SET utf8mb4 COMMENT '评论内容',
  `parent_id` varchar(50) DEFAULT '0' COMMENT '父级评论id',
  `like_num` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件评论表';

-- ----------------------------
-- Records of t_task_comment
-- ----------------------------
INSERT INTO `t_task_comment` VALUES ('976960730e024f58983bae315de5a9f4', '2a8b66f90f284a2c84b2a5da9e0edf3d', '1', '0', '0', '1', '06cd6367762049e69338a08ae65a812a', '1506682700574', null);
INSERT INTO `t_task_comment` VALUES ('c93abb92ac2b4c0bb34e1f72d89abf0d', '2a8b66f90f284a2c84b2a5da9e0edf3d', '22', 'd9a73636316444139bbd8762aac0f252', '2', '0', '1', '1506326851359', '1506334165710');
INSERT INTO `t_task_comment` VALUES ('d9a73636316444139bbd8762aac0f252', '2a8b66f90f284a2c84b2a5da9e0edf3d', '1', '0', '12', '1', '1', '1506326835526', '1506333815638');

-- ----------------------------
-- Table structure for t_task_comment_like
-- ----------------------------
DROP TABLE IF EXISTS `t_task_comment_like`;
CREATE TABLE `t_task_comment_like` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '点赞人id',
  `comment_id` varchar(32) NOT NULL COMMENT '评论id',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `status` int(2) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件评论点赞表';

-- ----------------------------
-- Records of t_task_comment_like
-- ----------------------------

-- ----------------------------
-- Table structure for t_task_like
-- ----------------------------
DROP TABLE IF EXISTS `t_task_like`;
CREATE TABLE `t_task_like` (
  `id` varchar(32) NOT NULL COMMENT '点赞',
  `user_id` varchar(32) NOT NULL COMMENT '点赞人',
  `task_id` varchar(32) NOT NULL COMMENT '事件ID',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `status` int(2) NOT NULL COMMENT '状态，1有效，0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件点赞';

-- ----------------------------
-- Records of t_task_like
-- ----------------------------
INSERT INTO `t_task_like` VALUES ('38082eb493734a74b11e21d1b620496c', '1', '85e15c33042e4e7dba000c0470c90a7e', '1506594321005', '1');
INSERT INTO `t_task_like` VALUES ('f24cc77942194be2a1a2a69b9bc5e13e', '1', '2a8b66f90f284a2c84b2a5da9e0edf3d', '1506592109319', '1');

-- ----------------------------
-- Table structure for t_task_value
-- ----------------------------
DROP TABLE IF EXISTS `t_task_value`;
CREATE TABLE `t_task_value` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `task_id` varchar(32) NOT NULL COMMENT '事件id',
  `value_id` varchar(32) NOT NULL COMMENT '属性值id',
  `status` int(2) NOT NULL COMMENT '状态  1有效  0无效',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件标签';

-- ----------------------------
-- Records of t_task_value
-- ----------------------------
INSERT INTO `t_task_value` VALUES ('b7c0eb556cdb4da1919a475f327eb3d4', '2a8b66f90f284a2c84b2a5da9e0edf3d', 'ae817906fd704451b013285df0cb3edc', '1', '1', '1506336836834', '1', '1506336841310');
INSERT INTO `t_task_value` VALUES ('f729561ee1d4402bb08db701d99fdf0e', '2a8b66f90f284a2c84b2a5da9e0edf3d', '55e790d87c864336b0b9440775b34b4c', '1', '1', '1506332343150', '1', '1506332349999');

-- ----------------------------
-- Table structure for t_up
-- ----------------------------
DROP TABLE IF EXISTS `t_up`;
CREATE TABLE `t_up` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `introduction` text COMMENT '简介',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `game_id` varchar(32) DEFAULT NULL COMMENT '所属游戏',
  `popularity_index` bigint(20) DEFAULT NULL COMMENT '人气指数',
  `rank` bigint(20) DEFAULT NULL COMMENT '排名',
  `is_search` int(2) DEFAULT '0' COMMENT '是否可以搜索',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频作者';

-- ----------------------------
-- Records of t_up
-- ----------------------------

-- ----------------------------
-- Table structure for t_up_name
-- ----------------------------
DROP TABLE IF EXISTS `t_up_name`;
CREATE TABLE `t_up_name` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `up_id` varchar(32) NOT NULL COMMENT '视频作者id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `type` int(2) DEFAULT NULL COMMENT '类别 1:主名  2：次名',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `un_upid_idx` (`up_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频作者名称';

-- ----------------------------
-- Records of t_up_name
-- ----------------------------

-- ----------------------------
-- Table structure for t_up_photo
-- ----------------------------
DROP TABLE IF EXISTS `t_up_photo`;
CREATE TABLE `t_up_photo` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `up_id` varchar(32) DEFAULT NULL COMMENT '人物id',
  `up_second_id` varchar(32) DEFAULT NULL COMMENT '人物二级id',
  `photo_name` varchar(255) DEFAULT NULL COMMENT '相册名称',
  `photo` varchar(300) DEFAULT NULL COMMENT '图片地址',
  `is_main` int(2) DEFAULT '0' COMMENT '是否是主图，默认0   0：非主图  1：主图',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人物图册表';

-- ----------------------------
-- Records of t_up_photo
-- ----------------------------

-- ----------------------------
-- Table structure for t_up_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_up_relation`;
CREATE TABLE `t_up_relation` (
  `id` varchar(32) NOT NULL,
  `up_id` varchar(32) DEFAULT NULL COMMENT '人物id',
  `relation_up_id` varchar(32) DEFAULT NULL COMMENT '相关人物id',
  `relation_desc` varchar(60) DEFAULT NULL COMMENT '关系描述',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `up_id_relation_idx` (`up_id`,`relation_up_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人物关系表';

-- ----------------------------
-- Records of t_up_relation
-- ----------------------------

-- ----------------------------
-- Table structure for t_up_second_level
-- ----------------------------
DROP TABLE IF EXISTS `t_up_second_level`;
CREATE TABLE `t_up_second_level` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父id',
  `up_id` varchar(255) DEFAULT NULL COMMENT '人物id',
  `title_type` int(3) DEFAULT NULL COMMENT '标题类型，字典存储',
  `content` text COMMENT '内容',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人物二级信息表';

-- ----------------------------
-- Records of t_up_second_level
-- ----------------------------

-- ----------------------------
-- Table structure for t_up_station
-- ----------------------------
DROP TABLE IF EXISTS `t_up_station`;
CREATE TABLE `t_up_station` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `up_id` varchar(32) DEFAULT NULL COMMENT '视频作者id',
  `home_id` varchar(200) DEFAULT NULL COMMENT '个人主页ID（非本站ID）',
  `home_url` varchar(200) DEFAULT NULL COMMENT '个人主页地址',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `up_introduction` varchar(500) DEFAULT NULL COMMENT '简介',
  `up_avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `up_video_count` bigint(20) DEFAULT NULL COMMENT '视频数量',
  `up_fans_count` bigint(20) DEFAULT NULL COMMENT '粉丝数量',
  `up_friend_count` bigint(20) DEFAULT NULL COMMENT '关注数量',
  `up_play_count` bigint(20) DEFAULT NULL COMMENT '播放数量',
  `station` int(2) DEFAULT NULL COMMENT '所属站点',
  `third_party` int(2) DEFAULT NULL COMMENT '所属第三方',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `us_s_h` (`station`,`home_id`) USING BTREE,
  KEY `us_u_v_f_p_f` (`up_id`,`up_video_count`,`up_fans_count`,`up_play_count`,`up_friend_count`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频作者站点';

-- ----------------------------
-- Records of t_up_station
-- ----------------------------

-- ----------------------------
-- Table structure for t_up_value
-- ----------------------------
DROP TABLE IF EXISTS `t_up_value`;
CREATE TABLE `t_up_value` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `up_id` varchar(32) NOT NULL COMMENT '视频作者id',
  `value_id` varchar(32) NOT NULL COMMENT '属性值id',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uv_u_v` (`up_id`,`value_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频作者属性值关联';

-- ----------------------------
-- Records of t_up_value
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_forget_password
-- ----------------------------
DROP TABLE IF EXISTS `t_user_forget_password`;
CREATE TABLE `t_user_forget_password` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `type` int(2) DEFAULT NULL COMMENT '用户反馈类型：0：用户忘记密码  1：正在开发中',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户',
  `forget_reason` varchar(300) DEFAULT NULL,
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_forget_password
-- ----------------------------
INSERT INTO `t_user_forget_password` VALUES ('0fd39cd256e34f35a7b5f214a2b03835', '1', null, '什么时候对外', '1', null, '1500539202279');
INSERT INTO `t_user_forget_password` VALUES ('40c535132c1145f2b8a3252c03fdb36e', '1', null, 'مرتضییی', '1', null, '1501589384477');
INSERT INTO `t_user_forget_password` VALUES ('438ef7661b1940cabb9850ea2634ec15', '1', null, 'dota地图', '1', null, '1502355571388');
INSERT INTO `t_user_forget_password` VALUES ('4736748b54894cb49bb87d2b1165c55a', '1', '12e83fde1ef44aa29609785ec03433b6', '还好，试试。', '1', null, '1502424195880');
INSERT INTO `t_user_forget_password` VALUES ('8627e4c52b7d459cbd107686e7a57efa', '1', null, 'مرتضی', '1', null, '1501589224595');
INSERT INTO `t_user_forget_password` VALUES ('c034943cd6f64ba78801a4bad6f809e3', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', '1', null, '1501557714541');
INSERT INTO `t_user_forget_password` VALUES ('cfeb69c88d164b46a7c0af41f722ede3', '0', '36eedbaea8514cb6a28b6f97f19cd970', null, '1', null, '1500539314710');
INSERT INTO `t_user_forget_password` VALUES ('e09bab131a17407982aa3b9e5fdb4ccc', '1', '31ecce68d6fa4185a3798b9ef7d92cf4', '123213', '1', null, '1504774000344');
INSERT INTO `t_user_forget_password` VALUES ('fa6475c4f17b4909b6999b35514a007d', '0', '2acfe173afc14c2fa1836ebb771d7acb', '最近修改了密码，没记住', '1', null, '1500536145182');

-- ----------------------------
-- Table structure for t_user_increment
-- ----------------------------
DROP TABLE IF EXISTS `t_user_increment`;
CREATE TABLE `t_user_increment` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `sex` int(2) DEFAULT NULL COMMENT '性别  1：男  0：女',
  `birthday` bigint(20) DEFAULT NULL COMMENT '生日',
  `integral` int(11) DEFAULT '0' COMMENT '总积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户增量表';

-- ----------------------------
-- Records of t_user_increment
-- ----------------------------
INSERT INTO `t_user_increment` VALUES ('1', '1', '0', null, '1480920155066', '1', '1489132001065', '1', null, null);
INSERT INTO `t_user_increment` VALUES ('59e9d400817f448f9f275fcd1e7bfdb8', 'fb81845169554873a8e809c4610a463e', '1', null, '1500519960223', null, null, null, null, '10');
INSERT INTO `t_user_increment` VALUES ('5faeb4700c384f08bb29f87decd38d4a', '06cd6367762049e69338a08ae65a812a', '1', null, '1500619934745', null, null, null, null, '40');
INSERT INTO `t_user_increment` VALUES ('653046392fe6421bac95061dee02f737', 'fe367eb73b164e7797ccfe5bab9171a6', '1', null, '1501143029998', null, null, '0', '383972393402', '40');
INSERT INTO `t_user_increment` VALUES ('721fb32a6bf2494b9e421be0566951ef', '6ab0651ce12b44daafbcee76de129bb4', '1', null, '1500978155317', null, null, null, null, '10');
INSERT INTO `t_user_increment` VALUES ('740aa5e8079c4ddf9423a5a9c93d99b4', '2169db33b2f64697a84d4027dbd7bcc3', '1', null, '1500888865843', null, null, null, null, '15');
INSERT INTO `t_user_increment` VALUES ('84de0d2f27104b728e8ffb7a62a3f02a', 'a4237b3b08c84a229fde55509068d639', '1', null, '1500954024235', null, null, null, null, '15');
INSERT INTO `t_user_increment` VALUES ('89a6263edcaa4aa7b3e045141f68372a', '896c70c78ab14afd9594fcb9faf0b52e', '1', null, '1500521160436', null, null, '1', '734930912194', '25');
INSERT INTO `t_user_increment` VALUES ('8f03f7791b284870bf0f193b83d0ea97', 'd196dddf1d714336ad987a26d544d990', '1', null, '1500879015245', null, null, null, null, '15');
INSERT INTO `t_user_increment` VALUES ('936001d981b34e999fbda88f841d6867', '31ecce68d6fa4185a3798b9ef7d92cf4', '1', null, '1504771240417', null, null, null, null, '15');
INSERT INTO `t_user_increment` VALUES ('b086e9d2916644278c1c8129faf4fa62', '02836c03b3bc4f07a697f2d05f70100b', '1', null, '1502100609876', null, null, null, null, '10');
INSERT INTO `t_user_increment` VALUES ('c2276e82f1474437bd98099d230ca772', '7540d5600b4e4236a6586326bbb60418', '1', null, '1501835654585', null, null, null, null, '10');
INSERT INTO `t_user_increment` VALUES ('c6711c6e29aa42a98e6d9a225cf4631f', '8e9ba9fdb29c4826a65984820155d282', '1', null, '1502164162103', null, null, null, null, '10');
INSERT INTO `t_user_increment` VALUES ('e435d29cacab4775990dac9f9e5abfd5', '12e83fde1ef44aa29609785ec03433b6', '1', null, '1500521691774', null, null, null, null, '15');
INSERT INTO `t_user_increment` VALUES ('f1ae7b9d9b4f4abe9603b80a6dc357b2', '36eedbaea8514cb6a28b6f97f19cd970', '1', null, '1500447510851', null, null, null, null, '20');
INSERT INTO `t_user_increment` VALUES ('f9109f7f6afc41b9b4bcffc2bdbc5949', '2acfe173afc14c2fa1836ebb771d7acb', '1', null, '1500522267082', null, null, null, null, '10');

-- ----------------------------
-- Table structure for t_user_integral
-- ----------------------------
DROP TABLE IF EXISTS `t_user_integral`;
CREATE TABLE `t_user_integral` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `type` int(3) DEFAULT NULL COMMENT '积分类型，0：注册 1：上传头像',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户',
  `integral` int(11) DEFAULT NULL COMMENT '积分',
  `integral_source` varchar(255) DEFAULT NULL,
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户积分表';

-- ----------------------------
-- Records of t_user_integral
-- ----------------------------
INSERT INTO `t_user_integral` VALUES ('006caa33673a4af2b844b8536fcc7719', '1', 'a4237b3b08c84a229fde55509068d639', '5', '签到', '1', 'a4237b3b08c84a229fde55509068d639', '1500954097398');
INSERT INTO `t_user_integral` VALUES ('008f9e30f76e43e2ba3ad2b98a773fd0', '1', '2169db33b2f64697a84d4027dbd7bcc3', '5', '首次上传头像', '1', '2169db33b2f64697a84d4027dbd7bcc3', '1500888936329');
INSERT INTO `t_user_integral` VALUES ('086cf316270548999064ed14ee91cfe6', '2', '896c70c78ab14afd9594fcb9faf0b52e', '5', '首次设置昵称', '1', '896c70c78ab14afd9594fcb9faf0b52e', '1500521174571');
INSERT INTO `t_user_integral` VALUES ('0df7e038ab8b4092a1abdb38e9162039', '2', 'a4237b3b08c84a229fde55509068d639', '5', '首次设置昵称', '1', 'a4237b3b08c84a229fde55509068d639', '1500954028728');
INSERT INTO `t_user_integral` VALUES ('1066b6d1e1524eb49f64b7fb2d3dff9b', '0', '6ab0651ce12b44daafbcee76de129bb4', '10', '注册', '1', '6ab0651ce12b44daafbcee76de129bb4', '1500978155357');
INSERT INTO `t_user_integral` VALUES ('1879bda8f0de40ccad15db9bafceeb5a', '1', '12e83fde1ef44aa29609785ec03433b6', '5', '签到', '1', '12e83fde1ef44aa29609785ec03433b6', '1502633360944');
INSERT INTO `t_user_integral` VALUES ('1b58e1b49da642d29e0c511c4c616bf9', '1', '31ecce68d6fa4185a3798b9ef7d92cf4', '5', '签到', '1', '31ecce68d6fa4185a3798b9ef7d92cf4', '1504771394686');
INSERT INTO `t_user_integral` VALUES ('1b86c86fb5764374a70d62753480ae8a', '1', '06cd6367762049e69338a08ae65a812a', '5', '签到', '1', '06cd6367762049e69338a08ae65a812a', '1502680031425');
INSERT INTO `t_user_integral` VALUES ('1ee69771925e4bf2b6c1d2c7f19b27e9', '1', '896c70c78ab14afd9594fcb9faf0b52e', '5', '首次上传头像', '1', '896c70c78ab14afd9594fcb9faf0b52e', '1500521189352');
INSERT INTO `t_user_integral` VALUES ('1f4222828ddb43869cc6d2ce83ebe558', '1', '12e83fde1ef44aa29609785ec03433b6', '5', '签到', '1', '12e83fde1ef44aa29609785ec03433b6', '1500735416360');
INSERT INTO `t_user_integral` VALUES ('202e5ee4d343439b9a953beaf160c21a', '1', '2169db33b2f64697a84d4027dbd7bcc3', '5', '签到', '1', '2169db33b2f64697a84d4027dbd7bcc3', '1500889000189');
INSERT INTO `t_user_integral` VALUES ('230dd059c8714042be68b2064de89353', '2', '6ab0651ce12b44daafbcee76de129bb4', '5', '首次设置昵称', '1', '6ab0651ce12b44daafbcee76de129bb4', '1500978164011');
INSERT INTO `t_user_integral` VALUES ('23dc54e714c345d883d23993dc15b5f3', '0', '896c70c78ab14afd9594fcb9faf0b52e', '10', '注册', '1', '896c70c78ab14afd9594fcb9faf0b52e', '1500521160440');
INSERT INTO `t_user_integral` VALUES ('24cdaa5e64a34b6f9a1bbbde9d3b9bd4', '4', '896c70c78ab14afd9594fcb9faf0b52e', '5', '设置生日', '1', '896c70c78ab14afd9594fcb9faf0b52e', '1500521322909');
INSERT INTO `t_user_integral` VALUES ('26ea9e8a62fa4893b14e2189ff5a87d3', '1', '31ecce68d6fa4185a3798b9ef7d92cf4', '5', '首次上传头像', '1', '31ecce68d6fa4185a3798b9ef7d92cf4', '1504771367851');
INSERT INTO `t_user_integral` VALUES ('278c8b2733d74a5588055b7aab6f60b3', '2', '36eedbaea8514cb6a28b6f97f19cd970', '5', '首次设置昵称', '1', '36eedbaea8514cb6a28b6f97f19cd970', '1500447529567');
INSERT INTO `t_user_integral` VALUES ('280cda20683144e88c6110f5db48021a', '1', '1', '5', '签到', '1', '1', '1502691626128');
INSERT INTO `t_user_integral` VALUES ('29ac83415d8c405caad1fc08f3c26bec', '1', '36eedbaea8514cb6a28b6f97f19cd970', '5', '签到', '1', '36eedbaea8514cb6a28b6f97f19cd970', '1500447554493');
INSERT INTO `t_user_integral` VALUES ('2b529af802c14a1f8ab92c9d8e0a4be2', '1', '12e83fde1ef44aa29609785ec03433b6', '5', '首次上传头像', '1', '12e83fde1ef44aa29609785ec03433b6', '1500521904348');
INSERT INTO `t_user_integral` VALUES ('2bd9c569700e485da5ea5f0b0afc2ddd', '5', '896c70c78ab14afd9594fcb9faf0b52e', '5', '设置性别', '1', '896c70c78ab14afd9594fcb9faf0b52e', '1500521303854');
INSERT INTO `t_user_integral` VALUES ('2ec19ceb28214ed18b7d8c3056c6194a', '5', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '设置性别', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501556411690');
INSERT INTO `t_user_integral` VALUES ('328eaf2a5c914dffb152da30c0c865b1', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '签到', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501556002627');
INSERT INTO `t_user_integral` VALUES ('3f7f79bf4a474fb8afd388cf1aa18a29', '2', '8e9ba9fdb29c4826a65984820155d282', '5', '首次设置昵称', '1', '8e9ba9fdb29c4826a65984820155d282', '1502164205860');
INSERT INTO `t_user_integral` VALUES ('4169e28ae7f74bbfa8107fbc34af2b32', '0', '2169db33b2f64697a84d4027dbd7bcc3', '10', '注册', '1', '2169db33b2f64697a84d4027dbd7bcc3', '1500888865847');
INSERT INTO `t_user_integral` VALUES ('45e9a74671724fb298533860e08eb372', '1', '12e83fde1ef44aa29609785ec03433b6', '5', '签到', '1', '12e83fde1ef44aa29609785ec03433b6', '1501730342532');
INSERT INTO `t_user_integral` VALUES ('47302ff8f4b14de38af2838837148edd', '2', '06cd6367762049e69338a08ae65a812a', '5', '首次设置昵称', '1', '06cd6367762049e69338a08ae65a812a', '1500619939349');
INSERT INTO `t_user_integral` VALUES ('48dc92e5a884487a8721b79ff0319105', '0', '12e83fde1ef44aa29609785ec03433b6', '10', '注册', '1', '12e83fde1ef44aa29609785ec03433b6', '1500521691776');
INSERT INTO `t_user_integral` VALUES ('49a6e63d10c54b8e97a2fd2c585df892', '8', '06cd6367762049e69338a08ae65a812a', '10', '完成每日评论奖励', '1', '06cd6367762049e69338a08ae65a812a', '1502679886771');
INSERT INTO `t_user_integral` VALUES ('4a082fe163444e139ac3d0d561e9c0b1', '1', '6ab0651ce12b44daafbcee76de129bb4', '5', '签到', '1', '6ab0651ce12b44daafbcee76de129bb4', '1500978178700');
INSERT INTO `t_user_integral` VALUES ('4bd46e5570d44c89a056ff0390efa6d5', '3', '1', '5', '添加兴趣', '1', '1', '1504164781638');
INSERT INTO `t_user_integral` VALUES ('4c2bec640f054698a53afe886348b8f7', '0', '06cd6367762049e69338a08ae65a812a', '10', '注册', '1', '06cd6367762049e69338a08ae65a812a', '1500619934759');
INSERT INTO `t_user_integral` VALUES ('4c826c8a8de14cfbae36dc10084389a1', '0', '2acfe173afc14c2fa1836ebb771d7acb', '10', '注册', '1', '2acfe173afc14c2fa1836ebb771d7acb', '1500522267096');
INSERT INTO `t_user_integral` VALUES ('53ea2cc4574f49c6a1b1f83832c7673f', '3', '36eedbaea8514cb6a28b6f97f19cd970', '5', '添加兴趣', '1', '36eedbaea8514cb6a28b6f97f19cd970', '1501147430348');
INSERT INTO `t_user_integral` VALUES ('58d8b87911954f9db02adb322eca7531', '8', '06cd6367762049e69338a08ae65a812a', '10', '完成每日评论奖励', '1', '06cd6367762049e69338a08ae65a812a', '1502679843433');
INSERT INTO `t_user_integral` VALUES ('5d021c5f70db4e9984e027ada7d60084', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '签到', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501640877124');
INSERT INTO `t_user_integral` VALUES ('635c718b00a44a28b34e1c19bc239cbd', '0', '8e9ba9fdb29c4826a65984820155d282', '10', '注册', '1', '8e9ba9fdb29c4826a65984820155d282', '1502164162116');
INSERT INTO `t_user_integral` VALUES ('647f6ba201b044f19c01f3b555504681', '1', 'd196dddf1d714336ad987a26d544d990', '5', '首次上传头像', '1', 'd196dddf1d714336ad987a26d544d990', '1500879037393');
INSERT INTO `t_user_integral` VALUES ('668537f863304c2798196620e204dd08', '0', '02836c03b3bc4f07a697f2d05f70100b', '10', '注册', '1', '02836c03b3bc4f07a697f2d05f70100b', '1502100609880');
INSERT INTO `t_user_integral` VALUES ('66e1957e98344c74aac857e736473390', '1', 'fb81845169554873a8e809c4610a463e', '5', '签到', '1', 'fb81845169554873a8e809c4610a463e', '1500519973138');
INSERT INTO `t_user_integral` VALUES ('69a794d8854b41d5badf52bc462a7724', '4', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '设置生日', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501556404855');
INSERT INTO `t_user_integral` VALUES ('718fd019b92f44239674c0d800432351', '3', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '添加兴趣', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144775837');
INSERT INTO `t_user_integral` VALUES ('739e115c34234106853d4565c0c8960e', '2', 'd196dddf1d714336ad987a26d544d990', '5', '首次设置昵称', '1', 'd196dddf1d714336ad987a26d544d990', '1500879021140');
INSERT INTO `t_user_integral` VALUES ('750374304aef4adeb89f67ef26085162', '0', '36eedbaea8514cb6a28b6f97f19cd970', '10', '注册', '1', '36eedbaea8514cb6a28b6f97f19cd970', '1500447510855');
INSERT INTO `t_user_integral` VALUES ('78f195443ff048a79c5671beb56efb25', '8', '06cd6367762049e69338a08ae65a812a', '10', '完成每日评论奖励', '1', '06cd6367762049e69338a08ae65a812a', '1502679874954');
INSERT INTO `t_user_integral` VALUES ('7926fafc946848d3a7a30e071b3a29da', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '签到', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501212669231');
INSERT INTO `t_user_integral` VALUES ('795b9a8fc08846b0bc766ff546264e90', '1', '12e83fde1ef44aa29609785ec03433b6', '5', '签到', '1', '12e83fde1ef44aa29609785ec03433b6', '1500953033640');
INSERT INTO `t_user_integral` VALUES ('7a156b86f4324d56b6a710b8f0d8d49a', '2', '2acfe173afc14c2fa1836ebb771d7acb', '5', '首次设置昵称', '1', '2acfe173afc14c2fa1836ebb771d7acb', '1500522271855');
INSERT INTO `t_user_integral` VALUES ('80a5802eed554c28b22e36dea0309f2e', '2', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '首次设置昵称', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501143044252');
INSERT INTO `t_user_integral` VALUES ('810151e92194436abffdc34f6b11a7c5', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '签到', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501482110695');
INSERT INTO `t_user_integral` VALUES ('813adfb9c0f54b438a569b80d74abcf5', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '签到', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1502265635262');
INSERT INTO `t_user_integral` VALUES ('82fd59da94a24cbbb0c0898fded623cf', '0', 'fb81845169554873a8e809c4610a463e', '10', '注册', '1', 'fb81845169554873a8e809c4610a463e', '1500519960237');
INSERT INTO `t_user_integral` VALUES ('87ad090b783545b0aed3cb419c96b9c3', '1', '7540d5600b4e4236a6586326bbb60418', '5', '签到', '1', '7540d5600b4e4236a6586326bbb60418', '1501835971701');
INSERT INTO `t_user_integral` VALUES ('880e58bcd7954c6ba96b79b7aee9ffce', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '首次上传头像', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501143102971');
INSERT INTO `t_user_integral` VALUES ('8bf8d36f95b646798dcb816039870cc4', '0', 'a4237b3b08c84a229fde55509068d639', '10', '注册', '1', 'a4237b3b08c84a229fde55509068d639', '1500954024246');
INSERT INTO `t_user_integral` VALUES ('90a37783b9e34c4ab6b6bd524c94ba65', '2', '7540d5600b4e4236a6586326bbb60418', '5', '首次设置昵称', '1', '7540d5600b4e4236a6586326bbb60418', '1501835662143');
INSERT INTO `t_user_integral` VALUES ('94efbfa0f81344bbaa2b30d8ecba1fa3', '2', '2169db33b2f64697a84d4027dbd7bcc3', '5', '首次设置昵称', '1', '2169db33b2f64697a84d4027dbd7bcc3', '1500888871021');
INSERT INTO `t_user_integral` VALUES ('960df9cb9fb04a259a238fb6499ea30c', '0', 'fe367eb73b164e7797ccfe5bab9171a6', '10', '注册', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501143030029');
INSERT INTO `t_user_integral` VALUES ('9c84995ad2764aa78cfb0332512da894', '2', 'fb81845169554873a8e809c4610a463e', '5', '首次设置昵称', '1', 'fb81845169554873a8e809c4610a463e', '1500519964088');
INSERT INTO `t_user_integral` VALUES ('a28f2e19b2f64b91a97e25698577c1ae', '0', '31ecce68d6fa4185a3798b9ef7d92cf4', '10', '注册', '1', '31ecce68d6fa4185a3798b9ef7d92cf4', '1504771240481');
INSERT INTO `t_user_integral` VALUES ('a6ac625485034810876927b124025abe', '2', '1', '5', '首次设置昵称', '1', '1', '1502693012518');
INSERT INTO `t_user_integral` VALUES ('a6c947fe4db342af87e3a3d81b18a93b', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '签到', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144599505');
INSERT INTO `t_user_integral` VALUES ('b4fea8af72f5428a8f425920ceeb6bfc', '1', '12e83fde1ef44aa29609785ec03433b6', '5', '签到', '1', '12e83fde1ef44aa29609785ec03433b6', '1501667049337');
INSERT INTO `t_user_integral` VALUES ('b6ef75c725b944dd880839a19e3f069a', '2', '02836c03b3bc4f07a697f2d05f70100b', '5', '首次设置昵称', '1', '02836c03b3bc4f07a697f2d05f70100b', '1502100621808');
INSERT INTO `t_user_integral` VALUES ('b781e17f8b964521a68baa6ceb486371', '0', '7540d5600b4e4236a6586326bbb60418', '10', '注册', '1', '7540d5600b4e4236a6586326bbb60418', '1501835654590');
INSERT INTO `t_user_integral` VALUES ('bf077c6d9aa148ea9c7147e47096a2dd', '6', 'fe367eb73b164e7797ccfe5bab9171a6', '10', '完成用户信息奖励', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501556433056');
INSERT INTO `t_user_integral` VALUES ('ca79bbb3d45f4a9dac89b01262e5ce55', '2', '12e83fde1ef44aa29609785ec03433b6', '5', '首次设置昵称', '1', '12e83fde1ef44aa29609785ec03433b6', '1500521739514');
INSERT INTO `t_user_integral` VALUES ('cf430009a940499bb230db1ab11a6347', '2', '31ecce68d6fa4185a3798b9ef7d92cf4', '5', '首次设置昵称', '1', '31ecce68d6fa4185a3798b9ef7d92cf4', '1504771246365');
INSERT INTO `t_user_integral` VALUES ('d029259b91204d989e9403efd76e70d7', '1', '36eedbaea8514cb6a28b6f97f19cd970', '5', '签到', '1', '36eedbaea8514cb6a28b6f97f19cd970', '1500517095365');
INSERT INTO `t_user_integral` VALUES ('d300bafce6f044b386a9f5425ec7fead', '1', '1', '5', '签到', '1', '1', '1500446064135');
INSERT INTO `t_user_integral` VALUES ('de134c1398e843f29beb8fe18782916c', '1', '1', '5', '签到', '1', '1', '1502096987911');
INSERT INTO `t_user_integral` VALUES ('dea64b3ed7934671aa0570f6dd8971c0', '0', 'd196dddf1d714336ad987a26d544d990', '10', '注册', '1', 'd196dddf1d714336ad987a26d544d990', '1500879015256');
INSERT INTO `t_user_integral` VALUES ('e21627a65230438798112f96076ef278', '1', '12e83fde1ef44aa29609785ec03433b6', '5', '签到', '1', '12e83fde1ef44aa29609785ec03433b6', '1500522915470');
INSERT INTO `t_user_integral` VALUES ('e4993a27f5e04fe4996b56b4286bb902', '1', '12e83fde1ef44aa29609785ec03433b6', '5', '签到', '1', '12e83fde1ef44aa29609785ec03433b6', '1501479778173');
INSERT INTO `t_user_integral` VALUES ('f179ccc545b349e3abb2041677e284b2', '1', 'a4237b3b08c84a229fde55509068d639', '5', '首次上传头像', '1', 'a4237b3b08c84a229fde55509068d639', '1500954082601');
INSERT INTO `t_user_integral` VALUES ('fa9648466c54480fa4316995d881e9c2', '1', '36eedbaea8514cb6a28b6f97f19cd970', '5', '首次上传头像', '1', '36eedbaea8514cb6a28b6f97f19cd970', '1500447542025');
INSERT INTO `t_user_integral` VALUES ('fbf1ce964352471aaaf85619880635b2', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '5', '签到', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501729323488');
INSERT INTO `t_user_integral` VALUES ('ff06327569434f818166b86d2f8c50cf', '1', '12e83fde1ef44aa29609785ec03433b6', '5', '签到', '1', '12e83fde1ef44aa29609785ec03433b6', '1502424061500');

-- ----------------------------
-- Table structure for t_user_interest
-- ----------------------------
DROP TABLE IF EXISTS `t_user_interest`;
CREATE TABLE `t_user_interest` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户',
  `interest` varchar(60) DEFAULT NULL COMMENT '兴趣',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户兴趣表';

-- ----------------------------
-- Records of t_user_interest
-- ----------------------------
INSERT INTO `t_user_interest` VALUES ('05c51754f27045f0bdb4f0ae67539906', '1', '123', '1', '1', '1504164790334', null, null);
INSERT INTO `t_user_interest` VALUES ('1a53adfcab7849479c4b25f45c8fe5c9', 'fe367eb73b164e7797ccfe5bab9171a6', '哈哈哈哈哈哈哈哈', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501556148492', null, null);
INSERT INTO `t_user_interest` VALUES ('2a282315aa784f769b807f91e1630ca5', 'fe367eb73b164e7797ccfe5bab9171a6', '五位数测试', '0', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144786305', null, null);
INSERT INTO `t_user_interest` VALUES ('348dd8a64fd242f58ff7b15d43debd38', 'fe367eb73b164e7797ccfe5bab9171a6', '666', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144875863', null, null);
INSERT INTO `t_user_interest` VALUES ('3b32bd42169541699953b1fdf80fd449', 'fe367eb73b164e7797ccfe5bab9171a6', '颜色不一样', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144990641', null, null);
INSERT INTO `t_user_interest` VALUES ('46bc95f3a3e444be8849f36de22bb2e7', 'fe367eb73b164e7797ccfe5bab9171a6', '测试', '0', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144775833', null, null);
INSERT INTO `t_user_interest` VALUES ('6043864b066c4b16b1f7ef24bd2fd409', '1', '222', '0', '1', '1504164854233', null, null);
INSERT INTO `t_user_interest` VALUES ('82290140149543e6afa63b79094bef8b', '1', '234', '1', '1', '1504164793887', null, null);
INSERT INTO `t_user_interest` VALUES ('88a9539158b94510959ac8a40f42a17e', '36eedbaea8514cb6a28b6f97f19cd970', '第三方的', '1', '36eedbaea8514cb6a28b6f97f19cd970', '1501147430309', null, null);
INSERT INTO `t_user_interest` VALUES ('89846efe24374a5d94b9d593755b9f7b', '36eedbaea8514cb6a28b6f97f19cd970', '呜呜呜', '1', '36eedbaea8514cb6a28b6f97f19cd970', '1501147485398', null, null);
INSERT INTO `t_user_interest` VALUES ('8ba4718dddcf437bb946a04f7c47a52c', '1', '2222', '1', '1', '1504164927690', null, null);
INSERT INTO `t_user_interest` VALUES ('8c1bf30148524eaba953931f0618ce95', '1', 'qqq', '0', '1', '1504164800973', null, null);
INSERT INTO `t_user_interest` VALUES ('8c82f399d5b84566bf34406595f1e3ad', 'fe367eb73b164e7797ccfe5bab9171a6', '是事实上四是', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144998184', null, null);
INSERT INTO `t_user_interest` VALUES ('9dfbe1418b7042f4903b24458e9454da', '36eedbaea8514cb6a28b6f97f19cd970', '是的当然', '0', '36eedbaea8514cb6a28b6f97f19cd970', '1501147444454', null, null);
INSERT INTO `t_user_interest` VALUES ('bfcd42142908470d98343866a67c6f65', '1', '456', '0', '1', '1504164797588', null, null);
INSERT INTO `t_user_interest` VALUES ('df31e488bcb7480eb0d0663076093069', '1', 'test', '1', '1', '1504164781604', null, null);
INSERT INTO `t_user_interest` VALUES ('e3fc7af93d144a41a714c2028c904a67', '1', '111', '0', '1', '1504164828195', null, null);
INSERT INTO `t_user_interest` VALUES ('ef4c54b85504412ea9612d997df78ee8', 'fe367eb73b164e7797ccfe5bab9171a6', '111', '1', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144872323', null, null);
INSERT INTO `t_user_interest` VALUES ('f7eb4e02d56641a2a85b13864d5a0057', 'fe367eb73b164e7797ccfe5bab9171a6', '试一试', '0', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144782104', null, null);

-- ----------------------------
-- Table structure for t_user_message
-- ----------------------------
DROP TABLE IF EXISTS `t_user_message`;
CREATE TABLE `t_user_message` (
  `ID` varchar(32) NOT NULL COMMENT '唯一标识',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID（接收用户）',
  `BUSINESS_TYPE` int(11) DEFAULT NULL COMMENT '业务类型 1:评论',
  `BUSINESS_ID` varchar(32) DEFAULT NULL COMMENT '业务ID',
  `CONTENT` varchar(512) DEFAULT NULL COMMENT '消息描述',
  `STATE` int(11) DEFAULT NULL COMMENT '阅读状态 1：已读，0：未读',
  `IS_DELETED` int(11) DEFAULT NULL COMMENT '默认0     1：已删除   0：正常',
  `CREATE_TIME` bigint(20) DEFAULT NULL COMMENT '消息创建时间',
  `UPDATE_TIME` bigint(20) DEFAULT NULL COMMENT '更新已读状态时，更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_message
-- ----------------------------
INSERT INTO `t_user_message` VALUES ('0199edd1370146cca8f1a418a9dd132d', 'fe367eb73b164e7797ccfe5bab9171a6', '1', 'ad7c3d48e5f647e598a7fd9cd72ef221', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：test</a>', '1', '0', '1501556920687', '1501558575721');
INSERT INTO `t_user_message` VALUES ('08089b3439604a2c8a5f1f742f4dbb30', 'fe367eb73b164e7797ccfe5bab9171a6', '1', 'a1c396b4c2004aadb33f49ab018ced30', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：；；；；；；</a>', '1', '0', '1501557050880', '1501558575721');
INSERT INTO `t_user_message` VALUES ('0ca720e0605b44e3b41bc1dc92382bcb', '36eedbaea8514cb6a28b6f97f19cd970', '1', '6db87ec2746f4faca572d43f02f79541', '<a href=\"/video/a86842c86a6b40ab8a79a73ff9a337ef\">素颜 在 三国杀解说幻影,三国杀的山寨时代,乱侃三国杀 中回复我：对自己好点</a>', '0', '0', '1500602424355', null);
INSERT INTO `t_user_message` VALUES ('0d2d5030bc194fb49ca8900d1b1171e9', '12e83fde1ef44aa29609785ec03433b6', '1', '00edfb03eda94e849e6c13e3db7d2f3a', '<a href=\"/video/a86842c86a6b40ab8a79a73ff9a337ef\">大支 在 三国杀解说幻影,三国杀的山寨时代,乱侃三国杀 中回复我：呵呵。。。</a>', '1', '0', '1502424568371', '1502633365717');
INSERT INTO `t_user_message` VALUES ('11be4b9d19d04805ae8c9f2b23d3199d', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '9200be3ebe0649bc881a5549340da6f7', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：好尴尬</a>', '1', '0', '1501557020993', '1501558575721');
INSERT INTO `t_user_message` VALUES ('12a440b8aba64fe68bf0813a71ee33c3', 'fe367eb73b164e7797ccfe5bab9171a6', '1', 'be4a98e6b82b4977b6955777e21955ae', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：；；；；；</a>', '1', '0', '1501557098938', '1501558575721');
INSERT INTO `t_user_message` VALUES ('19b7b748d2d145d88fb4afa22265be6d', '06cd6367762049e69338a08ae65a812a', '2', '49a6e63d10c54b8e97a2fd2c585df892', '恭喜，您完成了每日10条评论,成功获得10个积分。', '1', '0', '1502679887471', '1502680021238');
INSERT INTO `t_user_message` VALUES ('29ecd936724b4027a50d6ac942c8d5a9', '2169db33b2f64697a84d4027dbd7bcc3', '3', '299ce1abfd904adc8e83692f55e2a7da', null, '1', null, '1500889025631', '1500889025631');
INSERT INTO `t_user_message` VALUES ('33028d6fb83c44bdb95d0aad7df8546b', '896c70c78ab14afd9594fcb9faf0b52e', '3', '299ce1abfd904adc8e83692f55e2a7da', null, '1', null, '1500521371079', '1500521371079');
INSERT INTO `t_user_message` VALUES ('351a6c54116c4122af4a3033eaf853cc', '12e83fde1ef44aa29609785ec03433b6', '3', '299ce1abfd904adc8e83692f55e2a7da', null, '1', null, '1500522920493', '1500522920493');
INSERT INTO `t_user_message` VALUES ('443ecce3b80f4ae1a2c5967d3ff92386', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '609c254710524d379a2a0314e15dccf4', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：test</a>', '1', '0', '1501556931788', '1501558575721');
INSERT INTO `t_user_message` VALUES ('456302f8e09d40b79c72d7c201aa21f0', 'fe367eb73b164e7797ccfe5bab9171a6', '1', 'c0d4241902ba432482c50abef4c38d9d', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：。。。。。</a>', '1', '0', '1501557104523', '1501558575721');
INSERT INTO `t_user_message` VALUES ('4f9091bf929543f4bfa92fe88df36f8b', '06cd6367762049e69338a08ae65a812a', '2', '78f195443ff048a79c5671beb56efb25', '恭喜，您完成了每日10条评论,成功获得10个积分。', '1', '0', '1502679874960', '1502680021764');
INSERT INTO `t_user_message` VALUES ('58d654bef73948bb83af1230278ba7e3', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '73e381595f6f463987a21493d7c002e4', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：；；；；；；；；</a>', '1', '0', '1501557042519', '1501558575721');
INSERT INTO `t_user_message` VALUES ('5f6d348e4dc64ef0be7b471b51b3005c', 'fe367eb73b164e7797ccfe5bab9171a6', '1', 'd4eb25c4f75a42cca92fa9f8e2cab91a', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：test</a>', '1', '0', '1501226711968', '1501490058779');
INSERT INTO `t_user_message` VALUES ('6417274408594d62be981dbe4b89f985', '1', '3', '299ce1abfd904adc8e83692f55e2a7da', null, '1', null, '1500437674644', '1500437674644');
INSERT INTO `t_user_message` VALUES ('7d2204b259264e049833445fea4bc36f', '06cd6367762049e69338a08ae65a812a', '3', '299ce1abfd904adc8e83692f55e2a7da', null, '1', null, '1500969395599', '1500969395599');
INSERT INTO `t_user_message` VALUES ('807f3599616d459d9977c544f1f2b897', '12e83fde1ef44aa29609785ec03433b6', '1', '0fb47e33242a48468bcc834f85b0984f', '<a href=\"/video/a86842c86a6b40ab8a79a73ff9a337ef\">大支 在 三国杀解说幻影,三国杀的山寨时代,乱侃三国杀 中回复我：我去。</a>', '1', '0', '1502633386734', '1502633672363');
INSERT INTO `t_user_message` VALUES ('8436f8d24f9a4c90b4436e0a58326dbb', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '0a7f50ca975447c4b548a81aa72f7298', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：‘’‘’‘’</a>', '1', '0', '1501557114079', '1501558575721');
INSERT INTO `t_user_message` VALUES ('a7168171af5d425a87e4603b92d78e78', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '23cc80e255ae44c996de41f9f49d1c3f', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：t</a>', '1', '0', '1501557005057', '1501558575721');
INSERT INTO `t_user_message` VALUES ('ac476501fabb45f694864bea42b3afd1', 'fe367eb73b164e7797ccfe5bab9171a6', '1', 'e56ad29976f44020b857c5b4f3403b79', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：test</a>', '1', '0', '1501556943192', '1501558575721');
INSERT INTO `t_user_message` VALUES ('b558a59b6b584943885c33d235019d89', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '28077c9a9acc4267912a6d992240b642', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：555666</a>', '1', '0', '1501557214161', '1501558575721');
INSERT INTO `t_user_message` VALUES ('b9acd34e5a26480592ffdd50c5b5875c', '06cd6367762049e69338a08ae65a812a', '2', '58d8b87911954f9db02adb322eca7531', '恭喜，您完成了每日10条评论,成功获得10个积分。', '1', '0', '1502679843451', '1502679855816');
INSERT INTO `t_user_message` VALUES ('cf1b0de6b80b41cd8e40ba8905073686', 'fe367eb73b164e7797ccfe5bab9171a6', '1', 'fd5b23776acc482aa4b493ebef7f1383', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：标点符号。。。</a>', '1', '0', '1501557148405', '1501558575721');
INSERT INTO `t_user_message` VALUES ('e1c608c561044d738622d9e0a1a0f053', 'fe367eb73b164e7797ccfe5bab9171a6', '3', '299ce1abfd904adc8e83692f55e2a7da', null, '1', null, '1501145139448', '1501145139448');
INSERT INTO `t_user_message` VALUES ('e468f127da5c40f2960b698bf1b9c02d', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '9494a8c1ad904752b4ab4e6aa0d9dca5', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：啦啦啦啦啦</a>', '1', '0', '1501557036422', '1501558575721');
INSERT INTO `t_user_message` VALUES ('e5074adcecf34ae28aa233fa24fb33a0', 'fe367eb73b164e7797ccfe5bab9171a6', '2', 'bf077c6d9aa148ea9c7147e47096a2dd', '恭喜，您完成了个人资料设置,成功获得10个积分。', '0', '0', '1501556433085', null);
INSERT INTO `t_user_message` VALUES ('e5a7d361d7d14218a4b5fc52e011f132', '6ab0651ce12b44daafbcee76de129bb4', '3', '299ce1abfd904adc8e83692f55e2a7da', null, '1', null, '1500978194934', '1500978194934');
INSERT INTO `t_user_message` VALUES ('e878d09519784be1aa296e9ca62e0a45', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '488983e3ee7f48639abc888033f64d84', '<a href=\"/video/00007454615f481898de9d0f6061b3cb\">小黄鸭 在 仙剑奇侠传6 誓言成晖 中回复我：tes</a>', '1', '0', '1501556999633', '1501558575721');
INSERT INTO `t_user_message` VALUES ('fb9e3e1599f640f380afe43ee0141d8c', '36eedbaea8514cb6a28b6f97f19cd970', '1', '4690625d1e4a401598272dd124e546f3', '<a href=\"/video/a86842c86a6b40ab8a79a73ff9a337ef\">大支 在 三国杀解说幻影,三国杀的山寨时代,乱侃三国杀 中回复我：呵呵</a>', '0', '0', '1501474088305', null);

-- ----------------------------
-- Table structure for t_user_photo
-- ----------------------------
DROP TABLE IF EXISTS `t_user_photo`;
CREATE TABLE `t_user_photo` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户',
  `photo` varchar(300) DEFAULT NULL COMMENT '头像',
  `is_current` int(11) DEFAULT NULL,
  `status` int(2) DEFAULT NULL COMMENT '状态， 默认1 ， 1：有效  0：无效',
  `create_time` bigint(20) DEFAULT NULL COMMENT '上传时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户头像表';

-- ----------------------------
-- Records of t_user_photo
-- ----------------------------
INSERT INTO `t_user_photo` VALUES ('1d2882cc462a488198c45ffeb85d0b60', 'a4237b3b08c84a229fde55509068d639', '/upload/avatar/20170725/62a125b5a9a14eb5b7f80b5505987158.jpg', '1', '1', '1500954082616', null);
INSERT INTO `t_user_photo` VALUES ('23ccc6abc7eb41478adf3889880164d5', '1', '/upload/avatar/20170807/110ebd2aa73e4e8da120aa82d30b401f.jpg', '1', '1', '1502071876641', null);
INSERT INTO `t_user_photo` VALUES ('2a1c75e056bc476e9722d2e19bfa4c4c', '896c70c78ab14afd9594fcb9faf0b52e', '/upload/avatar/20170720/9b17d68a895647188bf5f590cf566a2b.jpg', '1', '1', '1500521189359', null);
INSERT INTO `t_user_photo` VALUES ('5dae48b3126c489b833724449f434d2d', '12e83fde1ef44aa29609785ec03433b6', '/upload/avatar/20170720/ca432660cd954e6fba13650a3aeccb8c.jpg', '1', '1', '1500521904353', null);
INSERT INTO `t_user_photo` VALUES ('7885ff99208f45d6ac8e5e84545a2266', '36eedbaea8514cb6a28b6f97f19cd970', '/upload/avatar/20170719/8eb2a59dce9a4813b44f3ab83f26acc9.jpg', '1', '1', '1500447542031', null);
INSERT INTO `t_user_photo` VALUES ('7cdc43a9d6fe4ca2a33a68cccf960142', 'fe367eb73b164e7797ccfe5bab9171a6', '/upload/avatar/20170727/0635cf54fea34e419d56a69d2689c286.png', '0', '1', '1501143102977', null);
INSERT INTO `t_user_photo` VALUES ('9b33f45d1e4b42cc87148fab7cf39d7a', '31ecce68d6fa4185a3798b9ef7d92cf4', '/upload/avatar/20170907/24c6dfbe4ce74151a0dd60cfcf02e03d.jpg', '1', '1', '1504771367874', null);
INSERT INTO `t_user_photo` VALUES ('a53ccb12b6444c2cbcab7d922412a19e', 'fe367eb73b164e7797ccfe5bab9171a6', '/upload/avatar/20170801/2fef519614f44df18322324f41869f38.png', '1', '1', '1501581489086', null);
INSERT INTO `t_user_photo` VALUES ('b8129d07fc884889a93f7845b6a25caa', '2169db33b2f64697a84d4027dbd7bcc3', '/upload/avatar/20170724/e13f22b7bf864ca98ef5695f045bac4e.png', '1', '1', '1500888936337', null);
INSERT INTO `t_user_photo` VALUES ('cc67ab66f66049f6be3539b1194240f9', 'd196dddf1d714336ad987a26d544d990', '/upload/avatar/20170724/70334ba7d02e43c781daed847e404984.png', '1', '1', '1500879037404', null);

-- ----------------------------
-- Table structure for t_user_security_question
-- ----------------------------
DROP TABLE IF EXISTS `t_user_security_question`;
CREATE TABLE `t_user_security_question` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL COMMENT '问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '答案',
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户安全问题';

-- ----------------------------
-- Records of t_user_security_question
-- ----------------------------
INSERT INTO `t_user_security_question` VALUES ('ea52498333b4498ca09c5791c2225997', '1', '1', '少飞', '1503650205365', null);

-- ----------------------------
-- Table structure for t_user_sign
-- ----------------------------
DROP TABLE IF EXISTS `t_user_sign`;
CREATE TABLE `t_user_sign` (
  `ID` varchar(32) NOT NULL COMMENT '唯一标识',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `ADD_TIME` bigint(20) DEFAULT NULL COMMENT '签到日期',
  `INTEGRAL` int(20) DEFAULT NULL COMMENT '赠送金币量',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_sign
-- ----------------------------
INSERT INTO `t_user_sign` VALUES ('1caf68d2e5f742b084a7791a6dc46d92', '12e83fde1ef44aa29609785ec03433b6', '1502424061498', '5');
INSERT INTO `t_user_sign` VALUES ('1e630c0fc27b4328ace47073067838bd', '12e83fde1ef44aa29609785ec03433b6', '1500735416358', '5');
INSERT INTO `t_user_sign` VALUES ('21071ce2114f4aa4888c6ddecc7c8fe0', 'fe367eb73b164e7797ccfe5bab9171a6', '1501556002625', '5');
INSERT INTO `t_user_sign` VALUES ('28dc499b0da2462581e50241ef008315', '12e83fde1ef44aa29609785ec03433b6', '1500953033638', '5');
INSERT INTO `t_user_sign` VALUES ('29ee4f8ddc54410dbaca55d1cba68960', '6ab0651ce12b44daafbcee76de129bb4', '1500978178697', '5');
INSERT INTO `t_user_sign` VALUES ('2f9f0818561547c08ea3d4c245818ce6', '2169db33b2f64697a84d4027dbd7bcc3', '1500889000188', '5');
INSERT INTO `t_user_sign` VALUES ('2fa764941697404e92825f1477674051', '36eedbaea8514cb6a28b6f97f19cd970', '1500517095363', '5');
INSERT INTO `t_user_sign` VALUES ('5413ef25e8884c0d848a9d37dc901c51', 'fe367eb73b164e7797ccfe5bab9171a6', '1501729323487', '5');
INSERT INTO `t_user_sign` VALUES ('556104387af74ea7abbd8fa53e43366c', '31ecce68d6fa4185a3798b9ef7d92cf4', '1504771394683', '5');
INSERT INTO `t_user_sign` VALUES ('7247034b2825461d9bea4f3792f83a95', '36eedbaea8514cb6a28b6f97f19cd970', '1500447554492', '5');
INSERT INTO `t_user_sign` VALUES ('7f33323e53e846f7aa1b7ad9b9314116', '1', '1500446064133', '5');
INSERT INTO `t_user_sign` VALUES ('823f8d4da6f942a7a5de2b8d477a2300', '1', '1502691626126', '5');
INSERT INTO `t_user_sign` VALUES ('837de3f9301f437f87f6432853e28bac', '1', '1502096987909', '5');
INSERT INTO `t_user_sign` VALUES ('84f9ff7e42bf43c7b9ad2507310e70d9', 'fe367eb73b164e7797ccfe5bab9171a6', '1501144599503', '5');
INSERT INTO `t_user_sign` VALUES ('9d51a22a042e43eb9a4895a8f520b4cd', 'a4237b3b08c84a229fde55509068d639', '1500954097396', '5');
INSERT INTO `t_user_sign` VALUES ('9edcccf44a7540ee9546399070b66e05', '06cd6367762049e69338a08ae65a812a', '1502680031424', '5');
INSERT INTO `t_user_sign` VALUES ('aa83fd5747694d0997915163bd308e2a', '12e83fde1ef44aa29609785ec03433b6', '1502633360941', '5');
INSERT INTO `t_user_sign` VALUES ('acce46e24fc8404582f29351bbe3b3fc', 'fe367eb73b164e7797ccfe5bab9171a6', '1502265635260', '5');
INSERT INTO `t_user_sign` VALUES ('b951147032044485a9262bc77bcf793f', '7540d5600b4e4236a6586326bbb60418', '1501835971700', '5');
INSERT INTO `t_user_sign` VALUES ('c36a049c8c7d41148aad5a474a374d9b', '12e83fde1ef44aa29609785ec03433b6', '1501730342531', '5');
INSERT INTO `t_user_sign` VALUES ('c4ad3ae84f1042c49063fa830d84c1d7', '12e83fde1ef44aa29609785ec03433b6', '1501667049335', '5');
INSERT INTO `t_user_sign` VALUES ('c760c8ad03a44d9fba2407d19f32e88c', '12e83fde1ef44aa29609785ec03433b6', '1501479778171', '5');
INSERT INTO `t_user_sign` VALUES ('c84c56058d50401eb2362a0edadf54e4', 'fe367eb73b164e7797ccfe5bab9171a6', '1501640877122', '5');
INSERT INTO `t_user_sign` VALUES ('ce7ee940cf384ebbb2f689f0ffc1a262', 'fe367eb73b164e7797ccfe5bab9171a6', '1501212669229', '5');
INSERT INTO `t_user_sign` VALUES ('d02242f5b11b4ca59be20249314806e7', '12e83fde1ef44aa29609785ec03433b6', '1500522915469', '5');
INSERT INTO `t_user_sign` VALUES ('ddfe0ef413484d4c82a123d5f73a324f', 'fb81845169554873a8e809c4610a463e', '1500519973137', '5');
INSERT INTO `t_user_sign` VALUES ('faea07b9eb7e469ca215172951931fba', 'fe367eb73b164e7797ccfe5bab9171a6', '1501482110693', '5');

-- ----------------------------
-- Table structure for t_user_switch
-- ----------------------------
DROP TABLE IF EXISTS `t_user_switch`;
CREATE TABLE `t_user_switch` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `switch_type` varchar(255) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_switch
-- ----------------------------
INSERT INTO `t_user_switch` VALUES ('019548e9002c459a912103728cbb1d6f', '06cd6367762049e69338a08ae65a812a', '1', '0', '1501574155298', null);
INSERT INTO `t_user_switch` VALUES ('04af240c2ce24ccd94ca2461afba7713', '12e83fde1ef44aa29609785ec03433b6', '1', '1', '1502424068276', null);
INSERT INTO `t_user_switch` VALUES ('0bda71b3f8f340b5b8ae9ad4a41499e9', '12e83fde1ef44aa29609785ec03433b6', '1', '0', '1502424065244', null);
INSERT INTO `t_user_switch` VALUES ('19be7624e8584ea7b5e1a7843bbe8f5d', '06cd6367762049e69338a08ae65a812a', '1', '0', '1501574174739', null);
INSERT INTO `t_user_switch` VALUES ('39f36bf28f424d46a43233ab819162a4', '31ecce68d6fa4185a3798b9ef7d92cf4', '1', '0', '1504775236714', null);
INSERT INTO `t_user_switch` VALUES ('3ad8e50b9ab1468e9342fb78143e4cf2', '06cd6367762049e69338a08ae65a812a', '1', '0', '1501574168933', null);
INSERT INTO `t_user_switch` VALUES ('675fef04e09449b18aa35444df224933', '06cd6367762049e69338a08ae65a812a', '1', '1', '1501574153458', null);
INSERT INTO `t_user_switch` VALUES ('7f33d5d082f740ea8a72c52b5a55321e', '12e83fde1ef44aa29609785ec03433b6', '1', '0', '1502424067538', null);
INSERT INTO `t_user_switch` VALUES ('801ec0c67d8f40fd93ec664a6da586e7', '06cd6367762049e69338a08ae65a812a', '1', '0', '1501574151433', null);
INSERT INTO `t_user_switch` VALUES ('82f605dead514162bd2bfd3d52f4c566', '06cd6367762049e69338a08ae65a812a', '1', '0', '1501574154137', null);
INSERT INTO `t_user_switch` VALUES ('8d55c7980a004df8b85085c6c558b884', '06cd6367762049e69338a08ae65a812a', '1', '0', '1501574152235', null);
INSERT INTO `t_user_switch` VALUES ('9b2b6662d6af45be8def48c144d79054', '06cd6367762049e69338a08ae65a812a', '1', '0', '1501574160454', null);
INSERT INTO `t_user_switch` VALUES ('9f044518c8be4a7aae0a9888220b26ab', '31ecce68d6fa4185a3798b9ef7d92cf4', '1', '1', '1504775237217', null);
INSERT INTO `t_user_switch` VALUES ('a0d2f8b93cf14c79a5bf0549ec322e8a', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '0', '1501145150782', null);
INSERT INTO `t_user_switch` VALUES ('a9045a11a6e045be82c6704072042a25', '12e83fde1ef44aa29609785ec03433b6', '1', '1', '1502424066026', null);
INSERT INTO `t_user_switch` VALUES ('e6ea1e143ddc4aa1bacfe224cec946ad', 'fe367eb73b164e7797ccfe5bab9171a6', '1', '1', '1501145151324', '1501668086905');
INSERT INTO `t_user_switch` VALUES ('eb8f2d031d464ff2adfc4325e7dee9e1', '06cd6367762049e69338a08ae65a812a', '1', '1', '1501574154796', '1502680113422');

-- ----------------------------
-- Table structure for t_user_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_user_tag`;
CREATE TABLE `t_user_tag` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `value_id` varchar(32) DEFAULT NULL,
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) NOT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_tag
-- ----------------------------

-- ----------------------------
-- Table structure for t_video
-- ----------------------------
DROP TABLE IF EXISTS `t_video`;
CREATE TABLE `t_video` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `short_id` varchar(32) DEFAULT NULL COMMENT '短ID',
  `title` varchar(200) DEFAULT NULL COMMENT '视频标题',
  `cover` varchar(200) DEFAULT NULL COMMENT '视频封面',
  `duration` double(10,2) DEFAULT NULL COMMENT '视频时长',
  `score` double(4,2) DEFAULT NULL COMMENT '评分',
  `game_id` varchar(32) DEFAULT NULL COMMENT '所属游戏',
  `album_id` varchar(32) DEFAULT NULL COMMENT '专辑id',
  `album_index` int(10) DEFAULT NULL COMMENT '当前集数',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `v_s_s_d` (`short_id`,`score`,`duration`) USING BTREE,
  KEY `v_title_x` (`title`) USING BTREE,
  KEY `v_album_idx` (`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频';

-- ----------------------------
-- Records of t_video
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_album
-- ----------------------------
DROP TABLE IF EXISTS `t_video_album`;
CREATE TABLE `t_video_album` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(200) NOT NULL COMMENT '专辑名称',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级id',
  `inde` int(2) DEFAULT NULL COMMENT '排序集数',
  `cover` varchar(200) DEFAULT NULL COMMENT '合集封面',
  `author` varchar(50) DEFAULT NULL COMMENT '作者名称',
  `update_remarks` varchar(200) DEFAULT NULL COMMENT '更新时间备注',
  `home_id` varchar(200) DEFAULT NULL COMMENT 'ID（非本站ID）',
  `home_url` varchar(200) DEFAULT NULL COMMENT '地址',
  `introduction` varchar(500) DEFAULT NULL COMMENT '专辑简介',
  `score` double(4,2) DEFAULT NULL COMMENT '评分',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频专辑';

-- ----------------------------
-- Records of t_video_album
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_album_value
-- ----------------------------
DROP TABLE IF EXISTS `t_video_album_value`;
CREATE TABLE `t_video_album_value` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `video_album_id` varchar(32) NOT NULL COMMENT '视频专辑id',
  `value_id` varchar(32) NOT NULL COMMENT '属性值id',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频专辑属性值关联';

-- ----------------------------
-- Records of t_video_album_value
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_video_comment`;
CREATE TABLE `t_video_comment` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `video_id` varchar(32) NOT NULL COMMENT '视频id',
  `c_id` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '评论id（第三方平台）',
  `c_content` text CHARACTER SET utf8mb4 COMMENT '评论内容',
  `c_createTime` bigint(20) DEFAULT NULL COMMENT '评论时间',
  `c_user_id` varchar(50) DEFAULT NULL COMMENT '评论者id',
  `c_user_name` varchar(50) DEFAULT NULL COMMENT '评论者昵称',
  `c_parent_id` varchar(50) DEFAULT '0' COMMENT '父级评论id',
  `station` int(2) NOT NULL COMMENT '所属站点',
  `status` int(2) NOT NULL COMMENT '状态',
  `like_num` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vc_v_c_p_u` (`video_id`,`c_id`,`c_createTime`,`c_parent_id`,`c_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频评论';

-- ----------------------------
-- Records of t_video_comment
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_comment_like
-- ----------------------------
DROP TABLE IF EXISTS `t_video_comment_like`;
CREATE TABLE `t_video_comment_like` (
  `id` varchar(32) NOT NULL COMMENT '点赞',
  `user_id` varchar(32) NOT NULL COMMENT '点赞人',
  `comment_id` varchar(32) NOT NULL COMMENT '评论',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `status` int(2) NOT NULL COMMENT '状态，1有效，0无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `comment_id` (`comment_id`,`user_id`) USING BTREE,
  KEY `vcl_cid_idx` (`comment_id`) USING BTREE,
  KEY `vcl_uid_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_video_comment_like
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_video_grade`;
CREATE TABLE `t_video_grade` (
  `id` varchar(32) NOT NULL COMMENT '评分',
  `user_id` varchar(32) NOT NULL COMMENT '评分人',
  `video_id` varchar(32) NOT NULL COMMENT '视频 t_video',
  `score` double(4,1) NOT NULL COMMENT '评分',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `status` int(2) NOT NULL COMMENT '状态，1有效，0无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`video_id`) USING BTREE,
  KEY `idx_vg_uid` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_video_grade
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_recommend
-- ----------------------------
DROP TABLE IF EXISTS `t_video_recommend`;
CREATE TABLE `t_video_recommend` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `video_id` varchar(32) NOT NULL COMMENT '视频id',
  `url` varchar(200) NOT NULL COMMENT '展示视频地址',
  `start_time` bigint(20) DEFAULT NULL COMMENT '展示时间开始',
  `end_time` bigint(20) DEFAULT NULL COMMENT '展示时间结束',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页推荐视频';

-- ----------------------------
-- Records of t_video_recommend
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_station
-- ----------------------------
DROP TABLE IF EXISTS `t_video_station`;
CREATE TABLE `t_video_station` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `video_id` varchar(32) DEFAULT NULL COMMENT '视频id',
  `v_id` varchar(100) DEFAULT NULL COMMENT '视频源id',
  `v_url` varchar(300) DEFAULT NULL COMMENT '视频源url',
  `v_flash_url` varchar(300) DEFAULT NULL COMMENT '视频播放url',
  `v_title` varchar(200) DEFAULT NULL COMMENT '视频标题',
  `v_introduction` varchar(500) DEFAULT NULL COMMENT '视频简介',
  `v_cover` varchar(200) DEFAULT NULL COMMENT '视频封面',
  `v_duration` double(10,2) DEFAULT NULL COMMENT '视频时长',
  `v_category` varchar(10) DEFAULT NULL COMMENT '视频类别',
  `v_published` bigint(20) DEFAULT NULL COMMENT '视频发布时间',
  `v_view_count` bigint(20) DEFAULT NULL COMMENT '视频播放量',
  `v_comment_count` bigint(20) DEFAULT NULL COMMENT '视频评论量',
  `v_down_count` bigint(20) DEFAULT NULL COMMENT '视频下载量',
  `v_favorite_count` bigint(20) DEFAULT NULL COMMENT '视频收藏量',
  `up_id` varchar(32) DEFAULT NULL COMMENT '视频作者id',
  `station` int(2) NOT NULL COMMENT '所属站点',
  `status` int(2) NOT NULL COMMENT '状态',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vs_v` (`v_id`,`station`) USING BTREE,
  KEY `vs_v_p_v_c` (`video_id`,`v_published`,`v_view_count`,`v_comment_count`) USING BTREE,
  KEY `vs_c_s` (`v_category`,`station`) USING BTREE,
  KEY `vs_u` (`up_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频站点';

-- ----------------------------
-- Records of t_video_station
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_value
-- ----------------------------
DROP TABLE IF EXISTS `t_video_value`;
CREATE TABLE `t_video_value` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `video_id` varchar(32) NOT NULL COMMENT '视频id',
  `value_id` varchar(32) NOT NULL COMMENT '属性值id',
  `status` int(2) NOT NULL COMMENT '状态  1有效  0无效',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `up_num` int(11) DEFAULT '0' COMMENT '赞的数量',
  `inform_status` int(2) DEFAULT '0' COMMENT '举报状态 ：0：正常  1：已举报  2：已处理',
  PRIMARY KEY (`id`),
  KEY `vv_video_id_idx` (`video_id`) USING BTREE,
  KEY `vv_value_id_idx` (`value_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频属性值关联';

-- ----------------------------
-- Records of t_video_value
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_value_inform
-- ----------------------------
DROP TABLE IF EXISTS `t_video_value_inform`;
CREATE TABLE `t_video_value_inform` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `video_value_id` varchar(32) NOT NULL COMMENT '视频id',
  `inform_reason` int(2) DEFAULT NULL COMMENT '举报原因  0:内容不相关 1：敏感信息 2：恶意攻击 3：剧透内容 4：恶意删除',
  `status` int(2) NOT NULL COMMENT '状态  1有效  0无效',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_video_value_inform
-- ----------------------------

-- ----------------------------
-- Table structure for t_video_value_log
-- ----------------------------
DROP TABLE IF EXISTS `t_video_value_log`;
CREATE TABLE `t_video_value_log` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `video_value_id` varchar(32) NOT NULL COMMENT '视频id',
  `operate_type` int(2) DEFAULT NULL COMMENT '操作类型：0:添加  1:点赞 2: 取消点赞  4:删除',
  `status` int(2) NOT NULL COMMENT '状态  1有效  0无效',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '修改者',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `vvl_v_c` (`video_value_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_video_value_log
-- ----------------------------

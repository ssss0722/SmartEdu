/*
 Navicat Premium Data Transfer

 Source Server         : GQL1
 Source Server Type    : MySQL
 Source Server Version : 80011 (8.0.11)
 Source Host           : localhost:3306
 Source Schema         : edu_system

 Target Server Type    : MySQL
 Target Server Version : 80011 (8.0.11)
 File Encoding         : 65001

 Date: 07/07/2025 21:01:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for about_us
-- ----------------------------
DROP TABLE IF EXISTS `about_us`;
CREATE TABLE `about_us`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `subtitle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `picture1` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片1',
  `picture2` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片2',
  `picture3` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '关于我们' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of about_us
-- ----------------------------
INSERT INTO `about_us` VALUES (1, '2025-07-07 11:41:50', '关于我们', '智能化在线教学支持服务平台教师中心系统', '<p>当你设想门外是寒冷可怕的世界时，你还应该开门出去看看，是否真的如此。如果你有信心，你对前途就不犹豫了。如果你有勇气，你就不怕前途是否有困难或危险了每个人心中都应有两盏灯，一盏是希望的灯，一盏是勇气的灯。有了这两盏灯，我们就不怕海上的黑暗和风涛的险恶了。人的一生很像是在雾中行走。远远望去，只是迷蒙一片，辨不出方向和吉凶。可是，当你鼓起勇气，放下恐惧和怀疑，一步一步向前走去的时候，你就会发现，每走一步，你都能把下一步路看得清楚一点。“往前走，别站在远远的地方观望！”你就可以找到你的方向。</p>', 'upload/aboutus_picture1.jpg', 'upload/aboutus_picture2.jpg', 'upload/aboutus_picture3.jpg');

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置参数名称',
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置参数值',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '配置文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, 'picture1', 'upload/picture1.jpg', NULL);
INSERT INTO `config` VALUES (2, 'picture2', 'upload/picture2.jpg', NULL);
INSERT INTO `config` VALUES (3, 'picture3', 'upload/picture3.jpg', NULL);

-- ----------------------------
-- Table structure for course_categories
-- ----------------------------
DROP TABLE IF EXISTS `course_categories`;
CREATE TABLE `course_categories`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `course` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程类别',
  PRIMARY KEY (`id`, `course`) USING BTREE,
  UNIQUE INDEX `kechengleibie`(`course` ASC) USING BTREE,
  INDEX `id`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程类别' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course_categories
-- ----------------------------
INSERT INTO `course_categories` VALUES (51, '2025-07-07 11:41:50', 'JAVA程序设计与实现');
INSERT INTO `course_categories` VALUES (52, '2025-07-07 11:41:50', '高等数学A');
INSERT INTO `course_categories` VALUES (53, '2025-07-07 11:41:50', '统计与概率');
INSERT INTO `course_categories` VALUES (54, '2025-07-07 11:41:50', '敦煌的艺术');
INSERT INTO `course_categories` VALUES (55, '2025-07-07 11:41:50', '实用英语阅读与写作');
INSERT INTO `course_categories` VALUES (56, '2025-07-07 11:41:50', '近代史纲要');
INSERT INTO `course_categories` VALUES (57, '2025-07-07 11:41:50', '马克思主义原理');
INSERT INTO `course_categories` VALUES (58, '2025-07-07 11:41:50', 'WEB开发');
INSERT INTO `course_categories` VALUES (59, '2025-07-07 11:41:50', '开源操作系统');

-- ----------------------------
-- Table structure for course_material
-- ----------------------------
DROP TABLE IF EXISTS `course_material`;
CREATE TABLE `course_material`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `course` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程类别',
  `picture` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片',
  `attachment` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '附件',
  `t_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `publish_at` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `clicktime` datetime NULL DEFAULT NULL COMMENT '最近点击时间',
  `clicknum` int(11) NULL DEFAULT 0 COMMENT '点击次数',
  `discussnum` int(11) NULL DEFAULT 0 COMMENT '评论数',
  `storeupnum` int(11) NULL DEFAULT 0 COMMENT '收藏数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_co_material`(`t_username` ASC) USING BTREE,
  INDEX `cate_co_material`(`course` ASC) USING BTREE,
  CONSTRAINT `cate_co_material` FOREIGN KEY (`course`) REFERENCES `course_categories` (`course`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_co_material` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教学资料' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course_material
-- ----------------------------
INSERT INTO `course_material` VALUES (39, '2024-03-05 11:53:19', 'openEuler操作系统', '开源操作系统', 'upload/openEuler操作系统.jpg', 'upload/1709610795897.doc', '111', '2024-03-05 11:55:14', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2024-03-05 11:57:04', 1, 1, 1);
INSERT INTO `course_material` VALUES (40, '2025-07-07 16:14:04', 'JAVA程序设计与实现', 'JAVA程序设计与实现', 'upload/JAVA程序设计与实现.jpg', '40', '111', '2025-07-07 16:19:27', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2025-07-07 16:19:39', 0, 0, 0);
INSERT INTO `course_material` VALUES (41, '2025-07-07 16:14:04', '高等数学A', '高等数学A', 'upload/高等数学A.jpg', '40', '111', '2025-07-07 16:19:27', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2025-07-07 16:19:39', 0, 0, 0);
INSERT INTO `course_material` VALUES (42, '2025-07-07 19:01:44', '概率论与数理统计', '统计与概率', 'upload/概率论与数理统计.jpg', '12', '111', '2025-07-07 20:44:56', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2025-07-07 20:45:30', 0, 0, 0);
INSERT INTO `course_material` VALUES (43, '2025-07-07 19:04:11', '敦煌', '敦煌的艺术', 'upload/敦煌.jpg', NULL, '111', '2025-07-07 20:44:59', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2025-07-07 20:45:32', 0, 0, 0);
INSERT INTO `course_material` VALUES (44, '2025-07-07 19:04:42', '英语阅读与写作教程', '实用英语阅读与写作', 'upload/英语阅读与写作教程.jpg', NULL, '111', '2025-07-07 20:45:01', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2025-07-07 20:45:36', 0, 0, 0);
INSERT INTO `course_material` VALUES (45, '2025-07-07 19:05:12', '中国近代史纲要', '近代史纲要', 'upload/中国近代史纲要.jpg', NULL, '111', '2025-07-07 20:45:05', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2025-07-07 20:45:34', 0, 0, 0);
INSERT INTO `course_material` VALUES (46, '2025-07-07 19:05:39', '马克思主义基本原理', '马克思主义原理', 'upload/马克思主义基本原理.jpg', NULL, '111', '2025-07-07 20:45:07', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2025-07-07 20:45:40', 0, 0, 0);
INSERT INTO `course_material` VALUES (47, '2025-07-07 19:06:06', 'springboot', 'WEB开发', 'upload/springboot.jpg', NULL, '111', '2025-07-07 20:45:10', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2025-07-07 20:45:38', 0, 0, 0);

-- ----------------------------
-- Table structure for course_student
-- ----------------------------
DROP TABLE IF EXISTS `course_student`;
CREATE TABLE `course_student`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `course_id` bigint(20) NULL DEFAULT NULL COMMENT '课程',
  `s_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `co_s`(`s_username` ASC) USING BTREE,
  INDEX `co_s_cate`(`course_id` ASC) USING BTREE,
  CONSTRAINT `co_s` FOREIGN KEY (`s_username`) REFERENCES `user_student` (`s_username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `co_s_cate` FOREIGN KEY (`course_id`) REFERENCES `course_categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_student
-- ----------------------------

-- ----------------------------
-- Table structure for course_teacher
-- ----------------------------
DROP TABLE IF EXISTS `course_teacher`;
CREATE TABLE `course_teacher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键（教学班序号）',
  `course_id` bigint(20) NOT NULL COMMENT '课程类别',
  `t_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '老师',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `co_t`(`t_username` ASC) USING BTREE,
  INDEX `co_t_cate`(`course_id` ASC) USING BTREE,
  CONSTRAINT `co_t` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `co_t_cate` FOREIGN KEY (`course_id`) REFERENCES `course_categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_teacher
-- ----------------------------

-- ----------------------------
-- Table structure for course_video
-- ----------------------------
DROP TABLE IF EXISTS `course_video`;
CREATE TABLE `course_video`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `course` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程类别',
  `cover` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '封面',
  `t_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `video` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '教学视频',
  `published_at` date NULL DEFAULT NULL COMMENT '发布时间',
  `intro` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '基本介绍',
  `clicktime` datetime NULL DEFAULT NULL COMMENT '最近点击时间',
  `clicknum` int(11) NULL DEFAULT 0 COMMENT '点击次数',
  `discussnum` int(11) NULL DEFAULT 0 COMMENT '评论数',
  `storeupnum` int(11) NULL DEFAULT 0 COMMENT '收藏数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_co_video`(`t_username` ASC) USING BTREE,
  INDEX `cate_co_video`(`course` ASC) USING BTREE,
  CONSTRAINT `cate_co_video` FOREIGN KEY (`course`) REFERENCES `course_categories` (`course`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_co_video` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教学视频' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course_video
-- ----------------------------
INSERT INTO `course_video` VALUES (49, '2024-03-05 11:53:53', '系统环境的搭建', '开源操作系统', 'upload/1709610825018.jpg', '111', 'upload/1709611063013.mp4', '2024-03-05', '<p>远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现远程教育网站的设计与实现</p>', '2024-03-05 11:58:25', 4, 0, 1);

-- ----------------------------
-- Table structure for exam_homework
-- ----------------------------
DROP TABLE IF EXISTS `exam_homework`;
CREATE TABLE `exam_homework`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '考试ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '考试名称',
  `paper_id` bigint(20) NOT NULL COMMENT '关联试卷ID',
  `start_time` datetime NOT NULL COMMENT '考试开始时间',
  `end_time` datetime NOT NULL COMMENT '考试结束时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_exam_paper`(`paper_id` ASC) USING BTREE,
  CONSTRAINT `fk_exam_paper` FOREIGN KEY (`paper_id`) REFERENCES `paper` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_homework
-- ----------------------------

-- ----------------------------
-- Table structure for exam_homework_student
-- ----------------------------
DROP TABLE IF EXISTS `exam_homework_student`;
CREATE TABLE `exam_homework_student`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `exam_id` bigint(20) NULL DEFAULT NULL COMMENT '考试或作业的ID',
  `s_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ex`(`exam_id` ASC) USING BTREE,
  INDEX `ex_stu`(`s_username` ASC) USING BTREE,
  CONSTRAINT `ex` FOREIGN KEY (`exam_id`) REFERENCES `exam_homework` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ex_stu` FOREIGN KEY (`s_username`) REFERENCES `user_student` (`s_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_homework_student
-- ----------------------------

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '在线考试名称',
  `time` int(11) NOT NULL COMMENT '测试时长(分钟)',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '在线考试状态',
  `t_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `course_id` bigint(20) NULL DEFAULT NULL COMMENT '课程ID外键',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_ex_paper`(`t_username` ASC) USING BTREE,
  INDEX `paper_co`(`course_id` ASC) USING BTREE,
  CONSTRAINT `paper_co` FOREIGN KEY (`course_id`) REFERENCES `course_categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_ex_paper` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '在线考试表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of paper
-- ----------------------------
INSERT INTO `paper` VALUES (1, '2024-03-05 11:41:50', '十万个为什么', 60, '1', NULL, NULL);
INSERT INTO `paper` VALUES (2, '2024-03-05 11:54:40', '编程', 30, '1', '111', NULL);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `paperid` bigint(20) NOT NULL COMMENT '所属在线考试id（外键）',
  `t_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `question_id` bigint(20) NULL DEFAULT NULL COMMENT '试题序号（外键）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_ex_queation`(`t_username` ASC) USING BTREE,
  INDEX `qu_pa`(`paperid` ASC) USING BTREE,
  INDEX `bank_qu`(`question_id` ASC) USING BTREE,
  CONSTRAINT `bank_qu` FOREIGN KEY (`question_id`) REFERENCES `question_bank` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `qu_pa` FOREIGN KEY (`paperid`) REFERENCES `paper` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_ex_queation` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试题表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question
-- ----------------------------

-- ----------------------------
-- Table structure for question_bank
-- ----------------------------
DROP TABLE IF EXISTS `question_bank`;
CREATE TABLE `question_bank`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '试题名称',
  `options` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '选项，json字符串',
  `answer` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正确答案',
  `analysis` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '答案解析',
  `type` bigint(20) NULL DEFAULT 0 COMMENT '试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空）',
  `sequence` bigint(20) NULL DEFAULT 100 COMMENT '试题排序，值越大排越前面',
  `t_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `score` bigint(20) NULL DEFAULT NULL COMMENT '试题分值',
  `course_id` bigint(20) NULL DEFAULT NULL COMMENT '课程外检',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_ex_bank`(`t_username` ASC) USING BTREE,
  INDEX `bank_co`(`course_id` ASC) USING BTREE,
  CONSTRAINT `bank_co` FOREIGN KEY (`course_id`) REFERENCES `course_categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_ex_bank` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '试题库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_bank
-- ----------------------------
INSERT INTO `question_bank` VALUES (12, '2025-07-07 21:00:32', 'Java中以下哪个关键字用于继承一个类？', '[{\"text\":\"A.extends\",\"code\":\"A\"},{\"text\":\"B.implements\",\"code\":\"B\"},{\"text\":\"C.import\",\"code\":\"C\"}]', 'A', '用于继承类', 0, 1, NULL, 5, 51);
INSERT INTO `question_bank` VALUES (13, '2025-07-07 21:00:32', '以下哪些属于Java集合类？', '[{\"text\":\"A.ArrayList\",\"code\":\"A\"},{\"text\":\"B.HashMap\",\"code\":\"B\"},{\"text\":\"C.Scanner\",\"code\":\"C\"}]', 'A,B', '集合类有List和Map等', 1, 2, NULL, 5, 51);
INSERT INTO `question_bank` VALUES (14, '2025-07-07 21:00:32', 'Java程序入口是（ ）。', '[]', 'main方法', 'main方法', 3, 3, NULL, 5, 51);
INSERT INTO `question_bank` VALUES (15, '2025-07-07 21:00:32', '在Java中，接口可以被实例化。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'B', '接口不能被直接实例化', 2, 4, NULL, 5, 51);
INSERT INTO `question_bank` VALUES (16, '2025-07-07 21:00:32', '关键字final修饰的类可以被继承。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'B', 'final类不可继承', 2, 5, NULL, 5, 51);
INSERT INTO `question_bank` VALUES (17, '2025-07-07 21:00:32', '函数y=sin(x)在区间[0,π]的最大值是（ ）。', '[]', '1', 'sin在π/2处为1', 3, 1, NULL, 5, 52);
INSERT INTO `question_bank` VALUES (18, '2025-07-07 21:00:32', '下列哪些函数在x=0处连续？', '[{\"text\":\"A.e^x\",\"code\":\"A\"},{\"text\":\"B.ln(x)\",\"code\":\"B\"},{\"text\":\"C.x^2\",\"code\":\"C\"}]', 'A,C', 'ln(x)在0无定义', 1, 2, NULL, 5, 52);
INSERT INTO `question_bank` VALUES (19, '2025-07-07 21:00:32', '导数的本质是（ ）。', '[]', '变化率', '导数是变化率的极限', 3, 3, NULL, 5, 52);
INSERT INTO `question_bank` VALUES (20, '2025-07-07 21:00:32', '如果函数在一点可导，则一定连续。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'A', '可导必连续', 2, 4, NULL, 5, 52);
INSERT INTO `question_bank` VALUES (21, '2025-07-07 21:00:32', '下列哪个积分表示面积？', '[{\"text\":\"A.定积分\",\"code\":\"A\"},{\"text\":\"B.不定积分\",\"code\":\"B\"},{\"text\":\"C.微分\",\"code\":\"C\"}]', 'A', '定积分表示面积', 0, 5, NULL, 5, 52);
INSERT INTO `question_bank` VALUES (22, '2025-07-07 21:00:32', '正态分布的期望等于（ ）。', '[]', '均值', '正态分布均值即期望', 3, 1, NULL, 5, 53);
INSERT INTO `question_bank` VALUES (23, '2025-07-07 21:00:32', '以下哪些属于描述统计？', '[{\"text\":\"A.均值\",\"code\":\"A\"},{\"text\":\"B.标准差\",\"code\":\"B\"},{\"text\":\"C.假设检验\",\"code\":\"C\"}]', 'A,B', '假设检验是推断统计', 1, 2, NULL, 5, 53);
INSERT INTO `question_bank` VALUES (24, '2025-07-07 21:00:32', '样本方差用于度量数据的（ ）。', '[]', '离散程度', '方差是离散度量', 3, 3, NULL, 5, 53);
INSERT INTO `question_bank` VALUES (25, '2025-07-07 21:00:32', '概率是客观存在的确定性。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'B', '概率表示随机性', 2, 4, NULL, 5, 53);
INSERT INTO `question_bank` VALUES (26, '2025-07-07 21:00:32', '0到1之间的任何数都可以作为概率值。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'A', '概率在[0,1]范围', 2, 5, NULL, 5, 53);
INSERT INTO `question_bank` VALUES (27, '2025-07-07 21:00:32', '敦煌莫高窟始建于（ ）朝代。', '[]', '前秦', '前秦建造', 3, 1, NULL, 5, 54);
INSERT INTO `question_bank` VALUES (28, '2025-07-07 21:00:32', '下列哪些属于敦煌艺术形式？', '[{\"text\":\"A.壁画\",\"code\":\"A\"},{\"text\":\"B.雕塑\",\"code\":\"B\"},{\"text\":\"C.瓷器\",\"code\":\"C\"}]', 'A,B', '瓷器非敦煌主要形式', 1, 2, NULL, 5, 54);
INSERT INTO `question_bank` VALUES (29, '2025-07-07 21:00:32', '敦煌壁画多以（ ）题材为主。', '[]', '佛教', '佛教题材', 3, 3, NULL, 5, 54);
INSERT INTO `question_bank` VALUES (30, '2025-07-07 21:00:32', '敦煌遗书出土于藏经洞。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'A', '确实出土于藏经洞', 2, 4, NULL, 5, 54);
INSERT INTO `question_bank` VALUES (31, '2025-07-07 21:00:32', '敦煌石窟艺术是多民族文化交流的产物。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'A', '多民族文化交流', 2, 5, NULL, 5, 54);
INSERT INTO `question_bank` VALUES (32, '2025-07-07 21:00:32', 'Which of the following is NOT a type of essay?', '[{\"text\":\"A.Argumentative\",\"code\":\"A\"},{\"text\":\"B.Narrative\",\"code\":\"B\"},{\"text\":\"C.Experimental\",\"code\":\"C\"}]', 'C', 'Experimental is not common type', 0, 1, NULL, 5, 55);
INSERT INTO `question_bank` VALUES (33, '2025-07-07 21:00:32', 'A paragraph usually contains ( ) idea.', '[]', 'one main', 'one main idea', 3, 2, NULL, 5, 55);
INSERT INTO `question_bank` VALUES (34, '2025-07-07 21:00:32', '以下哪些属于连接词？', '[{\"text\":\"A.Therefore\",\"code\":\"A\"},{\"text\":\"B.Because\",\"code\":\"B\"},{\"text\":\"C.Apple\",\"code\":\"C\"}]', 'A,B', 'Apple不是连接词', 1, 3, NULL, 5, 55);
INSERT INTO `question_bank` VALUES (35, '2025-07-07 21:00:32', 'Reading skills include skimming and scanning. ( )', '[{\"text\":\"A.True\",\"code\":\"A\"},{\"text\":\"B.False\",\"code\":\"B\"}]', 'A', '正确', 2, 4, NULL, 5, 55);
INSERT INTO `question_bank` VALUES (36, '2025-07-07 21:00:32', 'Formal writing avoids contractions. ( )', '[{\"text\":\"A.True\",\"code\":\"A\"},{\"text\":\"B.False\",\"code\":\"B\"}]', 'A', '应避免缩写', 2, 5, NULL, 5, 55);
INSERT INTO `question_bank` VALUES (37, '2025-07-07 21:00:32', '五四运动爆发于（ ）年。', '[]', '1919', '1919年爆发', 3, 1, NULL, 5, 56);
INSERT INTO `question_bank` VALUES (38, '2025-07-07 21:00:32', '辛亥革命推翻了（ ）的统治。', '[]', '清朝', '推翻清朝', 3, 2, NULL, 5, 56);
INSERT INTO `question_bank` VALUES (39, '2025-07-07 21:00:32', '新文化运动的中心人物有（ ）。', '[{\"text\":\"A.陈独秀\",\"code\":\"A\"},{\"text\":\"B.胡适\",\"code\":\"B\"},{\"text\":\"C.鲁迅\",\"code\":\"C\"}]', 'A,B,C', '三人都是核心人物', 1, 3, NULL, 5, 56);
INSERT INTO `question_bank` VALUES (40, '2025-07-07 21:00:32', '鸦片战争标志中国近代史的开始。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'A', '确实如此', 2, 4, NULL, 5, 56);
INSERT INTO `question_bank` VALUES (41, '2025-07-07 21:00:32', '辛亥革命建立了中华民国。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'A', '确立中华民国', 2, 5, NULL, 5, 56);
INSERT INTO `question_bank` VALUES (42, '2025-07-07 21:00:32', '马克思主义的核心理论是（ ）。', '[]', '唯物史观', '核心是唯物史观', 3, 1, NULL, 5, 57);
INSERT INTO `question_bank` VALUES (43, '2025-07-07 21:00:32', '以下哪些属于马克思主义组成部分？', '[{\"text\":\"A.哲学\",\"code\":\"A\"},{\"text\":\"B.政治经济学\",\"code\":\"B\"},{\"text\":\"C.科学社会主义\",\"code\":\"C\"}]', 'A,B,C', '三部分组成', 1, 2, NULL, 5, 57);
INSERT INTO `question_bank` VALUES (44, '2025-07-07 21:00:32', '阶级斗争是历史发展的动力。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'A', '确实如此', 2, 3, NULL, 5, 57);
INSERT INTO `question_bank` VALUES (45, '2025-07-07 21:00:32', '马克思主义产生于19世纪（ ）。', '[]', '40年代', '19世纪40年代', 3, 4, NULL, 5, 57);
INSERT INTO `question_bank` VALUES (46, '2025-07-07 21:00:32', '剩余价值理论是马克思经济学的核心。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'A', '确实如此', 2, 5, NULL, 5, 57);
INSERT INTO `question_bank` VALUES (47, '2025-07-07 21:00:32', 'HTML中用于定义段落的标签是（ ）。', '[]', '<p>', 'p标签定义段落', 3, 1, NULL, 5, 58);
INSERT INTO `question_bank` VALUES (48, '2025-07-07 21:00:32', '以下哪些属于CSS属性？', '[{\"text\":\"A.color\",\"code\":\"A\"},{\"text\":\"B.font-size\",\"code\":\"B\"},{\"text\":\"C.padding\",\"code\":\"C\"}]', 'A,B,C', '三者都是CSS属性', 1, 2, NULL, 5, 58);
INSERT INTO `question_bank` VALUES (49, '2025-07-07 21:00:32', 'JavaScript可以操作DOM。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'A', '可以操作DOM', 2, 3, NULL, 5, 58);
INSERT INTO `question_bank` VALUES (50, '2025-07-07 21:00:32', '响应式布局常用的框架是（ ）。', '[]', 'Bootstrap', 'Bootstrap用于响应式', 3, 4, NULL, 5, 58);
INSERT INTO `question_bank` VALUES (51, '2025-07-07 21:00:32', 'HTTP协议默认端口号是（ ）。', '[]', '80', 'HTTP默认80', 3, 5, NULL, 5, 58);
INSERT INTO `question_bank` VALUES (52, '2025-07-07 21:00:32', 'Linux内核最初由（ ）开发。', '[]', 'Linus Torvalds', 'Linus开发', 3, 1, NULL, 5, 59);
INSERT INTO `question_bank` VALUES (53, '2025-07-07 21:00:32', '以下哪些属于Linux发行版？', '[{\"text\":\"A.Ubuntu\",\"code\":\"A\"},{\"text\":\"B.Fedora\",\"code\":\"B\"},{\"text\":\"C.RedHat\",\"code\":\"C\"}]', 'A,B,C', '三者都是发行版', 1, 2, NULL, 5, 59);
INSERT INTO `question_bank` VALUES (54, '2025-07-07 21:00:32', 'Linux是一个闭源系统。（ ）', '[{\"text\":\"A.对\",\"code\":\"A\"},{\"text\":\"B.错\",\"code\":\"B\"}]', 'B', 'Linux是开源', 2, 3, NULL, 5, 59);
INSERT INTO `question_bank` VALUES (55, '2025-07-07 21:00:32', 'GNU是（ ）的缩写。', '[]', 'GNU\'s Not Unix', '递归缩写', 3, 4, NULL, 5, 59);
INSERT INTO `question_bank` VALUES (56, '2025-07-07 21:00:32', 'Linux默认Shell是（ ）。', '[]', 'Bash', '默认Bash', 3, 5, NULL, 5, 59);

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `s_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生用户id（外键）',
  `paperid` bigint(20) NOT NULL COMMENT '在线考试id（外键）',
  `question_id` bigint(20) NOT NULL COMMENT '试题id（外键）',
  `ismark` bigint(20) NULL DEFAULT 0 COMMENT '是否批卷',
  `myscore` bigint(20) NOT NULL DEFAULT 0 COMMENT '试题得分',
  `myanswer` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '考生答案',
  `t_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教师工号(外键)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_ex_record`(`t_username` ASC) USING BTREE,
  INDEX `re_qu_bank`(`question_id` ASC) USING BTREE,
  INDEX `re_user_s`(`s_username` ASC) USING BTREE,
  INDEX `re_pa`(`paperid` ASC) USING BTREE,
  CONSTRAINT `re_pa` FOREIGN KEY (`paperid`) REFERENCES `paper` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `re_qu_bank` FOREIGN KEY (`question_id`) REFERENCES `question_bank` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `re_user_s` FOREIGN KEY (`s_username`) REFERENCES `user_student` (`s_username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_ex_record` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for store_up
-- ----------------------------
DROP TABLE IF EXISTS `store_up`;
CREATE TABLE `store_up`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `refid` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `tablename` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `picture` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片',
  `type` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '类型',
  `inteltype` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐类型',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of store_up
-- ----------------------------
INSERT INTO `store_up` VALUES (1, '2024-03-05 11:57:04', 1709611010643, 39, 'jiaoxueziliao', '初级编程', 'upload/1709610789808.jpg', '1', '编程', NULL);
INSERT INTO `store_up` VALUES (3, '2024-03-05 11:58:10', 1709611010643, 69, 'kechengzuoye', '第一单元', 'upload/1709610853914.jpg', '1', NULL, NULL);
INSERT INTO `store_up` VALUES (4, '2024-03-05 11:58:26', 1709611010643, 49, 'jiaoxueshipin', '初级编程', 'upload/1709610825018.jpg', '1', '编程', NULL);

-- ----------------------------
-- Table structure for system_intro
-- ----------------------------
DROP TABLE IF EXISTS `system_intro`;
CREATE TABLE `system_intro`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `subtitle` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `picture1` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片1',
  `picture2` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片2',
  `picture3` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统简介' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_intro
-- ----------------------------
INSERT INTO `system_intro` VALUES (1, '2024-03-05 11:41:50', '系统简介', '智能化在线教学支持服务平台教师中心系统', '<p>在平静的海平面上，每个人都可以成为领航员。但如果只有阳光而没有阴影，只有欢乐而没有痛苦，那就不是完整的人生。就拿最幸福的人来说吧——他的幸福是一团纠结的纱线。痛苦和幸福轮番而至，让我们悲喜交集，甚至死亡都让人生更加可爱。人在生命的严肃时刻，在悲伤与丧亲的阴影下，才最接近真实的自我。在生活和事业的各个方面，才智的功能远不如性格，头脑的功能远不如心性，天分远不如自制力、毅力与教养。我始终认为内心开始过严肃生活的人，他外在的生活会开始变得更为俭朴。在一个奢侈浪费的年代，但愿我能让世人了解：人类真正的需求是多么的稀少。不重蹈覆辙才是真正的醒悟。比别人优秀并无任何高贵之处，真正的高贵在于超越从前的自我。</p>', 'upload/systemintro_picture1.jpg', 'upload/systemintro_picture2.jpg', 'upload/systemintro_picture3.jpg');

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `tablename` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `role` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  `token` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'token表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of token
-- ----------------------------
INSERT INTO `token` VALUES (1, 1, 'admin', 'users', '管理员', 'hjwpf1iqw47qh0rzxvup93159ox8no6u', '2024-03-05 11:51:11', '2025-06-27 09:54:27');
INSERT INTO `token` VALUES (2, 1709610637034, '111', 'jiaoshi', '教师', 'u8mn5y60ss0yt05a6l4n2nv8fp90lpql', '2024-03-05 11:52:54', '2024-03-05 13:00:09');
INSERT INTO `token` VALUES (3, 1709611010643, '222', 'xuesheng', '学生', 'qct54zynsr6p47r3l0uadlc2cgcj806j', '2024-03-05 11:56:54', '2025-06-27 10:00:22');

-- ----------------------------
-- Table structure for user_admin
-- ----------------------------
DROP TABLE IF EXISTS `user_admin`;
CREATE TABLE `user_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '管理员' COMMENT '角色',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_admin
-- ----------------------------
INSERT INTO `user_admin` VALUES (1, 'admin', 'admin', 'upload/image1.jpg', '管理员', '2024-03-05 11:41:50');

-- ----------------------------
-- Table structure for user_student
-- ----------------------------
DROP TABLE IF EXISTS `user_student`;
CREATE TABLE `user_student`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `s_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生账号',
  `s_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `gender` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `tel` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `avatar` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '头像',
  PRIMARY KEY (`id`, `s_username` DESC) USING BTREE,
  UNIQUE INDEX `xueshengzhanghao`(`s_username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1709611010644 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_student
-- ----------------------------
INSERT INTO `user_student` VALUES (1709611010643, '2024-03-05 11:56:50', '222', '杨悦', '222', '女', '13612514514', 'upload/1750986712544.png');

-- ----------------------------
-- Table structure for user_teacher
-- ----------------------------
DROP TABLE IF EXISTS `user_teacher`;
CREATE TABLE `user_teacher`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `t_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教师工号（登录账号）',
  `t_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教师姓名',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `gender` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `avatar` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '头像',
  `tel` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `course` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `role` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色（分为助教和教师）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `jiaoshigonghao`(`t_username` ASC) USING BTREE,
  INDEX `t_name`(`t_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1709610637035 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '教师' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_teacher
-- ----------------------------
INSERT INTO `user_teacher` VALUES (1709610637034, '2024-03-05 11:50:37', '111', '杨洋', '111', '女', 'upload/1709610626972.png', '13612514514', '编程', '教师');

SET FOREIGN_KEY_CHECKS = 1;

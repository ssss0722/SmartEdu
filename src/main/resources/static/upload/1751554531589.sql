/*
 Navicat Premium Data Transfer

 Source Server         : myStore
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : smartedu

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 03/07/2025 20:51:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for about_us
-- ----------------------------
DROP TABLE IF EXISTS `about_us`;
CREATE TABLE `about_us`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '标题',
  `subtitle` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '内容',
  `picture1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '图片1',
  `picture2` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '图片2',
  `picture3` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '图片3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '关于我们' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '配置参数名称',
  `value` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '配置参数值',
  `url` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '配置文件' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course_categories
-- ----------------------------
DROP TABLE IF EXISTS `course_categories`;
CREATE TABLE `course_categories`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `course` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '课程类别',
  PRIMARY KEY (`id`, `course`) USING BTREE,
  UNIQUE INDEX `kechengleibie`(`course` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '课程类别' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course_homework
-- ----------------------------
DROP TABLE IF EXISTS `course_homework`;
CREATE TABLE `course_homework`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `course` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `homework` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '作业名称',
  `picture` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '图片',
  `attachment` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '相关附件',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `publish_at` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `contxt` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '作业内容',
  `discussnum` int NULL DEFAULT 0 COMMENT '评论数',
  `storeupnum` int NULL DEFAULT 0 COMMENT '收藏数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_co_ho`(`t_username` ASC) USING BTREE,
  INDEX `cate_co_ho`(`course` ASC) USING BTREE,
  CONSTRAINT `cate_co_ho` FOREIGN KEY (`course`) REFERENCES `course_categories` (`course`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_co_ho` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '课程作业' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course_homework_question
-- ----------------------------
DROP TABLE IF EXISTS `course_homework_question`;
CREATE TABLE `course_homework_question`  (
  `id` bigint NOT NULL COMMENT '主键',
  `addtime` timestamp NOT NULL COMMENT '创建时间',
  `homework_id` bigint NOT NULL COMMENT '所属作业id（外键）',
  `question_id` bigint NOT NULL COMMENT '试题ID（外键）',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号（外键）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_ho_question`(`t_username` ASC) USING BTREE,
  INDEX `qu_ho`(`homework_id` ASC) USING BTREE,
  INDEX `bank_ho_qu`(`question_id` ASC) USING BTREE,
  CONSTRAINT `bank_ho_qu` FOREIGN KEY (`question_id`) REFERENCES `exam_question_bank` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `qu_ho` FOREIGN KEY (`homework_id`) REFERENCES `course_homework` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_ho_question` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course_homework_record
-- ----------------------------
DROP TABLE IF EXISTS `course_homework_record`;
CREATE TABLE `course_homework_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `s_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学生用户id（外键）',
  `homework_id` bigint NOT NULL COMMENT '作业id（外键）',
  `question_id` bigint NOT NULL COMMENT '试题id（外键）',
  `ismark` bigint NULL DEFAULT 0 COMMENT '是否批改',
  `myscore` bigint NOT NULL DEFAULT 0 COMMENT '试题得分',
  `myanswer` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '考生答案',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号(外键)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ho_re_id`(`homework_id` ASC) USING BTREE,
  INDEX `ho_re_qu_bank`(`question_id` ASC) USING BTREE,
  INDEX `ho_re_user_s`(`s_username` ASC) USING BTREE,
  INDEX `ho_t_ex_record`(`t_username` ASC) USING BTREE,
  CONSTRAINT `ho_re_id` FOREIGN KEY (`homework_id`) REFERENCES `course_homework` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ho_re_qu_bank` FOREIGN KEY (`question_id`) REFERENCES `exam_question_bank` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ho_re_user_s` FOREIGN KEY (`s_username`) REFERENCES `user_student` (`s_username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ho_t_ex_record` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course_homework_review
-- ----------------------------
DROP TABLE IF EXISTS `course_homework_review`;
CREATE TABLE `course_homework_review`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `course` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `homework_id` bigint NOT NULL COMMENT '作业名称',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `s_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '学生账号',
  `grade` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '作业评分',
  `reviewed_at` datetime NULL DEFAULT NULL COMMENT '批改时间',
  `teacher_comment` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '教师评语',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_co_ho_re`(`t_username` ASC) USING BTREE,
  INDEX `s_co_ho_re`(`s_username` ASC) USING BTREE,
  INDEX `cate_co_ho_re`(`course` ASC) USING BTREE,
  INDEX `rev_ho`(`homework_id` ASC) USING BTREE,
  CONSTRAINT `cate_co_ho_re` FOREIGN KEY (`course`) REFERENCES `course_categories` (`course`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rev_ho` FOREIGN KEY (`homework_id`) REFERENCES `course_homework` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `s_co_ho_re` FOREIGN KEY (`s_username`) REFERENCES `user_student` (`s_username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_co_ho_re` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '作业批改' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course_homework_submission
-- ----------------------------
DROP TABLE IF EXISTS `course_homework_submission`;
CREATE TABLE `course_homework_submission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `course` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `homework_id` bigint NULL DEFAULT NULL COMMENT '作业名称',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `s_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '学生账号',
  `submission` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '提交作业',
  `submission_at` datetime NULL DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_co_ho_sub`(`t_username` ASC) USING BTREE,
  INDEX `s_co_ho_sub`(`s_username` ASC) USING BTREE,
  INDEX `cate_co_ho_sub`(`course` ASC) USING BTREE,
  INDEX `sub_ho`(`homework_id` ASC) USING BTREE,
  CONSTRAINT `cate_co_ho_sub` FOREIGN KEY (`course`) REFERENCES `course_categories` (`course`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `s_co_ho_sub` FOREIGN KEY (`s_username`) REFERENCES `user_student` (`s_username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sub_ho` FOREIGN KEY (`homework_id`) REFERENCES `course_homework` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_co_ho_sub` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '作业提交' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course_material
-- ----------------------------
DROP TABLE IF EXISTS `course_material`;
CREATE TABLE `course_material`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '标题',
  `course` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '课程类别',
  `picture` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '图片',
  `attachment` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '附件',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `publish_at` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '内容',
  `clicktime` datetime NULL DEFAULT NULL COMMENT '最近点击时间',
  `clicknum` int NULL DEFAULT 0 COMMENT '点击次数',
  `discussnum` int NULL DEFAULT 0 COMMENT '评论数',
  `storeupnum` int NULL DEFAULT 0 COMMENT '收藏数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_co_material`(`t_username` ASC) USING BTREE,
  INDEX `cate_co_material`(`course` ASC) USING BTREE,
  CONSTRAINT `cate_co_material` FOREIGN KEY (`course`) REFERENCES `course_categories` (`course`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_co_material` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '教学资料' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course_video
-- ----------------------------
DROP TABLE IF EXISTS `course_video`;
CREATE TABLE `course_video`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `course` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '课程类别',
  `cover` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '封面',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `video` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '教学视频',
  `published_at` date NULL DEFAULT NULL COMMENT '发布时间',
  `intro` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '基本介绍',
  `clicktime` datetime NULL DEFAULT NULL COMMENT '最近点击时间',
  `clicknum` int NULL DEFAULT 0 COMMENT '点击次数',
  `discussnum` int NULL DEFAULT 0 COMMENT '评论数',
  `storeupnum` int NULL DEFAULT 0 COMMENT '收藏数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_co_video`(`t_username` ASC) USING BTREE,
  INDEX `cate_co_video`(`course` ASC) USING BTREE,
  CONSTRAINT `cate_co_video` FOREIGN KEY (`course`) REFERENCES `course_categories` (`course`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_co_video` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '教学视频' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for discuss_course_homework
-- ----------------------------
DROP TABLE IF EXISTS `discuss_course_homework`;
CREATE TABLE `discuss_course_homework`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refid` bigint NOT NULL COMMENT '关联表id',
  `userid` bigint NOT NULL COMMENT '用户id',
  `avatarurl` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '头像',
  `nickname` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '评论内容',
  `reply` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '回复内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `co_ho_dis`(`refid` ASC) USING BTREE,
  CONSTRAINT `co_ho_dis` FOREIGN KEY (`refid`) REFERENCES `course_homework` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '课程作业评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for discuss_course_material
-- ----------------------------
DROP TABLE IF EXISTS `discuss_course_material`;
CREATE TABLE `discuss_course_material`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refid` bigint NOT NULL COMMENT '关联表id',
  `userid` bigint NOT NULL COMMENT '用户id',
  `avatarurl` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '头像',
  `nickname` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '评论内容',
  `reply` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '回复内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `co_ma_dis`(`refid` ASC) USING BTREE,
  CONSTRAINT `co_ma_dis` FOREIGN KEY (`refid`) REFERENCES `course_material` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '教学资料评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for discuss_course_video
-- ----------------------------
DROP TABLE IF EXISTS `discuss_course_video`;
CREATE TABLE `discuss_course_video`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refid` bigint NOT NULL COMMENT '关联表id',
  `userid` bigint NOT NULL COMMENT '用户id',
  `avatarurl` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '头像',
  `nickname` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '评论内容',
  `reply` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '回复内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `co_vi_dis`(`refid` ASC) USING BTREE,
  CONSTRAINT `co_vi_dis` FOREIGN KEY (`refid`) REFERENCES `course_video` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '教学视频评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '考试ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '考试名称',
  `paper_id` bigint NOT NULL COMMENT '关联试卷ID',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '考试状态：0 草稿，1 已发布',
  `start_time` datetime NOT NULL COMMENT '考试开始时间',
  `end_time` datetime NOT NULL COMMENT '考试结束时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_exam_paper`(`paper_id` ASC) USING BTREE,
  CONSTRAINT `fk_exam_paper` FOREIGN KEY (`paper_id`) REFERENCES `exam_paper` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for exam_paper
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper`;
CREATE TABLE `exam_paper`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '在线考试名称',
  `time` int NOT NULL COMMENT '测试时长(分钟)',
  `status` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '在线考试状态',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_ex_paper`(`t_username` ASC) USING BTREE,
  CONSTRAINT `t_ex_paper` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '在线考试表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for exam_question
-- ----------------------------
DROP TABLE IF EXISTS `exam_question`;
CREATE TABLE `exam_question`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `paperid` bigint NOT NULL COMMENT '所属在线考试id（外键）',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `question_id` bigint NULL DEFAULT NULL COMMENT '试题序号（外键）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_ex_queation`(`t_username` ASC) USING BTREE,
  INDEX `qu_pa`(`paperid` ASC) USING BTREE,
  INDEX `bank_qu`(`question_id` ASC) USING BTREE,
  CONSTRAINT `bank_qu` FOREIGN KEY (`question_id`) REFERENCES `exam_question_bank` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `qu_pa` FOREIGN KEY (`paperid`) REFERENCES `exam_paper` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_ex_queation` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '试题表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for exam_question_bank
-- ----------------------------
DROP TABLE IF EXISTS `exam_question_bank`;
CREATE TABLE `exam_question_bank`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '试题名称',
  `options` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '选项，json字符串',
  `answer` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '正确答案',
  `analysis` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '答案解析',
  `type` bigint NULL DEFAULT 0 COMMENT '试题类型，0：单选题 1：多选题 2：判断题 3：填空题（暂不考虑多项填空） 4:主观题',
  `sequence` bigint NULL DEFAULT 100 COMMENT '试题排序，值越大排越前面',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号',
  `score` bigint NULL DEFAULT NULL COMMENT '试题分值',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_ex_bank`(`t_username` ASC) USING BTREE,
  CONSTRAINT `t_ex_bank` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '试题库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for exam_record
-- ----------------------------
DROP TABLE IF EXISTS `exam_record`;
CREATE TABLE `exam_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `s_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学生用户id（外键）',
  `paperid` bigint NOT NULL COMMENT '在线考试id（外键）',
  `question_id` bigint NOT NULL COMMENT '试题id（外键）',
  `ismark` bigint NULL DEFAULT 0 COMMENT '是否批卷',
  `myscore` bigint NOT NULL DEFAULT 0 COMMENT '试题得分',
  `myanswer` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '考生答案',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '教师工号(外键)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_ex_record`(`t_username` ASC) USING BTREE,
  INDEX `re_qu_bank`(`question_id` ASC) USING BTREE,
  INDEX `re_user_s`(`s_username` ASC) USING BTREE,
  INDEX `re_pa`(`paperid` ASC) USING BTREE,
  CONSTRAINT `re_pa` FOREIGN KEY (`paperid`) REFERENCES `exam_paper` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `re_qu_bank` FOREIGN KEY (`question_id`) REFERENCES `exam_question_bank` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `re_user_s` FOREIGN KEY (`s_username`) REFERENCES `user_student` (`s_username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_ex_record` FOREIGN KEY (`t_username`) REFERENCES `user_teacher` (`t_username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '测试记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for forum
-- ----------------------------
DROP TABLE IF EXISTS `forum`;
CREATE TABLE `forum`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '帖子标题',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '帖子内容',
  `parentid` bigint NULL DEFAULT NULL COMMENT '父节点id',
  `userid` bigint NOT NULL COMMENT '用户id',
  `username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `avatarurl` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '头像',
  `isdone` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '状态',
  `istop` int NULL DEFAULT 0 COMMENT '是否置顶',
  `toptime` datetime NULL DEFAULT NULL COMMENT '置顶时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '交流论坛' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '标题',
  `introduction` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '简介',
  `typename` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发布人',
  `headportrait` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '头像',
  `clicknum` int NULL DEFAULT 0 COMMENT '点击次数',
  `clicktime` datetime NULL DEFAULT NULL COMMENT '最近点击时间',
  `thumbsupnum` int NULL DEFAULT 0 COMMENT '赞',
  `crazilynum` int NULL DEFAULT 0 COMMENT '踩',
  `storeupnum` int NULL DEFAULT 0 COMMENT '收藏数',
  `picture` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图片',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 160 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统公告' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for news_type
-- ----------------------------
DROP TABLE IF EXISTS `news_type`;
CREATE TABLE `news_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `typename` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 150 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统公告分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for store_up
-- ----------------------------
DROP TABLE IF EXISTS `store_up`;
CREATE TABLE `store_up`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` bigint NOT NULL COMMENT '用户id',
  `refid` bigint NULL DEFAULT NULL COMMENT '商品id',
  `tablename` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表名',
  `name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名称',
  `picture` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '图片',
  `type` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '1' COMMENT '类型',
  `inteltype` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '推荐类型',
  `remark` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for system_intro
-- ----------------------------
DROP TABLE IF EXISTS `system_intro`;
CREATE TABLE `system_intro`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '标题',
  `subtitle` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '副标题',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '内容',
  `picture1` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '图片1',
  `picture2` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '图片2',
  `picture3` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '图片3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '系统简介' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` bigint NOT NULL COMMENT '用户id',
  `username` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `tablename` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '表名',
  `role` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色',
  `token` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'token表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_admin
-- ----------------------------
DROP TABLE IF EXISTS `user_admin`;
CREATE TABLE `user_admin`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `image` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '管理员' COMMENT '角色',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_student
-- ----------------------------
DROP TABLE IF EXISTS `user_student`;
CREATE TABLE `user_student`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `s_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学生账号',
  `s_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `password` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `gender` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别',
  `tel` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `avatar` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '头像',
  PRIMARY KEY (`id`, `s_username` DESC) USING BTREE,
  UNIQUE INDEX `xueshengzhanghao`(`s_username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1709611010644 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '学生' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_teacher
-- ----------------------------
DROP TABLE IF EXISTS `user_teacher`;
CREATE TABLE `user_teacher`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `t_username` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '教师工号（登录账号）',
  `t_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '教师姓名',
  `password` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `gender` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别',
  `avatar` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '头像',
  `tel` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `course` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `role` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色（分为助教和教师）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `jiaoshigonghao`(`t_username` ASC) USING BTREE,
  INDEX `t_name`(`t_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1751539878407 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '教师' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

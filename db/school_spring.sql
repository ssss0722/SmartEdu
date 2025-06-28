/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.27 : Database - school_spring
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`school_spring` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `school_spring`;

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `course_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(20) NOT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `course` */

insert  into `course`(`course_id`,`course_name`) values (1,'语文'),(2,'数学'),(3,'英语'),(4,'物理');

/*Table structure for table `grade` */

DROP TABLE IF EXISTS `grade`;

CREATE TABLE `grade` (
  `grade_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grade_name` varchar(20) NOT NULL,
  PRIMARY KEY (`grade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `grade` */

insert  into `grade`(`grade_id`,`grade_name`) values (1,'一年级'),(2,'二年级'),(3,'三年级'),(4,'四年级');

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `path` varchar(200) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT '0',
  `user_type` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `menu` */

insert  into `menu`(`menu_id`,`title`,`path`,`icon`,`parent_id`,`user_type`) values (1,'首页','/main','el-icon-eleme',0,'1,2'),(2,'用户管理','/user','el-icon-user',0,'2'),(3,'学生管理','/student','el-icon-star-off',0,'2'),(4,'成绩管理','/score','el-icon-s-claim',0,'1,2'),(5,'科目管理','/course','el-icon-s-grid',0,'2'),(6,'班级管理','/grade','el-icon-s-home',0,'2'),(7,'菜单管理','/menu','el-icon-s-platform',0,'2');

/*Table structure for table `score` */

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `score_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_id` bigint(20) NOT NULL,
  `score_score` int(11) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  PRIMARY KEY (`score_id`),
  KEY `FK_STUDENT_ID` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4;

/*Data for the table `score` */

insert  into `score`(`score_id`,`student_id`,`score_score`,`course_id`) values (3,100001,80,3),(4,100001,99,4),(5,100002,60,4),(6,100002,70,3),(7,100002,80,2),(8,100002,98,1),(13,100004,60,4),(14,100004,70,3),(15,100004,80,2),(16,100004,97,1),(21,100006,60,4),(22,100006,70,3),(23,100006,80,2),(24,100006,95,1),(25,100007,60,1),(26,100007,70,2),(27,100007,80,3),(28,100007,90,4),(29,100008,60,4),(30,100008,70,3),(31,100008,80,2),(32,100008,90,1),(33,100009,60,1),(34,100009,70,2),(35,100009,80,3),(36,100009,90,4),(37,100010,60,4),(38,100010,70,3),(39,100010,80,2),(40,100010,90,1),(41,100011,60,1),(42,100011,70,2),(43,100011,80,3),(44,100011,90,4),(45,100012,60,4),(46,100012,70,3),(47,100012,80,2),(48,100012,90,1),(49,100013,60,1),(50,100013,70,2),(51,100013,80,3),(52,100013,90,4),(53,100014,60,4),(54,100014,70,3),(55,100014,80,2),(56,100014,90,1);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_name` varchar(30) NOT NULL,
  `student_birth` date DEFAULT NULL,
  `student_address` varchar(100) DEFAULT '位置地址',
  `student_no` bigint(20) NOT NULL,
  `grade_id` bigint(20) NOT NULL,
  `student_age` int(11) DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `student_no` (`student_no`),
  KEY `class_id` (`grade_id`),
  KEY `testGroupIndex` (`student_name`,`student_birth`,`student_address`)
) ENGINE=InnoDB AUTO_INCREMENT=100174 DEFAULT CHARSET=utf8mb4;

/*Data for the table `student` */

insert  into `student`(`student_id`,`student_name`,`student_birth`,`student_address`,`student_no`,`grade_id`,`student_age`) values (100001,'张小山三','1989-07-20','成华大道',1000011,3,15),(100002,'小张张','1888-08-08','北京大道1100号',100002,2,99),(100004,'李晓明1','1999-01-01','弥敦道三号',100004,2,33),(100006,'孙无忌','1999-01-01','弥敦道四号',100006,2,16),(100007,'成昆','1999-01-01','弥敦道五号',100007,2,17),(100008,'赵鹏','1999-01-01','弥敦道六号',100008,2,18),(100009,'刘盛文','1999-01-01','弥敦道七号',100009,2,19),(100010,'何鹏','1999-01-01','弥敦道八号',100010,2,15),(100011,'陈小龙','1999-01-01','弥敦道九号',100011,3,16),(100012,'孙晓武','1999-01-01','弥敦道是号',100012,3,17),(100013,'赵庆鹏','1999-01-01','弥敦道一号',100013,3,18),(100014,'刘娟','1999-01-01','弥敦道二号',100014,3,19),(100015,'钟林军','1999-01-01','弥敦道三号',100015,3,15),(100016,'熊建军','1999-01-01','米顿号四号',100016,3,16),(100017,'张某某11','1999-09-09','成华大道',1000333,4,22),(100021,'孙珊','1999-01-01','吊袜带',100017,3,16),(100022,'赵小花','1999-09-09','朝阳社区1号',1000334,4,22),(100023,'张某某11','1999-09-09','成华大道',1000335,4,22),(100051,'孙珊珊','1999-01-01','为企鹅无群',100018,1,17),(100081,'刘德华','1999-01-01','不知道',100081,1,18),(100101,'李世民','1999-01-01','长安皇宫',100019,1,19),(100151,'王武','1999-01-01',NULL,100020,2,15),(100153,'%_孙三三','1999-01-01','不知道',100088,1,16),(100154,'孙五一','1999-01-01','不知道',100082,4,17),(100155,'孙三五','1999-01-01','不知道',100083,2,18),(100163,'刘某某1','1999-08-08','北京大道',100084,2,18),(100164,'刘某某2','1999-08-08','北京大道',100085,2,18),(100165,'刘某某3','1999-08-08','北京大道',100086,2,18),(100168,'赵小雨a',NULL,'成华大道1100号',199999,1,0),(100169,'李五狗','1990-01-01','北京大道1号',110098,1,18),(100171,'李小敏1','2000-02-02','北京大道1',110666,2,19),(100172,'李小敏2','2000-02-02','北京大道2',110667,2,19),(100173,'李小敏','2000-02-02',NULL,110066,2,19);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `user_type` int(11) DEFAULT NULL COMMENT '1.学生 2.老师',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`user_id`,`username`,`password`,`user_type`) values (1,'100002','123321',1),(2,'600001','123321',2),(4,'100004','123321',1),(5,'600002','123321',2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

CREATE TABLE `book` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `name` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `id_UNIQUE` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8
INSERT INTO `book` VALUES (1, 'book1');
INSERT INTO `book` VALUES (2, 'book2');
INSERT INTO `book` VALUES (6, 'book3');
INSERT INTO `book` VALUES (7, 'book3');
INSERT INTO `book` VALUES (8, 'book3');
INSERT INTO `book` VALUES (9, 'book3');
INSERT INTO `book` VALUES (10, 'book3');
INSERT INTO `book` VALUES (11, 'book3');
INSERT INTO `book` VALUES (12, 'book3');

CREATE TABLE `classes` (
   `cid` bigint(20) NOT NULL,
   `classes_name` varchar(255) NOT NULL,
   PRIMARY KEY (`cid`) USING BTREE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
INSERT INTO `classes` VALUES (1, '小班');
INSERT INTO `classes` VALUES (2, '中班');
INSERT INTO `classes` VALUES (3, '大班');


CREATE TABLE `student` (
   `sid` bigint(20) NOT NULL AUTO_INCREMENT,
   `student_name` varchar(255) NOT NULL,
   `classes_id` bigint(20) NOT NULL,
   `sex` varchar(45) NOT NULL,
   `country` varchar(45) NOT NULL,
   PRIMARY KEY (`sid`) USING BTREE,
   KEY `classes_id` (`classes_id`),
   CONSTRAINT `classes_id` FOREIGN KEY (`classes_id`) REFERENCES `classes` (`cid`)
 ) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8
INSERT INTO `student` VALUES (1, '学生1', 1, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (2, '学生2', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (3, '学生3', 3, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (4, '学生4', 1, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (5, '学生5', 1, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (8, '学生6', 1, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (9, '学生7', 1, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (10, '学生8', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (11, '学生9', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (12, '学生8', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (13, '学生9', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (14, '学生8', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (15, '学生9', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (16, '学生8', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (17, '学生9', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (18, '学生8', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (19, '学生9', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (20, '学生10', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (21, '学生11', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (22, '学生10', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (23, '学生11', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (24, '学生10', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (25, '学生11', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (26, '学生10', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (27, '学生11', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (28, '学生10', 2, 'BOY', 'CHINA');
INSERT INTO `student` VALUES (29, '学生11', 2, 'BOY', 'CHINA');

CREATE TABLE `teacher` (
   `tid` bigint(20) NOT NULL AUTO_INCREMENT,
   `teacher_name` varchar(255) NOT NULL,
   PRIMARY KEY (`tid`)
 ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8
INSERT INTO `teacher` VALUES (1, 'teacher1');
INSERT INTO `teacher` VALUES (2, 'teacher2');

CREATE TABLE `teacher_student` (
   `tid` bigint(20) NOT NULL,
   `sid` bigint(20) NOT NULL,
   KEY `sid` (`sid`),
   KEY `tid` (`tid`),
   CONSTRAINT `sid` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`),
   CONSTRAINT `tid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`tid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
INSERT INTO `teacher_student` VALUES (1, 1);
INSERT INTO `teacher_student` VALUES (1, 2);
INSERT INTO `teacher_student` VALUES (2, 3);
INSERT INTO `teacher_student` VALUES (3, 4);

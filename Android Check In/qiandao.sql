/*
SQLyog Community v8.8 Beta2
MySQL - 5.1.51-community : Database - microblog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE  IF NOT EXISTS `qiandao` DEFAULT CHARACTER SET utf8;

USE `qiandao`;

/*Table structure for table `tb_admin` */
DROP TABLE IF EXISTS `qiandaozongbiao`; 
DROP TABLE IF EXISTS `Student information`;
DROP TABLE IF EXISTS `Student_information`;

CREATE TABLE `Student_information` (
  `s_id` int(10) NOT NULL ,
  `s_name` char(50) NOT NULL, 
  `s_phone` char(11) not NULL,
  `s_phonecm` char(50) not NULL,
  `s_pwd` char(50) NOT NULL,
  PRIMARY KEY (`s_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

/*Data for the table `xueshengxinxi` */

insert  into `Student_information`(`s_id`,`s_name`,`s_phone`,`s_phonecm`,`s_pwd`) 
values 
('11071153','李明','13360146010','A00000F59AK','1234'),
('11071001','韦金辛','13360146010','A00000F59AK','1234'),
('11071003','韦书胜','13360146010','A00000F59AK','1234'),
('11071005','甘学良','13360146010','A00000F59AK','1234'),
('11071008','李仲裁','13360146010','A00000F59AK','1234'),
('11071009','符传鑫','13360146010','A00000F59AK','1234'),
('11071011','韩东起','13360146010','A00000F59AK','1234'),
('11071012','李忠喜','13360146010','A00000F59AK','1234'),
('11071013','向鹏程','13360146010','A00000F59AK','1234'),
('11071014','胡超','13360146010','A00000F59AK','1234'),
('11071016','符太柱','13360146010','A00000F59AK','1234'),
('11071021','马建国','13360146010','A00000F59AK','1234'),
('11071029','虞磊','13360146010','A00000F59AK','1234'),
('11071030','林虹宇','13360146010','A00000F59AK','1234'),
('11071032','郑昱晨','13360146010','A00000F59AK','1234'),
('11071037','游洵','13360146010','A00000F59AK','1234'),
('11071042','张子寒','13360146010','A00000F59AK','1234'),
('11071043','田幸','13360146010','A00000F59AK','1234'),
('11071048','刘畅','13360146010','A00000F59AK','1234'),
('11071154','王华','18221533487','B50142KBSB2','1234'),
('2012271133','孙刚','15944165525','C001079KG520','1234'),
('2012214303','赵明灿','15944165525','C001079KG520','1234'),
('2012214308','简文','15944165525','C001079KG520','1234'),
('2012214309','杨欣','15944165525','C001079KG520','1234'),
('2012214342','苏耀龙','15944165525','C001079KG520','1234'),
('2012214345','金艺红','15944165525','C001079KG520','1234'),
('2012214347','杜悦','15944165525','C001079KG520','1234'),
('2012214348','刘孟丽','15944165525','C001079KG520','1234'),
('2012214352','覃薇薇','15944165525','C001079KG520','1234'),
('2012214353','蒋青竹','15944165525','C001079KG520','1234'),
('2012214354','严婷婷','15944165525','C001079KG520','1234'),
('2012214356','刘英蔚','15944165525','C001079KG520','1234'),
('2012214358','胡冉越','15944165525','C001079KG520','1234'),
('2012214363','周强','15944165525','C001079KG520','1234'),
('2012214365','周维宇','15944165525','C001079KG520','1234'),
('2012214366','梁贝贝','15944165525','C001079KG520','1234'),
('2012214367','冯耀坤','15944165525','C001079KG520','1234'),
('2012214368','曾庆伟','15944165525','C001079KG520','1234'),
('2012214374','宋涌','15944165525','C001079KG520','1234'),
('2012214376','周庆','15944165525','C001079KG520','1234'),
('2012214380','张腾','15944165525','C001079KG520','1234'),
('2012031025','司马曹','18729189981','B30255ABC33kecheng','1234');


/*Table structure for table `tb_collection` */

DROP TABLE IF EXISTS `Teacher information`;
DROP TABLE IF EXISTS `Teacher_information`;
CREATE TABLE `Teacher_information` (
  `t_id` int(11) NOT NULL,
  `t_name` char(50) not NULL,
  `t_phone` char(11) not NULL,
  `t_pwd` char(50) not NULL,
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Data for the table `tb_collection` */

insert  into `Teacher_information`(`t_id`,`t_name`,`t_phone`,`t_pwd`) 
values ('1115050','周老师','13925103515','0000'),
       ('1115051','李老师','15839276954','0000'),
       ('1115052','胡老师','13127146954','0000');

/*Table structure for table `tb_comment` */

DROP TABLE IF EXISTS `Course information`;
DROP TABLE IF EXISTS `Course_information`;

CREATE TABLE `Course_information` (
  `c_id` char(11) NOT NULL ,
  `c_name` char(50) NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Data for the table `tb_comment` */

insert  into `Course_information`(`c_id`,`c_name`) 
values ('000991','java'),
       ('000324','c语言'),
       ('000241','高数');
/*Table structure for table `tb_follow` */

DROP TABLE IF EXISTS `xuanqianbiao`;


CREATE TABLE `xuanqianbiao` (
`s_id` int(10) NOT NULL ,
`t_id` int(11) NOT NULL ,
`c_id` char(11) NOT NULL ,
`GPS` char(50) NOT NULL,
`QD_number` char(10) NOT NULL,
`Qermissions` char(11) NOT NULL ,

  PRIMARY KEY (`s_id`,`t_id`,`c_id`),
constraint `s_id`
  FOREIGN KEY (`s_id`) REFERENCES `Student_information` (`s_id`),
CONSTRAINT `t_id`
  FOREIGN KEY (`t_id`) REFERENCES `Teacher_information` (`t_id`),
CONSTRAINT `c_id`
FOREIGN KEY (`c_id`) REFERENCES `Course_information` (`c_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/*Data for the table `tb_follow` */

insert  into `xuanqianbiao`(`s_id`,`t_id`,`c_id`,`GPS`,`QD_number`,`Qermissions`) 
values 
('11071153','1115050','000241','1.1.1.1','0','f'),
('11071001','1115050','000241','1.1.1.1','0','f'),
('11071003','1115050','000241','1.1.1.1','0','f'),
('11071005','1115050','000241','1.1.1.1','0','f'),
('11071008','1115050','000241','1.1.1.1','0','f'),
('11071009','1115050','000241','1.1.1.1','0','f'),
('11071011','1115050','000241','1.1.1.1','0','f'),
('11071012','1115050','000241','1.1.1.1','0','f'),
('11071013','1115050','000241','1.1.1.1','0','f'),
('11071014','1115050','000241','1.1.1.1','0','f'),
('11071016','1115050','000241','1.1.1.1','0','f'),
('11071021','1115050','000241','1.1.1.1','0','f'),
('11071029','1115050','000241','1.1.1.1','0','f'),
('11071030','1115050','000241','1.1.1.1','0','f'),
('11071032','1115050','000241','1.1.1.1','0','f'),
('11071037','1115050','000241','1.1.1.1','0','f'),
('11071042','1115050','000241','1.1.1.1','0','f'),
('11071043','1115050','000241','1.1.1.1','0','f'),
('11071048','1115050','000241','1.1.1.1','0','f'),
('11071154','1115050','000241','1.1.1.1','0','f'),
('2012271133','1115051','000324','1.1.1.1','0','f'),
('2012214303','1115051','000324','1.1.1.1','0','f'),
('2012214308','1115051','000324','1.1.1.1','0','f'),
('2012214309','1115051','000324','1.1.1.1','0','f'),
('2012214342','1115051','000324','1.1.1.1','0','f'),
('2012214345','1115051','000324','1.1.1.1','0','f'),
('2012214347','1115051','000324','1.1.1.1','0','f'),
('2012214348','1115051','000324','1.1.1.1','0','f'),
('2012214352','1115051','000324','1.1.1.1','0','f'),
('2012214353','1115051','000324','1.1.1.1','0','f'),
('2012214354','1115051','000324','1.1.1.1','0','f'),
('2012214356','1115051','000324','1.1.1.1','0','f'),
('2012214358','1115051','000324','1.1.1.1','0','f'),
('2012214363','1115051','000324','1.1.1.1','0','f'),
('2012214365','1115051','000324','1.1.1.1','0','f'),
('2012214367','1115051','000324','1.1.1.1','0','f'),
('2012214368','1115051','000324','1.1.1.1','0','f'),
('2012214374','1115051','000324','1.1.1.1','0','f'),
('2012214376','1115051','000324','1.1.1.1','0','f'),
('2012214380','1115051','000324','1.1.1.1','0','f'),
('2012031025','1115051','000324','1.1.1.1','0','f'),

('11071008','1115052','000991','1.1.1.1','0','f'),
('11071009','1115052','000991','1.1.1.1','0','f'),
('11071011','1115052','000991','1.1.1.1','0','f'),
('11071012','1115052','000991','1.1.1.1','0','f'),
('11071013','1115052','000991','1.1.1.1','0','f'),
('11071014','1115052','000991','1.1.1.1','0','f'),
('11071016','1115052','000991','1.1.1.1','0','f'),
('11071021','1115052','000991','1.1.1.1','0','f'),
('11071029','1115052','000991','1.1.1.1','0','f'),
('11071030','1115052','000991','1.1.1.1','0','f'),
('11071032','1115052','000991','1.1.1.1','0','f'),
('11071037','1115052','000991','1.1.1.1','0','f'),
('11071042','1115052','000991','1.1.1.1','0','f'),
('11071043','1115052','000991','1.1.1.1','0','f'),
('11071048','1115052','000991','1.1.1.1','0','f'),
('11071154','1115052','000991','1.1.1.1','0','f'),
('2012271133','1115052','000991','1.1.1.1','0','f'),
('2012214303','1115052','000991','1.1.1.1','0','f'),
('2012214308','1115052','000991','1.1.1.1','0','f'),
('2012214309','1115052','000991','1.1.1.1','0','f'),
('2012214342','1115052','000991','1.1.1.1','0','f'),
('2012214345','1115052','000991','1.1.1.1','0','f'),
('2012214347','1115052','000991','1.1.1.1','0','f'),
('2012214348','1115052','000991','1.1.1.1','0','f'),
('2012214352','1115052','000991','1.1.1.1','0','f'),
('2012214353','1115052','000991','1.1.1.1','0','f'),
('2012214354','1115052','000991','1.1.1.1','0','f'),
('2012214356','1115052','000991','1.1.1.1','0','f'),
('2012214358','1115052','000991','1.1.1.1','0','f'),
('2012214363','1115052','000991','1.1.1.1','0','f'),
('2012214365','1115052','000991','1.1.1.1','0','f'),
('2012214367','1115052','000991','1.1.1.1','0','f'),
('2012214368','1115052','000991','1.1.1.1','0','f'),
('2012214374','1115052','000991','1.1.1.1','0','f'),
('2012214376','1115052','000991','1.1.1.1','0','f'),
('2012214380','1115052','000991','1.1.1.1','0','f'),
('2012031025','1115052','000991','1.1.1.1','0','f');


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
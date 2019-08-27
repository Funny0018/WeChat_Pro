/*
SQLyog Enterprise - MySQL GUI v7.12 
MySQL - 5.0.87-community-nt : Database - wx_jvxxs
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`wx_jvxxs` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wx_jvxxs`;

/*Table structure for table `t_amountrecord` */

DROP TABLE IF EXISTS `t_amountrecord`;

CREATE TABLE `t_amountrecord` (
  `fid` int(11) NOT NULL auto_increment,
  `fdate` varchar(20) default NULL,
  `fuserid` int(11) default NULL,
  `fpayamount` decimal(10,2) default NULL,
  `famount` decimal(10,2) default NULL,
  `fstate` int(11) default NULL,
  `fbillno` varchar(20) NOT NULL,
  `fwxno` varchar(80) default NULL,
  `fattech` varchar(50) default NULL,
  `fbody` varchar(50) default NULL,
  `fbilltype` varchar(10) default NULL,
  PRIMARY KEY  (`fid`),
  UNIQUE KEY `fwxno` (`fwxno`)
) ENGINE=InnoDB AUTO_INCREMENT=761 DEFAULT CHARSET=utf8;

/*Table structure for table `t_basecitylist` */

DROP TABLE IF EXISTS `t_basecitylist`;

CREATE TABLE `t_basecitylist` (
  `fid` int(11) NOT NULL,
  `ffullname` varchar(50) default NULL,
  `flat` varchar(50) default NULL,
  `flng` varchar(50) default NULL,
  `fname` varchar(50) default NULL,
  PRIMARY KEY  (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_bill` */

DROP TABLE IF EXISTS `t_bill`;

CREATE TABLE `t_bill` (
  `fbillid` int(10) NOT NULL auto_increment,
  `fstate` int(10) default NULL,
  `ftotalAmount` decimal(10,2) default NULL,
  `fdikouAmount` decimal(10,2) default NULL,
  `fhongbaoAmount` decimal(10,2) default NULL,
  `fproductAmount` decimal(10,2) default NULL,
  `fyunfei` decimal(10,2) default NULL,
  `fvipamount` decimal(10,2) default NULL,
  `ffinalAmount` decimal(10,2) default NULL,
  `fuserID` int(10) default NULL,
  `fbilldate` varchar(50) default NULL,
  `fpaydate` varchar(50) default NULL,
  `fsenddate` varchar(50) default NULL,
  `farrivedate` varchar(50) default NULL,
  `fbillNo` varchar(20) default NULL,
  `fsendno` varchar(200) default NULL,
  `ftype` int(10) NOT NULL default '0',
  PRIMARY KEY  (`fbillid`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

/*Table structure for table `t_billdeliveryaddress` */

DROP TABLE IF EXISTS `t_billdeliveryaddress`;

CREATE TABLE `t_billdeliveryaddress` (
  `fbillDeliveryAddressid` int(11) NOT NULL auto_increment,
  `fbillid` int(11) default NULL,
  `fName` varchar(50) default NULL,
  `fMobile` varchar(50) default NULL,
  `fCity` varchar(50) default NULL,
  `fAddress` varchar(50) default NULL,
  `fBuildNo` varchar(200) default NULL,
  `fDeliveryAddressid` int(11) default NULL,
  PRIMARY KEY  (`fbillDeliveryAddressid`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;

/*Table structure for table `t_billproduct` */

DROP TABLE IF EXISTS `t_billproduct`;

CREATE TABLE `t_billproduct` (
  `fBillProductID` int(11) NOT NULL auto_increment,
  `fbillID` int(11) default NULL,
  `fName` varchar(50) default NULL,
  `fImgUrl` varchar(255) default NULL,
  `fDesc` varchar(200) default NULL,
  `fDescDetail` varchar(255) default NULL,
  `fOldPrice` decimal(10,2) default NULL,
  `fvipprice` decimal(10,2) default NULL,
  `fPrice` decimal(10,2) default NULL,
  `fUnit` varchar(10) default NULL,
  `fCount` decimal(10,2) default NULL,
  `fproductID` int(11) default NULL,
  PRIMARY KEY  (`fBillProductID`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8;

/*Table structure for table `t_cart` */

DROP TABLE IF EXISTS `t_cart`;

CREATE TABLE `t_cart` (
  `fid` int(11) NOT NULL auto_increment,
  `fproductid` int(11) default NULL,
  `fcount` int(11) default NULL,
  `fuserid` int(11) default NULL,
  PRIMARY KEY  (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `t_deliveryaddress` */

DROP TABLE IF EXISTS `t_deliveryaddress`;

CREATE TABLE `t_deliveryaddress` (
  `fid` int(11) NOT NULL auto_increment,
  `fname` varchar(20) default NULL,
  `fphone` varchar(20) default NULL,
  `fcity` varchar(50) default NULL,
  `faddress` varchar(200) default NULL,
  `fbuildno` varchar(200) default NULL,
  `fuserid` int(11) default NULL,
  `fisdefault` int(11) default NULL,
  PRIMARY KEY  (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Table structure for table `t_optionalcity` */

DROP TABLE IF EXISTS `t_optionalcity`;

CREATE TABLE `t_optionalcity` (
  `fid` int(11) NOT NULL auto_increment,
  `fcityname` varchar(20) default NULL,
  `fstate` int(2) default NULL,
  PRIMARY KEY  (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `t_productimgs` */

DROP TABLE IF EXISTS `t_productimgs`;

CREATE TABLE `t_productimgs` (
  `fid` int(11) NOT NULL auto_increment,
  `fproductid` int(11) default NULL,
  `fimageurl` varchar(255) default NULL,
  `fstate` int(11) NOT NULL default '0',
  `fcreatedate` varchar(20) default NULL,
  PRIMARY KEY  (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Table structure for table `t_products` */

DROP TABLE IF EXISTS `t_products`;

CREATE TABLE `t_products` (
  `fproductid` int(11) NOT NULL auto_increment,
  `fname` varchar(20) default NULL,
  `fimgurl` varchar(255) default NULL,
  `fdetailimg` varchar(255) default NULL,
  `fdesc` varchar(100) default NULL,
  `fdescdetail` varchar(255) default NULL,
  `foldprice` decimal(10,2) default NULL,
  `fvipprice` decimal(10,2) default NULL,
  `fprice` decimal(10,2) default NULL,
  `funit` varchar(10) default NULL,
  `ftype` int(11) NOT NULL default '0',
  `ftag` varchar(100) default NULL,
  `fsalled` int(11) NOT NULL default '0',
  `fcreatedate` varchar(50) default NULL,
  `fstate` int(11) NOT NULL default '0',
  PRIMARY KEY  (`fproductid`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

/*Table structure for table `t_producttag` */

DROP TABLE IF EXISTS `t_producttag`;

CREATE TABLE `t_producttag` (
  `fid` int(11) NOT NULL auto_increment,
  `fname` varchar(20) default NULL,
  `fstate` int(11) default NULL,
  PRIMARY KEY  (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `t_producttype` */

DROP TABLE IF EXISTS `t_producttype`;

CREATE TABLE `t_producttype` (
  `ftypeid` int(11) NOT NULL auto_increment,
  `fname` varchar(20) default NULL,
  `fstate` int(11) default NULL,
  PRIMARY KEY  (`ftypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Table structure for table `t_rechargetype` */

DROP TABLE IF EXISTS `t_rechargetype`;

CREATE TABLE `t_rechargetype` (
  `fid` int(11) NOT NULL auto_increment,
  `fprice` decimal(10,0) default NULL,
  `fdec` varchar(20) default NULL,
  `famount` decimal(10,2) default NULL,
  `fstate` int(11) default NULL,
  PRIMARY KEY  (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `fuserid` int(11) NOT NULL auto_increment,
  `fopenid` varchar(50) default NULL,
  `funionid` varchar(50) default NULL,
  `fmoney` decimal(10,2) default NULL,
  `fisvip` int(11) default '0',
  `fvipstartdate` varchar(50) default '1900-01-01 00:00:00',
  `fvipenddate` varchar(50) default '1900-01-01 00:00:00',
  PRIMARY KEY  (`fuserid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Table structure for table `t_vipbill` */

DROP TABLE IF EXISTS `t_vipbill`;

CREATE TABLE `t_vipbill` (
  `fid` int(11) NOT NULL auto_increment,
  `fstate` int(11) default '0',
  `fbillno` varchar(20) default NULL,
  `fuserid` int(11) default NULL,
  `famount` decimal(10,2) default NULL,
  `fdate` varchar(20) default '1900-01-01 00:00:00',
  `fviptype` int(11) default '0',
  `frechargetype` int(11) default '0',
  `ftype` varchar(10) default NULL,
  `fpaydate` varchar(20) default '1900-01-01 00:00:00',
  PRIMARY KEY  (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Table structure for table `t_viptype` */

DROP TABLE IF EXISTS `t_viptype`;

CREATE TABLE `t_viptype` (
  `fid` int(11) NOT NULL auto_increment,
  `fname` varchar(50) default NULL,
  `ffullname` varchar(200) default NULL,
  `fdiscount` varchar(50) default NULL,
  `foldprice` decimal(10,2) default NULL,
  `fprice` decimal(10,2) default NULL,
  `fstate` int(11) default NULL,
  `ftime` varchar(20) default '0,0,1',
  PRIMARY KEY  (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `Fid` varchar(10) NOT NULL,
  `ftest` varchar(10) default NULL,
  PRIMARY KEY  (`Fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

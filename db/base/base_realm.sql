/*
SQLyog Community v11.31 Beta1 (64 bit)
MySQL - 5.5.35 : Database - ancestra_realm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `accounts` */

DROP TABLE IF EXISTS `accounts`;

CREATE TABLE `accounts` (
  `guid` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(30) NOT NULL,
  `pass` varchar(50) NOT NULL,
  `level` int(11) NOT NULL DEFAULT '0',
  `subscription` int(11) NOT NULL DEFAULT '525600',
  `email` varchar(100) NOT NULL,
  `lastIP` varchar(15) NOT NULL,
  `lastConnectionDate` varchar(100) NOT NULL,
  `question` varchar(100) NOT NULL DEFAULT 'DELETE?',
  `reponse` varchar(100) NOT NULL DEFAULT 'DELETE',
  `pseudo` varchar(30) NOT NULL,
  `banned` tinyint(3) NOT NULL DEFAULT '0',
  `curIP` varchar(15) NOT NULL,
  PRIMARY KEY (`guid`),
  UNIQUE KEY `account` (`account`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Table structure for table `banip` */

DROP TABLE IF EXISTS `banip`;

CREATE TABLE `banip` (
  `ip` varchar(15) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `gameservers` */

DROP TABLE IF EXISTS `gameservers`;

CREATE TABLE `gameservers` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ServerIP` text NOT NULL,
  `ServerPort` int(11) NOT NULL,
  `State` int(11) NOT NULL,
  `ServerBDD` text NOT NULL,
  `ServerDBName` text NOT NULL,
  `ServerUser` text NOT NULL,
  `ServerPassword` text NOT NULL,
  `key` text NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

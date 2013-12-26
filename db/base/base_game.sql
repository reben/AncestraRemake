/*
SQLyog Community v11.31 Beta1 (64 bit)
MySQL - 5.5.35 : Database - ancestra_game
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `account_data` */

DROP TABLE IF EXISTS `account_data`;

CREATE TABLE `account_data` (
  `guid` int(11) NOT NULL AUTO_INCREMENT,
  `friends` text NOT NULL,
  `enemys` text NOT NULL,
  `bankObj` text NOT NULL,
  `bankKamas` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`guid`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Table structure for table `animations` */

DROP TABLE IF EXISTS `animations`;

CREATE TABLE `animations` (
  `guid` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) NOT NULL DEFAULT '0',
  `nom` varchar(50) NOT NULL DEFAULT '0',
  `area` int(11) NOT NULL DEFAULT '0',
  `action` int(11) NOT NULL DEFAULT '0',
  `size` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`guid`)
) ENGINE=InnoDB AUTO_INCREMENT=459 DEFAULT CHARSET=latin1;

/*Table structure for table `area_data` */

DROP TABLE IF EXISTS `area_data`;

CREATE TABLE `area_data` (
  `id` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `superarea` int(11) NOT NULL,
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `coffres` */

DROP TABLE IF EXISTS `coffres`;

CREATE TABLE `coffres` (
  `id` int(11) NOT NULL,
  `id_house` int(11) NOT NULL,
  `mapid` int(11) NOT NULL,
  `cellid` int(11) NOT NULL,
  `object` text NOT NULL,
  `kamas` int(11) NOT NULL,
  `key` varchar(8) NOT NULL DEFAULT '-',
  `owner_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `crafts` */

DROP TABLE IF EXISTS `crafts`;

CREATE TABLE `crafts` (
  `id` int(11) NOT NULL,
  `craft` text NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `drops` */

DROP TABLE IF EXISTS `drops`;

CREATE TABLE `drops` (
  `mob` int(11) NOT NULL,
  `item` int(11) NOT NULL,
  `seuil` int(11) NOT NULL DEFAULT '100',
  `max` int(11) NOT NULL,
  `taux` decimal(10,0) NOT NULL,
  KEY `mob` (`mob`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

/*Table structure for table `endfight_action` */

DROP TABLE IF EXISTS `endfight_action`;

CREATE TABLE `endfight_action` (
  `map` int(11) NOT NULL,
  `fighttype` int(11) NOT NULL,
  `action` int(11) NOT NULL,
  `args` varchar(30) COLLATE latin1_bin NOT NULL,
  `cond` varchar(50) COLLATE latin1_bin NOT NULL DEFAULT '',
  KEY `map` (`map`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

/*Table structure for table `experience` */

DROP TABLE IF EXISTS `experience`;

CREATE TABLE `experience` (
  `lvl` int(11) NOT NULL,
  `perso` bigint(11) NOT NULL,
  `metier` bigint(11) NOT NULL,
  `dinde` bigint(11) NOT NULL,
  `pvp` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `guild_members` */

DROP TABLE IF EXISTS `guild_members`;

CREATE TABLE `guild_members` (
  `guid` int(11) NOT NULL,
  `guild` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `level` int(11) NOT NULL,
  `gfxid` int(11) NOT NULL,
  `rank` int(11) NOT NULL,
  `xpdone` bigint(20) NOT NULL,
  `pxp` int(11) NOT NULL,
  `rights` int(11) NOT NULL,
  `align` tinyint(4) NOT NULL,
  `lastConnection` varchar(30) NOT NULL,
  UNIQUE KEY `guid` (`guid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `guilds` */

DROP TABLE IF EXISTS `guilds`;

CREATE TABLE `guilds` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `emblem` varchar(20) NOT NULL,
  `lvl` int(11) NOT NULL DEFAULT '1',
  `xp` bigint(20) NOT NULL DEFAULT '0',
  `capital` int(11) NOT NULL DEFAULT '0',
  `nbrmax` int(11) NOT NULL DEFAULT '0',
  `sorts` varchar(255) NOT NULL DEFAULT '462;0|461;0|460;0|459;0|458;0|457;0|456;0|455;0|454;0|453;0|452;0|451;0|',
  `stats` varchar(255) NOT NULL DEFAULT '176;100|158;1000|124;100|',
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `hdvs` */

DROP TABLE IF EXISTS `hdvs`;

CREATE TABLE `hdvs` (
  `map` int(11) NOT NULL,
  `categories` varchar(250) NOT NULL,
  `sellTaxe` double NOT NULL DEFAULT '1',
  `lvlMax` int(11) NOT NULL DEFAULT '1000',
  `accountItem` int(11) NOT NULL DEFAULT '20',
  `sellTime` int(11) NOT NULL DEFAULT '350'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `hdvs_items` */

DROP TABLE IF EXISTS `hdvs_items`;

CREATE TABLE `hdvs_items` (
  `itemid` int(11) NOT NULL,
  `hdvmapid` int(11) NOT NULL,
  `ownerGuid` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `count` int(3) NOT NULL,
  `sellDate` varchar(20) NOT NULL DEFAULT 'rien',
  PRIMARY KEY (`itemid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `houses` */

DROP TABLE IF EXISTS `houses`;

CREATE TABLE `houses` (
  `id` int(10) unsigned NOT NULL,
  `map_id` int(10) unsigned NOT NULL DEFAULT '0',
  `cell_id` int(10) unsigned NOT NULL DEFAULT '0',
  `owner_id` int(10) NOT NULL DEFAULT '0',
  `owner_pseudo` text NOT NULL,
  `sale` int(10) NOT NULL DEFAULT '-1',
  `guild_id` int(10) NOT NULL DEFAULT '-1',
  `access` int(10) unsigned NOT NULL DEFAULT '0',
  `key` varchar(8) NOT NULL DEFAULT '00000000',
  `guild_rights` int(8) unsigned NOT NULL DEFAULT '0',
  `mapid` int(11) NOT NULL DEFAULT '0',
  `caseid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `interactive_objects_data` */

DROP TABLE IF EXISTS `interactive_objects_data`;

CREATE TABLE `interactive_objects_data` (
  `id` int(11) NOT NULL,
  `respawn` int(11) NOT NULL DEFAULT '10000',
  `duration` int(11) NOT NULL DEFAULT '1500',
  `unknow` int(11) NOT NULL DEFAULT '4',
  `walkable` int(2) NOT NULL DEFAULT '1',
  `Name IO` text NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `item_template` */

DROP TABLE IF EXISTS `item_template`;

CREATE TABLE `item_template` (
  `id` int(11) NOT NULL DEFAULT '-1',
  `type` int(11) NOT NULL DEFAULT '-1',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `level` int(11) NOT NULL DEFAULT '1',
  `statsTemplate` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `pod` int(11) NOT NULL DEFAULT '0',
  `panoplie` int(11) NOT NULL DEFAULT '-1',
  `prix` int(11) NOT NULL DEFAULT '0' COMMENT 'prix de vente PAR un Npc',
  `condition` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `armesInfos` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `guid` int(11) NOT NULL,
  `template` int(11) NOT NULL,
  `qua` int(11) NOT NULL,
  `pos` int(11) NOT NULL,
  `stats` text NOT NULL,
  UNIQUE KEY `guid` (`guid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `itemsets` */

DROP TABLE IF EXISTS `itemsets`;

CREATE TABLE `itemsets` (
  `ID` int(11) NOT NULL DEFAULT '0',
  `name` varchar(150) NOT NULL,
  `items` text NOT NULL,
  `bonus` text NOT NULL COMMENT 'bonus2items1,bonus2items2;bonus3items1,bonus3items2',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AVG_ROW_LENGTH=61;

/*Table structure for table `jobs_data` */

DROP TABLE IF EXISTS `jobs_data`;

CREATE TABLE `jobs_data` (
  `id` tinyint(4) NOT NULL,
  `tools` varchar(300) NOT NULL COMMENT 'outils utilisables',
  `crafts` text NOT NULL COMMENT 'templateID craftable',
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `live_action` */

DROP TABLE IF EXISTS `live_action`;

CREATE TABLE `live_action` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PlayerID` int(11) NOT NULL,
  `Action` int(11) NOT NULL,
  `Nombre` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `maps` */

DROP TABLE IF EXISTS `maps`;

CREATE TABLE `maps` (
  `id` int(11) NOT NULL,
  `date` varchar(50) NOT NULL,
  `width` int(11) NOT NULL DEFAULT '-1',
  `heigth` int(11) NOT NULL DEFAULT '-1',
  `places` varchar(300) NOT NULL DEFAULT '|',
  `key` text NOT NULL,
  `mapData` text NOT NULL,
  `cells` text NOT NULL,
  `monsters` text NOT NULL,
  `capabilities` int(5) NOT NULL DEFAULT '0',
  `mappos` varchar(15) NOT NULL DEFAULT '0,0,0',
  `numgroup` int(11) NOT NULL DEFAULT '5',
  `groupmaxsize` int(11) NOT NULL DEFAULT '6',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `mobgroups_fix` */

DROP TABLE IF EXISTS `mobgroups_fix`;

CREATE TABLE `mobgroups_fix` (
  `mapid` int(11) NOT NULL,
  `cellid` int(11) NOT NULL,
  `groupData` varchar(200) NOT NULL,
  KEY `mapid` (`mapid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `monsters` */

DROP TABLE IF EXISTS `monsters`;

CREATE TABLE `monsters` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `gfxID` int(11) NOT NULL,
  `align` int(11) NOT NULL,
  `grades` text NOT NULL,
  `colors` varchar(30) NOT NULL DEFAULT '-1,-1,-1',
  `stats` text NOT NULL COMMENT 'For,Sag,Int,Cha,Agi',
  `spells` text NOT NULL,
  `pdvs` varchar(200) NOT NULL DEFAULT '1|1|1|1|1|1|1|1|1|1',
  `points` varchar(200) NOT NULL DEFAULT '1;1|1;1|1;1|1;1|1;1|1;1|1;1|1;1|1;1|1;1',
  `inits` varchar(200) NOT NULL DEFAULT '1|1|1|1|1|1|1|1|1|1',
  `minKamas` int(11) NOT NULL DEFAULT '0',
  `maxKamas` int(11) NOT NULL DEFAULT '0',
  `exps` varchar(200) NOT NULL DEFAULT '1|1|1|1|1|1|1|1|1|1',
  `AI_Type` int(11) NOT NULL DEFAULT '1' COMMENT '0: poutch 1: Agressif 2: Fuyarde 3: Soutient 4: Sp�cial',
  `capturable` int(11) NOT NULL DEFAULT '1',
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `mountpark_data` */

DROP TABLE IF EXISTS `mountpark_data`;

CREATE TABLE `mountpark_data` (
  `mapid` int(11) NOT NULL,
  `cellid` int(11) NOT NULL,
  `size` int(11) NOT NULL,
  `owner` int(11) NOT NULL,
  `guild` int(11) NOT NULL DEFAULT '-1',
  `price` int(11) NOT NULL,
  `data` text NOT NULL,
  PRIMARY KEY (`mapid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `mounts_data` */

DROP TABLE IF EXISTS `mounts_data`;

CREATE TABLE `mounts_data` (
  `id` int(11) NOT NULL,
  `color` int(11) NOT NULL,
  `sexe` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `xp` int(32) NOT NULL,
  `level` int(11) NOT NULL,
  `endurance` int(11) NOT NULL,
  `amour` int(11) NOT NULL,
  `maturite` int(11) NOT NULL,
  `serenite` int(11) NOT NULL,
  `reproductions` int(11) NOT NULL,
  `fatigue` int(11) NOT NULL,
  `energie` int(11) NOT NULL,
  `items` text NOT NULL,
  `ancetres` varchar(50) NOT NULL DEFAULT ',,,,,,,,,,,,,',
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `npc_questions` */

DROP TABLE IF EXISTS `npc_questions`;

CREATE TABLE `npc_questions` (
  `ID` int(11) NOT NULL,
  `responses` varchar(100) NOT NULL,
  `params` varchar(100) NOT NULL,
  `cond` text NOT NULL,
  `ifFalse` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `npc_reponses_actions` */

DROP TABLE IF EXISTS `npc_reponses_actions`;

CREATE TABLE `npc_reponses_actions` (
  `ID` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `args` text NOT NULL,
  KEY `ID` (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `npc_template` */

DROP TABLE IF EXISTS `npc_template`;

CREATE TABLE `npc_template` (
  `id` int(11) NOT NULL,
  `bonusValue` int(11) NOT NULL,
  `gfxID` int(11) NOT NULL,
  `scaleX` int(11) NOT NULL,
  `scaleY` int(11) NOT NULL,
  `sex` int(11) NOT NULL,
  `color1` int(11) NOT NULL,
  `color2` int(11) NOT NULL,
  `color3` int(11) NOT NULL,
  `accessories` varchar(30) NOT NULL DEFAULT '0,0,0,0',
  `extraClip` int(11) NOT NULL DEFAULT '-1',
  `customArtWork` int(11) NOT NULL DEFAULT '0',
  `initQuestion` int(11) NOT NULL DEFAULT '-1',
  `ventes` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `npcs` */

DROP TABLE IF EXISTS `npcs`;

CREATE TABLE `npcs` (
  `mapid` int(11) NOT NULL,
  `npcid` int(11) NOT NULL,
  `cellid` int(11) NOT NULL,
  `orientation` int(11) NOT NULL,
  KEY `mapid` (`mapid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `percepteurs` */

DROP TABLE IF EXISTS `percepteurs`;

CREATE TABLE `percepteurs` (
  `guid` int(11) NOT NULL AUTO_INCREMENT,
  `mapid` int(11) NOT NULL,
  `cellid` int(11) NOT NULL,
  `orientation` int(11) NOT NULL,
  `guild_id` int(11) NOT NULL,
  `N1` int(11) NOT NULL,
  `N2` int(11) NOT NULL,
  `objets` text NOT NULL,
  `kamas` int(11) NOT NULL,
  `xp` int(11) NOT NULL,
  PRIMARY KEY (`guid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `personnages` */

DROP TABLE IF EXISTS `personnages`;

CREATE TABLE `personnages` (
  `guid` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `sexe` tinyint(4) NOT NULL,
  `class` smallint(6) NOT NULL,
  `color1` int(11) NOT NULL,
  `color2` int(11) NOT NULL,
  `color3` int(11) NOT NULL,
  `kamas` int(11) NOT NULL,
  `spellboost` int(11) NOT NULL,
  `capital` int(11) NOT NULL,
  `energy` int(11) NOT NULL DEFAULT '10000',
  `level` int(11) NOT NULL,
  `xp` bigint(32) NOT NULL DEFAULT '0',
  `size` int(11) NOT NULL,
  `gfx` int(11) NOT NULL,
  `alignement` int(11) NOT NULL DEFAULT '0',
  `honor` int(11) NOT NULL DEFAULT '0',
  `deshonor` int(11) NOT NULL DEFAULT '0',
  `alvl` int(11) NOT NULL DEFAULT '0' COMMENT 'Niveau alignement',
  `account` int(11) NOT NULL,
  `vitalite` int(11) NOT NULL DEFAULT '0',
  `force` int(11) NOT NULL DEFAULT '0',
  `sagesse` int(11) NOT NULL DEFAULT '0',
  `intelligence` int(11) NOT NULL DEFAULT '0',
  `chance` int(11) NOT NULL DEFAULT '0',
  `agilite` int(11) NOT NULL DEFAULT '0',
  `seeSpell` tinyint(4) NOT NULL DEFAULT '0',
  `seeFriend` tinyint(4) NOT NULL DEFAULT '1',
  `seeAlign` int(11) NOT NULL DEFAULT '0',
  `seeSeller` tinyint(4) NOT NULL DEFAULT '0',
  `canaux` varchar(15) NOT NULL DEFAULT '*#%!pi$:?',
  `map` int(11) NOT NULL DEFAULT '8479',
  `cell` int(11) NOT NULL,
  `pdvper` int(11) NOT NULL DEFAULT '100',
  `spells` text NOT NULL,
  `objets` text NOT NULL,
  `storeObjets` text NOT NULL,
  `savepos` varchar(20) NOT NULL DEFAULT '10298,314',
  `zaaps` varchar(250) NOT NULL DEFAULT '',
  `jobs` varchar(300) NOT NULL DEFAULT '',
  `mountxpgive` int(11) NOT NULL DEFAULT '0',
  `mount` int(11) NOT NULL DEFAULT '-1',
  `title` int(11) NOT NULL DEFAULT '0',
  `wife` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`guid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `scripted_cells` */

DROP TABLE IF EXISTS `scripted_cells`;

CREATE TABLE `scripted_cells` (
  `MapID` int(11) NOT NULL,
  `CellID` int(11) NOT NULL,
  `ActionID` int(11) NOT NULL,
  `EventID` int(11) NOT NULL,
  `ActionsArgs` text NOT NULL,
  `Conditions` text NOT NULL,
  KEY `MapID` (`MapID`),
  KEY `CellID` (`CellID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `sorts` */

DROP TABLE IF EXISTS `sorts`;

CREATE TABLE `sorts` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) CHARACTER SET latin1 NOT NULL,
  `sprite` int(11) NOT NULL DEFAULT '-1',
  `spriteInfos` varchar(20) CHARACTER SET latin1 NOT NULL DEFAULT '0,0,0',
  `lvl1` text CHARACTER SET latin1 NOT NULL,
  `lvl2` text CHARACTER SET latin1 NOT NULL,
  `lvl3` text CHARACTER SET latin1 NOT NULL,
  `lvl4` text CHARACTER SET latin1 NOT NULL,
  `lvl5` text CHARACTER SET latin1 NOT NULL,
  `lvl6` text CHARACTER SET latin1 NOT NULL,
  `effectTarget` varchar(300) COLLATE utf8_bin NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `subarea_data` */

DROP TABLE IF EXISTS `subarea_data`;

CREATE TABLE `subarea_data` (
  `id` int(11) NOT NULL,
  `area` int(11) NOT NULL,
  `alignement` int(11) NOT NULL DEFAULT '-1',
  `name` varchar(200) NOT NULL,
  `subscribeNeed` int(1) NOT NULL DEFAULT '1',
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `temp_mapobjects` */

DROP TABLE IF EXISTS `temp_mapobjects`;

CREATE TABLE `temp_mapobjects` (
  `mapid` int(11) NOT NULL,
  `cellid` int(11) NOT NULL,
  `tempID` int(11) NOT NULL,
  `data` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `use_item_actions` */

DROP TABLE IF EXISTS `use_item_actions`;

CREATE TABLE `use_item_actions` (
  `template` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `args` varchar(100) COLLATE latin1_bin NOT NULL,
  KEY `template` (`template`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

/*Table structure for table `zaapi` */

DROP TABLE IF EXISTS `zaapi`;

CREATE TABLE `zaapi` (
  `mapid` int(11) NOT NULL,
  `align` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Table structure for table `zaaps` */

DROP TABLE IF EXISTS `zaaps`;

CREATE TABLE `zaaps` (
  `mapID` int(11) NOT NULL,
  `cellID` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

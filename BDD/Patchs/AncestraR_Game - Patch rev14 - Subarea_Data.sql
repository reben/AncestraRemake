/*
Navicat MySQL Data Transfer

Source Server         : R-Emu
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : ancestrar_game

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2013-12-26 11:59:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `subarea_data`
-- ----------------------------
DROP TABLE IF EXISTS `subarea_data`;
CREATE TABLE `subarea_data` (
  `id` int(11) NOT NULL,
  `area` int(11) NOT NULL,
  `alignement` int(11) NOT NULL DEFAULT '0',
  `name` varchar(200) NOT NULL,
  `subscribeNeed` int(1) NOT NULL DEFAULT '1',
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of subarea_data
-- ----------------------------
INSERT INTO `subarea_data` VALUES ('0', '0', '0', '//Amakna', '1');
INSERT INTO `subarea_data` VALUES ('1', '0', '0', 'Port de Madrestam', '1');
INSERT INTO `subarea_data` VALUES ('2', '0', '0', 'La montagne des Craqueleurs', '1');
INSERT INTO `subarea_data` VALUES ('3', '0', '0', 'Le champ des Ingalsses', '1');
INSERT INTO `subarea_data` VALUES ('4', '0', '0', 'La for?t d\'Amakna', '1');
INSERT INTO `subarea_data` VALUES ('5', '0', '0', 'Le coin des Bouftous', '1');
INSERT INTO `subarea_data` VALUES ('6', '0', '0', 'Le cimeti?re', '1');
INSERT INTO `subarea_data` VALUES ('7', '0', '0', 'Les cryptes', '1');
INSERT INTO `subarea_data` VALUES ('8', '0', '0', 'Campement des Bworks', '1');
INSERT INTO `subarea_data` VALUES ('9', '0', '0', 'La for?t mal?fique', '1');
INSERT INTO `subarea_data` VALUES ('10', '0', '0', 'Le village', '1');
INSERT INTO `subarea_data` VALUES ('11', '0', '0', 'Territoire des Porcos', '1');
INSERT INTO `subarea_data` VALUES ('12', '0', '0', 'La p?ninsule des gel?es', '1');
INSERT INTO `subarea_data` VALUES ('13', '0', '0', 'Le temple F?ca', '1');
INSERT INTO `subarea_data` VALUES ('14', '0', '0', 'Le temple Osamodas', '1');
INSERT INTO `subarea_data` VALUES ('15', '0', '0', 'Le temple Enutrof', '1');
INSERT INTO `subarea_data` VALUES ('16', '0', '0', 'Le temple Sram', '1');
INSERT INTO `subarea_data` VALUES ('17', '0', '0', 'Le temple X?lor', '1');
INSERT INTO `subarea_data` VALUES ('18', '0', '0', 'Le temple Ecaflip', '1');
INSERT INTO `subarea_data` VALUES ('19', '0', '0', 'Le temple Iop', '1');
INSERT INTO `subarea_data` VALUES ('20', '0', '0', 'Le temple Cr', '1');
INSERT INTO `subarea_data` VALUES ('21', '0', '0', 'Le temple Sadida', '1');
INSERT INTO `subarea_data` VALUES ('22', '0', '0', 'Bord de la f?ret mal?fique', '1');
INSERT INTO `subarea_data` VALUES ('23', '0', '0', 'La presqu\'?le des Dragoeufs', '1');
INSERT INTO `subarea_data` VALUES ('25', '1', '0', 'Sous-terrains des Wabbits', '1');
INSERT INTO `subarea_data` VALUES ('26', '0', '0', 'Le temple Eniripsa', '1');
INSERT INTO `subarea_data` VALUES ('27', '0', '0', 'C?te d\'Asse', '1');
INSERT INTO `subarea_data` VALUES ('28', '0', '0', 'Garnison d\'Amakna', '1');
INSERT INTO `subarea_data` VALUES ('29', '0', '0', 'Souterrain', '1');
INSERT INTO `subarea_data` VALUES ('30', '4', '0', 'Le berceau', '0');
INSERT INTO `subarea_data` VALUES ('31', '0', '0', 'Le mar?cage', '1');
INSERT INTO `subarea_data` VALUES ('32', '5', '0', '//Sufokia', '1');
INSERT INTO `subarea_data` VALUES ('33', '6', '0', '//For?t des Abraknydes', '1');
INSERT INTO `subarea_data` VALUES ('34', '3', '0', '//Prison', '1');
INSERT INTO `subarea_data` VALUES ('35', '0', '0', 'Porte de Sufokia', '1');
INSERT INTO `subarea_data` VALUES ('37', '7', '0', '//Bonta', '1');
INSERT INTO `subarea_data` VALUES ('38', '8', '0', '//Plaine de Cania', '1');
INSERT INTO `subarea_data` VALUES ('39', '0', '0', 'Le repaire des Roublards', '1');
INSERT INTO `subarea_data` VALUES ('41', '0', '0', 'Le temple Sacrieur', '1');
INSERT INTO `subarea_data` VALUES ('42', '8', '0', 'Route de Bonta', '1');
INSERT INTO `subarea_data` VALUES ('43', '7', '0', 'Fortification de Bonta', '1');
INSERT INTO `subarea_data` VALUES ('44', '7', '0', 'Quartier des Boulangers', '1');
INSERT INTO `subarea_data` VALUES ('45', '7', '0', 'Quartier de la Milice', '1');
INSERT INTO `subarea_data` VALUES ('46', '7', '0', 'Quartier des Bouchers', '1');
INSERT INTO `subarea_data` VALUES ('47', '7', '0', 'Quartier des Forgerons', '1');
INSERT INTO `subarea_data` VALUES ('48', '7', '0', 'Quartier des B?cherons', '1');
INSERT INTO `subarea_data` VALUES ('49', '7', '0', 'Quartier des Bricoleurs', '1');
INSERT INTO `subarea_data` VALUES ('50', '7', '0', 'Quartier des Tailleurs', '1');
INSERT INTO `subarea_data` VALUES ('51', '7', '0', 'Quartier des Bijoutiers', '1');
INSERT INTO `subarea_data` VALUES ('53', '11', '0', '//Br?kmar', '1');
INSERT INTO `subarea_data` VALUES ('54', '8', '0', 'Massif de Cania', '1');
INSERT INTO `subarea_data` VALUES ('55', '8', '0', 'P?nates du Corbac', '1');
INSERT INTO `subarea_data` VALUES ('56', '8', '0', 'For?t de Cania', '1');
INSERT INTO `subarea_data` VALUES ('57', '12', '0', '//Lande de Sidimote', '1');
INSERT INTO `subarea_data` VALUES ('59', '8', '0', 'Cimeti?re de Bonta', '1');
INSERT INTO `subarea_data` VALUES ('61', '12', '0', 'Cimeti?re des Tortur', '1');
INSERT INTO `subarea_data` VALUES ('62', '13', '0', '//Village Dopeul', '1');
INSERT INTO `subarea_data` VALUES ('63', '13', '0', 'Prisme des Dopeuls', '1');
INSERT INTO `subarea_data` VALUES ('64', '13', '0', 'Premi?re salle du prisme', '1');
INSERT INTO `subarea_data` VALUES ('65', '13', '0', 'Seconde salle du prisme', '1');
INSERT INTO `subarea_data` VALUES ('66', '13', '0', 'Troisi?me salle du prisme', '1');
INSERT INTO `subarea_data` VALUES ('67', '13', '0', 'Quatri?me salle du prisme', '1');
INSERT INTO `subarea_data` VALUES ('68', '8', '0', 'Les Champs de Cania', '1');
INSERT INTO `subarea_data` VALUES ('69', '8', '0', 'Bois de Litneg', '1');
INSERT INTO `subarea_data` VALUES ('70', '8', '0', 'Plaines Rocheuses', '1');
INSERT INTO `subarea_data` VALUES ('71', '12', '0', 'Gisgoul, le village devast', '1');
INSERT INTO `subarea_data` VALUES ('72', '12', '0', 'Fa?ade de Br?kmar', '1');
INSERT INTO `subarea_data` VALUES ('73', '7', '0', 'Egout de Bonta', '1');
INSERT INTO `subarea_data` VALUES ('74', '13', '0', '//Entrainement Dopeuls', '1');
INSERT INTO `subarea_data` VALUES ('75', '11', '0', 'Egout de Br?kmar', '1');
INSERT INTO `subarea_data` VALUES ('76', '14', '0', '//Village Brigandins', '1');
INSERT INTO `subarea_data` VALUES ('77', '0', '0', 'La gelaxiéme dimension', '1');
INSERT INTO `subarea_data` VALUES ('78', '0', '0', '//La gelaxiéme dimension (royale)', '1');
INSERT INTO `subarea_data` VALUES ('79', '14', '0', 'Premi?re plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('80', '14', '0', 'Seconde plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('81', '14', '0', 'Prisme Brigandin', '1');
INSERT INTO `subarea_data` VALUES ('82', '4', '0', 'Donjon des Bouftous', '0');
INSERT INTO `subarea_data` VALUES ('83', '17', '0', '//Tutorial', '0');
INSERT INTO `subarea_data` VALUES ('84', '15', '0', '//Foire du Trool', '1');
INSERT INTO `subarea_data` VALUES ('85', '16', '0', '//Jeu du Bouftou', '1');
INSERT INTO `subarea_data` VALUES ('86', '15', '0', 'Gladiatrool', '1');
INSERT INTO `subarea_data` VALUES ('87', '0', '0', '//Amakna Sud', '1');
INSERT INTO `subarea_data` VALUES ('88', '17', '0', '//Tainela', '0');
INSERT INTO `subarea_data` VALUES ('89', '0', '0', 'Tournoi Monde du Jeu', '1');
INSERT INTO `subarea_data` VALUES ('91', '0', '0', 'Souterrain mystérieux', '1');
INSERT INTO `subarea_data` VALUES ('92', '18', '0', 'Contour d\'Astrub', '0');
INSERT INTO `subarea_data` VALUES ('93', '2', '0', 'La plage de Moon', '1');
INSERT INTO `subarea_data` VALUES ('94', '12', '0', 'Donjon du Bworker', '1');
INSERT INTO `subarea_data` VALUES ('95', '18', '0', 'Cité d\'Astrub', '0');
INSERT INTO `subarea_data` VALUES ('96', '18', '0', 'Exploitation minière d\'Astrub', '0');
INSERT INTO `subarea_data` VALUES ('97', '18', '0', 'Forêt d\'Astrub', '0');
INSERT INTO `subarea_data` VALUES ('98', '18', '0', 'Champs d\'Astrub', '0');
INSERT INTO `subarea_data` VALUES ('99', '18', '0', 'Souterrain d\'Astrub', '0');
INSERT INTO `subarea_data` VALUES ('100', '18', '0', 'Souterrain profond d\'Astrub', '0');
INSERT INTO `subarea_data` VALUES ('101', '18', '0', 'Le coin des Tofus', '0');
INSERT INTO `subarea_data` VALUES ('102', '25', '0', 'Le champ du repos', '1');
INSERT INTO `subarea_data` VALUES ('103', '0', '0', 'Territoire des Bandits', '1');
INSERT INTO `subarea_data` VALUES ('105', '19', '0', '//Pandala Neutre', '1');
INSERT INTO `subarea_data` VALUES ('106', '20', '0', 'Bordure d\'Akwadala', '1');
INSERT INTO `subarea_data` VALUES ('107', '22', '0', 'Bordure de Feudala', '1');
INSERT INTO `subarea_data` VALUES ('108', '23', '0', 'Bordure d\'Aerdala', '1');
INSERT INTO `subarea_data` VALUES ('109', '21', '0', 'Bordure de Terrdala', '1');
INSERT INTO `subarea_data` VALUES ('110', '0', '0', '//Amakna pass', '1');
INSERT INTO `subarea_data` VALUES ('111', '23', '0', 'Porte Aerdala', '1');
INSERT INTO `subarea_data` VALUES ('112', '22', '0', 'Porte Feudala', '1');
INSERT INTO `subarea_data` VALUES ('113', '20', '0', 'Porte Akwadala', '1');
INSERT INTO `subarea_data` VALUES ('114', '21', '0', 'Porte Terrdala', '1');
INSERT INTO `subarea_data` VALUES ('115', '23', '0', 'Village d\'Aerdala', '1');
INSERT INTO `subarea_data` VALUES ('116', '22', '0', 'Village de Feudala', '1');
INSERT INTO `subarea_data` VALUES ('117', '20', '0', 'Village d\'Akwadala', '1');
INSERT INTO `subarea_data` VALUES ('118', '21', '0', 'Village de Terrdala', '1');
INSERT INTO `subarea_data` VALUES ('119', '19', '0', 'Village de Pandala', '1');
INSERT INTO `subarea_data` VALUES ('120', '23', '0', 'Prisme Aerdala', '1');
INSERT INTO `subarea_data` VALUES ('121', '22', '0', 'Prisme Feudala', '1');
INSERT INTO `subarea_data` VALUES ('122', '20', '0', 'Prisme Akwadala', '1');
INSERT INTO `subarea_data` VALUES ('123', '21', '0', 'Prisme Terrdala', '1');
INSERT INTO `subarea_data` VALUES ('124', '24', '0', 'Caverne des Bulbes', '1');
INSERT INTO `subarea_data` VALUES ('125', '24', '0', 'Repaire des Pandikazes - Premi?re plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('126', '24', '0', 'Repaire des Pandikazes - Seconde plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('127', '24', '0', 'Repaire des Pandikazes - Troisi?me plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('128', '24', '0', 'Repaire des Pandikazes - Quatri?me plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('129', '24', '0', 'Repaire des Pandikazes - Cinqui?me plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('130', '24', '0', 'Repaire des Pandikazes - Sixi?me plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('131', '24', '0', 'Repaire des Pandikazes - Septi?me plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('132', '24', '0', 'Repaire des Pandikazes - Huiti?me plate-forme', '1');
INSERT INTO `subarea_data` VALUES ('133', '24', '0', 'Donjon des Kitsounes - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('134', '24', '0', 'Donjon des Kitsounes - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('135', '24', '0', 'Donjon des Kitsounes - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('136', '24', '0', 'Donjon des Kitsounes - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('137', '24', '0', 'Donjon des Kitsounes - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('138', '24', '0', 'Donjon des Kitsounes - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('139', '24', '0', 'Donjon des Kitsounes - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('140', '24', '0', 'Donjon des Kitsounes - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('141', '24', '0', 'Donjon des Kitsounes - Repaire du Tanukou', '1');
INSERT INTO `subarea_data` VALUES ('143', '19', '0', 'Pont de Pandala', '1');
INSERT INTO `subarea_data` VALUES ('144', '24', '0', 'Donjon des Firefoux', '1');
INSERT INTO `subarea_data` VALUES ('145', '24', '0', 'Donjon des Firefoux - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('146', '24', '0', 'Donjon des Firefoux - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('147', '24', '0', 'Donjon des Firefoux - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('148', '24', '0', 'Donjon des Firefoux - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('149', '24', '0', 'Donjon des Firefoux - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('150', '24', '0', 'Donjon des Firefoux - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('151', '24', '0', 'Donjon des Firefoux - Salle 7', '1');
INSERT INTO `subarea_data` VALUES ('152', '19', '0', 'L\'?le de Grobe', '1');
INSERT INTO `subarea_data` VALUES ('153', '24', '0', 'Repaire des Pandikazes - Plate-forme finale', '1');
INSERT INTO `subarea_data` VALUES ('154', '43', '0', '//Donjon Cochon - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('155', '43', '0', '//Donjon Cochon - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('156', '43', '0', '//Donjon Cochon - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('157', '43', '0', '//Donjon Cochon - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('158', '43', '0', '//Donjon Cochon - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('159', '43', '0', '//Donjon Cochon - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('161', '1', '0', 'Ile des Wabbits', '1');
INSERT INTO `subarea_data` VALUES ('162', '1', '0', 'Ilot des Wabbits', '1');
INSERT INTO `subarea_data` VALUES ('163', '1', '0', 'Ile des Wabbits Squelettes', '1');
INSERT INTO `subarea_data` VALUES ('164', '1', '0', '?le du ch?teau des Wabbits', '1');
INSERT INTO `subarea_data` VALUES ('165', '2', '0', 'La jungle profonde de Moon', '1');
INSERT INTO `subarea_data` VALUES ('166', '2', '0', 'Le chemin vers Moon', '1');
INSERT INTO `subarea_data` VALUES ('167', '2', '0', 'Le bateau pirate', '1');
INSERT INTO `subarea_data` VALUES ('168', '6', '0', 'For?t des Abraknydes Sombres', '1');
INSERT INTO `subarea_data` VALUES ('169', '6', '0', 'Or?e de la for?t des Abraknydes', '1');
INSERT INTO `subarea_data` VALUES ('170', '0', '0', 'Plaine des Scarafeuilles', '1');
INSERT INTO `subarea_data` VALUES ('171', '19', '0', 'For?t de Pandala', '1');
INSERT INTO `subarea_data` VALUES ('173', '18', '0', 'Prairies d\'Astrub', '0');
INSERT INTO `subarea_data` VALUES ('174', '18', '0', 'Campement des Bandits Manchots', '0');
INSERT INTO `subarea_data` VALUES ('175', '27', '0', '//Donjon Abraknyde - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('177', '8', '0', 'Prairie des Blops', '1');
INSERT INTO `subarea_data` VALUES ('178', '8', '0', 'Plaine des Porkass', '1');
INSERT INTO `subarea_data` VALUES ('179', '0', '0', 'Le coin des Boos', '1');
INSERT INTO `subarea_data` VALUES ('180', '0', '0', 'Le château d\'Amakna', '1');
INSERT INTO `subarea_data` VALUES ('181', '0', '0', 'Souterrain du Château d\'Amakna', '1');
INSERT INTO `subarea_data` VALUES ('182', '28', '0', 'Village des Eleveurs', '1');
INSERT INTO `subarea_data` VALUES ('200', '26', '0', '//Labyrinthe du Dragon Cochon', '1');
INSERT INTO `subarea_data` VALUES ('201', '29', '0', '//Donjon des Tofus - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('202', '29', '0', '//Donjon des Tofus - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('203', '29', '0', '//Donjon des Tofus - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('204', '29', '0', '//Donjon des Tofus - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('205', '29', '0', '//Donjon des Tofus - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('206', '29', '0', '//Donjon des Tofus - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('207', '29', '0', '//Donjon des Tofus - Salle 7', '1');
INSERT INTO `subarea_data` VALUES ('208', '29', '0', '//Donjon des Tofus - Salle 8', '1');
INSERT INTO `subarea_data` VALUES ('209', '30', '0', 'L\'?le du Minotoror', '1');
INSERT INTO `subarea_data` VALUES ('210', '31', '0', 'Le labyrinthe du Minotoror', '1');
INSERT INTO `subarea_data` VALUES ('211', '32', '0', 'La biblioth?que du Ma?tre Corbac', '1');
INSERT INTO `subarea_data` VALUES ('212', '33', '0', '//Donjon des Canid?s - Entr', '1');
INSERT INTO `subarea_data` VALUES ('213', '33', '0', '//Donjon des Canid?s - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('214', '33', '0', '//Donjon des Canid?s - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('215', '33', '0', '//Donjon des Canid?s - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('216', '33', '0', '//Donjon des Canid?s - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('217', '33', '0', '//Donjon des Canid?s - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('218', '33', '0', '//Donjon des Canid?s - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('219', '33', '0', '//Donjon des Canid?s - Salle 7', '1');
INSERT INTO `subarea_data` VALUES ('220', '33', '0', '//Donjon des Canid?s - Salle 8', '1');
INSERT INTO `subarea_data` VALUES ('221', '33', '0', '//Donjon des Canid?s - Salle 9', '1');
INSERT INTO `subarea_data` VALUES ('222', '33', '0', '//Donjon des Canid?s - Salle 10', '1');
INSERT INTO `subarea_data` VALUES ('223', '34', '0', '//Caverne du Koulosse - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('224', '34', '0', '//Caverne du Koulosse - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('225', '34', '0', '//Caverne du Koulosse - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('226', '34', '0', '//Caverne du Koulosse - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('227', '34', '0', '//Caverne du Koulosse - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('228', '34', '0', '//Caverne du Koulosse - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('229', '34', '0', '//Caverne du Koulosse - L\'antre', '1');
INSERT INTO `subarea_data` VALUES ('230', '28', '0', 'Cimeti?re primitif', '1');
INSERT INTO `subarea_data` VALUES ('231', '28', '0', 'Lacs enchant', '1');
INSERT INTO `subarea_data` VALUES ('232', '28', '0', 'Mar?cages naus?abonds', '1');
INSERT INTO `subarea_data` VALUES ('233', '28', '0', 'Mar?cages sans fond', '1');
INSERT INTO `subarea_data` VALUES ('234', '28', '0', 'For?t de Kaliptus', '1');
INSERT INTO `subarea_data` VALUES ('235', '28', '0', 'Territoire des Dragodindes Sauvages', '1');
INSERT INTO `subarea_data` VALUES ('236', '36', '0', 'Sanctuaire des Familiers', '1');
INSERT INTO `subarea_data` VALUES ('243', '37', '0', '//Donjon des Craqueleurs - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('244', '37', '0', '//Donjon des Craqueleurs - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('245', '37', '0', '//Donjon des Craqueleurs - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('246', '37', '0', '//Donjon des Craqueleurs - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('247', '37', '0', '//Donjon des Craqueleurs - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('248', '37', '0', '//Donjon des Craqueleurs - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('249', '37', '0', '//Donjon des Craqueleurs - Salle 7', '1');
INSERT INTO `subarea_data` VALUES ('250', '37', '0', '//Donjon des Craqueleurs - Salle 8', '1');
INSERT INTO `subarea_data` VALUES ('251', '37', '0', '//Donjon des Craqueleurs - Salle 10', '1');
INSERT INTO `subarea_data` VALUES ('252', '37', '0', '//Donjon des Craqueleurs - Salle 11', '1');
INSERT INTO `subarea_data` VALUES ('253', '28', '0', 'Canyon sauvage', '1');
INSERT INTO `subarea_data` VALUES ('254', '35', '0', '//Repaire de Skeunk - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('255', '35', '0', '//Repaire de Skeunk - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('256', '35', '0', '//Repaire de Skeunk - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('257', '35', '0', '//Repaire de Skeunk - Emeraude', '1');
INSERT INTO `subarea_data` VALUES ('258', '35', '0', '//Repaire de Skeunk - Rubise', '1');
INSERT INTO `subarea_data` VALUES ('259', '35', '0', '//Repaire de Skeunk - Saphira', '1');
INSERT INTO `subarea_data` VALUES ('260', '35', '0', '//Repaire de Skeunk - Diamantine', '1');
INSERT INTO `subarea_data` VALUES ('261', '35', '0', '//Repaire de Skeunk - Antre', '1');
INSERT INTO `subarea_data` VALUES ('266', '35', '0', '//Repaire de Skeunk - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('267', '35', '0', '//Repaire de Skeunk - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('268', '29', '0', '//Donjon des Tofus - Salle 9', '1');
INSERT INTO `subarea_data` VALUES ('269', '29', '0', '//Donjon des Tofus - Salle 10', '1');
INSERT INTO `subarea_data` VALUES ('270', '29', '0', '//Donjon des Tofus - Salle 11', '1');
INSERT INTO `subarea_data` VALUES ('271', '29', '0', '//Donjon des Tofus - Salle 12', '1');
INSERT INTO `subarea_data` VALUES ('272', '29', '0', '//Donjon des Tofus - Salle 13', '1');
INSERT INTO `subarea_data` VALUES ('273', '29', '0', '//Donjon des Tofus - Salle 14', '1');
INSERT INTO `subarea_data` VALUES ('274', '29', '0', '//Donjon des Tofus - Salle 15', '1');
INSERT INTO `subarea_data` VALUES ('275', '28', '0', 'Vall?e de la Morh\'Kitu', '1');
INSERT INTO `subarea_data` VALUES ('276', '0', '0', 'Campement des Gobelins', '1');
INSERT INTO `subarea_data` VALUES ('277', '0', '0', 'Village des Bworks', '1');
INSERT INTO `subarea_data` VALUES ('278', '18', '0', 'Elevage de Bouftous du Château d\'Amakna', '0');
INSERT INTO `subarea_data` VALUES ('279', '7', '0', 'Bordure de Bonta', '1');
INSERT INTO `subarea_data` VALUES ('280', '11', '0', 'Bordure de Br?kmar', '1');
INSERT INTO `subarea_data` VALUES ('284', '39', '0', '//Donjon des Bworks - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('285', '39', '0', '//Donjon des Bworks - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('286', '39', '0', '//Donjon des Bworks - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('287', '39', '0', '//Donjon des Bworks - Cachot', '1');
INSERT INTO `subarea_data` VALUES ('288', '39', '0', '//Donjon des Bworks - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('289', '39', '0', '//Donjon des Bworks - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('290', '39', '0', '//Donjon des Bworks - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('291', '39', '0', '//Donjon des Bworks - Salle 7', '1');
INSERT INTO `subarea_data` VALUES ('292', '33', '0', '//Donjon des Canid?s - Salle 11', '1');
INSERT INTO `subarea_data` VALUES ('293', '33', '0', '//Donjon des Canid?s - Salle 12', '1');
INSERT INTO `subarea_data` VALUES ('294', '33', '0', '//Donjon des Canid?s - Salle 13', '1');
INSERT INTO `subarea_data` VALUES ('295', '33', '0', '//Donjon des Canid?s - Salle 14', '1');
INSERT INTO `subarea_data` VALUES ('296', '33', '0', '//Donjon des Canid?s - Salle 15', '1');
INSERT INTO `subarea_data` VALUES ('297', '40', '0', '//Donjon des Scarafeuilles - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('298', '40', '0', '//Donjon des Scarafeuilles - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('299', '40', '0', '//Donjon des Scarafeuilles - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('300', '40', '0', '//Donjon des Scarafeuilles - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('301', '40', '0', '//Donjon des Scarafeuilles - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('302', '40', '0', '//Donjon des Scarafeuilles - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('303', '40', '0', '//Donjon des Scarafeuilles - Salle 7', '1');
INSERT INTO `subarea_data` VALUES ('304', '40', '0', '//Donjon des Scarafeuilles - Salle 8', '1');
INSERT INTO `subarea_data` VALUES ('306', '41', '0', '//Donjon des Champs - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('307', '41', '0', '//Donjon des Champs - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('308', '41', '0', '//Donjon des Champs - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('309', '41', '0', '//Donjon des Champs - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('310', '41', '0', '//Donjon des Champs - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('311', '41', '0', '//Donjon des Champs - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('312', '41', '0', '//Donjon des Champs - Salle 7', '1');
INSERT INTO `subarea_data` VALUES ('313', '29', '0', '//Donjon des Tofus - Salle 16', '1');
INSERT INTO `subarea_data` VALUES ('314', '0', '0', 'Sanctuaire des Dragoeufs', '1');
INSERT INTO `subarea_data` VALUES ('315', '0', '0', 'Village des Dragoeufs', '1');
INSERT INTO `subarea_data` VALUES ('316', '0', '0', 'Sous terrain des Dragoeufs', '1');
INSERT INTO `subarea_data` VALUES ('317', '37', '0', '//Donjon des Craqueleurs - Salle 9', '1');
INSERT INTO `subarea_data` VALUES ('318', '18', '0', 'Alkatraz', '0');
INSERT INTO `subarea_data` VALUES ('319', '31', '0', 'Le labyrinthe du Minotoror', '1');
INSERT INTO `subarea_data` VALUES ('320', '42', '0', 'L\'?le de Nowel', '1');
INSERT INTO `subarea_data` VALUES ('321', '42', '0', 'Le donjon de Nowel', '1');
INSERT INTO `subarea_data` VALUES ('322', '43', '0', '//Donjon Cochon - Salle 7', '1');
INSERT INTO `subarea_data` VALUES ('323', '43', '0', '//Donjon Cochon - Salle 8', '1');
INSERT INTO `subarea_data` VALUES ('324', '43', '0', '//Donjon Cochon - Salle 9', '1');
INSERT INTO `subarea_data` VALUES ('325', '44', '0', '//Donjon Dragoeuf - Salle 1', '1');
INSERT INTO `subarea_data` VALUES ('326', '44', '0', '//Donjon Dragoeuf - Salle 2', '1');
INSERT INTO `subarea_data` VALUES ('327', '44', '0', '//Donjon Dragoeuf - Salle 3', '1');
INSERT INTO `subarea_data` VALUES ('328', '44', '0', '//Donjon Dragoeuf - Salle 4', '1');
INSERT INTO `subarea_data` VALUES ('329', '44', '0', '//Donjon Dragoeuf - Salle 5', '1');
INSERT INTO `subarea_data` VALUES ('330', '44', '0', '//Donjon Dragoeuf - Salle 6', '1');
INSERT INTO `subarea_data` VALUES ('331', '44', '0', '//Donjon Dragoeuf - Salle 7', '1');
INSERT INTO `subarea_data` VALUES ('332', '44', '0', '//Donjon Dragoeuf - Salle 8', '1');
INSERT INTO `subarea_data` VALUES ('333', '44', '0', '//Donjon Dragoeuf - Salle 9', '1');
INSERT INTO `subarea_data` VALUES ('334', '8', '0', 'Baie de Cania', '1');
INSERT INTO `subarea_data` VALUES ('335', '18', '0', 'Calanques d\'Astrub', '0');
INSERT INTO `subarea_data` VALUES ('336', '18', '0', 'Donjon Ensabl', '0');
INSERT INTO `subarea_data` VALUES ('337', '7', '0', 'Donjon des Rats de Bonta', '1');
INSERT INTO `subarea_data` VALUES ('338', '11', '0', 'Donjon des Rats de Br?kmar', '1');
INSERT INTO `subarea_data` VALUES ('339', '0', '0', 'Donjon des Rats du Château d\'Amakna', '1');
INSERT INTO `subarea_data` VALUES ('440', '45', '0', 'Pitons rocheux', '0');
INSERT INTO `subarea_data` VALUES ('441', '45', '0', 'Clairi?re', '0');
INSERT INTO `subarea_data` VALUES ('442', '45', '0', 'Lac', '0');
INSERT INTO `subarea_data` VALUES ('443', '45', '0', 'For', '0');
INSERT INTO `subarea_data` VALUES ('444', '45', '0', 'Champs', '0');
INSERT INTO `subarea_data` VALUES ('445', '45', '0', 'Prairie', '0');
INSERT INTO `subarea_data` VALUES ('446', '45', '0', 'Temple', '0');
INSERT INTO `subarea_data` VALUES ('447', '45', '0', 'Donjon', '0');
INSERT INTO `subarea_data` VALUES ('448', '45', '0', '//Divers', '0');
INSERT INTO `subarea_data` VALUES ('449', '45', '0', 'Cimeti?re', '0');
INSERT INTO `subarea_data` VALUES ('450', '45', '0', '//Sortie du temple', '0');
INSERT INTO `subarea_data` VALUES ('451', '46', '0', 'Ile des naufrag', '1');
INSERT INTO `subarea_data` VALUES ('452', '46', '0', 'Mer', '1');
INSERT INTO `subarea_data` VALUES ('453', '46', '0', 'Plage de Corail', '1');
INSERT INTO `subarea_data` VALUES ('454', '46', '0', 'Plaines herbeuses', '1');
INSERT INTO `subarea_data` VALUES ('455', '46', '0', 'Jungle obscure', '1');
INSERT INTO `subarea_data` VALUES ('457', '46', '0', 'Tourbi?re sans fond', '1');
INSERT INTO `subarea_data` VALUES ('459', '46', '0', 'Canop?e du Kimbo', '1');
INSERT INTO `subarea_data` VALUES ('460', '46', '0', 'Grotte Hesque', '1');
INSERT INTO `subarea_data` VALUES ('461', '46', '0', 'L\'arche d\'Otoma', '1');
INSERT INTO `subarea_data` VALUES ('462', '46', '0', 'La clairi?re de Floribonde', '1');
INSERT INTO `subarea_data` VALUES ('463', '46', '0', 'Le laboratoire du Tynril', '1');
INSERT INTO `subarea_data` VALUES ('464', '46', '0', 'Tronc de l\'arbre Hakam', '1');
INSERT INTO `subarea_data` VALUES ('465', '46', '0', 'Le village des ?leveurs', '1');
INSERT INTO `subarea_data` VALUES ('466', '46', '0', 'Le village c?tier', '1');
INSERT INTO `subarea_data` VALUES ('467', '46', '0', 'Cimeti?re de l\'?le d\'Otoma', '1');
INSERT INTO `subarea_data` VALUES ('468', '47', '0', '//Village des Zoths', '1');
INSERT INTO `subarea_data` VALUES ('469', '46', '0', 'Village de la Canop', '1');
INSERT INTO `subarea_data` VALUES ('470', '46', '0', 'Goulet du Rasboul', '1');
INSERT INTO `subarea_data` VALUES ('471', '46', '0', 'Tourbi?re naus?abonde', '1');
INSERT INTO `subarea_data` VALUES ('472', '46', '0', 'Feuillage de L\'arbre Hakam', '1');
INSERT INTO `subarea_data` VALUES ('473', '46', '0', 'Le laboratoire cach', '1');
INSERT INTO `subarea_data` VALUES ('474', '46', '0', 'Cale de l\'arche d\'Otoma', '1');
INSERT INTO `subarea_data` VALUES ('476', '19', '0', 'Pont de Grobe', '1');
INSERT INTO `subarea_data` VALUES ('477', '47', '0', 'Prisme Zothier', '1');
INSERT INTO `subarea_data` VALUES ('478', '47', '0', '//Gardiens de la porte des Zoths', '1');
INSERT INTO `subarea_data` VALUES ('479', '0', '0', 'Rivi?re Kawaii', '1');
INSERT INTO `subarea_data` VALUES ('480', '0', '0', 'Montagne basse des Craqueleurs', '1');
INSERT INTO `subarea_data` VALUES ('481', '0', '0', 'Clairi?re de Brouce Boulgour', '1');
INSERT INTO `subarea_data` VALUES ('482', '0', '0', 'La Millifutaie ', '1');
INSERT INTO `subarea_data` VALUES ('483', '0', '0', 'Le chemin de fer abandonn', '1');
INSERT INTO `subarea_data` VALUES ('484', '0', '0', 'Orée de la Millifutaie', '1');
INSERT INTO `subarea_data` VALUES ('485', '0', '0', 'La campagne', '1');
INSERT INTO `subarea_data` VALUES ('486', '0', '0', 'Le Bosquet du petit talus', '1');
INSERT INTO `subarea_data` VALUES ('487', '19', '0', 'Grenier-Cachot de Pandala Air', '1');
INSERT INTO `subarea_data` VALUES ('488', '0', '0', 'Souterrains d\'Amakna', '1');
INSERT INTO `subarea_data` VALUES ('490', '0', '0', 'Rivage du golfe sufokien', '1');
INSERT INTO `subarea_data` VALUES ('491', '0', '0', 'Donjon des Larves', '1');
INSERT INTO `subarea_data` VALUES ('492', '0', '0', 'Passage vers Brakmar', '1');
INSERT INTO `subarea_data` VALUES ('493', '8', '0', 'Donjon des Blops', '1');
INSERT INTO `subarea_data` VALUES ('494', '12', '0', 'Donjon Fungus', '1');
INSERT INTO `subarea_data` VALUES ('495', '12', '0', 'Caverne des Fungus', '1');
INSERT INTO `subarea_data` VALUES ('496', '12', '0', 'Donjon de Ku\'tan', '1');
INSERT INTO `subarea_data` VALUES ('497', '8', '0', 'Donjon d\'Ilyzaelle', '1');
INSERT INTO `subarea_data` VALUES ('498', '8', '0', 'Sanctuaire Hotomani', '1');
INSERT INTO `subarea_data` VALUES ('499', '30', '0', 'Cimetière de l\'Île du Minotoror', '1');
INSERT INTO `subarea_data` VALUES ('500', '47', '0', 'Cimetière du village des Zoths.', '1');
INSERT INTO `subarea_data` VALUES ('501', '0', '0', 'L\'îlot Estitch', '1');
INSERT INTO `subarea_data` VALUES ('502', '11', '0', 'Quartier des bûcherons', '1');
INSERT INTO `subarea_data` VALUES ('503', '11', '0', 'Quartier des bouchers', '1');
INSERT INTO `subarea_data` VALUES ('504', '11', '0', 'Quartier de la milice', '1');
INSERT INTO `subarea_data` VALUES ('505', '11', '0', 'Quartier des boulangers', '1');
INSERT INTO `subarea_data` VALUES ('506', '11', '0', 'Quartier des bijoutiers', '1');
INSERT INTO `subarea_data` VALUES ('507', '11', '0', 'Quartier des tailleurs', '1');
INSERT INTO `subarea_data` VALUES ('508', '11', '0', 'Quartier des forgerons', '1');
INSERT INTO `subarea_data` VALUES ('509', '11', '0', 'Quartier des bricoleurs', '1');
INSERT INTO `subarea_data` VALUES ('510', '11', '0', 'Arène', '1');
INSERT INTO `subarea_data` VALUES ('511', '11', '0', 'Centre-ville', '1');
INSERT INTO `subarea_data` VALUES ('512', '7', '0', 'Arène', '1');
INSERT INTO `subarea_data` VALUES ('513', '7', '0', 'Centre-ville', '1');
INSERT INTO `subarea_data` VALUES ('514', '7', '0', 'Tour des ordres', '1');
INSERT INTO `subarea_data` VALUES ('515', '11', '0', 'Tour des ordres', '1');
INSERT INTO `subarea_data` VALUES ('536', '0', '0', 'Goultarminator', '1');

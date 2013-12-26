CREATE TABLE IF NOT EXISTS `gift` (
  `giftId` int(11) NOT NULL,
  `items` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `pictureUrl` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `gift`
--

INSERT INTO `gift` (`giftId`, `items`, `title`, `description`, `pictureUrl`) VALUES
(1, '2409,1;5355,1', 'TITRE', 'DESCRIP', 'http://s2.e-monsite.com/2009/12/26/04/167wpr7.png');

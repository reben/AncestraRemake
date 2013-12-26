--
-- Structure de la table `challenge`
--

CREATE TABLE IF NOT EXISTS `challenge` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL DEFAULT '',
  `gainXp` int(11) NOT NULL DEFAULT '0',
  `gainDrop` int(11) NOT NULL DEFAULT '0',
  `gainParMob` int(11) NOT NULL DEFAULT '5',
  `conditions` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `challenge`
--

INSERT INTO `challenge` (`id`, `nom`, `gainXp`, `gainDrop`, `gainParMob`, `conditions`) VALUES
(1, 'Zombie', 40, 40, 2, 0),
(2, 'Statue', 35, 35, 2, 0),
(3, 'Désigné Volontaire', 20, 20, 4, 1),
(4, 'Sursis', 10, 10, 5, 1),
(5, 'Econome', 150, 150, 10, 0),
(6, 'Versatile', 75, 75, 5, 0),
(7, 'Jardinier', 15, 15, 1, 0),
(8, 'Nomade', 25, 25, 2, 0),
(9, 'Barbare', 60, 60, 3, 0),
(10, 'Cruel', 35, 35, 10, 1),
(11, 'Mystique', 60, 60, 5, 0),
(12, 'Fossoyeur', 10, 10, 0, 0),
(14, 'Casino Royal', 15, 15, 1, 0),
(15, 'Araknophile', 15, 15, 1, 0),
(17, 'Intouchable', 40, 40, 5, 0),
(18, 'Incurable', 25, 25, 5, 0),
(19, 'Mains Propres', 105, 105, 5, 0),
(20, 'Elémentaire', 55, 55, 5, 0),
(21, 'Circulez !', 20, 20, 5, 0),
(22, 'Le temps qui court', 20, 20, 5, 0),
(23, 'Perdu de Vue', 20, 20, 5, 0),
(24, 'Borné', 70, 70, 5, 0),
(25, 'Ordonné', 10, 10, 10, 1),
(28, 'Ni pioutes ni soumises', 35, 35, 2, 4),
(29, 'Ni pious ni soumis', 35, 35, 2, 4),
(30, 'Les petits d''abord', 40, 40, 2, 2),
(31, 'Focus', 20, 20, 5, 1),
(32, 'Elitiste', 40, 40, 2, 1),
(33, 'Survivant', 15, 15, 2, 2),
(34, 'Imprévisible', 40, 40, 5, 1),
(35, 'Tueur à gages', 10, 10, 10, 1),
(36, 'Hardi', 30, 30, 3, 0),
(37, 'Collant', 30, 30, 3, 2),
(38, 'Blitzkrieg', 70, 70, 5, 0),
(39, 'Anachorète', 30, 30, 3, 2),
(40, 'Pusillanime', 20, 20, 5, 0),
(41, 'Pétulant', 15, 15, 3, 0),
(42, 'Deux pour le prix d''un', 50, 50, 3, 8),
(43, 'Abnégation', 25, 25, 5, 0),
(44, 'Partage', 30, 30, 3, 16),
(45, 'Duel', 35, 35, 5, 2),
(46, 'Chacun son monstre', 65, 65, 10, 16),
(47, 'Contamination', 90, 90, 8, 0),
(48, 'Les mules d''abord', 95, 95, 5, 2),
(49, 'Protégez vos mules', 65, 65, 3, 2),
(50, 'Le cheat des devs', 1, 1, 0, 0);
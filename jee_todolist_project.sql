-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 17 jan. 2025 à 22:16
-- Version du serveur : 8.2.0
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `jee_todolist_project`
--

-- --------------------------------------------------------

--
-- Structure de la table `member`
--

DROP TABLE IF EXISTS `member`;
CREATE TABLE IF NOT EXISTS `member` (
  `id` binary(16) NOT NULL,
  `added_at` datetime(6) DEFAULT NULL,
  `privileges` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `role` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `list_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa0sq0tw50xi42gvnfokrc1nt1` (`list_id`),
  KEY `FKswb523yn1xw3806ojrfpcyadl` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Déchargement des données de la table `member`
--

INSERT INTO `member` (`id`, `added_at`, `privileges`, `role`, `list_id`, `user_id`) VALUES
(0x17ba8b7e1c914430a06d92decbbfdff9, '2025-01-17 23:00:40.419150', 'ALL', 'MEMBER_ADMIN', 0x37e62c760b444989bd9e1dadf1f6e05b, 0x8721167bed8f466682fe9753d4a16f1f),
(0x93491f0b481240228917a19960e1bb19, '2025-01-17 22:58:31.919002', 'ALL', 'MEMBER_ADMIN', 0xc79c063fdb2844ed9141be63c79a9ba8, 0x8721167bed8f466682fe9753d4a16f1f);

-- --------------------------------------------------------

--
-- Structure de la table `todo_list`
--

DROP TABLE IF EXISTS `todo_list`;
CREATE TABLE IF NOT EXISTS `todo_list` (
  `id` binary(16) NOT NULL,
  `category` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `title` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `owner_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4hmx7dam76e0m25is59s6p8g5` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Déchargement des données de la table `todo_list`
--

INSERT INTO `todo_list` (`id`, `category`, `created_at`, `description`, `title`, `updated_at`, `owner_id`) VALUES
(0x37e62c760b444989bd9e1dadf1f6e05b, 'groceries', '2025-01-17 23:00:40.418656', 'my personal todolist', 'saadTodoList', '2025-01-17 23:00:40.418656', 0x8721167bed8f466682fe9753d4a16f1f),
(0xc79c063fdb2844ed9141be63c79a9ba8, 'groceries', '2025-01-17 22:58:31.919002', 'my personal todolist', 'saadTodoList', '2025-01-17 22:58:31.919002', 0x8721167bed8f466682fe9753d4a16f1f);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` binary(16) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `email` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `gender` tinyint DEFAULT NULL,
  `password` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `profile_picture` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `user_name` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `birth_date`, `email`, `gender`, `password`, `profile_picture`, `user_name`) VALUES
(0x8721167bed8f466682fe9753d4a16f1f, '2000-04-03', 'saadboukili66@gmail.com', 0, '$2a$10$VBRRd85tBSlTMBTaMNpn4O6RHW4buabIqX81WVLyTBxUcBWQGPV3S', 'saad_1736860307355.jpg', 'Boukili Saad');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `member`
--
ALTER TABLE `member`
  ADD CONSTRAINT `FKa0sq0tw50xi42gvnfokrc1nt1` FOREIGN KEY (`list_id`) REFERENCES `todo_list` (`id`),
  ADD CONSTRAINT `FKswb523yn1xw3806ojrfpcyadl` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `todo_list`
--
ALTER TABLE `todo_list`
  ADD CONSTRAINT `FK4hmx7dam76e0m25is59s6p8g5` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

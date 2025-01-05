-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 05 jan. 2025 à 16:37
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
(0x69cefd20d8b64171880c4895bff6fb70, '1996-04-03', 'yassmineznatn@gmail.com', 1, '$2a$10$1xJ446VYMxyUD8/3Exqibu6OI/HDoLjr4gmizUJZNnHGQQs2X/auG', 'path/c/example', 'yassmine znatn'),
(0x73e32f019820475f893f443500b3dd8e, '2000-04-03', 'saadboukili@gmail.com', 0, '$2a$10$httS7V4pT208crOwuXUlf.I1MoTeFQ2xOIaTbBSSQ59.k3hmTWGiO', 'path/c/example', 'saad boukili');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

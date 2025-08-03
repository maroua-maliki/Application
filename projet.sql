-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 03 août 2025 à 13:51
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projet`
--

-- --------------------------------------------------------

--
-- Structure de la table `fichier_produit`
--

CREATE TABLE `fichier_produit` (
  `id` int(11) NOT NULL,
  `nom_fichier` varchar(255) NOT NULL,
  `type_fichier` varchar(50) NOT NULL,
  `path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `pdf_extrait`
--

CREATE TABLE `pdf_extrait` (
  `id` int(11) NOT NULL,
  `contenu` longtext DEFAULT NULL,
  `id_fichier` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `produit_excel`
--

CREATE TABLE `produit_excel` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `decoupage` varchar(255) NOT NULL,
  `prix_unitaire_proto` decimal(10,2) DEFAULT NULL,
  `prix_unitaire_serie` decimal(10,2) DEFAULT NULL,
  `id_fichier` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `produit_pdf`
--

CREATE TABLE `produit_pdf` (
  `id` int(11) NOT NULL,
  `ref` varchar(255) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `fichier_id` int(11) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `fichier_produit`
--
ALTER TABLE `fichier_produit`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `pdf_extrait`
--
ALTER TABLE `pdf_extrait`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_fichier` (`id_fichier`);

--
-- Index pour la table `produit_excel`
--
ALTER TABLE `produit_excel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_fichier` (`id_fichier`);

--
-- Index pour la table `produit_pdf`
--
ALTER TABLE `produit_pdf`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fichier_id` (`fichier_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `fichier_produit`
--
ALTER TABLE `fichier_produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=946;

--
-- AUTO_INCREMENT pour la table `pdf_extrait`
--
ALTER TABLE `pdf_extrait`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=861;

--
-- AUTO_INCREMENT pour la table `produit_excel`
--
ALTER TABLE `produit_excel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1891;

--
-- AUTO_INCREMENT pour la table `produit_pdf`
--
ALTER TABLE `produit_pdf`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `pdf_extrait`
--
ALTER TABLE `pdf_extrait`
  ADD CONSTRAINT `pdf_extrait_ibfk_1` FOREIGN KEY (`id_fichier`) REFERENCES `fichier_produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `produit_excel`
--
ALTER TABLE `produit_excel`
  ADD CONSTRAINT `produit_excel_ibfk_1` FOREIGN KEY (`id_fichier`) REFERENCES `fichier_produit` (`id`);

--
-- Contraintes pour la table `produit_pdf`
--
ALTER TABLE `produit_pdf`
  ADD CONSTRAINT `produit_pdf_ibfk_1` FOREIGN KEY (`fichier_id`) REFERENCES `fichier_produit` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

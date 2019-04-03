-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Počítač: 127.0.0.1
-- Vytvořeno: Úte 02. dub 2019, 11:15
-- Verze serveru: 5.6.24
-- Verze PHP: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databáze: `slovnik`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `slovnicek`
--

CREATE TABLE IF NOT EXISTS `slovnicek` (
  `id` int(11) NOT NULL,
  `cs` varchar(50) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL,
  `en` varchar(50) CHARACTER SET utf8 COLLATE utf8_czech_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

--
-- Vypisuji data pro tabulku `slovnicek`
--

INSERT INTO `slovnicek` (`id`, `cs`, `en`) VALUES
(4, 'plamen', 'flame'),
(6, 'oheň', 'fire'),
(7, 'slovo', 'word'),
(9, 'stůl', 'table'),
(10, 'kniha', 'book'),
(11, 'ulice', 'street'),
(12, 'přidat', 'add'),
(13, 'vymazat', 'delete'),
(14, 'hledat', 'find'),
(17, 'řidič', 'driver'),
(20, 'kukuřice', 'corn'),
(22, 'kukuřice', 'corn'),
(25, 'kukuřice', 'corn'),
(26, 'kukuřice', 'corn'),
(27, 'kukuřice', 'corn'),
(28, 'kukuřice', 'corn'),
(29, 'kukuřice', 'corn'),
(30, 'kukuřice', 'corn'),
(31, 'kukuřice', 'corn'),
(32, 'kukuřice', 'corn'),
(33, 'kukuřice', 'corn'),
(34, 'kukuřice', 'corn'),
(35, 'řidič', 'driver');

--
-- Klíče pro exportované tabulky
--

--
-- Klíče pro tabulku `slovnicek`
--
ALTER TABLE `slovnicek`
  ADD PRIMARY KEY (`id`), ADD KEY `slovnicek_cs_idx` (`cs`), ADD KEY `slovnicek_en_idx` (`en`);

--
-- AUTO_INCREMENT pro tabulky
--

--
-- AUTO_INCREMENT pro tabulku `slovnicek`
--
ALTER TABLE `slovnicek`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=36;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 10, 2024 at 12:42 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `exeter's public library`
--

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

CREATE TABLE `author` (
  `AuthorId` varchar(10) NOT NULL,
  `Name` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `BRN` varchar(10) NOT NULL,
  `ItemId` varchar(10) DEFAULT NULL,
  `AuthorId` varchar(10) DEFAULT NULL,
  `PublisherId` varchar(10) DEFAULT NULL,
  `Genre` tinytext DEFAULT NULL,
  `ISBN` varchar(13) DEFAULT NULL,
  `Year` year(4) DEFAULT NULL,
  `Language` tinytext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bookgenre`
--

CREATE TABLE `bookgenre` (
  `GenId` varchar(10) NOT NULL,
  `GenreName` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `BorrowID` int(11) NOT NULL,
  `ItemID` int(11) DEFAULT NULL,
  `MemberID` int(11) DEFAULT NULL,
  `BorrowDate` date DEFAULT NULL,
  `DueDate` date DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `director`
--

CREATE TABLE `director` (
  `DirectorId` varchar(10) NOT NULL,
  `Name` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `dvd`
--

CREATE TABLE `dvd` (
  `DRN` varchar(10) NOT NULL,
  `ItemId` varchar(10) DEFAULT NULL,
  `DirectorId` varchar(10) DEFAULT NULL,
  `PublisherId` varchar(10) DEFAULT NULL,
  `Genre` tinytext DEFAULT NULL,
  `NumOfDVD` tinyint(2) DEFAULT NULL,
  `Year` year(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `dvdgenre`
--

CREATE TABLE `dvdgenre` (
  `GenId` varchar(10) NOT NULL,
  `GenreName` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `dvdpublisher`
--

CREATE TABLE `dvdpublisher` (
  `DPublisherId` varchar(10) NOT NULL,
  `Name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `fine`
--

CREATE TABLE `fine` (
  `FineID` varchar(10) NOT NULL,
  `Amount` decimal(10,2) DEFAULT NULL,
  `FineDate` date DEFAULT NULL,
  `MemberID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `ItemId` varchar(10) NOT NULL,
  `Title` tinytext NOT NULL,
  `TypeId` varchar(10) DEFAULT NULL,
  `ShelfID` varchar(10) DEFAULT NULL,
  `Availability` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `MId` varchar(10) NOT NULL,
  `Name` tinytext NOT NULL,
  `Tel` varchar(10) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `NIC` varchar(12) NOT NULL,
  `Age` int(11) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `publisher`
--

CREATE TABLE `publisher` (
  `PublisherId` varchar(10) NOT NULL,
  `Name` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `returntable`
--

CREATE TABLE `returntable` (
  `ReturnID` varchar(10) NOT NULL,
  `ItemID` varchar(10) DEFAULT NULL,
  `MemberID` varchar(10) DEFAULT NULL,
  `ReturnDate` date DEFAULT NULL,
  `FineID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `shelf`
--

CREATE TABLE `shelf` (
  `ShelfID` varchar(10) NOT NULL,
  `TypeId` varchar(10) DEFAULT NULL,
  `Genre` tinytext DEFAULT NULL,
  `ShelfName` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `SId` varchar(10) NOT NULL,
  `Name` tinytext NOT NULL,
  `NIC` varchar(12) NOT NULL,
  `Tel` varchar(10) NOT NULL,
  `Email` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE `type` (
  `TypeId` varchar(10) NOT NULL,
  `TypeName` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`AuthorId`);
ALTER TABLE `author` ADD FULLTEXT KEY `AuthorId` (`AuthorId`,`Name`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`BRN`),
  ADD UNIQUE KEY `ItemId` (`ItemId`),
  ADD KEY `AuthorId` (`AuthorId`),
  ADD KEY `PublisherId` (`PublisherId`),
  ADD KEY `Genre` (`Genre`(255));

--
-- Indexes for table `bookgenre`
--
ALTER TABLE `bookgenre`
  ADD PRIMARY KEY (`GenId`);

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`BorrowID`),
  ADD KEY `ItemID` (`ItemID`),
  ADD KEY `MemberID` (`MemberID`);

--
-- Indexes for table `director`
--
ALTER TABLE `director`
  ADD PRIMARY KEY (`DirectorId`);

--
-- Indexes for table `dvd`
--
ALTER TABLE `dvd`
  ADD PRIMARY KEY (`DRN`),
  ADD UNIQUE KEY `ItemId` (`ItemId`),
  ADD KEY `DirectorId` (`DirectorId`),
  ADD KEY `PublisherId` (`PublisherId`),
  ADD KEY `Genre` (`Genre`(255));

--
-- Indexes for table `dvdgenre`
--
ALTER TABLE `dvdgenre`
  ADD PRIMARY KEY (`GenId`);

--
-- Indexes for table `dvdpublisher`
--
ALTER TABLE `dvdpublisher`
  ADD PRIMARY KEY (`DPublisherId`);

--
-- Indexes for table `fine`
--
ALTER TABLE `fine`
  ADD PRIMARY KEY (`FineID`),
  ADD KEY `MemberID` (`MemberID`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`ItemId`),
  ADD KEY `TypeId` (`TypeId`),
  ADD KEY `ShelfID` (`ShelfID`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`MId`),
  ADD UNIQUE KEY `NIC` (`NIC`);

--
-- Indexes for table `publisher`
--
ALTER TABLE `publisher`
  ADD PRIMARY KEY (`PublisherId`);

--
-- Indexes for table `returntable`
--
ALTER TABLE `returntable`
  ADD PRIMARY KEY (`ReturnID`),
  ADD KEY `ItemID` (`ItemID`),
  ADD KEY `MemberID` (`MemberID`),
  ADD KEY `FineID` (`FineID`);

--
-- Indexes for table `shelf`
--
ALTER TABLE `shelf`
  ADD PRIMARY KEY (`ShelfID`),
  ADD KEY `TypeId` (`TypeId`),
  ADD KEY `Genre` (`Genre`(255));

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`SId`),
  ADD UNIQUE KEY `NIC` (`NIC`);

--
-- Indexes for table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`TypeId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `fine`
--
ALTER TABLE `fine`
  ADD CONSTRAINT `fine_ibfk_1` FOREIGN KEY (`MemberID`) REFERENCES `member` (`MId`);

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`TypeId`) REFERENCES `type` (`TypeId`),
  ADD CONSTRAINT `item_ibfk_2` FOREIGN KEY (`ShelfID`) REFERENCES `shelf` (`ShelfID`);

--
-- Constraints for table `returntable`
--
ALTER TABLE `returntable`
  ADD CONSTRAINT `returntable_ibfk_1` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemId`),
  ADD CONSTRAINT `returntable_ibfk_2` FOREIGN KEY (`MemberID`) REFERENCES `member` (`MId`),
  ADD CONSTRAINT `returntable_ibfk_3` FOREIGN KEY (`FineID`) REFERENCES `fine` (`FineID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

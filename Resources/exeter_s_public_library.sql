-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 10, 2024 at 04:11 PM
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
  `AuthorId` varchar(6) NOT NULL,
  `Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `author`
--
DELIMITER $$
CREATE TRIGGER `before_author_insert` BEFORE INSERT ON `author` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    SELECT IFNULL(MAX(CAST(SUBSTRING(AuthorId, 3) AS UNSIGNED)), 0) INTO max_id FROM Author;
    SET new_id = CONCAT('AH', LPAD(max_id + 1, 4, '0'));
    SET NEW.AuthorId = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `BRN` varchar(10) NOT NULL,
  `ItemId` varchar(10) DEFAULT NULL,
  `AuthorId` varchar(6) DEFAULT NULL,
  `PublisherId` varchar(6) DEFAULT NULL,
  `Genre` varchar(6) DEFAULT NULL,
  `ISBN` varchar(13) DEFAULT NULL,
  `Year` year(4) DEFAULT NULL,
  `Language` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `book`
--
DELIMITER $$
CREATE TRIGGER `before_book_insert` BEFORE INSERT ON `book` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(10);
    SELECT IFNULL(MAX(CAST(SUBSTRING(BRN, 2) AS UNSIGNED)), 0) INTO max_id FROM Book;
    SET new_id = CONCAT('B', LPAD(max_id + 1, 9, '0'));
    SET NEW.BRN = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `bookgenre`
--

CREATE TABLE `bookgenre` (
  `GenId` varchar(6) NOT NULL,
  `GenreName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `bookgenre`
--
DELIMITER $$
CREATE TRIGGER `before_book_genre_insert` BEFORE INSERT ON `bookgenre` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    SELECT IFNULL(MAX(CAST(SUBSTRING(GenId, 3) AS UNSIGNED)), 0) INTO max_id FROM BookGenre;
    SET new_id = CONCAT('BG', LPAD(max_id + 1, 4, '0'));
    SET NEW.GenId = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `BorrowID` varchar(6) NOT NULL,
  `ItemID` varchar(10) DEFAULT NULL,
  `MemberID` int(11) DEFAULT NULL,
  `BorrowDate` date DEFAULT NULL,
  `DueDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `borrow`
--
DELIMITER $$
CREATE TRIGGER `before_borrow_insert` BEFORE INSERT ON `borrow` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    
    SELECT IFNULL(MAX(CAST(SUBSTRING(BorrowID, 2) AS UNSIGNED)), 0) INTO max_id FROM Borrow;
    SET new_id = CONCAT('1D', LPAD(max_id + 1, 4, '0'));
    SET NEW.BorrowID = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `director`
--

CREATE TABLE `director` (
  `DirectorId` varchar(6) NOT NULL,
  `Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `director`
--
DELIMITER $$
CREATE TRIGGER `before_director_insert` BEFORE INSERT ON `director` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    SELECT IFNULL(MAX(CAST(SUBSTRING(DirectorId, 3) AS UNSIGNED)), 0) INTO max_id FROM Director;
    SET new_id = CONCAT('DH', LPAD(max_id + 1, 4, '0'));
    SET NEW.DirectorId = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `dvd`
--

CREATE TABLE `dvd` (
  `DRN` varchar(10) NOT NULL,
  `ItemId` varchar(10) DEFAULT NULL,
  `DirectorId` varchar(6) DEFAULT NULL,
  `PublisherId` varchar(6) DEFAULT NULL,
  `Genre` varchar(6) DEFAULT NULL,
  `NumOfDVD` int(11) DEFAULT NULL,
  `Year` year(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `dvd`
--
DELIMITER $$
CREATE TRIGGER `before_dvd_insert` BEFORE INSERT ON `dvd` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(10);
    SELECT IFNULL(MAX(CAST(SUBSTRING(DRN, 2) AS UNSIGNED)), 0) INTO max_id FROM DVD;
    SET new_id = CONCAT('D', LPAD(max_id + 1, 9, '0'));
    SET NEW.DRN = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `dvdgenre`
--

CREATE TABLE `dvdgenre` (
  `GenId` varchar(6) NOT NULL,
  `GenreName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `dvdgenre`
--
DELIMITER $$
CREATE TRIGGER `before_dvd_genre_insert` BEFORE INSERT ON `dvdgenre` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    SELECT IFNULL(MAX(CAST(SUBSTRING(GenId, 3) AS UNSIGNED)), 0) INTO max_id FROM DVDGenre;
    SET new_id = CONCAT('DG', LPAD(max_id + 1, 4, '0'));
    SET NEW.GenId = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `dvdpublisher`
--

CREATE TABLE `dvdpublisher` (
  `DPublisherId` varchar(6) NOT NULL,
  `Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `dvdpublisher`
--
DELIMITER $$
CREATE TRIGGER `before_dvd_publisher_insert` BEFORE INSERT ON `dvdpublisher` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    SELECT IFNULL(MAX(CAST(SUBSTRING(DPublisherId, 3) AS UNSIGNED)), 0) INTO max_id FROM DVDPublisher;
    SET new_id = CONCAT('DP', LPAD(max_id + 1, 4, '0'));
    SET NEW.DPublisherId = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `fine`
--

CREATE TABLE `fine` (
  `FineID` varchar(6) NOT NULL,
  `Amount` decimal(10,2) DEFAULT NULL,
  `FineDate` date DEFAULT NULL,
  `MemberID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `fine`
--
DELIMITER $$
CREATE TRIGGER `before_fine_insert` BEFORE INSERT ON `fine` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    SELECT IFNULL(MAX(CAST(SUBSTRING(FineID, 3) AS UNSIGNED)), 0) INTO max_id FROM Fine;
    SET new_id = CONCAT('FA', LPAD(max_id + 1, 4, '0'));
    SET NEW.FineID = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `ItemId` varchar(10) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `TypeId` varchar(10) DEFAULT NULL,
  `ShelfID` varchar(10) DEFAULT NULL,
  `Availability` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `item`
--
DELIMITER $$
CREATE TRIGGER `before_item_insert` BEFORE INSERT ON `item` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(10);
    SELECT IFNULL(MAX(CAST(SUBSTRING(ItemId, 3) AS UNSIGNED)), 0) INTO max_id FROM Item;
    SET new_id = CONCAT('UG', LPAD(max_id + 1, 4, '0'));
    SET NEW.ItemId = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `MId` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Tel` varchar(10) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `NIC` varchar(12) NOT NULL,
  `Age` tinyint(4) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `publisher`
--

CREATE TABLE `publisher` (
  `PublisherId` varchar(6) NOT NULL,
  `Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `publisher`
--
DELIMITER $$
CREATE TRIGGER `before_publisher_insert` BEFORE INSERT ON `publisher` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    SELECT IFNULL(MAX(CAST(SUBSTRING(PublisherId, 2) AS UNSIGNED)), 0) INTO max_id FROM Publisher;
    SET new_id = CONCAT('P', LPAD(max_id + 1, 4, '0'));
    SET NEW.PublisherId = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `returntable`
--

CREATE TABLE `returntable` (
  `ReturnID` varchar(6) NOT NULL,
  `ItemID` varchar(10) DEFAULT NULL,
  `MemberID` int(11) DEFAULT NULL,
  `ReturnDate` date DEFAULT NULL,
  `FineID` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `returntable`
--
DELIMITER $$
CREATE TRIGGER `before_return_insert` BEFORE INSERT ON `returntable` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    
    SELECT IFNULL(MAX(CAST(SUBSTRING(ReturnID, 3) AS UNSIGNED)), 0) INTO max_id FROM ReturnTable;
    SET new_id = CONCAT('RA', LPAD(max_id + 1, 4, '0'));
    SET NEW.ReturnID = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `shelf`
--

CREATE TABLE `shelf` (
  `ShelfID` varchar(10) NOT NULL,
  `TypeId` varchar(10) DEFAULT NULL,
  `Genre` varchar(6) DEFAULT NULL,
  `ShelfName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `shelf`
--
DELIMITER $$
CREATE TRIGGER `before_shelf_insert` BEFORE INSERT ON `shelf` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(10);
    SELECT IFNULL(MAX(CAST(SUBSTRING(ShelfID, 4) AS UNSIGNED)), 0) INTO max_id FROM Shelf;
    SET new_id = CONCAT('SHF', LPAD(max_id + 1, 4, '0'));
    SET NEW.ShelfID = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `SId` varchar(6) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `NIC` varchar(12) NOT NULL,
  `Tel` varchar(10) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `staff`
--
DELIMITER $$
CREATE TRIGGER `before_staff_insert` BEFORE INSERT ON `staff` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    SELECT IFNULL(MAX(CAST(SUBSTRING(SId, 2) AS UNSIGNED)), 0) INTO max_id FROM Staff;
    SET new_id = CONCAT('S', LPAD(max_id + 1, 4, '0'));
    SET NEW.SId = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE `type` (
  `TypeId` varchar(10) NOT NULL,
  `TypeName` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Triggers `type`
--
DELIMITER $$
CREATE TRIGGER `increment_type_id` BEFORE INSERT ON `type` FOR EACH ROW BEGIN
    DECLARE new_id INT;
    SET new_id = (SELECT IFNULL(MAX(CAST(SUBSTRING(TypeId, 2) AS UNSIGNED)), 0) + 1 FROM Type);
    SET NEW.TypeId = CONCAT('T', LPAD(new_id, 3, '0'));
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`AuthorId`);

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`BRN`),
  ADD UNIQUE KEY `ItemId` (`ItemId`),
  ADD KEY `AuthorId` (`AuthorId`),
  ADD KEY `PublisherId` (`PublisherId`),
  ADD KEY `Genre` (`Genre`);

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
  ADD KEY `Genre` (`Genre`);

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
  ADD KEY `TypeId` (`TypeId`);

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
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `member`
--
ALTER TABLE `member`
  MODIFY `MId` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `book_ibfk_1` FOREIGN KEY (`ItemId`) REFERENCES `item` (`ItemId`),
  ADD CONSTRAINT `book_ibfk_2` FOREIGN KEY (`AuthorId`) REFERENCES `author` (`AuthorId`),
  ADD CONSTRAINT `book_ibfk_3` FOREIGN KEY (`PublisherId`) REFERENCES `publisher` (`PublisherId`),
  ADD CONSTRAINT `book_ibfk_4` FOREIGN KEY (`Genre`) REFERENCES `bookgenre` (`GenId`);

--
-- Constraints for table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemId`),
  ADD CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`MemberID`) REFERENCES `member` (`MId`);

--
-- Constraints for table `dvd`
--
ALTER TABLE `dvd`
  ADD CONSTRAINT `dvd_ibfk_1` FOREIGN KEY (`ItemId`) REFERENCES `item` (`ItemId`),
  ADD CONSTRAINT `dvd_ibfk_2` FOREIGN KEY (`DirectorId`) REFERENCES `director` (`DirectorId`),
  ADD CONSTRAINT `dvd_ibfk_3` FOREIGN KEY (`PublisherId`) REFERENCES `dvdpublisher` (`DPublisherId`),
  ADD CONSTRAINT `dvd_ibfk_4` FOREIGN KEY (`Genre`) REFERENCES `dvdgenre` (`GenId`);

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

--
-- Constraints for table `shelf`
--
ALTER TABLE `shelf`
  ADD CONSTRAINT `shelf_ibfk_1` FOREIGN KEY (`TypeId`) REFERENCES `type` (`TypeId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

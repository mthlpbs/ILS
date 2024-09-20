-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 20, 2024 at 01:43 PM
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
-- Database: `ils`
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
-- Dumping data for table `author`
--

INSERT INTO `author` (`AuthorId`, `Name`) VALUES
('AH0001', 'Jane Austen'),
('AH0002', 'Jon Erickson'),
('AH0003', 'Dava Sobel'),
('AH0004', 'Ellen Lupton'),
('AH0005', 'Michael Bierut');

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
  `ISBN` varchar(13) DEFAULT NULL,
  `Year` varchar(4) DEFAULT NULL,
  `Language` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`BRN`, `ItemId`, `AuthorId`, `PublisherId`, `ISBN`, `Year`, `Language`) VALUES
('B000000001', 'UG0008', 'AH0004', 'P0007', '9781942303190', '2017', 'English'),
('B000000002', 'UG0009', 'AH0005', 'P0007', '9780500773079', '2015', 'English');

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
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `BorrowID` varchar(6) NOT NULL,
  `ItemID` varchar(10) DEFAULT NULL,
  `MemberID` varchar(6) DEFAULT NULL,
  `BorrowDate` date DEFAULT NULL,
  `DueDate` date DEFAULT NULL,
  `FineID` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`BorrowID`, `ItemID`, `MemberID`, `BorrowDate`, `DueDate`, `FineID`) VALUES
('B00001', 'UG0006', 'ME0001', '2024-09-20', '2024-09-23', NULL),
('B00002', 'UG0006', 'ME0004', '2024-09-20', '2024-09-25', NULL),
('B00003', 'UG0007', 'ME0005', '2024-09-20', '2024-09-25', NULL),
('B00004', 'UG0008', 'ME0003', '2024-09-20', '2024-09-25', NULL);

--
-- Triggers `borrow`
--
DELIMITER $$
CREATE TRIGGER `before_borrow_insert` BEFORE INSERT ON `borrow` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    
    -- Get the maximum numeric part of the BorrowID
    SELECT IFNULL(MAX(CAST(SUBSTRING(BorrowID, 2) AS UNSIGNED)), 0) INTO max_id FROM Borrow;
    
    -- Generate the new BorrowID with the correct prefix
    SET new_id = CONCAT('B', LPAD(max_id + 1, 5, '0'));
    
    -- Set the new BorrowID
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
-- Dumping data for table `director`
--

INSERT INTO `director` (`DirectorId`, `Name`) VALUES
('DH0001', 'Frank Darabont'),
('DH0002', 'Christopher Nolan'),
('DH0003', 'Neil deGrasse Tyson');

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
  `NumOfDVD` varchar(11) DEFAULT NULL,
  `Year` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `dvd`
--

INSERT INTO `dvd` (`DRN`, `ItemId`, `DirectorId`, `PublisherId`, `NumOfDVD`, `Year`) VALUES
('D000000003', 'UG0010', 'DH0003', 'DP0003', '4', '2014');

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
-- Table structure for table `dvdpublisher`
--

CREATE TABLE `dvdpublisher` (
  `DPublisherId` varchar(6) NOT NULL,
  `Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `dvdpublisher`
--

INSERT INTO `dvdpublisher` (`DPublisherId`, `Name`) VALUES
('DP0001', 'Castle Rock'),
('DP0002', 'Warner Bros. Pictures'),
('DP0003', 'National Geographic');

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
  `Amount` decimal(10,2) DEFAULT NULL
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
  `Availability` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`ItemId`, `Title`, `TypeId`, `Availability`) VALUES
('UG0006', 'The Shawshank Redemption', 'T002', 1),
('UG0007', 'Inception', 'T002', 1),
('UG0008', 'Design Is Storytelling', 'T001', 1),
('UG0009', 'How to Use Graphic Design to Sell Things', 'T001', 1),
('UG0010', 'Cosmos A Spacetime Odyssey', 'T002', 1);

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
  `MId` varchar(6) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Tel` varchar(10) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `NIC` varchar(12) NOT NULL,
  `Age` tinyint(4) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`MId`, `Name`, `Tel`, `Email`, `NIC`, `Age`, `Address`) VALUES
('ME0001', 'Aeliana Charr', '5550101', 'aeliana.charr@example.com', 'NIC001', 16, '123 Lyoko Lane, France'),
('ME0002', 'Kieran Blayze', '5550102', 'kieran.blayze@example.com', 'NIC002', 17, '456 Quantum Ave, France'),
('ME0003', 'Mira Flare', '5550103', 'mira.flare@example.com', 'NIC003', 15, '789 Cyber St, France'),
('ME0004', 'Talon Vex', '5550104', 'talon.vex@example.com', 'NIC004', 18, '321 Data Drive, France'),
('ME0005', 'Rhea Waver', '5550105', 'rhea.waver@example.com', 'NIC005', 16, '654 Pixel Blvd, France'),
('ME0006', 'Jaxon Night', '5550106', 'jaxon.night@example.com', 'NIC006', 17, '987 Code Cres, France'),
('ME0007', 'Selena Rush', '5550107', 'selena.rush@example.com', 'NIC007', 15, '135 Byte Lane, France'),
('ME0008', 'Leo Storm', '5550108', 'leo.storm@example.com', 'NIC008', 18, '246 Sync St, France'),
('ME0009', 'Nova Glint', '5550109', 'nova.glint@example.com', 'NIC009', 17, '357 Link Ave, France'),
('ME0010', 'Zara Drift', '5550110', 'zara.drift@example.com', 'NIC010', 16, '468 Shift Rd, France');

--
-- Triggers `member`
--
DELIMITER $$
CREATE TRIGGER `before_member_insert` BEFORE INSERT ON `member` FOR EACH ROW BEGIN
    DECLARE max_id INT;
    DECLARE new_id VARCHAR(6);
    SELECT IFNULL(MAX(CAST(SUBSTRING(MId, 3) AS UNSIGNED)), 0) INTO max_id FROM Member;
    SET new_id = CONCAT('ME', LPAD(max_id + 1, 4, '0'));
    SET NEW.MId = new_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `publisher`
--

CREATE TABLE `publisher` (
  `PublisherId` varchar(6) NOT NULL,
  `Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `publisher`
--

INSERT INTO `publisher` (`PublisherId`, `Name`) VALUES
('P0002', 'Oxford Inc'),
('P0003', 'Barnes Noble Classics'),
('P0004', 'Barnes & Noble Classics'),
('P0005', 'No Starch Press'),
('P0006', 'Walker & Company'),
('P0007', 'Thames & Hudson');

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
  `MemberID` varchar(6) DEFAULT NULL,
  `ReturnDate` date DEFAULT NULL,
  `BorrowID` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `returntable`
--

INSERT INTO `returntable` (`ReturnID`, `ItemID`, `MemberID`, `ReturnDate`, `BorrowID`) VALUES
('RA0001', 'UG0006', 'ME0001', '2024-09-20', 'B00001'),
('RA0002', 'UG0006', 'ME0004', '2024-09-20', 'B00002'),
('RA0003', 'UG0007', 'ME0005', '2024-09-20', 'B00003'),
('RA0004', 'UG0008', 'ME0003', '2024-09-20', 'B00004');

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
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`SId`, `Name`, `NIC`, `Tel`, `Email`, `Password`) VALUES
('S0001', 'Jeremie Belpois', '200478405056', '0774137851', 'jr.bpois@dino.icu', '12345');

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
-- Dumping data for table `type`
--

INSERT INTO `type` (`TypeId`, `TypeName`) VALUES
('T001', 'Book'),
('T002', 'DVD');

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
  ADD KEY `PublisherId` (`PublisherId`);

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`BorrowID`),
  ADD KEY `ItemID` (`ItemID`),
  ADD KEY `MemberID` (`MemberID`),
  ADD KEY `fk_fine` (`FineID`);

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
  ADD KEY `PublisherId` (`PublisherId`);

--
-- Indexes for table `dvdpublisher`
--
ALTER TABLE `dvdpublisher`
  ADD PRIMARY KEY (`DPublisherId`);

--
-- Indexes for table `fine`
--
ALTER TABLE `fine`
  ADD PRIMARY KEY (`FineID`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`ItemId`),
  ADD KEY `TypeId` (`TypeId`);

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
  ADD KEY `FK_ReturnTable_Borrow` (`BorrowID`);

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
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `book_ibfk_1` FOREIGN KEY (`ItemId`) REFERENCES `item` (`ItemId`),
  ADD CONSTRAINT `book_ibfk_2` FOREIGN KEY (`AuthorId`) REFERENCES `author` (`AuthorId`),
  ADD CONSTRAINT `book_ibfk_3` FOREIGN KEY (`PublisherId`) REFERENCES `publisher` (`PublisherId`);

--
-- Constraints for table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemId`),
  ADD CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`MemberID`) REFERENCES `member` (`MId`),
  ADD CONSTRAINT `fk_fine` FOREIGN KEY (`FineID`) REFERENCES `fine` (`FineID`);

--
-- Constraints for table `dvd`
--
ALTER TABLE `dvd`
  ADD CONSTRAINT `dvd_ibfk_1` FOREIGN KEY (`ItemId`) REFERENCES `item` (`ItemId`),
  ADD CONSTRAINT `dvd_ibfk_2` FOREIGN KEY (`DirectorId`) REFERENCES `director` (`DirectorId`),
  ADD CONSTRAINT `dvd_ibfk_3` FOREIGN KEY (`PublisherId`) REFERENCES `dvdpublisher` (`DPublisherId`);

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`TypeId`) REFERENCES `type` (`TypeId`);

--
-- Constraints for table `returntable`
--
ALTER TABLE `returntable`
  ADD CONSTRAINT `FK_ReturnTable_Borrow` FOREIGN KEY (`BorrowID`) REFERENCES `borrow` (`BorrowID`),
  ADD CONSTRAINT `returntable_ibfk_1` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemId`),
  ADD CONSTRAINT `returntable_ibfk_2` FOREIGN KEY (`MemberID`) REFERENCES `member` (`MId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

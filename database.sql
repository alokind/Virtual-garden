-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: projekat
-- ------------------------------------------------------
-- Server version	5.7.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `isporukaproizvoda`
--

DROP TABLE IF EXISTS `isporukaproizvoda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `isporukaproizvoda` (
  `idisporukaproizvoda` int(11) NOT NULL AUTO_INCREMENT,
  `rasadnikId` int(11) NOT NULL,
  `prodavnicaId` int(11) NOT NULL,
  `proizvodId` int(11) NOT NULL,
  `kolicina` int(11) NOT NULL,
  `stize` datetime NOT NULL,
  `odbijenaPrihvacenaNaCekanju` int(11) NOT NULL,
  PRIMARY KEY (`idisporukaproizvoda`),
  KEY `rasadnikId_idx` (`rasadnikId`),
  KEY `proizvodId_idx` (`proizvodId`),
  KEY `prodavnicaId_idx` (`prodavnicaId`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isporukaproizvoda`
--

LOCK TABLES `isporukaproizvoda` WRITE;
/*!40000 ALTER TABLE `isporukaproizvoda` DISABLE KEYS */;
INSERT INTO `isporukaproizvoda` VALUES (1,1,4,4,1,'2020-04-20 22:17:17',0),(3,1,4,4,1,'2020-05-01 22:17:17',0),(6,1,4,4,2,'2020-05-01 22:17:17',0),(7,1,4,5,1,'2020-05-01 22:17:17',0),(62,1,4,4,2,'2020-05-02 01:43:46',3),(63,1,4,5,1,'2020-05-02 01:43:46',3),(64,1,4,4,2,'2020-05-02 02:27:19',0),(65,1,4,5,1,'2020-05-02 02:27:19',0),(66,1,4,4,1,'2020-05-02 07:26:05',3),(67,1,4,4,1,'2020-05-02 20:59:13',3),(68,1,4,5,1,'2020-05-02 20:59:13',3),(69,1,4,4,2,'2020-05-02 04:43:05',-1),(70,3,4,4,1,'2020-07-10 14:35:35',0);
/*!40000 ALTER TABLE `isporukaproizvoda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnici`
--

DROP TABLE IF EXISTS `korisnici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `korisnici` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `korime` varchar(45) NOT NULL,
  `lozinka` varchar(45) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) DEFAULT '',
  `datum` date NOT NULL,
  `mesto` varchar(45) NOT NULL,
  `telefon` varchar(45) DEFAULT '',
  `tip` varchar(45) NOT NULL,
  `jeOdobren` int(11) NOT NULL,
  `mejl` varchar(45) NOT NULL,
  `brDostavljaca` int(11) NOT NULL DEFAULT '5',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnici`
--

LOCK TABLES `korisnici` WRITE;
/*!40000 ALTER TABLE `korisnici` DISABLE KEYS */;
INSERT INTO `korisnici` VALUES (1,'nikola','1234','Nikola','Dimitrijevic','1997-12-19','Zajecar','060/4191299','poljoprivrednik',1,'alokindimitrijevic@gmail.com',5),(2,'pera','1234','Pera','Peric','1995-12-11','Beograd','065/1234567','poljoprivrednik',1,'pera@mejl.com',5),(3,'sima','1234','Sima','Simic','2020-04-06','Subotica','063/23346518','poljoprivrednik',1,'sima@mejl.com',5),(4,'cvece','1234','Cveta cvece','','2020-04-20','Beograd','','preduzece',1,'cvece@mejl.com',3),(5,'mile','1234','Miletova prodavnica','','2020-04-20','Nis','','preduzece',1,'mile@mejl.com',5),(6,'admin','1234','Admin','','1997-12-19','Zajecar','','admin',1,'admin@mejl.com',5);
/*!40000 ALTER TABLE `korisnici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ocena` (
  `idocena` int(11) NOT NULL AUTO_INCREMENT,
  `proizvodId` int(11) NOT NULL,
  `korisnikId` int(11) NOT NULL,
  `komentar` varchar(100) NOT NULL,
  `ocena` int(11) NOT NULL,
  PRIMARY KEY (`idocena`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocena`
--

LOCK TABLES `ocena` WRITE;
/*!40000 ALTER TABLE `ocena` DISABLE KEYS */;
INSERT INTO `ocena` VALUES (2,1,2,'Svidja mi se',4),(3,1,3,'Nista narocito',2),(4,2,2,'Ok',3),(5,3,2,'Pa dobro',4),(6,4,2,'Ok je',3),(7,5,2,'Super',5),(8,6,2,'Odlicno',5),(9,2,1,'Kom',2),(10,1,1,'Odlicno',5),(14,5,1,'',0);
/*!40000 ALTER TABLE `ocena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `onlineprodavnica`
--

DROP TABLE IF EXISTS `onlineprodavnica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `onlineprodavnica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prodavnicaId` int(11) NOT NULL,
  `proizvodId` int(11) NOT NULL,
  `kolicina` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_index` (`prodavnicaId`,`proizvodId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `onlineprodavnica`
--

LOCK TABLES `onlineprodavnica` WRITE;
/*!40000 ALTER TABLE `onlineprodavnica` DISABLE KEYS */;
INSERT INTO `onlineprodavnica` VALUES (1,4,1,3),(2,4,2,5),(3,5,2,0),(4,5,3,0),(6,4,4,2),(14,4,5,1),(20,4,6,2),(21,4,7,5),(22,4,8,5),(23,4,9,7),(24,4,10,6);
/*!40000 ALTER TABLE `onlineprodavnica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proizvodi`
--

DROP TABLE IF EXISTS `proizvodi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `proizvodi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `proizvodjac` varchar(45) NOT NULL,
  `jeSadnica` int(11) NOT NULL,
  `trajanje` int(11) NOT NULL,
  `cena` int(11) DEFAULT '100',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proizvodi`
--

LOCK TABLES `proizvodi` WRITE;
/*!40000 ALTER TABLE `proizvodi` DISABLE KEYS */;
INSERT INTO `proizvodi` VALUES (1,'Paprika','NS Seme',1,12,100),(2,'Paradajz','NS Seme',1,10,100),(3,'Luk','NS Seme',1,15,100),(4,'Kan','Azotara Pancevo',0,3,100),(5,'NPK','Elixir Zorka',0,5,100),(6,'Urea','Floris',0,4,100),(7,'Vestacko djubrivo','Elixir Zorka',0,5,120),(8,'Prokelj','NS Seme',1,15,200),(9,'Etiol','Galenika',0,5,170),(10,'Krompir','NS Seme',1,17,140);
/*!40000 ALTER TABLE `proizvodi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proizvodiumagacinu`
--

DROP TABLE IF EXISTS `proizvodiumagacinu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `proizvodiumagacinu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `proizvodId` int(11) NOT NULL,
  `rasadnikId` int(11) NOT NULL,
  `kolicina` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueRasadnikProizvod` (`proizvodId`,`rasadnikId`),
  KEY `proizvodId_idx` (`proizvodId`),
  KEY `rasadnikId_idx` (`rasadnikId`),
  CONSTRAINT `proizvodId` FOREIGN KEY (`proizvodId`) REFERENCES `proizvodi` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `rasadnikId` FOREIGN KEY (`rasadnikId`) REFERENCES `rasadnici` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proizvodiumagacinu`
--

LOCK TABLES `proizvodiumagacinu` WRITE;
/*!40000 ALTER TABLE `proizvodiumagacinu` DISABLE KEYS */;
INSERT INTO `proizvodiumagacinu` VALUES (1,1,1,3),(2,2,1,46),(3,4,1,20),(5,5,1,11),(8,3,1,13),(11,2,2,11),(14,4,2,36);
/*!40000 ALTER TABLE `proizvodiumagacinu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rasadnici`
--

DROP TABLE IF EXISTS `rasadnici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rasadnici` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  `mesto` varchar(45) NOT NULL,
  `kolVode` int(11) NOT NULL,
  `temp` double NOT NULL,
  `korisnikId` int(11) NOT NULL,
  `duzina` int(11) NOT NULL,
  `sirina` int(11) NOT NULL,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rasadnici`
--

LOCK TABLES `rasadnici` WRITE;
/*!40000 ALTER TABLE `rasadnici` DISABLE KEYS */;
INSERT INTO `rasadnici` VALUES (1,'Paprike','Zajecar',50,30,1,5,4,'2020-07-10 15:09:03'),(2,'Krastavci','Zajecar',0,0,1,5,3,'2020-07-10 15:09:03'),(3,'Paradajz','Zajecar',0,0,1,4,4,'2020-07-10 15:09:03'),(4,'Luk','Zajecar',0,0,1,4,3,'2020-07-10 15:09:03'),(5,'Krompir','Zajecar',0,0,1,2,2,'2020-07-10 15:09:03');
/*!40000 ALTER TABLE `rasadnici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sadnice`
--

DROP TABLE IF EXISTS `sadnice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sadnice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `proizvodId` int(11) NOT NULL,
  `rasadnikId` int(11) NOT NULL,
  `pozicija` int(11) NOT NULL,
  `vadjenje` date NOT NULL,
  `biceIzvadjena` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idProizvoda_idx` (`proizvodId`),
  KEY `idRasadnika_idx` (`rasadnikId`),
  CONSTRAINT `idProizvoda` FOREIGN KEY (`proizvodId`) REFERENCES `proizvodi` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idRasadnika` FOREIGN KEY (`rasadnikId`) REFERENCES `rasadnici` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sadnice`
--

LOCK TABLES `sadnice` WRITE;
/*!40000 ALTER TABLE `sadnice` DISABLE KEYS */;
INSERT INTO `sadnice` VALUES (1,1,1,10,'2020-06-15',0),(2,1,1,11,'2020-06-15',0),(3,1,1,12,'2020-06-15',0),(4,1,1,13,'2020-06-15',0),(5,1,1,14,'2020-06-15',0),(6,1,1,15,'2020-06-15',0),(12,1,1,1,'2020-08-04',0),(13,1,1,2,'2020-08-04',0),(14,1,1,3,'2020-08-04',0),(15,1,1,4,'2020-08-04',0),(17,1,1,6,'2020-08-04',0),(18,1,1,7,'2020-08-04',0),(19,2,1,9,'2020-05-06',0),(20,2,1,8,'2020-07-11',1),(21,2,1,17,'2020-04-23',0),(22,2,1,18,'2020-04-27',0),(24,2,1,5,'2020-04-28',0),(25,2,1,16,'2020-04-28',0),(26,1,1,19,'2020-05-03',0),(27,2,2,2,'2020-05-08',0);
/*!40000 ALTER TABLE `sadnice` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-10 17:18:01

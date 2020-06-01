CREATE DATABASE  IF NOT EXISTS `university_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `university_system`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: university_system
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `academicdegree`
--

DROP TABLE IF EXISTS `academicdegree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academicdegree` (
  `idacademicdegree` int NOT NULL AUTO_INCREMENT,
  `idacademicdegreename` int NOT NULL,
  `idprofessor` int NOT NULL,
  `begindate` date NOT NULL,
  `enddate` date DEFAULT NULL,
  PRIMARY KEY (`idacademicdegree`,`idacademicdegreename`,`idprofessor`),
  KEY `id_academicdegree_academicdegreename_idx` (`idacademicdegreename`),
  KEY `fk_aca_pf_idx` (`idprofessor`),
  CONSTRAINT `fk_aca_pf` FOREIGN KEY (`idprofessor`) REFERENCES `professor` (`idprofessor`),
  CONSTRAINT `id_academicdegree_academicdegreename` FOREIGN KEY (`idacademicdegreename`) REFERENCES `academicdegreename` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academicdegree`
--

LOCK TABLES `academicdegree` WRITE;
/*!40000 ALTER TABLE `academicdegree` DISABLE KEYS */;
INSERT INTO `academicdegree` VALUES (26,5,11,'2020-04-28',NULL),(28,7,16,'2020-04-27',NULL),(37,7,22,'2016-09-22',NULL),(38,7,23,'2020-04-27',NULL),(40,7,24,'2020-04-27',NULL),(41,7,20,'2020-04-27',NULL);
/*!40000 ALTER TABLE `academicdegree` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academicdegreename`
--

DROP TABLE IF EXISTS `academicdegreename`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academicdegreename` (
  `fullname` varchar(100) NOT NULL,
  `shortname` varchar(30) NOT NULL,
  `basestructcode` varchar(40) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `academicdegree_basestructcode_uindex` (`basestructcode`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academicdegreename`
--

LOCK TABLES `academicdegreename` WRITE;
/*!40000 ALTER TABLE `academicdegreename` DISABLE KEYS */;
INSERT INTO `academicdegreename` VALUES ('1','1','1',1),('2','2','2',2),('12','12','12',3),('е','е','е',4),('Ученая степень (Полное название)','Ученая степень','Ученая_Степень_ID',5),('09876543 ученая степень','09876543','09876543',6),('Отсутствует','нету','нету_code',7);
/*!40000 ALTER TABLE `academicdegreename` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `degree`
--

DROP TABLE IF EXISTS `degree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `degree` (
  `iddegree` int NOT NULL AUTO_INCREMENT,
  `iddegreename` int NOT NULL,
  `idprofessor` int NOT NULL,
  `begindate` date NOT NULL,
  `enddate` date DEFAULT NULL,
  PRIMARY KEY (`iddegree`,`iddegreename`,`idprofessor`),
  KEY `id_degree_degreename_idx` (`iddegreename`),
  KEY `fk_deg_pro_idx` (`idprofessor`),
  CONSTRAINT `id_degree_degreename` FOREIGN KEY (`iddegreename`) REFERENCES `degreename` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degree`
--

LOCK TABLES `degree` WRITE;
/*!40000 ALTER TABLE `degree` DISABLE KEYS */;
INSERT INTO `degree` VALUES (28,3,11,'2020-04-29',NULL),(29,4,16,'2020-04-27',NULL),(40,4,22,'2020-01-08',NULL),(41,4,23,'2020-04-27',NULL),(43,4,24,'2020-04-27',NULL),(44,4,20,'2020-03-31',NULL);
/*!40000 ALTER TABLE `degree` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `degreename`
--

DROP TABLE IF EXISTS `degreename`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `degreename` (
  `fullname` varchar(100) NOT NULL,
  `shortname` varchar(45) NOT NULL,
  `basestructcode` varchar(45) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `basestructcode_UNIQUE` (`basestructcode`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degreename`
--

LOCK TABLES `degreename` WRITE;
/*!40000 ALTER TABLE `degreename` DISABLE KEYS */;
INSERT INTO `degreename` VALUES ('1','1','1',1),('е','е','е',2),('Ученое Звание (Полное название)','Ученое Звание','Ученое_звание_ID',3),('отсутствует','нету','отсутствует_code',4);
/*!40000 ALTER TABLE `degreename` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `fullname` varchar(100) NOT NULL,
  `shortname` varchar(30) NOT NULL,
  `basestructcode` varchar(40) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `faculty_facultycode_uindex` (`basestructcode`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES ('1','1','1',1),('123','324','45',2),('13','13','13',3),('Факультет Прикладной Информатики','ФПМИ','ФПМИ_ID',4);
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan` (
  `idplan` int NOT NULL AUTO_INCREMENT,
  `idprofessor` int NOT NULL,
  `firstsemfact` int NOT NULL,
  `firstsemplan` int NOT NULL,
  `secondsemfact` int NOT NULL,
  `secondsemplan` int NOT NULL,
  `type` enum('EDUCATIONAL_WORK','EDUCATIONAL_AND_METHODICAL_WORK','SCIENTIFIC_AND_METHODICAL_WORK','RESEARCH_WORK','ORGANIZATIONAL_AND_METHODICAL_WORK','EXTRACURRICULAR_WORK_WITH_STUDENTS','OTHER_TYPES_OF_WORK') NOT NULL,
  `beginyear` int NOT NULL,
  PRIMARY KEY (`idplan`,`idprofessor`),
  KEY `fk_pl_prof_idx` (`idprofessor`),
  CONSTRAINT `fk_pl_prof` FOREIGN KEY (`idprofessor`) REFERENCES `professor` (`idprofessor`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES (1,11,20,64,6,32,'EDUCATIONAL_WORK',2020),(3,11,19,64,6,32,'EDUCATIONAL_WORK',2019),(4,11,18,64,6,32,'EDUCATIONAL_WORK',2018),(5,11,17,64,6,32,'EDUCATIONAL_WORK',2017),(6,11,16,64,6,32,'EDUCATIONAL_WORK',2016),(7,11,15,64,6,32,'EDUCATIONAL_WORK',2015),(8,11,14,64,6,32,'EDUCATIONAL_WORK',2014),(9,11,13,64,6,32,'EDUCATIONAL_WORK',2013),(10,11,12,64,6,32,'EDUCATIONAL_WORK',2012),(11,11,11,64,6,32,'EDUCATIONAL_WORK',2011),(12,11,10,64,6,32,'EDUCATIONAL_WORK',2010),(13,11,9,64,6,32,'EDUCATIONAL_WORK',2009),(14,11,8,64,6,32,'EDUCATIONAL_WORK',2008),(15,11,7,64,6,32,'EDUCATIONAL_WORK',2007),(16,11,6,64,200,32,'EDUCATIONAL_WORK',2006),(17,11,5,64,6,32,'EDUCATIONAL_WORK',2005),(18,11,4,64,6,32,'EDUCATIONAL_WORK',2004),(19,11,158,25,7,30,'EDUCATIONAL_AND_METHODICAL_WORK',2020),(20,22,64,128,12,54,'EDUCATIONAL_WORK',2020),(21,22,23,75,145,26,'EDUCATIONAL_AND_METHODICAL_WORK',2020),(22,23,128,15,164,100,'OTHER_TYPES_OF_WORK',2020),(23,20,450,120,220,64,'EDUCATIONAL_WORK',2018),(24,16,8,15,16,24,'EDUCATIONAL_WORK',2020),(25,24,64,64,45,64,'EDUCATIONAL_WORK',2020),(26,24,640,64,450,64,'EDUCATIONAL_WORK',2019),(27,24,12,12,13,13,'EDUCATIONAL_AND_METHODICAL_WORK',2019);
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `idpost` int NOT NULL AUTO_INCREMENT,
  `idpostname` int NOT NULL,
  `begindate` date NOT NULL,
  `enddate` date DEFAULT NULL,
  `salary` double NOT NULL,
  `idprofessor` int NOT NULL,
  PRIMARY KEY (`idpost`,`idpostname`,`idprofessor`),
  KEY `id_post_postname_idx` (`idpostname`),
  KEY `fk_pos_pro_idx` (`idprofessor`),
  CONSTRAINT `fk_pos_pro` FOREIGN KEY (`idprofessor`) REFERENCES `professor` (`idprofessor`),
  CONSTRAINT `id_post_postname` FOREIGN KEY (`idpostname`) REFERENCES `postname` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (24,6,'2020-04-28',NULL,880,11),(25,5,'2020-05-22','2020-05-30',123,11),(28,7,'2020-04-27',NULL,420,16),(37,7,'2020-04-27',NULL,560,22),(38,8,'2020-04-27',NULL,220,23),(40,6,'2020-01-06',NULL,300,24),(41,8,'2020-04-27','2020-05-19',220,24),(42,6,'2020-04-27',NULL,810,20),(43,8,'2020-04-27',NULL,87654,20);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postname`
--

DROP TABLE IF EXISTS `postname`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `postname` (
  `fullname` varchar(100) NOT NULL,
  `shortname` varchar(45) NOT NULL,
  `basestructcode` varchar(45) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `basestructcode_UNIQUE` (`basestructcode`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postname`
--

LOCK TABLES `postname` WRITE;
/*!40000 ALTER TABLE `postname` DISABLE KEYS */;
INSERT INTO `postname` VALUES ('1','1','1',1),('12','12','12',3),('123','123','123',4),('к','ккк','к',5),('Лектор (полное название)','Лектор','Лектор_ID',6),('Старший преподаватель','ст препод','ст_препод_code',7),('Ассистент','Ассистент','Ассистент_code',8);
/*!40000 ALTER TABLE `postname` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professor`
--

DROP TABLE IF EXISTS `professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professor` (
  `idprofessor` int NOT NULL AUTO_INCREMENT,
  `iduser` int NOT NULL,
  `iduniversityl` int NOT NULL,
  `idfaculty` int NOT NULL,
  `idpulpit` int NOT NULL,
  PRIMARY KEY (`idprofessor`,`iduser`,`iduniversityl`,`idfaculty`,`idpulpit`),
  UNIQUE KEY `iduser_UNIQUE` (`iduser`),
  KEY `id_pr_uni_idx` (`iduniversityl`),
  KEY `id_pr_fac_idx` (`idfaculty`),
  KEY `id_pr_use_idx` (`iduser`),
  KEY `id_pr_pul_idx` (`idpulpit`),
  CONSTRAINT `id_pr_fac` FOREIGN KEY (`idfaculty`) REFERENCES `faculty` (`id`),
  CONSTRAINT `id_pr_pul` FOREIGN KEY (`idpulpit`) REFERENCES `pulpit` (`id`),
  CONSTRAINT `id_pr_uni` FOREIGN KEY (`iduniversityl`) REFERENCES `university` (`id`),
  CONSTRAINT `id_pr_use` FOREIGN KEY (`iduser`) REFERENCES `users` (`idusers`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professor`
--

LOCK TABLES `professor` WRITE;
/*!40000 ALTER TABLE `professor` DISABLE KEYS */;
INSERT INTO `professor` VALUES (11,7,12,4,4),(16,12,12,4,5),(20,18,12,4,5),(22,20,12,4,5),(23,22,12,4,5),(24,23,12,4,5);
/*!40000 ALTER TABLE `professor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pulpit`
--

DROP TABLE IF EXISTS `pulpit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pulpit` (
  `fullname` varchar(100) NOT NULL,
  `shortname` varchar(45) NOT NULL,
  `basestructcode` varchar(45) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `basestructcode_UNIQUE` (`basestructcode`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pulpit`
--

LOCK TABLES `pulpit` WRITE;
/*!40000 ALTER TABLE `pulpit` DISABLE KEYS */;
INSERT INTO `pulpit` VALUES ('1','1','1',1),('12','12','12',2),('е','е','е',3),('Технологий Программирования','ТП','ТП_ID',4),('Информационных систем и управления','ИСУ','ИСУ_CODE',5);
/*!40000 ALTER TABLE `pulpit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `university`
--

DROP TABLE IF EXISTS `university`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `university` (
  `fullname` varchar(100) NOT NULL,
  `shortname` varchar(30) NOT NULL,
  `basestructcode` varchar(40) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `university_institutioncode_uindex` (`basestructcode`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `university`
--

LOCK TABLES `university` WRITE;
/*!40000 ALTER TABLE `university` DISABLE KEYS */;
INSERT INTO `university` VALUES ('qwerty','qwert','vccbbn',1),('qwerty','qwert','v7ccbbn',4),('4','4','4',5),('45','45','45',6),('1234','1234','5',9),('1','1','1',10),('21','12','21',11),('Белорусский Государственный Университет','БГУ','БГУ_ID',12);
/*!40000 ALTER TABLE `university` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `idusers` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `middlename` varchar(45) NOT NULL DEFAULT 'null',
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `userstatus` enum('admin','not_admin') NOT NULL,
  `birthdate` date NOT NULL DEFAULT '1970-01-01',
  `telephone` varchar(45) NOT NULL DEFAULT 'null',
  `location` varchar(45) NOT NULL DEFAULT 'null',
  PRIMARY KEY (`idusers`),
  UNIQUE KEY `users_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (7,'viktor','graskov','Ivanovich','vik_log','1','not_admin','2000-11-30','+375297473331','Mogilev'),(12,'Игорь','Орешко','Георгиевич','igor','igor','not_admin','2020-05-01','123456','Минск'),(18,'Сергей','Кашкевич','Иванович','kash','kash','not_admin','2019-10-10','87654','г минск'),(20,'Сергей','Гутников','Евгеньевич','gutos','gutos','not_admin','2015-01-22','420068','г Минск'),(21,'Админ','Админович','Иванович','admin220','admin220','admin','2020-05-18','4346','дома'),(22,'Вадим','Мацкевич','Владимирович','mackevich','mackevich','not_admin','2020-04-27','435868658','г Минск'),(23,'Иван','Иванов','Иванович','ivan','ivan','not_admin','2000-05-11','09876543','г Могилев');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-01  8:37:23

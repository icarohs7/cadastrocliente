-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cadastrocliente
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @old_character_set_client = @@character_set_client */;
/*!40101 SET @old_character_set_results = @@character_set_results */;
/*!40101 SET @old_collation_connection = @@collation_connection */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @old_time_zone = @@time_zone */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @old_unique_checks = @@unique_checks, UNIQUE_CHECKS = 0 */;
/*!40014 SET @old_foreign_key_checks = @@foreign_key_checks, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @old_sql_mode = @@sql_mode, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @old_sql_notes = @@sql_notes, SQL_NOTES = 0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
	`id`             INT(11)     NOT NULL AUTO_INCREMENT,
	`nome`           VARCHAR(70) NOT NULL,
	`telresidencial` VARCHAR(20) NOT NULL,
	`telcomercial`   VARCHAR(20) NOT NULL,
	`telcelular`     VARCHAR(20) NOT NULL,
	`email`          VARCHAR(70) NOT NULL,
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB
	AUTO_INCREMENT = 3
	DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente`
	DISABLE KEYS */;
INSERT INTO `cliente`
VALUES (1, 'Icaro Temponi', '(31) 1234-1234', '(31) 4321-4321', '(31) 1532-3141', 'icarohs7@gmail.com'),
       (2, 'Márcio Ribeiro', '(31) 1111-2222', '(31) 3333-4444', '(31) 2351-1413', 'profmarcioazevedoribeiro@gmail.com');
/*!40000 ALTER TABLE `cliente`
	ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @old_time_zone */;

/*!40101 SET SQL_MODE = @old_sql_mode */;
/*!40014 SET FOREIGN_KEY_CHECKS = @old_foreign_key_checks */;
/*!40014 SET UNIQUE_CHECKS = @old_unique_checks */;
/*!40101 SET CHARACTER_SET_CLIENT = @old_character_set_client */;
/*!40101 SET CHARACTER_SET_RESULTS = @old_character_set_results */;
/*!40101 SET COLLATION_CONNECTION = @old_collation_connection */;
/*!40111 SET SQL_NOTES = @old_sql_notes */;

-- Dump completed on 2018-06-22 17:22:00

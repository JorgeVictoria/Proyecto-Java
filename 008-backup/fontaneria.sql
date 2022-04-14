-- MariaDB dump 10.19  Distrib 10.4.24-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: fontaneria
-- ------------------------------------------------------
-- Server version	10.4.24-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `IDCliente` varchar(10) NOT NULL,
  `Tipo` varchar(10) NOT NULL,
  `DNI_NIF` varchar(9) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Apellido1` varchar(100) DEFAULT NULL,
  `Apellido2` varchar(100) DEFAULT NULL,
  `Direccion` varchar(200) NOT NULL,
  `CodigoPostal` varchar(5) NOT NULL,
  `Localidad` varchar(200) NOT NULL,
  `Telefono` varchar(9) NOT NULL,
  PRIMARY KEY (`IDCliente`),
  UNIQUE KEY `cliente_doc` (`DNI_NIF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('1','cliente','12345678a','pepe','pepa','pepo','pepu','46900','pep','123456789');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle`
--

DROP TABLE IF EXISTS `detalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle` (
  `IDDetalle` varchar(10) NOT NULL,
  `IDMaterial` varchar(10) DEFAULT NULL,
  `Cantidad` int(6) NOT NULL,
  `Precio` decimal(10,2) NOT NULL,
  PRIMARY KEY (`IDDetalle`),
  KEY `detalle_fk` (`IDMaterial`),
  CONSTRAINT `detalle_fk` FOREIGN KEY (`IDMaterial`) REFERENCES `material` (`IDMaterial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle`
--

LOCK TABLES `detalle` WRITE;
/*!40000 ALTER TABLE `detalle` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS `documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documento` (
  `IDDocumento` varchar(10) NOT NULL,
  `IDCliente` varchar(10) DEFAULT NULL,
  `TipoDocumento` varchar(20) NOT NULL,
  `Fecha` date NOT NULL,
  `IVA` int(2) NOT NULL,
  `ImporteInicial` decimal(10,2) NOT NULL,
  `ImporteIVA` decimal(10,2) NOT NULL,
  `ImporteFinal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`IDDocumento`),
  KEY `documento_fk` (`IDCliente`),
  CONSTRAINT `documento_fk` FOREIGN KEY (`IDCliente`) REFERENCES `cliente` (`IDCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento`
--

LOCK TABLES `documento` WRITE;
/*!40000 ALTER TABLE `documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documento_detalle`
--

DROP TABLE IF EXISTS `documento_detalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documento_detalle` (
  `IDDocumento` varchar(10) NOT NULL,
  `IDDetalle` varchar(10) NOT NULL,
  PRIMARY KEY (`IDDocumento`,`IDDetalle`),
  KEY `documento_detalle_fk2` (`IDDetalle`),
  CONSTRAINT `documento_detalle_fk1` FOREIGN KEY (`IDDocumento`) REFERENCES `documento` (`IDDocumento`),
  CONSTRAINT `documento_detalle_fk2` FOREIGN KEY (`IDDetalle`) REFERENCES `detalle` (`IDDetalle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento_detalle`
--

LOCK TABLES `documento_detalle` WRITE;
/*!40000 ALTER TABLE `documento_detalle` DISABLE KEYS */;
/*!40000 ALTER TABLE `documento_detalle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material` (
  `IDMaterial` varchar(10) NOT NULL,
  `Categoria` varchar(100) NOT NULL,
  `Nombre` varchar(200) NOT NULL,
  `Descripcion` varchar(200) NOT NULL,
  `PrecioCoste` decimal(10,2) NOT NULL,
  `Incremento` decimal(10,2) NOT NULL,
  `PrecioTotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`IDMaterial`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-10  0:31:26

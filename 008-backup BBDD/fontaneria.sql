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
  `DNI` varchar(9) NOT NULL,
  `IDCliente` varchar(10) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Apellido1` varchar(100) NOT NULL,
  `Apellido2` varchar(100) NOT NULL,
  `Direccion` varchar(200) NOT NULL,
  `CodigoPostal` varchar(5) NOT NULL,
  `Localidad` varchar(200) NOT NULL,
  `Provincia` varchar(100) NOT NULL,
  `Telefono` varchar(9) NOT NULL,
  `Correo` varchar(200) NOT NULL,
  PRIMARY KEY (`DNI`),
  UNIQUE KEY `cliente_doc` (`IDCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('12345671F','C.1','Cliente','Uno','García','C/Sin Nombre nº 1 puerta 1','46900','Torrent','Valencia','961111111','ClienteUno@fontaneria.com'),('12345672P','C.2','Cliente','Dos','Gómez','C/ Sin Nombre nº 1 puerta 2','46900','Torrent','Valencia','962222222','ClienteDos@fontaneria.com'),('12345673D','C.3','Cliente','Tres','Pérez','C/Sin Nombre nº1 puerta 3','46970','Alacuas','Valencia','963333333','ClienteTres@fontaneria.com');
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
  `Material_IDMaterial` varchar(10) NOT NULL,
  `Descripcion` varchar(200) DEFAULT NULL,
  `Cantidad` int(6) NOT NULL,
  `PrecioUnitario` decimal(10,2) NOT NULL,
  `Importe` decimal(10,2) NOT NULL,
  PRIMARY KEY (`IDDetalle`),
  KEY `detalle_fk` (`Material_IDMaterial`),
  CONSTRAINT `detalle_fk` FOREIGN KEY (`Material_IDMaterial`) REFERENCES `material` (`IDMaterial`)
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
-- Table structure for table `documento_detalle`
--

DROP TABLE IF EXISTS `documento_detalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documento_detalle` (
  `Presupuesto_IDPresupuesto` varchar(10) NOT NULL,
  `Detalle_IDDetalle` varchar(10) NOT NULL,
  PRIMARY KEY (`Presupuesto_IDPresupuesto`,`Detalle_IDDetalle`),
  KEY `documento_detalle_fk2` (`Detalle_IDDetalle`),
  CONSTRAINT `documento_detalle_fk1` FOREIGN KEY (`Presupuesto_IDPresupuesto`) REFERENCES `presupuesto` (`IDPresupuesto`),
  CONSTRAINT `documento_detalle_fk2` FOREIGN KEY (`Detalle_IDDetalle`) REFERENCES `detalle` (`IDDetalle`)
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
  `PrecioUnitario` decimal(10,2) NOT NULL,
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

--
-- Table structure for table `presupuesto`
--

DROP TABLE IF EXISTS `presupuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `presupuesto` (
  `IDPresupuesto` varchar(10) NOT NULL,
  `Cliente_DNI` varchar(10) NOT NULL,
  `NumAlbaran` varchar(10) DEFAULT NULL,
  `NumFactura` varchar(10) DEFAULT NULL,
  `Fecha` date NOT NULL,
  `TasaIVA` int(2) NOT NULL,
  `BaseImponible` decimal(10,2) NOT NULL,
  `ImporteIVA` decimal(10,2) NOT NULL,
  `Total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`IDPresupuesto`),
  KEY `documento_fk` (`Cliente_DNI`),
  CONSTRAINT `documento_fk` FOREIGN KEY (`Cliente_DNI`) REFERENCES `cliente` (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presupuesto`
--

LOCK TABLES `presupuesto` WRITE;
/*!40000 ALTER TABLE `presupuesto` DISABLE KEYS */;
/*!40000 ALTER TABLE `presupuesto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-25 14:18:54

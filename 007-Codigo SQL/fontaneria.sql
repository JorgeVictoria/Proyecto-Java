
#borramos la BBDD si existe
DROP DATABASE IF EXISTS fontaneria;

#creamos la BBDD
CREATE DATABASE fontaneria;

USE fontaneria;

#creamos la tabla cliente
CREATE TABLE cliente (
    DNI VARCHAR(9),
    IDCliente VARCHAR(10) NOT NULL,
    Nombre VARCHAR(100) NOT NULL,
    Apellido1 VARCHAR(100) NOT NULL,
    Apellido2 VARCHAR(100) NOT NULL,
    Direccion VARCHAR(200) NOT NULL,
    CodigoPostal VARCHAR(5) NOT NULL, 
    Localidad VARCHAR(200) NOT NULL,
    Provincia VARCHAR(100) NOT NULL,
    Telefono VARCHAR(9) NOT NULL,
    CONSTRAINT cliente_pk PRIMARY KEY (DNI),
    CONSTRAINT cliente_doc UNIQUE (IDCliente)
)


#creamos la tabla material
CREATE TABLE material (
    IDMaterial VARCHAR(10),
    Categoria VARCHAR(100) NOT NULL,
    Nombre VARCHAR(200) NOT NULL,
    Descripcion VARCHAR(200) NOT NULL,
    PrecioCoste DECIMAL (10,2) NOT NULL,
    Incremento DECIMAL (10,2)NOT NULL,
    PrecioUnitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT material_pk PRIMARY KEY (IDMaterial)
)


#creamos la tabla detalle
CREATE TABLE detalle (
    IDDetalle VARCHAR(10),
    Material_IDMaterial VARCHAR(10),
    Descripcion VARCHAR(200),
    Cantidad INTEGER(6) NOT NULL,
    PrecioUnitario DECIMAL(10,2) NOT NULL,
    Importe DECIMAL(10,2) NOT NULL,
    CONSTRAINT detalle_pk PRIMARY KEY (IDDetalle),
    CONSTRAINT detalle_fk FOREIGN KEY (Material_IDMaterial) REFERENCES material (IDMaterial)
)


#creamos la tabla presupuesto
CREATE TABLE presupuesto (
    IDPresupuesto VARCHAR(10),
    Cliente_DNI VARCHAR(10),
    NumAlbaran VARCHAR(10),
    NumFactura VARCHAR(10),
    Fecha DATE NOT NULL,
    TasaIVA INTEGER(2) NOT NULL,
    BaseImponible DECIMAL(10,2) NOT NULL,
    ImporteIVA DECIMAL(10,2) NOT NULL,
    Total DECIMAL(10,2) NOT NULL,
    CONSTRAINT documento_pk PRIMARY KEY (IDDocumento),
    CONSTRAINT documento_fk FOREIGN KEY (Cliente_DNI) REFERENCES cliente (DNI)
)


#creamos la tabla documento_detalle
CREATE TABLE documento_detalle (
    Presupuesto_IDPresupuesto VARCHAR(10),
    Detalle_IDDetalle VARCHAR(10),
    CONSTRAINT documento_detalle_pk PRIMARY KEY (Presupuesto_IDPresupuesto, Detalle_IDDetalle),
    CONSTRAINT documento_detalle_fk1 FOREIGN KEY (Presupuesto_IDPresupuesto) REFERENCES documento (IDPresupuesto),
    CONSTRAINT documento_detalle_fk2 FOREIGN KEY (Detalle_IDDetalle) REFERENCES detalle (IDDetalle)
)


	

#borramos la BBDD si existe
DROP DATABASE IF EXISTS fontaneria;

#creamos la BBDD
CREATE DATABASE fontaneria;

USE fontaneria;

CREATE TABLE cliente (
	IDCliente VARCHAR(10),
	Tipo VARCHAR(10) NOT NULL,
	DNI_NIF VARCHAR(9) NOT NULL,
	Nombre VARCHAR(100) NOT NULL,
	Apellido1 VARCHAR(100),
	Apellido2 VARCHAR(100),
	Direccion VARCHAR(200) NOT NULL,
	CodigoPostal VARCHAR(5) NOT NULL, 
	Localidad VARCHAR(200) NOT NULL,
	Telefono VARCHAR(9) NOT NULL,
	CONSTRAINT cliente_pk PRIMARY KEY (IDCliente),
	CONSTRAINT cliente_doc UNIQUE (DNI_NIF)
)

CREATE TABLE material (
	IDMaterial VARCHAR(10),
	Categoria VARCHAR(100) NOT NULL,
	Nombre VARCHAR(200) NOT NULL,
	Descripcion VARCHAR(200) NOT NULL,
	PrecioCoste DECIMAL (10,2) NOT NULL,
	Incremento DECIMAL (10,2)NOT NULL,
	PrecioTotal DECIMAL(10,2) NOT NULL,
	CONSTRAINT material_pk PRIMARY KEY (IDMaterial)
)

CREATE TABLE detalle (
	IDDetalle VARCHAR(10),
	IDMaterial VARCHAR(10),
	Cantidad INTEGER(6) NOT NULL,
	Precio DECIMAL(10,2) NOT NULL,
	CONSTRAINT detalle_pk PRIMARY KEY (IDDetalle),
	CONSTRAINT detalle_fk FOREIGN KEY (IDMaterial) REFERENCES material (IDMaterial)
)

CREATE TABLE documento (
	IDDocumento VARCHAR(10),
	IDCliente VARCHAR(10),
	TipoDocumento VARCHAR(20) NOT NULL,
	Fecha DATE NOT NULL,
	IVA INTEGER(2) NOT NULL,
	ImporteInicial DECIMAL(10,2) NOT NULL,
	ImporteIVA DECIMAL(10,2) NOT NULL,
	ImporteFinal DECIMAL(10,2) NOT NULL,
	CONSTRAINT documento_pk PRIMARY KEY (IDDocumento),
	CONSTRAINT documento_fk FOREIGN KEY (IDCliente) REFERENCES cliente (IDCliente)
)

CREATE TABLE documento_detalle (
	IDDocumento VARCHAR(10),
	IDDetalle VARCHAR(10),
	CONSTRAINT documento_detalle_pk PRIMARY KEY (IDDocumento,IDDetalle),
	CONSTRAINT documento_detalle_fk1 FOREIGN KEY (IDDocumento) REFERENCES documento (IDDocumento),
	CONSTRAINT documento_detalle_fk2 FOREIGN KEY (IDDetalle) REFERENCES detalle (IDDetalle)
)

	
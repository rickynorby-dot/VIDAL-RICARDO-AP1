CREATE DATABASE IF NOT EXISTS sitrac_met
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE sitrac_met;

CREATE TABLE sector (
    id_sector INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    descripcion VARCHAR(200)
) ENGINE=InnoDB;

CREATE TABLE responsable (
    id_responsable INT AUTO_INCREMENT PRIMARY KEY,
    id_sector INT NOT NULL,
    legajo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(120),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_responsable_sector
      FOREIGN KEY (id_sector) REFERENCES sector(id_sector)
) ENGINE=InnoDB;

CREATE TABLE tipo_instrumento (
    id_tipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    requiere_calibracion BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE instrumento (
    id_instrumento INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo INT NOT NULL,
    id_sector_referencia INT NOT NULL,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    marca VARCHAR(80) NOT NULL,
    modelo VARCHAR(80) NOT NULL,
    nro_serie VARCHAR(80) UNIQUE,
    estado ENUM('DISPONIBLE','ASIGNADO','EN_CALIBRACION','FUERA_SERVICIO') NOT NULL DEFAULT 'DISPONIBLE',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_instrumento_tipo
      FOREIGN KEY (id_tipo) REFERENCES tipo_instrumento(id_tipo),
    CONSTRAINT fk_instrumento_sector
      FOREIGN KEY (id_sector_referencia) REFERENCES sector(id_sector)
) ENGINE=InnoDB;

CREATE TABLE laboratorio (
    id_laboratorio INT AUTO_INCREMENT PRIMARY KEY,
    razon_social VARCHAR(120) NOT NULL,
    identificacion VARCHAR(40),
    email VARCHAR(120)
) ENGINE=InnoDB;

CREATE TABLE calibracion (
    id_calibracion INT AUTO_INCREMENT PRIMARY KEY,
    id_instrumento INT NOT NULL,
    id_laboratorio INT,
    fecha_calibracion DATE NOT NULL,
    resultado ENUM('APTO','NO_APTO') NOT NULL,
    nro_certificado VARCHAR(80) NOT NULL UNIQUE,
    proximo_vencimiento DATE NOT NULL,
    observaciones VARCHAR(250),
    CONSTRAINT fk_calibracion_instrumento
      FOREIGN KEY (id_instrumento) REFERENCES instrumento(id_instrumento),
    CONSTRAINT fk_calibracion_laboratorio
      FOREIGN KEY (id_laboratorio) REFERENCES laboratorio(id_laboratorio),
    INDEX idx_calibracion_vencimiento (proximo_vencimiento)
) ENGINE=InnoDB;

CREATE TABLE asignacion (
    id_asignacion INT AUTO_INCREMENT PRIMARY KEY,
    id_instrumento INT NOT NULL,
    id_responsable INT NOT NULL,
    fecha_entrega DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion DATETIME NULL,
    condicion_entrega VARCHAR(200),
    condicion_devolucion VARCHAR(200),
    CONSTRAINT fk_asignacion_instrumento
      FOREIGN KEY (id_instrumento) REFERENCES instrumento(id_instrumento),
    CONSTRAINT fk_asignacion_responsable
      FOREIGN KEY (id_responsable) REFERENCES responsable(id_responsable),
    INDEX idx_asignacion_abierta (id_instrumento, fecha_devolucion)
) ENGINE=InnoDB;

CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT NOT NULL,
    id_responsable INT NULL UNIQUE,
    nombre_usuario VARCHAR(60) NOT NULL UNIQUE,
    clave_hash VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol(id_rol),
    CONSTRAINT fk_usuario_responsable FOREIGN KEY (id_responsable) REFERENCES responsable(id_responsable)
) ENGINE=InnoDB;

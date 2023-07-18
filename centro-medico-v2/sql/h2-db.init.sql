DROP TABLE IF EXISTS paramedicos;
DROP TABLE IF EXISTS enfermeras;
DROP TABLE IF EXISTS medicos;
DROP TABLE IF EXISTS pacientes;


CREATE TABLE IF NOT EXISTS pacientes (
    dni                VARCHAR(15) PRIMARY KEY,
    nombre             VARCHAR(255) NOT NULL,
    telefono           VARCHAR(15) NOT NULL,
    fecha_nacimiento   DATE NOT NULL,
    grupo_sanguineo    VARCHAR(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS medicos (
    id                 INT PRIMARY KEY,
    dni                VARCHAR(15) NOT NULL,
    nombre             VARCHAR(255) NOT NULL,
    telefono           VARCHAR(15) NOT NULL,
    especialidad       VARCHAR(50) NOT NULL,
    UNIQUE (dni)
);

CREATE TABLE IF NOT EXISTS enfermeras (
    id                 INT PRIMARY KEY,
    dni                VARCHAR(15) NOT NULL,
    nombre             VARCHAR(255) NOT NULL,
    telefono           VARCHAR(15) NOT NULL,
    UNIQUE (dni)
);

CREATE TABLE IF NOT EXISTS paramedicos (
    id                 INT PRIMARY KEY,
    dni                VARCHAR(15) NOT NULL,
    nombre             VARCHAR(255) NOT NULL,
    telefono           VARCHAR(15) NOT NULL,
    UNIQUE (dni)
);

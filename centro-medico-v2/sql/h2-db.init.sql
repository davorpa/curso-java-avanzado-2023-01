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
    dni                VARCHAR(15) PRIMARY KEY,
    nombre             VARCHAR(255) NOT NULL,
    telefono           VARCHAR(15) NOT NULL,
    especialidad       VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS enfermeras (
    dni                VARCHAR(15) PRIMARY KEY,
    nombre             VARCHAR(255) NOT NULL,
    telefono           VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS paramedicos (
    dni                VARCHAR(15) PRIMARY KEY,
    nombre             VARCHAR(255) NOT NULL,
    telefono           VARCHAR(15) NOT NULL
);

-- DDL catálogo Relatos de Papel (alineado con entidad Book / tabla books)
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'catalogue_db')
    CREATE DATABASE catalogue_db;
GO

USE catalogue_db;
GO

IF OBJECT_ID('books', 'U') IS NOT NULL
    DROP TABLE books;
GO

CREATE TABLE books (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
    titulo          NVARCHAR(300)  NOT NULL,
    autor           NVARCHAR(200)  NOT NULL,
    genero          NVARCHAR(100)  NULL,
    idioma          NVARCHAR(50)   NULL,
    descripcion     NVARCHAR(MAX)  NULL,
    precio_cop      DECIMAL(12,2)  NULL,
    formato         NVARCHAR(50)   NULL,
    stock           INT            NULL DEFAULT 0,
    publicacion     INT            NULL,
    isbn            NVARCHAR(20)   NULL UNIQUE,
    valoracion      FLOAT          NULL,
    visible         BIT            NOT NULL DEFAULT 1
);
GO

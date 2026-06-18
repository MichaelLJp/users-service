-- DDL usuarios Relatos de Papel (alineado con la entidad User)
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'users_db')
    CREATE DATABASE users_db;
GO

USE users_db;
GO

IF OBJECT_ID('users', 'U') IS NOT NULL
    DROP TABLE users;
GO

CREATE TABLE users (
    id          BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre      NVARCHAR(150)  NOT NULL,
    email       NVARCHAR(150)  NOT NULL UNIQUE,
    password    NVARCHAR(255)  NOT NULL,
    role        NVARCHAR(20)   NOT NULL DEFAULT 'USER',
    created_at  DATETIME2      NOT NULL DEFAULT SYSDATETIME()
);
GO

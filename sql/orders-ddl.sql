-- DDL pedidos Relatos de Papel (alineado con entidades Order y OrderItem)
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'orders_db')
    CREATE DATABASE orders_db;
GO

USE orders_db;
GO

IF OBJECT_ID('order_items', 'U') IS NOT NULL
    DROP TABLE order_items;
GO

IF OBJECT_ID('orders', 'U') IS NOT NULL
    DROP TABLE orders;
GO

CREATE TABLE orders (
    id          BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id     BIGINT         NOT NULL,
    order_date  DATETIME2      NOT NULL,
    total       DECIMAL(12,2)  NOT NULL,
    status      NVARCHAR(20)   NOT NULL DEFAULT 'PENDING'
);
GO

CREATE TABLE order_items (
    id          BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id    BIGINT         NOT NULL,
    book_id     BIGINT         NOT NULL,
    quantity    INT            NOT NULL,
    price       DECIMAL(12,2)  NOT NULL,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
GO

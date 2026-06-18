USE [catalogue_db]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[books](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[titulo] [nvarchar](300) NOT NULL,
	[autor] [nvarchar](200) NOT NULL,
	[genero] [nvarchar](100) NULL,
	[idioma] [nvarchar](50) NULL,
	[descripcion] [text] NULL,
	[precio_cop] [decimal](12, 2) NOT NULL,
	[formato] [nvarchar](50) NULL,
	[stock] [int] NOT NULL,
	[publicacion] [int] NULL,
	[fechaPublicacion] [date] NULL,
	[isbn] [nvarchar](20) NULL,
	[valoracion] [decimal](3, 1) NULL,
	[visible] [bit] NOT NULL,
	[created_at] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[isbn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[books] ADD  DEFAULT ('Español') FOR [idioma]
GO

ALTER TABLE [dbo].[books] ADD  DEFAULT ('Físico') FOR [formato]
GO

ALTER TABLE [dbo].[books] ADD  DEFAULT ((0)) FOR [stock]
GO

ALTER TABLE [dbo].[books] ADD  DEFAULT ((1)) FOR [visible]
GO

ALTER TABLE [dbo].[books] ADD  DEFAULT (getdate()) FOR [created_at]
GO

ALTER TABLE [dbo].[books]  WITH CHECK ADD CHECK  (([valoracion]>=(1) AND [valoracion]<=(5)))
GO

CREATE DATABASE catalogue_db

CREATE DATABASE orders_db

CREATE TABLE [dbo].[order_items](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[order_id] [bigint] NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [numeric](38, 2) NULL,
	[book_id] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[order_items]  WITH CHECK ADD FOREIGN KEY([order_id])
REFERENCES [dbo].[orders] ([id])
ON DELETE CASCADE
GO

CREATE TABLE [dbo].[orders](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[total] [numeric](38, 2) NULL,
	[status] [varchar](255) NULL,
	[created_at] [datetime] NULL,
	[order_date] [datetime2](6) NOT NULL,
	[user_id] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[orders] ADD  DEFAULT ('PENDING') FOR [status]
GO

ALTER TABLE [dbo].[orders] ADD  DEFAULT (getdate()) FOR [created_at]
GO
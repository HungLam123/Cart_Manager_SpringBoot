USE [master]
GO
/****** Object:  Database [mockproject_t4_2022]    Script Date: 8/8/2022 3:03:24 PM ******/
CREATE DATABASE [mockproject_t4_2022]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'mockproject_t4_2022', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MAYAO\MSSQL\DATA\mockproject_t4_2022.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'mockproject_t4_2022_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MAYAO\MSSQL\DATA\mockproject_t4_2022_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [mockproject_t4_2022] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [mockproject_t4_2022].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [mockproject_t4_2022] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET ARITHABORT OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [mockproject_t4_2022] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [mockproject_t4_2022] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET  ENABLE_BROKER 
GO
ALTER DATABASE [mockproject_t4_2022] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [mockproject_t4_2022] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET RECOVERY FULL 
GO
ALTER DATABASE [mockproject_t4_2022] SET  MULTI_USER 
GO
ALTER DATABASE [mockproject_t4_2022] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [mockproject_t4_2022] SET DB_CHAINING OFF 
GO
ALTER DATABASE [mockproject_t4_2022] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [mockproject_t4_2022] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [mockproject_t4_2022] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [mockproject_t4_2022] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'mockproject_t4_2022', N'ON'
GO
ALTER DATABASE [mockproject_t4_2022] SET QUERY_STORE = OFF
GO
USE [mockproject_t4_2022]
GO
/****** Object:  Table [dbo].[order_details]    Script Date: 8/8/2022 3:03:25 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_details](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[orderId] [bigint] NULL,
	[productId] [bigint] NULL,
	[price] [decimal](12, 3) NOT NULL,
	[quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 8/8/2022 3:03:25 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[userId] [bigint] NULL,
	[address] [nvarchar](255) NOT NULL,
	[phone] [varchar](11) NOT NULL,
	[createdDate] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_types]    Script Date: 8/8/2022 3:03:25 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_types](
	[id] [tinyint] IDENTITY(1,1) NOT NULL,
	[description] [ntext] NULL,
	[slug] [varchar](255) NOT NULL,
	[isDeleted] [bit] NOT NULL,
	[isDelete] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[products]    Script Date: 8/8/2022 3:03:25 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[products](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[typeId] [tinyint] NULL,
	[quantity] [int] NOT NULL,
	[price] [decimal](12, 3) NOT NULL,
	[unitId] [tinyint] NULL,
	[imgUrl] [varchar](255) NULL,
	[description] [ntext] NULL,
	[slug] [varchar](255) NOT NULL,
	[isDeleted] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 8/8/2022 3:03:25 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles](
	[id] [tinyint] IDENTITY(1,1) NOT NULL,
	[description] [nvarchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[unit_types]    Script Date: 8/8/2022 3:03:25 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[unit_types](
	[id] [tinyint] IDENTITY(1,1) NOT NULL,
	[description] [ntext] NULL,
	[isDeleted] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 8/8/2022 3:03:25 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[username] [varchar](100) NULL,
	[fullname] [nvarchar](50) NULL,
	[hashPassword] [varchar](255) NOT NULL,
	[email] [varchar](100) NOT NULL,
	[createdDate] [datetime] NOT NULL,
	[imgUrl] [varchar](255) NULL,
	[reset_password_token] [varchar](30) NULL,
	[verification_code] [varchar](64) NULL,
	[enabled] [bit] NOT NULL,
	[roleId] [tinyint] NULL,
	[isDeleted] [bit] NOT NULL,
	[auth_provider] [varchar](15) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[order_details] ON 

INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (1, 1, 2, CAST(7500000.000 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (2, 1, 3, CAST(19250000.000 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (3, 1, 4, CAST(13450000.000 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (12, 4, 3, CAST(19250000.000 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (13, 4, 4, CAST(13450000.000 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (14, 5, 1, CAST(10000000.000 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (15, 7, 1, CAST(10000000.000 AS Decimal(12, 3)), 3)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (16, 7, 2, CAST(7500000.000 AS Decimal(12, 3)), 1)
SET IDENTITY_INSERT [dbo].[order_details] OFF
GO
SET IDENTITY_INSERT [dbo].[orders] ON 

INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate]) VALUES (1, 2, N'HCM', N'0937419961', CAST(N'2022-04-11T15:20:40.143' AS DateTime))
INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate]) VALUES (2, 1, N'Hà Nội', N'0911915295', CAST(N'2022-06-11T15:21:58.887' AS DateTime))
INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate]) VALUES (3, 3, N'Đồng Nai', N'093213213', CAST(N'2022-06-11T15:23:37.517' AS DateTime))
INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate]) VALUES (4, 2, N'Hà Nội', N'0937419961', CAST(N'2022-08-04T12:49:44.260' AS DateTime))
INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate]) VALUES (5, 2, N'Hà Nội', N'0ngaymai', CAST(N'2022-08-05T00:25:42.267' AS DateTime))
INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate]) VALUES (7, 2, N'Hà Nội', N'0937419961', CAST(N'2022-08-05T14:15:49.343' AS DateTime))
SET IDENTITY_INSERT [dbo].[orders] OFF
GO
SET IDENTITY_INSERT [dbo].[product_types] ON 

INSERT [dbo].[product_types] ([id], [description], [slug], [isDeleted], [isDelete]) VALUES (1, N'Điện thoại', N'dien-thoai', 0, NULL)
INSERT [dbo].[product_types] ([id], [description], [slug], [isDeleted], [isDelete]) VALUES (2, N'Laptop', N'laptop', 0, NULL)
SET IDENTITY_INSERT [dbo].[product_types] OFF
GO
SET IDENTITY_INSERT [dbo].[products] ON 

INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (1, N'Iphone 11 64GB', 1, 50, CAST(10000000.000 AS Decimal(12, 3)), 1, N'iphone-11.png', N'Điện thoại Iphone 11 bản 64GB', N'iphone-11-64gb', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (2, N'Samsung A95', 1, 20, CAST(7500000.000 AS Decimal(12, 3)), 1, N'samsung-a95.jpg', N'<b>Điện thoại samsung A95</b> là mẫu điện thoại mới nhất của Samsung với nhiều tính năng vượt trội', N'samsung-a95', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (3, N'Laptop HP Pavilion i7 10th', 2, 99, CAST(19250000.000 AS Decimal(12, 3)), 1, N'product-2.jpg', N'Laptop HP Pavilion core i7 10th', N'laptop-hp-pavilion-i7-10th', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (4, N'Laptop DELL Inspirion i5 gen 8', 2, 69, CAST(13450000.000 AS Decimal(12, 3)), 1, N'laptop-dell-inspirion.jpg', N'Laptop DELL Inspirion', N'laptop-dell-inspirion-i5-gen-8', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (5, N'Iphone 12 Pro max 256GB', 1, 150, CAST(37000000.000 AS Decimal(12, 3)), 1, N'iphone-12-pro-max.jpg', N'Iphone xịn', N'iphone-12-pro-max-256gb', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (6, N'Oppo Reno 4', 1, 70, CAST(13270000.000 AS Decimal(12, 3)), 1, N'oppo-reno-4.jpg', N'Điện thoại Oppo', N'oppo-reno-4', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (7, N'Iphone 12 pro max', 1, 99, CAST(13270000.000 AS Decimal(12, 3)), 1, N'iphone-12-pro-max.jpg', N'Điện thoại Oppo', N'san-pham-demo-1', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (8, N'Iphone 6 Pro XS Max', 1, 30, CAST(13270000.000 AS Decimal(12, 3)), 1, N'product-5.jpg', N'Điện thoại Oppo', N'san-pham-demo-2', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (9, N'Iphon 4 XS Pro SuperMax', 1, 66, CAST(13270000.000 AS Decimal(12, 3)), 1, N'product-3.jpg', N'Điện thoại Oppo', N'san-pham-demo-3', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (10, N'Iphon 4 XS Pro SuperMax', 2, 42, CAST(13270000.000 AS Decimal(12, 3)), 1, N'images.png', N'LapTop Pro Max', N'lap-top Pro Max', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (11, N'Iphon 4 XS Pro SuperMax', 2, 32, CAST(11980000.000 AS Decimal(12, 3)), 1, N'laptop_2.png', N'Laptop Phiên Bản XS Max', N'laptop-XSmax', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (12, N'Iphon 4 XS Pro SuperMax', 2, 54, CAST(13800000.000 AS Decimal(12, 3)), 1, N'lapto_3.png', N'Laptop Phiên Bản Never Die', N'laptop_never Die', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (15, N'Iphone 11 64GB323131232', 1, 132, CAST(32000000.000 AS Decimal(12, 3)), 1, N'', N'Laptop HP Pavilion core i7 10th122112', N'iphone-11-64gbDDDD', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (17, N'Iphone 11 64GB323131232', 1, 123, CAST(32000000.000 AS Decimal(12, 3)), 1, N'', N'Điện thoại Iphone 11 bản 64GB3231323123213123', N'iphone-11-64gbDDDD3213123123', 0)
SET IDENTITY_INSERT [dbo].[products] OFF
GO
SET IDENTITY_INSERT [dbo].[roles] ON 

INSERT [dbo].[roles] ([id], [description]) VALUES (1, N'admin')
INSERT [dbo].[roles] ([id], [description]) VALUES (2, N'user')
SET IDENTITY_INSERT [dbo].[roles] OFF
GO
SET IDENTITY_INSERT [dbo].[unit_types] ON 

INSERT [dbo].[unit_types] ([id], [description], [isDeleted]) VALUES (1, N'Chiếc', 0)
INSERT [dbo].[unit_types] ([id], [description], [isDeleted]) VALUES (2, N'Bộ', 0)
SET IDENTITY_INSERT [dbo].[unit_types] OFF
GO
SET IDENTITY_INSERT [dbo].[users] ON 

INSERT [dbo].[users] ([id], [username], [fullname], [hashPassword], [email], [createdDate], [imgUrl], [reset_password_token], [verification_code], [enabled], [roleId], [isDeleted], [auth_provider]) VALUES (1, N'duynt', N'Nguyễn Trần Duy', N'$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO', N'duynt@abc.com', CAST(N'2022-07-10T19:19:47.217' AS DateTime), N'default.png', NULL, NULL, 1, 1, 0, NULL)
INSERT [dbo].[users] ([id], [username], [fullname], [hashPassword], [email], [createdDate], [imgUrl], [reset_password_token], [verification_code], [enabled], [roleId], [isDeleted], [auth_provider]) VALUES (2, N'lamnh', N'Nguyễn Hùng Lâm', N'$2a$10$M41cPkcQPDAu.B5T76WJ5OEDkURwSUyGxWm.NH0phsdrvLUg8lQDG', N'lamdzvler0108@gmail.com', CAST(N'2022-07-10T19:19:47.217' AS DateTime), N'hunglam.png', NULL, NULL, 1, 1, 0, NULL)
INSERT [dbo].[users] ([id], [username], [fullname], [hashPassword], [email], [createdDate], [imgUrl], [reset_password_token], [verification_code], [enabled], [roleId], [isDeleted], [auth_provider]) VALUES (3, N'hunglam', N'Nguyễn Hoàng Lâm1', N'$2a$10$kIVs/hB9R2LcOqLQGKRrbeqCUdqbIO50ZS50BZP03fpPxwWkgrMB2', N'nguyenhunglam182004@gmail.com', CAST(N'2022-07-10T19:19:47.217' AS DateTime), N'hoanglam.png', NULL, NULL, 1, 2, 0, NULL)
INSERT [dbo].[users] ([id], [username], [fullname], [hashPassword], [email], [createdDate], [imgUrl], [reset_password_token], [verification_code], [enabled], [roleId], [isDeleted], [auth_provider]) VALUES (26, N'nguyenhunglam182001@gmail.com', N'Lâm Nguyễn', N'$2a$10$JbCiFNRECh67HO67sOb0pOP1CjT26ktI9H.w9vGyiPABegwnUVH1G', N'nguyenhunglam182001@gmail.com', CAST(N'2022-08-07T17:54:48.013' AS DateTime), NULL, NULL, NULL, 1, 2, 0, N'GOOGLE')
SET IDENTITY_INSERT [dbo].[users] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__products__32DD1E4C9361A4EB]    Script Date: 8/8/2022 3:03:25 PM ******/
ALTER TABLE [dbo].[products] ADD UNIQUE NONCLUSTERED 
(
	[slug] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__roles__489B0D97A7AA53B2]    Script Date: 8/8/2022 3:03:25 PM ******/
ALTER TABLE [dbo].[roles] ADD UNIQUE NONCLUSTERED 
(
	[description] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__AB6E6164119356E6]    Script Date: 8/8/2022 3:03:25 PM ******/
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__F3DBC5728961B374]    Script Date: 8/8/2022 3:03:25 PM ******/
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[orders] ADD  DEFAULT (getdate()) FOR [createdDate]
GO
ALTER TABLE [dbo].[product_types] ADD  DEFAULT ((0)) FOR [isDeleted]
GO
ALTER TABLE [dbo].[products] ADD  DEFAULT ((0)) FOR [isDeleted]
GO
ALTER TABLE [dbo].[unit_types] ADD  DEFAULT ((0)) FOR [isDeleted]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (getdate()) FOR [createdDate]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ((0)) FOR [enabled]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT ((0)) FOR [isDeleted]
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD FOREIGN KEY([orderId])
REFERENCES [dbo].[orders] ([id])
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD FOREIGN KEY([productId])
REFERENCES [dbo].[products] ([id])
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD FOREIGN KEY([userId])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD FOREIGN KEY([typeId])
REFERENCES [dbo].[product_types] ([id])
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD FOREIGN KEY([unitId])
REFERENCES [dbo].[unit_types] ([id])
GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD FOREIGN KEY([roleId])
REFERENCES [dbo].[roles] ([id])
GO
/****** Object:  StoredProcedure [dbo].[sp_getTotalPricePerMonth]    Script Date: 8/8/2022 3:03:25 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create Proc [dbo].[sp_getTotalPricePerMonth](
	@month varchar(2),
	@year varchar(4)
)
as begin
	Declare @result varchar(20)
	SET @result =(Select
						SUM(order_details.price * order_details.quantity) as 'totalPrice'
					From 
						orders
						inner join order_details on orders.id = order_details.orderId
					WHERE 
						MONTH(orders.createdDate) = @month
						AND YEAR(orders.createdDate) = @year)
	IF @result IS NULL begin SET @result = '0' END
	Select @result
end

GO
USE [master]
GO
ALTER DATABASE [mockproject_t4_2022] SET  READ_WRITE 
GO

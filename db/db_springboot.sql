use master
go

if db_id('mockproject_t4_2022') is not null
drop database mockproject_t4_2022
go

create database mockproject_t4_2022
go

use mockproject_t4_2022
go

create table roles
(
	id				tinyint			primary key identity,  -- auto_increment
	[description]	nvarchar(20)	not null unique
)
go

create table users
(
	id						bigint			primary key identity,
	username				varchar(20)		not null unique,
	fullname				nvarchar(50)	null,
	hashPassword			varchar(255)	not null,
	email					varchar(100)	not null unique,
	createdDate				datetime		not null default getdate(), -- now()
	imgUrl					varchar(255)	null,
	reset_password_token	varchar(30)		null,
	verification_code		varchar(64)		null,
	enabled					bit				not null default 0,
	roleId					tinyint			foreign key references roles(id),
	isDeleted				bit				not null default 0 -- 0: false, 1: true
)
go

create table product_types
(
	id				tinyint			primary key identity,
	[description]	ntext			null,
	slug			varchar(255)	not null,
	isDeleted		bit				not null default 0
)
go

create table unit_types
(
	id				tinyint			primary key identity,
	[description]	ntext			null,
	isDeleted		bit				not null default 0
)

create table products
(
	id				bigint			primary key identity,
	[name]			nvarchar(255)	not null,
	typeId			tinyint			foreign key references product_types(id),
	quantity		int				not null,
	price			decimal(12,3)	not null,
	unitId			tinyint			foreign key references unit_types(id),
	imgUrl			varchar(255)	null,
	[description]	ntext			null,
	slug			varchar(255)	not null unique,
	isDeleted		bit				not null default 0
)
go

create table orders
(
	id				bigint			primary key identity,
	userId			bigint			foreign key references users(id),
	[address]		nvarchar(255)	not null,
	phone			varchar(11)		not null,
	createdDate		datetime		not null default getdate()
)
go

create table order_details
(
	id				bigint			primary key identity,
	orderId			bigint			foreign key references orders(id),
	productId		bigint			foreign key references products(id),
	price			decimal(12,3)	not null,
	quantity		int				not null
)
go

insert into roles([description]) values
('admin'),
('user')
go

-- duynt: pass 111
insert into users(username, fullname, hashPassword, email, imgUrl, reset_password_token, verification_code, enabled, roleId) values
('duynt', N'Nguyễn Trần Duy', '$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO', 'duynt@abc.com', 'default.png', null, null, 0, 1),
('lamnh', N'Nguyễn Hùng Lâm', '$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO', 'lamdzvler0108@gmail.com', 'hunglam.png', null, null, 0, 1),
('hunglam', N'Nguyễn Hoàng Lâm', '$2a$10$bC3fIu4WyB/FGwlbOPlZt.3IRzkM34vZNt1Kbe5ZDcq7r/XZFWNrO', 'nguyenhunglam182001@gmail.com', 'hoanglam.png', null, null, 0, 2)
go

insert into product_types([description], slug) values
(N'Điện thoại', 'dien-thoai'),
(N'Laptop',		'laptop')
go

insert into unit_types([description]) values
(N'Chiếc'),
(N'Bộ')
go
INSERT [dbo].[products] ( [name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Iphone 11 64GB', 1, 4, CAST(10000000.000 AS Decimal(12, 3)), 1, N'iphone-11.png', N'Điện thoại Iphone 11 bản 64GB', N'iphone-11-64gb', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Samsung A95', 1, 10, CAST(7500000.000 AS Decimal(12, 3)), 1, N'samsung-a95.jpg', N'<b>Điện thoại samsung A95</b> là mẫu điện thoại mới nhất của Samsung với nhiều tính năng vượt trội', N'samsung-a95', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Laptop HP Pavilion i7 10th', 2, 100, CAST(19250000.000 AS Decimal(12, 3)), 1, N'product-2.jpg', N'Laptop HP Pavilion core i7 10th', N'laptop-hp-pavilion-i7-10th', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Laptop DELL Inspirion i5 gen 8', 2, 70, CAST(13450000.000 AS Decimal(12, 3)), 1, N'laptop-dell-inspirion.jpg', N'Laptop DELL Inspirion', N'laptop-dell-inspirion-i5-gen-8', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Iphone 12 Pro max 256GB', 1, 150, CAST(37000000.000 AS Decimal(12, 3)), 1, N'iphone-12-pro-max.jpg', N'Iphone xịn', N'iphone-12-pro-max-256gb', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Oppo Reno 4', 1, 70, CAST(13270000.000 AS Decimal(12, 3)), 1, N'oppo-reno-4.jpg', N'Điện thoại Oppo', N'oppo-reno-4', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Iphone 12 pro max', 1, 99, CAST(13270000.000 AS Decimal(12, 3)), 1, N'iphone-12-pro-max.jpg', N'Điện thoại Oppo', N'san-pham-demo-1', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Iphone 6 Pro XS Max', 1, 30, CAST(13270000.000 AS Decimal(12, 3)), 1, N'product-5.jpg', N'Điện thoại Oppo', N'san-pham-demo-2', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Iphon 4 XS Pro SuperMax', 1, 66, CAST(13270000.000 AS Decimal(12, 3)), 1, N'product-3.jpg', N'Điện thoại Oppo', N'san-pham-demo-3', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Iphon 4 XS Pro SuperMax', 2, 42, CAST(13270000.000 AS Decimal(12, 3)), 1, N'images.png', N'LapTop Pro Max', N'lap-top Pro Max', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Iphon 4 XS Pro SuperMax', 2, 32, CAST(11980000.000 AS Decimal(12, 3)), 1, N'laptop_2.png', N'Laptop Phiên Bản XS Max', N'laptop-XSmax', 0)
INSERT [dbo].[products] ([name], [typeId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (N'Iphon 4 XS Pro SuperMax', 2, 54, CAST(13800000.000 AS Decimal(12, 3)), 1, N'lapto_3.png', N'Laptop Phiên Bản Never Die', N'laptop_never Die', 0)
go

INSERT [dbo].[orders] ([userId], [address], [phone], [createdDate]) VALUES (2, N'HCM', N'0937419961', CAST(N'2022-04-11T15:20:40.143' AS DateTime))
INSERT [dbo].[orders] ([userId], [address], [phone], [createdDate]) VALUES (1, N'Hà Nội', N'0911915295', CAST(N'2022-06-11T15:21:58.887' AS DateTime))
INSERT [dbo].[orders] ([userId], [address], [phone], [createdDate]) VALUES (3, N'Đồng Nai', N'093213213', CAST(N'2022-06-11T15:23:37.517' AS DateTime))
go

INSERT [dbo].[order_details] ([orderId], [productId], [price], [quantity]) VALUES (1, 2, CAST(7500000.000 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([orderId], [productId], [price], [quantity]) VALUES (1, 3, CAST(19250000.000 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([orderId], [productId], [price], [quantity]) VALUES (1, 4, CAST(13450000.000 AS Decimal(12, 3)), 1)
go

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
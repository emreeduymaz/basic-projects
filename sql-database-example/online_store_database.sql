USE [master]
GO
/****** Object:  Database [OnlineStore]    Script Date: 24.05.2024 15:51:05 ******/
CREATE DATABASE [OnlineStore]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'OnlineStore', FILENAME = N'D:\SQL\MSSQL16.SQLEXPRESS\MSSQL\DATA\OnlineStore.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'OnlineStore_log', FILENAME = N'D:\SQL\MSSQL16.SQLEXPRESS\MSSQL\DATA\OnlineStore_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [OnlineStore] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [OnlineStore].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [OnlineStore] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [OnlineStore] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [OnlineStore] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [OnlineStore] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [OnlineStore] SET ARITHABORT OFF 
GO
ALTER DATABASE [OnlineStore] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [OnlineStore] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [OnlineStore] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [OnlineStore] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [OnlineStore] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [OnlineStore] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [OnlineStore] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [OnlineStore] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [OnlineStore] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [OnlineStore] SET  DISABLE_BROKER 
GO
ALTER DATABASE [OnlineStore] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [OnlineStore] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [OnlineStore] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [OnlineStore] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [OnlineStore] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [OnlineStore] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [OnlineStore] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [OnlineStore] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [OnlineStore] SET  MULTI_USER 
GO
ALTER DATABASE [OnlineStore] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [OnlineStore] SET DB_CHAINING OFF 
GO
ALTER DATABASE [OnlineStore] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [OnlineStore] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [OnlineStore] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [OnlineStore] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [OnlineStore] SET QUERY_STORE = ON
GO
ALTER DATABASE [OnlineStore] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [OnlineStore]
GO
/****** Object:  User [regular_user]    Script Date: 24.05.2024 15:51:05 ******/
CREATE USER [regular_user] FOR LOGIN [regular_login] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [manager_user]    Script Date: 24.05.2024 15:51:05 ******/
CREATE USER [manager_user] FOR LOGIN [manager_login] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [admin_user]    Script Date: 24.05.2024 15:51:05 ******/
CREATE USER [admin_user] FOR LOGIN [admin_login] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  DatabaseRole [UserRole]    Script Date: 24.05.2024 15:51:05 ******/
CREATE ROLE [UserRole]
GO
/****** Object:  DatabaseRole [Manager]    Script Date: 24.05.2024 15:51:05 ******/
CREATE ROLE [Manager]
GO
/****** Object:  DatabaseRole [Admin]    Script Date: 24.05.2024 15:51:05 ******/
CREATE ROLE [Admin]
GO
ALTER ROLE [UserRole] ADD MEMBER [regular_user]
GO
ALTER ROLE [Manager] ADD MEMBER [manager_user]
GO
ALTER ROLE [Admin] ADD MEMBER [admin_user]
GO
/****** Object:  Table [dbo].[Customers]    Script Date: 24.05.2024 15:51:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customers](
	[CustomerID] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [nvarchar](50) NULL,
	[LastName] [nvarchar](50) NULL,
	[Email] [nvarchar](100) NULL,
	[PhoneNumber] [nvarchar](15) NULL,
	[Address] [nvarchar](255) NULL,
	[LastOrderDate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[CustomerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[OrderID] [int] IDENTITY(1,1) NOT NULL,
	[CustomerID] [int] NULL,
	[OrderDate] [datetime] NULL,
	[TotalAmount] [decimal](10, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[CustomerOrders]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[CustomerOrders] AS
SELECT 
    C.CustomerID, C.FirstName, C.LastName, O.OrderID, O.OrderDate, O.TotalAmount
FROM 
    Customers C
JOIN 
    Orders O ON C.CustomerID = O.CustomerID;
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[CategoryID] [int] IDENTITY(1,1) NOT NULL,
	[CategoryName] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Products]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[ProductID] [int] IDENTITY(1,1) NOT NULL,
	[ProductName] [nvarchar](100) NULL,
	[CategoryID] [int] NULL,
	[Price] [decimal](10, 2) NULL,
	[Stock] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ProductID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[ProductAndCategoryNames]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[ProductAndCategoryNames] AS
SELECT 
    ProductName AS Name, 'Product' AS Type
FROM 
    Products
UNION
SELECT 
    CategoryName AS Name, 'Category' AS Type
FROM 
    Categories;
GO
/****** Object:  View [dbo].[HighValueOrders]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[HighValueOrders] AS
SELECT 
    OrderID, CustomerID, TotalAmount
FROM 
    Orders
WHERE 
    TotalAmount > (SELECT AVG(TotalAmount) FROM Orders);
GO
/****** Object:  Table [dbo].[OrderItems]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderItems](
	[OrderItemID] [int] IDENTITY(1,1) NOT NULL,
	[OrderID] [int] NULL,
	[ProductID] [int] NULL,
	[Quantity] [int] NULL,
	[UnitPrice] [decimal](10, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderItemID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductSuppliers]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductSuppliers](
	[ProductID] [int] NOT NULL,
	[SupplierID] [int] NOT NULL,
 CONSTRAINT [PK_ProductSuppliers] PRIMARY KEY CLUSTERED 
(
	[ProductID] ASC,
	[SupplierID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reviews]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reviews](
	[ReviewID] [int] IDENTITY(1,1) NOT NULL,
	[ProductID] [int] NULL,
	[CustomerID] [int] NULL,
	[Rating] [int] NULL,
	[ReviewText] [nvarchar](1000) NULL,
	[ReviewDate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[ReviewID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Suppliers]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Suppliers](
	[SupplierID] [int] IDENTITY(1,1) NOT NULL,
	[SupplierName] [nvarchar](100) NULL,
	[ContactName] [nvarchar](50) NULL,
	[PhoneNumber] [nvarchar](15) NULL,
	[Email] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[SupplierID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Customer__A9D10534E852F0DA]    Script Date: 24.05.2024 15:51:06 ******/
ALTER TABLE [dbo].[Customers] ADD UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_Customers_Email]    Script Date: 24.05.2024 15:51:06 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_Customers_Email] ON [dbo].[Customers]
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_OrderItems_OrderID]    Script Date: 24.05.2024 15:51:06 ******/
CREATE NONCLUSTERED INDEX [IX_OrderItems_OrderID] ON [dbo].[OrderItems]
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_Orders_CustomerID]    Script Date: 24.05.2024 15:51:06 ******/
CREATE NONCLUSTERED INDEX [IX_Orders_CustomerID] ON [dbo].[Orders]
(
	[CustomerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_Products_CategoryID]    Script Date: 24.05.2024 15:51:06 ******/
CREATE NONCLUSTERED INDEX [IX_Products_CategoryID] ON [dbo].[Products]
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [IX_Reviews_ProductID]    Script Date: 24.05.2024 15:51:06 ******/
CREATE NONCLUSTERED INDEX [IX_Reviews_ProductID] ON [dbo].[Reviews]
(
	[ProductID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[OrderItems]  WITH CHECK ADD  CONSTRAINT [FK_OrderItems_Orders] FOREIGN KEY([OrderID])
REFERENCES [dbo].[Orders] ([OrderID])
GO
ALTER TABLE [dbo].[OrderItems] CHECK CONSTRAINT [FK_OrderItems_Orders]
GO
ALTER TABLE [dbo].[OrderItems]  WITH CHECK ADD  CONSTRAINT [FK_OrderItems_Products] FOREIGN KEY([ProductID])
REFERENCES [dbo].[Products] ([ProductID])
GO
ALTER TABLE [dbo].[OrderItems] CHECK CONSTRAINT [FK_OrderItems_Products]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Orders_Customers] FOREIGN KEY([CustomerID])
REFERENCES [dbo].[Customers] ([CustomerID])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Orders_Customers]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK_Products_Categories] FOREIGN KEY([CategoryID])
REFERENCES [dbo].[Categories] ([CategoryID])
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK_Products_Categories]
GO
ALTER TABLE [dbo].[ProductSuppliers]  WITH CHECK ADD  CONSTRAINT [FK_ProductSuppliers_Products] FOREIGN KEY([ProductID])
REFERENCES [dbo].[Products] ([ProductID])
GO
ALTER TABLE [dbo].[ProductSuppliers] CHECK CONSTRAINT [FK_ProductSuppliers_Products]
GO
ALTER TABLE [dbo].[ProductSuppliers]  WITH CHECK ADD  CONSTRAINT [FK_ProductSuppliers_Suppliers] FOREIGN KEY([SupplierID])
REFERENCES [dbo].[Suppliers] ([SupplierID])
GO
ALTER TABLE [dbo].[ProductSuppliers] CHECK CONSTRAINT [FK_ProductSuppliers_Suppliers]
GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD  CONSTRAINT [FK_Reviews_Customers] FOREIGN KEY([CustomerID])
REFERENCES [dbo].[Customers] ([CustomerID])
GO
ALTER TABLE [dbo].[Reviews] CHECK CONSTRAINT [FK_Reviews_Customers]
GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD  CONSTRAINT [FK_Reviews_Products] FOREIGN KEY([ProductID])
REFERENCES [dbo].[Products] ([ProductID])
GO
ALTER TABLE [dbo].[Reviews] CHECK CONSTRAINT [FK_Reviews_Products]
GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD CHECK  (([Rating]>=(1) AND [Rating]<=(5)))
GO
/****** Object:  StoredProcedure [dbo].[AddProduct]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[AddProduct] 
    @ProductName NVARCHAR(100), 
    @CategoryID INT, 
    @Price DECIMAL(10, 2), 
    @Stock INT
AS
BEGIN
    INSERT INTO Products (ProductName, CategoryID, Price, Stock)
    VALUES (@ProductName, @CategoryID, @Price, @Stock);
END;
GO
/****** Object:  StoredProcedure [dbo].[GetCustomerOrders]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetCustomerOrders] 
    @CustomerID INT
AS
BEGIN
    SELECT 
        OrderID, OrderDate, TotalAmount
    FROM 
        Orders
    WHERE 
        CustomerID = @CustomerID;
END;
GO
/****** Object:  StoredProcedure [dbo].[UpdateStock]    Script Date: 24.05.2024 15:51:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UpdateStock] 
    @ProductID INT, 
    @NewStock INT
AS
BEGIN
    UPDATE Products
    SET Stock = @NewStock
    WHERE ProductID = @ProductID;
END;
GO
USE [master]
GO
ALTER DATABASE [OnlineStore] SET  READ_WRITE 
GO

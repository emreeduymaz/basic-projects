CREATE TABLE Customers (
    CustomerID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100),
    Phone VARCHAR(15)
);

INSERT INTO Customers VALUES
(101, 'John', 'Doe', 'john.doe@example.com', '123-456-7890'),
(102, 'Jane', 'Smith', 'jane.smith@example.com', '234-567-8901'),
(103, 'Alice', 'Johnson', 'alice.johnson@example.com', '345-678-9012'),
(104, 'Bob', 'Brown', 'bob.brown@example.com', '456-789-0123'),
(112, 'Charlie', 'Williams', 'charlie.williams@example.com', '567-890-1234');



CREATE TABLE Orders (
    OrderID INT PRIMARY KEY,
    CustomerID INT,
    OrderDate DATE,
    TotalValue DECIMAL(10, 2),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

INSERT INTO Orders VALUES
(201, 101, '2024-01-10', 1500.00),
(202, 102, '2024-02-15', 2500.00),
(203, 103, '2024-03-05', 3000.00),
(204, 104, '2024-03-25', 1200.00),
(205, 112, '2024-01-20', 2100.00);



CREATE TABLE Products (
    ProductID INT PRIMARY KEY,
    Category VARCHAR(50),
    Model VARCHAR(50),
    ExecutionTime INT,
    WorkCenterID INT
);

INSERT INTO Products VALUES
(301, 'Electronics', 'ModelA', 5, 401),
(302, 'Electronics', 'ModelB', 6, 402),
(303, 'Furniture', 'ModelC', 7, 403),
(304, 'Clothing', 'ModelD', 3, 404),
(305, 'Electronics', 'ModelE', 4, 401);



CREATE TABLE WorkCenters (
    WorkCenterID INT PRIMARY KEY,
    City VARCHAR(50),
    Country VARCHAR(50)
);

INSERT INTO WorkCenters VALUES
(401, 'New York', 'USA'),
(402, 'Los Angeles', 'USA'),
(403, 'Chicago', 'USA'),
(404, 'Houston', 'USA'),
(405, 'Phoenix', 'USA');



CREATE TABLE Warehouses (
    WarehouseID INT PRIMARY KEY,
    Name VARCHAR(50),
    City VARCHAR(50),
    Country VARCHAR(50)
);

INSERT INTO Warehouses VALUES
(501, 'WarehouseA', 'New York', 'USA'),
(502, 'WarehouseB', 'Los Angeles', 'USA'),
(503, 'WarehouseC', 'Chicago', 'USA'),
(504, 'WarehouseD', 'Houston', 'USA'),
(505, 'WarehouseE', 'Phoenix', 'USA');



CREATE TABLE ProductWarehouses (
    ProductID INT,
    WarehouseID INT,
    StockLevel INT,
    PRIMARY KEY (ProductID, WarehouseID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (WarehouseID) REFERENCES Warehouses(WarehouseID)
);

INSERT INTO ProductWarehouses VALUES
(301, 501, 100),
(302, 502, 150),
(303, 503, 200),
(304, 504, 250),
(305, 505, 300);


--Provide a list of all clients.

SELECT CustomerID, FirstName, LastName
FROM Customers
ORDER BY LastName;

--Provide a list of product work centers

SELECT wc.WorkCenterID, wc.City, wc.Country, p.ProductID, p.Model
FROM WorkCenters wc
JOIN Products p ON wc.WorkCenterID = p.WorkCenterID;


--Provide an alphabetical list 

SELECT DISTINCT Country, City
FROM WorkCenters
ORDER BY Country, City;


--Provide a list of orders placed by the customer with ID 112.

SELECT OrderID, OrderDate, TotalValue
FROM Orders
WHERE CustomerID = 112
ORDER BY OrderDate DESC;


--Provide a list of orders worth more than $2,000.

SELECT OrderID, CustomerID, TotalValue
FROM Orders
WHERE TotalValue > 2000
ORDER BY TotalValue DESC;



--Provide a list of product models

SELECT ProductID, Category, Model, ExecutionTime AS Price
FROM Products
ORDER BY ExecutionTime DESC;


--Provide a list of all "backpack" product models with prices ranging from $15 to $30.

SELECT ProductID, Category, Model, ExecutionTime AS Price
FROM Products
WHERE Category = 'backpack' AND ExecutionTime BETWEEN 15 AND 30
ORDER BY ExecutionTime ASC;



--List all orders over $50 placed between 01/03/24 and 03/26/24.

SELECT OrderID, OrderDate, TotalValue
FROM Orders
WHERE TotalValue > 50 AND OrderDate BETWEEN '2024-01-03' AND '2024-03-26'
ORDER BY OrderDate DESC, TotalValue DESC;

--View the in-stock status of each product model.

SELECT p.Model, w.Name, w.City, pw.StockLevel
FROM ProductWarehouses pw
JOIN Products p ON pw.ProductID = p.ProductID
JOIN Warehouses w ON pw.WarehouseID = w.WarehouseID
ORDER BY p.Model, pw.StockLevel;

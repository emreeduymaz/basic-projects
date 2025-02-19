-- Insert initial data into Users table
INSERT INTO Users (username, password, email) VALUES ('user1', 'password1', 'user1@example.com');
INSERT INTO Users (username, password, email) VALUES ('user2', 'password2', 'user2@example.com');

-- Insert initial data into Products table
INSERT INTO Products (name, description, price, stock) VALUES ('Product 1', 'Description for product 1', 10.99, 100);
INSERT INTO Products (name, description, price, stock) VALUES ('Product 2', 'Description for product 2', 15.99, 200);

-- Insert initial data into Orders table
INSERT INTO Orders (user_id, total_amount, status) VALUES (1, 25.98, 'Processing');

-- Insert initial data into OrderItems table
INSERT INTO OrderItems (order_id, product_id, quantity, price) VALUES (1, 1, 1, 10.99);
INSERT INTO OrderItems (order_id, product_id, quantity, price) VALUES (1, 2, 1, 14.99);

-- Insert initial data into PaymentTransactions table
INSERT INTO PaymentTransactions (order_id, amount, payment_method) VALUES (1, 25.98, 'Credit Card');

CREATE DATABASE customers_orders;
USE customers_orders;

CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(50),
    city VARCHAR(30));
    
CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    amount DECIMAL(10,2),
    order_date DATE,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id));
    
INSERT INTO Customers (customer_id, customer_name, city) VALUES
(1, 'Ravi', 'Hyderabad'),
(2, 'Priya', 'Bangalore'),
(3, 'Arun', 'Chennai'),
(4, 'Sneha', 'Mumbai'),
(5, 'Kiran', 'Delhi');

INSERT INTO Orders (order_id, customer_id, amount, order_date) VALUES
(101, 1, 3000, '2026-01-10'),
(102, 1, 2500, '2026-01-15'),
(103, 2, 5000, '2026-02-05'),
(104, 2, 3000, '2026-02-10'),
(105, 3, 7000, '2026-02-20'),
(106, 4, 2000, '2026-03-01'),
(107, 5, 4000, '2026-03-05');

SELECT customer_id, SUM(amount) AS total_order_amount FROM Orders GROUP BY customer_id;

SELECT customer_id, COUNT(*) AS order_count FROM Orders GROUP BY customer_id HAVING COUNT(*) > 3;

SELECT customer_id, AVG(amount) AS average_order_amount FROM Orders GROUP BY customer_id;

SELECT customer_id, MAX(amount) AS highest_order_amount FROM Orders GROUP BY customer_id;

SELECT c.customer_id, c.customer_name, c.city,SUM(o.amount) AS total_purchase_amount
FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name, c.city ORDER BY total_purchase_amount DESC;

SELECT c.customer_id, c.customer_name,SUM(o.amount) AS total_purchase_amount
FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name HAVING SUM(o.amount) > 10000;

SELECT c.customer_name,COUNT(o.order_id) AS total_orders
FROM Customers c LEFT JOIN Orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name;

SELECT c.customer_id, c.customer_name,SUM(o.amount) AS total_purchase_amount
FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name ORDER BY total_purchase_amount DESC LIMIT 1;

SELECT c.customer_id, c.customer_name,COUNT(o.order_id) AS order_count
FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name HAVING COUNT(o.order_id) = (
    SELECT MAX(order_count) FROM (
        SELECT COUNT(*) AS order_count FROM Orders
        GROUP BY customer_id) AS order_counts);
        
SELECT c.customer_id, c.customer_name,AVG(o.amount) AS average_order_amount
FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name HAVING AVG(o.amount) > 2000;

SELECT c.customer_id, c.customer_name,SUM(o.amount) AS total_purchase_amount
FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name ORDER BY total_purchase_amount DESC LIMIT 5;

SELECT c.customer_id, c.customer_name,MIN(o.amount) AS minimum_order_amount
FROM Customers c JOIN Orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name;

SELECT c.customer_id, c.customer_name,SUM(o.amount) AS total_purchase_amount
FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name HAVING SUM(o.amount) > 5000;

SELECT c.customer_id, c.customer_name,
       COUNT(o.order_id) AS total_orders,
       SUM(o.amount) AS total_purchase_amount
FROM Customers c JOIN Orders o
ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name;

SELECT c.customer_id, c.customer_name,
       COUNT(o.order_id) AS total_orders,
       SUM(o.amount) AS total_purchase_amount
FROM Customers c
JOIN Orders o
ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name
HAVING COUNT(o.order_id) > 2
   AND SUM(o.amount) > 8000;
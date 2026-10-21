CREATE DATABASE banking;

USE banking;
CREATE TABLE customers (
customer_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50),
city VARCHAR(50));

DESC customers;

SELECT * FROM customers;

ALTER TABLE customers CHANGE age city VARCHAR(50);

CREATE TABLE accounts (
account_id INT PRIMARY KEY AUTO_INCREMENT,
customer_id INT,
account_type VARCHAR(20),
balance DECIMAL(10, 2));

DESC accounts;
SELECT * FROM accounts;
INSERT INTO customers (name, city) VALUES 
('Janani','Chennai'), 
('Arun','Coimbatore'), 
('Priya','Madurai'), 
('Karthik','Salem');

ALTER TABLE accounts
ADD CONSTRAINT fk_accounts_customer
FOREIGN KEY (customer_id)
REFERENCES customers(customer_id);

INSERT INTO accounts (customer_id, account_type, balance) VALUES
(1,'Savings',50000),
(1,'Current',20000),
(2,'Savings',30000),
(3,'Savings',15000),
(4,'Current',40000);

SELECT * FROM accounts WHERE balance > 20000;

SELECT * FROM customers WHERE city = 'Chennai';

SELECT * FROM accounts WHERE balance BETWEEN 20000 AND 50000;

SELECT * FROM customers WHERE name LIKE 'J%';

SELECT * FROM accounts WHERE account_type IN ('Savings', 'Current');

SELECT * FROM accounts WHERE account_type <> 'Savings';

SELECT * FROM customers WHERE name LIKE '%a%';

SELECT * FROM accounts WHERE balance <= 30000;

SELECT * FROM customers WHERE city <> 'Madurai';

SELECT * FROM accounts WHERE balance NOT BETWEEN 10000 AND 40000;

SELECT * FROM customers WHERE name LIKE '%i';

SELECT * FROM accounts WHERE balance = 50000;

SELECT * FROM customers WHERE city IN ('Chennai', 'Salem');

SELECT * FROM accounts WHERE balance > 10000 AND balance < 40000;

SELECT * FROM accounts WHERE account_type NOT IN ('Current');

SELECT * FROM accounts ORDER BY balance DESC;

SELECT * FROM customers ORDER BY name ASC;

SELECT * FROM accounts ORDER BY account_type ASC, balance DESC;

SELECT SUM(balance) AS total_balance FROM accounts;

SELECT AVG(balance) AS average_balance FROM accounts;

SELECT MAX(balance) AS maximum_balance FROM accounts;

SELECT MIN(balance) AS minimum_balance FROM accounts;

SELECT COUNT(*) AS total_customers FROM customers;

SELECT account_type, SUM(balance) AS total_balance FROM accounts GROUP BY account_type;

SELECT account_type, AVG(balance) AS average_balance FROM accounts GROUP BY account_type;

SELECT account_type, AVG(balance) AS average_balance FROM accounts GROUP BY account_type HAVING AVG(balance) > 20000;

SELECT customer_id, COUNT(*) AS account_count FROM accounts GROUP BY customer_id;

SELECT customer_id, COUNT(*) AS account_count FROM accounts GROUP BY customer_id HAVING COUNT(*) > 1;

SELECT customers.name, accounts.balance FROM customers JOIN accounts ON customers.customer_id = accounts.customer_id;

SELECT customers.name, accounts.account_type, accounts.balance
FROM customers LEFT JOIN accounts ON customers.customer_id = accounts.customer_id;

SELECT accounts.account_id,
       customers.name,
       customers.city,
       accounts.account_type,
       accounts.balance
FROM accounts JOIN customers ON accounts.customer_id = customers.customer_id;

SELECT customers.name, accounts.account_type
FROM customers JOIN accounts
ON customers.customer_id = accounts.customer_id WHERE accounts.balance > 20000;

SELECT customers.name, SUM(accounts.balance) AS total_balance
FROM customers JOIN accounts
ON customers.customer_id = accounts.customer_id GROUP BY customers.name;

SELECT customers.name, accounts.balance
FROM customers JOIN accounts
ON customers.customer_id = accounts.customer_id ORDER BY accounts.balance ASC;

SELECT customers.city, COUNT(accounts.account_id) AS account_count
FROM customers JOIN accounts
ON customers.customer_id = accounts.customer_id GROUP BY customers.city;

SELECT * FROM accounts WHERE balance > (SELECT AVG(balance) FROM accounts);

SELECT DISTINCT customers.name
FROM customers JOIN accounts ON customers.customer_id = accounts.customer_id;

SELECT customers.name FROM customers LEFT JOIN accounts
ON customers.customer_id = accounts.customer_id WHERE accounts.account_id IS NULL;

SELECT * FROM accounts WHERE balance = (SELECT MAX(balance) FROM accounts);

SELECT customers.name, SUM(accounts.balance) AS total_balance
FROM customers
JOIN accounts
ON customers.customer_id = accounts.customer_id
GROUP BY customers.name
HAVING SUM(accounts.balance) > 40000;
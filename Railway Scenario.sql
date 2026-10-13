SHOW TABLES;
SHOW DATABASES;

CREATE DATABASE railway;

USE railway;

CREATE TABLE trains (
    train_id INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(50),
    source VARCHAR(50),
    destination VARCHAR(50));
    
    CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    passenger_name VARCHAR(50),
    fare DECIMAL(10,2),
    status VARCHAR(20),
    FOREIGN KEY (train_id) REFERENCES trains(train_id));
    
INSERT INTO trains (train_name, source, destination) VALUES
('Express1', 'Chennai', 'Madurai'),
('Express2', 'Coimbatore', 'Salem'),
('Express3', 'Madurai', 'Chennai');

INSERT INTO bookings (train_id, passenger_name, fare, status) VALUES
(1, 'Janani', 500, 'Confirmed'),
(1, 'Arun', 500, 'Waiting'),
(2, 'Priya', 300, 'Confirmed'),
(3, 'Karthik', 450, 'Cancelled'),
(2, 'Meena', 300, 'Confirmed');

SELECT * FROM bookings WHERE fare > 400;

SELECT * FROM bookings WHERE status <> 'Confirmed';

SELECT * FROM trains WHERE source = 'Chennai';

SELECT * FROM bookings WHERE fare BETWEEN 300 AND 500;

SELECT * FROM bookings WHERE passenger_name LIKE 'A%';

SELECT trains.train_name, bookings.passenger_name FROM trains
JOIN bookings ON trains.train_id = bookings.train_id;

SELECT train_id, COUNT(*) AS booking_count FROM bookings GROUP BY train_id;

SELECT trains.train_name, SUM(bookings.fare) AS total_fare
FROM trains JOIN bookings
ON trains.train_id = bookings.train_id
GROUP BY trains.train_id, trains.train_name;

SELECT * FROM bookings WHERE fare = (SELECT MAX(fare) FROM bookings);
 
SELECT trains.train_name, COUNT(bookings.booking_id) AS booking_count
FROM trains JOIN bookings
ON trains.train_id = bookings.train_id
GROUP BY trains.train_id, trains.train_name
HAVING COUNT(bookings.booking_id) > 1;
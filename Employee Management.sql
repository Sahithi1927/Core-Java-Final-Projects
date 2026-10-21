USE employee_management;

CREATE TABLE Employee (
   emp_id INT PRIMARY KEY,
   emp_name VARCHAR(50),
   department VARCHAR(30),
   salary DECIMAL(10,2),
   city VARCHAR(30),
   joining_date DATE);
   
SHOW TABLES;

SELECT * FROM Employee;

INSERT INTO Employee VALUES
(101,'John','IT',60000,'Chennai','2022-01-15'),
(102,'David','HR',45000,'Bangalore','2021-03-10'),
(103,'Smith','IT',70000,'Chennai','2020-07-12'),
(104,'Mary','Finance',55000,'Mumbai','2023-01-20'),
(105,'James','HR',48000,'Delhi','2022-05-05'),
(106,'Linda','Finance',65000,'Mumbai','2021-08-18');


SELECT department, COUNT(*) AS employee_count FROM Employee GROUP BY department;

SELECT department, AVG(salary) AS average_salary FROM Employee GROUP BY department;

SELECT department, COUNT(*) AS employee_count FROM Employee GROUP BY department HAVING COUNT(*) > 1;

SELECT department, MAX(salary) AS highest_salary FROM Employee GROUP BY department;

SELECT department, MIN(salary) AS lowest_salary FROM Employee GROUP BY department;

SELECT department, AVG(salary) AS average_salary FROM Employee GROUP BY department HAVING AVG(salary) > 50000;

SELECT department, SUM(salary) AS total_salary FROM Employee GROUP BY department;

SELECT * FROM Employee ORDER BY salary DESC;

SELECT * FROM Employee ORDER BY department ASC, salary DESC;

SELECT city, COUNT(*) AS employee_count FROM Employee GROUP BY city HAVING COUNT(*) > 1;

SELECT city, SUM(salary) AS total_salary FROM Employee GROUP BY city;

SELECT department, SUM(salary) AS total_salary
FROM Employee GROUP BY department ORDER BY total_salary DESC;

SELECT department, COUNT(*) AS employee_count 
FROM Employee WHERE salary > 50000 GROUP BY department;

SELECT department,MAX(salary) - MIN(salary) AS salary_difference
FROM Employee GROUP BY department;

SELECT * FROM Employee ORDER BY salary DESC LIMIT 3;
CREATE DATABASE students_db;
USE students_db;

CREATE TABLE Students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(50),
    department VARCHAR(30),
    marks INT);
    
INSERT INTO Students (student_id, student_name, department, marks) VALUES
(1, 'Ravi', 'CSE', 85),(2, 'Priya', 'CSE', 78),(3, 'Arun', 'CSE', 92),
(4, 'Sneha', 'ECE', 88),(5, 'Kiran', 'ECE', 76),(6, 'Anil', 'ECE', 82),
(7, 'Meena', 'EEE', 69),(8, 'Rahul', 'EEE', 74),(9, 'Divya', 'EEE', 81),
(10, 'Vijay', 'IT', 95),(11, 'Pooja', 'IT', 87),(12, 'Suresh', 'IT', 72);

SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department;

SELECT department, AVG(marks) AS average_marks FROM Students
GROUP BY department HAVING AVG(marks) > 75;

SELECT department, MAX(marks) AS highest_marks FROM Students GROUP BY department;

SELECT department, COUNT(*) AS total_students FROM Students GROUP BY department;

SELECT department, COUNT(*) AS total_students FROM Students GROUP BY department HAVING COUNT(*) > 5;

SELECT department, AVG(marks) AS average_marks FROM Students
GROUP BY department ORDER BY average_marks DESC;

SELECT department, AVG(marks) AS average_marks
FROM Students GROUP BY department ORDER BY average_marks DESC LIMIT 3;

SELECT department, AVG(marks) AS average_marks FROM Students
GROUP BY department HAVING AVG(marks) BETWEEN 70 AND 90;

SELECT department, SUM(marks) AS total_marks
FROM Students GROUP BY department;

SELECT department, COUNT(*) AS total_students
FROM Students GROUP BY department
ORDER BY total_students DESC;

SELECT department, MIN(marks) AS lowest_marks
FROM Students GROUP BY department;

SELECT department, MAX(marks) AS highest_marks
FROM Students GROUP BY department HAVING MAX(marks) > 90;

SELECT department,COUNT(*) AS students_above_80
FROM Students WHERE marks > 80 GROUP BY department;

SELECT department,COUNT(*) AS students_above_75
FROM Students WHERE marks > 75 GROUP BY department HAVING COUNT(*) > 3;

SELECT department, MAX(marks) AS highest_marks
FROM Students GROUP BY department ORDER BY highest_marks DESC;
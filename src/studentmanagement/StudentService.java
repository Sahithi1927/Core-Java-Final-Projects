package studentmanagement;

import java.util.HashMap;
import java.util.Map;

public class StudentService {
	private Map<Integer, Student> students = new HashMap<>();

    public void addStudent(Student student) {

        if (students.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student ID already exists!");
        }

        students.put(student.getId(), student);
        System.out.println("Student added successfully.");
    }

  
    public void displayAllStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students.values()) {
            System.out.println(student);
        }
    }

    
    public Student searchById(int id) {

        Student student = students.get(id);

        if (student == null) {
            throw new IllegalArgumentException("Student not found!");
        }

        return student;
    }

  
    public void searchByName(String name) {

        boolean found = false;

        for (Student student : students.values()) {

            if (student.getName().equalsIgnoreCase(name)) {
                System.out.println(student);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Student not found!");
        }
    }

 
    public void updateMarks(int id, double marks) {

        Student student = searchById(id);

        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        }

        student.setMarks(marks);

        System.out.println("Marks updated successfully.");
    }

 
    public void updateAttendance(int id, int attendance) {

        Student student = searchById(id);

        if (attendance < 0 || attendance > 100) {
            throw new IllegalArgumentException("Attendance must be between 0 and 100.");
        }

        student.setAttendance(attendance);

        System.out.println("Attendance updated successfully.");
    }

    public String calculateGrade(int id) {

        Student student = searchById(id);

        double marks = student.getMarks();

        if (marks >= 90) {
            return "A";
        } else if (marks >= 75) {
            return "B";
        } else if (marks >= 60) {
            return "C";
        } else if (marks >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

  
    public void removeStudent(int id) {

        if (!students.containsKey(id)) {
            throw new IllegalArgumentException("Student not found!");
        }

        students.remove(id);

        System.out.println("Student removed successfully.");
    }

    public void displayTopper() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        Student topper = null;

        for (Student student : students.values()) {

            if (topper == null || student.getMarks() > topper.getMarks()) {
                topper = student;
            }
        }

        System.out.println("Topper:");
        System.out.println(topper);
    }
    public Map<Integer, Student> getAllStudents() {
        return students;
    }
}


package studentmanagement;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentService service = new StudentService();
        StudentFileService fileService = new StudentFileService();

        // Load existing students from file
        fileService.loadStudents(service);

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Update Marks");
            System.out.println("6. Update Attendance");
            System.out.println("7. Calculate Grade");
            System.out.println("8. Remove Student");
            System.out.println("9. Display Topper");
            System.out.println("10. Save Students");
            System.out.println("11. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {

                switch (choice) {

                    case 1:

                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Student Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Marks: ");
                        double marks = sc.nextDouble();

                        System.out.print("Enter Attendance: ");
                        int attendance = sc.nextInt();

                        sc.nextLine();

                        System.out.println("Select Status:");
                        System.out.println("1. ACTIVE");
                        System.out.println("2. INACTIVE");
                        System.out.println("3. COMPLETED");

                        System.out.print("Enter status choice: ");
                        int statusChoice = sc.nextInt();

                        StudentStatus status;

                        if (statusChoice == 1) {
                            status = StudentStatus.ACTIVE;
                        } else if (statusChoice == 2) {
                            status = StudentStatus.INACTIVE;
                        } else if (statusChoice == 3) {
                            status = StudentStatus.COMPLETED;
                        } else {
                            throw new IllegalArgumentException("Invalid status choice!");
                        }

                        if (marks < 0 || marks > 100) {
                            throw new IllegalArgumentException(
                                "Marks must be between 0 and 100."
                            );
                        }

                        if (attendance < 0 || attendance > 100) {
                            throw new IllegalArgumentException(
                                "Attendance must be between 0 and 100."
                            );
                        }

                        Student student = new Student(
                            id,
                            name,
                            email,
                            marks,
                            attendance,
                            status
                        );

                        service.addStudent(student);

                        break;

                    case 2:

                        service.displayAllStudents();

                        break;

                    case 3:

                        System.out.print("Enter Student ID: ");
                        int searchId = sc.nextInt();

                        Student foundStudent = service.searchById(searchId);

                        System.out.println(foundStudent);

                        break;

                    case 4:

                        System.out.print("Enter Student Name: ");
                        String searchName = sc.nextLine();

                        service.searchByName(searchName);

                        break;

                    case 5:

                        System.out.print("Enter Student ID: ");
                        int marksId = sc.nextInt();

                        System.out.print("Enter New Marks: ");
                        double newMarks = sc.nextDouble();

                        service.updateMarks(marksId, newMarks);

                        break;

                    case 6:

                        System.out.print("Enter Student ID: ");
                        int attendanceId = sc.nextInt();

                        System.out.print("Enter New Attendance: ");
                        int newAttendance = sc.nextInt();

                        service.updateAttendance(
                            attendanceId,
                            newAttendance
                        );

                        break;

                    case 7:

                        System.out.print("Enter Student ID: ");
                        int gradeId = sc.nextInt();

                        String grade = service.calculateGrade(gradeId);

                        System.out.println("Grade: " + grade);

                        break;

                    case 8:

                        System.out.print("Enter Student ID: ");
                        int removeId = sc.nextInt();

                        service.removeStudent(removeId);

                        break;

                    case 9:

                        service.displayTopper();

                        break;

                    case 10:

                        fileService.saveStudents(service);

                        break;

                    case 11:

                        fileService.saveStudents(service);

                        System.out.println(
                            "Thank you for using Student Management System."
                        );

                        sc.close();

                        return;

                    default:

                        System.out.println("Invalid choice!");

                }

            } catch (IllegalArgumentException e) {

                System.out.println("Error: " + e.getMessage());

            }
        }
    }
}


package management;

import java.sql.*;
import java.util.Scanner;

public class ComplaintManagement {

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    // Database connection
    static void connectDB() {
        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/JDBC","root","Sahithi@3737");
               con.setCatalog("JDBC");
   
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    // Create tables
    static void createTables() {
        String users = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "phone VARCHAR(15) NOT NULL)";

        String officers = "CREATE TABLE IF NOT EXISTS officers (" +
                "officer_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL)";

        String complaints = "CREATE TABLE IF NOT EXISTS complaints (" +
                "complaint_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "user_id INT NOT NULL, " +
                "officer_id INT NULL, " +
                "description VARCHAR(500) NOT NULL, " +
                "status VARCHAR(20) NOT NULL DEFAULT 'Pending', " +
                "FOREIGN KEY (user_id) REFERENCES users(user_id), " +
                "FOREIGN KEY (officer_id) REFERENCES officers(officer_id))";

        try (Statement st = con.createStatement()) {
            st.executeUpdate(users);
            st.executeUpdate(officers);
            st.executeUpdate(complaints);
            System.out.println("All tables created successfully!");
        } catch (SQLException e) {
            System.out.println("Table creation error: " + e.getMessage());
        }
    }

    // Validate non-empty input
    static String readText(String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    // Register user
    static void registerUser() {
        String name = readText("Enter user name: ");
        String phone;

        while (true) {
            phone = readText("Enter phone number: ");
            if (phone.matches("[0-9]{10,15}")) {
                break;
            }
            System.out.println("Enter 10 to 15 digits only.");
        }

        String sql = "INSERT INTO users(name, phone) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
        }
    }

    // Add officer
    static void addOfficer() {
        String name = readText("Enter officer name: ");
        String sql = "INSERT INTO officers(name) VALUES (?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
            System.out.println("Officer added successfully!");
        } catch (SQLException e) {
            System.out.println("Officer error: " + e.getMessage());
        }
    }

    // Create complaint
    static void addComplaint() {
        int userId = readPositiveInt("Enter registered user ID: ");
        String description = readText("Enter complaint description: ");

        String sql = "INSERT INTO complaints(user_id, description) " +
                     "VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, description);
            ps.executeUpdate();
            System.out.println("Complaint registered successfully!");
        } catch (SQLException e) {
            System.out.println("Complaint error: " + e.getMessage());
        }
    }

    // Validate positive integer
    static int readPositiveInt(String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            try {
                int number = Integer.parseInt(value);
                if (number > 0) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // Display validation message below
            }

            System.out.println("Enter a valid positive number.");
        }
    }

    // Read: display complaints
    static void viewComplaints() {
        String sql = "SELECT c.complaint_id, u.name, c.description, " +
                     "c.status, o.name AS officer_name " +
                     "FROM complaints c " +
                     "JOIN users u ON c.user_id = u.user_id " +
                     "LEFT JOIN officers o ON c.officer_id = o.officer_id";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                    "ID: " + rs.getInt("complaint_id") +
                    " | User: " + rs.getString("name") +
                    " | Description: " + rs.getString("description") +
                    " | Status: " + rs.getString("status") +
                    " | Officer: " + rs.getString("officer_name")
                );
            }
        } catch (SQLException e) {
            System.out.println("Display error: " + e.getMessage());
        }
    }

    // Update complaint status
    static void updateComplaint() {
        int id = readPositiveInt("Enter complaint ID: ");

        System.out.println("1. Pending");
        System.out.println("2. In Progress");
        System.out.println("3. Resolved");

        int choice = readPositiveInt("Choose status: ");
        String status;

        switch (choice) {
            case 1:
                status = "Pending";
                break;
            case 2:
                status = "In Progress";
                break;
            case 3:
                status = "Resolved";
                break;
            default:
                System.out.println("Invalid status choice!");
                return;
        }

        String sql = "UPDATE complaints SET status = ? WHERE complaint_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0
                ? "Complaint updated successfully!"
                : "Complaint ID not found.");
        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }

    // Delete complaint
    static void deleteComplaint() {
        int id = readPositiveInt("Enter complaint ID to delete: ");
        String sql = "DELETE FROM complaints WHERE complaint_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0
                ? "Complaint deleted successfully!"
                : "Complaint ID not found.");
        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }

    // Main method
    public static void main(String[] args) {
        connectDB();

        if (con == null) {
            System.out.println("Cannot continue without database connection.");
            return;
        }

        createTables();

        while (true) {
            System.out.println("\n--- COMPLAINT MANAGEMENT SYSTEM ---");
            System.out.println("1. Register User");
            System.out.println("2. Add Officer");
            System.out.println("3. Register Complaint");
            System.out.println("4. View Complaints");
            System.out.println("5. Update Complaint Status");
            System.out.println("6. Delete Complaint");
            System.out.println("7. Exit");

            int choice = readPositiveInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    addOfficer();
                    break;
                case 3:
                    addComplaint();
                    break;
                case 4:
                    viewComplaints();
                    break;
                case 5:
                    updateComplaint();
                    break;
                case 6:
                    deleteComplaint();
                    break;
                case 7:
                    try {
                        con.close();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    sc.close();
                    System.out.println("Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Select 1 to 7.");
            }
        }
    }
}

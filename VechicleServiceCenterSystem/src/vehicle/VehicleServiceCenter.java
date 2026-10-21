package vehicle;

import java.sql.*;
import java.util.Scanner;

public class VehicleServiceCenter {

    static final String URL = "jdbc:mysql://localhost:3306/JDBC";
    static final String USER = "root";
    static final String PASSWORD = "Sahithi@3737";

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            // Connect to MySQL server
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database connected successfully!");

            // Create database automatically
            try (Statement st = con.createStatement()) {
                st.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS vehicle_service_db"
                );
            }

            // Select database
            con.setCatalog("vehicle_service_db");

            // Create all tables automatically
            createTables();

            System.out.println("Database and tables created successfully!");

            int choice;

            do {
                System.out.println("\n===== VEHICLE SERVICE CENTER =====");
                System.out.println("1. Register Customer");
                System.out.println("2. Register Vehicle");
                System.out.println("3. Book Vehicle Service");
                System.out.println("4. View Service Records");
                System.out.println("5. Update Service Status");
                System.out.println("6. Delete Service Record");
                System.out.println("7. View Customers");
                System.out.println("8. View Vehicles");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");

                choice = readInt();

                switch (choice) {
                    case 1:
                        registerCustomer();
                        break;

                    case 2:
                        registerVehicle();
                        break;

                    case 3:
                        bookService();
                        break;

                    case 4:
                        viewServiceRecords();
                        break;

                    case 5:
                        updateServiceStatus();
                        break;

                    case 6:
                        deleteServiceRecord();
                        break;

                    case 7:
                        viewCustomers();
                        break;

                    case 8:
                        viewVehicles();
                        break;

                    case 9:
                        System.out.println("Thank you!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } while (choice != 9);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());

        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing database connection.");
            }

            sc.close();
        }
    }

    // Create database tables
    static void createTables() throws SQLException {

        try (Statement st = con.createStatement()) {

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS customers (" +
                "customer_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "phone VARCHAR(15) NOT NULL UNIQUE)"
            );

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS vehicles (" +
                "vehicle_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "customer_id INT NOT NULL, " +
                "vehicle_number VARCHAR(20) NOT NULL UNIQUE, " +
                "model VARCHAR(100) NOT NULL, " +
                "FOREIGN KEY (customer_id) " +
                "REFERENCES customers(customer_id))"
            );

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS service_records (" +
                "service_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "vehicle_id INT NOT NULL, " +
                "service_date DATE NOT NULL, " +
                "service_type VARCHAR(100) NOT NULL, " +
                "cost DECIMAL(10,2) NOT NULL, " +
                "status VARCHAR(30) NOT NULL DEFAULT 'Booked', " +
                "FOREIGN KEY (vehicle_id) " +
                "REFERENCES vehicles(vehicle_id))"
            );
        }
    }

    // Read an integer safely
    static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }

        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    // Register a customer
    static void registerCustomer() throws SQLException {

        System.out.print("Enter customer name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine().trim();

        if (name.isEmpty() || !phone.matches("[0-9]{10,15}")) {
            System.out.println("Invalid name or phone number.");
            return;
        }

        String sql =
            "INSERT INTO customers(name, phone) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.executeUpdate();

            System.out.println("Customer registered successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("This phone number is already registered.");
        }
    }

    // Register a vehicle
    static void registerVehicle() throws SQLException {

        System.out.print("Enter customer ID: ");
        int customerId = readInt();

        System.out.print("Enter vehicle number: ");
        String vehicleNumber = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter vehicle model: ");
        String model = sc.nextLine().trim();

        if (customerId <= 0 || vehicleNumber.isEmpty()
                || model.isEmpty()) {
            System.out.println("Please enter valid details.");
            return;
        }

        String sql =
            "INSERT INTO vehicles(customer_id, vehicle_number, model) " +
            "VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setString(2, vehicleNumber);
            ps.setString(3, model);
            ps.executeUpdate();

            System.out.println("Vehicle registered successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(
                "Customer ID does not exist or vehicle number is duplicate."
            );
        }
    }

    // Book a vehicle service
    static void bookService() throws SQLException {

        System.out.print("Enter vehicle ID: ");
        int vehicleId = readInt();

        System.out.print("Enter service date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine().trim();

        System.out.print("Enter service type: ");
        String serviceType = sc.nextLine().trim();

        System.out.print("Enter service cost: ");
        String costInput = sc.nextLine().trim();

        if (vehicleId <= 0 || serviceType.isEmpty()) {
            System.out.println("Invalid vehicle ID or service type.");
            return;
        }

        Date serviceDate;
        double cost;

        try {
            serviceDate = Date.valueOf(dateInput);
            cost = Double.parseDouble(costInput);

            if (cost < 0 || !Double.isFinite(cost)) {
                System.out.println("Enter a valid non-negative cost.");
                return;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Enter a valid date and numeric cost.");
            return;
        }

        String sql =
            "INSERT INTO service_records " +
            "(vehicle_id, service_date, service_type, cost) " +
            "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            ps.setDate(2, serviceDate);
            ps.setString(3, serviceType);
            ps.setDouble(4, cost);
            ps.executeUpdate();

            System.out.println("Service booked successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Vehicle ID does not exist.");
        }
    }

    // View service records
    static void viewServiceRecords() throws SQLException {

        String sql =
            "SELECT sr.service_id, c.name, v.vehicle_number, v.model, " +
            "sr.service_date, sr.service_type, sr.cost, sr.status " +
            "FROM service_records sr " +
            "JOIN vehicles v ON sr.vehicle_id = v.vehicle_id " +
            "JOIN customers c ON v.customer_id = c.customer_id " +
            "ORDER BY sr.service_id";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("\nService ID: "
                    + rs.getInt("service_id"));
                System.out.println("Customer: "
                    + rs.getString("name"));
                System.out.println("Vehicle Number: "
                    + rs.getString("vehicle_number"));
                System.out.println("Model: "
                    + rs.getString("model"));
                System.out.println("Service Date: "
                    + rs.getDate("service_date"));
                System.out.println("Service Type: "
                    + rs.getString("service_type"));
                System.out.println("Cost: "
                    + rs.getBigDecimal("cost"));
                System.out.println("Status: "
                    + rs.getString("status"));
            }

            if (!found) {
                System.out.println("No service records found.");
            }
        }
    }

    // Update service status
    static void updateServiceStatus() throws SQLException {

        System.out.print("Enter service ID: ");
        int serviceId = readInt();

        System.out.println("1. Booked");
        System.out.println("2. In Progress");
        System.out.println("3. Completed");
        System.out.println("4. Cancelled");
        System.out.print("Choose new status: ");

        int option = readInt();
        String status;

        switch (option) {
            case 1:
                status = "Booked";
                break;

            case 2:
                status = "In Progress";
                break;

            case 3:
                status = "Completed";
                break;

            case 4:
                status = "Cancelled";
                break;

            default:
                System.out.println("Invalid status choice.");
                return;
        }

        String sql =
            "UPDATE service_records SET status = ? WHERE service_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, serviceId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Service status updated successfully!");
            } else {
                System.out.println("Service ID not found.");
            }
        }
    }

    // Delete a service record
    static void deleteServiceRecord() throws SQLException {

        System.out.print("Enter service ID to delete: ");
        int serviceId = readInt();

        String sql =
            "DELETE FROM service_records WHERE service_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, serviceId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Service record deleted successfully!");
            } else {
                System.out.println("Service ID not found.");
            }
        }
    }

    // View customers
    static void viewCustomers() throws SQLException {

        String sql = "SELECT * FROM customers ORDER BY customer_id";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println(
                    "Customer ID: " + rs.getInt("customer_id") +
                    ", Name: " + rs.getString("name") +
                    ", Phone: " + rs.getString("phone")
                );
            }

            if (!found) {
                System.out.println("No customers found.");
            }
        }
    }

    // View vehicles
    static void viewVehicles() throws SQLException {

        String sql =
            "SELECT v.vehicle_id, v.vehicle_number, v.model, c.name " +
            "FROM vehicles v " +
            "JOIN customers c ON v.customer_id = c.customer_id " +
            "ORDER BY v.vehicle_id";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println(
                    "Vehicle ID: " + rs.getInt("vehicle_id") +
                    ", Number: " + rs.getString("vehicle_number") +
                    ", Model: " + rs.getString("model") +
                    ", Customer: " + rs.getString("name")
                );
            }

            if (!found) {
                System.out.println("No vehicles found.");
            }
        }
    }
}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Employee {
    private final Connection connection; // Database connection
    private final Scanner scanner; // Scanner for user input

    // Constructor to initialize the connection and scanner
    public Employee(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Method to add a new employee to the database
    void addEmployee() {
        // Collecting employee details from user input
        System.out.print("First Name: ");
        String firstName = scanner.next();
        System.out.print("Last Name: ");
        String lastName = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Phone: ");
        String phone = scanner.next();
        System.out.print("Hire Date (YYYY-MM-DD): ");
        String hireDate = scanner.next();
        System.out.print("Job Title: ");
        String job = scanner.next();
        System.out.print("Salary: ");
        double salary = scanner.nextDouble();
        int jobid;

        try {
            // Prepare an SQL query to fetch the job_id based on the job title provided by the user
            String jobID =  "SELECT job_id FROM employee WHERE job_title = ?";
            PreparedStatement pstmt = connection.prepareStatement(jobID);
        
            // Set the job title in the prepared statement (user-provided job title)
            pstmt.setString(1, job);
        
            // Execute the query and get the result set containing the job_id
            ResultSet rs = pstmt.executeQuery();
        
            // Check if a result was returned (i.e., if the job title exists in the table)
            if (rs.next()) {
                // Retrieve the job_id from the result set and convert it to an integer
                jobid = Integer.parseInt(rs.getString("job_id"));
            } else {
                // If the job title is not found, inform the user and exit the method
                System.out.println("Invalid job title. Please try again.");
                return;
            }
        } catch (NumberFormatException | SQLException e) {
            // Handle any exceptions (e.g., SQL issues or number formatting problems)
            System.err.println("Error executing query: " + e.getMessage());
            return;
        }
        

        if (isValidEmail(email) && isValidName(firstName, lastName) && isValidPhone(phone)) {
            try {
                // SQL query to insert the employee details into the employees table
                String sql = "INSERT INTO employees (first_name, last_name, email, phone, hire_date, job_id, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                // Setting the parameters for the PreparedStatement
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.setString(5, hireDate);
                pstmt.setInt(6, jobid);
                pstmt.setDouble(7, salary);

                // Executing the update and checking if the employee was added successfully
                int affectedRow = pstmt.executeUpdate();
                if (affectedRow > 0) {
                    System.out.println("Employee added successfully.");
                } else {
                    System.out.println("Failed to add employee.");
                }
            } catch (SQLException e) {
                // Handling exceptions and displaying error messages
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input. Please make sure all fields are filled correctly.");
        }
    }

    // Method to view all employees in the database
    void viewEmployees() {
        String query = "SELECT * FROM employees"; // SQL query to select all employees
        try (
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();) {
            // Iterating through the result set and displaying employee details
            while (rs.next()) {
            System.out.println("");
            System.out.println("");
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                System.out.println(rs.getInt("employee_id") + "\t" + name + "\t" + rs.getString("email") + "\t"
                        + rs.getString("phone") + "\t" + rs.getString("hire_date") + "\t" + rs.getString("job_title")
                        + "\t" + rs.getDouble("salary"));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            // Handling exceptions and displaying error messages
            System.err.println("Unable to fetch the employee information due to: " + e.getMessage());
        }
    }

    // Method to update an existing employee's details
    void updateEmployee() {
        // Collecting the employee ID and new details from user input
        System.out.print("Enter the employee ID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter the new first name: ");
        String firstName = scanner.next();
        System.out.print("Enter the new last name: ");
        String lastName = scanner.next();
        System.out.print("Enter the new email: ");
        String email = scanner.next();
        System.out.print("Enter the new phone: ");
        String phone = scanner.next();
        System.out.print("Enter the new hire date (YYYY-MM-DD): ");
        String hireDate = scanner.next();
        System.out.print("Enter the new job title: ");
        String job = scanner.next();
        System.out.print("Enter the new salary: ");
        double salary = scanner.nextDouble();

        // SQL query to update the employee details based on employee ID
        String query = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone = ?, hire_date = ?, job_title = ?, salary = ? WHERE employee_id = ?";
        if (isValidEmail(email) && isValidName(firstName, lastName) && isValidPhone(phone)) {
            try (
                    PreparedStatement pstmt = connection.prepareStatement(query);) {
                // Setting the parameters for the PreparedStatement
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.setString(5, hireDate);
                pstmt.setString(6, job);
                pstmt.setDouble(7, salary);
                pstmt.setInt(8, id);

                // Executing the update and checking if the employee was updated successfully
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Employee updated successfully.");
                } else {
                    System.out.println("No employee found with that ID.");
                }
            } catch (Exception e) {
                // Handling exceptions and displaying error messages
                System.err.println("Error updating employee: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input. Please make sure all fields are filled correctly.");
        }
    }

    // Method to delete an employee from the database
    void deleteEmployee() throws SQLException {
        // Collecting the employee ID to delete from user input
        System.out.print("Enter the employee ID to delete: ");
        int id = scanner.nextInt();

        // SQL query to delete the employee based on employee ID
        String query = "DELETE FROM employees WHERE employee_id = ?";
        try (
                PreparedStatement pstmt = connection.prepareStatement(query);) {
            // Setting the employee ID parameter
            pstmt.setInt(1, id);

            // Executing the update and checking if the employee was deleted successfully
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Employee deleted successfully.");
            } else {
                System.out.println("The employee with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            // Handling exceptions and displaying error messages
            System.out.println("Error while deleting employee due to: " + e.getMessage());
        }
    }

    private static boolean isValidEmail(String email) {
        // Simple regex for email validation
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // Method to validate if a name contains only alphabetic characters
    private static boolean isValidName(String fname, String lname) {
        // Allow alphabetic characters and spaces
        String name = fname + " " + lname;
        return name.matches("[a-zA-Z ]+");
    }

    private static boolean isValidPhone(String number) {
        return number.matches("[0-9]+");
    }
}

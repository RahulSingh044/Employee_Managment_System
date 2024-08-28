import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Department {
    private final Connection connection; // Database connection
    private final Scanner scanner;

    public Department(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    private static boolean isValidName(String dep) {
        // Allow alphabetic characters and space
        return dep.matches("[A-Z ]+");
    }

    void adddepartment() throws SQLException {
        // Collecting the department details from user input
        System.out.print("Enter the department name: ");
        String depName = scanner.nextLine().toUpperCase();

        if (isValidName(depName)) {
            try {
                String sql = "INSERT INTO department (department_name) VALUES (?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, depName);
                int affectedrow = pstmt.executeUpdate();
                if (affectedrow > 0) {
                    System.out.println("Department added Successfully");
                } else {
                    System.out.println("Failed to add Department");
                }
            } catch (SQLException e) {
                System.out.println("ERROR while Insertion" + e.getMessage());
            }
        } else {
            System.out.println("Check the details again !!");
        }
    }

    void updateDepartment() throws SQLException {
        System.out.print("Enter the department ID you want to update : ");
        int id = scanner.nextInt();

        scanner.nextLine();

        try {
            String checkDEP = "SELECT 1 FROM department WHERE department_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(checkDEP);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("!! NO SUCH Department IS FOUND IN DATABASE !!");
            } else {
                System.out.print("Enter the Department Name: ");
                String depName = scanner.nextLine();
        
                System.out.print("Enter the Department Location: ");
                String depLocation = scanner.nextLine();
        
                String query = "UPDATE department SET department_name = ?, location = ? WHERE department_id = ?";

                PreparedStatement ps = connection
                        .prepareStatement(query);
                ps.setString(1, depName);
                ps.setString(2, depLocation);
                ps.setInt(3, id);

                int rowAffected = ps.executeUpdate();
                if (rowAffected > 0) {
                    System.out.println("Department updated successfully.");
                } else {
                    System.out.println("Failed to update job.");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    void deleteDepartment() throws SQLException {
        System.out.print("Enter the department ID you want to delete : ");
        int id = scanner.nextInt();

        String query = "DELETE FROM department WHERE department_id = ? ";

        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Department with department_ID " + id + " is Deleted Successfully!!");
            } else {
                System.out.println("Deaprtment ID is not found in the dataBase ");
            }
        } catch (SQLException e) {
            System.err.println("Unable to delete the data due to " + e.getMessage());
        }
    }

    void readDepartment() {
        String query = "SELECT * FROM department"; // SQL query to select all employees
        try (
                PreparedStatement pstmt = connection.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();) {
               System.out.printf("%4s\t%-25s\t%-15s\n","ID","Department","Location");
                while (rs.next()) {
                    System.out.printf(String.format("%4d\t", rs.getInt("department_id")));
                    System.out.printf(String.format("%-25s\t", rs.getString("department_name")));
                    System.out.printf(String.format("%-15s\n", rs.getString("location")));
                }
                rs.close();
                pstmt.close();
        } catch (Exception e) {
            // Handling exceptions and displaying error messages
            System.err.println("Unable to fetch the employee information due to: " + e.getMessage());
        }
    }

}

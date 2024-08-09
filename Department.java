import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Department {
    private Connection connection; // Database connection
    private Scanner scanner;

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

        if(isValidName(depName)) {
            try {
                String sql = "INSERT INTO departments (department_name) VALUES (?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, depName);
                int affectedrow = pstmt.executeUpdate();
                if(affectedrow > 0) {
                    System.out.println("Department added Successfully");
                }
                else{
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

        System.out.print("Enter the Department Name: ");
        String depName = scanner.nextLine().toUpperCase();

        String query = "UPDATE departments SET department_name = ? WHERE department_id = ?";
        try (
            PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setString(1, depName);
            pstmt.setInt(2, id);
            int affectedrow = pstmt.executeUpdate();
            if (affectedrow > 0) {
                System.out.println("Department updated successfully.");
            } else {
                System.out.println("No department found with the " + id + " ID.");
            }
        } catch (Exception e) {
            System.out.print("Errors due to "+ e.getMessage());
        }
    }

    void deleteDepartment() throws SQLException {
        System.out.print("Enter the department ID you want to delete : ");
        int id = scanner.nextInt();

        String query = "DELETE FROM departmentS WHERE department_id = ? ";

        try( PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setInt(1,id);
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Department with department_ID "+ id + " is Deleted Successfully!!");
            } else {
                System.out.println("Deaprtment ID is not found in the dataBase ");
            }
        }catch (SQLException e) {
            System.err.println("Unable to delete the data due to "+ e.getMessage());
        }
    }

}

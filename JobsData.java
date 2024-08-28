import java.sql.*;
import java.util.Scanner;

public class JobsData {
    private final Connection connection;
    private final Scanner scanner;

    public JobsData(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    void addJob() {
        System.out.print("Enter JOB_Title: ");
        String title = scanner.next();
        System.out.print("Enter min_Salary: ");
        double minSalary = scanner.nextDouble();
        System.out.println("Enter max_Salary: ");
        double maxSalary = scanner.nextDouble();

        try {
            String sql = "Insert into job (job_title,min_Salary, max_Salary) values (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setDouble(2, minSalary);
            pstmt.setDouble(3, maxSalary);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Job added successfully.");
            } else {
                System.out.println("Failed to add job.");
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    void updateJob() {
        System.out.print("Enter Job ID you want to update: ");
        int id = scanner.nextInt();

        try {
            String checkJOB = "SELECT 1 FROM job WHERE job_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(checkJOB);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("!! NO SUCH JOB IS FOUND IN DATABASE !!");
            } else {
            scanner.nextLine();
                System.out.print("Enter the updated JOB_title: ");
                String jbTitle = scanner.nextLine();
                System.out.print("Enter updated min_Salary: ");
                double minSalary = scanner.nextDouble();
                System.out.print("Enter updates max_Salary: ");
                double maxSalary = scanner.nextDouble();

                PreparedStatement ps = connection
                        .prepareStatement("UPDATE job SET job_title = ?, min_Salary = ?, max_Salary = ? WHERE job_title = ?");
                ps.setString(1, jbTitle);
                ps.setDouble(2, minSalary);
                ps.setDouble(3, maxSalary);
                ps.setInt(4,id);
                int rowAffected = ps.executeUpdate();
                if (rowAffected > 0) {
                    System.out.println("Job updated successfully.");
                } else {
                    System.out.println("Failed to update job.");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    void deleteJOB() {
        System.out.print("Enter Job_Title you want to DELETE: ");
        int id = scanner.nextInt();

        try (
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM job WHERE job_id =?");

        ) {
            pstmt.setInt(1, id);
            int rowAffected = pstmt.executeUpdate();

            if(rowAffected >= 0){
                System.out.println("Job deleted successfully.");
            } else {
                System.out.println("No job found with the given ID.");
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    void readJOB() {
        try (
                PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM job");
                ResultSet rs = pstmt.executeQuery();) {
            System.out.printf("%4s\t%-30s\t%-10s\t%-10s\n\n", "ID", "Job", "MinSalary", "MaxSalary");
            while (rs.next()) {
                System.out.print(String.format("%4d\t", rs.getInt("job_id")));
                System.out.print(String.format("%-30s\t", rs.getString("job_title")));
                System.out.print(String.format("%-10.2f\t", rs.getDouble("min_salary")));
                System.out.print(String.format("%-10.2f\t", rs.getDouble("max_salary")));

                System.out.println("\n");
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}

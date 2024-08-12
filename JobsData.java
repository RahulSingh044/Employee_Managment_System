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
            String sql = "Insert into Jobs (job_title,min_Salary, max_Salary) values (?,?,?)";
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
        System.out.print("Enter Job_Title you want to update: ");
        String title = scanner.nextLine();

        try {
            String checkJOB = "SELECT 1 FROM job WHERE job_title = ?";
            PreparedStatement pstmt = connection.prepareStatement(checkJOB);
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("!! NO SUCH JOB IS FOUND IN DATABASE !!");
            } else {
                System.out.println("Enter the updated JOB_title: ");
                String jbTitle = scanner.nextLine();
                System.out.print("Enter updated min_Salary: ");
                double minSalary = scanner.nextDouble();
                System.out.println("Enter updates max_Salary: ");
                double maxSalary = scanner.nextDouble();

                PreparedStatement ps = connection
                        .prepareStatement("Insert into Jobs (job_title,min_Salary, max_Salary) values (?,?,?)");
                ps.setString(1, jbTitle);
                ps.setDouble(2, minSalary);
                ps.setDouble(2, maxSalary);

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

    void deleteJOB(){
        System.out.print("Enter Job_Title you want to DELETE: ");
        String title = scanner.nextLine();

        try {
            String checkJOB = "SELECT 1 FROM job WHERE job_title = ?";
            PreparedStatement pstmt = connection.prepareStatement(checkJOB);
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("!! NO SUCH JOB IS FOUND IN DATABASE !!");
            } else {
                PreparedStatement ps = connection
                        .prepareStatement("DELETE FROM job WHERE job_title = "+title+"");
    
                int rowAffected = ps.executeUpdate();
                if (rowAffected > 0) {
                    System.out.println("Job Deleted successfully.");
                } else {
                    System.out.println("Failed to Delete job.");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    void readJOB(){
    
        try(
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM job");
            ResultSet rs = pstmt.executeQuery();
            ){
                if(rs.next()){
                    do{
                        System.out.println(rs.getInt("job_id")+ "\t" + rs.getString("job_title")+ "\t" + rs.getDouble("min_salary")+ "\t" + rs.getDouble("max_salary"));
                    } while(rs.next());
                }
            }
            catch(SQLException e){
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
    }

}

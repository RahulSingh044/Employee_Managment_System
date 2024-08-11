import java.sql.*;
import java.util.Scanner;

public class ProjectData {
    private final Connection connection; // Database connection
    private final Scanner scanner;
    
    public ProjectData(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    void addProject(){
        System.out.print("Enter project name: ");
        String name = scanner.next();
        System.out.print("Enter project Start Date: ");
        String startDate = scanner.next();
        System.out.print("Enter project End Date: ");
        String endDate = scanner.next();
        System.out.print("Enter project Budget: ");
        double budget = scanner.nextDouble();

        // SQL query to insert a new project into the projects table
        String query = "INSERT INTO projects (project_name, start_date, end_date, budget) VALUES (?,?,?,?)";

        try (
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setString(1, name);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);
            pstmt.setDouble(4, budget);
            pstmt.executeUpdate();
            System.out.println("Project added successfully.");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    void updateProject(){
    System.out.println("Enter project name you want to update:  ");
    String name = scanner.next();
    System.out.print("Enter new start date: ");
    String newStartDate = scanner.next();
    System.out.print("Enter new end date: ");
    String newEndDate = scanner.next();
    System.out.print("Enter new budget: ");
    double newBudget = scanner.nextDouble();

    try (
        PreparedStatement pstmt = connection.prepareStatement("UPDATE projects SET start_date =?, end_date =?, budget =? WHERE project_name =?");
        ) {
            pstmt.setString(1, newStartDate);
            pstmt.setString(2, newEndDate);
            pstmt.setDouble(3, newBudget);
            pstmt.setString(4, name);
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Project updated successfully.");
            } else{
                System.out.println("No project found with the given name.");
            }
         } 
         catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
}

    void deleteProject(){
        System.out.println("Enter project name you want to delete:  ");
        String name = scanner.next();

        try(
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM project WHERE project_name = ?");
        ) {
            pstmt.setString(1,name);

            int RowAffected = pstmt.executeUpdate();
            if(RowAffected > 0) {
                System.out.println("Project deleted successfully.");
            } else{
                System.out.println("No project found with the given name.");
            }
        
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
}

    void ReadProject() {

        try(
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM project");
            ResultSet rs = pstmt.executeQuery();
        ) {
            if(rs.next()) {
                do{
                    System.out.println(rs.getInt("project_id")+ "\t" + rs.getString("project_name")+ "\t" + rs.getString("start_date")+ "\t" + rs.getString("end_date")+ "\t" + rs.getDouble("budget"));
                } while(rs.next());
            }
            else {
                System.out.println("!!! NO RECORDS YET !!!");
            }
        } catch(SQLException e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}

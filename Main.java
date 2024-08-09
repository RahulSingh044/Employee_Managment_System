import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static final Scanner scanner = new Scanner(System.in);

    static void createConnections() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/employee_ms";
            String user = "root";
            String password = "rahhul_36";

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully!");
        } catch (SQLException e) {
            System.out.println("Unable to connect to Database due to " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        Main mn = new Main();
        try {
            // Establishing connection to the databaseq
            createConnections();
        
            boolean exit = false;

            while (!exit) {
            String str = "";
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println("                Welcome to Employee Management SYSTEM");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");

                System.out.println("MenuBar:");
                System.out.println("1. Employee Data");
                System.out.println("2. Department Data");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int x = scanner.nextInt();
                switch (x) {
                    case 1 -> mn.EmployeeIntro();
                    case 2 -> mn.DepartmentIntro();
                    case 3 -> exit = true;
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            }

            connection.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    void EmployeeIntro() throws SQLException {
        boolean isEmployee = false;
        Employee emp = new Employee(connection, scanner);
        System.out.println("");
        System.out.println("");
        System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println("                    Welcome to Employer's Database");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
           
        while (!isEmployee) {
            System.out.println("");
            System.out.println("");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. View Employee Details");
            System.out.println("5. Exit");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> emp.addEmployee();
                case 2 -> emp.updateEmployee();
                case 3 -> emp.deleteEmployee();
                case 4 -> emp.viewEmployees();
                case 5 -> isEmployee = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }

        }
    }

    void DepartmentIntro() throws SQLException {
        boolean isDepartment = false;
        Department dpt = new Department(connection, scanner);
        while (!isDepartment) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println("                    Welcome to Department Database");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println("1. Add Department");
            System.out.println("2. Update Department");
            System.out.println("3. Delete Department");
            System.out.println("4. View Department Details");
            System.out.println("5. Exit");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> dpt.adddepartment();
                case 2 -> dpt.updateDepartment();
                case 3 -> dpt.deleteDepartment();
                case 5 -> isDepartment = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

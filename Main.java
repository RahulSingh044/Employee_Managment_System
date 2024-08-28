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
            String url = "jdbc:mysql://localhost:3306/employedb";
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
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println("                Welcome to CODEZ SOLUTION pvt. Ltd.");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");

                System.out.println("Menu_Bar:");
                System.out.println("1. Employee");
                System.out.println("2. Department");
                System.out.println("3. Jobs Fields");
                System.out.println("4. Projects Info");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int x = scanner.nextInt();
                switch (x) {
                    case 1 -> mn.EmployeeIntro();
                    case 2 -> mn.DepartmentIntro();
                    case 3 -> mn.Jobsf();
                    case 4 -> mn.ProjectsInfo();
                    case 5 -> exit = true;
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
            System.out.println("                    Welcome to Employers Section");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
           
        while (!isEmployee) {
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
            System.out.println("                    Welcome to Department Section");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println("1. Add Department");
            System.out.println("2. Update Department");
            System.out.println("3. Delete Department");
            System.out.println("4. View Department Details");
            System.out.println("5. Exit");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.print("Enter your Choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> dpt.adddepartment();
                case 2 -> dpt.updateDepartment();
                case 3 -> dpt.deleteDepartment();
                case 4 -> dpt.readDepartment();
                case 5 -> isDepartment = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    void Jobsf() throws SQLException {
        boolean isJOB = false;
        JobsData jb = new JobsData(connection,scanner);
        while (!isJOB) {
        System.out.println("");
        System.out.println("");
        System.out.println(
            "------------------------------------------------------------------------------------------------");
    System.out.println("                    Welcome to Jobs Fields");
    System.out.println(
            "------------------------------------------------------------------------------------------------");
            System.out.println("1. Add JOB");
            System.out.println("2. Update JOB");
            System.out.println("3. Delete JOB");
            System.out.println("4. View JOB Details");
            System.out.println("5. Exit");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> jb.addJob();
                case 2 -> jb.updateJob();
                case 3 -> jb.deleteJOB();
                case 4 -> jb.readJOB();
                case 5 -> isJOB = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    void ProjectsInfo() throws SQLException {
        boolean isProject = false;
        ProjectData pd = new ProjectData(connection, scanner);
        while(!isProject) {
        System.out.println("");
        System.out.println("");
        System.out.println(
            "------------------------------------------------------------------------------------------------");
    System.out.println("                    Welcome to Projects Details");
    System.out.println(
            "------------------------------------------------------------------------------------------------");
            System.out.println("1. Add Project");
            System.out.println("2. Update Project");
            System.out.println("3. Delete Project");
            System.out.println("4. View Projects");
            System.out.println("5. Exit");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> pd.addProject();
                case 2 -> pd.updateProject();
                case 3 -> pd.deleteProject();
                case 4 -> pd.ReadProject();
                case 5 -> isProject = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}


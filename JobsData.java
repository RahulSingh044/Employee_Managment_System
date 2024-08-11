import java.sql.Connection;
import java.util.Scanner;

public class JobsData {
     private final Connection connection; 
    private final Scanner scanner;

    public JobsData(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

}

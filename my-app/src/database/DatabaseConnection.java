package database;
import java.sql.*;

public class DatabaseConnection {

    private Connection connection = null;

    public DatabaseConnection() {
        String connectionUrl =
            "jdbc:sqlserver://PHOEBE:1433;"
                + "database=HackRice12;"
                + "user=sa;"
                + "password=secret;"
                + "encrypt=true;"
                + "trustServerCertificate=true;";

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("JDBC driver loaded");
        }
        catch (Exception e)
        {
            System.err.println("Error loading JDBC driver");
            e.printStackTrace(System.err);
            System.exit(0);
        }

        try {
            this.connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected to database");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Connect to your database.
    public static void main(String[] args) {

    }
}

package ass2_oisinAeonn.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton class responsible for establishing and maintaining a connection with the database. 
// It ensures that only a single connection instance is active throughout the application's lifetime.

public class DatabaseConnector {

    // Database connection parameters

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ass2_oisinAeonn";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASS = "";

    // The singleton instance of the DatabaseConnector
    
    private static DatabaseConnector instance;

    // Represents the active database connection
    
    private Connection connection;

    // Private constructor to prevent external instantiation.
     
    private DatabaseConnector() {
    
        try {
    
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
            System.out.println("Failed to establish connection.");
        
        }
    
    }

    // Returns the singleton instance of the DatabaseConnector
    // If there isn't one it will create a new one

    public static DatabaseConnector getInstance() {
    
        if (instance == null) {
    
            // Synchronized block to prevent multi-threading issues
            
            synchronized (DatabaseConnector.class) {
    
                if (instance == null) {
    
                    instance = new DatabaseConnector();
    
                }
    
            }
    
        }
    
        return instance;
    
    }

    // Gets the active Database Connection (called externally)

    public Connection getConnection() {
    
        return this.connection;
    
    }

}

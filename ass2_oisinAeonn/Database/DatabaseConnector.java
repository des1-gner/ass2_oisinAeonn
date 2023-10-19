package ass2_oisinAeonn.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ass2_oisinAeonn";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASS = "";
    
    private static DatabaseConnector instance;
    private Connection connection;

    private DatabaseConnector() {
    
        try {
    
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
            System.out.println("Failed to establish connection.");
        
        }
    
    }

    public static DatabaseConnector getInstance() {
    
        if (instance == null) {
    
            synchronized (DatabaseConnector.class) {
    
                if (instance == null) {
    
                    instance = new DatabaseConnector();
    
                }
    
            }
    
        }
    
        return instance;
    
    }

    public Connection getConnection() {
    
        return this.connection;
    
    }

}
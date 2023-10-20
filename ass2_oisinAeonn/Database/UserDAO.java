package ass2_oisinAeonn.Database;

import ass2_oisinAeonn.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Alert;

// Class including methods to insert, alter, and delete entries in the database to do with the users table

public class UserDAO {

    // Singleton pattern to ensure a single instance of UserDAO

    private static UserDAO instance = null;
    private Connection conn;

    // Private constructor to prevent external instantiation

    public UserDAO() {
    
        this.conn = DatabaseConnector.getInstance().getConnection();
    
    }

    // getInstance() - Singleton pattern method

    public static UserDAO getInstance() {
    
        if (instance == null) {
    
            instance = new UserDAO();
    
        }
    
        return instance;
    
    }

    // updateUserType() - Update the user type in the database for a given username

    public static void updateUserType(String username, String newType) {
    
        String sql = "UPDATE users SET userType = ? WHERE username = ?";
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
    
        try {
    
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newType);
            stmt.setString(2, username);
            stmt.executeUpdate();
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
            System.err.println("Error updating user type for user " + username);
        
        } 
        
        finally {
        
            // Close the PreparedStatement. The connection shouldn't be closed since it's a singleton.
        
            if (stmt != null) {
        
                try {
        
                    stmt.close();
        
                } 
                
                catch (SQLException e) {
                
                    e.printStackTrace();
                
                }
            
            }
        
        }
    
    }

    // getAllUsers() - Fetches all the users from the database
    
    public static List<User> getAllUsers() {
    
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";    
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
    
            stmt = conn.prepareStatement(sql);        
            rs = stmt.executeQuery();
            
            while (rs.next()) {
            
                String username = rs.getString("username");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String password = rs.getString("password");
                String userType = rs.getString("userType");
                String profilePicture = rs.getString("profilePicture");
                
                // Create a User object with the retrieved data
            
                User user = new User(username, firstName, lastName, password, userType, profilePicture); 
                
                users.add(user);
            
            }
            
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
        }
        
        return users;
    
    }

    // updateProfileImagePath() - Update the profile image path for a user in the database

    public static void updateProfileImagePath(String username, String path) {
    
        String sql = "UPDATE users SET profilePicture = ? WHERE username = ?";
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
    
        try {
    
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, path);
            stmt.setString(2, username);
            stmt.executeUpdate();
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
            System.err.println("Error updating profile image path for user " + username);
        
        } 
        
        finally {
        
            // Close the PreparedStatement. The connection shouldn't be closed since it's a singleton.
        
            if (stmt != null) {
        
                try {
        
                    stmt.close();
        
                } 
                
                catch (SQLException e) {
                
                    e.printStackTrace();
                
                }
            
            }
        
        }
    
    }
    
    // deletePostsForUser() - Deletes all the posts for a user from the database
    
    public static void deletePostsForUser(String username) {
    
        String deletePostsQuery = "DELETE FROM posts WHERE author = ?";        
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        
        try {

            preparedStatement = conn.prepareStatement(deletePostsQuery);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
    
            System.out.println("All posts for user " + username + " have been deleted.");
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
            System.err.println("Error deleting posts for user " + username);
        
        } 
        
        finally {
        
        }
    
    }

    // deleteUserByUsername() - Deletes a user by their username from the database
    
    public static void deleteUserByUsername(String username) {
    
        String sql = "DELETE FROM users WHERE username = ?";
        
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        
        try {

            stmt = conn.prepareStatement(sql);     
            stmt.setString(1, username);
            stmt.executeUpdate();
            
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
        }
    
    }

    // registerUser() - Registers a new user in the database

    public static void registerUser(User user) {

        String sql = "INSERT INTO users (username, firstName, lastName, password, userType) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        
        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getUserType());
            stmt.executeUpdate();
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
        }
    
    }

    // checkIfUserExists() - Checks if a user with a given username exists in the database
    
    public static boolean checkIfUserExists(String username) {
    
        String sql = "SELECT username FROM users WHERE username = ?";
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
            
                return true;

            }
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
        }
        
        return false;
    
    }

    // upgradeUserToVIP() - Upgrades a user to a VIP status in the database

    public static void upgradeUserToVIP(String username) {
    
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
        alert.setTitle("Upgrade Successful");
        alert.setHeaderText(null);
        alert.setContentText("Please re-login to gain VIP functionality.");
        alert.showAndWait();
    
        String sql = "UPDATE users SET userType = 'VIP' WHERE username = ?";
    
        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
        }
    
    }

    // upgradeUserToAdmin() - Upgrades a user to an admin status in the database
    
    public static void upgradeUserToAdmin(String username) {
    
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
    
        String sql = "UPDATE users SET userType = admin WHERE username = ?";
    
        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
        }
    
    }

    // fetchPasswordAndUserTypeForUsername() - Fetches the hashed password and user type for a given username
    
    public static UserPasswordAndType fetchPasswordAndUserTypeForUsername(String username) {
    
        String storedPasswordHash = null;
        String userType = null;
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {

            stmt = conn.prepareStatement("SELECT password, userType FROM users WHERE username = ?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
    
                storedPasswordHash = rs.getString("password");
                userType = rs.getString("userType");
    
            }
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();  // Handle in a more user-friendly way in a real application
        
        } 
        
        finally {
        
            // Close the ResultSet
        
            if (rs != null) {
        
                try {
        
                    rs.close();
        
                } 
                
                catch (SQLException e) {
                
                    e.printStackTrace();
                
                }
            
            }
    
            // Close the PreparedStatement
    
            if (stmt != null) {
    
                try {
    
                    stmt.close();
    
                } 
                
                catch (SQLException e) {
                
                    e.printStackTrace();
                
                }
            
            }
    
        }
    
        return new UserPasswordAndType(storedPasswordHash, userType);
    
    }

    // Inner static class - Holds password hash and user type for a user
    
    public static class UserPasswordAndType {
    
        public final String passwordHash;
        public final String userType;

        public UserPasswordAndType(String passwordHash, String userType) {
    
            this.passwordHash = passwordHash;
            this.userType = userType;
    
        }
    
    }

    // formatDateTime() - Helper method to format date time strings

    private static String formatDateTime(String string) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(string, formatter);

        return dateTime.format(formatter);

    }

    // getUsersDistribution() - Fetches the distribution of user types in the database

    public static Map<String, Integer> getUsersDistribution() {

        Map<String, Integer> distribution = new HashMap<>();
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT userType, COUNT(*) as count FROM users GROUP BY userType";
        
        try {

            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
    
            while (rs.next()) {
    
                distribution.put(rs.getString("userType"), rs.getInt("count"));
    
            }
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
    
        } 
        
        finally {
    
        }

        return distribution;

    }

    // deleteUser() - Deletes a user by their username from the database

    public static void deleteUser(String username) {
    
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement preparedStatement = null;

        String deleteQuery = "DELETE FROM users WHERE username = ?";
    
        try {

            preparedStatement = conn.prepareStatement(deleteQuery);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        
            System.out.println("User " + username + " has been deleted.");
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
            System.err.println("Error deleting user " + username);
    
        } 
        
        finally {
    
        }

    }

    // getUserByUsername() - Fetches a User object for a given username

    public static User getUserByUsername(String username) {
    
        User user = null;
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE username = ?";

        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
        
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String password = rs.getString("password");
                String userType = rs.getString("userType");
                String profilePicture = rs.getString("profilePicture");
        
                user = new User(username, firstName, lastName, password, userType, profilePicture);
        
            }
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
        }

        return user;

    }

    // updatePostAuthor() - Updates the author name for all posts of a user

    public static int updatePostAuthor(String currentUsername, String newUsername) {
    
        String sql = "UPDATE posts SET author = ? WHERE author = ?";
        int affectedRows = 0;
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {

        
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newUsername);
            stmt.setString(2, currentUsername);
            affectedRows = stmt.executeUpdate();
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
    
        } 
        
        finally {
    
        }

        return affectedRows;

    }

    // updateUser() - Updates user details for a given username

    public static int updateUser(String currentUsername, String newUsername, String firstName, String lastName, String password) {
        
        String sql = "UPDATE users SET username = ?, firstName = ?, lastName = ?, password = ? WHERE username = ?";
        int affectedRows = 0;
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newUsername);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, password);
            stmt.setString(5, currentUsername);
            affectedRows = stmt.executeUpdate();
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
        }

        return affectedRows;
    
    }

    // updateUserWithoutPassword() - Updates user details excluding password for a given username

    public static void updateUserWithoutPassword(String currentUsername, String newUsername, String firstName, String lastName) {
    
        String sql = "UPDATE users SET username = ?, firstName = ?, lastName = ? WHERE username = ?";
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;

        try {

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newUsername);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, currentUsername);
            stmt.executeUpdate();
     
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
        }
    
    }

}
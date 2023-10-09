package ass2_oisinAeonn.Database;

import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Model.User;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnector {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ass2_oisinAeonn";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASS = "";
    
    // Singleton instance

    private static DatabaseConnector instance;
    private Connection connection;

    // Private constructor
    
    private DatabaseConnector() {
    
        try {
    
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
            System.out.println("Failed to establish connection.");
        
        }
    
    }

    // Public method to access the Singleton instance
    
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

    // Instead of opening a new connection every time, we use the singleton connection
    
    public Connection getConnection() {
    
        return this.connection;
    
    }
    
    public static List<User> getAllUsers() {
    
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";    
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
    
            conn = getInstance().getConnection();
            stmt = conn.prepareStatement(sql);        
            rs = stmt.executeQuery();
            
            while (rs.next()) {
            
                String username = rs.getString("username");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String password = rs.getString("password");
                String userType = rs.getString("userType");
                
                // Create a User object with the retrieved data
            
                User user = new User(username, firstName, lastName, password, userType);  // Assuming your User class has such a constructor
                
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
    
    public static void deletePostsForUser(String username) {
        String deletePostsQuery = "DELETE FROM posts WHERE author = ?";
        
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try {
            conn = getInstance().getConnection();
            preparedStatement = conn.prepareStatement(deletePostsQuery);
    
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
    
            System.out.println("All posts for user " + username + " have been deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error deleting posts for user " + username);
        } finally {
            // Close resources properly.
        }
    }
    
    public static void deleteUserByUsername(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
             
            stmt.setString(1, username);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources properly.
        }
    }
    


public static void insertPost(Post post) {
    String sql = "INSERT INTO posts (author, content, likes, shares, dateTime, image) VALUES (?, ?, ?, ?, ?, ?)";
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, post.getAuthor());
        stmt.setString(2, post.getContent());
        stmt.setInt(3, post.getLikes());
        stmt.setInt(4, post.getShares());
        stmt.setString(5, post.getDateTime());  // No need to format
        stmt.setString(6, post.getImage());  // Assuming it's a string representing path or URL

        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources properly.
    }
}

public static void registerUser(User user) {
    String sql = "INSERT INTO users (username, firstName, lastName, password, userType) VALUES (?, ?, ?, ?, ?)";
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getFirstName());
        stmt.setString(3, user.getLastName());
        stmt.setString(4, user.getPassword());
        stmt.setString(5, user.getUserType());

        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources properly.
    }
}

public static boolean checkIfUserExists(String username) {
    String sql = "SELECT username FROM users WHERE username = ?";
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, username);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources properly.
    }
    
    return false;
}

public static Map<String, Integer> getPostsSharesDistribution() {
    Map<String, Integer> distribution = new HashMap<>();
    Connection conn = null;
    PreparedStatement ps1 = null, ps2 = null, ps3 = null;
    ResultSet rs1 = null, rs2 = null, rs3 = null;
    
    try {
        conn = getInstance().getConnection();

        ps1 = conn.prepareStatement("SELECT COUNT(*) FROM posts WHERE shares BETWEEN 0 AND 99");
        rs1 = ps1.executeQuery();
        if (rs1.next()) {
            distribution.put("0-99", rs1.getInt(1));
        }

        ps2 = conn.prepareStatement("SELECT COUNT(*) FROM posts WHERE shares BETWEEN 100 AND 999");
        rs2 = ps2.executeQuery();
        if (rs2.next()) {
            distribution.put("100-999", rs2.getInt(1));
        }

        ps3 = conn.prepareStatement("SELECT COUNT(*) FROM posts WHERE shares >= 1000");
        rs3 = ps3.executeQuery();
        if (rs3.next()) {
            distribution.put("1000+", rs3.getInt(1));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close all resources properly.
    }
    
    return distribution;
}

    
    public static Map<String, Integer> getPostsLikesDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        Connection conn = null;
        PreparedStatement ps1 = null, ps2 = null, ps3 = null;
        ResultSet rs1 = null, rs2 = null, rs3 = null;
        
        try {
            conn = getInstance().getConnection();
    
            ps1 = conn.prepareStatement("SELECT COUNT(*) FROM posts WHERE likes BETWEEN 0 AND 99");
            rs1 = ps1.executeQuery();
            if (rs1.next()) {
                distribution.put("0-99", rs1.getInt(1));
            }
    
            ps2 = conn.prepareStatement("SELECT COUNT(*) FROM posts WHERE likes BETWEEN 100 AND 999");
            rs2 = ps2.executeQuery();
            if (rs2.next()) {
                distribution.put("100-999", rs2.getInt(1));
            }
    
            ps3 = conn.prepareStatement("SELECT COUNT(*) FROM posts WHERE likes >= 1000");
            rs3 = ps3.executeQuery();
            if (rs3.next()) {
                distribution.put("1000+", rs3.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close all resources properly.
        }
        
        return distribution;
    }
    
    public static void upgradeUserToVIP(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upgrade Successful");
        alert.setHeaderText(null);
        alert.setContentText("Please re-login to gain VIP functionality.");
        alert.showAndWait();
    
        String sql = "UPDATE users SET userType = 'VIP' WHERE username = ?";
        try {
            conn = getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
    
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources properly.
        }
    }
    
    public static void upgradeUserToAdmin(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
    
        String sql = "UPDATE users SET userType = admin WHERE username = ?";
        try {
            conn = getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
    
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources properly.
        }
    }
    

    public static UserPasswordAndType fetchPasswordAndUserTypeForUsername(String username) {
        String storedPasswordHash = null;
        String userType = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = getInstance().getConnection();
            stmt = conn.prepareStatement("SELECT password, userType FROM users WHERE username = ?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                storedPasswordHash = rs.getString("password");
                userType = rs.getString("userType");
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle in a more user-friendly way in a real application
        } finally {
            // Close the ResultSet
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            // Close the PreparedStatement
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
            // No need to close the Connection as it's a singleton connection.
            // If you ever switch to creating a new connection per request, then you'd close it here.
        }
    
        return new UserPasswordAndType(storedPasswordHash, userType);
    }
    

    public static class UserPasswordAndType {
        public final String passwordHash;
        public final String userType;

        public UserPasswordAndType(String passwordHash, String userType) {
            this.passwordHash = passwordHash;
            this.userType = userType;
        }
    }


    private static String formatDateTime(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
        return dateTime.format(formatter);
    }

    // Inside DatabaseConnector.java

    public static Post getPostById(int postId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM posts WHERE postId = ?";
        try {
            conn = getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, postId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Post post = new Post();
                post.setAuthor(rs.getString("author"));
                post.setContent(rs.getString("content"));
                post.setLikes(rs.getInt("likes"));
                post.setShares(rs.getInt("shares"));
                post.setDateTime(rs.getString("dateTime"));
                post.setImage(rs.getString("image"));
                return post;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources here, if necessary.
        }
        return null;
    }
    
    public static void deletePostById(int postId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String sql = "DELETE FROM posts WHERE postId = ?";
        try {
            conn = getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, postId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources here, if necessary.
        }
    }
    

public static List<Post> getTrendingPosts(String columnName, boolean isAscending, int retrieveCount, String filterUsername) {
    List<Post> posts = new ArrayList<>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    String sortOrder = isAscending ? "ASC" : "DESC";
    String userFilter = (filterUsername != null && !filterUsername.trim().isEmpty()) ? " WHERE author = ?" : "";
    String query = String.format("SELECT postId, author, content, likes, shares, dateTime, image FROM posts %s ORDER BY %s %s LIMIT ?", userFilter, columnName, sortOrder);

    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(query);
        if (!userFilter.isEmpty()) {
            stmt.setString(1, filterUsername);
            stmt.setInt(2, retrieveCount);
        } else {
            stmt.setInt(1, retrieveCount);
        }
        rs = stmt.executeQuery();
        while (rs.next()) {
            int postId = rs.getInt("postId");
            String author = rs.getString("author");
            String content = rs.getString("content");
            int likes = rs.getInt("likes");
            int shares = rs.getInt("shares");
            String dateTime = rs.getString("dateTime");
            String image = rs.getString("image");
            
            Post post = new Post(postId, content, author, likes, shares, dateTime, image);
            posts.add(post);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources here, if necessary.
    }

    return posts;
}

public static Map<String, Integer> getUsersDistribution() {
    Map<String, Integer> distribution = new HashMap<>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    String sql = "SELECT userType, COUNT(*) as count FROM users GROUP BY userType";
    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        while (rs.next()) {
            distribution.put(rs.getString("userType"), rs.getInt("count"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources here, if necessary.
    }

    return distribution;
}

public static void deleteUser(String username) {
    Connection conn = null;
    PreparedStatement preparedStatement = null;

    String deleteQuery = "DELETE FROM users WHERE username = ?";
    try {
        conn = getInstance().getConnection();
        preparedStatement = conn.prepareStatement(deleteQuery);
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();
        System.out.println("User " + username + " has been deleted.");
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error deleting user " + username);
    } finally {
        // Close resources here, if necessary.
    }
}

public static List<Post> getPostsByUsername(String username) {
    List<Post> userPosts = new ArrayList<>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    String sql = "SELECT * FROM posts WHERE author = ?";
    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        rs = stmt.executeQuery();
        while (rs.next()) {
            int postId = rs.getInt("postId");
            String postContent = rs.getString("content");
            int likes = rs.getInt("likes");
            int shares = rs.getInt("shares");
            String dateTime = rs.getString("dateTime");
            String image = rs.getString("image");
            
            Post post = new Post(postId, postContent, username, likes, shares, dateTime, image);
            userPosts.add(post);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources here, if necessary.
    }

    return userPosts;
}

public static int getPostIdByContent(String postContent) {
    int postId = -1;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    String sql = "SELECT postId FROM posts WHERE content = ?";
    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, postContent);
        rs = stmt.executeQuery();
        if (rs.next()) {
            postId = rs.getInt("postId");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources here, if necessary.
    }

    return postId;
}

public static String getPostContent(int postId) {
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    String query = "SELECT content FROM posts WHERE postId = ?";
    String postContent = "";
    try {
        conn = getInstance().getConnection();
        preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, postId);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            postContent = resultSet.getString("content");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error retrieving post content.");
    } finally {
        // Close resources here, if necessary.
    }

    return postContent;
}


// Assuming you have a 'User' model class already
public static User getUserByUsername(String username) {
    User user = null;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String sql = "SELECT * FROM users WHERE username = ?";

    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        rs = stmt.executeQuery();

        if (rs.next()) {
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String password = rs.getString("password");
            user = new User(username, firstName, lastName, password);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources here, if necessary.
    }

    return user;
}

public static int updatePostAuthor(String currentUsername, String newUsername) {
    String sql = "UPDATE posts SET author = ? WHERE author = ?";
    int affectedRows = 0;
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, newUsername);
        stmt.setString(2, currentUsername);
        affectedRows = stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources here, if necessary.
    }

    return affectedRows;
}

public static int updateUser(String currentUsername, String newUsername, String firstName, String lastName, String password) {
    String sql = "UPDATE users SET username = ?, firstName = ?, lastName = ?, password = ? WHERE username = ?";
    int affectedRows = 0;
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, newUsername);
        stmt.setString(2, firstName);
        stmt.setString(3, lastName);
        stmt.setString(4, password);
        stmt.setString(5, currentUsername);
        affectedRows = stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources here, if necessary.
    }

    return affectedRows;
}

public static void updateUserWithoutPassword(String currentUsername, String newUsername, String firstName, String lastName) {
    String sql = "UPDATE users SET username = ?, firstName = ?, lastName = ? WHERE username = ?";
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = getInstance().getConnection();
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, newUsername);
        stmt.setString(2, firstName);
        stmt.setString(3, lastName);
        stmt.setString(4, currentUsername);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources here, if necessary.
    }
}




}

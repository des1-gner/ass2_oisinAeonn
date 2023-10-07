package ass2_oisinAeonn.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Model.User;
import javafx.scene.control.Alert;

public class DatabaseConnector {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ass2_oisinAeonn";  
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASS = "";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
    }
    
    public static void testConnection() {
    try (Connection conn = getConnection()) {
        System.out.println("Connection successful!");
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Failed to establish connection.");
    }
}


    public static void insertPost(Post post) {
        String sql = "INSERT INTO posts (author, content, likes, shares, dateTime, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            // Print out the values of the Post object
            System.out.println("Author: " + post.getAuthor());
            System.out.println("Content: " + post.getContent());
            System.out.println("Likes: " + post.getLikes());
            System.out.println("Shares: " + post.getShares());
            System.out.println("DateTime: " + post.getDateTime());
            System.out.println("Image: " + post.getImage());
            stmt.setString(1, post.getAuthor());
            stmt.setString(2, post.getContent());
            stmt.setInt(3, post.getLikes());
            stmt.setInt(4, post.getShares());
            stmt.setString(5, post.getDateTime());  // No need to format
            stmt.setString(6, post.getImage());  // Assuming it's a string representing path or URL

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerUser(User user) {
        String sql = "INSERT INTO users (username, firstName, lastName, password, userType) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getUserType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfUserExists(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
public static Map<String, Integer> getPostsSharesDistribution() {
    Map<String, Integer> distribution = new HashMap<>();

    try {
        Connection connection = getConnection();
        
        // Fetch posts with shares between 0 and 99
        PreparedStatement ps1 = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE shares BETWEEN 0 AND 99");
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) {
            distribution.put("0-99", rs1.getInt(1));
        }

        // Fetch posts with shares between 100 and 999
        PreparedStatement ps2 = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE shares BETWEEN 100 AND 999");
        ResultSet rs2 = ps2.executeQuery();
        if (rs2.next()) {
            distribution.put("100-999", rs2.getInt(1));
        }

        // Fetch posts with 1000 or more shares
        PreparedStatement ps3 = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE shares >= 1000");
        ResultSet rs3 = ps3.executeQuery();
        if (rs3.next()) {
            distribution.put("1000+", rs3.getInt(1));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return distribution;
}

public static Map<String, Integer> getPostsLikesDistribution() {
    Map<String, Integer> distribution = new HashMap<>();

    try {
        Connection connection = getConnection();
        
        // Fetch posts with likes between 0 and 99
        PreparedStatement ps1 = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE likes BETWEEN 0 AND 99");
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) {
            distribution.put("0-99", rs1.getInt(1));
        }

        // Fetch posts with likes between 100 and 999
        PreparedStatement ps2 = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE likes BETWEEN 100 AND 999");
        ResultSet rs2 = ps2.executeQuery();
        if (rs2.next()) {
            distribution.put("100-999", rs2.getInt(1));
        }

        // Fetch posts with 1000 or more likes
        PreparedStatement ps3 = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE likes >= 1000");
        ResultSet rs3 = ps3.executeQuery();
        if (rs3.next()) {
            distribution.put("1000+", rs3.getInt(1));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return distribution;
}


public static void upgradeUserToVIP(String username) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Upgrade Successful");
alert.setHeaderText(null);
alert.setContentText("Please re-login to gain VIP functionality.");
alert.showAndWait();

    String sql = "UPDATE users SET userType = 'VIP' WHERE username = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, username);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public static void upgradeUserToAdmin(String username) {
        String sql = "UPDATE users SET userType = admin WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String formatDateTime(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
        return dateTime.format(formatter);
    }

    // Inside DatabaseConnector.java

public static Post getPostById(int postId) {
    String sql = "SELECT * FROM posts WHERE postId = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setInt(1, postId);
        ResultSet rs = stmt.executeQuery();
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
    }
    return null;
}

public static void deletePostById(int postId) {
    String sql = "DELETE FROM posts WHERE postId = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setInt(1, postId);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static List<Post> getTrendingPosts(String columnName, boolean isAscending, int retrieveCount, String filterUsername) {
    List<Post> posts = new ArrayList<>();

    String sortOrder = isAscending ? "ASC" : "DESC";
    String userFilter = (filterUsername != null && !filterUsername.trim().isEmpty()) ? " WHERE author = ?" : "";
    String query = String.format("SELECT postId, author, content, likes, shares, dateTime, image FROM posts %s ORDER BY %s %s LIMIT ?", userFilter, columnName, sortOrder);

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
         
        if (!userFilter.isEmpty()) {
            stmt.setString(1, filterUsername);
            stmt.setInt(2, retrieveCount);
        } else {
            stmt.setInt(1, retrieveCount);
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int postId = rs.getInt("postId");
            String author = rs.getString("author");
            String content = rs.getString("content");
            int likes = rs.getInt("likes");
            int shares = rs.getInt("shares");
            String dateTime = rs.getString("dateTime");
            String image = rs.getString("image");
            
            // Create a Post object with the retrieved data
            Post post = new Post(postId, content, author, likes, shares, dateTime, image);
            
            posts.add(post);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return posts;
}


public static void deleteUser(String username) {
    String deleteQuery = "DELETE FROM users WHERE username = ?";

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();

        System.out.println("User " + username + " has been deleted.");
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error deleting user " + username);
    }
}

public static List<Post> getPostsByUsername(String username) {
    List<Post> userPosts = new ArrayList<>();

    String sql = "SELECT * FROM posts WHERE author = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, username);
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            int postId = rs.getInt("postId");
            String postContent = rs.getString("content");
            int likes = rs.getInt("likes");
            int shares = rs.getInt("shares");
            String dateTime = rs.getString("dateTime");
            String image = rs.getString("image");
            
            // Create a Post object with the retrieved data
            Post post = new Post(postId, postContent, username, likes, shares, dateTime, image);
            
            userPosts.add(post);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return userPosts;
}





public static int getPostIdByContent(String postContent) {
    int postId = -1; // Default value if post is not found

    String sql = "SELECT postId FROM posts WHERE content = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, postContent);
        
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            postId = rs.getInt("postId");
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return postId;
}


public static String getPostContent(int postId) {
    String query = "SELECT content FROM posts WHERE postId = ?";
    String postContent = "";

    try (Connection connection = getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setInt(1, postId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            postContent = resultSet.getString("content");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error retrieving post content.");
    }

    return postContent;
}

// Assuming you have a 'User' model class already
public static User getUserByUsername(String username) {
    User user = null;
    String sql = "SELECT * FROM users WHERE username = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, username);
        
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            // Assuming these are the fields you have in your User model and in your database
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String password = rs.getString("password");
            
            user = new User(username, firstName, lastName, password);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return user;
}

public static void updateUser(String currentUsername, String newUsername, String firstName, String lastName, String password) {
    String sql = "UPDATE users SET username = ?, firstName = ?, lastName = ?, password = ? WHERE username = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, newUsername);
        stmt.setString(2, firstName);
        stmt.setString(3, lastName);
        stmt.setString(4, password);
        stmt.setString(5, currentUsername);

        stmt.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void updateUserWithoutPassword(String currentUsername, String newUsername, String firstName, String lastName) {
    String sql = "UPDATE users SET username = ?, firstName = ?, lastName = ? WHERE username = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, newUsername);
        stmt.setString(2, firstName);
        stmt.setString(3, lastName);
        stmt.setString(4, currentUsername);

        stmt.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



}

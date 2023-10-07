package ass2_oisinAeonn.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Model.User;

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

    public static boolean checkIfUserExists(String username) throws SQLException {
    String sql = "SELECT username FROM users WHERE username = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return true;
        }
    }
    return false;
}


public static void upgradeUserToVIP(String username) {
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

public static List<Post> getTrendingPosts(String columnName, boolean isAscending, int retrieveCount) {
    List<Post> posts = new ArrayList<>();

    String sortOrder = isAscending ? "ASC" : "DESC";
    String query = String.format("SELECT * FROM posts ORDER BY %s %s LIMIT ?", columnName, sortOrder);

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, retrieveCount);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Post post = new Post();
            post.setPostId(rs.getInt("postId"));
            post.setAuthor(rs.getString("author"));
            post.setContent(rs.getString("content"));
            post.setLikes(rs.getInt("likes"));
            post.setShares(rs.getInt("shares"));
            post.setDateTime(rs.getString("dateTime"));
            post.setImage(rs.getString("image"));
            posts.add(post);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return posts;
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

public static void updateUser(String username, String firstName, String lastName, String password) {
    String sql = "UPDATE users SET firstName = ?, lastName = ?, password = ? WHERE username = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, password);
        stmt.setString(4, username);

        stmt.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void updateUserWithoutPassword(String username, String firstName, String lastName) {
    String sql = "UPDATE users SET firstName = ?, lastName = ? WHERE username = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, username);

        stmt.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}

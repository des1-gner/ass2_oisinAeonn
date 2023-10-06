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
        String sql = "UPDATE users SET userType = VIP WHERE username = ?";
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

public static List<Post> getFilteredPosts(String filterBy, int numPosts, String sortOrder) {
    List<Post> posts = new ArrayList<>();
    
    // Building the SQL query string
    String query = "SELECT * FROM posts ORDER BY ";
    
    switch (filterBy) {
        case "Post ID":
            query += "postId";
            break;
        
        case "Date":
            query += "dateTime";
            break;

        case "Likes":
            query += "likes";
            break;

        case "Shares":
            query += "shares";
            break;
    }

    query += " " + sortOrder.toUpperCase() + " LIMIT " + numPosts;

    // Debug: Print the query to ensure it's correct
    System.out.println(query);

    // Execute the query and populate the posts list
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Post post = new Post();
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



}

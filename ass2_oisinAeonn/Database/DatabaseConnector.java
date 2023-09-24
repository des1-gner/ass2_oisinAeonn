package ass2_oisinAeonn.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import ass2_oisinAeonn.Model.Post;

public class DatabaseConnector {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/postsDB";
    private static final String DATABASE_USER = "YOUR_USERNAME";
    private static final String DATABASE_PASS = "YOUR_PASSWORD";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
    }
    
    public static void insertPost(Post post) {
        String sql = "INSERT INTO posts (postId, author, content, likes, shares, dateTime) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, post.getPostId());
            stmt.setString(2, post.getAuthor());
            stmt.setString(3, post.getContent());
            stmt.setInt(4, post.getLikes());
            stmt.setInt(5, post.getShares());
            stmt.setString(6, post.getDateTime());  // Ensure this matches your table's format

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle this in a more user-friendly way in a real application
        }
    }
}

package ass2_oisinAeonn.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Model.User;

public class DatabaseConnector {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ass2_oisinAeonn";  // Note: Added the "jdbc:mysql://" prefix
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASS = "";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
    }
    
    public static void insertPost(Post post) {
        String sql = "INSERT INTO posts (postId, author, content, likes, shares, dateTime, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, post.getPostId());
            stmt.setString(2, post.getAuthor());
            stmt.setString(3, post.getContent());
            stmt.setInt(4, post.getLikes());
            stmt.setInt(5, post.getShares());
            stmt.setString(6, post.getDateTime());
            stmt.setString(7, post.getImage());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerUser(User user) {
        String sql = "INSERT INTO users (username, firstName, lastName, password, vip, admin) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getPassword());
            stmt.setBoolean(5, user.isVip());
            stmt.setBoolean(6, user.isAdmin());

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
        String sql = "UPDATE users SET vip = TRUE WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void upgradeUserToAdmin(String username) {
        String sql = "UPDATE users SET admin = TRUE WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

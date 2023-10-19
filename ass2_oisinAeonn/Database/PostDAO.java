package ass2_oisinAeonn.Database;

import ass2_oisinAeonn.Model.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PostDAO {
    
    private static PostDAO instance = null;
    private Connection conn;

    public PostDAO() {
    
        this.conn = DatabaseConnector.getInstance().getConnection();
    
    }

    public static PostDAO getInstance() {
    
        if (instance == null) {
    
            instance = new PostDAO();
    
        }
    
        return instance;
    
    }

    public static List<Post> getAllPosts() {
    
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts";    
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
    
            stmt = conn.prepareStatement(sql);        
            rs = stmt.executeQuery();
            
            while (rs.next()) {
    
                int postId = rs.getInt("postId");
                String content = rs.getString("content");
                String author = rs.getString("author");
                int likes = rs.getInt("likes");
                int shares = rs.getInt("shares");
                String dateTime = rs.getString("dateTime");
                String image = rs.getString("image");
    
                Post post = new Post(postId, content, author, likes, shares, dateTime, image);  
    
                posts.add(post);
    
            }        
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
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
            
            // No need to close the Connection as it's a singleton connection.
        
        }
        
        return posts;
    
    }

    public static void insertPost(Post post) {
    
        String sql = "INSERT INTO posts (author, content, likes, shares, dateTime, image) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        
        try {
        
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, post.getAuthor());
            stmt.setString(2, post.getContent());
            stmt.setInt(3, post.getLikes());
            stmt.setInt(4, post.getShares());
            stmt.setString(5, post.getDateTime());  // No need to format
            stmt.setString(6, post.getImage());  // Assuming it's a string representing path or URL  
            stmt.executeUpdate();
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
            // Close resources properly.
        
        }
    
    }

    public static Map<String, Integer> getPostsSharesDistribution() {
    
        Map<String, Integer> distribution = new HashMap<>();
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement ps1 = null, ps2 = null, ps3 = null;
        ResultSet rs1 = null, rs2 = null, rs3 = null;
        
        try {
    
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
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
            // Close all resources properly.
        
        }
        
        return distribution;
    
    }
    
    public static Map<String, Integer> getPostsLikesDistribution() {
    
        Map<String, Integer> distribution = new HashMap<>();
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement ps1 = null, ps2 = null, ps3 = null;
        ResultSet rs1 = null, rs2 = null, rs3 = null;
            
        try {
        
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
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
            // Close all resources properly.
        
        }
            
        return distribution;
        
    }

    public static Post getPostById(int postId) {
    
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
            
        String sql = "SELECT * FROM posts WHERE postId = ?";
    
        try {
    
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, postId);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
    
                Post post = new Post();
    
                post.setPostId(rs.getInt("postId"));
                post.setAuthor(rs.getString("author"));
                post.setContent(rs.getString("content"));
                post.setLikes(rs.getInt("likes"));
                post.setShares(rs.getInt("shares"));
                post.setDateTime(rs.getString("dateTime"));
                post.setImage(rs.getString("image"));
    
                return post;
            }
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
            // Close resources here, if necessary.
        
        }
        
        return null;
        
    }
        
    public static void deletePostById(int postId) {
    
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
            
        String sql = "DELETE FROM posts WHERE postId = ?";
    
        try {
    
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, postId);
            stmt.executeUpdate();
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
            // Close resources here, if necessary.
        
        }
        
    }
        
    
    public static List<Post> getTrendingPosts(String columnName, boolean isAscending, int retrieveCount, String filterUsername) {
    
        List<Post> posts = new ArrayList<>();
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        String sortOrder = isAscending ? "ASC" : "DESC";
        String userFilter = (filterUsername != null && !filterUsername.trim().isEmpty()) ? " WHERE author = ?" : "";
        String query = String.format("SELECT postId, author, content, likes, shares, dateTime, image FROM posts %s ORDER BY %s %s LIMIT ?", userFilter, columnName, sortOrder);
    
        try {
    
            stmt = conn.prepareStatement(query);
    
            if (!userFilter.isEmpty()) {
    
                stmt.setString(1, filterUsername);
                stmt.setInt(2, retrieveCount);
    
            } 
            
            else {
            
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
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
            // Close resources here, if necessary.
        
        }
    
        return posts;
    
    }

    public static List<Post> getPostsByUsername(String username) {
    
        List<Post> userPosts = new ArrayList<>();
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        String sql = "SELECT * FROM posts WHERE author = ?";
    
        try {
    
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
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
            // Close resources here, if necessary.
        
        }
    
        return userPosts;
    
    }
    
    public static int getPostIdByContent(String postContent) {
    
        int postId = -1;
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        String sql = "SELECT postId FROM posts WHERE content = ?";
    
        try {
    
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, postContent);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
    
                postId = rs.getInt("postId");
    
            }
    
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
        } 
        
        finally {
        
            // Close resources here, if necessary.
        
        }
    
        return postId;
    
    }
    
    public static String getPostContent(int postId) {
    
        Connection conn = DatabaseConnector.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        String query = "SELECT content FROM posts WHERE postId = ?";
        String postContent = "";
        
        try {
        
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            resultSet = preparedStatement.executeQuery();
        
            if (resultSet.next()) {
        
                postContent = resultSet.getString("content");
        
            }
        
        } 
        
        catch (SQLException e) {
        
            e.printStackTrace();
        
            System.err.println("Error retrieving post content.");
        
        } 
        
        finally {
        
            // Close resources here, if necessary.
        
        }
    
        return postContent;
    
    }

}
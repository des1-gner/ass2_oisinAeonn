package ass2_oisinAeonn.JUnit;

import ass2_oisinAeonn.Database.PostDAO;
import ass2_oisinAeonn.Database.UserDAO;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Model.User;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

// This class provides JUnit test cases for the database access objects (DAOs)

public class BasicDAOTest {

    // This method is executed before each test

    @Before

    public void setup() {
    
    }

    // This method is executed after each test

    @After
    
    public void tearDown() {
    
    }

    // Test that all posts can be fetched from the database

    @Test
    
    public void testGetAllPosts() {
    
        List<Post> posts = PostDAO.getAllPosts();
    
        assertNotNull(posts);
    
    }

    // Test that all users can be fetched from the database

    @Test
    
    public void testGetAllUsers() {
    
        List<User> users = UserDAO.getAllUsers();
    
        assertNotNull(users);
    
    }

    // Test that a post can be inserted and then deleted

    @Test
    
    public void testInsertAndDeletePost() {
    
        Post post = new Post(-1, "Test Content", "TestAuthor", 0, 0, "01/01/2022 10:10", "testImage.jpg");
    
        PostDAO.insertPost(post);

        List<Post> authorPosts = PostDAO.getPostsByUsername("TestAuthor");
    
        assertTrue(!authorPosts.isEmpty());

        for (Post p : authorPosts) {
    
            PostDAO.deletePostById(p.getPostId());
    
        }
    
    }

    // Test that a user can be registered and then deleted

    @Test
    
    public void testRegisterAndDeleteUser() {
    
        User user = new User("testUsername", "Test", "User", "testPass123$", "standard", null);
    
        UserDAO.registerUser(user);

        boolean exists = UserDAO.checkIfUserExists("testUsername");
    
        assertTrue(exists);

        UserDAO.deleteUserByUsername("testUsername");
    
    }

    // Test that a distribution of post shares can be fetched

    @Test
    
    public void testGetPostSharesDistribution() {
    
        Map<String, Integer> distribution = PostDAO.getPostsSharesDistribution();
    
        assertNotNull(distribution);
    
    }

    // Test that a distribution of post likes can be fetched

    @Test
    
    public void testGetPostLikesDistribution() {
    
        Map<String, Integer> distribution = PostDAO.getPostsLikesDistribution();
    
        assertNotNull(distribution);
    
    }

    // Test that a distribution of users can be fetched

    @Test
    
    public void testUsersDistribution() {
    
        Map<String, Integer> distribution = UserDAO.getUsersDistribution();
    
        assertNotNull(distribution);
    
    }

    // Test that a post by a specific ID can be fetched

    @Test
    
    public void testGetPostById() {
    
        Post post = PostDAO.getPostById(1);
    
        assertNotNull(post);
    
    }

    // Test that content of a post by a specific ID can be fetched

    @Test
    
    public void testGetPostContent() {
    
        String content = PostDAO.getPostContent(1);
    
        assertNotNull(content);
    
    }

    // Test that a user by a specific username can be fetched

    @Test
    
    public void testGetUserByUsername() {
    
        User user = UserDAO.getUserByUsername("testUsername2");
    
        assertNotNull(user);
    
    }

}

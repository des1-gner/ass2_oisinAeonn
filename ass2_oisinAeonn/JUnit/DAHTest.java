package ass2_oisinAeonn.JUnit;

import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Model.User;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DAHTest {

    @Before
    public void setup() {
        // Setup code before each test
    }

    @After
    public void tearDown() {
        // Cleanup code after each test
    }

    @Test
    public void testGetAllPosts() {
        List<Post> posts = DatabaseConnector.getAllPosts();
        assertNotNull(posts);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = DatabaseConnector.getAllUsers();
        assertNotNull(users);
    }

    @Test
    public void testInsertAndDeletePost() {
        Post post = new Post(-1, "Test Content", "TestAuthor", 0, 0, "01/01/2022 10:10", "testImage.jpg");
        DatabaseConnector.insertPost(post);

        List<Post> authorPosts = DatabaseConnector.getPostsByUsername("TestAuthor");
        assertTrue(!authorPosts.isEmpty());

        for (Post p : authorPosts) {
            DatabaseConnector.deletePostById(p.getPostId());
        }
    }

    @Test
    public void testRegisterAndDeleteUser() {
        User user = new User("testUsername", "Test", "User", "testPass", "Regular");
        DatabaseConnector.registerUser(user);

        boolean exists = DatabaseConnector.checkIfUserExists("testUsername");
        assertTrue(exists);

        DatabaseConnector.deleteUserByUsername("testUsername");
    }

    @Test
    public void testUpgradeUser() {
        User user = new User("testVIP", "Test", "VIP", "testPass", "standard");
        DatabaseConnector.registerUser(user);
        DatabaseConnector.upgradeUserToVIP("testVIP");

        DatabaseConnector.deleteUserByUsername("testVIP");
    }

    @Test
    public void testGetPostSharesDistribution() {
        Map<String, Integer> distribution = DatabaseConnector.getPostsSharesDistribution();
        assertNotNull(distribution);
    }

    @Test
    public void testGetPostLikesDistribution() {
        Map<String, Integer> distribution = DatabaseConnector.getPostsLikesDistribution();
        assertNotNull(distribution);
    }

    @Test
    public void testUsersDistribution() {
        Map<String, Integer> distribution = DatabaseConnector.getUsersDistribution();
        assertNotNull(distribution);
    }

    @Test
    public void testGetPostById() {
        Post post = DatabaseConnector.getPostById(1);
        assertNotNull(post);
    }

    @Test
    public void testGetPostContent() {
        String content = DatabaseConnector.getPostContent(1);
        assertNotNull(content);
    }

    @Test
    public void testGetUserByUsername() {
        User user = DatabaseConnector.getUserByUsername("testUsername");
        assertNotNull(user);
    }

    // Add more tests as needed based on the methods you have.
}

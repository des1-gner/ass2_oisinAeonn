package ass2_oisinAeonn.JUnit;

import ass2_oisinAeonn.Database.PostDAO;
import ass2_oisinAeonn.Database.UserDAO;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Model.User;
import org.junit.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BasicDAOTest {

    @Before

    public void setup() {
    
    }

    @After
    
    public void tearDown() {
    
    }

    @Test
    
    public void testGetAllPosts() {
    
        List<Post> posts = PostDAO.getAllPosts();
    
        assertNotNull(posts);
    
    }

    @Test
    
    public void testGetAllUsers() {
    
        List<User> users = UserDAO.getAllUsers();
    
        assertNotNull(users);
    
    }

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

    @Test
    
    public void testRegisterAndDeleteUser() {
    
        User user = new User("testUsername", "Test", "User", "testPass123$", "standard", null);
    
        UserDAO.registerUser(user);

        boolean exists = UserDAO.checkIfUserExists("testUsername");
    
        assertTrue(exists);

        UserDAO.deleteUserByUsername("testUsername");
    
    }

    @Test
    
    public void testUpgradeUser() {
    
        User user = new User("testVIP", "Test", "VIP", "testPass", "standard", null);
        
        UserDAO.registerUser(user);
        UserDAO.upgradeUserToVIP("testVIP");
        UserDAO.deleteUserByUsername("testVIP");
    
    }

    @Test
    
    public void testGetPostSharesDistribution() {
    
        Map<String, Integer> distribution = PostDAO.getPostsSharesDistribution();
    
        assertNotNull(distribution);
    
    }

    @Test
    
    public void testGetPostLikesDistribution() {
    
        Map<String, Integer> distribution = PostDAO.getPostsLikesDistribution();
    
        assertNotNull(distribution);
    
    }

    @Test
    
    public void testUsersDistribution() {
    
        Map<String, Integer> distribution = UserDAO.getUsersDistribution();
    
        assertNotNull(distribution);
    
    }

    @Test
    
    public void testGetPostById() {
    
        Post post = PostDAO.getPostById(1);
    
        assertNotNull(post);
    
    }

    @Test
    
    public void testGetPostContent() {
    
        String content = PostDAO.getPostContent(1);
    
        assertNotNull(content);
    
    }

    @Test
    
    public void testGetUserByUsername() {
    
        User user = UserDAO.getUserByUsername("testUsername");
    
        assertNotNull(user);
    
    }

}

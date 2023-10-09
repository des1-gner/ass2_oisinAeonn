package ass2_oisinAeonn.JUnit;

// Imports

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Model.User;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

public class DAHTest {

    private DatabaseConnector dbConnector;

    @Before
    public void setUp() {
        dbConnector = DatabaseConnector.getInstance();
    }

    @Test
    public void testUpdateUserType() {
        String username = "testUser";
        String newType = "VIP";

        String originalType = dbConnector.getUserTypeByUsername(username);

        dbConnector.updateUserType(username, newType);

        String updatedType = dbConnector.getUserTypeByUsername(username);

        assertNotEquals(originalType, updatedType);
        assertEquals(newType, updatedType);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = dbConnector.getAllUsers();
        
        assertEquals(10, users.size());
    }

    @Test
    public void testDeleteUserByUsername() {
        String usernameToDelete = "testUser";


        assertTrue(dbConnector.checkIfUserExists(usernameToDelete));

        dbConnector.deleteUserByUsername(usernameToDelete);

        assertFalse(dbConnector.checkIfUserExists(usernameToDelete));
    }

    @Test
    public void testInsertPost() {
        int initialCount = dbConnector.getAllPosts().size();

        Post testPost = new Post(initialCount, "testAuthor", "testContent", 10, 5, "01/01/2023 10:00", "testImage");
        dbConnector.insertPost(testPost);

        int afterInsertCount = dbConnector.getAllPosts().size();

        assertEquals(initialCount + 1, afterInsertCount);
    }

    @Test
    public void testGetPostsSharesDistribution() {
        Map<String, Integer> distribution = dbConnector.getPostsSharesDistribution();
        
        assertNotNull(distribution);
        assertTrue(distribution.containsKey("0-99"));
        assertTrue(distribution.containsKey("100-999"));
        assertTrue(distribution.containsKey("1000+"));
    }

    @Test
    public void testGetUsersDistribution() {
        Map<String, Integer> distribution = dbConnector.getUsersDistribution();

        assertNotNull(distribution);
        assertTrue(distribution.containsKey("admin"));
        assertTrue(distribution.containsKey("VIP"));

    }

    @After
    public void tearDown() {

    }
}

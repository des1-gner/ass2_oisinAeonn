package ass1_oisinAeonn;

// Imports

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

// Declaration of the socialMediaAnalyzerApp instance for JUnit Tests

public class socialMediaAnalyzerAppTest {

    private socialMediaAnalyzerApp sma;

    // This method will run before each test

    @Before

    public void setUp() {

        // Initialize a new socialMediaAnalyzerApp before each test

        sma = new socialMediaAnalyzerApp();

    }

    // Get Console Output for some tests that don't return any value

    private String getConsoleOutput(ByteArrayOutputStream outputStream) {

        String[] lines = outputStream.toString().trim().split("\n");
        
        return lines[lines.length - 1];
    
    }
    
    // TEST 1: This method will test the 'addPost' functionality

    @Test

    public void testAddPost() {
        
        // Create an instance of socialMediaAnalyzerApp
        
        socialMediaAnalyzerApp sma = new socialMediaAnalyzerApp();

        // Save the initial count of posts
        
        int initialPostCount = sma.getPosts();

        // Create a new Post object
        
        Post post = new Post(77, "testContent", "testAuthor", 2021, 162, "22/07/2023 12:00");

        // Add the post to the socialMediaAnalyzerApp
        
        sma.addPost(post);
        sma.appendPost();

        // Check if the count of posts increased by 1
        
        int afterPostCount = sma.getPosts();
        
        assertEquals(initialPostCount + 1, afterPostCount);
    
    }

    // Test 2: Testing Add Post Errors

    @Test
    
    public void testAddPostError() {
    
        // Create an instance of socialMediaAnalyzerApp
        
        socialMediaAnalyzerApp sma = new socialMediaAnalyzerApp();

        // Redirect System.out to capture console output
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Test cannot have invalid postId
        
        Post post1 = new Post(-3, "testContent", "testAuthor", 3, 4, "22/07/2023 12:00");
        
        sma.addPost(post1);
        
        // Check if the post will not create given an invalid postId
        
        assertEquals("The Post Id must be a positive integer. Please Try Again.", getConsoleOutput(outputStream));

        // Test cannot have invalid likes
        
        Post post2 = new Post(3, "testContent", "testAuthor", -3, 4, "22/07/2023 12:00");
        
        sma.addPost(post2);
        
        // Check if the post will not create given an invalid likes
        
        assertEquals("Likes must be a positive integer. Please Try Again.", getConsoleOutput(outputStream));

        // Test cannot have invalid shares
        
        Post post3 = new Post(4, "testContent", "testAuthor", 3, -4, "22/07/2023 12:00");
        
        sma.addPost(post3);
        
        // Check if the post will not create given an invalid shares
        
        assertEquals("Shares must be a positive integer. Please Try Again.", getConsoleOutput(outputStream));

        // Test cannot have an invalid time
        
        Post post4 = new Post(5, "testContent", "testAuthor", 3, 4, "broken time");
        
        sma.addPost(post4);
        
        // Check if the post will not create given an invalid date
        
        assertEquals("Invalid DateTime format. Please Try Again entering in dd/MM/yyyy HH:mm format.", getConsoleOutput(outputStream));

        // Test cannot be duplicate postId
        
        Post post5 = new Post(78, "testContent2", "testAuthor2", 3, 4, "22/07/2023 12:00");
        Post post6 = new Post(78, "testContent3", "testAuthor3", 3, 4, "22/07/2023 12:00");
        
        sma.addPost(post5);
        sma.addPost(post6);
        
        // Check if the post was already created, and will not create a duplicate
        
        assertEquals("This Post Id is already in use. Please Try Again with a different Post Id.", getConsoleOutput(outputStream));

        // Reset System.out
        
        System.setOut(originalOut);
    
    }

    // TEST 3: This method will test the 'deletePostById' functionality

    @Test

    public void testDeletePostById() {

        // Create a new Post object

        Post post = new Post(31, "testContent", "testAuthor", 592, 61, "22/07/2023 12:00");

        // Add the post to the socialMediaAnalyzerApp

        sma.addPost(post);
        sma.appendPost();

        // Check if the post was successfully deleted

        assertEquals("Post deleted successfully.", sma.deletePostById(31));

    }

    // TEST 4: This method will test the 'retrievePost' functionality

    @Test

    public void testRetrievePost() {

        // Create a new Post object

        Post post = new Post(137, "testContent", "testAuthor", 416, 52, "22/07/2023 12:00");

        // Add the post to the socialMediaAnalyzerApp

        sma.addPost(post);
        sma.appendPost();

        // Check if the correct post was retrieved

        assertEquals("137,testContent,testAuthor,416,52,22/07/2023 12:00", sma.retrievePost(137).toString());

    }

    // TEST 5: This method will test the 'retrieveTopNPostsByLikes' functionality

    @Test

    public void testRetrieveTopNPostsByLikes() {

        // Create two new Post objects

        Post post1 = new Post(1, "testContent1", "testAuthor1", 10, 5, "22/07/2023 12:00");
        Post post2 = new Post(2, "testContent2", "testAuthor2", 15, 5, "22/07/2023 13:00");

        // Add the posts to the socialMediaAnalyzerApp

        sma.addPost(post1);
        sma.addPost(post2);
        sma.appendPost();

        // Retrieve the top 2 posts by likes

        List<Post> topPosts = sma.retrieveTopNPostsByLikes(2);

        // Check if the correct number and order of posts were retrieved
        
        assertNotNull(topPosts);
        assertEquals(2, topPosts.size());
        assertEquals(post2, topPosts.get(0)); // post2 has more likes
    
    }

    // TEST 6: This method will test the 'retrieveTopNPostsByShares' functionality
    
    @Test
    
    public void testRetrieveTopNPostsByShares() {
    
        // Create two new Post objects
    
        Post post1 = new Post(6, "testContent1", "testAuthor1", 10, 5, "22/07/2023 12:00");
        Post post2 = new Post(7, "testContent2", "testAuthor2", 10, 10, "22/07/2023 13:00");

        // Add the posts to the socialMediaAnalyzerApp
    
        sma.addPost(post1);
        sma.addPost(post2);
        sma.appendPost();

        // Retrieve the top 2 posts by shares
    
        List<Post> topPosts = sma.retrieveTopNPostsByShares(2);

        // Check if the correct number and order of posts were retrieved
    
        assertNotNull(topPosts);
        assertEquals(2, topPosts.size());
        assertEquals(post2, topPosts.get(0)); // post2 has more shares
    
    }

    // Test 7: Fail Delete

    @Test
    
    public void testFailDelete() {
    
        int postId = -3;
    
        String expectedErrorMessage = "No Post found with given ID.";
    
        String actualErrorMessage = sma.deletePostById(postId);
    
        assertEquals(expectedErrorMessage, actualErrorMessage);
    
    }

    // Test 8: Fail Retrieve
    
    @Test
    
    public void testFailRetrieve() {
    
        int postId = -3;
    
        Post retrievedPost = sma.retrievePost(postId);
    
        assertNull(retrievedPost);
    
    }

}
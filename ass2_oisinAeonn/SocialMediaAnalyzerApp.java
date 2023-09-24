package ass2_oisinAeonn;

// Imports used in this Program. 

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ass2_oisinAeonn.model.Post;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Define public class of socialMediaAnalyzerApp

public class SocialMediaAnalyzerApp {

  // Static Declaration of csv file

  private String file = "posts.csv";

  // Define ArrayLists for Objects: "Post".  
 
  private static LinkedHashMap<Integer, Post> posts;

  // Constructor initializes ArrayLists. 

  public SocialMediaAnalyzerApp() {
    
    // Initializes ArrayList: "posts".

    posts = new LinkedHashMap<Integer, Post>();
  
  }

  // Run Menu, and whole application until option 6 is selected.

  public void run() {

    // Initialize Flag for Loop.

    boolean flag = true;

    // Create Scanner Object to Read Input from User.

    Scanner input = new Scanner(System.in);

    // Loop Until User Quits.

    while (flag) {
      
      try {
      
        System.out.println(
      
        "\n-----------------------------------------\n" +
          "        Social Media Analyzer App\n" +
          "        Written by Oisin s3952320\n" +
          "-------------------------------------------\n" +
          "[  1  ]: Add a Social Media Post\n" +
          "[  2  ]: Delete an existing Social Media Post\n" +
          "[  3  ]: Retrieve a Social Media Post\n" +
          "[  4  ]: Retrieve the top N posts with most likes\n" +
          "[  5  ]: Retrieve the top N posts with most shares\n" +
          "[  6  ]: Exit\n"
          
        );

        System.out.print("Choose an Option [1 - 6]: ");
        
        String inputString = input.nextLine();
        
        int option;

        try {
        
          option = Integer.parseInt(inputString);
          
          if (option < 1) {
        
            throw new Exception("Invalid Menu Option. You entered a number less than 1. Please Try Again entering an Integer Number for a Menu Option [1 - 6].");
        
          } 
        
          else if (option > 6) {
        
            throw new Exception("Invalid Menu Option. You entered a number greater than 6. Please Try Again entering an Integer Number for a Menu Option [1 - 6].");
        
          } 
      
        }
      
        catch (NumberFormatException e) {
      
          System.out.println("Invalid Input for Menu Option. Please Try Again entering an Integer Number for a Menu Option [1 - 6].");
      
          continue;
      
        }
      
      catch (Exception e) {
      
        System.out.println(e.getMessage());
      
        continue;
      
      }         

      switch (option) {
        
        // Add a Social Media Post

        case 1:

        readPosts();

        addPost();
      
        appendPost();

        break;
      
        // Delete an existing Social Media Post

        case 2: 
      
        readPosts();

        deletePostById();

        savePosts();

        break;
      
        // Retrieve a Social Media Post

        case 3:
      
        readPosts();
      
        retrievePost();
      
        break;
      
        // Retrieve posts based on likes

        case 4:
      
        readPosts();

        retrieveTopNPostsByLikes();
      
        break;
      
        // Retrieve posts based on shares

        case 5:
      
        readPosts();

        retrieveTopNPostsByShares();
      
        break;
      
        // Exit the Application

        case 6:
      
        System.out.println("Thank you for using the Social Media Analyzer App! \nGoodbye for Now :)");
      
        flag = false;
      
        break;
      
        }
    
      } 
    
      catch (Exception e) {
      
        e.printStackTrace();
      
      } 
    
    }
    
  }

  // 1: Add a Post to the System.

  public static void addPost() {
  
    Scanner input = new Scanner(System.in);
    int postId;
    String author;
    String content;
    int likes;
    int shares;
    String dateTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Repeat until Valid Input is given for post ID.
  
    while (true) {
   
      try {
    
        System.out.print("Please provide the Post Id: ");
    
        postId = input.nextInt();
    
        // Check whether the postId is positive
    
        if (postId <= 0) {
    
          System.out.println("The Post Id must be a positive integer. Please Try Again.");
    
          continue;  // Continue to the next loop iteration without consuming the newline
    
        }
    
        if (posts.containsKey(postId)) {
    
          System.out.println("This Post Id is already in use. Please Try Again with a different Post Id.");
    
        } 
        
        else {
        
          break;  // Exit the loop if the postId is not already in use
        
        }
    
      } 
      
      catch (InputMismatchException e) {
      
        System.out.println("Invalid Input. Please Try Again entering a valid Post Id (Integer).");
      
      } 
      
      finally {
      
        input.nextLine();  // Consume the newline or the invalid token
        
      }

    }

    // Repeat until Valid Input is given for content.
  
    while (true) {
  
      try {
  
        System.out.print("Please provide the post content: ");
  
        content = input.nextLine();
  
        break;
  
      }
  
      catch (InputMismatchException e) {
  
        System.out.println("Invalid Input. Please Try Again entering valid Content (String).");
  
        input.nextLine();
  
      }
  
    }

    // Repeat until Valid Input is given for author.
  
    while (true) {
  
      try {
  
        System.out.print("Please provide the post author: ");
  
        author = input.nextLine();
  
        break;
  
      }
  
      catch (InputMismatchException e) {
  
        System.out.println("Invalid Input. Please Try Again entering a valid Author (String).");
  
        input.nextLine();
  
      }
  
    }

    // Repeat until Valid Input is given for likes.

    while (true) {

      try {

        System.out.print("Please provide the post likes: ");

        likes = input.nextInt();

        // Check if likes is positive
        
        if (likes < 0) {
        
          throw new InputMismatchException();
        
        }

        input.nextLine();

        break;

      }

      catch (InputMismatchException e) {

        System.out.println("Invalid Input. Please Try Again entering a valid number of likes (Positive Integer).");

        input.nextLine();

      }

    }

    // Repeat until Valid Input is given for shares.

    while (true) {

      try {

        System.out.print("Please provide the post shares: ");

        shares = input.nextInt();

        // Check if shares is positive
        
        if (shares < 0) {
        
          throw new InputMismatchException();
        
        }

        input.nextLine();

        break;

      }

      catch (InputMismatchException e) {

        System.out.println("Invalid Input. Please Try Again entering a valid number of shares (Positive Integer).");

        input.nextLine();

      }

    }

    // Repeat until Valid Input is given for DateTime.
    
    while (true) {
    
      try {
    
        System.out.print("Please provide the date and time of the post in the format of dd/MM/yyyy HH:mm: ");
        
        String userInput = input.nextLine();
        
        LocalDateTime dT = LocalDateTime.parse(userInput, formatter);
        
        dateTime = dT.format(formatter); // 'T' is replaced with a space
        
        break;  // exit the loop if parsing was successful
        
      } 
      
      catch (DateTimeParseException e) {
      
        System.out.println("Invalid DateTime format. Please Try Again entering in dd/MM/yyyy HH:mm format.");
      
      }
    
    }

    posts.clear();
    
    Post post = new Post(postId, content, author, likes, shares, dateTime);
    
    posts.put(postId, post);

    System.out.println("Post added to the System.");  

  }


  // Overloaded addPost method with Post parameter

  public void addPost(Post post) {

    // Check whether the postId is positive
    
    if (post.getPostId() <= 0) {
  
      System.out.println("The Post Id must be a positive integer. Please Try Again.");
      
      return;

    }
    
    if (posts.containsKey(post.getPostId())) {

      System.out.println("This Post Id is already in use. Please Try Again with a different Post Id.");
      
      return;

    }

    // Check whether likes and shares are positive
    
    if (post.getLikes() <= 0) {
    
      System.out.println("Likes must be a positive integer. Please Try Again.");
      
      return;
    
    }

    if (post.getShares() <= 0) {
    
      System.out.println("Shares must be a positive integer. Please Try Again.");

      return; 
    
    }

    else {

      // Check date and time format

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

      LocalDateTime dT;

      try {

        dT = LocalDateTime.parse(post.getDateTime(), formatter);

      } 
      
      catch (DateTimeParseException e) {
    
        System.out.println("Invalid DateTime format. Please Try Again entering in dd/MM/yyyy HH:mm format.");
    
        return;
      
      }

      posts.put(post.getPostId(), post);

    }

  }

  // 2: Delete an existing Social Media Post.

  public void deletePostById() {

    Scanner input = new Scanner(System.in);

    int postId;

    // Repeat until Valid Input is given for post ID.

    while (true) {

      try {

        System.out.print("Enter Post ID to delete: ");

        postId = input.nextInt();

        input.nextLine();  // consume the newline left-over

        break;

      }

      catch (InputMismatchException e) {

        System.out.println("Invalid Input. Please Try Again entering an integer for Post ID.");

        input.nextLine();  // consume the invalid token so we can continue

      }

    }

    // Check if post exists and remove it

    if (posts.containsKey(postId)) {

      posts.remove(postId);

      System.out.println("Post deleted successfully.");

    } 
    
    else {

      System.out.println("No Post found with given ID.");

    }

  }

  // Overloaded with input of postId for testing.

  public String deletePostById(int postId) {
  
    // Check if post exists and remove it
  
    if (posts.containsKey(postId)) {
  
      posts.remove(postId);
  
      return "Post deleted successfully.";
  
    } 
    
    else {
    
      return "No Post found with given ID.";
    
    }
  
  }

  // 3: Retrieve Post

  public void retrievePost() {

    Scanner input = new Scanner(System.in);

    int postId;

    // Repeat until Valid Input is given for post ID.

    while (true) {

      try {

        System.out.print("Enter Post Id to retrieve: ");

        postId = input.nextInt();

        input.nextLine();  // consume the newline left-over

        break;

      }

      catch (InputMismatchException e) {

        System.out.println("Invalid Input. Please Try Again entering an integer for Post Id.");

        input.nextLine();  // consume the invalid token so we can continue

      }

    }

    // retrieve post from posts map

    if (posts.containsKey(postId)) {

      Post postToRetrieve = posts.get(postId);
      
      System.out.println("Post details:\n" + postToRetrieve);

    } 
    
    else {

      System.out.println("No Post found with given Id.");

    }

  }

  // Overloaded Post Retrieval by postId, based on testing.

  public Post retrievePost(int postId) {
  
    // Retrieve post from posts map
  
    if (posts.containsKey(postId)) {
  
      return posts.get(postId);
    
    } 
    
    else {
    
      System.out.println("No Post found with given Id.");
      
      return null;   
    
    }
  
  }

  // 4: Retrieve the top N posts with most likes

  public void retrieveTopNPostsByLikes() {

    Scanner input = new Scanner(System.in);
    int N = -1;
    
    while (N <= 0) {
    
      System.out.print("Please specify the number of posts to retrieve (N): ");
    
      try {
    
        N = input.nextInt();
    
        if (N <= 0) {
    
          System.out.println("Input must be a positive integer. Please try again.");
    
        }
        
      } 
      
      catch (InputMismatchException e) {
      
        System.out.println("Input must be an integer. Please try again.");
      
        input.next(); // consume the invalid token
      
      }
    
    }
    
    input.nextLine(); // consume leftover newline

    if (N > posts.size()) {
    
      System.out.println("Only " + posts.size() + " posts exist in the database. Showing all of them.");
    
      N = posts.size();
    
    }

    List<Post> topNPosts = new ArrayList<>(posts.values());

    topNPosts = topNPosts.stream()

      // Chain Method Calls (result of the previous method call)

      // sorts based on number of likes

      .sorted(Comparator.comparing(Post::getLikes).reversed())
      
      // limits number to specified user amount

      .limit(N)
      
      // creates new list
      
      .collect(Collectors.toList());

    System.out.println("The top-" + N + " posts with the most likes are:");

    for (Post post : topNPosts) {
    
      System.out.println(post);
    
    }
  
  }

  // Overloaded Retrieval based on likes

  public List<Post> retrieveTopNPostsByLikes(int N) {
    
    if (N > posts.size()) {
    
      System.out.println("Only " + posts.size() + " posts exist in the database. Showing all of them.");
    
      N = posts.size();
    
    }

    List<Post> topNPosts = new ArrayList<>(posts.values());

    topNPosts = topNPosts.stream()

      // Chain Method Calls (result of the previous method call)

      // sorts based on number of likes

      .sorted(Comparator.comparing(Post::getLikes).reversed())
      
      // limits number to specified user amount

      .limit(N)
      
      // creates new list
      
      .collect(Collectors.toList());

    System.out.println("The top-" + N + " posts with the most likes are:");

    for (Post post : topNPosts) {
    
      System.out.println(post);
    
    }
    
    return topNPosts;
  
  }

  // 5: Retrieve the top N posts with most shares

  public void retrieveTopNPostsByShares() {
    
    Scanner input = new Scanner(System.in);
    
    int N = -1;
    
    while (N <= 0) {
    
      System.out.print("Please specify the number of posts to retrieve (N): ");
    
      try {
    
        N = input.nextInt();
    
        if (N <= 0) {
    
          System.out.println("Input must be a positive integer. Please try again.");
    
        }
        
      } 
      
      catch (InputMismatchException e) {
      
        System.out.println("Input must be an integer. Please try again.");
      
        input.next(); // consume the invalid token
      
      }
    
    }
    
    input.nextLine(); // consume leftover newline

    if (N > posts.size()) {
    
      System.out.println("Only " + posts.size() + " posts exist in the database. Showing all of them.");
    
      N = posts.size();
    
    }

    List<Post> topNPosts = new ArrayList<>(posts.values());

    topNPosts = topNPosts.stream()
    
      // Chain Method Calls (result of the previous method call)

      // sorts based on number of shares

      .sorted(Comparator.comparing(Post::getShares).reversed())
      
      // limits number to specified user amount

      .limit(N)
      
      // creates new list
      
      .collect(Collectors.toList());

    System.out.println("The top-" + N + " posts with the most shares are:");

    for (Post post : topNPosts) {
    
      System.out.println(post);
    
    }
  
  }

  public List<Post> retrieveTopNPostsByShares(int N) {

    if (N > posts.size()) {

      System.out.println("Only " + posts.size() + " posts exist in the database. Showing all of them.");

      N = posts.size();

    }

    List<Post> topNPosts = new ArrayList<>(posts.values());

    topNPosts = topNPosts.stream()

      // Chain Method Calls (result of the previous method call)

      // sorts based on number of shares

      .sorted(Comparator.comparing(Post::getShares).reversed())
      
      // limits number to specified user amount

      .limit(N)
      
      // creates new list
      
      .collect(Collectors.toList());

    System.out.println("The top-" + N + " posts with the most shares are:");

    for (Post post : topNPosts) {

      System.out.println(post);

    }

    return topNPosts;

  }

  public void readPosts() {
  
    posts.clear();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
  
    try {
    
      Scanner scanner = new Scanner(new File(file));
    
      // Skip the first line (header)
      
      if (scanner.hasNextLine()) {
      
        scanner.nextLine();
      
      }

      while (scanner.hasNextLine()) {

        String line = scanner.nextLine();
        String[] values = line.split(",");

        if (values.length >= 6) { 

          int postId = Integer.parseInt(values[0].trim());

          // Parse the date and time
          
          LocalDateTime dT = LocalDateTime.parse(values[5].replace("\"", ""), formatter);
          
          String dateTime = dT.format(formatter); // 'T' is replaced with a space
          String content = values[1].replace("\"", "").trim();
          String author = values[2].replace("\"", "").trim();

          int likes = Integer.parseInt(values[3].trim());
          int shares = Integer.parseInt(values[4].trim());

          Post post = new Post(postId, content, author, likes, shares, dateTime);

          posts.put(postId, post);
      
        } 
        
      }
  
    } 
    
    catch (FileNotFoundException e) {
      
      e.printStackTrace();
    
    }

  }  

  // this method is for adding
  
  public void appendPost() {

    try (FileWriter writer = new FileWriter(file, true)) {

        for (Post p : posts.values()) {

            writer.write(p + "\n");

        }

    } 
    
    catch (IOException e) {

      e.printStackTrace();

    }

  }

  // this method is for saving
  
  public void savePosts() {

    try (FileWriter writer = new FileWriter(file, false)) { // false for overwrite

      // write header if necessary

      writer.write("PostId,DateTime,Author,Content,Likes,Shares\n");

      for (Post p : posts.values()) {

        writer.write(p + "\n");

      }

    } 
    
    catch (IOException e) {

      e.printStackTrace();

    }

  }

  public int getPosts() {

    String filePath = file;
    
    int lines = 0;
    
    try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
    
      // Count the non-empty lines while skipping the header
    
      lines = (int) stream.skip(1).filter(line -> !line.trim().isEmpty()).count();
    
    } 
    
    catch (IOException e) {
    
      e.printStackTrace();
    
    }
    
    return lines;
  
  }
  
}
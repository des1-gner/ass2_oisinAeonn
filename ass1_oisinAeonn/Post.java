package ass1_oisinAeonn;

// Imports used in this Program. 

import java.io.*;

// Public Class Post Implements an Abstract Interface.

public class Post implements Serializable {

  // Post Attributes / Properties / Fields.

  private int postId;
  private String content;
  private String author;
  private int likes;
  private int shares;
  private String dateTime;

  // Post Class Attributes / Properties / Fields.

  public Post (int postId, String content, String author, int likes, int shares, String dateTime) {

    this.postId = postId;
    this.content = content;
    this.author = author;
    this.likes = likes;
    this.shares = shares;
    this.dateTime = dateTime;

  }

  // Getter methods for postId, Contents, Author, Likes, Shares, and dateTime.

  public int getPostId () {

    return postId;

  }
  
  public String getContent () {

    return content;

  }

  public String getAuthor () {

    return author;

  }

  public int getLikes () {

    return likes;

  }

  public int getShares () {

    return shares;

  }

  public String getDateTime () {

    return dateTime;

  }

  // ToString Output of the Object Post.
 
  @Override
  
  public String toString() {
    
    return postId + "," +

           content + "," +
    
           author + "," +
    
           likes + "," +
    
           shares + "," +
    
           dateTime;

  }

  public int getText() {
  
    return 0;

  }
  
}
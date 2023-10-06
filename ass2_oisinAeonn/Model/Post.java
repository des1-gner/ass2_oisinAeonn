package ass2_oisinAeonn.Model;

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
  private String image;

  // Post Class Attributes / Properties / Fields.

  public Post (int postId, String content, String author, int likes, int shares, String dateTime, String image) {

    this.postId = postId;
    this.content = content;
    this.author = author;
    this.likes = likes;
    this.shares = shares;
    this.dateTime = dateTime;
    this.image = image;

  }

  // Getter methods for postId, Contents, Author, Likes, Shares, and dateTime.

  public Post() {
}

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

  public String getImage () {

    return image;

  }

  public void setImage(String image) {
    this.image = image;
}

  // ToString Output of the Object Post.
 
  @Override
  
  public String toString() {
    return String.join(",", Integer.toString(postId), content, author, Integer.toString(likes), Integer.toString(shares), dateTime, image);
}

public void setAuthor(String username) {
  this.author = username;
}

public void setLikes(int likes) {
  this.likes = likes;
}

public void setShares(int shares) {
  this.shares = shares;
}

public void setDateTime(String dateTime) {
  this.dateTime = dateTime;
}

public void setContent(String content) {
    this.content = content;
}

public void setPostId(int postId) {
  this.postId = postId;
}

  
}
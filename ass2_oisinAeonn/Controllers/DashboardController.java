package ass2_oisinAeonn.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ass2_oisinAeonn.StageManager;
import ass2_oisinAeonn.Database.PostDAO;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Views.DashboardView;
import ass2_oisinAeonn.Views.ProfileView;
import ass2_oisinAeonn.Views.UpgradeView;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

 // Controller for the basic operations any user can do

public class DashboardController {

    public DashboardView view;
    private StageManager stageManager;
    protected String username;
    private Post post;
    private Post searchedPost;

    // Constructor, Initializes the controller with views

    public DashboardController(DashboardView view, StageManager stageManager, String username) {
        
        this.view = view;
        this.stageManager = stageManager;
        this.username = username;
        this.post = new Post();

        populateAllPostsTableView();

        view.getUploadImageButton().setOnAction(e -> handleUploadImage());

        attachHandlers();
    
    }

    // Event Handlers for Buttons mapping to their Method Functions

    private void attachHandlers() {
    
        view.getPostButton().setOnAction(e -> addPost());
        view.getProfileMenuItem().setOnAction(e -> showProfileScene());
        view.getUpgradeMenuItem().setOnAction(e -> showUpgradeScene());
        view.getLogoutMenuItem().setOnAction(e -> handleLogoutAction());
        view.getRetrieveButton().setOnAction(e -> retrievePost());
        view.getDeleteButton().setOnAction(e -> deletePost());
        view.getFilterButton().setOnAction(e -> retrieveTrendingPosts());
        view.getExportSearchedPostButton().setOnAction(e -> handleExportSearchedPostToCSV());
        view.getExportSelectedPostsButton().setOnAction(e -> handleExportSelectedPostsToCSV());

    }

    // Populate the TableView with all the Posts

    private void populateAllPostsTableView() {
    
        List<Post> allPosts = PostDAO.getAllPosts();
    
        view.getAllPostsTableView().setItems(FXCollections.observableArrayList(allPosts));
    
    }

    // Export the posts selected in the TableView to CSV

    protected void handleExportSelectedPostsToCSV() {
    
        List<Post> selectedPosts = view.getAllPostsTableView().getSelectionModel().getSelectedItems();
        
        if (selectedPosts.isEmpty()) {
    
            showAlert("Info", "Please select posts before exporting.");
    
            return;
    
        }
    
        FileChooser fileChooser = new FileChooser();
    
        fileChooser.setTitle("Export Selected Posts as CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    
        File file = fileChooser.showSaveDialog(view.getPane().getScene().getWindow());
    
        if (file != null) {
    
            try (FileWriter writer = new FileWriter(file)) {
    
                String csvHeader = "postId,content,author,likes,shares,dateTime,image\n";
    
                writer.write(csvHeader);
                
                for (Post post : selectedPosts) {
    
                    String postAttributes = post.getPostId() + ","
    
                    + "\"" + post.getContent() + "\"" + ","
                    + "\"" + post.getAuthor() + "\"" + ","
                    + post.getLikes() + ","
                    + post.getShares() + ","
                    + "\"" + post.getDateTime() + "\"" + ","
                    + "\"" + (post.getImage() != null ? post.getImage() : "") + "\"" + "\n";
                    writer.write(postAttributes);
        
                }
        
            } 
            
            catch (IOException e) {
            
                e.printStackTrace();
            
                showAlert("Error", "Failed to export posts to CSV.");
            
            }
        
        }
    
    }

    // Add a Post (adapted from Ass1 to take user input from JavaFX GUI)

    private void addPost() {

        if (!view.isContentValid()) {
    
            showAlert("Error", "Please enter the post content.");
    
            return;
    
        }
    
        if (!view.isLikesValid()) {
    
            showAlert("Error", "Please enter a valid positive integer for likes.");
    
            return;
    
        }
    
        if (!view.isSharesValid()) {
    
            showAlert("Error", "Please enter a valid positive integer for shares.");
    
            return;
    
        }
    
        if (!view.isDateValid()) {
    
            showAlert("Error", "Please select a date.");
    
            return;
    
        }
    
        if (!view.isImageUploaded()) {
    
            showAlert("Error", "Please upload an image.");
    
            return;
    
        }
    
        boolean isError = false;
        String content = view.getPostContentField().getText();
    
        post.setAuthor(username);
        post.setContent(view.getPostContentField().getText());
        post.setLikes(Integer.parseInt(view.getLikesField().getText()));
        post.setShares(Integer.parseInt(view.getSharesField().getText()));

        LocalDate dateFromPicker = view.getDatePicker().getValue();
        String dateStr = dateFromPicker.toString();

        if (dateStr.contains("T")) {
        
            dateStr = dateStr.replace("T", " ");
        
        }

        LocalTime currentTime = LocalTime.now();
        LocalDateTime combinedDateTime = LocalDateTime.of(LocalDate.parse(dateStr), currentTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = combinedDateTime.format(formatter);
        
        post.setDateTime(formattedDateTime);

        if (content == null || content.trim().isEmpty()) {
        
            isError = true;
        
            showAlert("Error", "Please enter the post content.");
        
            return;
        
        }

        if (!isError) {
        
            PostDAO.insertPost(post);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
            alert.getDialogPane().getStylesheets().add(getClass().getResource("../../assets/styles.css").toExternalForm());
            alert.setTitle("Post Added");
            alert.setHeaderText(null);
            alert.setContentText("\nPost added successfully!");
            alert.showAndWait();
        
        }
    
    }

    // Export the searched post by postId to CSV

    private void handleExportSearchedPostToCSV() {
    
        if (searchedPost != null) {
    
            // Show a file dialog to choose the save location
    
            FileChooser fileChooser = new FileChooser();
    
            fileChooser.setTitle("Save Post as CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        
            File file = fileChooser.showSaveDialog(view.getPane().getScene().getWindow());

        
            if (file != null) {
        
                // Write the post attributes to the selected file
        
                try (FileWriter writer = new FileWriter(file)) {
        
                    String csvHeader = "postId,content,author,likes,shares,dateTime,image\n";
                    String postAttributes = searchedPost.getPostId() + ","  
                    
                    + "\"" + searchedPost.getContent() + "\"" + ","
                    + "\"" + searchedPost.getAuthor() + "\"" + ","
                    + searchedPost.getLikes() + ","
                    + searchedPost.getShares() + ","
                    + "\"" + searchedPost.getDateTime() + "\"" + ","
                    + "\"" + (searchedPost.getImage() != null ? searchedPost.getImage() : "") + "\"" + "\n";
                
                    writer.write(csvHeader);
                    writer.write(postAttributes);
            
                } 
                
                catch (IOException e) {
                
                    e.printStackTrace();
                
                    showAlert("Error", "Failed to export post to CSV.");
            
                }
        
            }
    
        } 
        
        else {
        
            showAlert("Info", "Please retrieve a post before exporting.");
    
        }

    }

    // Handle the process of uploading an image for a post (required to add a post)

    private void handleUploadImage() {

        try {

            File chosenFile = view.showImageFileChooser();

            if (chosenFile != null) {

                Path copiedPath = copyFileToAssets(chosenFile);

                if (copiedPath != null) {

                    post.setImage(copiedPath.toString());

                    Image image = new Image(copiedPath.toUri().toString());

                    view.setPostImageView(image);

                }

            }

        } 
        
        catch (Exception e) {
        
            e.printStackTrace();
        
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        
            errorAlert.getDialogPane().getStylesheets().add(getClass().getResource("../../assets/styles.css").toExternalForm());
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to upload the image.");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        
        }
    
    }

    // Copies the chosen image to the assets folder

    private Path copyFileToAssets(File chosenFile) {
    
        Path destDir = Paths.get("assets");
    
        if (!Files.exists(destDir)) {
    
            try {
    
                Files.createDirectory(destDir);
    
            } 
            
            catch (IOException e) {
            
                e.printStackTrace();
            
                return null;
            
            }
        
        }
        
        Path dest = destDir.resolve(chosenFile.getName());
        
        try {
        
            Files.copy(chosenFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        
        } 
        
        catch (IOException e) {
        
            e.printStackTrace();
        
            return null;
        
        }
        
        return dest;
    
    }

    // Switch the Scene to Profile View

    public void showProfileScene() {
    
        ProfileView profileView = new ProfileView();  // Create an instance of the ProfileView
    
        new ProfileController(profileView, username, this.view, stageManager);  // This sets up the event handlers, etc
    
        // Now swap the content
    
        Scene currentScene = view.getPane().getScene();
    
        currentScene.setRoot(profileView.getMainLayout());
    
    }

    // Utility Method to display alerts to the user
    
    public void showAlert(String title, String message) {
    
        Alert alert = new Alert(Alert.AlertType.ERROR);
    
        alert.getDialogPane().getStylesheets().add(getClass().getResource("../../assets/styles.css").toExternalForm());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    
    }
    
    // Switch to the Upgrade Scene 

    public void showUpgradeScene() {
    
        UpgradeView upgradeView = new UpgradeView(); // Create the view
    
        new UpgradeController(upgradeView, username, this.view); // This will setup all the necessary handlers
    
        // Now swap the content
    
        Scene currentScene = view.getPane().getScene();
    
        currentScene.setRoot(upgradeView.getMainFrame());
    
    }

    // Handles the logout process, closing all windows and opening up the first stage
      
    protected void handleLogoutAction() {
    
        try {
    
            ((Stage) view.getPane().getScene().getWindow()).close();
    
            stageManager.setupLoginRegisterStage();
    
        } 
        
        catch (Exception e) {
        
            e.printStackTrace();
        
        }
    
    }

    // Retrieve a post by postId, and display it to a listView

    private void retrievePost() {
    
        int postId;
    
        try {
    
            postId = Integer.parseInt(view.getSearchField().getText());
        
        } 
        
        catch (NumberFormatException e) {
        
            showAlert("Error", "Invalid Post ID");
        
            return;
        
        }
    
        Post post = PostDAO.getPostById(postId);
        
        searchedPost = PostDAO.getPostById(postId); // Store the retrieved post
        
        if (post != null) {
        
            view.getSearchResultsArea().setText(
            "postId: " +post.getPostId() + "\n" +
            "Author: " + post.getAuthor() + "\n" +
            "Content: " + post.getContent() + "\n" +
            "Likes: " + post.getLikes() + "\n" +
            "Shares: " + post.getShares() + "\n" +
            "dateTime: " + post.getDateTime() + "\n" +
            "Image: " + post.getImage());
    
            // Display image if it exists

            if (post.getImage() != null && !post.getImage().trim().isEmpty()) {

                String imagePath = "file:" + post.getImage();
                Image image = new Image(imagePath);

                view.getSearchedPostImageView().setImage(image);

            } 
            
            else {
            
                view.getSearchedPostImageView().setImage(null); // clear any previous image
            
            }
            
        } 
        
        else {
        
            view.getSearchedPostImageView().setImage(null); // clear any previous image
        
            showAlert("Info", "No post found for the given ID.");
        
        }
    
    }
    
    // Delete a post by its postId

    private void deletePost() {

        int postId;
        
        try {
        
            postId = Integer.parseInt(view.getSearchField().getText());
        
        } 
        
        catch (NumberFormatException e) {
        
            showAlert("Error", "Invalid Post ID");
        
            return;
        
        }
    
        // Check if the post exists before attempting deletion
        
        Post postBeforeDeletion = PostDAO.getPostById(postId);
        
        if (postBeforeDeletion == null) {
        
            showAlert("Info", "No post found for the given ID.");
        
            return;
        
        }
    
        // Attempt deletion
        
        PostDAO.deletePostById(postId);
    
        // Check if the post still exists after the attempted deletion
        
        Post postAfterDeletion = PostDAO.getPostById(postId);
        
        if (postAfterDeletion == null) {
        
            view.getSearchResultsArea().setText("Post successfully deleted.");
        
        } 
        
        else {
        
            showAlert("Error", "Failed to delete post.");
        
        }
    
    }
    
    // Retrieve and display a list of trending posts to the user via the trending tab

    // This is the closest thing to a collection

    private void retrieveTrendingPosts() {
    
        // Extract user's choice for column to sort by
    
        RadioButton selectedRadioButton = (RadioButton) view.getRadioGroup().getSelectedToggle();
        String selectedColumn = "";
        
        if (selectedRadioButton == view.getPostIdRadio()) {
        
            selectedColumn = "postId";
        
        } 
        
        else if (selectedRadioButton == view.getDateRadio()) {
        
            selectedColumn = "dateTime";
        
        } 
        
        else if (selectedRadioButton == view.getLikesRadio()) {
        
            selectedColumn = "likes";
        
        } 
        
        else if (selectedRadioButton == view.getSharesRadio()) {
        
            selectedColumn = "shares";
        
        }
    
        // Extract user's choice for sort order
        
        boolean isAscending = "Ascending".equals(view.getSortOrderComboBox().getValue());
    
        // Extract user's choice for number of posts
        
        int retrieveCount;
        
        try {
        
            retrieveCount = Integer.parseInt(view.getRetrieveCountField().getText());
    
            // Check if the value is a non-positive integer (i.e., <= 0)
        
            if (retrieveCount <= 0) {
        
                showAlert("Error", "Please enter a natural number for posts to retrieve.");
        
                return;
        
            }
    
            if (retrieveCount > 100) retrieveCount = 100;  // Cap at 100 as per the placeholder
    
        } 
        
        catch (NumberFormatException e) {
        
            showAlert("Error", "Please enter a valid number for posts to retrieve.");
        
            return;
        
        }
        
        String usernameFilter = view.getUsernameFilterField().getText();
        List<Post> trendingPosts = PostDAO.getTrendingPosts(selectedColumn, isAscending, retrieveCount, usernameFilter);

        // Update the UI with the retrieved posts

        view.getPostsListView().setItems(FXCollections.observableArrayList(trendingPosts));
    
    }
    
}
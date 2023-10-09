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
import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Views.DashboardView;
import ass2_oisinAeonn.Views.ProfileView;
import ass2_oisinAeonn.Views.UpgradeView;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DashboardController {

    public DashboardView view;
    private StageManager stageManager;
    protected String username;
    private Post post;
    private Post searchedPost;

    public DashboardController(DashboardView view, StageManager stageManager, String username) {
        this.view = view;
        this.stageManager = stageManager;
        this.username = username;

        this.post = new Post();

        view.getUploadImageButton().setOnAction(e -> handleUploadImage());

        attachHandlers();
    }

    private void attachHandlers() {
        view.getPostButton().setOnAction(e -> addPost());
        view.getProfileMenuItem().setOnAction(e -> showProfileScene());
        view.getUpgradeMenuItem().setOnAction(e -> showUpgradeScene());
        view.getLogoutMenuItem().setOnAction(e -> handleLogoutAction());
        view.getRetrieveButton().setOnAction(e -> retrievePost());
        view.getDeleteButton().setOnAction(e -> deletePost());
        view.getFilterButton().setOnAction(e -> retrieveTrendingPosts());
        view.getExportSearchedPostButton().setOnAction(e -> handleExportSearchedPostToCSV());
    }

    private void addPost() {
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
            DatabaseConnector.insertPost(post);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Post Added");
            alert.setHeaderText(null);
            alert.setContentText("\nContent: " + content);
            alert.showAndWait();
        }
    }

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
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to export post to CSV.");
            }
        }
    } else {
        showAlert("Info", "Please retrieve a post before exporting.");
    }
}

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
        } catch (Exception e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to upload the image.");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    private Path copyFileToAssets(File chosenFile) {
        Path destDir = Paths.get("assets");
        if (!Files.exists(destDir)) {
            try {
                Files.createDirectory(destDir);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        Path dest = destDir.resolve(chosenFile.getName());
        try {
            Files.copy(chosenFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return dest;
    }

    public void showProfileScene() {
        ProfileView profileView = new ProfileView();  // Create an instance of the ProfileView
        new ProfileController(profileView, username, this.view, stageManager);  // This sets up the event handlers, etc.
    
        // Now swap the content
        Scene currentScene = view.getPane().getScene();
        currentScene.setRoot(profileView.getMainLayout());
    }
    

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

    public void showUpgradeScene() {
        UpgradeView upgradeView = new UpgradeView(); // Create the view
        new UpgradeController(upgradeView, username, this.view); // This will setup all the necessary handlers
    
        // Now swap the content
        Scene currentScene = view.getPane().getScene();
        currentScene.setRoot(upgradeView.getMainLayout());
    }    

    protected void handleLogoutAction() {
        try {
            ((Stage) view.getPane().getScene().getWindow()).close();
            stageManager.setupLoginStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retrievePost() {
        int postId;
        try {
            postId = Integer.parseInt(view.getSearchField().getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Post ID");
            return;
        }
    
        Post post = DatabaseConnector.getPostById(postId);
        searchedPost = DatabaseConnector.getPostById(postId); // Store the retrieved post
        if (post != null) {
            view.getSearchResultsArea().setText(
                "Author: " + post.getAuthor() + "\n" +
                "Content: " + post.getContent() + "\n" +
                "Likes: " + post.getLikes() + "\n" +
                "Shares: " + post.getShares() + "\n" +
                "DateTime: " + post.getDateTime() + "\n" +
                "Image: " + post.getImage()
            );
    
            // Display image if it exists
            if (post.getImage() != null && !post.getImage().trim().isEmpty()) {
                String imagePath = "file:" + post.getImage();
                Image image = new Image(imagePath);
                view.getSearchedPostImageView().setImage(image);
            } else {
                view.getSearchedPostImageView().setImage(null); // clear any previous image
            }
            
            
            
        } else {
            view.getSearchedPostImageView().setImage(null); // clear any previous image
            showAlert("Info", "No post found for the given ID.");
        }
    }
    
    
    private void deletePost() {
        int postId;
        try {
            postId = Integer.parseInt(view.getSearchField().getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Post ID");
            return;
        }
    
        DatabaseConnector.deletePostById(postId);
        view.getSearchResultsArea().setText("Post successfully deleted.");
    }

    private void retrieveTrendingPosts() {
        // Extract user's choice for column to sort by
        RadioButton selectedRadioButton = (RadioButton) view.getRadioGroup().getSelectedToggle();
        String selectedColumn = "";
        if (selectedRadioButton == view.getPostIdRadio()) {
            selectedColumn = "postId";
        } else if (selectedRadioButton == view.getDateRadio()) {
            selectedColumn = "dateTime";
        } else if (selectedRadioButton == view.getLikesRadio()) {
            selectedColumn = "likes";
        } else if (selectedRadioButton == view.getSharesRadio()) {
            selectedColumn = "shares";
        }
    
        // Extract user's choice for sort order
        boolean isAscending = "Ascending".equals(view.getSortOrderComboBox().getValue());
    
        // Extract user's choice for number of posts
        int retrieveCount;
        try {
            retrieveCount = Integer.parseInt(view.getRetrieveCountField().getText());
            if (retrieveCount > 100) retrieveCount = 100;  // Cap at 100 as per the placeholder
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for posts to retrieve.");
            return;
        }
        String usernameFilter = view.getUsernameFilterField().getText();

        List<Post> trendingPosts = DatabaseConnector.getTrendingPosts(selectedColumn, isAscending, retrieveCount, usernameFilter);

        // Update the UI with the retrieved posts
        view.getPostsListView().setItems(FXCollections.observableArrayList(trendingPosts));
    }
    
    
}

package ass2_oisinAeonn.Controllers;

import java.io.File;
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

import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.UI.DashboardView;
import ass2_oisinAeonn.UI.ProfileView;
import ass2_oisinAeonn.UI.UpgradeView;
import ass2_oisinAeonn.UI.StageManager;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController {

    private DashboardView view;
    private StageManager stageManager;
    private String username;
    private Post post;

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
        Scene currentScene = view.getPane().getScene();
        VBox profileView = view.getProfileView();
        Button backButton = (Button) profileView.getChildren().stream()
                                   .filter(node -> node instanceof Button)
                                   .findFirst().orElse(null);
        if (backButton != null) {
            backButton.setOnAction(e -> currentScene.setRoot(view.getDashboardView()));
        }
        currentScene.setRoot(profileView);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

    public void showUpgradeScene() {
        Scene currentScene = view.getPane().getScene();
        VBox upgradeView = view.getUpgradeView();
        Button backButton = (Button) upgradeView.getChildren().stream()
                                   .filter(node -> node instanceof Button)
                                   .findFirst().orElse(null);
        if (backButton != null) {
            backButton.setOnAction(e -> currentScene.setRoot(view.getDashboardView()));
        }
        currentScene.setRoot(upgradeView);
    }

    private void handleLogoutAction() {
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
        if (post != null) {
            view.getSearchResultsArea().setText(
                "Author: " + post.getAuthor() + "\n" +
                "Content: " + post.getContent() + "\n" +
                "Likes: " + post.getLikes() + "\n" +
                "Shares: " + post.getShares() + "\n" +
                "DateTime: " + post.getDateTime() + "\n" +
                "Image: " + post.getImage()
            );
        } else {
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
}

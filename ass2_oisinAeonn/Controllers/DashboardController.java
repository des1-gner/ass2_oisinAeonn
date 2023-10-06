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
        view.getFilterButton().setOnAction(e -> filterAndLoadTrendingPosts());

        attachHandlers();
    }

    private void attachHandlers() {
        view.getPostButton().setOnAction(e -> addPost());
        view.getProfileMenuItem().setOnAction(e -> showProfileScene());
        view.getUpgradeMenuItem().setOnAction(e -> showUpgradeScene());
        view.getLogoutMenuItem().setOnAction(e -> handleLogoutAction());
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
        System.out.println(post);

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

    private void filterAndLoadTrendingPosts() {
    String filterType = null;
    if (view.getPostIdRadio().isSelected()) {
        filterType = "postId";
    } else if (view.getLikesRadio().isSelected()) {
        filterType = "likes";
    } else if (view.getSharesRadio().isSelected()) {
        filterType = "shares";
    }

    int retrieveCount;
    try {
        retrieveCount = Integer.parseInt(view.getRetrieveCountField().getText().trim());
        if (retrieveCount > 100) retrieveCount = 100;
    } catch (NumberFormatException e) {
        showAlert("Error", "Please enter a valid number of posts to retrieve.");
        return;
    }

    String sortOrder = view.getSortOrderComboBox().getSelectionModel().getSelectedItem();

    List<Post> trendingPosts = DatabaseConnector.getFilteredTrendingPosts(filterType, retrieveCount, sortOrder);
    view.getTrendingPostsTable().getItems().setAll(trendingPosts);
}

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
}

package ass2_oisinAeonn.Controllers;

import java.io.File;

import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.UI.DashboardView;
import ass2_oisinAeonn.UI.ProfileView;
import ass2_oisinAeonn.UI.StageManager;
import ass2_oisinAeonn.UI.UpgradeView;
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
    
        this.post = new Post();  // Initialize the post instance
        
        view.getUploadImageButton().setOnAction(e -> handleUploadImage());
        attachHandlers();
    }
    
    

    private void attachHandlers() {
        view.getPostButton().setOnAction(e -> addPost());
        view.getUploadImageButton().setOnAction(e -> handleUploadImage());
        view.getProfileMenuItem().setOnAction(e -> showProfileScene());
        view.getUpgradeMenuItem().setOnAction(e -> showUpgradeScene());
        view.getLogoutMenuItem().setOnAction(e -> handleLogoutAction());
        
    }    

   private void addPost() {
        // Assuming you have methods to get other fields from the view.
        String content = view.getPostContentField().getText();
        post.setContent(content);
        // ... Set other attributes for post ...

        DatabaseConnector.insertPost(post); // Add post to the database

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Post Added");
        alert.setHeaderText(null);
        alert.setContentText("\nContent: " + content);
        alert.showAndWait();
    }

    private void handleUploadImage() {
        try {
            File chosenFile = view.showImageFileChooser();
            if (chosenFile != null) {
                Image image = new Image(chosenFile.toURI().toString());
                view.setPostImageView(image);
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
            // Close the current dashboard stage
            ((Stage) view.getPane().getScene().getWindow()).close();
        
            // Show the login stage again
            stageManager.setupLoginStage();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}

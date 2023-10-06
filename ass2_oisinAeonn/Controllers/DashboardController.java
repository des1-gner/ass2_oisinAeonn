package ass2_oisinAeonn.Controllers;

import java.io.File;
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

    public DashboardController(DashboardView view, StageManager stageManager, String username) {
        this.view = view;
        this.stageManager = stageManager;
        this.username = username; 
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
        String content = view.getPostContentField().getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Post Added");
        alert.setHeaderText(null);
        alert.setContentText("\nContent: " + content);
        alert.showAndWait();
    }

    private void handleUploadImage() {
        File chosenFile = view.showImageFileChooser();
        if (chosenFile != null) {
            view.setPostImageView(new Image(chosenFile.toURI().toString()));
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

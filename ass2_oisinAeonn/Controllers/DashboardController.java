package ass2_oisinAeonn.Controllers;

import java.io.File;
import ass2_oisinAeonn.UI.DashboardView;
import ass2_oisinAeonn.UI.StageManager;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DashboardController {
    
    private DashboardView view;
    private StageManager stageManager;

    public DashboardController(DashboardView view, StageManager stageManager) {
        this.view = view;
        this.stageManager = stageManager;

        attachHandlers();
    }

    private void attachHandlers() {
        view.getPostButton().setOnAction(e -> addPost());
        view.getUploadImageButton().setOnAction(e -> handleUploadImage());
        view.getProfileMenuItem().setOnAction(e -> handleProfileAction());
        view.getUpgradeAccountMenuItem().setOnAction(e -> handleUpgradeAccountAction());
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

    private void handleProfileAction() {
        // For demo purposes, use an alert. Replace with actual navigation logic.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Navigation");
        alert.setHeaderText(null);
        alert.setContentText("Navigated to Profile.");
        alert.showAndWait();
    }

    private void handleUpgradeAccountAction() {
        // For demo purposes, use an alert. Replace with actual navigation logic.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Navigation");
        alert.setHeaderText(null);
        alert.setContentText("Navigated to Upgrade Account.");
        alert.showAndWait();
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

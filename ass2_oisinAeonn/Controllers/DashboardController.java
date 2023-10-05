package ass2_oisinAeonn.Controllers;

import java.io.File;

import ass2_oisinAeonn.UI.DashboardView;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class DashboardController {
    
    private DashboardView view;

    public DashboardController(DashboardView view) {
        this.view = view;
        initializeHandlers();
        view.getUploadImageButton().setOnAction(e -> handleUploadImage());
    }

    private void initializeHandlers() {
        view.getPostButton().setOnAction(e -> addPost());
        // Add other handlers if required...
    }

    private void addPost() {
        // Extract data from fields
        String content = view.getPostContentField().getText();

        // For demonstration, display it as an Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Post Added");
        alert.setHeaderText(null);
        alert.setContentText("\nContent: " + content);
        alert.showAndWait();
    }

    private void handleUploadImage() {
        File chosenFile = view.showImageFileChooser();
        if (chosenFile != null) {
            // Handle the chosen file, e.g., save its path, display it in the UI, etc.
            // If you want to display it immediately in the Dashboard:
            view.setPostImageView(new Image(chosenFile.toURI().toString()));
        }
    }    

    // You can add more methods here as needed.
}

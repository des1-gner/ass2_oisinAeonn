package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.UI.DashboardView;
import javafx.scene.control.Alert;

public class DashboardController {
    
    private DashboardView view;

    public DashboardController(DashboardView view) {
        this.view = view;
        initializeHandlers();
    }

    private void initializeHandlers() {
        view.getPostButton().setOnAction(e -> addPost());
        // Add other handlers if required...
    }

    private void addPost() {
        // Extract data from fields
        String postId = view.getPostIdField().getText();
        String content = view.getPostContentField().getText();
        String author = view.getAuthorField().getText();

        // For demonstration, display it as an Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Post Added");
        alert.setHeaderText(null);
        alert.setContentText("ID: " + postId + "\nContent: " + content + "\nAuthor: " + author);
        alert.showAndWait();
    }

    // You can add more methods here as needed.
}

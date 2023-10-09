package ass2_oisinAeonn.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ass2_oisinAeonn.StageManager;
import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Views.VIPView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

public class VIPController extends DashboardController {

    public VIPController(VIPView view, StageManager stageManager, String username) {
        super(view, stageManager, username);
        //TODO Auto-generated constructor stub
        // Inside VIPController's constructor
view.getExportFilteredPostsButton().setOnAction(e -> handleExportFilteredPostsToCSV());
view.getUpgradeMenuItem().setOnAction(e -> handleDowngrade());

    }

    private void handleDowngrade() {
        // Prompt the user to ensure they really want to downgrade
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Downgrade Confirmation");
        alert.setHeaderText("Are you sure you want to downgrade your account to 'standard'?");
        alert.setContentText("This action cannot be undone.");

        // Show the dialog and wait for user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Downgrade the account in the database
                DatabaseConnector.updateUserType(username, "standard");
                // Log out the user
                handleLogoutAction();
            }
        });
    }

    private void handleExportFilteredPostsToCSV() {
        List<Post> filteredPosts = view.getPostsListView().getItems();
        
        if (!filteredPosts.isEmpty()) {
            // Show a file dialog to choose the save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Filtered Posts as CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(view.getPane().getScene().getWindow());
    
            if (file != null) {
                // Write the post attributes to the selected file
                try (FileWriter writer = new FileWriter(file)) {
                    String csvHeader = "postId,content,author,likes,shares,dateTime,image\n";
                    writer.write(csvHeader);
    
                    for (Post post : filteredPosts) {
                        String postAttributes = post.getPostId() + ","
                                + "\"" + post.getContent() + "\"" + ","
                                + "\"" + post.getAuthor() + "\"" + ","
                                + post.getLikes() + ","
                                + post.getShares() + ","
                                + "\"" + post.getDateTime() + "\"" + ","
                                + "\"" + (post.getImage() != null ? post.getImage() : "") + "\"" + "\n";
                        writer.write(postAttributes);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to export filtered posts to CSV.");
                }
            }
        } else {
            showAlert("Info", "There are no filtered posts to export.");
        }
    }
    

    
}

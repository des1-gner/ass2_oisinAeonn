package ass2_oisinAeonn.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import ass2_oisinAeonn.StageManager;
import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Views.VIPView;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

public class VIPController extends DashboardController {

    public VIPController(VIPView view, StageManager stageManager, String username) {
        super(view, stageManager, username);
        //TODO Auto-generated constructor stub
        // Inside VIPController's constructor
view.getUpgradeMenuItem().setOnAction(e -> handleDowngrade());
view.getImportPostsButton().setOnAction(e -> handleImportPostsFromCSV());
view.getExportFilteredPostsButton().setOnAction(e -> handleExportFilteredPostsToCSV());


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
            showAlert("Info", "There are no filtered posts to export.");}
        }
    @Override
protected void handleExportSelectedPostsToCSV() {
    List<Post> selectedPosts = ((VIPView) view).getAllPostsListView().getSelectionModel().getSelectedItems();
    
    if (!selectedPosts.isEmpty()) {
            // Show a file dialog to choose the save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Selected Posts as CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(view.getPane().getScene().getWindow());
    
            if (file != null) {
                // Write the post attributes to the selected file
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
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to export selected posts to CSV.");
                }
            }
        } else {
            showAlert("Info", "Please select posts before exporting.");
        }
    }
    
    
    private void handleImportPostsFromCSV() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Import Posts from CSV");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    File file = fileChooser.showOpenDialog(view.getPane().getScene().getWindow());

    if (file != null) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());

            // Skipping the header and parsing posts
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] attributes = line.split(",");

                // Assuming you have a method in DatabaseConnector to insert posts
                Post post = new Post(Integer.parseInt(attributes[0].replace("\"", "")), attributes[1].replace("\"", ""), attributes[2].replace("\"", ""),
    Integer.parseInt(attributes[3]), Integer.parseInt(attributes[4]), attributes[5].replace("\"", ""), attributes[6].replace("\"", ""));

                DatabaseConnector.insertPost(post);
            }
            showAlert("Success", "Posts imported successfully.");

            // Refresh the ListView after successfully importing posts
            populateAllPostsListView();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to import posts from CSV.");
        }
    }
}

private void populateAllPostsListView() {
    List<Post> allPosts = DatabaseConnector.getAllPosts();
    view.getAllPostsListView().setItems(FXCollections.observableArrayList(allPosts));
}


    
}

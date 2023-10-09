package ass2_oisinAeonn.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.UI.StageManager;
import ass2_oisinAeonn.UI.VIPView;
import javafx.stage.FileChooser;

public class VIPController extends DashboardController {

    public VIPController(VIPView view, StageManager stageManager, String username) {
        super(view, stageManager, username);
        //TODO Auto-generated constructor stub
        // Inside VIPController's constructor
view.getExportFilteredPostsButton().setOnAction(e -> handleExportFilteredPostsToCSV());

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

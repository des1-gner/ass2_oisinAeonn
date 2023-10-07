package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.UI.DashboardView;
import ass2_oisinAeonn.UI.ProfileView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ass2_oisinAeonn.Model.User;
import ass2_oisinAeonn.UI.StageManager;
import ass2_oisinAeonn.Database.DatabaseConnector;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class ProfileController {

    private ProfileView view;
    private String username;
    private DashboardView dashboardView;
    private StageManager stageManager;

    private ListView<String> postListView;
    
    public ProfileController(ProfileView view, String username, DashboardView dashboardView, StageManager stageManager) {
        this.view = view;
        this.username = username;
        this.dashboardView = dashboardView;
        this.stageManager = stageManager;
        
        User existingUser = DatabaseConnector.getUserByUsername(username);
        loadExistingDetails(existingUser);

        view.getUpdateButton().setOnAction(e -> handleUpdate());
        view.getBackButton().setOnAction(e -> handleBack());
        view.getDeleteButton().setOnAction(e -> handleDeleteAccount());
    view.getExportButton().setOnAction(e -> handleExportPostToCSV());
    }

    private void loadExistingDetails(User user) {
        view.getUsernameField().setText(user.getUsername());
        view.getFirstNameField().setText(user.getFirstName());
        view.getLastNameField().setText(user.getLastName());
    }

    private void handleUpdate() {
        String newUsername = view.getUsernameField().getText();
        String firstName = view.getFirstNameField().getText();
        String lastName = view.getLastNameField().getText();
        String newPassword = view.getPasswordField().getText();
    
        if (!newUsername.equals(username) && DatabaseConnector.checkIfUserExists(newUsername)) {
            view.getErrorLabel().setText("New username already exists");
            return;
        }
    
        if (!newPassword.isBlank()) {
            newPassword = hashPassword(newPassword);
            DatabaseConnector.updateUser(username, newUsername, firstName, lastName, newPassword);
        } else {
            DatabaseConnector.updateUserWithoutPassword(username, newUsername, firstName, lastName);
        }
    
        // If the username has changed, restart the stage
        if (!newUsername.equals(username)) {
            handleLogoutWithNewUsername(newUsername);
        } else {
            handleBack();
        }
    }

    private boolean confirmDeletion() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to delete your account? This action cannot be undone.");

    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
}
    
private void handleLogout() {
    // Close the current stage
    Stage currentStage = (Stage) view.getPane().getScene().getWindow();
    currentStage.close();
    stageManager.setupLoginStage();
}

    private void handleDeleteAccount() {
        if (confirmDeletion()) {
            DatabaseConnector.deleteUser(username);
            handleLogout();
        }
    }

    private void handleExportPostToCSV() {
        String selectedPost = postListView.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            // Show a file dialog to choose the save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Post as CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            Stage stage = (Stage) view.getPane().getScene().getWindow();
            java.io.File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                // Write the post content to the selected file
                try {
                    FileWriter writer = new FileWriter(file);
                    // Retrieve post content from the database based on 'selectedPost'
                    String postContent = DatabaseConnector.getPostContent(selectedPost);
                    writer.write(postContent);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleLogoutWithNewUsername(String newUsername) {
        // Close the current stage
        Stage currentStage = (Stage) view.getPane().getScene().getWindow();
        currentStage.close();
        
        // Now, open the new Dashboard with the new username
        stageManager.setupDashboardStage(newUsername);
    }
    
    

    private void handleBack() {
        Scene currentScene = view.getPane().getScene();
        currentScene.setRoot(dashboardView.getPane());
    }
    
    private String hashPassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public ProfileView getView() {
        return this.view;
    }
}

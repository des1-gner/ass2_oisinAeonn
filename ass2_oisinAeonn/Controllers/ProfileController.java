package ass2_oisinAeonn.Controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ass2_oisinAeonn.Model.Post;
import ass2_oisinAeonn.Model.User;
import ass2_oisinAeonn.Views.DashboardView;
import ass2_oisinAeonn.Views.ProfileView;
import ass2_oisinAeonn.StageManager;
import ass2_oisinAeonn.Database.PostDAO;
import ass2_oisinAeonn.Database.UserDAO;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
        
        User existingUser = UserDAO.getUserByUsername(username);
        loadExistingDetails(existingUser);
        populatePostListView(); // Populate the post list view

        view.getUpdateButton().setOnAction(e -> handleUpdate());
        view.getBackButton().setOnAction(e -> handleBack());
        view.getDeleteButton().setOnAction(e -> handleDeleteAccount());
    view.getExportButton().setOnAction(e -> handleExportPostToCSV());
    view.getExportAllPostsButton().setOnAction(e -> handleExportAllPostsToCSV());
    }

    private void populatePostListView() {
        List<Post> userPosts = PostDAO.getPostsByUsername(username);
    
        if (userPosts != null && !userPosts.isEmpty()) {
            view.getPostListView().getItems().setAll(userPosts);
        } else {
            view.getPostListView().getItems().clear();
        }
    }
    
    private void handleExportAllPostsToCSV() {
        List<Post> allPosts = PostDAO.getPostsByUsername(username);

        if (allPosts != null && !allPosts.isEmpty()) {
            // Show a file dialog to choose the save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save All Posts as CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            Stage stage = (Stage) view.getPane().getScene().getWindow();
            java.io.File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                // Write the post attributes to the selected file
                try {
                    FileWriter writer = new FileWriter(file);
                    String csvHeader = "postId,content,author,likes,shares,dateTime,image\n";
                    writer.write(csvHeader);
                    for (Post post : allPosts) {
                        String postAttributes = post.getPostId() + ","
                            + "\"" + post.getContent() + "\"" + ","
                            + "\"" + post.getAuthor() + "\"" + ","
                            + post.getLikes() + ","
                            + post.getShares() + ","
                            + "\"" + post.getDateTime() + "\"" + ","
                            + "\"" + post.getImage() + "\"" + "\n";
                        writer.write(postAttributes);
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

    private void loadExistingDetails(User user) {
        view.getUsernameField().setText(user.getUsername());
        view.getFirstNameField().setText(user.getFirstName());
        view.getLastNameField().setText(user.getLastName());
    }

    private void handleUpdate() {
        String newUsername = view.getUsernameField().getText().trim();
        String firstName = view.getFirstNameField().getText().trim();
        String lastName = view.getLastNameField().getText().trim();
        String newPassword = view.getPasswordField().getText().trim();
    
        String validationResult = ValidationHelper.validateFields(newUsername, firstName, lastName, newPassword);
        if (validationResult != null) {
            view.getErrorLabel().setText(validationResult);
            return;
        }
    
        if (!newUsername.equals(username) && UserDAO.checkIfUserExists(newUsername)) {
            view.getErrorLabel().setText("New username already exists");
            return;
        }
    
        List<Post> userPosts = null;
        if (!newUsername.equals(username)) {
            userPosts = PostDAO.getPostsByUsername(username);
            UserDAO.deletePostsForUser(username);
        }
    
        if (!newPassword.isBlank()) {
            newPassword = hashPassword(newPassword);
            UserDAO.updateUser(username, newUsername, firstName, lastName, newPassword);
        } else {
            UserDAO.updateUserWithoutPassword(username, newUsername, firstName, lastName);
        }
    
        if (userPosts != null && !userPosts.isEmpty()) {
            for (Post post : userPosts) {
                post.setAuthor(newUsername);
                PostDAO.insertPost(post);
            }
        }
    
        if (!newUsername.equals(username)) {
            handleLogoutWithNewUsername(newUsername);
        } else {
            handleBack();
        }
    }

    private class ValidationHelper {

        public static String validateFields(String username, String firstName, String lastName, String password) {
            if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
                return "All fields are required";
            }
    
            if (!username.matches("[a-zA-Z0-9]+") || !firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
                return "Username, First Name, and Last Name should be alphanumeric";
            }
    
            if (password.length() < 8) {
                return "Password must be at least 8 characters long";
            }
    
            if (!password.matches(".*[A-Z].*")) {
                return "Password must contain at least one uppercase letter";
            }
    
            if (!password.matches(".*[0-9].*")) {
                return "Password must contain at least one number";
            }
    
            if (!password.matches(".*[@#$%^&+=].*")) {
                return "Password must contain at least one special character (@, #, $, %, ^, &, +, =)";
            }
    
            return null;  // No validation errors
        }
    }  
    
    

    private boolean confirmDeletion() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to delete your account, and all associated posts? This action cannot be undone.");

    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
}
    
private void handleLogout() {
    // Close the current stage
    Stage currentStage = (Stage) view.getPane().getScene().getWindow();
    currentStage.close();
    stageManager.setupLoginRegisterStage();
}

private void handleDeleteAccount() {
    if (confirmDeletion()) {
        // First delete all posts of the user
        UserDAO.deletePostsForUser(username);
        // Then delete the user
        UserDAO.deleteUser(username);
        // Display a confirmation message
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Account Deleted");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Your account and all associated posts have been deleted.");
        confirmationAlert.showAndWait();
        
        handleLogout();
    }
}



    private void handleExportPostToCSV() {
        Post selectedPost = view.getPostListView().getSelectionModel().getSelectedItem();
    
        if (selectedPost != null) {
            // Show a file dialog to choose the save location
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Post as CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            Stage stage = (Stage) view.getPane().getScene().getWindow();
            java.io.File file = fileChooser.showSaveDialog(stage);
    
            if (file != null) {
                // Write the post attributes to the selected file
                try {
                    FileWriter writer = new FileWriter(file);
                    String csvHeader = "postId,content,author,likes,shares,dateTime,image\n";
                    String postAttributes = selectedPost.getPostId() + ","
                            + "\"" + selectedPost.getContent() + "\"" + ","
                            + "\"" + selectedPost.getAuthor() + "\"" + ","
                            + selectedPost.getLikes() + ","
                            + selectedPost.getShares() + ","
                            + "\"" + selectedPost.getDateTime() + "\"" + ","
                            + "\"" + selectedPost.getImage() + "\"" + "\n";
                    writer.write(csvHeader);
                    writer.write(postAttributes);
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

package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.UI.DashboardView;
import ass2_oisinAeonn.UI.ProfileView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ass2_oisinAeonn.Model.User;
import ass2_oisinAeonn.UI.StageManager;
import ass2_oisinAeonn.Database.DatabaseConnector;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProfileController {

    private ProfileView view;
    private String username;
    private DashboardView dashboardView;
    private StageManager stageManager;
    
    public ProfileController(ProfileView view, String username, DashboardView dashboardView, StageManager stageManager) {
        this.view = view;
        this.username = username;
        this.dashboardView = dashboardView;
        this.stageManager = stageManager;
        
        User existingUser = DatabaseConnector.getUserByUsername(username);
        loadExistingDetails(existingUser);

        view.getUpdateButton().setOnAction(e -> handleUpdate());
        view.getBackButton().setOnAction(e -> handleBack());
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

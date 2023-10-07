package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.UI.DashboardView;
import ass2_oisinAeonn.UI.ProfileView;
import javafx.scene.Scene;
import ass2_oisinAeonn.Model.User;
import ass2_oisinAeonn.Database.DatabaseConnector;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProfileController {

    private ProfileView view;
    private String username;
    private DashboardView dashboardView;
    
    public ProfileController(ProfileView view, String username, DashboardView dashboardView) {
        this.view = view;
        this.username = username;
        this.dashboardView = dashboardView;
        
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

        // Check if username is being changed
        if (!newUsername.equals(username)) {
            // Check if new username is already in use
            if (DatabaseConnector.checkIfUserExists(newUsername)) {
                view.getErrorLabel().setText("Username already in use!");
                return;
            }
        }

        if (!newPassword.isBlank()) {
            newPassword = hashPassword(newPassword);
            DatabaseConnector.updateUser(username, newUsername, firstName, lastName, newPassword);
        } else {
            DatabaseConnector.updateUserWithoutPassword(username, newUsername, firstName, lastName);
        }

        username = newUsername;  // Update the controller's username to the new one
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

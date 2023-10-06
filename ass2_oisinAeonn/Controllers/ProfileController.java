package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.UI.ProfileView;
import ass2_oisinAeonn.Database.DatabaseConnector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {

    private ProfileView view;

    public ProfileController(ProfileView view) {
        this.view = view;
        initializeHandlers();
    }

    private void initializeHandlers() {
        view.getUpdateButton().setOnAction(e -> handleProfileUpdate());
        view.getBackButton().setOnAction(e -> handleBack());
    }

    private void handleProfileUpdate() {
        String username = view.getUsernameField().getText();
        String currentPassword = view.getCurrentPasswordField().getText();
        String newPassword = view.getNewPasswordField().getText();
        String storedPasswordHash = null;

        // Check current password
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                storedPasswordHash = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            view.getErrorLabel().setText("Database error occurred.");
        }

        if (storedPasswordHash != null && storedPasswordHash.equals(hashPassword(currentPassword))) {
            // Update with the new password
            try (Connection connection = DatabaseConnector.getConnection()) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                stmt.setString(1, hashPassword(newPassword));
                stmt.setString(2, username);
                stmt.executeUpdate();
                view.getErrorLabel().setText("Profile updated successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                view.getErrorLabel().setText("Error updating profile.");
            }
        } else {
            view.getErrorLabel().setText("Current password is incorrect.");
        }
    }

    private void handleBack() {
        // Navigate back to the Dashboard or wherever you'd like.
        // This will depend on how you've set up your UI flow.
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

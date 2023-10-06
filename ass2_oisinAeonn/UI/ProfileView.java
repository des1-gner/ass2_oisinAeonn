package ass2_oisinAeonn.UI;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ProfileView {

    private VBox profileVBox;
    private Button updateButton;
    private Button backButton;
    private TextField usernameField;
    private PasswordField currentPasswordField;
    private PasswordField newPasswordField;

    public ProfileView(String username, Runnable onBackButtonPressed) {
        profileVBox = new VBox(10);
        Label profileInfo = new Label("Profile Info for: " + username);
        
        // Define the components
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setText(username);  // Set the username
        usernameField.setEditable(false); // Typically, users shouldn't change their usernames.
        
        currentPasswordField = new PasswordField();
        currentPasswordField.setPromptText("Current Password");
        
        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        
        updateButton = new Button("Update Profile");
        backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> onBackButtonPressed.run());

        profileVBox.getChildren().addAll(profileInfo, usernameField, currentPasswordField, newPasswordField, updateButton, backButton);
        profileVBox.setPadding(new Insets(10));
        profileVBox.setSpacing(10);
    }

    public Parent getPane() {
        return profileVBox;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getCurrentPasswordField() {
        return currentPasswordField;
    }

    public PasswordField getNewPasswordField() {
        return newPasswordField;
    }

    public Labeled getErrorLabel() {
        return null;
    }
}

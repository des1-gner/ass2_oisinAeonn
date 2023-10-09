package ass2_oisinAeonn.Views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegisterView {

    private Pane pane;
    private TextField usernameField;
    private TextField firstNameField;
    private TextField lastNameField;
    private PasswordField passwordField;
    private Button registerButton;
    private Button backButton;
    private Label errorLabel;

    public RegisterView() {
        pane = new VBox(10);
        pane.setPadding(new Insets(20));

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        registerButton = new Button("Register");

        backButton = new Button("Back");

        errorLabel = new Label();
        
        pane.getChildren().addAll(backButton, usernameField, firstNameField, lastNameField, passwordField, registerButton, errorLabel);
    }

    // Getters for fields and buttons
    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public Parent getPane() {
        return pane;
    }

    public Runnable getOnBackEvent() {
        return onBackEvent;
    }

    // Inside RegisterView class

private Runnable onBackEvent;

public void setOnBackEvent(Runnable onBackEvent) {
    this.onBackEvent = onBackEvent;
}

public boolean isUsernameValid() {
    String username = usernameField.getText();
    return username.length() >= 4;
}

public boolean isNameValid(String name) {
    return (name != null && !name.trim().isEmpty());
}

public boolean isPasswordValid() {
    String password = passwordField.getText();
    return password.matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$&*]).{8,}$");
}


}

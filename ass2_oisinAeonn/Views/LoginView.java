package ass2_oisinAeonn.Views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LoginView {

    private VBox pane;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Label errorLabel;  // This is the new error label

    public LoginView() {
        pane = new VBox(10);
        pane.setPadding(new Insets(20));

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");
        registerButton = new Button("Register");

        errorLabel = new Label();  // Initialize the error label
        errorLabel.setTextFill(Color.RED);  // Set error text color to red

        pane.getChildren().addAll(usernameField, passwordField, loginButton, registerButton, errorLabel);
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Parent getPane() {
        return pane;
    }

    // New getter for the error label
    public Label getErrorLabel() {
        return errorLabel;
    }
}

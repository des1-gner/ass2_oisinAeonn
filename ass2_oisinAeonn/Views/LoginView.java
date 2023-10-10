package ass2_oisinAeonn.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LoginView {

    private StackPane rootPane;
    private VBox pane;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Label errorLabel;

    public LoginView() {
        pane = new VBox(10);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        // Load the logo image
        Image logoImage = new Image(getClass().getResourceAsStream("../../assets/logo.jpg"));
        ImageView logoView = new ImageView(logoImage);
        // Optional: Adjust the size of the ImageView if needed
        logoView.setFitWidth(100);  // Adjust width as needed
        logoView.setPreserveRatio(true); // Preserve aspect ratio

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(200);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        loginButton = new Button("Login");
        registerButton = new Button("Register");

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        // Add the logoView at the top of your VBox
        pane.getChildren().addAll(logoView, usernameField, passwordField, loginButton, registerButton, errorLabel);

        rootPane = new StackPane(pane);
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
        return rootPane;  // Return the StackPane as the root
    }

    // New getter for the error label
    public Label getErrorLabel() {
        return errorLabel;
    }
}

package ass2_oisinAeonn.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

// Represents the graphical interface for the user login screen

public class LoginView {

    private StackPane rootPane;
    private VBox pane;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Label errorLabel;

    // Constructor initializing the login view components

    public LoginView() {
    
        pane = new VBox(10);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);

        // Load the logo image
    
        Image logoImage = new Image(getClass().getResourceAsStream("../../assets/logo.jpg"));
        ImageView logoView = new ImageView(logoImage);
    
        logoView.setFitWidth(100);  
        logoView.setPreserveRatio(true); 

        // Setup and configuration of text fields and buttons

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(200);
        usernameField.setId("usernameField");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);
        passwordField.setId("passwordField");

        loginButton = new Button("Login");
        loginButton.setId("loginButton");

        registerButton = new Button("Register");
        registerButton.setId("registerButton");

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        // Adding the components to the VBox container
        
        pane.getChildren().addAll(logoView, usernameField, passwordField, loginButton, registerButton, errorLabel);

        rootPane = new StackPane(pane);
    
    }

    // Getters

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
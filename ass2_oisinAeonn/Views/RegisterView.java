package ass2_oisinAeonn.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class RegisterView {

    private StackPane rootPane;
    private VBox pane;
    private TextField usernameField;
    private TextField firstNameField;
    private TextField lastNameField;
    private PasswordField passwordField;
    private Button registerButton;
    private Button backButton;

    public RegisterView() {
        
        pane = new VBox(10);
        pane.setPadding(new Insets(20));
        pane.setAlignment(Pos.CENTER);
    
        // Load the logo image
        
        Image logoImage = new Image(getClass().getResourceAsStream("../../assets/logo.jpg"));
        ImageView logoView = new ImageView(logoImage);
        
        logoView.setFitWidth(100); 
        logoView.setPreserveRatio(true); // Preserve aspect ratio
    
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(200);
    
        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameField.setMaxWidth(200);
    
        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.setMaxWidth(200);
    
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);
    
        backButton = new Button("Back");
        registerButton = new Button("Register");
    
        HBox buttonBox = new HBox(10); // spacing
        
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton, registerButton);
    
        pane.getChildren().addAll(logoView, usernameField, firstNameField, lastNameField, passwordField, buttonBox);
    
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

    public Parent getPane() {
    
        return pane;  // Return the StackPane as the root
    
    }

    public Runnable getOnBackEvent() {

        return onBackEvent;

    }

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
package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class loginGUI {
    
    private Pane pane;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;

    public loginGUI() {

        pane = new VBox(10);
        pane.setPadding(new Insets(20));
        
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        loginButton = new Button("Login");
        
        registerButton = new Button("Register");
        
        pane.getChildren().addAll(usernameField, passwordField, loginButton, registerButton);
    
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

}
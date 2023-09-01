package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.function.Consumer;

public class loginGUI {

    private Pane pane;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;

    private Consumer<String> onLoginSuccessEvent;
    private Runnable onRegisterEvent;

    public loginGUI() {
    
        pane = new VBox(10);
        pane.setPadding(new Insets(20));

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin());

        registerButton = new Button("Register");
        registerButton.setOnAction(e -> handleRegister());

        pane.getChildren().addAll(usernameField, passwordField, loginButton, registerButton);
    
    }

    public void setOnLoginSuccessEvent(Consumer<String> onLoginSuccessEvent) {
    
        this.onLoginSuccessEvent = onLoginSuccessEvent;
    
    }

    public void setOnRegisterEvent(Runnable onRegisterEvent) {
    
        this.onRegisterEvent = onRegisterEvent;
    
    }

    private void handleLogin() {
    
        // TODO: Handle actual authentication
    
        if (onLoginSuccessEvent != null) {
    
            onLoginSuccessEvent.accept(usernameField.getText());
    
        }
    
    }

    private void handleRegister() {
        
        if (onRegisterEvent != null) {
        
            onRegisterEvent.run();
        
        }
    
    }

    public Parent getPane() {

        return pane;
    
    }

}

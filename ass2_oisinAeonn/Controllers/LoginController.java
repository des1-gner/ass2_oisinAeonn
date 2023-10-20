package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.Database.UserDAO;
import ass2_oisinAeonn.Views.LoginView;
import javafx.scene.control.Alert;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Consumer;

// Controller responsible for Logging in

public class LoginController {

    private LoginView view;
    private Consumer<String> onLoginSuccessEvent;
    private Runnable onRegisterEvent;

    // Basic Constructor initializing the view

    public LoginController(LoginView view) {
    
        this.view = view;
    
        initEventHandlers();
    
    }

    // Setter Callbacks 

    public void setOnLoginSuccessEvent(Consumer<String> onLoginSuccessEvent) {
    
        this.onLoginSuccessEvent = onLoginSuccessEvent;
    
    }

    public void setOnRegisterEvent(Runnable onRegisterEvent) {
    
        this.onRegisterEvent = onRegisterEvent;
    
    }

    private void initEventHandlers() {
    
        view.getLoginButton().setOnAction(e -> handleLogin());
        view.getRegisterButton().setOnAction(e -> handleRegister());
    
    }

    // Handle login process by validating username, and password

    private void handleLogin() {
    
        String username = view.getUsernameField().getText();
        String enteredPassword = view.getPasswordField().getText();

        UserDAO.UserPasswordAndType userInfo = UserDAO.fetchPasswordAndUserTypeForUsername(username);
        String storedPasswordHash = userInfo.passwordHash;
        String userType = userInfo.userType;

        if (storedPasswordHash != null && storedPasswordHash.equals(hashPassword(enteredPassword))) {

            // Display a welcome alert

            Alert welcomeAlert = new Alert(Alert.AlertType.INFORMATION);

            welcomeAlert.setTitle("Welcome");
            welcomeAlert.setHeaderText(null);
            welcomeAlert.setContentText("Welcome back, " + username + "!");
            welcomeAlert.getDialogPane().getStylesheets().add(getClass().getResource("../../assets/styles.css").toExternalForm());
            welcomeAlert.showAndWait();

            if (onLoginSuccessEvent != null) {
        
                onLoginSuccessEvent.accept(userType); // Pass the user type to the event
        
            }
    
        } 
        
        else {
        
            view.getErrorLabel().setText("Invalid username or password.");
    
        }

    }    

    // Triggers callback event to swap scene to the Register page

    private void handleRegister() {
    
        if (onRegisterEvent != null) {
    
            onRegisterEvent.run();
    
        }
    
    }

    // Generates a SHA-256 hash for a given password.
    // This is used for password hashing to store and validate passwords securely.

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
    
        } 
        
        catch (NoSuchAlgorithmException e) {
        
            e.printStackTrace();
        
        }
        
        return generatedPassword;
    
    }

}
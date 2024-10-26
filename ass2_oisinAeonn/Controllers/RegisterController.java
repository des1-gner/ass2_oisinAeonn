package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.Model.User;
import ass2_oisinAeonn.Views.RegisterView;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import ass2_oisinAeonn.Database.UserDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Controller responsible for managing user registration functionality and interactions

public class RegisterController {

    private RegisterView view;
    
    // Constructor Initializes the controller with the provided view and sets up the action handlers

    public RegisterController(RegisterView view) {
        
        this.view = view;
        
        view.getRegisterButton().setOnAction(e -> handleRegister());
        view.getBackButton().setOnAction(e -> handleBack());
        
    }

    // Handles the registration process including validation

    private void handleRegister() {
    
        if (!view.isUsernameValid()) {
    
            showErrorAlert("Error", "Username should be at least 4 characters.");
    
            return;
    
        }
    
        if (!view.isNameValid(view.getFirstNameField().getText()) || !view.isNameValid(view.getLastNameField().getText())) {
    
            showErrorAlert("Error", "First name and Last name cannot be empty.");
            
            return;
        
        }
    
        if (!view.isPasswordValid()) {
        
            showErrorAlert("Error", "Password must be at least 8 characters, include a capital letter, a number, and a special character.");
        
            return;
        
        }
    
        String username = view.getUsernameField().getText();
        String firstName = view.getFirstNameField().getText();
        String lastName = view.getLastNameField().getText();
        String password = hashPassword(view.getPasswordField().getText());
    
        User user = new User(username, firstName, lastName, password, "standard", null);
    
        if (UserDAO.checkIfUserExists(username)) {
        
            showErrorAlert("Error", "Username already exists");
        
            return;
        
        }
    
        UserDAO.registerUser(user);
    
        // Display a success alert
    
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
    
        confirmationAlert.setTitle("Registration Successful");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("You have successfully registered!");
    
        styleAlert(confirmationAlert);
    
        confirmationAlert.showAndWait();
    
        // Redirect to login page
    
        handleBack();
    
    }
    
    // Displays an error alert with the provided title and message

    private void showErrorAlert(String title, String message) {
    
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
    
        styleAlert(errorAlert);
    
        errorAlert.showAndWait();
    
    }

    // Applies styling to the provided alert
    
    private void styleAlert(Alert alert) {
    
        DialogPane dialogPane = alert.getDialogPane();
    
        dialogPane.getStylesheets().add(getClass().getResource("../../assets/styles.css").toExternalForm());
    
    }

    // Handles the "back" button action (swaps scene to login page)
    
    private void handleBack() {
    
        if (view.getOnBackEvent() != null) {
    
            view.getOnBackEvent().run();
    
        }
    
    }

    // Hashes the provided password using SHA-256 and returns the hash
    
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

    // Getter for the registration view

    public RegisterView getView() {
    
        return this.view;
    
    }    

}
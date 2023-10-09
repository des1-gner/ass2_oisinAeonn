package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.Model.User;
import ass2_oisinAeonn.Views.RegisterView;
import javafx.scene.control.Alert;
import ass2_oisinAeonn.Database.DatabaseConnector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterController {

    private RegisterView view;
    
    public RegisterController(RegisterView view) {
        this.view = view;
        
        view.getRegisterButton().setOnAction(e -> handleRegister());
        view.getBackButton().setOnAction(e -> handleBack());
        
    }

    private void handleRegister() {
    if (!view.isUsernameValid()) {
        view.getErrorLabel().setText("Username should be at least 4 characters.");
        return;
    }

    if (!view.isNameValid(view.getFirstNameField().getText()) ||
        !view.isNameValid(view.getLastNameField().getText())) {
        view.getErrorLabel().setText("First name and Last name cannot be empty.");
        return;
    }

    if (!view.isPasswordValid()) {
        view.getErrorLabel().setText("Password must be at least 8 characters, include a capital letter, a number, and a special character.");
        return;
    }

    String username = view.getUsernameField().getText();
    String firstName = view.getFirstNameField().getText();
    String lastName = view.getLastNameField().getText();
    String password = hashPassword(view.getPasswordField().getText());

    User user = new User(username, firstName, lastName, password, null);

    if (DatabaseConnector.checkIfUserExists(username)) {
        view.getErrorLabel().setText("Username already exists");
        return;
    }

    DatabaseConnector.registerUser(user);

    // Display a success alert
    Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
    confirmationAlert.setTitle("Registration Successful");
    confirmationAlert.setHeaderText(null);
    confirmationAlert.setContentText("You have successfully registered!");
    confirmationAlert.showAndWait();

    // Redirect to login page
    handleBack();
}

    
    private void handleBack() {
        if (view.getOnBackEvent() != null) {
            view.getOnBackEvent().run();
        }
    }
    
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public RegisterView getView() {
        return this.view;
    }    

}

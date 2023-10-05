package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.UI.RegisterView;
import ass2_oisinAeonn.Model.User;
import ass2_oisinAeonn.Database.DatabaseConnector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RegisterController {

    private RegisterView view;
    
    public RegisterController(RegisterView view) {
        this.view = view;
        
        view.getRegisterButton().setOnAction(e -> handleRegister());
        view.getBackButton().setOnAction(e -> handleBack());
        
    }

    private void handleRegister() {
        String username = view.getUsernameField().getText();
        String firstName = view.getFirstNameField().getText();
        String lastName = view.getLastNameField().getText();
        String password = hashPassword(view.getPasswordField().getText());

        User user = new User(username, firstName, lastName, password, false, false);

        try {
            if (DatabaseConnector.checkIfUserExists(username)) {
                view.getErrorLabel().setText("Username already exists");
                return;
            }
            DatabaseConnector.registerUser(user);
            view.getErrorLabel().setText("Registration successful!");
        } catch (SQLException e) {
            e.printStackTrace();
            view.getErrorLabel().setText("Error occurred during registration.");
        }
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

package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.UI.LoginView;
import ass2_oisinAeonn.Database.DatabaseConnector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class LoginController {

    private LoginView view;
    private Consumer<String> onLoginSuccessEvent;
    private Runnable onRegisterEvent;

    public LoginController(LoginView view) {
        this.view = view;
        initEventHandlers();
    }

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

    private void handleLogin() {
        String username = view.getUsernameField().getText();
        String enteredPassword = view.getPasswordField().getText();
        String storedPasswordHash = null;
        String userType = null;  // Defaulted to null, to check later

        // Fetch stored password hash and user type for the given username
        try (Connection connection = DatabaseConnector.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT password, userType FROM users WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                storedPasswordHash = rs.getString("password");
                userType = rs.getString("userType"); // Fetch the user type
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle in a more user-friendly way in a real application
        }

        if (storedPasswordHash != null && storedPasswordHash.equals(hashPassword(enteredPassword))) {
            if (onLoginSuccessEvent != null) {
                onLoginSuccessEvent.accept(userType); // Pass the user type to the event
            }
        } else {
            view.getErrorLabel().setText("Invalid username or password.");
        }
    }

    private void handleRegister() {
        if (onRegisterEvent != null) {
            onRegisterEvent.run();
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
}

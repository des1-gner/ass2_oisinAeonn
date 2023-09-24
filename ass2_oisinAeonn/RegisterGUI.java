package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

import ass2_oisinAeonn.model.User;

public class RegisterGUI {

    private Pane pane;
    private TextField usernameField;
    private TextField firstNameField;
    private TextField lastNameField;
    private PasswordField passwordField;
    private Button registerButton;
    private Button backButton;
    private Label errorLabel;

    private Consumer<User> onRegisterSuccessEvent;
    private Runnable onBackEvent;

    private String file = "users.csv";

    private static LinkedHashMap<String, User> users;

    public RegisterGUI() {
        users = new LinkedHashMap<>();
        loadUsers();

        pane = new VBox(10);
        pane.setPadding(new Insets(20));

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        registerButton = new Button("Register");
        registerButton.setOnAction(e -> handleRegister());

        backButton = new Button("Back");
        backButton.setOnAction(e -> handleBack());

        errorLabel = new Label();
        
        pane.getChildren().addAll(backButton, usernameField, firstNameField, lastNameField, passwordField, registerButton, errorLabel);
        
    }

    public void setOnRegisterSuccessEvent(Consumer<User> onRegisterSuccessEvent) {
        this.onRegisterSuccessEvent = onRegisterSuccessEvent;
    }

    public void setOnBackEvent(Runnable onBackEvent) {
        this.onBackEvent = onBackEvent;
    }

    private void handleRegister() {
        String username = usernameField.getText();

        if (users.containsKey(username)) {
            errorLabel.setText("Username already exists");
            return;
        }

        String hashedPassword = hashPassword(passwordField.getText());
        User user = new User(username, firstNameField.getText(), lastNameField.getText(), hashedPassword);

        users.put(username, user);
        saveUsers();

        if (onRegisterSuccessEvent != null) {
            onRegisterSuccessEvent.accept(user);
        }

        errorLabel.setText("Registration successful!");
    }

    private void handleBack() {
        if (onBackEvent != null) {
            onBackEvent.run();
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

    public void saveUsers() {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("username,firstName,lastName,password\n");
            for (User u : users.values()) {
                writer.write(u + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsers() {

        try (Stream<String> stream = Files.lines(Paths.get(file))) {
        
            stream.skip(1)
        
            .forEach(line -> {
        
                String[] fields = line.split(",");
        
                if (fields.length == 4) {
        
                    User user = new User(fields[0], fields[1], fields[2], fields[3]);
        
                    users.put(fields[0], user);
        
                }
        
            });
        
        } 
        
        catch (IOException e) {
        
            e.printStackTrace();
        
        }
    
    }

    public Parent getPane() {
    
        return pane;
    
    }

}
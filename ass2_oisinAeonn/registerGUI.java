package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.function.Consumer;

public class registerGUI {

    private Pane pane;
    private TextField usernameField;
    private TextField firstNameField;
    private TextField lastNameField;
    private PasswordField passwordField;
    private Button registerButton;

    private Consumer<User> onRegisterSuccessEvent;

    public registerGUI() {

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

        pane.getChildren().addAll(usernameField, firstNameField, lastNameField, passwordField, registerButton);
    }

    public void setOnRegisterSuccessEvent(Consumer<User> onRegisterSuccessEvent) {
        this.onRegisterSuccessEvent = onRegisterSuccessEvent;
    }

    private void handleRegister() {
        // TODO: Add logic to check for valid input and store the user data
        // Create a new User instance and pass it to the onRegisterSuccessEvent
        if (onRegisterSuccessEvent != null) {
            User newUser = new User(usernameField.getText(), firstNameField.getText(), lastNameField.getText(), passwordField.getText());
            onRegisterSuccessEvent.accept(newUser);
        }
    }

    public Parent getPane() {
        return pane;
    }
}

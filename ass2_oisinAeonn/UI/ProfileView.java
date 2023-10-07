package ass2_oisinAeonn.UI;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import ass2_oisinAeonn.Model.Post; // Import your Post class


public class ProfileView {

    private VBox pane;
    private TextField usernameField;
    private TextField firstNameField;
    private TextField lastNameField;
    private PasswordField passwordField;
    private Button updateButton;
    private Button backButton;
    private Label errorLabel;
    private Button deleteButton;
    private ListView<Post> postListView;
    private Button exportButton;
    private Label postLabel;

    public ProfileView() {
        pane = new VBox(10);
        pane.setPadding(new Insets(20));

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        passwordField = new PasswordField();
        passwordField.setPromptText("New Password");

        updateButton = new Button("Update");

        backButton = new Button("Back");

        errorLabel = new Label();

        deleteButton = new Button("Delete Account");

        postListView = new ListView<>();
        postListView.setPrefHeight(200);

        exportButton = new Button("Export Post to CSV");

        postLabel = new Label("User's Posts:"); // Label for the list

        pane.getChildren().addAll(
            backButton, usernameField, firstNameField, lastNameField, passwordField, updateButton,
            deleteButton, postLabel, postListView, exportButton, errorLabel
        );
    }

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

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
    
    public Button getExportButton() {
        return exportButton;
    }
    

    public Button getBackButton() {
        return backButton;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public VBox getPane() {
        return pane;
    }

    public Parent getMainLayout() {
        return pane;
    }

    public ListView<Post> getPostListView() {
        return postListView;
    }
    
}

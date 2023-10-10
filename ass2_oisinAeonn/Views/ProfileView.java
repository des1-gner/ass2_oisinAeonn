package ass2_oisinAeonn.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import ass2_oisinAeonn.Model.Post; 


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
    private Button exportAllPostsButton;
    private Label postLabel;

    public ProfileView() {
        // Initialize all member variables first

        pane = new VBox(10);
        VBox userInfoBox = new VBox(10);  // This VBox will hold user info fields
        userInfoBox.setAlignment(Pos.CENTER);

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
        exportAllPostsButton = new Button("Export All Posts to CSV");  
        postLabel = new Label("User's Posts:");

        // Now perform modifications or additions on these member variables
        
        // Logo at the top
        Image logo = new Image(getClass().getResource("../../assets/logo.jpg").toString());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);
        
        HBox logoBox = new HBox(logoView);
        logoBox.setAlignment(javafx.geometry.Pos.CENTER);
        logoBox.setPadding(new Insets(10, 0, 20, 0));
        pane.getChildren().add(logoBox);

        // Make fields a bit smaller for a more refined look
        usernameField.setMaxWidth(200);
        firstNameField.setMaxWidth(200);
        lastNameField.setMaxWidth(200);
        passwordField.setMaxWidth(200);
        pane.getChildren().add(backButton);

        pane.getChildren().add(userInfoBox);

        userInfoBox.getChildren().addAll(
            usernameField, 
            firstNameField, 
            lastNameField, 
            passwordField, 
            updateButton,
            deleteButton
        );

        // Add the userInfoBox to the main pane

        pane.getChildren().addAll(
            postLabel, postListView, 
            exportButton, exportAllPostsButton, errorLabel
        );
    }

    public Button getExportAllPostsButton() {
        return exportAllPostsButton;
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

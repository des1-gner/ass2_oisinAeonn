package ass2_oisinAeonn.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
    private ImageView profileImageView;
    private Button changeProfileImageButton;

    public ProfileView() {
        pane = new VBox(10);
        pane.setPadding(new Insets(20));
        pane.setSpacing(20); 

        // Logo at the top
        Image logo = new Image(getClass().getResource("../../assets/logo.jpg").toString());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);
        
        HBox logoBox = new HBox(logoView);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(10, 0, 20, 0));
        pane.getChildren().add(logoBox);

        profileImageView = new ImageView(new Image("assets/profile.png"));
        profileImageView.setFitWidth(300);  // Making the profile picture much bigger
        profileImageView.setPreserveRatio(true);
        changeProfileImageButton = new Button("Change Profile Image");

        VBox profileBox = new VBox(10);
        profileBox.getChildren().addAll(profileImageView, changeProfileImageButton);

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(200);

        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameField.setMaxWidth(200);

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.setMaxWidth(200);

        passwordField = new PasswordField();
        passwordField.setPromptText("New Password");
        passwordField.setMaxWidth(200);

        updateButton = new Button("Update");
        deleteButton = new Button("Delete Account");

        VBox userInfoBox = new VBox(10);
        userInfoBox.setAlignment(Pos.CENTER);
        userInfoBox.getChildren().addAll(
            usernameField,
            firstNameField,
            lastNameField,
            passwordField,
            updateButton,
            deleteButton
        );

        HBox mainLayout = new HBox(50);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(profileBox, userInfoBox);
        backButton = new Button("Back");
userInfoBox.getChildren().add(backButton);

        postLabel = new Label("User's Posts:");
        postListView = new ListView<>();
        postListView.setPrefHeight(200);
        exportButton = new Button("Export Post to CSV");
        exportAllPostsButton = new Button("Export All Posts to CSV");
        errorLabel = new Label();

        pane.getChildren().add(mainLayout);
        pane.getChildren().addAll(
            postLabel, postListView, 
            exportButton, exportAllPostsButton, errorLabel
        );
    }

    public File showProfileImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) pane.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
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

    public Image getProfileImage() {
        return profileImageView.getImage();
    }

    public Button getChangeProfileImageButton() {
        return changeProfileImageButton;
    }

    public ImageView getProfileImageView() {
        return profileImageView;
    }
    
    
}

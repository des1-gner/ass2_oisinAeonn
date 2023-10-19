package ass2_oisinAeonn.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<Post> postTableView;
    private Button exportButton;
    private Button exportAllPostsButton;
    private Label postLabel;
    private ImageView profileImageView;
    private Button changeProfileImageButton;
    private BorderPane mainContainer;

    public ProfileView() {
        pane = new VBox(10);
        pane.setPadding(new Insets(20));
        pane.setSpacing(20); 

        // Logo at the top
        Image logo = new Image(getClass().getResource("../../assets/logo.jpg").toString());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        
        HBox logoBox = new HBox(logoView);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(10, 0, 20, 0));
        pane.getChildren().add(logoBox);

        profileImageView = new ImageView(new Image("assets/profile.png"));
        profileImageView.setFitWidth(200);  // Making the profile picture much bigger
        profileImageView.setPreserveRatio(true);
        changeProfileImageButton = new Button("Change Profile Image");

        VBox profileBox = new VBox(10);
        profileBox.getChildren().addAll(profileImageView, changeProfileImageButton);

        Label usernameLabel = new Label("Username:");
Label firstNameLabel = new Label("First Name:");
Label lastNameLabel = new Label("Last Name:");


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
        usernameLabel,    
        usernameField,
        firstNameLabel,
            firstNameField,
            lastNameLabel,
            lastNameField,
            passwordField,
            updateButton,
            deleteButton
        );

        HBox mainLayout = new HBox(50);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(profileBox, userInfoBox);
        backButton = new Button("Back");

        mainContainer = new BorderPane();
mainContainer.setTop(backButton);
mainContainer.setCenter(pane);
backButton.setPadding(new Insets(10, 10, 10, 10));


    

        postLabel = new Label("User's Posts:");
        postTableView = new TableView<>();
postTableView.setPrefHeight(300);
TableColumn<Post, Integer> postIdColumn = new TableColumn<>("Post ID");
postIdColumn.setCellValueFactory(new PropertyValueFactory<>("postId"));
postIdColumn.setMaxWidth(Double.MAX_VALUE); 
        postIdColumn.prefWidthProperty().bind(postTableView.widthProperty().multiply(0.1428)); 

TableColumn<Post, String> contentColumn = new TableColumn<>("Content");
contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
contentColumn.setMaxWidth(Double.MAX_VALUE); 
        contentColumn.prefWidthProperty().bind(postTableView.widthProperty().multiply(0.1428)); 

TableColumn<Post, String> authorColumn = new TableColumn<>("Author");
authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
authorColumn.setMaxWidth(Double.MAX_VALUE); 
        authorColumn.prefWidthProperty().bind(postTableView.widthProperty().multiply(0.1428)); 

TableColumn<Post, Integer> likesColumn = new TableColumn<>("Likes");
likesColumn.setCellValueFactory(new PropertyValueFactory<>("likes"));
likesColumn.setMaxWidth(Double.MAX_VALUE); 
        likesColumn.prefWidthProperty().bind(postTableView.widthProperty().multiply(0.1428)); 

TableColumn<Post, Integer> sharesColumn = new TableColumn<>("Shares");
sharesColumn.setCellValueFactory(new PropertyValueFactory<>("shares"));
sharesColumn.setMaxWidth(Double.MAX_VALUE); 
        sharesColumn.prefWidthProperty().bind(postTableView.widthProperty().multiply(0.1428)); 

TableColumn<Post, String> dateTimeColumn = new TableColumn<>("Date & Time");
dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
dateTimeColumn.setMaxWidth(Double.MAX_VALUE); 
        dateTimeColumn.prefWidthProperty().bind(postTableView.widthProperty().multiply(0.1428)); 

TableColumn<Post, String> imageColumn = new TableColumn<>("Image");
imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
imageColumn.setMaxWidth(Double.MAX_VALUE); 
        imageColumn.prefWidthProperty().bind(postTableView.widthProperty().multiply(0.1428)); 

postTableView.getColumns().addAll(postIdColumn, contentColumn, authorColumn, likesColumn, sharesColumn, dateTimeColumn, imageColumn);


        exportButton = new Button("Export Post to CSV");
        exportAllPostsButton = new Button("Export All Posts to CSV");
        errorLabel = new Label();

        pane.getChildren().add(mainLayout);
        pane.getChildren().addAll(postLabel, postTableView, exportButton, exportAllPostsButton, errorLabel);
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
    return mainContainer;
}


    public TableView<Post> getPostTableView() {
        return postTableView;
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

package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.Post;

public class DashboardGUI {

    protected VBox dashboardVBox;
    protected Label welcomeLabel;
    protected TabPane tabPane;
    protected MenuButton menuButton;
    protected TextArea postList;
    protected Runnable onLogoutEvent;
    protected DatePicker datePicker;
    protected ImageView postImageView; 

    public DashboardGUI(String username) {
        createDashboardView(username);
    }

    protected void createDashboardView(String username) {
        welcomeLabel = new Label("Welcome, " + username + "!");
        ImageView menuIcon = new ImageView(new Image("file:assets/menu.png"));
        menuIcon.setFitHeight(16);
        menuIcon.setFitWidth(16);
        menuButton = new MenuButton("", menuIcon);
        menuButton.setStyle("-fx-background-insets:0; -fx-padding:0;");

        MenuItem profileItem = new MenuItem("Profile");
        profileItem.setOnAction(e -> setProfileScene());

        MenuItem upgradeAccountItem = new MenuItem("Upgrade Account");
        upgradeAccountItem.setOnAction(e -> setUpgradeAccountScene());

        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(e -> handleLogout());

        menuButton.getItems().addAll(profileItem, upgradeAccountItem, logoutItem);

        Tab addPostTab = new Tab("Add Post");
        Tab searchTab = new Tab("Search");
        addPostTab.setClosable(false);
        searchTab.setClosable(false);

        TextField postIdField = new TextField();
        postIdField.setPromptText("Post ID");
        TextField postContentField = new TextField();
        postContentField.setPromptText("Content");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        
        // Date picker
        datePicker = new DatePicker();
        datePicker.setPromptText("Date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        });

        // File chooser for selecting an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        Button selectImageButton = new Button("Select Image");
        selectImageButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(dashboardVBox.getScene().getWindow());
            if (selectedFile != null) {
                // Here you can do something with the file, like display it in an ImageView
                Image image = new Image(selectedFile.toURI().toString());
                postImageView.setImage(image);
            }
        });
        
        TextField likesField = new TextField();
        likesField.setPromptText("Likes");
        TextField sharesField = new TextField();
        sharesField.setPromptText("Shares");
        TextField dateTimeField = new TextField();
        dateTimeField.setPromptText("Date & Time");
        
        Button postButton = new Button("Add Post");
        postList = new TextArea();
        postList.setEditable(false);

        postButton.setOnAction(e -> {
            String post = "Post added: " + postIdField.getText() + ", " + postContentField.getText() + ", " + authorField.getText() + ", " + datePicker.getValue().format(formatter) + ", " + likesField.getText() + ", " + sharesField.getText() + "\n";
            postList.appendText(post);
        });

        VBox addPostVBox = new VBox(postIdField, postContentField, authorField, datePicker, selectImageButton, likesField, sharesField, dateTimeField, postButton, postList);
        addPostVBox.setPadding(new Insets(15));
        addPostVBox.setSpacing(10);
        addPostTab.setContent(addPostVBox);

        // Search Functionality
        TextField searchField = new TextField();
        searchField.setPromptText("Enter Post ID...");
        Button retrieveButton = new Button("Retrieve");
        Button deleteButton = new Button("Delete");
        TextArea searchResults = new TextArea();
        searchResults.setEditable(false);
        postImageView = new ImageView();
        postImageView.setFitWidth(200);
        postImageView.setPreserveRatio(true);
        
        retrieveButton.setOnAction(e -> {
            int postId = Integer.parseInt(searchField.getText());
            String result = "Post Content: ";
            searchResults.setText(result);
            
            Image image = new Image("file:path/to/your/image.png");
            postImageView.setImage(image);
        });

        VBox searchVBox = new VBox(searchField, retrieveButton, deleteButton, searchResults, postImageView);
        searchVBox.setPadding(new Insets(15));
        searchVBox.setSpacing(10);
        searchTab.setContent(searchVBox);

        tabPane = new TabPane();
        tabPane.getTabs().addAll(addPostTab, searchTab);
        HBox topBar = new HBox(tabPane, menuButton);
        dashboardVBox = new VBox();
        dashboardVBox.setPadding(new Insets(10, 10, 10, 10));
        dashboardVBox.setSpacing(10);
        dashboardVBox.getChildren().addAll(welcomeLabel, topBar);
    }

    protected void setProfileScene() {
        VBox profileVBox = new VBox(new Label("Profile View"));
        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> setDashboardScene());
        profileVBox.getChildren().add(backButton);
        Scene scene = dashboardVBox.getScene();
        scene.setRoot(profileVBox);
    }

    protected void setUpgradeAccountScene() {
        VBox upgradeVBox = new VBox(new Label("Upgrade Account View"));
        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> setDashboardScene());
        upgradeVBox.getChildren().add(backButton);
        Scene scene = dashboardVBox.getScene();
        scene.setRoot(upgradeVBox);
    }

    protected void setDashboardScene() {
        Scene scene = dashboardVBox.getScene();
        if (scene != null) {
            scene.setRoot(dashboardVBox);
        }
    }    

    protected void handleLogout() {
        if (onLogoutEvent != null) {
            onLogoutEvent.run();
        }
    }

    public void setOnLogoutEvent(Runnable onLogoutEvent) {
        this.onLogoutEvent = onLogoutEvent;
    }

    public Parent getPane() {
        return dashboardVBox;
    }

    protected void addPost() {
        // Post post = new Post(postId, content, author, likes, shares, dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        // DatabaseConnector.insertPost(post);

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Post added successfully!");
        successAlert.showAndWait();
    // } catch (NumberFormatException ex) {
        // ... other exceptions ...
    }
// });
   // }
}

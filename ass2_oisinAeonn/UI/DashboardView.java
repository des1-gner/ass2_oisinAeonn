package ass2_oisinAeonn.UI;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardView {
    
    private VBox dashboardVBox;
    private Label welcomeLabel;
    private TabPane tabPane;
    private MenuButton menuButton;
    private TextArea postList;
    private DatePicker datePicker;
    private ImageView postImageView;
    private TextField postIdField, postContentField, authorField;
    private Button postButton;

    public DashboardView(String username) {
        dashboardVBox = new VBox(10);
        dashboardVBox.setPadding(new Insets(20));

        welcomeLabel = new Label("Welcome, " + username + "!");

        // Setting up the menu
        ImageView menuIcon = new ImageView(new Image("file:assets/menu.png"));
        menuIcon.setFitHeight(16);
        menuIcon.setFitWidth(16);
        menuButton = new MenuButton("", menuIcon);
        menuButton.setStyle("-fx-background-insets:0; -fx-padding:0;");

        MenuItem profileItem = new MenuItem("Profile");
        MenuItem upgradeAccountItem = new MenuItem("Upgrade Account");
        MenuItem logoutItem = new MenuItem("Logout");
        menuButton.getItems().addAll(profileItem, upgradeAccountItem, logoutItem);

        // Set up the tabs
        tabPane = new TabPane();

        VBox addPostVBox = new VBox(10, 
            postIdField = new TextField(),
            postContentField = new TextField(),
            authorField = new TextField(),
            datePicker = new DatePicker(),
            postButton = new Button("Post"),
            postList = new TextArea()
        );
        addPostVBox.setPadding(new Insets(15));

        postList.setEditable(false);
        postList.setPromptText("Posts will be displayed here...");
        postIdField.setPromptText("Post ID");
        postContentField.setPromptText("Content");
        authorField.setPromptText("Author");
        datePicker.setPromptText("Date of Post");

        Tab addPostTab = new Tab("Add Post", addPostVBox);
        addPostTab.setClosable(false);

        Tab searchTab = new Tab("Search");
        searchTab.setClosable(false);
        // You can add the search content for the searchTab here as you did previously.

        tabPane.getTabs().addAll(addPostTab, searchTab);

        dashboardVBox.getChildren().addAll(welcomeLabel, tabPane, menuButton);
    }

    // Getters for the controller to use
    public TextArea getPostList() {
        return postList;
    }

    public TextField getPostIdField() {
        return postIdField;
    }

    public TextField getPostContentField() {
        return postContentField;
    }

    public TextField getAuthorField() {
        return authorField;
    }

    public Button getPostButton() {
        return postButton;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public Parent getPane() {
        return dashboardVBox;
    }
}

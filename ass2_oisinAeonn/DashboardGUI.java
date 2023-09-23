package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DashboardGUI {

    private VBox vbox;
    private Label welcomeLabel;
    private TabPane tabPane;
    private MenuButton menuButton;
    private TextArea postList;

    private Runnable onLogoutEvent;

    public DashboardGUI(String username) {
        
        welcomeLabel = new Label("Welcome, " + username + "!");

        ImageView menuIcon = new ImageView(new Image("file:assets/menu.png"));
        menuIcon.setFitHeight(16);
        menuIcon.setFitWidth(16);
        menuButton = new MenuButton("", menuIcon);
        menuButton.setStyle("-fx-background-insets:0; -fx-padding:0;");
        
        MenuItem profileItem = new MenuItem("Profile");
        MenuItem upgradeAccountItem = new MenuItem("Upgrade Account");
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
        
            String post = "Post added: " + postIdField.getText() + ", " + postContentField.getText() + ", " + authorField.getText() + ", " + likesField.getText() + ", " + sharesField.getText() + ", " + dateTimeField.getText() + "\n";
            postList.appendText(post);
        
        });

        VBox addPostVBox = new VBox(postIdField, postContentField, authorField, likesField, sharesField, dateTimeField, postButton, postList);
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

        retrieveButton.setOnAction(e -> {
        
            int postId = Integer.parseInt(searchField.getText());
            String result = "Post Content: ";
            searchResults.setText(result);
        
        });

        deleteButton.setOnAction(e -> {
        
            int postId = Integer.parseInt(searchField.getText());
            String result = "Post deleted.";
            searchResults.setText(result);
        
        });

        VBox searchVBox = new VBox(searchField, retrieveButton, deleteButton, searchResults);
        searchVBox.setPadding(new Insets(15));
        searchVBox.setSpacing(10);

        searchTab.setContent(searchVBox);

        tabPane = new TabPane();
        tabPane.getTabs().addAll(addPostTab, searchTab);

        HBox topBar = new HBox(tabPane, menuButton);

        vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.getChildren().addAll(welcomeLabel, topBar);
    }

    private void handleLogout() {

        if (onLogoutEvent != null) {

            onLogoutEvent.run();

        }

    }

    public void setOnLogoutEvent(Runnable onLogoutEvent) {

        this.onLogoutEvent = onLogoutEvent;

    }

    public Parent getPane() {

        return vbox;

    }

}
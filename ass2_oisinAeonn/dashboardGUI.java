package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class dashboardGUI {

    private VBox vbox;
    private Label welcomeLabel;
    private TabPane tabPane;
    private MenuButton menuButton;
    private TextArea postList;  // To display the posts

    // Runnable to handle logout action
    private Runnable onLogoutEvent;

    public dashboardGUI(String username) {
    
        // Welcome label
    
        welcomeLabel = new Label("Welcome, " + username + "!");
        
        // Create MenuButton with an icon
    
        ImageView menuIcon = new ImageView(new Image("file:assets/menu.png"));
        menuIcon.setFitHeight(16);
        menuIcon.setFitWidth(16);
        menuButton = new MenuButton("", menuIcon);
        
        // Add menu items to the MenuButton
    
        MenuItem profileItem = new MenuItem("Profile");
        MenuItem upgradeAccountItem = new MenuItem("Upgrade Account");
        MenuItem logoutItem = new MenuItem("Logout");

        // Attach action to logoutItem
    
        logoutItem.setOnAction(e -> handleLogout());

        menuButton.getItems().addAll(profileItem, upgradeAccountItem, logoutItem);
        
        // Create Tabs
    
        Tab addPostTab = new Tab("Add Post");
        Tab searchTab = new Tab("Search");
        
        // Make tabs non-closable
    
        addPostTab.setClosable(false);
        searchTab.setClosable(false);
        
        // Existing Add Post functionality
    
        TextField postTextField = new TextField();
        postTextField.setPromptText("Write your post here...");
        Button postButton = new Button("Add Post");
        postList = new TextArea();
        postList.setEditable(false);
        
        postButton.setOnAction(e -> {
    
            String postContent = postTextField.getText();
            postList.appendText(username + ": " + postContent + "\n");
            postTextField.clear();
    
        });
        
        VBox addPostVBox = new VBox(postTextField, postButton, postList);
        addPostVBox.setPadding(new Insets(15));
        addPostVBox.setSpacing(10);
        
        addPostTab.setContent(addPostVBox);

        // New Search functionality
    
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
        
        // Create a TabPane and add tabs to it
    
        tabPane = new TabPane();
        tabPane.getTabs().addAll(addPostTab, searchTab);
        
        // Create a VBox for the welcome label and the TabPane
    
        vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.getChildren().addAll(welcomeLabel, tabPane, menuButton);
    }

    // Method to handle logout
    
    private void handleLogout() {
    
        if (onLogoutEvent != null) {
    
            onLogoutEvent.run();
    
        }
    
    }

    // Setter for onLogoutEvent
    
    public void setOnLogoutEvent(Runnable onLogoutEvent) {
    
        this.onLogoutEvent = onLogoutEvent;
    
    }

    public Parent getPane() {
    
        return vbox;
    
    }

}
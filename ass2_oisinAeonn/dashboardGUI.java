package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class dashboardGUI {
    
    private VBox vbox;
    private Label welcomeLabel;
    private TabPane tabPane;
    private MenuButton menuButton;
    
    public dashboardGUI(String username) {
        
        // Welcome label
        
        // THIS SHOULD BE FIRSTNAME, LASTNAME!!!

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
        menuButton.getItems().addAll(profileItem, upgradeAccountItem, logoutItem);
        
        // Create Tabs
        
        Tab addPostTab = new Tab("Add Post");
        Tab searchTab = new Tab("Search");
        
        // Make tabs non-closable
        
        addPostTab.setClosable(false);
        searchTab.setClosable(false);

        // Create a TabPane and add tabs to it
        
        tabPane = new TabPane();
        tabPane.getTabs().addAll(addPostTab, searchTab);
        
        // Create a horizontal box to hold the MenuButton and TabPane
        
        HBox hBox = new HBox();
        hBox.getChildren().addAll(tabPane, menuButton);
        
        // Create a VBox for the welcome label and the HBox
        
        vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.getChildren().addAll(welcomeLabel, hBox);
    
    }
    
    public Parent getPane() {

        return vbox;
    
    }

}
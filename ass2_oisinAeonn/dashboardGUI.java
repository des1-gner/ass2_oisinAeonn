package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class dashboardGUI {

    private VBox vbox;
    private Label welcomeLabel;
    private TabPane tabPane;

    public dashboardGUI(String username) {
        // Welcome label
        welcomeLabel = new Label("Welcome, " + username + "!");

        // Create a TabPane
        tabPane = new TabPane();
        
        // Create Tabs and make them non-closable
        Tab profileTab = new Tab("Edit Profile");
        profileTab.setClosable(false);
        
        Tab postTab = new Tab("Add Post");
        postTab.setClosable(false);
        
        Tab searchTab = new Tab("Post Search");
        searchTab.setClosable(false);
        
        Tab logoutTab = new Tab("Logout");
        logoutTab.setClosable(false);
        
        Tab upgradeTab = new Tab("Upgrade Account");
        upgradeTab.setClosable(false);

        // Add tabs to TabPane
        tabPane.getTabs().addAll(profileTab, postTab, searchTab, logoutTab, upgradeTab);
        
        // VBox to contain everything
        vbox = new VBox(10, welcomeLabel, tabPane);
        vbox.setPadding(new Insets(20));
    }

    public Parent getPane() {
        return vbox;
    }
}

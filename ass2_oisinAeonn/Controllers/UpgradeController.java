package ass2_oisinAeonn.Controllers;

import javafx.scene.Scene;
import ass2_oisinAeonn.Database.UserDAO;
import ass2_oisinAeonn.Views.DashboardView;
import ass2_oisinAeonn.Views.UpgradeView;

// Controller responsible for managing the user upgrade functionality and interactions

public class UpgradeController {

    private UpgradeView view;
    private String username;
    private DashboardView dashboardView;

    // Constructor Initializes the controller with the provided view, username, and dashboardView. 
    // Sets up the required event handlers.

    public UpgradeController(UpgradeView view, String username, DashboardView dashboardView) {
    
        this.view = view;
        this.username = username;
        this.dashboardView = dashboardView;

        setupHandlers();

    }

    // Sets up event handlers for the view's components

    private void setupHandlers() {

        view.getPayButton().setOnAction(e -> UserDAO.upgradeUserToVIP(username));
        view.getBackButton().setOnAction(e -> switchBackToDashboard());
          
    }

    // Switches the current view back to the dashboard view
    
    private void switchBackToDashboard() {
    
        Scene currentScene = view.getMainLayout().getScene();
    
        currentScene.setRoot(dashboardView.getPane());  // Switch back to the dashboard view
    
    }

    // Displays the upgrade view within the existing scene

    public void showUpgradeScene() {
    
        UpgradeView upgradeView = new UpgradeView(); // Create the view
    
        Scene currentScene = dashboardView.getPane().getScene();
    
        currentScene.setRoot(upgradeView.getMainLayout());
    
    }

}
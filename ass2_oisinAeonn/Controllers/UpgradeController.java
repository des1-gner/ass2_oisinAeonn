package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.UI.DashboardView;
import ass2_oisinAeonn.UI.UpgradeView;
import javafx.scene.Scene;
import ass2_oisinAeonn.Database.DatabaseConnector;

public class UpgradeController {

    private UpgradeView view;
    private String username;
    private DashboardView dashboardView;

    public UpgradeController(UpgradeView view, String username, DashboardView dashboardView) {
        this.view = view;
        this.username = username;
        this.dashboardView = dashboardView;

        setupHandlers();
    }

    private void setupHandlers() {
        view.getPayButton().setOnAction(e -> DatabaseConnector.upgradeUserToVIP(username));
        view.getBackButton().setOnAction(e -> switchBackToDashboard());
          
        }
    

    private void switchBackToDashboard() {
        Scene currentScene = view.getMainLayout().getScene();
        currentScene.setRoot(dashboardView.getPane());  // Switch back to the dashboard view
    }

    public void showUpgradeScene() {
        UpgradeView upgradeView = new UpgradeView(); // Create the view
    

        Scene currentScene = dashboardView.getPane().getScene();
        currentScene.setRoot(upgradeView.getMainLayout());
    }
    

}

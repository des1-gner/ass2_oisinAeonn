package ass2_oisinAeonn.Controllers;

import java.util.Map;

import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.UI.AdminView;
import ass2_oisinAeonn.UI.StageManager;

public class AdminController extends VIPController {

    private AdminView adminView;  // You can cast the parent's view, but for clarity, we use a new reference.

    public AdminController(AdminView view, StageManager stageManager, String username) {
        super(view, stageManager, username);
        this.adminView = view;
        // Initialize the users distribution in the chart
        setupUserDistributionChart();
    }
    
    private void setupUserDistributionChart() {
        // Fetch data from the database
        Map<String, Integer> usersDistribution = DatabaseConnector.getUsersDistribution();

        // Use the adminView to update the chart (you'll need methods in AdminView to do this)
        adminView.updateUserDistributionChart(usersDistribution);
    }
}

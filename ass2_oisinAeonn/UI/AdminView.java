package ass2_oisinAeonn.UI;

import javafx.scene.control.Tab;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class AdminView extends VIPView {

    public AdminView(String username) {
        super(username);

        // Create and add the Users Tab
        Tab usersTab = createUsersTab();
        getTabPane().getTabs().add(usersTab);  // Using a getter for tabPane
    }

    private Tab createUsersTab() {
        VBox layout = new VBox(10);

        // Generate bar chart for user distribution
        BarChart<String, Number> userDistributionChart = generateUserDistributionChart();
        
        // List of all users
        ListView<String> usersListView = new ListView<>();
        // Populate this list view with user data, e.g., usersListView.getItems().addAll(...);

        Button deleteUserBtn = new Button("Delete User");
        // Add event handler to this button to delete the selected user

        Button changeUserTypeBtn = new Button("Change User Type");
        // Add event handler to this button to change user type

        layout.getChildren().addAll(userDistributionChart, usersListView, deleteUserBtn, changeUserTypeBtn);

        Tab usersTab = new Tab("Users", layout);
        usersTab.setClosable(false);
        return usersTab;
    }

    private BarChart<String, Number> generateUserDistributionChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        barChart.getData().add(series);
        return barChart;
    }


}

package ass2_oisinAeonn.UI;

import javafx.scene.control.Tab;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class AdminView extends VIPView {

    private BarChart<String, Number> userDistributionBarChart;

    public AdminView(String username) {
        super(username);
        // Create and add the Users Tab
        Tab usersTab = createUsersTab();
        getTabPane().getTabs().add(usersTab);  // Using a getter for tabPane
    }

    private Tab createUsersTab() {
        VBox layout = new VBox(10);

        // Generate bar chart for user distribution
        userDistributionBarChart = generateUserDistributionChart();
        
        // List of all users
        ListView<String> usersListView = new ListView<>();
        // Populate this list view with user data. This should be done by fetching data from your backend

        Button deleteUserBtn = new Button("Delete User");
        // TODO: Add event handler to this button to delete the selected user

        Button changeUserTypeBtn = new Button("Change User Type");
        // TODO: Add event handler to this button to change user type

        layout.getChildren().addAll(userDistributionBarChart, usersListView, deleteUserBtn, changeUserTypeBtn);
        Tab usersTab = new Tab("Users", layout);
        usersTab.setClosable(false);

        return usersTab;
    }

    private BarChart<String, Number> generateUserDistributionChart() {
    CategoryAxis xAxis = new CategoryAxis();
    xAxis.setCategories(FXCollections.<String>observableArrayList("standard", "VIP", "admin"));  // set the categories in order
    
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
    XYChart.Series<String, Number> series = new XYChart.Series<>();

    barChart.getData().add(series);
    return barChart;
}


    public void updateUserDistributionChart(Map<String, Integer> distribution) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
    
        // Ensure all user types are in the map
        if (!distribution.containsKey("standard")) {
            distribution.put("standard", 0);
        }
        if (!distribution.containsKey("VIP")) {
            distribution.put("VIP", 0);
        }
        if (!distribution.containsKey("admin")) {
            distribution.put("admin", 0);
        }
    
        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
            if(entry.getKey() != null && entry.getValue() != null) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            } else {
                // Log or print out a warning about null values.
                System.out.println("Warning: Null key or value encountered in data distribution");
            }
        }
    
        userDistributionBarChart.getData().clear();  // Remove old data
        userDistributionBarChart.getData().add(series);  // Add new data
    }
    

    public BarChart<String, Number> getBarChart() {
        return userDistributionBarChart;
    }
}

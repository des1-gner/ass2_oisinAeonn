package ass2_oisinAeonn.UI;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.User;

import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class AdminView extends VIPView {

    private BarChart<String, Number> userDistributionBarChart;
    private ListView<User> usersListView;  // List to hold User objects

    public AdminView(String username) {
        super(username);
        // Create and add the Users Tab
        Tab usersTab = createUsersTab();
        getTabPane().getTabs().add(usersTab);
    }

    private Tab createUsersTab() {
        VBox layout = new VBox(10);

        // Generate bar chart for user distribution
        userDistributionBarChart = generateUserDistributionChart();
        
        // List of all users
        usersListView = new ListView<>();
        List<User> allUsers = DatabaseConnector.getAllUsers();
        usersListView.getItems().addAll(allUsers);

        Button deleteUserBtn = new Button("Delete User");
        deleteUserBtn.setOnAction(e -> {
            User selectedUser = usersListView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                // Provide a confirmation alert
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmation");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("You are about to delete the user and all of its posts. Do you want to continue?");
        
                // If the admin confirms the deletion
                if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                    // Delete all posts for the user
                    DatabaseConnector.deletePostsForUser(selectedUser.getUsername());
        
                    // Delete the user
                    DatabaseConnector.deleteUserByUsername(selectedUser.getUsername());
        
                    // Update the list view
                    usersListView.getItems().remove(selectedUser);
                }
            } else {
                // Display a message to select a user if no user is selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please select a user to delete.");
                alert.showAndWait();
            }
        });
        

        Button changeUserTypeBtn = new Button("Change User Type");
        changeUserTypeBtn.setOnAction(e -> {
            // For now, this button will just display a placeholder message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Functionality for changing user type is not yet implemented.");
            alert.showAndWait();
        });

        layout.getChildren().addAll(userDistributionBarChart, usersListView, deleteUserBtn, changeUserTypeBtn);
        Tab usersTab = new Tab("Users", layout);
        usersTab.setClosable(false);

        return usersTab;
    }

    private BarChart<String, Number> generateUserDistributionChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList("standard", "VIP", "admin"));
        
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        barChart.getData().add(series);
        return barChart;
    }

    public void updateUserDistributionChart(Map<String, Integer> distribution) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        if (!distribution.containsKey("standard")) {
            distribution.put("standard", 0);
        }
        if (!distribution.containsKey("VIP")) {
            distribution.put("VIP", 0);
        }
        if (!distribution.containsKey("admin")) {
            distribution.put("admin", 0);
        }

        series.getData().add(new XYChart.Data<>("standard", distribution.get("standard")));
        series.getData().add(new XYChart.Data<>("VIP", distribution.get("VIP")));
        series.getData().add(new XYChart.Data<>("admin", distribution.get("admin")));

        userDistributionBarChart.getData().clear();
        userDistributionBarChart.getData().add(series);
    }

    public BarChart<String, Number> getBarChart() {
        return userDistributionBarChart;
    }
}

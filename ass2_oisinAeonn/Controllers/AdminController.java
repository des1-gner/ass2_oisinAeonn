package ass2_oisinAeonn.Controllers;

import java.util.Map;

import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.User;
import ass2_oisinAeonn.UI.AdminView;
import ass2_oisinAeonn.UI.StageManager;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AdminController extends VIPController {
    
    private AdminView adminView;

    public AdminController(AdminView view, StageManager stageManager, String username) {
        super(view, stageManager, username);
        this.adminView = view;
        setupUserDistributionChart();
        setupButtonEventHandlers();
    }

    private void setupButtonEventHandlers() {
        adminView.deleteUserBtn.setOnAction(e -> handleDeleteUser());
        adminView.changeUserTypeBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Functionality for changing user type is not yet implemented.");
            alert.showAndWait();
        });
    }

    private void handleDeleteUser() {
        User selectedUser = adminView.getUsersListView().getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("You are about to delete the user and all of its posts. Do you want to continue?");
            if (confirmAlert.showAndWait().get() == ButtonType.OK) {
                DatabaseConnector.deletePostsForUser(selectedUser.getUsername());
                DatabaseConnector.deleteUserByUsername(selectedUser.getUsername());
                adminView.getUsersListView().getItems().remove(selectedUser);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
        }
    }
    
    public void updateUserDistributionChart(Map<String, Integer> distribution) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("standard", distribution.getOrDefault("standard", 0)));
        series.getData().add(new XYChart.Data<>("VIP", distribution.getOrDefault("VIP", 0)));
        series.getData().add(new XYChart.Data<>("admin", distribution.getOrDefault("admin", 0)));

        adminView.userDistributionBarChart.getData().clear();
        adminView.userDistributionBarChart.getData().add(series);
    }


    private void setupUserDistributionChart() {
        // Fetch data from the database
        Map<String, Integer> usersDistribution = DatabaseConnector.getUsersDistribution();

        // Use the adminView to update the chart (you'll need methods in AdminView to do this)
        updateUserDistributionChart(usersDistribution);
    }

    

}
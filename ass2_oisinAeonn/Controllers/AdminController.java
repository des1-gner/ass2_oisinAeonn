package ass2_oisinAeonn.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import ass2_oisinAeonn.StageManager;
import ass2_oisinAeonn.Database.UserDAO;
import ass2_oisinAeonn.Model.User;
import ass2_oisinAeonn.Views.AdminView;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

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
        adminView.changeUserTypeBtn.setOnAction(e -> handleChangeUserType());
    
    }

    private void handleDeleteUser() {
    
        User selectedUser = adminView.getUsersTableView().getSelectionModel().getSelectedItem();
    
        if (selectedUser != null) {
    
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("You are about to delete the user and all of its posts. Do you want to continue?");
    
            if (confirmAlert.showAndWait().get() == ButtonType.OK) {
    
                UserDAO.deletePostsForUser(selectedUser.getUsername());
                UserDAO.deleteUserByUsername(selectedUser.getUsername());
    
                adminView.getUsersTableView().getItems().remove(selectedUser);
    
            }
    
        } 
        
        else {
        
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

    private void handleChangeUserType() {
    
        User selectedUser = adminView.getUsersTableView().getSelectionModel().getSelectedItem();
    
        if (selectedUser != null) {
    
            List<String> choices = List.of("standard", "VIP", "admin");
            ChoiceDialog<String> dialog = new ChoiceDialog<>(selectedUser.getUserType(), choices);
        
            dialog.setTitle("Change User Type");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose new user type:");

            Optional<String> result = dialog.showAndWait();
            
            result.ifPresent(chosenType -> {
            
                UserDAO.updateUserType(selectedUser.getUsername(), chosenType);
            
                selectedUser.setUserType(chosenType); // Update the model (consider refreshing the ListView if needed)
            
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("User type has been changed to " + chosenType + ".");
                infoAlert.showAndWait();
        
            });
    
        } 
        
        else {
        
            Alert alert = new Alert(Alert.AlertType.WARNING);
        
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to change its type.");
            alert.showAndWait();}

            // Refresh the ListView to reflect the changes
    
            refreshUsersListView();
    
        }

    private void refreshUsersListView() {
    
        // Fetch the updated list of users
    
        List<User> updatedUsers = UserDAO.getAllUsers();
    
        // Clear the current users and update the ListView
    
        adminView.getUsersTableView().getItems().clear();
        adminView.getUsersTableView().getItems().addAll(updatedUsers);
    
    }
    

    private void setupUserDistributionChart() {
    
        // Fetch data from the database
    
        Map<String, Integer> usersDistribution = UserDAO.getUsersDistribution();

        updateUserDistributionChart(usersDistribution);
    
    }

}
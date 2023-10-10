package ass2_oisinAeonn.Views;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

import ass2_oisinAeonn.Database.DatabaseConnector;
import ass2_oisinAeonn.Model.User;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class AdminView extends VIPView {
    
    public BarChart<String, Number> userDistributionBarChart;
    public ListView<User> usersListView;
    public Button deleteUserBtn;
    public Button changeUserTypeBtn;

    public AdminView(String username) {
        super(username);
        Tab usersTab = createUsersTab();
        getTabPane().getTabs().add(usersTab);
    }

    private Tab createUsersTab() {
        VBox layout = new VBox(10);
        userDistributionBarChart = generateUserDistributionChart();
        usersListView = new ListView<>();
        List<User> allUsers = DatabaseConnector.getAllUsers();
        usersListView.getItems().addAll(allUsers);
        deleteUserBtn = new Button("Delete User");
        changeUserTypeBtn = new Button("Change User Type");
        layout.getChildren().addAll(userDistributionBarChart, usersListView, deleteUserBtn, changeUserTypeBtn);
        Tab usersTab = new Tab("Users", layout);
        usersTab.setClosable(false);
        return usersTab;
    }

    private BarChart<String, Number> generateUserDistributionChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.observableArrayList("standard", "VIP", "admin"));
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        barChart.setLegendVisible(false);
        barChart.getData().add(series);
        barChart.getStylesheets().add(getClass().getResource("../../assets/styles.css").toExternalForm());
        return barChart;
    }

    public ListView<User> getUsersListView() {
        return usersListView;
    }
}

package ass2_oisinAeonn.Views;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;

import ass2_oisinAeonn.Database.UserDAO;
import ass2_oisinAeonn.Model.User;

import java.util.List;

public class AdminView extends VIPView {
    
    public BarChart<String, Number> userDistributionBarChart;
    protected TableView<User> usersTableView;
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
        
        usersTableView = new TableView<>();
        

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setMaxWidth(Double.MAX_VALUE); 
        usernameColumn.prefWidthProperty().bind(usersTableView.widthProperty().multiply(0.25)); 
        
        TableColumn<User, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setMaxWidth(Double.MAX_VALUE); 
    firstNameColumn.prefWidthProperty().bind(usersTableView.widthProperty().multiply(0.25)); 
    

        TableColumn<User, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setMaxWidth(Double.MAX_VALUE); 
        lastNameColumn.prefWidthProperty().bind(usersTableView.widthProperty().multiply(0.25)); 
        

        TableColumn<User, String> userTypeColumn = new TableColumn<>("User Type");
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        userTypeColumn.setMaxWidth(Double.MAX_VALUE); 
        userTypeColumn.prefWidthProperty().bind(usersTableView.widthProperty().multiply(0.25)); 
        

        usersTableView.getColumns().addAll(usernameColumn, firstNameColumn, lastNameColumn, userTypeColumn);
        
        List<User> allUsers = UserDAO.getAllUsers();
        usersTableView.getItems().addAll(allUsers);
        
        deleteUserBtn = new Button("Delete User");
        deleteUserBtn.getStyleClass().add("red-button");
        changeUserTypeBtn = new Button("Change User Type");
        layout.getChildren().addAll(userDistributionBarChart, usersTableView, deleteUserBtn, changeUserTypeBtn);

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

    public TableView<User> getUsersTableView() {
        return usersTableView;
    }
}

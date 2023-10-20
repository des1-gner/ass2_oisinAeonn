package ass2_oisinAeonn.Views;

import ass2_oisinAeonn.Database.PostDAO;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.util.Map;

// Represents the graphical interface for the user VIP Extension of the dashboard screen

public class VIPView extends DashboardView {
    
    // Buttons for VIP features

    private Button exportFilteredPostsButton;
    private Button importPostsButton;

    // Default Constructor

    public VIPView(String username) {
    
        super(username); // call parent constructor
    
        // Initialize export/import buttons

        exportFilteredPostsButton = new Button("Export to CSV");
        importPostsButton = new Button("Import from CSV");
        
        // Enhance trending tab with export functionality

        Tab trendingTab = getTrendingTab();  // Retrieve the trending tab using a getter we'll define
        VBox trendingLayout = (VBox) trendingTab.getContent();

        trendingLayout.getChildren().add(exportFilteredPostsButton);

        // Enable multi-row selection in posts table view

        allPostsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Enhance all posts tab with import functionality

        Tab allTab = getAllTab();  // Retrieve the trending tab using a getter we'll define
        VBox allLayout = (VBox) allTab.getContent();

        allLayout.getChildren().add(importPostsButton);

        // Create a new visualisation tab and add it to the tab pane

        Tab visualisationTab = createVisualisationTab();

        getTabPane().getTabs().add(visualisationTab);  // Using a getter for tabPane

        // Change upgrade menu item to indicate downgrade

        getUpgradeMenuItem().setText("Downgrade Account");

    }

    private Tab getAllTab() {

        return allTab;

    }

    private Tab createVisualisationTab() {

        // Create the split pane

        SplitPane splitPane = new SplitPane();

        // Fetch data

        Map<String, Integer> likesDistribution = PostDAO.getPostsLikesDistribution();
        Map<String, Integer> sharesDistribution = PostDAO.getPostsSharesDistribution(); 

        // Generate charts

        PieChart likesPieChart = generatePieChart(likesDistribution, "Likes Distribution");
        PieChart sharesPieChart = generatePieChart(sharesDistribution, "Shares Distribution");

        // Add charts to split pane

        splitPane.getItems().addAll(likesPieChart, sharesPieChart);

        // Set the divider position (0.5 means it's in the middle)

        splitPane.setDividerPositions(0.5);

        // Create the tab and set its content

        Tab visualisationTab = new Tab("Visualisation", splitPane);

        visualisationTab.setClosable(false);

        return visualisationTab;

    }

    // create a pie chart for the distribution of shares, and likes

    private PieChart generatePieChart(Map<String, Integer> distribution, String title) {

        PieChart pieChart = new PieChart();

        for (Map.Entry<String, Integer> entry : distribution.entrySet()) {

            PieChart.Data slice = new PieChart.Data(entry.getKey() + ": " + entry.getValue(), entry.getValue());
            pieChart.getData().add(slice);

        }

        pieChart.setTitle(title);

        return pieChart;

    }

    // Getter for tabPane to access it in the subclass

    public TabPane getTabPane() {

        return tabPane;

    }

    // Various other getter methods

    public Button getExportFilteredPostsButton() {

        return exportFilteredPostsButton;

    }

    public Tab getTrendingTab() {

        for (Tab tab : getTabPane().getTabs()) {

            if (tab.getText().equals("Trending")) {

                return tab;

            }

        }

        return null;  // Should never reach here 

    }

    public ButtonBase getImportPostsButton() {

        return importPostsButton;

    }

}
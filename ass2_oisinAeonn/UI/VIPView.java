package ass2_oisinAeonn.UI;

import ass2_oisinAeonn.Database.DatabaseConnector;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.util.Map;


public class VIPView extends DashboardView {
    private Button exportFilteredPostsButton;

    public VIPView(String username) {
        super(username);
        exportFilteredPostsButton = new Button("Export to CSV");
        
        Tab trendingTab = getTrendingTab();  // Retrieve the trending tab using a getter we'll define
    VBox trendingLayout = (VBox) trendingTab.getContent();
    trendingLayout.getChildren().add(exportFilteredPostsButton);

        // Create and add the Visualisation Tab
        Tab visualisationTab = createVisualisationTab();
        getTabPane().getTabs().add(visualisationTab);  // Using a getter for tabPane
    }

    private Tab createVisualisationTab() {
        // Create the split pane
        SplitPane splitPane = new SplitPane();

        // Fetch data
        Map<String, Integer> likesDistribution = DatabaseConnector.getPostsLikesDistribution();
        Map<String, Integer> sharesDistribution = DatabaseConnector.getPostsSharesDistribution();  // Assuming you've created this method

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

    public Button getExportFilteredPostsButton() {
        return exportFilteredPostsButton;
    }

    public Tab getTrendingTab() {
        for (Tab tab : getTabPane().getTabs()) {
            if (tab.getText().equals("Trending")) {
                return tab;
            }
        }
        return null;  // Should never reach here unless tab name is changed
    }
}

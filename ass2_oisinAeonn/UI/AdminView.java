package ass2_oisinAeonn.UI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AdminView extends DashboardView {

    private Button vipFeatureButton;

    public AdminView(String username) {
    
        super(username); // Initialize the base dashboard
        setupVIPFeatures(); // Add additional VIP features
    
    }

    private void setupVIPFeatures() {

        vipFeatureButton = new Button("VIP Feature");
        
        // Add event handling or other functionalities for the VIP feature
        
        vipFeatureButton.setOnAction(e -> {
        
            // Handle VIP-specific action here
    
        });

        VBox vipBox = new VBox(vipFeatureButton);  // A container for all VIP specific features
        
        vipBox.setPadding(new Insets(10, 0, 0, 0));

        vbox.getChildren().add(vipBox);  // Add the VIP box to the main container
    
    }

}

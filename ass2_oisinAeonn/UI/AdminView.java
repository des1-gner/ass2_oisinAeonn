package ass2_oisinAeonn.UI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AdminView extends VIPView {

    private Button vipFeatureButton;

    public AdminView(String username) {
    
        super(username); // Initialize the base dashboard
        setupadminFeatures(); // Add additional VIP features
    
    }

    private void setupadminFeatures() {

        Button adminFeatureButton = new Button("Admin Feature");
        
        // Add event handling or other functionalities for the VIP feature
        
        adminFeatureButton.setOnAction(e -> {
        
            // Handle VIP-specific action here
    
        });

        VBox adminVbox = new VBox(vipFeatureButton);  // A container for all VIP specific features
        
        adminVbox.setPadding(new Insets(10, 0, 0, 0));

        adminVbox.getChildren().add(adminVbox);  // Add the VIP box to the main container
    
    }

}

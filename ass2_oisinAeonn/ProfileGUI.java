package ass2_oisinAeonn;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProfileGUI {
    
    private VBox vbox;
    
    public ProfileGUI(String username, Runnable onBackButtonPressed) {
    
        vbox = new VBox(10);
        
        Label profileInfo = new Label("Profile Info for: " + username);
    
        // You can add more profile details here
        
        Button backButton = new Button("Back to Dashboard");
    
        backButton.setOnAction(e -> onBackButtonPressed.run());

        vbox.getChildren().addAll(profileInfo, backButton);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
    
    }
    
    // Overloaded constructor to maintain the compatibility with previous codes
    
    public ProfileGUI(String username) {
    
        vbox = new VBox(10);
        
        Label profileInfo = new Label("Profile Info for: " + username);
    
        vbox.getChildren().add(profileInfo);
    
    }

    public Parent getPane() {
    
        return vbox;
    
    }

}

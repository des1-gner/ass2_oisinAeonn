package ass2_oisinAeonn;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class UpgradeAccountGUI {

    private VBox vbox;
    
    public UpgradeAccountGUI() {
        
        vbox = new VBox(10);
        
        Button upgradeButton = new Button("Upgrade to VIP");
        
        // You can add action handlers and more details here
        
        vbox.getChildren().add(upgradeButton);
    
    }
    
    public UpgradeAccountGUI(Object object) {
    
    }

    public Parent getPane() {
    
        return vbox;
    
    }

}
    
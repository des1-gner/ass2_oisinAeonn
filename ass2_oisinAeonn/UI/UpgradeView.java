package ass2_oisinAeonn.UI;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class UpgradeView {

    private VBox vbox;
    
    public UpgradeView() {
        
        vbox = new VBox(10);
        
        Button upgradeButton = new Button("Upgrade to VIP");
        
        // You can add action handlers and more details here
        
        vbox.getChildren().add(upgradeButton);
    
    }
    
    public UpgradeView(Object object) {
    
    }

    public Parent getPane() {
    
        return vbox;
    
    }

}
    
package ass2_oisinAeonn.Views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UpgradeView {

    private BorderPane mainFrame;  // Main frame
    private VBox mainLayout;       // Inner layout for main content
    private VBox radioFrame;       // Frame for the radio buttons
    private ToggleGroup paymentGroup;
    private Button payButton;
    private Button backButton;

    public UpgradeView() {
        
        // Create the main frame and set padding/margins if needed
        
        mainFrame = new BorderPane();

        // Logo at the top
        
        Image logo = new Image(getClass().getResource("/assets/logo.jpg").toString());

        ImageView logoView = new ImageView(logo);
        
        logoView.setFitWidth(500);  // Adjust width as needed
        logoView.setPreserveRatio(true); // Preserve aspect ratio
        
        // Wrapping the ImageView in an HBox to center it
        
        HBox logoBox = new HBox(logoView);
        
        logoBox.setAlignment(Pos.CENTER);
        
        backButton = new Button("Back");
        
        VBox topSection = new VBox();
        
        topSection.getChildren().addAll(backButton, logoBox);
        mainFrame.setTop(topSection);
        
        mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);

        Text vipDescription = new Text("VIP Users have access to in-depth analytics, and bulk Imports for $10 a month");

        RadioButton creditCardOption = new RadioButton("Credit Card");
        
        creditCardOption.setSelected(true);
        
        RadioButton cashOption = new RadioButton("Cash");
        RadioButton cryptoOption = new RadioButton("Cryptocurrency");
        RadioButton payLaterOption = new RadioButton("Pay Later");

        paymentGroup = new ToggleGroup();

        creditCardOption.setToggleGroup(paymentGroup);
        cashOption.setToggleGroup(paymentGroup);
        cryptoOption.setToggleGroup(paymentGroup);
        payLaterOption.setToggleGroup(paymentGroup);

        payButton = new Button("Pay");

        // Create a VBox for the radio buttons and set some padding/margins if needed
        
        radioFrame = new VBox(5, creditCardOption, cashOption, cryptoOption, payLaterOption);
        radioFrame.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        radioFrame.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(vipDescription, radioFrame, payButton);

        // Set the mainLayout to the center of the main frame
        
        mainFrame.setCenter(mainLayout);
    
    }

    public BorderPane getMainFrame() {
    
        return mainFrame;
    
    }

    public Button getPayButton() {
    
        return payButton;
    
    }

    public Button getBackButton() {
    
        return backButton;
    
    }

    public Pane getPane() {
    
        return mainFrame; // Return the main frame as the root
    
    }

    public VBox getMainLayout() {
    
        return mainLayout;
    
    }

}
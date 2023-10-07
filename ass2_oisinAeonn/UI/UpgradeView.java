package ass2_oisinAeonn.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UpgradeView {

    private VBox mainLayout;
    private ToggleGroup paymentGroup;
    private Button payButton;
    private Button backButton;

    public UpgradeView() {
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

        backButton = new Button("Back");
        mainLayout.getChildren().addAll(vipDescription, creditCardOption, cashOption, cryptoOption, payLaterOption, payButton, backButton);
        
    }

    public VBox getMainLayout() {
        return mainLayout;
    }

    public Button getPayButton() {
        return payButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Pane getPane() {
    return mainLayout; // Replace 'mainLayout' with the name of your main layout/container if different.
}

}

package ass2_oisinAeonn.UI;

import ass2_oisinAeonn.Controllers.LoginController;
import ass2_oisinAeonn.Controllers.AdminController; 
import ass2_oisinAeonn.Controllers.VIPController;
import ass2_oisinAeonn.Controllers.DashboardController;
import ass2_oisinAeonn.Controllers.RegisterController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {

    private Stage currentStage;

    public StageManager() {
        this.currentStage = new Stage();
    }

    public void setupLoginStage() {
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);

        Scene loginScene = new Scene(loginView.getPane(), 400, 300);
        currentStage.setTitle("Data Analytics Hub - Login");
        currentStage.setScene(loginScene);

        loginController.setOnLoginSuccessEvent(userType -> { // Assuming userType is a String
            closeCurrentStage();
            String username = loginView.getUsernameField().getText(); // Get the username from the login view
        
            if ("Admin".equals(userType)) {
                setupAdminStage(username);
            } else if ("VIP".equals(userType)) {
                setupVIPStage(username);
            } else {
                setupDashboardStage(username);
            }            
        });
        

        loginController.setOnRegisterEvent(() -> {
            closeCurrentStage();
            setupRegisterStage();
        });

        currentStage.show();
    }

    public void setupRegisterStage() {
        RegisterView registerView = new RegisterView();
        currentStage = new Stage();
        Scene registerScene = new Scene(registerView.getPane(), 400, 300);
        currentStage.setTitle("Data Analytics Hub - Register");
        currentStage.setScene(registerScene);

        RegisterController registerController = new RegisterController(registerView);
            registerController.getView().setOnBackEvent(() -> {
            closeCurrentStage();
            setupLoginStage();
        });        

        currentStage.show();
    }

    public void setupDashboardStage(String username) {
        DashboardView dashboardView = new DashboardView(username);
        DashboardController dashboardController = new DashboardController(dashboardView, this, username); // Linking view with controller
    
        currentStage = new Stage();
        Scene dashboardScene = new Scene(dashboardView.getPane(), 800, 600);
    
        currentStage.setTitle("Data Analytics Hub - Dashboard");
        currentStage.setScene(dashboardScene);
        currentStage.show();
    }
    

    public void setupVIPStage(String username) {
        VIPView vipView = new VIPView(username);
        currentStage = new Stage();
        Scene vipScene = new Scene(vipView.getPane(), 700, 450);

        currentStage.setTitle("Data Analytics Hub - VIP Dashboard");
        currentStage.setScene(vipScene);
        currentStage.show();
    }

    public void setupAdminStage(String username) {
        AdminView adminView = new AdminView(username); // Assuming you have this GUI.
        currentStage = new Stage();
        Scene adminScene = new Scene(adminView.getPane(), 700, 450);

        currentStage.setTitle("Data Analytics Hub - Admin Dashboard");
        currentStage.setScene(adminScene);
        currentStage.show();
    }

    private void closeCurrentStage() {
        if (currentStage != null) {
            currentStage.close();
        }
    }

    public void swapSceneContent(Parent newContent) {
        if (currentStage != null) {
            Scene newScene = new Scene(newContent, currentStage.getScene().getWidth(), currentStage.getScene().getHeight());
            currentStage.setScene(newScene);
        }
    }
    
    
}

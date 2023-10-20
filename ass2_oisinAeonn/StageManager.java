package ass2_oisinAeonn;

import ass2_oisinAeonn.Controllers.LoginController;
import ass2_oisinAeonn.Controllers.AdminController; 
import ass2_oisinAeonn.Controllers.VIPController;
import ass2_oisinAeonn.Views.AdminView;
import ass2_oisinAeonn.Views.DashboardView;
import ass2_oisinAeonn.Views.LoginView;
import ass2_oisinAeonn.Views.RegisterView;
import ass2_oisinAeonn.Views.VIPView;
import ass2_oisinAeonn.Controllers.DashboardController;
import ass2_oisinAeonn.Controllers.RegisterController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// StageManager as the title implies is responsible for managing and switching between stages of my JavaFX Project

public class StageManager {

    private Stage currentStage;

    // Default constructor, initializes a new Stage

    public StageManager() {
    
        this.currentStage = new Stage();
    
    }

    // Set the application logo

    private void setAppLogo() {
    
        Image appIcon = new Image("assets/logo.jpg");
    
        currentStage.getIcons().add(appIcon);
    
    }

    // Configures and displays the Login and Register Stage 

    public void setupLoginRegisterStage() {
    
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);
    
        Scene loginScene = new Scene(loginView.getPane(), 350, 300);
    
        loginScene.getStylesheets().add(getClass().getResource("../assets/styles.css").toExternalForm());
        currentStage.setTitle("Data Analytics Hub - Login");
        currentStage.setScene(loginScene);
    
        setAppLogo();
    
        loginController.setOnLoginSuccessEvent(userType -> { 
    
            closeCurrentStage();
    
            String username = loginView.getUsernameField().getText();
            
            if ("admin".equals(userType)) {
    
                setupAdminStage(username);
    
            } 
            
            else if ("VIP".equals(userType)) {
            
                setupVIPStage(username);
            
            } 
            
            else {
            
                setupDashboardStage(username);
            
            }            
        
        });
    
        loginController.setOnRegisterEvent(() -> {
        
            setupRegisterScene(); 
        
        });
    
        currentStage.show();
    
    }

    // Configure and swap to Register Scene
    
    public void setupRegisterScene() {
    
        RegisterView registerView = new RegisterView();
        Scene registerScene = new Scene(registerView.getPane(), 350, 300);
    
        registerScene.getStylesheets().add(getClass().getResource("../assets/styles.css").toExternalForm());
        currentStage.setTitle("Data Analytics Hub - Register");
        currentStage.setScene(registerScene); 
    
        setAppLogo();
    
        RegisterController registerController = new RegisterController(registerView);
    
        registerController.getView().setOnBackEvent(() -> {
    
            setupLoginRegisterStage();
    
        });        
    
    }    

    // Configure and display the dashboard for standard users

    public void setupDashboardStage(String username) {
    
        DashboardView dashboardView = new DashboardView(username);
        DashboardController dashboardController = new DashboardController(dashboardView, this, username); // Linking view with controller
    
        currentStage = new Stage();
    
        setAppLogo();
    
        Scene dashboardScene = new Scene(dashboardView.getPane(), 1368, 892);
    
        dashboardScene.getStylesheets().add(getClass().getResource("../assets/styles.css").toExternalForm());
    
        currentStage.setTitle("Data Analytics Hub - Dashboard");
        currentStage.setScene(dashboardScene);
        currentStage.show();
    
    }

    // Configure and display the dashboard for VIP users
    
    public void setupVIPStage(String username) {
    
        VIPView vipView = new VIPView(username);
        VIPController vipController = new VIPController(vipView, this, username);

        currentStage = new Stage();
    
        setAppLogo();
    
        Scene vipScene = new Scene(vipView.getPane(), 1368, 892);
    
        vipScene.getStylesheets().add(getClass().getResource("../assets/styles.css").toExternalForm());

        currentStage.setTitle("Data Analytics Hub - VIP Dashboard");
        currentStage.setScene(vipScene);
        currentStage.show();
    
    }

    // Configure and display the dashboard for admin users

    public void setupAdminStage(String username) {
    
        AdminView adminView = new AdminView(username);

        // Setup the AdminController

        AdminController adminController = new AdminController(adminView, this, username); 
    
        currentStage = new Stage();
    
        setAppLogo();
    
        Scene adminScene = new Scene(adminView.getPane(), 1368, 892);
    
        adminScene.getStylesheets().add(getClass().getResource("../assets/styles.css").toExternalForm());
    
        currentStage.setTitle("Data Analytics Hub - Admin Dashboard");
        currentStage.setScene(adminScene);
        currentStage.show();
    
    }

    // Closes the current stage if its open
    
    private void closeCurrentStage() {
    
        if (currentStage != null) {
    
            currentStage.close();
    
        }
    
    }

    // Update the current stage scene with new content (Refresh)

    public void swapSceneContent(Parent newContent) {
    
        if (currentStage != null) {
    
            Scene newScene = new Scene(newContent, currentStage.getScene().getWidth(), currentStage.getScene().getHeight());
    
            currentStage.setScene(newScene);
    
        }
    
    }
    
}
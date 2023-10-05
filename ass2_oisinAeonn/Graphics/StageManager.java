package ass2_oisinAeonn.Graphics;

import ass2_oisinAeonn.LoginGUI;
import ass2_oisinAeonn.RegisterGUI;
import ass2_oisinAeonn.VIPDashboardGUI;
import ass2_oisinAeonn.ProfileGUI;
import ass2_oisinAeonn.DashboardGUI;
import ass2_oisinAeonn.UpgradeAccountGUI;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {

    private Stage currentStage;

    public StageManager() {
        this.currentStage = new Stage();
    }

    public void setupLoginStage() {
        LoginGUI loginView = new LoginGUI();
        Scene loginScene = new Scene(loginView.getPane(), 400, 300);
        currentStage.setTitle("Social Media Analyzer App - Login");
        currentStage.setScene(loginScene);

        // Attach login success and register event handlers
        loginView.setOnLoginSuccessEvent(user -> {
            closeCurrentStage();
            setupDashboardStage(user);
        });

        loginView.setOnRegisterEvent(() -> {
            closeCurrentStage();
            setupRegisterStage();
        });

        currentStage.show();
    }

    private void closeCurrentStage() {
    }

    public void setupRegisterStage() {
        RegisterGUI registerView = new RegisterGUI();
        currentStage = new Stage();
        Scene registerScene = new Scene(registerView.getPane(), 400, 300);
        currentStage.setTitle("Social Media Analyzer App - Register");
        currentStage.setScene(registerScene);

        registerView.setOnBackEvent(() -> {
            closeCurrentStage();
            setupLoginStage();
        });

        currentStage.show();
    }

    public void setupDashboardStage(String username) {
        DashboardGUI dashboardView = new DashboardGUI(username);
        currentStage = new Stage();
        Scene dashboardScene = new Scene(dashboardView.getPane(), 600, 400);

        // Setting up the Profile Scene
        ProfileGUI profileView = new ProfileGUI(username);
        Scene profileScene = new Scene(profileView.getPane(), 400, 300);

        // Setting up the Upgrade Account Scene
        UpgradeAccountGUI upgradeAccountView = new UpgradeAccountGUI();
        Scene upgradeAccountScene = new Scene(upgradeAccountView.getPane(), 400, 300);

        currentStage.setTitle("Social Media Analyzer App - Dashboard"); }
    }
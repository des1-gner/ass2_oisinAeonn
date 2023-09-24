package ass2_oisinAeonn;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setupLoginStage(primaryStage);
    }

    private void setupLoginStage(Stage primaryStage) {
        LoginGUI loginView = new LoginGUI();
        Scene loginScene = new Scene(loginView.getPane(), 400, 300);
        primaryStage.setTitle("Social Media Analyzer App - Login");
        primaryStage.setScene(loginScene);

        // Attach login success and register event handlers
        loginView.setOnLoginSuccessEvent(user -> {
            primaryStage.hide();  // Close the login window
            setupDashboardStage(user);
        });

        loginView.setOnRegisterEvent(() -> {
            primaryStage.hide();  // Close the login window
            setupRegisterStage(primaryStage);
        });

        primaryStage.show();
    }

    private void setupRegisterStage(Stage previousStage) {
        RegisterGUI registerView = new RegisterGUI();
        Stage registerStage = new Stage();
        Scene registerScene = new Scene(registerView.getPane(), 400, 300);
        registerStage.setTitle("Social Media Analyzer App - Register");
        registerStage.setScene(registerScene);

        registerView.setOnBackEvent(() -> {
            registerStage.close();  // Close the register window
            previousStage.show();  // Show the login window again
        });

        registerStage.show();
    }

    private void setupDashboardStage(String username) {
        DashboardGUI dashboardView = new DashboardGUI(username);
        Stage dashboardStage = new Stage();
        Scene dashboardScene = new Scene(dashboardView.getPane(), 600, 400);
        dashboardStage.setTitle("Social Media Analyzer App - Dashboard");
        dashboardStage.setScene(dashboardScene);

        dashboardView.setOnLogoutEvent(() -> {
            dashboardStage.close();  // Close the dashboard
            setupLoginStage(new Stage());  // Show the login stage again
        });

        dashboardStage.show();
    }
}

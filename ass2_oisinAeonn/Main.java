package ass2_oisinAeonn;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {

    // create an instance of the object socialMediaAnalyzerApp class.
    
    // socialMediaAnalyzerApp socialMediaAnalyzerApp = new socialMediaAnalyzerApp();

    // Run Menu until Option 6 is Inputted by User.
    
    // socialMediaAnalyzerApp.run();
    
    // Start the JavaFX application
    
    launch(args);
  
  }

  @Override
  
  public void start(Stage primaryStage) {

    // Create the login view and set it as the scene
    
    loginGUI loginView = new loginGUI(); // Assuming you have a class named LoginGUI
    Scene loginScene = new Scene(loginView.getPane(), 400, 300);
    
    primaryStage.setTitle("Login App");
    primaryStage.setScene(loginScene);

    // Attach login success and register event handlers
    
    loginView.setOnLoginSuccessEvent((user) -> {
    
      // Create a user-specific dashboard and set it as the scene
    
      dashboardGUI dashboardView = new dashboardGUI(user); // Assuming you have a class named DashboardView
      Scene dashboardScene = new Scene(dashboardView.getPane(), 600, 400);
    
      primaryStage.setScene(dashboardScene);
    
    });

    loginView.setOnRegisterEvent(() -> {
    
      // Create a register view and set it as the scene
    
      registerGUI registerView = new registerGUI(); // Assuming you have a class named RegisterView
      Scene registerScene = new Scene(registerView.getPane(), 400, 300);
    
      primaryStage.setScene(registerScene);
    
    });

    primaryStage.show();
  
  }

}
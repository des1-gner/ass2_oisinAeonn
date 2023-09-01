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
    
    loginGUI loginView = new loginGUI();
    Scene loginScene = new Scene(loginView.getPane(), 400, 300);
    
    // Function to set login scene
    
    Runnable setLoginScene = () -> primaryStage.setScene(loginScene);

    primaryStage.setTitle("Social Media Analyzer App");
    primaryStage.setScene(loginScene);

    // Attach login success and register event handlers
    
    loginView.setOnLoginSuccessEvent((user) -> {
    
      dashboardGUI dashboardView = new dashboardGUI(user);
      Scene dashboardScene = new Scene(dashboardView.getPane(), 600, 400);

      // Attach the logout event handler to switch back to login scene
      
      dashboardView.setOnLogoutEvent(setLoginScene);
    
      primaryStage.setScene(dashboardScene);
    
    });

    loginView.setOnRegisterEvent(() -> {
    
      registerGUI registerView = new registerGUI();
      Scene registerScene = new Scene(registerView.getPane(), 400, 300);
    
      registerView.setOnBackEvent(setLoginScene);  // Attach the back event handler
    
      primaryStage.setScene(registerScene);
    
    });

    primaryStage.show();
  }

}

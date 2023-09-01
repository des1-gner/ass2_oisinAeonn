package ass2_oisinAeonn;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// This class represents the Main Entry Point for the Social Media Analyzer Application.

public class Main extends Application {

  // The main method initializes the socialMediaAnalyzerApp object, and runs the Application.

  public static void main (String[] args) {

    launch(args);
    
    // create an instance of the object socialMediaAnalyzerApp class. 

    socialMediaAnalyzerApp socialMediaAnalyzerApp = new socialMediaAnalyzerApp();
    
    // Run Menu until Option 6 is Inputted by User.
    
    // socialMediaAnalyzerApp.run();

  }

  @Override
    public void start(Stage primaryStage) {
        try {
            // Create the login view and set it as the scene
            LoginView loginView = new LoginView();
            Scene scene = new Scene(loginView.getPane(), 400, 300);

            primaryStage.setTitle("Login App");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
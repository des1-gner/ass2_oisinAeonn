package ass2_oisinAeonn;

import javafx.application.Application;
import javafx.stage.Stage;

// Main class responsible for launching the Data Analytics Hub Application. 

public class Main extends Application {

    // Instance of the StageManager to control and manage different stages 

    private StageManager stageManager;

    // Main entry point for all JavaFX Applications. 

    public static void main(String[] args) {
    
        launch(args);
    
    }

    // Overrides the start method (main entry point for JavaFX contents)

    @Override
    
    public void start(Stage primaryStage) {
    
        stageManager = new StageManager();

        // Initialize StageManager and set up the initial stage (Login/Register view)

        stageManager.setupLoginRegisterStage();
    
    }

}
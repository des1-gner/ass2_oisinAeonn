package ass2_oisinAeonn;

import ass2_oisinAeonn.UI.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private StageManager stageManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stageManager = new StageManager();
        stageManager.setupLoginStage();
    }
}

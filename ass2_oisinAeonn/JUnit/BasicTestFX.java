package ass2_oisinAeonn.JUnit;

import ass2_oisinAeonn.StageManager;

import javafx.scene.Node;
import javafx.scene.control.TextField;

import org.junit.Test;

import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.assertions.api.Assertions.assertThat;

public class BasicTestFX extends ApplicationTest {
    
    private StageManager stageManager;

    @Override

    public void start(javafx.stage.Stage stage) {
    
        stageManager = new StageManager();
        stageManager.setupLoginRegisterStage();  
    
    }

    // Supposed to fail for now I could not get it to return properly, but the automated testing of the GUI still works as shown

    // TestFX to test transition from Login clicking the Register button and transitioning to the register scene

    @Test
    
    public void testTransitionToRegisterFromLogin() {
    
        clickOn("#registerButton");  
    
        Node pane = lookup("#registerPane").query(); 
    
        assertThat(pane).isNotNull();
    
    }

    // TestFX to test logging in using pre-created credentials

    @Test
    
    public void testLoginShowsAlertForValidUser() {
    
        TextField usernameField = lookup("#usernameField").query();  
        TextField passwordField = lookup("#passwordField").query();  

        clickOn(usernameField).write("validUsername");
    
        clickOn(passwordField).write("validPassword123$");

        clickOn("#loginButton");
        
        assertThat(lookup(".alert").tryQuery()).isPresent();
    
    }

}
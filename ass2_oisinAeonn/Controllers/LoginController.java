package ass2_oisinAeonn.Controllers;

import ass2_oisinAeonn.UI.LoginView;
import java.util.function.Consumer;

public class LoginController {

    private LoginView view;
    private Consumer<String> onLoginSuccessEvent;
    private Runnable onRegisterEvent;

    public LoginController(LoginView view) {
        this.view = view;
        initEventHandlers();
    }

    public void setOnLoginSuccessEvent(Consumer<String> onLoginSuccessEvent) {
        this.onLoginSuccessEvent = onLoginSuccessEvent;
    }

    public void setOnRegisterEvent(Runnable onRegisterEvent) {
        this.onRegisterEvent = onRegisterEvent;
    }

    private void initEventHandlers() {
        view.getLoginButton().setOnAction(e -> handleLogin());
        view.getRegisterButton().setOnAction(e -> handleRegister());
    }

    private void handleLogin() {
        // TODO: Handle actual authentication
        if (onLoginSuccessEvent != null) {
            onLoginSuccessEvent.accept(view.getUsernameField().getText());
        }
    }

    private void handleRegister() {
        if (onRegisterEvent != null) {
            onRegisterEvent.run();
        }
    }
}

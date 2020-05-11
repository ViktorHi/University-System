package universitysystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import universitysystem.animations.Shake;
import universitysystem.model.structs.User;
import universitysystem.model.db.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginController {

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private Label infoLabel;

    @FXML
    void initialize() {
        authSignInButton.setOnAction(event -> {

            String login = loginTextField.getText().trim();
            String pass = passwordTextField.getText().trim();

            if (!login.equals("") && !pass.equals("")) {
                infoLabel.setText("");
                loginUser(login, pass);
            } else {
                infoLabel.setText("Сначала введите логин и пароль");
            }
        });

        loginSignUpButton.setOnAction(event -> {
            NavigationController.openNewScene(authSignInButton, SignUpController.location);
        });
    }

    private void loginUser(String login, String pass) {
        DatabaseHandler handler = new DatabaseHandler();
        User user = new User(login, pass);
        User res = User.getUserByLoginAndPass(user);

        if(res != null){
            infoLabel.setText("Авторизация пройдена успешно");
            //todo transition to next Scene
//                NavigationController.openNewScene();
        }
        else{
            Shake shake = new Shake(passwordTextField);
            Shake shake2 = new Shake(loginTextField);
            shake.play();
            shake2.play();
            infoLabel.setText("Логин или пароль неверный");
        };

    }
}

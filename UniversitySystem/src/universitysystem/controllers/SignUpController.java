package universitysystem.controllers;

/**
 * Sample Skeleton for 'signUp.fxml' Controller Class
 */


import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import universitysystem.model.structs.User;
import universitysystem.model.structs.UserStatus;

public class SignUpController {

    // group for radio buttons
    ToggleGroup group =new ToggleGroup();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;


    public static String location = "/universitysystem/views/signUp.fxml";

    @FXML // fx:id="loginTextField"
    private TextField loginTextField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTextField"
    private PasswordField passwordTextField; // Value injected by FXMLLoader

    @FXML // fx:id="signUpButton"
    private Button signUpButton; // Value injected by FXMLLoader

    @FXML // fx:id="lastNameTextField"
    private TextField lastNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="nameTextField"
    private TextField nameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="reapetPasswordTextField"
    private PasswordField reapetPasswordTextField; // Value injected by FXMLLoader


    @FXML // fx:id="adminRadioButton"
    private RadioButton adminRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="notAdminRadioButton"
    private RadioButton notAdminRadioButton; // Value injected by FXMLLoader

    @FXML
    private Label infoLabel;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert loginTextField != null : "fx:id=\"loginTextField\" was not injected: check your FXML file 'signUp.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'signUp.fxml'.";
        assert signUpButton != null : "fx:id=\"signUpButton\" was not injected: check your FXML file 'signUp.fxml'.";
        assert lastNameTextField != null : "fx:id=\"lastNameTextField\" was not injected: check your FXML file 'signUp.fxml'.";
        assert nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'signUp.fxml'.";
        assert reapetPasswordTextField != null : "fx:id=\"reapetPasswordTextField\" was not injected: check your FXML file 'signUp.fxml'.";

        group =new ToggleGroup();
        adminRadioButton.setToggleGroup(group);
        notAdminRadioButton.setToggleGroup(group);

        signUpButton.setOnAction(event -> {

            infoLabel.setText("");

            String login = loginTextField.getText().trim();
            String pass = passwordTextField.getText().trim();
            String secondPass = reapetPasswordTextField.getText().trim();
            String lastName = lastNameTextField.getText().trim();
            String firstName = nameTextField.getText().trim();
            UserStatus status = getSelectedMode();
            User user = new User(firstName, lastName, login, pass, status);


            if(!login.equals("") && !lastName.equals("") && !firstName.equals("")&&
            !pass.equals("") && !secondPass.equals("")){
                if(pass.equals(secondPass)){
                    if(User.getUserByLogin(user.getUserLogin()) == null){
                        User.signUpUser(user);
                        infoLabel.setText("Регистрация успешна");
                        NavigationController.openNewScene(adminRadioButton, UserPollViewController.location);
                    }else{
                        infoLabel.setText("Пользователь с таким логином уже существует");
                    }
                }else{
                    infoLabel.setText("Пароли должны совпадать");
                }
            }else{
                infoLabel.setText("Поля не должны быть пустыми");
            }


        });
    }

    private UserStatus getSelectedMode(){
        RadioButton activeBt = (RadioButton) group.getSelectedToggle();
        if(activeBt.getId().equals("adminRadioButton"))
            return UserStatus.admin;
        return UserStatus.not_admin;
    }
}

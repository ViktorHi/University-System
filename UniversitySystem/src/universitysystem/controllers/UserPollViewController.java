package universitysystem.controllers;
/**
 * Sample Skeleton for 'pollView.fxml' Controller Class
 */

import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import universitysystem.model.Const;
import universitysystem.model.structs.*;

public class UserPollViewController implements Controllable {

    private enum WorkMode{
        update, signUp
    }

    //region fields

    private User currentUser;
    private Controllable parent;
    WorkMode mode = null;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    public static final String location="/universitysystem/views/pollView.fxml";

    @FXML // fx:id="userLoginLb"
    private Label userLoginLb; // Value injected by FXMLLoader

    @FXML // fx:id="nameTB"
    private TextField nameTB; // Value injected by FXMLLoader

    @FXML // fx:id="lastNameTB"
    private TextField lastNameTB; // Value injected by FXMLLoader

    @FXML // fx:id="middleNameTB"
    private TextField middleNameTB; // Value injected by FXMLLoader

    @FXML // fx:id="telephoneTB"
    private TextField telephoneTB; // Value injected by FXMLLoader

    @FXML // fx:id="addressTB"
    private TextField addressTB; // Value injected by FXMLLoader

    @FXML // fx:id="BirthdayTB"
    private DatePicker BirthdayTB; // Value injected by FXMLLoader

    @FXML
    private Button applyBt;

    @FXML
    private PasswordField passRpPF;

    @FXML
    private PasswordField passPF;

    @FXML
    private Label infoLabel;

    ToggleGroup group;

//endregion

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        applyBt.setOnAction(event -> {
            User user = prepareUserToApply();
            if(user != null){
                User.updateUserByLogin(user);
                if(mode == WorkMode.signUp){
                    if(user.getUserStatus() != UserStatus.admin){
                        NavigationController.openNewScene(
                                applyBt,
                                Const.VIEW_PROFESSOR_POLL_LOCATION,
                                user,
                                this,
                                false
                        );
                    }else{
                        NavigationController.openNewScene(
                                applyBt,
                                Const.VIEW_MAIN_LOCATION,
                                user,
                                this,
                                false
                        );
                    }

                }
            }
        });
    }

    private User prepareUserToApply(){
        infoLabel.setText("");
        String name= nameTB.getText().trim();
        String lastName= lastNameTB.getText().trim();
        String middleName= middleNameTB.getText().trim();
        String telephone= telephoneTB.getText().trim();
        String address= addressTB.getText().trim();
        String pass= passPF.getText().trim();
        String passAgain= passRpPF.getText().trim();
        LocalDate birthday = BirthdayTB.getValue();

        if(birthday == null) {
            return null;
        }

        boolean isValid=true;
        if(name.equals("")) {
            isValid = false;
        }
        if(lastName.equals("")){
            isValid = false;
        }
        if(middleName.equals("")) {
            isValid = false;
        }
        if(telephone.equals("")) {
            isValid = false;
        }
        if(address.equals("")) {
            isValid = false;
        }
        if(pass.equals("") && passAgain.equals("")){
            pass = currentUser.getUserPassword();
        }else{
            if(!pass.equals(passAgain)){
                isValid = false;
                infoLabel.setText("Пароли должны совпадать");
            }
        }

        if(isValid){
            Date date = Date.valueOf(birthday);
            return new User(name, lastName, middleName,currentUser.getUserLogin(),
                    pass,
                    telephone, address, currentUser.getUserStatus(),
                    date, currentUser.getId());
        }
        else {
            return null;
        }
    }

    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        if(currentUser!= null){

            userLoginLb.setText(currentUser.getUserLogin());
            nameTB.setText(currentUser.getUserFirstName());
            lastNameTB.setText(currentUser.getUserLastName());
            middleNameTB.setText(currentUser.getUserMiddleName());

            mode = WorkMode.signUp;
            if(currentUser.getBirthDate() != null){
                BirthdayTB.setValue(currentUser.getBirthDate().toLocalDate());
                mode = WorkMode.update;
            }

            telephoneTB.setText(currentUser.getTelephone());
            addressTB.setText(currentUser.getLocation());

            this.currentUser = User.getUserByLogin(currentUser.getUserLogin());
        }
    }

    @Override
    public void setControllable(Controllable controllable) {
        parent = controllable;
    }

    @Override
    public void updateParent() {
    }

}

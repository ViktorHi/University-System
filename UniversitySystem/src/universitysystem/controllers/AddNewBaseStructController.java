package universitysystem.controllers;

/**
 * Sample Skeleton for 'newInstitutionView.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import universitysystem.model.structs.*;

public class AddNewBaseStructController implements Controllable {
    static final public String locationAca = "/universitysystem/views/asknewforms/newAcademicDegreeView.fxml";
    static final public String locationDeg = "/universitysystem/views/asknewforms/newDegreeView.fxml";
    static final public String locationFac = "/universitysystem/views/asknewforms/newFacultyView.fxml";
    static final public String locationIns = "/universitysystem/views/asknewforms/newInstitutionView.fxml";
    static final public String locationPos = "/universitysystem/views/asknewforms/newPostView.fxml";
    static final public String locationPul = "/universitysystem/views/asknewforms/newPulpitView.fxml";

    private User currentUser;
    private Controllable parent;

    enum ViewNames {
        academicDegree,
        degree,
        faculty,
        pulpit,
        post,
        institution,
    }

    ViewNames name;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Label viewId;

    @FXML
    private Label infoLabel;

    @FXML // fx:id="userLoginLb"
    private Label userLoginLb; // Value injected by FXMLLoader

    @FXML // fx:id="fullNameTB"
    private TextField fullNameTB; // Value injected by FXMLLoader

    @FXML // fx:id="shortNameTB"
    private TextField shortNameTB; // Value injected by FXMLLoader

    @FXML // fx:id="codeTB"
    private TextField codeTB; // Value injected by FXMLLoader

    @FXML // fx:id="enterBT"
    private Button enterBT; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert userLoginLb != null : "fx:id=\"userLoginLb\" was not injected: check your FXML file 'newInstitutionView.fxml'.";
        assert fullNameTB != null : "fx:id=\"fullNameTB\" was not injected: check your FXML file 'newInstitutionView.fxml'.";
        assert shortNameTB != null : "fx:id=\"shortNameTB\" was not injected: check your FXML file 'newInstitutionView.fxml'.";
        assert codeTB != null : "fx:id=\"codeTB\" was not injected: check your FXML file 'newInstitutionView.fxml'.";
        assert enterBT != null : "fx:id=\"enterBT\" was not injected: check your FXML file 'newInstitutionView.fxml'.";

        //userLoginLb.setText(currentUser.getUserLogin());

        name = ViewNames.valueOf(viewId.getText());
        enterBT.setOnAction(event -> {

            String fullName = fullNameTB.getText().trim();
            String shortName = shortNameTB.getText().trim();
            String code = codeTB.getText().trim();
            BaseStruct struct = new BaseStruct(fullName, shortName, code);

            if(!isTextValid(fullName, shortName, code)) {
                infoLabel.setText("Строки должны быть заполнены");
                return;
            }

            boolean isLuck = false;

            switch (name){
                case institution:
                    isLuck=University.addNewUniversity(struct);
                    break;
                case faculty:
                    isLuck= Faculty.addNewFaculty(struct);
                    break;
                case post:
                    isLuck=Post.addNewPostName(struct);
                    break;
                case degree:
                    isLuck= Degree.addNewDegree(struct);
                    break;
                case academicDegree:
                    isLuck=AcademicDegree.addNewAcademicDegree(struct);
                    break;
                case pulpit:
                    isLuck=Pulpit.addNewPulpit(struct);
                    break;
            }

            if(isLuck){
                infoLabel.setText("Добавление прошло успешно");
                parent.updateParent();
                infoLabel.getScene().getWindow().hide();
            }else {
                infoLabel.setText("Данные не могут повторяться");
            }
        });
    }

    private boolean isTextValid (String fullName, String shortName, String code){
        if(fullName.equals("")) return false;
        if(shortName.equals("")) return false;
        return !code.equals("");
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
        if(user != null){
            userLoginLb.setText(currentUser.getUserLogin());
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

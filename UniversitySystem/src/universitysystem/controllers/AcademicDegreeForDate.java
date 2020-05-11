package universitysystem.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import universitysystem.model.structs.User;

public class AcademicDegreeForDate implements Controllable {

    @FXML
    private Menu aboutMeBt;

    @FXML
    private TableView<?> tableTV;

    @FXML
    private TableColumn<?, ?> firstNameTC;

    @FXML
    private TableColumn<?, ?> secondNameTC;

    @FXML
    private TableColumn<?, ?> middleNameTC;

    @FXML
    private TableColumn<?, ?> facultyTC;

    @FXML
    private TableColumn<?, ?> pulpitTC;

    @FXML
    private TableColumn<?, ?> academicDegreeTC;

    @FXML
    private TableColumn<?, ?> degreeTC;

    @FXML
    private TableColumn<?, ?> postTC;

    @FXML
    private TableColumn<?, ?> yearTC;

    @FXML
    private ComboBox<?> academicDegreeCB;

    @FXML
    private DatePicker dayDP;

    @FXML
    private Button searchBt;

    @FXML
    private Label leftInfoLabel;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private Label rightInfoLabel;

    @Override
    public void setCurrentUser(User user) {

    }

    @Override
    public void setControllable(Controllable controllable) {

    }

    @Override
    public void updateParent() {

    }
}


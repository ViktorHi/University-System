package universitysystem.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import universitysystem.model.Const;
import universitysystem.model.structs.BaseStruct;
import universitysystem.model.structs.Professor;
import universitysystem.model.structs.User;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AcademicDegreeForDate implements Controllable {

    //region fields

    private User currentUser;
    private BaseStruct currentAcaDegree;
    private Date currentDate;

    public class ForDateTableLine{
        private String firstName;
        private String lastName;
        private String middleName;
        private String faculty;
        private String pulpit;
        private String academicDegree;
        private String degree;
        private String post;


        public ForDateTableLine(String firstName, String lastName, String middleName, String faculty, String pulpit, String academicDegree, String degree, String post)
        {
            this.firstName = firstName;
            this.lastName = lastName;
            this.middleName = middleName;
            this.faculty = faculty;
            this.pulpit = pulpit;
            this.academicDegree = academicDegree;
            this.degree = degree;
            this.post = post;
        }

        //region field
        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public String getFaculty() {
            return faculty;
        }

        public String getPulpit() {
            return pulpit;
        }

        public String getAcademicDegree() {
            return academicDegree;
        }

        public String getDegree() {
            return degree;
        }

        public String getPost() {
            return post;
        }

        //endregion
    }

    @FXML
    private Menu aboutMeBt;

    @FXML
    private TableView<ForDateTableLine> tableTV;

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
    private ComboBox<BaseStruct> academicDegreeCB;

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
    //endregion

    @FXML
    void initialize() {
        init();

        //region actions
        academicDegreeCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == null) return;
            currentAcaDegree = newValue;
        });

        searchBt.setOnAction(event -> {
           if(prepareToSearch()){
               updateTable();
           }
        });
        //endregion
    }

    private boolean prepareToSearch(){
        if(currentAcaDegree == null) return false;
        if(dayDP.getValue() == null) return false;

        currentDate = Date.valueOf(dayDP.getValue());

        return true;
    }

    private void updateTable(){
        tableTV.getItems().clear();

        List<ForDateTableLine> lines = getDataForTable();
        if(lines ==null) return;

        ObservableList<ForDateTableLine> tableLines = FXCollections.observableArrayList(lines);
        tableTV.setItems(tableLines);
    }

    private List<ForDateTableLine> getDataForTable(){

        List<Professor> professors = Professor.getAllProfessorsByAcaDegreeForDate(currentAcaDegree, currentDate);
        if (professors == null) return null;

        return professors.stream().map(professor -> {
           return new ForDateTableLine(
                    professor.getUser().getUserFirstName(),
                    professor.getUser().getUserLastName(),
                    professor.getUser().getUserMiddleName(),
                    professor.getFaculty().getShortName(),
                    professor.getPulpit().getShortName(),
                    currentAcaDegree.getShortName(),
                    professor.getDegrees().get(0).getStruct().getShortName(),
                    professor.getPosts().stream().sorted().findFirst().get().getStruct().getShortName());

        }).collect(Collectors.toList());
    }

    private void init(){
        addValueFactoryToColumn();
        updateComboBox();
    }

    private void addValueFactoryToColumn(){
        firstNameTC.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        secondNameTC.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        middleNameTC.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        facultyTC.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        pulpitTC.setCellValueFactory(new PropertyValueFactory<>("pulpit"));
        academicDegreeTC.setCellValueFactory(new PropertyValueFactory<>("academicDegree"));
        degreeTC.setCellValueFactory(new PropertyValueFactory<>("degree"));
        postTC.setCellValueFactory(new PropertyValueFactory<>("post"));
    }

    private void updateComboBox(){
        List<BaseStruct> acaList = BaseStruct.getBaseStructs(Const.ACADEMIC_DEGREE_NAMES_TABLE);
        ObservableList<BaseStruct> list = BaseStruct.getForComboBox(acaList);
        academicDegreeCB.setItems(list);
    }

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


package universitysystem.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import universitysystem.model.Const;
import universitysystem.model.structs.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorPollViewController implements Controllable {
    enum WorkMode {
        add,
        update,
        admin
    }

    //region fields
    @FXML
    private Label userLoginLb;

    @FXML
    private Button newInstitutionBt;

    @FXML
    private Button newFacultyBt;

    @FXML
    private Button newPulpitBt;

    @FXML
    private Button newPostBt;

    @FXML
    private Button newAcademicDegreeBt;

    @FXML
    private Button newDegreeBt;

    @FXML
    private RadioButton staffMemberRB;

    @FXML
    private RadioButton poohBahRB;

    @FXML
    private Button applyBt;

    @FXML
    private Label infoLabel;

    @FXML
    private Spinner<PeriodStruct> postSp;

    @FXML
    private Button addPosBt;

    @FXML
    private Button delPosBt;

    @FXML
    private Spinner<PeriodStruct> academicDegreeSp;

    @FXML
    private Button addAcaBt;

    @FXML
    private Button delAcaBt;

    @FXML
    private Button delDegBt;

    @FXML
    private Button delPulBt;

    @FXML
    private Button delFucBt;

    @FXML
    private Button delUniBt;

    @FXML
    private Button addDegBt;

    @FXML
    private Button addPulBt;

    @FXML
    private Button addFucBt;

    @FXML
    private Button addUniBt;

    @FXML
    private Spinner<PeriodStruct> degreeSp;

    @FXML
    private Spinner<PeriodStruct> pulpitSp;

    @FXML
    private Spinner<PeriodStruct> facultySp;

    @FXML
    private Spinner<BaseStruct> universitySp;

    @FXML
    private ChoiceBox<?> professorChoiceB;


    private ToggleGroup group;
    private User currentUser;
    private User userToUpdate;
    private Controllable parent;
    private Professor professorToUpdate;
    private WorkMode workMode;


//endregion

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        group = new ToggleGroup();
        poohBahRB.setToggleGroup(group);
        staffMemberRB.setToggleGroup(group);
        setCurrentUser(User.getUserByLogin("vik_log"));

        //region add new object listeners to BaseStruct
        newInstitutionBt.setOnAction(event -> {
            openAddNewBaseStructView(AddNewBaseStructController.locationIns);
        });

        newFacultyBt.setOnAction(event -> {
            openAddNewBaseStructView(AddNewBaseStructController.locationFac);
        });

        newAcademicDegreeBt.setOnAction(event -> {
            openAddNewBaseStructView(AddNewBaseStructController.locationAca);
        });

        newDegreeBt.setOnAction(event -> {
            openAddNewBaseStructView(AddNewBaseStructController.locationDeg);
        });

        newPostBt.setOnAction(event -> {
            openAddNewBaseStructView(AddNewBaseStructController.locationPos);
        });

        newPulpitBt.setOnAction(event -> {
            openAddNewBaseStructView(AddNewBaseStructController.locationPul);
        });
        //endregion

        //region add  new object listeners to add Bt
        addUniBt.setOnAction(event -> {
            BaseStruct res = DateDialog.createDialogBase("Пожалйста для университета", Const.UNIVERSITY_NAMES_TABLE);
            if(res != null){
                professorToUpdate.setUniversity(new University(res));
                updateSpiners();
            }

        });

        addFucBt.setOnAction(event -> {

            BaseStruct res = DateDialog.createDialogBase("Пожалйста для факультет", Const.FACULTY_NAMES_TABLE);
            if(res != null){
                professorToUpdate.setFaculty(new Faculty(res));
                updateSpiners();
            }
        });

        addPulBt.setOnAction(event -> {
            BaseStruct res = DateDialog.createDialogBase("Пожалйста для кафедра", Const.PULPIT_NAMES_TABLE);
            if(res != null){
                professorToUpdate.setPulpit(new Pulpit(res));
                updateSpiners();
            }
        });

        addDegBt.setOnAction(event -> {

            PeriodStruct res = DateDialog.createDialogPeriod("Пожалйста для Ученой звание", Const.DEGREE_NAMES_TABLE);
            if(res != null){
                List<PeriodStruct> degrees = (List<PeriodStruct>) professorToUpdate.getDegrees();
                degrees.add(res);
                professorToUpdate.setDegrees(degrees);
                updateSpiners();
            }


        });


        addAcaBt.setOnAction(event -> {

            PeriodStruct res = DateDialog.createDialogPeriod("Пожалйста для Ученой степень", Const.ACADEMIC_DEGREE_NAMES_TABLE);
            if(res != null){
                List<PeriodStruct> degrees = (List<PeriodStruct>) professorToUpdate.getAcademicDegrees();
                degrees.add(res);
                professorToUpdate.setAcademicDegrees(degrees);
                updateSpiners();
            }
        });

        addPosBt.setOnAction(event -> {
            PostStruct res = DateDialog.createDialogForPost("Пожалйста для должности", Const.POST_NAMES_TABLE);
            if( res != null){
                List<PeriodStruct> degrees = (List<PeriodStruct>) professorToUpdate.getPosts();
                degrees.add(res);
                professorToUpdate.setPosts(degrees);
                updateSpiners();
            }

        });
        //endregion

        //region add new object listeners to del Bt

        delDegBt.setOnAction(event -> {
            PeriodStruct value = degreeSp.getValue();
            if (!professorToUpdate.getDegrees().remove(value)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Элемент не был удален");
                alert.showAndWait();
            }
            updateSpiners();
        });

        delAcaBt.setOnAction(event -> {
            PeriodStruct value = academicDegreeSp.getValue();
            if (!professorToUpdate.getAcademicDegrees().remove(value)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Элемент не был удален");
                alert.showAndWait();
            }
            updateSpiners();
        });

        delPosBt.setOnAction(event -> {
            PeriodStruct value = postSp.getValue();
            if (!professorToUpdate.getPosts().remove(value)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Элемент не был удален");
                alert.showAndWait();
            }
            updateSpiners();
        });

        //endregion

        applyBt.setOnAction(event -> {
            switch (workMode) {
                case update:
                    Professor.updateProfessorOnDb(professorToUpdate, professorToUpdate.getIds().getIdProfessor());
                    break;
                case add:
                    if (prepareToApply()) {

                        Professor.addProfessorToDb(professorToUpdate);
                    } else {
                        return;
                    }
                    break;
                case admin:
                    break;
            }
        });

        System.out.println("after init");
    }

    private void openAddNewBaseStructView(String location) {
        Stage stage = (Stage) newAcademicDegreeBt.getScene().getWindow();
        NavigationController.openNewDependScene(
                stage,
                location,
                stage.getX() + 100,
                stage.getY() + 100,
                currentUser,
                this
        );
    }

    private void updateSpiners() {
        if (userToUpdate == null)
            return;


        switch (workMode) {

            case admin:
                break;
            case add:
            case update:

                University university = professorToUpdate.getUniversity();
                List<BaseStruct> list = new ArrayList();
                list.add(university);
                SpinnerValueFactory<BaseStruct> uniSpVaF =
                        new SpinnerValueFactory.ListSpinnerValueFactory<BaseStruct>(BaseStruct.getForComboBox(list));
                universitySp.setValueFactory(uniSpVaF);

                Faculty faculty = professorToUpdate.getFaculty();
                list = new ArrayList<>();
                list.add(faculty);
                SpinnerValueFactory<PeriodStruct> facSpVaF =
                        new SpinnerValueFactory.ListSpinnerValueFactory<PeriodStruct>(BaseStruct.getForComboBox(list));
                facultySp.setValueFactory(facSpVaF);

                Pulpit palpit = professorToUpdate.getPulpit();
                list = new ArrayList<>();
                list.add(palpit);
                SpinnerValueFactory<PeriodStruct> pulSpVaF =
                        new SpinnerValueFactory.ListSpinnerValueFactory<PeriodStruct>(BaseStruct.getForComboBox(list));
                pulpitSp.setValueFactory(pulSpVaF);


                ObservableList<? extends PeriodStruct> periodStructs = FXCollections.observableArrayList(professorToUpdate.getAcademicDegrees());
                SpinnerValueFactory<PeriodStruct> acaSpVaF =
                        new SpinnerValueFactory.ListSpinnerValueFactory<PeriodStruct>((ObservableList<PeriodStruct>) periodStructs);
                academicDegreeSp.setValueFactory(acaSpVaF);

                SpinnerValueFactory<PeriodStruct> degSpVaF =
                        new SpinnerValueFactory.ListSpinnerValueFactory<PeriodStruct>(FXCollections.observableArrayList(professorToUpdate.getDegrees()));
                degreeSp.setValueFactory(degSpVaF);


                SpinnerValueFactory<PeriodStruct> posSpVaF =
                        new SpinnerValueFactory.ListSpinnerValueFactory<PeriodStruct>(FXCollections.observableArrayList(professorToUpdate.getPosts()));
                postSp.setValueFactory(posSpVaF);

                break;
        }

    }

    private boolean prepareToApply() {
        if (professorToUpdate.getUniversity() == null) {
            infoLabel.setText("Поле университета должно быть заполнено");
            return false;
        }
        if (professorToUpdate.getFaculty() == null) {
            infoLabel.setText("Поле факультета должно быть заполнено");
            return false;
        }
        if (professorToUpdate.getPosts() == null) {
            infoLabel.setText("Поле должности должно быть заполнено");
            return false;
        }
        if (professorToUpdate.getPulpit() == null) {
            infoLabel.setText("Поле кафедры должно быть заполнено");
            return false;
        }
        if (professorToUpdate.getDegrees() == null) {
            infoLabel.setText("Поле ученое звание должно быть заполнено");
            return false;
        }
        if (professorToUpdate.getAcademicDegrees() == null) {
            infoLabel.setText("Поле ученая степень должно быть заполнено");
            return false;
        }
        return true;
    }

    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        if (currentUser != null) {

            userLoginLb.setText(currentUser.getUserLogin());
            if (currentUser.getUserStatus() != UserStatus.admin) {
                //not_admin

                professorChoiceB.setVisible(false);
                Professor professor = Professor.getProfessorByLogin(currentUser.getUserLogin());
                if (professor == null) {
                    workMode = WorkMode.add;
                    professorToUpdate = new Professor();
                    professorToUpdate.setUser(currentUser);
                    professorToUpdate.setPosts(new ArrayList<>());
                    professorToUpdate.setAcademicDegrees(new ArrayList<>());
                    professorToUpdate.setDegrees(new ArrayList<>());

                } else {
                    workMode = WorkMode.update;
                    professorToUpdate = professor;
                }
                userToUpdate = currentUser;

            } else {
                //admin
                workMode = WorkMode.admin;
                userToUpdate = null;
            }

            updateSpiners();
        }
    }

    @Override
    public void setControllable(Controllable controllable) {
        parent = controllable;
    }

    @Override
    public void updateParent() {
        //updateComboBoxs();
    }

}
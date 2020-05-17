package universitysystem.controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import universitysystem.animations.Shake;
import universitysystem.model.Const;
import universitysystem.model.db.DatabaseHandler;
import universitysystem.model.structs.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainController implements Controllable {

    class Pair {
        public String english;
        public String russian;

        public Pair(String english, String russian) {
            this.english = english;
            this.russian = russian;
        }

        @Override
        public String toString() {
            return russian;
        }
    }

    //region fields
    @FXML
    private MenuItem overfulfilledMi;

    @FXML
    private MenuItem academicDegreeMi;

    @FXML
    private Menu aboutMeBt;

    @FXML
    private MenuItem aboutUserMi;

    @FXML
    private MenuItem aboutProfessorMi;

    @FXML
    private ComboBox<Integer> addYearCB;

    @FXML
    private ListView<Pair> workNameList;

    @FXML
    private ListView<Professor> professorList;

    @FXML
    private Label workTypeLb;

    @FXML
    private TextField sem1FactTF;

    @FXML
    private TextField sem1PlanTF;

    @FXML
    private TextField sem2FactTF;

    @FXML
    private TextField sem2PlanTF;

    @FXML
    private Button applyBt;

    @FXML
    private Label firstSumLb;

    @FXML
    private Label secondSumLb;

    @FXML
    private Label leftInfoLabel;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private Label rightInfoLabel;

    @FXML
    private SplitPane workAndProfSP;

    @FXML
    private Button addNewYearBt;

    private User currentUser;
    private Professor currentProfessor;
    private WorkType currentWorkType = WorkType.EDUCATIONAL_WORK;
    private Plan currentPlan;
    private Map<Integer, Plan> currentPlans = new HashMap<>();

    //endregion

    @FXML
    void initialize() {
        //setCurrentUser(User.getUserByLogin("kash"));

        //region listeners initialize
        addYearCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            currentPlan = (currentPlans.get(newValue));
            updatePlansTable();
            updateAllSums();
        });

        workNameList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            currentWorkType = WorkType.valueOf(newValue.english);
            updateAll();
        });

        applyBt.setOnAction(event -> {
            if (prepareToUpdate()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Обновление прошло успешно");
                alert.showAndWait();
            }
        });

        addNewYearBt.setOnAction(event -> {
            if (prepareToAdd()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Добовление прошло успешно");
                alert.showAndWait();
            }
        });

        professorList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            currentProfessor = newValue;
            updateAll();
        });

        overfulfilledMi.setOnAction(event -> {
            if (currentUser.getUserStatus() == UserStatus.admin) {
                Stage stage = (Stage) addNewYearBt.getScene().getWindow();
                NavigationController.openNewDependScene(
                        stage,
                        Const.VIEW_FACT_MORE_PLAN_LOCATION,
                        stage.getX() + 100,
                        stage.getY() + 100,
                        currentUser,
                        this,
                        true
                );
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Для продолжения необходимо авторизоваться в качестве администратора");
                alert.showAndWait();
            }
        });

        academicDegreeMi.setOnAction(event -> {
            if (currentUser.getUserStatus() == UserStatus.admin) {
                Stage stage = (Stage) addNewYearBt.getScene().getWindow();
                NavigationController.openNewDependScene(
                        stage,
                        Const.VIEW_ACADEMIC_FOR_DATE_LOCATION,
                        stage.getX() + 100,
                        stage.getY() + 100,
                        currentUser,
                        this,
                        true
                );
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Для продолжения необходимо авторизоваться в качестве администратора");
                alert.showAndWait();
            }
        });

        aboutUserMi.setOnAction(event -> {

            Stage stage = (Stage) addNewYearBt.getScene().getWindow();
            NavigationController.openNewDependScene(
                    stage,
                    Const.VIEW_USER_POLL_LOCATION,
                    stage.getX() + 100,
                    stage.getY() + 100,
                    currentUser,
                    this,
                    true
            );

        });

        aboutProfessorMi.setOnAction(event -> {
            if (currentUser.getUserStatus() != UserStatus.admin) {
                Stage stage = (Stage) addNewYearBt.getScene().getWindow();
                NavigationController.openNewDependScene(
                        stage,
                        Const.VIEW_PROFESSOR_POLL_LOCATION,
                        stage.getX() + 100,
                        stage.getY() + 100,
                        currentUser,
                        this,
                        true
                );
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Для продолжения необходимо авторизоваться в качестве профессора");
                alert.showAndWait();
            }
        });

        //endregion

    }

    private void updatePlansTable() {
        workTypeLb.setText("Вид работы \"" + currentWorkType.getText() + "\"");

        if (currentPlan != null) {
            sem1PlanTF.setText(currentPlan.getFirstSemPlan() + "");
            sem1FactTF.setText(currentPlan.getFirstSemFact() + "");
            sem2PlanTF.setText(currentPlan.getSecondSemPlan() + "");
            sem2FactTF.setText(currentPlan.getSecondSemFact() + "");
        } else {
            sem1PlanTF.setText("Не определено");
            sem1FactTF.setText("Не определено");
            sem2PlanTF.setText("Не определено");
            sem2FactTF.setText("Не определено");
        }

    }

    private void updateYearChoiceBox(List<Plan> plans) {
        currentPlans = new HashMap<>();
        //todo bug
        addYearCB.getItems().clear();
        if (plans.isEmpty())
            return;
        List<Integer> list = new ArrayList();

        for (Plan plan : plans) {
            list.add(plan.getYear());
            currentPlans.put(plan.getYear(), new Plan(plan));
        }
        ObservableList<Integer> obs = FXCollections.observableArrayList(list);

        //todo bug here
        addYearCB.setItems(obs);

        addYearCB.setValue(currentPlan.getYear());
    }

    private void setWorkTypesValue() {
        ObservableList<Pair> workNames;
        List<Pair> values = new ArrayList<>();
        for (WorkType value : WorkType.values()) {
            values.add(new Pair(value.getStringValue(), value.getText()));
        }
        workNames = FXCollections.observableArrayList(values);
        workNameList.setItems(workNames);
        workNameList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        workNameList.getSelectionModel().selectIndices(0);

    }

    private void setProfessorsValue() {
        ObservableList<Professor> professors;

        professors = FXCollections.observableArrayList(Professor.getAllProfessors());

        professorList.setItems(professors);
        professorList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        professorList.getSelectionModel().selectIndices(0);

        currentProfessor = professorList.getItems().get(0);
    }

    /**
     * this method update Table and Year box
     */
    private void updateAll() {
        List<Plan> plans = Plan.getPlansByProfessorIdAndWorkType(currentProfessor.getIds().getIdProfessor(), currentWorkType);

        if (!plans.isEmpty()) {
            plans.sort(Plan::compareTo);
            currentPlan = new Plan(plans.get(0));

        } else {
            currentPlan = null;
        }
        updatePlansTable();
        updateYearChoiceBox(plans);
        updateAllSums();
    }

    private boolean prepareToUpdate() {
        if (currentPlan == null) return false;
        if (validateTextFields()) return false;

        int secondSemPlan = Integer.parseInt(sem2PlanTF.getText().trim());
        int secondSemFact = Integer.parseInt(sem2FactTF.getText().trim());
        int firstSemFact = Integer.parseInt(sem1FactTF.getText().trim());
        int firstSemPlan = Integer.parseInt(sem1PlanTF.getText().trim());

        currentPlan.setFirstSemFact(firstSemFact);
        currentPlan.setFirstSemPlan(firstSemPlan);
        currentPlan.setSecondSemPlan(secondSemPlan);
        currentPlan.setSecondSemFact(secondSemFact);

        currentPlan.updatePlan();

        return true;
    }

    private boolean validateTextFields() {

        if (validateTextField(sem1PlanTF)) return true;

        if (validateTextField(sem2PlanTF)) return true;

        if (validateTextField(sem1FactTF)) return true;

        if (validateTextField(sem2FactTF)) return true;

        return false;
    }

    private boolean validateTextField(TextField sem1PlanTF) {
        if (!isInt(sem1PlanTF.getText().trim())) {
            Shake shake = new Shake(sem1PlanTF);
            shake.play();
            return true;
        }
        return false;
    }

    private boolean prepareToAdd() {
        if (validateTextFields()) return false;

        int secondSemPlan = Integer.parseInt(sem2PlanTF.getText().trim());
        int secondSemFact = Integer.parseInt(sem2FactTF.getText().trim());
        int firstSemFact = Integer.parseInt(sem1FactTF.getText().trim());
        int firstSemPlan = Integer.parseInt(sem1PlanTF.getText().trim());


        Integer year = DateDialog.createDialogGetYear("Введите год на который необходимо создать план", currentPlans.keySet());

        if (year == null) return false;

        PlanStruct planStruct = new PlanStruct(currentProfessor.getIds().getIdProfessor(),
                firstSemPlan,
                firstSemFact,
                secondSemFact,
                secondSemPlan,
                year,
                currentWorkType);

        try {
            Plan.addPlanStruct(planStruct);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Plan for that year is already exist");
            alert.showAndWait();
            return false;
        }

        updateAll();
        return true;
    }

    private boolean isInt(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void updateAllSums() {
        updateFirstSum();
        updateSecondSum();
    }

    private void updateFirstSum() {
        javafx.util.Pair<Integer, Integer> res = updateSum(Const.PLAN_FIRST_SEM_PLAN, Const.PLAN_FIRST_SEM_FACT);
        if (res != null) {
            firstSumLb.setText(res.getKey() + " из " + res.getValue());
        } else {
            firstSumLb.setText(".. из ..");
        }

    }

    private void updateSecondSum() {
        javafx.util.Pair<Integer, Integer> res = updateSum(Const.PLAN_SECOND_SEM_PLAN, Const.PLAN_SECOND_SEM_FACT);
        if (res != null) {
            secondSumLb.setText(res.getKey() + " из " + res.getValue());
        } else {
            secondSumLb.setText(".. из ..");
        }

    }

    private javafx.util.Pair<Integer, Integer> updateSum(String firstColumnPlan, String secondColumFact) {
        if (currentPlan == null)
            return null;
        DatabaseHandler handler = new DatabaseHandler();

        String query = "SELECT SUM(" + firstColumnPlan + ") AS plansum, " +
                "SUM(" + secondColumFact + ") AS factsum FROM " + Const.PLAN_TABLE +
                " WHERE " + Const.PLAN_ID_PROFESSOR + "=? AND " + Const.PLAN_BEGIN_YEAR + "=?";
        PreparedStatement prSt = null;
        try {
            prSt = handler.getDbConnection().prepareStatement(query);

            prSt.setInt(1, currentProfessor.getIds().getIdProfessor());
            prSt.setInt(2, currentPlan.getYear());
            ResultSet resultSet = prSt.executeQuery();
            if (resultSet.next()) {
                int plansum = resultSet.getInt("plansum");
                int factsum = resultSet.getInt("factsum");
                return new javafx.util.Pair<>(factsum, plansum);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
        if (currentUser == null)
            return;

        setProfessorsValue();
        setWorkTypesValue();

        if (currentUser.getUserStatus() == UserStatus.admin) {
//            setProfessorValues()
        } else {
            currentProfessor = Professor.getProfessorByLogin(user.getUserLogin());
            workAndProfSP.setDividerPositions(1.0);
            disableDividers(workAndProfSP);
            updateAll();
        }

    }

    @Override
    public void setControllable(Controllable controllable) {

    }

    @Override
    public void updateParent() {
        currentUser = User.getUserByLogin(currentUser.getUserLogin());
    }

    private void disableDividers(final SplitPane split) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Node div : split.lookupAll(".split-pane-divider")) {
                    div.setMouseTransparent(true);
                }
            }
        });
    }
}

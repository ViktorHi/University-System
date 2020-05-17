package universitysystem.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import universitysystem.model.Const;
import universitysystem.model.db.DatabaseHandler;
import universitysystem.model.structs.*;

public class FactMoreThanPlanController implements Controllable{


    //region fields

    User currentUser;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Menu aboutMeBt;

    @FXML
    private TableView<CustomTableLine> tableTV;

    @FXML
    private TableColumn<CustomTableLine, String> firstNameTC;

    @FXML
    private TableColumn<CustomTableLine, String> secondNameTC;

    @FXML
    private TableColumn<CustomTableLine, String> middleNameTC;

    @FXML
    private TableColumn<CustomTableLine, String> facultyTC;

    @FXML
    private TableColumn<CustomTableLine, String> pulpitTC;

    @FXML
    private TableColumn<CustomTableLine, String> academicDegreeTC;

    @FXML
    private TableColumn<CustomTableLine, String> degreeTC;

    @FXML
    private TableColumn<CustomTableLine, String> postTC;

    @FXML
    private TableColumn<CustomTableLine, Integer> yearTC;

    @FXML
    private TableColumn<CustomTableLine, Integer> semestrTC;

    @FXML
    private TableColumn<CustomTableLine, Integer> factTC;

    @FXML
    private TableColumn<CustomTableLine, Integer> planTC;

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
        assert aboutMeBt != null : "fx:id=\"aboutMeBt\" was not injected: check your FXML file 'factMoreThanPlanView.fxml'.";
        assert leftInfoLabel != null : "fx:id=\"leftInfoLabel\" was not injected: check your FXML file 'factMoreThanPlanView.fxml'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'factMoreThanPlanView.fxml'.";
        assert x4 != null : "fx:id=\"x4\" was not injected: check your FXML file 'factMoreThanPlanView.fxml'.";
        assert rightInfoLabel != null : "fx:id=\"rightInfoLabel\" was not injected: check your FXML file 'factMoreThanPlanView.fxml'.";
        updateTable();
    }

    private void updateTable() {

        firstNameTC.setSortType(TableColumn.SortType.DESCENDING);
        planTC.setSortable(false);
        addValueFactoryToColumn();
        List<CustomTableLine> data = getData();

        ObservableList<CustomTableLine> tableLines = FXCollections.observableArrayList(data);
        tableTV.setItems(tableLines);
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

        yearTC.setCellValueFactory(new PropertyValueFactory<>("year"));
        semestrTC.setCellValueFactory(new PropertyValueFactory<>("semestr"));
        factTC.setCellValueFactory(new PropertyValueFactory<>("fact"));
        planTC.setCellValueFactory(new PropertyValueFactory<>("plan"));

    }


    private List<CustomTableLine> getData() {
        DatabaseHandler handler = new DatabaseHandler();
        PreparedStatement prSt;
        List<CustomTableLine> ans = null;
        Map<Integer, Set<Integer>> map = new HashMap<>();

        String query1 = "SELECT * FROM " + Const.PLAN_TABLE;
        Connection dbConnection = null;
        try {
            dbConnection = handler.getDbConnection();

            prSt = dbConnection.prepareStatement(query1);
            ResultSet resultSet1 = prSt.executeQuery();
            while (resultSet1.next()) {
                if (ans == null) ans = new ArrayList<>();

                int prof_id = resultSet1.getInt(Const.PLAN_ID_PROFESSOR);
                int year = resultSet1.getInt(Const.PLAN_BEGIN_YEAR);

                if (map.containsKey(prof_id)) {
                    map.get(prof_id).add(year);
                } else {
                    map.put(prof_id, new HashSet<>(year));
                }
            }

            for (Integer prof_id : map.keySet()) {
                Set<Integer> years = map.get(prof_id);
                for (int year : years) {
                    CustomTableLine line1 = getTableLine(prof_id, year, dbConnection, 1);
                    CustomTableLine line2 = getTableLine(prof_id, year, dbConnection, 2);

                    if (line1 != null) ans.add(line1);
                    if (line2 != null) ans.add(line2);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



        return ans;
    }

    private CustomTableLine getTableLine(int idProf, int year, Connection conn, int sem) throws SQLException {
        String plan = "";
        String fact = "";
        if (sem == 1) {
            plan = Const.PLAN_FIRST_SEM_PLAN;
            fact = Const.PLAN_FIRST_SEM_FACT;
        } else {
            plan = Const.PLAN_SECOND_SEM_PLAN;
            fact = Const.PLAN_SECOND_SEM_FACT;
        }


        String query = "SELECT *, SUM(" + plan + ") AS plansum, " +
                "SUM(" + fact + ") AS factsum FROM " + Const.PLAN_TABLE +
                " WHERE " + Const.PLAN_ID_PROFESSOR + "=? AND " + Const.PLAN_BEGIN_YEAR + "=?";
        PreparedStatement prSt = conn.prepareStatement(query);

        prSt.setInt(1, idProf);
        prSt.setInt(2, year);

        ResultSet resultSet = prSt.executeQuery();
        if (resultSet.next()) {
            int plansum = resultSet.getInt("plansum");
            int factsum = resultSet.getInt("factsum");
            if (factsum > plansum) {
                Professor professor = Professor.getProfessorById(idProf);
                User user = professor.getUser();

                List<? extends PeriodStruct> academicDegrees = professor.getAcademicDegrees();
                academicDegrees.sort(PeriodStruct::compareTo);
                PeriodStruct periodStruct = academicDegrees.get(0);

                List<? extends PeriodStruct> degrees = professor.getDegrees();
                degrees.sort(PeriodStruct::compareTo);
                PeriodStruct periodStruct1 = degrees.get(0);

                List<? extends PeriodStruct> posts = professor.getPosts();
                posts.sort(PeriodStruct::compareTo);
                PeriodStruct periodStruct2 = posts.get(0);

                CustomTableLine ans = new CustomTableLine(
                        user.getUserFirstName(),
                        user.getUserLastName(),
                        user.getUserMiddleName(),
                        professor.getFaculty().getShortName(),
                        professor.getPulpit().getCodeName(),
                        periodStruct.getStruct().getFullName(),
                        periodStruct1.getStruct().getFullName(),
                        periodStruct2.getStruct().getFullName(),
                        year,
                        sem,
                        factsum,
                        plansum);
                return ans;
            }
        }

        return null;
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
        if(user == null) return;

    }

    @Override
    public void setControllable(Controllable controllable) {

    }

    @Override
    public void updateParent() {

    }
}


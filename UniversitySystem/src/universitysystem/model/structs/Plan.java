package universitysystem.model.structs;

import universitysystem.model.Const;
import universitysystem.model.db.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Plan extends PlanStruct implements  Comparable{
    int id;

    private Plan(int idProfessor, int firstSemPlan, int firstSemFact, int secondSemFact, int secondSemPlan, int year, WorkType workType, int id) {
        super(idProfessor, firstSemPlan, firstSemFact, secondSemFact, secondSemPlan, year, workType);
        this.id = id;
    }

    public Plan(Plan plan) {
        this(plan.idProfessor, plan.firstSemPlan, plan.firstSemFact, plan.secondSemFact, plan.secondSemPlan, plan.year, plan.workType, plan.id);
    }

    private static Plan getPlan(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(Const.PLAN_ID);
        int prId = resultSet.getInt(Const.PLAN_ID_PROFESSOR);
        int fsf = resultSet.getInt(Const.PLAN_FIRST_SEM_FACT);
        int fsp = resultSet.getInt(Const.PLAN_FIRST_SEM_PLAN);
        int ssf = resultSet.getInt(Const.PLAN_SECOND_SEM_FACT);
        int ssp = resultSet.getInt(Const.PLAN_SECOND_SEM_PLAN);
        String type = resultSet.getString(Const.PLAN_TYPE);
        int yea = resultSet.getInt(Const.PLAN_BEGIN_YEAR);
        return new Plan(prId, fsp, fsf, ssf, ssp, yea, WorkType.valueOf(type), id);
    }


    public static Plan getPlan(int professorId, WorkType workType, int year){
        DatabaseHandler handler = new DatabaseHandler();
        Plan ans = null;

        String query = "SELECT * FROM " + Const.PLAN_TABLE +
                " WHERE "+ Const.PLAN_ID_PROFESSOR + "=? AND "+
                Const.PLAN_TYPE + "=? AND "+
                Const.PLAN_BEGIN_YEAR + "=?";

        PreparedStatement prSt = null;
        try {
            Connection dbConnection = handler.getDbConnection();

            prSt = dbConnection.prepareStatement(query);

            prSt.setInt(1, professorId);
            prSt.setString(2,workType.getStringValue());
            prSt.setInt(3,year);

            ResultSet resultSet = prSt.executeQuery();

            if(resultSet.next()){
                ans = getPlan(resultSet);
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        return ans;

    }

    public static List<Plan> getPlansByProfessorId(int professorId){
        DatabaseHandler handler = new DatabaseHandler();
        List<Plan> ans = null;

        String query = "SELECT * FROM " + Const.PLAN_TABLE +
                " WHERE "+ Const.PLAN_ID_PROFESSOR + "=?";

        PreparedStatement prSt = null;
        try {
            Connection dbConnection = handler.getDbConnection();

            prSt = dbConnection.prepareStatement(query);

            prSt.setInt(1, professorId);

            ResultSet resultSet = prSt.executeQuery();

            ans = new ArrayList<>();
            while(resultSet.next()){
                ans.add(getPlan(resultSet));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        return ans;
    }

    public static List<Plan> getPlansByProfessorIdAndWorkType(int professorId, WorkType workType){
        DatabaseHandler handler = new DatabaseHandler();
        List<Plan> ans = null;

        String query = "SELECT * FROM " + Const.PLAN_TABLE +
                " WHERE "+ Const.PLAN_ID_PROFESSOR + "=? AND " +
                Const.PLAN_TYPE + "=? ";

        PreparedStatement prSt = null;
        try {
            Connection dbConnection = handler.getDbConnection();

            prSt = dbConnection.prepareStatement(query);

            prSt.setInt(1, professorId);
            prSt.setString(2, workType.getStringValue());

            ResultSet resultSet = prSt.executeQuery();

            ans = new ArrayList<>();
            while(resultSet.next()){
                ans.add(getPlan(resultSet));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        return ans;
    }

    public static boolean addPlanStruct(PlanStruct planStruct) throws Exception {
        return PlanStruct.addPlanStruct(planStruct);
    }

    public void updatePlan(){
        super.updatePlan(this);
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare( ((Plan)o).year, this.year);
    }
}




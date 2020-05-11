package universitysystem.model.structs;

import universitysystem.model.Const;
import universitysystem.model.db.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PlanStruct {

    protected int idProfessor;
    protected int firstSemPlan;
    protected int firstSemFact;
    protected int secondSemFact;
    protected int secondSemPlan;
    protected int year;
    protected WorkType workType;

    public PlanStruct(int idProfessor, int firstSemPlan, int firstSemFact, int secondSemFact, int secondSemPlan, int year, WorkType workType) {
        this.idProfessor = idProfessor;
        this.firstSemPlan = firstSemPlan;
        this.firstSemFact = firstSemFact;
        this.secondSemFact = secondSemFact;
        this.secondSemPlan = secondSemPlan;
        this.year = year;
        this.workType = workType;
    }

    protected static boolean addPlanStruct(PlanStruct planStruct) throws Exception {

        if(isPlanExistByTypeAndYear(planStruct.workType, planStruct.year, planStruct.idProfessor)){
            throw new Exception("you can't add new record, because only one allows for one work type and year");
        }

        DatabaseHandler handler = new DatabaseHandler();


        String query = "INSERT INTO " + Const.PLAN_TABLE + " (" + Const.PLAN_ID_PROFESSOR + ", " +
                Const.PLAN_FIRST_SEM_FACT + ", " + Const.PLAN_FIRST_SEM_PLAN + ", " +
                Const.PLAN_SECOND_SEM_FACT + ", " + Const.PLAN_SECOND_SEM_PLAN + ", " +
                Const.PLAN_BEGIN_YEAR + ", " + Const.PLAN_TYPE + ") " + "VALUES(?,?,?,?,?,?,?)";

        PreparedStatement prSt = null;
        try {
            prSt = getPreparedStatement(planStruct, handler, query);

            prSt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    private static PreparedStatement getPreparedStatement(PlanStruct planStruct, DatabaseHandler handler, String query) throws ClassNotFoundException, SQLException {
        PreparedStatement prSt;
        Connection dbConnection = handler.getDbConnection();

        prSt = dbConnection.prepareStatement(query);

        prSt.setInt(1, planStruct.idProfessor);
        prSt.setInt(2, planStruct.firstSemFact);
        prSt.setInt(3, planStruct.firstSemPlan);
        prSt.setInt(4, planStruct.secondSemFact);
        prSt.setInt(5, planStruct.secondSemPlan);
        prSt.setInt(6, planStruct.year);
        prSt.setString(7, planStruct.workType.getStringValue());
        return prSt;
    }

    protected void updatePlan(Plan plan) {
        DatabaseHandler handler = new DatabaseHandler();


        String query = "UPDATE " + Const.PLAN_TABLE + " SET " + Const.PLAN_ID_PROFESSOR + "=?, " +
                Const.PLAN_FIRST_SEM_FACT + "=?, " + Const.PLAN_FIRST_SEM_PLAN + "=?, " +
                Const.PLAN_SECOND_SEM_FACT + "=?, " + Const.PLAN_SECOND_SEM_PLAN + "=?, " +
                Const.PLAN_BEGIN_YEAR + "=?, " + Const.PLAN_TYPE + "=? " + "WHERE " + Const.PLAN_ID + "=?";


        try {
            PreparedStatement prSt = getPreparedStatement(plan, handler, query);
            prSt.setInt(8, plan.id);

            prSt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static boolean isPlanExistByTypeAndYear(WorkType workType, int year, int idProfessor) {
        return Plan.getPlan(idProfessor, workType, year) != null;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public int getFirstSemPlan() {
        return firstSemPlan;
    }

    public int getFirstSemFact() {
        return firstSemFact;
    }

    public int getSecondSemFact() {
        return secondSemFact;
    }

    public int getSecondSemPlan() {
        return secondSemPlan;
    }

    public int getYear() {
        return year;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setFirstSemPlan(int firstSemPlan) {
        this.firstSemPlan = firstSemPlan;
    }

    public void setFirstSemFact(int firstSemFact) {
        this.firstSemFact = firstSemFact;
    }

    public void setSecondSemFact(int secondSemFact) {
        this.secondSemFact = secondSemFact;
    }

    public void setSecondSemPlan(int secondSemPlan) {
        this.secondSemPlan = secondSemPlan;
    }
}

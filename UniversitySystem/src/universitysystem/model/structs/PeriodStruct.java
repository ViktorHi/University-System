package universitysystem.model.structs;

import universitysystem.model.Const;
import universitysystem.model.db.DatabaseHandler;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeriodStruct implements Comparable{
    private BaseStruct struct;
    private Date beginDate;
    private Date endDate;
    private int id;
    private int idProfessor;


    public PeriodStruct(BaseStruct struct, Date beginDate, Date endDate, int id, int idProfessor) {
        this.struct = struct;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.id = id;
        this.idProfessor = idProfessor;
    }

    public PeriodStruct(BaseStruct struct, LocalDate begin, LocalDate end) {
        this.struct = struct;
        this.beginDate = Date.valueOf(begin);
        if(end != null){
            this.endDate = Date.valueOf(end);
        }
        else{
            this.endDate = null;
        }

    }

    static public PeriodStruct getPeriodStructFromDbById(int id, String table) {
        DatabaseHandler handler = new DatabaseHandler();
        PeriodStruct ans = null;

        try {
            Connection dbConnection = handler.getDbConnection();

            String query = " SELECT * FROM " + table + " WHERE " + Const.PERIOD_STRUCT_ID_1 + table + " =? ";

            PreparedStatement prSt = dbConnection.prepareStatement(query);
            prSt.setInt(1, id);

            ResultSet resultSet = prSt.executeQuery();
            if (resultSet.next()) {
                int baseId = resultSet.getInt(
                        Const.PERIOD_STRUCT_ID_1 + table + Const.PERIOD_STRUCT_BASE_ID_3);
                int idProf = resultSet.getInt(Const.PERIOD_ID_PROFESSOR);
                Date begin = resultSet.getDate(Const.PERIOD_STRUCT_START_DATE);
                Date end = resultSet.getDate(Const.PERIOD_STRUCT_END_DATE);
                BaseStruct baseStruct = BaseStruct.getBaseStructById(table + "name", baseId);

                ans = new PeriodStruct(baseStruct, begin, end, id, idProf);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ans;
    }

    static public List<PeriodStruct> getPeriodStructs(String table) {
        DatabaseHandler handler = new DatabaseHandler();
        List<PeriodStruct> ans = null;

        try {
            Connection dbConnection = handler.getDbConnection();
            String query = " SELECT * FROM " + table;

            PreparedStatement prSt = dbConnection.prepareStatement(query);

            ResultSet resultSet = prSt.executeQuery();

            ans = getPeriodStructList(resultSet, table);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ans;
    }

    static public List<PeriodStruct> getStructsByBaseStructLater(BaseStruct baseStruct, String table, Date beginDate){
        List<PeriodStruct> ans = null;
        DatabaseHandler handler = new DatabaseHandler();
        try {
            Connection dbConnection = handler.getDbConnection();

            String query = "SELECT * FROM " + table + " WHERE " + Const.PERIOD_STRUCT_ID_1 + table + 
                    Const.PERIOD_STRUCT_BASE_ID_3 + " = " + baseStruct.id + " AND " + 
                    Const.PERIOD_STRUCT_START_DATE + " > " + "?";

            PreparedStatement prSt = dbConnection.prepareStatement(query);
            prSt.setDate(1 , beginDate);

            ResultSet resultSet = prSt.executeQuery();

            ans = getPeriodStructList(resultSet, table);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ans;
    }
    
    static public List<PeriodStruct> getStructsByProfessorId(int idProfessor, String table) {
        DatabaseHandler handler = new DatabaseHandler();
        List<PeriodStruct> ans = null;

        try {
            Connection dbConnection = handler.getDbConnection();
            String query = " SELECT * FROM " + table + " WHERE " + Const.PERIOD_ID_PROFESSOR + "=?";

            PreparedStatement prSt = dbConnection.prepareStatement(query);
            prSt.setInt(1, idProfessor);

            ResultSet resultSet = prSt.executeQuery();

            ans = getPeriodStructList(resultSet, table);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ans;
    }

    private static List<PeriodStruct> getPeriodStructList(ResultSet resultSet, String table) throws SQLException {
        List<PeriodStruct> ans = null;
        while (resultSet.next()) {
            if (ans == null) ans = new ArrayList<PeriodStruct>();
            int baseId = resultSet.getInt(
                    Const.PERIOD_STRUCT_ID_1 + table + Const.PERIOD_STRUCT_BASE_ID_3);
            Date begin = resultSet.getDate(Const.PERIOD_STRUCT_START_DATE);
            Date end = resultSet.getDate(Const.PERIOD_STRUCT_END_DATE);
            BaseStruct baseStruct = BaseStruct.getBaseStructById(table + "name", baseId);
            int idProf = resultSet.getInt(Const.PERIOD_ID_PROFESSOR);
            int idPer = resultSet.getInt(Const.PERIOD_STRUCT_ID_1 + table);
            ans.add(new PeriodStruct(baseStruct, begin, end, idPer, idProf));
        }
        return ans;
    }

    public static boolean addToDbWithProfId(PeriodStruct periodStruct, String table, int profId){
        DatabaseHandler handler = new DatabaseHandler();

        String query = " INSERT INTO " + table + " (" +Const.PERIOD_STRUCT_ID_1 + table + Const.PERIOD_STRUCT_BASE_ID_3+ " , " +
                Const.PERIOD_STRUCT_END_DATE + ", " + Const.PERIOD_STRUCT_START_DATE+ "," + Const.PERIOD_ID_PROFESSOR + ")" +
                " VALUES(?,?,?,?)";

        PreparedStatement prSt = null;
        try {
            prSt = handler.getDbConnection().prepareStatement(query);

            prSt.setInt(1, periodStruct.struct.id);
            prSt.setDate(2, periodStruct.endDate);
            prSt.setDate(3, periodStruct.beginDate);
            prSt.setInt(4, profId);

            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean delFromDbByStructId(String table, int id){
        DatabaseHandler handler = new DatabaseHandler();

        String query = " DELETE FROM " + table + " WHERE " + Const.PERIOD_STRUCT_ID_1+ table+  "=?";

        return delById(id, handler, query);
    }

    public static boolean delFromDbByProfId(String table, int id){
        DatabaseHandler handler = new DatabaseHandler();

        String query = " DELETE FROM " + table + " WHERE " + Const.PERIOD_ID_PROFESSOR +  "=?";

        return delById(id, handler, query);
    }

    private static boolean delById(int id, DatabaseHandler handler, String query) {
        PreparedStatement prSt = null;
        try {
            prSt = handler.getDbConnection().prepareStatement(query);
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //region get set
    public BaseStruct getStruct() {
        return struct;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getId() {
        return id;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    //endregion 
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);

    }

    @Override
    public String toString() {
        return  struct.shortName +
                " [" + beginDate +
                " -> " + endDate +
                ']';
    }


    @Override
    public int compareTo(Object o) {
        return this.beginDate.toLocalDate().compareTo(((PeriodStruct)o).beginDate.toLocalDate());
    }
}

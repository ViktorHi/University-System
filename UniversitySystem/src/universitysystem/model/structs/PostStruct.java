package universitysystem.model.structs;

import universitysystem.model.Const;
import universitysystem.model.db.DatabaseHandler;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PostStruct extends PeriodStruct{

    double salary;

    public PostStruct(BaseStruct struct, LocalDate begin, LocalDate end, double salary) {
        super(struct, begin, end);
        this.salary = salary;
    }

    private PostStruct(BaseStruct struct, Date beginDate, Date endDate, int id, int idProfessor, double salary) {
        super(struct, beginDate, endDate, id, idProfessor);
        this.salary = salary;
    }

    static List<PostStruct> getPostStructsByProfessorId(int idProfessor, String table) {
        DatabaseHandler handler = new DatabaseHandler();
        List<PostStruct> ans = null;

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

    private static List<PostStruct> getPeriodStructList(ResultSet resultSet, String table) throws SQLException {
        List<PostStruct> ans = null;
        while (resultSet.next()) {
            if (ans == null) ans = new ArrayList<PostStruct>();
            int baseId = resultSet.getInt(
                    Const.PERIOD_STRUCT_ID_1 + table + Const.PERIOD_STRUCT_BASE_ID_3);
            double salary = resultSet.getDouble(Const.POST_STRUCT_SALARY);
            Date begin = resultSet.getDate(Const.PERIOD_STRUCT_START_DATE);
            Date end = resultSet.getDate(Const.PERIOD_STRUCT_END_DATE);
            BaseStruct baseStruct = BaseStruct.getBaseStructById(table + "name", baseId);
            int idProf = resultSet.getInt(Const.PERIOD_ID_PROFESSOR);
            int idPer = resultSet.getInt(Const.PERIOD_STRUCT_ID_1 + table);
            ans.add(new PostStruct(baseStruct, begin, end, idPer, idProf, salary));
        }
        return ans;
    }

    public static boolean addToDbWithProfId(PostStruct periodStruct, String table, int profId){
        DatabaseHandler handler = new DatabaseHandler();

        String query = " INSERT INTO " + table + " (" +Const.PERIOD_STRUCT_ID_1 + table + Const.PERIOD_STRUCT_BASE_ID_3+ " , " +
                Const.PERIOD_STRUCT_END_DATE + ", " + Const.PERIOD_STRUCT_START_DATE+ "," + Const.PERIOD_ID_PROFESSOR + ", " +
                Const.POST_STRUCT_SALARY + ")" + " VALUES(?,?,?,?,?)";

        PreparedStatement prSt = null;
        try {
            prSt = handler.getDbConnection().prepareStatement(query);

            prSt.setInt(1, periodStruct.getStruct().id);
            prSt.setDate(2, periodStruct.getEndDate());
            prSt.setDate(3, periodStruct.getBeginDate());
            prSt.setInt(4, profId);
            prSt.setDouble(5,periodStruct.salary);

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

    @Override
    public String toString() {
        return super.toString()+" "+salary +"$";
    }
}

package universitysystem.model.structs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universitysystem.model.Const;
import universitysystem.model.db.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseStruct {
    protected int id;
    protected String fullName;
    protected String shortName;
    protected String codeName;

    public BaseStruct(int id, String fullName, String shortName, String codeName) {
        this.id = id;
        this.fullName = fullName;
        this.shortName = shortName;
        this.codeName = codeName;
    }

    public BaseStruct(String fullName, String shortName, String codeName) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.codeName = codeName;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getCodeName() {
        return codeName;
    }

    @Override
    public String toString() {
        return shortName;
    }

    /**
     *
     * @param baseStruct
     * @return true if insertion was successfully,
     * false if insertion failed
     */
    static boolean addNewStruct(BaseStruct baseStruct, String tableName){
        DatabaseHandler handler = new DatabaseHandler();

        String query = "INSERT INTO " + tableName + "(" +
                Const.BASE_STRUCT_FULL_NAME + "," + Const.BASE_STRUCT_SHORT_NAME + "," +
                Const.BASE_STRUCT_CODE + ")" + "VALUES (?,?,?)";

        try {
            PreparedStatement prSt = handler.getDbConnection().prepareStatement(query);

            prSt.setString(1, baseStruct.fullName);
            prSt.setString(2, baseStruct.shortName);
            prSt.setString(3, baseStruct.codeName);

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

    public static ObservableList getForComboBox(List<? extends BaseStruct> list){
        ObservableList<BaseStruct> ans
                = FXCollections.observableArrayList(list);
        return ans;
    }


    public static List<BaseStruct> getBaseStructs(String tableName){
        DatabaseHandler handler = new DatabaseHandler();
        List<BaseStruct> ans = new ArrayList<BaseStruct>();
        try {
            Connection conn = handler.getDbConnection();

            String query = "SELECT * FROM " + tableName;

            PreparedStatement prSt = conn.prepareStatement(query);
            ResultSet resultSet = prSt.executeQuery();

            while(resultSet.next()){
                String fullName = resultSet.getString(Const.BASE_STRUCT_FULL_NAME);
                String shortName = resultSet.getString(Const.BASE_STRUCT_SHORT_NAME);
                String code = resultSet.getString(Const.BASE_STRUCT_CODE);
                int id = resultSet.getInt(Const.BASE_STRUCT_ID);

                BaseStruct uni = new BaseStruct(id, fullName, shortName, code);

                ans.add(uni);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ans;
    }

    static BaseStruct getBaseStructById(String table, int id){
        DatabaseHandler handler = new DatabaseHandler();
        BaseStruct ans =null;

        try {
            Connection dbConnection = handler.getDbConnection();

            String query = "SELECT * FROM " + table + " WHERE " + Const.BASE_STRUCT_ID + "=?";

            PreparedStatement prSt = dbConnection.prepareStatement(query);
            prSt.setInt(1, id);

            ResultSet resultSet = prSt.executeQuery();

            if(resultSet.next()){
                String fullName = resultSet.getString(Const.BASE_STRUCT_FULL_NAME);
                String shortName = resultSet.getString(Const.BASE_STRUCT_SHORT_NAME);
                String codeName = resultSet.getString(Const.BASE_STRUCT_CODE);
                ans = new BaseStruct(id, fullName, shortName, codeName);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ans;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
//        return ((BaseStruct) obj).codeName.equals(this.codeName);
    }
}

package universitysystem.model.db;

import universitysystem.model.Const;
import universitysystem.model.structs.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler {
    private static Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        MyConnection myConnection = MyConnection.getMyConnection();

        return myConnection.getConnection();
    }



}


class MyConnection extends Configs{
    private static MyConnection myConnection;
    private Connection conn;

    public static MyConnection getMyConnection() throws SQLException, ClassNotFoundException {
        if(myConnection == null){
            myConnection = new MyConnection();
        }
        return myConnection;
    }

    private MyConnection() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName+dbMeta;

        //throw exception if class not found
        Class.forName("com.mysql.jdbc.Driver");

        conn = DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    public Connection getConnection(){
        return conn;
    }
}

package universitysystem.model.structs;

import universitysystem.model.Const;
import universitysystem.model.db.DatabaseHandler;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class User {
    private String userFirstName;
    private String userLastName;
    private String userMiddleName;
    private String userLogin;
    private String userPassword;
    private String telephone;
    private String location;
    private UserStatus userStatus;
    private Date birthDate;
    private int id;

    public User(String login, String pass) {
        userLogin = login;
        userPassword = pass;
    }

    public User(String userFirstName, String userLastName, String userMiddleName, String userLogin, String userPassword, String telephone, String location, UserStatus userStatus, Date birthDate) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userMiddleName = userMiddleName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.telephone = telephone;
        this.location = location;
        this.userStatus = userStatus;
        this.birthDate = birthDate;
    }

    public User(String userFirstName, String userLastName, String userLogin, String userPassword, UserStatus userStatus) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
    }

    public User(String userFirstName, String userLastName, String userMiddleName, String userLogin, String userPassword, String telephone, String location, UserStatus userStatus, Date birthDate, int id) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userMiddleName = userMiddleName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.telephone = telephone;
        this.location = location;
        this.userStatus = userStatus;
        this.birthDate = birthDate;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public String getUserMiddleName() {
        return userMiddleName;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getLocation() {
        return location;
    }

    public Date getBirthDate() {
        return birthDate;
    }


    /**
     *
     * @param userLogin
     * @return null if user not found
     */
    public static User getUserByLogin(String userLogin) {
        User ans = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_USERNAME + " =?";

        try {
            DatabaseHandler handler = new DatabaseHandler();
            PreparedStatement prSt = handler.getDbConnection().prepareStatement(select);

            prSt.setString(1, userLogin);

            ResultSet resultSet = prSt.executeQuery();

            if (resultSet.next()) {
                return getUser(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static User getUserById(int id){
        User ans = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_ID + " =?";

        try {
            DatabaseHandler handler = new DatabaseHandler();
            PreparedStatement prSt = handler.getDbConnection().prepareStatement(select);

            prSt.setInt(1, id);

            ResultSet resultSet = prSt.executeQuery();

            if (resultSet.next()) {
                return getUser(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USER_FIRSTNAME + "," + Const.USER_LASTNAME + "," +
                Const.USER_USERNAME + "," + Const.USER_PASSWORD +
                "," + Const.USER_STATUS + ")" +
                "VALUES(?,?,?,?,?)";

        try {
            DatabaseHandler handler = new DatabaseHandler();
            PreparedStatement prSt = handler.getDbConnection().prepareStatement(insert);

            prSt.setString(1, user.getUserFirstName());
            prSt.setString(2, user.getUserLastName());
            prSt.setString(3, user.getUserLogin());
            prSt.setString(4, user.getUserPassword());
            prSt.setString(5, user.getUserStatus().name());

            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    /**
     *
     * @param user
     * @return null if user not found
     */
    static public User getUserByLoginAndPass(User user) {
        User ans = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_USERNAME + " =? AND " +
                Const.USER_PASSWORD + "=?";

        ;
        try {
            DatabaseHandler handler = new DatabaseHandler();
            PreparedStatement prSt = handler.getDbConnection().prepareStatement(select);

            prSt.setString(1, user.getUserLogin());
            prSt.setString(2, user.getUserPassword());

            ResultSet resultSet = prSt.executeQuery();

            if(resultSet.next()){
                return getUser(resultSet);
            }
            prSt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static private User getUser(ResultSet resultSet) throws SQLException {

        String firstName = resultSet.getString(Const.USER_FIRSTNAME);
        String lastName = resultSet.getString(Const.USER_LASTNAME);
        String middleName = resultSet.getString(Const.USER_MIDDLENAME);
        String login = resultSet.getString(Const.USER_USERNAME);
        String pass = resultSet.getString(Const.USER_PASSWORD);
        String status = resultSet.getString(Const.USER_STATUS);
        String tel = resultSet.getString(Const.USER_TELEPHONE);
        String loc = resultSet.getString(Const.USER_LOCATION);
        Date date = resultSet.getDate(Const.USER_BIRTH_DATE);
        int id = resultSet.getInt(Const.USER_ID);

        return new User(firstName, lastName, middleName, login, pass,
                tel, loc, UserStatus.valueOf(status), date, id);
    }

    static public boolean updateUserByLogin(User user){
        String update = "UPDATE " + Const.USER_TABLE + " SET " + Const.USER_FIRSTNAME + " =?, " +
                Const.USER_LASTNAME + " =?, " +Const.USER_MIDDLENAME + " =?, " +
                Const.USER_USERNAME + " =?, " +Const.USER_PASSWORD + " =?, " +
                Const.USER_STATUS + " =?, " +Const.USER_TELEPHONE + " =?, " +
                Const.USER_LOCATION + " =?, " +Const.USER_BIRTH_DATE + " =? " +
                " WHERE " + Const.USER_USERNAME + " =? ";

        try {
            DatabaseHandler handler = new DatabaseHandler();
            PreparedStatement prSt = handler.getDbConnection().prepareStatement(update);

            prSt.setString(1, user.getUserFirstName());
            prSt.setString(2, user.getUserLastName());
            prSt.setString(3, user.getUserMiddleName());
            prSt.setString(4, user.getUserLogin());
            prSt.setString(5, user.getUserPassword());
            prSt.setString(6, user.getUserStatus().getValue());
            prSt.setString(7, user.getTelephone());
            prSt.setString(8, user.getLocation());
            prSt.setDate(9, user.getBirthDate());
            prSt.setString(10, user.getUserLogin());

            prSt.executeUpdate();

            prSt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

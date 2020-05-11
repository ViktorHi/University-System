package universitysystem.model.structs;

import universitysystem.model.Const;
import universitysystem.model.db.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static universitysystem.model.Const.*;

public class Professor {

    public static class ProfessorsId {
        private int idProfessor;
        private int idUser;
        private int idUniversity;
        private int idFaculty;
        private int idPulpit;


        public ProfessorsId(int idUser, int idProfessor, int idUniversity, int idFaculty, int idPulpit) {
            this.idUser = idUser;
            this.idUniversity = idUniversity;
            this.idFaculty = idFaculty;
            this.idPulpit = idPulpit;
            this.idProfessor = idProfessor;
        }

        public int getIdProfessor() {
            return idProfessor;
        }
    }

    private University university;
    private Pulpit pulpit;
    private Faculty faculty;
    private List<? extends PeriodStruct> posts;
    private List<? extends PeriodStruct> academicDegrees;
    private List<? extends PeriodStruct> degrees;
    private User user;
    private ProfessorsId ids;

    public Professor(University university,
                     Pulpit pulpit,
                     Faculty faculty,
                     List<? extends PeriodStruct> posts,
                     List<? extends PeriodStruct> academicDegrees,
                     List<? extends PeriodStruct> degrees,
                     User user,
                     ProfessorsId ids) {
        this.university = university;
        this.pulpit = pulpit;
        this.faculty = faculty;
        this.posts = posts;
        this.academicDegrees = academicDegrees;
        this.degrees = degrees;
        this.ids = ids;
        this.user = user;
    }

    public Professor() {

    }

    public Professor(University university,
                     Pulpit pulpit, Faculty faculty, User user,
                     List<? extends PeriodStruct> posts,
                     List<? extends PeriodStruct> academicDegrees,
                     List<? extends PeriodStruct> degrees) {
        this.university = university;
        this.pulpit = pulpit;
        this.faculty = faculty;
        this.posts = posts;
        this.academicDegrees = academicDegrees;
        this.degrees = degrees;
        this.user = user;
    }

    public static Professor getProfessorById(int idProf) {
        Professor ans = null;
        DatabaseHandler handler = new DatabaseHandler();
        Connection dbConnection = null;
        try {
            dbConnection = handler.getDbConnection();

            String query = "SELECT * FROM " + PROFESSOR_TABLE + " WHERE " + PROFESSOR_ID + "=?";
            PreparedStatement prSt = dbConnection.prepareStatement(query);

            prSt.setInt(1, idProf);
            ResultSet resultSet = prSt.executeQuery();

            ProfessorsId ids = null;
            ids = getProfessorsIds(resultSet);

            ans = getProfessorByIds(ids);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ans;
    }

    private static ProfessorsId getProfessorsIds(ResultSet resultSet) throws SQLException {
        ProfessorsId ids = null;
        if (resultSet.next()) {
            int idPr = resultSet.getInt(Const.PROFESSOR_ID);
            int idFuc = resultSet.getInt(Const.PROFESSOR_ID_FACULTY);
            int idPul = resultSet.getInt(Const.PROFESSOR_ID_PULPIT);
            int idUni = resultSet.getInt(Const.PROFESSOR_ID_UNIVERSITY);
            int idUse = resultSet.getInt(Const.PROFESSOR_ID_USER);
            ids = new ProfessorsId(idUse, idPr, idUni, idFuc, idPul);
        }
        return ids;
    }

    static public Professor getProfessorByLogin(String login) {
        ProfessorsId idsByLogin = getProfessorsIdsByLogin(login);
        return getProfessorByIds(idsByLogin);
    }

    private static Professor getProfessorByIds(ProfessorsId idsByLogin) {
        if (idsByLogin == null) {
            return null;
        }

        Faculty faculty = new Faculty(Faculty.getBaseStructById(Const.FACULTY_NAMES_TABLE, idsByLogin.idFaculty));
        Pulpit pulpit = new Pulpit(Faculty.getBaseStructById(PULPIT_NAMES_TABLE, idsByLogin.idPulpit));
        University university = new University(Faculty.getBaseStructById(UNIVERSITY_NAMES_TABLE, idsByLogin.idUniversity));

        List<PostStruct> posts =
                PostStruct.getPostStructsByProfessorId(idsByLogin.idProfessor, Const.POST_TABLE);
        List<PeriodStruct> academicDegrees =
                PeriodStruct.getStructsByProfessorId(idsByLogin.idProfessor, Const.ACADEMIC_DEGREE_TABLE);
        List<PeriodStruct> degree =
                PeriodStruct.getStructsByProfessorId(idsByLogin.idProfessor, Const.DEGREE_TABLE);

        User user = User.getUserById(idsByLogin.idUser);

        return new Professor(university, pulpit, faculty, posts, academicDegrees, degree, user, idsByLogin);
    }

    static public List<Professor> getAllProfessors() {
        DatabaseHandler handler = new DatabaseHandler();
        List<Professor> professors = null;
        String query = "SELECT * FROM " + PROFESSOR_TABLE + " INNER JOIN " + USER_TABLE +
                " ON " + PROFESSOR_TABLE + "." + PROFESSOR_ID_USER + " = " +
                USER_TABLE + "." + USER_ID;

        PreparedStatement prSt = null;
        try {
            prSt = handler.getDbConnection().prepareStatement(query);

            ResultSet resultSet = prSt.executeQuery();

            professors = new ArrayList<>();
            while (resultSet.next()) {
                professors.add(getProfessorByLogin(resultSet.getString(USER_USERNAME)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return professors;
    }

    static private ProfessorsId getProfessorsIdsByLogin(String login) {
        DatabaseHandler handler = new DatabaseHandler();
        ProfessorsId ans = null;
        try {
            Connection dbConnection = handler.getDbConnection();


            String query = "SELECT * FROM " + PROFESSOR_TABLE + " JOIN " +
                    Const.USER_TABLE + " ON " + PROFESSOR_TABLE + "." + PROFESSOR_ID_USER + "=" +
                    USER_TABLE + "." + Const.USER_ID + " WHERE " +
                    USER_TABLE + "." + Const.USER_USERNAME + "=?";
            PreparedStatement prSt = dbConnection.prepareStatement(query);

            prSt.setString(1, login);
            ResultSet resultSet = prSt.executeQuery();
            ans = getProfessorsIds(resultSet);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ans;
    }

    static public boolean updateProfessorOnDb(Professor professor, int idProf) {
        DatabaseHandler handler = new DatabaseHandler();
        String query = "UPDATE " + PROFESSOR_TABLE + " SET " +
                PROFESSOR_ID_FACULTY + " =?, " + PROFESSOR_ID_UNIVERSITY + " =?, " +
                PROFESSOR_ID_PULPIT + " =?, " + PROFESSOR_ID_USER + " =? " +
                "WHERE " + PROFESSOR_ID + "=?";
        PreparedStatement prSt = null;
        try {
            prSt = handler.getDbConnection().prepareStatement(query);
            prSt.setInt(1, professor.faculty.id);
            prSt.setInt(2, professor.university.id);
            prSt.setInt(3, professor.pulpit.id);
            prSt.setInt(4, professor.user.getId());
            prSt.setInt(5, idProf);

            prSt.executeUpdate();

            deleteAllProfessorDepends(idProf);
            addForeignConstrains(professor);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    static public boolean addProfessorToDb(Professor professor) {

        DatabaseHandler handler = new DatabaseHandler();
        String query = "INSERT INTO " + PROFESSOR_TABLE + " (" +
                PROFESSOR_ID_FACULTY + ", " + PROFESSOR_ID_UNIVERSITY + ", " +
                PROFESSOR_ID_PULPIT + ", " + PROFESSOR_ID_USER + ") " +
                "VALUES (?,?,?,?)";
        PreparedStatement prSt = null;
        try {
            prSt = handler.getDbConnection().prepareStatement(query);
            prSt.setInt(1, professor.faculty.id);
            prSt.setInt(2, professor.university.id);
            prSt.setInt(3, professor.pulpit.id);
            prSt.setInt(4, professor.user.getId());


            prSt.executeUpdate();
            addForeignConstrains(professor);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static void addForeignConstrains(Professor professor) throws SQLException, ClassNotFoundException {

        DatabaseHandler handler = new DatabaseHandler();
        PreparedStatement prSt = handler.getDbConnection().prepareStatement(
                "SELECT * FROM " + PROFESSOR_TABLE + " WHERE " + PROFESSOR_ID_USER + " = " + professor.user.getId());
        ResultSet resultSet = prSt.executeQuery();
        int idProf = 0;
        if (resultSet.next()) {
            idProf = resultSet.getInt(PROFESSOR_ID);
        }

        int finalIdProf = idProf;
        professor.getAcademicDegrees().forEach(elem -> {
            // if(elem !=null)
            PeriodStruct.addToDbWithProfId(elem, ACADEMIC_DEGREE_TABLE, finalIdProf);
        });

        professor.getDegrees().forEach(elem -> {
            //if(elem !=null)
            PeriodStruct.addToDbWithProfId(elem, DEGREE_TABLE, finalIdProf);
        });

        professor.getPosts().forEach(elem -> {
            // if(elem !=null)
            PostStruct.addToDbWithProfId((PostStruct) elem, POST_TABLE, finalIdProf);
        });

    }

    private static void deleteAllProfessorDepends(int idProfessor) {
        PeriodStruct.delFromDbByProfId(Const.ACADEMIC_DEGREE_TABLE, idProfessor);
        PeriodStruct.delFromDbByProfId(DEGREE_TABLE, idProfessor);
        PeriodStruct.delFromDbByProfId(POST_TABLE, idProfessor);
    }

    //region get set

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public University getUniversity() {
        return university;
    }

    public Pulpit getPulpit() {
        return pulpit;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public List<? extends PeriodStruct> getPosts() {
        return posts;
    }

    public List<? extends PeriodStruct> getAcademicDegrees() {
        return academicDegrees;
    }

    public List<? extends PeriodStruct> getDegrees() {
        return degrees;
    }

    public ProfessorsId getIds() {
        return ids;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public void setPulpit(Pulpit pulpit) {
        this.pulpit = pulpit;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setPosts(List<? extends PeriodStruct> posts) {
        this.posts = posts;
    }

    public void setAcademicDegrees(List<? extends PeriodStruct> academicDegrees) {
        this.academicDegrees = academicDegrees;
    }

    public void setDegrees(List<? extends PeriodStruct> degrees) {
        this.degrees = degrees;
    }
    //endregion


    @Override
    public String toString() {
        return user.getUserLastName() + " " + user.getUserFirstName() + " " + user.getUserMiddleName();
    }
}

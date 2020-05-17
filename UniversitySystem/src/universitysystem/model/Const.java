package universitysystem.model;

import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;

public class Const {
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "idusers";
    public static final String USER_FIRSTNAME = "firstname";
    public static final String USER_LASTNAME = "lastname";
    public static final String USER_MIDDLENAME = "middlename";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_STATUS = "userstatus";
    public static final String USER_BIRTH_DATE = "birthdate";
    public static final String USER_TELEPHONE = "telephone";
    public static final String USER_LOCATION = "location";

    public static final String UNIVERSITY_NAMES_TABLE = "university";
    public static final String FACULTY_NAMES_TABLE = "faculty";
    public static final String ACADEMIC_DEGREE_NAMES_TABLE = "academicdegreename";
    public static final String DEGREE_NAMES_TABLE = "degreename";
    public static final String POST_NAMES_TABLE = "postname";
    public static final String PULPIT_NAMES_TABLE = "pulpit";

    public static final String ACADEMIC_DEGREE_TABLE = "academicdegree";
    public static final String DEGREE_TABLE = "degree";
    public static final String POST_TABLE = "post";

    public static final String BASE_STRUCT_ID = "id";
    public static final String BASE_STRUCT_SHORT_NAME = "shortname";
    public static final String BASE_STRUCT_FULL_NAME = "fullname";
    public static final String BASE_STRUCT_CODE = "basestructcode";

    public static final String PERIOD_STRUCT_ID_1 = "id";
    public static final String PERIOD_STRUCT_BASE_ID_3 = "name";
    public static final String PERIOD_STRUCT_START_DATE = "begindate";
    public static final String PERIOD_STRUCT_END_DATE = "enddate";
    public static final String PERIOD_ID_PROFESSOR = "idprofessor";
    public static final String POST_STRUCT_SALARY = "salary";

    public static final String PROFESSOR_TABLE = "professor";
    public static final String PROFESSOR_ID = "idprofessor";
    public static final String PROFESSOR_ID_USER = "iduser";
    public static final String PROFESSOR_ID_UNIVERSITY = "iduniversityl";
    public static final String PROFESSOR_ID_FACULTY = "idfaculty";
    public static final String PROFESSOR_ID_PULPIT = "idpulpit";

    public static final String PLAN_TABLE = "plan";
    public static final String PLAN_ID = "idplan";
    public static final String PLAN_ID_PROFESSOR = "idprofessor";
    public static final String PLAN_BEGIN_YEAR = "beginyear";
    public static final String PLAN_TYPE = "type";
    public static final String PLAN_FIRST_SEM_PLAN = "firstsemplan";
    public static final String PLAN_FIRST_SEM_FACT = "firstsemfact";
    public static final String PLAN_SECOND_SEM_PLAN = "secondsemplan";
    public static final String PLAN_SECOND_SEM_FACT = "secondsemfact";

    public static final String IMAGE_URL = "/universitysystem/assets/images/icon.png";
    public static final String SYSTEM_TITLE = "University System";

    //region views locations
    public static final String VIEW_MAIN_LOCATION = "/universitysystem/views/mainView.fxml";
    public static final String VIEW_REGISTRATION_LOCATION = "/universitysystem/views/signUp.fxml";
    public static final String VIEW_LOGIN_LOCATION = "/universitysystem/views/login.fxml";
    public static final String VIEW_USER_POLL_LOCATION = "/universitysystem/views/UserPollView.fxml";
    public static final String VIEW_PROFESSOR_POLL_LOCATION = "/universitysystem/views/ProfessorPollView.fxml";
    public static final String VIEW_ACADEMIC_FOR_DATE_LOCATION = "/universitysystem/views/academicDegreeForDate.fxml";
    public static final String VIEW_FACT_MORE_PLAN_LOCATION = "/universitysystem/views/factMoreThanPlanView.fxml";
    //endregion
}

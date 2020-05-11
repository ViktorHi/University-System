package universitysystem.model.structs;

public class CustomTableLine {
    private String firstName;
    private String lastName;
    private String middleName;
    private String faculty;
    private String pulpit;
    private String academicDegree;
    private String degree;
    private String post;
    private int year;
    private int semestr;
    private int fact;
    private int plan;

    public CustomTableLine(String firstName, String lastName, String middleName, String faculty, String pulpit, String academicDegree, String degree, String post, int year, int semestr, int fact, int plan) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.faculty = faculty;
        this.pulpit = pulpit;
        this.academicDegree = academicDegree;
        this.degree = degree;
        this.post = post;
        this.year = year;
        this.semestr = semestr;
        this.fact = fact;
        this.plan = plan;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getPulpit() {
        return pulpit;
    }

    public void setPulpit(String pulpit) {
        this.pulpit = pulpit;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemestr() {
        return semestr;
    }

    public void setSemestr(int semestr) {
        this.semestr = semestr;
    }

    public int getFact() {
        return fact;
    }

    public void setFact(int fact) {
        this.fact = fact;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }
}

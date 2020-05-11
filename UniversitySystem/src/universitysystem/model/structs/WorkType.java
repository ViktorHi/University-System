package universitysystem.model.structs;

public enum WorkType{

    EDUCATIONAL_WORK ("Учебная работа", "EDUCATIONAL_WORK"),
    EDUCATIONAL_AND_METHODICAL_WORK("Учебно-методическая работа", "EDUCATIONAL_AND_METHODICAL_WORK"),
    SCIENTIFIC_AND_METHODICAL_WORK("Научно-методическая работа", "SCIENTIFIC_AND_METHODICAL_WORK"),
    RESEARCH_WORK("Научно-исследовательская работа", "RESEARCH_WORK"),
    ORGANIZATIONAL_AND_METHODICAL_WORK("Организационно-методическая работа", "ORGANIZATIONAL_AND_METHODICAL_WORK"),
    EXTRACURRICULAR_WORK_WITH_STUDENTS("Внеучебная работа со студентами", "EXTRACURRICULAR_WORK_WITH_STUDENTS"),
    OTHER_TYPES_OF_WORK("Прочие виды работ", "OTHER_TYPES_OF_WORK");

    private  String text;
    private  String stringValue;

    WorkType(String t, String v) {
        text = t;
        stringValue = v;
    }

    public String getText() {
        return text;
    }

    public String getStringValue() {
        return stringValue;
    }
}


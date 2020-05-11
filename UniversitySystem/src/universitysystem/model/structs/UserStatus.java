package universitysystem.model.structs;

public enum  UserStatus {
    admin("admin") ,
    not_admin("not_admin");

    private final String value;

    UserStatus(String value)
    {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

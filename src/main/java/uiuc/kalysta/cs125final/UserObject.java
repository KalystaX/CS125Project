package uiuc.kalysta.cs125final;

public class UserObject {
    private String name;
    private String phone;

    public UserObject(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }
}
